package io.github.selemba1000;

public class JMTCTimelineProperties {

    public final Long start;
    public final Long end;
    public final Long seekStart;
    public final Long seekEnd;

    public JMTCTimelineProperties(Long start, Long end, Long seekStart, Long seekEnd) {
        this.start = start;
        this.end = end;
        this.seekStart = seekStart;
        this.seekEnd = seekEnd;
    }
}
