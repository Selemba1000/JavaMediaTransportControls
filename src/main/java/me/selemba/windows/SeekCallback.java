package me.selemba.windows;

import com.sun.jna.Callback;

public interface SeekCallback extends Callback {
    void callback(Long position);
}
