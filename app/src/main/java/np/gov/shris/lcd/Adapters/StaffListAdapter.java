package np.gov.shris.lcd.Adapters;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import np.gov.shris.lcd.Models.Staff;
import np.gov.shris.lcd.R;
import np.gov.shris.lcd.Utilities.CircleTransform;


/**
 * Created by shris on 6/10/2017.
 */

public class StaffListAdapter extends ArrayAdapter<Staff> {

    Context context;
    ArrayList<Staff> staffList;

    public StaffListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Staff> objects) {
        super(context, resource, objects);

        this.context = context;
        this.staffList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final Staff staff = staffList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.staff_contact, null);

        final TextView name = (TextView) view.findViewById(R.id.staffName);
        TextView designation = (TextView) view.findViewById(R.id.staffDesignation);
        final TextView email = (TextView) view.findViewById(R.id.staffEmail);
        ImageView image = (ImageView) view.findViewById(R.id.staffImage);

        //Check empty
        //Hide fields if empty or show defaults



        name.setText(staff.name);
        if (TextUtils.isEmpty(staff.designation)) {
            designation.setText("");
        }else {
            designation.setText(staff.designation);
        }
        //        image.setImageResource((staff.image == null || staff.image == -1) ? R.drawable.ic_user : staff.image);
        if (staff.image == null || staff.image == -1){
            try {
                Picasso.with(getContext())
                        .load(R.drawable.ic_user)
                        .resize(400,400)
                        .transform(new CircleTransform())
                        .into(image);
            }catch (OutOfMemoryError e){
                e.printStackTrace();
            }
        }else{
            try {

                Picasso.with(getContext())
                        .load(staff.image)
                        .resize(400,400)
                        .transform(new CircleTransform())
                        .into(image);
            }catch (OutOfMemoryError e){
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(staff.email)) {
            email.setText("");
            email.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }else {
            email.setText(staff.email);
            // drawable right click listener
            email.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;

                    if(event.getAction() == 0) {
                        if(event.getRawX() >= (email.getRight() - email.getCompoundDrawables()
                                [DRAWABLE_RIGHT].getBounds().width())) {
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{staff.email});

                            try {

                                context.startActivity(emailIntent);
                            } catch (ActivityNotFoundException activityNotFound) {

                                Toast.makeText(context,context.getString(R.string.no_email_program),
                                        Toast.LENGTH_LONG).show();
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
        }

        if (TextUtils.isEmpty(staff.phone) || (staff.phone == null)) {
            name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }else{
            // drawable right click listener
            name.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;

                    if(event.getAction() == 0) {
                        if(event.getRawX() >= (name.getRight() - name.getCompoundDrawables()
                                [DRAWABLE_RIGHT].getBounds().width())) {

                            Intent intent1 = new Intent(Intent.ACTION_DIAL);
                            intent1.setData(Uri.parse("tel:" + staff.phone));
                            context.startActivity(intent1);

                            try {
                                context.startActivity(intent1);
                            } catch (ActivityNotFoundException activityNotFound) {

                                Toast.makeText(context,context.getString(R.string.no_phone_program),
                                        Toast.LENGTH_LONG).show();
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        return view;
    }
}
