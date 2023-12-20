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
            System.out.println("Play");
        };
        callbacks.onPause = () -> {
            control.setPlayingState(JMTCPlayingState.PAUSED);
            System.out.println("Pause");
        };
        callbacks.onShuffle = (Boolean shuffle) -> {
            JMTCParameters param = control.getParameters();
            param.shuffle=shuffle;
            control.setParameters(param);
        };
        callbacks.onLoop = (JMTCParameters.LoopStatus loop) -> {
            JMTCParameters param = control.getParameters();
            param.loopStatus=loop;
            control.setParameters(param);
        };
        callbacks.onNext = () -> System.out.println("next");
        control.setEnabled(true);
        control.setEnabledButtons(new JMTCEnabledButtons(
                true,
                true,
                true,
                true,
                true
                ));
        control.setCallbacks(callbacks);
        control.setPlayingState(JMTCPlayingState.STOPPED);
        control.setMediaType(JMTCMediaType.Music);
        control.setTimelineProperties(new JMTCTimelineProperties(
                        0L,
                        100000L,
                        0L,
                        100000L
                ));
        control.setMediaProperties(new JMTCMusicProperties(
                "TestTitle",
                "TestArtist",
                "",
                "",
                new String[]{"test","abc"},
                3,
                1,
                null
        ));
        control.setParameters(
                new JMTCParameters(
                        JMTCParameters.LoopStatus.Track,
                        1.0,
                        1.0,
                        true
                )
        );
        control.updateDisplay();
        control.setPlayingState(JMTCPlayingState.PLAYING);
        //control.updateDisplay();
        for (Long l = 0L; l < 100000L;l+=1000L){
            control.setPosition(l);
            System.out.println(l);
            control.updateDisplay();
            Thread.sleep(1000);
        }
    }

}
