package com.omninos.freshup.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.Activities.JoinQueueActivity;
import com.omninos.freshup.Activities.ProfileActivity;
import com.omninos.freshup.Activities.SettingActivity;
import com.omninos.freshup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private ImageView popUpmenu,ProfileData;
    private HomeActivity activity;
    private Button serviceButton, productButton;

    //private TabLayout tabLayout;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        activity= (HomeActivity) getActivity();
        initView(view);
        SetUps(view);

        return view;
    }

    private void initView(View view) {

        serviceButton = view.findViewById(R.id.serviceButton);
        productButton = view.findViewById(R.id.productButton);
        popUpmenu=view.findViewById(R.id.popUpmenu);
        ProfileData=view.findViewById(R.id.ProfileData);

        //  tabLayout=view.findViewById(R.id.tabLayout);
    }

    private void SetUps(View view) {
        // tabLayout.addTab(tabLayout.newTab().setText("Services"));
//        tabLayout.addTab(tabLayout.newTab().setText("Products"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        serviceButton.setOnClickListener(this);
        productButton.setOnClickListener(this);
        popUpmenu.setOnClickListener(this);
        ProfileData.setOnClickListener(this);
        setFragment(new ServicesFragment());
//        setUpFragment(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.serviceButton:
                serviceButton.setBackground(getResources().getDrawable(R.drawable.rectangle_white_button));
                productButton.setBackground(getResources().getDrawable(R.drawable.rectangle_button));
                serviceButton.setTextColor(getResources().getColor(R.color.black));
                productButton.setTextColor(getResources().getColor(R.color.white));
                setFragment(new ServicesFragment());
                break;

            case R.id.productButton:
                serviceButton.setBackground(getResources().getDrawable(R.drawable.rectangle_button));
                productButton.setBackground(getResources().getDrawable(R.drawable.rectangle_white_button));
                serviceButton.setTextColor(getResources().getColor(R.color.white));
                productButton.setTextColor(getResources().getColor(R.color.black));
                setFragment(new ProductsFragment());
                break;

            case R.id.popUpmenu:
//                OpenPopUpMenu(v);
                startActivity(new Intent(activity,SettingActivity.class));
                break;

            case R.id.ProfileData:
              startActivity(new Intent(activity,ProfileActivity.class));
                break;
        }
    }


    private void OpenPopUpMenu(View view) {
        PopupMenu popup = new PopupMenu(activity,popUpmenu);
        popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.appointment:
                        //Toast.makeText(activity, "Appointment", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(activity,AppointmentActivity.class));
                        break;

                    case R.id.queue:
//                        Toast.makeText(activity, "Queue", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity,JoinQueueActivity.class));
                        break;
                }
                return true;
            }
        });
        popup.show();

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.datacontainer, fragment);
        transaction.commit();
    }

//    private void setUpFragment(View view) {
//        final ViewPager viewPager=view.findViewById(R.id.viewPage);
//
//        PagerAdapter pagerAdapter=new PagerAdapter(getChildFragmentManager(),tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
}
