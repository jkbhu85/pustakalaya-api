package com.jk.pustakalaya.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DbConfig {
	private static final Logger log = LoggerFactory.getLogger(DbConfig.class);

	@Value("${db.props.file}")
	private String dbPropsFileName;

	@Bean
	public DataSource dataSource() throws IOException {
		String dbPropsFilePath = System.getenv(App.NAME_ENV_VAR_CONFIG) + File.separator + dbPropsFileName;

		log.info("Database properties file path: {}", dbPropsFilePath);

		Properties dbProps = new Properties();
		dbProps.load(new FileInputStream(dbPropsFilePath));

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dbProps.getProperty("db.driver_class_name"));
		dataSource.setUrl(dbProps.getProperty("db.url"));
		dataSource.setUsername(dbProps.getProperty("db.username"));
		dataSource.setPassword(dbProps.getProperty("db.password"));

		return dataSource;
	}
}
