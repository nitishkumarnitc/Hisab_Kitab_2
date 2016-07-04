package com.example.sameershekhar.hisab_kitab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import services.DataBaseHandler;
import services.ID;
import services.User;
import services.UserFunction;

public class Home extends Activity {

    DataBaseHandler dataBaseHandler;
    UserFunction userFunction=new UserFunction();
    Cursor userCursor;
    TextView name,email;
    ListView listView;

    Button logOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dataBaseHandler=new DataBaseHandler(Home.this);

         Log.d("Home", "i am in home");
        name=(TextView)findViewById(R.id.h_name);
        email=(TextView)findViewById(R.id.h_email);
        listView=(ListView)findViewById(R.id.h_listView);


        User user=dataBaseHandler.getUserdata();
        name.setText(user.fname+ " "+user.lname );
        email.setText(user.email);

        logOutButton=(Button)findViewById(R.id.h_logout);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHandler.resetUserTables();
                Intent mainIntent=new Intent(Home.this,MainActivity.class);
                Home.this.startActivity(mainIntent);

            }
        });


        new GetUsersContacts().execute();

    }

class GetUsersContacts extends AsyncTask<String,Void,JSONObject>
{
    JSONObject UsersContacts=new JSONObject();


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected void onPostExecute(JSONObject signjsonObject) {

        JSONArray jsonUserArray=null;
        DataBaseHandler dataBaseHandler=new DataBaseHandler(Home.this);


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
                    jsonUserArray=signjsonObject.getJSONArray("users");
                    int i=0;
                    int size=jsonUserArray.length();
                    while (i<size) {
                        JSONObject user =  jsonUserArray.getJSONObject(i);

                            boolean x=dataBaseHandler.insertUsers(user.getInt("id"),user.getString("fname"),
                            user.getString("lname"),user.getString("email_id"),
                            user.getString("mobile_no"),user.getString("sex"),user.getString("created_at"));
                            i++;
                    }

                }
                else if(successKey==0){
                    Log.d("Home:GetContactsAsync","No Contacts found");
                    return;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<User> users=dataBaseHandler.getUserContacts();

        ArrayList<String> usersFname=new ArrayList<String>();
        ArrayList<String> usersLname=new ArrayList<String>();
        int i=0;

        while(i<users.size())
        {
            usersFname.add(i,users.get(i).fname);
            usersLname.add(i,users.get(i).lname);
            i++;

        }

        ContactsAdapter adapter=new ContactsAdapter(Home.this,usersFname,usersLname);

        listView.setAdapter(adapter);


    }

    @Override
    protected JSONObject doInBackground(String... params) {

        UsersContacts=userFunction.getUserContacts();
        return UsersContacts ;
    }
}


    class ContactsAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> fname;
        ArrayList<String> lname;

        public ContactsAdapter(Context context, ArrayList<String> fnameArray, ArrayList<String> lnameArray) {
            super(context, R.layout.contacts, R.id.c_name, fnameArray);
            this.context = context;
            this.fname = fnameArray;
            this.lname = lnameArray;
        }


        class ViewHolder {
           ImageView imageView;
            TextView fname;
            TextView lname;
            TextView balance;

            ViewHolder(View view) {
                imageView = (ImageView)view.findViewById(R.id.contact_image);
                fname = (TextView) view.findViewById(R.id.c_name);
                balance= (TextView)view.findViewById(R.id.c_balance);
            }

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.contacts, parent, false);
               // row = View.inflate(context,R.layout.contacts,null);
                holder = new ViewHolder(row);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();

            }

            holder.fname.setText(fname.get(position) + lname.get(position));
            final ViewHolder finalHolder = holder;
            return row;


        }


    }

}
