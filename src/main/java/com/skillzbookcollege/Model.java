package com.skillzbookcollege;


import java.lang.instrument.Instrumentation;
import java.lang.instrument.*;

import java.lang.*;
import java.math.*;
import java.lang.Class;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONArray;


class ModelField extends JSONObject {    

    ModelField(String field, String type, String label) {

        this.put("field", field);
        this.put("type", type);
        this.put("label", label);
        this.put("flags", new JSONArray());

    }


    ModelField flag(String flag) {

        JSONArray flags = (JSONArray) this.get("flags");
        flags.put(flag);

        return this;

    }


    String sql() {

        String field = this.getString("field");
        String type = this.getString("type");
        String df = "";

        // set default value
        if (this.has("default"))
        {
            df = String.format("DEFAULT %s", this.get("default"));
        }

        // type transformations
        if (type == "password") type = "string";

        return String.format("%s %s %s", field, type, df);
    }

}


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
    

}


