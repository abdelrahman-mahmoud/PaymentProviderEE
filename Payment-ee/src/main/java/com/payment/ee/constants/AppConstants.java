package com.payment.ee.constants;

public class AppConstants {
	
	//Transaction types operations
	public static final String TRANSACTION_TYPE_REGISTER  = "register";
	public static final String TRANSACTION_TYPE_AUTHORISE = "authorise";
	public static final String TRANSACTION_TYPE_CAPTURE  = "capture";
	public static final String TRANSACTION_TYPE_REVERSE  = "reverse";
	
	//Transaction types codes 
	public static final String TRANSACTION_TYPE_REG_CODE  = "REG";
	public static final String TRANSACTION_TYPE_AUTH_CODE = "AUTH";
	public static final String TRANSACTION_TYPE_CAP_CODE  = "CAP";
	public static final String TRANSACTION_TYPE_REV_CODE  = "REV";
	
	//Payment status
	public static final String STATUS_SUCCESS = "SUCCESS";
	public static final String PAYMENT_STATUS_ERROR = "ERROR";
	
	//Reports
	public static final String REPORT_FIND_BY_ORDER_ID_CLIENT_ID = "findByOrder";
	public static final String REPORT_FIND_PENDING = "findPending";
	public static final String REPORT_TOTAL_AMOUNT_OF_SUCCESS_PAYMENTS = "totalAmountOfSuccessPayments";
	

}
