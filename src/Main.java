//This is a CLI based student management system.
// users are students, instructors and managers.
//This project uses object serialization for data persistence.
//Erase previous content from related files ,to start new.
//To use student options ,you have to add student using manager option first.
//To use instructor options ,you have to add a course and assign an instructor to the course using the manager option first.
//Use exit option when you're done to save changes to files.



import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
         Connection connection=new DBConnection("jdbc:mysql://localhost:3306/portal","root","b:tse@t1996").getConnection();


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
                    StudentOptions studentOptions = new StudentOptions(connection);
                    studentOptions.menu();
                    break;
                case 2:
                    InstructorOptions instructorOptions = new InstructorOptions(connection);
                    instructorOptions.menu();
                    break;
                case 3:
                 ManagerOptions managerOptions = new ManagerOptions(connection);
                   managerOptions.menu();
                    break;
                case 4:
                    flag = false;
            }

        }

    }

}








