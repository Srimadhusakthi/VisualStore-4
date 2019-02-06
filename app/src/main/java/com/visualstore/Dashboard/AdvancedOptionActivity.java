package com.visualstore.Dashboard;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visualstore.Adapter.CustomOrderAdapter;
import com.visualstore.BaseActivity;
import com.visualstore.Model.LensOrderModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;
import com.visualstore.Validation.RangesDisplay1;
import com.visualstore.Validation.Validation1;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdvancedOptionActivity extends BaseActivity {

    private Activity activity;

    @BindView(R.id.advancedoption_toolbar)
    protected Toolbar mAdvancedoption_toolbar;

    /*INDIJUAL LENS DATA*/
    @BindView(R.id.pantoangle_lenstype)
    protected AppCompatEditText mPantoangle;

    @BindView(R.id.framefit_lenstype)
    protected AppCompatEditText mFramefit;

    @BindView(R.id.bowangle_lenstype)
    protected AppCompatEditText mBowangle;

    @BindView(R.id.distancenear_lenstype)
    protected AppCompatEditText mDistancenear;

    @BindView(R.id.neartype_lenstype)
    protected AppCompatEditText mNeartype;

    @BindView(R.id.mid_lenstype)
    protected AppCompatEditText mMid;

    @BindView(R.id.indivijuallengrav_lenstype)
    protected AppCompatEditText mIndivijuallengrav;

    /*Spinner*/
    @BindView(R.id.framedata_list)
    protected AppCompatSpinner mFramedata_list;

    private ArrayList<LensOrderModel> mLensOrderList;

    private CustomOrderAdapter mFrameAdapter;


    @BindView(R.id.framelength_right)
    protected AppCompatEditText mFramelength_right;

    @BindView(R.id.framelength_left)
    protected AppCompatEditText mFramelength_left;

    @BindView(R.id.frameheight_right)
    protected AppCompatEditText mFrameheight_right;

    @BindView(R.id.frameheight_left)
    protected AppCompatEditText mFrameheight_left;

    @BindView(R.id.dbl_right)
    protected AppCompatEditText mDbl_right;

    @BindView(R.id.dbl_left)
    protected AppCompatEditText mDbl_left;
    private String  mFrameType = "";
    private String mFrameid = "";

    /*centration data*/

    @BindView(R.id.pdz_right)
    protected AppCompatEditText mPdz_right;

    @BindView(R.id.pdz_left)
    protected AppCompatEditText mPdz_left;

    @BindView(R.id.yfh_right)
    protected AppCompatEditText mYfh_right;

    @BindView(R.id.yfh_left)
    protected AppCompatEditText mYfh_left;

    @BindView(R.id.bvd_right)
    protected AppCompatEditText mBvd_right;

    @BindView(R.id.bvd_left)
    protected AppCompatEditText mBvd_left;


    @BindView(R.id.indijual_valuessteps)
    protected TextView mIndijual_valuessteps;

    @BindView(R.id.indijual_error_txt)
    protected TextView mIndijual_error_txt;

    @BindView(R.id.framedata_valuessteps)
    protected TextView mFramedata_valuessteps;

    @BindView(R.id.framedata_error_txt)
    protected TextView mFramedata_error_txt;


    @BindView(R.id.centrationdata_valuessteps)
    protected TextView mCentrationdata_valuessteps;

    @BindView(R.id.centrationdata_error_txt)
    protected TextView mCentrationdata_error_txt;

    @BindView(R.id.advancedoption_parent_layout)
    protected RelativeLayout mAdvancedoption_parent_layout;
    private String side = BaseActivity.mLensRefractiontxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_option);
        ButterKnife.bind(this);
        activity = AdvancedOptionActivity.this;
        onAdvancedToollbar();
        onFrameType();
        onValidation();
        onIndividualValidaiation();
        if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.mOrderEdit, "").equals("editorder") || BaseActivity.mAdvance == 1) {
            onAdvanceOptionValue();
            onIndividualValidaiation();
        }else {
            onClearAdvanceOption();
        }
    }

    private void onIndividualValidaiation(){
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_individual,"").equals("0")){
            onHide(R.drawable.hide,false);
        }else{ onHide(R.drawable.rectangle,true);
        }
    }


    private void onAdvancedToollbar(){
        setSupportActionBar(mAdvancedoption_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.advancedoption));
        mAdvancedoption_toolbar.setTitleTextColor(Color.WHITE);
        mAdvancedoption_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    private void onAdvanceOptionValue(){
         if(BaseActivity.mLensRefractiontxt.equals("right")){
                onAdvanceValueRight();
        } else if(BaseActivity.mLensRefractiontxt.equals("left")){
              onAdavnceValuesLeft();
        } else{
               onAdvanceValueRight();
               onAdavnceValuesLeft();
         }

        onAdavnaceAdditionalValues();
    }

    private void onAdavnaceAdditionalValues(){
        mPantoangle.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.panto_angle, ""));
        mFramefit.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.framefit, ""));
        mBowangle.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.bow_angle, ""));
        mDistancenear.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.distance_near, ""));
        mNeartype.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.neartype, ""));
        mMid.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.mid, ""));
        mIndivijuallengrav.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.indiv_engrav, ""));
    }

    private void onAdavnceValuesLeft(){
        mFrameheight_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.frame_height_l, ""));
        mFramelength_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.frame_length_l, ""));
        mDbl_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.DBL_left, ""));
        mPdz_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.PDZ_left, ""));
        mYfh_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.YFH_left, ""));
        mBvd_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.bvd_left, ""));
    }

    private void onAdvanceValueRight(){
        mFrameheight_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.frame_height_r, ""));
        mFramelength_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.frame_length_r, ""));
        mDbl_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.DBL_right, ""));
        mPdz_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.PDZ_right, ""));
        mYfh_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.YFH_right, ""));
        mBvd_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.bvd_right, ""));

    }

    private void onFrameType(){
        mLensOrderList = new ArrayList<>();
        mLensOrderList.add(new LensOrderModel(getResources().getString(R.string.select) ,"0"));
        mLensOrderList.add(new LensOrderModel(getResources().getString(R.string.metal) +" " + getResources().getString(R.string.frame),"1"));
        mLensOrderList.add(new LensOrderModel(getResources().getString(R.string.plastic) +" " + getResources().getString(R.string.frame),"2"));
        mLensOrderList.add(new LensOrderModel(getResources().getString(R.string.nylor) ,"3"));
        mLensOrderList.add(new LensOrderModel(getResources().getString(R.string.rimless) ,"4"));
        mFrameAdapter = new CustomOrderAdapter(activity,R.layout.custom_lensorder_txt,mLensOrderList);
        mFramedata_list.setAdapter(mFrameAdapter);
         if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.frame_type,"").isEmpty()){
            int i = Integer.parseInt(Sharedpreference.getSharedprefernce(activity,Sharedpreference.frame_type_id,""));
            mFramedata_list.setSelection(i);
        }
        mFramedata_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFrameType = mLensOrderList.get(position).getmName();
                mFrameid = mLensOrderList.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick(R.id.advancedoption_confirm)
    protected void onConfirm(){
        if(BaseActivity.mLensRefractiontxt.equals("both")) {
            if (mFramelength_right.getText().toString().isEmpty()) {
                onSnackBar(mAdvancedoption_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.framelength));
            } else if (mFrameheight_right.getText().toString().isEmpty()) {
                onSnackBar(mAdvancedoption_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.frameheight));
            } else if (mFramelength_left.getText().toString().isEmpty()) {
                onSnackBar(mAdvancedoption_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.framelength));
            } else if (mFrameheight_left.getText().toString().isEmpty()) {
                onSnackBar(mAdvancedoption_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.frameheight));
            } else {
                onUploadValues();
            }
        } else if(BaseActivity.mLensRefractiontxt.equals("right")) {
            if (mFramelength_right.getText().toString().isEmpty()) {
                onSnackBar(mAdvancedoption_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.framelength));
            } else if (mFrameheight_right.getText().toString().isEmpty()) {
                onSnackBar(mAdvancedoption_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.frameheight));
            } else {
                onUploadValues();
            }
        } else {
            if (mFramelength_left.getText().toString().isEmpty()) {
                onSnackBar(mAdvancedoption_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.framelength));
            } else if (mFrameheight_left.getText().toString().isEmpty()) {
                onSnackBar(mAdvancedoption_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.frameheight));
            } else {
                onUploadValues();
            }
        }

    }

    @OnClick(R.id.advancedoption_cancel)
    protected void onCancel(){
        if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.mOrderEdit, "").equals("editorder") || BaseActivity.mAdvance == 1) {
            onAdvanceOptionValue();
        }else {
            onClearAdvanceOption();
        }

        activity.finish();

    }


    private void onHide(int drawable,boolean values){
        mFramefit.setBackgroundResource(drawable);
        mDistancenear.setBackgroundResource(drawable);
        mNeartype.setBackgroundResource(drawable);
        mMid.setBackgroundResource(drawable);
        mIndivijuallengrav.setBackgroundResource(drawable);
        mFramefit.setEnabled(values);
        mDistancenear.setEnabled(values);
        mNeartype.setEnabled(values);
        mMid.setEnabled(values);
        mIndivijuallengrav.setEnabled(values);
    }

    private void onUploadValues(){
        BaseActivity.mAdvance = 1;
        Sharedpreference.onStorePreferences(activity,Sharedpreference.panto_angle,mPantoangle.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.framefit,mFramefit.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.bow_angle,mBowangle.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.distance_near,mDistancenear.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.neartype,mNeartype.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mid,mMid.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.indiv_engrav,mIndivijuallengrav.getText().toString().trim());
        if(mFrameid.equals("0")){
            Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type, "");
            Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type_id, "");
        }else {
            Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type, mFrameType);
            Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type_id, mFrameid);
        }
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_length_r,mFramelength_right.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_length_l,mFramelength_left.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_height_r,mFrameheight_right.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_height_l,mFrameheight_left.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.DBL_right,mDbl_right.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.DBL_left,mDbl_left.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.PDZ_right,mPdz_right.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.PDZ_left,mPdz_left.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.YFH_right,mYfh_right.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.YFH_left,mYfh_left.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.bvd_right,mBvd_right.getText().toString().trim());
        Sharedpreference.onStorePreferences(activity,Sharedpreference.bvd_left,mBvd_left.getText().toString().trim());

        activity.finish();
    }


    private void onClearAdvanceOption(){
        Sharedpreference.onStorePreferences(activity,Sharedpreference.panto_angle,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.framefit,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.bow_angle,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.distance_near,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.neartype,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mid,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.indiv_engrav,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_type,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_type_id,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_length_r,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_length_l,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_height_r,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_height_l,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.DBL_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.DBL_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.PDZ_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.PDZ_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.YFH_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.YFH_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.bvd_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.bvd_left,"");
    }
    
    
    private void onValidation(){

        mPantoangle.setOnFocusChangeListener(new RangesDisplay1(activity,mIndijual_valuessteps, R.string.values_panto,mPantoangle,mPantoangle, -20.0,30.0,mIndijual_error_txt,R.string.panto_notinrange));
        mBowangle.setOnFocusChangeListener(new RangesDisplay1(activity,mIndijual_valuessteps, R.string.values_panto,mBowangle,mBowangle, -20.0,30.0,mIndijual_error_txt,R.string.bow_notinrange));

        mPantoangle.setOnEditorActionListener(new Validation1(activity,mPantoangle,mBowangle,mPantoangle,-20.0,30.0,mIndijual_error_txt,R.string.panto_notinrange));
        mBowangle.setOnEditorActionListener(new Validation1(activity,mBowangle,mFramelength_right,mBowangle,-20.0,30.0,mIndijual_error_txt,R.string.bow_notinrange));
       
        if(side.equals("right")) {
            
            /*Validation for only Right*/

            mFramelength_right.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_framelength,mFramelength_right,mFramelength_right, 30.0,80.0,mFramedata_error_txt,R.string.framelength_notinrange));
            mFrameheight_right.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_frameheight,mFrameheight_right,mFrameheight_right, 15.0,65.0,mFramedata_error_txt,R.string.frameHeight_notinrange));
            mDbl_right.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_dbl,mDbl_right,mDbl_right, 0.1,40.0,mFramedata_error_txt,R.string.dbl_notinrange));
            mPdz_right.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mPdz_right,mPdz_right, 0.0,50.0,mCentrationdata_error_txt,R.string.pdz_notinrange));
            mYfh_right.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mYfh_right,mYfh_right, 0.0,50.0,mCentrationdata_error_txt,R.string.yfh_notinrange));
            mBvd_right.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mBvd_right,mBvd_right, 0.0,50.0,mCentrationdata_error_txt,R.string.bvd_notinrange));


            mFramelength_right.setOnEditorActionListener(new Validation1(activity, mFramelength_right, mFrameheight_right, mFramelength_right, 30.0, 80.0,mFramedata_error_txt,R.string.framelength_notinrange));
            mFrameheight_right.setOnEditorActionListener(new Validation1(activity, mFrameheight_right, mDbl_right, mFrameheight_right, 15.0, 65.0,mFramedata_error_txt,R.string.frameHeight_notinrange));
            mDbl_right.setOnEditorActionListener(new Validation1(activity, mDbl_right, mPdz_right, mDbl_right, 0.1, 40.0,mFramedata_error_txt,R.string.dbl_notinrange));
            mPdz_right.setOnEditorActionListener(new Validation1(activity, mPdz_right, mYfh_right, mPdz_right, 0.0, 50.0,mCentrationdata_error_txt,R.string.pdz_notinrange));
            mYfh_right.setOnEditorActionListener(new Validation1(activity, mYfh_right, mBvd_right, mYfh_right, 0.0, 50.0,mCentrationdata_error_txt,R.string.yfh_notinrange));
            mBvd_right.setOnEditorActionListener(new Validation1(activity, mBvd_right, mBvd_right, mBvd_right, 0.0, 50.0,mCentrationdata_error_txt,R.string.bvd_notinrange));


            mFramelength_left.getText().clear();
            mFrameheight_left.getText().clear();
            mDbl_left.getText().clear();
            mPdz_left.getText().clear();
            mYfh_left.getText().clear();
            mBvd_left.getText().clear();


            mFramelength_left.setEnabled(false);
            mFramelength_left.setBackground(getResources().getDrawable(R.drawable.hide));
            mFrameheight_left.setEnabled(false);
            mFrameheight_left.setBackground(getResources().getDrawable(R.drawable.hide));
            mDbl_left.setEnabled(false);
            mDbl_left.setBackground(getResources().getDrawable(R.drawable.hide));
            mPdz_left.setEnabled(false);
            mPdz_left.setBackground(getResources().getDrawable(R.drawable.hide));
            mYfh_left.setEnabled(false);
            mYfh_left.setBackground(getResources().getDrawable(R.drawable.hide));
            mBvd_left.setEnabled(false);
            mBvd_left.setBackground(getResources().getDrawable(R.drawable.hide));
            mFramelength_left.setText("");
            mFrameheight_left.setText("");
            mDbl_left.setText("");
            mPdz_left.setText("");
            mYfh_left.setText("");
            mBvd_left.setText("");

