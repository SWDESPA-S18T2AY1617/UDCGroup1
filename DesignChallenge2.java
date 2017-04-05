/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package designchallenge1;

/**
 *
 * @author Arturo III
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DesignChallenge2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //CalendarProgram cp = new CalendarProgram();
        String driverName = "com.mysql.jdbc.Driver";
        String localhost = "127.0.0.1";
        String url = "jdbc:mysql://127.0.0.1:3306/";
        String database = "mydb";
        String username = "root";
        String password = "password";
        Connection connection;

        try {
          Class.forName("com.mysql.jdbc.Driver");
          connection = DriverManager.getConnection(url + database + "?autoReconnect=true&useSSL=false", username, password);
          Statement query = connection.createStatement();
          CalendarModel cm= new CalendarModel(query);
          CalendarView cv = new CalendarView();
          CalendarController cc = new CalendarController(cv, cm);
          cv.setController(cc);
        } catch (Exception ex) {
          System.out.println("hmm");
        }
        
      //  cm.attachIn(cc);
    }
}
