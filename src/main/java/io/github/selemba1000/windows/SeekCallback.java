package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCSeekCallback;

class SeekCallback implements Callback {

    private final JMTCSeekCallback callbackDel;

    protected SeekCallback(JMTCSeekCallback callback) {
        this.callbackDel = callback;
    }

    @SuppressWarnings("unused")
    void callback(Long position) {
        if (callbackDel != null) {
            callbackDel.callback(position);
        }
    }
}