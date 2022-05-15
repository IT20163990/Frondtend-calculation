package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Calculation {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cal_db", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	//read
	public String readCalculation()  
	{   
		String output = ""; 
	
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the HTML table to be displayed    
			output = "<table class='table' border='1'><thead class='table-dark'>"
					+ "<th>Mounth</th>"
					+ "<th>Units</th>"
					+ "<th>Unit Cost</th>"
					+ "<th>Total</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></thead>";
	 
			String query = "select * from calculation"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				String billId = Integer.toString(rs.getInt("billId")); 
				String month = rs.getString("month");
				String units = rs.getString("units");
				String unit_cost = Double.toString(rs.getDouble("unit_cost"));
				String total = Double.toString(rs.getDouble("total"));

				// Add into the HTML table 
				output += "<tr><td><input id='hidCalculationIDUpdate' "
						+ "name='hidCalculationIDUpdate' "
						+ "type='hidden' value='" + billId + "'>" 
						+ month + "</td>"; 
				output += "<td>" + units + "</td>";
				output += "<td>" + unit_cost + "</td>";
				output += "<td>" + total + "</td>";

				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-billid='" + billId + "'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='" + billId + "'>" + "</td></tr>"; 
		
			}
			con.close(); 
	 
			// Complete the HTML table    
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the Calculation.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
	
	//Add details about the calculatio
		public String insertCalculation(String month, String units, String unit_cost, String total)  
		{   
			String output = ""; 
		 
			try
			{    
				Connection con = connect(); 
		 
				if (con == null)    
				{
					return "Error while connecting to the database for inserting.";
				} 
		 
				// create a prepared statement 
				String query = " insert into calculation (billId , month , units , unit_cost , total)" + " values (?, ?, ?, ?, ?)"; 
		 
		 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
				// binding values    
				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(2, month);
				 preparedStmt.setString(3, units);
				 preparedStmt.setDouble(4, Double.parseDouble(unit_cost));
				 preparedStmt.setDouble(5, Double.parseDouble(total));
				
				// execute the statement    
				preparedStmt.execute();    
				con.close(); 
		   
				String newCalculation = readCalculation(); 
				output =  "{\"status\":\"success\", \"data\": \"" + newCalculation + "\"}";    
			}   
			catch (Exception e)   
			{    
				output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Calculation.\"}";  
				System.err.println(e.getMessage());   
			} 
			
		  return output;  
		}
		
		
		//update
		public String updateCalculation(String billId, String month, String units, String unit_cost, String total)    
		{   
			String output = ""; 
		 
			try   
			{    
				Connection con = connect(); 
		 
				if (con == null)    
				{
					return "Error while connecting to the database for updating.";
				} 
		 
				// create a prepared statement    
				String query = "UPDATE calculation SET month=?,units=?,unit_cost=?,unit_cost=? WHERE billId=?"; 
		 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
				// binding values    
				preparedStmt.setString(1, month);
				preparedStmt.setString(2, units);
				preparedStmt.setDouble(3, Double.parseDouble(unit_cost));
				preparedStmt.setDouble(4, Double.parseDouble(total));
				preparedStmt.setInt(5, Integer.parseInt(billId)); 
		 
				// execute the statement    
				preparedStmt.execute();    
				con.close(); 
		 
				String newCalculation = readCalculation();    
				output = "{\"status\":\"success\", \"data\": \"" + newCalculation + "\"}";    
			}   
			catch (Exception e)   
			{    
				output =  "{\"status\":\"error\", \"data\": \"Error while updating the Calculation.\"}";   
				System.err.println(e.getMessage());   
			} 
		 
		  return output;  
		} 
		
		//delete
		public String deleteCalculation(String billId)   
		{   
			String output = ""; 
		 
			try   
			{    
				Connection con = connect(); 
		 
				if (con == null)    
				{
					return "Error while connecting to the database for deleting."; 
				} 
		 
				// create a prepared statement    
				String query = "delete from calculation where billId=?";  
		 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
				// binding values    
				preparedStmt.setInt(1, Integer.parseInt(billId)); 
		 
				// execute the statement    
				preparedStmt.execute();    
				con.close(); 
		 
				String newCalculation = readCalculation();  
				    
				output = "{\"status\":\"success\", \"data\": \"" +  newCalculation + "\"}";    
			}   
			catch (Exception e)   
			{    
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the calculation.\"}";    
				System.err.println(e.getMessage());   
			} 
		 
			return output;  
		}

}
