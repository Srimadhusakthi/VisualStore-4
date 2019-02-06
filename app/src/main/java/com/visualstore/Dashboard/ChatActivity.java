package com.visualstore.Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.visualstore.BaseActivity;
import com.visualstore.JivoChat.JivoDelegate;
import com.visualstore.JivoChat.JivoSdk;
import com.visualstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity implements JivoDelegate {

    private Activity activity;

    @BindView(R.id.chat_toolbar)
    protected Toolbar mChat_toolbar;

    private JivoSdk jivoSdk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activity = ChatActivity.this;
        ButterKnife.bind(this);
        onOrderReviewToolbar();
        onJivoChatWeb();
    }


    private void onOrderReviewToolbar(){
        setSupportActionBar(mChat_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.chatwithus));
        mChat_toolbar.setTitleTextColor(Color.WHITE);
        mChat_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    private void onJivoChatWeb(){
        jivoSdk = new JivoSdk((WebView) findViewById(R.id.chat_webview));
        jivoSdk.delegate = this;
        jivoSdk.prepare();
    }


    @Override
    public void onEvent(String name, String data) {
        if(name.equals("url.click")){
            if(data.length() > 2){
                String url = data.substring(1, data.length() - 1);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        }
    }

}
