package com.omninos.freshup.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {


    private HomeActivity activity;
    private ImageView back;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_change_password, container, false);

        activity= (HomeActivity) getActivity();
        initView(view);
        Setups(view);

        return view;
    }

    private void initView(View view) {
        back=view.findViewById(R.id.back);
    }

    private void Setups(View view) {
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                MoveToBack();
                break;
        }
    }

    private void MoveToBack() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new SettingFragment());
        transaction.commit();
    }
}
