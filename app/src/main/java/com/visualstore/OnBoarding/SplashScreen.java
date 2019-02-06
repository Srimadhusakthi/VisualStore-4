package com.visualstore.OnBoarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.visualstore.BaseActivity;
import com.visualstore.Dashboard.MainActivity;
import com.visualstore.Dashboard.OrderReviewActivity;
import com.visualstore.Model.StoreId;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

public class SplashScreen extends BaseActivity {
    private int SPLASH_DISPLAY_LENGTH = 1000;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        activity = SplashScreen.this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.log_inauth,"").isEmpty()){
                    startActivity(new Intent(activity,LoginActivity.class));
                    activity.finish();
                }else{
                    StoreId.instances.setAuth_id(Sharedpreference.getSharedprefernce(activity,Sharedpreference.log_inauth,""));
                    startActivity(new Intent(activity,MainActivity.class));
                    activity.finish();
                }
//                startActivity(new Intent(activity,LoginActivity.class));
//                activity.finish();
            }
        },SPLASH_DISPLAY_LENGTH);
    }
}
