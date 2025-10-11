//This is a CLI based student management system.
// users are students, instructors and managers.
//This project uses object serialization for data persistence.
//Erase previous content from related files ,to start new.
//To use student options ,you have to add student using manager option first.
//To use instructor options ,you have to add a course and assign an instructor to the course using the manager option first.
//Use exit option when you're done to save changes to files.


import java.beans.Transient;
import java.io.*;
import java.util.*;

interface  options{
    Scanner scanner=new Scanner(System.in);
    static String  retName(String message){
        System.out.print(message);
        return scanner.nextLine();
    }
    static String retId(String message ){
        System.out.println(message);
        return  scanner.nextLine();
    }
    static  String retCode(String message){
         System.out.print(message);
         return scanner.nextLine();
    }


    void menu();

}

public class Main {
    public static void main(String[] args){

        Scanner scanner=new Scanner(System.in);

         boolean flag=true;
         while (flag) {
             System.out.println("""
                     Welcome to our school portal.
                     enter the number written before your occupation.
                     are you a 1 student
                               2 instructor
                               3 manager
                               4 exit this portal.
                     """);

             int answer= 0;
             try {
                 answer = scanner.nextInt();
                 if(answer>4||answer<1)
                 {
                     System.out.println("options range from 1 to 4 ,try again");
                     continue;
                 }
             } catch (InputMismatchException e) {
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
                           flag=false;
                 }

         }
        }
    }
    class InstructorOptions implements options{
        @Override
        public void menu() {
              Manager manager=new Manager();
              Course course= manager.searchCourseByCode(options.retCode("Enter code for the course you are teaching: "));
              if(course==null){
                  System.out.println("course not found.");
                  return;
              }
              String id=options.retId("Enter your id : ");
              Course.Instructor instructor=course.getInstructor();
              if(!instructor.getId().equals(id)){
                  System.out.println("you haven't been assigned to the course.");
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




                 int answer= 0;
                 try {
                     answer = scanner.nextInt();
                 } catch (InputMismatchException e) {
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
                      course.ListStudents();
                      break;
                 case 2:
                     instructor.seeStudentProfile(options.retId("Enter the student id: "));
                     break;
                 case 3:
                     instructor.gradeStudent(options.retId("Enter the student id : "));
                     break;
                 case 4:
                     instructor.removeStu(options.retId("Enter the student id: "));
                     break;
                 case 5:
                       flag=false;

             }
        }
             course.exportChanges();
        }
    }

    class ManagerOptions implements  options{
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
                    answer = scanner.nextInt();
                } catch (InputMismatchException e) {
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
                       manager.addCourse();
                       break;
                   case 2:
                       manager.deleteCourse(options.retCode("Enter the course code you wish to delete: "));
                       break;
                   case 3:
                        manager.viewCourses();
                        break;
                   case 4:

                       manager.seeStuProfile(options.retId("Enter the student id you wish to see:  "));
                       break;
                   case 5:
                       manager.removeStudent(options.retId("Enter the student id: "),options.retCode("Enter the course code: "));
                       break;

                   case 6:
                          manager.addStudent();
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
}

class StudentOptions implements options{
     public void menu(){
         Manager manager=new Manager();
         boolean flag=true;
         Student student=manager.searchStuById(options.retId("Enter your ID: "));
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


                 int answer= 0;
                 try {
                     answer = scanner.nextInt();
                 } catch (InputMismatchException e) {
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
                       student.viewAvailableCourses();
                       break;
                   case 2:
                        student.listEnrolledCourses();
                        break;
                   case 3:
                       student.enroll(options.retCode("Enter the course code you wish to enroll in: "));
                       break;
                   case 4:
                       student.seeAllGrades();
                       break;
                   case 5:
                       student.seeGrade(options.retCode("Enter the course code you wish to see grades : "));
                       break;
                   case 6:
                       student.seeReportCard();
                       break;
                   case 7:
                       student.seeProfile();
                       break;
                   case 8:
                       student.updateProfile();
                       break;
                   case 9:
                       student.dis_enroll(options.retCode("Enter the course code you wish to withdraw : "));
                       break;
                   case 10:
                          flag=false;
               }
           }
            student.exportChanges();
       }
}


