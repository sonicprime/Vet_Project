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

package edu.wright.cs.sp16.ceg3120.sql;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * This will create a derby database to connect to within the application.
 * 
 * @author Bonnie
 *
 */
public class DerbyConnect  {

	private String dbName;
	private String dbUrl = "jdbc:derby://localhost:1527/testDB;create=true;"
			+ "user=admin;password=";
	private Connection conn = null;

	/**
	 * Default constructor.
	 */
	public DerbyConnect() {

	}

	/**
	 * this constructor is used to set the database address, username, password
	 * and the database name.
	 * @param dbName
	 *            - sets the database name.
	 */
	public DerbyConnect(String dbName) {
		setDbName(dbName);
	}

	/**
	 * This will create the connection for the derby database.
	 */
	public void configure() throws SQLException {
		// Still working on creating the connection.
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			conn = DriverManager.getConnection(dbUrl);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Sets the database name for the derby db.
	 * 
	 * @param dbName
	 *            - name of the database.
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * returns the database name used for the derby connection.
	 * 
	 * @return - returns a string of database name.
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * the method is to run derby commands in derby drivers and return the results of any command
	 * 
	 * @return String variable containing the results of the query executed.
	 * @throws Exception
	 *  */
	@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(value = 
			"SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE", justification = 
			"We specifically want to allow the user to execute arbitrary Derby")
	public String[][] executeQuery(String query) {
		String [][] table = null;
		JTable t1 = new JTable();
		DefaultTableModel dt = new DefaultTableModel();
		try (
				Statement input = conn.createStatement();
				ResultSet rs = input.executeQuery(query)) {
			// ResulSetMetaData does not implement AutoClosable() so it
			// cannot be handled by try-with-resources.
			ResultSetMetaData rsmd = null;
			// Try to read the result set and its meta data and print out to
			// string.
			rsmd = rs.getMetaData();
			rs.last();
			int row = rs.getRow();
			rs.beforeFirst();
			int columnsNumber = rsmd.getColumnCount();
			//table = new String[row][columnsNumber];
			int rowCounter = 0;
			// Iterate through all data returned and append to string
			// result.
			dt.setColumnCount(columnsNumber);
			dt.setRowCount(row);
			while (rs.next()) {
				for (int i = 0; i < columnsNumber; i++) {
					if (i > 1) {
						String columnValue = rs.getString(i + 1);
						//table[rowCounter][i] = columnValue;
						//possible fix
						dt.setValueAt(columnValue,rowCounter,i);
					}
				}
				System.out.println("");
				rowCounter++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.setModel(dt);
		return table;
	}

}