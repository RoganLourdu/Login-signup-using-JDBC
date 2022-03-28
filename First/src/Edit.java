import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Edit {
    private static String[] checking(Connection con, Scanner input) throws SQLException {
        String[] ans = new String[2];
        PreparedStatement stmt = con.prepareStatement("select * from details  where pmail = ? and ppassword= ? ");
        input.nextLine();
        String pmail;
        boolean pmailcheck;
        do {
            System.out.print("\nPlease enter your mail: ");
            pmail = input.nextLine();
            pmailcheck = pmail.endsWith("@gmail.com") && pmail.length() > 10;
            if (pmailcheck == false) {
                System.out.println("\nThe user mail not valid try again \n1)Check that your mail ends-with @gmail.com (or) \n2)Mail can have characters in front of @gmail.com (or) \n3)Mail alphabets characters are in lowercase " );
            }
        }
        while (pmailcheck == false);
        System.out.println("\nYour mail " + pmail);
        stmt.setString(1, pmail);
        String password;
        boolean passwordcheck;
        do {
            System.out.print("\nPlease enter your password: ");
            password = input.nextLine();
            stmt.setString(2,encryption.encryption(password));
            passwordcheck = password.trim().isEmpty();
        }
        while (passwordcheck == true);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ans[0] = pmail;
            ans[1] = "true";
            return ans;
        }
        rs = stmt.executeQuery();
        if (rs.next() == false) {
            System.out.println("\nThe Mail or password is not correct");
            return ans;
        }
        return ans;
    }

    public static void Edit(Connection con, Scanner input) throws Exception {
        System.out.println("\nGive what do you want(in numbers):\n1)rename user \n2)rename mail\n3)edit phone number\n4)change password\n5)Exit");
        System.out.print("\nEnter the key : ");
        int value = input.nextInt();
        switch (value) {
            case 1:
                System.out.println("\nYou select to rename user ");
                renameuser(con, input);
                break;
            case 2:
                System.out.println("\nYou select rename user mail");
                renamemail(con, input);
                break;
            case 3:
                System.out.println("\nYou select to edit phone number");
            case 5:
                System.out.println("\nexit");
                break;
            default:
                System.out.println("\nEnter right key");
                Edit(con, input);
        }
    }
    private static void renameuser(Connection con, Scanner input) throws Exception {
        System.out.println("\nFor edit details you want to login");
        String[] array = checking(con, input);
        renameuserexcecute(con,input,array);
    }
    public static void renameuserexcecute (Connection con,Scanner input,String[] array) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("update details set pname=? where pname=? and pmail=?");
        if (array[1] == "true") {
            stmt.setString(3, array[0]);
            System.out.print("\nenter the old user name : ");
            stmt.setString(2, input.nextLine());
            System.out.print("\nenter the new user name : ");
            String newpname = input.nextLine();
            if (newpname.trim().isEmpty() == false) {
                stmt.setString(1, newpname);
                stmt.executeUpdate();
                System.out.println("\nThe user name is edited");
                System.out.print("\nIf you want to check enter 1 :");
                int checkkey = input.nextInt();
                switch (checkkey) {
                    case 1:
                        System.out.println("\nYou select to check mail");
                        login.login(con, input);
                        break;
                    default:
                        System.out.println("");
                }
            } else {
                System.out.println("\nPlease give user name not as empty try again");
                renameuserexcecute(con, input, array);
            }
        }
        else{
            System.out.println("The mail not founded");
        }
    }
    private static void renamemail(Connection con, Scanner input) throws Exception {
        System.out.println("\nFor edit details you want to login");
        String[] array = checking(con, input);
        String oldpmail="", newpmail="";
        boolean newmailcheck,newmailisnew,newmailcharcheck;
       if(array[1]=="true") {
           System.out.print("\nEnter your old mail = "+array[0]+"\n");
           oldpmail =array[0];
           do {
               System.out.print("\nEnter the new mail : ");
               newpmail = input.nextLine();
               newmailcheck =  newpmail.endsWith("@gmail.com") && newpmail.length() > 10;
           }
           while (newmailcheck == false);
           newmailisnew=adddata.usermailisnew(con,newpmail);
           newmailcharcheck=adddata.mailcharcheck(newpmail);
           if(newmailisnew==true && newmailcharcheck==true) excecutemailedit(con, input, oldpmail, newpmail);
       }
       else{
           System.out.println("The mail not VALID");
       }
    }

    private static void excecutemailedit(Connection con, Scanner input, String oldpmail, String newpmail) throws SQLException {
        System.out.println("\nYour new mail "+newpmail);
        PreparedStatement stmt = con.prepareStatement("update details set pmail=? where pmail=?");
        stmt.setString(2, oldpmail);
        stmt.setString(1, newpmail);
        stmt.executeUpdate();
        System.out.println("\nEdited");
        System.out.println("\nThe mail name is edited");
        System.out.print("\nIf you want to check enter 1 else press any key :");
        int checkkey = input.nextInt();
        switch (checkkey) {
            case 1:
                System.out.println("\nYou select to check mail");
                login.login(con, input);
                break;
            default:
                System.out.println("");
        }
    }
}