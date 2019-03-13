package com.visualstore.Dashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visualstore.Adapter.EmployeeAdapter;
import com.visualstore.BaseActivity;
import com.visualstore.Model.DeletEmployeeModel;
import com.visualstore.Model.EmployeeAddNameModel;
import com.visualstore.Model.EmployeeDataModel;
import com.visualstore.Model.EmployeeModel;
import com.visualstore.Model.EmployeeResultModel;
import com.visualstore.Model.EmployeeUpdateNameModel;
import com.visualstore.Model.Result;
import com.visualstore.R;
import com.visualstore.Retrfofit.Retro;
import com.visualstore.Retrfofit.RetroServices;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EmployeeManagement extends BaseActivity {

    private  Activity activity;

    @BindView(R.id.employeemanagement_toolbar)
    protected Toolbar mEmployeeManagment_Toolbar;

    @BindView(R.id.employee_recyclerview)
    protected  RecyclerView mEmployee_recyclerview;

    private  ArrayList<EmployeeDataModel>  mEmployeeArrayList ;

    private  RetroServices mRetroservice;

    private RelativeLayout mEmployee_parent_layout;
    private EmployeeAdapter employeeAdapter;
    private String mEmployeeId;
    private String mEmployeeName;
    private int mEmployee = -1;

    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipe_refresh;

    @BindView(R.id.employee_name_save)
    protected AppCompatButton mEmployeename_save;


    @BindView(R.id.employee_name_cancel)
    protected AppCompatButton mEmployeename_cancel;


    @BindView(R.id.employee_name)
    protected AppCompatEditText employee_name;

    @BindView(R.id.employee_add_edit_layout)
    protected RelativeLayout mEmployee_add_edit_layout;

    @BindView(R.id.employee_header)
    protected TextView mEmployee_header;

    @BindView(R.id.employee_search)
    protected SearchView mEmployee_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_management);
        activity = EmployeeManagement.this;
        ButterKnife.bind(this);
        onRetroIntialize();
        onToolbar();
        onEmployeeRecyclerView();
        onSwipeRefresh();
        onEmployeeSearch();
        onProgress(R.id.custom_progressbar,activity);
        onEmployeeList();


    }

    private void onRetroIntialize(){
        mRetroservice = Retro.get().createWithOutToken(RetroServices.class);

    }
    private void onSwipeRefresh(){
        mSwipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onEmployeeList();
                mSwipe_refresh.setRefreshing(false);
                onProgress(R.id.custom_progressbar,activity);
            }
        });
    }

    private void onEmployeeList(){
        if(BaseActivity.isNetworkConnected(activity) == true){
            onEmployeeDetailList();
        }else{
            onSnackBar(mEmployee_parent_layout,getString(R.string.interntconnectionalert) );
        }
    }

    private void onToolbar(){
        setSupportActionBar(mEmployeeManagment_Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.employeemanagement));
        mEmployeeManagment_Toolbar.setTitleTextColor(Color.WHITE);
        mEmployeeManagment_Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        mEmployee_parent_layout = (RelativeLayout) findViewById(R.id.employee_parent_layout);
    }


    private  void onEmployeeRecyclerView(){
          RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
          mEmployee_recyclerview.setLayoutManager(layoutManager);
          mEmployee_recyclerview.setItemAnimator(new DefaultItemAnimator());
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_management,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mEmployeeId = Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeId,"");
        mEmployeeName = Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeName,"");
        int id  = item.getItemId();
        if(id == R.id.action_item_add){
            mEmployee  = 2;
            employee_name.setText("");
            mEmployee_add_edit_layout.setVisibility(View.VISIBLE);
            mEmployee_header.setText("Add Employee");

        } else  if(id  == R.id.action_item_edit){

            mEmployee_header.setText("Update Employee");
            if(mEmployeeId.equals("")){
                onSnackBar(mEmployee_parent_layout,getResources().getString(R.string.noitemselected));
            } else{
                mEmployee  = 1;
                onEmployeeName();
                mEmployee_add_edit_layout.setVisibility(View.VISIBLE);
            }
         }else if(id == R.id.action_item_delete){
            if(mEmployeeId.equals("")){
                onSnackBar(mEmployee_parent_layout,getResources().getString(R.string.noitemselected));
            }else{
                onDeleteAlert();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.employee_name_save)
    protected void onSaveEmployee(){
        if(BaseActivity.isNetworkConnected(activity) == true){
        if(employee_name.getText().toString().trim().length() == 0){
            onSnackBar(mEmployee_parent_layout,activity.getResources().getString(R.string.employeenamenotempty));
        }else if (mEmployee == 1){
            EmployeeUpdateNameModel updateNameModel= new EmployeeUpdateNameModel();
            updateNameModel.setId(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeId,""));
            updateNameModel.setName(employee_name.getText().toString().trim());
            onUpdateEmployee(updateNameModel);
        } else{
            EmployeeAddNameModel employeeAddNameModel = new EmployeeAddNameModel();
            employeeAddNameModel.setName(employee_name.getText().toString().trim());
            onAddEmployee(employeeAddNameModel);
        }
            onProgress(R.id.custom_progressbar,activity);
        }else{
            onSnackBar(mEmployee_parent_layout,getString(R.string.interntconnectionalert) );
        }

    }


    @OnClick(R.id.employee_name_cancel)
    protected void onCancelEmployee(){
        mEmployee_add_edit_layout.setVisibility(View.GONE);
    }


    private void onDeleteAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.areyousurewanttodelete) + "' "+Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeName,"") + " '");
        alert.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.setPositiveButton(activity.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeletEmployeeModel deletEmployeeModel = new DeletEmployeeModel();
                deletEmployeeModel.setId(""+Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeId,"") );
                onDeleteEmployee(deletEmployeeModel);
                onProgress(R.id.custom_progressbar,activity);
            }
        });
        alert.show();
    }



    private  void onEmployeeName(){
        employee_name.setText(Sharedpreference.getSharedprefernce(activity,Sharedpreference.mEmployeeName,""));
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
                        mEmployeeArrayList = new ArrayList<>();

                        for(EmployeeDataModel dataModel : employeeModel.getData()){
                            mEmployeeArrayList.add(dataModel);
                        }
                        employeeAdapter = new EmployeeAdapter(activity,mEmployeeArrayList);
                        mEmployee_recyclerview.setAdapter(employeeAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.mEmployeeId, "");
                        Sharedpreference.onStorePreferences(activity, Sharedpreference.mEmployeeName, "");
                        onHideProgress(R.id.custom_progressbar,activity);

                    }
                });

    }


    private  void onAddEmployee(EmployeeAddNameModel employeeAddNameModel){
                mRetroservice.addEmployeeName(employeeAddNameModel).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EmployeeResultModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EmployeeResultModel employeeResultModel) {
                        onSnackBar(mEmployee_parent_layout,employeeResultModel.getMessage());
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onComplete() {
                        
                        mEmployee_add_edit_layout.setVisibility(View.GONE);
                        onEmployeeDetailList();
                        onHideProgress(R.id.custom_progressbar,activity);
                    }
                });

    }


    private  void onUpdateEmployee(EmployeeUpdateNameModel updateNameModel){
        mRetroservice.updateEmployeeModel(updateNameModel).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EmployeeResultModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(EmployeeResultModel employeeResultModel) {
                        onSnackBar(mEmployee_parent_layout,employeeResultModel.getMessage());
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                        
                        mEmployee_add_edit_layout.setVisibility(View.GONE);
                        onEmployeeDetailList();
                        onHideProgress(R.id.custom_progressbar,activity);
                    }
                });


    }


    private void onDeleteEmployee(DeletEmployeeModel mEmployeeId){
        mRetroservice.deleteEmployee(mEmployeeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result retroResult) {
                      onSnackBar(mEmployee_parent_layout,retroResult.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        onHideProgress(R.id.custom_progressbar,activity);
                           onEmployeeDetailList();
                    }
                });
        }


        private void onEmployeeSearch(){
        mEmployee_search.setQueryHint("Search the employee");
            mEmployee_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    employeeAdapter.getFilter().filter(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    employeeAdapter.getFilter().filter(s);
                    return false;
                }
            });
        }

}
