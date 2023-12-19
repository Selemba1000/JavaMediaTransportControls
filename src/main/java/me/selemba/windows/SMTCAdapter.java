package me.selemba.windows;

import com.sun.jna.Library;
import com.sun.jna.WString;

interface SMTCAdapter extends Library {
    void init();

    void setOnPlay(ButtonPressedCallback callback);
    void setOnPause(ButtonPressedCallback callback);
    void setOnStop(ButtonPressedCallback callback);
    void setOnNext(ButtonPressedCallback callback);
    void setOnPrevious(ButtonPressedCallback callback);

    void setOnSeek(SeekCallback callback);

    boolean getEnabled();
    void  setEnabled(boolean enabled);

    boolean getNextEnabled();
    void  setNextEnabled(boolean enabled);

    boolean getPreviousEnabled();
    void  setPreviousEnabled(boolean enabled);

    boolean getPlayEnabled();
    void  setPlayEnabled(boolean enabled);

    boolean getPauseEnabled();
    void  setPauseEnabled(boolean enabled);

    boolean getStopEnabled();
    void  setStopEnabled(boolean enabled);

    int getPlaybackState();
    void setPlaybackState(int state);

    void setTimelineProperties(Long start, Long end, Long seekStart, Long seekEnd);
    void setPosition(Long position);

    void update();
    void reset();

    int getMediaType();
    void setMediaType(int type);

    void setThumbnail(WString path);

    WString getMusicTitle();
    void  setMusicTitle(WString title);

    WString getMusicArtist();
    void  setMusicArtist(WString artist);

    WString getMusicAlbumTitle();
    void  setMusicAlbumTitle(WString title);

    WString getMusicAlbumArtist();
    void  setMusicAlbumArtist(WString artist);

    WString[] getMusicGenres();
    void addMusicGenre(WString genre);
    void clearMusicGenres();

    UnsignedInt getMusicAlbumTrackCount();
    void setMusicAlbumTrackCount(UnsignedInt count);

    UnsignedInt getMusicTrack();
    void setMusicTrack(UnsignedInt track);
}
