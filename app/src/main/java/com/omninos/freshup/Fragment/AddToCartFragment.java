package com.omninos.freshup.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.Activities.PromoActivity;
import com.omninos.freshup.Adapters.AddTocartAdapter;
import com.omninos.freshup.ModelClasses.AddToCartModel;
import com.omninos.freshup.ModelClasses.GetAddToCartListModel;
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
public class AddToCartFragment extends Fragment implements View.OnClickListener {


    private RecyclerView add_to_card;
    AddTocartAdapter addTocartAdapter;
    private HomeActivity activity;
    private TextView totalPrice, emptyCart;
    private Button buyItems;
    List<GetAddToCartListModel> listModels = new ArrayList<>();
    List<GetAddToCartListModel.Detail> details = new ArrayList<>();
    List<AddToCartModel> addToCartModels = new ArrayList<>();
    List<AddToCartModel.Detail> cartListDetail = new ArrayList<>();
    List<String> SaveIds = new ArrayList<>();
    StringBuilder productIds;
    private String StrProductId;


    public AddToCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_to_cart, container, false);

        activity = (HomeActivity) getActivity();
        initView(view);
        SetUps(view);
        return view;
    }

    private void initView(View view) {
        add_to_card = view.findViewById(R.id.add_to_card);
        buyItems = view.findViewById(R.id.buyItems);
        totalPrice = view.findViewById(R.id.totalPrice);
        emptyCart = view.findViewById(R.id.emptyCard);

    }

    private void SetUps(View view) {

        buyItems.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        add_to_card.setLayoutManager(linearLayoutManager);

        getCartdataFromServer();

    }

    private void getCartdataFromServer() {
        if (details != null) {
            details.clear();
        }
        if (SaveIds != null) {
            SaveIds.clear();
        }
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            api.getCartList(App.getAppPreferences().getUserId(activity)).enqueue(new Callback<GetAddToCartListModel>() {
                @Override
                public void onResponse(Call<GetAddToCartListModel> call, Response<GetAddToCartListModel> response) {
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {

                            add_to_card.setVisibility(View.VISIBLE);
                            emptyCart.setVisibility(View.GONE);

                            int size = response.body().getDetails().size();
                            for (int i = 0; i < size; i++) {
                                SaveIds.add(response.body().getDetails().get(i).getId());
                                GetAddToCartListModel model = new GetAddToCartListModel();
                                GetAddToCartListModel.Detail detail = new GetAddToCartListModel.Detail();

                                detail.setCartPrice(response.body().getDetails().get(i).getCartPrice());
                                detail.setDescription(response.body().getDetails().get(i).getDescription());
                                detail.setId(response.body().getDetails().get(i).getId());
                                detail.setPrice(response.body().getDetails().get(i).getPrice());
                                detail.setProductId(response.body().getDetails().get(i).getProductId());
                                detail.setPaymentStatus(response.body().getDetails().get(i).getPaymentStatus());
                                detail.setQuantity(response.body().getDetails().get(i).getQuantity());
                                detail.setTitle(response.body().getDetails().get(i).getTitle());
                                detail.setProductImage(response.body().getDetails().get(i).getProductImage());

                                details.add(detail);
                                model.setDetails(details);

                                listModels.add(model);
                            }

                            App.getAppPreferences().setProductAmount(response.body().getTotalPrice());
                            totalPrice.setText("€" + response.body().getTotalPrice());

                            addTocartAdapter = new AddTocartAdapter(activity, details, "Get", new AddTocartAdapter.RemoveCart() {
                                @Override
                                public void RemoveData(int position) {
                                    getCartdataFromServer();
                                }

                                @Override
                                public void AddData(int position, int quantity) {
                                    AddtoCart(details.get(position).getProductId(), quantity);
                                    addTocartAdapter.notifyDataSetChanged();

                                }
                            });
                            addTocartAdapter.notifyDataSetChanged();
                            add_to_card.setAdapter(addTocartAdapter);

                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            add_to_card.setVisibility(View.GONE);
                            emptyCart.setVisibility(View.VISIBLE);
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<GetAddToCartListModel> call, Throwable t) {
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void AddtoCart(String id, int quantity) {
        if (CommonUtils.isNetworkConnected(activity)) {

            CommonUtils.showProgress(activity, "");

            Api api = ApiClient.getApiClient().create(Api.class);

            api.addtocart(App.getAppPreferences().getUserId(activity), id, quantity).enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            getCartdataFromServer();
                        } else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<AddToCartModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buyItems:
                try {
                    productIds = new StringBuilder();
                    for (String s : SaveIds) {
                        productIds.append(s + ",");
                    }
                    int size = productIds.length();
                    productIds.deleteCharAt(size - 1);
                    System.out.println("Data Is: " + productIds);
                    StrProductId = productIds.toString();
                    System.out.println("Data Sys: " + StrProductId);
                    App.getAppPreferences().setProductId(StrProductId);
                    startActivity(new Intent(activity, PromoActivity.class));
                } catch (Exception e) {
                    Toast.makeText(activity, "Choose Services", Toast.LENGTH_SHORT).show();
                    productIds = new StringBuilder();
                }
                break;
        }
    }
}
