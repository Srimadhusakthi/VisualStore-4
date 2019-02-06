package com.visualstore.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.visualstore.BaseActivity;
import com.visualstore.Dashboard.ShapeBevel;
import com.visualstore.Model.ShapeBevelModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

public class ShapeBevelAdapter extends RecyclerView.Adapter<ShapeBevelAdapter.ShapeBevelHolder> {
    private Activity activity;
    private ArrayList<ShapeBevelModel> mList;
    private  int value = 0;

    public ShapeBevelAdapter(Activity act,ArrayList<ShapeBevelModel> mLenslits){
       activity= act;
       mList = mLenslits;
   }

    @NonNull
    @Override
    public ShapeBevelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(activity).inflate(R.layout.shapebevel,viewGroup,false);
        return new ShapeBevelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShapeBevelHolder shapeBevelHolder, final int i) {
        ShapeBevelModel shapeBevel = mList.get(i);
        shapeBevelHolder.mshapebevel.setBackgroundResource(shapeBevel.getmLenstype());
        shapeBevelHolder.mShapecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.selection = i;
                notifyDataSetChanged();
                BaseActivity.mShapeBevel = shapeBevel.getmId();

            }
        });
        if (BaseActivity.selection == i ) {
            shapeBevelHolder.mShapecard.setCardBackgroundColor(Color.GRAY);
        }else{
            shapeBevelHolder.mShapecard.setCardBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ShapeBevelHolder extends RecyclerView.ViewHolder{
        private ImageView mshapebevel;
        private CardView mShapecard;
        public ShapeBevelHolder(@NonNull View itemView) {
            super(itemView);
            mshapebevel = itemView.findViewById(R.id.mshapebevel);
            mShapecard = itemView.findViewById(R.id.shape_card);

        }
    }
}
