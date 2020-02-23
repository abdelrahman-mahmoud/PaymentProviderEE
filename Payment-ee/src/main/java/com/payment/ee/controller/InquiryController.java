package com.payment.ee.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.payment.ee.constants.AppConstants;
import com.payment.ee.constants.AppExceptionMessages;
import com.payment.ee.exceptions.InvalidOrderIdException;
import com.payment.ee.pojo.OutputMessage;
import com.payment.ee.pojo.PaymentTransaction;
import com.payment.ee.service.InquiryService;

@Component
public class InquiryController {

	@Autowired
	private InquiryService inquiryService;

	@Autowired
	private Logger logger;

	/**
     * decide which report is requested
     *
     * @param operation requested report
     * @param payment transaction details
     */
	public void proxy(String operation, PaymentTransaction paymentTransaction) {
		if (operation.equalsIgnoreCase(AppConstants.REPORT_FIND_BY_ORDER_ID_CLIENT_ID)) {
			findClientIdByOrderIdReport(paymentTransaction);
		} else if (operation.equalsIgnoreCase(AppConstants.REPORT_FIND_PENDING)) {
			findPending(paymentTransaction);
		} else if (operation.equalsIgnoreCase(AppConstants.REPORT_TOTAL_AMOUNT_OF_SUCCESS_PAYMENTS)) {
			findTotalAmountOfSuccessPayments(paymentTransaction);
		}

	}

	/**
     * Find a payment by client id and order id
     *
     * 
     * @param payment transaction details
     */
	private void findClientIdByOrderIdReport(PaymentTransaction paymentTransaction) {
		try {
			PaymentTransaction order = inquiryService.findByClientIdOrderId(paymentTransaction);
			logger.info("findByOrder: " + order);
		} catch (Exception e) {
			OutputMessage outputMessage = null;
			if (e instanceof InvalidOrderIdException) {
				// in case the the client is trying to submit order id or client id which is not
				// exist in DB
				outputMessage = new OutputMessage(paymentTransaction.getTransactionType(),
						AppConstants.PAYMENT_STATUS_ERROR, AppExceptionMessages.INVALID_ORDER_ID_EXCEPTION_MSG);
			} else {
				outputMessage = new OutputMessage(AppConstants.REPORT_FIND_BY_ORDER_ID_CLIENT_ID,
						AppConstants.PAYMENT_STATUS_ERROR, e.getMessage());
			}
			logger.info(outputMessage);
		}

	}

	/**
     * Find pending orders (which are not successfully captured or reversed)
     *
     * 
     * @param payment transaction details
     */
	private void findPending(PaymentTransaction paymentTransaction) {
		try {
			List<Integer> pendingStatus = new ArrayList<Integer>();
			pendingStatus.add(1);
			pendingStatus.add(2);
			List<PaymentTransaction> orderList = inquiryService.findPendingByClientId(paymentTransaction,pendingStatus);
			logger.info("findPending: " + orderList);
		} catch (Exception e) {
			OutputMessage outputMessage = null;
			if (e instanceof InvalidOrderIdException) {
				// in case the the client is trying to submit order id or client id which is not
				// exist in DB
				outputMessage = new OutputMessage(paymentTransaction.getTransactionType(),
						AppConstants.PAYMENT_STATUS_ERROR, AppExceptionMessages.INVALID_CLIENT_ID_EXCEPTION_MSG);
			} else {
				outputMessage = new OutputMessage(AppConstants.REPORT_FIND_PENDING, AppConstants.PAYMENT_STATUS_ERROR,
						e.getMessage());
			}
			logger.info(outputMessage);
		}
	}

	/**
     * Find total of payment amounts for all successful payments for specific client and time period
     *
     * 
     * @param payment transaction details
     */
	private void findTotalAmountOfSuccessPayments(PaymentTransaction paymentTransaction) {
		// TODO Auto-generated method stub
		double totalAmount;
		try {
			totalAmount = inquiryService.findTotalAmountOfSuccessPayments(paymentTransaction);
			logger.info("Total Amount of Success Payments for Client [" + paymentTransaction.getClientId() + "] = "
					+ totalAmount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			// e.printStackTrace();

		}

	}

}
