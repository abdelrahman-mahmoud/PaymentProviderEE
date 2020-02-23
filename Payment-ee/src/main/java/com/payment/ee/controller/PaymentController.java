package com.payment.ee.controller;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.payment.ee.constants.AppConstants;
import com.payment.ee.constants.AppExceptionMessages;
import com.payment.ee.exceptions.InvalidOrderIdException;
import com.payment.ee.pojo.OutputMessage;
import com.payment.ee.pojo.PaymentTransaction;
import com.payment.ee.service.OrderPaymentService;

@Controller
public class PaymentController {

	@Autowired
	 OrderPaymentService orderPaymentService;
	
	@Autowired
	 Logger logger;

	

	/**
     * handle each transaction type (either register, authorise, capture or reverse) is requested
     *
     * @param operation requested
     * @param payment transaction details
     */
	@Transactional
	public void proxy(String operation, PaymentTransaction paymentTransaction) {
		
		if (operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_REGISTER)) {
			// handle request with transaction type [register]
			registerTransaction(paymentTransaction);
		} else {
			// handle any other request with transaction type either authorise, capture or reverse
			procceedWithTransaction(paymentTransaction);
		}
	}

	/**
     * handle only register a new order
     *
     * @param payment transaction details
     */
	private void registerTransaction(PaymentTransaction paymentTransaction) {
		OutputMessage outputMessage = null;
		try {
			if(paymentTransaction == null) {
				throw new Exception("ERROR");
			}
			
			boolean isTransactionRegistered = orderPaymentService.registerTransaction(paymentTransaction);
			if(isTransactionRegistered) {
				outputMessage = new OutputMessage(AppConstants.TRANSACTION_TYPE_REGISTER, AppConstants.STATUS_SUCCESS, null);
				logger.info(outputMessage);
			}
		} catch (Exception e) {
			if (e instanceof ConstraintViolationException) {
				// in case the the client is trying to submit a used order id by same client
				outputMessage = new OutputMessage(AppConstants.TRANSACTION_TYPE_REGISTER, AppConstants.PAYMENT_STATUS_ERROR,
						AppExceptionMessages.ORDER_ID_ALREADY_EXISTS_EXCEPTION_MSG);
			} else {
				outputMessage = new OutputMessage(AppConstants.TRANSACTION_TYPE_REGISTER, AppConstants.PAYMENT_STATUS_ERROR,
						e.getMessage());
			}
			e.printStackTrace();
			System.out.println(outputMessage);
			logger.info("Problem: "+outputMessage);
		}
	}

	/**
     * handle transaction types like (either authorise, capture or reverse) is requested
     *
     * @param payment transaction details
     */
	private void procceedWithTransaction(PaymentTransaction paymentTransaction) {
		OutputMessage outputMessage = null;
		try {
			orderPaymentService.procceedWithTransaction(paymentTransaction);
			outputMessage = new OutputMessage(paymentTransaction.getTransactionType(), AppConstants.STATUS_SUCCESS,
					null);
			logger.info(outputMessage);
		} catch (Exception e) {
			if (e instanceof InvalidOrderIdException) {
				// in case the the client is trying to submit order id or client id which is not
				// exist in DB
				outputMessage = new OutputMessage(paymentTransaction.getTransactionType(),
						AppConstants.PAYMENT_STATUS_ERROR, AppExceptionMessages.INVALID_ORDER_ID_EXCEPTION_MSG);
			} else {
				outputMessage = new OutputMessage(AppConstants.TRANSACTION_TYPE_REGISTER, AppConstants.PAYMENT_STATUS_ERROR,
						e.getMessage());
			}
			logger.info("Problem: "+outputMessage);
		}
		
	}

}
