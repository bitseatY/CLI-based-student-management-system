import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

interface  options{
    Scanner scanner=new Scanner(System.in);
    static String  retName(){
        System.out.print("enter full name : ");
        return scanner.nextLine();
    }
    static String retId(){
        System.out.println("enter id: ");
        return  scanner.nextLine();
    }
    static  Course retCourse(){
        System.out.print("enter the name of the course: ");
        String name=scanner.nextLine();
        System.out.print("enter the code for the course : ");
        String code=scanner.nextLine();
        System.out.print("Enter the credit hour in number: ");
        int cr= scanner.nextInt();
        scanner.nextLine();
        return  new Course(name,code,cr);

    }
    static  String retCode(){
         System.out.print("enter code for the course: ");
         return scanner.nextLine();
    }

    void menu();

}

public class Main {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
         boolean flag=true;
         while (flag){
           System.out.println("""
                   Welcome to our school portal.
                   are you a 1 student
                             2 instructor
                             3 manager
                   enter the number written before your occupation.
                   enter 4 to exit this portal.
                   """);
           int answer=scanner.nextInt();
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
              Course course= manager.searchCourseByCode(options.retCode());
              if(course==null){
                  System.out.println("course not found.");
                  return;
              }
              String id=options.retId();
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
             int answer=scanner.nextInt();
             scanner.nextLine();
             switch (answer){
                 case 1:
                      course.ListStudents();
                      break;
                 case 2:
                     instructor.seeStudentProfile(options.retId());
                     break;
                 case 3:
                     instructor.gradeStudent(options.retId());
                     break;
                 case 4:
                     instructor.removeStu(options.retId());
                     break;
                 case 5:
                       flag=false;

             }
        }
        }
    }

    class ManagerOptions implements  options{
          public void menu(){
            Manager manager=new Manager(options.retName(),options.retId());
            boolean flag=true;
            while (flag){
                System.out.println("""
                        what would you like to do?
                        1.add a course
                        2.remove a course
                        3.view available courses
                        4.see student profile
                        5.remove student from a course
                        6.assign instructor to a course
                        7.exit
                        
                        """);
                int answer=scanner.nextInt();
                scanner.nextLine();
                if(answer<1||answer>7){
                   continue;
                }
               switch (answer){
                   case 1:
                       Course course=options.retCourse();
                       manager.addCourse(course);
                       break;
                   case 2:
                       manager.deleteCourse(options.retCode());
                       break;
                   case 3:
                        manager.viewCourses();
                        break;
                   case 4:

                       manager.seeStuProfile(options.retId());
                       break;
                   case 5:
                       manager.removeStudent(options.retId(),options.retCode());
                       break;
                   case 6:
                       manager.assignInstructor(options.retCode());
                       break;
                   case 7:
                       flag=false;
               }
            }
          }
}

class StudentOptions implements options{
     public void menu(){
         boolean flag=true;
         Student student=new Student(options.retName(),options.retId());
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
               int answer=scanner.nextInt();
               scanner.nextLine();
               if(answer<1||answer>10)
                   continue;
               switch (answer) {
                   case 1:
                       student.viewAvailableCourses();
                       break;
                   case 2:
                        student.listEnrolledCourses();
                        break;
                   case 3:
                       student.enroll(options.retCode());
                       break;
                   case 4:
                       student.seeAllGrades();
                       break;
                   case 5:
                       student.seeGrade(options.retCode());
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
                       student.dis_enroll(options.retCode());
                       break;
                   case 10:
                          flag=false;
               }
           }

       }
}


class Student{
    private final Scanner scanner=new Scanner(System.in);
    private String name;
    private  String id;
    private final ArrayList<Course> courses;
    private final HashMap<Course,Grade> gradesForCoursesMap;


    public Student(String name,String id){
        this.name=name; this.id=id;
         courses=new ArrayList<>();
        gradesForCoursesMap=new HashMap<>();
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
        for(Course course:courses){
            System.out.printf("%s(%s) -----%s%n",course.getName(),course.getCode(),course.getInstructor().getName()
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

class Course{
    private final String c_name;
    private  final  String c_code;
    private final int creditHr;
    private  Instructor instructor;
    private  final Scanner scanner=new Scanner(System.in);
    ArrayList<Student> students=new ArrayList<>();

    public Course(String c_name,String c_code,int creditHr){
        this.c_name=c_name; this.c_code=c_code;this.creditHr=creditHr;
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
    public float getCreditHr() {
        return creditHr;
    }
}

class Manager{
    private  String M_name;
    private  String M_id;
    private static  final ArrayList<Course> coursesAvailable=new ArrayList<>();
    private final Scanner scanner=new Scanner(System.in);

    public Manager(String M_name,String M_id){
              this.M_id=M_id; this.M_name=M_name;
          }
    public Manager(){}

    public String getM_id() {
        return M_id;
    }

    public String getM_name() {
        return M_name;
    }

    public void addCourse(Course course){
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
        for(int i=0;i<coursesAvailable.size();i++){
            if(i%5==0){
                System.out.println();
            }
            System.out.print(coursesAvailable.get(i).getName()+"  ");
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
       if(coursesAvailable.isEmpty()){
           System.out.println("no courses are available right now.");
           return null;
        }
       Student student=null;
        Outer:
        for(Course course:coursesAvailable){
            for(Student s: course.students){
                if(s.getId().equals(id)){
                    student=s;break Outer;
                }
            }
        }
        return student;
    }
   public void assignInstructor(String code){
       Course course=searchCourseByCode(code);
        if(course==null){
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