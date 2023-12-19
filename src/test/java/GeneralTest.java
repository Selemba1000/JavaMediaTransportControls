import io.github.selemba1000.*;
import org.junit.jupiter.api.Test;

import java.io.File;

public class GeneralTest {

    @Test
    void test() throws InterruptedException {



        JMTC control = JMTC.getInstance(new JMTCSettings("test-application","test-application"));
        JMTCCallbacks callbacks = new JMTCCallbacks();
        callbacks.onPlay = () -> {
            control.setPlayingState(JMTCPlayingState.PLAYING);
        };
        callbacks.onPause = () -> control.setPlayingState(JMTCPlayingState.PAUSED);
        control.setEnabledButtons(new JMTCEnabledButtons(
                true,
                true,
                true,
                true,
                true
                ));
        control.setCallbacks(callbacks);
        control.setEnabled(true);
        control.setMediaType(JMTCMediaType.Music);
        control.setPlayingState(JMTCPlayingState.PAUSED);
        control.setMediaProperties(new JMTCMusicProperties(
                "TestTitle",
                "TestArtist",
                "test",
                "tset",
                new String[]{},
                0,
                1,
                null
        ));
        control.setTimelineProperties(new JMTCTimelineProperties(
                0L,
                100000L,
                0L,
                100000L
        ));
        control.setPlayingState(JMTCPlayingState.PLAYING);
        control.updateDisplay();
        for (Long l = 0L; l < 100000L;l+=1000L){
            control.setPosition(l);
            System.out.println(l);
            control.updateDisplay();
            Thread.sleep(1000);
        }
    }

}
