//importing packages
import java.util.*;
import java.io.*;
import java.sql.*;


public class CityDB{

	//checkingDB (used for tests)
	public boolean checkDB(String usr, String pwd){

		DBConfig db = new DBConfig(usr,pwd);
		if(db.connection==null)
			return false;
		else
			return true;

	}

	//main method
	public static void main(String[] argv){

		//comment out if DB is already populated
		//ParseCSV psv = new ParseCSV();

		//variables
		int operation = 0;
		String cityname = null;
		Double latInput = 0.0;
		Double lonInput = 0.0;
		int cities = 0;
		String user = "";
		String password = "";

		//DB Connection
		DBConfig db = new DBConfig(user,password);

		//Scanner object
		Scanner input = new Scanner(System.in);

		//input
		System.out.println("Select your operation:\n1.Search by City Name\n2.Search by Lat/Long Value");
		operation = input.nextInt();

		//search by city name
		if(operation==1){

			try{
			
				//city name
				System.out.println("Enter city name (e.g Ondo):");
				cityname = input.next();

				//searching DB
				String query = "select * from cityData where city = ? ";
				PreparedStatement statement = db.connection.prepareStatement(query);
				statement.setString(1, '"'+cityname+'"');
				ResultSet rs = statement.executeQuery();

				if (!rs.next()){
					System.out.println("Nothing Found for the entered city!");
				}

				//printing
				while (rs.next()){

				  	Double lat = rs.getDouble(3);
				  	Double lon = rs.getDouble(4);

				  	System.out.println("Latitude: "+lat);
				  	System.out.println("Longitude:"+lon);
				}
			}
			catch(Exception sqle){

				sqle.printStackTrace();
				System.out.println("Error in executing SQL query!");

			}
		}

		//searchnearby cities
		else if(operation==2){

			//getting input form user
			System.out.println("Enter lat value:");
			latInput = input.nextDouble();
			System.out.println("Enter long value:");
			lonInput = input.nextDouble();
			System.out.println("Enter number of nearby cities:");
			cities = input.nextInt();

			try{
				
				String query = "SELECT * FROM (SELECT z.locid,z.city,z.latitude, z.longitude,p.radius,p.distance_unit* DEGREES(ACOS(COS(RADIANS(p.latpoint)) * COS(RADIANS(z.latitude)) * COS(RADIANS(p.longpoint - z.longitude)) + SIN(RADIANS(p.latpoint)) * SIN(RADIANS(z.latitude)))) AS distance FROM cityData AS z JOIN (SELECT  "+latInput+"  AS latpoint,  "+lonInput+" AS longpoint,5000.0 AS radius,      111.045 AS distance_unit) AS p ON 1=1 WHERE z.latitude BETWEEN p.latpoint  - (p.radius / p.distance_unit) AND p.latpoint  + (p.radius / p.distance_unit) AND z.longitude BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))) AS d WHERE distance <= radius ORDER BY distance LIMIT "+cities;
				PreparedStatement statement = db.connection.prepareStatement(query);
				ResultSet rs = statement.executeQuery();

				//printing results
				while (rs.next()){
					
					String cName = rs.getString(2);
					Double lat = rs.getDouble(3);
					Double lon = rs.getDouble(4);
					System.out.println("City Name: "+cName+"\tLatitude: "+lat+ "\tLongitude: "+lon);
				}
			}
			catch(SQLException se){
				se.printStackTrace();
				System.out.println("Error executing mysql query\n");
			}	
		}

		//invalid operation
		else{
			System.out.println("Invalid Operation!\n");
		}

	}

}