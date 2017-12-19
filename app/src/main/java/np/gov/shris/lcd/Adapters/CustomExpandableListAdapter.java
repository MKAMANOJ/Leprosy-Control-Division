package np.gov.shris.lcd.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import np.gov.shris.lcd.Models.NoticeItem;
import np.gov.shris.lcd.R;


public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listHeader;

    //Child data
    private HashMap<String, List<NoticeItem>> listChildData;


    public CustomExpandableListAdapter(Context context, List<String> listHeader,
                                       HashMap<String, List<NoticeItem>> listChildData) {
        this.context = context;
        this.listHeader = listHeader;
        this.listChildData = listChildData;
    }


    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChildData.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChildData.get(listHeader.get(groupPosition)).get(childPosition);
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.publication_list_group, null);
        }

        if (groupPosition % 2 == 0) {
            //holder.rootView.setBackgroundColor(Color.BLACK);
            convertView.setBackgroundResource(R.color.expandableList_even);
        } else {
            //holder.rootView.setBackgroundColor(Color.WHITE);
            convertView.setBackgroundResource(R.color.expandableList_odd);
        }

        //Set items
        TextView tvGroupTitle = (TextView) convertView.findViewById(R.id.tvGroupTitle);
        tvGroupTitle.setText((String) getGroup(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.publication_list_group, null);
        }

        //Set items
        TextView tvItemTitle = (TextView) convertView.findViewById(R.id.tvGroupTitle);

        tvItemTitle.setText(((NoticeItem)getChild(groupPosition, childPosition)).notice);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
