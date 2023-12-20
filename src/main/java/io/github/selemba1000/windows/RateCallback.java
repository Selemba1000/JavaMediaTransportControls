package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCValueChangedCallback;

public class RateCallback implements Callback {

    private final JMTCValueChangedCallback<Double> callbackDel;

    RateCallback(JMTCValueChangedCallback<Double> callback){
        this.callbackDel = callback;
    }

    public void callback(Double value){
        if (callbackDel!=null){
            callbackDel.callback(value);
        }
    }

}
