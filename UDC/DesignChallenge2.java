/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package designchallenge1;

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
        CalendarModel cm= new CalendarModel();
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
        
      //  cm.attachIn(cc);
    }
}
