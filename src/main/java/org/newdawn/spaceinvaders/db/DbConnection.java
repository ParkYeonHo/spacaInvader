package org.newdawn.spaceinvaders.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		// Note: Change the connection parameters accordingly.
		String hostName = "localhost";
		String dbName = "spaceinvader";
		String userName = "root";
		String password = "root";
		return getMySQLConnection(hostName, dbName, userName, password);
	}

	public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
			throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");

		// URL Connection for MySQL:
		// Example:
		// jdbc:mysql://localhost:3306/simpler
		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";


		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
}
