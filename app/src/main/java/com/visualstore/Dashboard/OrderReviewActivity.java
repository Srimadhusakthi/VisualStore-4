package com.visualstore.Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.visualstore.BaseActivity;
import com.visualstore.Model.PlaceOrderModel;
import com.visualstore.Model.ProductLensDataModel;
import com.visualstore.Model.Result;
import com.visualstore.R;
import com.visualstore.Retrfofit.Retro;
import com.visualstore.Retrfofit.RetroServices;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderReviewActivity extends BaseActivity {
    private Activity mActivity;

    @BindView(R.id.orderreview_toolbar)
    protected Toolbar mOrderreview_toolbar;

    @BindView(R.id.orderreview_orderreference)
    protected AppCompatTextView mOrderreference;

    @BindView(R.id.orderreview_consumername)
    protected AppCompatTextView mConsumername;

    @BindView(R.id.orderreview_consignee)
    protected AppCompatTextView mConsignee;

    @BindView(R.id.orderreview_employee)
    protected AppCompatTextView mEmployee;

    @BindView(R.id.orderreview_ordertype)
    protected AppCompatTextView mOrdertype;

    /*REFRACTIONS*/
    @BindView(R.id.refraction_right_sphere)
    protected AppCompatTextView mSphere_right;

    @BindView(R.id.refraction_right_cylinder)
    protected AppCompatTextView mCylinder_right;

    @BindView(R.id.refraction_right_axis)
    protected AppCompatTextView mAxis_right;

    @BindView(R.id.refraction_right_addition)
    protected AppCompatTextView mAddition_right;

    @BindView(R.id.refraction_left_sphere)
    protected AppCompatTextView mSphere_left;

    @BindView(R.id.refraction_left_cylinder)
    protected AppCompatTextView mCylinder_left;

    @BindView(R.id.refraction_left_axis)
    protected AppCompatTextView mAxis_left;

    @BindView(R.id.refraction_left_addition)
    protected AppCompatTextView mAddition_left;

    @BindView(R.id.refraction_right_resprism)
    protected AppCompatTextView mResprism_right;

    @BindView(R.id.refraction_left_resprism)
    protected AppCompatTextView mResprism_left;

    @BindView(R.id.refraction_right_resbase)
    protected AppCompatTextView mResbase_right;

    @BindView(R.id.refraction_left_resbase)
    protected AppCompatTextView mResbase_left;

    /*framedata*/

    @BindView(R.id.framedata_right_length)
    protected AppCompatTextView mLength_right;

    @BindView(R.id.framedata_left_length)
    protected AppCompatTextView mLength_left;

    @BindView(R.id.framedata_right_height)
    protected AppCompatTextView mHeight_right;

    @BindView(R.id.framedata_left_height)
    protected AppCompatTextView mHeight_left;

    @BindView(R.id.framedata_left_dbl)
    protected AppCompatTextView mDbl_left;

    @BindView(R.id.framedata_right_dbl)
    protected AppCompatTextView mDbl_right;

    @BindView(R.id.framedata_left_shapedata)
    protected AppCompatTextView mShapedata_left;

    @BindView(R.id.framedata_right_shapedata)
    protected AppCompatTextView mShapedata_right;

    @BindView(R.id.frame_type)
    protected AppCompatTextView mFrame_type;

    @BindView(R.id.shapebevel_id)
    protected AppCompatTextView mShape_id;


    /*centrationdata*/
    @BindView(R.id.centrationdata_right_pdz)
    protected AppCompatTextView mPdz_right;

    @BindView(R.id.centrationdata_left_pdz)
    protected AppCompatTextView mPdz_left;

    @BindView(R.id.centrationdata_right_yfh)
    protected AppCompatTextView mYfh_right;

    @BindView(R.id.centrationdata_left_yfh)
    protected AppCompatTextView mYfh_left;

    @BindView(R.id.centrationdata_right_bvd)
    protected AppCompatTextView mBvd_right;

    @BindView(R.id.centrationdata_left_bvd)
    protected AppCompatTextView mBvd_left;

    /*lenstype*/
    @BindView(R.id.lenstype_right_value)
    protected AppCompatTextView mLenstype_right;

    @BindView(R.id.lenstype_left_value)
    protected AppCompatTextView mLenstype_left;

    @BindView(R.id.lenstype_right_diamter)
    protected AppCompatTextView mDiamter_right;

    @BindView(R.id.lenstype_left_diamter)
    protected AppCompatTextView mDiamter_left;

    @BindView(R.id.lenstype_coating)
    protected AppCompatTextView mCoating;

    @BindView(R.id.lenstype_tint)
    protected AppCompatTextView mTint;

    private RetroServices mRetroservice;
    private String mSide = "";

    @BindView(R.id.orderreview_parentlayout)
    protected RelativeLayout orderreview_parentlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review);
        ButterKnife.bind(this);
        mActivity = OrderReviewActivity.this;
        onOrderReviewToolbar();
        onIntializeValue();

    }


    private void onOrderReviewToolbar(){
        setSupportActionBar(mOrderreview_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.orderreview));
        mOrderreview_toolbar.setTitleTextColor(Color.WHITE);
        mOrderreview_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        mRetroservice = Retro.get().createWithOutToken(RetroServices.class);
    }


    @OnClick(R.id.placeorder)
    protected void onPlaceorder(){
        if(BaseActivity.isNetworkConnected(activity) == true) {
            onPlaceOrdervalues(BaseActivity.mPlaceOrder);
        }else{
            onSnackBar(orderreview_parentlayout,getResources().getString(R.string.interntconnectionalert));
        }

        onProgress(R.id.custom_progressbar,activity);
    }


    @OnClick(R.id.storeascompleteorder)
    protected void onCompleteorder(){
        if(BaseActivity.isNetworkConnected(activity) == true) {
            onPlaceOrdervalues(BaseActivity.mCompleteOrder);
        }else{
            onSnackBar(orderreview_parentlayout,getResources().getString(R.string.interntconnectionalert));
        }

        onProgress(R.id.custom_progressbar,activity);
    }

    @OnClick(R.id.incompleteorders)
    protected void onInCompleteorder(){
        if(BaseActivity.isNetworkConnected(activity) == true) {
            onPlaceOrdervalues(BaseActivity.mInCompleteOrder);
        }else{
            onSnackBar(orderreview_parentlayout,getResources().getString(R.string.interntconnectionalert));
        }

        onProgress(R.id.custom_progressbar,activity);
    }


    private void onIntializeValue()
    {
        mOrderreference.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.orderreference,""));
        mConsumername.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.firstname,"") +"  " + Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lastname,""));
        mConsignee.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.consignee_list,""));
        mEmployee.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.employee_list,""));
        mOrdertype.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.order_type_name,""));


        mSphere_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.sphere_right,""));
        mCylinder_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.cyclind_right,""));
        mAxis_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.axis_right,""));
        mAddition_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.addition_right,""));

        mSphere_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.sphere_left,""));
        mCylinder_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.cyclind_left,""));
        mAxis_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.axis_left,""));
        mAddition_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.addition_left,""));

        /*frame Data*/

        mLength_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.frame_length_r,""));
        mHeight_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.frame_height_r,""));
        mDbl_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.DBL_right,""));


        mLength_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.frame_length_l,""));
        mHeight_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.frame_height_l,""));
        mDbl_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.DBL_left,""));

        mFrame_type.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.frame_type,""));
        if(BaseActivity.ShapeBevelAct == 1){
            mShape_id.setText("");
        }else{
            mShape_id.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.Shape_bevel_id,""));
        }

        mPdz_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.PDZ_right,""));
        mYfh_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.YFH_right,""));
        mBvd_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.bvd_right,""));


        mPdz_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.PDZ_left,""));
        mYfh_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.YFH_left,""));
        mBvd_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.bvd_left,""));

        mLenstype_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lens_typename_right,""));
        mDiamter_right.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lens_diameter_name_right,""));

        mLenstype_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lens_typename_left,""));
        mDiamter_left.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lens_diameter_name_left,""));


        mCoating.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lens_coatingname,""));
        mTint.setText(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lens_tintname,""));


    }

    private void onPlaceOrdervalues(String mOrderType){


        PlaceOrderModel placeOrderModel = new PlaceOrderModel();
        String mType[] ;
        if(BaseActivity.mLensRefractiontxt.equals("both")){
            mType = new String[] {"r","l"};
            mSide = "2";
            placeOrderModel.setFrm_width(Sharedpreference.getSharedprefernce(activity,Sharedpreference.frame_length_r,""));
            placeOrderModel.setFrm_height(Sharedpreference.getSharedprefernce(activity,Sharedpreference.frame_height_r,""));
            placeOrderModel.setFrm_dbl(Sharedpreference.getSharedprefernce(activity,Sharedpreference.DBL_right,""));
        }else if(BaseActivity.mLensRefractiontxt.equals("right")){
            mType = new String[] {"r"};
            mSide = "1";
            placeOrderModel.setFrm_width(Sharedpreference.getSharedprefernce(activity,Sharedpreference.frame_length_r,""));
            placeOrderModel.setFrm_height(Sharedpreference.getSharedprefernce(activity,Sharedpreference.frame_height_r,""));
            placeOrderModel.setFrm_dbl(Sharedpreference.getSharedprefernce(activity,Sharedpreference.DBL_right,""));
        }else{
            mType = new String[] {"l"};
            mSide = "1";
            placeOrderModel.setFrm_width(Sharedpreference.getSharedprefernce(activity,Sharedpreference.frame_length_l,""));
            placeOrderModel.setFrm_height(Sharedpreference.getSharedprefernce(activity,Sharedpreference.frame_height_l,""));
            placeOrderModel.setFrm_dbl(Sharedpreference.getSharedprefernce(activity,Sharedpreference.DBL_left,""));
        }

        placeOrderModel.setAdmin_id(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.admin_id,""));
        placeOrderModel.setOrder_type(mOrderType);
        placeOrderModel.setOrder_reference(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.orderreference,""));
        placeOrderModel.setPatient_term(Sharedpreference.getSharedprefernce(activity,Sharedpreference.personaldata,""));
        if(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.firstname,"").isEmpty()){
            placeOrderModel.setPatient_firstName("FirstName");
        }else{
            placeOrderModel.setPatient_firstName(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.firstname,""));
        }
        if(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lastname,"").isEmpty()){
            placeOrderModel.setPatient_lastName("LastName");
        }else{
            placeOrderModel.setPatient_lastName(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lastname,""));
        }
        placeOrderModel.setSide(mSide);
        placeOrderModel.setLens_lr_lens(""+Arrays.toString(mType));
        placeOrderModel.setLens_r_sphere(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.sphere_right,""));
        placeOrderModel.setLens_r_power(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.cyclind_right,""));
        placeOrderModel.setLens_r_axis(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.axis_right,""));
        placeOrderModel.setLens_r_addition(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.addition_right,""));
        placeOrderModel.setLens_r_commercialCode(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typecode_right,""));
        placeOrderModel.setLens_r_commercialName(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,""));
        placeOrderModel.setLens_r_dia(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_diameter_name_right,""));

        placeOrderModel.setCoating_commercialCode(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_coatingcode,""));
        placeOrderModel.setCoating_commercialType(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_coatingtype,""));
        placeOrderModel.setCoating_commercialTint(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_tintcode,""));
        placeOrderModel.setCoating_commercialCodeName(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_coatingcode,""));
        placeOrderModel.setCoating_commercialTintName(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_tintdisplay_name,""));

        placeOrderModel.setPortfolio(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_selected_portfolio_name,""));
        placeOrderModel.setEmployee_name(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeName,""));
        placeOrderModel.setLens_l_sphere(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.sphere_left,""));
        placeOrderModel.setLens_l_power(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.cyclind_left,""));
        placeOrderModel.setLens_l_axis(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.axis_left,""));
        placeOrderModel.setLens_l_addition(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.addition_left,""));
        placeOrderModel.setLens_l_commercialCode(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typecode_left,""));
        placeOrderModel.setLens_l_commercialName(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,""));
        placeOrderModel.setLens_l_dia(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_diameter_name_left,""));
        placeOrderModel.setFrm_material(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.frame_type,""));
        if(BaseActivity.ShapeBevelAct == 1) {
            placeOrderModel.setFrm_model("");}
             else{
                 placeOrderModel.setFrm_model(Sharedpreference.getSharedprefernce(activity,Sharedpreference.Shape_bevel_id,""));}


        placeOrderModel.setPanangle(Sharedpreference.getSharedprefernce(activity,Sharedpreference.panto_angle,""));
        placeOrderModel.setBowangle(Sharedpreference.getSharedprefernce(activity,Sharedpreference.bow_angle,""));
        placeOrderModel.setFrame_fit(Sharedpreference.getSharedprefernce(activity,Sharedpreference.framefit,""));
        placeOrderModel.setIndiv_engrav(Sharedpreference.getSharedprefernce(activity,Sharedpreference.indiv_engrav,""));
        placeOrderModel.setDist_near(Sharedpreference.getSharedprefernce(activity,Sharedpreference.distance_near,""));
        placeOrderModel.setMid(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mid,""));
        placeOrderModel.setNear_type(Sharedpreference.getSharedprefernce(activity,Sharedpreference.neartype,""));



        placeOrderModel.setCentration_r_pdz(Sharedpreference.getSharedprefernce(activity,Sharedpreference.PDZ_right,""));
        placeOrderModel.setCentration_r_height(Sharedpreference.getSharedprefernce(activity,Sharedpreference.YFH_right,""));
        placeOrderModel.setCentration_r_bvd(Sharedpreference.getSharedprefernce(activity,Sharedpreference.bvd_right,""));

        placeOrderModel.setCentration_l_pdz(Sharedpreference.getSharedprefernce(activity,Sharedpreference.PDZ_left,""));
        placeOrderModel.setCentration_l_height(Sharedpreference.getSharedprefernce(activity,Sharedpreference.YFH_left,""));
        placeOrderModel.setCentration_l_bvd(Sharedpreference.getSharedprefernce(activity,Sharedpreference.bvd_left,""));
        placeOrderModel.setIndiv_Val(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_individual,""));


        onPLaceOrder(placeOrderModel);
    }




    private void onPLaceOrder(PlaceOrderModel placeOrderModel){
        mRetroservice.onPLaceOrder(placeOrderModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {

                        onToast(mActivity,result.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
//                        onToast(mActivity,e.getMessage());
                        onClear();
                    }

                    @Override
                    public void onComplete() {
                        onClear();

                    }
                });

    }

    private void onClear(){
        onProgress(R.id.custom_progressbar,activity);
        Sharedpreference.onStorePreferences(activity,Sharedpreference.orderreference,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.firstname,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lastname,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.personaldata,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.consignee_list,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.employee_list,"");

        Sharedpreference.onStorePreferences(activity,Sharedpreference.both_single,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.sphere_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cyclind_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.axis_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.addition_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.sphere_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cyclind_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.axis_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.addition_left,"");


        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_selected_portfolio_id,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_selected_portfolio_name,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_id_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_id_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_name_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_name_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_individual,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_code_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_code_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_coatingname,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_coatingcode,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_coatingtype,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_tintname,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_tintcode,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_tintdisplay_name,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mEmployeeName,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mEmployeeId,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_portfolio_id,"0");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diamter_right_id,"0");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diamter_left_id,"0");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,"0");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_name,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cutom_id,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mlens_coating_id,"");
        /*Shape Bevel*/
        Sharedpreference.onStorePreferences(activity,Sharedpreference.Shape_bevel_id,"");
        /*Advanced Option*/
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
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_height_r,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_length_l,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.frame_height_l,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.DBL_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.DBL_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.PDZ_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.PDZ_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.YFH_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.YFH_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.bvd_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.bvd_left,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderType_id,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.side,"");
        mActivity.finish();
        startActivity(new Intent(activity,MainActivity.class));
    }

}