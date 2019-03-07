package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omninos.freshup.Adapters.SingleProductAdapter;
import com.omninos.freshup.Fragment.BottomsheetFragment;
import com.omninos.freshup.ModelClasses.AddToCartModel;
import com.omninos.freshup.ModelClasses.SingleProductCategoryModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleProductActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView single_product_items;
    private ImageView back, itemImage, backImage;
    private TextView item_name;

    private SingleProductActivity activity;
    private SingleProductAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        activity = SingleProductActivity.this;

        initView();
        SetUps();
    }

    private void initView() {
        single_product_items = findViewById(R.id.single_product_items);
        back = findViewById(R.id.back);
        itemImage = findViewById(R.id.product_item);
        item_name = findViewById(R.id.item_text);
        backImage = findViewById(R.id.backImage);
        item_name.setText(getIntent().getStringExtra("Title"));
        Glide.with(activity).load(getIntent().getStringExtra("Image")).into(itemImage);
        Glide.with(activity).load(getIntent().getStringExtra("backImage")).into(backImage);
    }

    private void SetUps() {
        single_product_items.setLayoutManager(new GridLayoutManager(this, 2));


        if (App.getAppPreferences().getProduct() != null) {

            adapter = new SingleProductAdapter(this, App.getAppPreferences().getProduct(), new SingleProductAdapter.PayPayment() {
                @Override
                public void Payment(int position, String status) {
                    if (status.equalsIgnoreCase("Move")) {
                        App.getAppPreferences().setSetcart("Data");
                        startActivity(new Intent(activity, HomeActivity.class));
                        finishAffinity();
                    } else {
                        AddtoCart(App.getAppPreferences().getProduct().get(position).getId());
                    }
                }

                @Override
                public void OpenDetail(int position) {
                    List<String> items = Arrays.asList(App.getAppPreferences().getProduct().get(position).getProductImage().split(","));
                    App.getAppPreferences().setMultipleImages(items);
                    BottomsheetFragment fragment = new BottomsheetFragment();
                    Bundle args = new Bundle();
                    args.putString("Desc", App.getAppPreferences().getProduct().get(position).getDescription());
                    fragment.setArguments(args);
                    fragment.show(getSupportFragmentManager(), "Hii");
                }
            });


            single_product_items.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No detail found", Toast.LENGTH_SHORT).show();
        }

        back.setOnClickListener(this);
    }

    private void AddtoCart(String id) {
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");
            api.addtocart(App.getAppPreferences().getUserId(activity), id, 1).enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {

                        if (response.body().getSuccess().equalsIgnoreCase("1")) {

                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            App.getAppPreferences().setSetcart("Data");
                            startActivity(new Intent(activity, HomeActivity.class));
                            finishAffinity();

                        } else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<AddToCartModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();

                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
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
