package com.omninos.freshup.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.omninos.freshup.BuildConfig;
import com.omninos.freshup.ModelClasses.GetProfilePojo;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;
import com.omninos.freshup.Utils.ImageUtil;
import com.omninos.freshup.util.Constants;
import com.omninos.freshup.util.InstagramApp;
import com.omninos.freshup.util.LocaleHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    CallbackManager callbackManager;
    private ProfileActivity activity;
    private ImageView editButton;
    private EditText et_name, et_mail, et_number;
    private RelativeLayout Logout;
    private int Editstatus = 0;
    private TextView saveButton, title, nameText, emailText, numberText, logoutText;
    private CircleImageView userImg;
    private ProgressBar progress;


    private String imagepath, tempNumber, Updatename, UpdateNumber, UpdateUserId;

    private MultipartBody.Part user_image;


    private InstagramApp mApp;
    private int status = 0;


    private File photoFile;
    private static final int GALLERY_REQUEST = 101;

    private static final int CAMERA_REQUEST = 102;

    private Uri uri;


    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        activity = ProfileActivity.this;

        context = LocaleHelper.setLocale(activity, App.getAppPreferences().getLanguage(activity));
        resources = context.getResources();


        mApp = new InstagramApp(activity, Constants.CLIENT_ID,
                Constants.CLIENT_SECRET, Constants.CALLBACK_URL);

        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(activity);
        initView();
        ChangeLanguage();
        setUps();
        DisableEdit();
        getUserProfile();
    }

    private void ChangeLanguage() {
        title.setText(resources.getString(R.string.profile));
        nameText.setText(resources.getString(R.string.name));
        emailText.setText(resources.getString(R.string.email));
        numberText.setText(resources.getString(R.string.phone_number));
        logoutText.setText(resources.getString(R.string.log_out));
    }


    private void getUserProfile() {

        if (CommonUtils.isNetworkConnected(activity)) {


            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");
            api.getProfile(App.getAppPreferences().getUserId(activity)).enqueue(new Callback<GetProfilePojo>() {
                @Override
                public void onResponse(Call<GetProfilePojo> call, Response<GetProfilePojo> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            et_name.setText(response.body().getDetails().getName());
                            et_mail.setText(response.body().getDetails().getEmail());
                            et_number.setText(response.body().getDetails().getPhone());
                            tempNumber = et_number.getText().toString();
                            if (response.body().getDetails().getImage() != null && !response.body().getDetails().getImage().equalsIgnoreCase("")) {
                                progress.setVisibility(View.VISIBLE);
                                Glide.with(activity).load(response.body().getDetails().getImage()).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        progress.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        progress.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).into(userImg);
                            }
                        } else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    private void setUps() {
        Logout.setOnClickListener(this);
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        userImg.setOnClickListener(this);
    }

    private void initView() {
        editButton = findViewById(R.id.editButton);
        et_name = findViewById(R.id.et_name);
        et_mail = findViewById(R.id.et_mail);
        et_number = findViewById(R.id.et_number);
        Logout = findViewById(R.id.Logout);
        saveButton = findViewById(R.id.saveButton);
        userImg = findViewById(R.id.userImg);
        progress = findViewById(R.id.progress);

        title = findViewById(R.id.title);
        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        numberText = findViewById(R.id.numberText);
        logoutText = findViewById(R.id.logoutText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Logout:
                if (App.getAppPreferences().GetSocialType(activity).equalsIgnoreCase("FB")) {
                    FBLogout();
                } else if (mApp.hasAccessToken()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            activity);
                    builder.setMessage("Logout from FreshUp?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            mApp.resetAccessToken();
                                            LogOutAccount();
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    LogOutAccount();
                }
                break;

            case R.id.editButton:
                status = 1;
                saveButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.GONE);
                EnableEdit();
                break;

            case R.id.saveButton:
                status = 0;
                saveButton.setVisibility(View.GONE);
                editButton.setVisibility(View.VISIBLE);
                SaveData();
                break;

            case R.id.userImg:
                if (status == 1) {
                    OpenDialog();
                    GetPermissions();
                } else {
                }
                break;
        }
    }


    private void FBLogout() {
        LoginManager.getInstance().logOut();
        App.getAppPreferences().Logout(activity);
        startActivity(new Intent(activity, LoginActivity.class));
        activity.finishAffinity();
    }

    private void GetPermissions() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE + Manifest.permission.WRITE_EXTERNAL_STORAGE + Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
        } else {
            OpenDialog();
            Toast.makeText(activity, "Granted", Toast.LENGTH_SHORT).show();
        }

    }

    private void OpenDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


