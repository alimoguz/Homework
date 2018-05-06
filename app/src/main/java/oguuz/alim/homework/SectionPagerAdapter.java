package oguuz.alim.homework;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alim on 6.5.2018.
 */

class SectionPagerAdapter extends FragmentPagerAdapter {
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position){
            case 0:
                FoodFragment food_fragment= new FoodFragment();
                return food_fragment;

            case 1:
                AnnouncementsFragment announcements_fragment= new AnnouncementsFragment();
                return  announcements_fragment;

            case 2:
                NewsFragment news_fragment= new NewsFragment();
                return  news_fragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "FOOD LIST";

            case 1:
                return "ANNOUNCE...";

            case 2:
                return "NEWS";

            default:
                return null;

        }
    }
}
