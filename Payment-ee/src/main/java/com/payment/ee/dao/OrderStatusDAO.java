package com.payment.ee.dao;

import com.payment.ee.entitiy.OrderStatus;

public interface OrderStatusDAO {
	OrderStatus getOrderStatusByStatuseCode(String statusCode);
}
