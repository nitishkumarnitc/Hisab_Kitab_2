package com.example.sameershekhar.hisab_kitab;

import android.content.Intent;
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
import services.UserFunction;

public class SignUp extends AppCompatActivity {


    UserFunction userFunction=new UserFunction();
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


    JSONObject signjsonObject=new JSONObject();
    JSONArray jsonArray=new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName=(EditText)findViewById(R.id.r_fname);
        lastName=(EditText)findViewById(R.id.r_lname);
        email=(EditText)findViewById(R.id.r_email);
        password=(EditText)findViewById(R.id.r_pass);
        mobile=(EditText)findViewById(R.id.r_mobile);

        radioGroup= (RadioGroup)findViewById(R.id.r_sex);
        register= (Button)findViewById(R.id.r_signupbutton);


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

        if (!isValidName(KEY_FNAME)) {
            firstName.setError("Invalid Fname");
        }
        else if (!isValidName(KEY_LNAME)) {
            lastName.setError("Invalid Lname");
        }
        else if (!isValidEmail(KEY_EMAIL)) {
            email.setError("Invalid Email");
        }
        else if (!isValidPassword(KEY_PASS)) {
            password.setError("Invalid Password");
        }
        else if (!isValidPhone(KEY_MOBILE)) {
            mobile.setError("Invalid Phone num");
        }
        else
        {
            /*CheckNetwork checkNetwork=new CheckNetwork(context);
            checkNetwork.delegate=this;
            checkNetwork.execute();*/
            DataBaseHandler dataBaseHandler=new DataBaseHandler(this);
            Log.d("Sign up ", "Calling user function sign up");
            signjsonObject=userFunction.signup(KEY_FNAME, KEY_LNAME, KEY_SEX, KEY_EMAIL, KEY_PASS, KEY_MOBILE);

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
                        JSONObject jsonObjectuser=signjsonObject.getJSONObject("user");
                       boolean x=dataBaseHandler.insertUsers(jsonObjectuser.getInt("id"),jsonObjectuser.getString("fname"),
                                jsonObjectuser.getString("lname"),jsonObjectuser.getString("email_id"),
                               jsonObjectuser.getString("mobile_no"),jsonObjectuser.getString("sex"),jsonObjectuser.getString("created_at"));
                        if(x)
                        Log.d("Signup1","success");
                        else
                            Log.d("Signup1","fail");
                        Intent intent=new Intent(SignUp.this,Home.class);
                        startActivity(intent);
                        Log.d("Signup2", "after inseting data2");

                        Log.d("Signup3", "after inseting data3");


                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //validating phonenum

    private static boolean isValidPhone(String phone) {
        String MobilePattern = "[0-9]{10}";
        Pattern pattern = Pattern.compile(MobilePattern);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }


    // validating name
    private static boolean isValidName(String name) {
        String roomPattern = "[A-Za-z]+";
        Pattern pattern = Pattern.compile(roomPattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    //validating hostel




    // validating password with retype password
    private static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 1) {
            return true;
        }
        return false;
    }


}