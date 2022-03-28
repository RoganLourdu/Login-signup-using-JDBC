import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //create connection with database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Mail", "root", "");
            //calling menu method
            menu(con, input);
        } catch (Exception e) {
            //catch exception and prints stack trace
            e.printStackTrace();
        } finally {
            //close connection
            con.close();
            System.out.println("\nAll executed");
        }
    }
   public static void menu(Connection con,Scanner input) throws Exception {
        //ask what they want from user
     System.out.println("\nGive what do you want(in numbers):\n1)Create account\n2)log in\n3)Edit account\n4)Exit");
     System.out.print("\nEnter value here : ");
     int value=input.nextInt();
     switch (value){
         case 1:
             System.out.println("\nYou select to create account");
             //when user select to create account go to add data class
             adddata.adddata(con,input);
             break;
         case 2:
             System.out.println("\nYou select to log in");
             //when user select to log in go to log in class
             login.login(con,input);
             break;
         case 3:
             System.out.println("\nYou select to edit account");
             //when user want to edit some in account go to edit method
             Edit.Edit(con, input);
             break;
         case 4:
             //when user select exit
             System.out.println("\nexit");
             break;
         default:
             System.out.println("\nkey number not valid");
             //when user cannot give correct key calling again menu method
             menu(con, input);
     }

 }
}
