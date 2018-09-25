package com.skillzbookcollege;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DB {


    static Connection _instance = null;


    static Connection instance() throws Exception
    {
        if (DB._instance == null)
        {
            DB._instance = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skillzbookcollege", "postgres", "rabbit01");
        }

        return DB._instance;
    }

}