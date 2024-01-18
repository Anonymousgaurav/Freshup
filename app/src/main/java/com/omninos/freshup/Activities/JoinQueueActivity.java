package com.omninos.freshup.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.Adapters.QueueAdapter;
import com.omninos.freshup.ModelClasses.JoinQueueModel;
import com.omninos.freshup.ModelClasses.QueueModelClass;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;
import com.omninos.freshup.util.LocaleHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinQueueActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView barbarsQueue;
    private QueueAdapter adapter;
    private List<JoinQueueModel> list = new ArrayList<>();
    private JoinQueueActivity activity;
    private ImageView back;
    private String datedata;
    String currentTime;
    DateFormat timeFormat;
    private List<QueueModelClass> queueModelClasses = new ArrayList<>();
    private List<QueueModelClass.Detail> details = new ArrayList<>();
    private TextView item_text;


    Context context;
    Resources resources;

    Calendar cal;
    SimpleDateFormat sdf, sdf1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue);

        activity = JoinQueueActivity.this;
        context = LocaleHelper.setLocale(JoinQueueActivity.this, App.getAppPreferences().getLanguage(JoinQueueActivity.this));
        resources = context.getResources();


        initView();
        SetUp();

        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        datedata = sdf.format(cal.getTime());

        timeFormat = new SimpleDateFormat("HH:mm");
        final Date date = new Date();
        currentTime = timeFormat.format(date);

        getDataFromServer();
        RecyclerViewSetUps();
    }

    private void getDataFromServer() {
        if (CommonUtils.isNetworkConnected(activity)) {

            CommonUtils.showProgress(activity, "");

            Api api = ApiClient.getApiClient().create(Api.class);

            api.JoinQueue(datedata,currentTime).enqueue(new Callback<QueueModelClass>() {
                @Override
                public void onResponse(Call<QueueModelClass> call, Response<QueueModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            int size = response.body().getDetails().size();
                            for (int i = 0; i < size; i++) {
                                QueueModelClass queueModelClass = new QueueModelClass();
                                QueueModelClass.Detail detailList = new QueueModelClass.Detail();

                                detailList.setId(response.body().getDetails().get(i).getId());
                                detailList.setImage(response.body().getDetails().get(i).getImage());
                                detailList.setName(response.body().getDetails().get(i).getName());
                                detailList.setPhone(response.body().getDetails().get(i).getPhone());
                                detailList.setBookingDetails(response.body().getDetails().get(i).getBookingDetails());

                                details.add(detailList);
                                queueModelClass.setDetails(details);

                                queueModelClasses.add(queueModelClass);
                            }

                            adapter = new QueueAdapter(activity, queueModelClasses, new QueueAdapter.JoinQueue() {
                                @Override
                                public void join(int position) {
//                                    App.getAppPreferences().setBookingDetailList(queueModelClasses.get(position).getDetails().get(position).getBookingDetails());
//                                    if (queueModelClasses.get(position).getDetails().get(position).getBookingDetails().size() > 0) {
//                                        Intent intent = new Intent(activity, SeeYourTurnActivity.class);
//                                        intent.putExtra("name", queueModelClasses.get(position).getDetails().get(position).getName());
//                                        startActivity(intent);
//                                    }


                                    joinQue(queueModelClasses.get(position).getDetails().get(position).getId(), datedata);

                                }

                                @Override
                                public void See(int position) {
                                    App.getAppPreferences().setBookingDetailList(queueModelClasses.get(position).getDetails().get(position).getBookingDetails());
                                    if (queueModelClasses.get(position).getDetails().get(position).getBookingDetails().size() > 0) {
                                        Intent intent = new Intent(activity, SeeYourTurnActivity.class);
                                        intent.putExtra("name", queueModelClasses.get(position).getDetails().get(position).getName());
                                        startActivity(intent);
                                    }
                                }
                            });
                            barbarsQueue.setAdapter(adapter);
                        } else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<QueueModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void joinQue(String id, String datedata) {
        if (CommonUtils.isNetworkConnected(activity)) {

            CommonUtils.showProgress(activity, "");
            Api api = ApiClient.getApiClient().create(Api.class);
            api.justJoin(App.getAppPreferences().getUserId(activity), datedata, id).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().get("success").equals("1")) {
                            Toast.makeText(activity, "You are in Queue", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, HomeActivity.class));
                            finishAffinity();
                        } else {

                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }


    private void initView() {
        barbarsQueue = findViewById(R.id.barbarsQueue);
        back = findViewById(R.id.back);
        item_text=findViewById(R.id.item_text);

        item_text.setText(resources.getString(R.string.join_queue));
    }

    private void SetUp() {
        back.setOnClickListener(this);
    }

    private void RecyclerViewSetUps() {
        barbarsQueue.setLayoutManager(new GridLayoutManager(this, 2));

//        JoinQueueModel model = new JoinQueueModel("Hushan", "4");
//        list.add(model);
//
//        model = new JoinQueueModel("Arfid", "2");
//        list.add(model);
//
//        model = new JoinQueueModel("Shahid", "3");
//        list.add(model);
//
//        adapter = new QueueAdapter(this, list, new QueueAdapter.JoinQueue() {
//            @Override
//            public void join(int position) {
//                Intent move = new Intent(activity, SeeYourTurnActivity.class);
//                move.putExtra("Name", list.get(position).getName());
//                move.putExtra("No", list.get(position).getInQueue());
//                startActivity(move);
//            }
//        });
//
//        adapter.notifyDataSetChanged();
//        barbarsQueue.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
