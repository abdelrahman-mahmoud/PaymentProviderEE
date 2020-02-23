package com.payment.ee.constants;

public class AppExceptionMessages {
	
	public static final String CURRENCY_NOT_SUPPORTED_EXCEPTION_MSG = "Currency is not suuported";
	public static final String TRANSACTION_TYPE_NOT_SUPPORTED_EXCEPTION_MSG = "Transaction type is not supported";
	public static final String PAYMENT_METHOD_NOT_SUPPORTED_EXCEPTION_MSG = "Payment methos is not supported";
	public static final String INVALID_ORDER_ID_EXCEPTION_MSG = "Invalid Order ID or Client ID";
	public static final String INVALID_CLIENT_ID_EXCEPTION_MSG = "Invalid Client ID";
	public static final String ORDER_ID_ALREADY_EXISTS_EXCEPTION_MSG = "Order ID is already exists to that user";
	public static final String INVALID_TRANSITION = "Invalid workflow transition ";
	public static final String ORDER_IS_ALREADY_CAPTURED = "Multiple successful payments for the same order is not allowed";
	public static final String No_RECORD_MATCHES_THE_SEARCH_CRITERIA = "No Record(s) matches the search criteria ";
	public static final String INVALID_ARGUMENTS = "Please specify a command. Available commands: register, authorise, capture, reverse, findByOrder, findPending, findTotal";
	public static final String CURRENCY_IS_REQUIRED = "Currency is Required";
	public static final String PAYMENT_METHOD_IS_REQUIRED = "Payment is Required";
	public static final String TRANSACTION_TYPE_IS_REQUIRED = "Transaction Type is required";
	public static final String INVALID_REQUEST = "Invalid Request";

}
