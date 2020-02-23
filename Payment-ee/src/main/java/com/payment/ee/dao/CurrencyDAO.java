package com.payment.ee.dao;

import com.payment.ee.entitiy.Currency;

public interface CurrencyDAO {
	Currency getCurrencyByCurrencyCode(String currencyCode);

}
