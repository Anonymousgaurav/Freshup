package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omninos.freshup.Adapters.SubServicesAdapter;
import com.omninos.freshup.Adapters.SubSubServiceAdapter;
import com.omninos.freshup.ModelClasses.GetServicesDataModel;
import com.omninos.freshup.ModelClasses.GetSubServices;
import com.omninos.freshup.ModelClasses.GetSubSubServices;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubServicesActivity extends AppCompatActivity implements View.OnClickListener {

    SubServicesActivity activity;
    private RecyclerView subServices;
    private SubServicesAdapter adapter;
    private String serviceId, StrServiceId;
    private Button continuemove;
    private TextView item_title;
    private ImageView back, product_item, backImage;
    StringBuilder serviceId1;
    StringBuilder prices;
    private double priceData = 0.0;


    private List<GetServicesDataModel> list = new ArrayList<>();
    private List<GetServicesDataModel.Detail> detaillist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_services);
        activity = SubServicesActivity.this;
        initView();
        SetUps();
        SetUpRecyclerView();

    }

    private void SetUpRecyclerView() {
        subServices.setHasFixedSize(true);

        //toolbar.setTitle("Shop");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        subServices.setLayoutManager(linearLayoutManager);
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");

            api.getSubServices(serviceId).enqueue(new Callback<GetServicesDataModel>() {
                @Override
                public void onResponse(Call<GetServicesDataModel> call, Response<GetServicesDataModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {

                            int size = response.body().getDetails().size();

                            for (int i = 0; i < size; i++) {
                                GetServicesDataModel model = new GetServicesDataModel();
                                GetServicesDataModel.Detail detail = new GetServicesDataModel.Detail();

                                detail.setId(response.body().getDetails().get(i).getId());
                                detail.setTitle(response.body().getDetails().get(i).getTitle());
                                detail.setSubSubServices(response.body().getDetails().get(i).getSubSubServices());

                                detaillist.add(detail);
                                model.setDetails(detaillist);
                                list.add(model);
                            }

                            adapter = new SubServicesAdapter(activity, list, new SubServicesAdapter.MoveNext() {
                                @Override
                                public void NextClick(int position) {

                                }
                            });

                            subServices.setAdapter(adapter);


                        } else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetServicesDataModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        subServices = findViewById(R.id.subServices);

        serviceId = getIntent().getStringExtra("ServiceId");

        continuemove = findViewById(R.id.continuemove);

        product_item = findViewById(R.id.product_item);

        item_title = findViewById(R.id.item_title);

        back = findViewById(R.id.back);
        backImage = findViewById(R.id.backImage);

    }

    private void SetUps() {
        continuemove.setOnClickListener(this);
        back.setOnClickListener(this);
        Glide.with(activity).load(getIntent().getStringExtra("Image")).into(product_item);
        item_title.setText(getIntent().getStringExtra("Title"));
        Glide.with(activity).load(getIntent().getStringExtra("backImage")).into(backImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continuemove:
                checkListData();
                break;

            case R.id.back:
                onBackPressed();
                break;
        }
    }

    private void checkListData() {
        if (App.getAppPreferences().getServicesName() != null) {
            List<String> getSubServices = App.getAppPreferences().getServicesName();

            try {
                serviceId1 = new StringBuilder();
                for (String s : getSubServices) {
                    serviceId1.append(s + ",");
                }
                int size = serviceId1.length();
                serviceId1.deleteCharAt(size - 1);
                System.out.println("Data Is: " + serviceId1);
                StrServiceId = serviceId1.toString();
                System.out.println("Data Sys: " + StrServiceId);
                App.getAppPreferences().setSaveServiceName(StrServiceId);
                CheckPrice();
            } catch (Exception e) {
                Toast.makeText(this, "Choose Services", Toast.LENGTH_SHORT).show();
                serviceId1 = new StringBuilder();
            }
        } else {
            Toast.makeText(activity, "Select Services", Toast.LENGTH_SHORT).show();
        }
    }

    private void CheckPrice() {
        priceData = 0.0;
        if (App.getAppPreferences().getPriceList() != null) {

            List<Double> getPrice = App.getAppPreferences().getPriceList();

            try {
                prices = new StringBuilder();
                for (Double i : getPrice) {
                    priceData = priceData + i;
                }
                System.out.println("Total Price: " + priceData);
                App.getAppPreferences().setProductAmount(String.valueOf(priceData));
                Move();
            } catch (Exception e) {
                Toast.makeText(this, "Choose Services", Toast.LENGTH_SHORT).show();
                priceData = 0.0;
                prices = new StringBuilder();
            }
        } else {
            Toast.makeText(activity, "Select Sub Services", Toast.LENGTH_SHORT).show();
        }
    }


    private void Move() {
        Intent intent = new Intent(activity, MyServiceSecond.class);
        intent.putExtra("Price", priceData);
        intent.putExtra("ServiceId", serviceId);
        intent.putExtra("Title", item_title.getText().toString());
        intent.putExtra("Image", getIntent().getStringExtra("Image"));
        intent.putExtra("Heading", StrServiceId);
        intent.putExtra("backImage", getIntent().getStringExtra("backImage"));
        startActivity(intent);
    }
}
