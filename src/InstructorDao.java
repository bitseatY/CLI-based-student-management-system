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


    public  void  add(Instructor instructor) throws SQLException{
        String query="insert into instructor (i_id,name) values(?,?)";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,instructor.getId());
            ps.setString(2,instructor.getName());
            ps.executeUpdate();

        }


    }
    public List<Instructor> getInstructors() throws SQLException{
        List<Instructor> instructors=new ArrayList<>();
        String query="select * from instructor";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ResultSet rs=ps.executeQuery();
            while (rs.next())
                instructors.add(new Instructor(rs.getString("i_id"),rs.getString("name")));

        }
        return  instructors;
    }



    public int getInsId(String id) throws SQLException {
        String query="select id from instructor where i_id=?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,id);
            ResultSet rs=ps.executeQuery();
            if (rs.next())
                return rs.getInt("id");
           return 0;
        }

    }
    public  Instructor getInsById(String i_id) throws SQLException {
        String query="select * from instructor where i_id=?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,i_id);
            ResultSet rs=ps.executeQuery();
            if (rs.next())
                return  new Instructor(rs.getString("name"),rs.getString("i_id"));
            return null;
        }

    }




}
