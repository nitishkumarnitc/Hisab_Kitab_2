package com.example.sameershekhar.hisab_kitab;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import loginsignup.Login;
import loginsignup.Signup;
import services.AndroidDatabaseManager;

public class MainActivity extends AppCompatActivity {

    Button loginButton,signButton,dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loginButton=(Button)findViewById(R.id.button_login);
        signButton=(Button)findViewById(R.id.button_signup);
        dbManager=(Button)findViewById(R.id.dataBaseManager);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Login bot= new Login();
                fragmentTransaction.add(R.id.fragment_container,bot,"ccc");
                fragmentTransaction.commit();
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Signup bot= new Signup();
                fragmentTransaction.add(R.id.fragment_container,bot,"ccc");
                fragmentTransaction.commit();
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


}
