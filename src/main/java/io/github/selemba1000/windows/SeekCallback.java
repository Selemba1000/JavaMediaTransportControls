package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCSeekCallback;

/**
 * The callback for when the seekbar in UI is used.
 */
public class SeekCallback implements Callback {

    private final JMTCSeekCallback callbackDel;

    /**
     * Constructor that set the callback
     * @param callback The callback to set
     */
    protected SeekCallback(JMTCSeekCallback callback) {
        this.callbackDel = callback;
    }

    /**
     * The callback function.
     * @param position the requested seek position
     */
    @SuppressWarnings("unused")
    public void callback(Long position) {
        if (callbackDel != null) {
            callbackDel.callback(position);
        }
    }
}
