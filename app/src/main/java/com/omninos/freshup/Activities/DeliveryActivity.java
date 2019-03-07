package com.omninos.freshup.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hbb20.CountryCodePicker;
import com.omninos.freshup.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DeliveryActivity extends AppCompatActivity implements View.OnClickListener {

    private CountryCodePicker countryCodePicker;
    private String country, zip, sdPath, state = " ", city = " ", address = " ";
    private DeliveryActivity activity;
    private EditText et_state, et_city, et_address, et_zipCode;
    private Button proceed;
    private ImageView back;


    private LocationManager locationManager;
    private GoogleApiClient client;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(activity, PromoActivity.class));
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        activity = DeliveryActivity.this;
        getPermissions();
        initView();
        SetUps();
    }

    private void getPermissions() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION + Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            GetLocation();
            Toast.makeText(activity, "Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean loaction1 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean loaction2 = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (grantResults.length > 0 && loaction1 && loaction2) {

                    GetLocation();
                    Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show();

                } else if (Build.VERSION.SDK_INT > 23 && !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION + Manifest.permission.ACCESS_FINE_LOCATION)) {
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
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                }
                break;
        }
    }

    private void initView() {
        countryCodePicker = findViewById(R.id.ccp);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_address = findViewById(R.id.et_address);
        et_zipCode = findViewById(R.id.et_zipCode);
        proceed = findViewById(R.id.proceed);
        country = countryCodePicker.getSelectedCountryName();
        back = findViewById(R.id.back);

    }

    private void SetUps() {
        proceed.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ccp:
                openCountryPicker();
                break;

            case R.id.proceed:
                BookProduct();
                break;

            case R.id.back:
                startActivity(new Intent(activity, PromoActivity.class));
                finishAffinity();
                break;
        }

    }

    private void BookProduct() {
        state = et_state.getText().toString().trim();
        city = et_city.getText().toString().trim();
        address = et_address.getText().toString().trim();
        zip = et_zipCode.getText().toString().trim();
        if (state.isEmpty()) {
            Toast.makeText(activity, "Enter State", Toast.LENGTH_SHORT).show();
        } else if (city.isEmpty()) {
            Toast.makeText(activity, "Enter City", Toast.LENGTH_SHORT).show();
        } else if (address.isEmpty()) {
            Toast.makeText(activity, "Enter Address", Toast.LENGTH_SHORT).show();
        } else if (zip.isEmpty()) {
            Toast.makeText(activity, "Enter Zip", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(activity, PaymentActivity.class);
            intent.putExtra("City", city);
            intent.putExtra("State", state);
            intent.putExtra("Address", address);
            intent.putExtra("Zip", zip);
            intent.putExtra("Country", country);
            intent.putExtra("DeliverType", "Home");
            intent.putExtra("Type", "Product");
            startActivity(intent);
        }
    }

    private void openCountryPicker() {
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = countryCodePicker.getSelectedCountryName();
            }
        });
    }

    private void GetLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(DeliveryActivity.this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        et_zipCode.setText(postalCode);
                        et_address.setText(address);
                        et_city.setText(city);
                        et_state.setText(state);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } else if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(DeliveryActivity.this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();

                        et_zipCode.setText(postalCode);
                        et_address.setText(address);
                        et_city.setText(city);
                        et_state.setText(state);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
}
