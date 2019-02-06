package com.visualstore.Dashboard;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.visualstore.Adapter.CoatingAdapter;
import com.visualstore.Adapter.EmployeeAdapter;
import com.visualstore.Adapter.ProductLensAdapter;
import com.visualstore.Adapter.TintAdapter;
import com.visualstore.BaseActivity;
import com.visualstore.Database.DatabaseHelper;
import com.visualstore.Database.DatabaseHelperTint;
import com.visualstore.Model.CoatingTintDataModel;
import com.visualstore.Model.CoatingTintModel;
import com.visualstore.Model.EmployeeDataModel;
import com.visualstore.Model.EmployeeModel;
import com.visualstore.Model.GetOrderData;
import com.visualstore.Model.ProductLensDataModel;
import com.visualstore.Model.ProductlensModel;
import com.visualstore.Model.TintDataModel;
import com.visualstore.R;
import com.visualstore.Retrfofit.Retro;
import com.visualstore.Retrfofit.RetroServices;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LensDataSearchList extends BaseActivity {

    private Activity mActivity;

    @BindView(R.id.lensorder_detail_title)
    protected Toolbar mToolbar;

    @BindView(R.id.list_search)
    protected SearchView mLensSearch;


    @BindView(R.id.order_list)
    protected RecyclerView mLensList;

    @BindView(R.id.swipe_refresh_lensorder)
    protected SwipeRefreshLayout mRefresh_lensorder;

    private RetroServices mRetroservice;
    private ArrayList<EmployeeDataModel> mLenOrderList;
    private EmployeeAdapter mEmpAdapter;
    private ProductLensAdapter mProdutAdapter;
    private CoatingAdapter mCoatingTintAdapter;
    private TintAdapter mTintAdapter;
    private ArrayList<ProductlensModel> mProductList;
    private ArrayList<CoatingTintDataModel> mCoatinglist;
    private ArrayList<TintDataModel> mTintList;
    private ArrayList<ProductLensDataModel> mLenTypeList;
    private ArrayList<ProductLensDataModel> ProductLensDataModelLists = new ArrayList<>();
    private DatabaseHelper mDBHelper;
    private DatabaseHelperTint mDatabseTint;
    private String mAdditionValues;
    private String mValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lens_data_list);
        ButterKnife.bind(this);
        mActivity = LensDataSearchList.this;
        mDBHelper = new DatabaseHelper(mActivity);
        mDatabseTint = new DatabaseHelperTint(mActivity);
        onToolbar();
        onRecyclerviewIntialize();
        onSwipeRefresh();

    }

    @Override
    protected void onResume() {
        super.onResume();
   }

    public void onToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         activity.finish();
            }
        });
        mRetroservice = Retro.get().createWithOutToken(RetroServices.class);

        if(BaseActivity.mLensRefractiontxt.toLowerCase().trim().equals("both") && !BaseActivity.mAdditionLeft.toLowerCase().trim().isEmpty() && !BaseActivity.mAdditionRight.toLowerCase().trim().isEmpty()) {
            mAdditionValues = "1";
        } else if(BaseActivity.mLensRefractiontxt.toLowerCase().trim().equals("right") && !BaseActivity.mAdditionRight.toLowerCase().trim().isEmpty()){
            mAdditionValues = "1";
        } else if(BaseActivity.mLensRefractiontxt.toLowerCase().trim().equals("left") && !BaseActivity.mAdditionLeft.toLowerCase().trim().isEmpty()){
            mAdditionValues = "1";
        }else{
            mAdditionValues = "0";
        }

        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_type_act,"").equals("2")){
            getLensTypeList(mAdditionValues);
            onSearchProductView();
            getSupportActionBar().setTitle(getResources().getString(R.string.lenstype));

        }  else if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_type_act,"").equals("3")) {
            onGetCoating(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mlens_coating_id,""));
            getSupportActionBar().setTitle(getResources().getString(R.string.coating));
            onSearchCoatingView();
        }
           else if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_type_act,"").equals("4")) {
            onGetTint();
            onSearchTintView();
            getSupportActionBar().setTitle(getResources().getString(R.string.tint));
        }
            else{
            onEmployeeDetailList();
            onSearchEmployeeView();
            getSupportActionBar().setTitle(getResources().getString(R.string.employeename));
        }
    }

    private void onRecyclerviewIntialize(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        mLensList.setLayoutManager(layoutManager);
        mLensList.setItemAnimator(new DefaultItemAnimator());
    }


    private void onSwipeRefresh(){
        mRefresh_lensorder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh_lensorder.setRefreshing(false);
            }
        });
    }


    private  void onEmployeeDetailList(){
        mRetroservice.getEmployeeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EmployeeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(EmployeeModel employeeModel) {
                        mLenOrderList = new ArrayList<>();

                        for(EmployeeDataModel dataModel : employeeModel.getData()){
                            mLenOrderList.add(dataModel);
                        }
                        mEmpAdapter = new EmployeeAdapter(activity,mLenOrderList);
                        mLensList.setAdapter(mEmpAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                }); }



    private void getLensTypeList(String mvalues) {
        mLenTypeList = new ArrayList<>();
        if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_selected_portfolio_id,"").equals("0")){
             mValue  = "1";
        }else{
             mValue  = "2";
        }
        mLenTypeList = mDBHelper.getLensType(mValue,mvalues);
        mProdutAdapter = new ProductLensAdapter(activity, mLenTypeList);
        mLensList.setAdapter(mProdutAdapter);
    }


    private void onGetCoating(String mLenscode){
        mRetroservice.getCoatingCode(mLenscode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoatingTintModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(CoatingTintModel coatingCodeListModel) {
                           mCoatinglist = new ArrayList<>();

                        for(CoatingTintDataModel getcoatData : coatingCodeListModel.getData()){
                            mCoatinglist.add(getcoatData);
                        }
                           mCoatingTintAdapter = new CoatingAdapter(activity,mCoatinglist);
                           mLensList.setAdapter(mCoatingTintAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        onToast(activity,e.getLocalizedMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }



    private void onGetTint(){
        mTintList = new ArrayList<>();
        mTintList = mDatabseTint.getAllValues();
        mTintAdapter = new TintAdapter(activity,mTintList);
        mLensList.setAdapter(mTintAdapter);
    }

    @Override
    public void onBackPressed() {

    }

    private void onSearchProductView(){
        mLensSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mProdutAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mProdutAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    private void onSearchTintView(){
        mLensSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mTintAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mTintAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void onSearchCoatingView(){
        mLensSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mCoatingTintAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mCoatingTintAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    private void onSearchEmployeeView(){
        mLensSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mEmpAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mEmpAdapter.getFilter().filter(s);
                return false;
            }
        });
    }


}
