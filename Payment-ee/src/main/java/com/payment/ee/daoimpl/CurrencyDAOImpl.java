package com.payment.ee.daoimpl;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.ee.dao.CurrencyDAO;
import com.payment.ee.entitiy.Currency;

@Repository
public class CurrencyDAOImpl implements CurrencyDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
     * retrieve currency by currency code
     *
     * @param currencyCode
     * @return Currency
     */
	@Override
	public Currency getCurrencyByCurrencyCode(String currencyCode) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createNamedQuery("Currency.findByCurrencyCode", Currency.class);
		query.setParameter("currencyCode", currencyCode);
		return (Currency) query.getSingleResult();
	}

}
