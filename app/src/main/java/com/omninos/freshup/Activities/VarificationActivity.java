package com.omninos.freshup.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.ModelClasses.OtpPojo;
import com.omninos.freshup.ModelClasses.SimplePojo;
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

public class VarificationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText first, second, third, fourth;
    private Button varify;
    private String otp_1, otp_2, otp_3, otp_4, compeleteOtp, OTP;
    private VarificationActivity activity;
    private TextView resend, varifyText, demotext;


    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification);

        activity = VarificationActivity.this;

        context = LocaleHelper.setLocale(VarificationActivity.this, App.getAppPreferences().getLanguage(VarificationActivity.this));
        resources = context.getResources();


        initView();
        ChangeLanguage();


        //Single digit EditText
        first.addTextChangedListener(generalTextWatcher);
        second.addTextChangedListener(generalTextWatcher);
        third.addTextChangedListener(generalTextWatcher);
        fourth.addTextChangedListener(generalTextWatcher);
    }

    private void ChangeLanguage() {
        varifyText.setText(resources.getString(R.string.verification));
        demotext.setText(resources.getString(R.string.enter_your_verification_code));
        varify.setText(resources.getString(R.string.verify_code));
        resend.setText(resources.getString(R.string.resend_verification_code));
    }

    //Find All Id's
    private void initView() {
        //check otp null or not
        if (App.getAppPreferences().getOTP() != null) {
            OTP = App.getAppPreferences().getOTP();
        }
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        varify = findViewById(R.id.varify);
        resend = findViewById(R.id.resend);


        //set Actions
        varify.setOnClickListener(this);
        resend.setOnClickListener(this);

        varifyText = findViewById(R.id.varifyText);
        demotext = findViewById(R.id.demotext);
    }


    //Fill Otp fields
    TextWatcher generalTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (first.getText().hashCode() == editable.hashCode()) {
                otp_1 = first.getText().toString().trim();
                if (!otp_1.equalsIgnoreCase("")) {
                    second.requestFocus();
                }

            } else if (second.getText().hashCode() == editable.hashCode()) {
                otp_2 = second.getText().toString().trim();
                if (!otp_2.equalsIgnoreCase("")) {
                    third.requestFocus();
                } else {
                    first.requestFocus();
                }

            } else if (third.getText().hashCode() == editable.hashCode()) {
                otp_3 = third.getText().toString().trim();
                if (!otp_3.equalsIgnoreCase("")) {
                    fourth.requestFocus();
                } else {
                    second.requestFocus();
                }
            } else if (fourth.getText().hashCode() == editable.hashCode()) {
                otp_4 = fourth.getText().toString().trim();
                if (!otp_4.equalsIgnoreCase("")) {
                    hideKeyboard(activity);
                } else {
                    third.requestFocus();
                }

            }

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.varify:
                //Check Otp
                CheckOtp();
                break;
            case R.id.resend:
                //Resend Otp
                resendOtp();
                break;
        }
    }

    //Resend Otp
    private void resendOtp() {
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");

            api.resendToken(App.getAppPreferences().getUserId(activity)).enqueue(new Callback<OtpPojo>() {
                @Override
                public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            OTP = String.valueOf(response.body().getDetails().getOtp());
                            Toast.makeText(activity, response.body().getDetails().getOtp() + "", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OtpPojo> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t + "", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

        }
    }

    //Check Otp
    private void CheckOtp() {
        otp_1 = first.getText().toString().trim();
        otp_2 = second.getText().toString().trim();
        otp_3 = third.getText().toString().trim();
        otp_4 = fourth.getText().toString().trim();
        compeleteOtp = otp_1 + otp_2 + otp_3 + otp_4;
        if (compeleteOtp.length() != 4) {
            Toast.makeText(this, "enter values", Toast.LENGTH_SHORT).show();
        } else {
//            if (App.getAppPreferences().getOTP() != null) {
//                CheckOtpData();
//            } else {
            CheckOtpvalidation();
//            }
        }
    }

    private void CheckOtpData() {
        if (compeleteOtp.equalsIgnoreCase(OTP)) {
            openDailogBox();
        } else {
            Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }

    //check otp from server
    private void CheckOtpvalidation() {
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");
            api.matchToken(App.getAppPreferences().getUserId(activity), compeleteOtp).enqueue(new Callback<SimplePojo>() {
                @Override
                public void onResponse(Call<SimplePojo> call, Response<SimplePojo> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            App.getAppPreferences().saveToken(activity);
                            openDailogBox();
                        } else {
                            Toast.makeText(activity, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SimplePojo> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t + "", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDailogBox() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_dialog_box_varify, null);

        Button verify = view.findViewById(R.id.done);
        TextView varifyTexts=view.findViewById(R.id.varifyTexts);


        verify.setText(resources.getString(R.string.done));
        varifyTexts.setText(resources.getString(R.string.your_mobile_number_successfully_verified));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                startActivity(new Intent(activity, HomeActivity.class));
                finishAffinity();

            }
        });
        alertDialog.show();
    }


    //hide keyboard
    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
