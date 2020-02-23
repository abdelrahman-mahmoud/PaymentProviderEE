package com.payment.ee.service;

import com.payment.ee.pojo.PaymentTransaction;

public interface OrderPaymentService {
	boolean registerTransaction(PaymentTransaction paymentTransaction) throws Exception;

//	boolean isOrderIdExists(String orderId);

	void procceedWithTransaction(PaymentTransaction paymentTransaction) throws Exception;
	

}
