package com.visualstore.Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.visualstore.BaseActivity;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import butterknife.BindView;

public class Home extends BaseActivity {

    private Activity activity;
    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.home);
                    return true;
                case R.id.navigation_lensordering:
                    mTextMessage.setText(R.string.lensordering);
                    return true;
                case R.id.navigation_sentorder:
                    mTextMessage.setText(R.string.sentorder);
                    onSentOrder();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity = Home.this;
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    /*Click Event Actions*/

    private void onLensOrder() {

        startActivity(new Intent(activity, LensOrdering.class));
    }

    private void onSentOrder() {
        Sharedpreference.onStorePreferences(activity, Sharedpreference.mOrderType, "Sent Order");
        startActivity(new Intent(activity, OrderActivity.class));
    }

}
