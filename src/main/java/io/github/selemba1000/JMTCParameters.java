package io.github.selemba1000;

/**
 * Class to group parameters for JMTC.
 */
@SuppressWarnings("CanBeFinal")
public class JMTCParameters {

    /**
     * Loop status.
     * @see JMTCParameters.LoopStatus
     * @see JMTCCallbacks#onLoop
     */
    public LoopStatus loopStatus;
    /**
     * Volume.
     * Between 0 and 1. Will not be checked by Library.
     * @see JMTCCallbacks#onVolume
     */
    public Double volume;
    /**
     * Rate.
     * Between 0 and 1. Will not be checked by Library.
     * @see JMTCCallbacks#onRate
     */
    public Double rate;
    /**
     * Shuffle.
     * @see JMTCCallbacks#onShuffle
     */
    public Boolean shuffle;

    /**
     * Constructor for JMTCParameters.
     * @param loopStatus Loop status.
     * @param volume Volume.
     * @param rate Rate.
     * @param shuffle Shuffle.
     */
    public JMTCParameters(LoopStatus loopStatus, Double volume, Double rate, Boolean shuffle) {
        this.loopStatus = loopStatus;
        this.volume = volume;
        this.rate = rate;
        this.shuffle = shuffle;
    }

    /**
     * Enum to store Loop Status.
     * <p>
     * None = No Looping
     * <p>
     * Playlist = Loop Playlist
     * <p>
     * Track = Loop current Track
     */
    public enum LoopStatus {
        None,
        Playlist,
        Track
    }
}


