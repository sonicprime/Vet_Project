/*
 * Copyright (C) 2016
 * 
 * 
 * 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.wright.cs.sp16.ceg3120;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author rhys
 * 
 *         MySqlConnect is a class to run basic connection to MySQL test
 *         database. This class is capable of receiving a database address, user
 *         name, password, and database name of a MySQL server. Currently, it
 *         will iterate over the designated database and return its table
 *         values.
 */

public class MySqlConnect {

	private String dbAddress;
	private String dbUsername;
	private String dbPassword;
	private String dbName;
	private com.mysql.jdbc.jdbc2.optional.MysqlDataSource dataSource = 
			new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
	private Connection conn = null;

	/**
	 * MySqlConnect is just a place holder constructor.
	 */
	public MySqlConnect() {

	}

	/**
	 * MySqlConnect is the default constructor that the MySqlConnect class has.
	 * After this is done, call the configure method to set up the database
	 * connection.
	 * 
	 * 
	 * @param dbAddress
	 *            database address without port (uses default MySQL port = 3306)
	 *            Capitalization does not matter, IP address or domain names are
	 *            accepted.
	 * @param dbUsername
	 *            Database user name: not case sensitive, needs to be created in
	 *            the database before use here.
	 * @param dbPassword
	 *            Database password: is case sensitive, will be verified against
	 *            the database.
	 * @param dbName
	 *            Database name: Not case sensitive, will not connect if the
	 *            database name does not exist.
	 * 
	 */
	public MySqlConnect(String dbAddress, String dbUsername, String dbPassword, String dbName) {
		setDbAddress(dbAddress);
		setDbUsername(dbUsername);
		setDbPassword(dbPassword);
		setDbName(dbName);
	}

	/**
	 * This constructor sets up the database connection.
	 * 
	 * @throws SQLException
	 *             when a SQL connection can't be made.
	 */
	public void configure() throws SQLException {
		dataSource.setUser(getDbUsername());
		dataSource.setPassword(getDbPassword());
		dataSource.setServerName(getDbAddress());
		dataSource.setDatabaseName(getDbName());
		conn = dataSource.getConnection();
		try {
			java.sql.Statement stmt = conn.createStatement();
			try {

				ResultSet rs = stmt.executeQuery("SELECT * FROM inventory");

				System.out.println("If you see this you connected!");

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (rs.next()) {
					for (int i = 1; i <= columnsNumber; i++) {
						if (i > 1) {
							System.out.print(",  ");
							String columnValue = rs.getString(i);
							System.out.print(columnValue + " " + rsmd.getColumnName(i));
						}
					}
					System.out.println("");
				}

				rs.close();
				stmt.close();
				conn.close();

			} catch (SQLException SqlEx) {
				stmt.close();
				conn.close();
				System.out.println("If you see this, you failed to connect!");
				System.out.println(SqlEx.getMessage());

			} finally {
				stmt.close();
				conn.close();
			}
			stmt.close();
			conn.close();

		} finally {
			conn.close();
		}
	}

	/**
	 * executeQuery. definition.
	 * 
	 * @return string
	 * @throws SQLException
	 *             fd
	 */

//	public String executeQuery(String stringQuery) throws SQLException {
//		try {
//			conn = dataSource.getConnection();
//			java.sql.PreparedStatement preparedStatement = null;
//			ResultSet rs = null;
//			try {
//				try {
//					preparedStatement = conn.prepareStatement(stringQuery);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} finally {
//					conn.close();
//				}
//				try {
//					rs = preparedStatement.executeQuery();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} finally {
//					// rs.close();
//					preparedStatement.close();
//				}
//				try {
//					ResultSetMetaData rsmd = rs.getMetaData();
//					int columnsNumber = rsmd.getColumnCount();
//					while (rs.next()) {
//						for (int i = 1; i <= columnsNumber; i++) {
//							if (i > 1) {
//								System.out.print(",  ");
//								String columnValue = rs.getString(i);
//								System.out.print(columnValue + " " + rsmd.getColumnName(i));
//							}
//						}
//						System.out.println("");
//					}
//					rs.close();
//					preparedStatement.close();
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} finally {
//					rs.close();
//					preparedStatement.close();
//				}
//			} catch (SQLException e) {
//				rs = null;
//				preparedStatement = null;
//				conn.close();
//				System.out.println("ERROR!!!");
//				e.printStackTrace();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			conn.close();
//		}
//
//		String string = null;
//		return string;
//	}

	/**
	 * Constructor returns the database address as data type String. Null if no
	 * address entered already.
	 * 
	 * @return string
	 */
	public String getDbAddress() {
		return dbAddress;
	}

	/**
	 * Constructor receives a string that is an address of a MySQL database
	 * without the port. Default port of 3306 is used in the underlying code.
	 * 
	 * @param dbAddress
	 *            database address
	 */
	public void setDbAddress(String dbAddress) {
		this.dbAddress = dbAddress;
	}

	/**
	 * Constructor returns the set user name for the database as type String.
	 * Null if no user name entered already.
	 * 
	 * @return string
	 */
	public String getDbUsername() {
		return dbUsername;
	}

	/**
	 * Constructor sets the user name for the database. Receives a String value,
	 * is not case sensitive.
	 * 
	 * @param dbUsername
	 *            database username
	 */
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	/**
	 * Constructor returns the set password as a String. If there is no password
	 * set, the constructor returns NULL.
	 * 
	 * @return string
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**
	 * Sets the database password as a String value. It is case sensitive, and
	 * is verified against the database.
	 * 
	 * @param dbPassword
	 *            database password
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	/**
	 * Constructor returns a database name, if there is no name set it returns
	 * NULL.
	 * 
	 * @return string
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * Constructor sets the database name. Receives a String variable and is not
	 * case sensitive.
	 * 
	 * @param dbName
	 *            database name
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
