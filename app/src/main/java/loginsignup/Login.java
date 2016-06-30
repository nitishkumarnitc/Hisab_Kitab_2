package loginsignup;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sameershekhar.hisab_kitab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.AsyncResponse;
import services.CheckNetwork;
import services.DataBaseHandler;
import services.UserFunction;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment implements AsyncResponse {

    UserFunction userFunction=new UserFunction();
    JSONObject loginjsonobject=new JSONObject();
    private View rootView=null;
    private Context context=null;

    private   String KEY_EMAIL = "email";
    private  String KEY_PASS = "password";

    EditText email,password;
    Button loginbtn;


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_login, container, false);
        context=rootView.getContext();
        email=(EditText)rootView.findViewById(R.id.l_email);
        password=(EditText)rootView.findViewById(R.id.l_password);
        loginbtn=(Button)rootView.findViewById(R.id.l_button);
        return rootView;
    }
    public void setLoginbtn(View view)
    {
        KEY_EMAIL=email.getText().toString();
        KEY_PASS=password.getText().toString();
        CheckNetwork checkNetwork=new CheckNetwork(context);
        checkNetwork.delegate=this;
        checkNetwork.execute();
    }

    JSONArray jsonArray=new JSONArray();
    DataBaseHandler dataBaseHandler=new DataBaseHandler(context);
    @Override
    public void processFinish(boolean output) {
        if(output)
        {
            loginjsonobject=userFunction.login(KEY_EMAIL,KEY_PASS);
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


                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
