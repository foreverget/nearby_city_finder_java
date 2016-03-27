//importing packages
import java.util.*;
import java.io.*;
import java.sql.*;


public class ParseCSV{

	//constructor
	ParseCSV(){

		try {

			//defining variables
			DBConfig db = new DBConfig();
			String line = "";
			int count = 0;

			//opening file
			FileReader fr = new FileReader("GeoLiteCity-Location.csv");
			BufferedReader br = new BufferedReader(fr);

			//reading file 
			while ((line = br.readLine()) != null) {

				//incrementing line count
				count++;

				//ignoring first 2 lines
				if(count>2){

					//getting data
					String[] tmpData = line.split(",");	

					//sql query
		            PreparedStatement statement = db.connection.prepareStatement("Insert into cityData (city, latitude, longitude) Values (?,?,?)");
		            
		            //query value
		            statement.setString(1, tmpData[3]);
		            statement.setDouble(2, Double.parseDouble(tmpData[5]));
		            statement.setDouble(3, Double.parseDouble(tmpData[6]));

		            //executing query
		            statement.executeUpdate();
		        }    
			}	


		} catch (Exception e){
			e.printStackTrace();
		} 

	}

}