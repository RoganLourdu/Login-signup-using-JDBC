import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class login {
    public static void login(Connection con,Scanner input) throws SQLException {
        PreparedStatement stmt=con.prepareStatement("select * from details  where pmail = ? and ppassword= ? ");
        //get mail from user
        System.out.print("\nPlease enter your mail: ");
        input.nextLine();
        stmt.setString(1,input.nextLine());
        //get password from user
        System.out.print("\nPlease enter your password: ");
        //encrypt the user password
        stmt.setString(2,encryption.encryption(input.nextLine()));
        ResultSet rs=stmt.executeQuery();
        while(rs.next()) {
            //print the values
            System.out.println("\nName: "+rs.getString(2)+"\nEmail: "+rs.getString(3)+"\nMobile: "+rs.getString(4));
        }
        rs=stmt.executeQuery();
        if(!rs.next()) {
            System.out.println("\nEmail id is notvalid or password incorrect\n Please Create account");
        }

    }
}
