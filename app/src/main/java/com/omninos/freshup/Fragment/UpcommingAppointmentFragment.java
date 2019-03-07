package com.omninos.freshup.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.omninos.freshup.Activities.HomeActivity;
import com.omninos.freshup.Adapters.AppointmentAdapter;
import com.omninos.freshup.ModelClasses.AppointmentModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcommingAppointmentFragment extends Fragment {

    private RecyclerView appointmentList;
    private AppointmentAdapter adapter;

    HomeActivity activity;
    String currentDate, currentTime;
    DateFormat dateFormat, timeFormat;

    List<AppointmentModel> list = new ArrayList<>();
    List<AppointmentModel.Detail> detailList = new ArrayList<>();

    public UpcommingAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcomming_appointment, container, false);

        activity = (HomeActivity) getActivity();
        initView(view);
        SetUpRecyclerView();

        return view;
    }

    private void initView(View view) {

        appointmentList = view.findViewById(R.id.appointmentlist);
    }

    private void SetUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        appointmentList.setLayoutManager(linearLayoutManager);


        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeFormat = new SimpleDateFormat("HH:mm");
        final Date date = new Date();
        currentDate = dateFormat.format(date);
        currentTime = timeFormat.format(date);


        if (list != null) {
            list.clear();
        }
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            //  CommonUtils.showProgress(activity,"");

            api.getAppointment(App.getAppPreferences().getUserId(activity)).enqueue(new Callback<AppointmentModel>() {
                @Override
                public void onResponse(Call<AppointmentModel> call, Response<AppointmentModel> response) {
                    //  CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {

                            int size = response.body().getDetails().size();
                            for (int i = 0; i < size; i++) {
                                AppointmentModel model = new AppointmentModel();

                                AppointmentModel.Detail detail = new AppointmentModel.Detail();


                                try {
                                    Date appointMentDate = dateFormat.parse(response.body().getDetails().get(i).getApointmentDate());
                                    Date Current = dateFormat.parse(currentDate);
                                    if (Current.getTime() > appointMentDate.getTime()) {

                                    } else {
                                        if (Current.getTime() == appointMentDate.getTime()) {
                                            List<String> list1 = new ArrayList<String>(Arrays.asList(response.body().getDetails().get(i).getTimeslot().split(",")));
                                            Date appointMentTime = timeFormat.parse(list1.get(0));
                                            Date timeData = timeFormat.parse(currentTime);

                                            if (timeData.getTime() > appointMentTime.getTime()) {
                                                Log.d("TimeData : ", "IF");
//                                                myViewHolder.cancel.setVisibility(View.GONE);
                                            } else {
                                                Log.d("TimeData : ", "Else");
//                                                myViewHolder.cancel.setVisibility(View.VISIBLE);
                                                detail.setApointmentDate(response.body().getDetails().get(i).getApointmentDate());
                                                detail.setUserId(response.body().getDetails().get(i).getUserId());
                                                detail.setSubSubServiceTitle(response.body().getDetails().get(i).getSubSubServiceTitle());
                                                detail.setTimeslot(response.body().getDetails().get(i).getTimeslot());
                                                detail.setBarberName(response.body().getDetails().get(i).getBarberName());
                                                detailList.add(detail);
                                                model.setDetails(detailList);
                                                list.add(model);
                                            }
                                        } else {
                                            detail.setApointmentDate(response.body().getDetails().get(i).getApointmentDate());
                                            detail.setUserId(response.body().getDetails().get(i).getUserId());
                                            detail.setSubSubServiceTitle(response.body().getDetails().get(i).getSubSubServiceTitle());
                                            detail.setTimeslot(response.body().getDetails().get(i).getTimeslot());
                                            detail.setBarberName(response.body().getDetails().get(i).getBarberName());
                                            detailList.add(detail);
                                            model.setDetails(detailList);
                                            list.add(model);
                                        }
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }

                            adapter = new AppointmentAdapter(list, activity, "Upcoming", new AppointmentAdapter.CancelAppointment() {
                                @Override
                                public void Cancel(int position) {
//                                    WantToCancelAppointment(position);
                                }
                            });
                            appointmentList.setAdapter(adapter);

                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppointmentModel> call, Throwable t) {
                    //CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }


    }

    private void WantToCancelAppointment(final int position) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        appointmentList.setAdapter(adapter);
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.cancel();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

}
