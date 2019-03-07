package com.omninos.freshup.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.Adapters.AppointmentAdapter;
import com.omninos.freshup.Adapters.AppointmentPagerAdapter;
import com.omninos.freshup.ModelClasses.AppointmentModel;
import com.omninos.freshup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainAppointmentFragment extends Fragment implements View.OnClickListener {

    private RecyclerView appointmentList;
    private AppointmentAdapter adapter;
    HomeActivity activity;
    private TextView noAppointment;
    private ImageView back;
    private TabLayout tabLayout;
    List<AppointmentModel> list = new ArrayList<>();


    public MainAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_appointment, container, false);

        activity = (HomeActivity) getActivity();

        initView(view);
        SetUps(view);
        RecyclerViewSetUps();
        return view;

    }

    private void initView(View view) {
        appointmentList = view.findViewById(R.id.appointmentlist);
        noAppointment = view.findViewById(R.id.noAppointment);
        back=view.findViewById(R.id.back);
        tabLayout=view.findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
    private void SetUps(View view) {
        back.setOnClickListener(this);

        setUpFragment(view);


    }
    private void setUpFragment(View view) {
        final ViewPager viewPager=view.findViewById(R.id.viewPage);

        AppointmentPagerAdapter pagerAdapter=new AppointmentPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void RecyclerViewSetUps() {

        //toolbar=getSupportActionBar();

        //toolbar.setTitle("Shop");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        appointmentList.setLayoutManager(linearLayoutManager);
//
//
//        adapter = new AppointmentAdapter(activity, list, new AppointmentAdapter.CancelAppointment() {
//            @Override
//            public void Cancel(int position) {
//                WantToCancelAppointment(position);
//            }
//        });
//        appointmentList.setAdapter(adapter);

    }

    private void WantToCancelAppointment(final int position) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        appointmentList.setAdapter(adapter);
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.cancel();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    @Override
    public void onClick(View v) {

    }
}
