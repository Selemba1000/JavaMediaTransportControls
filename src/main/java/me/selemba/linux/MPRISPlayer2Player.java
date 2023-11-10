package me.selemba.linux;

import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.TypeRef;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.Variant;

import java.util.Map;

@DBusInterfaceName("org.mpris.MediaPlayer2.Player")
@DBusProperty(name = "Metadata", type = MPRISPlayer2Player.PropertyMetadataType.class, access = Access.READ)
@DBusProperty(name = "PlaybackStatus", type = String.class, access = Access.READ)
@DBusProperty(name = "LoopStatus", type = String.class, access = Access.READ_WRITE)
@DBusProperty(name = "Volume", type = Double.class, access = Access.READ_WRITE)
@DBusProperty(name = "Shuffle", type = Double.class, access = Access.READ_WRITE)
@DBusProperty(name = "Position", type = Integer.class, access = Access.READ)
@DBusProperty(name = "Rate", type = Double.class, access = Access.READ_WRITE)
@DBusProperty(name = "MinimumRate", type = Double.class, access = Access.READ_WRITE)
@DBusProperty(name = "MaximumRate", type = Double.class, access = Access.READ_WRITE)
@DBusProperty(name = "CanControl", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanPlay", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanPause", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanSeek", type = Boolean.class, access = Access.READ)
public interface MPRISPlayer2Player extends DBusInterface {

    void Previous();

    void Next();

    void Stop();

    void Play();

    void Pause();

    void PlayPause();

    void Seek(long _arg0);

    void OpenUri(String _arg0);

    void SetPosition(DBusPath _arg0, long _arg1);

    interface PropertyMetadataType extends TypeRef<Map<String, Variant<?>>> {

    }

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
