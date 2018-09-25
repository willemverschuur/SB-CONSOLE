package com.skillzbookcollege;


import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.google.common.hash.Hashing;

import javax.net.ssl.HttpsURLConnection;
import java.lang.reflect.*;



import com.skillzbookcollege.MODELS;
import com.skillzbookcollege.Model;
import com.skillzbookcollege.ModelField;


import org.json.JSONArray;
import org.json.JSONObject;


public class App 
{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main( String[] args )
    {

        try {

            //MODELS.instance().updateDB();

            ModelObject user = MODELS.instance().model("auth_user").insert();

            user.val("email", "will@edian.co.za");
            user.val("password", "asdf");
            user.commit();



            /*
            Connection db = DB.instance();
            PreparedStatement insertuser = db.prepareStatement("INSERT INTO auth_user (_id, _created, email, name, password, active, superuser, telno, groups) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    
            insertuser.setObject(1, UUID.randomUUID());
            insertuser.setTimestamp(2, new Timestamp(new Date().getTime()));
            insertuser.setString(3, email);
            insertuser.setString(4, name);
            insertuser.setString(5, Hashing.sha256().hashString(password, StandardCharsets.UTF_8) .toString());
            insertuser.setBoolean(6, true);
            insertuser.setBoolean(7, true);
            insertuser.setString(8, telno);
            insertuser.setString(9, groups);
            insertuser.execute();   
            */

            //System.out.println(user.toString());

        } catch (Exception e) {

            System.out.printf("Error occured " + e.getMessage());

        }

	}        

}
