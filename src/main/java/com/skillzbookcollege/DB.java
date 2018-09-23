package com.skillzbookcollege;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DB {


    static DB _instance = null;
    private Connection _connection = null;


    static DB instance() throws Exception
    {
        if (DB._instance == null)
        {
            DB._instance = new DB();
        }

        return DB._instance;
    }


    public DB() throws Exception
    {
        this._connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skillzbookcollege", "postgres", "rabbit01");
        System.out.println("Opened database successfully");
    }


    public void execute(String sql) throws Exception
    {
        Statement statement = this._connection.createStatement();
        statement.execute(sql);
    }

}