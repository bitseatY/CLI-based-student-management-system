import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private Connection connection;
    private static  StudentDao studentDao;
    private StudentDao(Connection connection){
        this.connection=connection;
    }
    public static   StudentDao getStudentDao(Connection connection){
         if(studentDao==null)
            studentDao=new StudentDao(connection);
         return  studentDao;
    }

    public  void  addStudent(String id,String name) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement("insert into student (s_id,name) values (?,?)")){
            ps.setString(1,id);
            ps.setString(2,name);
           ps.executeUpdate();
        }
    }
    public List<User> getStudents() throws SQLException{
        List<User> students=new ArrayList<>();
        try(PreparedStatement ps= connection.prepareStatement("select * from student")){
            ResultSet rs=  ps.executeQuery();

            while (rs.next()) {
                students.add(new User(rs.getString("s_id"), rs.getString("name")));

            }

        }
        return  students;
    }

    public User getStudent(String id) throws SQLException{

        User student=null;
        try(PreparedStatement ps= connection.prepareStatement("select * from student where s_id=?")){
            ps.setString(1,id);
            ResultSet rs=  ps.executeQuery();
             if (rs.next()) {
                  student=new User(rs.getString("name"),id);
            }

        }
        return student;
    }


    public  int getStudentId(String id) throws SQLException{
       String query="select id from student  where s_id=?";
       try(PreparedStatement ps= connection.prepareStatement(query)){
           ps.setString(1, id);
           ResultSet rs=ps.executeQuery();
           if(rs.next())
               return rs.getInt("id");
           return 0;
       }
    }
    public  User getStudentById(int id) throws SQLException{
        String query="select * from student  where id=?";
        try(PreparedStatement ps= connection.prepareStatement(query)){
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                return  new User(rs.getString("name"),rs.getString("s_id"));
            return null;
        }
    }






    public  void updateStudentProfile(String st_id,String name) throws SQLException{
        String query="update student set name=?  where id=? ";
        try(PreparedStatement ps= connection.prepareStatement(query)){
            ps.setString(1, name);
            ps.setString(2, st_id);
           ps.executeUpdate();

        }
    }







}
