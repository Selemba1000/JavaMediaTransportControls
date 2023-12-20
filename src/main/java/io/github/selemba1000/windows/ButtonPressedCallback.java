package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCButtonCallback;

public class ButtonPressedCallback implements Callback {

    private final JMTCButtonCallback callbackDel;

    protected ButtonPressedCallback(JMTCButtonCallback callback) {
        this.callbackDel = callback;
    }

    @SuppressWarnings("unused")
    public void callback() {
        if (callbackDel != null) {
            callbackDel.callback();
        }
    }
}
