package services;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import loginsignup.Signup;



/**
 * Created by sameershekhar on 30-Jun-16.
 */
public class CheckNetwork extends AsyncTask<String,ProgressDialog,Boolean> {

    Context context=null;
    public AsyncResponse delegate=null;

    private ProgressDialog nDialog;
    private ProgressDialog progressDialog;


    public CheckNetwork(Context context)
    {
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        nDialog=new ProgressDialog(context);
        nDialog.setTitle("Checking Internet Connection");
        nDialog.setMessage("Loading Please Wait");
        nDialog.setCancelable(false);
         nDialog.setIndeterminate(false);
        nDialog.show();


    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        nDialog.dismiss();
        if(aBoolean)
        {
            delegate.processFinish(aBoolean);
        }

    }

    @Override
    protected Boolean doInBackground(String... params) {

        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null)
            {
                if(networkInfo.isConnected())
                    return networkInfo.isConnected();
            }
        }
        return false;
    }
}
