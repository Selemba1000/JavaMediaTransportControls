package io.github.selemba1000;

/**
 * Enum to store the current state of the player.
 * <p>
 * Playing = the player is playing a file.
 * <p>
 * Paused = the player is paused.
 * <p>
 * Stopped = the player is stopped. Windows only
 * <p>
 * Closed = the player is closed. Windows only
 * <p>
 * Changing = the player is changing the file. Windows only
 */
public enum JMTCPlayingState {
    /**
     * Media is currently playing
     */
    PLAYING,
    /**
     * Media is currently paused
     */
    PAUSED,
    /**
     * Media is stopped. Usually when media was selected, but not started.
     */
    STOPPED,
    /**
     * Currently no media loaded.
     */
    CLOSED,
    /**
     * The Media is currently changing/loading.
     */
    CHANGING,
}
