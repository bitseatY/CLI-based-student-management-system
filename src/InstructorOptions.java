import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class InstructorOptions {
    private final Connection connection;
    private final   StudentDao studentDao;
    private final  MarklistDao marklistDao;
    private  final  InstructorDao instructorDao;
    private  final  CourseDao courseDao;

    private final Scanner scanner=new Scanner(System.in);
    public  InstructorOptions(Connection connection){
        this.connection=connection;
        studentDao=StudentDao.getStudentDao(connection);
        marklistDao=MarklistDao.getMarklistDao(connection);
        courseDao =CourseDao.getCourseDao(connection);
        instructorDao=InstructorDao.getInstructorDao(connection);
    }


    public void menu()  throws SQLException {

        Course course= courseDao.getCourseByCode(Options.retCode("Enter code for the course you are teaching: "));
        if(course==null){
            System.out.println("course not found.");
            return;
        }
        String id=Options.retId("Enter your id : ");

        Instructor instructor=instructorDao.getInsById(id);
        if(instructor==null){
            System.out.println("not found");
            return;
        }
        boolean flag=true;
        while(flag){
            System.out.println("""
     what would you like to do?
                     1.view list of students enrolled
                     2.view student profile
                     3.grade a student
                     4.remove a student from the course
                     5.exit
   """);
                 int answer;
                 try {
                     answer = Integer.parseInt(scanner.nextLine());
                 } catch (NumberFormatException e) {
                     System.out.println("incorrect input,enter valid number and try again.");
                     scanner.nextLine();
                     continue;
                 }

                 if(answer<1||answer>8){
                     System.out.println("options range from 1-5, try again.");
                     continue;
                 }
            switch (answer){
                case 1:
                    String code=Options.retCode("enter the code for the course: ");
                    listEnrolledStudents(code);
                    break;
                case 2:
                    new StudentOptions(connection).seeProfile(Options.retId("Enter the student id: "));
                    break;
                case 3:
                    gradeStudent(Options.retId("Enter the student id : "),Options.retCode("enter code for course: "));
                    break;
                case 4:
                   new StudentOptions(connection).un_enroll(Options.retId("Enter the student id: "),Options.retCode("enter the code for the course"));
                   break;
                case 5:
                    flag=false;

            }
        }

    }

    public void gradeStudent(String s_id,String c_code) throws SQLException {

        Student student = StudentDao.getStudentDao(connection).getStudent(s_id);
        Course course=CourseDao.getCourseDao(connection).getCourseByCode(c_code);
        if (student == null) {
            System.out.println("student not found.");
            return;
        }
        if (course == null) {
            System.out.println("course not found.");
            return;
        }
        System.out.println(student.getName() + "(ID-" + student.getId() + ")");
        System.out.print("enter score for " + course.getTitle() + "(" + course.getCode() + ")");
        double score = Double.parseDouble(scanner.nextLine());
        marklistDao.gradeStudent(s_id,c_code,score );
    }










    public void listEnrolledStudents(String code) throws SQLException{
           List<Student> students= marklistDao.getCourseEnrolledStudents(code);
           if(students.isEmpty())
               return;
           for(Student student:students)
               System.out.println("name:"+student.getName());

    }










}
