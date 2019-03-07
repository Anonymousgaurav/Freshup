package com.omninos.freshup.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.ModelClasses.BarbarDetailsModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class BarbarDetailAdapter extends RecyclerView.Adapter<BarbarDetailAdapter.MyViewHolder> {

    Context context;
    List<BarbarDetailsModel> list;
    List<String> timeSlots = new ArrayList<>();
    List<String> setUpTimenew = new ArrayList<>();
    List<String> barbarVisibiliry = new ArrayList<>();

    Selectbarbar selectbarbar;
    int select = 0;

    public interface Selectbarbar {
        public void ChooseBarbar(int position);
    }

    public BarbarDetailAdapter(Context context, List<BarbarDetailsModel> list1, Selectbarbar selectbarbar) {

        if (list != null) {
            list.clear();
        }
        this.context = context;
        list = list1;
        this.selectbarbar = selectbarbar;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_barbar_details_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.ServiceProviderName.setText(list.get(i).getDetails().getBarberDeatils().get(i).getName());

        if (timeSlots != null) {
            timeSlots.clear();
        }
        displayTimeSlots(list.get(i).getDetails().getTimeSlotDetails().getStartTime(), list.get(i).getDetails().getTimeSlotDetails().getEndTime(), list.get(i).getDetails().getTimeSlotDetails().getSlotTime());

        myViewHolder.firstRecycler.setLayoutManager(new GridLayoutManager(context, 5));

        TimeAdapter adapter = new TimeAdapter(context, list.get(i).getDetails().getBarberDeatils().get(i).getBookingTime(), timeSlots, new TimeAdapter.ChooseTime() {
            @Override
            public void selectTime(int position) {

                App.getAppPreferences().setBarbarId(list.get(i).getDetails().getBarberDeatils().get(i).getId());
                App.getAppPreferences().setBarbarName(list.get(i).getDetails().getBarberDeatils().get(i).getName());

//                if (!setUpTimenew.contains(timeSlots.get(position))) {
//                    if (setUpTimenew.size()<2) {
//                        setUpTimenew.add(timeSlots.get(position));
//                    }else {
//                        Toast.makeText(context, "you choose maximum 2 slot", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    setUpTimenew.remove(timeSlots.get(position));
//                }
//                App.getAppPreferences().setTimeSlote(setUpTimenew);

//                Toast.makeText(context, timeSlots.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.firstRecycler.setAdapter(adapter);

    }

    private int getHoursValue(int hours) {
        return hours - 12;
    }

    private void displayTimeSlots(String startTime, String lastTime, String timeSlot) {

        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateToStr = format1.format(today);
        System.out.println("Today Date " + dateToStr);

        String timeValue = "2015-10-28T18:37:04.899+05:30";
        StringTokenizer stringTokenizer = new StringTokenizer(timeValue, "T");
        String dateValue = stringTokenizer.nextElement().toString();
        String endDateValue = "2015-10-28";
        String restString = stringTokenizer.nextElement().toString();
        StringTokenizer secondTokeniser = new StringTokenizer(startTime, ":");

        StringTokenizer slotToken = new StringTokenizer(timeSlot, ":");


        StringTokenizer secondTokeniser1 = new StringTokenizer(lastTime, ":");

        String hours = secondTokeniser.nextElement().toString();
        String minutes = secondTokeniser.nextElement().toString();


        String slothour = slotToken.nextElement().toString();

        String slotmin = slotToken.nextElement().toString();


        String hours1 = secondTokeniser1.nextElement().toString();
        String minutes1 = secondTokeniser1.nextElement().toString();


        hours = String.valueOf(Integer.parseInt(hours));


        hours1 = String.valueOf(Integer.parseInt(hours1));


        if (Integer.parseInt(minutes) > 30) {
            minutes = "00";
        } else {
            minutes = "30";
        }

        if (Integer.parseInt(minutes1) > 30) {
            minutes1 = "00";
        } else {
            minutes1 = "30";
        }

//        String amOrPm;


//        String amOrPm1;

//        if (Integer.parseInt(hours1) < 12) {
//            amOrPm1 = "AM";
//        } else {
//            amOrPm1 = "PM";
//            hours1 = String.valueOf(getHoursValue(Integer.parseInt(hours1)));
//        }
//
//        if (Integer.parseInt(hours) < 12) {
//            amOrPm = "AM";
//        } else {
//            amOrPm = "PM";
//            hours = String.valueOf(getHoursValue(Integer.parseInt(hours)));
//        }

        String time1 = hours + ":" + minutes;
        String time2 = hours1 + ":" + minutes1;
//        String time1 = hours + ":" + minutes + " " + amOrPm;
//        String time2 = hours1 + ":" + minutes1 + " " + amOrPm1;

        String format = "yyyy-MM-dd HH:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        int timeslot = Integer.parseInt(slotmin);

        int value = timeslot * 60 * 1000;
        try {
            Date dateObj1 = sdf.parse(dateValue + " " + time1);
            Date dateObj2 = sdf.parse(endDateValue + " " + time2);
            Log.d("TAG", "Date Start: " + dateObj1);
            Log.d("TAG", "Date End: " + dateObj2);
            long dif = dateObj1.getTime();
            while (dif < dateObj2.getTime()) {
                Date slot1 = new Date(dif);
                dif += value;
//                Date slot2 = new Date(dif);
//                dif += 2400000;
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a, dd/MM/yy");
                Log.d("TAG", "New Hour slot = " + sdf1.format(slot1));

                timeSlots.add(sdf1.format(slot1));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView ServiceProviderName;
        private RecyclerView firstRecycler;
        CardView firstCard1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ServiceProviderName = itemView.findViewById(R.id.ServiceProviderName);
            firstRecycler = itemView.findViewById(R.id.firstRecycler);
            firstCard1 = itemView.findViewById(R.id.firstCard1);
            firstCard1.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.firstCard1:
                    if (!barbarVisibiliry.contains(list.get(getLayoutPosition()).getDetails().getBarberDeatils().get(getLayoutPosition()).getId())) {

                        if (barbarVisibiliry.size() < 1) {
                            firstRecycler.setVisibility(View.VISIBLE);
//                        App.getAppPreferences().setTimeSlote(null);
                            App.getAppPreferences().setTimeSlote(null);
                            barbarVisibiliry.add(list.get(getLayoutPosition()).getDetails().getBarberDeatils().get(getLayoutPosition()).getId());

                        }else {
                            Toast.makeText(context, "close previously once", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        barbarVisibiliry.remove(list.get(getLayoutPosition()).getDetails().getBarberDeatils().get(getLayoutPosition()).getId());
                        firstRecycler.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }
}
