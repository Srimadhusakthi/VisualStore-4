package com.visualstore.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.visualstore.BaseActivity;
import com.visualstore.Model.GetOrderData;
import com.visualstore.Model.GetOrderModel;
import com.visualstore.Model.GetOrderData;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderAdapterHolder> implements Filterable {

    private Activity mActivity;
    private ArrayList<GetOrderData> mOrderList;
    private ArrayList<GetOrderData> mFilterOrderList;
    private int selecteditem = -1;

    public OrderAdapter(Activity activity,ArrayList<GetOrderData> mOrderLists){
        mActivity = activity;
        mOrderList = mOrderLists;
        mFilterOrderList = mOrderLists;

    }

    @Override
    public OrderAdapter.OrderAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(mActivity).inflate(R.layout.order_list,viewGroup,false);
        return new OrderAdapter.OrderAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderAdapterHolder orderAdapterHolder, final int i) {
        GetOrderData orderList = mFilterOrderList.get(i);
        orderAdapterHolder.mOrderDate.setText(orderList.getOrder_date());
        orderAdapterHolder.mOrderId.setText(orderList.getOrder_id());

//        try {
        if(orderList.getConsumer_name().equals("FirstName LastName")){
            orderAdapterHolder.mOrderConsumerName.setText("");
        }else {
            orderAdapterHolder.mOrderConsumerName.setText(orderList.getConsumer_name());
        }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        Log.d("ORDERADA",orderList.getStatus() +"----" + mOrderList.size() +"====="+ mFilterOrderList.size());
        if(orderList.getStatus().equals("1")){
            orderAdapterHolder.mOrderStatus.setText(mActivity.getResources().getString(R.string.incomplete));
        }else if(orderList.getStatus().equals("2")){
            orderAdapterHolder.mOrderStatus.setText(mActivity.getResources().getString(R.string.complete));
        }
        orderAdapterHolder.mOrderlist_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteditem = i;
                notifyDataSetChanged();
                if(orderList.getStatus().equals("2")){
                    Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mOrderStatus,"2");
                } else  if(orderList.getStatus().equals("1")){
                    Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mOrderStatus,"1");
                }
                Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mOrder_id,orderList.getId());
                Sharedpreference.onStorePreferences(mActivity,Sharedpreference.mOrderid_id,orderList.getOrder_id());
                Log.d("ORDERADAP" ,"-----"+ orderList.getId() +"--" + orderList.getStatus());
            }
        });

        if(selecteditem == i){
            orderAdapterHolder.mOrderlist_layout.setBackground(mActivity.getDrawable(R.drawable.rippleeffet));
        }else{

            orderAdapterHolder.mOrderlist_layout.setBackgroundColor(Color.WHITE);
        }



    }

    @Override
    public int getItemCount() {
        return mFilterOrderList.size();
    }

    public class OrderAdapterHolder extends RecyclerView.ViewHolder{
        private TextView mOrderDate,mOrderStatus,mOrderConsumerName,mOrderId;
        private LinearLayout mOrderlist_layout;
        public OrderAdapterHolder(@NonNull View itemView) {
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
                        if(       orderModel.getOrder_date().toLowerCase().contains(mFiltervalue)||
                                  orderModel.getOrder_id().toLowerCase().contains(mFiltervalue) ||
                                  orderModel.getConsumer_name().toLowerCase().contains(mFiltervalue)
                                ){
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
