package io.github.selemba1000;

/**
 * Class to group all different callbacks.
 */
@SuppressWarnings("CanBeFinal")
public class JMTCCallbacks {
    /**
     * Callback for when Play button is pressed in UI or as MediaKey.
     */
    public JMTCButtonCallback onPlay = () -> {
    };
    /**
     * Callback for when Pause button is pressed in UI or as MediaKey.
     */
    public JMTCButtonCallback onPause = () -> {
    };
    /**
     * Callback for when Stop button is pressed in UI or as MediaKey.
     */
    public JMTCButtonCallback onStop = () -> {
    };
    /**
     * Callback for when Next button is pressed in UI or as MediaKey.
     */
    public JMTCButtonCallback onNext = () -> {
    };
    /**
     * Callback for when Previous button is pressed in UI or as MediaKey.
     */
    public JMTCButtonCallback onPrevious = () -> {
    };
    /**
     * Callback for when the seek bar is moved in UI.
     */
    public JMTCSeekCallback onSeek = (Long x) -> {
    };
    /**
     * Callback for when the shuffle button is pressed in UI.
     * var x is true if shuffle is supposed to be enabled, false otherwise
     * @apiNote The state of shuffle is not automatically updated. Should be manually updated in callback, or ignored if not applicable.
     */
    public JMTCValueChangedCallback<Boolean> onShuffle = (Boolean x) -> {
    };
    /**
     * Callback for when the rate slider is moved in UI.
     * var x is the new rate value
     * @apiNote The state of rate is not automatically updated. Should be manually updated in callback, or ignored if not applicable.
     */
    public JMTCValueChangedCallback<Double> onRate = (Double x) -> {
    };
    /**
     * Callback for when the loop button is pressed in UI.
     * var x is the new loop status
     * @apiNote The state of loop is not automatically updated. Should be manually updated in callback, or ignored if not applicable.
     */
    public JMTCValueChangedCallback<JMTCParameters.LoopStatus> onLoop = (JMTCParameters.LoopStatus x) -> {
    };
    /**
     * Callback for when the volume slider is moved in UI.
     * var x is the new volume value
     * @apiNote The state of volume is not automatically updated. Should be manually updated in callback, or ignored if not applicable.
     */
    public JMTCValueChangedCallback<Double> onVolume = (Double x) -> {
    };

}
