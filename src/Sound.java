import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class Sound extends Thread {

    String fileName;
    File soundFile;
    SourceDataLine audioLine;
    AudioInputStream audioStream;
    AudioFormat format;
    DataLine.Info info;

    public Sound(String fileName) {
        soundFile = new File(fileName);
    }

    @Override
    public void run() {

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
            format = audioStream.getFormat();
            info = new DataLine.Info(SourceDataLine.class, format);

            audioLine = (SourceDataLine) AudioSystem.getLine(info);
            audioLine.open(format);
            audioLine.start();

            System.out.println("Playback started.");

            byte[] bytesBuffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }
}
