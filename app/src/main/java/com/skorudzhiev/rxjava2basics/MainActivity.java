package com.skorudzhiev.rxjava2basics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView text;
    Button buttonHel, bindButton, subjButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text_view);
        buttonHel = findViewById(R.id.hel_button);
        buttonHel.setOnClickListener(this);
        bindButton = findViewById(R.id.bind_button);
        bindButton.setOnClickListener(this);
        subjButton = findViewById(R.id.subject_button);
        subjButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.hel_button:
                Intent intent = new Intent(this, HelloActivity.class);
                startActivity(intent);
                break;
            case R.id.bind_button:
                Intent bindIntent = new Intent(this, RxBindingActivity.class);
                startActivity(bindIntent);
                break;
            case R.id.subject_button:
                Intent subjIntent = new Intent(this, SubjectsActivity.class);
                startActivity(subjIntent);
                break;
        }

    }
}
