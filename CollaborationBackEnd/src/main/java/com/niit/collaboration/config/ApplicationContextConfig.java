package com.niit.collaboration.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@ComponentScan("com.niit")
@EnableTransactionManagement
public class ApplicationContextConfig {

	@Bean(name = "dataSource")
	public DataSource getH2DataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//dataSource.setUrl("jdbc:h2:tcp://localhost/~/shoppingcartdb");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
        //jdbc:oracle:thin:@localhost:1521:XE
		//dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		//oracle.jdbc.driver.OracleDriver

		dataSource.setUsername("Blog"); //schema name
		dataSource.setPassword("Noorie@9695");
		
		
		return dataSource;
	}

	
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		//org.hibernate.dialect.Oracle10gDialect)
		//properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.enable_lazy_load_no_trans", "true");
		return properties;
	}

	@Autowired
	@Bean(name = "sessioncFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addProperties(getHibernateProperties());
		
		sessionBuilder.scanPackages("com.niit");
	
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

	

}