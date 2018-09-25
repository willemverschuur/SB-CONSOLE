package com.skillzbookcollege;


import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import java.util.Date;
import java.util.UUID;
import java.sql.*;


import com.skillzbookcollege.*;


public class AuthUser
{

    static private AuthUser _instance = null;


    static public AuthUser instance()
    {
        if (AuthUser._instance == null)
        {
            AuthUser._instance = new AuthUser();
        }

        return AuthUser._instance;
    }


    public void insert(String email, String name, String password, Boolean active, Boolean superuser, String telno, String groups) throws Exception
    {
     
    }


    public ResultSet selectEmail(String email) throws Exception
    {
        Connection db = DB.instance();
        PreparedStatement selectuser = db.prepareStatement("SELECT * FROM auth_user WHERE email = ?");
        
        selectuser.setString(1, email);
        return selectuser.executeQuery();
    }


    public ResultSet selectID(String id) throws Exception
    {
        Connection db = DB.instance();
        PreparedStatement selectuser = db.prepareStatement("SELECT * FROM auth_user WHERE email = ?");
        
        selectuser.setObject(1, UUID.fromString(id));
        return selectuser.executeQuery();
    }

}