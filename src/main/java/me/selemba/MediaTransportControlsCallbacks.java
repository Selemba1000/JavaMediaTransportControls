package me.selemba;

public class MediaTransportControlsCallbacks{
    public MediaTransportControlsButtonCallback onPlay = () -> {};
    public MediaTransportControlsButtonCallback onPause = () -> {};
    public MediaTransportControlsButtonCallback onStop = () -> {};
    public MediaTransportControlsButtonCallback onNext = () -> {};
    public MediaTransportControlsButtonCallback onPrevious = () -> {};
    public MediaTransportControlsSeekCallback onSeek = (Long x) -> {};
    public MediaTransportControlsValueChangedCallback<Boolean> onShuffle = (Boolean x) -> {};
    public MediaTransportControlsValueChangedCallback<Double> onRate = (Double x) -> {};
    public MediaTransportControlsValueChangedCallback<MediaTransportControlsParameters.LoopStatus> onLoop = (MediaTransportControlsParameters.LoopStatus x) -> {};
    public MediaTransportControlsValueChangedCallback<Double> onVolume = (Double x) -> {};

}
