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
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBconnection class creates a connection thru an embedded derby driver. 
 * @author AlisonGuyton.
 *
 */
public class DBconnection {
	Connection conn = null;
	String url;
	//String username;
	//String password;

	/**
	 * DBconnection will establish a connection.
	 */
	public DBconnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		//path may vary per user because it is an Embedded Driver
		url = "jdbc:derby:./DATABASE;"
				+ "create=true";

		System.out.println("Created SQL Connect");
	}

	/**
	 * CreateConnection creates a connection.
	 */
	public void createConnection() {
		try {
			conn = DriverManager.getConnection(url);
			System.out.println("Successfully Connected");
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * closeConnection closes the established connection to a derby database.
	 */
	public void closeConnection() {
		try {
			this.conn.close();	
			System.out.println("Connection successfully closed");
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
}