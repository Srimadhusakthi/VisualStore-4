package com.visualstore.OnBoarding;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.widget.RelativeLayout;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.visualstore.BaseActivity;
import com.visualstore.Dashboard.MainActivity;
import com.visualstore.Database.DatabaseHelper;
import com.visualstore.Database.DatabaseHelperTint;
import com.visualstore.Model.LensEditData;
import com.visualstore.Model.LoginCredential;
import com.visualstore.Model.LoginResultData;
import com.visualstore.Model.MyProfileModel;
import com.visualstore.Model.ProductLensDataModel;
import com.visualstore.Model.ProductlensModel;
import com.visualstore.Model.StoreId;
import com.visualstore.Model.TintDataModel;
import com.visualstore.Model.TintModel;
import com.visualstore.R;
import com.visualstore.Retrfofit.Retro;
import com.visualstore.Retrfofit.RetroServices;
import com.visualstore.Retrfofit.RetroTokenService;
import com.visualstore.Sharedpreference.Sharedpreference;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {



    @BindView(R.id.textinput_username)
    protected TextInputLayout mTextinput_Username;

    @BindView(R.id.textinput_userpassword)
    protected TextInputLayout mTextinput_Userpassword;

    private Activity activity;
    private AwesomeValidation awesomeValidation;
    private RetroServices mRetroservice;
    private RetroTokenService mRetroserviceToken;

    @BindView(R.id.email)
    protected AppCompatAutoCompleteTextView mUserEmail;

    @BindView(R.id.password)
    protected AppCompatEditText mUserpassword;

    @BindView(R.id.login_parent_layout)
    protected RelativeLayout mLogin_parent_layout;

    private DatabaseHelper sqLiteDatabase;
    private DatabaseHelperTint mTint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activity = LoginActivity.this;
        awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        mRetroservice = Retro.get().createWithOutToken(RetroServices.class);
        mRetroserviceToken = Retro.get().createWithToken(RetroTokenService.class);
        addValidationtoviews();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);

        sqLiteDatabase = new DatabaseHelper(activity);
        mTint = new DatabaseHelperTint(activity);
    }

    @OnClick(R.id.login_button)
    protected   void onLogin(){
       if( isNetworkConnected(activity) == true){
        onSubmitform();
        }else{
           onSnackBar(mLogin_parent_layout,getString(R.string.interntconnectionalert) );
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        mTint.onDelete();
        sqLiteDatabase.onDelete();
//        Sharedpreference.onDeleteAllValues(activity);
    }

    /* Loginactivity validation views deatils*/

    private  void addValidationtoviews(){
     String username = ".{4,}";
     String password = ".{6,}";
//     awesomeValidation.addValidation(activity,R.id.textinput_username,Patterns.EMAIL_ADDRESS,R.string.invalid_email);
     awesomeValidation.addValidation(activity,R.id.textinput_userpassword,password,R.string.invalid_password);
        awesomeValidation.addValidation(activity,R.id.textinput_username,username,R.string.invalid_username);

    }

    /*Submitting forms*/
    private void onSubmitform(){
        if(awesomeValidation.validate()){

            LoginCredential credential = new LoginCredential();
            credential.setUsername(mUserEmail.getText().toString().trim());
            credential.setPassword(mUserpassword.getText().toString().trim());
            onLogins(credential);

            progressDialog.setTitle(activity.getResources().getString(R.string.loading));
           progressDialog.show();
        }
    }

    private void onLogins(LoginCredential loginCredential){
      mRetroservice.onLogin(loginCredential)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Observer<LoginResultData>() {
                  @Override
                  public void onSubscribe(Disposable d) {

                  }

                  @Override
                  public void onNext(LoginResultData loginResult) {
                      if(loginResult.getStatus().equals("200")){
                          StoreId.get().setAuth_id(loginResult.getData().getAuth_id());
                          Sharedpreference.onStorePreferences(activity,Sharedpreference.admin_id,loginResult.getData().getId());
                          Sharedpreference.onStorePreferences(activity,Sharedpreference.log_inauth,loginResult.getData().getAuth_id());
                          getLensTypeList();
//                          onSnackBar(mLogin_parent_layout,""+loginResult.getMessage());
                      }
                  }

                  @Override
                  public void onError(Throwable e) {
                      progressDialog.dismiss();
                       if(e.getLocalizedMessage().toString().equals("HTTP 406 Not Acceptable")){
                           onSnackBar(mLogin_parent_layout,"Please check username and password");
                       }else {
                           onSnackBar(mLogin_parent_layout, e.getLocalizedMessage());
                       }
                  }

                  @Override
                  public void onComplete() {


                  }
              });



    }



    private void getLensTypeList(){
        DatabaseHelper databaseHelper = new DatabaseHelper(activity);
        mRetroservice.getProductLens()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductlensModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(ProductlensModel productlensmodel) {

                        for(ProductLensDataModel productLensDataModel : productlensmodel.getData()){
                            databaseHelper.onInsertValues(productLensDataModel);
                    }}
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onComplete() {
                        onTint();
                    }
                });
    }


    private void onTint(){
        DatabaseHelperTint databaseHelperTint = new DatabaseHelperTint(activity);
        mRetroservice.getTintCodeList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TintModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TintModel tintModel) {
                        for(TintDataModel dataModel : tintModel.getData()){
                           databaseHelperTint.inserTintValues(dataModel);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        startActivity(new Intent(activity,MainActivity.class));
                        activity.finish();
                    }
                });
    }



    @Override
    public void onBackPressed() {

    }



}