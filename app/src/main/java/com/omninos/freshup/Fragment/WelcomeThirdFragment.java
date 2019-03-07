package com.omninos.freshup.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.Activities.LoginActivity;
import com.omninos.freshup.Activities.WellcomeScreenActivity;
import com.omninos.freshup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeThirdFragment extends Fragment {

    WellcomeScreenActivity activity;
    int i=0;

    public WelcomeThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_welcome_third, container, false);

        activity= (WellcomeScreenActivity) getActivity();


        return view;
    }

}
