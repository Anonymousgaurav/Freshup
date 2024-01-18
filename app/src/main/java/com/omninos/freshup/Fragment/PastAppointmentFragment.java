package com.omninos.freshup.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class PastAppointmentFragment extends Fragment {

    private RecyclerView appointmentList;
    private AppointmentAdapter adapter;

    Context activity;
    String currentDate, currentTime;
    DateFormat dateFormat, timeFormat;
    List<AppointmentModel> list = new ArrayList<>();

    private TextView noAppointment;
    List<AppointmentModel.Detail> detailList = new ArrayList<>();

    Context context;
    Resources resources;

    private boolean doInOnAttach = true;

    public PastAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past_appointment, container, false);

        activity = getActivity();
        context = LocaleHelper.setLocale(getActivity(), App.getAppPreferences().getLanguage(getActivity()));
        resources = context.getResources();

        initView(view);
        WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        appointmentList.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void initView(View view) {

        appointmentList = view.findViewById(R.id.appointmentlist);

        noAppointment = view.findViewById(R.id.noAppointment);

        noAppointment.setText(resources.getString(R.string.no_past_appointment));

    }

    private void SetUpRecyclerView() {


        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeFormat = new SimpleDateFormat("HH:mm");
        final Date date = new Date();
        currentDate = dateFormat.format(date);
        currentTime = timeFormat.format(date);


        if (list != null) {
            list.clear();
        }
        if (detailList != null) {
            detailList.clear();
        }
        if (CommonUtils.isNetworkConnected(getActivity())) {

            Api api = ApiClient.getApiClient().create(Api.class);

//              CommonUtils.showProgress(getActivity(),"");

            api.getAppointment(App.getAppPreferences().getUserId1(getActivity()), currentTime).enqueue(new Callback<AppointmentModel>() {
                @Override
                public void onResponse(Call<AppointmentModel> call, Response<AppointmentModel> response) {
//                      CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {

                            int size = response.body().getDetails().size();
//                            for (int i = 0; i < size; i++) {
//                                AppointmentModel model = new AppointmentModel();
//
//                                AppointmentModel.Detail detail = new AppointmentModel.Detail();
//                                try {
//                                    Date appointMentDate = dateFormat.parse(response.body().getDetails().get(i).getApointmentDate());
//                                    Date Current = dateFormat.parse(currentDate);
//                                    if (Current.getTime() >= appointMentDate.getTime()) {
//                                        if (Current.getTime() == appointMentDate.getTime()) {
//                                            List<String> list1 = new ArrayList<String>(Arrays.asList(response.body().getDetails().get(i).getTimeslot().split(",")));
//                                            Date appointMentTime = timeFormat.parse(list1.get(0));
//                                            Date timeData = timeFormat.parse(currentTime);
//                                            if (timeData.getTime() > appointMentTime.getTime()) {
//                                                Log.d("TimeData : ", "IF");
////                                                myViewHolder.cancel.setVisibility(View.GONE);
//                                                detail.setId(response.body().getDetails().get(i).getId());
//                                                detail.setUserStatus(response.body().getDetails().get(i).getUserStatus());
//                                                detail.setApointmentDate(response.body().getDetails().get(i).getApointmentDate());
//                                                detail.setUserId(response.body().getDetails().get(i).getUserId());
//                                                detail.setSubSubServiceTitle(response.body().getDetails().get(i).getSubSubServiceTitle());
//                                                detail.setTimeslot(response.body().getDetails().get(i).getTimeslot());
//                                                detail.setBarberName(response.body().getDetails().get(i).getBarberName());
//                                                detailList.add(detail);
//                                                model.setDetails(detailList);
//                                                list.add(model);
//                                            } else {
//                                                Log.d("TimeData : ", "Else");
////                                                myViewHolder.cancel.setVisibility(View.VISIBLE);
//
//                                            }
//                                        } else {
//                                            detail.setId(response.body().getDetails().get(i).getId());
//                                            detail.setApointmentDate(response.body().getDetails().get(i).getApointmentDate());
//                                            detail.setUserId(response.body().getDetails().get(i).getUserId());
//                                            detail.setUserStatus(response.body().getDetails().get(i).getUserStatus());
//                                            detail.setSubSubServiceTitle(response.body().getDetails().get(i).getSubSubServiceTitle());
//                                            detail.setTimeslot(response.body().getDetails().get(i).getTimeslot());
//                                            detail.setBarberName(response.body().getDetails().get(i).getBarberName());
//                                            detailList.add(detail);
//                                            model.setDetails(detailList);
//                                            list.add(model);
//                                        }
//
//                                    } else {
//                                        if (response.body().getDetails().get(i).getUserStatus().equalsIgnoreCase("1")) {
//                                            detail.setId(response.body().getDetails().get(i).getId());
//                                            detail.setUserStatus(response.body().getDetails().get(i).getUserStatus());
//                                            detail.setApointmentDate(response.body().getDetails().get(i).getApointmentDate());
//                                            detail.setUserId(response.body().getDetails().get(i).getUserId());
//                                            detail.setSubSubServiceTitle(response.body().getDetails().get(i).getSubSubServiceTitle());
//                                            detail.setTimeslot(response.body().getDetails().get(i).getTimeslot());
//                                            detail.setBarberName(response.body().getDetails().get(i).getBarberName());
//                                            detailList.add(detail);
//                                            model.setDetails(detailList);
//                                            list.add(model);
//                                        }
//                                    }
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }

//                            }

                            for (int i = 0; i < size; i++) {
                                AppointmentModel model = new AppointmentModel();
                                AppointmentModel.Detail detail = new AppointmentModel.Detail();
                                if (response.body().getDetails().get(i).getUpcomingPastApointment() == 0) {

                                    noAppointment.setVisibility(View.GONE);
                                    appointmentList.setVisibility(View.VISIBLE);

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
                                } else {

//                                    noAppointment.setVisibility(View.VISIBLE);
//                                    appointmentList.setVisibility(View.GONE);
                                }
                            }
                            adapter = new AppointmentAdapter(getActivity(), "Past", list, new AppointmentAdapter.CancelAppointment() {
                                @Override
                                public void Cancel(int position) {
//                                    WantToCancelAppointment(position);
                                }
                            });
                            appointmentList.setAdapter(adapter);

                            adapter.notifyDataSetChanged();
//                            Toast.makeText(getActivity(), "Past", Toast.LENGTH_SHORT).show();

                        } else {

                            noAppointment.setVisibility(View.VISIBLE);
                            appointmentList.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppointmentModel> call, Throwable t) {
//                    CommonUtils.dismissProgress();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

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

//        Toast.makeText(context, "Past", Toast.LENGTH_SHORT).show();
        SetUpRecyclerView();
        // code to execute here
    }
}
