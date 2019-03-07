package com.omninos.freshup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.omninos.freshup.ModelClasses.JoinQueueModel;
import com.omninos.freshup.R;

import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.MyViewHolder> {

    Context context;
    List<JoinQueueModel> list;
    JoinQueue joinQueue;


    public interface JoinQueue{
        public void join(int position);
    }

    public QueueAdapter(Context context, List<JoinQueueModel> list, JoinQueue joinQueue) {
        this.context = context;
        this.list = list;
        this.joinQueue = joinQueue;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custome_queue_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.barbarName.setText(list.get(i).getName());
        myViewHolder.inqueueNo.setText(list.get(i).getInQueue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView barbarName,inqueueNo;
        private Button joinQueueButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            barbarName=itemView.findViewById(R.id.barbarName);
            inqueueNo=itemView.findViewById(R.id.inqueueNo);
            joinQueueButton=itemView.findViewById(R.id.joinQueue);
            joinQueueButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
         joinQueue.join(getLayoutPosition());
        }
    }
}
