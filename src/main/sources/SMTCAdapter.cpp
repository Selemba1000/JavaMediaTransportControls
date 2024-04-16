#include "SMTCAdapter.h"

#include <functional>
#include <winrt/Windows.Media.Playback.h>
#include <winrt/Windows.Foundation.h>
#include <winrt/Windows.Foundation.Collections.h>
#include <winrt/windows.foundation.h>
#include <winrt/Windows.Storage.Streams.h>
#include <winrt/Windows.Storage.h>
#include <iostream>

using namespace winrt;
using namespace Windows::Media;
using namespace Windows::Media::Playback;
using namespace Windows::Foundation::Collections;
using namespace Windows::Foundation;
using namespace Windows::Storage::Streams;
using namespace Windows::Storage;

static SystemMediaTransportControls smtc = nullptr;
static SystemMediaTransportControlsDisplayUpdater updater = nullptr;

static std::function<void()> onPlay = []() { smtc.PlaybackRate(2.0); };
static std::function<void()> onPause = []() { smtc.PlaybackRate(2.0); };
static std::function<void()> onStop = []() {};
static std::function<void()> onNext = []() {};
static std::function<void()> onPrevoius = []() {};

static std::function<void(long long)> onSeek = [](long long pos) {};

static std::function<void(double)> onRateChanged = [](double rate) {};
static std::function<void(bool)> onShuffleChanged = [](bool shuffle) {};
static std::function<void(unsigned int)> onLoopChanged = [](unsigned int loop) {};

static long long start = 0;
static long long end = 0;
static long long seekStart = 0;
static long long seekEnd = 0;

winrt::event_token button_revoker;
winrt::event_token repeat_revoker;
winrt::event_token rate_revoker;
winrt::event_token shuffle_revoker;
winrt::event_token position_revoker;

SMTC_API void init()
{
	winrt::init_apartment();

	auto mp = MediaPlayer();
	smtc = mp.SystemMediaTransportControls();
	//updater = smtc.DisplayUpdater();
	mp.CommandManager().IsEnabled(false);

	smtc.IsPlayEnabled(true);
	smtc.IsPauseEnabled(true);
	//smtc.IsStopEnabled(true);
	smtc.PlaybackStatus(MediaPlaybackStatus::Closed);

	 button_revoker = smtc.ButtonPressed([&](SystemMediaTransportControls smtc, SystemMediaTransportControlsButtonPressedEventArgs args) {
		switch (args.Button())
		{
		case SystemMediaTransportControlsButton::Play:
			onPlay();
			break;
		case SystemMediaTransportControlsButton::Pause:
			onPause();
			break;
		case SystemMediaTransportControlsButton::Stop:
			onStop();
			break;
		case SystemMediaTransportControlsButton::Next:
			onNext();
			break;
		case SystemMediaTransportControlsButton::Previous:
			onPrevoius();
			break;
		default:
			onStop();
			break;
		}
	});

	repeat_revoker = smtc.AutoRepeatModeChangeRequested(
		[&](SystemMediaTransportControls sender, AutoRepeatModeChangeRequestedEventArgs args) {
			switch (args.RequestedAutoRepeatMode())
			{
			case MediaPlaybackAutoRepeatMode::None:
				onLoopChanged(0);
				break;
			case MediaPlaybackAutoRepeatMode::Track:
				onLoopChanged(1);
				break;
			case MediaPlaybackAutoRepeatMode::List:
				onLoopChanged(2);
				break;
			}
		}
	);

	rate_revoker = smtc.PlaybackRateChangeRequested(
		[&](SystemMediaTransportControls sender, PlaybackRateChangeRequestedEventArgs args) {
			onRateChanged(args.RequestedPlaybackRate());
		}
	);

	shuffle_revoker = smtc.ShuffleEnabledChangeRequested(
		[&](SystemMediaTransportControls sender, ShuffleEnabledChangeRequestedEventArgs args) {
			onShuffleChanged(args.RequestedShuffleEnabled());
		}
	);

	position_revoker = smtc.PlaybackPositionChangeRequested( [](SystemMediaTransportControls smtc, PlaybackPositionChangeRequestedEventArgs args) {
		onSeek(std::chrono::duration_cast<std::chrono::milliseconds>(args.RequestedPlaybackPosition()).count());
		}
	);
}

SMTC_API void setOnPlay(void(*onplay)())
{
	onPlay = onplay;
}

SMTC_API void setOnPause(void(*onpause)())
{
	onPause = onpause;
}

SMTC_API void setOnStop(void(*onstop)())
{
	onStop = onstop;
}

SMTC_API void setOnNext(void(*onnext)())
{
	onNext = onnext;
}

SMTC_API void setOnPrevious(void(*onprevious)())
{
	onPrevoius = onprevious;
}

SMTC_API void setOnSeek(void(*onseek)(long long))
{
	onSeek = onseek;
}

SMTC_API void setOnRateChanged(void(*onrate)(double))
{
	onRateChanged = onrate;
}

SMTC_API void setOnShuffleChanged(void(*onshuffle)(bool))
{
	onShuffleChanged = onshuffle;
}

