package com.omninos.freshup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.freshup.ModelClasses.GetServicesDataModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;

import java.util.ArrayList;
import java.util.List;

public class SubSubServiceAdapter extends RecyclerView.Adapter<SubSubServiceAdapter.MyViewHolder> {

    Activity context;
    List<GetServicesDataModel.SubSubService> list;
    ChooseSubsubCategory chooseSubsubCategory;
    List<String> listdata = new ArrayList<>();
    List<String> serviceName=new ArrayList<>();
    List<Double> price=new ArrayList<>();

    int check = 0;

    public interface ChooseSubsubCategory {
        public void Choose(int position);
    }

    public SubSubServiceAdapter(Activity context, List<GetServicesDataModel.SubSubService> list, ChooseSubsubCategory chooseSubsubCategory) {
        this.context = context;
        this.list = list;
        this.chooseSubsubCategory = chooseSubsubCategory;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_sub_sub_services, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.subsubItemPrice.setText("€" + list.get(i).getPrice());
        if (App.getAppPreferences().getLanguage(context).equalsIgnoreCase("fr")){
            myViewHolder.subsubitemName.setText(list.get(i).getFrenchTitle());
        }else {
            myViewHolder.subsubitemName.setText(list.get(i).getTitle());
        }
        myViewHolder.subsubitemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!listdata.contains(list.get(i).getId())) {
                        if (listdata.size()<2) {
                            listdata.add(list.get(i).getId());
                            serviceName.add(list.get(i).getTitle());
                            price.add(Double.valueOf(list.get(i).getPrice()));
                            myViewHolder.slectsubsubitem.setImageResource(R.drawable.ic_radio_on_button);
                        }else {
                            Toast.makeText(context, "You can choose only two services", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        listdata.remove(list.get(i).getId());
                        serviceName.remove(list.get(i).getTitle());
                        price.remove(Double.valueOf(list.get(i).getPrice()));
                        myViewHolder.slectsubsubitem.setImageResource(R.drawable.ic_circle);
                    }
                    Toast.makeText(context, list.get(i).getTitle(), Toast.LENGTH_SHORT).show();

                    App.getAppPreferences().setItemsdataSubServices(listdata);
                    App.getAppPreferences().setServicesName(serviceName);
                    App.getAppPreferences().setPriceList(price);
                    App.getAppPreferences().setSubCategorySize(listdata.size());
                    System.out.println("MyData: " + App.getAppPreferences().getItemsdataSubServices());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView subsubitemName, subsubItemPrice;
        private CardView subsubitemCard;
        private ImageView slectsubsubitem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subsubitemName = itemView.findViewById(R.id.subsubitemName);
            subsubItemPrice = itemView.findViewById(R.id.subsubItemPrice);
            subsubitemCard = itemView.findViewById(R.id.subsubitemCard);
            slectsubsubitem = itemView.findViewById(R.id.slectsubsubitem);
        }

    }
}
