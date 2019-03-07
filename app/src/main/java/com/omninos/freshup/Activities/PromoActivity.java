package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.omninos.freshup.R;

public class PromoActivity extends AppCompatActivity implements View.OnClickListener {

    private PromoActivity activity;
    private Button skip,homeDelivery,pickfromShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        activity=PromoActivity.this;

        initView();
        SetUps();
    }

    private void initView() {
        skip=findViewById(R.id.skip);
        homeDelivery=findViewById(R.id.homeDelivery);
        pickfromShop=findViewById(R.id.pickFromShop);
    }

    private void SetUps() {
        skip.setOnClickListener(this);
        homeDelivery.setOnClickListener(this);
        pickfromShop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skip:
//                startActivity(new Intent(activity,));
                break;
            case R.id.homeDelivery:
                Intent homeDeliver=new Intent(activity,DeliveryActivity.class);
                homeDeliver.putExtra("DeliveryType","Home");
                startActivity(homeDeliver);
                break;

            case R.id.pickFromShop:
                Intent intent=new Intent(activity,PaymentActivity.class);
                intent.putExtra("City","");
                intent.putExtra("State","");
                intent.putExtra("Address","");
                intent.putExtra("Zip","");
                intent.putExtra("Country","");
                intent.putExtra("Type","Product");
                intent.putExtra("DeliverType","Shop");
                startActivity(intent);
                break;
        }
    }
}
