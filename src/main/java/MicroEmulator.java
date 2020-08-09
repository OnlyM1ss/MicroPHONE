import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class MicroEmulator {
    int bytesRead = 0;
    TargetDataLine microphone;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    SourceDataLine speakers;
    int CHUNK_SIZE = 1024;
    // записывает звук и возвращает байт инфу
    public byte[] recordVoice() {
        AudioFormat format = new AudioFormat(11025.0f, 16, 1, true, true);
        TargetDataLine microphone;
        try {
            microphone = AudioSystem.getTargetDataLine(format);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();
            while (bytesRead < 10000) {
                recordVoice();
                return data;
                //numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                //bytesRead += numBytesRead;
                //out.write(data, 0, numBytesRead);
                //speakers.write(data, 0, numBytesRead);
                //System.out.println(data);
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Принимает байты и отправляет по сокету
    public void sendSound(byte[] data) {
        /**
         * Логика отправки данных по сокету(порт 7777)
         */
        int numBytesRead;
        data = recordVoice();
        numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
        bytesRead += numBytesRead;
        out.write(data, 0, numBytesRead);
        speakers.write(data, 0, numBytesRead);
        speakers.drain();
        speakers.close();
        microphone.close();
        System.out.println(data);
    }
}