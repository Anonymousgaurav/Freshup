package com.omninos.freshup.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.Adapters.AppointmentAdapter;
import com.omninos.freshup.ModelClasses.AppointmentModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;
import com.omninos.freshup.util.LocaleHelper;
import com.omninos.freshup.util.WrapContentLinearLayoutManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllAppointmentFragment extends Fragment {


    private RecyclerView appointmentList;
    private AppointmentAdapter adapter;

    Context activity;
    String currentTime;
    DateFormat timeFormat;
    private TextView noAppointment;

    private boolean doInOnAttach = true;


    List<AppointmentModel> list = new ArrayList<>();
    List<AppointmentModel.Detail> detailList = new ArrayList<>();


    Context context;
    Resources resources;
    public AllAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_appointment, container, false);

        activity = getActivity();
        context = LocaleHelper.setLocale(getActivity(), App.getAppPreferences().getLanguage(getActivity()));
        resources = context.getResources();

        initView(view);
        WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        appointmentList.setLayoutManager(linearLayoutManager);

//        SetUpRecyclerView();

        return view;
    }

    private void initView(View view) {
        appointmentList = view.findViewById(R.id.appointmentlist);
        noAppointment = view.findViewById(R.id.noAppointment);
        noAppointment.setText(resources.getString(R.string.no_appointment));

    }

    private void SetUpRecyclerView() {

        String UserId = App.getAppPreferences().getUserId1(getActivity());
        timeFormat = new SimpleDateFormat("HH:mm");
        final Date date = new Date();
        currentTime = timeFormat.format(date);
        if (list != null) {
            list.clear();
        }
        if (detailList != null) {
            detailList.clear();
        }
        if (CommonUtils.isNetworkConnected(getActivity())) {

            Api api = ApiClient.getApiClient().create(Api.class);

//            CommonUtils.showProgress(getActivity(), "");
            api.getAppointment(UserId, currentTime).enqueue(new Callback<AppointmentModel>() {
                @Override
                public void onResponse(Call<AppointmentModel> call, Response<AppointmentModel> response) {
//                     CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {

                            noAppointment.setVisibility(View.GONE);
                            appointmentList.setVisibility(View.VISIBLE);
                            int size = response.body().getDetails().size();
                            for (int i = 0; i < size; i++) {
                                AppointmentModel model = new AppointmentModel();

                                AppointmentModel.Detail detail = new AppointmentModel.Detail();
                                detail.setId(response.body().getDetails().get(i).getId());
                                detail.setUserStatus(response.body().getDetails().get(i).getUserStatus());
                                detail.setApointmentDate(response.body().getDetails().get(i).getApointmentDate());
                                detail.setUserId(response.body().getDetails().get(i).getUserId());
                                detail.setSubSubServiceTitle(response.body().getDetails().get(i).getSubSubServiceTitle());
                                detail.setTimeslot(response.body().getDetails().get(i).getTimeslot());
                                detail.setBarberName(response.body().getDetails().get(i).getBarberName());
                                detail.setPaymentMethod(response.body().getDetails().get(i).getPaymentMethod());
                                detail.setUpcomingPastApointment(response.body().getDetails().get(i).getUpcomingPastApointment());
                                detailList.add(detail);
                                model.setDetails(detailList);
                                list.add(model);
                            }

                            App.getAppPreferences().setAppointmentModels(list);

                            adapter = new AppointmentAdapter(getActivity(), list, "All", new AppointmentAdapter.CancelAppointment() {
                                @Override
                                public void Cancel(int position) {
                                    WantToCancelAppointment(position);
                                }
                            });
                            appointmentList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
//                            Toast.makeText(activity, "ALL", Toast.LENGTH_SHORT).show();

                        } else {
                            noAppointment.setVisibility(View.VISIBLE);
                            appointmentList.setVisibility(View.GONE);
//                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppointmentModel> call, Throwable t) {
//                       CommonUtils.dismissProgress();
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Network Issue", Toast.LENGTH_SHORT).show();
        }


    }

    private void WantToCancelAppointment(final int position) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Cancel(list.get(position).getDetails().get(position).getId());
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.cancel();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void Cancel(String id) {
        if (CommonUtils.isNetworkConnected(getActivity())) {

            Api api = ApiClient.getApiClient().create(Api.class);
            api.Cancel(id).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (response.isSuccessful()) {
//                        list.clear();
                        SetUpRecyclerView();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {

                }
            });

        } else {
            Toast.makeText(getActivity(), "network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        // if the fragment is visible
        if (visible) {
            // ... but the activity has not yet been initialized
            if (!doInOnAttach) {
                myAction(getActivity());
            }

        } else {

            // myAction(getActivity());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (doInOnAttach) {
            myAction(context);
            doInOnAttach = false;
        }
    }

    private void myAction(Context context) {

//        Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
        SetUpRecyclerView();
        // code to execute here
    }
}
