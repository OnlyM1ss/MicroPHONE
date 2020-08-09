import javax.sound.sampled.*;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        MicroEmulator microEmulator = new MicroEmulator();
        microEmulator.recordVoice();
    }
}