//            centerLeft.setChecked(false);
//            centerLeft.setEnabled(false);
        }else if(side.equals("left")){


            /*Vaidation for only Left*/
            mFramelength_left.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_framelength,mFramelength_left,mFramelength_left, 30.0,80.0,mFramedata_error_txt,R.string.framelength_notinrange));
            mFrameheight_left.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_frameheight,mFrameheight_left,mFrameheight_left, 15.0,65.0,mFramedata_error_txt,R.string.frameHeight_notinrange));
            mDbl_left.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_dbl,mDbl_left,mDbl_left, 0.1,40.0,mFramedata_error_txt,R.string.dbl_notinrange));
            mPdz_left.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mPdz_left,mPdz_left, 0.0,50.0,mCentrationdata_error_txt,R.string.pdz_notinrange));
            mYfh_left.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mYfh_left,mYfh_left, 0.0,50.0,mCentrationdata_error_txt,R.string.yfh_notinrange));
            mBvd_left.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mBvd_left,mBvd_left, 0.0,50.0,mCentrationdata_error_txt,R.string.bvd_notinrange));

            mFramelength_left.setOnEditorActionListener(new Validation1(activity,mFrameheight_left,mDbl_left,mFrameheight_left,15.0,65.0,mFramedata_error_txt,R.string.frameHeight_notinrange));
            mDbl_left.setOnEditorActionListener(new Validation1(activity,mDbl_left,mPdz_left,mDbl_left,0.1,40.0,mFramedata_error_txt,R.string.dbl_notinrange));
            mPdz_left.setOnEditorActionListener(new Validation1(activity,mPdz_left,mYfh_left,mPdz_left,0.0,50.0,mFramedata_error_txt,R.string.pdz_notinrange));
            mYfh_left.setOnEditorActionListener(new Validation1(activity,mYfh_left,mBvd_left,mYfh_left,0.0,50.0,mFramedata_error_txt,R.string.yfh_notinrange));
            mBvd_left.setOnEditorActionListener(new Validation1(activity,mBvd_left,mBvd_left,mBvd_left,0.0,50.0,mFramedata_error_txt,R.string.bvd_notinrange));

            mFramelength_right.getText().clear();
            mFrameheight_right.getText().clear();
            mDbl_right.getText().clear();
            mPdz_right.getText().clear();
            mYfh_right.getText().clear();
            mBvd_right.getText().clear();

            mFramelength_right.setEnabled(false);
            mFramelength_right.setBackground(getResources().getDrawable(R.drawable.hide));
            mFrameheight_right.setEnabled(false);
            mFrameheight_right.setBackground(getResources().getDrawable(R.drawable.hide));
            mDbl_right.setEnabled(false);
            mDbl_right.setBackground(getResources().getDrawable(R.drawable.hide));
            mPdz_right.setEnabled(false);
            mPdz_right.setBackground(getResources().getDrawable(R.drawable.hide));
            mYfh_right.setEnabled(false);
            mYfh_right.setBackground(getResources().getDrawable(R.drawable.hide));
            mBvd_right.setEnabled(false);
            mBvd_right.setBackground(getResources().getDrawable(R.drawable.hide));
            mFramelength_right.setText("");
            mFrameheight_right.setText("");
            mDbl_right.setText("");
            mPdz_right.setText("");
            mYfh_right.setText("");
            mBvd_right.setText("");
