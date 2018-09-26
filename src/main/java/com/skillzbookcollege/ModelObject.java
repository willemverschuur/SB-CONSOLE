package com.skillzbookcollege;

import com.skillzbookcollege.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;
import java.text.SimpleDateFormat;


public class ModelObject extends JSONObject {

    Model _model;
    Boolean _exists;
    ArrayList<String> _changes = new ArrayList();



    ModelObject(Model model) {

        this._exists = false;
        this._model = model;

        this.put("_id", UUID.randomUUID().toString());
        this.put("_created", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()));

    }


    ModelObject(Model model, ResultSet rc) {

        this._exists = true;
        this._model = model;
    }


    ModelObject val(String key, Object value) {

        if (!this._changes.contains(key)) this._changes.add(key);
        return (ModelObject) this.put(key, value);
    }


    void commit() throws Exception {

        Connection db = DB.instance();
        PreparedStatement statement;

        String tablename = "auth_user";
        String sql = null;
        ArrayList<String> fields = new ArrayList();
        ArrayList<String> qs = new ArrayList();
        ArrayList<Object> values = new ArrayList();


        if (!this._exists) {

            for (String field : JSONObject.getNames(this))
            {
                ModelField props = this._model.props(field);

                fields.add(field);
                qs.add("?");
                values.add(this.get(field));

            }

            sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tablename, String.join(", ", fields), String.join(", ", qs));
        }


        else if (this._exists) {

            ArrayList<String> set = new ArrayList();


            for (String field : this._changes)
            {
                ModelField props = this._model.props(field);
      
                fields.add(field);
                set.add(String.format("%s=?", field));
                values.add(this.get(field));

            }

            sql = String.format("UPDATE %s SET %s WHERE _id = ?", tablename, String.join(", ", set));            
            System.out.println(sql);

            
            fields.add("_id");
            values.add(this.get("_id"));
        }


        statement = db.prepareStatement(sql);
        System.out.printf("\n assemble values");
        

        int idx = 0;
        for (Object value : values)
        {
            String field = fields.get(idx);
            System.out.printf(" %s %s %s", tablename, field, value);

            ModelField props = this._model.props(field);
            String type = props.getString("type");



            if (type == "timestamp") {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                Date parsedDate = dateFormat.parse((String) value);

                statement.setTimestamp(idx+1, new java.sql.Timestamp(parsedDate.getTime()));

            }

            else if (type == "uuid") {

                statement.setObject(idx+1, value);
            }

            else{ 

                statement.setObject(idx+1, value);
            }

            idx++;
        }


        statement.execute();        
    }

}