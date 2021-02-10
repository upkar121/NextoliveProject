package com.example.nextoliveproject.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nextoliveproject.R;
import com.example.nextoliveproject.models.ExpandedMenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private List<ExpandedMenuModel> mListDataHeader; // header titles
    List<ExpandedMenuModel> listDataHeaderThirdLevell = new ArrayList<>();//3rd level

    // child data in format of header title, child title
    private HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> mListDataChild;
    private HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> mlistDataChildOfChild;//for 3rd level
    ExpandableListView expandList;

    public CustomExpandableListAdapter(Context context, List<ExpandedMenuModel> listDataHeader,
                                       HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listChildData, ExpandableListView mView,
                                       HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listDataChildOfChild,
                                       List<ExpandedMenuModel> listDataHeaderThirdLevel) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.mlistDataChildOfChild = listDataChildOfChild;
        this.expandList = mView;
        this.listDataHeaderThirdLevell = listDataHeaderThirdLevel;
    }

    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
        Log.d("GROUPCOUNT", String.valueOf(i));
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        try {
            if (this.mListDataChild.get(this.mListDataHeader.get(groupPosition)) != null)
                childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).size();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition).toString());
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.submenu);
        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        ImageView menuIcon = (ImageView) convertView.findViewById(R.id.menuIcon);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getIconName());

        try {
            menuIcon.setImageResource(headerTitle.getIconImg());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getChild(groupPosition, childPosition);
        final String childText = headerTitle.getIconName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.submenu);
        ImageView menuIcon = (ImageView) convertView.findViewById(R.id.menuIcon);
        txtListChild.setText(childText);
        try {
            menuIcon.setImageResource(headerTitle.getIconImg());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}