package com.payment.ee.dao;

import java.util.List;

import com.payment.ee.entitiy.OrderPayment;
import com.payment.ee.pojo.PaymentTransaction;

public interface OrderPaymentDAO {
	void saveTransaction(OrderPayment OrderPayment);

	OrderPayment findByClientIdOrderId(PaymentTransaction paymentTransaction);

	List<OrderPayment> findPendingByClientId(PaymentTransaction paymentTransaction, List<Integer> pendingStatus);

	Number findTotalAmountOfSuccessPayments(PaymentTransaction paymentTransaction);
}
