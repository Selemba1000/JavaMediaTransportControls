package me.selemba;

public class JMTCParameters {

    public LoopStatus loopStatus;
    public Double volume;
    public Double rate;
    public Boolean shuffle;

    public JMTCParameters(LoopStatus loopStatus, Double volume, Double rate, Boolean shuffle){
        this.loopStatus = loopStatus;
        this.volume = volume;
        this.rate = rate;
        this.shuffle = shuffle;
    }

    public enum LoopStatus {
        None,
        Playlist,
        Track
    }
}


