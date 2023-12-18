package me.selemba;

import me.selemba.linux.LinuxMediaTransportControls;
import me.selemba.windows.WindowsMediaTransportControls;

public abstract class MediaTransportControls {

    protected static MediaTransportControls INSTANCE;

    protected MediaTransportControls() {
    }

    public static synchronized MediaTransportControls getInstance(MediaTransportControlsSettings settings) {
        if (INSTANCE == null) {
            String os = System.getProperty("os.name");
            if (os.toLowerCase().contains("win")) {
                INSTANCE = new WindowsMediaTransportControls();
            } else if (os.toLowerCase().contains("nux")) {
                INSTANCE = new LinuxMediaTransportControls(settings.playerName, settings.desktopFile);
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public abstract MediaTransportControlsPlayingState getPlayingState();

    public abstract void setPlayingState(MediaTransportControlsPlayingState state);

    @SuppressWarnings("unused")
    public abstract boolean getEnabled();

    public abstract void setEnabled(boolean enabled);

    @SuppressWarnings("unused")
    public abstract MediaTransportControlsEnabledButtons getEnabledButtons();

    public abstract void setEnabledButtons(MediaTransportControlsEnabledButtons enabledButtons);

    public abstract void setCallbacks(MediaTransportControlsCallbacks callbacks);

    @SuppressWarnings("unused")
    public abstract MediaTransportControlsParameters getParameters();

    public abstract void setParameters(MediaTransportControlsParameters parameters);

    public abstract void setTimelineProperties(MediaTransportControlsTimelineProperties timelineProperties);

    public abstract void setPosition(Long position);

    public abstract void updateDisplay();

    @SuppressWarnings("unused")
    public abstract void resetDisplay();

    @SuppressWarnings("unused")
    public abstract MediaTransportControlsMediaType getMediaType();

    public abstract void setMediaType(MediaTransportControlsMediaType mediaType);

    @SuppressWarnings("unused")
    public abstract MediaTransportControlsMediaProperties getMediaProperties();

    public abstract void setMediaProperties(MediaTransportControlsMediaProperties mediaProperties);

}
