package com.payment.ee.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.ee.constants.AppExceptionMessages;
import com.payment.ee.dao.OrderPaymentDAO;
import com.payment.ee.entitiy.OrderPayment;
import com.payment.ee.exceptions.InvalidOrderIdException;
import com.payment.ee.pojo.PaymentTransaction;
import com.payment.ee.service.InquiryService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InquiryServiceImpl implements InquiryService {
	
	@Autowired
	private OrderPaymentDAO orderPaymentDAO;

	/**
     * Ask the data object layer to retrieve an order by client id and order id
     *
     * @param PaymentTransaction
     * 
     * @return PaymentTransaction
     */
	@Override
	@Transactional(readOnly = true)
	public PaymentTransaction findByClientIdOrderId(PaymentTransaction paymentTransaction) throws Exception {
		OrderPayment result =  null;
		try {
			
			result = orderPaymentDAO.findByClientIdOrderId(paymentTransaction);
			paymentTransaction.setAmount(result.getAmount());
			paymentTransaction.setCurrency(result.getCurrency().getCurrencyCode());
			paymentTransaction.setPaymentMethod(result.getPaymentMethod().getPaymentMethodName());
			paymentTransaction.setTransactionType(result.getOrderStatus().getOrderStatusName());
		}catch (Exception e) {
			if (e instanceof NoResultException) {
				// in case the currency is not supported (Not found in the DB)
				throw new InvalidOrderIdException(AppExceptionMessages.INVALID_CLIENT_ID_EXCEPTION_MSG + 
						" [Client ID-> "+paymentTransaction.getClientId()+", Order ID->" +paymentTransaction.getOrderId()+"]");
			} else {
				// handle any other exception
				throw e;
			}
		}
		return paymentTransaction;
	}

	/**
     * Ask the data object layer to retrieve pending orders by client id
     *
     * @param PaymentTransaction
     * @param pendingStatus
     * 
     * @return List<PaymentTransaction>
     */
	@Override
	@Transactional(readOnly = true)
	public List<PaymentTransaction> findPendingByClientId(PaymentTransaction paymentTransaction, List<Integer> pendingStatus) throws Exception{
		List<PaymentTransaction> pendingPaymentTransactionOrderList = new ArrayList<PaymentTransaction>();
		try {
			List<OrderPayment> pendingOrderList = orderPaymentDAO.findPendingByClientId(paymentTransaction, pendingStatus);
			for(OrderPayment order : pendingOrderList) {
				PaymentTransaction payment = new PaymentTransaction();
				payment.setOrderId(order.getOrderId());
				payment.setClientId(order.getClientId());
				payment.setAmount(order.getAmount());
				payment.setCurrency(order.getCurrency().getCurrencyCode());
				payment.setPaymentMethod(order.getPaymentMethod().getPaymentMethodName());
				payment.setTransactionType(order.getOrderStatus().getOrderStatusName());
				
				pendingPaymentTransactionOrderList.add(payment);
			}
		}catch (Exception e) {
			if (e instanceof NoResultException) {
				// in case the currency is not supported (Not found in the DB)
				throw new InvalidOrderIdException(AppExceptionMessages.INVALID_CLIENT_ID_EXCEPTION_MSG + 
						" [Client ID-> "+paymentTransaction.getClientId()+", Order ID->" +paymentTransaction.getOrderId()+"]");
			} else {
				// handle any other exception
				throw e;
			}
		}
		return pendingPaymentTransactionOrderList;
	}
	
	
	/**
     * Ask the data object layer to retrieve a total amount of successful payments within a period of time
     *
     * @param PaymentTransaction
     * 
     * 
     * @return total 
     */
	@Override
	@Transactional(readOnly = true)
	public double findTotalAmountOfSuccessPayments(PaymentTransaction paymentTransaction) throws Exception {
		// TODO Auto-generated method stub
		Number number = orderPaymentDAO.findTotalAmountOfSuccessPayments(paymentTransaction);
		
		if(number == null) {
			throw new Exception(AppExceptionMessages.No_RECORD_MATCHES_THE_SEARCH_CRITERIA);
		}else {
			return number.doubleValue();
		}
	}


}
