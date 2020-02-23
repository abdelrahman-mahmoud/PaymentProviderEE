package com.payment.ee.serviceimpl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.ee.constants.AppConstants;
import com.payment.ee.constants.AppExceptionMessages;
import com.payment.ee.dao.CurrencyDAO;
import com.payment.ee.dao.OrderPaymentDAO;
import com.payment.ee.dao.OrderStatusDAO;
import com.payment.ee.dao.PaymentMethodDAO;
import com.payment.ee.dao.StatusDAO;
import com.payment.ee.dao.TransactionDAO;
import com.payment.ee.dao.TransactionTypeDAO;
import com.payment.ee.entitiy.Currency;
import com.payment.ee.entitiy.OrderPayment;
import com.payment.ee.entitiy.OrderStatus;
import com.payment.ee.entitiy.PaymentMethod;
import com.payment.ee.entitiy.Status;
import com.payment.ee.entitiy.Transaction;
import com.payment.ee.entitiy.TransactionType;
import com.payment.ee.entitiy.TransactionWorkflowSetup;
import com.payment.ee.exceptions.CurrencyNotSupportedException;
import com.payment.ee.exceptions.InvalidOrderIdException;
import com.payment.ee.exceptions.PaymentMethodNotSupportedException;
import com.payment.ee.exceptions.TransactionTypeNotSupportedException;
import com.payment.ee.exceptions.TransactionWorkflowException;
import com.payment.ee.pojo.PaymentTransaction;
import com.payment.ee.service.OrderPaymentService;

