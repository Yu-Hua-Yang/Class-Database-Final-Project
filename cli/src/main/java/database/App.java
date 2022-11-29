package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App 
{
    /**
     * Runs the main program and the beginning of the method chain
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void main( String[] args ) throws SQLException, ClassNotFoundException{   

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║               WELCOME TO THE DATABASE PROGRAM               ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        Connection conn = getConnection();

        runProgram(conn);
    }

    /**
     * runProgram runs the first part of program
     * it creates a select menu asking what you would like to do
     * then running its own individual programs
     * @param conn
     */
    private static void runProgram(Connection conn){
        Scanner scan = new Scanner(System.in);
        boolean isValid = false;

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                  WHAT WOULD YOU LIKE TO DO                  ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        while(!isValid){
            String regex = "^[0-9A-Z]{3}-[0-9A-Z]{3}-[0-9A-Z]{2}$";
            boolean inputValid = false;
            String input = "";
            String courseID = "";

            while(!inputValid){
                    
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║           (1)UPDATE, (2)ADD, (3)DELETE, (4)DISPLAY          ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

                input = scan.nextLine();
                System.out.println("");
                
                switch(input){
                    //Update
                    case "1":

                        createObject(input, conn);
                        isValid = true;
                        inputValid = true;
                        break;
                    //Add
                    case "2":

                        createObject(input, conn);
                        isValid = true;
                        inputValid = true;
                        break;
                    //Delete
                    case "3":
                        //Printing out all the Possible Course Id to Delete
                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                        System.out.println("║          HERE IS A LIST OF ALL EXISTING COURSES ID          ║");
                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
            
                        List<String> coursesList = printCourses(conn);
                    
                        //Asks to enter a course id of the one provided
                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                        System.out.println("║                 ENTER ONE OF THE ID PROVIDED                ║");
                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
            
                        courseID = matchID(coursesList);

                        deleteCourse(courseID, conn);

                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                        System.out.println("║                COURSE INPUT HAS BEEN DELETED                ║");
                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

                        isValid = true;
                        inputValid = true;
                        break;
                    //Display
                    case "4":

                        boolean displayAgain = false;

                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                        System.out.println("║                WHAT WOULD YOU LIKE TO DISPLAY               ║");
                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

                        while(!displayAgain){
                            //Asks what it would like to Display
                            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                            System.out.println("║                    (1)COURSE OR (2)AUDIT                    ║");
                            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 
                            
                            String displayInput = scan.nextLine();
                            System.out.println("");

                            //Check if equals to Course
                            if(displayInput.equals("1")){
                                //Printing out all the Possible Course Id to Display
                                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                                System.out.println("║          HERE IS A LIST OF ALL EXISTING COURSES ID          ║");
                                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                    
                                List<String> coursesList2 = printCourses(conn);

                                //Asks to enter a course id of the one provided
                                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                                System.out.println("║                 ENTER ONE OF THE ID PROVIDED                ║");
                                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                    
                                courseID = matchID(coursesList2);
                                displayCourse(courseID, conn);

                                displayAgain = true;
                                break;
                            }
                            //Checking if it equals Audit
                            else if(displayInput.equals("2")){
                                //Printing out the Whole Audit Log
                                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                                System.out.println("║                 HERE IS THE WHOLE AUDIT LOG                 ║");
                                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

                                displayAudit(conn);

                                displayAgain = true;
                                break;
                            }
                            //Re runs the Loop
                            else{
                                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                                continue;
                            }
                        }
                        isValid = true;
                        inputValid = true;
                        break;
                    //Default Re Runs the Method
                    default:

                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                        System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                        continue;

                }
            }
            //breaks out of the loop
            if(inputValid == true){
                isValid = true;
            }
        }
    }
    
    /**
     * Gets the Connection by user input with the username and the password
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Scanner scan = new Scanner(System.in);
        String url = "jdbc:oracle:thin:@198.168.52.211:1521/pdbora19c.dawsoncollege.qc.ca";
        boolean isEmpty = false;
        String user = "";
        String pass = "";
    
        //gets the user to input into getConnection
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                        ENTER USERNAME                       ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        user = scan.nextLine();
        System.out.println("");
        
        while(!isEmpty){
            //gets the pass to input into getConnection
            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("║                        ENTER PASSWORD                       ║");
            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

            String password= new String(System.console().readPassword());
            System.out.println("");

            if(password == null || password.trim().isEmpty()){
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
            else{
                pass = password;
                isEmpty = true;
                break;
            }
        }
        //runs getConnection with the inputed variables
        Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }     
    
    /**
     * runAgain is put at the end of every action to check if the user wants
     * to run the program again or to exit the program
     * @param conn
     */
    public static void runAgain(Connection conn){
        boolean runAgain = false;
        Scanner scan = new Scanner(System.in);

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║              WOULD YOU LIKE TO DO ANYTHING ELSE             ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

        //loops for data validation
        while(!runAgain){
            //asks if yes or no
            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("║                       (1)YES OR (2)NO                       ║");
            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

            String input = scan.nextLine();
            System.out.println("");

            //if yes it will run the program again and loop from the top of the program
            if(input.equals("1")){
                runProgram(conn);
            }
            //it thanks you for using and exits you out
            else if(input.equals("2")){
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║              THANK YOU FOR USING OUR SERVICES               ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║                    HOPE TO SEE YOU AGAIN                    ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

                runAgain = true;
                break;
            }
            //if input is invalid will run again
            else{
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
            break;
        }   
    }

    /**
     * displayAudit takes a prepared statement to grab all the information
     * from audit log table and prints it out to display for the user
     * @param conn
     */
    public static void displayAudit(Connection conn){
        try {
            PreparedStatement state = conn.prepareStatement("SELECT * FROM AuditLog");
            ResultSet res = state.executeQuery();
            while(res.next()){
                //puts into strings
                String print1 = "Auditor: " + res.getString(1);
                String print2 = "Audit Type: " + res.getString(2);
                String print3 = "Audit Date: " + res.getString(3);
                String print4 = "Audit Table: " + res.getString(4);
        
                //prints out the strings
                System.out.println(print1);
                System.out.println(print2);
                System.out.println(print3);
                System.out.println(print4);
                System.out.println("");
            }
        }
        //prints any exception error
        catch(SQLException e) {
            System.out.println(e);
        }
        //runs runAgain to see if user wants to run again
        finally {
            runAgain(conn);
        }
    }

    /**
     * deleteCourse takes a input of courseID to delete the course where
     * the course id is the inputed id
     * @param courseID
     * @param conn
     */
    public static void deleteCourse(String courseID, Connection conn){
        try {
            //calls the procedure made to delete a course
            CallableStatement state = conn.prepareCall("{call class_data.delete_course(?)}");
            state.setObject(1, courseID);
            state.execute();

            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("║             COURSE HAS BEEN SUCCESSFULLY DELETED            ║");
            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        }
        //prints any exception error
        catch(SQLException e) {
            System.out.println(e);
        }
        //runs runAgain to see if user wants to run again
        finally {
            runAgain(conn);
        }
    }

    /**
     * printCourses prints out all the course id in the courses table
     * and will return a list of all the course id
     * @param conn
     * @return list of courseID
     */
    public static List<String> printCourses(Connection conn){
        List<String> courseList = new ArrayList<String>();
        try {
            //will prepare a statment to get all the data on course id
            PreparedStatement state = conn.prepareStatement("SELECT course_id FROM COURSES");
            ResultSet res = state.executeQuery();
            System.out.println("Course ID: ");
            System.out.println("");
            while(res.next()){
                courseList.add(res.getString(1));
                System.out.println(res.getString(1));
            }
            System.out.println("");
        }
        //prints any exception error
        catch(SQLException e) {
            System.out.println(e);
        }
        //returns the list of courseID
        return courseList;

    }

    /**
     * displayCourse takes a course id as input to get all information
     * from the view Course_View where course id equals the one inputed
     * and prints it all out
     * @param courseID
     * @param conn
     */
    public static void displayCourse(String courseID, Connection conn){
        try {
            //prepared statement to grab all the information
            PreparedStatement state = conn.prepareStatement("SELECT * FROM COURSE_VIEW WHERE course_id = ?");
            state.setString(1, courseID);
            ResultSet res = state.executeQuery();

            //creating the prints
            while(res.next()){
                String print1 = "Course ID: " + res.getString(1);
                String print2 = "Course Title: " + res.getString(2);
                String print3 = "Course Type Title: " + res.getString(3);
                String print4 = "Course Description: " + res.getString(4);
                String print5 = "Course Hours: " + res.getString(5);
                String print6 = "Ponderation: " + res.getString(6);
                String print7 = "Term ID: " + res.getString(7);
                String print8 = "Credits: " + res.getString(8);
                
                //printing all the statements
                System.out.println(print1);
                System.out.println(print2);
                System.out.println(print3);
                System.out.println(print4);
                System.out.println(print5);
                System.out.println(print6);
                System.out.println(print7);
                System.out.println(print8);
                System.out.println("");
            }
        }
        //prints any exception error
        catch(SQLException e) {
            System.out.println(e);
        }
        //runs runAgain to see if user wants to run again
        finally {
            runAgain(conn);
        }
    }

    /**
     * takes two input and checks if the string value is equal
     * to the regex value provided
     * @param value
     * @param regex
     * @return boolean if they are valid
     */
    private static boolean isValid(String value, String regex) {
        if(value.matches(regex)) {
            return true;
        }
        return false;
    }

    /**
     * validateInput takes in a regex and checks your regex
     * and if it matches the input if not it will repeat it 
     * continously until it is correct
     * @param regex
     * @return the input you put in
     */
    private static String validateInput(String regex) {
        Scanner scan = new Scanner(System.in);
        boolean reInput = false;
        String input = "";

        //while loop for validation
        while(!reInput){
            input = scan.nextLine();
            System.out.println("");

            //CREATE REGEX WITH VINCE
            boolean valid = isValid(input, regex);

            if(valid){
                //checks if valid if it is then breaks out
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║                      INPUT IS VALIDATED                     ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                reInput = true;
                break;
            }
            else{
                //if not valid it will just loop again
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }
        //returns the input
        return input;
    }

    /**
     * matchID takes a list and will run a loop for datavalidation to check
     * if the inputed courseId is in the database if not will loop again
     * @param list
     * @return a courseID that matches the one provided from the list
     */
    private static String matchID(List<String> list){
        Scanner scan = new Scanner(System.in);
        boolean isMatch = false;
        String input = "";
        boolean valid = false;
        String courseID = "";

        //while loop to check validation
        while(!isMatch){

            input = scan.nextLine();
            System.out.println("");

            //loops through each index and checks if they are the same
            for( String index : list){
                if(input.equals(index)){
                    System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                    System.out.println("║                        INPUT IS VALID                       ║");
                    System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                    //makes it equal if index and courseID is the same
                    courseID = index;
                    valid = true;
                    isMatch = true;
                    break;
                }
                //else it doesn't containt the input
                else{
                    valid = false;
                }
            }

            //checks if valid is false or true
            if(!valid){
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }   
        //returns courseID 
        return courseID;
    }

    /**
     * runUpdate takes in a courseID to grab all other values
     * from the table courses where courseID equals inputed courseID
     * @param conn
     * @param courseID
     */
    private static void runUpdate(Connection conn, String courseID){
        //declaration of variables
        String courseDescription = "";
        String courseTitle = "";
        String courseHours = "";
        String ponderation = "";
        String termID = "";
        String credits = "";
        String courseType = "";
        try{
            //prepared statement to grab all values
            PreparedStatement state = conn.prepareStatement("SELECT course_description, course_title, course_hours, ponderation, term_id, credits, course_type FROM COURSES WHERE course_id = ?");
            state.setString(1, courseID);
            ResultSet res = state.executeQuery();
            while(res.next()){
                //putting values grabbed into respective String value
                courseDescription = res.getString(1);
                courseTitle = res.getString(2);
                courseHours = res.getString(3);
                ponderation = res.getString(4);
                termID = res.getString(5);
                credits = res.getString(6);
                courseType = res.getString(7);
            }
        }
        //prints any exception error
        catch(SQLException e) {
            System.out.println(e);
        }
        //runs runAgain to see if user wants to run again
        finally{
            updateInput(courseID, courseDescription, courseTitle, courseHours, ponderation, termID, credits, courseType, conn);
        }
    }

    private static void whichTerm(String termID, Connection conn){
        String termTitle = "";
        
        if(termID.equals("1") || termID.equals("3") || termID.equals("5")){
            termTitle = "FALL";
            Term addNewTerm = new Term(Integer.parseInt(termID), termTitle);
            addNewTerm.addTerm(conn);
        }
        else if(termID.equals("2") || termID.equals("4") || termID.equals("6")){
            termTitle = "WINTER";
            Term addNewTerm = new Term(Integer.parseInt(termID), termTitle);
            addNewTerm.addTerm(conn);
        }
    }

    private static void whichType(String typeID, Connection conn){
        String typeTitle = "";

        if(typeID.equals("CON")){
            typeTitle = "CONCENTRATION";
            ClassType addNewClassType = new ClassType(typeID, typeTitle);
            addNewClassType.addClassType(conn);
        }
        else if(typeID.equals("GEN")){
            typeTitle = "GENERAL";
            ClassType addNewClassType = new ClassType(typeID, typeTitle);
            addNewClassType.addClassType(conn);
        }
    }

    /**
     * updateInput takes as input all the information of a object provided by a list
     * to then go through everything user what they would like to change and thus you 
     * can pick and choose instead of changing everything
     * @param courseID
     * @param courseDescription
     * @param courseTitle
     * @param courseHours
     * @param ponderation
     * @param termID
     * @param credits
     * @param courseType
     * @param conn
     */
    private static void updateInput(String courseID, String courseDescription, String courseTitle, String courseHours, String ponderation, String termID, String credits, String courseType, Connection conn){
        Scanner scan = new Scanner(System.in);

        String input = "";
        String regex = "";

        //declaring variable for storage
        String course_description = "";
        String course_title = "";
        String course_hours = "";
        String course_ponderation = "";
        String term_id = "";
        String course_credits = "";
        String course_type = "";

        //declaring while loops
        boolean isDescription = false;
        boolean isTitle = false;
        boolean isHours = false;
        boolean isPonderation = false;
        boolean isTermId = false;
        boolean isCredits = false;
        boolean isCourseType = false;

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║              WOULD YOU LIKE TO UPDATE DESCRIPTION           ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        //asking to update or not
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                       (1)YES OR (2)NO                       ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 
        // INPUTTING DESCRIPTION
        while(!isDescription){

            input = scan.nextLine();
            System.out.println("");

            if(input.equals("1")){
                //running data validation for input
                regex = "^.{1,500}$";
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║          ENTER THE COURSE DESCRIPTION FOR THE COURSE        ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║             INPUT MUST BE BETWEEN 1 TO 500 CHAR             ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

                //checks input and regex
                course_description = validateInput(regex);
                isDescription = true;
                break;
            }
            else if(input.equals("2")){
                //giving variable the exact one as the previous inserted
                course_description = courseDescription;
                isDescription = true;
                break;
            }
            else{
                //loops back because of invalid input
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║               WOULD YOU LIKE TO UPDATE TITLE                ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        //asking to update or not
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                       (1)YES OR (2)NO                       ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        // INPUTING TITLE
        while(!isTitle){
             
            input = scan.nextLine();
            System.out.println("");

            if(input.equals("1")){
                //running data validation for input
                regex = "^.{1,50}$";
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║            ENTER THE COURSE TITLE FOR THE COURSE            ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║             INPUT MUST BE BETWEEN 1 TO 50 CHAR              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                
                //checks input and regex
                course_title = validateInput(regex);
                isTitle = true;
                break;
            }
            else if(input.equals("2")){
                //giving variable the exact one as the previous inserted
                course_title = courseTitle;
                isTitle = true;
                break;
            }
            
            else{
                //loops back because of invalid input
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║               WOULD YOU LIKE TO UPDATE HOURS                ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        //asking to update or not
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                       (1)YES OR (2)NO                       ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

        // INPUTING HOURS
        while(!isHours){

            input = scan.nextLine();
            System.out.println("");

            if(input.equals("1")){
                //running data validation for input
                regex = "^[0-9]{1,3}$";
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║            ENTER THE COURSE HOURS FOR THE COURSE            ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║                 INPUT MUST BE 1 OR 3 DIGITS                 ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                
                //checks input and regex
                course_hours = validateInput(regex);
                isHours = true;
                break;
            }
            else if(input.equals("2")){
                //giving variable the exact one as the previous inserted
                course_hours = courseHours;
                isHours = true;
                break;
            }
            else{
                //loops back because of invalid input
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║            WOULD YOU LIKE TO UPDATE PONDERATION             ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        //asking to update or not
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                       (1)YES OR (2)NO                       ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

        // INPUTING PONDERATION
        while(!isPonderation){
        
            input = scan.nextLine();
            System.out.println("");

            if(input.equals("1")){
                //running data validation for input
                regex = "^[0-9]{1}-[0-9]{1}-[0-9]{1}$";
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║         ENTER THE COURSE PONDERATION FOR THE COURSE         ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║          INPUT MUST BE A DIGIT - A DIGIT - A DIGIT          ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                
                //checks input and regex
                course_ponderation = validateInput(regex);
                isPonderation = true;
                break;
            }
            else if(input.equals("2")){
                //giving variable the exact one as the previous inserted
                course_ponderation = ponderation;
                isPonderation = true;
                break;
            }
            else{
                //loops back because of invalid input
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║              WOULD YOU LIKE TO UPDATE TERM ID               ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        //asking to update or not
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                       (1)YES OR (2)NO                       ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

        // INPUTTING TERM ID
        while(!isTermId){

            input = scan.nextLine();
            System.out.println("");

            if(input.equals("1")){
                //running data validation for input
                regex = "^[1-6]{1}$";
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║           ENTER THE COURSE TERM ID FOR THE COURSE           ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║            INPUT MUST BE A DIGIT BETWEEN 1 AND 6            ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                
                //checks input and regex
                term_id = validateInput(regex);
                whichTerm(term_id, conn);
                isTermId = true;
                break;
            }
            else if(input.equals("2")){
                //giving variable the exact one as the previous inserted
                term_id = termID;
                isTermId = true;
                break;
            }
            else{
                //loops back because of invalid input
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║              WOULD YOU LIKE TO UPDATE CREDITS               ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        //asking to update or not
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                       (1)YES OR (2)NO                       ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

        // INPUTTING CREDITS
        while(!isCredits){

            input = scan.nextLine();
            System.out.println("");

            if(input.equals("1")){
                //running data validation for input
                regex = "^[0-9]{1-2}[.]?[0-9]{1}$";
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║           ENTER THE COURSE CREDITS FOR THE COURSE           ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║         INPUT MUST BE 1 OR 2 DIGITS . 0 OR 1 DIGITS         ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                
                //checks input and regex
                course_credits = validateInput(regex);
                isCredits = true;
                break;
            }
            else if(input.equals("2")){
                //giving variable the exact one as the previous inserted
                course_credits = credits;
                isCredits = true;
                break;
            }
            else{
                //loops back because of invalid input
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║            WOULD YOU LIKE TO UPDATE COURSE TYPE             ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        //asking to update or not
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                       (1)YES OR (2)NO                       ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n"); 

        // INPUTTING COURSE TYPE
        while(!isCourseType){
            
            input = scan.nextLine();
            System.out.println("");

            if(input.equals("1")){
                //running data validation for input
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║             ENTER THE COURSE TYPE FOR THE COURSE            ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║                   INPUT MUST BE CON OR GEN                  ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                
                boolean reInput = false;
                //checks input and regex
                while(!reInput){
                input = scan.nextLine();
                System.out.println("");

                if(input.equals("CON") || input.equals("GEN")){
                    //checks if valid if it is then breaks out
                    System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                    System.out.println("║                      INPUT IS VALIDATED                     ║");
                    System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                    course_type = input;
                    reInput = true;
                    break;
                }
                else{
                    //if not valid it will just loop again
                    System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                    System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                    System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                    continue;
                }
                }

                whichType(course_type, conn);
                isCourseType = true;
                break;
            }
            else if(input.equals("2")){
                //giving variable the exact one as the previous inserted
                course_type = courseType;
                isCourseType = true;
                break;
            }
            else{
                //loops back because of invalid input
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }

        //creates the object with all the values just inputed
        Courses updateCourseObj = new Courses(courseID, course_description, course_title, Integer.parseInt(course_hours), course_ponderation, Integer.parseInt(term_id),  Double.parseDouble(course_credits), course_type);
        
        //uses that object to run the update updatecourse method calling upon the procedure
        updateCourseObj.updateCourse(conn);

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║             COURSE HAS BEEN SUCCESSFULLY UPDATED            ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
    }  

    /**
     * addInput creates empty variables to store all information provided by user
     * and it runs through every variable need to add to the table and checks with 
     * the regex to then insert it into objects and adds it to table by calling a procedure
     * @param conn
     */
    public static void addInput(Connection conn){
        //data initialization
        Scanner scan = new Scanner(System.in);
        String regex = "";
        String courseID = "";
        String courseDescription = "";
        String courseTitle = "";
        String courseHours = "";
        String ponderation = "";
        String termID = "";
        String credits = "";
        String courseType = "";
        String typeTitle = "";
        String termTitle = "";

        // COURSE ID
        //regex for data validation
        regex = "^[0-9A-Z]{3}-[0-9A-Z]{3}-[0-9A-Z]{2}$";
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║              ENTER THE COURSE ID FOR THE COURSE             ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║            INPUT MUST BE 3 CHAR - 3 CHAR - 2 CHAR           ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        //uses regex to data validate and stores the input  
        courseID = validateInput(regex);

        // COURSE DESCRIPTION
        //regex for data validation
        regex = "^.{1,500}$";
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║          ENTER THE COURSE DESCRIPTION FOR THE COURSE        ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║             INPUT MUST BE BETWEEN 1 TO 500 CHAR             ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        //uses regex to data validate and stores the input
        courseDescription = validateInput(regex);
    

        // COURSE TITLE
        //regex for data validation
        regex = "^.{1,50}$";
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║            ENTER THE COURSE TITLE FOR THE COURSE            ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║             INPUT MUST BE BETWEEN 1 TO 50 CHAR              ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        //uses regex to data validate and stores the input
        courseTitle = validateInput(regex);

        // COURSE HOURS
        //regex for data validation
        regex = "^[0-9]{1,3}$";
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║            ENTER THE COURSE HOURS FOR THE COURSE            ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                 INPUT MUST BE 1 OR 3 DIGITS                 ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        //uses regex to data validate and stores the input
        courseHours = validateInput(regex);

        // PONDERATION
        //regex for data validation
        regex = "^[0-9]{1}-[0-9]{1}-[0-9]{1}$";
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║         ENTER THE COURSE PONDERATION FOR THE COURSE         ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║          INPUT MUST BE A DIGIT - A DIGIT - A DIGIT          ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        //uses regex to data validate and stores the input
        ponderation = validateInput(regex);

        // TERM ID
        //regex for data validation
        regex = "^[1-6]{1}$";
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║           ENTER THE COURSE TERM ID FOR THE COURSE           ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║            INPUT MUST BE A DIGIT BETWEEN 1 AND 6            ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        //uses regex to data validate and stores the input
        termID = validateInput(regex);
        whichTerm(termID, conn);

        // COURSE CREDITS
        //regex for data validation
        regex = "^[0-9]{1,2}[.]?[0-9]{1}$";
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║           ENTER THE COURSE CREDITS FOR THE COURSE           ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║         INPUT MUST BE 1 OR 2 DIGITS . 0 OR 1 DIGITS         ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
        //uses regex to data validate and stores the input
        credits = validateInput(regex);

        // COURSE TYPE
        //regex for data validation
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║             ENTER THE COURSE TYPE FOR THE COURSE            ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
        System.out.println("║                   INPUT MUST BE CON OR GEN                  ║");
        System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

        boolean reInput = false;
        String input = "";

        //while loop for validation
        while(!reInput){
            input = scan.nextLine();
            System.out.println("");

            if(input.equals("CON") || input.equals("GEN")){
                //checks if valid if it is then breaks out
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║                      INPUT IS VALIDATED                     ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                courseType = input;
                reInput = true;
                break;
            }
            else{
                //if not valid it will just loop again
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║               INPUT IS INVALID PLEASE RE-ENTER              ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
                continue;
            }
        }
        
        whichType(courseType, conn);

         //creating objects for 3 different table with the information provided by the user
         Courses addCourseObj = new Courses(courseID, courseDescription, courseTitle, Integer.parseInt(courseHours), ponderation, Integer.parseInt(termID), Double.parseDouble(credits), courseType);

         //using the created object to call the respective add methods calling on their procedures
         addCourseObj.addCourse(conn);

         System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
         System.out.println("║              COURSE HAS BEEN SUCCESSFULLY ADDED             ║");
         System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");
    }
    
    /**
     * createObject takes in a String task of what they wanted to do and when running they 
     * check whether it was update or add then running the respective methods for what was
     * requested to be done
     * @param task
     * @param conn
     */
    public static void createObject(String task, Connection conn){
        String courseID = "";

        //checking if task equals 2 or add to table
        if(task.equals("2")){
            //runs add input
            addInput(conn);
        }
        //checking if task equals 1 or update table
        else if(task.equals("1")){
            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("║          HERE IS A LIST OF ALL EXISTING COURSES ID          ║");
            System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

            //takes printCourses and returns it into a list
            List<String> coursesList = printCourses(conn);

                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                System.out.println("║                 ENTER ONE OF THE ID PROVIDED                ║");
                System.out.println("║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║\n");

                //takes input list to run through and check if input matches then inserting it into a variable
                courseID = matchID(coursesList);

                //runs update course
                runUpdate(conn, courseID);
        }

        //runs everything again from the very top of the program
        runAgain(conn);
    }
}
//done