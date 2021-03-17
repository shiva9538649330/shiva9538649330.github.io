package com.spingboot.webConfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.springboot.*")
@EnableWebMvc
@EnableTransactionManagement
public class ApplicationContextConfig {

	@Bean

	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		// dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		// dataSource.setUrl("jdbc:mysql://localhost:3306/xe");
		// dataSource.setUsername("system");
		// dataSource.setPassword("tiger");

		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("system");
		dataSource.setPassword("tiger");

		/* Use this configuration while submitting solution in hobbes. */
		// dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		// dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" +
		// System.getenv("MYSQL_DATABASE")
		// + "?verifyServerCertificate=false&useSSL=false&requireSSL=false");
		// dataSource.setUsername(System.getenv("MYSQL_USER"));
		// dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));

		return dataSource;
	}

	@Bean

	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan("com.springboot.model");

		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put("hibernate.hbm2ddl.auto", "update");

		sessionFactory.setHibernateProperties(properties);

		return sessionFactory;
	}

	@Bean

	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

}
