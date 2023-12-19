package io.github.selemba1000;

import java.io.File;

@SuppressWarnings("CanBeFinal")
public class JMTCMusicProperties extends JMTCMediaProperties {

    public String title;
    public String artist;
    public String albumTitle;
    public String albumArtist;

    public String[] genres;

    public File art;

    public int albumTracks;

    public int track;

    public JMTCMusicProperties(String title, String artist, String albumTitle, String albumArtist, String[] genres, int albumTracks, int track, File art) {
        this.title = title;
        this.artist = artist;
        this.albumTitle = albumTitle;
        this.albumArtist = albumArtist;
        this.genres = genres;
        this.albumTracks = albumTracks;
        this.track = track;
        this.art = art;
    }
}
