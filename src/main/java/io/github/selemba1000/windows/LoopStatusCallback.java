package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCParameters;
import io.github.selemba1000.JMTCValueChangedCallback;

/**
 * The callback for when the Loop option is pressed in UI.
 */
public class LoopStatusCallback implements Callback {

    private final JMTCValueChangedCallback<JMTCParameters.LoopStatus> callbackDel;

    /**
     * Constructor that sets the callback.
     * @param callback The callback to be set
     */
    LoopStatusCallback(JMTCValueChangedCallback<JMTCParameters.LoopStatus> callback){
        this.callbackDel = callback;
    }

    /**
     * The callback function.
     * @param loop the requested loop status
     */
    public void callback(UnsignedInt loop){
        if(callbackDel!=null) {
            switch (loop.intValue()) {
                case 0:
                    callbackDel.callback(JMTCParameters.LoopStatus.None);
                    break;
                case 1:
                    callbackDel.callback(JMTCParameters.LoopStatus.Track);
                    break;
                case 2:
                    callbackDel.callback(JMTCParameters.LoopStatus.Playlist);
                    break;
            }
        }
    }

}
