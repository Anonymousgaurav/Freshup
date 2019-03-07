package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout changepass;
    private CardView settingCard,changePaswordCard;
    private SettingActivity activity;
    private ImageView back;
    private TextView title;
    private Button changePasswordButton;
    private EditText oldPassFirst,confirmPass,newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        activity= SettingActivity.this;
        initView();
        setUps();
    }

    private void setUps() {
        changepass.setOnClickListener(this);
        back.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
    }

    private void initView() {
        changepass=findViewById(R.id.changepass);
        back=findViewById(R.id.back);
        settingCard=findViewById(R.id.settingCard);
        changePaswordCard=findViewById(R.id.changePaswordCard);
        title=findViewById(R.id.title);
        oldPassFirst=findViewById(R.id.oldPassFirst);
        confirmPass=findViewById(R.id.ConfirmPass);
        newPassword=findViewById(R.id.newPass);
        changePasswordButton=findViewById(R.id.changePasswordButton);
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

            case R.id.changePasswordButton:
                UpdateNewPassword();
                break;
        }
    }

    private void UpdateNewPassword() {

        String old1=oldPassFirst.getText().toString();
        String confirm=confirmPass.getText().toString();
        String newPass=newPassword.getText().toString();

        if (old1.isEmpty()){
            oldPassFirst.setError("enter old password");
        }else if (newPass.isEmpty() || newPass.length() < 7){
            newPassword.setError("enter new password");
        }else if (confirm.isEmpty() || !confirm.equals(newPass)){
            confirmPass.setError("password mismatch");
        }else {

            if (CommonUtils.isNetworkConnected(activity)){

                Api api=ApiClient.getApiClient().create(Api.class);

                CommonUtils.showProgress(activity,"");
                api.changePassword(App.getAppPreferences().getUserId(activity),old1,confirm).enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        CommonUtils.dismissProgress();
                            if (response.isSuccessful()){
                                Toast.makeText(activity, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();
                                MoveToBack();
                            }else {
                                Toast.makeText(activity, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        CommonUtils.dismissProgress();
                        Toast.makeText(activity, t+"", Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
            }
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
