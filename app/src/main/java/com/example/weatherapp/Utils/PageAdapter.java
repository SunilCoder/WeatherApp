package com.example.weatherapp.Utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.weatherapp.Fragment.CurrentFragment;
import com.example.weatherapp.Fragment.ForeCastFragment;

import java.util.Currency;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       if(position==0){
           CurrentFragment currentFragment = new CurrentFragment();
           return  currentFragment;
       }else{
           ForeCastFragment foreCastFragment = new ForeCastFragment();
           return foreCastFragment;
       }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
