package io.github.selemba1000.windows;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import io.github.selemba1000.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WindowsJMTC extends JMTC {

    private final SMTCAdapter lib = Native.load("SMTCAdapter", SMTCAdapter.class);

    public WindowsJMTC() {
        super();
        lib.init();
    }

    @Override
    public JMTCPlayingState getPlayingState() {
        UnsignedInt res = lib.getPlaybackState();
        switch (res.intValue()) {
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
                lib.setPlaybackState(new UnsignedInt(0));
                break;
            case PAUSED:
                lib.setPlaybackState(new UnsignedInt(1));
                break;
            case STOPPED:
                lib.setPlaybackState(new UnsignedInt(2));
                break;
            case PLAYING:
                lib.setPlaybackState(new UnsignedInt(3));
                break;
            case CHANGING:
                lib.setPlaybackState(new UnsignedInt(4));
                break;
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
        JMTCParameters.LoopStatus loop = JMTCParameters.LoopStatus.None;
        switch (lib.getLoop().intValue()){
            case 0:
                loop = JMTCParameters.LoopStatus.None;
                break;
            case 1:
                loop = JMTCParameters.LoopStatus.Track;
                break;
            case 2:
                loop = JMTCParameters.LoopStatus.Playlist;
                break;
        }
        return new JMTCParameters(
                loop, 1.0, lib.getRate(), lib.getShuffle());
    }

    @Override
    public void setParameters(JMTCParameters parameters) {
        switch (parameters.loopStatus){
            case None:
                lib.setLoop(new UnsignedInt(0));
                break;
            case Track:
                lib.setLoop(new UnsignedInt(1));
                break;
            case Playlist:
                lib.setLoop(new UnsignedInt(2));
                break;
        }
        lib.setRate(parameters.rate);
        lib.setShuffle(parameters.shuffle);
    }

    @Override
    public void setCallbacks(JMTCCallbacks callbacks) {
        lib.setOnPlay(new ButtonPressedCallback(callbacks.onPlay));
        lib.setOnPause(new ButtonPressedCallback(callbacks.onPause));
        lib.setOnStop(new ButtonPressedCallback(callbacks.onStop));
        lib.setOnNext(new ButtonPressedCallback(callbacks.onNext));
        lib.setOnPrevious(new ButtonPressedCallback(callbacks.onPrevious));
        lib.setOnSeek(new SeekCallback(callbacks.onSeek));
        lib.setOnRateChanged(new RateCallback(callbacks.onRate));
        lib.setOnShuffleChanged(new ShuffleCallback(callbacks.onShuffle));
        lib.setOnLoopChanged(new LoopStatusCallback(callbacks.onLoop));
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
        List<String> genres = new ArrayList<>();
        for (int count = 0; count < lib.getMusicGenresSize(); count++){
            genres.add(lib.getMusicGenreAt(new UnsignedInt(count)).toString());
        }
        String[] x = {};
        String[] res = genres.toArray(x);
        return new JMTCMusicProperties(
                lib.getMusicTitle().toString(),
                lib.getMusicArtist().toString(),
                lib.getMusicAlbumTitle().toString(),
                lib.getMusicAlbumArtist().toString(),
                res,
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
            if (mediaPropertiesCast.albumTracks >= 0)
                lib.setMusicAlbumTrackCount(new UnsignedInt(mediaPropertiesCast.albumTracks));
            if (mediaPropertiesCast.track >= 0) lib.setMusicTrack(new UnsignedInt(mediaPropertiesCast.track));
            lib.clearMusicGenres();
            for (String genre : mediaPropertiesCast.genres
            ) {
                lib.addMusicGenre(new WString(genre));
            }
            if (((JMTCMusicProperties) mediaProperties).art != null) {
                lib.setThumbnail(new WString(((JMTCMusicProperties) mediaProperties).art.toURI().toString()));
                System.out.println(((JMTCMusicProperties) mediaProperties).art.toURI().toString());
            }
        }
    }

}
