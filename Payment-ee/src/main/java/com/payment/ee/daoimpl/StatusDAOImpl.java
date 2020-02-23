package com.payment.ee.daoimpl;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.payment.ee.dao.StatusDAO;
import com.payment.ee.entitiy.Status;

@Repository
public class StatusDAOImpl implements StatusDAO {
	@Autowired
	private SessionFactory sessionFactory;

	/**
     * retrieve payment status by payment status description
     *
     * @param statusName
     * 
     * 
     * @return Status
     */
	@Override
	public Status getPaymentStatusByPaymentStatusDesc(String statusName) {
		Query query = sessionFactory.getCurrentSession()
				.createNamedQuery("Status.findStatusByStatusName", Status.class);
		query.setParameter("statusName", statusName);
		return (Status) query.getSingleResult();
	}

}
