package com.visualstore.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visualstore.BaseActivity;
import com.visualstore.Model.ProductLensDataModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

public class ProductLensAdapter extends RecyclerView.Adapter<ProductLensAdapter.ProdcutHolder> implements Filterable {

    private ArrayList<ProductLensDataModel> mProductlist;
    private ArrayList<ProductLensDataModel> mFilterArraylist;
    private Activity activity;

    public ProductLensAdapter(Activity mActivity,ArrayList<ProductLensDataModel> list){
        activity = mActivity;
        mProductlist = list;
        mFilterArraylist = list;
    }


    @NonNull
    @Override
    public ProdcutHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.employee_list,viewGroup,false);
        return new ProdcutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdcutHolder mProdcutHolder, int i) {
        ProductLensDataModel model = mFilterArraylist.get(i);

        mProdcutHolder.mProductlensename.setText(model.getName() +  i);
        mProdcutHolder.mProductListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_id,"").equals("2")){
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,model.getName());
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,model.getLens_code());
                } else{
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,model.getName());
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,model.getLens_code());
                }*/

                if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.both_single,"").equals("single")){
                    if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.lens_typename_id,"").equals("2")){
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,model.getName());
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,model.getLens_code());
                    } else{
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,model.getName());
                        Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,model.getLens_code());
                    }
                } else if(Sharedpreference.getSharedprefernce(activity,Sharedpreference.both_single,"").equals("both")){
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_right,model.getName());
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typename_left,model.getName());

                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_right,model.getLens_code());
                    Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_typecode_left,model.getLens_code());
                }

                Sharedpreference.onStorePreferences(activity,Sharedpreference.mlens_coating_id,model.getLens_code());
                Sharedpreference.onStorePreferences(activity,Sharedpreference.lens_individual,model.getIndividual());
                activity.finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mFilterArraylist.size();
    }

    public class ProdcutHolder extends RecyclerView.ViewHolder{
        private TextView mProductlensename;
        private RelativeLayout mProductListLayout;
        public ProdcutHolder(@NonNull View itemView) {
            super(itemView);
            mProductlensename = (TextView)itemView.findViewById(R.id.employee_name);
            mProductListLayout =(RelativeLayout) itemView.findViewById(R.id.employee_listlayout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String mFilterTxt = constraint.toString();
                if(mFilterTxt.isEmpty()){
                    mFilterArraylist = mProductlist;
                }
                else{
                    ArrayList<ProductLensDataModel> mFilterList = new ArrayList<>();

                    for (ProductLensDataModel ProductLensDataModel : mProductlist){
                        if(ProductLensDataModel.getName().toLowerCase().contains(mFilterTxt)){
                            mFilterList.add(ProductLensDataModel);
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
                mFilterArraylist = (ArrayList<ProductLensDataModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
