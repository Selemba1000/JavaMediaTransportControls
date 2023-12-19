package me.selemba;

@SuppressWarnings("CanBeFinal")
public class JMTCEnabledButtons {

    public boolean isPlayEnabled;
    public boolean isPauseEnabled;
    public boolean isStopEnabled;
    public boolean isNextEnabled;
    public boolean isPreviousEnabled;

    public JMTCEnabledButtons(boolean isPlayEnabled, boolean isPauseEnabled, boolean isStopEnabled, boolean isNextEnabled, boolean isPreviousEnabled) {
        this.isPlayEnabled = isPlayEnabled;
        this.isPauseEnabled = isPauseEnabled;
        this.isStopEnabled = isStopEnabled;
        this.isNextEnabled = isNextEnabled;
        this.isPreviousEnabled = isPreviousEnabled;
    }
}
