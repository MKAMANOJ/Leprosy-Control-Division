package np.gov.shris.lcd.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import np.gov.shris.lcd.Fragments.Introduction_treatment;
import np.gov.shris.lcd.Fragments.Introduction_info;
import np.gov.shris.lcd.Fragments.Introduction_control;
import np.gov.shris.lcd.Fragments.Introduction_diagnosis;

public class IntroductionCategoryAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Info", "Control", "Diagnosis","Treatment"};

    public IntroductionCategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new Introduction_info();
        } else if (position == 1) {
            return new Introduction_control();
        } else if (position == 2){
            return new Introduction_diagnosis();
        } else
            return new Introduction_treatment();
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
