package com.payment.ee.daoimpl;

import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.ee.dao.OrderPaymentDAO;
import com.payment.ee.entitiy.OrderPayment;
import com.payment.ee.pojo.PaymentTransaction;

@Repository
public class OrderPaymentDAOImpl implements OrderPaymentDAO {

	// inject the hibernate session factory
	@Autowired
	private SessionFactory sessionFactory;

	/**
     * save order or update existing order
     *
     * @param OrderPayment
     */
	@Override
	public void saveTransaction(OrderPayment OrderPayment) {
		// TODO Auto-generated method stub
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(OrderPayment);		
	}
/*
	@Override
	public PaymentReqDetail findByOrderId(String orderId) {
		PaymentReqDetail paymentReqDetail = null;
		Query query = sessionFactory.getCurrentSession().createNamedQuery("PaymentReqDetail.findByOrderId", PaymentReqDetail.class);
		query.setParameter("orderId", orderId);
		try {
			paymentReqDetail = (PaymentReqDetail) query.getSingleResult();
			
		}catch(Exception e) {
			
				
		}
		return paymentReqDetail;
	}
*/
	/**
     * retrieve an order by client id and order id
     *
     * @param PaymentTransaction
     * @return OrderPayment
     */
	@Override
	public OrderPayment findByClientIdOrderId(PaymentTransaction paymentTransaction) {
		OrderPayment orderPayment = null;
		Query query = sessionFactory.getCurrentSession().createNamedQuery("OrderPayment.findByClientIdOrderId", OrderPayment.class);
		query.setParameter("orderId", paymentTransaction.getOrderId());
		query.setParameter("clientId", paymentTransaction.getClientId());
		orderPayment = (OrderPayment) query.getSingleResult();
		return orderPayment;
	}

	
	/**
     * retrieve a list of pending orders
     *
     * @param PaymentTransaction
     * @param List<Integer> pendingStatus
     * 
     * @return List<OrderPayment>
     */
	@Override
	public List<OrderPayment> findPendingByClientId(PaymentTransaction paymentTransaction, List<Integer> pendingStatus) {
		/*
		Query query = sessionFactory.getCurrentSession().createNamedQuery("OrderPayment.findPending");
		query.setParameter("clientId", paymentTransaction.getClientId());	
		return (List<OrderPayment>)query.getResultList();
		*/		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<OrderPayment> query = builder.createQuery(OrderPayment.class);
		Root<OrderPayment> root = query.from(OrderPayment.class);
		
		
		Predicate clientIdPerdicate = builder.equal(root.get("clientId"), paymentTransaction.getClientId());
		Predicate statusIdPerdicate = builder.equal(root.get("status"), 1);
		In<Integer> inClause = builder.in(root.get("orderStatus"));
		for(Integer pending : pendingStatus) {
			inClause.value(pending);
		}
		
		Predicate wherePerdicate = builder.and(clientIdPerdicate,statusIdPerdicate,inClause);
		builder.in(inClause);
		query.select(root).where(wherePerdicate);
		
		List<OrderPayment> list = sessionFactory.createEntityManager().createQuery(query).getResultList();
		 
		return list;
	}
	
	/**
     * retrieve a total amount of success payments for a certain clientId within a period of time
     *
     * @param PaymentTransaction
     * 
     * @return Number
     */
	@Override
	public Number findTotalAmountOfSuccessPayments(PaymentTransaction paymentTransaction) {
		Query query = sessionFactory.getCurrentSession().createNamedQuery("OrderPayment.totalAmountOfSuccessPayments");
		
		
		query.setParameter("clientId", paymentTransaction.getClientId());
		query.setParameter("fromDate", paymentTransaction.getFromDate(), TemporalType.TIMESTAMP);
		query.setParameter("toDate", paymentTransaction.getToDate(), TemporalType.TIMESTAMP);
		Object result = query.getResultList().get(0);
		if(result == null) {
			return null;
		}else {
			return (Number) result;
		}
	}
	
	
	
	

}
