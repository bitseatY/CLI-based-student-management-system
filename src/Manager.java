import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manager {
    private Connection connection;
    private  StudentDao studentDao;
    private CourseDao courseDao;
    private  InstructorDao instructorDao;
    private ArrayList<Course> coursesAvailable;
    private ObjectOutputStream studentsToFile;

    private  ArrayList<Student> students;
    private ObjectOutputStream coursesToFile;
    private transient final Scanner scanner=new Scanner(System.in);

    public Manager(){

        importChanges();
    }
    public void importChanges() {
        try {
            File file1 = new File("src/students.ser");
            File file2 = new File("src/courses.ser");
            if (!file1.exists() || file1.length() == 0) {
                students = new ArrayList<>();

            } else {
                ObjectInputStream studentsFromFile = new ObjectInputStream(new FileInputStream(file1));
                students = (ArrayList<Student>) studentsFromFile.readObject();
                studentsFromFile.close();
            }
            if (!file2.exists() || file2.length() == 0) {
                coursesAvailable = new ArrayList<>();
            } else {
                ObjectInputStream coursesFromFile = new ObjectInputStream(new FileInputStream(file2));
                coursesAvailable = (ArrayList<Course>) coursesFromFile.readObject();
                coursesFromFile.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }
    public void exportChangesToFile(){
        try {
            studentsToFile=new ObjectOutputStream(new FileOutputStream("src/students.ser"));
            studentsToFile.writeObject(students);
            studentsToFile.close();
            coursesToFile=new ObjectOutputStream(new FileOutputStream("src/courses.ser"));
            coursesToFile.writeObject(coursesAvailable);
            coursesToFile.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addStudent() throws SQLException {
        System.out.println("Enter name of student you wish to register: ");
        String name=scanner.nextLine();
        System.out.println("Enter id of student you wish to register : ");
        String ID=scanner.nextLine();
        studentDao.addStudent(ID,name);
    }

    public  boolean isCoursePresent (String code) throws SQLException{
        List<Course> courses=courseDao.getCourses();
        for(Course c:courses) {
            if (c.getCode().equals(code))
                return  true;
        }
        return  false;
    }
    public Course getCourse(String code) throws SQLException{
        List<Course> courses=courseDao.getCourses();
        Course course=null;
        for(Course c:courses) {
            if (c.getCode().equals(code)) {
                course = c;
                break;
            }

        }
        return  course;
    }





    public void addCourse() throws SQLException{
        importChanges();
        System.out.print("enter the title of the course you wish to add: ");
        String title=scanner.nextLine();
        System.out.print("enter the code for the course you wish to add : ");
        String code=scanner.nextLine();
        System.out.print("Enter the credit hour in number for the course you wish to addd: ");
        int cr_hr=Integer.parseInt(scanner.nextLine());
        if(isCoursePresent(code)){
            System.out.println("course already present.");
            return;
        }
        Course course=new Course(code,title,cr_hr);
        courseDao.addCourse(course);

        System.out.println(course.getName()+" -("+course.getCode()+") is successfully added to available courses.");

    }

    public void removeCourse(String code) throws SQLException{
        List<Course> courses=courseDao.getCourses();
        if(courses.isEmpty()||!isCoursePresent(code)){
            System.out.println(" course not found.");
            return;
        }
        Course course=null;
        for(Course c:courses) {
              if (c.getCode().equals(code)) {
                  course =  c;
                  break;
              }
        }

        courseDao.remove(course);
        System.out.println(course.getName()+"("+course.getCode()+") is successfully removed.");

    }


    public void viewCourses() throws SQLException{
        List<Course> courses=courseDao.getCourses();
        if(courses.isEmpty()){
            System.out.println("no courses available right now.");
            return;
        }
        for(Course course:courses){
            System.out.print(course.getName()+"("+course.getCode()+")...."+course.getCreditHr()+
                    " HR\n");
        }
        System.out.println();
    }


    public void assignInstructor(String code) throws SQLException{

        List<Instructor> instructors=instructorDao.getInstructors();
        if(!isCoursePresent(code)){
            System.out.println("course not found.");
            return;
        }
        Course course=getCourse(code);
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


    public void seeStuProfile(String id){
        Student student=searchStuById(id);
        if(student==null){
            System.out.println("student not found."); return;
        }
        student.seeProfile();
    }
    public void removeStudent(String id,String code){
        Course course=searchCourseByCode(code);
        Student student=searchStuById(id);
        if(course!=null&student!=null) {
            course.getInstructor().removeStu(student.getName());
            student.getCourses().remove(course);
            student.getGradesForCoursesMap().remove(course);
        }
        else{
            System.out.println("either course or student doesn't exist.");
        }
        exportChangesToFile();
    }


}
