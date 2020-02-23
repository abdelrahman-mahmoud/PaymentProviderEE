package com.payment.ee;

import java.util.Random;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mchange.util.AssertException;
import com.payment.ee.constants.AppConstants;
import com.payment.ee.controller.ExtractorController;
import com.payment.ee.controller.PaymentController;
import com.payment.ee.dao.CurrencyDAO;
import com.payment.ee.dao.OrderPaymentDAO;
import com.payment.ee.dao.OrderStatusDAO;
import com.payment.ee.dao.PaymentMethodDAO;
import com.payment.ee.dao.StatusDAO;
import com.payment.ee.dao.TransactionTypeDAO;
import com.payment.ee.daoimpl.CurrencyDAOImpl;
import com.payment.ee.entitiy.Currency;
import com.payment.ee.entitiy.OrderPayment;
import com.payment.ee.entitiy.OrderStatus;
import com.payment.ee.entitiy.PaymentMethod;
import com.payment.ee.entitiy.TransactionType;
import com.payment.ee.exceptions.CurrencyNotSupportedException;
import com.payment.ee.pojo.OutputMessage;
import com.payment.ee.pojo.PaymentTransaction;
import com.payment.ee.service.OrderPaymentService;
import com.payment.ee.serviceimpl.OrderPaymentServiceImpl;

public class OrderPaymentTest {
	
	
	/*
	 * @InjectMocks PaymentController paymentController;
	 */
	
	 @InjectMocks  OrderPaymentServiceImpl orderPaymentService;
	  
	 @Mock CurrencyDAO currencyDAO;
	 @Mock PaymentMethodDAO paymentMethodDAO;
	 @Mock TransactionTypeDAO transactionTypeDAO;
	 @Mock OrderStatusDAO orderStatusDAO;
	 @Mock StatusDAO statusDAO;
	 @Mock OrderPaymentDAO orderPaymentDAO;
	 
	
	/*
	 * @Spy Logger logger = Logger.getLogger(OrderPaymentService.class);
	 */
	
	/*
	 * @Mock OrderPaymentDAO orderpaymentDAO;
	 */
//	
	@Spy
	PaymentTransaction paymentTransaction;
	
	@InjectMocks 
	ExtractorController extractorController;
	
	@BeforeClass
	public void init() {
	    MockitoAnnotations.initMocks(this);
	    
	    paymentTransaction.setAmount(111);
		paymentTransaction.setClientId("C-ID-4");
		paymentTransaction.setCurrency("USD");
		paymentTransaction.setOrderId("O-ID-75327539");    
		paymentTransaction.setPaymentMethod("Card");
		paymentTransaction.setToken("token-1");
		paymentTransaction.setTransactionType("REG");

	}

	@Test
	public void missingArguments()  {
		
		try {
		 extractorController.perform(null);
		}
		catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Please specify a command. Available commands: register, authorise, capture, reverse, findByOrder, findPending, findTotal");
		}

	}
	
	@Test
	public void successRegisterPayment() {
		Currency c = new Currency();
		c.setId(1);
		c.setCurrencyCode("USD");
		
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setId(1);
		paymentMethod.setPaymentMethodName("Card");
		
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(1);
		orderStatus.setOrderStatusCode("REG");
		orderStatus.setOrderStatusName("ORDER-REGISTERED");
		
		TransactionType transactionType = new TransactionType();
		transactionType.setId(1);
		transactionType.setTranTypeCode("REG");
		transactionType.setTranTypeDesc("register");
		
		
		try {
			when(orderPaymentService.retrieveCurrency("USD")).thenReturn(c);
			when(orderPaymentService.retrievePaymentMethod("Card")).thenReturn(paymentMethod);
			when(orderPaymentService.retrieveOrderStatus("REG")).thenReturn(orderStatus);
			when(orderPaymentService.retrieveTransactionType("REG")).thenReturn(transactionType);
			
			Assert.assertEquals(orderPaymentService.registerTransaction(paymentTransaction),true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
