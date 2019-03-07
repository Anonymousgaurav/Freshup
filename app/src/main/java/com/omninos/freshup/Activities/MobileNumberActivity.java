package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.omninos.freshup.ModelClasses.RegisterModel;
import com.omninos.freshup.ModelClasses.SocialLoginModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileNumberActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText number;
    private Button next;
    private String mobileNo,reg_id;
    private MobileNumberActivity activity;

    private CountryCodePicker countryCodePicker;
    private String countryCode,name,email,image,SocailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);

        activity = MobileNumberActivity.this;

        intView();
        SetUps();

    }

    private void intView() {

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        image=getIntent().getStringExtra("image");
        SocailId=getIntent().getStringExtra("SocailId");

//        if (App.getAppPreferences().getInstaId()!=null){
//            SocailId=App.getAppPreferences().getInstaId();
//            image=App.getAppPreferences().getInstaUserPicture();
//            name=App.getAppPreferences().getInstaUserName();
//            email="";
//        }

        number = findViewById(R.id.number);
        next = findViewById(R.id.next);
        countryCodePicker = findViewById(R.id.ccp);
        countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();

    }

    private void SetUps() {
        next.setOnClickListener(this);
        countryCodePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                CheckNumber();
                break;

            case R.id.ccp:
                OpenCountryCodeDailog();
                break;
        }
    }

    private void OpenCountryCodeDailog() {
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode=countryCodePicker.getSelectedCountryCodeWithPlus();

            }
        });
    }

    private void CheckNumber() {
        mobileNo = number.getText().toString().trim();
        if (mobileNo.isEmpty() || mobileNo.length()!=10) {
            number.setError("enter valid number length");
        } else {
            UserRegister();
        }
    }

    private void UserRegister() {

        reg_id = FirebaseInstanceId.getInstance().getToken();

        if (CommonUtils.isNetworkConnected(activity)){
            CommonUtils.showProgress(activity,"");

            Api api=ApiClient.getApiClient().create(Api.class);
            api.SocialLogin(name,email,image,mobileNo,SocailId,"Android",reg_id,"Social Login").enqueue(new Callback<SocialLoginModel>() {
                @Override
                public void onResponse(Call<SocialLoginModel> call, Response<SocialLoginModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body()!=null){
                        if (response.body().getSuccess().equalsIgnoreCase("1")){
                            App.getAppPreferences().saveUserId(activity, response.body().getDetails().getId());
                            App.getAppPreferences().saveUserIdTemp(activity,"1");
                            App.getAppPreferences().setOTP(response.body().getDetails().getOtp().toString());
                            Toast.makeText(activity, response.body().getDetails().getOtp() + "", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, VarificationActivity.class));
                        }else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SocialLoginModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                }
            });


        }else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }
}
