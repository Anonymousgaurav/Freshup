package com.omninos.freshup.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.omninos.freshup.Adapters.SingleUserQueueAdapter;
import com.omninos.freshup.ModelClasses.SingleUserQueueList;
import com.omninos.freshup.R;

import java.util.ArrayList;
import java.util.List;

public class SeeYourTurnActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private TextView barbarName;
    RecyclerView queueList;
    private SeeYourTurnActivity activity;
    private List<SingleUserQueueList> lists=new ArrayList<>();
    private SingleUserQueueAdapter adapter;
    String noofQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_your_turn);

        activity=SeeYourTurnActivity.this;
        intView();
        SetUps();
        SetUpRecyclerView();
    }

    private void SetUpRecyclerView() {
        queueList.setHasFixedSize(true);

        //toolbar=getSupportActionBar();

        //toolbar.setTitle("Shop");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        queueList.setLayoutManager(linearLayoutManager);


        SingleUserQueueList model=new SingleUserQueueList("JOHN","Bear(Cash)");
        lists.add(model);

        model=new SingleUserQueueList("JENNI","Hair Cut(PayPal)");
        lists.add(model);

        model=new SingleUserQueueList("YOU","Hair and Bear(Cash)");
        lists.add(model);

        model=new SingleUserQueueList("JAMES","Hair Cut(PayPal)");
        lists.add(model);

        adapter=new SingleUserQueueAdapter(activity,lists,noofQueue);

        queueList.setAdapter(adapter);

    }

    private void intView() {
        back=findViewById(R.id.back);
        barbarName=findViewById(R.id.barbarName);
        queueList=findViewById(R.id.queueList);
    }

    private void SetUps() {
        back.setOnClickListener(this);
        barbarName.setText(getIntent().getStringExtra("Name"));
        noofQueue=getIntent().getStringExtra("No");
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
