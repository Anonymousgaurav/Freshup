package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;

public class ChooseLanguageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button select;
    private String selectedlanguage,Move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        initView();
        SetUps();
    }

    private void initView() {
        select = findViewById(R.id.selectLanguage);
        Move=getIntent().getStringExtra("Move");
    }

    private void SetUps() {
        select.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.englishLng:
                if (checked)
                    // Pirates are the best
                    selectedlanguage = "en";
                break;
            case R.id.frenchLng:
                if (checked)
                    // Ninjas rule
                    selectedlanguage = "fr";
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectLanguage:
                if (Move.equalsIgnoreCase("1")){
                    App.getAppPreferences().saveLanguage(ChooseLanguageActivity.this, selectedlanguage);
                    startActivity(new Intent(ChooseLanguageActivity.this, SettingActivity.class));
                }else {
                    App.getAppPreferences().saveLanguage(ChooseLanguageActivity.this, selectedlanguage);
                    startActivity(new Intent(ChooseLanguageActivity.this, WellcomeScreenActivity.class));
                    finishAffinity();
                }

                break;
        }
    }
}
