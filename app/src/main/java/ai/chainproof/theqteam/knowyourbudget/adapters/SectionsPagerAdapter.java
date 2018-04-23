package ai.chainproof.theqteam.knowyourbudget.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantinos Tsiounis on 23-Apr-18.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private List<Bundle> argsList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title, Bundle args){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        argsList.add(args);
    }

    @Override
    public Fragment getItem(int position) {
        mFragmentList.get(position).setArguments(argsList.get(position));

        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
