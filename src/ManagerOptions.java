import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ManagerOptions {
    private Connection connection;
    private  StudentDao studentDao;
    private CourseDao courseDao;
    private MarklistDao marklistDao;
    private  InstructorDao instructorDao;
    private transient final Scanner scanner=new Scanner(System.in);


    public void menu(){

        Manager manager=new Manager();
        boolean flag=true;
        while (flag){
            System.out.println("""
                        what would you like to do?
                        1.add a course
                        2.remove a course
                        3.view available courses
                        4.see student profile
                        5.remove student from a course
                        6.register a student
                        7.assign instructor to a course
                        8.exit
                        
                        """);
            int answer= 0;
            try {
                answer = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("incorrect input,enter valid number and try again.");
                scanner.nextLine();
                continue;
            }

            if(answer<1||answer>8){
                System.out.println("options range from 1-8, try again.");
                continue;
            }
            switch (answer){
                case 1:
                    addCourse();
                    break;
                case 2:
                    removeCourse(options.retCode("Enter the course code you wish to delete: "));
                    break;
                case 3:
                    manager.viewCourses();
                    break;
                case 4:

                   seeStuProfile(options.retId("Enter the student id you wish to see:  "));
                    break;
                case 5:
                   removeStudent(options.retId("Enter the student id: "),options.retCode("Enter the course code: "));
                    break;

                case 6:
                    addStudent();
                    break;

                case 7:
                    manager.assignInstructor(options.retCode("Enter the course code: "));
                    break;
                case 8:
                    flag=false;
            }
        }
        manager.exportChangesToFile();
    }
    public void addCourse() throws SQLException {

        System.out.print("enter the title of the course you wish to add: ");
        String title=scanner.nextLine();
        System.out.print("enter the code for the course you wish to add : ");
        String code=scanner.nextLine();
        System.out.print("Enter the credit hour in number for the course you wish to addd: ");
        int cr_hr=Integer.parseInt(scanner.nextLine());
        if(courseDao.getCourseByCode(code)!=null){
            System.out.println("course already present.");
            return;
        }
        Course course=new Course(code,title,cr_hr);
        courseDao.addCourse(course);

        System.out.println(course.getName()+" -("+course.getCode()+") is successfully added to available courses.");

    }
    public void removeCourse(String code) throws SQLException{
        List<Course> courses=courseDao.getCourses();
        Course course=courseDao.getCourseByCode(code);
        if(courses.isEmpty()||course==null){
            System.out.println(" course not found.");
            return;
        }
        courseDao.remove(course);
        System.out.println(course.getName()+"("+course.getCode()+") is successfully removed.");

    }
    public void assignInstructor(String code) throws SQLException{

        List<Instructor> instructors=instructorDao.getInstructors();
        Course course=courseDao.getCourseByCode(code);
        if(course==null){
            System.out.println("course not found.");
            return;
        }
        System.out.print("enter the name of the instructor: ");
        String name=scanner.nextLine();
        System.out.print("enter the id of the instructor: ");
        String id=scanner.nextLine();

        Instructor instructor;
        for(Instructor i:instructors){
            if(i.getId().equals(id)){
                instructor=i;
                course.setInstructor(instructor.getId());
                System.out.println("Ir."+name+" is assigned to the course "+course.getName()+" ("+course.getCode()+")");
                return ;
            }
        }
        instructor=new Instructor(name,id);
        instructorDao.add(instructor);
        course.setInstructor(instructor.getId());
        System.out.println("Ir."+name+" is assigned to the course "+course.getName()+" ("+course.getCode()+")");
    }

    public void seeStuProfile(String id) throws SQLException{
        Student student=studentDao.getStudent(id);
        if(student==null){
            System.out.println("student not found.");
            return;
        }
        student.seeProfile();
    }
    public void removeStudent(String id,String code) throws SQLException{
        Course course=courseDao.getCourseByCode(code);
        Student student=studentDao.getStudent(id);
        if(course!=null&student!=null) {
            marklistDao.removeStudentFromCourse(id,code);
        }
        else{
            System.out.println("either course or student doesn't exist.");
        }

    }
    public void addStudent() throws SQLException {
        System.out.println("Enter name of student you wish to register: ");
        String name=scanner.nextLine();
        System.out.println("Enter id of student you wish to register : ");
        String ID=scanner.nextLine();
        studentDao.addStudent(ID,name);
    }



}