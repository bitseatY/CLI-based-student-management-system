import java.util.Scanner;

public class InstructorOptions {
    private Scanner scanner=new Scanner(System.in);


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
