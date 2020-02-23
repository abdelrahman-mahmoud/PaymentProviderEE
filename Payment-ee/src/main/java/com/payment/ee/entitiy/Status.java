package com.payment.ee.entitiy;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the status database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Status.findAll", query="SELECT s FROM Status s"),
	@NamedQuery(name = "Status.findStatusByStatusName", query = "FROM Status s where s.statusName = :statusName")})
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="status_name")
	private String statusName;

	//bi-directional many-to-one association to OrderPayment
	@OneToMany(mappedBy="status")
	private List<OrderPayment> orderPayments;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="status")
	private List<Transaction> transactions;

	public Status() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<OrderPayment> getOrderPayments() {
		return this.orderPayments;
	}

	public void setOrderPayments(List<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}

	public OrderPayment addOrderPayment(OrderPayment orderPayment) {
		getOrderPayments().add(orderPayment);
		orderPayment.setStatus(this);

		return orderPayment;
	}

	public OrderPayment removeOrderPayment(OrderPayment orderPayment) {
		getOrderPayments().remove(orderPayment);
		orderPayment.setStatus(null);

		return orderPayment;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setStatus(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setStatus(null);

		return transaction;
	}

}