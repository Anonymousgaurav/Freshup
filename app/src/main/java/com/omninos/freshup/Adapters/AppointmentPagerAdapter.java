package com.omninos.freshup.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.omninos.freshup.Fragment.AllAppointmentFragment;
import com.omninos.freshup.Fragment.PastAppointmentFragment;
import com.omninos.freshup.Fragment.UpcommingAppointmentFragment;

public class AppointmentPagerAdapter extends FragmentPagerAdapter {

    int noOfTabs;

    public AppointmentPagerAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllAppointmentFragment allAppointmentFragment = new AllAppointmentFragment();
                return allAppointmentFragment;
            case 1:
                UpcommingAppointmentFragment upcommingAppointmentFragment = new UpcommingAppointmentFragment();
                return upcommingAppointmentFragment;
            case 2:
                PastAppointmentFragment pastAppointmentFragment = new PastAppointmentFragment();
                return pastAppointmentFragment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
