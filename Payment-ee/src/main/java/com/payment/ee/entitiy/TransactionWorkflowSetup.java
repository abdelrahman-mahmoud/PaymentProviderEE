package com.payment.ee.entitiy;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;




/**
 * The persistent class for the transaction_workflow_setup database table.
 * 
 */
@Entity
@Table(name="transaction_workflow_setup")
@NamedQuery(name="TransactionWorkflowSetup.findAll", query="SELECT t FROM TransactionWorkflowSetup t")
public class TransactionWorkflowSetup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="next_step")
	private int nextStep;

	//bi-directional many-to-one association to OrderStatus
	@ManyToOne
	@JoinColumn(name="current_step")
	private OrderStatus orderStatus;

	public TransactionWorkflowSetup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNextStep() {
		return this.nextStep;
	}

	public void setNextStep(int nextStep) {
		this.nextStep = nextStep;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}