package me.selemba;

public class MediaTransportControlsMusicProperties extends MediaTransportControlsMediaProperties {

    public String title;
    public String artist;
    public String albumTitle;
    public String albumArtist;

    public String[] genres;

    public int albumTracks;

    public int track;

    public MediaTransportControlsMusicProperties(String title, String artist, String albumTitle, String albumArtist, String[] genres, int albumTracks, int track) {
        this.title = title;
        this.artist = artist;
        this.albumTitle = albumTitle;
        this.albumArtist = albumArtist;
        this.genres = genres;
        this.albumTracks = albumTracks;
        this.track = track;
    }
}