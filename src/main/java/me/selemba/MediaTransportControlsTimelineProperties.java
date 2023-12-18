package me.selemba;

public class MediaTransportControlsTimelineProperties {

    public final Long start;
    public final Long end;
    public final Long seekStart;
    public final Long seekEnd;

    public MediaTransportControlsTimelineProperties(Long start, Long end, Long seekStart, Long seekEnd) {
        this.start = start;
        this.end = end;
        this.seekStart = seekStart;
        this.seekEnd = seekEnd;
    }
}
