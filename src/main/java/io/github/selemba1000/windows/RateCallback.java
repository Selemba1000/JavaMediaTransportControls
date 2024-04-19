package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCValueChangedCallback;

/**
 * The callback for when the rate button is pressed in UI.
 */
public class RateCallback implements Callback {

    private final JMTCValueChangedCallback<Double> callbackDel;

    /**
     * Constructor that sets the callback.
     * @param callback The callback to be set
     */
    RateCallback(JMTCValueChangedCallback<Double> callback){
        this.callbackDel = callback;
    }

    /**
     * The callback function.
     * @param value the requested playback rate
     */
    public void callback(Double value){
        if (callbackDel!=null){
            callbackDel.callback(value);
        }
    }

}
