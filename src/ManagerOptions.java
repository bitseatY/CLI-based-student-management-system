public class ManagerOptions {
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
                    manager.addCourse();
                    break;
                case 2:
                    manager.removeCourse(options.retCode("Enter the course code you wish to delete: "));
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
