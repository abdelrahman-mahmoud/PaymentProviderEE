package com.payment.ee.entitiy;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;


/**
 * The persistent class for the currency database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c"),
	@NamedQuery(name = "Currency.findByCurrencyCode", query = "FROM Currency c where c.currencyCode = :currencyCode")})
public class Currency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="currency_code")
	private String currencyCode;

	//bi-directional many-to-one association to OrderPayment
	@OneToMany(mappedBy="currency")
	private List<OrderPayment> orderPayments;

	public Currency() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public List<OrderPayment> getOrderPayments() {
		return this.orderPayments;
	}

	public void setOrderPayments(List<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}

	public OrderPayment addOrderPayment(OrderPayment orderPayment) {
		getOrderPayments().add(orderPayment);
		orderPayment.setCurrency(this);

		return orderPayment;
	}

	public OrderPayment removeOrderPayment(OrderPayment orderPayment) {
		getOrderPayments().remove(orderPayment);
		orderPayment.setCurrency(null);

		return orderPayment;
	}

}