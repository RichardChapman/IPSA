package dbquery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class searchQuery {
	
	/*
	 * Variables for connecting to the server.
	 */
	final static String username = "cis499";
	final static String password = "password";
	final static String driverClass = "com.mysql.jdbc.Driver";
	final static String url = "jdbc:mysql://scraggley.cis.umassd.edu/" + username;
	
	/*
	 * Empty constructor
	 */
	public searchQuery(){
		
	}
	
	/*
	 * Access methods directly
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
	}
	
	/*
	 * takes a query and returns a dataset from the columns
	 * specified.
	 */
	public ResultSet getDataset(String query){
		Connection connection = null;
		/*
		 * Make sure we dont ruin the server with a try catch block
		 */
		try {
		// Open a DB Connection
		connection = DriverManager.getConnection(url, username, password);

		// Create a Statement
		Statement stmnt = connection.createStatement();

		// Execute a query
		ResultSet results = stmnt.executeQuery(query);
		
		//return result set
		return results;
		} catch (Exception ex) {
			System.out.println(query);
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {
			}
		}
		//if connection fails or if syntax is incorrect.
		return null;
	}
	
	/*
	 * Sends query to the server, returns the amount of rows 
	 * affected by query.
	 * Returns 0 if no rows were affected, or if the connection
	 *  or query fails.
	 */
	public int addOrDelete(String query){
		Connection connection = null;
		try {
		// Open a DB Connection
		connection = DriverManager.getConnection(url, username, password);

		// Create a Statement
		Statement stmnt = connection.createStatement();

		// Execute a query
		int results = stmnt.executeUpdate(query);
		
		//returns the amount of columns affected.
		return results;
		} catch (Exception ex) {
			System.out.println(query);
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {
			}
		}
		//if connection fails or if syntax is incorrect.
		return 0;
	}

}

