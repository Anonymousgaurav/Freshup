package com.omninos.freshup.Adapters;

import android.annotation.SuppressLint;
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

import java.util.List;

public class MyServicesAdapter extends RecyclerView.Adapter<MyServicesAdapter.MyViewHolder> {

    Context context;
    List<GetHomeDataModel> list;
    MyClick myClick;

    public interface MyClick{
        public void MyClicked(int position);
    }

    public MyServicesAdapter(Context context, List<GetHomeDataModel> list, MyClick myClick) {
        this.context = context;
        this.list = list;
        this.myClick = myClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_service_items_layout,viewGroup,false);
      return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.serviceName.setText(list.get(i).getDetails().get(i).getTitle());
        Glide.with(context).load(list.get(i).getDetails().get(i).getImage1()).into(myViewHolder.centerImg);
        Glide.with(context).load(list.get(i).getDetails().get(i).getImage2()).into(myViewHolder.Imgback);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView serviceName;
        private ImageView centerImg,Imgback;
        public MyViewHolder(View itemView) {
            super(itemView);

            serviceName=itemView.findViewById(R.id.serviceName);
            centerImg=itemView.findViewById(R.id.centerImg);
            Imgback=itemView.findViewById(R.id.Imgback);
            centerImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClick.MyClicked(getLayoutPosition());
        }
    }
}
