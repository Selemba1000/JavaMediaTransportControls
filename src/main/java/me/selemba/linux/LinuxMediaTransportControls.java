package me.selemba.linux;

import me.selemba.*;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder;
import org.freedesktop.dbus.interfaces.Properties;
import org.freedesktop.dbus.types.Variant;

import java.lang.reflect.Field;
import java.util.*;

public class LinuxMediaTransportControls extends MediaTransportControls implements MPRISPlayer2, MPRISPlayer2Player, Properties {

    private static final String generalInterface = "org.mpris.MediaPlayer2";
    private static final String playerInterface = "org.mpris.MediaPlayer2.Player";

    public final PlayerProperties properties;

    public DBusConnection connection;

    public LinuxMediaTransportControls(String playerName, String desktopFile) {
        properties = new PlayerProperties(playerName, desktopFile);
        try {
            connection = DBusConnectionBuilder.forSessionBus().build();
            connection.requestBusName("org.mpris.MediaPlayer2."+playerName);
            connection.exportObject("/org/mpris/MediaPlayer2",this);
        }catch (Exception e){
            System.out.println("conn failed: "+e.getMessage());
        }

        Field[] fields = this.getClass().getDeclaredFields();

        HashMap<String,Variant<?>> map = new HashMap<>();

        for (Field field : fields){
            if (Arrays.asList(field.getType().getInterfaces()).contains(SignaledDBusPropertyInterface.class)){
                try {
                    ((SignaledDBusPropertyInterface) field.get(this)).ProvideConnection(connection);
                    ((SignaledDBusPropertyInterface) field.get(this)).signal();
                    map.putAll(((SignaledDBusPropertyInterface) field.get(this)).getVariantMap());
                }catch (Exception e){
                    //TODO logging
                }
            }
        }
    }

    @Override
    public MediaTransportControlsPlayingState getPlayingState() {
        switch (PlaybackStatus.getValue()){
            case "Playing":
                return MediaTransportControlsPlayingState.PLAYING;
            case "Paused":
                return MediaTransportControlsPlayingState.PAUSED;
            case "Stopped":
                return MediaTransportControlsPlayingState.CLOSED;
            default:
                return MediaTransportControlsPlayingState.STOPPED;
        }
    }

