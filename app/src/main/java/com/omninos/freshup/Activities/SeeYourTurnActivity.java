package com.omninos.freshup.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.omninos.freshup.Adapters.SingleUserQueueAdapter;
import com.omninos.freshup.ModelClasses.QueueModelClass;
import com.omninos.freshup.ModelClasses.SingleUserQueueList;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SeeYourTurnActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private TextView barbarName,dateType;
    RecyclerView queueList;
    private SeeYourTurnActivity activity;
    private List<SingleUserQueueList> lists=new ArrayList<>();
    private SingleUserQueueAdapter adapter;
    String noofQueue;
    private List<QueueModelClass.BookingDetail> details=new ArrayList<>();

    Calendar cal;
    SimpleDateFormat sdf, sdf1;
    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_your_turn);

        activity=SeeYourTurnActivity.this;


        cal = Calendar.getInstance();
        sdf1 = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        intView();
        SetUps();
        details=App.getAppPreferences().getBookingDetailList();

        SetUpRecyclerView();
    }

    private void SetUpRecyclerView() {
        queueList.setHasFixedSize(true);

        //toolbar=getSupportActionBar();

        //toolbar.setTitle("Shop");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        queueList.setLayoutManager(linearLayoutManager);


//        SingleUserQueueList model=new SingleUserQueueList("JOHN","Bear(Cash)");
//        lists.add(model);
//
//        model=new SingleUserQueueList("JENNI","Hair Cut(PayPal)");
//        lists.add(model);
//
//        model=new SingleUserQueueList("YOU","Hair and Bear(Cash)");
//        lists.add(model);
//
//        model=new SingleUserQueueList("JAMES","Hair Cut(PayPal)");
//        lists.add(model);
//
//        adapter=new SingleUserQueueAdapter(activity,lists,noofQueue);
//
//        queueList.setAdapter(adapter);

        adapter=new SingleUserQueueAdapter(activity,details,String.valueOf(details.size()));
        queueList.setAdapter(adapter);

    }

    private void intView() {
        back=findViewById(R.id.back);
        barbarName=findViewById(R.id.barbarName);
        queueList=findViewById(R.id.queueList);
        dateType=findViewById(R.id.dateType);
    }

    private void SetUps() {
        back.setOnClickListener(this);
        barbarName.setText(getIntent().getStringExtra("name"));
        noofQueue=getIntent().getStringExtra("No");

        final Date date = new Date();
        dateType.setText(sdf1.format(date));
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