@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {

	@Autowired
	private OrderPaymentDAO orderPaymentDAO;

	@Autowired
	 CurrencyDAO currencyDAO;

	@Autowired
	private PaymentMethodDAO paymentMethodDAO;

	@Autowired
	private TransactionTypeDAO transactionTypeDAO;

	@Autowired
	private StatusDAO statusDAO;

	@Autowired
	private OrderStatusDAO orderStatusDAO;

	@Autowired
	private TransactionDAO transactionDAO;

	/**
     * Ask the data object layer to save(register) a new order 
     *
     * @param PaymentTransaction
     * 
     * 
     * @return boolean
     */
	@Override
	@Transactional
	public boolean registerTransaction(PaymentTransaction paymentTransaction) throws Exception {
		OrderPayment orderPayment = new OrderPayment();
		Transaction transaction = new Transaction();

		Currency currency = retrieveCurrency(paymentTransaction.getCurrency());
		
		if(currency==null) {
			throw new CurrencyNotSupportedException(
					AppExceptionMessages.CURRENCY_NOT_SUPPORTED_EXCEPTION_MSG + " [" + paymentTransaction.getCurrency() + "]");
		}

		PaymentMethod paymentMethod = retrievePaymentMethod(paymentTransaction.getPaymentMethod());
		
		if(paymentMethod == null) {
			throw new PaymentMethodNotSupportedException(
					AppExceptionMessages.PAYMENT_METHOD_NOT_SUPPORTED_EXCEPTION_MSG + " [" + paymentTransaction.getPaymentMethod()
							+ "]");
		}

		OrderStatus orderStatus = retrieveOrderStatus(paymentTransaction.getTransactionType());
		
		if(orderStatus == null) {
			throw new PaymentMethodNotSupportedException(
					AppExceptionMessages.PAYMENT_METHOD_NOT_SUPPORTED_EXCEPTION_MSG + " [" + paymentTransaction.getTransactionType() + "]");
		}

		Status status = statusDAO.getPaymentStatusByPaymentStatusDesc(AppConstants.STATUS_SUCCESS);
		

		orderPayment.setAmount(paymentTransaction.getAmount());
		orderPayment.setClientId(paymentTransaction.getClientId());
		orderPayment.setCurrency(currency);
		orderPayment.setModifiedOn(null);
		orderPayment.setOrderId(paymentTransaction.getOrderId());
		orderPayment.setPaymentMethod(paymentMethod);
		orderPayment.setStatus(status);
		orderPayment.setToken(paymentTransaction.getToken());
		orderPayment.setOrderStatus(orderStatus);

		TransactionType transactionType = retrieveTransactionType(paymentTransaction.getTransactionType());
		
		if(transactionType == null) {
			throw new TransactionTypeNotSupportedException(
					AppExceptionMessages.TRANSACTION_TYPE_NOT_SUPPORTED_EXCEPTION_MSG + " [" + paymentTransaction.getTransactionType() + "]");
		}

		transaction.setTransactionType(transactionType);
		transaction.setStatus(status);

		orderPayment.addTransaction(transaction);

		orderPaymentDAO.saveTransaction(orderPayment);
		return true;
	}

	/*
	 * Retrieve the OrderStatus (record) object linked to the Tran Type (ex:REG,
	 * AUTH, CAP, REV )
	 */
	public OrderStatus retrieveOrderStatus(String statusCode) throws Exception {
		OrderStatus orderStatus = null;
		try {
			orderStatus = orderStatusDAO.getOrderStatusByStatuseCode(statusCode);
		} catch (Exception e) {
			return null;
		}
		return orderStatus;
	}

	/*
	 * Retrieve the PaymentMethod (record) object linked to the tranType (ex:Card,
	 * Invoice, cash )
	 */
	public PaymentMethod retrievePaymentMethod(String paymentMethodDesc) throws Exception {
		PaymentMethod paymentMethod = null;
		try {
			paymentMethod = paymentMethodDAO.getPaymentMethodByPaymentMethodDesc(paymentMethodDesc);
		} catch (Exception e) {
			return null;
		}
		return paymentMethod;
	}

	/*
	 * Retrieve the TransactionType (record) object linked to the tranType (ex:REG,
	 * AUTH, CAP or REV )
	 */
	 public TransactionType retrieveTransactionType(String tranType) throws Exception {
		TransactionType transactionType = null;
		try {
			transactionType = transactionTypeDAO.getTransactionTypeByTranTypeCode(tranType);
		} catch (Exception e) {
			return null;
		}
		return transactionType;
	}

	/*
	 * Retrieve the currency (record) object linked to the currency ISO code
	 * (ex:USD,EUR,GBP )
	 */
	public Currency retrieveCurrency(String currencyCode) throws Exception {
		Currency currency = null;
		try {
			currency = currencyDAO.getCurrencyByCurrencyCode(currencyCode);
		} catch (Exception e) {
			return null;
		}
		return currency;
	}

//	@Override
//	public boolean isOrderIdExists(String orderId) {
//		PaymentReqDetail paymentReqDetail = orderPaymentDAO.findByOrderId(orderId);
//		if(paymentReqDetail == null) {
//			return false;
//		}
//		else { 
//			return true;
//		}
//	}
//
	
	/**
     * Ask the data object layer to proceed with transaction(s) on a certain order
     *
     * @param PaymentTransaction
     * 
     */
	@Override
	@Transactional
	public void procceedWithTransaction(PaymentTransaction paymentTransaction) throws Exception {
		// get the object of the requested transaction type received and an exception
		// will be thrown in case invalid transaction type
		TransactionType nextTransactionTypeRequestedByClient = retrieveTransactionType(
				paymentTransaction.getTransactionType());

		// get the object of the requested order status received and an exception will
		// be thrown in case invalid transaction type
		OrderStatus nextOrderStatusRequestByClient = retrieveOrderStatus(paymentTransaction.getTransactionType());

		// Get Order object from order_payment by Client ID and orderID
		OrderPayment orderPayment = retrieveOrderPaymentByClientIdOrderId(paymentTransaction);

		// Multiple successful payments for the same order should be prevented
		if (orderPayment.getOrderStatus().getId() == retrieveTransactionType("CAP").getId()) {
			throw new TransactionWorkflowException(AppExceptionMessages.ORDER_IS_ALREADY_CAPTURED);
		}

		// check: Is the requested transition workflow on an order is valid or not?
		boolean isValidWorkflow = false;
		for (TransactionWorkflowSetup transactionWorkflowSetup : orderPayment.getOrderStatus()
				.getTransactionWorkflowSetups()) {
			if (transactionWorkflowSetup.getNextStep() == nextOrderStatusRequestByClient.getId()) {
				isValidWorkflow = true;
				break;
			}
		}

		if (isValidWorkflow) {
			Transaction nextTransaction = new Transaction();

			Status status = statusDAO.getPaymentStatusByPaymentStatusDesc(AppConstants.STATUS_SUCCESS);

			if (status.getId() == 1) {
				// in case of SUCCESS status, update the order (OrderStatus, modified_on) record
				// in order_payment table, then insert a new record with tran details in
				// Transaction table
				nextTransaction.setOrderPayment(orderPayment);
				nextTransaction.setStatus(status);
				nextTransaction.setTransactionType(nextTransactionTypeRequestedByClient);

				orderPayment.setOrderStatus(nextOrderStatusRequestByClient);
				orderPayment.addTransaction(nextTransaction);

				orderPaymentDAO.saveTransaction(orderPayment);
			} else if (status.getId() == 2) {
				// in case of FAIL status, insert a new record with tran details in Transaction
				// table with FAIL status
				nextTransaction.setOrderPayment(orderPayment);
				nextTransaction.setStatus(status);
				nextTransaction.setTransactionType(nextTransactionTypeRequestedByClient);
				transactionDAO.saveTransaction(nextTransaction);

			}

		} else {
			throw new TransactionWorkflowException(AppExceptionMessages.INVALID_TRANSITION + " FROM "
					+ orderPayment.getOrderStatus().getOrderStatusName() + " to "
					+ nextOrderStatusRequestByClient.getOrderStatusName());
		}

	}

	/**
     * Ask the data object layer to retrieve an order payment by client id and order id
     *
     * @param PaymentTransaction
     * 
     * 
     * @return OrderPayment
     */
	private OrderPayment retrieveOrderPaymentByClientIdOrderId(PaymentTransaction paymentTransaction) throws Exception {
		OrderPayment orderPayment = null;
		try {
			orderPayment = orderPaymentDAO.findByClientIdOrderId(paymentTransaction);
		} catch (Exception e) {
			if (e instanceof NoResultException) {
				// in case the currency is not supported (Not found in the DB)
				throw new InvalidOrderIdException(AppExceptionMessages.INVALID_ORDER_ID_EXCEPTION_MSG + " [Client ID-> "
						+ paymentTransaction.getClientId() + ", Order ID->" + paymentTransaction.getOrderId() + "]");
			} else {
				// handle any other exception
				throw e;
			}
		}
		return orderPayment;
	}


}
