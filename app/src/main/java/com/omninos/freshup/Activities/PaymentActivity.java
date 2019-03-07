package com.omninos.freshup.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.omninos.freshup.Adapters.CustomSpinnerAdapter;
import com.omninos.freshup.ModelClasses.ProductBuyModel;
import com.omninos.freshup.R;
import com.omninos.freshup.Retrofit.Api;
import com.omninos.freshup.Retrofit.ApiClient;
import com.omninos.freshup.Utils.App;
import com.omninos.freshup.Utils.CommonUtils;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.omninos.freshup.Utils.App.getContext;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    PaymentActivity activity;
    private RelativeLayout stripeType, paypalType, cashType;
    Button bt_verify;
    CardView cardpayment, typeOfPayment;
    Spinner spCard;
    private String card = "Select Card";
    private String paymentMethod = "", StrTimeslot, datedata, barbarId, StrServiceId;
    int cardMonth, cardYear, currentYear, currentMonth;
    private EditText card_number, expiryDate, et_ccv, firstName, lastname;
    String[] cardList = {"Select Card", "Debit Card", "Credit Card", "Master Card"};
    int[] cardImageList = {R.drawable.ic_visa, R.drawable.ic_visa, R.drawable.ic_credit_card, R.drawable.ic_mastercard};
    private String DeliveryType, ProductIds, City, Address, State, Zip, Country, Type, price, ServicesIds;
    private int min, max, orderId;

    private double priceData = 0.0;
    StringBuilder prices;
    StringBuilder serviceId;


    @Override
    public void onBackPressed() {
        if (typeOfPayment.getVisibility() == View.GONE) {
            typeOfPayment.setVisibility(View.VISIBLE);
            cardpayment.setVisibility(View.GONE);
        } else {
            if (getIntent().getStringExtra("Type").equalsIgnoreCase("Product")) {
                if (getIntent().getStringExtra("DeliverType").equalsIgnoreCase("Home")) {
                    startActivity(new Intent(activity, DeliveryActivity.class));
                    finishAffinity();
                } else {
                    startActivity(new Intent(activity, PromoActivity.class));
                    finishAffinity();
                }
            } else if (getIntent().getStringExtra("Type").equalsIgnoreCase("Services")) {
                startActivity(new Intent(activity, HomeActivity.class));
                finishAffinity();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        activity = PaymentActivity.this;


        min = 1000000;
        max = 9999999;
        orderId = new Random().nextInt((max - min) + 1) + min;

        initView();
        SetUps();

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(activity, cardList, cardImageList);
        spCard.setAdapter(customSpinnerAdapter);
        spCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    card = cardList[i];
                    Toast.makeText(activity, card, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        spCard = findViewById(R.id.sp_card);
        stripeType = findViewById(R.id.stripeType);
        paypalType = findViewById(R.id.paypalType);
        cashType = findViewById(R.id.cashType);
        cardpayment = findViewById(R.id.cardPayment);
        typeOfPayment = findViewById(R.id.typeOfPayment);
        bt_verify = findViewById(R.id.bt_verify);
        card_number = findViewById(R.id.card_number);
        expiryDate = findViewById(R.id.expiryDate);
        et_ccv = findViewById(R.id.et_ccv);
        firstName = findViewById(R.id.cardFirstName);
        lastname = findViewById(R.id.cardLastName);


        Type = getIntent().getStringExtra("Type");

        if (Type.equalsIgnoreCase("Product")) {
            DeliveryType = getIntent().getStringExtra("DeliveryType");
            ProductIds = App.getAppPreferences().getProductId();

            City = getIntent().getStringExtra("City");

            State = getIntent().getStringExtra("State");

            Address = getIntent().getStringExtra("Address");
            Country = getIntent().getStringExtra("Country");
            Zip = getIntent().getStringExtra("Zip");
        } else if (Type.equalsIgnoreCase("MyServices")) {

            StrTimeslot = getIntent().getStringExtra("StrTimeslot");
            datedata = getIntent().getStringExtra("datedata");
            barbarId = getIntent().getStringExtra("barbarId");
//            StrServiceId=getIntent().getStringExtra("StrServiceId");

        } else {
//            DeliveryType = getIntent().getStringExtra("DeliveryType");
//            ProductIds = App.getAppPreferences().getProductId();
//
//            City = getIntent().getStringExtra("City");
//
//            State = getIntent().getStringExtra("State");
//
//            Address = getIntent().getStringExtra("Address");
//            Country = getIntent().getStringExtra("Country");
//            Zip = getIntent().getStringExtra("Zip");

            ServicesIds = getIntent().getStringExtra("BookingServiceId");
            price = getIntent().getStringExtra("amount");
        }

    }

    private void SetUps() {
        back.setOnClickListener(this);
        stripeType.setOnClickListener(this);
        paypalType.setOnClickListener(this);
        cashType.setOnClickListener(this);
        bt_verify.setOnClickListener(this);
        expiryDate.setFocusable(false);
        expiryDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(activity, DeliveryActivity.class));
                finishAffinity();
                break;

            case R.id.stripeType:
                paymentMethod = "stripe";
                typeOfPayment.setVisibility(View.GONE);
                cardpayment.setVisibility(View.VISIBLE);
                break;

            case R.id.paypalType:
                paymentMethod = "Paypal";
                typeOfPayment.setVisibility(View.GONE);
                cardpayment.setVisibility(View.VISIBLE);
                break;

            case R.id.cashType:
                paymentMethod = "cash";
                OpenConfirmBox();
                break;

            case R.id.bt_verify:
                GetCardData();
                break;

            case R.id.expiryDate:
                OpenDialog();
                break;
        }
    }

    private void OpenDialog() {

        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        currentMonth = mMonth;
        currentYear = mYear;
        DatePickerDialog monthDatePickerDialog = new DatePickerDialog(activity,
                AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                expiryDate.setText((month + 1) + "/" + year);
                cardMonth = month + 1;
                cardYear = year;
            }
        }, mYear, mMonth, mDay) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                getDatePicker().findViewById(getResources().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
            }
        };
        monthDatePickerDialog.setTitle("select_month");
        monthDatePickerDialog.show();
    }

    private void GetCardData() {

        String cardNumver = card_number.getText().toString();
        String fname = firstName.getText().toString();
        String lname = lastname.getText().toString();
        String ccvNumber = et_ccv.getText().toString();
        if (card.equalsIgnoreCase("Select Card")) {
            Toast.makeText(activity, "Select Card", Toast.LENGTH_SHORT).show();
        } else if (fname.isEmpty()) {
            Toast.makeText(activity, "enter first name", Toast.LENGTH_SHORT).show();
        } else if (lname.isEmpty()) {
            Toast.makeText(activity, "enter surname", Toast.LENGTH_SHORT).show();
        } else if (currentYear <= cardYear) {
            if (currentYear == cardYear) {
                if (currentMonth > cardMonth) {
                    Toast.makeText(activity, "Enter valid exp month", Toast.LENGTH_SHORT).show();
                } else {
                    Verifycard(cardNumver, cardMonth, cardYear, ccvNumber);
                }
            } else {
                Verifycard(cardNumver, cardMonth, cardYear, ccvNumber);
            }
        } else {
            Toast.makeText(activity, "Enter valid exp Year", Toast.LENGTH_SHORT).show();
        }
    }

    private void OpenConfirmBox() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
