package com.example.sameershekhar.hisab_kitab;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;


import services.AndroidDatabaseManager;
import services.DataBaseHandler;
import services.UserFunction;

public class MainActivity extends AppCompatActivity {

    Button loginButton,signButton,dbManager;

    UserFunction userFunction=new UserFunction();
    JSONObject loginjsonobject=new JSONObject();

    private Context context=null;

    private   String KEY_EMAIL = "email";
    private  String KEY_PASS = "password";

    EditText email,password;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loginButton=(Button)findViewById(R.id.l_button);
        signButton=(Button)findViewById(R.id.l_signbtn);
        email=(EditText)findViewById(R.id.l_email);
        password=(EditText)findViewById(R.id.l_password);
        dbManager=(Button)findViewById(R.id.dataBaseManager);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                logIn(v);

            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);

            }
        });

        dbManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbmanager = new Intent(getApplicationContext(),AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

    }

    void logIn(View v)
    {
        Log.d("Main","tag0");

        DataBaseHandler dataBaseHandler=new DataBaseHandler(this);
        KEY_EMAIL=email.getText().toString();
        KEY_PASS=password.getText().toString();
        Log.d("Main","tag1");

        loginjsonobject=userFunction.login(KEY_EMAIL, KEY_PASS);
        Log.d("Main", "tag2");
        try {
            String res=loginjsonobject.getString("success");
            if(res!=null)
            {
                int successKey=Integer.parseInt(res);
                if(successKey==1)
                {
                    JSONObject jsonObjectuser=loginjsonobject.getJSONObject("user");
                    dataBaseHandler.insertUsers(jsonObjectuser.getInt("id"), jsonObjectuser.getString("fname"),
                            jsonObjectuser.getString("lname"), jsonObjectuser.getString("email_id"),
                            jsonObjectuser.getString("mobile_no"), jsonObjectuser.getString("sex"), jsonObjectuser.getString("created_at"));
                    Intent intent=new Intent(MainActivity.this,Home.class);
                    MainActivity.this.startActivity(intent);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
