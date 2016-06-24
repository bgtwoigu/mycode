package com.oneplus.opcameratest;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/17.
 */
public class CamParameterAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> groups;
    private ArrayList<ArrayList<CamParameter>> mCamPars;
    private LayoutInflater inflater;

    public CamParameterAdapter(Context context,
                      ArrayList<String> groups,
                      ArrayList<ArrayList<CamParameter>> mCamPars ) {
        this.context = context;
        this.groups = groups;
        this.mCamPars = mCamPars;
        inflater = LayoutInflater.from( context );
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mCamPars.get( groupPosition ).get( childPosition );
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (long)( groupPosition*1024+childPosition );  // Max 1024 children per group
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View mView = null;
        ViewHolderChild mHolder = null;
        final int groupIndex = groupPosition;
        final int childIndex = childPosition;

        if( convertView != null ) {
            mView = convertView;
            mHolder = (ViewHolderChild) mView.getTag();
        } else {
            mView = inflater.inflate(R.layout.child_row, parent, false);
            mHolder = new ViewHolderChild();
            mHolder.name = (TextView) mView.findViewById(R.id.childName);
            mHolder.type = (TextView) mView.findViewById( R.id.childType );
            mHolder.checkbox = (CheckBox) mView.findViewById(R.id.childCheck);
            mView.setTag(mHolder);
        }

        CamParameter mCamParameter = (CamParameter) getChild( groupPosition, childPosition );
        if( mHolder.name != null )
            mHolder.name.setText(mCamParameter.getName());
        if( mHolder.type != null )
            mHolder.type.setText(mCamParameter.getType());
        if (mHolder.checkbox != null) {
            mHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((CamParameter) getChild( groupIndex, childIndex )).State = isChecked;
//                    notifyDataSetChanged();
                }
            });

            mHolder.checkbox.setEnabled(mCamParameter.isEnable());
            mHolder.checkbox.setChecked(mCamParameter.getState());
        }

//        mHolder.checkbox.setChecked(mCamPars.get(groupPosition).get(childPosition).State == false ? false : true);
        return mView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCamPars.get( groupPosition ).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get( groupPosition );
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return (long)( groupPosition*1024 );  // To be consistent with getChildId
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = inflater.inflate(R.layout.group_row, parent, false);
        String gt = (String)getGroup( groupPosition );
        TextView parGroup = (TextView)v.findViewById( R.id.groupName );
        if( gt != null )
            parGroup.setText( gt );

        ImageView imageView = (ImageView) v.findViewById(R.id.label_indicator);
        switch (getChildrenCount(groupPosition)) {
            case 0:
                imageView.setImageResource(R.drawable.ic_clear_black_24dp);
                break;
            case 1:
                imageView.setImageResource(R.drawable.ic_filter_1_black_24dp);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_filter_2_black_24dp);
                break;
            case 3:
                imageView.setImageResource(R.drawable.ic_filter_3_black_24dp);
                break;
            case 4:
                imageView.setImageResource(R.drawable.ic_filter_4_black_24dp);
                break;
            case 5:
                imageView.setImageResource(R.drawable.ic_filter_5_black_24dp);
                break;
            case 6:
                imageView.setImageResource(R.drawable.ic_filter_6_black_24dp);
                break;
            case 7:
                imageView.setImageResource(R.drawable.ic_filter_7_black_24dp);
                break;
            case 8:
                imageView.setImageResource(R.drawable.ic_filter_8_black_24dp);
                break;
            case 9:
                imageView.setImageResource(R.drawable.ic_filter_9_black_24dp);
                break;
            default:
                imageView.setImageResource(R.drawable.ic_filter_9_plus_black_24dp);
                break;
        }

        return v;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void onGroupCollapsed (int groupPosition) {}
    public void onGroupExpanded(int groupPosition) {}

    static class ViewHolderChild {
        TextView name = null;
        TextView type = null;
        CheckBox checkbox = null;
    }
}
