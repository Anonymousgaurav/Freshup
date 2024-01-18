package com.omninos.freshup.Activities;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.omninos.freshup.Fragment.AddToCartFragment;
import com.omninos.freshup.Fragment.HomeFragment;
import com.omninos.freshup.Fragment.MainAppointmentFragment;
import com.omninos.freshup.Fragment.OrderHistoryFragment;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.util.LocaleHelper;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationViewEx bnve;
    private Menu menu;
    private HomeActivity activity;


    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activity = HomeActivity.this;


        context = LocaleHelper.setLocale(HomeActivity.this, App.getAppPreferences().getLanguage(HomeActivity.this));
        resources = context.getResources();

        initView();
//        ChageLanguage();
        SetUps();
        SetUpHomeFragment();


        //Set Click on navigation items
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        bnve.setIconTintList(0, null);
                        menu.getItem(0).setIcon(R.drawable.ic_select_scissors);
                        menu.getItem(1).setIcon(R.drawable.ic_shopping_cart);
                        menu.getItem(2).setIcon(R.drawable.ic_order_history);
                        menu.getItem(3).setIcon(R.drawable.ic_appointment);
                        menu.getItem(0).setTitle(resources.getString(R.string.home));

                        menu.getItem(1).setTitle(resources.getString(R.string.cart));

                        menu.getItem(2).setTitle(resources.getString(R.string.orders));

                        menu.getItem(3).setTitle(resources.getString(R.string.appoint));
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.container, new HomeFragment());
                        transaction1.commit();
                        break;

                    case R.id.navigation_promo:
                        bnve.setIconTintList(1, null);
                        menu.getItem(0).setIcon(R.drawable.unselect_scissors);
                        menu.getItem(1).setIcon(R.drawable.select_cart);
                        menu.getItem(2).setIcon(R.drawable.ic_order_history);
                        menu.getItem(3).setIcon(R.drawable.ic_appointment);

                        menu.getItem(0).setTitle(resources.getString(R.string.home));

                        menu.getItem(1).setTitle(resources.getString(R.string.cart));

                        menu.getItem(2).setTitle(resources.getString(R.string.orders));

                        menu.getItem(3).setTitle(resources.getString(R.string.appoint));
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.container, new AddToCartFragment());
                        transaction2.commit();
//                        OpenDialog();
                        break;

                    case R.id.navigation_profile:
                        bnve.setIconTintList(2, null);
                        menu.getItem(0).setIcon(R.drawable.unselect_scissors);
                        menu.getItem(1).setIcon(R.drawable.ic_shopping_cart);
                        menu.getItem(2).setIcon(R.drawable.selected_history);
                        menu.getItem(3).setIcon(R.drawable.ic_appointment);

                        menu.getItem(0).setTitle(resources.getString(R.string.home));

                        menu.getItem(1).setTitle(resources.getString(R.string.cart));

                        menu.getItem(2).setTitle(resources.getString(R.string.orders));

                        menu.getItem(3).setTitle(resources.getString(R.string.appoint));
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.container, new OrderHistoryFragment());
                        transaction3.commit();
                        break;

                    case R.id.navigation_setting:
                        bnve.setIconTintList(3, null);
                        menu.getItem(0).setIcon(R.drawable.unselect_scissors);
                        menu.getItem(1).setIcon(R.drawable.ic_shopping_cart);
                        menu.getItem(2).setIcon(R.drawable.ic_order_history);
                        menu.getItem(3).setIcon(R.drawable.selected_appointment);

                        menu.getItem(0).setTitle(resources.getString(R.string.home));

                        menu.getItem(1).setTitle(resources.getString(R.string.cart));

                        menu.getItem(2).setTitle(resources.getString(R.string.orders));

                        menu.getItem(3).setTitle(resources.getString(R.string.appoint));
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, new MainAppointmentFragment());
                        transaction.commit();
                        break;
                }

                return false;
            }
        });

    }


//    private void OpenDialog() {
//        LayoutInflater layoutInflater = getLayoutInflater();
//        View view = layoutInflater.inflate(R.layout.custom_peomo_layout, null);
//
//        Button verify=view.findViewById(R.id.apply);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(view);
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        verify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.cancel();
//               // startActivity(new Intent(activity,HomeActivity.class));
//             //   finish();
//                bnve.setIconTintList(0, null);
//                menu.getItem(0).setIcon(R.drawable.ic_select_scissors);
//                menu.getItem(1).setIcon(R.drawable.unselect_promo);
//                menu.getItem(2).setIcon(R.drawable.unselected_profile);
//                menu.getItem(3).setIcon(R.drawable.unselected_setting);
//                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//                transaction1.replace(R.id.container, new HomeFragment());
//                transaction1.commit();
//            }
//        });
//        alertDialog.show();
//    }

    //Set First Fragment
    private void SetUpHomeFragment() {

        bnve.setIconTintList(0, null);
        bnve.setIconTintList(1, null);
        bnve.setIconTintList(2, null);
        bnve.setIconTintList(3, null);

        //if come from add to cart item
        if (App.getAppPreferences().getSetcart() != null) {
            App.getAppPreferences().setSetcart(null);
            bnve.setIconTintList(1, null);
            menu.getItem(0).setIcon(R.drawable.unselect_scissors);
            menu.getItem(1).setIcon(R.drawable.select_cart);
            menu.getItem(2).setIcon(R.drawable.unselected_profile);
            menu.getItem(3).setIcon(R.drawable.ic_appointment);

            menu.getItem(0).setTitle(resources.getString(R.string.home));

            menu.getItem(1).setTitle(resources.getString(R.string.cart));

            menu.getItem(2).setTitle(resources.getString(R.string.orders));

            menu.getItem(3).setTitle(resources.getString(R.string.appoint));
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            transaction1.replace(R.id.container, new AddToCartFragment());
            transaction1.commit();
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_select_scissors);

            menu.getItem(0).setTitle(resources.getString(R.string.home));

            menu.getItem(1).setTitle(resources.getString(R.string.cart));

            menu.getItem(2).setTitle(resources.getString(R.string.orders));

            menu.getItem(3).setTitle(resources.getString(R.string.appoint));
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, new HomeFragment());
            transaction.commit();
        }
    }

    private void initView() {

        bnve = findViewById(R.id.bnve);
        menu = bnve.getMenu();

    }

    //Set Navigation icon or text
    private void SetUps() {

        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        bnve.setIconMarginTop(0, BottomNavigationViewEx.dp2px(this, 8));
        bnve.setIconMarginTop(1, BottomNavigationViewEx.dp2px(this, 8));
        bnve.setIconMarginTop(2, BottomNavigationViewEx.dp2px(this, 8));
        bnve.setIconMarginTop(3, BottomNavigationViewEx.dp2px(this, 8));

    }
}
