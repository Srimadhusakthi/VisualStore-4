package com.visualstore.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visualstore.Model.EmployeeModel;
import com.visualstore.Model.LensOrderModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

public class LensDataAdapter extends RecyclerView.Adapter<LensDataAdapter.LensOrderHolder> implements Filterable {
    private Activity mActivity;
    private ArrayList<LensOrderModel> mLensOrderList;
    private ArrayList<LensOrderModel> mFilterLensOrderList;
    private int mSelctedItem = -1;
    public LensDataAdapter(Activity activity, ArrayList<LensOrderModel> arrayList){
        mActivity = activity;
        mLensOrderList = arrayList;
        mFilterLensOrderList = arrayList;
    }


    @NonNull
    @Override
    public LensDataAdapter.LensOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.employee_list,viewGroup,false);
        return new LensDataAdapter.LensOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LensDataAdapter.LensOrderHolder lenorderHolder, int i) {
        LensOrderModel lensOrderModel = mLensOrderList.get(i);
        lenorderHolder.mEmployeename.setText(lensOrderModel.getmName());
        lenorderHolder.mEmployeeListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelctedItem = i;
                notifyDataSetChanged();
//                Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mEmployeeId,employeeModel.getData().get(i).getId());
//                Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mEmployeeName,employeeModel.getData().get(i).getName());

            }
        });

        if(mSelctedItem == i){
            lenorderHolder.mEmployeeListLayout.setBackground(mActivity.getDrawable(R.drawable.rippleeffet));
            lenorderHolder.mEmployeename.setTextColor(Color.WHITE);
        }else{
            lenorderHolder.mEmployeeListLayout.setBackgroundColor(Color.WHITE);
            lenorderHolder.mEmployeename.setTextColor(Color.parseColor("#A1A1A1"));
        }

    }

    @Override
    public int getItemCount() {
        return mFilterLensOrderList.size();
    }

    public class LensOrderHolder extends RecyclerView.ViewHolder{
        private TextView mEmployeename;
        private RelativeLayout mEmployeeListLayout;

        public LensOrderHolder(@NonNull View itemView) {
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
                String mFilterResult = constraint.toString();
                if(mFilterResult.length() == 0){
                    mFilterLensOrderList = mLensOrderList;
                }
                else {
                    ArrayList<LensOrderModel> mFilterList = new ArrayList<>();

                    for (LensOrderModel orderModel : mLensOrderList) {
                        if (orderModel.getmName().toLowerCase().contains(mFilterResult)) {
                            mFilterList.add(orderModel);
                        }
                    }

                    mFilterLensOrderList = mFilterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterLensOrderList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }
}