//            centerRight.setChecked(false);
//            centerRight.setEnabled(false);
        }



        else {

            /*Validation for Both*/
            mFramelength_right.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_framelength,mFramelength_right,mFramelength_left, 30.0,80.0,mFramedata_error_txt,R.string.framelength_notinrange));
            mFrameheight_right.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_frameheight,mFrameheight_right,mFrameheight_left, 15.0,65.0,mFramedata_error_txt,R.string.frameHeight_notinrange));
            mDbl_right.setOnFocusChangeListener(new RangesDisplay1(activity,mFramedata_valuessteps, R.string.values_dbl,mDbl_right,mDbl_left, 0.1,40.0,mFramedata_error_txt,R.string.dbl_notinrange));
            mPdz_right.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mPdz_right,mPdz_left, 0.0,50.0,mCentrationdata_error_txt,R.string.pdz_notinrange));
            mYfh_right.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mYfh_right,mYfh_left, 0.0,50.0,mCentrationdata_error_txt,R.string.yfh_notinrange));
            mBvd_right.setOnFocusChangeListener(new RangesDisplay1(activity,mCentrationdata_valuessteps, R.string.values_pdz_yfh,mBvd_right,mBvd_left, 0.0,50.0,mCentrationdata_error_txt,R.string.bvd_notinrange));

            mFramelength_right.setOnEditorActionListener(new Validation1(activity, mFramelength_right, mFrameheight_right, mFramelength_left, 30.0, 80.0,mFramedata_error_txt,R.string.framelength_notinrange));
            mFrameheight_right.setOnEditorActionListener(new Validation1(activity, mFrameheight_right, mDbl_right, mFrameheight_left, 15.0, 65.0,mFramedata_error_txt,R.string.frameHeight_notinrange));
            mDbl_right.setOnEditorActionListener(new Validation1(activity, mDbl_right, mPdz_right, mDbl_left, 0.1, 40.0,mFramedata_error_txt,R.string.dbl_notinrange));
            mPdz_right.setOnEditorActionListener(new Validation1(activity, mPdz_right, mYfh_right, mPdz_left, 0.0, 50.0,mFramedata_error_txt,R.string.pdz_notinrange));
            mYfh_right.setOnEditorActionListener(new Validation1(activity, mYfh_right, mBvd_right, mYfh_left, 0.0, 50.0,mFramedata_error_txt,R.string.yfh_notinrange));
            mBvd_right.setOnEditorActionListener(new Validation1(activity, mBvd_right, mBvd_right, mBvd_left, 0.0, 50.0,mFramedata_error_txt,R.string.bvd_notinrange));
        }


    }
}
