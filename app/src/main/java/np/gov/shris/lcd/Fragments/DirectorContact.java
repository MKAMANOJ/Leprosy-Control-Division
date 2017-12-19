package np.gov.shris.lcd.Fragments;


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import np.gov.shris.lcd.Helpers.AppConstants;
import np.gov.shris.lcd.R;
import np.gov.shris.lcd.Utilities.CircleTransform;


public class DirectorContact extends Fragment {

    public DirectorContact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.staff_contact, container, false);



        final TextView name = (TextView) rootview.findViewById(R.id.staffName);
        TextView designation = (TextView) rootview.findViewById(R.id.staffDesignation);
        ImageView image = (ImageView) rootview.findViewById(R.id.staffImage);
        final TextView email = (TextView) rootview.findViewById(R.id.staffEmail);

        name.setText(AppConstants.directorContactName);
        designation.setText(AppConstants.directorContactDesignation);
        email.setText(AppConstants.directorContactEmail);
        Picasso.with(getContext())
                .load(R.drawable.mohammad_daud)
                .transform(new CircleTransform())
                .into(image);


        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == 0) {
                    if(event.getRawX() >= (email.getRight() - email.getCompoundDrawables()
                            [DRAWABLE_RIGHT].getBounds().width())) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]
                                {AppConstants.directorContactEmail});

                        try {

                            getContext().startActivity(emailIntent);
                        } catch (ActivityNotFoundException activityNotFound) {

                            Toast.makeText(getContext(),getContext().getString(R.string.no_email_program),
                                    Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        // drawable right click listener
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == 0 ) {
                    if(event.getRawX() >= (name.getRight() - name.getCompoundDrawables()
                            [DRAWABLE_RIGHT].getBounds().width())) {
                        final String[] phoneList = AppConstants.directorContactPhoneList;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setItems(phoneList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                                intent1.setData(Uri.parse("tel:" + phoneList[which]));
                                try {
                                    getContext().startActivity(intent1);
                                } catch (ActivityNotFoundException activityNotFound) {

                                    Toast.makeText(getContext(),getContext().getString(R.string.no_phone_program),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.show();
                        return true;
                    }
                }
                return false;
            }
        });

        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
