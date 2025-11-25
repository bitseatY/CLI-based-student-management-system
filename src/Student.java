import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Student {
    private transient final Scanner scanner=new Scanner(System.in);
    private String name;
    private  String id;
    private ArrayList<Course> courses;
    private HashMap<Course, Grade> gradesForCoursesMap;

    public Student(String name,String id){
        this.name=name; this.id=id;
        importChanges();
    }
    public void importChanges(){
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
    static class Grade implements Serializable{
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
        importChanges();
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
        course.exportChanges();
        courses.add(course);
        exportChanges();
        System.out.println("you have successfully enrolled to "+course.getName()+"-"+course.getCode());
    }
    public void listEnrolledCourses(){
        importChanges();
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
        importChanges();
        Course course=null;

        for(Course c:courses){
            if(c.getCode().equals(code)){
                course=c; break;
            }
        }
        return  course;
    }
    public void seeAllGrades(){
        importChanges();
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
        importChanges();
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
        importChanges();
        if(courses.isEmpty()){
            System.out.println("you haven't enrolled to any course yet.");
            return;
        }
        if(gradesForCoursesMap.size()!=courses.size()){
            System.out.println("report card not ready yet.");
            return;
        }
        seeAllGrades();
        System.out.printf("GPA=%.2f\n",calGpa());

    }
    public double calGpa(){
        importChanges();
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
        System.out.println("you have successfully updated your profile to:\n ");
        seeProfile();
    }
    public void un_enroll(String code){

        Course course= searchCourseByCode(code);
        if(course==null){
            System.out.println("you weren't attending the course.");
            return;
        }
        System.out.println("you have successfully  un enrolled from "+course.getName()+"-("+course.getCode()+")");
        courses.remove(course);
        exportChanges();
    }



}
