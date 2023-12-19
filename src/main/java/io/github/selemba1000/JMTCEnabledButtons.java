package io.github.selemba1000;

/**
 * Class for storing enabled state of buttons.
 */
@SuppressWarnings("CanBeFinal")
public class JMTCEnabledButtons {

    /**
     * Play button enabled state.
     */
    public boolean isPlayEnabled;
    /**
     * Pause button enabled state.
     */
    public boolean isPauseEnabled;
    /**
     * Stop button enabled state.
     * @apiNote Ignored on Linux.
     */
    public boolean isStopEnabled;
    /**
     * Next button enabled state.
     */
    public boolean isNextEnabled;
    /**
     * Previous button enabled state.
     */
    public boolean isPreviousEnabled;

    /**
     * Constructor for JMTCEnabledButtons.
     * @param isPlayEnabled Play button enabled state.
     * @param isPauseEnabled Pause button enabled state.
     * @param isStopEnabled Stop button enabled state.
     * @param isNextEnabled Next button enabled state.
     * @param isPreviousEnabled Previous button enabled state.
     */
    public JMTCEnabledButtons(boolean isPlayEnabled, boolean isPauseEnabled, boolean isStopEnabled, boolean isNextEnabled, boolean isPreviousEnabled) {
        this.isPlayEnabled = isPlayEnabled;
        this.isPauseEnabled = isPauseEnabled;
        this.isStopEnabled = isStopEnabled;
        this.isNextEnabled = isNextEnabled;
        this.isPreviousEnabled = isPreviousEnabled;
    }
}
