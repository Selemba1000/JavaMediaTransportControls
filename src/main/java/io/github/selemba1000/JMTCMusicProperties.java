package io.github.selemba1000;

import java.io.File;

/**
 * Class to store information about playing media.
 */
@SuppressWarnings("CanBeFinal")
public class JMTCMusicProperties extends JMTCMediaProperties {

    /**
     * Title of the track.
     */
    public String title;
    /**
     * Artist of the track.
     */
    public String artist;
    /**
     * Title of the album.
     */
    public String albumTitle;
    /**
     * Artist of the album.
     */
    public String albumArtist;
    /**
     * Genres of the track.
     */
    public String[] genres;
    /**
     * Cover Art of the album.
     * File will not be checked. It is up to the user to make sure the file exists. null if no cover art is available.
     */
    public File art;
    /**
     * Number of tracks on the album.
     */
    public int albumTracks;
    /**
     * Track number of this track.
     */
    public int track;

    /**
     * Constructor for JMTCMusicProperties.
     *
     * @param title       Title of the track.
     * @param artist      Artist of the track.
     * @param albumTitle  Title of the album.
     * @param albumArtist Artist of the album.
     * @param genres      Genres of the track.
     * @param albumTracks Number of tracks on the album.
     * @param track       Track number of this track.
     * @param art         Cover Art of the album.
     */
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
