package services;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.Settings.Global.getString;

/**
 * Created by sameershekhar on 28-Jun-16.
 */
public class UserFunction {
    private JSONParser jsonParser=new JSONParser();

   // private static String homeAddress="http://www.mistu.org/";
    private static String url="http://www.mistu.org/HisabKitab/";
    //private static String url="http://192.168.0.3/project/register.php"
    private static String login_tag="login";
    private static String register_tag="register";
    private static String forpass_tag="forpass";
    private static String chgpass_tag="chgpass";


    public UserFunction(){


    }

    public JSONObject login(String email,String pass)
    {
        JSONObject params=new JSONObject();
        try {
            params.put("tag",login_tag);
            params.put("email",email);
            params.put("password",pass);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  jsonParser.getJsonFromUrl(url, "POST",params);


    }
    public JSONObject signup(String fname,String lname,String sex,String email,String password,String mobile)
    {

        JSONObject params=new JSONObject();
        JSONObject result=new JSONObject();

        try {
            params.put("tag",register_tag);
            params.put("fname",fname);
            params.put("lname",lname);
            params.put("email",email);
            params.put("password",password);
            params.put("mobile",mobile);
            params.put("sex",sex);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("User Function ", " before calling getJsonfromUrl");
        result= jsonParser.getJsonFromUrl(url,"POST",params);
        if(result==null){
            Log.d("User Function", " getJson from Url returning  null");
        }
        return result;

    }

    public JSONObject forgetPassword(String email)
    {
        JSONObject params=new JSONObject();
        try {
            params.put("tag",forpass_tag);
            params.put("email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParser.getJsonFromUrl(url,"POST",params);
    }
    public JSONObject changePass(String email,String oldPass,String newPass)
    {
        JSONObject params=new JSONObject();
        try {
            params.put("tag",chgpass_tag);
            params.put("oldpass",oldPass);
            params.put("newpass",newPass);
            params.put("email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonParser.getJsonFromUrl(url,"POST",params);
    }



}