//    private void galleryIntent() {
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, PICK_FROM_GALLERY);
//    }
//
//    private void cameraIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
//        }
//    }
//
//    @SuppressLint("LongLogTag")
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK)
//            switch (requestCode) {
//                case PICK_FROM_GALLERY:
//                    Uri selectedImage = data.getData();
//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedImage);
//                        userImg.setImageBitmap(bitmap);
//
//                        Log.d("onActivityResult: ", getRealPathFromUri(selectedImage));
//                        imagepath = getRealPathFromUri(selectedImage);
//                        App.getAppPreferences().saveUserImagePath(activity, imagepath);
////                        CommonUtil.SaveImgPath(activity, p);
//                    } catch (IOException e) {
//                        Log.i("TAG", "Some exception " + e);
//                    }
//                    break;
//
//                case CAMERA_REQUEST:
//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    userImg.setImageBitmap(imageBitmap);
//                    // Log.d( "onActivityResult: FromData", String.valueOf(data.getData()));
//                    Log.d("onActivityResult: FromMethod", String.valueOf(getImageUri(activity, imageBitmap)));
//                    Uri captureImage = getImageUri(activity, imageBitmap);
//                    imagepath = getRealPathFromUri(captureImage);
//                    App.getAppPreferences().saveUserImagePath(activity, imagepath);
////                    CommonUtil.SaveImgPath(activity, p);
//                    Log.d("onActivityResult: Path", imagepath);
//
//            }
//    }
//
//
//    private Uri getImageUri(Context context, Bitmap bitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
//        return Uri.parse(path);
//    }
//
//    private String getRealPathFromUri(Uri tempUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = {MediaStore.Images.Media.DATA};
//            cursor = activity.getContentResolver().query(tempUri, proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }

    //choose from camera
    private void cameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            photoFile = null;
            photoFile = ImageUtil.getTemporaryCameraFile();
            if (photoFile != null) {
                Uri uri = FileProvider.getUriForFile(ProfileActivity.this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(pictureIntent,
                        CAMERA_REQUEST);
            }
        }
    }

    //Choose from gallery
    private void galleryIntent() {
        //gallery intent
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("profilePic/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == CAMERA_REQUEST) {
                //set image path
                imagepath = ImageUtil.compressImage(photoFile.getAbsolutePath());
                App.getAppPreferences().saveUserImagePath(activity, ImageUtil.compressImage(photoFile.getAbsolutePath()));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap mBitmapInsurance = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
                //set image preview on imageView
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotated = Bitmap.createBitmap(mBitmapInsurance, 0, 0, mBitmapInsurance.getWidth(), mBitmapInsurance.getHeight(),
                        matrix, true);

                if (Build.VERSION.SDK_INT > 25 && Build.VERSION.SDK_INT < 27) {
                    // Do something for lollipop and above versions
                    userImg.setImageBitmap(rotated);
                } else {
                    // do something for phones running an SDK before lollipop
                    userImg.setImageBitmap(mBitmapInsurance);
                }
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            uri = data.getData();
            if (uri != null) {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index_data = cursor.getColumnIndex(projection[0]);
                cursor.moveToFirst();
                imagepath = cursor.getString(column_index_data);
                App.getAppPreferences().saveUserImagePath(activity, imagepath);
                //set image preview on Image View
                Glide.with(activity).load("file://" + imagepath).into(userImg);
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean read = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean write = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean camera = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                if (grantResults.length > 0 && read && write && camera) {
                    Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show();

                } else if (Build.VERSION.SDK_INT > 23 && !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE + Manifest.permission.WRITE_EXTERNAL_STORAGE + Manifest.permission.CAMERA)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Permissions");
                    builder.setMessage("Permissions are requeired");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(activity.getApplicationContext(), "Go to the setting for granting permissions", Toast.LENGTH_SHORT).show();
                            boolean sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    })
                            .create()
                            .show();

                } else {
                    Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
                }
                break;
        }
    }


    private void SaveData() {

        if (App.getAppPreferences().getUserImagePath(activity).equalsIgnoreCase("")) {
            Toast.makeText(activity, "Choose Image", Toast.LENGTH_SHORT).show();
        } else {
            Updatename = et_name.getText().toString();

            UpdateNumber = et_number.getText().toString();
            if (UpdateNumber.length() < 8) {
                Toast.makeText(activity, "enter valid number", Toast.LENGTH_SHORT).show();
            } else if (tempNumber.equalsIgnoreCase(UpdateNumber)) {
                UpdateNumber = "";
            } else {
                UpdateNumber = et_number.getText().toString();
            }
            UpdateUserId = App.getAppPreferences().getUserId(activity);

            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), Updatename);

            RequestBody numberBody = RequestBody.create(MediaType.parse("text/plain"), UpdateNumber);

            RequestBody IdBody = RequestBody.create(MediaType.parse("text/plain"), UpdateUserId);

            if (imagepath != null) {
                File file = new File(imagepath);
                final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                user_image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            } else {
//                File file = new File(App.getAppPreferences().getUserImagePath(activity));
                final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                user_image = MultipartBody.Part.createFormData("image", "", requestFile);
            }

            if (CommonUtils.isNetworkConnected(activity)) {


                Api api = ApiClient.getApiClient().create(Api.class);

                CommonUtils.showProgress(activity, "");
                api.updateProfile(IdBody, nameBody, numberBody, user_image).enqueue(new Callback<GetProfilePojo>() {
                    @Override
                    public void onResponse(Call<GetProfilePojo> call, Response<GetProfilePojo> response) {
                        CommonUtils.dismissProgress();
                        if (response.body() != null) {
                            if (response.body().getSuccess().equalsIgnoreCase("1")) {
                                Toast.makeText(activity, response.body().getDetails().getOtp(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(activity, VarificationActivity.class));
                                activity.finish();
                            } else if (response.body().getSuccess().equalsIgnoreCase("2")) {
                                status = 0;
                                saveButton.setVisibility(View.GONE);
                                editButton.setVisibility(View.VISIBLE);
                                getUserProfile();

                                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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


        DisableEdit();

    }

    private void DisableEdit() {
        et_name.setEnabled(false);
        et_mail.setEnabled(false);
        et_number.setEnabled(false);
    }

    private void EnableEdit() {
        et_name.setEnabled(true);
        et_mail.setEnabled(false);
        et_number.setEnabled(true);
        et_name.setFocusable(true);
        et_mail.setFocusable(false);
        et_number.setFocusable(true);
    }

    private void LogOutAccount() {
        App.getAppPreferences().Logout(activity);
        startActivity(new Intent(activity, LoginActivity.class));
        activity.finishAffinity();
    }
}
