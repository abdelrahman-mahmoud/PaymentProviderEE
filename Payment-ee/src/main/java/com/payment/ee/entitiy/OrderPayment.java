package com.payment.ee.entitiy;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the order_payment database table.
 * 
 */
@Entity
@Table(name = "order_payment")
@NamedQueries({
@NamedQuery(name="OrderPayment.findAll", query="SELECT o FROM OrderPayment o"),
@NamedQuery(name="OrderPayment.findByOrderId", query="FROM OrderPayment o where o.orderId = :orderId"),
@NamedQuery(name="OrderPayment.findByClientIdOrderId", query="FROM OrderPayment o where o.orderId = :orderId and o.clientId = :clientId"),
@NamedQuery(name="OrderPayment.findPending", query = "FROM OrderPayment o where o.clientId = :clientId and o.orderStatus.id not in (3,4) and o.status.id = 1" ),
@NamedQuery(name="OrderPayment.totalAmountOfSuccessPayments", query = "SELECT sum(o.amount) FROM OrderPayment o where o.clientId = :clientId and o.orderStatus.id = 3 and o.status.id = 1 and o.modifiedOn BETWEEN :fromDate AND :toDate" )
})
public class OrderPayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double amount;

	@Column(name = "client_id")
	private String clientId;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "modified_on")
	@UpdateTimestamp
	private Timestamp modifiedOn;

	@Column(name = "token")
	private String token;

	// bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	// bi-directional many-to-one association to Currency
	@ManyToOne
	private Currency currency;

	// bi-directional many-to-one association to OrderStatus
	@ManyToOne
	@JoinColumn(name = "order_status_id")
	private OrderStatus orderStatus;

	// bi-directional many-to-one association to PaymentMethod
	@ManyToOne
	@JoinColumn(name = "payment_method_id")
	private PaymentMethod paymentMethod;

	// bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy = "orderPayment", cascade = CascadeType.ALL)
	private List<Transaction> transactions;

	public OrderPayment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Timestamp getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Transaction addTransaction(Transaction transaction) {
		if (getTransactions() == null) {
			this.transactions = new ArrayList<Transaction>();
		}
		getTransactions().add(transaction);
		transaction.setOrderPayment(this);

		return transaction;
	}

}