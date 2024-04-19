package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCValueChangedCallback;

/**
 * The callback for when the shuffle button is pressed in UI
 */
public class ShuffleCallback implements Callback {

    private final JMTCValueChangedCallback<Boolean> callbackDel;

    /**
     * Constructor that sets the callback
     * @param callback The callback to be set
     */
    ShuffleCallback(JMTCValueChangedCallback<Boolean> callback){
        this.callbackDel = callback;
    }

    /**
     * The callback function.
     * @param value the requested shuffle setting
     */
    public void callback(Boolean value){
        if(callbackDel!=null){
            callbackDel.callback(value);
        }
    }

}
