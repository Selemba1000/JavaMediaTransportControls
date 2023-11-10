import me.selemba.*;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    boolean flag = false;

    @Test
    void test() throws InterruptedException {
        MediaTransportControls control = MediaTransportControls.getInstance("");
        MediaTransportControlsCallbacks callbacks = new MediaTransportControlsCallbacks();
        callbacks.onNext = () -> {
            flag = true;
            System.out.println("next");
        };
        control.setEnabledButtons(new MediaTransportControlsEnabledButtons(
                true,
                true,
                true,
                true,
                false
                ));
        control.setCallbacks(callbacks);
        control.setEnabled(true);
        control.setMediaType(MediaTransportControlsMediaType.Music);
        control.setMediaProperties(new MediaTransportControlsMusicProperties(
                "TestTitle",
                "TestArtist",
                "",
                "",
                new String[]{},
                0,
                0
        ));
        control.setTimelineProperties(new MediaTransportControlsTimelineProperties(
                0L,
                100000L,
                0L,
                100000L
        ));
        control.updateDisplay();
        control.setPlayingState(MediaTransportControlsPlayingState.PLAYING);
        for (Long l = 0L; l < 100000L;l+=1000L){
            control.setPosition(l);
            System.out.println(l);
            control.updateDisplay();
            Thread.sleep(1000);
        }
    }

}