SMTC_API void setOnLoopChanged(void(*onloop)(unsigned int))
{
	onLoopChanged = onloop;
}

SMTC_API void revokeCallbacks() {
	smtc.ButtonPressed(button_revoker);
	smtc.AutoRepeatModeChangeRequested(repeat_revoker);
	smtc.PlaybackRateChangeRequested(rate_revoker);
	smtc.ShuffleEnabledChangeRequested(shuffle_revoker);
	smtc.PlaybackPositionChangeRequested(position_revoker);
}

SMTC_API bool getEnabled()
{
	return smtc.IsEnabled();
}

SMTC_API void setEnabled(bool enabled)
{
	smtc.IsEnabled(enabled);
}

SMTC_API bool getNextEnabled()
{
	return smtc.IsNextEnabled();
}

SMTC_API void setNextEnabled(bool enabled)
{
	smtc.IsNextEnabled(enabled);
}

SMTC_API bool getPreviousEnabled()
{
	return smtc.IsPreviousEnabled();
}

SMTC_API void setPreviousEnabled(bool enabled)
{
	smtc.IsPreviousEnabled(enabled);
}

SMTC_API bool getPlayEnabled()
{
	return smtc.IsPlayEnabled();
}

SMTC_API void setPlayEnabled(bool enabled)
{
	smtc.IsPlayEnabled(enabled);
}

SMTC_API bool getPauseEnabled()
{
	return smtc.IsPauseEnabled();
}

SMTC_API void setPauseEnabled(bool enabled)
{
	smtc.IsPauseEnabled(enabled);
}

SMTC_API bool getStopEnabled()
{
	return smtc.IsStopEnabled();
}

SMTC_API void setStopEnabled(bool enabled)
{
	smtc.IsStopEnabled(enabled);
}

SMTC_API double getRate()
{
	return smtc.PlaybackRate();
}

SMTC_API void setRate(double rate)
{
	smtc.PlaybackRate(rate);
}

SMTC_API bool getShuffle()
{
	return smtc.ShuffleEnabled();
}

SMTC_API void setShuffle(bool shuffle)
{
	smtc.ShuffleEnabled(shuffle);
}

SMTC_API unsigned int getLoop()
{
	switch (smtc.AutoRepeatMode())
	{
	case MediaPlaybackAutoRepeatMode::None:
		return 0;
	case MediaPlaybackAutoRepeatMode::Track: 
		return 1;
	case MediaPlaybackAutoRepeatMode::List:
		return 2;
	default:
		return 0;
	}
}

SMTC_API void setLoop(unsigned int loop)
{
	switch (loop)
	{
	case 0:
		smtc.AutoRepeatMode(MediaPlaybackAutoRepeatMode::None);
		break;
	case 1:
		smtc.AutoRepeatMode(MediaPlaybackAutoRepeatMode::Track);
		break;
	case 2:
		smtc.AutoRepeatMode(MediaPlaybackAutoRepeatMode::List);
		break;
	}
}

SMTC_API unsigned int getPlaybackState()
{
	switch (smtc.PlaybackStatus())
	{
	case MediaPlaybackStatus::Closed:
		return 0;
		break;
	case MediaPlaybackStatus::Paused:
		return 1;
		break;
	case MediaPlaybackStatus::Stopped:
		return 2;
		break;
	case MediaPlaybackStatus::Playing:
		return 3;
		break;
	case MediaPlaybackStatus::Changing:
		return 4;
		break;
	default:
		return -1;
		break;
	}
}

SMTC_API void setPlaybackState(unsigned int state)
{
	switch (state)
	{
	case 0:
		smtc.PlaybackStatus(MediaPlaybackStatus::Closed);
		break;
	case 1:
		smtc.PlaybackStatus(MediaPlaybackStatus::Paused);
		break;
	case 2:
		smtc.PlaybackStatus(MediaPlaybackStatus::Stopped);
		break;
	case 3:
		smtc.PlaybackStatus(MediaPlaybackStatus::Playing);
		break;
	case 4:
		smtc.PlaybackStatus(MediaPlaybackStatus::Changing);
		break;
	default:
		smtc.PlaybackStatus(MediaPlaybackStatus::Stopped);
		break;
	}
}

SMTC_API void setTimelineProperties(long long pstart, long long pend, long long pseekStart, long long pseekEnd)
{
	start = pstart;
	end = pend;
	seekStart = pseekStart;
	seekEnd = pseekEnd;

}

SMTC_API void setPosition(long long position)
{
	auto properties = SystemMediaTransportControlsTimelineProperties();
	properties.StartTime(std::chrono::milliseconds(start));
	properties.EndTime(std::chrono::milliseconds(end));
	properties.MinSeekTime(std::chrono::milliseconds(seekStart));
	properties.MaxSeekTime(std::chrono::milliseconds(seekEnd));
	properties.Position(std::chrono::milliseconds(position));
	smtc.UpdateTimelineProperties(properties);
}

SMTC_API void update()
{
	updater = smtc.DisplayUpdater();
	updater.Update();
}

