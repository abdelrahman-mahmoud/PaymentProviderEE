package com.payment.ee;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.payment.ee.constants.AppExceptionMessages;
import com.payment.ee.controller.ExtractorController;
import com.payment.ee.dao.CurrencyDAO;
import com.payment.ee.dao.OrderPaymentDAO;
import com.payment.ee.dao.OrderStatusDAO;
import com.payment.ee.dao.PaymentMethodDAO;
import com.payment.ee.dao.StatusDAO;
import com.payment.ee.dao.TransactionTypeDAO;
import com.payment.ee.entitiy.Currency;
import com.payment.ee.entitiy.OrderStatus;
import com.payment.ee.entitiy.PaymentMethod;
import com.payment.ee.entitiy.TransactionType;
import com.payment.ee.pojo.PaymentTransaction;
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
			Assert.assertEquals(e.getMessage(), AppExceptionMessages.INVALID_ARGUMENTS);
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
