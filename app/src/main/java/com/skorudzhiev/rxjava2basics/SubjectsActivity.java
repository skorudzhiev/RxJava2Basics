package com.skorudzhiev.rxjava2basics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class SubjectsActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button publishButton, replayButton, behaviorButton, asyncButton;
    TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjects_activity);

        publishButton = findViewById(R.id.publish_subject);
        replayButton = findViewById(R.id.replay_button);
        behaviorButton = findViewById(R.id.behavior_button);
        asyncButton = findViewById(R.id.async_button);
        textView = findViewById(R.id.subject_text_view);

        publishButton.setOnClickListener(view -> publishSomeWork());
        replayButton.setOnClickListener(view -> replaySomeWork());
        behaviorButton.setOnClickListener(view -> behaviorSomeWork());
        asyncButton.setOnClickListener(view -> asyncSomeWork());

    }

    /**
     * PublishSubject emits to an observer only those items that are emitted
     * by the source Observable, subsequent to the time of the subscription.
     */
    private void publishSomeWork() {
        textView.setText("");

        PublishSubject<Integer> source = PublishSubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        /*
         * it will emit 4 and onComplete for second observer also.
         */
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();

    }

    /**
     * ReplaySubject emits to any observer all of the items that were emitted
     * by the source Observable, regardless of when the observer subscribes.
     */
    private void replaySomeWork() {
        textView.setText("");

        ReplaySubject<Integer> source = ReplaySubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

        /*
         * it will emit 1, 2, 3, 4 for second observer also as we have used replay
         */
        source.subscribe(getSecondObserver());

    }

    /**
     * When an observer subscribes to a BehaviorSubject, it begins by emitting the item most
     * recently emitted by the source Observable (or a seed/default value if none has yet been
     * emitted) and then continues to emit any other items emitted later by the source Observable(s).
     * It is different from Async Subject as async emits the last value (and only the last value)
     * but the Behavior Subject emits the last and the subsequent values also.
     */
    private void behaviorSomeWork() {
        textView.setText("");

        BehaviorSubject<Integer> source = BehaviorSubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        /*
         * it will emit 3(last emitted), 4 and onComplete for second observer also.
         */
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();

    }

    /**
     * An AsyncSubject emits the last value (and only the last value) emitted by the source
     * Observable, and only after that source Observable completes. (If the source Observable
     * does not emit any values, the AsyncSubject also completes without emitting any values.)
     */
    private void asyncSomeWork() {
        textView.setText("");

        AsyncSubject<Integer> source = AsyncSubject.create();

        source.subscribe(getFirstObserver()); // it will emit only 4 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        /*
         * it will emit 4 and onComplete for second observer also.
         */
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();

    }


    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " First onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" First onNext : value : " + value);
                textView.append(System.getProperty("line.separator"));
                Log.d(TAG, " First onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" First onError : " + e.getMessage());
                textView.append(System.getProperty("line.separator"));
                Log.d(TAG, " First onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" First onComplete");
                textView.append(System.getProperty("line.separator"));
                Log.d(TAG, " First onComplete");
            }
        };
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                textView.append(" Second onSubscribe : isDisposed :" + d.isDisposed());
                Log.d(TAG, " Second onSubscribe : " + d.isDisposed());
                textView.append(System.getProperty("line.separator"));
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" Second onNext : value : " + value);
                textView.append(System.getProperty("line.separator"));
                Log.d(TAG, " Second onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" Second onError : " + e.getMessage());
                textView.append(System.getProperty("line.separator"));
                Log.d(TAG, " Second onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" Second onComplete");
                textView.append(System.getProperty("line.separator"));
                Log.d(TAG, " Second onComplete");
            }
        };
    }


}
