package io.github.selemba1000.windows;

import com.sun.jna.Native;
import com.sun.jna.WString;
import io.github.selemba1000.*;

import java.util.Arrays;

public class WindowsJMTC extends JMTC {

    private final SMTCAdapter lib = Native.load("SMTCAdapter", SMTCAdapter.class);

    public WindowsJMTC() {
        super();
        lib.init();
    }

    @Override
    public JMTCPlayingState getPlayingState() {
        int res = lib.getPlaybackState();
        switch (res) {
            case 0:
                return JMTCPlayingState.CLOSED;
            case 1:
                return JMTCPlayingState.PAUSED;
            case 2:
                return JMTCPlayingState.STOPPED;
            case 3:
                return JMTCPlayingState.PLAYING;
            case 4:
                return JMTCPlayingState.CHANGING;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void setPlayingState(JMTCPlayingState state) {
        switch (state) {
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
    public JMTCEnabledButtons getEnabledButtons() {
        return new JMTCEnabledButtons(
                lib.getPlayEnabled(),
                lib.getPauseEnabled(),
                lib.getStopEnabled(),
                lib.getNextEnabled(),
                lib.getPreviousEnabled()
        );
    }

    @Override
    public void setEnabledButtons(JMTCEnabledButtons enabledButtons) {
        lib.setPlayEnabled(enabledButtons.isPlayEnabled);
        lib.setPauseEnabled(enabledButtons.isPauseEnabled);
        lib.setStopEnabled(enabledButtons.isStopEnabled);
        lib.setNextEnabled(enabledButtons.isNextEnabled);
        lib.setPreviousEnabled(enabledButtons.isPreviousEnabled);
    }

    @Override
    public JMTCParameters getParameters() {
        return new JMTCParameters(JMTCParameters.LoopStatus.None, 1.0, 1.0, false);
    }

    @Override
    public void setParameters(JMTCParameters parameters) {
        //TODO Windows parameters
    }

    @Override
    public void setCallbacks(JMTCCallbacks callbacks) {
        lib.setOnPlay(new ButtonPressedCallback(callbacks.onPlay));
        lib.setOnPause(new ButtonPressedCallback(callbacks.onPause));
        lib.setOnStop(new ButtonPressedCallback(callbacks.onStop));
        lib.setOnNext(new ButtonPressedCallback(callbacks.onNext));
        lib.setOnPrevious(new ButtonPressedCallback(callbacks.onPrevious));
        lib.setOnSeek(new SeekCallback(callbacks.onSeek));
    }

    @Override
    public void setTimelineProperties(JMTCTimelineProperties timelineProperties) {
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

    @SuppressWarnings({"SwitchStatementWithTooFewBranches"})
    @Override
    public JMTCMediaType getMediaType() {
        int res = lib.getMediaType();
        switch (res) {
            //TODO MediaTypes
            case 0:
                return JMTCMediaType.Music;
            default:
                throw new IllegalStateException();
        }
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void setMediaType(JMTCMediaType mediaType) {
        switch (mediaType) {
            //TODO MediaTypes
            case Music:
                lib.setMediaType(0);
        }
    }

    @Override
    public JMTCMediaProperties getMediaProperties() {
        String[] genres = (String[]) Arrays.stream(lib.getMusicGenres()).map(WString::toString).toArray();
        return new JMTCMusicProperties(
                lib.getMusicTitle().toString(),
                lib.getMusicArtist().toString(),
                lib.getMusicAlbumTitle().toString(),
                lib.getMusicAlbumArtist().toString(),
                genres,
                lib.getMusicAlbumTrackCount().intValue(),
                lib.getMusicTrack().intValue(),
                null
        );
    }

    @Override
    public void setMediaProperties(JMTCMediaProperties mediaProperties) {
        if (mediaProperties.getClass() == JMTCMusicProperties.class) {
            JMTCMusicProperties mediaPropertiesCast = (JMTCMusicProperties) mediaProperties;
            lib.setMusicTitle(new WString(mediaPropertiesCast.title));
            lib.setMusicArtist(new WString(mediaPropertiesCast.artist));
            if (!mediaPropertiesCast.albumTitle.isEmpty())
                lib.setMusicAlbumTitle(new WString(mediaPropertiesCast.albumTitle));
            if (!mediaPropertiesCast.albumArtist.isEmpty())
                lib.setMusicAlbumArtist(new WString(mediaPropertiesCast.albumArtist));
            if (mediaPropertiesCast.albumTracks > 0)
                lib.setMusicAlbumTrackCount(new UnsignedInt(mediaPropertiesCast.albumTracks));
            if (mediaPropertiesCast.track > 0) lib.setMusicTrack(new UnsignedInt(mediaPropertiesCast.track));
            lib.clearMusicGenres();
            for (String genre : mediaPropertiesCast.genres
            ) {
                lib.addMusicGenre(new WString(genre));
            }
            if (((JMTCMusicProperties) mediaProperties).art != null) {
                lib.setThumbnail(new WString(((JMTCMusicProperties) mediaProperties).art.toURI().toString()));
            }
        }
    }

}
