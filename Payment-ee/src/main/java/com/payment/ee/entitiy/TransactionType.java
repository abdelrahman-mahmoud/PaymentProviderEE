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
 * The persistent class for the transaction_type database table.
 * 
 */
@Entity
@Table(name="transaction_type")
@NamedQueries({
@NamedQuery(name = "TransactionType.findAll", query = "SELECT t FROM TransactionType t"),
@NamedQuery(name = "TransactionType.findTransactionTypeByTranTypeCode", query = "FROM TransactionType t where t.tranTypeCode = :tranTypeCode")})
public class TransactionType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="tran_type_code")
	private String tranTypeCode;

	@Column(name="tran_type_desc")
	private String tranTypeDesc;

	public TransactionType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTranTypeCode() {
		return this.tranTypeCode;
	}

	public void setTranTypeCode(String tranTypeCode) {
		this.tranTypeCode = tranTypeCode;
	}

	public String getTranTypeDesc() {
		return this.tranTypeDesc;
	}

	public void setTranTypeDesc(String tranTypeDesc) {
		this.tranTypeDesc = tranTypeDesc;
	}

}