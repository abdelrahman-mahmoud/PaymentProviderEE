package com.payment.ee.entitiy;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the payment_method database table.
 * 
 */
@Entity
@Table(name="payment_method")
@NamedQueries({
@NamedQuery(name = "PaymentMethod.findAll", query = "SELECT p FROM PaymentMethod p"),
@NamedQuery(name = "PaymentMethod.findPaymentByPaymentMethodName", query = "FROM PaymentMethod p where p.paymentMethodName = :paymentMethodName ")})

public class PaymentMethod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="payment_method_name")
	private String paymentMethodName;

	//bi-directional many-to-one association to OrderPayment
	@OneToMany(mappedBy="paymentMethod")
	private List<OrderPayment> orderPayments;

	public PaymentMethod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaymentMethodName() {
		return this.paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public List<OrderPayment> getOrderPayments() {
		return this.orderPayments;
	}

	public void setOrderPayments(List<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}

	public OrderPayment addOrderPayment(OrderPayment orderPayment) {
		getOrderPayments().add(orderPayment);
		orderPayment.setPaymentMethod(this);

		return orderPayment;
	}

	public OrderPayment removeOrderPayment(OrderPayment orderPayment) {
		getOrderPayments().remove(orderPayment);
		orderPayment.setPaymentMethod(null);

		return orderPayment;
	}

}