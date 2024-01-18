package com.omninos.freshup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omninos.freshup.Activities.LoginActivity;
import com.omninos.freshup.ModelClasses.JoinQueueModel;
import com.omninos.freshup.ModelClasses.QueueModelClass;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.util.LocaleHelper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<QueueModelClass> list;
    JoinQueue joinQueue;
    Resources resources;


    public interface JoinQueue {
        public void join(int position);

        void See(int position);
    }

    public QueueAdapter(Activity context, List<QueueModelClass> list, JoinQueue joinQueue) {
        this.activity = context;
        this.list = list;
        this.joinQueue = joinQueue;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custome_queue_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        context = LocaleHelper.setLocale(activity, App.getAppPreferences().getLanguage(activity));
        resources = context.getResources();

        myViewHolder.inqueue.setText(resources.getString(R.string.in_queue));
        myViewHolder.joinQueueButton.setText(resources.getString(R.string.join_queue));
        myViewHolder.seeQueue.setText(resources.getString(R.string.see_queue));

        myViewHolder.barbarName.setText(list.get(i).getDetails().get(i).getName());
        myViewHolder.inqueueNo.setText(String.valueOf(list.get(i).getDetails().get(i).getBookingDetails().size()));
        Glide.with(context).load(list.get(i).getDetails().get(i).getImage()).into(myViewHolder.barbarImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView barbarName, inqueueNo, inqueue;
        private Button joinQueueButton, seeQueue;
        private CircleImageView barbarImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            barbarName = itemView.findViewById(R.id.barbarName);
            inqueueNo = itemView.findViewById(R.id.inqueueNo);
            joinQueueButton = itemView.findViewById(R.id.joinQueue);
            barbarImage = itemView.findViewById(R.id.barbarImage);
            seeQueue = itemView.findViewById(R.id.seeQueue);
            inqueue = itemView.findViewById(R.id.inqueue);
            joinQueueButton.setOnClickListener(this);
            seeQueue.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.joinQueue:
                    joinQueue.join(getLayoutPosition());
                    break;

                case R.id.seeQueue:
                    joinQueue.See(getLayoutPosition());
                    break;
            }
        }
    }
}
