package models;

import props.Users;
import utils.DB;
import utils.Util;
import views.ChangePassword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class UserImpl implements IUsers{
    DB db = new DB();
    public static String userName = "";
    public static int uid = 0;
    public static String emailAddress = "";
    public static String userPassword = "";
    @Override
    public boolean usersLogin(String user_email, String password) {
        boolean status= false;
        try {
            String sql="select * from users where user_email=? and password=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,user_email);
            pre.setString(2, Util.MD5(password));
            ResultSet rs=pre.executeQuery();
            status=rs.next();
            if(status){
                userName = rs.getString("user_name")+" "+rs.getString("user_surname");
                uid =rs.getInt("uid");
            }
        }catch (Exception e){
            System.out.println("UserLogin error: "+e);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int usersUpdate(Users users) {
        int status=0;

        try {
            String sql="update users set user_name=?,  user_surname=?,  user_email=?,  password=? where uid=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1, users.getUser_name());
            pre.setString(2, users.getUser_surname());
            pre.setString(3,users.getUser_email());
            pre.setString(4, Util.MD5(users.getPassword()));
            pre.setInt(5, users.getUid());
            status= pre.executeUpdate();
        }catch (Exception e){
            System.err.println("usersUpdate Error: "+ e);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int usersInsert(Users users) {
        int status=0;
        try {
            String sql="insert into users values(null,?,?,?,?)";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,users.getUser_email());
            pre.setString(2,Util.MD5(users.getPassword()));
            pre.setString(3,users.getUser_name());
            pre.setString(4, users.getUser_surname() );
            status= pre.executeUpdate();
        }catch (Exception e){
            System.err.println("UsersInsert Error: "+ e);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int usersChangePassword(Users users) {
        int status=0;
        try {
            String sql="update users set password=? where user_email=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,Util.MD5(ChangePassword.rePassword));
            pre.setString(2,emailAddress);
            status=pre.executeUpdate();


        }catch (Exception e){
            System.out.println("userChangePassword Error: "+e);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int usersForgotPassword(Users users) {
        int status=0;
        try {
            String sql="update users set password=? where user_email=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,Util.MD5(userPassword));
            pre.setString(2,emailAddress);
            status=pre.executeUpdate();

        }catch (Exception e){
            System.out.println("userForgetPassword Error: "+e);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public boolean userGetEmail(String user_email) {
        boolean status=false;
        try {
            String sql="Select * from users where user_email=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,user_email);

            ResultSet rs=pre.executeQuery();
            status= rs.next();
            if(status){
                emailAddress=rs.getString("email");
            }

        }catch (Exception e){
            System.err.println("userGetEmail Error"+e);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public boolean userGetPassword(String password) {
        boolean status=false;
        try {
            String sql="Select * from users where password=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,password);

            ResultSet rs=pre.executeQuery();
            status= rs.next();
            if(status)
                userPassword=rs.getString("password");

        }catch (Exception e){
            System.err.println("userPassword Error"+e);
        }finally {
            db.close();
        }
        return status;
        }
}

