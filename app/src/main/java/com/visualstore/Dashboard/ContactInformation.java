package com.visualstore.Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactInformation extends AppCompatActivity {
    private Activity activity;

    @BindView(R.id.contactinfo_email)
    protected AppCompatTextView mContactinfo_email;

    @BindView(R.id.contactinfo_phone)
    protected AppCompatTextView mContactinfo_phone;

    @BindView(R.id.contactinfo_fax)
    protected AppCompatTextView mContactinfo_fax;

    @BindView(R.id.contactinformation_toolbar)
    protected Toolbar contactinformation_toolbar;

    @BindView(R.id.chatwithus_contactinfo)
    protected AppCompatButton mChatwithus_contactinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_information);
        activity = ContactInformation.this;
        ButterKnife.bind(this);
        onToolbar();
    }


    private void onToolbar(){
        setSupportActionBar(contactinformation_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        contactinformation_toolbar.setTitleTextColor(Color.WHITE);
        onTitle();
        contactinformation_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    private void onTitle(){
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.userinfo,"").equals("1")){
            getSupportActionBar().setTitle(activity.getResources().getString(R.string.contactinformation));
        }else{
            getSupportActionBar().setTitle(activity.getResources().getString(R.string.myaccount));
            mChatwithus_contactinfo.setVisibility(View.GONE);
        }

        mContactinfo_email.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.name,""));
        mContactinfo_phone.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.phone,""));
//        mContactinfo_fax.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.name,""));
    }

    @OnClick(R.id.chatwithus_contactinfo)
    protected void onChatWithUs(){
        startActivity(new Intent(activity,ChatActivity.class));
    }
}