    @Override
    public void setPlayingState(MediaTransportControlsPlayingState state) {
        switch (state){
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
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public MediaTransportControlsEnabledButtons getEnabledButtons() {
        return new MediaTransportControlsEnabledButtons(
                CanPlay.getValue(),
                CanPause.getValue(),
                false,
                CanGoNext.getValue(),
                CanGoPrevious.getValue()
        );
    }

    @Override
    public void setEnabledButtons(MediaTransportControlsEnabledButtons enabledButtons) {
        CanPlay.setValue(enabledButtons.isPlayEnabled);
        CanPause.setValue(enabledButtons.isPauseEnabled);
        CanGoNext.setValue(enabledButtons.isNextEnabled);
        CanGoPrevious.setValue(enabledButtons.isPreviousEnabled);
    }

    @Override
    public MediaTransportControlsParameters getParameters(){
        return new MediaTransportControlsParameters(
                properties.LoopStatus.toOuter(),
                properties.Volume,
                properties.Rate,
                properties.Shuffle
        );
    }

    public void setParameters(MediaTransportControlsParameters parameters){
        properties.LoopStatus = parameters.loopStatus.toInner();
        properties.Volume = parameters.volume;
        properties.Rate = parameters.rate;
        properties.Shuffle = parameters.shuffle;
    }

    protected MediaTransportControlsCallbacks callbacks;

    @Override
    public void setCallbacks(MediaTransportControlsCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void setTimelineProperties(MediaTransportControlsTimelineProperties timelineProperties) {
        Metadata.setAt("mpris:length", timelineProperties.end - timelineProperties.start);
    }

    @Override
    public void setPosition(Long position) {
        Position.setValue(position);
    }

    @Override
    public void updateDisplay() {}

    @Override
    public void resetDisplay() {
        Metadata.clear();
    }

    @Override
    public MediaTransportControlsMediaType getMediaType() {
        return MediaTransportControlsMediaType.Music;
    }

    @Override
    public void setMediaType(MediaTransportControlsMediaType mediaType) {}

    @Override
    public MediaTransportControlsMediaProperties getMediaProperties() {
        return new MediaTransportControlsMusicProperties(
                (String) Metadata.getAt("xesam:title"),
                Arrays.toString((String[])Metadata.getAt("xesam:artist")),
                (String) Metadata.getAt("xesam:album"),
                Arrays.toString((String[])Metadata.getAt("xesam:albumArtist")),
                new String[]{},
                0,
                (Integer) Metadata.getAt("xesam:trackNumber"),
                null
        );
    }

    @Override
    public void setMediaProperties(MediaTransportControlsMediaProperties mediaProperties) {
        if(mediaProperties.getClass() == MediaTransportControlsMusicProperties.class) {
            Metadata.setAll(Map.of(
                    "xesam:title",((MediaTransportControlsMusicProperties) mediaProperties).title,
                    "xesam:artist",((MediaTransportControlsMusicProperties) mediaProperties).artist.split(","),
                    "xesam:album",((MediaTransportControlsMusicProperties) mediaProperties).albumTitle,
                    "xesam:albumArtist",((MediaTransportControlsMusicProperties) mediaProperties).albumArtist.split(","),
                    "xesam:trackNumber",((MediaTransportControlsMusicProperties) mediaProperties).track,
                    "mpris:trackid","/me/selemba/std"
            ));
            if(((MediaTransportControlsMusicProperties) mediaProperties).art!=null){
                System.out.println(((MediaTransportControlsMusicProperties) mediaProperties).art.toURI().toString());
                Metadata.setAt(
                        "mpris:artUrl",((MediaTransportControlsMusicProperties) mediaProperties).art.toURI().toString()
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
        if (callbacks!=null&&callbacks.onPrevious!=null){
            callbacks.onPrevious.callback();
        }
    }

    @Override
    public void Next() {
        if (callbacks!=null&&callbacks.onNext!=null){
            callbacks.onNext.callback();
        }
    }

    @Override
    public void Stop() {
        if (callbacks!=null&&callbacks.onStop!=null){
            callbacks.onStop.callback();
        }
    }

    @Override
    public void Play() {
        if (callbacks!=null&&callbacks.onPlay!=null){
            callbacks.onPlay.callback();
        }
    }

    @Override
    public void Pause() {
        if (callbacks!=null&&callbacks.onPause!=null){
            callbacks.onPause.callback();
        }
    }

    @Override
    public void PlayPause() {
        if (callbacks!=null&&callbacks.onPlay!=null&&callbacks.onPause!=null){
            if(Objects.equals(PlaybackStatus.getValue(), "Playing")) callbacks.onPause.callback();
            else callbacks.onPlay.callback();
        }
    }

    @Override
    public void Seek(long _arg0) {
        if (callbacks!=null&&callbacks.onSeek!=null){
            callbacks.onSeek.callback(_arg0 + properties.Position);
        }
    }

    @Override
    public void OpenUri(String _arg0) {

    }

    @Override
    public void SetPosition(DBusPath _arg0, long _arg1) {
        if (callbacks!=null&&callbacks.onSeek!=null){
            callbacks.onSeek.callback(_arg1);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A> A Get(String _interfaceName, String _propertyName) {
        A ret = null;
        try {
            ret = (A) ((DBusPropertyInterface)this.getClass().getDeclaredField(_propertyName).get(this)).getVariantMap().get(_propertyName);
        } catch (Exception e){
            //TODO logging
        }
        System.out.println(ret);
        return ret;
    }

    @Override
    public <A> void Set(String _interfaceName, String _propertyName, A _value) {
        switch (_propertyName){
            case "LoopStatus":
                //properties.LoopStatus = MPRISPlayer2Player.LoopStatus.valueOf((String) _value);
                callbacks.onLoop.callback(MPRISPlayer2Player.LoopStatus.valueOf( (String)_value).toOuter() );
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
        return properties.toMap();
    }

    @Override
    public String getObjectPath() {
        return "/org/mpris/MediaPlayer2";
    }

    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> CanQuit = new SignaledDBusProperty<>(false,"CanQuit",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> Fullscreen = new SignaledDBusProperty<>(false,"Fullscreen",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> CanSetFullscreen = new SignaledDBusProperty<>(false,"CanSetFullscreen",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> CanRaise = new SignaledDBusProperty<>(false,"CanRaise",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> HasTrackList = new SignaledDBusProperty<>(false,"HasTrackList",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<String> Identity = new SignaledDBusProperty<>("free-danza","Identity",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<String> DesktopEntry = new SignaledDBusProperty<>("brave-browser","DesktopEntry",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<String[]> SupportedUriSchemes = new SignaledDBusProperty<>(new String[]{"danza"},"SupportedUriSchemes",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<String[]> SupportedMimeTypes = new SignaledDBusProperty<>(new String[]{},"SupportedMimeTypes",getObjectPath(),generalInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<String> PlaybackStatus = new SignaledDBusProperty<>("Stopped","PlaybackStatus",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<String> LoopStatus = new SignaledDBusProperty<>("None","LoopStatus",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Double> Rate = new SignaledDBusProperty<>(1.0,"Rate",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> Shuffle = new SignaledDBusProperty<>(false,"Shuffle",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusPropertyMap<Object> Metadata = new SignaledDBusPropertyMap<>("Metadata",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Double> Volume = new SignaledDBusProperty<>(1.0,"Volume",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    DBusProperty<Long> Position = new DBusProperty<>(0L,"Position");
    @SuppressWarnings("unused")
    SignaledDBusProperty<Double> MaximumRate = new SignaledDBusProperty<>(1.0,"MaximumRate",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Double> MinimumRate = new SignaledDBusProperty<>(1.0,"MinimumRate",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> CanGoNext = new SignaledDBusProperty<>(false,"CanGoNext",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> CanGoPrevious = new SignaledDBusProperty<>(false,"CanGoPrevious",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> CanPlay = new SignaledDBusProperty<>(true,"CanPlay",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> CanPause = new SignaledDBusProperty<>(true,"CanPause",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    SignaledDBusProperty<Boolean> CanSeek = new SignaledDBusProperty<>(true,"CanSeek",getObjectPath(),playerInterface);
    @SuppressWarnings("unused")
    DBusProperty<Boolean> CanControl = new DBusProperty<>(true,"CanControl");


    public static class PlayerProperties{
        boolean Fullscreen = false;
        boolean CanSetFullscreen = false;
        boolean CanRaise = false;
        boolean HasTrackList = false;
        String Identity;
        String DesktopEntry;
        String[] SupportedUriSchemes = {""};
        String[] SupportedMimeTypes = {""};

        //Player Interface

        PlaybackStatus PlaybackStatus = MPRISPlayer2Player.PlaybackStatus.Stopped;
        LoopStatus LoopStatus = MPRISPlayer2Player.LoopStatus.None;
        Double Rate = 1.0;
        boolean Shuffle = false;
        Metadata Metadata = new Metadata();
        double Volume = 1.0;
        Long Position = 0L;
        Double MinimumRate = 1.0;
        Double MaximumRate = 1.0;
        boolean CanGoNext = false;
        boolean CanGoPrevious = false;
        boolean CanPlay = true;
        boolean CanPause = true;
        boolean CanSeek = true;
        boolean CanControl = true;

        public PlayerProperties(String identity, String desktopEntry) {
            Identity = identity;
            DesktopEntry = desktopEntry;
        }

        public Map<String, Variant<?>> toMap(){
            HashMap<String,Variant<?>> map = new HashMap<>();
            //map.put("CanQuit",new Variant<>(this.CanQuit));
            map.put("Fullscreen", new Variant<>(this.Fullscreen));
            map.put("CanSetFullscreen", new Variant<>(this.CanSetFullscreen));
            map.put("CanRaise", new Variant<>(this.CanRaise));
            map.put("HasTrackList", new Variant<>(this.HasTrackList));
            map.put("Identity", new Variant<>(this.Identity));
            map.put("DesktopEntry", new Variant<>(this.DesktopEntry));
            map.put("SupportedUriSchemes", new Variant<>(this.SupportedUriSchemes));
            map.put("SupportedMimeTypes", new Variant<>(this.SupportedMimeTypes));
            map.put("PlaybackStatus", new Variant<>(this.PlaybackStatus));
            map.put("LoopStatus", new Variant<>(this.LoopStatus));
            map.put("Rate", new Variant<>(this.Rate));
            map.put("Shuffle", new Variant<>(this.Shuffle));
            map.put("Metadata", new Variant<>(this.Metadata.toMap(), "a{sv}"));
            map.put("Volume", new Variant<>(this.Volume));
            map.put("Position", new Variant<>(this.Position));
            map.put("MinimumRate", new Variant<>(this.MinimumRate));
            map.put("MaximumRate", new Variant<>(this.MaximumRate));
            map.put("CanGoNext", new Variant<>(this.CanGoNext));
            map.put("CanGoPrevious", new Variant<>(this.CanGoPrevious));
            map.put("CanPlay", new Variant<>(this.CanPlay));
            map.put("CanPause", new Variant<>(this.CanPause));
            map.put("CanSeek", new Variant<>(this.CanSeek));
            map.put("CanControl", new Variant<>(this.CanControl));
            return map;
        }

    }

}
