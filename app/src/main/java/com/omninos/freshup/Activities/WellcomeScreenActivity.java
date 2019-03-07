package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omninos.freshup.Fragment.WelcomeFirstFragment;
import com.omninos.freshup.Fragment.WelcomeSecondFragment;
import com.omninos.freshup.Fragment.WelcomeThirdFragment;
import com.omninos.freshup.R;

public class WellcomeScreenActivity extends AppCompatActivity {

    private ViewPager mPager;
    LinearLayout dots;
    private PagerAdapter mPagerAdapter;

    private static final int NUM_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_screen);

        initView();

    }

    private void initView() {
        dots = findViewById(R.id.dots);


        addDotsIndicator(0);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(final int i) {
                addDotsIndicator(i);
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1500);
                            if (i==2){
                                startActivity(new Intent(WellcomeScreenActivity.this,LoginActivity.class));
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                 thread.start();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    private void addDotsIndicator(int position) {

        TextView[] tv_dots = new TextView[3];
        dots.removeAllViews();
        for (int i = 0; i < tv_dots.length; i++) {
            tv_dots[i] = new TextView(getApplicationContext());
            tv_dots[i].setText(Html.fromHtml(" - "));
            tv_dots[i].setTextSize(45);
            tv_dots[i].setTextColor(getResources().getColor(R.color.grey));
            dots.addView(tv_dots[i]);
        }

        tv_dots[position].setTextColor(getResources().getColor(R.color.black));
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    Log.d("getItem: ", String.valueOf(i));
                    fragment = new WelcomeFirstFragment();
                break;
                    case 1:
//
                    Log.d("getItem: ", String.valueOf(i));
                    fragment= new WelcomeSecondFragment();
                    break;

                case 2:
                    Log.d("getItem: ", String.valueOf(i));
                    fragment= new WelcomeThirdFragment();
                    break;
                default:
                    return null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
