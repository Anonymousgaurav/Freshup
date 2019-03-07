
package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.omninos.freshup.R;

public class MyServiceFirstActivity extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout hair, beard, Facial, hairColor, innerberad, innerFacial, innerhairColor, innerHair;
    private ImageView back, beardArrow, beardArrowDown, facialArrow, facialArrowDown, hairColorArrow, hairColorArrowDown, hairArrow, HairArrowDown;
    private int a = 0, b = 0, c = 0, d = 0;

    private TextView textbeard, textfacial, texthaircolor, texthair;
    private Button continuemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_first);

        initView();
        SetUps();
    }

    private void initView() {
        hair = findViewById(R.id.hair);
        hairColor = findViewById(R.id.hairColor);
        Facial = findViewById(R.id.Facial);
        beard = findViewById(R.id.beard);
        innerberad = findViewById(R.id.innerberad);
        innerFacial = findViewById(R.id.innerFacial);
        innerhairColor = findViewById(R.id.innerhairColor);
        innerHair = findViewById(R.id.innerHair);

        beardArrow = findViewById(R.id.beardArrow);
        beardArrowDown = findViewById(R.id.beardArrowDown);
        facialArrow = findViewById(R.id.facialArrow);
        facialArrowDown = findViewById(R.id.facialArrowDown);
        hairArrow = findViewById(R.id.hairArrow);
        HairArrowDown = findViewById(R.id.HairArrowDown);
        hairColorArrow = findViewById(R.id.hairColorArrow);
        hairColorArrowDown = findViewById(R.id.hairColorArrowDown);


        textbeard = findViewById(R.id.textbeard);
        textfacial = findViewById(R.id.textfacial);
        texthaircolor = findViewById(R.id.texthaircolor);
        texthair = findViewById(R.id.texthair);

        back = findViewById(R.id.back);

        continuemove=findViewById(R.id.continuemove);


    }

    private void SetUps() {
        texthair.setOnClickListener(this);
        texthaircolor.setOnClickListener(this);
        textfacial.setOnClickListener(this);
        textbeard.setOnClickListener(this);
        back.setOnClickListener(this);
        continuemove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.continuemove:
                startActivity(new Intent(this,MyServiceSecond.class));
                break;
            case R.id.back:
                onBackPressed();
                break;

            case R.id.textbeard:

                if (a == 0) {
                    beardArrow.setVisibility(View.GONE);
                    beardArrowDown.setVisibility(View.VISIBLE);

                    facialArrow.setVisibility(View.VISIBLE);
                    facialArrowDown.setVisibility(View.GONE);

                    hairColorArrow.setVisibility(View.VISIBLE);
                    hairColorArrowDown.setVisibility(View.GONE);

                    hairArrow.setVisibility(View.VISIBLE);
                    HairArrowDown.setVisibility(View.GONE);

                    innerberad.setVisibility(View.VISIBLE);
                    innerFacial.setVisibility(View.GONE);
                    innerHair.setVisibility(View.GONE);
                    innerhairColor.setVisibility(View.GONE);
                    a = 1;
                } else {
                    beardArrow.setVisibility(View.VISIBLE);
                    beardArrowDown.setVisibility(View.GONE);

                    facialArrow.setVisibility(View.VISIBLE);
                    facialArrowDown.setVisibility(View.GONE);

                    hairColorArrow.setVisibility(View.VISIBLE);
                    hairColorArrowDown.setVisibility(View.GONE);

                    hairArrow.setVisibility(View.VISIBLE);
                    HairArrowDown.setVisibility(View.GONE);

                    innerberad.setVisibility(View.GONE);
                    innerFacial.setVisibility(View.GONE);
                    innerHair.setVisibility(View.GONE);
                    innerhairColor.setVisibility(View.GONE);
                    a = 0;
                }
                b = 0;
                c = 0;
                d = 0;
                break;

            case R.id.textfacial:

                if (b == 0) {
                    beardArrow.setVisibility(View.VISIBLE);
                    beardArrowDown.setVisibility(View.GONE);

                    facialArrow.setVisibility(View.GONE);
                    facialArrowDown.setVisibility(View.VISIBLE);

                    hairColorArrow.setVisibility(View.VISIBLE);
                    hairColorArrowDown.setVisibility(View.GONE);

                    hairArrow.setVisibility(View.VISIBLE);
                    HairArrowDown.setVisibility(View.GONE);

                    innerberad.setVisibility(View.GONE);
                    innerFacial.setVisibility(View.VISIBLE);
                    innerHair.setVisibility(View.GONE);
                    innerhairColor.setVisibility(View.GONE);
                    b = 1;
                } else {
                    beardArrow.setVisibility(View.VISIBLE);
                    beardArrowDown.setVisibility(View.GONE);

                    facialArrow.setVisibility(View.VISIBLE);
                    facialArrowDown.setVisibility(View.GONE);

                    hairColorArrow.setVisibility(View.VISIBLE);
                    hairColorArrowDown.setVisibility(View.GONE);

                    hairArrow.setVisibility(View.VISIBLE);
                    HairArrowDown.setVisibility(View.GONE);

                    innerberad.setVisibility(View.GONE);
                    innerFacial.setVisibility(View.GONE);
                    innerHair.setVisibility(View.GONE);
                    innerhairColor.setVisibility(View.GONE);
                    b = 0;
                }
                a = 0;
                c = 0;
                d = 0;


                break;

            case R.id.texthaircolor:

                if (c == 0) {
                    beardArrow.setVisibility(View.VISIBLE);
                    beardArrowDown.setVisibility(View.GONE);

                    facialArrow.setVisibility(View.VISIBLE);
                    facialArrowDown.setVisibility(View.GONE);

                    hairColorArrow.setVisibility(View.GONE);
                    hairColorArrowDown.setVisibility(View.VISIBLE);

                    hairArrow.setVisibility(View.VISIBLE);
                    HairArrowDown.setVisibility(View.GONE);

                    innerberad.setVisibility(View.GONE);
                    innerFacial.setVisibility(View.GONE);
                    innerHair.setVisibility(View.GONE);
                    innerhairColor.setVisibility(View.VISIBLE);
                    c = 1;
                } else {
                    beardArrow.setVisibility(View.VISIBLE);
                    beardArrowDown.setVisibility(View.GONE);

                    facialArrow.setVisibility(View.VISIBLE);
                    facialArrowDown.setVisibility(View.GONE);

                    hairColorArrow.setVisibility(View.VISIBLE);
                    hairColorArrowDown.setVisibility(View.GONE);

                    hairArrow.setVisibility(View.VISIBLE);
                    HairArrowDown.setVisibility(View.GONE);

                    innerberad.setVisibility(View.GONE);
                    innerFacial.setVisibility(View.GONE);
                    innerHair.setVisibility(View.GONE);
                    innerhairColor.setVisibility(View.GONE);
                    c = 0;
                }
                a = 0;
                b = 0;
                d = 0;
                break;

            case R.id.texthair:


                if (d == 0) {
                    beardArrow.setVisibility(View.VISIBLE);
                    beardArrowDown.setVisibility(View.GONE);

                    facialArrow.setVisibility(View.VISIBLE);
                    facialArrowDown.setVisibility(View.GONE);

                    hairColorArrow.setVisibility(View.VISIBLE);
                    hairColorArrowDown.setVisibility(View.GONE);

                    hairArrow.setVisibility(View.GONE);
                    HairArrowDown.setVisibility(View.VISIBLE);

                    innerberad.setVisibility(View.GONE);
                    innerFacial.setVisibility(View.GONE);
                    innerHair.setVisibility(View.VISIBLE);
                    innerhairColor.setVisibility(View.GONE);
                    d = 1;
                } else {
                    beardArrow.setVisibility(View.VISIBLE);
                    beardArrowDown.setVisibility(View.GONE);

                    facialArrow.setVisibility(View.VISIBLE);
                    facialArrowDown.setVisibility(View.GONE);

                    hairColorArrow.setVisibility(View.VISIBLE);
                    hairColorArrowDown.setVisibility(View.GONE);

                    hairArrow.setVisibility(View.VISIBLE);
                    HairArrowDown.setVisibility(View.GONE);

                    innerberad.setVisibility(View.GONE);
                    innerFacial.setVisibility(View.GONE);
                    innerHair.setVisibility(View.GONE);
                    innerhairColor.setVisibility(View.GONE);
                    d = 0;
                }

                a = 0;
                b = 0;
                c = 0;
                break;
        }
    }
}
