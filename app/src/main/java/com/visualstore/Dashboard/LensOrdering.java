package com.visualstore.Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.visualstore.Adapter.CustomOrderAdapter;
import com.visualstore.BaseActivity;
import com.visualstore.Model.CoatingTintModel;
import com.visualstore.Model.GetOrderData;
import com.visualstore.Model.LensEditData;
import com.visualstore.Model.LensOrderModel;
import com.visualstore.Model.ProductLensDataModel;
import com.visualstore.R;
import com.visualstore.Retrfofit.Retro;
import com.visualstore.Retrfofit.RetroServices;
import com.visualstore.Sharedpreference.Sharedpreference;
import com.visualstore.Validation.RangesDisplay;
import com.visualstore.Validation.RangesDisplay1;
import com.visualstore.Validation.Validation;
import com.visualstore.Validation.Validation1;
import org.json.JSONObject;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LensOrdering extends BaseActivity  {
    private Activity activity;

    @BindView(R.id.lensordering_toolbar)
    protected Toolbar mLensorderToolbar;

    @BindView(R.id.lensordering_listview)
    protected ExpandableListView expandableListView;

    private ArrayList<LensOrderModel> mLensOrderRightDiamterList;
    private ArrayList<LensOrderModel> mLensOrderLefttDiamterList;
    private ArrayList<LensOrderModel> mLensPortfolioList;
    private ArrayList<LensOrderModel> mLensOrderTypeList;

    private CustomOrderAdapter mAdapter;
    private CustomOrderAdapter mAOrderTypedapter;
    private ArrayList<GetOrderData> mLenslits;

    /*Parent Buttons*/
    @BindView(R.id.customername)
    protected AppCompatButton mCustomername;

    @BindView(R.id.refraction)
    protected AppCompatButton mRefraction;

    @BindView(R.id.lenstypeselection)
    protected AppCompatButton mLenstypeselection;

    @BindView(R.id.treatments)
    protected AppCompatButton mTreatments;

    @BindView(R.id.lensordering_parent_layout)
    protected RelativeLayout mLensordering_parent_layout;

    /*Child Vies*/
    @BindView(R.id.customername_layout)
    protected RelativeLayout mCustomernamelayout;

    @BindView(R.id.refraction_layout)
    protected RelativeLayout mRefractionlayout;

    @BindView(R.id.lenstypeselection_layout)
    protected RelativeLayout mLenstypeselectionlayout;

    @BindView(R.id.treatments_layout)
    protected RelativeLayout mTreatmentslayout;
    boolean showingFirst;

    /*Order Referances Layout Id*/
    @BindView(R.id.orderreference)
    protected AppCompatEditText mOrderreference;

    @BindView(R.id.consumerdetail_layout)
    protected RelativeLayout mConsumerdetail_layout;

    @BindView(R.id.firstname)
    protected AppCompatEditText mFirstname;

    @BindView(R.id.lastanme)
    protected AppCompatEditText mLastanme;

    @BindView(R.id.personaldata)
    protected CheckBox mPersonaldata;

    @BindView(R.id.consignee_list)
    protected AppCompatTextView mConsignee_list;

    @BindView(R.id.employee_list)
    protected AppCompatTextView mEmployee_list;

    /*Refraction Layout ID*/
    @BindView(R.id.radio_grp)
    protected RadioGroup mRadio_grp;

    @BindView(R.id.single)
    protected RadioButton mSingle;

    @BindView(R.id.both)
    protected RadioButton mBoth;

    @BindView(R.id.inputvalue_right_layout)
    protected LinearLayout mInputvalue_right_layout;

    @BindView(R.id.inputvalue_left_layout)
    protected LinearLayout mInputvalue_left_layout;

    @BindView(R.id.leftside)
    protected AppCompatCheckBox mLeftside;

    @BindView(R.id.rightside)
    protected AppCompatCheckBox mRightside;

    @BindView(R.id.sphere_right)
    protected EditText mSphere_right;

    @BindView(R.id.cyclind_right)
    protected AppCompatEditText mCyclind_right;

    @BindView(R.id.axis_right)
    protected AppCompatEditText mAxis_right;

    @BindView(R.id.addition_right)
    protected AppCompatEditText mAddition_right;

    @BindView(R.id.sphere_left)
    protected AppCompatEditText mSphere_left;

    @BindView(R.id.cyclind_left)
    protected AppCompatEditText mCyclind_left;

    @BindView(R.id.axis_left)
    protected AppCompatEditText mAxis_left;

    @BindView(R.id.addition_left)
    protected AppCompatEditText mAddition_left;

    /*Lens Selection Type ID's*/

    @BindView(R.id.portfolio_list)
    protected AppCompatSpinner mPortfolio_list;

    @BindView(R.id.lenstype_list_right)
    protected AppCompatTextView mLenstype_list_right;

    @BindView(R.id.lenstype_list_left)
    protected AppCompatTextView mLenstype_list_left;

    @BindView(R.id.diameter_list_right)
    protected AppCompatSpinner mDiameter_list_right;

    @BindView(R.id.diameter_list_left)
    protected AppCompatSpinner mDiameter_list_left;

    @BindView(R.id.diameter_right)
    protected TextView mDiameter_right;

    @BindView(R.id.diameter_left)
    protected TextView mDiameter_left;

    @BindView(R.id.lenstype_right)
    protected TextView mLenstype_right_header;


    @BindView(R.id.lenstype_left)
    protected TextView mLenstype_left_header;

    @BindView(R.id.errortxt)
    protected TextView errortxt;




    /*Treatments ID's*/

    @BindView(R.id.coating_list)
    protected AppCompatTextView mCoating_list;

    @BindView(R.id.tint_list)
    protected AppCompatTextView mTint_list;

    @BindView(R.id.ordertype_list)
    protected AppCompatSpinner mOrdertype_list;

    @BindView(R.id.uv_protect_selected)
    protected Switch mUV_protect_selected;


    @BindView(R.id.orderreview)
    protected AppCompatButton mOrderReview;

    @BindView(R.id.shapebevel)
    protected AppCompatButton mShapebevel;



    @BindView(R.id.advancedoption)
    protected AppCompatButton mAdvancedoption;


    private ArrayList<String> mPortfolio;

    @BindView(R.id.valuessteps)
    protected AppCompatTextView valuessteps;

    private AwesomeValidation validation;
    private String mPersonal = "0";
    private ArrayList<ProductLensDataModel> ProductLensDataModelList = new ArrayList<>();
    private RetroServices mRetroservice;
    private String mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lens_ordering);
        activity = LensOrdering.this;
        ButterKnife.bind(this);
        onLensOrderToolbar();

        if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.mOrderEdit, "").equals("editorder")) {
            String mAdinId = Sharedpreference.getSharedprefernce(activity,Sharedpreference.admin_id,"");
            String mOrderStatus = Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrderStatus,"");
            String mOrderid = Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrder_id,"");
            if(BaseActivity.isNetworkConnected(activity) == true) {
                onGetOrderDetails(mOrderStatus, mAdinId, mOrderid);
            }else{
                onSnackBar(mLensordering_parent_layout,getString(R.string.interntconnectionalert));
            }
            onRefractionEdit();
            onProgress(R.id.custom_progressbar,activity);
        } else{
        onPortfolioList();
        onOrderList();
        onCheckPersonalData();
        /*lens Order Type*/
        mBoth.setChecked(true);
        mLeftside.setChecked(true);
        mRightside.setChecked(true);
        mSingle.setChecked(false);
        onRefraction();
        onRefractionBoth();
        Sharedpreference.onStorePreferences(activity,Sharedpreference.both_single,"both");
        BaseActivity.mLensRefractiontxt = "both";
    }
        mCoating_list.setHint(getResources().getString(R.string.select) + " " + getResources().getString(R.string.coating));
        mTint_list.setHint(getResources().getString(R.string.select) + " " + getResources().getString(R.string.tint));
        validation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        mLenstype_right_header.setText(getResources().getString(R.string.lenstype) + " - " + getResources().getString(R.string.r));
        mLenstype_left_header.setText(getResources().getString(R.string.lenstype) + " - " + getResources().getString(R.string.l));
        mDiameter_left.setText(getResources().getString(R.string.diameter) + " - " + getResources().getString(R.string.l));
        mDiameter_right.setText(getResources().getString(R.string.diameter) + " - " + getResources().getString(R.string.r));
    }

    private void onLensOrderToolbar() {
        setSupportActionBar(mLensorderToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(activity.getResources().getString(R.string.lensordering));
        mLensorderToolbar.setTitleTextColor(Color.WHITE);
        mLensorderToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderEdit,"");
            }
        });
        mConsignee_list.setText(getResources().getString(R.string.visioncenter));
        mRetroservice = Retro.get().createWithOutToken(RetroServices.class);
    }



    /*Lens Ordering button click*/

    @OnClick(R.id.customername)
    protected void onCustomerNameBtn() {
        onViewChanges(true, mCustomername, mRefraction, mLenstypeselection, mTreatments, mCustomernamelayout, mRefractionlayout, mLenstypeselectionlayout, mTreatmentslayout);
    }

    @OnClick(R.id.refraction)
    protected void onRefractionBtn() {
        onViewChanges(true, mRefraction, mCustomername, mLenstypeselection, mTreatments, mRefractionlayout, mCustomernamelayout, mLenstypeselectionlayout, mTreatmentslayout);
    }

    @OnClick(R.id.lenstypeselection)
    protected void onLenstypeselectionBtn() {
        onViewChanges(true, mLenstypeselection, mCustomername, mRefraction, mTreatments, mLenstypeselectionlayout, mCustomernamelayout, mRefractionlayout, mTreatmentslayout);
    }

    @OnClick(R.id.treatments)
    protected void onTreatmentsBtn() {
        onViewChanges(true, mTreatments, mCustomername, mRefraction, mLenstypeselection, mTreatmentslayout, mCustomernamelayout, mRefractionlayout, mLenstypeselectionlayout);
    }

    private void onViewChanges(boolean showingFirst, AppCompatButton mAppCompatButton, AppCompatButton mAppCompatButton1, AppCompatButton mAppCompatButton2, AppCompatButton mAppCompatButton3,
                               View mDisplayLayout, View mNonDiplay1, View mNonDiplay2, View mNonDiplay3) {
        if (showingFirst) {
            showingFirst = false;
            mAppCompatButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow, 0);
            mDisplayLayout.setVisibility(View.VISIBLE);
            mNonDiplay1.setVisibility(View.GONE);
            mNonDiplay2.setVisibility(View.GONE);
            mNonDiplay3.setVisibility(View.GONE);
            mAppCompatButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_angle_arrow_down, 0);
            mAppCompatButton2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_angle_arrow_down, 0);
            mAppCompatButton3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_angle_arrow_down, 0);
            mAppCompatButton.setTextColor(getResources().getColor(R.color.blue));
            mAppCompatButton1.setTextColor(getResources().getColor(R.color.darkgray));
            mAppCompatButton2.setTextColor(getResources().getColor(R.color.darkgray));
            mAppCompatButton3.setTextColor(getResources().getColor(R.color.darkgray));
        } else {
            showingFirst = true;
            mAppCompatButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_angle_arrow_down, 0);
            mDisplayLayout.setVisibility(View.GONE);
        }
    }

    /*Oredr Reference Module */
    public void onOrderReferences() {

    }


    /* Order Referances Button Click*/
    @OnClick(R.id.consumecard_btn)
    protected void onConsumerBtn() {
        mConsumerdetail_layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.or_confirm)
    protected void onConfirm() {
        if (mFirstname.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.firstname));
        } else if (mLastanme.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lastname));
        } else if(mPersonal.equals("0")){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plscheck));
        }
        else {
            mConsumerdetail_layout.setVisibility(View.GONE);
        }
    }

    private void onCheckPersonalData(){
        mPersonaldata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked == true){
                    mPersonal = "1";
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.personaldata,"1");
                } else {
                    mPersonal = "0";
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.personaldata,"0");
                }
            }
        });
    }
    @OnClick(R.id.or_cancel)
    protected void onCancel() {
        mConsumerdetail_layout.setVisibility(View.GONE);
    }

    /*Refraction Module*/

    /*Refraction cnditions*/
    private void onRefraction() {
//        DatabaseHelper databaseHelper = new DatabaseHelper(activity);
        mRadio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.single:
                        onSingleCheck();
                        mLeftside.setChecked(false);
                        mRightside.setChecked(false);
                        onEnableBoth();
                        mLeftside.setVisibility(View.VISIBLE);
                        mRightside.setVisibility(View.VISIBLE);
                        onHide();
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.both_single,"single");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,"");
                        onClearRefractionLeft();
                        onClearRefractionRight();
                        onDiablerefactionLeft(false);
                        onDiablerefactionRight(false);
                        mLeftside.setEnabled(true);
                        mRightside.setEnabled(true);
                        break;
                    case R.id.both:
