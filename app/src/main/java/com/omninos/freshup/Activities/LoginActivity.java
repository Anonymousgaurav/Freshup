package com.omninos.freshup.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.omninos.freshup.ModelClasses.GetProfilePojo;
import com.omninos.freshup.ModelClasses.SocialLoginModelClass;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;
import com.omninos.freshup.util.Constants;
import com.omninos.freshup.util.InstagramApp;
import com.omninos.freshup.util.LocaleHelper;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    CallbackManager callbackManager;
    private LinearLayout moveToSignUp;
    private EditText Useremail, password;
    private Button singIn, fb_signIn, InstaLogin;
    private String Email, Pass, reg_id;
    private LoginActivity activity;
    private TextView forgotPass, sinText, textEmail, textPassword, second, donthave, signUptext;
    private String userIdfacebook, firstName, lastName, email, socialUsersername, SocialImage = "";
    private URL profilePicture;

    AccessToken accessToken;

    private InstagramApp mApp;

    Context context;
    Resources resources;


    //Instagram Login
    private HashMap<String, String> userInfoHashmap = new HashMap<String, String>();
    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == InstagramApp.WHAT_FINALIZE) {
                userInfoHashmap = mApp.getUserInfo();
            } else if (msg.what == InstagramApp.WHAT_FINALIZE) {
                Toast.makeText(LoginActivity.this, "Check your network.",
                        Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;


        context = LocaleHelper.setLocale(LoginActivity.this, App.getAppPreferences().getLanguage(LoginActivity.this));
        resources = context.getResources();


        initView();
        ChangeLanguage();

        InitFacebook();
        SetUp();


        //Instagram Login
        mApp = new InstagramApp(this, Constants.CLIENT_ID,
                Constants.CLIENT_SECRET, Constants.CALLBACK_URL);
        mApp.setListener(new InstagramApp.OAuthAuthenticationListener() {

            @Override
            public void onSuccess() {

                mApp.fetchUserName(handler);

                //Save instagram data
                App.getAppPreferences().SaveSocailType(activity, "Insta");
                Intent intent = new Intent(activity, MobileNumberActivity.class);
                intent.putExtra("name", App.getAppPreferences().getInstaUserName());
                intent.putExtra("SocailId", App.getAppPreferences().getInstaId());
                intent.putExtra("email", "");
                intent.putExtra("image", App.getAppPreferences().getInstaUserPicture());
                startActivity(intent);

            }

            @Override
            public void onFail(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT)
                        .show();
            }
        });


        if (mApp.hasAccessToken()) {
            // tvSummary.setText("Connected as " + mApp.getUserName());
            mApp.fetchUserName(handler);

        }

    }

    private void ChangeLanguage() {


        password.setHint(resources.getString(R.string.password));
        sinText.setText(resources.getString(R.string.sign_in));
        textEmail.setText(resources.getString(R.string.email));
        Useremail.setHint(resources.getString(R.string.email));
        textEmail.setText(resources.getString(R.string.email));
        textPassword.setText(resources.getString(R.string.password));
        forgotPass.setText(resources.getString(R.string.forgot_password));
        singIn.setText(resources.getString(R.string.sign_in));
        second.setText(resources.getString(R.string.or_login_with));
        donthave.setText(resources.getString(R.string.don_t_have_an_account));
        signUptext.setText(resources.getString(R.string.sign_up));

    }

    //facebook Login
    private void InitFacebook() {
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    //find all Id's
    private void initView() {
        moveToSignUp = findViewById(R.id.moveToSignUp);
        Useremail = findViewById(R.id.email);
        password = findViewById(R.id.password);
        singIn = findViewById(R.id.signIn);
        forgotPass = findViewById(R.id.forgotPass);
        fb_signIn = findViewById(R.id.fb_signIn);
        InstaLogin = findViewById(R.id.InstaLogin);


        sinText = findViewById(R.id.sinText);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        second = findViewById(R.id.second);
        donthave = findViewById(R.id.donthave);
        signUptext = findViewById(R.id.signUptext);


    }

    //Setup Actions
    private void SetUp() {
        moveToSignUp.setOnClickListener(this);
        singIn.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
        fb_signIn.setOnClickListener(this);
        InstaLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moveToSignUp:
                //Move to Register activity
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;

            case R.id.signIn:
                //Check Validations
                Validate();
                break;

            case R.id.forgotPass:
                //Move to forgot screen
                startActivity(new Intent(this, ForgotPasswordActivity.class));

                break;

            case R.id.fb_signIn:
                //FB Login
                LoginWithFaceBook();
                break;
            case R.id.InstaLogin:
                //Instagram Login
                LoginInsta();
                break;
        }
    }

    //Instagram Login
    private void LoginInsta() {
//        if (mApp.hasAccessToken()) {
//            final AlertDialog.Builder builder = new AlertDialog.Builder(
//                    LoginActivity.this);
//            builder.setMessage("Disconnect from Instagram?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    mApp.resetAccessToken();
//                                }
//                            })
//                    .setNegativeButton("No",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    dialog.cancel();
//                                }
//                            });
//            final AlertDialog alert = builder.create();
//            alert.show();
//        } else {
        mApp.authorize();
//        }
    }

    //get facebook Data
    private void LoginWithFaceBook() {

        if (CommonUtils.isNetworkConnected(activity)) {
            LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            Log.e("LoginActivity", "onSuccess method called");
                            getFacebookData(loginResult);
                            accessToken = loginResult.getAccessToken();
                            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                            Log.e("LoginActivity", "accessToken=>" + accessToken);
                        }

                        @Override
                        public void onCancel() {
                            Log.e("LoginActivity", "onCancel() method called");
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            Log.e("LoginActivity", "onError method call" + exception);
                        }
                    });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }

    }

    //Get facebook Data
    private void getFacebookData(LoginResult loginResult) {

        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object != null) {
                    try {
                        if (object.has("id")) {

                            userIdfacebook = object.getString("id");

                            Log.e("LoginActivity", "id" + userIdfacebook);

                        }
                        if (object.has("first_name")) {
                            firstName = object.getString("first_name");
                            Log.e("LoginActivity", "first_name" + firstName);

                        }
                        //check permisson last userName
                        if (object.has("last_name")) {
                            lastName = object.getString("last_name");
                            Log.e("LoginActivity", "last_name" + lastName);
                        }
                        //check permisson email
                        if (object.has("email")) {
                            email = object.getString("email");
                            Log.e("LoginActivity", "email" + email);
                        }

                        socialUsersername = firstName + " " + lastName;
                        Log.e("LoginActivity", "fbLastName" + socialUsersername);

                        JSONObject jsonObject = new JSONObject(object.getString("picture"));
                        Log.e("Loginactivity", "json oject get picture" + jsonObject);
                        if (jsonObject != null) {
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            Log.e("Loginactivity", "json oject get picture" + dataObject);
                            profilePicture = new URL("https://graph.facebook.com/" + userIdfacebook + "/picture?width=500&height=500");
                            Log.e("LoginActivity", "json object=>" + profilePicture);
                        }

                        if (email == null || email.equalsIgnoreCase("")) {
//                            CommonUtil.showSnackbarAlert(tvTitle, "Email not found in your facebook account");
                            Toast.makeText(activity, "Email not found in your facebook account", Toast.LENGTH_SHORT).show();
                            LogOut();
                        } else {
                            SocialImage = String.valueOf(profilePicture);
                            App.getAppPreferences().SaveSocailType(activity, "FB");
                            CheckSocialId();
//                            Intent intent = new Intent(activity, MobileNumberActivity.class);
//                            intent.putExtra("name", socialUsersername);
//                            intent.putExtra("SocailId", userIdfacebook);
//                            intent.putExtra("email", email);
//                            intent.putExtra("image", SocialImage);
//                            startActivity(intent);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Set Permissions
        Bundle bundle = new Bundle();
        Log.e("LoginActivity", "bundle set");
        bundle.putString("fields", "id, first_name, last_name,email,picture,gender");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    //Check user already register with Social media or not
    private void CheckSocialId() {
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");

            api.CheckSocialId(userIdfacebook).enqueue(new Callback<SocialLoginModelClass>() {
                @Override
                public void onResponse(Call<SocialLoginModelClass> call, Response<SocialLoginModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            App.getAppPreferences().saveToken(activity);
                            App.getAppPreferences().saveUserId(activity, response.body().getDetails().getId());
                            startActivity(new Intent(activity, HomeActivity.class));
                            finishAffinity();
                        } else {
                            Intent intent = new Intent(activity, MobileNumberActivity.class);
                            intent.putExtra("name", socialUsersername);
                            intent.putExtra("SocailId", userIdfacebook);
                            intent.putExtra("email", email);
                            intent.putExtra("image", SocialImage);
                            startActivity(intent);
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<SocialLoginModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    //Facebook Logout
    private void LogOut() {
        LoginManager.getInstance().logOut();
    }


    //Check validations
    private void Validate() {
        Email = Useremail.getText().toString().trim();
        Pass = password.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{2,4}";
        if (Email.isEmpty() || !Email.matches(emailPattern)) {
            Useremail.setError("enter valid Useremail");
        } else if (Pass.isEmpty()) {
            password.setError("enter password");
        } else {
            UserLogin();
        }
    }

    //Userlogin
    private void UserLogin() {

        reg_id = FirebaseInstanceId.getInstance().getToken();
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");
            api.UserLogin(Email, Pass, reg_id, "Android").enqueue(new Callback<GetProfilePojo>() {
                @Override
                public void onResponse(Call<GetProfilePojo> call, Response<GetProfilePojo> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            //if number is register
                            App.getAppPreferences().saveToken(activity);
                            App.getAppPreferences().TempClear(activity);
                            App.getAppPreferences().saveUserId(activity, response.body().getDetails().getId());
                            startActivity(new Intent(activity, HomeActivity.class));
                            finishAffinity();
                        } else if (response.body().getSuccess().equalsIgnoreCase("2")) {
                            //if number is not register
                            App.getAppPreferences().saveUserId(activity, response.body().getDetails().getId());
                            Toast.makeText(activity, response.body().getDetails().getOtp() + "", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, VarificationActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetProfilePojo> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t + "", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    //callback from Facebook
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

}
