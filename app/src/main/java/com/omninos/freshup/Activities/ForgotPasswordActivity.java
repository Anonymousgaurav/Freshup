package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.CommonUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private Button send;
    ForgotPasswordActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        activity=ForgotPasswordActivity.this;

        initView();
        SetUps();


    }

    private void initView() {
        send=findViewById(R.id.sendPassword);
        email=findViewById(R.id.email);
    }

    private void SetUps() {
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sendCodeToEmail();
    }

    private void sendCodeToEmail() {
        String et_email=email.getText().toString().trim();

        if (et_email.isEmpty()){
            Toast.makeText(activity, "enter email", Toast.LENGTH_SHORT).show();
        }else {
            if (CommonUtils.isNetworkConnected(activity)){


                Api api=ApiClient.getApiClient().create(Api.class);

                CommonUtils.showProgress(activity,"");
                api.forgetPassword(et_email).enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        CommonUtils.dismissProgress();
                        if (response.isSuccessful()){
                            Toast.makeText(activity, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity,LoginActivity.class));
                            finishAffinity();
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
}