//                        mLeftside.setVisibility(View.INVISIBLE);
//                        mRightside.setVisibility(View.INVISIBLE);
                        mLeftside.setEnabled(false);
                        mRightside.setEnabled(false);
                        BaseActivity.mLensRefractiontxt = "both";
                        onEnableBoth();
                        onDisplayAllSide(mLenstype_list_left,mLenstype_left_header,mDiameter_list_left,mDiameter_left,
                                mLenstype_list_right,mLenstype_right_header,mDiameter_list_right,mDiameter_right);
                        mLenstype_list_left.setClickable(false);
                        mDiameter_list_left.setEnabled(false);
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.both_single,"both");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,"");
                        onClearRefractionLeft();
                        onClearRefractionRight();
                        onRefractionBoth();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void onRefractionEdit(){
        mRadio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.single:
                        onSingleCheckEdit();
                        mRightside.setEnabled(true);
                        mLeftside.setEnabled(true);
                        mRightside.setChecked(false);
                        mLeftside.setChecked(false);
                        onEnableBothEdit();
                        break;
                    case R.id.both:
                        BaseActivity.mLensRefractiontxt = "both";
                        mRightside.setEnabled(false);
                        mLeftside.setEnabled(false);
                        onEnableBothValue();
                        onEnableBothEdit();
                        onRefractionBoth();
                        onDisplayAllSide(mLenstype_list_left,mLenstype_left_header,mDiameter_list_left,mDiameter_left,
                                mLenstype_list_right,mLenstype_right_header,mDiameter_list_right,mDiameter_right);
                        mLenstype_list_left.setClickable(false);
                        mDiameter_list_left.setEnabled(false);

                        Sharedpreference.onStorePreferences(activity,Sharedpreference.both_single,"both");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,"");
                        onClearRefractionLeft();
                        onClearRefractionRight();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void onEnableBothValue(){
             onRightValue();
             onLeftValue();
          }

    private void onLeftValue(){
        mSphere_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.sphere_left, ""));
        mCyclind_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.cyclind_left, ""));
        mAxis_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.axis_left, ""));
        mAddition_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.addition_left, ""));
    }

    private void onRightValue(){
        mSphere_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.sphere_right, ""));
        mCyclind_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.cyclind_right, ""));
        mAxis_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.axis_right, ""));
        mAddition_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.addition_right, ""));
    }


    private void onClearRefractionRight(){
        mSphere_right.setText("");
        mCyclind_right.setText("");
        mAxis_right.setText("");
        mAddition_right.setText("");
    }

    private void onClearRefractionLeft(){
        mSphere_left.setText("");
        mCyclind_left.setText("");
        mAxis_left.setText("");
        mAddition_left.setText("");
    }


    private void onDiablerefactionRight(boolean value){
        mSphere_right.setEnabled(value);
        mCyclind_right.setEnabled(value);
        mAxis_right.setEnabled(value);
        mAddition_right.setEnabled(value);
    }

    private void onDiablerefactionLeft(boolean value){
        mSphere_left.setEnabled(value);
        mCyclind_left.setEnabled(value);
        mAxis_left.setEnabled(value);
        mAddition_left.setEnabled(value);
    }

    private void onSingleCheck() {
        mRightside.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onCheckBoxSingle(BaseActivity.mLensRefractiontxt = "right");
                }
            }
        });

        mLeftside.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onCheckBoxSingle(BaseActivity.mLensRefractiontxt = "left");
                }
            }
        });

    }

    private void onCheckBoxSingle(String  mvalue){
        if(mvalue.equals("right")){
            mLeftside.setChecked(false);
            onHideLayout(mSphere_left, mCyclind_left, mAxis_left, mAddition_left);
            onEnableLayout(mSphere_right, mCyclind_right, mAxis_right, mAddition_right);
            onCheck();
            onDisplaySide(mLenstype_list_left,mLenstype_left_header,mDiameter_list_left,mDiameter_left,
                    mLenstype_list_right,mLenstype_right_header,mDiameter_list_right,mDiameter_right);
            onRefractionRight();
            onClearRefractionLeft();
            onClearRefractionRight();
            onDiablerefactionRight(true);
            onDiablerefactionLeft(false);
        } else if(mvalue.equals("left")){
            mRightside.setChecked(false);
            onHideLayout(mSphere_right, mCyclind_right, mAxis_right, mAddition_right);
            onEnableLayout(mSphere_left, mCyclind_left, mAxis_left, mAddition_left);
            onCheck();
            onDisplaySide(mLenstype_list_right,mLenstype_right_header,mDiameter_list_right,mDiameter_right,
                    mLenstype_list_left,mLenstype_left_header,mDiameter_list_left,mDiameter_left);
            onRefractionLeft();
            mLenstype_list_left.setClickable(true);
            mDiameter_list_left.setEnabled(true);
            onClearRefractionLeft();
            onClearRefractionRight();
            onDiablerefactionRight(false);
            onDiablerefactionLeft(true);
        }
        else{
//            onHideLayout(mSphere_left, mCyclind_left, mAxis_left, mAddition_left);
//            onHideLayout(mSphere_right, mCyclind_right, mAxis_right, mAddition_right);
        }

    }



    private void onSingleCheckEdit() {
        mRightside.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    onCheckBoxSingle(BaseActivity.mLensRefractiontxt = "right");
                    onRightValue();
                }
            }
        });
        mLeftside.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    onCheckBoxSingle(BaseActivity.mLensRefractiontxt = "left");
                    onLeftValue();
                }
            }
        });

    }


    private void onRefractionBoth(){
        mSphere_right.setOnEditorActionListener(new Validation(activity, mSphere_right, mCyclind_right, mSphere_left, -20.0, 23.0,errortxt,R.string.spherenotinrange));
        mCyclind_right.setOnEditorActionListener(new Validation(activity, mCyclind_right, mAxis_right, mCyclind_left, -10.0, 10.0,errortxt,R.string.cylindnotinrange));
        mAxis_right.setOnEditorActionListener(new Validation(activity, mAxis_right, mAddition_right, mAxis_left, 0.0, 180.0,errortxt,R.string.axisnotinrange));
        mAddition_right.setOnEditorActionListener(new Validation1(activity, mAddition_right, mAddition_right, mAddition_left, 0.5, 4.0,errortxt,R.string.additionnotinrange));
        mSphere_right.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_sphere,mSphere_right,mSphere_left, -20.0,23.0,errortxt,R.string.spherenotinrange));
        mCyclind_right.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_cylind,mCyclind_right,mCyclind_left, -10.0,10.0,errortxt,R.string.cylindnotinrange));
        mAxis_right.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_axis,mAxis_right,mAxis_left, 0.0,180.0,errortxt,R.string.axisnotinrange));
        mAddition_right.setOnFocusChangeListener(new RangesDisplay1(activity,valuessteps, R.string.value_addition,mAddition_right,mAddition_left,0.5,4.0,errortxt,R.string.additionnotinrange));


    }


    private void onRefractionRight(){
        mSphere_right.setOnEditorActionListener(new Validation(activity, mSphere_right, mCyclind_right, mSphere_right, -20.0, 23.0,errortxt,R.string.spherenotinrange));
        mCyclind_right.setOnEditorActionListener(new Validation(activity, mCyclind_right, mAxis_right, mCyclind_right, -10.0, 10.0,errortxt,R.string.cylindnotinrange));
        mAxis_right.setOnEditorActionListener(new Validation(activity, mAxis_right, mAddition_right, mAxis_right, 0.0, 180.0,errortxt,R.string.axisnotinrange));
        mAddition_right.setOnEditorActionListener(new Validation1(activity, mAddition_right, mAddition_right, mAddition_right, 0.5, 4.0,errortxt,R.string.additionnotinrange));
        mSphere_right.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_sphere,mSphere_right,mSphere_right, -20.0,23.0,errortxt,R.string.spherenotinrange));
        mCyclind_right.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_cylind,mCyclind_right,mCyclind_right, -10.0,10.0,errortxt,R.string.cylindnotinrange));
        mAxis_right.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_axis,mAxis_right,mAxis_right, 0.0,180.0,errortxt,R.string.axisnotinrange));
        mAddition_right.setOnFocusChangeListener(new RangesDisplay1(activity,valuessteps, R.string.value_addition,mAddition_right,mAddition_right,0.5,4.0,errortxt,R.string.additionnotinrange));

    }


    private void onRefractionLeft(){
        mSphere_left.setOnEditorActionListener(new Validation(activity, mSphere_left, mCyclind_left, mSphere_left, -20.0, 23.0,valuessteps,R.string.spherenotinrange));
        mCyclind_left.setOnEditorActionListener(new Validation(activity, mCyclind_left, mAxis_left, mCyclind_left, -10.0, 10.0,valuessteps,R.string.cylindnotinrange));
        mAxis_left.setOnEditorActionListener(new Validation(activity, mAxis_left, mAddition_left, mAxis_left, 0.0, 180.0,valuessteps,R.string.axisnotinrange));
        mAddition_left.setOnEditorActionListener(new Validation1(activity, mAddition_left, mAddition_left, mAddition_left, 0.5, 4.0,valuessteps,R.string.additionnotinrange));
        mSphere_left.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_sphere,mSphere_left,mSphere_left, -20.0,23.0,valuessteps,R.string.spherenotinrange));
        mCyclind_left.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_cylind,mCyclind_left,mCyclind_left, -10.0,10.0,valuessteps,R.string.cylindnotinrange));
        mAxis_left.setOnFocusChangeListener(new RangesDisplay(activity,valuessteps, R.string.value_axis,mAxis_left,mAxis_left, 0.0,180.0,valuessteps,R.string.axisnotinrange));
        mAddition_left.setOnFocusChangeListener(new RangesDisplay1(activity,valuessteps, R.string.value_addition,mAddition_left,mAddition_left,0.5,4.0,valuessteps,R.string.additionnotinrange));
    }

    /*Lens Type Module Logic*/
    @OnClick(R.id.lenstype_list_left)
    protected  void onmLenstype(){
        startActivity(new Intent(activity,LensDataSearchList.class));
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_id,"1");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_type_act,"2");
        BaseActivity.mAdditionLeft = mAddition_left.getText().toString().trim();
        BaseActivity.mAdditionRight = mAddition_right.getText().toString().trim();

    }
    @OnClick(R.id.lenstype_list_right)
    protected  void onLenstype(){
        startActivity(new Intent(activity,LensDataSearchList.class));
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_id,"2");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_type_act,"2");
        BaseActivity.mAdditionLeft = mAddition_left.getText().toString().trim();
        BaseActivity.mAdditionRight = mAddition_right.getText().toString().trim();

    }

    private void onPortfolioList(){
        mLensPortfolioList = new ArrayList<LensOrderModel>();
        mLensPortfolioList.clear();
        mLensPortfolioList.add(new LensOrderModel(getResources().getString(R.string.zeiss),"0"));
        mLensPortfolioList.add(new LensOrderModel(getResources().getString(R.string.synchrony),"1"));
        mAdapter = new CustomOrderAdapter(activity,R.layout.custom_lensorder_txt,mLensPortfolioList);
        mPortfolio_list.setAdapter(mAdapter);
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cutom_id,"1");
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_selected_portfolio_name,"").equals(getResources().getString(R.string.zeiss))){
                 mPortfolio_list.setSelection(0);
            }else {
                mPortfolio_list.setSelection(1);
            }

        mPortfolio_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
