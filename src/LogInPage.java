import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class LogInPage {

    public static void menu()  throws SQLException {

       

        Scanner scanner = new Scanner(System.in);
       System.out.println("enter url for the database;");
        String url=scanner.nextLine();
         System.out.println("enter user name ;");
        String username=scanner.nextLine();
         System.out.println("enter password for the database ;");
        String password=scanner.nextLine();
        Connection connection = new DBConnection(url, username, password ).getConnection();


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
            int answer;
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
                    Options managerOptions = new ManagerOptions(connection);
                    managerOptions.menu();
                    break;
                case 4:
                    flag = false;
            }
        }

    }

}
