package np.gov.shris.lcd.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import np.gov.shris.lcd.Fragments.DirectorContact;
import np.gov.shris.lcd.Fragments.DirectorMessage;

public class DirectorCategoryAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Message", "Contact"};

    public DirectorCategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new DirectorMessage();
        } else
            return new DirectorContact();
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
