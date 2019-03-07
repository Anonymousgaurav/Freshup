package com.omninos.freshup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omninos.freshup.ModelClasses.OrderHistoryModel;
import com.omninos.freshup.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    Context context;
    List<OrderHistoryModel> list;

    public OrderHistoryAdapter(Context context, List<OrderHistoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_order_history_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.dateData.setText("Date: "+list.get(i).getDetails().get(i).getCreated().substring(0,10));
        myViewHolder.orderId.setText("Order Id: "+list.get(i).getDetails().get(i).getOrderId());
        myViewHolder.productPrice.setText("€"+list.get(i).getDetails().get(i).getPrice());
        myViewHolder.quantity.setText(list.get(i).getDetails().get(i).getQuantity());
        myViewHolder.title.setText(list.get(i).getDetails().get(i).getTitle());
        Glide.with(context).load(list.get(i).getDetails().get(i).getProductImage()).into(myViewHolder.productImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dateData,orderId,title,quantity,productPrice;
        private ImageView productImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.productImage);
            dateData=itemView.findViewById(R.id.dateData);
            orderId=itemView.findViewById(R.id.orderId);
            title=itemView.findViewById(R.id.title);
            quantity=itemView.findViewById(R.id.quantity);
            productPrice=itemView.findViewById(R.id.productPrice);
        }

    }
}
