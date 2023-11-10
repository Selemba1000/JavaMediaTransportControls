package me.selemba.linux;

import me.selemba.*;

public class LinuxMediaTransportControls extends MediaTransportControls {

    public LinuxMediaTransportControls(String playerName){

    }

    @Override
    public MediaTransportControlsPlayingState getPlayingState() {
        return null;
    }

    @Override
    public void setPlayingState(MediaTransportControlsPlayingState state) {

    }

    @Override
    public boolean getEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public MediaTransportControlsEnabledButtons getEnabledButtons() {
        return null;
    }

    @Override
    public void setEnabledButtons(MediaTransportControlsEnabledButtons enabledButtons) {

    }

    @Override
    public void setCallbacks(MediaTransportControlsCallbacks callbacks) {

    }

    @Override
    public void setTimelineProperties(MediaTransportControlsTimelineProperties timelineProperties) {

    }

    @Override
    public void setPosition(Long position) {

    }

    @Override
    public void updateDisplay() {

    }

    @Override
    public void resetDisplay() {

    }

    @Override
    public MediaTransportControlsMediaType getMediaType() {
        return null;
    }

    @Override
    public void setMediaType(MediaTransportControlsMediaType mediaType) {

    }

    @Override
    public MediaTransportControlsMediaProperties getMediaProperties() {
        return null;
    }

    @Override
    public void setMediaProperties(MediaTransportControlsMediaProperties mediaProperties) {

    }
}
