import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private Connection connection;
    public StudentDao(Connection connection){
        this.connection=connection;
    }

    public  void  addStudent(String id,String name) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement("insert into student (s_id,name) values (?,?)")){
            ps.setString(1,id);
            ps.setString(2,name);
           ps.executeUpdate();
        }
    }
    public List<Student> getStudents() throws SQLException{
        List<Student> students=new ArrayList<>();
        try(PreparedStatement ps= connection.prepareStatement("select * from student")){
            ResultSet rs=  ps.executeQuery();
            while (rs.next())
                students.add(new Student(rs.getString("s_id"),rs.getString("name")));

        }
        return  students;
    }






}
