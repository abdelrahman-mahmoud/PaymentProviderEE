package com.payment.ee.entitiy;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="created_on")
	private Timestamp createdOn;

	//bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	//bi-directional many-to-one association to TransactionType
	@ManyToOne
	@JoinColumn(name="transaction_type_id")
	private TransactionType transactionType;

	//bi-directional many-to-one association to OrderPayment
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="order_payment_id")
	private OrderPayment orderPayment;

	public Transaction() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public TransactionType getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public OrderPayment getOrderPayment() {
		return this.orderPayment;
	}

	public void setOrderPayment(OrderPayment orderPayment) {
		this.orderPayment = orderPayment;
	}

}