package me.selemba;

import me.selemba.linux.LinuxJMTC;
import me.selemba.windows.WindowsJMTC;

public abstract class JMTC {

    protected static JMTC INSTANCE;

    protected JMTC() {
    }

    public static synchronized JMTC getInstance(JMTCSettings settings) {
        if (INSTANCE == null) {
            String os = System.getProperty("os.name");
            if (os.toLowerCase().contains("win")) {
                INSTANCE = new WindowsJMTC();
            } else if (os.toLowerCase().contains("nux")) {
                INSTANCE = new LinuxJMTC(settings.playerName, settings.desktopFile);
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public abstract JMTCPlayingState getPlayingState();

    public abstract void setPlayingState(JMTCPlayingState state);

    @SuppressWarnings("unused")
    public abstract boolean getEnabled();

    public abstract void setEnabled(boolean enabled);

    @SuppressWarnings("unused")
    public abstract JMTCEnabledButtons getEnabledButtons();

    public abstract void setEnabledButtons(JMTCEnabledButtons enabledButtons);

    public abstract void setCallbacks(JMTCCallbacks callbacks);

    @SuppressWarnings("unused")
    public abstract JMTCParameters getParameters();

    @SuppressWarnings("unused")
    public abstract void setParameters(JMTCParameters parameters);

    public abstract void setTimelineProperties(JMTCTimelineProperties timelineProperties);

    public abstract void setPosition(Long position);

    public abstract void updateDisplay();

    @SuppressWarnings("unused")
    public abstract void resetDisplay();

    @SuppressWarnings("unused")
    public abstract JMTCMediaType getMediaType();

    public abstract void setMediaType(JMTCMediaType mediaType);

    @SuppressWarnings("unused")
    public abstract JMTCMediaProperties getMediaProperties();

    public abstract void setMediaProperties(JMTCMediaProperties mediaProperties);

}
