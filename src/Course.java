
import java.util.Scanner;

public class Course {
    private final String c_name;
    private final String c_code;
    private final int creditHr;
    private  String ins_id;
    private transient final Scanner scanner = new Scanner(System.in);

    public Course(String c_name, String c_code, int creditHr) {
        this.c_name = c_name;
        this.c_code = c_code;
        this.creditHr = creditHr;
    }
    public void setInstructor(String ins_id) {
        this.ins_id=ins_id;
    }
    public  String getIns_id(){
        return  ins_id;
    }
    public String getName() {
        return c_name;
    }
    public String getCode() {
        return c_code;
    }
    public int getCreditHr() {
        return creditHr;
    }
}
