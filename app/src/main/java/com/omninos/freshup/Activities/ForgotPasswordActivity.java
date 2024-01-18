package com.omninos.freshup.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;
import com.omninos.freshup.util.LocaleHelper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private Button send;
    ForgotPasswordActivity activity;
    private TextView sinText, emailText;


    Context context;
    Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        activity = ForgotPasswordActivity.this;


        context = LocaleHelper.setLocale(ForgotPasswordActivity.this, App.getAppPreferences().getLanguage(ForgotPasswordActivity.this));
        resources = context.getResources();


        initView();
        changeLanguage();
        SetUps();


    }

    private void changeLanguage() {
        send.setText(resources.getString(R.string.send_my_password));
        sinText.setText(resources.getString(R.string.forgot_password_text));
        emailText.setText(resources.getString(R.string.email));
        email.setHint(resources.getString(R.string.email));

    }

    private void initView() {
        send = findViewById(R.id.sendPassword);
        email = findViewById(R.id.email);

        sinText = findViewById(R.id.sinText);
        emailText = findViewById(R.id.emailText);
    }

    private void SetUps() {
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sendCodeToEmail();
    }

    private void sendCodeToEmail() {
        String et_email = email.getText().toString().trim();

        if (et_email.isEmpty()) {
            Toast.makeText(activity, "enter email", Toast.LENGTH_SHORT).show();
        } else {
            if (CommonUtils.isNetworkConnected(activity)) {


                Api api = ApiClient.getApiClient().create(Api.class);

                CommonUtils.showProgress(activity, "");
                api.forgetPassword(et_email).enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        CommonUtils.dismissProgress();
                        if (response.isSuccessful()) {
                            Toast.makeText(activity, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, LoginActivity.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(activity, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        CommonUtils.dismissProgress();
                        Toast.makeText(activity, t + "", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
