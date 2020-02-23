package com.payment.ee.dao;

import com.payment.ee.entitiy.PaymentMethod;

public interface PaymentMethodDAO {
	PaymentMethod getPaymentMethodByPaymentMethodDesc(String paymentMethodDesc);
}
