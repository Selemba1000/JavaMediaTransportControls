package me.selemba;

import me.selemba.linux.MPRISPlayer2Player;

public class MediaTransportControlsParameters {

    public LoopStatus loopStatus;
    public Double volume;
    public Double rate;
    public Boolean shuffle;

    public MediaTransportControlsParameters(LoopStatus loopStatus, Double volume, Double rate, Boolean shuffle){
        this.loopStatus = loopStatus;
        this.volume = volume;
        this.rate = rate;
        this.shuffle = shuffle;
    }

    public enum LoopStatus {
        None,
        Playlist,
        Track;

        public MPRISPlayer2Player.LoopStatus toInner(){
            switch (this){
                case None:
                    return MPRISPlayer2Player.LoopStatus.None;
                case Track:
                    return MPRISPlayer2Player.LoopStatus.Track;
                case Playlist:
                    return MPRISPlayer2Player.LoopStatus.Playlist;
                default:
                    throw new IllegalStateException();
            }
        }

    }
}


