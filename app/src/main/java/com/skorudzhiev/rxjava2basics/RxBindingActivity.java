package com.skorudzhiev.rxjava2basics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

public class RxBindingActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binding_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.edit_button);
        textView = findViewById(R.id.edit_text_view);
        editText = findViewById(R.id.edit_text);

        RxView.clicks(button)
                // .debounce() operator of ReactiveX
                // this button should react to an OnClick event only if
                // there’s been at least a 500 millisecond gap since the previous click event
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid ->
                        Toast.makeText(RxBindingActivity.this,
                                "RxView.clicks", Toast.LENGTH_SHORT).show());

        RxTextView.textChanges(editText)
                .subscribe(charSequence -> textView.setText(charSequence));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
