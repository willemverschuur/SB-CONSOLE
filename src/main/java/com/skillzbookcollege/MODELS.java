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
import com.skillzbookcollege.Model;
import com.skillzbookcollege.DB;



class models_auth_track extends Model {

    models_auth_track() {

        super("auth_track", "Track", false);

        this.addField("_id", "uuid").flag("notnull");
        this.addField("_created", "timestamp").flag("notnull");

    }
} 


class models_auth_user extends Model {

    models_auth_user() {

        super("auth_user", "System User", true);

        this.addField("email", "text", "Email").flag("notnull");
        this.addField("password", "text", "Password").flag("notnull");
        this.addField("active", "bool", "Active").put("default", false);
        this.addField("name", "text", "Name").flag("notnull");
        this.addField("telno", "text", "Tel#").flag("notnull");
        this.addField("groups", "text", "Groups");
        this.addField("superuser", "bool", "Superuser").put("default", false);
    }
} 


class models_auth_group extends Model {

    models_auth_group() {

        super("auth_group", "System Group", true);

        this.addField("name", "text", "String").flag("notnull");
    }
} 


class models_auth_session extends Model {

    models_auth_session() {

        super("auth_session", "Auth Session", true);

        this.addField("login", "text", "Login");
        this.addField("token", "text", "token");

        this.addIndex("tokenindex", new JSONArray("['token']"));
    }
}


class models_auth_token extends Model {

    models_auth_token() {

        super("auth_token", "Auth Token", true);

        this.addField("email", "text", "Email");
        this.addField("process", "text", "Process");
        this.addField("user_id", "uuid", "Process");
        this.addField("token", "text", "token");

        this.addIndex("tokenindex", new JSONArray("['token']"));
    }
}


class models_cl_stimulus extends Model {

    models_cl_stimulus() {

        super("cl_stimulus", "Collection Stimulus", true);

        this.addField("title", "text", "Title");
        this.addField("content", "text", "Content"); // [chapter FF ][page FF][item FF] => [ type, questiontext, ... ]
    }
}


class models_cl_response extends Model {

    models_cl_response() {

        super("cl_response", "Collection Response", true);

        this.addField("stimulus_id", "uuid");
        this.addField("user_id", "uuid");
        this.addField("status", "text");
        this.addField("content", "text"); // [chapter][page][item] => [ response ]
    }
}


class models_dk_doc extends Model {

    models_dk_doc() {

        super("dk_doc", "Document", true);

        this.addField("name", "text");
        this.addField("mimetype", "text");
        this.addField("size", "bigint");
        this.addField("origin", "text");
    }
}


class models_email_que extends Model {

    models_email_que() {

        super("email_que", "Email", true);

        this.addField("mailto", "text");
        this.addField("mailcc", "text");
        this.addField("mailbcc", "text");
        this.addField("status", "text");
        this.addField("mailsubject", "text");
        this.addField("mailtitle", "text");
        this.addField("mailbody", "text");
    }
}


class models_pay_reference extends Model {

    models_pay_reference() {


        super("pay_reference", "Pay Integration Reference", true);

        this.addField("origin", "text");
        this.addField("amount", "money");
        this.addField("status", "text");
        this.addField("courses", "text");
        this.addField("user_id", "uuid");


    }
}


class MODELS extends JSONObject {    

    private static MODELS _instance = null;

    static public MODELS instance() {

        if (MODELS._instance == null)
        {
            MODELS._instance = new MODELS();

            new models_auth_track();
            new models_pay_reference();
            new models_auth_group();
            new models_auth_session();
            new models_auth_token();
            new models_auth_user();

            new models_dk_doc();
            new models_email_que();
            new models_cl_stimulus();
            new models_cl_response();

        }
        return MODELS._instance;
    }


    public void registerModel(Model model)
    {
        this.put(model.getString("table"), model);
    }


    public Model model(String modelname)
    {
        return (Model) super.get(modelname);
    }


    /* 
    public void updateDB() throws Exception
    {
        DB db = DB.instance();

        for (String modelname : this.keySet()) {

            Model model = this.getModel(modelname);
            String sql = model.sql();
    

            System.out.printf("\n\n-----------------------------\ncreate table %s%s\n%s%s", App.ANSI_BLUE, modelname, App.ANSI_WHITE, sql);
            db.execute(sql);
        }

        system.out.printf("\n\n\n");
    }*/

}

