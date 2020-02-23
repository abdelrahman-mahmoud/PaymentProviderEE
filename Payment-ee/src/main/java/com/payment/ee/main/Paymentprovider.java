package com.payment.ee.main;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.payment.ee.config.PaymentEEConfig;
import com.payment.ee.controller.ExtractorController;

/**
 * Hello world!
 *
 */
public class Paymentprovider {
	
	public static void main(String[] args) {
		// entry point
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PaymentEEConfig.class);
		ExtractorController extractorController = context.getBean("extractorController", ExtractorController.class);
		Logger logger = context.getBean("logger", Logger.class);
		try {
			extractorController.perform(args);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}

		context.close();
	}

}
