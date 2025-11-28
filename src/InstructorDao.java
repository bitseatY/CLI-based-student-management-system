import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorDao {
    private Connection connection;
    private static InstructorDao instructorDao;
    private  InstructorDao(Connection connection){
        this.connection=connection;
    }
    public static  InstructorDao getInstructorDao(Connection connection){
        if(instructorDao==null)
            instructorDao=new InstructorDao(connection);
        return instructorDao;

    }


    public  void  add(String name,String i_id) throws SQLException{
        String query="insert into instructors (id,name) values(?,?)";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,i_id);
            ps.setString(2,name);
            ps.executeUpdate();

        }


    }
    public List<User> getInstructors() throws SQLException{
        List<User> instructors=new ArrayList<>();
        String query="select * from instructors";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ResultSet rs=ps.executeQuery();
            while (rs.next())
                instructors.add(new User(rs.getString("id"),rs.getString("name")));

        }
        return  instructors;
    }



    public int getInsId(String id) throws SQLException {
        String query="select roll_num from instructor where id=?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,id);
            ResultSet rs=ps.executeQuery();
            if (rs.next())
                return rs.getInt("roll_num");
           return 0;
        }

    }
    public  User getInsById(String id) throws SQLException {
        String query="select * from instructors where id=?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,id);
            ResultSet rs=ps.executeQuery();
            if (rs.next())
                return  new User(rs.getString("name"),rs.getString("id"));
            return null;
        }

    }




}
