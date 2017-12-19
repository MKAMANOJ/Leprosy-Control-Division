package np.gov.shris.lcd.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import np.gov.shris.lcd.Models.ProgramItem;
import np.gov.shris.lcd.R;

/**
 * Created by shris on 6/10/2017.
 */

public class ProgramsAdapter extends ArrayAdapter<ProgramItem> {

    Context context;
    ArrayList<ProgramItem> programItems;


    public ProgramsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ProgramItem> objects) {
        super(context, resource, objects);

        this.context = context;
        this.programItems = objects;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ProgramItem programItem = programItems.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.nip_publication_single_item, null);

        final TextView notice = (TextView) view.findViewById(R.id.noticeItem);

        notice.setText(programItem.getTitle());
        return view;
    }

}
