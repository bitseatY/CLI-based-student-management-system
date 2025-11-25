//This is a CLI based student management system.
// users are students, instructors and managers.
//This project uses object serialization for data persistence.
//Erase previous content from related files ,to start new.
//To use student options ,you have to add student using manager option first.
//To use instructor options ,you have to add a course and assign an instructor to the course using the manager option first.
//Use exit option when you're done to save changes to files.


import java.beans.Transient;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;



public class Main {
    public static void main(String[] args) throws SQLException {
/**
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        while (flag) {
            System.out.println("""
                    Welcome to our school portal.
                    enter the number written before your occupation.
                    are you a 1 student
                              2 instructor
                              3 manager
                              4 exit this portal.
                    """);

            int answer = 0;
            try {
                answer = Integer.parseInt(scanner.nextLine());
                if (answer > 4 || answer < 1) {
                    System.out.println("options range from 1 to 4 ,try again");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid input ,please enter a valid number.");
                scanner.nextLine();
                continue;
            }


            switch (answer) {
                case 1:
                    options options1 = new StudentOptions();
                    options1.menu();
                    break;
                case 2:
                    options options2 = new InstructorOptions();
                    options2.menu();
                    break;
                case 3:
                    options options3 = new ManagerOptions();
                    options3.menu();
                    break;
                case 4:
                    flag = false;
            }

        }*/

        Connection connection=new DBConnection("jdbc:mysql://localhost:3306/portal","root","b:tse@t1996").getConnection();
        StudentDao dao=new StudentDao(connection);
        dao.addStudent("1","lala");
    }

}








