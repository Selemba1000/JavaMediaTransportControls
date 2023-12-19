package io.github.selemba1000;

/**
 * Class to group settings for JMTC.
 */
public class JMTCSettings {

    /**
     * Player name.
     * Only used on Linux. Used as the name for the DBus entry + Identity in mpris.
     */
    final String playerName;
    /**
     * Desktop file.
     * Only used on Linux. Used as the DesktopEntry in mpris. Should be the Name of the desktop file without file extension.
     */
    final String desktopFile;

    /**
     * Constructor for JMTCSettings.
     * @param playerName Player name.
     * @param desktopFile Desktop file.
     */
    public JMTCSettings(String playerName, String desktopFile) {
        this.playerName = playerName;
        this.desktopFile = desktopFile;
    }

}
