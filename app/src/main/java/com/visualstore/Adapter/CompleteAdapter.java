package com.visualstore.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.visualstore.Model.GetOrderData;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.CompleteAdapterHolder> implements Filterable {

    private Activity mActivity;
    private ArrayList<GetOrderData> mOrderList;
    private ArrayList<GetOrderData> mFilterOrderList;
    private int selecteditem = -1;

    public CompleteAdapter(Activity activity,ArrayList<GetOrderData> mOrderArrayList){
        mActivity = activity;
        mOrderList = mOrderArrayList;
        mFilterOrderList = mOrderArrayList;

    }

    @Override
    public CompleteAdapter.CompleteAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(mActivity).inflate(R.layout.order_list,viewGroup,false);
        return new CompleteAdapter.CompleteAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteAdapter.CompleteAdapterHolder CompleteAdapterHolder, final int i) {
        GetOrderData orderList = mFilterOrderList.get(i);
        CompleteAdapterHolder.mOrderDate.setText(orderList.getOrder_date());
        CompleteAdapterHolder.mOrderId.setText(orderList.getOrder_id());
        CompleteAdapterHolder.mOrderConsumerName.setText(orderList.getConsumer_name());
//        if(orderList.getStatus().equals("1")){
//            CompleteAdapterHolder.mOrderStatus.setText(mActivity.getResources().getString(R.string.incomplete));
//        }else{
//            CompleteAdapterHolder.mOrderStatus.setText(mActivity.getResources().getString(R.string.complete));
////        }
//        CompleteAdapterHolder.mOrderlist_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selecteditem = i;
//
//                notifyDataSetChanged();
//
////                if(orderList.getStatus().equals("2")){
//                    Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mOrderStatus,"2");
////                } else  if(orderList.getStatus().equals("1")){
////                    Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mOrderStatus,"1");
////                }
//
//                Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mOrder_id,orderList.getId());
////                BaseActivity.onToast(mActivity,orderList.getId());
//                Log.d("ORDERADAP" ,"-----"+ orderList.getId() +"--" + orderList.getStatus());
//            }
//        });
//
//        if(selecteditem == i){
//
//            CompleteAdapterHolder.mOrderlist_layout.setBackground(mActivity.getDrawable(R.drawable.rippleeffet));
//        }else{
//
//            CompleteAdapterHolder.mOrderlist_layout.setBackgroundColor(Color.WHITE);
//        }

    }

    @Override
    public int getItemCount() {
        return mFilterOrderList.size();
    }

    public class CompleteAdapterHolder extends RecyclerView.ViewHolder{
        private TextView mOrderDate,mOrderStatus,mOrderConsumerName,mOrderId;
        private LinearLayout mOrderlist_layout;
        public CompleteAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mOrderConsumerName = (TextView)itemView.findViewById(R.id.order_cosumername);
            mOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
            mOrderId = (TextView)itemView.findViewById(R.id.order_id);
            mOrderDate = (TextView)itemView.findViewById(R.id.order_date);
            mOrderlist_layout=(LinearLayout)itemView.findViewById(R.id.orderlist_layout);
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
                    ArrayList<GetOrderData>  mFilter  = new ArrayList<>();

                    for(GetOrderData orderModel : mFilterOrderList){
                        if(orderModel.getOrder_date().toLowerCase().contains(mFiltervalue)
                                ||
                                orderModel.getOrder_id().toLowerCase().contains(mFiltervalue) ||
                                orderModel.getConsumer_name().toLowerCase().contains(mFiltervalue)){
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
                mFilterOrderList =  (ArrayList<GetOrderData>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
