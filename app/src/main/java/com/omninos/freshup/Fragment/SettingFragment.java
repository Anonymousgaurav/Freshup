package com.omninos.freshup.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout changepass;
    private CardView settingCard,changePaswordCard;
    private HomeActivity activity;
    private ImageView back;
    private TextView title;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);

        activity= (HomeActivity) getActivity();
        initView(view);
        setUps(view);
        return view;
    }

    private void initView(View view) {
        changepass=view.findViewById(R.id.changepass);
        back=view.findViewById(R.id.back);
        settingCard=view.findViewById(R.id.settingCard);
        changePaswordCard=view.findViewById(R.id.changePaswordCard);
        title=view.findViewById(R.id.title);
    }

    private void setUps(View view) {
        changepass.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changepass:
                MoveToChangePassword();
                break;

            case R.id.back:
                MoveToBack();
                break;
        }
    }

    private void MoveToBack() {
        title.setText("Settings");
        back.setVisibility(View.GONE);
        changePaswordCard.setVisibility(View.GONE);
        settingCard.setVisibility(View.VISIBLE);
    }

    private void MoveToChangePassword() {
        title.setText("Change Password");
        back.setVisibility(View.VISIBLE);
        changePaswordCard.setVisibility(View.VISIBLE);
        settingCard.setVisibility(View.GONE);
    }
}
