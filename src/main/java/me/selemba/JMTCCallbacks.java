package me.selemba;


@SuppressWarnings("CanBeFinal")
public class JMTCCallbacks {
    public JMTCButtonCallback onPlay = () -> {
    };
    public JMTCButtonCallback onPause = () -> {
    };
    public JMTCButtonCallback onStop = () -> {
    };
    public JMTCButtonCallback onNext = () -> {
    };
    public JMTCButtonCallback onPrevious = () -> {
    };
    public JMTCSeekCallback onSeek = (Long x) -> {
    };
    public JMTCValueChangedCallback<Boolean> onShuffle = (Boolean x) -> {
    };
    public JMTCValueChangedCallback<Double> onRate = (Double x) -> {
    };
    public JMTCValueChangedCallback<JMTCParameters.LoopStatus> onLoop = (JMTCParameters.LoopStatus x) -> {
    };
    public JMTCValueChangedCallback<Double> onVolume = (Double x) -> {
    };

}
