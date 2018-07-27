package com.jk.ptk.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.jk.ptk.app.AppProps;

/**
 * This class configures database connectivity.
 *
 * @author Jitendra
 *
 */
@Configuration
public class DbConfig {
	/**
	 * Returns data source to be used by this application.
	 *
	 * @return data source to be used by this application
	 * @throws IOException
	 *             if there is problem reading properties from file
	 */
	@Bean
	public DataSource dataSource() throws IOException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(AppProps.valueOf("db.driver.class.name"));
		dataSource.setUrl(AppProps.valueOf("db.url"));
		dataSource.setUsername(AppProps.valueOf("db.username"));
		dataSource.setPassword(AppProps.valueOf("db.password"));
		
		System.out.println(AppProps.valueOf("db.driver.class.name ______"));

		return dataSource;
	}
}
