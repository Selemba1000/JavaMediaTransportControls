package me.selemba.windows;

import com.sun.jna.Callback;
import me.selemba.MediaTransportControlsSeekCallback;

public class SeekCallback implements Callback {

    private final MediaTransportControlsSeekCallback callbackDel;

    protected SeekCallback(MediaTransportControlsSeekCallback callback){this.callbackDel = callback;}

    void callback(Long position){
        if(callbackDel!=null){
            callbackDel.callback(position);
        }
    }
}
