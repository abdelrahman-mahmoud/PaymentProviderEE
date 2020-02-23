package com.payment.ee.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentTransaction {
	private String clientId;
	private String orderId;
	private double amount;
	private String currency;
	private String paymentMethod;
	private String transactionType;
	private String token;
	
	private String fromDateStr;
	private String toDateStr;
	
	private Date fromDate;
	private Date toDate;

	public PaymentTransaction(String clientId, String orderId, double amount, String currency, String paymentMethod,
			String transactionType, String token, String fromDate, String toDate) {
		super();
		this.clientId = clientId;
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.paymentMethod = paymentMethod;
		this.transactionType = transactionType;
		this.token = token;
		this.fromDateStr = fromDate;
		this.toDateStr = toDate;
	}

	public PaymentTransaction() {
	}

	public PaymentTransaction(String clientId, String orderId, double amount, String currency, String paymentMethod,
			String transactionType, String token) {
		super();
		this.clientId = clientId;
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.paymentMethod = paymentMethod;
		this.transactionType = transactionType;
		this.token = token;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
		try {
			toDate =  new SimpleDateFormat("yyyy-MM-dd").parse(this.toDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getToDate() {
		return toDate;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
		try {
			fromDate =  new SimpleDateFormat("yyyy-MM-dd").parse(this.fromDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public Date getFromDate() {
		return fromDate;
	}


	@Override
	public String toString() {
		return "PaymentTransaction [clientId=" + clientId + ", orderId=" + orderId + ", amount=" + amount
				+ ", currency=" + currency + ", paymentMethod=" + paymentMethod + ", Status=" + transactionType +"]";
	}

}
