package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.omninos.freshup.ModelClasses.RegisterModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout moveToSingIn;
    private EditText userName, userEmail, userPass, userConfirmPass,number;
    private String S_name, S_email, S_pass, S_ConfirmPas,reg_id,mobileNo;
    private Button SignUp;
    private RegisterActivity activity;

    private CountryCodePicker countryCodePicker;
    private String countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        activity=RegisterActivity.this;
        initView();
        SetUp();
    }

    private void initView() {
        moveToSingIn = findViewById(R.id.moveToSingIn);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPass = findViewById(R.id.userPass);
        userConfirmPass = findViewById(R.id.userConfirmPass);
        SignUp = findViewById(R.id.SignUp);

        number = findViewById(R.id.number);
        countryCodePicker = findViewById(R.id.ccp);
        countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();
    }

    private void SetUp() {
        moveToSingIn.setOnClickListener(this);
        SignUp.setOnClickListener(this);
        countryCodePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moveToSingIn:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.SignUp:
                Validate();
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

    private void Validate() {
        reg_id = FirebaseInstanceId.getInstance().getToken();
        S_name = userName.getText().toString().trim();
        S_email = userEmail.getText().toString().trim();
        S_pass = userPass.getText().toString().trim();
        S_ConfirmPas = userConfirmPass.getText().toString().trim();
        mobileNo = number.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{2,4}";
        if (S_name.isEmpty()) {
            userName.setError("enter username");
        }else if (S_email.isEmpty() || !S_email.matches(emailPattern)) {
            userEmail.setError("enter valid email");
        } else if (S_pass.isEmpty() || S_pass.length() < 7) {
            userPass.setError("enter minimum 7 character password");
        }else if (S_ConfirmPas.isEmpty() || !S_ConfirmPas.equals(S_pass)){
            userConfirmPass.setError("password mismatch");
        }else if (mobileNo.isEmpty() || mobileNo.length()!=10) {
            number.setError("enter valid number length");
        }else {
            MoveToNext();
        }
    }

    private void MoveToNext() {
        String completeNumber=countryCode+mobileNo;
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");
            api.UserRegister(S_name, S_email, completeNumber, S_pass, "Android", reg_id).enqueue(new Callback<RegisterModel>() {
                @Override
                public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            App.getAppPreferences().saveUserId(activity, response.body().getDetails().getId());
                            App.getAppPreferences().saveUserIdTemp(activity,"1");
                            Toast.makeText(activity, response.body().getDetails().getOtp() + "", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, VarificationActivity.class));
                        } else {
                            Toast.makeText(activity, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegisterModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t + "", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }
}
