package com.visualstore.Dashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.visualstore.BaseActivity;
import com.visualstore.Database.DatabaseHelper;
import com.visualstore.Database.DatabaseHelperTint;
import com.visualstore.Model.DeleteOrderModel;
import com.visualstore.Model.LogOutModel;
import com.visualstore.Model.MyProfileModel;
import com.visualstore.Model.Result;
import com.visualstore.Model.StoreId;
import com.visualstore.R;
import com.visualstore.Retrfofit.Retro;
import com.visualstore.Retrfofit.RetroServices;
import com.visualstore.Retrfofit.RetroTokenService;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {


    private Activity activity;

    @BindView(R.id.toolbar)
    protected  Toolbar mToolbar;

    @BindView(R.id.fab)
    protected FloatingActionButton mFab;

    protected TextView username;

    protected TextView usermobilenumber;

    protected TextView useremail;


    private RetroTokenService mRetroTokenService;
    private DatabaseHelper sqLiteDatabase;
    private DatabaseHelperTint mTint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activity = MainActivity.this;
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setHomeAsUpIndicator(R.drawable.ic_chat);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mFab.setColorFilter(Color.WHITE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*user_details*/
        View v = navigationView.getHeaderView(0);
        username=(TextView) v.findViewById(R.id.username);
        usermobilenumber=(TextView)v.findViewById(R.id.usermobilenumber);
        useremail=(TextView)v.findViewById(R.id.useremail);
        ImageView  imageView = v.findViewById(R.id.imageView);
        imageView.setColorFilter(Color.WHITE);


        /*Retro Serivce connection establish*/
        mRetroTokenService = Retro.get().createWithToken(RetroTokenService.class);
//        onGetProfile();
        sqLiteDatabase = new DatabaseHelper(activity);
        mTint = new DatabaseHelperTint(activity);
        onProgress(R.id.custom_progressbar,activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onHideProgress(R.id.custom_progressbar,activity);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.user) {

            onUserInfo();
            Sharedpreference.onStorePreferences(activity,Sharedpreference.userinfo,"2");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_lensordering) {
            onLensOrderingActivity();
        } else if (id == R.id.nav_myorder) {
        } else if (id == R.id.nav_cart) {
            Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderType,"All Order");
            onNaviagationActivity();
        } else if (id == R.id.nav_sentorder) {
            onSentOrder();
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_employeemanagement) {
            startActivity(new Intent(activity,EmployeeManagement.class));
            Sharedpreference.onStorePreferences(activity,Sharedpreference.mEmployeeId,"");
            Sharedpreference.onStorePreferences(activity,Sharedpreference.mEmployeeName,"");

        }
        else if (id == R.id.nav_contactinformation) {
            onUserInfo();
            Sharedpreference.onStorePreferences(activity,Sharedpreference.userinfo,"1");
        }
        else if (id == R.id.nav_myaccount) {
            onUserInfo();
            Sharedpreference.onStorePreferences(activity,Sharedpreference.userinfo,"2");

        } else if(id == R.id.nav_logout){

            if(isNetworkConnected(activity)){

                onLogoutAlert();

            }else{

                onToast(activity,getResources().getString(R.string.interntconnectionalert));
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* Navigate to order activity with help of click event from mainactivity class
    * */

    @OnClick(R.id.sentorder)
    protected   void onSentOrder(){
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderType,"Sent Order");
        onNaviagationActivity();
    }

    @OnClick(R.id.incompleteorder)
    protected   void onInCompleteOrder(){
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderType,"InComplete Order");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderStatus,"1");
        onNaviagationActivity();
    }

    @OnClick(R.id.completeorder)
    protected   void onCompleteOrder(){
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderType,"Complete Order");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderStatus,"2");
        onNaviagationActivity();
    }

    @OnClick(R.id.neworder)
    protected   void onNewOrder(){
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderType,"New Order");
           onLensOrderingActivity();
    }



    private void onNaviagationActivity(){
        startActivity(new Intent(activity,OrderActivity.class));
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrder_id,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderid_id,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderEdit,"");

    }


    private void onLensOrderingActivity(){
        startActivity(new Intent(activity,LensOrdering.class));

        /*Clearing all values*/
        /*LensOrdeing */

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
        Sharedpreference.onStorePreferences(activity,Sharedpreference.order_type_id,"5");
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
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderEdit,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderType_id,"");
        Sharedpreference.onStorePreferences(activity,Sharedpreference.side,"");

    }

    private void onChatctivity(){
        startActivity(new Intent(activity,ChatActivity.class));
    }

    private void onUserInfo(){
        startActivity(new Intent(activity,ContactInformation.class));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_lensordering:
                    onLensOrderingActivity();
                    return true;
                case R.id.navigation_sentorder:
                    onSentOrder();
                    return  true;
                case R.id.navigation_chat:
                    onChatctivity();
                    return true;
            }
            return false;
        }
    };

    private  void onLogout(LogOutModel logOutModel){
        mRetroTokenService.onUserLogout(logOutModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {

                        mTint.onDelete();
                        sqLiteDatabase.onDelete();
                        Sharedpreference.onDeleteAllValues(activity);
                        onToast(activity,result.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {

                        onToast(activity,e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.exit(1);
                    }
                });
    }

    private void onGetProfile(){
        mRetroTokenService.onGetProfile().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MyProfileModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MyProfileModel myProfileModel) {


                          Sharedpreference.onStorePreferences(activity,Sharedpreference.name,myProfileModel.getData().getName());
                          Sharedpreference.onStorePreferences(activity,Sharedpreference.email,myProfileModel.getData().getEmail());
                          Sharedpreference.onStorePreferences(activity,Sharedpreference.phone,myProfileModel.getData().getPhone());
                          Sharedpreference.onStorePreferences(activity,Sharedpreference.username,myProfileModel.getData().getUsername());
                          Sharedpreference.onStorePreferences(activity,Sharedpreference.logged_in,myProfileModel.getData().getLogin_code());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                useremail.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.email,""));
                usermobilenumber.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.phone,""));
                username.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.name,""));
            }
        });
    }


    private void onLogoutAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.areyousurewanttologout));
        alert.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        alert.setPositiveButton(activity.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogOutModel logOutModel = new LogOutModel();
                logOutModel.setId(Sharedpreference.getSharedprefernce(activity,Sharedpreference.admin_id,""));
                logOutModel.setUsername(Sharedpreference.getSharedprefernce(activity,Sharedpreference.username,""));
                onLogout(logOutModel);
            }
        });
        alert.show();
    }

}
