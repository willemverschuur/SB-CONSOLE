package com.skillzbookcollege;


import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import java.lang.reflect.*;



import com.skillzbookcollege.MODELS;
import com.skillzbookcollege.Model;

import org.json.JSONArray;


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

            MODELS.instance().updateDB();

        } catch (Exception e) {

            System.out.printf("Error occured " + e.getMessage());

        }

	}        

}
