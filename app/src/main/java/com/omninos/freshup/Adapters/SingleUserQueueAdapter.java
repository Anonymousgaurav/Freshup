package com.omninos.freshup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omninos.freshup.ModelClasses.QueueModelClass;
import com.omninos.freshup.ModelClasses.SingleUserQueueList;
import com.omninos.freshup.R;

import java.util.List;

public class SingleUserQueueAdapter extends RecyclerView.Adapter<SingleUserQueueAdapter.MyViewHolder> {

    Context context;
    List<QueueModelClass.BookingDetail> lists;
    String size;


    public SingleUserQueueAdapter(Context context, List<QueueModelClass.BookingDetail> lists, String size) {
        this.context = context;
        this.lists = lists;
        this.size=size;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_single_user,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.sNo.setText(String.valueOf(i+1));
        myViewHolder.firstUseName.setText(lists.get(i).getUserName());
        myViewHolder.firstUserService.setText(lists.get(i).getServiceTitle());
    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(size);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sNo,firstUseName,firstUserService;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sNo=itemView.findViewById(R.id.sNo);
            firstUseName=itemView.findViewById(R.id.firstUseName);
            firstUserService=itemView.findViewById(R.id.firstUserService);
        }
    }
}
