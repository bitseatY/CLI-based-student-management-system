import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkListDao {
    private final Connection connection;
    private  static MarkListDao marklistDao;
    private MarkListDao(Connection connection){
        this.connection=connection;
    }
    public static MarkListDao getMarklistDao(Connection connection){
           if(marklistDao==null)
              marklistDao= new MarkListDao(connection);
           return  marklistDao;
    }

    public void enrollStudentToCourse(String id,String code) throws SQLException {
        String query="insert into  markList (co_roll_num,st_roll_num) values (?,?) ";
        try (PreparedStatement ps=connection.prepareStatement(query)){
            int st_roll_num=StudentDao.getStudentDao(connection).getStudentRollNum(id);
            int co_roll_num=CourseDao.getCourseDao(connection).getCourseRollNum(code);
            ps.setInt(1,co_roll_num);
            ps.setInt(2,st_roll_num);
            if(co_roll_num!=0&&st_roll_num!=0)
                ps.executeUpdate();
        }

    }

    public void removeStudentFromCourse(String id,String code) throws SQLException {
          String query="delete from markList where co_roll_num=? and st_roll_num=?";
          try (PreparedStatement ps=connection.prepareStatement(query)){
              int st_roll_num=StudentDao.getStudentDao(connection).getStudentRollNum(id);
              int co_roll_num=CourseDao.getCourseDao(connection).getCourseRollNum(code);
              ps.setInt(1,co_roll_num);
              ps.setInt(2,st_roll_num);
              if(st_roll_num!=0&&co_roll_num!=0)
                  ps.executeUpdate();
          }

    }
    public void showStudentEnrolledCourses(String id) throws SQLException {
            List<Course> courses=getStudentEnrolledCourses(id);
            if(courses.isEmpty()){
                System.out.println("no courses found.");
                return;
            }
            System.out.println("you have been enrolled to the following courses.\n");

            for(Course course:courses) {
                System.out.printf("%s(%s) -%dHR ",
                        course.getTitle(), course.getCode(), course.getCreditHr());
            }
    }

    public List<User> getCourseEnrolledStudents(String code) throws SQLException {
        List<User> students =new ArrayList<>();
        String query="select st_roll_num from markList where co_roll_num=?";
        try (PreparedStatement ps=connection.prepareStatement(query)) {
            int c_id = CourseDao.getCourseDao(connection).getCourseRollNum(code);
            ps.setInt(1, c_id);
            if (c_id == 0)
                System.out.println("course not found,");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("students not found,");
            }
            while (rs.next()) {
                User student=StudentDao.getStudentDao(connection).getStudentByRollNum(rs.getInt("st_roll_num"));
                students.add(student);
            }
            return  students;
        }
    }
    public void gradeStudent(String id,String code,double  mark) throws SQLException {
        String query="insert into  markList values(?,?,?) ";
        try (PreparedStatement ps=connection.prepareStatement(query)){
            int st_roll_num=StudentDao.getStudentDao(connection).getStudentRollNum(id);
            int co_roll_num=CourseDao.getCourseDao(connection).getCourseRollNum(code);
            ps.setInt(1,co_roll_num);
            ps.setInt(2,st_roll_num);
            ps.setDouble(3,mark);

            if(st_roll_num!=0&&co_roll_num!=0&&mark>0)
                ps.executeUpdate();
        }

    }








    public List<Course> getStudentEnrolledCourses(String id) throws SQLException {
        List<Course> courses =new ArrayList<>();
        String query="select co_roll_num   from markList where st_roll_num=?";
        try (PreparedStatement ps=connection.prepareStatement(query)) {
            int st_roll_num = StudentDao.getStudentDao(connection).getStudentRollNum(id);
            ps.setInt(1, st_roll_num);
            if (st_roll_num == 0)
                System.out.println("student not found,");
            ResultSet rs = ps.executeQuery();
            CourseDao courseDao=CourseDao.getCourseDao(connection);
            while (rs.next()) {
                Course course=courseDao.getCourseByRollNum(rs.getInt("co_roll_num"));
                courses.add(course);
            }
           return  courses;
        }
    }
    public Map<Course,String> getGradePerCourse(String st_id) throws SQLException {
        Map<Course,String> map=new HashMap<>();
        String query="select co_roll_num,grade  from markList where st_roll_num=?";
        try (PreparedStatement ps=connection.prepareStatement(query)) {
            int st_roll_num = StudentDao.getStudentDao(connection).getStudentRollNum(st_id);
            ps.setInt(1, st_roll_num);
            if (st_roll_num == 0) {
                System.out.println("student not found,");
                return  null;
            }
            ResultSet rs = ps.executeQuery();

            boolean flag=true;
            while (rs.next()) {
                Course course=CourseDao.getCourseDao(connection).getCourseByRollNum(rs.getInt("co_roll_num"));
                map.put(course,rs.getString("grade"));
                flag=false;

            }
            if(flag==true) {
                System.out.println("courses not found,");
            }
           return map;
        }

    }








    public void seeAllGrades(String stu_id) throws SQLException{
        String query="select co_roll_num,mark,grade from markList where st_roll_num=?";
        try (PreparedStatement ps=connection.prepareStatement(query)){
            int st_roll_num=StudentDao.getStudentDao(connection).getStudentRollNum(stu_id);
            ps.setInt(1,st_roll_num);
            if(st_roll_num==0) {
                System.out.println("student not found.\n");
                return;
            }
            ResultSet rs=ps.executeQuery();
            boolean flag=true;

            while (rs.next()) {
                Course course=CourseDao.getCourseDao(connection).getCourseByRollNum(rs.getInt("co_roll_num"));
                System.out.println(course.getTitle()+"("+course.getCode()+")" +"--"+rs.getInt("mark")+"---"+
                        rs.getString("grade"));
                flag=false;
            }
            if(flag==true)
                System.out.println("courses not found");

        }

    }








}
