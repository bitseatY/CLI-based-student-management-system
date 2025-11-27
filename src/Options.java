import java.util.Scanner;

public interface Options {
    Scanner scanner=new Scanner(System.in);
    static String  retName(String message){
        System.out.print(message);
        return scanner.nextLine();
    }
    static String retId(String message ){
        System.out.println(message);
        return  scanner.nextLine();
    }
    static  String retCode(String message){
        System.out.print(message);
        return scanner.nextLine();
    }
    void menu();


}
