package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.omninos.freshup.Adapters.QueueAdapter;
import com.omninos.freshup.ModelClasses.JoinQueueModel;
import com.omninos.freshup.R;

import java.util.ArrayList;
import java.util.List;

public class JoinQueueActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView barbarsQueue;
    private QueueAdapter adapter;
    private List<JoinQueueModel> list=new ArrayList<>();
    private JoinQueueActivity activity;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue);

        activity=JoinQueueActivity.this;

        initView();
        SetUp();
        RecyclerViewSetUps();
    }

    private void initView() {
        barbarsQueue=findViewById(R.id.barbarsQueue);
        back=findViewById(R.id.back);
    }

    private void SetUp() {
        back.setOnClickListener(this);
    }

    private void RecyclerViewSetUps() {
        barbarsQueue.setLayoutManager(new GridLayoutManager(this, 2));

        JoinQueueModel model=new JoinQueueModel("Hushan","4");
        list.add(model);

        model=new JoinQueueModel("Arfid","2");
        list.add(model);

        model=new JoinQueueModel("Shahid","3");
        list.add(model);

        adapter=new QueueAdapter(this, list, new QueueAdapter.JoinQueue() {
            @Override
            public void join(int position) {
                Intent move=new Intent(activity,SeeYourTurnActivity.class);
                move.putExtra("Name",list.get(position).getName());
                move.putExtra("No",list.get(position).getInQueue());
                startActivity(move);
            }
        });

        adapter.notifyDataSetChanged();
        barbarsQueue.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
