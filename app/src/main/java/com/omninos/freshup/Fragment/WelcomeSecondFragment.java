package com.omninos.freshup.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omninos.freshup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeSecondFragment extends Fragment {


    public WelcomeSecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_second, container, false);
    }

}
