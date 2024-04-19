package io.github.selemba1000.linux;

import io.github.selemba1000.JMTCParameters;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;

import java.util.Map;

/**
 * DBus reference for MPRIS Player
 */
@DBusInterfaceName("org.mpris.MediaPlayer2.Player")
@DBusProperty(name = "Metadata", type = Map.class, access = Access.READ)
@DBusProperty(name = "PlaybackStatus", type = String.class, access = Access.READ)
@DBusProperty(name = "LoopStatus", type = String.class)
@DBusProperty(name = "Volume", type = Double.class)
@DBusProperty(name = "Shuffle", type = Double.class)
@DBusProperty(name = "Position", type = Integer.class, access = Access.READ)
@DBusProperty(name = "Rate", type = Double.class)
@DBusProperty(name = "MinimumRate", type = Double.class)
@DBusProperty(name = "MaximumRate", type = Double.class)
@DBusProperty(name = "CanControl", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanPlay", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanPause", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanSeek", type = Boolean.class, access = Access.READ)
public interface MPRISPlayer2Player extends DBusInterface {

    /**
     * Skip to the track before the currently playing one.
     */
    @SuppressWarnings("unused")
    void Previous();

    /**
     * Skip to the Song after the currently playing one.
     */
    @SuppressWarnings("unused")
    void Next();

    /**
     * Stop playback.
     */
    @SuppressWarnings("unused")
    void Stop();

    /**
     * Start playback.
     */
    @SuppressWarnings("unused")
    void Play();

    /**
     * Pause playback.
     */
    @SuppressWarnings("unused")
    void Pause();

    /**
     * Start and stop playback. Stop if currently playing. Start if currently paused.
     */
    @SuppressWarnings("unused")
    void PlayPause();

    /**
     * Seek to specified position.
     *
     * @param _arg0 position to seek to
     */
    @SuppressWarnings("unused")
    void Seek(long _arg0);

    /**
     * Open and play the specified uri.
     *
     * @param _arg0 The uri to play
     */
    @SuppressWarnings("unused")
    void OpenUri(String _arg0);

    /**
     * Jump to the specified Position in the specified song.
     *
     * @param _arg0 Song specified by path
     * @param _arg1 Position to jump to
     */
    @SuppressWarnings("unused")
    void SetPosition(DBusPath _arg0, long _arg1);

    /**
     * Enum for the Loop status conversions.
     */
    enum LoopStatus {
        /**
         * No repeating.
         */
        None("None"),
        /**
         * Repeat the current Track.
         */
        Track("Track"),
        /**
         * Repeat the current playlist
         */
        Playlist("Playlist");

        /**
         * THe string representation of the status.
         */
        public final String value;

        /**
         * Set a loop status via string representation
         * @param value
         */
        LoopStatus(String value) {
            this.value = value;
        }

        /**
         * Convert generalised status to linux specific version.
         * @param status The general status to be converted
         * @return The corresponding linux status
         */
        static LoopStatus fromInner(JMTCParameters.LoopStatus status) {
            switch (status) {
                case None:
                    return MPRISPlayer2Player.LoopStatus.None;
                case Track:
                    return MPRISPlayer2Player.LoopStatus.Track;
                case Playlist:
                    return MPRISPlayer2Player.LoopStatus.Playlist;
                default:
                    throw new IllegalStateException();
            }
        }

        /**
         * Convert this status to the generalised version.
         * @return Generalised status
         */
        JMTCParameters.LoopStatus toOuter() {
            switch (this) {
                case None:
                    return JMTCParameters.LoopStatus.None;
                case Track:
                    return JMTCParameters.LoopStatus.Track;
                case Playlist:
                    return JMTCParameters.LoopStatus.Playlist;
            }
            throw new IllegalStateException();
        }

    }

    /**
     * The DBusSignal emitted by using the seek feature.
     */
    @SuppressWarnings("unused")
    class Seeked extends DBusSignal {
        private final long timeInUs;

        /**
         * THe constructor to create the Seeked signal,
         * @param _path the path to the relevant track
         * @param _timeInUs the time in us to seek
         * @throws DBusException if DBus is not working properly
         */
        public Seeked(String _path, long _timeInUs) throws DBusException {
            super(_path);
            timeInUs = _timeInUs;
        }

        /**
         * Getter for the seeked time.
         * @return The seeked TIme
         */
        public long getTimeInUs() {
            return timeInUs;
        }

    }
}
