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
import com.visualstore.Model.CoatingTintDataModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

public class CoatingAdapter extends RecyclerView.Adapter<CoatingAdapter.CoatingTintHolder> implements Filterable {

    private ArrayList<CoatingTintDataModel> mCoatingTintlits;
    private ArrayList<CoatingTintDataModel> mFilterArraylist;
    private Activity activity;

    public CoatingAdapter(Activity mActivity, ArrayList<CoatingTintDataModel> list){
        activity = mActivity;
        mCoatingTintlits = list;
        mFilterArraylist = list;
    }


    @NonNull
    @Override
    public CoatingAdapter.CoatingTintHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.employee_list,viewGroup,false);
        return new CoatingAdapter.CoatingTintHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoatingAdapter.CoatingTintHolder mCoatingTintHolder, int i) {
        CoatingTintDataModel model = mCoatingTintlits.get(i);
        mCoatingTintHolder.mProductlensename.setText(model.getName());
        mCoatingTintHolder.mCoatingTintlitsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(i == 0){
                    BaseActivity.onSnackBar(mCoatingTintHolder.mCoatingTintlitsLayout,activity.getResources().getString(R.string.pleaseselect) +" "+activity.getResources().getString(R.string.coating));
                }else {
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingname, model.getName());
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingcode, model.getCode());
                    Sharedpreference.onStorePreferences(activity, Sharedpreference.lens_coatingtype, model.getType());
                    activity.finish();
                }
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
                    ArrayList<CoatingTintDataModel> mFilterList = new ArrayList<>();

                    for (CoatingTintDataModel CoatingTintDataModel : mCoatingTintlits){

                        if(CoatingTintDataModel.getName().toLowerCase().contains(mFilterTxt)){
                            mFilterList.add(CoatingTintDataModel);
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
                mFilterArraylist = (ArrayList<CoatingTintDataModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
