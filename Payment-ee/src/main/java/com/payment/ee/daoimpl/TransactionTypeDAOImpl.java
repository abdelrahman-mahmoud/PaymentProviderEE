package com.payment.ee.daoimpl;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.ee.dao.TransactionTypeDAO;
import com.payment.ee.entitiy.TransactionType;

@Repository
public class TransactionTypeDAOImpl implements TransactionTypeDAO {
	@Autowired
	private SessionFactory sessionFactory;

	/**
     * get a transaction type by transaction type code
     *
     * @param tranTypeCode
     * 
     * 
     */
	@Override
	public TransactionType getTransactionTypeByTranTypeCode(String tranTypeCode) {
		Query query = sessionFactory.getCurrentSession()
				.createNamedQuery("TransactionType.findTransactionTypeByTranTypeCode", TransactionType.class);
		query.setParameter("tranTypeCode", tranTypeCode);
		
		return (TransactionType) query.getSingleResult();
	}

}
