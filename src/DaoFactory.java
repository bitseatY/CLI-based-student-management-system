import java.sql.Connection;

public class DaoFactory {
    private  Connection connection;
   public DaoFactory(Connection connection){
       this.connection=connection;
   }
    public MarklistDao getMarklistDao() {
        return MarklistDao.getMarklistDao(connection);
    }
    public  StudentDao getStudentDao(){
        return StudentDao.getStudentDao(connection);
    }
    public CourseDao getCourseDao(){
        return CourseDao.getCourseDao(connection);
    }
    public InstructorDao getInstructorDao(){
        return  InstructorDao.getInstructorDao(connection);
    }
}
