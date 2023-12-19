package me.selemba.windows;

import com.sun.jna.Callback;
import me.selemba.JMTCButtonCallback;

class ButtonPressedCallback implements Callback {

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
