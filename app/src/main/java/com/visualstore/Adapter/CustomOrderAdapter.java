/*
package com.visualstore.Adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.visualstore.Model.LensOrderModel;
import com.visualstore.R;

import java.util.ArrayList;

public class CustomOrderAdapter extends ArrayAdapter<LensOrderModel> {

    private Activity activity;
    private ArrayList<LensOrderModel>  list;


    public CustomOrderAdapter(Activity mAct,ArrayList<LensOrderModel> mList){
    super(mAct, android.R.layout.simple_list_item_1, mList);

        activity = mAct;
        list = mList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.custom_lensorder_txt, null);
        }
        LensOrderModel lensOrderModel = list.get(position);
        TextView name = (TextView) convertView.findViewById(R.id.custom_ordername);
//        lbl.setTextColor(Color.BLACK);
        name.setText(lensOrderModel.getmName());
        return convertView;
    }
}
*/

package com.visualstore.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.visualstore.Model.LensOrderModel;
import com.visualstore.R;
import com.visualstore.Sharedpreference.Sharedpreference;

import java.util.ArrayList;

public class CustomOrderAdapter extends ArrayAdapter<LensOrderModel> {

    private Activity activity;
    private ArrayList<LensOrderModel> modelArrayList;

    public CustomOrderAdapter(Activity context, int resourceId, ArrayList<LensOrderModel> objects) {
        super(context, resourceId, objects);
        activity = context;
        modelArrayList = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
// TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LensOrderModel lensOrderModel = modelArrayList.get(position);
        LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View row=inflater.inflate(R.layout.custom_lensorder_txt, parent, false);
        TextView label=(TextView)row.findViewById(R.id.custom_ordername);
        label.setText(lensOrderModel.getmName());
        CardView lensorder_card= (CardView)row.findViewById(R.id.lensorder_card);

       if(label.getText().toString().isEmpty()){
//           lensorder_card.setCardBackgroundColor(Color.GREEN);
//           lensorder_card.setVisibility(View.GONE);
       }


        return row;
    }




}