//                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_selected_portfolio_id,"0");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_selected_portfolio_name,getResources().getString(R.string.zeiss));
                        break;
                    case 1:
//                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_selected_portfolio_id,"1");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_selected_portfolio_name,getResources().getString(R.string.synchrony));
                        break;
                    default:
                         break;
                }}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_selected_portfolio_id,"0");
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_selected_portfolio_name,getResources().getString(R.string.zeiss));
            }
        });
     }




    private void onDiameterRight(){
            mLensOrderRightDiamterList = new ArrayList<LensOrderModel>();
            mLensOrderRightDiamterList.clear();
        if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").isEmpty() &&
                Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_right, "").contains("Finished")
                || Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_right, "").contains("FSV")) {

            mLensOrderRightDiamterList.add(new LensOrderModel("65", "2"));
            mLensOrderRightDiamterList.add(new LensOrderModel("70", "3"));
            mLensOrderRightDiamterList.add(new LensOrderModel("75", "4"));
            mLensOrderRightDiamterList.add(new LensOrderModel("", ""));
            mLensOrderRightDiamterList.add(new LensOrderModel("", ""));
            } else {
                mLensOrderRightDiamterList.add(new LensOrderModel("55", "0"));
                mLensOrderRightDiamterList.add(new LensOrderModel("60", "1"));
                mLensOrderRightDiamterList.add(new LensOrderModel("65", "2"));
                mLensOrderRightDiamterList.add(new LensOrderModel("70", "3"));
                mLensOrderRightDiamterList.add(new LensOrderModel("75", "4"));
            }

            mAdapter = new CustomOrderAdapter(activity,R.layout.custom_lensorder_txt,mLensOrderRightDiamterList);
            mDiameter_list_right.setAdapter(mAdapter);





        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_diameter_id_right,"").isEmpty()){
        }else{
            int i = Integer.parseInt(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_diameter_id_right,""));
            mDiameter_list_right.setSelection(i);
        }
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cutom_id,"2");
        mDiameter_list_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_id_left,mLensOrderLefttDiamterList.get(position).getId());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_id_right,mLensOrderLefttDiamterList.get(position).getId());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_name_right,mLensOrderLefttDiamterList.get(position).getmName());
                mDiameter_list_left.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

