package me.selemba.linux;

import me.selemba.JMTCParameters;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;

import java.util.Map;

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

    @SuppressWarnings("unused")
    void Previous();

    @SuppressWarnings("unused")
    void Next();

    @SuppressWarnings("unused")
    void Stop();

    @SuppressWarnings("unused")
    void Play();

    @SuppressWarnings("unused")
    void Pause();

    @SuppressWarnings("unused")
    void PlayPause();

    @SuppressWarnings("unused")
    void Seek(long _arg0);

    @SuppressWarnings("unused")
    void OpenUri(String _arg0);

    @SuppressWarnings("unused")
    void SetPosition(DBusPath _arg0, long _arg1);

    enum LoopStatus {
        None("None"),
        Track("Track"),
        Playlist("Playlist");
        public final String value;

        LoopStatus(String value) {
            this.value = value;
        }

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

    @SuppressWarnings("unused")
    class Seeked extends DBusSignal {
        private final long timeInUs;

        public Seeked(String _path, long _timeInUs) throws DBusException {
            super(_path);
            timeInUs = _timeInUs;
        }

        public long getTimeInUs() {
            return timeInUs;
        }

    }
}
