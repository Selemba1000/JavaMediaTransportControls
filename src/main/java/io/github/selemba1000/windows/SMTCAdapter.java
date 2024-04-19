package io.github.selemba1000.windows;

import com.sun.jna.Library;
import com.sun.jna.WString;

/**
 * The interface definition for JNA.
 */
public interface SMTCAdapter extends Library {
    void init();

    void setOnPlay(ButtonPressedCallback callback);

    void setOnPause(ButtonPressedCallback callback);

    void setOnStop(ButtonPressedCallback callback);

    void setOnNext(ButtonPressedCallback callback);

    void setOnPrevious(ButtonPressedCallback callback);

    void setOnRateChanged(RateCallback callback);

    void setOnShuffleChanged(ShuffleCallback callback);

    void setOnLoopChanged(LoopStatusCallback callback);

    void setOnSeek(SeekCallback callback);

    boolean getEnabled();

    void setEnabled(boolean enabled);

    boolean getNextEnabled();

    void setNextEnabled(boolean enabled);

    boolean getPreviousEnabled();

    void setPreviousEnabled(boolean enabled);

    boolean getPlayEnabled();

    void setPlayEnabled(boolean enabled);

    boolean getPauseEnabled();

    void setPauseEnabled(boolean enabled);

    boolean getStopEnabled();

    void setStopEnabled(boolean enabled);

    Double getRate();

    void setRate(Double rate);

    Boolean getShuffle();

    void setShuffle(Boolean shuffle);

    UnsignedInt getLoop();

    void setLoop(UnsignedInt loop);

    UnsignedInt getPlaybackState();

    void setPlaybackState(UnsignedInt state);

    void setTimelineProperties(Long start, Long end, Long seekStart, Long seekEnd);

    void setPosition(Long position);

    void update();

    void reset();

    int getMediaType();

    void setMediaType(int type);

    Boolean thumbnailLoaded();
    void setThumbnail(WString path);

    WString getMusicTitle();

    void setMusicTitle(WString title);

    WString getMusicArtist();

    void setMusicArtist(WString artist);

    WString getMusicAlbumTitle();

    void setMusicAlbumTitle(WString title);

    WString getMusicAlbumArtist();

    void setMusicAlbumArtist(WString artist);

    Integer getMusicGenresSize();

    WString getMusicGenreAt(UnsignedInt i);

    void addMusicGenre(WString genre);

    void clearMusicGenres();

    UnsignedInt getMusicAlbumTrackCount();

    void setMusicAlbumTrackCount(UnsignedInt count);

    UnsignedInt getMusicTrack();

    void setMusicTrack(UnsignedInt track);
}
