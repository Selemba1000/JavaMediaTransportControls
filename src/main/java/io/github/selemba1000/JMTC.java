package io.github.selemba1000;

import io.github.selemba1000.linux.LinuxJMTC;
import io.github.selemba1000.windows.WindowsJMTC;

public abstract class JMTC {

    protected static JMTC INSTANCE;

    protected JMTC() {
    }

    /**
     * Returns the singleton instance of JMTC.
     * Entry point for the library.
     * @apiNote Keep the Instance from getting GC'd
     * @param settings Settings for setting up the instance. Only used on first call.
     * @return JMTC
     */
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

    /**
     * Getter for the current PlyaingState.
     * @see JMTCPlayingState
     * @return JMTCPlayingState The current PlayingState.
     */
    @SuppressWarnings("unused")
    public abstract JMTCPlayingState getPlayingState();

    /**
     * Setter for the current PlayingState.
     * @see JMTCPlayingState
     * @param state The new PlayingState.
     */
    public abstract void setPlayingState(JMTCPlayingState state);

    /**
     * Getter for the Enabled State
     * @apiNote Only works on Windows
     * @return boolean The current Enabled State
     */
    @SuppressWarnings("unused")
    public abstract boolean getEnabled();

    /**
     * Setter for the Enabled State
     * @apiNote Only works on Windows
     * @param enabled The new Enabled State
     */
    public abstract void setEnabled(boolean enabled);

    /**
     * Getter for the EnabledButton configuration
     * @see JMTCEnabledButtons
     * @return JMTCEnabledButtons The current EnabledButton configuration
     */
    @SuppressWarnings("unused")
    public abstract JMTCEnabledButtons getEnabledButtons();

    /**
     * Setter for the EnabledButton configuration
     * @see JMTCEnabledButtons
     * @param enabledButtons The new EnabledButton configuration
     */
    public abstract void setEnabledButtons(JMTCEnabledButtons enabledButtons);

    /**
     * Sets the Callbacks for the JMTC instance.
     * Contains Play, Pause, Stop, Next, Previous, Seek, Shuffle, Loop, Volume and Rate callbacks.
     * @param callbacks The new Callbacks.
     */
    public abstract void setCallbacks(JMTCCallbacks callbacks);

    /**
     * Getter for the current Parameters.
     * @see JMTCParameters
     * @return JMTCParameters The current Parameters.
     */
    @SuppressWarnings("unused")
    public abstract JMTCParameters getParameters();

    /**
     * Setter for the current Parameters.
     * @see JMTCParameters
     * @param parameters The new Parameters.
     */
    @SuppressWarnings("unused")
    public abstract void setParameters(JMTCParameters parameters);

    /**
     * Setter for the current TimelineProperties.
     * @see JMTCTimelineProperties
     * @param timelineProperties The new TimelineProperties.
     */
    public abstract void setTimelineProperties(JMTCTimelineProperties timelineProperties);

    /**
     * Setter to update the current Position the Media.
     * @param position The new Position in milliseconds.
     */
    public abstract void setPosition(Long position);

    /**
     * Updates the System UI with the current values.
     * @apiNote Only needed on Windows
     */
    public abstract void updateDisplay();

    /**
     * Clears all values from the MediaProperties and updates the UI.
     */
    @SuppressWarnings("unused")
    public abstract void resetDisplay();

    /**
     * Getter for the current MediaType.
     * @see JMTCMediaType
     * @return JMTCMediaType The current MediaType.
     * @apiNote   Only Music is currently supported
     */
    @SuppressWarnings("unused")
    public abstract JMTCMediaType getMediaType();

    /**
     * Setter for the current MediaType.
     * @see JMTCMediaType
     * @param mediaType The new MediaType.
     * @apiNote   Only Music is currently supported
     */
    public abstract void setMediaType(JMTCMediaType mediaType);

    /**
     * Getter for the current MediaProperties.
     * @see JMTCMediaProperties
     * @return JMTCMediaProperties The current MediaProperties.
     */
    @SuppressWarnings("unused")
    public abstract JMTCMediaProperties getMediaProperties();

    /**
     * Setter for the current MediaProperties.
     * @see JMTCMediaProperties
     * @param mediaProperties The new MediaProperties.
     */
    public abstract void setMediaProperties(JMTCMediaProperties mediaProperties);

}
