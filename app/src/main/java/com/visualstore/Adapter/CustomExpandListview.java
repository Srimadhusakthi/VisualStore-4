package com.visualstore.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.visualstore.BaseActivity;
import com.visualstore.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomExpandListview extends BaseExpandableListAdapter {



    // 4 Child types
    private static final int CHILD_TYPE_1 = 0;
    private static final int CHILD_TYPE_2 = 1;
    private static final int CHILD_TYPE_3 = 2;
    private static final int CHILD_TYPE_4 = 3;
    private static final int CHILD_TYPE_UNDEFINED = 4;

    // 3 Group types
    private static final int GROUP_TYPE_1 = 0;
    private static final int GROUP_TYPE_2 = 1;
    private static final int  GROUP_TYPE_3 = 2;
    private static final int  GROUP_TYPE_4 = 3;

    private Activity context;
    private HashMap<String, List<String>> child;
    private List<String> group_list;

    public CustomExpandListview(Activity context, List<String> group_list,
                                HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.child = listChildData;
        this.group_list = group_list;
    }

    public Object getChild(int groupPosition, int childPosition) {
//        return comments_feed_collection.get(group_list.get(groupPosition)).get(childPosition);
        return this.child.get(this.group_list.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        final String incoming_text = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        int childType = getChildType(groupPosition, childPosition);

        // We need to create a new "cell container"
        if (convertView == null ) {
            switch (childType) {
                case CHILD_TYPE_1:
                    convertView = inflater.inflate(R.layout.activity_customerdata_lensordering, null);
                    convertView.setTag(childType);
                    break;
                case CHILD_TYPE_2:
                    convertView = inflater.inflate(R.layout.activity_refraction_lensordering, null);
                    convertView.setTag(childType);
                    break;
                case CHILD_TYPE_3:
                    convertView = inflater.inflate(R.layout.activity_lenstype_lensordering, null);
                    convertView.setTag(childType);
                    break;
                case CHILD_TYPE_4:
                    convertView = inflater.inflate(R.layout.activity_treatments_lensordering, null);
                    convertView.setTag(childType);
                    break;
                default:
                    // Maybe we should implement a default behaviour but it should be ok we know there are 4 child types right?
                    break;
            }
        }
        // We'll reuse the existing one
        else {
            // There is nothing to do here really we just need to set the content of view which we do in both cases
        }

        switch (childType) {
            case CHILD_TYPE_1:
//                TextView description_child = (TextView) convertView.findViewById(R.id.description_of_ads_expandable_list_child_text_view);
//                description_child.setText(incoming_text);
                break;
            case CHILD_TYPE_2:
                //Define how to render the data on the CHILD_TYPE_2 layout

                    onRefraction(convertView);

                break;
            case CHILD_TYPE_3:
                //Define how to render the data on the CHILD_TYPE_3 layout
                break;
            case CHILD_TYPE_4:
                //Define how to render the data on the CHILD_TYPE_UNDEFINED layout
                break;
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        // return children count
//        return this.child.get(this.group_list.get(groupPosition)).size();
        return 1;
    }

    public Object getGroup(int groupPosition) {
        return group_list.get(groupPosition);
    }
    public int getGroupCount() {
        return group_list.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final String incoming_text = (String) getGroup(groupPosition);

        int groupType = getGroupType(groupPosition);

        // We need to create a new "cell container"
        if (convertView == null ) {
            switch (groupType) {
                case GROUP_TYPE_1 :
                    convertView = inflater.inflate(R.layout.header, null);
                    break;
                case GROUP_TYPE_2:
                    convertView = inflater.inflate(R.layout.header, null);
                    break;
                case GROUP_TYPE_3:
                    convertView = inflater.inflate(R.layout.header, null);
                    break;
                case GROUP_TYPE_4:
                    convertView = inflater.inflate(R.layout.header, null);
                    break;
                default:
                    // Maybe we should implement a default behaviour but it should be ok we know there are 3 group types right?
                    break;
            }
        }
        // We'll reuse the existing one
        else {
            // There is nothing to do here really we just need to set the content of view which we do in both cases
        }

        switch (groupType) {
            case GROUP_TYPE_1 :
                TextView item = (TextView) convertView.findViewById(R.id.header);
                item.setTypeface(null, Typeface.BOLD);
                item.setText(incoming_text);
                break;
            case GROUP_TYPE_2:
                TextView items = (TextView) convertView.findViewById(R.id.header);
                items.setTypeface(null, Typeface.BOLD);
                items.setText(incoming_text);
                break;
            case GROUP_TYPE_3:
                TextView item_ = (TextView) convertView.findViewById(R.id.header);
                item_.setTypeface(null, Typeface.BOLD);
                item_.setText(incoming_text);
                break;

            case GROUP_TYPE_4:
                TextView ite = (TextView) convertView.findViewById(R.id.header);
                ite.setTypeface(null, Typeface.BOLD);
                ite.setText(incoming_text);
                break;
            default:
                // Maybe we should implement a default behaviour but it should be ok we know there are 3 group types right?
                break;
        }

        return convertView;
    }


    public boolean hasStableIds() {
        return true;
    }


    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    @Override
    public int getChildTypeCount() {
        return 4; // I defined 4 child types (CHILD_TYPE_1, CHILD_TYPE_2, CHILD_TYPE_3, CHILD_TYPE_UNDEFINED)
    }

    @Override
    public int getGroupTypeCount() {
        return 3; // I defined 3 groups types (GROUP_TYPE_1, GROUP_TYPE_2, GROUP_TYPE_3)
    }

    @Override
    public int getGroupType(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return GROUP_TYPE_1;
            case 1:
                return GROUP_TYPE_1;
            case 2:
                return GROUP_TYPE_2;
            case 3:
                return GROUP_TYPE_3;
            default:
                return GROUP_TYPE_4;
        }
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        switch (groupPosition) {
            case 0:
                return CHILD_TYPE_1;
            case 1:
                return CHILD_TYPE_2;

            case 2:
                return CHILD_TYPE_3;

            case 3:
                return CHILD_TYPE_4;

            default:
                return CHILD_TYPE_UNDEFINED;
        }

    }


    private void onRefraction(View view){
        AppCompatCheckBox rightside = (AppCompatCheckBox)view.findViewById(R.id.rightside);
        AppCompatCheckBox leftside = (AppCompatCheckBox)view.findViewById(R.id.leftside);
        boolean rightsideCheckbox = rightside.isChecked();
        boolean leftsideCheckbox = leftside.isChecked();
        RadioGroup mGroup = (RadioGroup) view.findViewById(R.id.radio_grp);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.single:
//                        rightside.setChecked(false);
//                        leftside.setChecked(false);
                        break;
                    case R.id.both:
//                        rightside.setChecked(true);
//                        leftside.setChecked(true);
//                        leftside.setClickable(false);
//                        rightside.setClickable(false);
                        break;
                }
            }
        });

    }
}
