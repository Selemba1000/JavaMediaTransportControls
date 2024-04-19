package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCButtonCallback;

/**
 * Interface for callbacks executed by ButtonPresses
 */
public class ButtonPressedCallback implements Callback {

    /**
     * Callback delegate to avoid empty callbacks
     */
    private final JMTCButtonCallback callbackDel;

    /**
     * Constructor to set the callback
     * @param callback Callback to be set
     */
    protected ButtonPressedCallback(JMTCButtonCallback callback) {
        this.callbackDel = callback;
    }

    /**
     * The Callback
     */
    @SuppressWarnings("unused")
    public void callback() {
        if (callbackDel != null) {
            callbackDel.callback();
        }
    }
}
