package me.selemba;

import me.selemba.linux.LinuxMediaTransportControls;
import me.selemba.windows.WindowsMediaTransportControls;

public abstract class MediaTransportControls {

    protected static MediaTransportControls INSTANCE;

    protected MediaTransportControls(){};

    public static synchronized MediaTransportControls getInstance(String playerName){
        if (INSTANCE==null){
            String os = System.getProperty("os.name");
            if(os.toLowerCase().contains("win")){
                INSTANCE = new WindowsMediaTransportControls();
            }else if(os.toLowerCase().contains("nux")){
                INSTANCE = new LinuxMediaTransportControls(playerName);
            }
        }
        return INSTANCE;
    }

    public abstract MediaTransportControlsPlayingState getPlayingState();

    public abstract void setPlayingState(MediaTransportControlsPlayingState state);

    public abstract boolean getEnabled();
    public abstract void setEnabled(boolean enabled);

    public abstract MediaTransportControlsEnabledButtons getEnabledButtons();
    public abstract void setEnabledButtons(MediaTransportControlsEnabledButtons enabledButtons);

    public abstract void setCallbacks(MediaTransportControlsCallbacks callbacks);

    public abstract void setTimelineProperties(MediaTransportControlsTimelineProperties timelineProperties);
    public abstract void setPosition(Long position);

    public abstract void updateDisplay();
    public abstract void resetDisplay();

    public abstract MediaTransportControlsMediaType getMediaType();
    public abstract void setMediaType(MediaTransportControlsMediaType mediaType);

    public abstract MediaTransportControlsMediaProperties getMediaProperties();
    public abstract void setMediaProperties(MediaTransportControlsMediaProperties mediaProperties);

}
