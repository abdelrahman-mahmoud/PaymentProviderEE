package com.payment.ee.entitiy;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the order_status database table.
 * 
 */
@Entity
@Table(name="order_status")
@NamedQueries({
	@NamedQuery(name="OrderStatus.findAll", query="SELECT o FROM OrderStatus o"),
	@NamedQuery(name="OrderStatus.findByStatusCode", query = "FROM OrderStatus o where o.orderStatusCode = :orderStatusCode")
})
public class OrderStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "order_status_code")
	private String orderStatusCode;

	@Column(name="order_status_name")
	private String orderStatusName;

	//bi-directional many-to-one association to TransactionWorkflowSetup
	@OneToMany(mappedBy="orderStatus")
	private List<TransactionWorkflowSetup> transactionWorkflowSetups;

	public OrderStatus() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(String orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}

	public String getOrderStatusName() {
		return this.orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public List<TransactionWorkflowSetup> getTransactionWorkflowSetups() {
		return this.transactionWorkflowSetups;
	}

	public void setTransactionWorkflowSetups(List<TransactionWorkflowSetup> transactionWorkflowSetups) {
		this.transactionWorkflowSetups = transactionWorkflowSetups;
	}

	public TransactionWorkflowSetup addTransactionWorkflowSetup(TransactionWorkflowSetup transactionWorkflowSetup) {
		getTransactionWorkflowSetups().add(transactionWorkflowSetup);
		transactionWorkflowSetup.setOrderStatus(this);

		return transactionWorkflowSetup;
	}

	public TransactionWorkflowSetup removeTransactionWorkflowSetup(TransactionWorkflowSetup transactionWorkflowSetup) {
		getTransactionWorkflowSetups().remove(transactionWorkflowSetup);
		transactionWorkflowSetup.setOrderStatus(null);

		return transactionWorkflowSetup;
	}

}