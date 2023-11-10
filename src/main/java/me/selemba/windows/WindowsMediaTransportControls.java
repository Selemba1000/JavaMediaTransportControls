package me.selemba.windows;

import com.sun.jna.Native;
import com.sun.jna.WString;
import me.selemba.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WindowsMediaTransportControls extends MediaTransportControls {

    private final SMTCAdapter lib = Native.load("SMTCAdapter", SMTCAdapter.class);

    public WindowsMediaTransportControls(){
        super();
        lib.init();
    }

    @Override
    public MediaTransportControlsPlayingState getPlayingState(){
        int res = lib.getPlaybackState();
        switch (res){
            case 0:
                return MediaTransportControlsPlayingState.CLOSED;
            case 1:
                return MediaTransportControlsPlayingState.PAUSED;
            case 2:
                return MediaTransportControlsPlayingState.STOPPED;
            case 3:
                return MediaTransportControlsPlayingState.PLAYING;
            case 4:
                return MediaTransportControlsPlayingState.CHANGING;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void setPlayingState(MediaTransportControlsPlayingState state){
        switch (state){
            case CLOSED:
                lib.setPlaybackState(0);
            case PAUSED:
                lib.setPlaybackState(1);
            case STOPPED:
                lib.setPlaybackState(2);
            case PLAYING:
                lib.setPlaybackState(3);
            case CHANGING:
                lib.setPlaybackState(4);
        }
    }

    @Override
    public boolean getEnabled() {
        return lib.getEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        lib.setEnabled(enabled);
    }

    @Override
    public MediaTransportControlsEnabledButtons getEnabledButtons() {
        return new MediaTransportControlsEnabledButtons(
                lib.getPlayEnabled(),
                lib.getPauseEnabled(),
                lib.getStopEnabled(),
                lib.getNextEnabled(),
                lib.getPreviousEnabled()
        );
    }

    @Override
    public void setEnabledButtons(MediaTransportControlsEnabledButtons enabledButtons) {
        lib.setPlayEnabled(enabledButtons.isPlayEnabled);
        lib.setPauseEnabled(enabledButtons.isPauseEnabled);
        lib.setStopEnabled(enabledButtons.isStopEnabled);
        lib.setNextEnabled(enabledButtons.isNextEnabled);
        lib.setPreviousEnabled(enabledButtons.isPreviousEnabled);
    }

    @Override
    public void setCallbacks(MediaTransportControlsCallbacks callbacks) {
        lib.setOnPlay(new ButtonPressedCallback(callbacks.onPlay));
        lib.setOnPause(new ButtonPressedCallback(callbacks.onPause));
        lib.setOnStop(new ButtonPressedCallback(callbacks.onStop));
        lib.setOnNext(new ButtonPressedCallback(callbacks.onNext));
        lib.setOnPrevious(new ButtonPressedCallback(callbacks.onPrevious));
    }

    @Override
    public void setTimelineProperties(MediaTransportControlsTimelineProperties timelineProperties) {
        lib.setTimelineProperties(timelineProperties.start, timelineProperties.end, timelineProperties.seekStart, timelineProperties.seekEnd);
    }

    @Override
    public void setPosition(Long position) {
        lib.setPosition(position);
    }

    @Override
    public void updateDisplay() {
        lib.update();
    }

    @Override
    public void resetDisplay() {
        lib.reset();
    }

    @Override
    public MediaTransportControlsMediaType getMediaType() {
        int res = lib.getMediaType();
        switch (res){
            case 0:
                return MediaTransportControlsMediaType.Music;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void setMediaType(MediaTransportControlsMediaType mediaType) {
        switch (mediaType){
            case Music:
                lib.setMediaType(0);
        }
    }

    @Override
    public MediaTransportControlsMediaProperties getMediaProperties() {
        String[] genres = (String[]) Arrays.stream(lib.getMusicGenres()).map(WString::toString).toArray();
        return new MediaTransportControlsMusicProperties(
                lib.getMusicTitle().toString(),
                lib.getMusicArtist().toString(),
                lib.getMusicAlbumTitle().toString(),
                lib.getMusicAlbumArtist().toString(),
                genres,
                lib.getMusicAlbumTrackCount().intValue(),
                lib.getMusicTrack().intValue()
        );
    }

    @Override
    public void setMediaProperties(MediaTransportControlsMediaProperties mediaProperties) {
        if(mediaProperties.getClass() == MediaTransportControlsMusicProperties.class){
            MediaTransportControlsMusicProperties mediaPropertiesCast = (MediaTransportControlsMusicProperties) mediaProperties;
            lib.setMusicTitle(new WString(mediaPropertiesCast.title));
            lib.setMusicArtist(new WString(mediaPropertiesCast.artist));
            if(! mediaPropertiesCast.albumTitle.isEmpty()) lib.setMusicAlbumTitle(new WString(mediaPropertiesCast.albumTitle));
            if(! mediaPropertiesCast.albumArtist.isEmpty())lib.setMusicTitle(new WString(mediaPropertiesCast.albumArtist));
            if(mediaPropertiesCast.albumTracks > 0) lib.setMusicAlbumTrackCount(new UnsignedInt(mediaPropertiesCast.albumTracks));
            if(mediaPropertiesCast.track > 0)lib.setMusicTrack(new UnsignedInt(mediaPropertiesCast.track));
            lib.clearMusicGenres();
            for (String genre: mediaPropertiesCast.genres
                 ) {
                lib.addMusicGenre(new WString(genre));
            }
        }
    }

}
