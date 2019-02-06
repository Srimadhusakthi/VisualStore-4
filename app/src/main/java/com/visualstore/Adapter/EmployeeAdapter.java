package com.visualstore.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.visualstore.BaseActivity;
import com.visualstore.Dashboard.EmployeeManagement;
import com.visualstore.Model.EmployeeDataModel;
import com.visualstore.Model.EmployeeDataModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter

        extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> implements Filterable {
    private Activity mActivity;
    private ArrayList<EmployeeDataModel> mEmployeeList;
    private ArrayList<EmployeeDataModel> mFilterList;
    private int mSelctedItem = -1;
    public EmployeeAdapter(Activity activity, ArrayList<EmployeeDataModel> arrayList){
        mActivity = activity;
        mEmployeeList = arrayList;
        mFilterList = arrayList;
    }


    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.employee_list,viewGroup,false);
        return new EmployeeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder employeeHolder, int i) {
        EmployeeDataModel EmployeeDataModel = mFilterList.get(i);
        employeeHolder.mEmployeename.setText(EmployeeDataModel.getName());
        employeeHolder.mEmployeeListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Sharedpreference.getSharedprefernce(mActivity,Sharedpreference.lens_type_act,"").equals("1")){
                    mActivity.finish();
                }else {
                    mSelctedItem = i;
                    notifyDataSetChanged();
                }

                Sharedpreference.onStorePreferences(mActivity, Sharedpreference.mEmployeeId, EmployeeDataModel.getId());
                Sharedpreference.onStorePreferences(mActivity, Sharedpreference.mEmployeeName, EmployeeDataModel.getName());
            }
        });

        if(mSelctedItem == i){
            employeeHolder.mEmployeeListLayout.setBackground(mActivity.getDrawable(R.drawable.rippleeffet));
            employeeHolder.mEmployeename.setTextColor(Color.WHITE);
        }else{
            employeeHolder.mEmployeeListLayout.setBackgroundColor(Color.WHITE);
            employeeHolder.mEmployeename.setTextColor(Color.parseColor("#A1A1A1"));
        }

    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    public class EmployeeHolder extends RecyclerView.ViewHolder{
        private TextView mEmployeename;
        private RelativeLayout mEmployeeListLayout;

        public EmployeeHolder(@NonNull View itemView) {
            super(itemView);
            mEmployeename = (TextView)itemView.findViewById(R.id.employee_name);
            mEmployeeListLayout =(RelativeLayout) itemView.findViewById(R.id.employee_listlayout);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mFilterList = mEmployeeList;
                } else {
                    ArrayList<EmployeeDataModel> filteredList = new ArrayList<>();
                    for (EmployeeDataModel employeeDataModel : mFilterList) {
                        if (employeeDataModel.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(employeeDataModel);
                        }
                    }
                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (ArrayList<EmployeeDataModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}
