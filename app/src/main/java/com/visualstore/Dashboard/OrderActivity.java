package com.visualstore.Dashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.visualstore.Adapter.OrderAdapter;
import com.visualstore.Adapter.SentOrderAdapter;
import com.visualstore.BaseActivity;
import com.visualstore.Model.DeletEmployeeModel;
import com.visualstore.Model.DeleteOrderModel;
import com.visualstore.Model.GetOrderData;
import com.visualstore.Model.GetOrderModel;
import com.visualstore.Model.OrderListModel;
import com.visualstore.Model.Result;
import com.visualstore.Model.SentOrderDataModel;
import com.visualstore.Model.SentOrderModel;
import com.visualstore.R;
import com.visualstore.Retrfofit.Retro;
import com.visualstore.Retrfofit.RetroServices;
import com.visualstore.Retrfofit.RetroTokenService;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderActivity extends BaseActivity {


    private Activity activity;


    @BindView(R.id.order_toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.order_searchview)
    protected SearchView mCart_searchview;

    @BindView(R.id.order_recyclerview)
    protected RecyclerView mOrderRecyclerview;


    private ArrayList<SentOrderDataModel> mSentOrderList;

    @BindView(R.id.order_header)
    protected LinearLayout mOrderHeader;


    @BindView(R.id.sentorder_header)
    protected LinearLayout mSentOrderHeader;

    private OrderAdapter mOderAdapter;

    @BindView(R.id.order_parent_layout)
    protected RelativeLayout mOrder_parent_layout;

    /*Retrofit Service*/
    private RetroServices mRetroservice;

    private ArrayList<GetOrderData> mOrderList;

    @BindView(R.id.swipe_order_layout)
    protected SwipeRefreshLayout mSwipe_order_layout;

    private SentOrderAdapter sentOrderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        activity = OrderActivity.this;
        onToolbar();
        onRecyclerview();
        onServiceIntialize();
        onSwipeRefresh();
        onProgress(R.id.custom_progressbar,activity);
    }


    private void onToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(""+Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrderType,""));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(BaseActivity.isNetworkConnected(activity) == true){
            onManageOrderType();
        }else{
            onSnackBar(mOrder_parent_layout,getString(R.string.interntconnectionalert) );
        }
    }

    private void onSwipeRefresh(){
        mSwipe_order_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onManageOrderType();
                mSwipe_order_layout.setRefreshing(false);
                onProgress(R.id.custom_progressbar,activity);
            }
        });
    }

    private void onServiceIntialize(){
        mRetroservice = Retro.get().createWithOutToken(RetroServices.class);
    }

    private void onManageOrderType(){
        mOrderList = new ArrayList<>();
        String mAdinId = Sharedpreference.getSharedprefernce(activity,Sharedpreference.admin_id,"");
        String mOrderStatus = Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrderStatus,"");

        if (Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrderType,"").equals("Sent Order")){
            mSentOrderHeader.setVisibility(View.VISIBLE);
            onSuccesOrderDeatils(mAdinId);
            onSearchSentOrderView();
        }else if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrderType,"").equals("All Order")){
            mOrderHeader.setVisibility(View.VISIBLE);
            onGetAllOrderDetails(mAdinId);
            onAllSearchOrderView();
        } else{
            mOrderHeader.setVisibility(View.VISIBLE);
            mOrderHeader.setVisibility(View.VISIBLE);
            onGetOrderDetails(mOrderStatus,mAdinId);
            onSearchOrderView();
        }
        onProgress(R.id.custom_progressbar,activity);

    }

    private void onSearchOrderView(){
        mCart_searchview.setQueryHint(activity.getResources().getString(R.string.searchorderreferenceid));
        mCart_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mOderAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mOderAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    private void onAllSearchOrderView(){
        mCart_searchview.setQueryHint(activity.getResources().getString(R.string.searchorderreferenceid));
        mCart_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mOderAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mOderAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void onSearchSentOrderView(){
        mCart_searchview.setQueryHint(activity.getResources().getString(R.string.searchorderreferenceid));
        mCart_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                sentOrderAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sentOrderAdapter.getFilter().filter(s);
                return false;
            }
        });
    }


    private void onRecyclerview(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        mOrderRecyclerview.setLayoutManager(layoutManager);
        mOrderRecyclerview.setItemAnimator(new DefaultItemAnimator());

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrderType,"").equals("Sent Order")){

        }else{
            getMenuInflater().inflate(R.menu.order,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id  = item.getItemId();

        if(id == R.id.order_edit){

            Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderEdit,"editorder");
            if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrder_id,"").isEmpty()){
                onSnackBar(mOrder_parent_layout,getResources().getString(R.string.pleaseselect) +" " +  getResources().getString(R.string.yourorder));
            }else{
                onLensClearLensvalue();
            }
            return  true;
        }
        else if(id == R.id.order_delete){
            if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrderid_id,"").isEmpty()){
                 onSnackBar(mOrder_parent_layout,getResources().getString(R.string.pleaseselect) +" " +  getResources().getString(R.string.yourorder));
            }else{
                onDeleteAlert();
            }
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void onLensClearLensvalue(){
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

    }

    private void onDeleteAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.areyousurewanttodelete) + " " + activity.getResources().getString(R.string.thisorder));
        alert.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.setPositiveButton(activity.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteOrderModel deleteOrderModel = new DeleteOrderModel();
                deleteOrderModel.setOrder_id(""+Sharedpreference.getSharedprefernce(activity,Sharedpreference.mOrderid_id,"") );
                onDeleteOrder(deleteOrderModel);
            }
        });
        alert.show();
    }

    private void onDeleteOrder(DeleteOrderModel mEmployeeId){
        mRetroservice.onDeleteOrder(mEmployeeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result retroResult) {
                        onSnackBar(mOrder_parent_layout,retroResult.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onSnackBar(mOrder_parent_layout,e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        onHideProgress(R.id.custom_progressbar,activity);
                        onManageOrderType();

                    }
                });
    }



    private void onGetOrderDetails(String mStatus,String  admin_id){
        mRetroservice.getOrderValues(mStatus,admin_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetOrderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetOrderModel getOrderModel) {

                        for(GetOrderData getOrderData : getOrderModel.getData()){
                            mOrderList.add(getOrderData);
                        }
                        mOderAdapter = new OrderAdapter(activity,mOrderList);
                        mOrderRecyclerview.setAdapter(mOderAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                        onSnackBar(mOrder_parent_layout,e.getMessage());
                    }

                    @Override
                    public void onComplete() {


                        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderid_id,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrder_id,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderEdit,"");
                        onHideProgress(R.id.custom_progressbar,activity);
                    }
                });

    }


    private void onGetAllOrderDetails(String  admin_id){
        mRetroservice.getAllOrderValues(admin_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetOrderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetOrderModel getOrderModel) {

                        for(GetOrderData getOrderData: getOrderModel.getData()){
                            mOrderList.add(getOrderData);
                        }
                        mOderAdapter = new OrderAdapter(activity,mOrderList);
                        mOrderRecyclerview.setAdapter(mOderAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {

                        onSnackBar(mOrder_parent_layout,e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderid_id,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrder_id,"");
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.mOrderEdit,"");
                        onHideProgress(R.id.custom_progressbar,activity);
                    }
                });

    }

    private void onSuccesOrderDeatils(String mAdmin_id){
        mRetroservice.getSuccessOrder(mAdmin_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SentOrderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SentOrderModel sentOrderModel) {

                        mSentOrderList = new ArrayList<>();
                        for(SentOrderDataModel sentOrder : sentOrderModel.getData()){
                            mSentOrderList.add(sentOrder);
                        }
                         sentOrderAdapter = new SentOrderAdapter(activity,mSentOrderList);
                        mOrderRecyclerview.setAdapter(sentOrderAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                            onHideProgress(R.id.custom_progressbar,activity);
                       
                    }
                });
    }

}
