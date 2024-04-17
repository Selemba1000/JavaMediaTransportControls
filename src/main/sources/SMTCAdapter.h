#pragma once
#ifndef SMTC_Adapter
#define SMTC_Adapter

#define EXPORT_SMTC

#if defined EXPORT_SMTC
#define SMTC_API __declspec(dllexport)
#else
#define SMTC_API __declspec(dllimport)
#endif

extern "C"{

	SMTC_API void init();

	SMTC_API void setOnPlay(void (*)());
	SMTC_API void setOnPause(void (*)());
	SMTC_API void setOnStop(void (*)());
	SMTC_API void setOnNext(void (*)());
	SMTC_API void setOnPrevious(void (*)());

	SMTC_API void setOnRateChanged(void (*)(double));
	SMTC_API void setOnShuffleChanged(void (*)(bool));
	SMTC_API void setOnLoopChanged(void (*)(unsigned int));

	SMTC_API void revokeCallbacks();

	SMTC_API void setOnSeek(void (*)(long long));

	SMTC_API bool getEnabled();
	SMTC_API void setEnabled(bool);

	SMTC_API bool getNextEnabled();
	SMTC_API void setNextEnabled(bool);

	SMTC_API bool getPreviousEnabled();
	SMTC_API void setPreviousEnabled(bool);

	SMTC_API bool getPlayEnabled();
	SMTC_API void setPlayEnabled(bool);

	SMTC_API bool getPauseEnabled();
	SMTC_API void setPauseEnabled(bool);

	SMTC_API bool getStopEnabled();
	SMTC_API void setStopEnabled(bool);

	SMTC_API double getRate();
	SMTC_API void setRate(double);

	SMTC_API bool getShuffle();
	SMTC_API void setShuffle(bool);

	SMTC_API unsigned int getLoop();
	SMTC_API void setLoop(unsigned int);

	//TODO chnl up down ffd rew

	// mapping: 0: Closed; 1: Paused; 2: Stopped; 3: Playing; 4: Changing;
	SMTC_API unsigned int getPlaybackState();
	SMTC_API void setPlaybackState(unsigned int);

	SMTC_API void setTimelineProperties(long long, long long, long long, long long);
	SMTC_API void setPosition(long long);

	SMTC_API void update();
	SMTC_API void reset();

	// mapping: 0: Music; 1: Video; 2: Image; 3: Unknown;
	SMTC_API int getMediaType();
	SMTC_API void setMediaType(int);

	SMTC_API bool thumbnailLoaded();
	SMTC_API void setThumbnail(const wchar_t*);

	SMTC_API const wchar_t* getMusicTitle();
	SMTC_API void setMusicTitle(const wchar_t*);

	SMTC_API const wchar_t* getMusicArtist();
	SMTC_API void setMusicArtist(const wchar_t*);

	SMTC_API const wchar_t* getMusicAlbumTitle();
	SMTC_API void setMusicAlbumTitle(const wchar_t*);

	SMTC_API const wchar_t* getMusicAlbumArtist();
	SMTC_API void setMusicAlbumArtist(const wchar_t*);

	SMTC_API unsigned int getMusicGenresSize();
	SMTC_API const wchar_t* getMusicGenreAt(unsigned int i);
	SMTC_API void addMusicGenre(const wchar_t*);
	SMTC_API void clearMusicGenres();

	SMTC_API unsigned int getMusicAlbumTrackCount();
	SMTC_API void setMusicAlbumTrackCount(unsigned int);

	SMTC_API unsigned int getMusicTrack();
	SMTC_API void setMusicTrack(unsigned int);

	//TODO Video Image

}

#endif