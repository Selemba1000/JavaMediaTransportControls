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

import java.util.HashMap;
import java.util.Map;

@DBusInterfaceName("org.mpris.MediaPlayer2.Player")
@DBusProperty(name = "Metadata", type = Map.class, access = Access.READ)
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

    enum PlaybackStatus{
        Playing("Playing"),
        Stopped("Stopped"),
        Paused("Paused");
        public final String value;
        PlaybackStatus(String value){
            this.value = value;
        }
    }

    enum LoopStatus{
        None("None"),
        Track("Track"),
        Playlist("Playlist");
        public final String value;
        LoopStatus(String value){
            this.value = value;
        }
    }

    class Metadata{
        public String MPRIS_TrackId = "/me/selemba/free-danza/1";
        public Long MPRIS_Length = 0L;
        public String MPRIS_ArtUrl = "";
        public String XESAM_Album = "";
        public String[] XESAM_AlbumArtist = {};
        public String[] XESAM_Artist = {};
        public String XESAM_Title = "";
        public Integer XESAM_TrackNumber = 0;

        public Map<String,Variant<?>> toMap(){
            HashMap<String,Variant<?>> map = new HashMap<>(
                    Map.of(
                            "mpris:trackid", new Variant<>(this.MPRIS_TrackId),
                            "mpris:length", new Variant<Long>(this.MPRIS_Length),
                            "xesam:album", new Variant<>(this.XESAM_Album),
                            "xesam:albumArtist", new Variant<>(this.XESAM_AlbumArtist),
                            "xesam:artist", new Variant<>(this.XESAM_Artist),
                            "xesam:title", new Variant<>(this.XESAM_Title),
                            "xesam:trackNumber", new Variant<>(this.XESAM_TrackNumber)
                    )
            );
            if(!this.MPRIS_ArtUrl.isEmpty()){
                map.put("mpris:artUrl", new Variant<>(this.MPRIS_ArtUrl));
            }
            return map;
        }

        public HashMap<String,Variant<?>> toMutableMap(){
            HashMap<String, Variant<?>> map = new HashMap<>(this.toMap());
            return map;
        }

    }

}
