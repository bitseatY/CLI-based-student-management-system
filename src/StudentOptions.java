public class StudentOptions {
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
                    student.un_enroll(options.retCode("Enter the course code you wish to withdraw : "));
                    break;
                case 10:
                    flag=false;
            }
        }
        student.exportChanges();

    }



        }