class Student implements  Serializable{
      private transient final Scanner scanner=new Scanner(System.in);
    private String name;
    private  String id;
    private final ArrayList<Course> courses;
    private final HashMap<Course,Grade> gradesForCoursesMap;


    public Student(String name,String id){
        this.name=name; this.id=id;
        try {
            File file1=new File("src/enrolledCo.ser");
            File file2=new File("src/grades.ser");
            if(!file1.exists()||file1.length()==0){
                courses=new ArrayList<>();
            }else{
            ObjectInputStream enrolledCourses=new ObjectInputStream(new FileInputStream(file1));
            courses=(ArrayList<Course>) enrolledCourses.readObject();
            enrolledCourses.close();
            }
            if(!file2.exists()||file2.length()==0){
                gradesForCoursesMap=new HashMap<>();
            }else{
            ObjectInputStream grades=new ObjectInputStream(new FileInputStream(file2));
            gradesForCoursesMap=(HashMap<Course, Grade>) grades.readObject();
            grades.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //everytime after using student class we export changes to file
  public void  exportChanges(){
      try {
          ObjectOutputStream coursesToFile=new ObjectOutputStream(new FileOutputStream("src/enrolledCo.ser"));
          coursesToFile.close();
          ObjectOutputStream gradesToFile=new ObjectOutputStream(new FileOutputStream("src/grades.ser"));
          gradesToFile.close();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }


  }



    public ArrayList<Course> getCourses() {
        return courses;
    }

    static class Grade{
       private final double score;
       private  final   String grade;
        public Grade(double score){
            this.score=score;
            if(score>=90)
                grade="A+";
            else if(score>=80)
                grade="A";
            else if(score>=70)
                grade="B";
            else if(score>=60)
                grade="C";
            else
                grade="D";
        }

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<Course, Grade> getGradesForCoursesMap() {
        return gradesForCoursesMap;
    }
    public void viewAvailableCourses(){
        Manager manager=new Manager();
        manager.viewCourses();
    }

    public void enroll(String code){
        Manager manager=new Manager();
         Course course= manager.searchCourseByCode(code);
         if(course==null){
             System.out.println("course not found.");
             return;
         }
        course.students.add(this);
        courses.add(course);
        System.out.println("you have successfully enrolled to "+course.getName()+"-"+course.getCode());
    }
    public void listEnrolledCourses(){
        if(courses.isEmpty()){
            System.out.println("none");
            return;
        }
        System.out.println("you have been enrolled to the following courses.\n");
        for(Course course:courses){
            System.out.printf("%s(%s) -%dHR ",course.getName(),course.getCode()  ,course.getCreditHr()
                   );
        }
    }
    public  Course searchCourseByCode(String code){
        Course course=null;

        for(Course c:courses){
            if(c.getCode().equals(code)){
                course=c; break;
            }
        }
        return  course;
    }

    public void seeAllGrades(){
        if(courses.isEmpty()){
            System.out.println("you haven't enrolled to any course.");
            return;
        }
        if(gradesForCoursesMap.isEmpty()){
            System.out.println("grades haven't been assigned yet.");
            return;
        }
        for(Course course:gradesForCoursesMap.keySet()){
            System.out.println(course.getName()+"("+course.getCode()+")" +"--"+gradesForCoursesMap.get(course).score+"---"+
                    gradesForCoursesMap.get(course).grade);
        }
    }
    public void seeGrade(String code){
        Course course=searchCourseByCode(code);
        if(course==null){
            System.out.println("you are not enrolled to this course");
            return;}
       if(gradesForCoursesMap.containsKey(course)){
            System.out.println(course.getName()+"  "+gradesForCoursesMap.get(course).score+"("+gradesForCoursesMap.get(course).grade+")");
       }else{
           System.out.println("not assigned yet.");
       }

    }
    public void seeReportCard(){
        if(courses.isEmpty()){
            System.out.println("you haven't enrolled to any course yet.");
            return;
        }
          if(gradesForCoursesMap.size()!=courses.size()){
              System.out.println("report card not ready yet.");
              return;
          }
          seeAllGrades();
          System.out.printf("GPA=%.2f",calGpa());

    }
    public double calGpa(){
        double total=0.0;
        double totalChr=0;
        for(Course course:courses){
            switch(gradesForCoursesMap.get(course).grade){
                case "A+":
                case "A":
                    total+=4.0*course.getCreditHr();
                    break;
                case "B":
                    total+=3.0*course.getCreditHr();
                    break;
                case "C":
                    total+=2.0*course.getCreditHr();
                    break;
                case "D":
                    total+=1.0*course.getCreditHr();
                    break;
            }totalChr+=course.getCreditHr();
        }
       return total/totalChr;
    }
    public void seeProfile(){
        System.out.println("******Student profile************");
        System.out.printf("Name- %S%nID- %s%nlist of enrolled courses:%n",this.getName(),this.getId());
        listEnrolledCourses();
    }
    public void updateProfile(){
        System.out.print("enter full name: ");
        setName(scanner.nextLine());
        System.out.print("enter id: ");
        setId(scanner.nextLine());
        System.out.println("you have successfully updated your profile ");
    }
    public void dis_enroll(String code){

        Course course= searchCourseByCode(code);
        if(course==null){
            System.out.println("you weren't attending the course.");
            return;
        }

        courses.remove(course);
    }
}

class Course implements Serializable{
    private final String c_name;
    private  final  String c_code;
    private final int creditHr;
    private  Instructor instructor;
    private transient final Scanner scanner=new Scanner(System.in);
    ArrayList<Student> students;

    public Course(String c_name,String c_code,int creditHr){
        this.c_name=c_name; this.c_code=c_code;this.creditHr=creditHr;
        try {
            File file1=new File("src/co_stu_list.ser");
            if(!file1.exists()||file1.length()==0){
                students=new ArrayList<>();
            }else{
            ObjectInputStream  studentsEnrolledInCourse=new ObjectInputStream(new FileInputStream("co_stu_list.ser"));
            students=(ArrayList<Student>) studentsEnrolledInCourse.readObject();
            studentsEnrolledInCourse.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    //everytime after using student class export changes
    public void exportChanges(){
        try {
            ObjectOutputStream listToFile=new ObjectOutputStream(new FileOutputStream("src/co_stu_list.ser"));
            listToFile.writeObject(students);
            listToFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Student searchById(String id){
        Student student=null;
        for(Student s:students){
            if(s.getId().equals(id)){
                student=s;
                break;
            }}
        return  student;

    }

    class Instructor {
        private final  String i_Name;
        private final String i_id;

        public Instructor(String i_Name, String i_id) {
            this.i_id=i_id;
            this.i_Name=i_Name;
        }

        public String getName() {
            return i_Name;
        }

        public String getId() {
            return i_id;
        }

        public void gradeStudent(String id){
            Student student=searchById(id);
            if(student==null){
                System.out.println("student not found.");
                return;
            }
            System.out.println(student.getName()+"("+student.getId()+")");
            System.out.print("enter score for "+c_name+"("+c_code+")");
            double score=scanner.nextDouble();
            student.getGradesForCoursesMap().put(Course.this, new Student.Grade(score));

        }

        public void seeStudentProfile(String id){
            Student student=searchById(id);
            if(student==null){
                System.out.println("student not found.");
                return;
            }
            student.seeProfile();
        }
        //remove student from course
        public void removeStu(String id){
            Student student=searchById(id);
            if(student==null){
                System.out.println("student not found.");
                return;
            }
            students.remove(student);
        }
    }
        public void ListStudents(){
          if(this.students.isEmpty()){
            return;
        }
        for(Student student: this.students){
            System.out.println(student.getName()+"  "+ student.getId());
        }
    }
    public Instructor getInstructor() {
        return instructor;
    }
    public String getName() {
        return c_name;
    }
    public String getCode() {
        return c_code;
    }
    public int getCreditHr() {
        return creditHr;
    }
}

class Manager implements Serializable{

    private  final  ArrayList<Course> coursesAvailable;
    private ObjectOutputStream studentsToFile;

    private final ArrayList<Student> students;
    private ObjectOutputStream coursesToFile;
    private transient final Scanner scanner=new Scanner(System.in);


    public Manager(){
        try {
            File file1=new File("src/students.ser");
            File file2=new File("src/courses.ser");
            if(!file1.exists()||file1.length()==0){
                students=new ArrayList<>();

            }else {
                ObjectInputStream studentsFromFile = new ObjectInputStream(new FileInputStream(file1));
                students = (ArrayList<Student>) studentsFromFile.readObject();
                studentsFromFile.close();
            }
            if(!file2.exists()||file2.length()==0){
                coursesAvailable=new ArrayList<>();
            }else{
            ObjectInputStream coursesFromFile = new ObjectInputStream(new FileInputStream(file2));
            coursesAvailable=(ArrayList<Course>) coursesFromFile.readObject();
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

    public void addStudent(){
        System.out.println("Enter name of student: ");
        String name=scanner.nextLine();
        System.out.println("Enter id of student: ");
        String ID=scanner.nextLine();
        students.add(new Student(name,ID));

    }



    public void addCourse(){
        System.out.print("enter the name of the course: ");
        String name=scanner.nextLine();
        System.out.print("enter the code for the course : ");
        String code=scanner.nextLine();
        System.out.print("Enter the credit hour in number: ");
        int cr= scanner.nextInt();
        scanner.nextLine();
        Course course=new Course(name,code,cr);
        if(coursesAvailable.contains(course)){
            System.out.println("course already present.");
            return;
        }
        coursesAvailable.add(course);
        System.out.println(course.getName()+" ("+course.getCode()+") is successfully added to available courses.");
    }
    public void deleteCourse(String code){
        if(coursesAvailable.isEmpty()){
            System.out.println("no courses are added yet.");
            return;
        }
        Course course=searchCourseByCode(code);
        if(course==null){
            System.out.println("course was never present");
            return;
        }
        coursesAvailable.remove(course);
        System.out.println(course.getName()+"("+course.getCode()+") is successfully removed.");
    }
    public void viewCourses(){
        if(coursesAvailable.isEmpty()){
            System.out.println("no courses available right now.");
            return;
        }
        for(Course course:coursesAvailable){
            System.out.print(course.getName()+"("+course.getCode()+")...."+course.getCreditHr()+
         "  \n");
        }
        System.out.println();
    }
    public  Course searchCourseByCode(String code){
        if(coursesAvailable.isEmpty()){
            return null;
        }
        Course course=null;
        for(Course c:coursesAvailable){
            if(c.getCode().equals(code)){
                course=c; break;
            }
        }
        return  course;
    }





   public  Student searchStuById(String id){
       if(students.isEmpty()){
           System.out.println("Students are not registered yet.");
           return null;
        }
       Student student=null;
       for(Student s:students){
           if(s.getId().equals(id)){
               student=s; break;
           }
       }
        return student;
    }


   public void assignInstructor(String code){
       Course course=searchCourseByCode(code);
        if(course==null){
            System.out.println("course not found.");
            return;
        }
        System.out.print("enter the name of the instructor: ");
        String name=scanner.nextLine();
        System.out.print("enter the id of the instructor: ");
        String id=scanner.nextLine();
        course.setInstructor(course.new Instructor(name,id));
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
    }

}