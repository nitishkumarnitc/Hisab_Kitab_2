package com.example.sameershekhar.hisab_kitab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;

import services.DataBaseHandler;

public class Home extends AppCompatActivity {

    DataBaseHandler dataBaseHandler=new DataBaseHandler(this);
    HashMap<String,String> userDetailMap=new HashMap<>();
    TextView name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       name=(TextView)findViewById(R.id.h_name);
        email=(TextView)findViewById(R.id.h_email);
        userDetailMap=dataBaseHandler.getUserdata();
        String fname=userDetailMap.get("fname");
        String lname=userDetailMap.get("lname");
        String userName=fname+lname;
        name.setText(userName);

        email.setText(userDetailMap.get("email"));




    }


}
