import java.io.*;
import java.util.Scanner;

public class Student {
    private transient final Scanner scanner=new Scanner(System.in);
    private final String name;
    private final   String id;
    public Student(String name,String id){
        this.name=name; this.id=id;

    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
}
