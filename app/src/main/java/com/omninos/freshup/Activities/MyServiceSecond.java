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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omninos.freshup.Adapters.BarbarDetailAdapter;
import com.omninos.freshup.Adapters.TimeAdapter;
import com.omninos.freshup.ModelClasses.BarbarDetailsModel;
import com.omninos.freshup.ModelClasses.BookingServicesModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;
import com.omninos.freshup.util.LocaleHelper;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.eventbus.Event;
import noman.weekcalendar.listener.OnDateClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyServiceSecond extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView first, second;
    private TimeAdapter adapter;
    private WeekCalendar weekCalendar;
    private ImageView back, product_item, backImage;
    private TextView item_text, serviceTitle, datedayData, holidays, closeShop, sign, holidaysDate;
    private Button next;
    private RecyclerView babardetailList;
    private BarbarDetailAdapter barbarDetailAdapter;
    private MyServiceSecond activity;

    List<BarbarDetailsModel> list = new ArrayList<>();
    List<BarbarDetailsModel.Details> detailList = new ArrayList<>();
    String startTime, lastTime;

    StringBuilder serviceId;
    StringBuilder timeslots;

    Calendar cal;
    SimpleDateFormat sdf, sdf1;


    String currentDate, Price;
    DateFormat dateFormat;
    int dateDatavalue = 0;


    String StrServiceId, StrTimeslot, datedata = "", barbarId = "";

    Context context;
    Resources resources;

    private TextView dataDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_second);

        activity = MyServiceSecond.this;

        context = LocaleHelper.setLocale(MyServiceSecond.this, App.getAppPreferences().getLanguage(MyServiceSecond.this));
        resources = context.getResources();

        initView();
        ChangeLanguage();

        RecyclerViewSetUP();
        SetUps();


        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf1 = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        datedata = sdf.format(cal.getTime());
        App.getAppPreferences().setSelecteAppointmentDate(datedata);

        final Date date = new Date();
        currentDate = sdf.format(date);
        datedayData.setText(sdf1.format(date));
        getbarbarDetails(datedata);
        weekCalendar.reset();


        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                System.out.println("Date Most" + sdf.format(dateTime.toDate()));
                Log.d("day of month: ", String.valueOf(dateTime.dayOfMonth()));
                Log.d("day of year: ", String.valueOf(dateTime.dayOfYear()));
                Log.d("day of last: ", String.valueOf(dateTime.toDateTime().dayOfWeek().getDateTime()));

                StringTokenizer stringTokenizer = new StringTokenizer(String.valueOf(dateTime.toDateTime().dayOfWeek().getDateTime()), "T");
                String dateValue = stringTokenizer.nextElement().toString();
                System.out.println("Date: " + dateTime.toDateTime());
                datedata = dateValue;
                try {
                    Date select = sdf.parse(datedata);
                    Date Current = sdf.parse(currentDate);
//
                    if (Current.getTime() <= select.getTime()) {
                        dateDatavalue = 0;
                        getbarbarDetails(datedata);
                        App.getAppPreferences().setSelecteAppointmentDate(datedata);
                        datedayData.setText(sdf1.format(dateTime.toDate()));
                    } else {
                        dateDatavalue = 1;
                        Toast.makeText(activity, "Choose future date", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void ChangeLanguage() {
        dataDay.setText(resources.getString(R.string.select_the_date));
        sign.setText(resources.getString(R.string.pick_a_time_slot));
        next.setText(resources.getString(R.string.bt_next));
        closeShop.setText(resources.getString(R.string.shop_close));
    }


    private void getbarbarDetails(String datedata) {
        if (list != null) {
            list.clear();
        }
        if (CommonUtils.isNetworkConnected(activity)) {

            CommonUtils.showProgress(activity, "");

            Api api = ApiClient.getApiClient().create(Api.class);

            api.barbarDetail(datedata).enqueue(new Callback<BarbarDetailsModel>() {
                @Override
                public void onResponse(Call<BarbarDetailsModel> call, Response<BarbarDetailsModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        babardetailList.setVisibility(View.VISIBLE);
                        closeShop.setVisibility(View.GONE);
                        sign.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);

                        if (response.body().getDetails().getBarberDeatils() != null) {

                            int size = response.body().getDetails().getBarberDeatils().size();

                            for (int i = 0; i < size; i++) {
                                BarbarDetailsModel model = new BarbarDetailsModel();
                                BarbarDetailsModel.Details details = new BarbarDetailsModel.Details();

                                details.setBarberDeatils(response.body().getDetails().getBarberDeatils());
                                details.setTimeSlotDetails(response.body().getDetails().getTimeSlotDetails());

                                startTime = response.body().getDetails().getTimeSlotDetails().getStartTime();
                                lastTime = response.body().getDetails().getTimeSlotDetails().getEndTime();
                                holidays.setText(resources.getString(R.string.holiday) + " : " + response.body().getDetails().getTimeSlotDetails().getNonWorkingDays());
                                if (!response.body().getDetails().getTimeSlotDetails().getHolidayStartDate().isEmpty()) {
                                    holidaysDate.setText("Holidays Date: " + response.body().getDetails().getTimeSlotDetails().getHolidayStartDate() + " to " + response.body().getDetails().getTimeSlotDetails().getHolidayEndDate());
                                }
                                detailList.add(details);
                                model.setDetails(details);
                                list.add(model);
                            }

                            //displayTimeSlots(startTime,lastTime);
                            barbarDetailAdapter = new BarbarDetailAdapter(activity, list, new BarbarDetailAdapter.Selectbarbar() {
                                @Override
                                public void ChooseBarbar(int position) {

//                                App.getAppPreferences().setBarbarName(list.get(position).getDetails().getBarberDeatils().get(position).getId());
//                                barbarId=list.get(position).getDetails().getBarberDeatils().get(position).getId();

//                                Toast.makeText(activity, list.get(position).getDetails().getBarberDeatils().get(position).getName(), Toast.LENGTH_SHORT).show();

                                }
                            });
                            babardetailList.setAdapter(barbarDetailAdapter);

                        }
                    } else if (response.body().getSuccess().equalsIgnoreCase("2")) {
                        babardetailList.setVisibility(View.GONE);
                        sign.setVisibility(View.GONE);
                        closeShop.setVisibility(View.VISIBLE);
                        next.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BarbarDetailsModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void SetUps() {

        back.setOnClickListener(this);
    }

    private void initView() {
        datedayData = findViewById(R.id.datedayData);
        next = findViewById(R.id.moveToPayment);
        first = findViewById(R.id.firstRecycler);
        second = findViewById(R.id.secondRecycler);
        weekCalendar = findViewById(R.id.weekCalendar);
        back = findViewById(R.id.back);
        babardetailList = findViewById(R.id.babardetailList);

        item_text = findViewById(R.id.item_text);
        product_item = findViewById(R.id.product_item);
        backImage = findViewById(R.id.backImage);
        serviceTitle = findViewById(R.id.serviceTitle);
        holidays = findViewById(R.id.holidays);
        closeShop = findViewById(R.id.closeShop);
        sign = findViewById(R.id.sign);
        holidaysDate = findViewById(R.id.holidaysDate);

        dataDay = findViewById(R.id.dataDay);


        item_text.setText(getIntent().getStringExtra("Title"));
        Glide.with(activity).load(getIntent().getStringExtra("Image")).into(product_item);
        Glide.with(activity).load(getIntent().getStringExtra("backImage")).into(backImage);
        serviceTitle.setText(getIntent().getStringExtra("Heading"));
        Price = getIntent().getStringExtra("Price");


    }

    private void RecyclerViewSetUP() {
        first.setLayoutManager(new GridLayoutManager(this, 5));
        second.setLayoutManager(new GridLayoutManager(this, 5));


        next.setOnClickListener(this);

        babardetailList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.moveToPayment:
                BookingSeat();
//                startActivity(new Intent(this,PaymentActivity.class));
                break;
        }
    }

    private void BookingSeat() {
        if (App.getAppPreferences().getBarbarId() != null) {

            barbarId = App.getAppPreferences().getBarbarId();
        }
        List<String> getSubServices = App.getAppPreferences().getItemsdataSubServices();
        List<String> getSlotTime = App.getAppPreferences().getTimeSlote();

        try {
            serviceId = new StringBuilder();
            for (String s : getSubServices) {
                serviceId.append(s + ",");
            }
            int size = serviceId.length();
            serviceId.deleteCharAt(size - 1);
            System.out.println("Data Is: " + serviceId);
            StrServiceId = serviceId.toString();
            System.out.println("Data Sys: " + StrServiceId);
            try {
                timeslots = new StringBuilder();
                for (String s : getSlotTime) {
                    timeslots.append(s + ",");
                }
                int size1 = timeslots.length();
                timeslots.deleteCharAt(size1 - 1);
                System.out.println("Data Is: " + timeslots);
                StrTimeslot = timeslots.toString();
                System.out.println("Data Sys: " + StrTimeslot);

                if (dateDatavalue == 1) {
                    Toast.makeText(activity, "Choose date", Toast.LENGTH_SHORT).show();
                } else if (barbarId.equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Choose Barber", Toast.LENGTH_SHORT).show();
                } else {
//            BookingConfirms();
                    OpenConfirmBox();
                }

            } catch (Exception e) {
                Toast.makeText(this, "Choose Time slot", Toast.LENGTH_SHORT).show();
                timeslots = new StringBuilder();
            }


        } catch (Exception e) {
            Toast.makeText(this, "Choose Services", Toast.LENGTH_SHORT).show();
            serviceId = new StringBuilder();
        }

//        try {
//            timeslots = new StringBuilder();
//            for (String s : getSlotTime) {
//                timeslots.append(s + ",");
//            }
//            int size = timeslots.length();
//            timeslots.deleteCharAt(size - 1);
//            System.out.println("Data Is: " + timeslots);
//            StrTimeslot = timeslots.toString();
//            System.out.println("Data Sys: " + StrTimeslot);
//
//        } catch (Exception e) {
//            Toast.makeText(this, "Choose Time slot", Toast.LENGTH_SHORT).show();
//            timeslots = new StringBuilder();
////        }
//        if (dateDatavalue == 1) {
//            Toast.makeText(activity, "Choose date", Toast.LENGTH_SHORT).show();
//        } else if (barbarId.equalsIgnoreCase("")) {
//            Toast.makeText(activity, "Choose Barber", Toast.LENGTH_SHORT).show();
//        } else {
////            BookingConfirms();
//            OpenConfirmBox();
//        }
    }

    private void OpenConfirmBox() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.confirm_popup, null);

        TextView services = view.findViewById(R.id.services);
        TextView barbarname = view.findViewById(R.id.barbarname);
        TextView appointDate = view.findViewById(R.id.appointDate);
        TextView timeSlot = view.findViewById(R.id.timeSlot);
        Button done = view.findViewById(R.id.done);
        Button cancel = view.findViewById(R.id.cancel);
        TextView textService = view.findViewById(R.id.textService);
        TextView textBarberName = view.findViewById(R.id.textBarberName);
        TextView textAppointmentDate = view.findViewById(R.id.textAppointmentDate);
        TextView textSlote = view.findViewById(R.id.textSlote);

        textService.setText(resources.getString(R.string.services));
        textBarberName.setText(resources.getString(R.string.barber_name));
        textAppointmentDate.setText(resources.getString(R.string.appointment_date));
        textSlote.setText(resources.getString(R.string.time_slot));
        done.setText(resources.getString(R.string.done));
        cancel.setText(resources.getString(R.string.cancel));


        services.setText(App.getAppPreferences().getSaveServiceName());
        barbarname.setText(App.getAppPreferences().getBarbarName());
        appointDate.setText(datedata);
        timeSlot.setText(timeslots.toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                BookingConfirms();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    private void BookingConfirms() {
//        if (CommonUtils.isNetworkConnected(activity)) {
//
//            CommonUtils.showProgress(activity, "");
//            Api api = ApiClient.getApiClient().create(Api.class);
//
//            api.booking(App.getAppPreferences().getUserId(activity), StrServiceId, barbarId, datedata, StrTimeslot).enqueue(new Callback<BookingServicesModel>() {
//                @Override
//                public void onResponse(Call<BookingServicesModel> call, Response<BookingServicesModel> response) {
//                    CommonUtils.dismissProgress();
//                    if (response.body() != null) {
//                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
//                            Intent intent = new Intent(activity, HomeActivity.class);
//                            startActivity(intent);
//                            finishAffinity();
//
//                        } else {
//                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<BookingServicesModel> call, Throwable t) {
//                    CommonUtils.dismissProgress();
//                }
//            });
//
//
//        } else {
//            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
//        }

        Intent intent = new Intent(MyServiceSecond.this, PaymentActivity.class);
        intent.putExtra("Type", "MyServices");
        intent.putExtra("StrServiceId", StrServiceId);
        intent.putExtra("barbarId", barbarId);
        intent.putExtra("datedata", datedata);
        intent.putExtra("StrTimeslot", StrTimeslot);
        intent.putExtra("Price", Price);
        startActivity(intent);

    }

}
