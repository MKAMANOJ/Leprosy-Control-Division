package np.gov.shris.lcd.Fragments;

/**
 * Created by mka on 9/18/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import np.gov.shris.lcd.R;


public class DirectorMessage extends Fragment {

    public DirectorMessage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_director_message, container, false);
        TextView msg = (TextView) rootview.findViewById(R.id.director_message);
        msg.setText(R.string.director_message);
        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
