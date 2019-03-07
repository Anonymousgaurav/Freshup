package com.omninos.freshup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.ModelClasses.AppointmentModel;
import com.omninos.freshup.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    Context context;
    List<AppointmentModel> All;
    List<AppointmentModel> Upcomming;
    List<AppointmentModel> Past;

    CancelAppointment cancelAppointment;
    List<String> listdata = new ArrayList<>();
    String InterestData;
    String Status;

    String currentDate, currentTime;
    DateFormat dateFormat, timeFormat;

    public interface CancelAppointment {
        public void Cancel(int position);
    }

    public AppointmentAdapter(Context context, List<AppointmentModel> All, String Status, CancelAppointment cancelAppointment) {
        this.context = context;
        this.All = All;
        this.Status = Status;
        this.cancelAppointment = cancelAppointment;
    }


    public AppointmentAdapter(List<AppointmentModel> Upcomming, Context context, String Status, CancelAppointment cancelAppointment) {
        this.Upcomming = Upcomming;
        this.context = context;
        this.Status = Status;
        this.cancelAppointment = cancelAppointment;
    }

    public AppointmentAdapter(Context context, String Status, List<AppointmentModel> Past, CancelAppointment cancelAppointment) {
        this.context = context;
        this.Status = Status;
        this.Past = Past;
        this.cancelAppointment = cancelAppointment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_appointment_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeFormat = new SimpleDateFormat("HH:mm");
        final Date date = new Date();
        currentDate = dateFormat.format(date);
        currentTime = timeFormat.format(date);

        if (Status.equalsIgnoreCase("All")) {
            myViewHolder.barbarName.setText(All.get(i).getDetails().get(i).getBarberName());


            int size = All.get(i).getDetails().get(i).getSubSubServiceTitle().size();
            if (listdata != null) {
                listdata.clear();
            }
            for (int j = 0; j < size; j++) {
                listdata.add(All.get(i).getDetails().get(i).getSubSubServiceTitle().get(j).getTitle());
            }

            StringBuilder stringBuilder;
            try {
                stringBuilder = new StringBuilder();
                for (String s : listdata) {
                    stringBuilder.append(s + ",");
                }
                int size1 = stringBuilder.length();
                stringBuilder.deleteCharAt(size1 - 1);
                System.out.println("Data Is: " + stringBuilder);
                InterestData = stringBuilder.toString();
                System.out.println("Data Sys: " + InterestData);

            } catch (Exception e) {
                stringBuilder = new StringBuilder();
            }
            myViewHolder.servicename.setText(InterestData);
            myViewHolder.timeType.setText(All.get(i).getDetails().get(i).getTimeslot());
            myViewHolder.dateType.setText(All.get(i).getDetails().get(i).getApointmentDate());

            List<String> list = new ArrayList<String>(Arrays.asList(All.get(i).getDetails().get(i).getTimeslot().split(",")));
            for (int j = 0; j < list.size(); j++) {
                try {
                    Date appointMentTime = timeFormat.parse(list.get(j));
                    Date timeData = timeFormat.parse(currentTime);

                    Date appointMentDate = dateFormat.parse(All.get(i).getDetails().get(i).getApointmentDate());
                    Date Current = dateFormat.parse(currentDate);

                    if (Current.getTime() > appointMentDate.getTime()) {
                        myViewHolder.cancel.setVisibility(View.GONE);
                    } else {
                        if (Current.getTime() == appointMentDate.getTime()){
                            if (timeData.getTime() > appointMentTime.getTime()) {
                                Log.d("TimeData : ", "IF");
                                myViewHolder.cancel.setVisibility(View.GONE);
                            } else {
                                Log.d("TimeData : ", "Else");
                                myViewHolder.cancel.setVisibility(View.VISIBLE);
                            }
                        }else {
                            myViewHolder.cancel.setVisibility(View.VISIBLE);
                        }

                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
//            try {
//                Date appointMentDate = dateFormat.parse(All.get(i).getDetails().get(i).getApointmentDate());
//                Date Current = dateFormat.parse(currentDate);
//
//                if (Current.getTime() >appointMentDate.getTime()) {
//                    myViewHolder.cancel.setVisibility(View.GONE);
//                } else {
//                    myViewHolder.cancel.setVisibility(View.VISIBLE);
//                }
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }


        } else if (Status.equalsIgnoreCase("Past")) {
            myViewHolder.barbarName.setText(Past.get(i).getDetails().get(i).getBarberName());


            int size = Past.get(i).getDetails().get(i).getSubSubServiceTitle().size();
            if (listdata != null) {
                listdata.clear();
            }
            for (int j = 0; j < size; j++) {

                listdata.add(Past.get(i).getDetails().get(i).getSubSubServiceTitle().get(j).getTitle());
            }

            StringBuilder stringBuilder;
            try {
                stringBuilder = new StringBuilder();
                for (String s : listdata) {
                    stringBuilder.append(s + ",");
                }
                int size1 = stringBuilder.length();
                stringBuilder.deleteCharAt(size1 - 1);
                System.out.println("Data Is: " + stringBuilder);
                InterestData = stringBuilder.toString();
                System.out.println("Data Sys: " + InterestData);

            } catch (Exception e) {
                stringBuilder = new StringBuilder();
            }
            myViewHolder.servicename.setText(InterestData);
            myViewHolder.timeType.setText(Past.get(i).getDetails().get(i).getTimeslot());
            myViewHolder.dateType.setText(Past.get(i).getDetails().get(i).getApointmentDate());
            myViewHolder.cancel.setVisibility(View.GONE);
        } else {
            myViewHolder.barbarName.setText(Upcomming.get(i).getDetails().get(i).getBarberName());


            int size = Upcomming.get(i).getDetails().get(i).getSubSubServiceTitle().size();
            if (listdata != null) {
                listdata.clear();
            }
            for (int j = 0; j < size; j++) {

                listdata.add(Upcomming.get(i).getDetails().get(i).getSubSubServiceTitle().get(j).getTitle());
            }

            StringBuilder stringBuilder;
            try {
                stringBuilder = new StringBuilder();
                for (String s : listdata) {
                    stringBuilder.append(s + ",");
                }
                int size1 = stringBuilder.length();
                stringBuilder.deleteCharAt(size1 - 1);
                System.out.println("Data Is: " + stringBuilder);
                InterestData = stringBuilder.toString();
                System.out.println("Data Sys: " + InterestData);

            } catch (Exception e) {
                stringBuilder = new StringBuilder();
            }
            myViewHolder.servicename.setText(InterestData);
            myViewHolder.timeType.setText(Upcomming.get(i).getDetails().get(i).getTimeslot());
            myViewHolder.dateType.setText(Upcomming.get(i).getDetails().get(i).getApointmentDate());
        }

    }

    @Override
    public int getItemCount() {
        if (Status.equalsIgnoreCase("All")) {
            return All.size();
        } else if (Status.equalsIgnoreCase("Past")) {
            return Past.size();
        } else {
            return Upcomming.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button cancel;
        private TextView titleName, barbarName, servicename, paymentType, timeType, dateType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cancel = itemView.findViewById(R.id.cancelButton);
            titleName = itemView.findViewById(R.id.titleName);
            barbarName = itemView.findViewById(R.id.barbarName);
            servicename = itemView.findViewById(R.id.servicename);
            paymentType = itemView.findViewById(R.id.paymentType);
            timeType = itemView.findViewById(R.id.timeType);
            dateType = itemView.findViewById(R.id.dateType);
            cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cancelAppointment.Cancel(getLayoutPosition());
        }
    }
}
