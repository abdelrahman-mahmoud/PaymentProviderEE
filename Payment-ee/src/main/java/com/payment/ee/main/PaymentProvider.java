package com.payment.ee.main;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.payment.ee.config.PaymentEEConfig;
import com.payment.ee.controller.ExtractorController;

/**
 * Hello world!
 *
 */
public class PaymentProvider {
	private static final Logger logger = Logger.getLogger(PaymentProvider.class);
	public static void main(String[] args) {
		// entry point
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PaymentEEConfig.class);
		ExtractorController extractorController = context.getBean("extractorController", ExtractorController.class);
		
		try {
			extractorController.perform(args);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}

		context.close();
	}

}
