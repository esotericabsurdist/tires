package com.spaceshipfreehold.tirecorrector;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TireUtilityAdapter extends FragmentPagerAdapter {

    List<TireFragment> mFragments;
    public TireUtilityAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragments = new ArrayList<>();
    }

    public void addUtility(TireFragment fragment){
        mFragments.add(fragment);
    }

    public void removeFragments(){
        mFragments.clear();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getUtilityTitle();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
