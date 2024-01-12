package io.github.selemba1000.linux;

import io.github.selemba1000.*;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.interfaces.Properties;
import org.freedesktop.dbus.types.Variant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LinuxJMTC extends JMTC implements MPRISPlayer2, MPRISPlayer2Player, Properties {

    private static final String generalInterface = "org.mpris.MediaPlayer2";
    private static final String playerInterface = "org.mpris.MediaPlayer2.Player";
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> CanQuit = new SignaledDBusProperty<>(false, "CanQuit", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> Fullscreen = new SignaledDBusProperty<>(false, "Fullscreen", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> CanSetFullscreen = new SignaledDBusProperty<>(false, "CanSetFullscreen", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> CanRaise = new SignaledDBusProperty<>(false, "CanRaise", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> HasTrackList = new SignaledDBusProperty<>(false, "HasTrackList", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<String> Identity = new SignaledDBusProperty<>("free-danza", "Identity", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<String> DesktopEntry = new SignaledDBusProperty<>("brave-browser", "DesktopEntry", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<String[]> SupportedUriSchemes = new SignaledDBusProperty<>(new String[]{"danza"}, "SupportedUriSchemes", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<String[]> SupportedMimeTypes = new SignaledDBusProperty<>(new String[]{}, "SupportedMimeTypes", getObjectPath(), generalInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<String> PlaybackStatus = new SignaledDBusProperty<>("Stopped", "PlaybackStatus", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<String> LoopStatus = new SignaledDBusProperty<>("None", "LoopStatus", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Double> Rate = new SignaledDBusProperty<>(1.0, "Rate", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> Shuffle = new SignaledDBusProperty<>(false, "Shuffle", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusPropertyMap<Object> Metadata = new SignaledDBusPropertyMap<>("Metadata", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Double> Volume = new SignaledDBusProperty<>(1.0, "Volume", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final DBusProperty<Long> Position = new DBusProperty<>(0L, "Position");
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Double> MaximumRate = new SignaledDBusProperty<>(1.0, "MaximumRate", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Double> MinimumRate = new SignaledDBusProperty<>(1.0, "MinimumRate", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> CanGoNext = new SignaledDBusProperty<>(false, "CanGoNext", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> CanGoPrevious = new SignaledDBusProperty<>(false, "CanGoPrevious", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> CanPlay = new SignaledDBusProperty<>(true, "CanPlay", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> CanPause = new SignaledDBusProperty<>(true, "CanPause", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final SignaledDBusProperty<Boolean> CanSeek = new SignaledDBusProperty<>(true, "CanSeek", getObjectPath(), playerInterface);
    @SuppressWarnings("unused")
    final DBusProperty<Boolean> CanControl = new DBusProperty<>(true, "CanControl");
    protected JMTCCallbacks callbacks;
    private DBusConnection connection;

    private Boolean enabled = false;

    private String playerName;

    public LinuxJMTC(String playerName, String desktopFile) {
        try {
            this.playerName = playerName;
            connection = DBusConnectionBuilder.forSessionBus().build();
            //connection.requestBusName("org.mpris.MediaPlayer2." + playerName);
            //connection.exportObject("/org/mpris/MediaPlayer2", this);
        } catch (Exception e) {
            System.out.println("conn failed: " + e.getMessage());
        }

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Arrays.asList(field.getType().getInterfaces()).contains(SignaledDBusPropertyInterface.class)) {
                try {
                    ((SignaledDBusPropertyInterface) field.get(this)).ProvideConnection(connection);
                    ((SignaledDBusPropertyInterface) field.get(this)).signal();
                } catch (Exception e) {
                    //TODO logging
                }
            }
        }

        Identity.setValue(playerName);
        DesktopEntry.setValue(desktopFile);
    }

    @Override
    public JMTCPlayingState getPlayingState() {
        switch (PlaybackStatus.getValue()) {
            case "Playing":
                return JMTCPlayingState.PLAYING;
            case "Paused":
                return JMTCPlayingState.PAUSED;
            default:
                return JMTCPlayingState.STOPPED;
        }
    }

    @Override
    public void setPlayingState(JMTCPlayingState state) {
        switch (state) {
            case PLAYING:
                PlaybackStatus.setValue("Playing");
                break;
            case PAUSED:
                PlaybackStatus.setValue("Paused");
                break;
            case STOPPED:
                PlaybackStatus.setValue("Stopped");
                break;
        }
    }

    @Override
    public boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if(enabled && !this.enabled){
            try {
                connection.requestBusName("org.mpris.MediaPlayer2." + playerName);
                connection.exportObject("/org/mpris/MediaPlayer2", this);
                this.enabled = true;
            }catch (Exception e){
                //TODO logging
            }
        }else if(this.enabled){
            try {
                connection.releaseBusName("org.mpris.MediaPlayer2." + playerName);
                this.enabled = false;
            }catch (Exception e){
                //TODO logging
            }
        }
    }

    @Override
    public JMTCEnabledButtons getEnabledButtons() {
        return new JMTCEnabledButtons(
                CanPlay.getValue(),
                CanPause.getValue(),
                false,
                CanGoNext.getValue(),
                CanGoPrevious.getValue()
        );
    }

    @Override
    public void setEnabledButtons(JMTCEnabledButtons enabledButtons) {
        CanPlay.setValue(enabledButtons.isPlayEnabled);
        CanPause.setValue(enabledButtons.isPauseEnabled);
        CanGoNext.setValue(enabledButtons.isNextEnabled);
        CanGoPrevious.setValue(enabledButtons.isPreviousEnabled);
    }

    @Override
    public JMTCParameters getParameters() {
        return new JMTCParameters(
                MPRISPlayer2Player.LoopStatus.valueOf(LoopStatus.getValue()).toOuter(),
                Volume.getValue(),
                Rate.getValue(),
                Shuffle.getValue()
        );
    }

    public void setParameters(JMTCParameters parameters) {
        LoopStatus.setValue(MPRISPlayer2Player.LoopStatus.fromInner(parameters.loopStatus).value);
        Volume.setValue(parameters.volume);
        Rate.setValue(parameters.rate);
        Shuffle.setValue(parameters.shuffle);
    }

    @Override
    public void setCallbacks(JMTCCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void setTimelineProperties(JMTCTimelineProperties timelineProperties) {
        Metadata.setAt("mpris:length", timelineProperties.end - timelineProperties.start);
    }

    @Override
    public void setPosition(Long position) {
        Position.setValue(position);
    }

    @Override
    public void updateDisplay() {
    }

    @Override
    public void resetDisplay() {
        Metadata.clear();
    }

    @Override
    public JMTCMediaType getMediaType() {
        return JMTCMediaType.Music;
    }

    @Override
    public void setMediaType(JMTCMediaType mediaType) {
    }

    @Override
    public JMTCMediaProperties getMediaProperties() {
        return new JMTCMusicProperties(
                (String) Metadata.getAt("xesam:title"),
                Arrays.toString((String[]) Metadata.getAt("xesam:artist")).replace("[", "").replace("]", ""),
                (String) Metadata.getAt("xesam:album"),
                Arrays.toString((String[]) Metadata.getAt("xesam:albumArtist")),
                new String[]{},
                0,
                (Integer) Metadata.getAt("xesam:trackNumber"),
                null
        );
    }

    @Override
    public void setMediaProperties(JMTCMediaProperties mediaProperties) {
        if (mediaProperties.getClass() == JMTCMusicProperties.class) {
            Metadata.setAll(Map.of(
                    "xesam:title", ((JMTCMusicProperties) mediaProperties).title,
                    "xesam:artist", ((JMTCMusicProperties) mediaProperties).artist.split(","),
                    "xesam:album", ((JMTCMusicProperties) mediaProperties).albumTitle,
                    "xesam:albumArtist", ((JMTCMusicProperties) mediaProperties).albumArtist.split(","),
                    "xesam:trackNumber", ((JMTCMusicProperties) mediaProperties).track,
                    "mpris:trackid", "/io/github/selemba1000/std"
            ));
            if (((JMTCMusicProperties) mediaProperties).art != null) {
                Metadata.setAt(
                        "mpris:artUrl", ((JMTCMusicProperties) mediaProperties).art.toURI().toString()
                );
            }
        }
    }

    @Override
    public void Raise() {

    }

    @Override
    public void Quit() {

    }

    @Override
    public void Previous() {
        if (callbacks != null && callbacks.onPrevious != null) {
            callbacks.onPrevious.callback();
        }
    }

    @Override
    public void Next() {
        if (callbacks != null && callbacks.onNext != null) {
            callbacks.onNext.callback();
        }
    }

    @Override
    public void Stop() {
        if (callbacks != null && callbacks.onStop != null) {
            callbacks.onStop.callback();
        }
    }

    @Override
    public void Play() {
        if (callbacks != null && callbacks.onPlay != null) {
            callbacks.onPlay.callback();
        }
    }

    @Override
    public void Pause() {
        if (callbacks != null && callbacks.onPause != null) {
            callbacks.onPause.callback();
        }
    }

    @Override
    public void PlayPause() {
        if (callbacks != null && callbacks.onPlay != null && callbacks.onPause != null) {
            if (Objects.equals(PlaybackStatus.getValue(), "Playing")) callbacks.onPause.callback();
            else callbacks.onPlay.callback();
        }
    }

    @Override
    public void Seek(long _arg0) {
        if (callbacks != null && callbacks.onSeek != null) {
            callbacks.onSeek.callback(_arg0 + Position.getValue());
        }
    }

    @Override
    public void OpenUri(String _arg0) {

    }

    @Override
    public void SetPosition(DBusPath _arg0, long _arg1) {
        if (callbacks != null && callbacks.onSeek != null) {
            callbacks.onSeek.callback(_arg1);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> A Get(String _interfaceName, String _propertyName) {
        A ret = null;
        try {
            ret = (A) ((DBusPropertyInterface) this.getClass().getDeclaredField(_propertyName).get(this)).getVariantMap().get(_propertyName);
        } catch (Exception e) {
            //TODO logging
        }
        System.out.println(ret);
        return ret;
    }

    @Override
    public <A> void Set(String _interfaceName, String _propertyName, A _value) {
        switch (_propertyName) {
            case "LoopStatus":
                //properties.LoopStatus = MPRISPlayer2Player.LoopStatus.valueOf((String) _value);
                callbacks.onLoop.callback(MPRISPlayer2Player.LoopStatus.valueOf((String) _value).toOuter());
                break;
            case "Rate":
                //properties.Rate = (Double) _value;
                callbacks.onRate.callback((Double) _value);
                break;
            case "Shuffle":
                //properties.Shuffle = (boolean) _value;
                callbacks.onShuffle.callback((Boolean) _value);
                break;
            case "Volume":
                //properties.Volume = (Double) _value;
                callbacks.onVolume.callback((Double) _value);
                break;
        }
    }

    @Override
    public Map<String, Variant<?>> GetAll(String _interfaceName) {

        Map<String, Variant<?>> map = new HashMap<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                if (field.get(this) instanceof DBusProperty) {
                    map.putAll(((DBusProperty<?>) field.get(this)).getVariantMap());
                }
            } catch (IllegalAccessException e) {
                //TODO logging
            }
        }

        return map;
    }

    @Override
    public String getObjectPath() {
        return "/org/mpris/MediaPlayer2";
    }

}
