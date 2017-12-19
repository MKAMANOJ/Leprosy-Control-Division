package np.gov.shris.lcd.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import np.gov.shris.lcd.Fragments.LeprosyEffectsCommonlyEffected;
import np.gov.shris.lcd.Fragments.LeprosyEffectsDeformities;
import np.gov.shris.lcd.Fragments.LeprosyEffectsTransmission;

public class LeprosyEffectsCategoryAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Deformities", "Transmission", "Commonly Affected"};

    public LeprosyEffectsCategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new LeprosyEffectsDeformities();
        } else if (position == 1) {
            return new LeprosyEffectsTransmission();
        } else {
            return new LeprosyEffectsCommonlyEffected();
        }
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
