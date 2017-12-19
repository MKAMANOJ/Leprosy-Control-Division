package np.gov.shris.lcd.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import np.gov.shris.lcd.Fragments.Leprosy_Control_Info;
import np.gov.shris.lcd.Fragments.Leprosy_Control_Objectives;
import np.gov.shris.lcd.Fragments.Leprosy_Control_Strategies;
import np.gov.shris.lcd.Fragments.Leprosy_Control_Targets;

public class LeprosyControlCategoryAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Info", "Objectives", "Strategies", "Targets"};

    public LeprosyControlCategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new Leprosy_Control_Info();
        } else if (position == 1) {
            return new Leprosy_Control_Objectives();
        } else if (position == 2){
            return new Leprosy_Control_Strategies();
        } else
            return new Leprosy_Control_Targets();
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
