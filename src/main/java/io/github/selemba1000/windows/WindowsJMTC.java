package io.github.selemba1000.windows;

import com.sun.jna.Native;
import com.sun.jna.WString;
import io.github.selemba1000.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The class that contains the Windows implementation
 */
public class WindowsJMTC extends JMTC {

    /**
     * The Internal reference to the native library
     */
    protected final SMTCAdapter lib;

    /**
     * Constructor to set up JMTC for Windows.
     */
    public WindowsJMTC() {
        super();
        lib = Native.load("SMTCAdapter", SMTCAdapter.class);
        lib.init();
    }

    @Override
    public synchronized JMTCPlayingState getPlayingState() {
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
    public synchronized void setPlayingState(JMTCPlayingState state) {
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
    public synchronized boolean getEnabled() {
        return lib.getEnabled();
    }

    @Override
    public synchronized void setEnabled(boolean enabled) {
        lib.setEnabled(enabled);
    }

    @Override
    public synchronized JMTCEnabledButtons getEnabledButtons() {
        return new JMTCEnabledButtons(
                lib.getPlayEnabled(),
                lib.getPauseEnabled(),
                lib.getStopEnabled(),
                lib.getNextEnabled(),
                lib.getPreviousEnabled()
        );
    }

    @Override
    public synchronized void setEnabledButtons(JMTCEnabledButtons enabledButtons) {
        lib.setPlayEnabled(enabledButtons.isPlayEnabled);
        lib.setPauseEnabled(enabledButtons.isPauseEnabled);
        lib.setStopEnabled(enabledButtons.isStopEnabled);
        lib.setNextEnabled(enabledButtons.isNextEnabled);
        lib.setPreviousEnabled(enabledButtons.isPreviousEnabled);
    }

    @Override
    public synchronized JMTCParameters getParameters() {
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
    public synchronized void setParameters(JMTCParameters parameters) {
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

    private static ButtonPressedCallback onPlay;
    private static ButtonPressedCallback onPause;
    private static ButtonPressedCallback onStop;
    private static ButtonPressedCallback onNext;
    private static ButtonPressedCallback onPrevious;
    private static SeekCallback onSeek;
    private static RateCallback onRate;
    private static ShuffleCallback onShuffle;
    private static LoopStatusCallback onLoop;

    @Override
    public synchronized void setCallbacks(JMTCCallbacks callbacks) {
        onPlay = new ButtonPressedCallback(callbacks.onPlay);
        lib.setOnPlay(onPlay);
        onPause = new ButtonPressedCallback(callbacks.onPause);
        lib.setOnPause(onPause);
        onStop = new ButtonPressedCallback(callbacks.onStop);
        lib.setOnStop(onStop);
        onNext = new ButtonPressedCallback(callbacks.onNext);
        lib.setOnNext(onNext);
        onPrevious = new ButtonPressedCallback(callbacks.onPrevious);
        lib.setOnPrevious(onPrevious);
        onSeek = new SeekCallback(callbacks.onSeek);
        lib.setOnSeek(onSeek);
        onRate = new RateCallback(callbacks.onRate);
        lib.setOnRateChanged(onRate);
        onShuffle = new ShuffleCallback(callbacks.onShuffle);
        lib.setOnShuffleChanged(onShuffle);
        onLoop = new LoopStatusCallback(callbacks.onLoop);
        lib.setOnLoopChanged(onLoop);
    }

    @Override
    public synchronized void setTimelineProperties(JMTCTimelineProperties timelineProperties) {
        lib.setTimelineProperties(timelineProperties.start, timelineProperties.end, timelineProperties.seekStart, timelineProperties.seekEnd);
    }

    @Override
    public synchronized void setPosition(Long position) {
        lib.setPosition(position);
    }

    @Override
    public synchronized void updateDisplay() {
        lib.update();
    }

    @Override
    public synchronized void resetDisplay() {
        lib.reset();
    }

    @SuppressWarnings({"SwitchStatementWithTooFewBranches"})
    @Override
    public synchronized JMTCMediaType getMediaType() {
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
    public synchronized void setMediaType(JMTCMediaType mediaType) {
        switch (mediaType) {
            //TODO MediaTypes
            case Music:
                lib.setMediaType(0);
        }
    }

    @Override
    public synchronized JMTCMediaProperties getMediaProperties() {
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
    public synchronized void setMediaProperties(JMTCMediaProperties mediaProperties) {
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
                while (!lib.thumbnailLoaded()){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

}
