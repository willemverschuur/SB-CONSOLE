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


public class ModelField extends JSONObject {    

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
