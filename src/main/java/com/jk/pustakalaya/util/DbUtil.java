package com.jk.pustakalaya.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
	private static String dbUrl;
	private static String dbUsername;
	private static String dbPassword;
	private static String driverClass;
	
	static {
		try {
			init();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void init() throws IOException, ClassNotFoundException, SQLException {
		InputStream in = new FileInputStream(App.CONFIG_PATH + File.separator + "app.properties");
		Properties prop = new Properties();
		prop.load(in);
		
		dbUrl = prop.getProperty("db.url");
		driverClass = prop.getProperty("db.driver_class");
		dbUsername = prop.getProperty("db.username");
		dbPassword = prop.getProperty("db.password");
		
		// load driver class
		Class.forName(driverClass);
		
		// test if connection to the database can be made
		Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		
		// close the connection
		con.close();
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	}
}
