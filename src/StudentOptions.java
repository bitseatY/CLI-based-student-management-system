import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;


public class StudentOptions implements Options{
    private final Connection connection;
    public  StudentOptions(Connection connection){
        this.connection=connection;

    }
    public void menu() throws SQLException {
        boolean flag=true;
        User student=StudentDao.getStudentDao(connection).getStudent(Options.retId("Enter your ID: "));

        if(student==null){
            System.out.println("ID not found try again.");
            return;
        }
        while(flag) {
            System.out.println("""
                        what would you like to do?
                        1.view available courses
                        2.view enrolled courses
                        3.enroll to a course
                        4.see Grade for all courses
                        5.see Grade for a specific course
                        6.see report card
                        7.see student profile
                        8.update profile
                        9.cancel a course.
                        10.exit
                       
                       """);

            int answer;
            try {
                answer = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException  e) {
                System.out.println("incorrect input,enter valid number and try again.");
                scanner.nextLine();
                continue;
            }

            if(answer<1||answer>10){
                System.out.println("options range from 1-8, try again.");
                continue;
            }
            switch (answer) {
                case 1:
                    viewAvailableCourses();
                    break;
                case 2:
                    listEnrolledCourses(student.getId());
                    break;
                case 3:
                    enroll(student,Options.retCode("Enter the course code you wish to enroll in: "));
                    break;
                case 4:
                    seeAllGrades(student.getId());
                    break;
                case 6:
                    seeReportCard(student.getId());
                    break;
                case 7:
                   seeProfile(student.getId());
                    break;
                case 8:
                   updateProfile(student.getId());
                    break;
                case 9:
                    un_enroll(student.getId(),Options.retCode("Enter the course code you wish to withdraw : "));
                    break;
                case 10:
                    flag=false;
            }
        }

    }
    public void viewAvailableCourses() throws SQLException {
           CourseDao.getCourseDao(connection).viewCourses();
    }
    public void  listEnrolledCourses(String id) throws SQLException{
            MarklistDao.getMarklistDao(connection).showStudentEnrolledCourses(id);
    }
    public void enroll(User student,String code) throws SQLException{
        Course course= CourseDao.getCourseDao(connection).getCourseByCode(code);
        if(course==null){
            System.out.println("course not found.");
            return;
        }
       MarklistDao.getMarklistDao(connection).enrollStudentToCourse(student.getId(),code);
        System.out.println("you have successfully enrolled to "+course.getTitle()+"-"+course.getCode());
    }
    public  void  seeAllGrades(String st_id) throws SQLException{
         MarklistDao.getMarklistDao(connection).seeAllGrades(st_id);
    }
    public void seeReportCard(String st_id) throws  SQLException{
        if(CourseDao.getCourseDao(connection).getCourses().isEmpty()){
            System.out.println("you haven't enrolled to any course yet.");
            return;
        }
        seeAllGrades(st_id);
        System.out.printf("GPA=%.2f\n",calGpa(st_id));

    }

    public double calGpa(String st_id) throws SQLException{
        Map<Course,String> gradePerCourse=MarklistDao.getMarklistDao(connection).getGradePerCourse(st_id);
        double total=0.0;
        double totalChr=0;
        for(Course course:gradePerCourse.keySet()){
            switch (gradePerCourse.get(course)){
                case "A+":
                case "A":
                    total+=4.0*course.getCreditHr();
                    break;
                case  "B+" :
                case "B":
                    total+=3.0*course.getCreditHr();
                    break;
                case "C":
                    total+=2.0*course.getCreditHr();
                    break;
                case "D":
                    total+=1.0*course.getCreditHr();
                    break;
            }
            totalChr+=course.getCreditHr();
        }
        return total/totalChr;
    }
    public void seeProfile(String st_id) throws SQLException{
        User student=StudentDao.getStudentDao(connection).getStudent(st_id);
        if(student==null)
            return;
        System.out.println("******Student profile************");
        System.out.printf("Name- %S%nID- %s%nlist of enrolled courses:%n",student.getName(),student.getId());
        listEnrolledCourses(st_id);
    }
    public void updateProfile(String st_id) throws SQLException{
        User student=StudentDao.getStudentDao(connection).getStudent(st_id);
        if(student==null)
            return;
        System.out.print("enter full name: ");
        String newName=scanner.nextLine();
        StudentDao.getStudentDao(connection).updateStudentProfile(st_id,newName);

        System.out.println("you have successfully updated your profile to:\n ");
        seeProfile(st_id);
    }
    public void un_enroll(String st_id,String code) throws SQLException{
        MarklistDao.getMarklistDao(connection).removeStudentFromCourse(st_id,code);

    }




}
