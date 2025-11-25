import java.io.Serializable;

public class Instructor {
      private final  String name;
      private final String id;

      public Instructor(String name, String id) {
            this.id=id;
            this.name=name;
      }

      public String getName() {
            return name;
      }

      public String getId() {
            return id;
      }
    public void gradeStudent(String id) {

        Student student = searchById(id);
        if (student == null) {
            System.out.println("student not found.");
            return;
        }
        System.out.println(student.getName() + "(ID-" + student.getId() + ")");
        System.out.print("enter score for " + c_name + "(" + c_code + ")");

        double score = Double.parseDouble(scanner.nextLine());
        student.getGradesForCoursesMap().put(Course.this, new Student.Grade(score));
        exportChanges();


    }
    public void seeStudentProfile(String id) {
        Student student = searchById(id);
        if (student == null) {
            System.out.println("student not found.");
            return;
        }
        student.seeProfile();
    }

    public void removeStu(String id) {
        Student student = searchById(id);
        if (student == null) {
            System.out.println("student not found.");
            return;
        }
        System.out.println("student " + student.getName() + " ID-" + student.getId() + "is successfully removed from list.");
        students.remove(student);
        exportChanges();
    }





}
