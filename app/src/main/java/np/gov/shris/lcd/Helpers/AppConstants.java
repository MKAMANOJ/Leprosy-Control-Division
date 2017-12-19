package np.gov.shris.lcd.Helpers;

import android.os.Environment;

import java.util.ArrayList;

import np.gov.shris.lcd.Models.Staff;
import np.gov.shris.lcd.R;


public class AppConstants {

    public static final String path = Environment.getExternalStorageDirectory() + "/"
            + "LCD" + "/";
    public static final String downloadPath = "LCD" + "/";

    public static final String DOWNLOAD_URL_PREFIX = "http://apis.citizeninfotech.com.np/lcd/";
    public static final String DOWNLOAD_NEWS_URL_PREFIX = "http://apis.citizeninfotech.com.np/lcd/";


    //Director -Contact
    public static final String directorContactName = "Mohammad Daud";
    public static final String directorContactEmail = "smdaud61@gmail.com";
    public static final String directorContactDesignation = "Director";
    public static final String[] directorContactPhoneList = {"01-4262009", "9851029526"};


    //DisabilityPreventionAndRehabilitation -Contact
    public static final String disabilityPreventionAndRehabilitationContactName = "Mahesh Kumar Puri";
    public static final String disabilityPreventionAndRehabilitationContactEmail = "mahesh.puri946@gmail.com";
    public static final String disabilityPreventionAndRehabilitationContactDesignation = "TB Leprosy Officer";
    public static final String[] disabilityPreventionAndRehabilitationContactPhoneList = {"01-4248535", "9848056407"};

    //Contact US
    public static final String[] contactPhoneList = {"01- 4262009", "01- 4248535"};
    public static final String[] contactEmailList = {"lcd@mohp.gov.np", "leprosycontrol@gmail.com"};

    //PhotoGallery
    public static final ArrayList<Integer> galleryList = new ArrayList<Integer>() {{
        add(R.drawable.gallery1);
        add(R.drawable.gallery2);
        add(R.drawable.gallery3);
        add(R.drawable.gallery4);
        add(R.drawable.gallery5);
        add(R.drawable.gallery6);
    }};

    //EmployeeInformation
    public static final ArrayList<Staff> staffsList = new ArrayList<Staff>() {{
        add(new Staff("Mohammad Daud", "Director","9851029526", "smdaud61@gmail.com",R.drawable.mohammad_daud));
        add(new Staff("Dr. Rabindra Baskota", "Consultant Dermatologist","", "",R.drawable.rabindra_bastoka));
        add(new Staff("Dr. Madhab Prasad Lamsal", "Deputy Health Administrator","", "",R.drawable.madhab_prasad_lamsal));
        add(new Staff("Mahesh Kumar Puri", "TB/Leprosy Officer","9848056407", "mahesh.puri946@gmail.com",R.drawable.mahesh_kumar_puri));
    }};

    public static final int PDF_REQUIRED = -1;

    public static final int NO_INTERNET_CONNECTION = 0;

}
