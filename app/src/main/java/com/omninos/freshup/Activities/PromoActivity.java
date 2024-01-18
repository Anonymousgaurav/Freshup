package com.omninos.freshup.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.util.LocaleHelper;

public class PromoActivity extends AppCompatActivity implements View.OnClickListener {

    private PromoActivity activity;
    private Button skip, homeDelivery, pickfromShop, apply;
    private ImageView back;
    private TextView titlePage, textPromo, email;

    Context context;
    Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        activity = PromoActivity.this;
        context = LocaleHelper.setLocale(PromoActivity.this, App.getAppPreferences().getLanguage(PromoActivity.this));
        resources = context.getResources();


        initView();
        ChangeLanguage();
        SetUps();
    }

    private void ChangeLanguage() {
        titlePage.setText(resources.getString(R.string.promo_code));
        textPromo.setText(resources.getString(R.string.enter_your_promo_code));
        email.setHint(resources.getString(R.string.promo));
        apply.setText(resources.getString(R.string.apply));
        pickfromShop.setText(resources.getString(R.string.pick_from_shop));
        homeDelivery.setText(resources.getString(R.string.home_delivery));

    }

    private void initView() {
        skip = findViewById(R.id.skip);
        homeDelivery = findViewById(R.id.homeDelivery);
        pickfromShop = findViewById(R.id.pickFromShop);
        back = findViewById(R.id.back);

        titlePage = findViewById(R.id.titlePage);
        textPromo = findViewById(R.id.textPromo);
        email = findViewById(R.id.email);
        apply = findViewById(R.id.apply);
    }

    private void SetUps() {
        skip.setOnClickListener(this);
        homeDelivery.setOnClickListener(this);
        pickfromShop.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.skip:
//                startActivity(new Intent(activity,));
                break;
            case R.id.homeDelivery:
                Intent homeDeliver = new Intent(activity, DeliveryActivity.class);
                homeDeliver.putExtra("DeliveryType", "Home");
                startActivity(homeDeliver);
                break;

            case R.id.pickFromShop:
                Intent intent = new Intent(activity, PaymentActivity.class);
                intent.putExtra("City", "");
                intent.putExtra("State", "");
                intent.putExtra("Address", "");
                intent.putExtra("Zip", "");
                intent.putExtra("Country", "");
                intent.putExtra("Type", "Product");
                intent.putExtra("DeliverType", "Shop");
                startActivity(intent);
                break;
        }
    }
}
