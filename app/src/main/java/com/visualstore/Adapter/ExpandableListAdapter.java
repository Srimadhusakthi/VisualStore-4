package com.visualstore.Adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.visualstore.R;

public class ExpandableListAdapter   extends BaseExpandableListAdapter {
    private Activity _context;
    private List<String> header;
    private HashMap<String, List<String>> child;

    public ExpandableListAdapter(Activity context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this.header = listDataHeader;
        this.child = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        // This will return the child
        return this.child.get(this.header.get(groupPosition)).get(
                childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = _context.getLayoutInflater();

        int childType = getChildType(groupPosition, childPosition);

        // We need to create a new "cell container"
        if (convertView == null /*|| convertView.getTag() != childType*/) {
            switch (childType) {
                case 0:
                    convertView = inflater.inflate(R.layout.activity_customerdata_lensordering, null);
                    convertView.setTag(childType);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.activity_refraction_lensordering, null);
                    convertView.setTag(childType);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.activity_lenstype_lensordering, null);
                    convertView.setTag(childType);
                    break;
                case 3:
                    convertView = inflater.inflate(R.layout.activity_treatments_lensordering, null);
                    convertView.setTag(childType);
                    break;
                default:
                    // Maybe we should implement a default behaviour but it should be ok we know there are 4 child types right?
                    break;
            }
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        // return children count
        return this.child.get(this.header.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        // Get header position
        return this.header.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        // Get header size
        return this.header.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        // Getting header title
        String headerTitle = (String) getGroup(groupPosition);

        // Inflating header layout and setting text
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.header, parent, false);
        }

        TextView header_text = (TextView) convertView.findViewById(R.id.header);

        header_text.setText(headerTitle);

        // If group is expanded then change the text into bold and change the
        // icon
        if (isExpanded) {
            header_text.setTypeface(null, Typeface.BOLD);
//            header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//                    R.drawable.ic_up_arrow, 0);
        } else {
            // If group is not expanded then change the text back into normal
            // and change the icon

            header_text.setTypeface(null, Typeface.NORMAL);
//            header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//                    R.drawable.ic_angle_arrow_down, 0);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}