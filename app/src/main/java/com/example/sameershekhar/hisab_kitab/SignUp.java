package com.example.sameershekhar.hisab_kitab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import services.DataBaseHandler;
import services.ID;
import services.UserFunction;
import services.Validator;

public class SignUp extends AppCompatActivity {

    UserFunction userFunction=new UserFunction();
    DataBaseHandler dataBaseHandler;
    private  String KEY_ID = "id";
    private  String KEY_FNAME = "fname";
    private   String KEY_LNAME = "lname";
    private   String KEY_EMAIL = "email";
    private  String KEY_PASS = "password";
    private   String KEY_MOBILE = "mobile";
    private String KEY_SEX="sex";

    private  final String KEY_DATE = "date";

    EditText firstName,lastName,email,password,mobile;
    Button register;
    RadioGroup radioGroup;
    Button login;


    JSONObject signjsonObject=new JSONObject();
    JSONArray jsonArray=new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dataBaseHandler=new DataBaseHandler(SignUp.this);
        firstName=(EditText)findViewById(R.id.r_fname);
        lastName=(EditText)findViewById(R.id.r_lname);
        email=(EditText)findViewById(R.id.r_email);
        password=(EditText)findViewById(R.id.r_pass);
        mobile=(EditText)findViewById(R.id.r_mobile);

        radioGroup= (RadioGroup)findViewById(R.id.r_sex);
        register= (Button)findViewById(R.id.r_signupbutton);
        login=(Button) findViewById(R.id.s_loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(SignUp.this,MainActivity.class);
                SignUp.this.startActivity(mainIntent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });
    }

    public void signUp(View view)
    {

        KEY_FNAME=firstName.getText().toString();
        KEY_LNAME=lastName.getText().toString();
        KEY_EMAIL=email.getText().toString();
        KEY_PASS=password.getText().toString();
        KEY_MOBILE=mobile.getText().toString();

        int id= radioGroup.getCheckedRadioButtonId();
        if(id==R.id.rd_female)
            KEY_SEX="female";
        else
            KEY_SEX="male";

        Log.d("Sign up ", "signUp Called");

        if (!Validator.isValidName(KEY_FNAME)) {
            firstName.setError("Invalid Fname");
        }
        else if (!Validator.isValidName(KEY_LNAME)) {
            lastName.setError("Invalid Lname");
        }
        else if (!Validator.isValidEmail(KEY_EMAIL)) {
            email.setError("Invalid Email");
        }
        else if (!Validator.isValidPassword(KEY_PASS)) {
            password.setError("Invalid Password");
        }
        else if (!Validator.isValidPhone(KEY_MOBILE)) {
            mobile.setError("Invalid Phone num");
        }
        else
        {

            new ProccessSignUp().execute();
        }
    }

    void startHome()
    {
        Log.d("StartHome","i am in starthome ");
        Intent intent=new Intent(SignUp.this,Home.class);
        SignUp.this.startActivity(intent);

    }

   private class ProccessSignUp extends AsyncTask<String,Void,JSONObject>

   {


       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           dataBaseHandler.resetUserTables();

       }

       @Override
       protected void onPostExecute(JSONObject signjsonObject) {
           //super.onPostExecute(jsonObject);
           JSONObject jsonObjectuser=null;

           if(signjsonObject==null){
               Log.d("Sign up ", signjsonObject +"is null");
           }

           try {

               String res=signjsonObject.getString("success");
               if(res!=null)
               {
                   int successKey=Integer.parseInt(res);
                   if(successKey==1)
                   {
                        jsonObjectuser=signjsonObject.getJSONObject("user");
                       boolean x=dataBaseHandler.insertUsers(jsonObjectuser.getInt("id"),jsonObjectuser.getString("fname"),
                               jsonObjectuser.getString("lname"),jsonObjectuser.getString("email_id"),
                               jsonObjectuser.getString("mobile_no"),jsonObjectuser.getString("sex"),jsonObjectuser.getString("created_at"));

                       ID.CURRENTUSERID=jsonObjectuser.getInt("id");

                       if(x)
                           Log.d("Signup1","success");
                       else
                           Log.d("Signup1","fail");

                   }

               }
           } catch (JSONException e) {
               e.printStackTrace();
           }


           startHome();

       }

       @Override
       protected JSONObject doInBackground(String... params) {

           Log.d("Sign up ", "Calling user function sign up");
           signjsonObject=userFunction.signup(KEY_FNAME, KEY_LNAME, KEY_SEX, KEY_EMAIL, KEY_PASS, KEY_MOBILE);

           return signjsonObject;
       }
   }




}
