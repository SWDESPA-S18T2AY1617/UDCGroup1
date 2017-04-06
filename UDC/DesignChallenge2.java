/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package designchallenge1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
/**
 *
 * @author Arturo III
 */
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
        String password = "root";
        Connection connection;
        try {
          Class.forName("com.mysql.jdbc.Driver");
          connection = DriverManager.getConnection(url + database + "?autoReconnect=true&useSSL=false", username, password);
          Statement query = connection.createStatement();
          CalendarModel cm= new CalendarModel(query);
          CalendarView d1 = new DoctorView("Doctor 1");
          CalendarView d2 = new DoctorView("Doctor 2");
          CalendarView d3 = new DoctorView("Doctor 3");
          CalendarView c1 = new ClientView("Client 1");
          CalendarView c2 = new ClientView("Client 2");
          CalendarView s = new SecretaryView("Secretary");
          
          CalendarController cc = new CalendarController(cm);
          cc.attachView(d1);
          cc.attachView(d2);
          cc.attachView(d3);
          cc.attachView(s);
          cc.attachView(c1);
          cc.attachView(c2);
          
          d1.setController(cc);
          d2.setController(cc);
          d3.setController(cc);
          c1.setController(cc);
          c2.setController(cc);
          s.setController(cc);
        } catch(Exception e) {
          e.printStackTrace();
          System.out.println("hmm");
        }
        
      //  cm.attachIn(cc);
    }
}
