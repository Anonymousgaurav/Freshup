package com.omninos.freshup.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.Activities.JoinQueueActivity;
import com.omninos.freshup.Activities.MyServiceFirstActivity;
import com.omninos.freshup.Activities.SubServicesActivity;
import com.omninos.freshup.Adapters.MyServicesAdapter;
import com.omninos.freshup.ModelClasses.GetHomeDataModel;
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
public class ServicesFragment extends Fragment implements View.OnClickListener {

    private HomeActivity activity;
    private RecyclerView servicesItems;
    private ImageView Imgback;
    private TextView serviceName;
    private List<GetHomeDataModel> list = new ArrayList<>();
    private List<GetHomeDataModel.Detail> detailList = new ArrayList<>();
    private MyServicesAdapter adapter;

    Context context;
    Resources resources;

    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        activity = (HomeActivity) getActivity();

        context = LocaleHelper.setLocale(activity, App.getAppPreferences().getLanguage(activity));
        resources = context.getResources();

        initView(view);

        getDataAndSetUpRecycler();

        return view;
    }

    private void getDataAndSetUpRecycler() {

        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);
            CommonUtils.showProgress(activity, "");
            api.getServices().enqueue(new Callback<GetHomeDataModel>() {
                @Override
                public void onResponse(Call<GetHomeDataModel> call, Response<GetHomeDataModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {

                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            int size = response.body().getDetails().size();

                            for (int i = 0; i < size; i++) {
                                GetHomeDataModel model = new GetHomeDataModel();
                                GetHomeDataModel.Detail detail = new GetHomeDataModel.Detail();

                                detail.setId(response.body().getDetails().get(i).getId());
                                detail.setTitle(response.body().getDetails().get(i).getTitle());
                                detail.setImage1(response.body().getDetails().get(i).getImage1());
                                detail.setImage2(response.body().getDetails().get(i).getImage2());
                                detail.setFrenchTitle(response.body().getDetails().get(i).getFrenchTitle());

                                detailList.add(detail);
                                model.setDetails(detailList);

                                list.add(model);
                            }

                            adapter = new MyServicesAdapter(activity, list, new MyServicesAdapter.MyClick() {
                                @Override
                                public void MyClicked(int position) {
                                    Intent intent = new Intent(activity, SubServicesActivity.class);
                                    if (App.getAppPreferences().getLanguage(activity).equalsIgnoreCase("fr")) {
                                        intent.putExtra("ServiceId", list.get(position).getDetails().get(position).getId());
                                        intent.putExtra("Title", list.get(position).getDetails().get(position).getFrenchTitle());
                                        intent.putExtra("backImage", list.get(position).getDetails().get(position).getImage2());
                                        intent.putExtra("Image", list.get(position).getDetails().get(position).getImage1());
                                    } else {
                                        intent.putExtra("ServiceId", list.get(position).getDetails().get(position).getId());
                                        intent.putExtra("Title", list.get(position).getDetails().get(position).getTitle());
                                        intent.putExtra("backImage", list.get(position).getDetails().get(position).getImage2());
                                        intent.putExtra("Image", list.get(position).getDetails().get(position).getImage1());
                                    }

                                    startActivity(intent);
                                }
                            });

                            servicesItems.setAdapter(adapter);

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

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(View view) {

        servicesItems = view.findViewById(R.id.servicesItems);
        servicesItems.setHasFixedSize(true);

        Imgback = view.findViewById(R.id.Imgback);
        serviceName = view.findViewById(R.id.serviceName);
        serviceName.setText(resources.getString(R.string.join_queue));
        Imgback.setOnClickListener(this);
        //toolbar=getSupportActionBar();

        //toolbar.setTitle("Shop");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        servicesItems.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Imgback:
                startActivity(new Intent(activity, JoinQueueActivity.class));
                break;
        }
    }
}
