import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Course {
    private final String c_name;
    private final String c_code;
    private final int creditHr;
    private  String ins_id;
    private transient final Scanner scanner = new Scanner(System.in);
    ArrayList<Student> students;

    public Course(String c_name, String c_code, int creditHr) {
        this.c_name = c_name;
        this.c_code = c_code;
        this.creditHr = creditHr;

        importChanges();
    }

    public void importChanges() {
        try {
            File file1 = new File("src/co_stu_list.ser");
            if (!file1.exists() || file1.length() == 0) {
                students = new ArrayList<>();
            } else {
                ObjectInputStream studentsEnrolledInCourse = new ObjectInputStream(new FileInputStream("src/co_stu_list.ser"));
                students = (ArrayList<Student>) studentsEnrolledInCourse.readObject();
                studentsEnrolledInCourse.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportChanges() {
        try {
            ObjectOutputStream listToFile = new ObjectOutputStream(new FileOutputStream("src/co_stu_list.ser"));
            listToFile.writeObject(students);
            listToFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInstructor(String ins_id) {

        this.ins_id=ins_id;
    }
    public  String getIns_id(){
        return  ins_id;
    }


    public Student searchById(String id) {
        importChanges();
        Student student = null;
        for (Student s : students) {
            if (s.getId().equals(id)) {
                student = s;
                break;
            }
        }
        return student;

    }


    public void ListStudents(){
        importChanges();
        if(this.students.isEmpty()){
            return;
        }
        for(Student student: this.students){
            System.out.println(student.getName()+"  ID- "+ student.getId());
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
