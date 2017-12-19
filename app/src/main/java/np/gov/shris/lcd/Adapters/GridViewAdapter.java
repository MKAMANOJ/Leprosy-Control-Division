package np.gov.shris.lcd.Adapters;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import np.gov.shris.lcd.Models.Item;
import np.gov.shris.lcd.R;

/**
 * Created by shris on 6/10/2017.
 */

public class GridViewAdapter extends BaseAdapter {

    private int selectedPosition = -1;

    private Context context;
    //private Integer[] mThumbsIds;
    private ArrayList<Item> itemsList;

    //public GridViewAdapter(Context context, Integer[] mThumbsIds) {
    public GridViewAdapter(Context context, ArrayList<Item> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_view_item, parent, false);
        } else {
            v = (View) convertView;
        }
        //set image and text
        ImageView imgView = (ImageView) v.findViewById(R.id.ivImg);
        TextView txtView = (TextView) v.findViewById(R.id.tvText);

        //imgView.setImageResource(mThumbsIds[position]);

        txtView.setText(itemsList.get(position).text);
        imgView.setImageResource(itemsList.get(position).img);

        if (selectedPosition == position) {
            v.setBackgroundColor(context.getResources().getColor(R.color.selected));
        } else {
            v.setBackgroundColor(context.getResources().getColor(R.color.not_selected));
        }

        return v;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
}
