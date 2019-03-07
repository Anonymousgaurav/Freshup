package com.omninos.freshup.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.Activities.MyServiceFirstActivity;
import com.omninos.freshup.Activities.SingleProductCategoryActivity;
import com.omninos.freshup.Adapters.MyProductAdapter;
import com.omninos.freshup.Adapters.MyServicesAdapter;
import com.omninos.freshup.ModelClasses.GetHomeDataModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {


    private HomeActivity activity;
    private RecyclerView productItems;
    private List<GetHomeDataModel> list = new ArrayList<>();
    private List<GetHomeDataModel.Detail> detailList=new ArrayList<>();
    private MyProductAdapter adapter;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        activity = (HomeActivity) getActivity();
        initView(view);

        SetUpRecycler();

        return view;
    }

    private void SetUpRecycler() {
        if (CommonUtils.isNetworkConnected(activity)){

            Api api=ApiClient.getApiClient().create(Api.class);
            CommonUtils.showProgress(activity,"");
            api.getProduct().enqueue(new Callback<GetHomeDataModel>() {
                @Override
                public void onResponse(Call<GetHomeDataModel> call, Response<GetHomeDataModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body()!=null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            int size = response.body().getDetails().size();

                            for (int i = 0; i < size; i++) {
                                GetHomeDataModel model = new GetHomeDataModel();
                                GetHomeDataModel.Detail detail = new GetHomeDataModel.Detail();

                                detail.setId(response.body().getDetails().get(i).getId());
                                detail.setTitle(response.body().getDetails().get(i).getTitle());
                                detail.setImage1(response.body().getDetails().get(i).getImage1());
                                detail.setImage2(response.body().getDetails().get(i).getImage2());

                                detailList.add(detail);
                                model.setDetails(detailList);
                                list.add(model);
                            }
                            adapter = new MyProductAdapter(activity, list, new MyProductAdapter.MyClick() {
                                @Override
                                public void MyClicked(int position) {
                                    App.getAppPreferences().setCategoryId(list.get(position).getDetails().get(position).getId());
                                    Intent intent = new Intent(activity, SingleProductCategoryActivity.class);
                                    intent.putExtra("Title", list.get(position).getDetails().get(position).getTitle());
                                    intent.putExtra("Image", list.get(position).getDetails().get(position).getImage1());
                                    intent.putExtra("backImage", list.get(position).getDetails().get(position).getImage2());
                                    startActivity(intent);
                                }
                            });

                            productItems.setAdapter(adapter);

                        } else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetHomeDataModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                }
            });

        }else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(View view) {
        productItems = view.findViewById(R.id.productItems);
        productItems.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productItems.setLayoutManager(linearLayoutManager);

    }

}
