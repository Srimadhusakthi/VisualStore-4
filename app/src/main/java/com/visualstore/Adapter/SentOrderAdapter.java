package com.visualstore.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.visualstore.Model.OrderListModel;
import com.visualstore.Model.SentOrderDataModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;


public class SentOrderAdapter extends RecyclerView.Adapter<SentOrderAdapter.OrderAdapterHolder> implements Filterable {

    private Activity mActivity;
    private ArrayList<SentOrderDataModel> mOrderList;
    private ArrayList<SentOrderDataModel> mFilterOrderList;
    private int selecteditem = -1;

    public SentOrderAdapter(Activity activity,ArrayList<SentOrderDataModel> mOrderArrayList){
        mActivity = activity;
        mOrderList = mOrderArrayList;
        mFilterOrderList = mOrderArrayList;

    }

    @Override
    public OrderAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(mActivity).inflate(R.layout.sentorder_list,viewGroup,false);
        return new  OrderAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapterHolder orderAdapterHolder, final int i) {
        SentOrderDataModel orderList = mFilterOrderList.get(i);
        orderAdapterHolder.mSentOrderDate.setText(orderList.getAdded_date());
        orderAdapterHolder.mSentOrderref.setText(orderList.getOrder_reference());
        orderAdapterHolder.mSentOrderstatus.setText(orderList.getStatus());
        orderAdapterHolder.mSentOrderno.setText(orderList.getOrder_id());
        orderAdapterHolder.mSentOrderlist_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteditem = i;
                notifyDataSetChanged();
                Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mOrder_id,orderList.getOrder_id());
            }
        });

        if(selecteditem == i){

            orderAdapterHolder.mSentOrderlist_layout.setBackground(mActivity.getDrawable(R.drawable.rippleeffet));
        }else{

            orderAdapterHolder.mSentOrderlist_layout.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return mFilterOrderList.size();
    }

    public class OrderAdapterHolder extends RecyclerView.ViewHolder{
        private TextView mSentOrderDate,mSentOrderref,mSentOrderno,mSentOrderstatus;
        private LinearLayout mSentOrderlist_layout;
        private CheckBox order_checkbox;
        public OrderAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mSentOrderDate = (TextView)itemView.findViewById(R.id.sentorder_date);
            mSentOrderref = (TextView)itemView.findViewById(R.id.sentorder_ref);
            mSentOrderno = (TextView)itemView.findViewById(R.id.sentorder_no);
            mSentOrderstatus = (TextView)itemView.findViewById(R.id.sentorder_status);
            mSentOrderlist_layout=(LinearLayout)itemView.findViewById(R.id.sentorderlist_layout);
        }}

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String  mFiltervalue = constraint.toString();
                if(mFiltervalue.isEmpty()){
                    mFilterOrderList = mOrderList;
                }
                else{
                    ArrayList<SentOrderDataModel>  mFilter  = new ArrayList<>();
                    
                    for(SentOrderDataModel orderModel : mFilterOrderList){
                        if(orderModel.getOrder_date().toLowerCase().contains(mFiltervalue) || orderModel.getStatus().toLowerCase().contains(mFiltervalue) ||
                        orderModel.getOrder_reference().toLowerCase().contains(mFiltervalue) || orderModel.getOrder_id().toLowerCase().contains(mFiltervalue)){
                            mFilter.add(orderModel);
                        }
                    }
                    mFilterOrderList = mFilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterOrderList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilterOrderList =  (ArrayList<SentOrderDataModel>) results.values;
                    notifyDataSetChanged();
            }
        };
    }
}
