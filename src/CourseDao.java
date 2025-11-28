import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    private Connection connection;
    private static CourseDao courseDao;
    private CourseDao(Connection connection){
        this.connection=connection;
    }
    public static CourseDao getCourseDao(Connection connection){
         if(courseDao==null)
             courseDao=new CourseDao(connection);
         return courseDao;
    }



    public  void  addCourse(Course course) throws SQLException {
        String query="insert into courses (code,title,credit_hr) values (?,?,?)";
        try(PreparedStatement ps= connection.prepareStatement(query)){
            ps.setString(1, course.getCode());
            ps.setString(2, course.getTitle());
            ps.setInt(3,course.getCreditHr());
            ps.executeUpdate();
        }
    }
    public Course getCourseByCode(String code) throws SQLException{
        String query="select * from courses where code=?";
        try(PreparedStatement ps= connection.prepareStatement(query)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Course(rs.getString("title"), rs.getString("code"), rs.getInt("credit_hr"));
            else
                return null;
        }
    }
    public Course getCourseByRollNum(int roll_num) throws SQLException{
        String query="select * from courses where roll_num=?";
        try(PreparedStatement ps= connection.prepareStatement(query)) {
            ps.setInt(1, roll_num);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Course(rs.getString("title"), rs.getString("code"), rs.getInt("credit_hr"));
            else
                return null;
        }
    }




    public  int getCourseRollNum(String code) throws SQLException{
        String query="select *  from courses where code=?";
        try(PreparedStatement ps= connection.prepareStatement(query)){
            ps.setString(1, code);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                return rs.getInt("roll_num");
            return 0;
        }


    }

    public List<Course> getCourses() throws SQLException{
        List<Course> courses=new ArrayList<>();
        String query="select * from courses";
        try(PreparedStatement ps= connection.prepareStatement(query)){
            ResultSet rs= ps.executeQuery();
            while (rs.next())
                courses.add(new Course(rs.getString("code"),rs.getString("title"),rs.getInt("credit_hr")));

            return  courses;
        }
    }
    public void viewCourses() throws SQLException{
        String query="select * from courses ";
        try(PreparedStatement ps= connection.prepareStatement(query)){
            ResultSet rs=ps.executeQuery();
             boolean flag=false;
             while(rs.next()) {
                 System.out.print(rs.getString("title") + "(" + rs.getString("code") + ")...." +
                         rs.getInt("credit_hr") +
                         " HR\n");
                 flag=true;
             }
             if(flag==false)
                 System.out.println("no courses found");
             System.out.println();
        }
    }


    public  void  remove(Course course) throws SQLException{
        String query="delete from courses where code=?";
        try (PreparedStatement ps= connection.prepareStatement(query)){
            ps.setString(1, course.getCode());
            ps.executeUpdate();
        }
    }
   public void  assignInstructor(Course course,User instructor) throws SQLException {
       String query = "update course set ins_id=? where code=? ";
       try (PreparedStatement ps = connection.prepareStatement(query)) {
           ps.setString(1, instructor.getId());
           ps.setString(2, course.getCode());
           ps.executeUpdate();
       }
   }






}
