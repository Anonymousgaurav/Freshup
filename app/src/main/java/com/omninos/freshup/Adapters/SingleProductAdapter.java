package com.omninos.freshup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omninos.freshup.ModelClasses.SingleProductCategoryModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.util.LocaleHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleProductAdapter extends RecyclerView.Adapter<SingleProductAdapter.MyViewHolder> {

    Context context;
    List<SingleProductCategoryModel.Product> list;
    PayPayment payPayment;
    Activity activity;
    Resources resources;

    public interface PayPayment {
        public void Payment(int position, String status);

        void OpenDetail(int position);
    }

    public SingleProductAdapter(Activity context, List<SingleProductCategoryModel.Product> list, PayPayment payPayment) {
        this.activity = context;
        this.list = list;
        this.payPayment = payPayment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_product_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        context = LocaleHelper.setLocale(activity, App.getAppPreferences().getLanguage(activity));
        resources = context.getResources();

        myViewHolder.buyButton.setText(resources.getString(R.string.add_to_cart));

        myViewHolder.productName.setText(list.get(i).getTitle());
        myViewHolder.productPrice.setText("€" + list.get(i).getPrice());
        myViewHolder.productServices.setText(list.get(i).getDescription());
        List<String> items = Arrays.asList(list.get(i).getProductImage().split(","));
        Glide.with(context).load(items.get(0)).into(myViewHolder.productImage);
        if (list.get(i).getAddToCartStatus().equalsIgnoreCase("1")) {
            if (App.getAppPreferences().getLanguage(activity).equalsIgnoreCase("fr")) {
                myViewHolder.buyButton.setText("Aller au panier");
            } else {
                myViewHolder.buyButton.setText("Go to Cart");
            }
        }

        myViewHolder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPayment.OpenDetail(i);
            }
        });
        myViewHolder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getAppPreferences().getLanguage(activity).equalsIgnoreCase("fr")) {
                    if (myViewHolder.buyButton.getText().equals("Aller au panier")) {
                        payPayment.Payment(i, "Move");
                    } else {
                        payPayment.Payment(i, "Add");
                    }
                } else {
                    if (myViewHolder.buyButton.getText().equals("Go to Cart")) {
                        payPayment.Payment(i, "Move");
                    } else {
                        payPayment.Payment(i, "Add");
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private Button buyButton;
        private ImageView productImage;
        private TextView productName, productServices, productPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buyButton = itemView.findViewById(R.id.buyButton);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productServices = itemView.findViewById(R.id.productServices);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }


    public void showPopup() {
        final View popupView = LayoutInflater.from(context).inflate(R.layout.recycler_popup_window, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button btn = (Button) popupView.findViewById(R.id.okButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.rv_recycler_view2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        ArrayList<String> data = new ArrayList<>();
        data.add("my data");
        data.add("my test data");
        PopupRecyclerViewAdapter adapter = new PopupRecyclerViewAdapter(context, data);
        recyclerView.setAdapter(adapter);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

    }
}
