package np.gov.shris.lcd.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import np.gov.shris.lcd.Fragments.DisabilityPreventionAndRehabilitationApproach;
import np.gov.shris.lcd.Fragments.DisabilityPreventionAndRehabilitationContact;
import np.gov.shris.lcd.Fragments.DisabilityPreventionAndRehabilitationInfo;

public class DisabilityPreventionAndRehabilitationAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Info", "Approach", "Contact"};

    public DisabilityPreventionAndRehabilitationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new DisabilityPreventionAndRehabilitationInfo();
        } else if (position == 1) {
            return new DisabilityPreventionAndRehabilitationApproach();
        } else
            return new DisabilityPreventionAndRehabilitationContact();

    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
