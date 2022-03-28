import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class adddata {
    public static void adddata(Connection con, Scanner input) throws SQLException {
        //calling username method
        username(con, input);
    }
    public static void username(Connection con,Scanner input) throws SQLException {
        input.nextLine();
        String pname="";
        boolean pnameboolean;
        //asking username from user
        do {
            System.out.print("\nEnter your user name :");
            pname= input.nextLine();
            //check that the username is empty
           pnameboolean=pname.trim().isEmpty();
           if (pnameboolean==true){
               System.out.println("\nThe user name is empty give user name again ");
           }
        }
        //when the user name is empty do loop running again
        while (pnameboolean==true);
        System.out.println("\nThe user name "+pname.trim());
        //calling the user mail method
            usermail(con,input,pname.trim());
    }
    public static void usermail(Connection con,Scanner input,String pname) throws SQLException {
        //get mail name from user
        System.out.print("\ngive user mail without space and ends with @gmail.com and all chars in lowercase or digits and dots are allowed:");
        String pmail=input.nextLine();
        //replacing space character to empty character
        pmail=pmail.replace(" ","");
        //checking that the mail name ends with @gmail.com and length grater than 10 and user mail is new
        boolean usermailisnewcheck=usermailisnew(con,pmail);
        boolean mailcharcheck=mailcharcheck(pmail);
        if(pmail.length()>10 && pmail.endsWith("@gmail.com") && usermailisnewcheck==true && mailcharcheck==true){
            System.out.println("\nYour mail "+pmail);
            //for check the mail is new call user mail is new method
            userpnumber(con,input,pname,pmail);
        }
        else {
            System.out.println("\nThe user mail not valid try again \n1)Check that your mail ends-with @gmail.com (or) \n2)Mail can have characters in front of @gmail.com (or) \n3)Mail alphabets characters are in lowercase " );
            //when the if statement is false call again  user mail method
            usermail(con, input, pname);
        }
    }
    public static boolean mailcharcheck(String pmail){
        //this method for chars are valid for mail
        //check characters start to end
        String ans="";
        for (int i = 0; i <pmail.length(); i++) {
            // Check if character is valid for mail name
            if ((int)pmail.charAt(i) >=48  && (int)pmail.charAt(i) <=59 || (int)pmail.charAt(i) >=97  && (int)pmail.charAt(i) <=122 || (int)pmail.charAt(i)==64 || (int)pmail.charAt(i)==46) {
                ans+="";
            }
            else {
                ans+="-";
            }
        }
        if(ans.trim().isEmpty()) return true;
        else return false;
    }
    public static boolean usermailisnew(Connection con,String pmail) throws SQLException{
        //this method for check the mail is new
        PreparedStatement stmtmail = con.prepareStatement("select * from details where pmail=?");
        stmtmail.setString(1,pmail);
        //checking the mail is new from execute the query
        ResultSet rs=stmtmail.executeQuery();
        //when user mail is already taken inform user that and call again user mail method
        while (rs.next()){
            //when the mail name is already taken return false
            System.out.println("\nThe mail name already taken please give another name to mail");
           return false;
        }
        rs=stmtmail.executeQuery();
        if(!rs.next()) {
            //when the new mail is new return true
            return true;
        }
        return false;
    }
    public static void userpnumber(Connection con,Scanner input,String pname,String pmail) throws SQLException{
        //get number from user
        System.out.print("\nGive your phone number here(phone number length 10) : ");
        String pnumber= input.nextLine();
        //check the mail is have only digits
        boolean pnumberisvalid=onlyDigits(pnumber,pnumber.length());
        //replace space to empty for check space character is present in the phone number
        String pnumberspace=pnumber.replace(" ","");
        //check any space is present in phone number
        boolean spaceisnotpresent=spacecheck(pnumber.length(),pnumberspace.length());
        //when the if condition is true call to ask password from user
        if (pnumber.length()==10 && pnumberisvalid==true && spaceisnotpresent==true){
            System.out.println("\nyour phone number "+pnumber);
            //calling user password method
            userpassword(con,input,pname,pmail,pnumber);
        }
        else{
            //for the phone number is not well to see so call again ask phone number
            System.out.println("\nThe phone number is not valid check that \n1)The phone number contains only digits. (or) \n2)The phone number not contains space. (or) \n3)The length of phone number not equal to 10. \nTry again");
            userpnumber(con, input, pname, pmail);
        }
    }
    public static boolean onlyDigits(String str, int n)
    {//this method for check phone number have only digits
        //check characters start to end
        String ans="";
        for (int i = 0; i < n; i++) {
            // Check if character is digit
            if ((int)str.charAt(i) >=48  && (int)str.charAt(i) <=59 ) {
               ans+="";
            }
            else {
                ans+="-";
            }
        }
        if(ans.trim().isEmpty()) return true;
        else return false;
    }
    public static void userpassword(Connection con,Scanner input,String pname,String pmail,String pnumber) throws  SQLException{
        //give instruction
        System.out.println("\nYour password want to have any one special character");
        //get password from user
        System.out.print("\nEnter the password here :");
        Console cons;
        String ppassword="";
        if((cons = System.console())!=(null)) {
            char[] pass = cons.readPassword("Password : ");
            ppassword = String.valueOf(pass);
        }else {
            ppassword = input.nextLine();
        }
        //replace space to empty character and find the length after remove space
        int passwordtrimlen = ppassword.replace(" ","").length();
        //check the space is present in the password
        boolean spaceisincheck= spacecheck(ppassword.length(),passwordtrimlen);
        //check that password has any special charactar
        boolean passwordcheck = anyspecialchar(ppassword);
        //when the if statement is true call add all datas method to add all datas
        if (ppassword.length()>=8 && ppassword.length() <=10 && passwordcheck==true && spaceisincheck==true){
           addalldatas(con,pname,pmail,pnumber,ppassword);
        }
        else{
            //the password is not valid so asking password again
            System.out.println("\nThe password is not valid check that \n1)The password contains any special charactar. (or) \n2)The password not contains space. (or) \n3)The length of password length <=8 & password length>=10. \nTry again");
            userpassword(con, input, pname, pmail, pnumber);
        }
    }
    public static boolean spacecheck(int wordlen,int wordtrimlen){
        //this method for check space is present in the string
        //in this method compare normal length and  space removed string length both length are same that return true else return false
        if(wordlen==wordtrimlen) return true;
        return false;
    }
    public static boolean anyspecialchar (String ppassword){
        //assign all special characters in a string
        String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
        for (int i=0; i < ppassword.length() ; i++)
        {
            char ch = ppassword.charAt(i);
            //when the password character is a special character return true
            if(specialCharactersString.contains(Character.toString(ch))) {
                return true;
            }
            //when character is last character of string return false
            else if(i == ppassword.length()-1)
                return false;
        }
        return false;
    }
    public static void addalldatas(Connection con,String pname,String pmail,String pnumber,String ppassword) throws SQLException{
        //this method for add all values in database
        PreparedStatement stmt = con.prepareStatement("insert into details (pname,pmail,pnumber,ppassword) values(?,?,?,?)");
        stmt.setString(1,pname);
        stmt.setString(2,pmail);
        stmt.setString(3,pnumber);
        //send password to encryption method and assigning the encrypted password
        stmt.setString(4,encryption.encryption(ppassword));
        stmt.executeUpdate();
    }
}
