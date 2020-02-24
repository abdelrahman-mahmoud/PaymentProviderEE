package com.payment.ee.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.payment.ee.constants.AppConstants;
import com.payment.ee.constants.AppExceptionMessages;
import com.payment.ee.pojo.PaymentTransaction;

@Component
public class ExtractorController {
	@Autowired
	private PaymentController paymentController;
	
	@Autowired
	private InquiryController inquiryController;
	
	@Autowired
	private Logger logger;

	private String operation = null;
	private PaymentTransaction paymentTransaction = null;
	
	
	/**
     * Extract and perform the requested operation (either payment or inquiry) and the payment transaction information
     *
     * @param args contains data sent during running the application from the console
	 * @throws Exception 
     */
	public void perform(String[] args) throws Exception {
		//String[] argDev = {"register","clientId=IBEE","orderId=book-37843","amount=250","currency=EUR","payMethod=Cash","payTokenId=cc-367b9832f651a01"};
		//String[] argDev = {"totalAmountOfSuccessPayments","clientId=C-ID-4", "fromDate=2020-02-21" , "toDate=2020-02-24"};
		//String[] argDev = {"findByOrder","clientId=IBEE", "orderId=book-37843"};
		
		// extract the payment information
		
			dataExtractor(args);
		
		
		//execute the operation either payment or inquiry
		if(operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_REGISTER) || operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_AUTHORISE) 
				|| operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_CAPTURE) || operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_REVERSE)) {
			// Call proxy method of PaymentController class to perform the requested operation (register, authorise, capture or reverse)
			paymentController.proxy(operation, paymentTransaction);
		}else if(operation.equalsIgnoreCase(AppConstants.REPORT_FIND_BY_ORDER_ID_CLIENT_ID) || operation.equalsIgnoreCase(AppConstants.REPORT_FIND_PENDING)
				|| operation.equalsIgnoreCase(AppConstants.REPORT_TOTAL_AMOUNT_OF_SUCCESS_PAYMENTS)) {
			// Call proxy method of InquiryController class to perform the requested operation (findByOrder, findPending or totalAmountOfSuccessPayments)
			inquiryController.proxy(operation, paymentTransaction);
		}
	}

	
	/**
     * Extract and setting the requested operation (either payment or inquiry) and the payment transaction information
     *
     * @param args contains data sent during running the application from the console
	 * @throws Exception 
     */
	private void dataExtractor(String[] args) throws Exception {
		paymentTransaction = new PaymentTransaction();
		operation = "";
		if (args != null && args.length > 0) {
			List<String> list = Arrays.asList(args);

			String[] str = null;
			for (String param : list) {
				if (!param.contains("=")) {
					operation = param;
					boolean isValidOperation = false;
					if (operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_REGISTER)) {
						paymentTransaction.setTransactionType(AppConstants.TRANSACTION_TYPE_REG_CODE);
						isValidOperation = true;
					} else if (operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_AUTHORISE)) {
						paymentTransaction.setTransactionType(AppConstants.TRANSACTION_TYPE_AUTH_CODE);
						isValidOperation = true;
					} else if (operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_CAPTURE)) {
						paymentTransaction.setTransactionType(AppConstants.TRANSACTION_TYPE_CAP_CODE);
						isValidOperation = true;
					} else if (operation.equalsIgnoreCase(AppConstants.TRANSACTION_TYPE_REVERSE)) {
						paymentTransaction.setTransactionType(AppConstants.TRANSACTION_TYPE_REV_CODE);
						isValidOperation = true;
					} else if (operation.equalsIgnoreCase(AppConstants.REPORT_FIND_BY_ORDER_ID_CLIENT_ID) || operation.equalsIgnoreCase(AppConstants.REPORT_FIND_PENDING)
							|| operation.equalsIgnoreCase(AppConstants.REPORT_TOTAL_AMOUNT_OF_SUCCESS_PAYMENTS)) {
						isValidOperation = true;
					}
					if(operation == null || "".equals(operation) || !isValidOperation) {
						throw new Exception(AppExceptionMessages.INVALID_ARGUMENTS);
					}
					
					continue;
				}

				if (param != null) {
					str = param.split("=");
					if (str[0].equalsIgnoreCase("clientId")) {
						// extract ClientId
						paymentTransaction.setClientId(str[1]);
					} else if (str[0].equalsIgnoreCase("orderId")) {
						// extract OrderId
						paymentTransaction.setOrderId(str[1]);
					} else if (str[0].equalsIgnoreCase("amount")) {
						// extract amount
						paymentTransaction.setAmount(Double.parseDouble(str[1]));
					} else if (str[0].equalsIgnoreCase("currency")) {
						// extract Currency
						paymentTransaction.setCurrency(str[1]);
					} else if (str[0].equalsIgnoreCase("payMethod")) {
						// extract payMethod
						paymentTransaction.setPaymentMethod(str[1]);
					} else if (str[0].equalsIgnoreCase("payTokenId")) {
						// extract payTokenId
						paymentTransaction.setToken(str[1]);
					} else if(str[0].equalsIgnoreCase("fromDate")) {
						// extract fromDate
						paymentTransaction.setFromDateStr(str[1]);
					} else if(str[0].equalsIgnoreCase("toDate")) {
						// extract toDate
						paymentTransaction.setToDateStr(str[1]);
					}
				}
				
			}
		}else {
			throw new Exception(AppExceptionMessages.INVALID_ARGUMENTS);
			//logger.error("Please specify a command. Available commands: register, authorise, capture, reverse, findByOrder, findPending, findTotal");
		}
	}
	
	
}