//                if( Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_right, "").contains("Finished") ||
//                        Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_right, "").contains("FSV")){
//                    mDiameter_list_right.setSelection(2);
//                }else{
//                    mDiameter_list_right.setSelection(0);
//                }

                onDiameterLeft();


            }
        });
    }



    private void onDiameterLeft(){
        mLensOrderLefttDiamterList = new ArrayList<LensOrderModel>();
        mLensOrderLefttDiamterList.clear();
        if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").isEmpty()
                && Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("Finished")
                || Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("FSV")) {

            mLensOrderLefttDiamterList.add(new LensOrderModel("65", "2"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("70", "3"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("75", "4"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("", ""));
            mLensOrderLefttDiamterList.add(new LensOrderModel("", ""));
        } else {
            mLensOrderLefttDiamterList.add(new LensOrderModel("55", "0"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("60", "1"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("65", "2"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("70", "3"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("75", "4"));
        }
        mAdapter = new CustomOrderAdapter(activity,R.layout.custom_lensorder_txt,mLensOrderLefttDiamterList);
        mDiameter_list_left.setAdapter(mAdapter);
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cutom_id,"3");
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_diameter_id_left,"").isEmpty()){

        }else{
            int position = Integer.parseInt(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_diameter_id_left,""));
            mDiameter_list_left.setSelection(position);
        }

        mDiameter_list_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_id_left,mLensOrderLefttDiamterList.get(position).getId());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_name_left,mLensOrderLefttDiamterList.get(position).getmName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                if( Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("Finished") ||
                        Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("FSV")){
                    mDiameter_list_left.setSelection(2);
                 }else{
                    mDiameter_list_left.setSelection(0);
                }
            }
        });
    }



    private void onDiameterLeftAlone(){
        mLensOrderLefttDiamterList = new ArrayList<LensOrderModel>();
        mLensOrderLefttDiamterList.clear();
        if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").isEmpty() &&
                Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("Finished") ||
                Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("FSV")) {

            mLensOrderLefttDiamterList.add(new LensOrderModel("65", "2"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("70", "3"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("75", "4"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("", ""));
            mLensOrderLefttDiamterList.add(new LensOrderModel("", ""));
        } else {
            mLensOrderLefttDiamterList.add(new LensOrderModel("55", "0"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("60", "1"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("65", "2"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("70", "3"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("75", "4"));
        }
        mAdapter = new CustomOrderAdapter(activity,R.layout.custom_lensorder_txt,mLensOrderLefttDiamterList);
        mDiameter_list_left.setAdapter(mAdapter);
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cutom_id,"3");

        mDiameter_list_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_id_left,mLensOrderLefttDiamterList.get(position).getId());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_name_left,mLensOrderLefttDiamterList.get(position).getmName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void onDiameterEditLeft(){
        mLensOrderLefttDiamterList = new ArrayList<LensOrderModel>();
        mLensOrderLefttDiamterList.clear();
        if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").isEmpty() &&
                Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("Finished") ||
                Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("FSV")) {
            mLensOrderLefttDiamterList.add(new LensOrderModel("65", "0"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("70", "1"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("75", "2"));
        } else {
            mLensOrderLefttDiamterList.add(new LensOrderModel("55", "0"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("60", "1"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("65", "2"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("70", "3"));
            mLensOrderLefttDiamterList.add(new LensOrderModel("75", "4"));
        }
        mAdapter = new CustomOrderAdapter(activity,R.layout.custom_lensorder_txt,mLensOrderLefttDiamterList);
        mDiameter_list_left.setAdapter(mAdapter);
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cutom_id,"3");
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_diameter_id_left,"").isEmpty()){

        }else{
            int position = Integer.parseInt(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_diameter_id_left,""));
            mDiameter_list_left.setSelection(position);
        }

        if(mLensRefractiontxt.equals("both")) {
//            Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, mLensOrderLefttDiamterList.get(i).getId());
//            Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_name_left, mLensOrderLefttDiamterList.get(i).getmName());
        }
        mDiameter_list_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_id_left,mLensOrderLefttDiamterList.get(position).getId());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_name_left,mLensOrderLefttDiamterList.get(position).getmName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onOrderList(){
        mLensOrderTypeList = new ArrayList<LensOrderModel>();
        mLensOrderTypeList.clear();
        mLensOrderTypeList.add(new LensOrderModel(getResources().getString(R.string.uncut),"0"));
        mLensOrderTypeList.add(new LensOrderModel(getResources().getString(R.string.glazing),"1"));
        mAOrderTypedapter = new CustomOrderAdapter(activity,R.layout.custom_lensorder_txt,mLensOrderTypeList);
        mOrdertype_list.setAdapter(mAOrderTypedapter);
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cutom_id,"4");
        mOrdertype_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        mOrderReview.setVisibility(View.VISIBLE);
                        mShapebevel.setVisibility(View.GONE);
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,mLensOrderTypeList.get(position).getId());
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_name,mLensOrderTypeList.get(position).getmName());
                        break;
                    case 1:
                        mOrderReview.setVisibility(View.GONE);
                        mShapebevel.setVisibility(View.VISIBLE);
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,mLensOrderTypeList.get(position).getId());
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_name,mLensOrderTypeList.get(position).getmName());
                        break;
                    default:
                        mOrderReview.setVisibility(View.VISIBLE);
                        mShapebevel.setVisibility(View.GONE);
                         break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,mLensOrderTypeList.get(0).getId());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_name,mLensOrderTypeList.get(0).getmName());

            }
        });
    }

    private void onOrderListEdit(){
        mLensOrderTypeList = new ArrayList<LensOrderModel>();
        mLensOrderTypeList.clear();
        mLensOrderTypeList.add(new LensOrderModel(getResources().getString(R.string.uncut),"0"));
        mLensOrderTypeList.add(new LensOrderModel(getResources().getString(R.string.glazing),"1"));
        mAOrderTypedapter = new CustomOrderAdapter(activity,R.layout.custom_lensorder_txt,mLensOrderTypeList);
        mOrdertype_list.setAdapter(mAOrderTypedapter);
        int i = Integer.parseInt(Sharedpreference.getSharedprefernce(activity,Sharedpreference.order_type_id,""));
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.order_type_id,"").isEmpty()){
        }else{
            mOrdertype_list.setSelection(i);
        }
        Sharedpreference.onStorePreferences(activity,Sharedpreference.cutom_id,"4");
        mOrdertype_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        mOrderReview.setVisibility(View.VISIBLE);
                        mShapebevel.setVisibility(View.GONE);
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,mLensOrderTypeList.get(position).getId());
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_name,mLensOrderTypeList.get(position).getmName());
                        break;
                    case 1:
                        mOrderReview.setVisibility(View.GONE);
                        mShapebevel.setVisibility(View.VISIBLE);
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,mLensOrderTypeList.get(position).getId());
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_name,mLensOrderTypeList.get(position).getmName());
                        break;
                    default:
                        mOrderReview.setVisibility(View.VISIBLE);
                        mShapebevel.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,mLensOrderTypeList.get(0).getId());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_name,mLensOrderTypeList.get(0).getmName());

            }
        });
    }


    private void onDisplaySide(TextView mLenstypeR,TextView mTypeheaderR,AppCompatSpinner mSpinnerR,TextView mSpinnerheaderR,
                               TextView mLenstypeL,TextView mTypeheaderL,AppCompatSpinner mSpinnerL,TextView mSpinnerheaderL){
        mLenstypeR.setVisibility(View.GONE);
        mSpinnerR.setVisibility(View.GONE);
        mSpinnerheaderR.setVisibility(View.GONE);
        mTypeheaderR.setVisibility(View.GONE);
        mLenstypeL.setVisibility(View.VISIBLE);
        mSpinnerL.setVisibility(View.VISIBLE);
        mSpinnerheaderL.setVisibility(View.VISIBLE);
        mTypeheaderL.setVisibility(View.VISIBLE);
    }
    private void onDisplayAllSide(TextView mLenstypeR,TextView mTypeheaderR,AppCompatSpinner mSpinnerR,TextView mSpinnerheaderR,
                                  TextView mLenstypeL,TextView mTypeheaderL,AppCompatSpinner mSpinnerL,TextView mSpinnerheaderL){
        mLenstypeR.setVisibility(View.VISIBLE);
        mSpinnerR.setVisibility(View.VISIBLE);
        mSpinnerheaderR.setVisibility(View.VISIBLE);
        mTypeheaderR.setVisibility(View.VISIBLE);
        mLenstypeL.setVisibility(View.VISIBLE);
        mSpinnerL.setVisibility(View.VISIBLE);
        mSpinnerheaderL.setVisibility(View.VISIBLE);
        mTypeheaderL.setVisibility(View.VISIBLE);
    }
    private void onDisplayAllSideHide(TextView mLenstypeR,TextView mTypeheaderR,AppCompatSpinner mSpinnerR,TextView mSpinnerheaderR,
                                      TextView mLenstypeL,TextView mTypeheaderL,AppCompatSpinner mSpinnerL,TextView mSpinnerheaderL){
        mLenstypeR.setVisibility(View.GONE);
        mSpinnerR.setVisibility(View.GONE);
        mSpinnerheaderR.setVisibility(View.GONE);
        mTypeheaderR.setVisibility(View.GONE);
        mLenstypeL.setVisibility(View.GONE);
        mSpinnerL.setVisibility(View.GONE);
        mSpinnerheaderL.setVisibility(View.GONE);
        mTypeheaderL.setVisibility(View.GONE);
    }



    /*Treatment*/
    @OnClick(R.id.coating_list)
    protected  void onCoatingtype(){
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mlens_coating_id,"").isEmpty()){
            onSnackBar(mLensordering_parent_layout,getResources().getString(R.string.pleaseselect) +" " + getResources().getString(R.string.lenstype));
        } else  if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").contains("Finished") ||
                Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").contains("Finished") ||
                Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").contains("FSV") ||
                Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").contains("FSV")){
        } else {
            startActivity(new Intent(activity, LensDataSearchList.class));
            Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_type_act, "3");
        }
    }

    private void onGetCoating(String mLenscode){

    }

    @OnClick(R.id.tint_list)
    protected  void onTinttype(){
        startActivity(new Intent(activity,LensDataSearchList.class));
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_type_act,"4");
    }



    private void onAdvanceOptionConditionBoth() {

         if(mLenstype_list_right.getText().toString().trim().isEmpty() && mLenstype_list_left.getText().toString().trim().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        }else if(BaseActivity.mLensRefractiontxt.equals("both") &&
                 !mLenstype_list_left.getText().toString().trim().isEmpty() &&
                 !mLenstype_list_right.getText().toString().trim().isEmpty() &&
                 Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").contains("Finished")
                 && Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").contains("Finished")
                 || Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").contains("FSV")
                 || Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").contains("FSV")
                 ){

        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mSphere_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mSphere_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && !mCyclind_right.getText().toString().trim().isEmpty() && mAxis_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        }  else if (BaseActivity.mLensRefractiontxt.equals("both") && !mCyclind_left.getText().toString().trim().isEmpty() && mAxis_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mLenstype_list_right.getText().toString().isEmpty() &&  mLenstype_list_left.getText().toString().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        }
        else {
            startActivity(new Intent(activity, AdvancedOptionActivity.class));
            onLensOrderValues();
        }
    }

    private void onAdvanceOptionRight(){


        if(BaseActivity.mLensRefractiontxt.equals("right") && !mLenstype_list_right.getText().toString().trim().isEmpty() &&
                Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").contains("Finished") ||
                Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").contains("FSV"))
        {
         } else if (BaseActivity.mLensRefractiontxt.equals("right") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && mSphere_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && !mCyclind_right.getText().toString().trim().isEmpty() && mAxis_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && mLenstype_list_right.getText().toString().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        }  else {
            startActivity(new Intent(activity, AdvancedOptionActivity.class));
            onLensOrderValues();
        }
    }

    private void onAdvanceOptionLeft(){
        if(BaseActivity.mLensRefractiontxt.equals("left") && !mLenstype_list_left.getText().toString().trim().isEmpty() &&
                Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").contains("Finished")
               || Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").contains("FSV")){
        }
        else
        if (BaseActivity.mLensRefractiontxt.equals("left") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && mSphere_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && !mCyclind_left.getText().toString().trim().isEmpty() && mAxis_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && mLenstype_list_left.getText().toString().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        } else {
            startActivity(new Intent(activity, AdvancedOptionActivity.class));
            onLensOrderValues();
        }
    }

    @OnClick(R.id.advancedoption)
    protected void onAdvanceOption(){

        if(BaseActivity.mLensRefractiontxt.equals("both")){
            onAdvanceOptionConditionBoth();
        } else if(BaseActivity.mLensRefractiontxt.equals("right")){
            onAdvanceOptionRight();
        }else if(BaseActivity.mLensRefractiontxt.equals("left")){
            onAdvanceOptionLeft();
        }

    }




    @OnClick(R.id.orderreview)
    protected  void onOrderreview(){

        if(BaseActivity.mLensRefractiontxt.equals("both")){
            onConditionBoth();
        } else if(BaseActivity.mLensRefractiontxt.equals("right")){
            onConditionRight();
        }else if(BaseActivity.mLensRefractiontxt.equals("left")){
            onConditionLeft();
        }


    }

    private void onConditionBoth() {
        if (BaseActivity.mLensRefractiontxt.equals("both") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mSphere_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mSphere_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && !mCyclind_right.getText().toString().trim().isEmpty() && mAxis_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        }  else if (BaseActivity.mLensRefractiontxt.equals("both") && !mCyclind_left.getText().toString().trim().isEmpty() && mAxis_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mLenstype_list_right.getText().toString().isEmpty() &&  mLenstype_list_left.getText().toString().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        }
        else {
            onOrderReviewActivity();
            onLensOrderValues();
        }
    }

    private void onConditionRight(){

        if (BaseActivity.mLensRefractiontxt.equals("right") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && mSphere_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && !mCyclind_right.getText().toString().trim().isEmpty() && mAxis_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && mLenstype_list_right.getText().toString().isEmpty()){
                onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        }  else {
            onOrderReviewActivity();
            onLensOrderValues();
        }
    }

    private void onConditionLeft(){
        if (BaseActivity.mLensRefractiontxt.equals("left") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && mSphere_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && !mCyclind_left.getText().toString().trim().isEmpty() && mAxis_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && mLenstype_list_left.getText().toString().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        } else {
            onOrderReviewActivity();
            onLensOrderValues();
        }
    }
    private void onSBConditionBoth() {
        if (BaseActivity.mLensRefractiontxt.equals("both") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mSphere_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mSphere_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && !mCyclind_right.getText().toString().trim().isEmpty() && mAxis_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        }  else if (BaseActivity.mLensRefractiontxt.equals("both") && !mCyclind_left.getText().toString().trim().isEmpty() && mAxis_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("both") && mLenstype_list_right.getText().toString().isEmpty() &&  mLenstype_list_left.getText().toString().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        }
        else {
            onShapeBevel();
            onLensOrderValues();
        }
    }

    private void onSBConditionRight(){

        if (BaseActivity.mLensRefractiontxt.equals("right") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && mSphere_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && !mCyclind_right.getText().toString().trim().isEmpty() && mAxis_right.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("right") && mLenstype_list_right.getText().toString().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        }  else {
            onShapeBevel();
            onLensOrderValues();
        }
    }

    private void onSBConditionLeft(){
        if (BaseActivity.mLensRefractiontxt.equals("left") && mOrderreference.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.orderreference));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && mSphere_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.sphere));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && !mCyclind_left.getText().toString().trim().isEmpty() && mAxis_left.getText().toString().trim().isEmpty()) {
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.axis));
        } else if (BaseActivity.mLensRefractiontxt.equals("left") && mLenstype_list_left.getText().toString().isEmpty()){
            onSnackBar(mLensordering_parent_layout, getResources().getString(R.string.plsenter) + " " + getResources().getString(R.string.lenstype));
        } else {
            onShapeBevel();
            onLensOrderValues();
        }
    }

    @OnClick(R.id.shapebevel)
    protected   void onShapebevel(){
        if(BaseActivity.mLensRefractiontxt.equals("both")){
            onSBConditionBoth();
        } else if(BaseActivity.mLensRefractiontxt.equals("right")){
            onSBConditionRight();
        }else if(BaseActivity.mLensRefractiontxt.equals("left")){
            onSBConditionLeft();
        }

        }


        private void onShapeBevel(){
            startActivity(new Intent(activity, ShapeBevel.class));

        }

        private void onOrderReviewActivity(){
            startActivity(new Intent(activity,OrderReviewActivity.class));
            BaseActivity.ShapeBevelAct = 1;
//            onValidationSB();     Sharedpreference.onStorePreferences(activity,Sharedpreference.Shape_bevel_id,"");
//
        }

        private void onValidationSB(){
            if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.order_type_id,"").equals("0")){
                Sharedpreference.onStorePreferences(activity,Sharedpreference.Shape_bevel_id,"");
            }
        }



    @OnClick(R.id.employee_list)
    protected void onEmployeeNameList() {
        startActivity(new Intent(activity, LensDataSearchList.class));
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_type_act,"1");
    }


    private void onCheck() {
        if (mLensRefractiontxt.equals("notselected")) {
            onSnackBar(mLensordering_parent_layout, "pls select radio btn");
            mLeftside.setChecked(false);
            mRightside.setChecked(false);
            onEnableBoth();

        } else if(mLensRefractiontxt.equals("right")){
            mLeftside.setChecked(false);
            mRightside.setChecked(true);

        }else if(mLensRefractiontxt.equals("left")){
            mLeftside.setChecked(true);
            mRightside.setChecked(false);

        }
    }

    private void onHide(){

        onDisplayAllSideHide(mLenstype_list_left,mLenstype_left_header,mDiameter_list_left,mDiameter_left,
                mLenstype_list_right,mLenstype_right_header,mDiameter_list_right,mDiameter_right);
    }

    private void onEnableBoth() {
        onEnableLayout(mSphere_left, mCyclind_left, mAxis_left, mAddition_left);
        onEnableLayout(mSphere_right, mCyclind_right, mAxis_right, mAddition_right);

        onDisplayAllSide(mLenstype_list_left,mLenstype_left_header,mDiameter_list_left,mDiameter_left,
                mLenstype_list_right,mLenstype_right_header,mDiameter_list_right,mDiameter_right);
    }

    private void onEnableBothEdit() {
        onEnableLayout(mSphere_left, mCyclind_left, mAxis_left, mAddition_left);
        onEnableLayout(mSphere_right, mCyclind_right, mAxis_right, mAddition_right);
    }


    private void onHideLayout(View view, View v2, View v3, View v4) {
        view.setBackground(getResources().getDrawable(R.drawable.hide));
        v2.setBackground(getResources().getDrawable(R.drawable.hide));
        v3.setBackground(getResources().getDrawable(R.drawable.hide));
        v4.setBackground(getResources().getDrawable(R.drawable.hide));
        view.setEnabled(false);
        v2.setEnabled(false);
        v3.setEnabled(false);
        v4.setEnabled(false);
    }

    private void onEnableLayout(View view, View v2, View v3, View v4) {
        view.setBackground(getResources().getDrawable(R.drawable.rectangle));
        v2.setBackground(getResources().getDrawable(R.drawable.rectangle));
        v3.setBackground(getResources().getDrawable(R.drawable.rectangle));
        v4.setBackground(getResources().getDrawable(R.drawable.rectangle));
        view.setEnabled(true);
        v2.setEnabled(true);
        v3.setEnabled(true);
        v4.setEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
         onDataValues();
         onCoating();
         onDiameterList();
    }


    private void onCoating(){
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").contains("Finished") ||
                Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").contains("Finished")
         ||  Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_right, "").contains("FSV") ||
        Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("FSV")) {
            mRetroservice.getCoatingCode(Sharedpreference.getSharedprefernce(activity, Sharedpreference.mlens_coating_id, "")).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CoatingTintModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(CoatingTintModel coatingCodeListModel) {
                            mCoating_list.setText(coatingCodeListModel.getData().get(1).getName());
                            Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingname, coatingCodeListModel.getData().get(1).getName());
                            Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingcode, coatingCodeListModel.getData().get(1).getCode());
                            Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingtype, coatingCodeListModel.getData().get(1).getType());
                        }
                        @Override
                        public void onError(Throwable e) {
//                            onToast(activity,""+e.getLocalizedMessage());
                        }
                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }



    private void onDataValues(){
           if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_coatingname,"").isEmpty()){
            mCoating_list.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_coatingname,""));
        } if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_tintname,"").isEmpty()){
            mTint_list.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_tintname,""));
        } if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeName,"").isEmpty()){
            mEmployee_list.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeName,""));
        }}

    private void onDiameterList(){
        if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,"").isEmpty()){
            onDiameterRight();onDiameterLeft();
        }
         else if(!Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,"").isEmpty()){
            onDiameterLeftAlone();
        }
            mLenstype_list_right.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,""));
            mLenstype_list_left.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,""));
            }


    private void onLensOrderValues(){
            Sharedpreference.onStorePreferences(activity, Sharedpreference.orderreference, mOrderreference.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.firstname, mFirstname.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.lastname, mLastanme.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.consignee_list, mConsignee_list.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.employee_list, mEmployee_list.getText().toString().trim());
            if(BaseActivity.mLensRefractiontxt.equals("right")){
                Sharedpreference.onStorePreferences(activity, Sharedpreference.sphere_right, mSphere_right.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.cyclind_right, mCyclind_right.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.axis_right, mAxis_right.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.addition_right, mAddition_right.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.sphere_left, "");
                Sharedpreference.onStorePreferences(activity, Sharedpreference.cyclind_left, "");
                Sharedpreference.onStorePreferences(activity, Sharedpreference.axis_left, "");
                Sharedpreference.onStorePreferences(activity, Sharedpreference.addition_left, "");
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,mLenstype_list_right.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,"");
//              Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,"");
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_name_left,"");

            } else if(BaseActivity.mLensRefractiontxt.equals("left")){
                Sharedpreference.onStorePreferences(activity, Sharedpreference.sphere_left, mSphere_left.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.cyclind_left, mCyclind_left.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.axis_left, mAxis_left.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.addition_left, mAddition_left.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.sphere_right, "");
                Sharedpreference.onStorePreferences(activity, Sharedpreference.cyclind_right, "");
                Sharedpreference.onStorePreferences(activity, Sharedpreference.axis_right, "");
                Sharedpreference.onStorePreferences(activity, Sharedpreference.addition_right, "");
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,"");
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,mLenstype_list_left.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_diameter_name_right,"");
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,"");
            }else{
            Sharedpreference.onStorePreferences(activity, Sharedpreference.sphere_right, mSphere_right.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.cyclind_right, mCyclind_right.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.axis_right, mAxis_right.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.addition_right, mAddition_right.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.sphere_left, mSphere_left.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.cyclind_left, mCyclind_left.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.axis_left, mAxis_left.getText().toString().trim());
            Sharedpreference.onStorePreferences(activity, Sharedpreference.addition_left, mAddition_left.getText().toString().trim());

                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,mLenstype_list_right.getText().toString().trim());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,mLenstype_list_left.getText().toString().trim());

               }

        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,"");


    }




    /*Edit Order*/
    private void onGetOrderDetails(String mStatus,String  admin_id,String mOrder_id){
            mRetroservice.getOrdeEditValues(mStatus,admin_id,mOrder_id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LensEditData>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(LensEditData lensEditData) {
                            mContent = lensEditData.getData().getContent().replaceAll("\\\\", "");
                            Log.d("ORDERADAP" ,"-----CONTENT"+ mContent);
                        }

                        @Override
                        public void onError(Throwable e) {
                            onToast(activity,e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            onContentDate(mContent);
                        }
                    });}


        private void onContentDate(String contentvalue){
            String mJsoncontent = contentvalue;
            try {
                JSONObject obj = new JSONObject(mJsoncontent);
                Log.d("ORDERADAP" ,"-----CONTENT"+ obj.optString("admin_id"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.admin_id, obj.optString("admin_id"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.mOrderType_id, obj.optString("order_type"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.orderreference, obj.optString("order_reference"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_selected_portfolio_name, obj.optString("portfolio"));

                Sharedpreference.onStorePreferences(activity, Sharedpreference.mEmployeeName, obj.optString("employee_name"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.personaldata, obj.optString("patient_term"));
                mPersonal = obj.optString("patient_term");

                if(obj.optString("patient_firstName").equals("FirstName")){
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.firstname, "");
                }else{
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.firstname, obj.optString("patient_firstName"));
                }

                if(obj.optString("patient_lastName").equals("LastName")){
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.lastname, "");
                }else{
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.lastname, obj.optString("patient_lastName"));
                }

                Sharedpreference.onStorePreferences(activity, Sharedpreference.side, obj.optString("side"));
                if (obj.optString("side").equals("2")) {
                    mBoth.setChecked(true);
                } else {
                    mSingle.setChecked(true);
                }


                String arr = obj.optString("lens_lr_lens").toUpperCase();
                if (arr.contains("[R, L]")) {
                    BaseActivity.mLensRefractiontxt = "both";
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.both_single,"both");
                    mRightside.setChecked(true);
                    mLeftside.setChecked(true);
                    mRightside.setEnabled(false);
                    mLeftside.setEnabled(false);
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_length_r, obj.optString("frm_width"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_height_r, obj.optString("frm_height"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_length_l, obj.optString("frm_width"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_height_l, obj.optString("frm_height"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.DBL_right, obj.optString("frm_dbl"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.DBL_left, obj.optString("frm_dbl"));
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.framefit,obj.optString("frame_fit"));
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.indiv_engrav,obj.optString("Indiv_engrav"));
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.distance_near,obj.optString("Dist_near"));
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.mid,obj.optString("mid"));
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.neartype,obj.optString("near_type"));
                } else if (arr.contains("[R]")) {
                    BaseActivity.mLensRefractiontxt = "right";
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.both_single,"single");
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_id,"2");
                    mRightside.setChecked(true);
                    mRightside.setEnabled(true);
                    mLeftside.setEnabled(true);
                    mLeftside.setChecked(false);
                    onHideLayout(mSphere_left, mCyclind_left, mAxis_left, mAddition_left);
                    onDisplaySide(mLenstype_list_left, mLenstype_left_header, mDiameter_list_left, mDiameter_left,
                            mLenstype_list_right, mLenstype_right_header, mDiameter_list_right, mDiameter_right);
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_length_r, obj.optString("frm_width"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_height_r, obj.optString("frm_height"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.DBL_right, obj.optString("frm_dbl"));

                } else if(arr.contains("[L]")) {
                    BaseActivity.mLensRefractiontxt = "left";
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.both_single,"single");
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_id,"1");
                    mLeftside.setChecked(true);
                    mLeftside.setEnabled(true);
                    mRightside.setEnabled(true);
                    mRightside.setChecked(false);
                    onHideLayout(mSphere_right, mCyclind_right, mAxis_right, mAddition_right);
                    onDisplaySide(mLenstype_list_right, mLenstype_right_header, mDiameter_list_right, mDiameter_right,
                            mLenstype_list_left, mLenstype_left_header, mDiameter_list_left, mDiameter_left);
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_length_l, obj.optString("frm_width"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_height_l, obj.optString("frm_height"));
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.DBL_left, obj.optString("frm_dbl"));

                }


                Sharedpreference.onStorePreferences(activity, Sharedpreference.sphere_right, obj.optString("lens_r_sphere"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.cyclind_right, obj.optString("lens_r_power"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.axis_right, obj.optString("lens_r_axis"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.addition_right, obj.optString("lens_r_addition"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_typecode_right, obj.optString("lens_r_commercialCode"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_typename_right, obj.optString("lens_r_commercialName"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_name_right, obj.optString("lens_r_dia"));


                Sharedpreference.onStorePreferences(activity, Sharedpreference.sphere_left, obj.optString("lens_l_sphere"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.cyclind_left, obj.optString("lens_l_power"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.axis_left, obj.optString("lens_l_axis"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.addition_left, obj.optString("lens_l_addition"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_typecode_left, obj.optString("lens_l_commercialCode"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_typename_left, obj.optString("lens_l_commercialName"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_name_left, obj.optString("lens_l_dia"));

                Sharedpreference.onStorePreferences(activity, Sharedpreference.PDZ_right, obj.optString("centration_r_pdz"));
        Sharedpreference.onStorePreferences(activity,Sharedpreference.YFH_right,obj.optString("centration_r_height"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.bvd_right, obj.optString("centration_r_bvd"));
        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,obj.optString("order_type"));
//        Sharedpreference.onStorePreferences(activity,Sharedpreference.admin_id,obj.optString("lens_hard"));

                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_tintcode, obj.optString("coating_commercialTint"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_tintname, obj.optString("coating_commercialTintName"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingtype, obj.optString("coating_commercialType"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingcode, obj.optString("coating_commercialCode"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingname, obj.optString("coating_commercialCodeName"));

                Sharedpreference.onStorePreferences(activity, Sharedpreference.PDZ_left, obj.optString("centration_l_pdz"));
        Sharedpreference.onStorePreferences(activity,Sharedpreference.YFH_left,obj.optString("centration_l_height"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.bvd_left, obj.optString("centration_l_bvd"));
//        Sharedpreference.onStorePreferences(activity,Sharedpreference.admin_id,obj.optString("geometry_l_optimavsm"));

                Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type, obj.optString("frm_material"));

                if (obj.optString("frm_material").contains("METAL FRAME")) {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type_id, "1");
                } else if (obj.optString("frm_material").contains("PLASTIC FRAME")) {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type_id, "2");
                } else if (obj.optString("frm_material").contains("NYLOR")) {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type_id, "3");
                } else if (obj.optString("frm_material").contains("RIMLESS")) {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type_id, "4");
                }else{
                Sharedpreference.onStorePreferences(activity, Sharedpreference.frame_type_id, "0");}

                Sharedpreference.onStorePreferences(activity, Sharedpreference.Shape_bevel_id, obj.optString("frm_model"));
                if (obj.optString("frm_model").equals("")) {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.order_type_id, "0");
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.Shape_bevel_id, "");
                    mOrdertype_list.setSelection(0);
                    mShapebevel.setVisibility(View.GONE);
                    mOrderReview.setVisibility(View.VISIBLE);

                } else {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.order_type_id, "1");
                    mOrdertype_list.setSelection(1);
                    mShapebevel.setVisibility(View.VISIBLE);
                    mOrderReview.setVisibility(View.GONE);
                }
                Sharedpreference.onStorePreferences(activity, Sharedpreference.panto_angle, obj.optString("panangle"));
                Sharedpreference.onStorePreferences(activity, Sharedpreference.bow_angle, obj.optString("bowangle"));


                mOrderreference.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.orderreference, ""));
                if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.personaldata, "").equals("1")) {
                    mPersonaldata.setChecked(true);
                }
                mFirstname.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.firstname, ""));
                mEmployee_list.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.mEmployeeName, ""));
                mLastanme.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.lastname, ""));
                mOrderreference.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.orderreference, ""));

                mSphere_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.sphere_right, ""));
                mCyclind_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.cyclind_right, ""));
                mAxis_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.axis_right, ""));
                mAddition_right.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.addition_right, ""));

                mSphere_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.sphere_left, ""));
                mCyclind_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.cyclind_left, ""));
                mAxis_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.axis_left, ""));
                mAddition_left.setText(Sharedpreference.getSharedprefernce(activity, Sharedpreference.addition_left, ""));


                if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_right, "").contains("Finished") ||
                        Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("Finished") ||
                        Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_right, "").contains("FSV") ||
                        Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_typename_left, "").contains("FSV")) {

                    if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_right, "").equals("65")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_right, "0");
                    } else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_right, "").equals("70")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_right, "1");
                    } else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_right, "").equals("75")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_right, "2");
                    }


                if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_left, "").equals("65")) {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, "0");
                } else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_left, "").equals("70")) {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, "1");
                } else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_left, "").equals("75")) {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, "2");
                }
            }
            else  {
                    if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_right, "").equals("55")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_right, "0");
                    } else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_right, "").equals("60")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_right, "1");
                    } else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_right, "").equals("65")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_right, "2");
                    }else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_right, "").equals("70")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_right, "3");
                    }else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_right, "").equals("75")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_right, "4");
                    }


                    if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_left, "").equals("55")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, "0");
                    } else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_left, "").equals("60")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, "1");
                    } else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_left, "").equals("65")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, "2");
                    }else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_left, "").equals("70")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, "3");
                    }else if (Sharedpreference.getSharedprefernce(activity, Sharedpreference.lens_diameter_name_left, "").equals("75")) {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_diameter_id_left, "4");
                    }
                }

                mLenstype_list_right.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_right,""));
                mLenstype_list_left.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_left,""));
                mCoating_list.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_coatingcode,""));
                mTint_list.setText(obj.optString("coating_commercialTintName"));
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_individual,obj.optString("Indiv_Val"));
                onDataValues();
                onPortfolioList();
                onOrderListEdit();
                onDiameterList();
            } catch (Throwable t) {
                Log.d("PARSING ERROr", t.getLocalizedMessage());
            }
            onHideProgress(R.id.custom_progressbar,activity);

        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderEdit,"");
    }
}