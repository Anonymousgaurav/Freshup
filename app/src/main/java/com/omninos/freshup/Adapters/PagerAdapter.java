package com.omninos.freshup.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.omninos.freshup.Fragment.ProductsFragment;
import com.omninos.freshup.Fragment.ServicesFragment;

public class PagerAdapter extends FragmentPagerAdapter {

   int noOfTabs;

    public PagerAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               ServicesFragment servicesFragment=new ServicesFragment();
               return servicesFragment;
           case 1:
               ProductsFragment productsFragment=new ProductsFragment();
               return productsFragment;
               default:
                   return null;
       }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
