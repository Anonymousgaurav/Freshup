package com.omninos.freshup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omninos.freshup.ModelClasses.SingleProductCategoryModel;
import com.omninos.freshup.R;

import java.util.List;

public class SingleProductCategoryAdapter extends RecyclerView.Adapter<SingleProductCategoryAdapter.MyViewHolder> {

    Context context;
    List<SingleProductCategoryModel> list;
    MyCategoryClick myCategoryClick;

    public interface MyCategoryClick{
        public void selectcategory(int position);
    }


    public SingleProductCategoryAdapter(Context context, List<SingleProductCategoryModel> list, MyCategoryClick myCategoryClick) {
        this.context = context;
        this.list = list;
        this.myCategoryClick = myCategoryClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_single_product_category_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.productName.setText(list.get(i).getDetails().get(i).getTitle());
        Glide.with(context).load(list.get(i).getDetails().get(i).getImage()).into(myViewHolder.productImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView productImage;
        private TextView productName;
        private CardView productCategoryCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productName);
            productCategoryCard=itemView.findViewById(R.id.productCategoryCard);
            productCategoryCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.productCategoryCard:
                    myCategoryClick.selectcategory(getLayoutPosition());
                    break;
            }
        }
    }
}
