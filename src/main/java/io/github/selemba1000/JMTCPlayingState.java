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
    PLAYING,
    PAUSED,
    STOPPED,
    CLOSED,
    CHANGING,

}
