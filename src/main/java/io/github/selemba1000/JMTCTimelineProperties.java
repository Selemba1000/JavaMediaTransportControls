package io.github.selemba1000;

/**
 * Class to group parameters of the seekbar.
 */
public class JMTCTimelineProperties {

    /**
     * Start of the timeline in milliseconds.
     * Usually 0.
     */
    public final Long start;
    /**
     * End of the timeline in milliseconds.
     * Linux only uses the total duration of the media.
     */
    public final Long end;
    /**
     * Start of the seekable area in milliseconds.
     * Windows only.
     */
    public final Long seekStart;
    /**
     * End of the seekable area in milliseconds.
     * Windows only.
     */
    public final Long seekEnd;

    /**
     * Constructor for TimelineProperties.
     *
     * @param start Start of the timeline in milliseconds.
     * @param end End of the timeline in milliseconds.
     * @param seekStart Start of the seekable area in milliseconds.
     * @param seekEnd End of the seekable area in milliseconds.
     */
    public JMTCTimelineProperties(Long start, Long end, Long seekStart, Long seekEnd) {
        this.start = start;
        this.end = end;
        this.seekStart = seekStart;
        this.seekEnd = seekEnd;
    }
}
