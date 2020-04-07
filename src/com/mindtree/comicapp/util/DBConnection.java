package com.mindtree.comicapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mindtree.comicapp.exception.dao.ComicAppDaoException;

public class DBConnection {

	private static final String DBURL = "jdbc:mysql://localhost:3306/comicdatabase";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Welcome123";

	public  Connection getConnection() throws ComicAppDaoException {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);

		} catch (ClassNotFoundException e) {
			throw new ComicAppDaoException(e.getMessage(), e);

		} catch (SQLException e) {
			throw new ComicAppDaoException("Error in Connection", e);
		}

		return connection;

	}

	public Connection closeConnection(Connection connection) throws ComicAppDaoException {

		try {
			connection.close();
		} catch (SQLException e) {
			throw new ComicAppDaoException("Error in Connection", e);
		}

		return connection;

	}
}
