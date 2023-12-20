package io.github.selemba1000.windows;

import com.sun.jna.Callback;
import io.github.selemba1000.JMTCParameters;
import io.github.selemba1000.JMTCValueChangedCallback;

public class LoopStatusCallback implements Callback {

    private final JMTCValueChangedCallback<JMTCParameters.LoopStatus> callbackDel;

    LoopStatusCallback(JMTCValueChangedCallback<JMTCParameters.LoopStatus> callback){
        this.callbackDel = callback;
    }

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
