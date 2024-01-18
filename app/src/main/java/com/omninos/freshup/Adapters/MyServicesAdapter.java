package com.omninos.freshup.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omninos.freshup.ModelClasses.GetHomeDataModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;

import java.util.List;

public class MyServicesAdapter extends RecyclerView.Adapter<MyServicesAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<GetHomeDataModel> list;
    MyClick myClick;

    public interface MyClick {
        public void MyClicked(int position);
    }

    public MyServicesAdapter(Activity activity, List<GetHomeDataModel> list, MyClick myClick) {
        this.activity = activity;
        this.list = list;
        this.myClick = myClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_service_items_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (App.getAppPreferences().getLanguage(activity).equalsIgnoreCase("fr")) {
            myViewHolder.serviceName.setText(list.get(i).getDetails().get(i).getFrenchTitle());
        } else {
            myViewHolder.serviceName.setText(list.get(i).getDetails().get(i).getTitle());
        }
        Glide.with(activity).load(list.get(i).getDetails().get(i).getImage1()).into(myViewHolder.centerImg);
        Glide.with(activity).load(list.get(i).getDetails().get(i).getImage2()).into(myViewHolder.Imgback);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView serviceName;
        private ImageView centerImg, Imgback;

        public MyViewHolder(View itemView) {
            super(itemView);

            serviceName = itemView.findViewById(R.id.serviceName);
            centerImg = itemView.findViewById(R.id.centerImg);
            Imgback = itemView.findViewById(R.id.Imgback);
            centerImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClick.MyClicked(getLayoutPosition());
        }
    }
}
