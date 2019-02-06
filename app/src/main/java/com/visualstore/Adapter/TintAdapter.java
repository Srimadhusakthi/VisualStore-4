package com.visualstore.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visualstore.BaseActivity;
import com.visualstore.Model.TintDataModel;
import com.visualstore.Model.TintDataModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

public class TintAdapter extends RecyclerView.Adapter<TintAdapter.CoatingTintHolder> implements Filterable {

    private ArrayList<TintDataModel> mCoatingTintlits;
    private ArrayList<TintDataModel> mFilterArraylist;
    private Activity activity;

    public TintAdapter(Activity mActivity, ArrayList<TintDataModel> list){
        activity = mActivity;
        mCoatingTintlits = list;
        mFilterArraylist = list;
    }


    @NonNull
    @Override
    public TintAdapter.CoatingTintHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.employee_list,viewGroup,false);
        return new TintAdapter.CoatingTintHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TintAdapter.CoatingTintHolder mCoatingTintHolder, int i) {
        TintDataModel model = mFilterArraylist.get(i);
        mCoatingTintHolder.mProductlensename.setText(model.getName());
        mCoatingTintHolder.mCoatingTintlitsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_tintname, model.getName());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_tintcode, model.getCode());
                Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_tintdisplay_name, model.getDisplay_name());
                       activity.finish();

            }
        });


    }

    @Override
    public int getItemCount() {
        return mFilterArraylist.size();
    }

    public class CoatingTintHolder extends RecyclerView.ViewHolder{
        private TextView mProductlensename;
        private RelativeLayout mCoatingTintlitsLayout;
        public CoatingTintHolder(@NonNull View itemView) {
            super(itemView);
            mProductlensename = (TextView)itemView.findViewById(R.id.employee_name);
            mCoatingTintlitsLayout =(RelativeLayout) itemView.findViewById(R.id.employee_listlayout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String mFilterTxt = constraint.toString();
                if(mFilterTxt.isEmpty()){
                    mFilterArraylist = mCoatingTintlits;
                }
                else{
                    ArrayList<TintDataModel> mFilterList = new ArrayList<>();

                    for (TintDataModel tintDataModel : mCoatingTintlits){

                        if(tintDataModel.getName().toLowerCase().contains(mFilterTxt)){
                            mFilterList.add(tintDataModel);
                        }
                    }
                    mFilterArraylist = mFilterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterArraylist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilterArraylist = (ArrayList<TintDataModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
