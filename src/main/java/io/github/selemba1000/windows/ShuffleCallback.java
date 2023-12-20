package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCValueChangedCallback;

public class ShuffleCallback implements Callback {

    private final JMTCValueChangedCallback<Boolean> callbackDel;

    ShuffleCallback(JMTCValueChangedCallback<Boolean> callback){
        this.callbackDel = callback;
    }

    public void callback(Boolean value){
        if(callbackDel!=null){
            callbackDel.callback(value);
        }
    }

}
