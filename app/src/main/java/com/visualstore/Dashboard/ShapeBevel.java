package com.visualstore.Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.visualstore.Adapter.ShapeBevelAdapter;
import com.visualstore.BaseActivity;
import com.visualstore.Model.ShapeBevelModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShapeBevel extends BaseActivity {

    @BindView(R.id.shapebevel_toolbar)
    protected Toolbar mShapeToolbar;

    @BindView(R.id.shapebevel_list)
    protected RecyclerView mShapeBevelList;

    private Activity activity;

    private ArrayList<ShapeBevelModel> mShapelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_bevel);
        ButterKnife.bind(this);
        activity = ShapeBevel.this;
        onToolbar();
        onRecyclerList();


    }

    @Override
    protected void onResume() {
        super.onResume();
        onShapeBevelValues();
        if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.Shape_bevel_id,"").isEmpty()){
            int value =  Integer.parseInt(Sharedpreference.getSharedprefernce(activity,Sharedpreference.Shape_bevel_id,""));
            BaseActivity.selection = value -1;
        }else{
           BaseActivity.selection = -1;
        }
    }

    private void onRecyclerList(){
        RecyclerView.LayoutManager manager = new GridLayoutManager(activity,2);
        mShapeBevelList.setLayoutManager(manager);
        mShapeBevelList.setItemAnimator(new DefaultItemAnimator());

    }

    private void onToolbar(){
        setSupportActionBar(mShapeToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mShapeToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(getResources().getString(R.string.shapebevel));
        mShapeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }


    private void onShapeBevelValues(){
        mShapelist = new ArrayList<>();
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_1,"1"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_2,"2"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_3,"3"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_4,"4"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_5,"5"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_6,"6"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_7,"7"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_8,"8"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_9,"9"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_10,"10"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_11,"11"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_12,"12"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_13,"13"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_14,"14"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_15,"15"));
        mShapelist.add(new ShapeBevelModel(R.drawable.lens_16,"16"));

        ShapeBevelAdapter adapter = new ShapeBevelAdapter(activity,mShapelist);
        mShapeBevelList.setAdapter(adapter);




    }


    @OnClick(R.id.shapeorderreview)
    protected  void onOrderReview(){
        BaseActivity.ShapeBevelAct = 2;
        startActivity(new Intent(activity,OrderReviewActivity.class));

        if(BaseActivity.mShapeBevel.equals("SB")){
            Sharedpreference.onStorePreferences(activity,Sharedpreference.Shape_bevel_id,"");
        }else{
        Sharedpreference.onStorePreferences(activity,Sharedpreference.Shape_bevel_id,BaseActivity.mShapeBevel);
        }
    }


}