SMTC_API void reset()
{
	updater = smtc.DisplayUpdater();
	updater.ClearAll();
}

SMTC_API int getMediaType()
{
	updater = smtc.DisplayUpdater();
	auto type = updater.Type();
	switch (type)
	{
	case winrt::Windows::Media::MediaPlaybackType::Unknown:
		return 3;
		break;
	case winrt::Windows::Media::MediaPlaybackType::Music:
		return 0;
		break;
	case winrt::Windows::Media::MediaPlaybackType::Video:
		return 1;
		break;
	case winrt::Windows::Media::MediaPlaybackType::Image:
		return 2;
		break;
	default:
		return -1;
		break;
	}
}

SMTC_API void setMediaType(int type)
{
	updater = smtc.DisplayUpdater();
	switch (type)
	{
	case 0:
		updater.Type(MediaPlaybackType::Music);
		break;
	case 1:
		updater.Type(MediaPlaybackType::Video);
		break;
	case 2:
		updater.Type(MediaPlaybackType::Image);
		break;
	case 3:
		updater.Type(MediaPlaybackType::Unknown);
		break;
	default:
		return;
		break;
	}
}

bool loaded = false;

SMTC_API bool thumbnailLoaded()
{
	return loaded;
}


SMTC_API void setThumbnail(const wchar_t* thumbnail)
{
	updater = smtc.DisplayUpdater();
	loaded = false;
	StorageFile::GetFileFromPathAsync(hstring(thumbnail)).Completed(
		[&](const winrt::Windows::Foundation::IAsyncOperation<winrt::Windows::Storage::StorageFile> result, AsyncStatus status) {
			updater.Thumbnail(RandomAccessStreamReference::CreateFromFile(result.GetResults()));
			loaded = true;
		}
	);
}

SMTC_API const wchar_t* getMusicTitle()
{
	updater = smtc.DisplayUpdater();
	return updater.MusicProperties().Title().c_str();
}

SMTC_API void setMusicTitle(const wchar_t* title)
{
	updater = smtc.DisplayUpdater();
	updater.MusicProperties().Title(hstring(title));
}

SMTC_API const wchar_t* getMusicArtist()
{
	updater = smtc.DisplayUpdater();
	return updater.MusicProperties().Artist().c_str();
}

SMTC_API void setMusicArtist(const wchar_t* artist)
{
	updater = smtc.DisplayUpdater();
	updater.MusicProperties().Artist(hstring(artist));
}

SMTC_API const wchar_t* getMusicAlbumTitle()
{
	updater = smtc.DisplayUpdater();
	return updater.MusicProperties().AlbumTitle().c_str();
}

SMTC_API void setMusicAlbumTitle(const wchar_t* title)
{
	updater = smtc.DisplayUpdater();
	updater.MusicProperties().AlbumTitle(hstring(title));
}

SMTC_API const wchar_t* getMusicAlbumArtist()
{
	updater = smtc.DisplayUpdater();
	return updater.MusicProperties().AlbumArtist().c_str();
}

SMTC_API void setMusicAlbumArtist(const wchar_t* artist)
{
	updater = smtc.DisplayUpdater();
	updater.MusicProperties().AlbumArtist(hstring(artist));
}

SMTC_API unsigned int getMusicGenresSize()
{
	updater = smtc.DisplayUpdater();
	IVector<hstring> genres = updater.MusicProperties().Genres();
	auto size = genres.Size();
	
	return size;
}

SMTC_API const wchar_t* getMusicGenreAt(unsigned int i)
{
	updater = smtc.DisplayUpdater();
	IVector<hstring> genres = updater.MusicProperties().Genres();
	auto size = genres.Size();

	const std::string orig = winrt::to_string(genres.GetAt(i));
	const size_t newsizew = orig.size() + 1;
	size_t convertedChars = 0;
	wchar_t* wcstring = new wchar_t[newsizew];
	mbstowcs_s(&convertedChars, wcstring, newsizew, orig.c_str(), _TRUNCATE);
	return wcstring;
}

SMTC_API void addMusicGenre(const wchar_t* genre)
{
	updater = smtc.DisplayUpdater();
	updater.MusicProperties().Genres().Append(hstring(genre));
}

SMTC_API void clearMusicGenres()
{
	updater = smtc.DisplayUpdater();
	updater.MusicProperties().Genres().Clear();
}

SMTC_API unsigned int getMusicAlbumTrackCount()
{
	updater = smtc.DisplayUpdater();
	return updater.MusicProperties().AlbumTrackCount();
}

SMTC_API void setMusicAlbumTrackCount(unsigned int tracks)
{
	updater = smtc.DisplayUpdater();
	updater.MusicProperties().AlbumTrackCount(tracks);
}

SMTC_API unsigned int getMusicTrack()
{
	updater = smtc.DisplayUpdater();
	return updater.MusicProperties().TrackNumber();
}

SMTC_API void setMusicTrack(unsigned int track)
{
	updater = smtc.DisplayUpdater();
	updater.MusicProperties().TrackNumber(track);
}
