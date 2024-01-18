package com.omninos.freshup.Fragment;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.Adapters.OrderHistoryAdapter;
import com.omninos.freshup.ModelClasses.OrderHistoryModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;
import com.omninos.freshup.util.LocaleHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment implements View.OnClickListener {


    private RecyclerView recyclerView;
    private TextView noData;
    private OrderHistoryAdapter adapter;
    private List<OrderHistoryModel> list = new ArrayList<>();
    private List<OrderHistoryModel.Detail> details = new ArrayList<>();
    private TextView item_text;

    Context context;
    Resources resources;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        context = LocaleHelper.setLocale(getActivity(), App.getAppPreferences().getLanguage(getActivity()));
        resources = context.getResources();


        recyclerView = view.findViewById(R.id.recyclerView);
        noData = view.findViewById(R.id.noData);

        item_text = view.findViewById(R.id.item_text);
        item_text.setText(resources.getString(R.string.order_history));
        noData.setText(resources.getString(R.string.no_items));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        getOrderList();


        return view;
    }

    private void getOrderList() {
        if (CommonUtils.isNetworkConnected(getActivity())) {

            CommonUtils.showProgress(getActivity(), "");
            Api api = ApiClient.getApiClient().create(Api.class);

            api.orderHistory(App.getAppPreferences().getUserId(getActivity())).enqueue(new Callback<OrderHistoryModel>() {
                @Override
                public void onResponse(Call<OrderHistoryModel> call, Response<OrderHistoryModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {

                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            noData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            int size = response.body().getDetails().size();

                            for (int i = 0; i < size; i++) {
                                OrderHistoryModel model = new OrderHistoryModel();
                                OrderHistoryModel.Detail detail = new OrderHistoryModel.Detail();
                                detail.setOrderId(response.body().getDetails().get(i).getOrderId());
                                detail.setCreated(response.body().getDetails().get(i).getCreated());
                                detail.setPrice(response.body().getDetails().get(i).getPrice());
                                detail.setQuantity(response.body().getDetails().get(i).getQuantity());
                                detail.setTitle(response.body().getDetails().get(i).getTitle());
                                detail.setProductImage(response.body().getDetails().get(i).getProductImage());
                                details.add(detail);
                                model.setDetails(details);
                                list.add(model);
                            }
                            adapter = new OrderHistoryAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                        } else {
                            noData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<OrderHistoryModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    noData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
