import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarklistDao {
    private Connection connection;
    public MarklistDao(Connection connection){

    }
    public void enrollStudentToCourse(String id,String code) throws SQLException {
        String query="insert into  marklist (c_id,s_id) values (?,?) ";
        try (PreparedStatement ps=connection.prepareStatement(query)){
            int st_id=new StudentDao(connection).getStudentId(id);
            int co_id=new CourseDao(connection).getCourseId(code);
            ps.setInt(1,co_id);
            ps.setInt(2,st_id);
            if(st_id!=0&&co_id!=0)
                ps.executeUpdate();
        }

    }

    public void removeStudentFromCourse(String id,String code) throws SQLException {
          String query="delete from marklist where c_id=? and s_id=?";
          try (PreparedStatement ps=connection.prepareStatement(query)){
              int st_id=new StudentDao(connection).getStudentId(id);
              int co_id=new CourseDao(connection).getCourseId(code);
              ps.setInt(1,co_id);
              ps.setInt(2,st_id);
              if(st_id!=0&&co_id!=0)
                  ps.executeUpdate();
          }

    }
    public void showStudentEnrolledCourses(String id) throws SQLException {
            List<Course> courses=getStudentEnrolledCourses(id);
            if(courses.isEmpty())
                return;
            System.out.println("you have been enrolled to the following courses.\n");
            CourseDao courseDao=new CourseDao(connection);
            for(Course course:courses) {
                System.out.printf("%s(%s) -%dHR ",
                        course.getName(), course.getCode(), course.getCreditHr());
            }
    }

    public List<Student> getCourseEnrolledStudents(String code) throws SQLException {
        List<Student> students =new ArrayList<>();
        String query="select s_id from marklist where c_id=?";
        try (PreparedStatement ps=connection.prepareStatement(query)) {
            int c_id = new CourseDao(connection).getCourseId(code);
            ps.setInt(1, c_id);
            if (c_id == 0)
                System.out.println("course not found,");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("students not found,");
            }
            CourseDao courseDao=new CourseDao(connection);
            while (rs.next()) {
                Student student=new StudentDao(connection).getStudentById(rs.getInt("s_id"));
                students.add(student);
            }
            return  students;
        }
    }
    public void gradeStudent(String s_id,String code,double  mark) throws SQLException {
        String query="insert into  marklist values(?,?,?) ";
        try (PreparedStatement ps=connection.prepareStatement(query)){
            int st_id=new StudentDao(connection).getStudentId(s_id);
            int co_id=new CourseDao(connection).getCourseId(code);
            ps.setInt(1,co_id);
            ps.setInt(2,st_id);
            ps.setDouble(3,mark);

            if(st_id!=0&&co_id!=0&&mark>0)
                ps.executeUpdate();
        }

    }








    public List<Course> getStudentEnrolledCourses(String id) throws SQLException {
        List<Course> courses =new ArrayList<>();
        String query="select c_id  from marklist where s_id=?";
        try (PreparedStatement ps=connection.prepareStatement(query)) {
            int s_id = new StudentDao(connection).getStudentId(id);
            ps.setInt(1, s_id);
            if (s_id == 0)
                System.out.println("student not found,");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("courses not found,");
            }
            CourseDao courseDao=new CourseDao(connection);
            while (rs.next()) {
                Course course=courseDao.getCourseById(rs.getInt("c_id"));
                courses.add(course);
            }
           return  courses;
        }
    }
    public Map<Course,String> getGradePerCourse(String st_id) throws SQLException {
        Map<Course,String> map=new HashMap<>();
        String query="select c_id,grade  from marklist where s_id=?";
        try (PreparedStatement ps=connection.prepareStatement(query)) {
            int s_id = new StudentDao(connection).getStudentId(st_id);
            ps.setInt(1, s_id);
            if (s_id == 0) {
                System.out.println("student not found,");
                return  null;
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("courses not found,");
                return null ;
            }
            CourseDao courseDao=new CourseDao(connection);
            while (rs.next()) {
                Course course=courseDao.getCourseById(rs.getInt("c_id"));
                map.put(course,rs.getString("grade"));
            }
            return  map;
        }
    }








    public void seeAllGrades(String stu_id) throws SQLException{
        String query="select c_id,mark,grade from marklist where s_id=?";
        try (PreparedStatement ps=connection.prepareStatement(query)){
            int st_id=new StudentDao(connection).getStudentId(stu_id);
            ps.setInt(1,st_id);
            if(st_id==0) {
                System.out.println("student not found.\n");
                return;
            }
            ResultSet rs=ps.executeQuery();
            if(!rs.next()){
                System.out.println("you haven't enrolled to any course.");
                return;
            }
            System.out.println("you have been enrolled to the following courses.\n");
            CourseDao courseDao=new CourseDao(connection);
            while (rs.next()) {
                Course course=courseDao.getCourseById(rs.getInt("c_id"));
                System.out.println(course.getName()+"("+course.getCode()+")" +"--"+rs.getInt("mark")+"---"+
                        rs.getString("grade"));
            }

        }

    }








}
