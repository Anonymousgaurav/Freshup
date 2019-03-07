package com.omninos.freshup.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.ModelClasses.BarbarDetailsModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder> {
    Context context;
    List<String> list;
    ChooseTime chooseTime;
    List<String> setUpTimenew = new ArrayList<>();
    List<BarbarDetailsModel.BookingTime> bookingTimeList;
    List<String> getTimeBooking = new ArrayList<>();
    StringBuilder stringBuilder;

    public interface ChooseTime {
        public void selectTime(int position);
    }

    public TimeAdapter(Context context, List<BarbarDetailsModel.BookingTime> bookingTimeList, List<String> list, ChooseTime chooseTime) {
        this.context = context;
        this.bookingTimeList = bookingTimeList;
        this.list = list;
        this.chooseTime = chooseTime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_timepick_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.timesData.setText(list.get(i));


        for (int j = 0; j < bookingTimeList.size(); j++) {

            List<String> elephantList = Arrays.asList(bookingTimeList.get(j).getTimeslot().split(","));
            for (String e : elephantList) {
                System.out.println("Times: " + e);
                if (list.get(i).equalsIgnoreCase(e)) {
                    myViewHolder.layoutSelect.setBackgroundColor(Color.parseColor("#ed0d0d"));
                    myViewHolder.timesData.setClickable(false);
                } else {

                }
            }


        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView timesData;
        RelativeLayout layoutSelect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timesData = itemView.findViewById(R.id.timesData);
            layoutSelect = itemView.findViewById(R.id.layoutSelect);
            timesData.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.timesData:
//                    if (!setUpTimenew.contains(list.get(getLayoutPosition()))) {
//                        if (App.getAppPreferences().getTimeSlote() != null) {
//                            if (App.getAppPreferences().getTimeSlote().size() < 2) {
//                                setUpTimenew.add(list.get(getLayoutPosition()));
//                                layoutSelect.setBackgroundColor(Color.parseColor("#47ec10"));
//                            } else {
////                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } else {
//                        setUpTimenew.remove(list.get(getLayoutPosition()));
//                        layoutSelect.setBackgroundColor(Color.parseColor("#f2efef"));
//                    }
//                    chooseTime.selectTime(getLayoutPosition());
                    if (!setUpTimenew.contains(list.get(getLayoutPosition()))) {
                        if (setUpTimenew.size()<2) {
                            setUpTimenew.add(list.get(getLayoutPosition()));
                            layoutSelect.setBackgroundColor(Color.parseColor("#47ec10"));
                        }else {
                            Toast.makeText(context, "You can choose maximum 2 slot", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        setUpTimenew.remove(list.get(getLayoutPosition()));
                        layoutSelect.setBackgroundColor(Color.parseColor("#f2efef"));
                    }
                    App.getAppPreferences().setTimeSlote(setUpTimenew);
                    chooseTime.selectTime(getLayoutPosition());
                    break;
            }
        }
    }
}
