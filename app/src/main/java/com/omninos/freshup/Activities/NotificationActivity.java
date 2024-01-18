package com.omninos.freshup.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.util.LocaleHelper;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private String barbarName, price, timeSlote, AppointmentDate, services, subtitle, Message, bookingServiceId;
    private TextView barberName, prices, timeData, dateData, servicesData, message;
    private Button Pay;

    private TextView textService,textBarberName,textAppointment,textSlot,textPrice;

    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        barbarName = getIntent().getStringExtra("barbarName");
        Message = getIntent().getStringExtra("message");
        price = getIntent().getStringExtra("price");
        timeSlote = getIntent().getStringExtra("timeSlote");
        AppointmentDate = getIntent().getStringExtra("AppointmentDate");
        services = getIntent().getStringExtra("services");
        subtitle = getIntent().getStringExtra("subtitle");
        bookingServiceId = getIntent().getStringExtra("bookingServiceId");

        context = LocaleHelper.setLocale(NotificationActivity.this, App.getAppPreferences().getLanguage(NotificationActivity.this));
        resources = context.getResources();



        initView();
        Changelanguage();
        SetUps();
    }

    private void Changelanguage() {
        textService.setText(resources.getString(R.string.services));
        textBarberName.setText(resources.getString(R.string.barber_name));
        textAppointment.setText(resources.getString(R.string.appointment_date));
        textSlot.setText(resources.getString(R.string.time_slot));
        textPrice.setText(resources.getString(R.string.price));
    }

    private void initView() {
        barberName = findViewById(R.id.barberName);
        prices = findViewById(R.id.price);
        timeData = findViewById(R.id.timeData);
        dateData = findViewById(R.id.dateData);
        servicesData = findViewById(R.id.services);
        Pay = findViewById(R.id.Pay);
        message = findViewById(R.id.message);


        textService=findViewById(R.id.textService);
        textBarberName=findViewById(R.id.textBarberName);
        textAppointment=findViewById(R.id.textAppointment);
        textSlot=findViewById(R.id.textSlot);
        textPrice=findViewById(R.id.textPrice);

    }

    private void SetUps() {
        barberName.setText(barbarName);
        prices.setText("€" + price);
        timeData.setText(timeSlote);
        dateData.setText(AppointmentDate);
        servicesData.setText(services);
        message.setText(Message);
        if (!Message.isEmpty()) {
            if (Message.endsWith("accepted")) {
                Pay.setVisibility(View.VISIBLE);
                Pay.setOnClickListener(this);
            } else {
                Pay.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Pay:
                MoveToPayment();
                break;
        }
    }

    private void MoveToPayment() {
        Intent intent = new Intent(NotificationActivity.this, PaymentActivity.class);
        intent.putExtra("Type", "Services");
        intent.putExtra("BookingServiceId", bookingServiceId);
        intent.putExtra("amount", price);
        startActivity(intent);
    }
}
