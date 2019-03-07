package com.omninos.freshup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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

public class SubServicesAdapter extends RecyclerView.Adapter<SubServicesAdapter.MyViewHolder> {

    Context context;
    List<GetServicesDataModel> list;
    MoveNext moveNext;
    List<String> listdata = new ArrayList<>();

    private SubSubServiceAdapter adapter;

    public interface MoveNext {
        public void NextClick(int position);
    }

    public SubServicesAdapter(Context context, List<GetServicesDataModel> list, MoveNext moveNext) {
        this.context = context;
        this.list = list;
        this.moveNext = moveNext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_subservice_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.textSubService.setText(list.get(i).getDetails().get(i).getTitle());
        myViewHolder.subsubItems.setHasFixedSize(true);

        //toolbar.setTitle("Shop");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myViewHolder.subsubItems.setLayoutManager(linearLayoutManager);

        adapter = new SubSubServiceAdapter(context, list.get(i).getDetails().get(i).getSubSubServices(), new SubSubServiceAdapter.ChooseSubsubCategory() {
            @Override
            public void Choose(int position) {

            }
        });

        myViewHolder.subsubItems.setAdapter(adapter);

        myViewHolder.textSubService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!listdata.contains(list.get(i).getDetails().get(i).getId())) {
                    if (listdata.size() < 1) {
                        listdata.add(list.get(i).getDetails().get(i).getId());
                        myViewHolder.subsubItems.setVisibility(View.VISIBLE);
                        myViewHolder.beardArrowDown.setVisibility(View.VISIBLE);
                        myViewHolder.beardArrow.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(context, "close previously once", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    listdata.remove(list.get(i).getDetails().get(i).getId());
                    myViewHolder.subsubItems.setVisibility(View.GONE);
                    myViewHolder.beardArrowDown.setVisibility(View.GONE);
                    myViewHolder.beardArrow.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textSubService;
        RecyclerView subsubItems;
        ImageView beardArrowDown, beardArrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubService = itemView.findViewById(R.id.textSubService);
            subsubItems = itemView.findViewById(R.id.subsubItems);
            beardArrow = itemView.findViewById(R.id.beardArrow);
            beardArrowDown = itemView.findViewById(R.id.beardArrowDown);
        }
    }
}
