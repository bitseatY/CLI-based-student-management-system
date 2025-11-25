import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    private Connection connection;
    public CourseDao(Connection connection){
        this.connection=connection;
    }

    public  void  addCourse(Course course) throws SQLException {
        String query="insert into course (code,title,credit_hr) values (?,?,?)";
        try(PreparedStatement ps= connection.prepareStatement(query)){
            ps.setString(1, course.getCode());
            ps.setString(2, course.getName());
            ps.setInt(3,course.getCreditHr());
            ps.executeUpdate();
        }
    }

    public List<Course> getCourses() throws SQLException{
        List<Course> courses=new ArrayList<>();
        String query="select * from course";
        try(PreparedStatement ps= connection.prepareStatement(query)){
            ResultSet rs= ps.executeQuery();
            while (rs.next())
                courses.add(new Course(rs.getString("code"),rs.getString("title"),rs.getInt("credit_hr")));

            return  courses;
        }
    }

    public  void  remove(Course course) throws SQLException{
        String query="delete from course where code=?";
        try (PreparedStatement ps= connection.prepareStatement(query)){
            ps.setString(1, course.getCode());
            ps.executeUpdate();
        }
    }
   public void  assignInstructor(Course course,Instructor instructor) throws SQLException{
       String query="update course set ins_id=? where code=? ";
       try (PreparedStatement ps= connection.prepareStatement(query)){
           ps.setString(1, instructor.getId());
           ps.setString(2, course.getCode());
           ps.executeUpdate();
       }





   }





}
