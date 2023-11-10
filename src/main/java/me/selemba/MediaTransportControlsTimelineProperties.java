package me.selemba;

public class MediaTransportControlsTimelineProperties {

    public Long start;
    public Long end;
    public Long seekStart;
    public Long seekEnd;

    public MediaTransportControlsTimelineProperties(Long start, Long end, Long seekStart, Long seekEnd) {
        this.start = start;
        this.end = end;
        this.seekStart = seekStart;
        this.seekEnd = seekEnd;
    }
}
