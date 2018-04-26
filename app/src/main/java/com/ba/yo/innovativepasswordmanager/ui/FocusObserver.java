package com.ba.yo.innovativepasswordmanager.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class FocusObserver implements LifecycleObserver{

    private AppCompatActivity current;

    /**
     * Construct observer
     * @param caller Activity that has created the observer
     */
    FocusObserver(AppCompatActivity caller){
        current = caller;
    }

    /**
     * This function is called when app returns to foreground
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void startSomething() {
        Log.d("ProcessLog", "APP IS ON FOREGROUND");
    }

    /**
     * This function is called when app goes to background
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stopSomething() {
        Log.d("ProcessLog", "APP IS IN BACKGROUND");
        current.finishAndRemoveTask();
        ProcessLifecycleOwner.get().getLifecycle().removeObserver(this);
    }
}
