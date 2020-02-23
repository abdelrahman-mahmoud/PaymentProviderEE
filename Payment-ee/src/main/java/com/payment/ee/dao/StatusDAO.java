package com.payment.ee.dao;

import com.payment.ee.entitiy.Status;

public interface StatusDAO {
	
	Status getPaymentStatusByPaymentStatusDesc(String paymentStatusDesc);

}
