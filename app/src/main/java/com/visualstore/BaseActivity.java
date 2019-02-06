package com.visualstore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.visualstore.Sharedpreference.Sharedpreference;

public class BaseActivity extends AppCompatActivity {
    public Activity activity = BaseActivity.this;
    public static String mLensRefractiontxt = "both";
    public static String mAdditionLeft = "";
    public static String mAdditionRight = "";
    public static ProgressDialog progressDialog;
    public static String mPlaceOrder = "3";
    public static String mInCompleteOrder = "1";
    public static String mCompleteOrder = "2";
    public static  int selection = -1;
    public static  int mAdvance = -1;
    public static LinearLayout progressBar;
    public static String mShapeBevel = "SB";
    public static  int ShapeBevelAct = -1;




    public  static   void onSnackBar(View mView, String message){
        Snackbar snackbar = Snackbar.make(mView,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static void onToast(Activity activity,String mMessage){
        Toast.makeText(activity,mMessage,Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkConnected(Context context){
       /* ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet
            return true;
        } else if (
                connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            return false;
        }
        return false;
    */
        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

    }





    public static void onProgress(int view,Activity activity){
        progressBar = new LinearLayout(activity);
        progressBar = activity.findViewById(view);
        progressBar.setVisibility(View.VISIBLE);
    }
    public static void onHideProgress(int view,Activity activity){
        progressBar = new LinearLayout(activity);
        progressBar = activity.findViewById(view);
        progressBar.setVisibility(View.GONE);
    }
}
