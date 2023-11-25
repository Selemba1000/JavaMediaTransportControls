import me.selemba.*;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    boolean flag = false;

    @Test
    void test() throws InterruptedException {
        MediaTransportControls control = MediaTransportControls.getInstance("free-danza");
        MediaTransportControlsCallbacks callbacks = new MediaTransportControlsCallbacks();
        callbacks.onPlay = () -> {
            flag = true;
            System.out.println("next");
            control.setPlayingState(MediaTransportControlsPlayingState.PLAYING);
        };
        callbacks.onPause = () -> {
            control.setPlayingState(MediaTransportControlsPlayingState.PAUSED);
        };
        control.setEnabledButtons(new MediaTransportControlsEnabledButtons(
                true,
                true,
                true,
                true,
                true
                ));
        control.setCallbacks(callbacks);
        control.setEnabled(true);
        control.setMediaType(MediaTransportControlsMediaType.Music);
        control.setPlayingState(MediaTransportControlsPlayingState.PAUSED);
        control.setMediaProperties(new MediaTransportControlsMusicProperties(
                "TestTitle",
                "TestArtist",
                "test",
                "tset",
                new String[]{},
                0,
                1
        ));
        control.setTimelineProperties(new MediaTransportControlsTimelineProperties(
                0L,
                100000L,
                0L,
                100000L
        ));
        control.setPlayingState(MediaTransportControlsPlayingState.PLAYING);
        control.updateDisplay();
        for (Long l = 0L; l < 100000L;l+=1000L){
            control.setPosition(l);
            System.out.println(l);
            control.updateDisplay();
            Thread.sleep(1000);
        }
    }

}
