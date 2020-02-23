package com.payment.ee.dao;

import com.payment.ee.entitiy.TransactionType;

public interface TransactionTypeDAO {
	TransactionType getTransactionTypeByTranTypeCode(String tranTypeCode);
}
