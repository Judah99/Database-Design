import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class sample {
	
    // Database credentials
    final static String HOSTNAME = "odia0000-sql-server.database.windows.net";
    final static String DBNAME = "cs-dsa-4513-sql-db";
    final static String USERNAME = "odia0000";
    final static String PASSWORD = "awesome60.123";

    // Database connection string
    final static String URL = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
            HOSTNAME, DBNAME, USERNAME, PASSWORD);


    // Query templates
    final static String QUERY_TEMPLATE_1 = "EXEC QUERY1 @team_Name=?, @team_type = ?, @date_formed = ?;";
    final static String QUERY_TEMPLATE_2 = "EXEC QUERY2 @SSN=?, @pname = ?, @birth_date = ?, @race=?, @gender = ?, @profession = ?, @mailing_list = ?, @mailing_address=?, @email=?, @phone_Number=?, @doctor_phone=?, @doctor_name=?, @attorney_name=?, @attorney_phone=?, @date_assigned=?, @team_Name=?, @activity=?;";
    final static String QUERY_TEMPLATE_3 = "EXEC QUERY3 @SSN=?, @pname = ?, @birth_date = ?, @race=?, @gender = ?, @profession = ?, @mailing_list = ?, @mailing_address=?, @email=?, @phone_Number=?, @date_joined=?, @date_recent_training=?, @location_recent_training=?, @team_Name=?, @serve_hours=?, @activity=?;";
    final static String QUERY_TEMPLATE_4 = "EXEC QUERY4 @SSN=?, @SERVE_HOURS = ?;";
    final static String QUERY_TEMPLATE_5 = "EXEC QUERY5 @SSN=?, @pname = ?, @birth_date = ?, @race=?, @gender = ?, @profession = ?, @mailing_list = ?, @mailing_address=?, @email=?, @phone_Number=?, @salary=?, @marital_status=?, @hire_date=?, @team_Name=?, @report_date=?, @content_description=?;";
    final static String QUERY_TEMPLATE_6 = "EXEC QUERY6 @amount=?, @expenses_date = ?, @expenses_description = ?, @SSN=?;";
    final static String QUERY_TEMPLATE_7 = "EXEC QUERY7 @org_Name=?, @mailing_address = ?, @phone_Number = ?, @contact_person=?, @team_Name=?; ";
    final static String QUERY_TEMPLATE_8 = "EXEC QUERY8 @SSN=?, @pname = ?, @birth_date = ?, @race=?, @gender = ?, @profession = ?, @mailing_list = ?, @mailing_address=?, @email=?, @phone_Number=?, @donor_date=?, @amount=?, @donor_type=?, @campaign_name=?, @anonymity=?, @card_no=?, @card_type=?, @expiration_date=?, @cheque_no=?;";
    final static String QUERY_TEMPLATE_9 = "EXEC QUERY9 @ORG_NAME=?, @MAILING_ADDRESS = ?, @PHONE_NUMBER = ?, @CONTACT_PERSON=?, @DONATION_DATE = ?, @AMOUNT = ?, @DONATION_TYPE = ?, @CAMPAIGN_NAME=?, @ANONYMITY=?, @card_no=?, @card_type=?, @expiration_date=?, @cheque_no=?;";
    final static String QUERY_TEMPLATE_10 = "EXEC QUERY10 @SSN = ?;";
    
    final static String QUERY_TEMPLATE_13 = "EXEC QUERY13;";
    final static String QUERY_TEMPLATE_14 = "EXEC QUERY14;";
    final static String QUERY_TEMPLATE_15 = "EXEC QUERY15;";
    final static String QUERY_TEMPLATE_16 = "EXEC QUERY16; ";
    final static String QUERY_TEMPLATE_17 = "EXEC QUERY17; ";
    final static String QUERY_TEMPLATE_18 = "EXEC QUERY18 @team_Name=?, @team_type = ?, @date_formed=?;";
    final static String QUERY_TEMPLATE_19 = "EXEC QUERY19; ";
    final static String QUERY_TEMPLATE_20 = "EXEC QUERY20; ";

    // User input prompt//
    final static String PROMPT = 
    		"1. Enter a new team into the database; \n"+
    		"2. Enter a new client into the database and associate him or her with one or more teams; \n"+
    		"3. Enter a new volunteer into the database and associate him or her with one or more teams; \n"+
    		"4. Enter the number of hours a volunteer worked this month for a particular team; \n"+
    		"5. Enter a new employee into the database and associate him or her with one or more teams \n"+
    		"6. Enter an expense charged by an employee \n"+
    		"7. Enter a new organization and associate it to one or more PAN teams \n"+
    		"8. Enter a new donor and associate him or her with several donations. \n"+
    		"9. Enter a new organization and associate it with several donations; \n"+
    		"10. Retrieve the name and phone number of the doctor of a particular client \n"+
    		"11. Retrieve the total amount of expenses charged by each employee for a particular period of time. The list should be sorted by the total amount of expenses \n" +
    		"12. Retrieve the list of volunteers that are members of teams that support a particular client  \n"+
    		"13. Retrieve the names and contact information of the clients that are supported by teams sponsored by an organization whose name starts with a letter between B and K. The client listshould be sorted by name \n"+
    		"14. Retrieve the name and total amount donated by donors that are also employees. The list should be sorted by the total amount of the donations, and indicate if each donor wishes to remain anonymous \n"+
    		"15. Retrieve the names of all teams that were founded after a particular date \n"+
    		"16. Increase the salary by 10% of all employees to whom more than one team must report. \n"+
    		"17. Delete all clients who do not have health insurance and whose value of importance for transportation is less than 5 \n"  +
            "18. Import. \n" +
    		"19. Export. \n" +
            "20. Quit";

    public static void main(String[] args) throws SQLException {

        System.out.println("Welcome to PAN Database (Project) application!");
        
        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input
        String option = ""; // Initialize user option selection as nothing
        while (!option.equals("20")) { // As user for options until option 3 is selected
            System.out.println(PROMPT); // Print the available options
            option = sc.next(); // Read in the user option selection
        
	        switch (option) { // Switch between different options
	        
	        case "1": // Insert new Team
	        	
	        	 // Collect the new team data from the user
	        	sc.nextLine();
               System.out.println("Please enter team name:");
               final String team_Name = sc.nextLine(); // Read in the team name

               System.out.println("Please enter team type:");
               final String team_type = sc.nextLine(); // Read in user input of team type (white-spaces allowed).

               System.out.println("Please enter team date_formed"); // User inputs the date of team formed
               // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
               final String date_formed = sc.nextLine(); // Read in user input of faculty department id.                
               //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                 
               System.out.println("Connecting to the database...");
               // Get a database connection and prepare a query statement
               try (final Connection connection = DriverManager.getConnection(URL)) {
                   try (
                       final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_1)) {
                       // Populate the query template with the data collected from the user
                       statement.setString(1, team_Name);
                       statement.setString(2, team_type);
                       statement.setString(3, date_formed);
                       
                       System.out.println("Dispatching the query...");
                       // Actually execute the populated query
                       final int rows_inserted = statement.executeUpdate();
                       System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                   }
               }
	        	 break;
	          
	       	       	        
	        case "2": // Insert new client
	        	
	        	 // Collect the new client data from the user
	        	sc.nextLine();
                System.out.println("Please enter SSN:");
                final String SSN = sc.nextLine(); // Read in the SSN of person

                System.out.println("Please enter Name:");
                // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input.
                // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.
                //sc.nextLine();
                final String pname = sc.nextLine(); // Read in user input of Person Name (white-spaces allowed).

                System.out.println("Please enter birth_date YYYY-MM-DD:"); // User inputs the date of birth
                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String birth_date = sc.nextLine(); // Read in user input of faculty department id.                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter race:"); // User inputs race of person
                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String race = sc.nextLine(); // Read in user input of faculty department id.                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter gender:"); // User inputs gender of person
                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String gender = sc.nextLine(); // Read in user input of faculty department id.                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter profession:"); // User inputs gender of person
                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String profession = sc.nextLine(); // Read in user input of faculty department id.                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter interest in mailing_list:"); // User inputs whether the person is interested in mailing list or not
                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String mailing_list = sc.nextLine(); // Read in user input of faculty department id.                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter mailing_address:"); // User inputs whether the person's mailing address
                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String mailing_address = sc.nextLine(); // Read in user input of faculty department id.                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter email:"); // User inputs the person's email address
                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String email = sc.nextLine(); // Read in user input of faculty department id.                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter phone_Number:"); //User inputs the person's phone number
                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String phone_Number = sc.nextLine(); // Read in user input                 
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter doctor_phone:"); // User inputs the clients doctor phone number                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String doctor_phone = sc.nextLine(); // Read in user input                 
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter doctor_name:"); // User inputs the clients doctor name                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String doctor_name = sc.nextLine(); // Read in user input                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter attorney_name:"); // User inputs the clients attorney name                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String attorney_name = sc.nextLine(); // Read in user input              
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter attorney_phone:"); // User inputs the clients attorney phone                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String attorney_phone = sc.nextLine(); // Read in user input                 
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter client date_assigned:"); // User inputs the clients attorney phone                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String date_assigned = sc.nextLine(); // Read in user input                 
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                
                System.out.println("Please enter client associated team name:"); // User inputs the clients attorney phone                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String team_Name1 = sc.nextLine(); // Read in user input                 
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
               
                System.out.println("Please enter activity where 1 signifies active and 0 non active:"); // User inputs the clients attorney phone                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                final String activity = sc.nextLine(); // Read in user input of faculty department id.                
                //sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                                
                System.out.println("Connecting to the database...");
                // Get a database connection and prepare a query statement
                try (final Connection connection = DriverManager.getConnection(URL)) {
                    try (
                        final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_2)) {
                        // Populate the query template with the data collected from the user
                        statement.setString(1, SSN);
                        statement.setString(2, pname);
                        statement.setString(3, birth_date);
                        statement.setString(4, race);
                        statement.setString(5, gender);
                        statement.setString(6, profession);
                        statement.setString(7, mailing_list);
                        statement.setString(8, mailing_address);
                        statement.setString(9, email);
                        statement.setString(10, phone_Number);
                        statement.setString(11, doctor_phone);
                        statement.setString(12, doctor_name);
                        statement.setString(13, attorney_name);
                        statement.setString(14, attorney_phone);
                        statement.setString(15, date_assigned);
                        statement.setString(16, team_Name1);
                        //statement.setString(17, Client_SSN);
                        statement.setString(17, activity);

                        System.out.println("Dispatching the query...");
                        // Actually execute the populated query
                        final int rows_inserted = statement.executeUpdate();
                        System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                    }
                }
	        	 break;
	        	 
	        case "3": // Insert new Volunteer and associate with team
	        	
	        	 // Collect the new volunteer data from the user
	        	sc.nextLine();
               System.out.println("Please enter SSN:");
               final String SSN2 = sc.nextLine(); // Read in the SSN of person

               System.out.println("Please enter Name:");
               final String pname2 = sc.nextLine(); // Read in user input of Person Name (white-spaces allowed).

               System.out.println("Please enter birth_date YYYY-MM-DD:"); // User inputs the date of birth
               final String birth_date2 = sc.nextLine(); // Read in user input of faculty department id.                
                              
               System.out.println("Please enter race:"); // User inputs race of person
               final String race2 = sc.nextLine(); // Read in user input of faculty department id.                
                             
               System.out.println("Please enter gender:"); // User inputs gender of person
               final String gender2 = sc.nextLine(); // Read in user input of faculty department id.                
                              
               System.out.println("Please enter profession:"); // User inputs gender of person
               final String profession2 = sc.nextLine(); // Read in user input of faculty department id.                
                              
               System.out.println("Please enter interest in mailing_list:"); // User inputs whether the person is interested in mailing list or not
               final String mailing_list2 = sc.nextLine(); // Read in user input of faculty department id.                
                              
               System.out.println("Please enter mailing_address:"); // User inputs whether the person's mailing address
               final String mailing_address2 = sc.nextLine(); // Read in user input of faculty department id.                
               
               System.out.println("Please enter email:"); // User inputs the person's email address
               final String email2 = sc.nextLine(); // Read in user input of faculty department id.                
                              
               System.out.println("Please enter phone_Number:"); //User inputs the person's phone number
               final String phone_Number2 = sc.nextLine(); // Read in user input of faculty department id.                
                              
               System.out.println("Please enter date_joined:"); // User inputs the date the volunteer joined the team                // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
               final String date_joined = sc.nextLine(); // Read in user input of faculty department id.                
                              
               System.out.println("Please enter date of recent training:"); // User inputs the date of recent training             // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
               final String date_recent_training = sc.nextLine(); // Read in user input of faculty department id.                
                              
               System.out.println("Please enter location of recent training:"); // User inputs the volunteers most recent location training          cere, because the preceding nextLine consumed the newline character.
               final String location_recent_training = sc.nextLine(); // Read in user input            
                    
               System.out.println("Please enter team name:"); // User inputs the team name the volunteer is attached to eding nextLine consumed the newline character.
               final String team_name2 = sc.nextLine(); // Read in user input               
               
               System.out.println("Please enter serve hours:"); // User inputs the volunteers hours of service              // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
               final int serve_hours = sc.nextInt(); // Read in user input of faculty department id.                
               sc.nextLine(); // Consuming the trailing new line character left after nextInt()
               
               System.out.println("Please enter volunteer activity where 1 = active and 0 non-active:"); // User inputs whether or not the volunteer is active in the assigned team           // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
               // 1 denotes the volunteer is active and 0 denotes inactivity
               final String activity2 = sc.nextLine(); // Read in user input of volunteer activity
                         
               System.out.println("Connecting to the database...");
               // Get a database connection and prepare a query statement
               try (final Connection connection = DriverManager.getConnection(URL)) {
                   try (
                       final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                       // Populate the query template with the data collected from the user
                       statement.setString(1, SSN2);
                       statement.setString(2, pname2);
                       statement.setString(3, birth_date2);
                       statement.setString(4, race2);
                       statement.setString(5, gender2);
                       statement.setString(6, profession2);
                       statement.setString(7, mailing_list2);
                       statement.setString(8, mailing_address2);
                       statement.setString(9, email2);
                       statement.setString(10, phone_Number2);
                       statement.setString(11, date_joined);
                       statement.setString(12, date_recent_training);
                       statement.setString(13, location_recent_training);
                       statement.setString(14, team_name2);
                       statement.setInt(15, serve_hours);
                       statement.setString(16, activity2);
                       //statement.setString(17, Member_SSN);
                       //statement.setString(18, Leader_SSN);

                       System.out.println("Dispatching the query...");
                       // Actually execute the populated query
                       final int rows_inserted = statement.executeUpdate();
                       System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                   }
               }
	        	 break;
	        	 
	        	case "4": // Enter the number of hours a volunteer worked this month for a particular team
                
                sc.nextLine();
                System.out.println("Please enter VOLUNTEER SSN:");
                final String SSN44 = sc.nextLine(); //
               
                System.out.println("Please enter NUMBER OF HOURS WORKED THIS MONTH");
                final int HOURS4 = sc.nextInt(); // 
                sc.nextLine();
                                                
                System.out.println("Connecting to the database...");
                // Get a database connection and prepare a query statement
                try (final Connection connection = DriverManager.getConnection(URL)) {
                    try (
                        final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_4)) {
                        // Populate the query template with the data collected from the user
                        statement.setString(1, SSN44);
                        statement.setInt(2, HOURS4);

                        System.out.println("Dispatching the query...");
                        // Actually execute the populated query
                        final int rows_inserted = statement.executeUpdate();
                        System.out.println(String.format("Done. %d rows updated.", rows_inserted));
                    }
                }
                 break;  	 
	     
	        case "5": // Insert new Employee and associate with one or more teams 
	        		        	 // Collect the new employee data from the user
	        	sc.nextLine();
              System.out.println("Please enter SSN:");
              final String SSN3 = sc.nextLine(); // Read in the SSN of person

              System.out.println("Please enter Name:");
              final String pname3 = sc.nextLine(); // Read in user input of Person Name (white-spaces allowed).

              System.out.println("Please enter birth_date YYYY-MM-DD:"); // User inputs the date of birth
              final String birth_date3 = sc.nextLine(); // Read in user input of faculty department id.                
                             
              System.out.println("Please enter race:"); // User inputs race of person
              final String race3 = sc.nextLine(); // Read in user input of faculty department id.                
                            
              System.out.println("Please enter gender:"); // User inputs gender of person
              final String gender3 = sc.nextLine(); // Read in user input of faculty department id.                
                             
              System.out.println("Please enter profession:"); // User inputs gender of person
              final String profession3 = sc.nextLine(); // Read in user input of faculty department id.                
                             
              System.out.println("Please enter interest in mailing_list:"); // User inputs whether the person is interested in mailing list or not
              //1 signifies interest, 0 signifies disinterest
              final String mailing_list3 = sc.nextLine(); // Read in user input of faculty department id.                
                             
              System.out.println("Please enter mailing_address:"); // User inputs whether the person's mailing address
              final String mailing_address3 = sc.nextLine(); // Read in user input                 
              
              System.out.println("Please enter email:"); // User inputs the person's email address
              final String email3 = sc.nextLine(); // Read in user input                 
                             
              System.out.println("Please enter phone_Number:"); //User inputs the person's phone number
              final String phone_Number3 = sc.nextLine(); // Read in user input                 
                             
              System.out.println("Please enter employee salary:"); // User inputs the salary of employee      // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
              final int salary = sc.nextInt(); // Read in user input                
              sc.nextLine(); // Consuming the trailing new line character left after nextInt()
                            
              System.out.println("Please enter your marital status:"); // User inputs the date of recent training             // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
              final String marital_status = sc.nextLine(); // Read in user input 
                             
              System.out.println("Please enter employee hire date:"); // User inputs the volunteers most recent location training          cere, because the preceding nextLine consumed the newline character.
              final String hire_date = sc.nextLine(); // Read in user input            
                   
              System.out.println("Please enter team name:"); // User inputs the team name the volunteer is attached to eding nextLine consumed the newline character.
              final String team_name3 = sc.nextLine(); // Read in user input               
              
              System.out.println("Please enter report date YY-MM-DD:"); // User inputs the date employee receives report             // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
              final String report_date = sc.nextLine(); // Read in user input             
              
              System.out.println("Please enter content description:"); // User inputs the content of the report
              final String content_description = sc.nextLine(); // Read in user input 
         
              System.out.println("Connecting to the database...");
              // Get a database connection and prepare a query statement
              try (final Connection connection = DriverManager.getConnection(URL)) {
                  try (
                      final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_5)) {
                      // Populate the query template with the data collected from the user
                      statement.setString(1, SSN3);
                      statement.setString(2, pname3);
                      statement.setString(3, birth_date3);
                      statement.setString(4, race3);
                      statement.setString(5, gender3);
                      statement.setString(6, profession3);
                      statement.setString(7, mailing_list3);
                      statement.setString(8, mailing_address3);
                      statement.setString(9, email3);
                      statement.setString(10, phone_Number3);
                      statement.setInt(11, salary);
                      statement.setString(12, marital_status);
                      statement.setString(13, hire_date);
                      statement.setString(14, team_name3);
                      statement.setString(15, report_date);
                      statement.setString(16, content_description);
                     
                      System.out.println("Dispatching the query...");
                      // Actually execute the populated query
                      final int rows_inserted = statement.executeUpdate();
                      System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                  }
              }
	        	 break;
	        
	        case "6": // Insert Expense charged by employee 
	        	 // Collect the new client data from the user
			sc.nextLine();
			System.out.println("Please enter Amount:");
			final int amount = sc.nextInt(); // Read in the expense amount
			sc.nextLine();
			
			System.out.println("Please enter expenses_date:");
			final String expenses_date = sc.nextLine(); // Read in user input.
			
			System.out.println("Please enter expenses_description"); // User inputs the date of birth
			final String expenses_description = sc.nextLine(); // Read in user input of faculty department id.                
			            
			System.out.println("Please enter SSN"); // User inputs employee SSN
			final String SSN4 = sc.nextLine(); // Read in user input of faculty department id.                
			
			System.out.println("Connecting to the database...");
			// Get a database connection and prepare a query statement
			try (final Connection connection = DriverManager.getConnection(URL)) {
			 try (
			     final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_6)) {
			     // Populate the query template with the data collected from the user
			     statement.setInt(1, amount);
			     statement.setString(2, expenses_date);
			     statement.setString(3, expenses_description);
			     statement.setString(4, SSN4);
			     
			     System.out.println("Dispatching the query...");
			     // Actually execute the populated query
			     final int rows_inserted = statement.executeUpdate();
			     System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
			 }
			}
			break;	        	 
				        	 
	        case "7": // Insert organization into the database
	        	 // Collect the new client data from the user
			sc.nextLine();
			System.out.println("Please enter organization name:");
			final String org_Name = sc.nextLine(); // Read in the name of the organization
			sc.nextLine();
			
			System.out.println("Please enter organization's mailing address:");
			final String mailing_address7 = sc.nextLine(); // Read in user input.
			
			System.out.println("Please enter org phone_number"); // User inputs the 
			final String phone_Number7 = sc.nextLine(); // Read in user input of organization phone number.                
			            
			System.out.println("Please enter contact person"); // User inputs name of the organization contact person
			final String contact_person = sc.nextLine(); // Read in user input
			
			System.out.println("Please enter the team_name an org sponsors"); // User inputs name of the organization associated team name
			final String team_Name7 = sc.nextLine(); // Read in user input
			
			System.out.println("Connecting to the database...");
			// Get a database connection and prepare a query statement
			try (final Connection connection = DriverManager.getConnection(URL)) {
			 try (
			     final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_7)) {
			     // Populate the query template with the data collected from the user
			     statement.setString(1, org_Name);
			     statement.setString(2, mailing_address7);
			     statement.setString(3, phone_Number7);
			     statement.setString(4, contact_person);
			     statement.setString(5, team_Name7);
			     
			     System.out.println("Dispatching the query...");
			     // Actually execute the populated query
			     final int rows_inserted = statement.executeUpdate();
			     System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
			 }
			}
			break;	
			
	        case "8": // Insert Donor info into the database and and associate with donations 
	        	 // Collect the new client data from the user
			sc.nextLine();
			System.out.println("Please enter Donor SSN:");
			final String SSN8 = sc.nextLine(); // Read in the name of the organization
			sc.nextLine();
			
			System.out.println("Please enter donor's name:");
			final String pname8 = sc.nextLine(); // Read in user input.
			
			System.out.println("Please enter donor's birth_date"); // User inputs the 
			final String birth_date8 = sc.nextLine(); // Read in user input of organization phone number.                
			            
			System.out.println("Please enter donor's race"); // User inputs name of the organization contact person
			final String race8 = sc.nextLine(); // Read in user input
			
			System.out.println("Please enter donor's gender"); // User inputs gender of donor
			// M signifies Male and F signifies Female
			final String gender8 = sc.nextLine(); // Read in user input
			
			 System.out.println("Please enter donor's profession:"); // User inputs gender of person
             final String profession8 = sc.nextLine(); // Read in user input of faculty department id.                
                            
             System.out.println("Please enter donor's interest in mailing_list:"); // User inputs whether the person is interested in mailing list or not
             //1 signifies interest, 0 signifies disinterest
             final String mailing_list8 = sc.nextLine(); // Read in user input of faculty department id.                
                            
             System.out.println("Please enter donor's mailing_address:"); // User inputs whether the person's mailing address
             final String mailing_address8 = sc.nextLine(); // Read in user input                 
             
             System.out.println("Please enter donor's email:"); // User inputs the person's email address
             final String email8 = sc.nextLine(); // Read in user input                 
                            
             System.out.println("Please enter donor's phone_Number:"); //User inputs the person's phone number
             final String phone_Number8 = sc.nextLine(); // Read in user input                 
              
             System.out.println("Please enter donor's donation date:"); //User inputs the person's phone number
             final String donor_date = sc.nextLine(); // Read in user input                 
              
             System.out.println("Please enter donor's donation amount:"); //User inputs the person's phone number
             final String amount8 = sc.nextLine(); // Read in user input                 
              
             System.out.println("Please enter donor type:"); //User inputs the person's phone number
             final String donor_type = sc.nextLine(); // Read in user input                 
              
             System.out.println("Please enter campaign name:"); //User inputs the donor campaign
             final String campaign_name = sc.nextLine(); // Read in user input                 
              
             System.out.println("Please enter donor's anonymity where 1 signifies anonymous and 0 non anonymous:"); //User inputs the person's anonymity preference
             // 1 signifies anonymous 0 signifies non anonymous
             final String anonymity8 = sc.nextLine(); // Read in user input                 
              
             System.out.println("Please enter donor's card_no:"); //User inputs the donors's card no
             final String card_no = sc.nextLine(); // Read in user input                 
             
             System.out.println("Please enter donor's card_type:"); //User inputs the donors's card type
             //Card types. e.g. MASTERCARD, VISA, DISCOVER etc.
             final String card_type = sc.nextLine(); // Read in user input                 
              
             System.out.println("Please enter donor's expiration_date:"); //User inputs the donors's card exp_date
             final String expiration_date = sc.nextLine(); // Read in user input                 
              
             System.out.println("Please enter donor's cheque_no:"); //User inputs the donors's cheque no
             final String cheque_no = sc.nextLine(); // Read in user input                 
              
			System.out.println("Connecting to the database...");
			// Get a database connection and prepare a query statement
			try (final Connection connection = DriverManager.getConnection(URL)) {
			 try (
			     final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_8)) {
			     // Populate the query template with the data collected from the user
			     statement.setString(1, SSN8);
			     statement.setString(2, pname8);
			     statement.setString(3, birth_date8);
			     statement.setString(4, race8);
			     statement.setString(5, gender8);
			     statement.setString(6, profession8);
			     statement.setString(7, mailing_list8);
			     statement.setString(8, mailing_address8);
			     statement.setString(9, email8);
			     statement.setString(10, phone_Number8);
			     statement.setString(11, donor_date);
			     statement.setString(12, amount8);
			     statement.setString(13, donor_type);
			     statement.setString(14, campaign_name);
			     statement.setString(15, anonymity8);
			     statement.setString(16, card_no);
			     statement.setString(17, card_type);
			     statement.setString(18, expiration_date);
			     statement.setString(19, cheque_no);
			         
			    	     
			     System.out.println("Dispatching the query...");
			     // Actually execute the populated query
			     final int rows_inserted = statement.executeUpdate();
			     System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
			 }
			}
			break;	
			
	        case "9": // Insert Organization and their donations
	        	 // Collect the new client data from the user
	        	//Org refers to organization name
			sc.nextLine();
			System.out.println("Please enter Org name:");
			final String org_name= sc.nextLine(); // Read in the name of the organization
			sc.nextLine();
			
			System.out.println("Please enter org mailing address:");
			final String mailing_address9 = sc.nextLine(); // Read in user input.
			
			System.out.println("Please enter org phone number"); // User inputs the 
			final String phone_number9 = sc.nextLine(); // Read in user input of organization phone number.                
			            
			System.out.println("Please enter org contact"); // User inputs name of the organization contact person
			final String contact_person9 = sc.nextLine(); // Read in user input
			
			System.out.println("Please enter org donation date"); // User inputs gender of donor
			// M signifies Male and F signifies Female
			final String donation_date9 = sc.nextLine(); // Read in user input
			
			 System.out.println("Please enter donation amount:"); // User inputs gender of person
            final int amount9 = sc.nextInt(); // Read in user input of faculty department id.  
            sc.nextLine();
                           
            System.out.println("Please enter org donation type:"); // User inputs whether the person is interested in mailing list or not
            final String donation_type = sc.nextLine(); // Read in user input of faculty department id.                
                           
            System.out.println("Please enter org campaign_name:"); // User inputs whether the person's mailing address
            final String campaign_name9 = sc.nextLine(); // Read in user input                 
            
            System.out.println("Please enter org anonymity preference where 1 is anoonymous and 0 non anonymous:"); // User inputs the person's email address
            //1 signifies anonymous 0 signifies non anonymous
            final String anonymity9 = sc.nextLine(); // Read in user input                 
                           
            System.out.println("Please enter org card_no:"); //User inputs the person's phone number
            final String card_no9 = sc.nextLine(); // Read in user input                 
             
            System.out.println("Please enter org card_type:"); //User inputs the person's phone number
            final String card_type9 = sc.nextLine(); // Read in user input                 
             
            System.out.println("Please enter org card exp_date:"); //User inputs the person's phone number
            final String expiration_date9 = sc.nextLine(); // Read in user input                 
             
            System.out.println("Please enter org cheque no:"); //User inputs the person's phone number
            final String cheque_no9 = sc.nextLine(); // Read in user input                 
                            
             
			System.out.println("Connecting to the database...");
			// Get a database connection and prepare a query statement
			try (final Connection connection = DriverManager.getConnection(URL)) {
			 try (
			     final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_9)) {
			     // Populate the query template with the data collected from the user
			     statement.setString(1, org_name);
			     statement.setString(2, mailing_address9);
			     statement.setString(3, phone_number9);
			     statement.setString(4, contact_person9);
			     statement.setString(5, donation_date9);
			     statement.setInt(6, amount9);
			     statement.setString(7, donation_type);
			     statement.setString(8, campaign_name9);
			     statement.setString(9, anonymity9);
			     statement.setString(10, card_no9);
			     statement.setString(11, card_type9);
			     statement.setString(12, expiration_date9);
			     statement.setString(13, cheque_no9);
			    
			     System.out.println("Dispatching the query...");
			     // Actually execute the populated query
			     final int rows_inserted = statement.executeUpdate();
			     System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
			 }
			}
			break;
			
	        case "10": // Retrieve the name and phone number of the doctor of a particular client
                sc.nextLine();
                   System.out.println("Please enter the CLIENT'S SSN ");
                   
                   final String SSN10 = sc.nextLine();        // Collects the client SSN        

                   System.out.println("Connecting to the database...");
                   try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (final PreparedStatement statement = connection.prepareStatement("EXEC QUERY10 @SSN=?")) {
                        // insert input into the database
                        statement.setString(1, SSN10);
                        try(
                                final ResultSet resultSet = statement.executeQuery()){
                                
                                while (resultSet.next()) {
                                System.out.println(String.format("\tDOCTORS'S NAME: %s, DOCTOR'S PHONE: %s",
                                    resultSet.getString(1), resultSet.getString(2)));
                                System.out.println("\n");
                            }
                        }
                    }
                   }
                break;
			
	        case "11": // Retrieve the total amount of expenses charged by each employee for a particular period of time. The list should be sorted by the total amount of expenses 
                sc.nextLine();
                   System.out.println("Please enter START DATE ");
                   
                   final String DATE11 = sc.nextLine();        // Accepts START DATE        
                   
                   System.out.println("Please enter END DATE ");
                   final String DATE11B = sc.nextLine();        // Accepts END DATE

                   System.out.println("Connecting to the database...");
                   try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (final PreparedStatement statement = connection.prepareStatement("EXEC QUERY11 @START_DATE=?, @END_DATE=?")) {
                        // insert input into database
                        statement.setString(1, DATE11);
                        statement.setString(2, DATE11B);
                        
                        try(
                                final ResultSet resultSet = statement.executeQuery()){
                                while (resultSet.next()) {
                                System.out.println(String.format("\tEMPLOYEE'S SSN: %s, TOTAL: %s",
                                    resultSet.getString(1), 
                                    resultSet.getString(2)));
                                System.out.println("\n");
                            }
                        }
                    }
                   }
                break;
                
	        case "12": //  Retrieve the list of volunteers that are members of teams that support a particular client
                	sc.nextLine();
                   System.out.println("Please enter the particular client name");
                   
                   final String cname12 = sc.nextLine();        // Accepts client name       
                
                   System.out.println("Connecting to the database...");
                   try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (final PreparedStatement statement = connection.prepareStatement("EXEC QUERY12 @CNAME=?")) {
                        // insert input into database
                        statement.setString(1, cname12);
                        
                        try(
                                final ResultSet resultSet = statement.executeQuery()){
                                while (resultSet.next()) {
                                System.out.println(String.format("\t VOLUNTEER NAME: %s",
                                    resultSet.getString(1)));
                                System.out.println("\n");
                            }
                        }
                    }
                   }
                break;
			
									
	        case "13": // Display all NAME AND CONTACT INFO
            System.out.println("Connecting to the database...");
                // Get the database connection, execute procedure, as no user input need be collected
                try (final Connection connection = DriverManager.getConnection(URL)) {
                    System.out.println("Dispatching the query...");
                    try (
                        final Statement statement = connection.createStatement();
                        final ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE_13)) {

                          System.out.println("Contents of the QUERY:");
                          System.out.println("P_NAME				| EMAIL | MAILING ADDRESS | PHONE_NUMBER");

                            // Unpack the tuples returned by the database and print them out to the user
                            while (resultSet.next()) {
                        System.out.println(String.format("%s				 | %s | %s | %s ",
                                    resultSet.getString(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4)));
                            }
                    }
                }
                 break;
                 
	        case "14": // Retrieve the name and total amount donated by donors that are also employees
                System.out.println("Connecting to the database...");
                // Get the database connection, execute procedure, as no user input need be collected
                try (final Connection connection = DriverManager.getConnection(URL)) {
                    System.out.println("Dispatching the query...");
                    try (
                        final Statement statement = connection.createStatement();
                        final ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE_14)) {

                            System.out.println("Contents of the QUERY:");
                            System.out.println("NAME | TOTAL AMOUNT | ANONYMOUS");

                            // Unpack the tuples returned by the database and print them out to the user
                            while (resultSet.next()) {
                                System.out.println(String.format("%s | %s ",
                                    resultSet.getString(1),
                                    resultSet.getString(2)
                                    
                                    ));
                            }
                    }
                }
                 break;
                 
	        case "15": // Retrieve the names of all teams that were founded after a particular date 
                sc.nextLine();
                   System.out.println("Please enter the particular interested date ");
                   
                   final String DATE15 = sc.nextLine();        // Accept the inserted date        

                   System.out.println("Connecting to the database...");
                   try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (final PreparedStatement statement = connection.prepareStatement("EXEC QUERY15 @DATE=?")) {
                        // insert input into the database
                        statement.setString(1, DATE15);
                        try(
                                final ResultSet resultSet = statement.executeQuery()){
                                
                                while (resultSet.next()) {
                                System.out.println(String.format("\tTEAM NAME: %s",
                                    resultSet.getString(1)));
                               // System.out.println("\n");
                            }
                        }
                    }
                   }
                break;         
 
	        case "16": //Increase the salary by 10% of all employees to whom more than one team must report.
                System.out.println("Connecting to the database...");
                // Get a database connection and prepare a query statement
                try (final Connection connection = DriverManager.getConnection(URL)) {
                    try (
                        final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_16)) {
                        // Populate the query template with the data collected from the user
                     
                        System.out.println("Dispatching the query...");
                        // Actually execute the populated query
                        final int rows_inserted = statement.executeUpdate();
                        System.out.println(String.format("Done. %d rows updated.", rows_inserted));
                    }
                }
                 break;  
	        
	        case "17": // Enter the number of hours a volunteer worked this month for a particular team
                
                System.out.println("Connecting to the database...");
                // Get a database connection and prepare a query statement
                try (final Connection connection = DriverManager.getConnection(URL)) {
                    try (
                        final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_17)) {
                        // Populate the query template with the data collected from the user
                     
                        System.out.println("Dispatching the query...");
                        // Actually execute the populated query
                        final int rows_inserted = statement.executeUpdate();
                        System.out.println(String.format("Done. %d rows updated.", rows_inserted));
                    }
                }
                 break;
                 
	        case "18": // Import: enter new teams from a data file until the file is empty.
                
                sc.nextLine();
               
                System.out.println("Please enter the input filename:");
                final String input_file = sc.nextLine();
                
                try {
                      File myObj = new File(input_file+".txt");
                      
                      Scanner myReader = new Scanner(myObj);
                      
                      while (myReader.hasNextLine()) {
                          for(int k=0; k<1; k++) {
                            String data = myReader.nextLine();
                              System.out.println(data);
                              
                            try (final Connection connection = DriverManager.getConnection(URL)) {
                              try (
                                  final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_18)) {
                                  
                                  
                              
                                  // Populate the query template with the data collected from the user
                                  statement.setString(1, data);
                                  data = myReader.nextLine();
                                  statement.setString(2, data);
                                  data = myReader.nextLine();
                                  statement.setString(3, data);

 

                                  System.out.println("Dispatching the query...");
                                  // Actually execute the populated query
                                  final int rows_inserted = statement.executeUpdate();
                                  System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                              }
                          }
                              
                          } 
                      }
                      myReader.close();
                    } catch (FileNotFoundException e) {
                      System.out.println("An error occurred.");
                      e.printStackTrace();
                    }                
                    break;                 
                 
            case "19": // Retrieve names and mailing addresses of all people on the mailing list
                sc.nextLine();
                System.out.println("Please enter the output filename ");
                final String outputFile = sc.nextLine();
                System.out.println("Connecting to the database...");
                // Get the database connection, execute procedure, as no user input need be collected
                try (final Connection connection = DriverManager.getConnection(URL)) {
                    System.out.println("Dispatching the query...");
                    try (
                        final Statement statement = connection.createStatement();
                        final ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE_19)) {
                        
                        try {
                            FileWriter myWriter = new FileWriter(outputFile+".txt");
                            
                          myWriter.write("Contents of the QUERY: \n");
                          myWriter.write("NAME \t  ||| MAILING ADDRESS \n");
                        //myWriter.write(resultSet.getString(1));
                        while (resultSet.next()) {
                          myWriter.write(resultSet.getString(1)+"\t\t\t");
                          
                          myWriter.write(resultSet.getString(2)+"\n");
                      }
                          
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                          } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                          }                           
                    }
                }
            
                break;
             			
	        case "20": // Do nothing, the while loop will terminate upon the next iteration
                System.out.println("Exiting! Goodbuy!");
                break;
                
            default: // Unrecognized option, re-prompt the user for the correct one
                System.out.println(String.format(
                    "Unrecognized option: %s\n" + 
                    "Please try again!", 
                    option));
                break;
	        	 
	        }
        }
        
        sc.close(); // Close the scanner before exiting the application
        
    }
}