//                        startActivity(new Intent(PaymentActivity.this, HomeActivity.class));
//                        finishAffinity();
                        if (Type.equalsIgnoreCase("Product")) {
                            BookProduct("");
                        } else {
                            BookingServices("");
                        }
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.cancel();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you Confirm ?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void Verifycard(String cardNumber, int cardExpMonth, int cardExpYear, String cardCVC) {
        CommonUtils.showProgress(activity, "");
        Card card = new Card(
                cardNumber,
                cardExpMonth,
                cardExpYear,
                cardCVC
        );

        card.validateNumber();
        card.validateCVC();

        if (card != null) {
            Stripe stripe = new Stripe(this, "pk_test_BvAnXwzQ41NgEnMpB7x74pH2");
            stripe.createToken(card, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Token token) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, "Success " + token.getId(), Toast.LENGTH_SHORT).show();
                    Log.d("onSuccess: ", token.getId());
                    if (Type.equalsIgnoreCase("Product")) {
                        BookProduct(token.getId());
                    } else {
                        BookingServices(token.getId());
                    }
                }
            });
        }
    }

    private void BookingServices(String id) {
        ServicesIds = CheckPrice();
        if (CommonUtils.isNetworkConnected(activity)) {

            Api api = ApiClient.getApiClient().create(Api.class);

            CommonUtils.showProgress(activity, "");

            api.bookingServicePayment(paymentMethod, App.getAppPreferences().getUserId(activity), ServicesIds, id, App.getAppPreferences().getProductAmount(), StrServiceId, barbarId, datedata, StrTimeslot).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        if (response.isSuccessful()) {
                            if (response.body().get("success").equals("1")) {
                                Toast.makeText(activity, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(activity, HomeActivity.class));
                                finishAffinity();
                            } else {
                                Toast.makeText(activity, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    CommonUtils.dismissProgress();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void BookProduct(String id) {
        if (CommonUtils.isNetworkConnected(activity)) {

            CommonUtils.showProgress(activity, "");

            Api api = ApiClient.getApiClient().create(Api.class);

            api.buyProduct(paymentMethod, App.getAppPreferences().getUserId(activity), Country, State, City, Address, Zip, App.getAppPreferences().getProductAmount(), id, ProductIds, String.valueOf(orderId)).enqueue(new Callback<ProductBuyModel>() {
                @Override
                public void onResponse(Call<ProductBuyModel> call, Response<ProductBuyModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {

                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, HomeActivity.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<ProductBuyModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Internet Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private String CheckPrice() {

        List<String> getSubServices = App.getAppPreferences().getItemsdataSubServices();
        try {
            serviceId = new StringBuilder();
            for (String s : getSubServices) {
                serviceId.append(s + ",");
            }
            int size = serviceId.length();
            serviceId.deleteCharAt(size - 1);
            System.out.println("Data Is: " + serviceId);
            StrServiceId = serviceId.toString();
            System.out.println("Data Sys: " + StrServiceId);
        } catch (Exception e) {
            Toast.makeText(this, "Choose Services", Toast.LENGTH_SHORT).show();
            serviceId = new StringBuilder();
        }
        return StrServiceId;
    }
}
