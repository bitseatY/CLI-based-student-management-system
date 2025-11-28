import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDao {
    private Connection connection;
    private  static ManagerDao managerDao;
    private ManagerDao(Connection connection){
        this.connection=connection;
    }
    public static ManagerDao getManagerDao(Connection connection){
        if(managerDao==null)
            managerDao=new ManagerDao(connection);
        return  managerDao;

    }


    public Manager getManagerById(String m_id) throws SQLException {
        String query="select * from manager where m_id=?";
        try(PreparedStatement ps= connection.prepareStatement(query)) {
            ps.setString(1, m_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Manager(rs.getString("name"), m_id);
            else
                return null;
        }
    }





}
