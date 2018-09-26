package com.skillzbookcollege;


import java.lang.instrument.Instrumentation;
import java.lang.instrument.*;

import java.sql.*;
import java.lang.*;
import java.math.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.lang.Class;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONObject;
import org.json.JSONArray;


public class Model extends JSONObject {

    // Construct model from JSON
    public Model(String json) {

        super(json);

        MODELS.instance().registerModel(this);
    }


    // Construct empty Model
    public Model(String table, String title, boolean audited) {

        this.put("table", table);
        this.put("title", title);
        this.put("audited", audited);
        this.put("fields", new JSONObject());
        this.put("indexes", new JSONObject());

        // add audit fields for audited tables, these are populated automatically
        // and values are inserted into audit_row table
        this.addField("_id", "uuid", "ID");
        this.addField("_owner", "uuid", "Creator");
        this.addField("_created", "timestamp", "Created");
        this.addField("_track", "uuid", "Track");

        MODELS.instance().registerModel(this);
    }


    ModelField addField(String field, String type, String label) {

        ModelField props = new ModelField(field, type, label);
        JSONObject fields = (JSONObject) this.get("fields");
        fields.put(field, props);

        return props;
    }


    ModelField addField(String field, String type) {

        return this.addField(field, type, "");
    }


    void addIndex(String indexname, JSONArray fields) {

        JSONObject indexes = (JSONObject) this.get("indexes");
        indexes.put(indexname, fields);

        this.put("indexes", indexes);
    }


    String sql() {

        String table = this.getString("table");
        JSONObject fields = (JSONObject) this.get("fields");
        String[] fieldnames = JSONObject.getNames(fields);
        ArrayList<String> fieldsql = new ArrayList<String>();

        for (String fieldname : fieldnames)
        {
            ModelField props = (ModelField) fields.get(fieldname);
            fieldsql.add(props.sql());
        }

        return String.format("CREATE TABLE IF NOT EXISTS %s (%s)", table, String.join(",", fieldsql));
        
    }


    ModelField props(String field)
    {
        JSONObject fields = (JSONObject) this.get("fields");
        return (ModelField) fields.get(field);
    }


    ModelObject insert()
    {
        ModelObject o = new ModelObject(this);
        o.val("_id", UUID.randomUUID().toString());
        o.val("_created", new Timestamp(System.currentTimeMillis()).toString());

        return o;
    }


    ModelObject selectId(String id) throws Exception
    {
        String table = this.getString("table");
        String sql = String.format("SELECT * FROM %s WHERE _id = ?", table, id);

        Connection db = DB.instance();
        PreparedStatement statement = db.prepareStatement(sql);

        statement.setObject(1, UUID.fromString((id)));

        ResultSet rc = statement.executeQuery();
        if (!rc.next()) return null;

        ResultSetMetaData meta = rc.getMetaData();
        ModelObject o = new ModelObject(this, rc);

        for (int col = 1; col <= meta.getColumnCount(); col++) 
        {
            String label = meta.getColumnLabel(col);
            o.put(label, rc.getObject(col));
        }
        return o;
    }


    ModelObject selectValue(String field, String value) throws Exception
    {
        ModelField props = this.props(field);
        String type = props.getString("type");
        JSONObject fields = (JSONObject) this.get("fields");
        String table = this.getString("table");
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", table, field);
        System.out.println(sql);

        Connection db = DB.instance();
        PreparedStatement statement = db.prepareStatement(sql);


        if (type == "uuid")
        {

        }
        else
        {
            statement.setObject(1, value);
        }

        ResultSet rc = statement.executeQuery();
        if (!rc.next()) return null;

        ResultSetMetaData meta = rc.getMetaData();
        ModelObject o = new ModelObject(this, rc);

        for (int col = 1; col <= meta.getColumnCount(); col++) 
        {
            String label = meta.getColumnLabel(col);
            o.put(label, rc.getObject(col));
        }

        System.out.printf("done with selectValue %s", value);
        return o;
    }


}


