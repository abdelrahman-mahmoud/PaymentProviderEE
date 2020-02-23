package com.payment.ee.daoimpl;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.ee.dao.OrderStatusDAO;
import com.payment.ee.entitiy.OrderStatus;
import com.payment.ee.entitiy.TransactionType;

@Repository
public class OrderStatusDAOImpl implements OrderStatusDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	/**
     * retrieve Order Status By Statuse Code
     *
     * @param statusCode
     * 
     * 
     * @return OrderStatus
     */
	@Override
	public OrderStatus getOrderStatusByStatuseCode(String statusCode) {
		Query query = sessionFactory.getCurrentSession()
				.createNamedQuery("OrderStatus.findByStatusCode", OrderStatus.class);
		query.setParameter("orderStatusCode", statusCode);
		
		return (OrderStatus) query.getSingleResult();
	}

}
