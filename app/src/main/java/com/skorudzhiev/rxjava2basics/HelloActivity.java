package com.skorudzhiev.rxjava2basics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class HelloActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    TextView textView;
    Button observe;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        observe = findViewById(R.id.observe_button);
        textView = findViewById(R.id.text_view);

        // Replacing the OnClick declaration with Lambda statement
        observe.setOnClickListener(view -> run());


    }



    private void run() {
        Observable<String> observable = Observable.just("Hello World!\n")
                // map() operator is used to transform one emitted item into another
                .map(s -> s + " -Stoyan\n")
                // Transforming the latest String into hashcode and returning result
                .map(s -> s + s.hashCode())
                .cache();

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String value) {
                textView.append(value);

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: All Done!");
            }
        };

        //Create our subscription//
        observable.subscribe(observer);


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
