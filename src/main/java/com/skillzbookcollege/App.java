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

            ModelObject user = MODELS.instance().model("auth_user").selectValue("email", "will@edian.co.za");
            if (user == null)
            {
                System.out.println("user found");
                user = MODELS.instance().model("auth_user").insert();
                user
                    .val("email", "will@edian.co.za")
                    .val("password", "asdf")
                    .val("superuser", true);
                    
                user.commit();
                System.out.println("user created");

                
            }
            else
            {
                System.out.println("\n\nthis is updating an existing object");
                user.val("groups", "[\"everything\"]");
                user.commit();
                System.out.println("user found");
                
            }

          
        } catch (Exception e) {

            System.out.printf("Error occured " + e.getMessage());

        }

	}        

}
