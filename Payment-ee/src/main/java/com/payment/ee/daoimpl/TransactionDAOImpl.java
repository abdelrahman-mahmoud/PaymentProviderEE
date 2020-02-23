package com.payment.ee.daoimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.ee.dao.TransactionDAO;
import com.payment.ee.entitiy.Transaction;

@Repository
public class TransactionDAOImpl implements TransactionDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
     * save a transaction occurred on a certain order id
     *
     * @param Transaction
     * 
     * 
     */
	@Override
	public void saveTransaction(Transaction transaction) {
		sessionFactory.getCurrentSession().save(transaction);

	}

}
