package com.payment.ee.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.payment.ee.controller.ExtractorController;
import com.payment.ee.controller.InquiryController;
import com.payment.ee.controller.PaymentController;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.payment.ee")
@PropertySource("classpath:persistence-mysql.properties")
public class PaymentEEConfig {

	// set up variable to hold properties
	@Autowired
	private Environment env;
	
	@Bean
	public ExtractorController extractorController() {
		return new ExtractorController();
	}
	
	@Bean
	public PaymentController paymentController() {
		return new PaymentController();
	}
	
	
	@Bean
	public InquiryController inquiryController() {
		return new InquiryController();
	}

	@Bean
	public DataSource createDataSource() {

		// create connection pool
		ComboPooledDataSource dataSource = new ComboPooledDataSource();

		// set the jdbc driver
		try {
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}

		// set database connection props
		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSource.setUser(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));

		// set connection pool props
		dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		

		return dataSource;
	}

	// read environment property and convert to int
	private int getIntProperty(String propName) {
		String propVal = env.getProperty(propName);
		int intPropVal = Integer.parseInt(propVal);

		return intPropVal;
	}

	private Properties getHibernateProperties() {
		// set hibernate properties
		Properties props = new Properties();

		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

		return props;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		// create session factorys
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		// set the properties
		sessionFactory.setDataSource(createDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hiberante.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());

		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}
	
	@Bean
	@Scope("prototype")
	public Logger logger(InjectionPoint injectionPoint) {
		// provide Logger (log4j)
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass());
	}

}
