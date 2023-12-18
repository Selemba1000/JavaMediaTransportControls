package me.selemba.windows;

import com.sun.jna.Callback;
import me.selemba.MediaTransportControlsButtonCallback;

public class ButtonPressedCallback implements Callback {

    private final MediaTransportControlsButtonCallback callbackDel;

    protected ButtonPressedCallback(MediaTransportControlsButtonCallback callback) {
        this.callbackDel = callback;
    }

    public void callback() {
        if (callbackDel != null) {
            callbackDel.callback();
        }
    }
}
