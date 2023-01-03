package com.example.medpharmaapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProductDetailsAdapter2 extends FragmentPagerAdapter {

    int totalTabs;

    public ProductDetailsAdapter2(@NonNull FragmentManager fm, int totalTabs) {
        super(fm, totalTabs);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Desc_Fragment2 desc = new Desc_Fragment2();
                return desc;
            case 1:
                Other_Details_Fragment2 other = new Other_Details_Fragment2();
                return other;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
