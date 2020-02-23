package com.payment.ee.daoimpl;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.ee.dao.PaymentMethodDAO;
import com.payment.ee.entitiy.PaymentMethod;

@Repository
public class PaymentMethodDAOImpl implements PaymentMethodDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
     * retreive the payment method by payment method name
     *
     * @param paymentMethodName
     */
	@Override
	public PaymentMethod getPaymentMethodByPaymentMethodDesc(String paymentMethodName) {
		Query query = sessionFactory.getCurrentSession()
				.createNamedQuery("PaymentMethod.findPaymentByPaymentMethodName", PaymentMethod.class);
		query.setParameter("paymentMethodName", paymentMethodName);
		
		return (PaymentMethod) query.getSingleResult();
	}

}
