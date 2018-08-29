package com.skorudzhiev.rxjava2basics;

import android.arch.lifecycle.ViewModel;

public class HelloViewModel extends ViewModel {

    private String helloWorld;

    public void setHelloWorld(String helloWorld) {
        this.helloWorld = helloWorld;
    }

    public String getHelloWorld() {
        return helloWorld;
    }
}
