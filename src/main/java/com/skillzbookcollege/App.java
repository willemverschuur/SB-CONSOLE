package com.skillzbookcollege;


import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;



import com.skillzbookcollege.Model;


class models_auth_user extends Model {

    models_auth_user() {

        super("auth_user", "System User", true);

        this.addField("email", "text", "Email").flag("notnull");
        this.addField("password", "text", "Password").flag("notnull");
        this.addField("active", "bool", "Active").put("default", false);
        this.addField("name", "text", "String").flag("notnull");
        this.addField("telno", "text", "Tel#").flag("notnull");
    }

} 


public class App 
{

    static final String USER_AGENT = "Mozilla/5.0";

    public static void main( String[] args )
    {

        try {


            



        } catch (Exception e) {

            System.out.printf("Error occured " + e.getMessage());

        }

	}        

}
