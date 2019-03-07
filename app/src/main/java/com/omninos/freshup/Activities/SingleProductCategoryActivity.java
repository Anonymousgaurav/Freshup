package com.omninos.freshup.Activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omninos.freshup.Adapters.SingleProductCategoryAdapter;
import com.omninos.freshup.ModelClasses.SingleProductCategoryModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleProductCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView product_items;
    private ImageView back,itemImage,backImage;
    private TextView item_name;
    private SingleProductCategoryActivity activity;

    List<SingleProductCategoryModel> list=new ArrayList<>();
    List<SingleProductCategoryModel.Detail> detailList=new ArrayList<>();

    SingleProductCategoryAdapter adapter;

    private String categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_category);

        activity=SingleProductCategoryActivity.this;
        initView();
        SetUps();
        RecyclerViewSetUp();
    }

    private void SetUps() {

        Glide.with(activity).load(getIntent().getStringExtra("Image")).into(itemImage);
        item_name.setText(getIntent().getStringExtra("Title"));
        Glide.with(activity).load(getIntent().getStringExtra("backImage")).into(backImage);
        back.setOnClickListener(this);
    }

    private void RecyclerViewSetUp() {
        product_items.setLayoutManager(new GridLayoutManager(this, 2));

        if (App.getAppPreferences().getCategoryId()!=null){
            if (CommonUtils.isNetworkConnected(activity)){

                CommonUtils.showProgress(activity,"");

                Api api=ApiClient.getApiClient().create(Api.class);

                api.getProducts(App.getAppPreferences().getCategoryId(),App.getAppPreferences().getUserId(activity)).enqueue(new Callback<SingleProductCategoryModel>() {
                    @Override
                    public void onResponse(Call<SingleProductCategoryModel> call, Response<SingleProductCategoryModel> response) {

                        CommonUtils.dismissProgress();
                        if (response.body()!=null) {
                            if (response.body().getSuccess().equalsIgnoreCase("1")) {

                                int size=response.body().getDetails().size();

                                for (int i=0;i<size;i++){
                                    SingleProductCategoryModel model=new SingleProductCategoryModel();
                                    SingleProductCategoryModel.Detail detail=new SingleProductCategoryModel.Detail();

                                    detail.setCategoryId(response.body().getDetails().get(i).getCategoryId());
                                    detail.setId(response.body().getDetails().get(i).getId());
                                    detail.setTitle(response.body().getDetails().get(i).getTitle());
                                    detail.setImage(response.body().getDetails().get(i).getImage());
                                    detail.setProduct(response.body().getDetails().get(i).getProduct());
                                    detailList.add(detail);
                                    model.setDetails(detailList);

                                    list.add(model);
                                }

                                adapter=new SingleProductCategoryAdapter(activity, list, new SingleProductCategoryAdapter.MyCategoryClick() {
                                    @Override
                                    public void selectcategory(int position) {
                                        App.getAppPreferences().setProduct(list.get(position).getDetails().get(position).getProduct());
                                        Intent intent=new Intent(activity,SingleProductActivity.class);
                                        intent.putExtra("Image",getIntent().getStringExtra("Image"));
                                        intent.putExtra("Title",getIntent().getStringExtra("Title"));
                                        intent.putExtra("backImage",getIntent().getStringExtra("backImage"));
                                        startActivity(intent);
                                    }
                                });

                                product_items.setAdapter(adapter);

                            } else {
                                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleProductCategoryModel> call, Throwable t) {
                        Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initView() {
        product_items=findViewById(R.id.product_items);

        itemImage=findViewById(R.id.product_item);

        item_name=findViewById(R.id.item_text);

        back=findViewById(R.id.back);
        backImage=findViewById(R.id.backImage);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
