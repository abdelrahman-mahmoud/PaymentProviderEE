package com.payment.ee.service;

import java.util.List;

import com.payment.ee.pojo.PaymentTransaction;

public interface InquiryService {
	
	PaymentTransaction findByClientIdOrderId(PaymentTransaction paymentTransaction) throws Exception;
	
	List<PaymentTransaction> findPendingByClientId(PaymentTransaction paymentTransaction, List<Integer> pendingStatus) throws Exception;
	
	double findTotalAmountOfSuccessPayments(PaymentTransaction paymentTransaction) throws Exception;

}
