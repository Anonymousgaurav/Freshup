package com.omninos.freshup.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.omninos.freshup.ModelClasses.AddToCartModel;
import com.omninos.freshup.ModelClasses.GetAddToCartListModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTocartAdapter extends RecyclerView.Adapter<AddTocartAdapter.MyViewHolder> {

    Context context;
    List<GetAddToCartListModel.Detail> listModels;
    RemoveCart removeCart;
    List<AddToCartModel> addToCartModels;
    String Status;

    public interface RemoveCart {
        public void RemoveData(int position);
        public void AddData(int position,int quantity);
        public void Delete(int position);
    }


    public AddTocartAdapter(Context context, List<GetAddToCartListModel.Detail> listModels1,String Status, RemoveCart removeCart) {
//        if (listModels!=null){
//            listModels.clear();
//        }
//        if (addToCartModels!=null){
//            addToCartModels.clear();
//        }
        this.context = context;
        listModels = listModels1;
        this.Status=Status;
        this.removeCart = removeCart;
    }

    public AddTocartAdapter(Context context, String status , List<AddToCartModel> addToCartModels1,RemoveCart removeCart) {
        if (addToCartModels!=null){
            addToCartModels.clear();
        }
        if (listModels!=null){
            listModels.clear();
        }
        this.context = context;
        this.Status = status;
        addToCartModels = addToCartModels1;
        this.removeCart = removeCart;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_add_to_card_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        if (Status.equalsIgnoreCase("Post")){
            myViewHolder.productName.setText(addToCartModels.get(i).getDetails().get(i).getTitle());
            myViewHolder.productPrice.setText("€"+String.valueOf(addToCartModels.get(i).getDetails().get(i).getCartPrice()));
            List<String> items = Arrays.asList(addToCartModels.get(i).getDetails().get(i).getProductImage().split(","));

            Glide.with(context).load(items.get(0)).into(myViewHolder.img);
//            myViewHolder.Bind(addToCartModels.get(i).getDetails().get(i).getId());
            myViewHolder.elegantNumberButton.setNumber(addToCartModels.get(i).getDetails().get(i).getQuantity());

            myViewHolder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    if (oldValue<newValue){
                        removeCart.AddData(i,newValue);
                    }else if (oldValue>newValue){
                        removeCart.AddData(i,newValue);
                    }else if (newValue==0){
                        removeCart.AddData(i,0);
                    }
                }
            });
        }else {
            myViewHolder.productName.setText(listModels.get(i).getTitle());
            myViewHolder.productPrice.setText("€"+String.valueOf(listModels.get(i).getCartPrice()));
            List<String> items = Arrays.asList(listModels.get(i).getProductImage().split(","));

            Glide.with(context).load(items.get(0)).into(myViewHolder.img);
//            Glide.with(context).load(listModels.get(i).getProductImage()).into(myViewHolder.img);
//            myViewHolder.Bind(listModels.get(i).getId());
            myViewHolder.elegantNumberButton.setNumber(listModels.get(i).getQuantity());

            myViewHolder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    if (oldValue<newValue){
                        removeCart.AddData(i,newValue);
                    }else if (oldValue>newValue){
                        removeCart.AddData(i,newValue);
                    }else if (newValue==0){
                        removeCart.AddData(i,0);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (Status.equalsIgnoreCase("Post")){
            return addToCartModels.size();
        }else {
            return listModels.size();

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img, deleteItem;
        TextView productName, productPrice;
        ElegantNumberButton elegantNumberButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            elegantNumberButton=itemView.findViewById(R.id.IncrementAndDecrement);
            deleteItem.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteItem:
//                    removeCart.RemoveData(getAdapterPosition());
                    removeCart.Delete(getLayoutPosition());
                    break;
            }
        }

//        public void Bind(final String id) {
//            deleteItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which) {
//                                case DialogInterface.BUTTON_POSITIVE:
//                                    //Yes button clicked  l
//                                    listModels.remove(getAdapterPosition());
//                                    notifyItemRemoved(getAdapterPosition());
//                                    Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
//                                    DeleteData(id);
//                                    dialog.dismiss();
//                                    break;
//
//                                case DialogInterface.BUTTON_NEGATIVE:
//                                    //No button clicked
//                                    dialog.cancel();
//                                    break;
//                            }
//                        }
//                    };
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
//                            .setNegativeButton("No", dialogClickListener).show();
//
//
//                }
//            });
//        }

//        private void DeleteData(String id) {
//            if (CommonUtils.isNetworkConnected(context)) {
//
//
//                Api api = ApiClient.getApiClient().create(Api.class);
//                api.DeleteItems(id).enqueue(new Callback<Map>() {
//                    @Override
//                    public void onResponse(Call<Map> call, Response<Map> response) {
////                        CommonUtils.dismissProgress();
//                        if (response.body() != null) {
//
//                        } else {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Map> call, Throwable t) {
////                        CommonUtils.dismissProgress();
//                    }
//                });
//
//            } else {
//                Toast.makeText(context, "Network Issue", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
