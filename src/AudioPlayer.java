import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

public class AudioPlayer {

    private volatile boolean playing = false;
    private Thread playbackThread;
    private SourceDataLine line;

    public void play(String filePath) {
        if (playing) return;

        playbackThread = new Thread(() -> {
            File file = new File(filePath);
            try (AudioInputStream in = getAudioInputStream(file)) {
                AudioFormat outFormat = getOutFormat(in.getFormat());
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, outFormat);

                try (SourceDataLine localLine = (SourceDataLine) AudioSystem.getLine(info)) {
                    line = localLine;
                    if (line != null) {
                        line.open(outFormat);
                        line.start();
                        playing = true;
                        stream(getAudioInputStream(outFormat, in), line);
                        line.drain();
                        line.stop();
                        line.close();
                    }
                }

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new IllegalStateException(e);
            } finally {
                playing = false;
            }
        });

        playbackThread.start();
    }

    public void playLoop(String filePath) {
        if (playing) return;

        playbackThread = new Thread(() -> {
            File file = new File(filePath);
            playing = true;

            while (playing) {
                try (AudioInputStream in = getAudioInputStream(file)) {
                    AudioFormat outFormat = getOutFormat(in.getFormat());
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, outFormat);

                    try (SourceDataLine localLine = (SourceDataLine) AudioSystem.getLine(info)) {
                        line = localLine;
                        if (line != null) {
                            line.open(outFormat);
                            line.start();
                            stream(getAudioInputStream(outFormat, in), line);
                            line.drain();
                            line.stop();
                            line.close();
                        }
                    }

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                    break;
                }
            }

            playing = false;
        });

        playbackThread.start();
    }


    public void stop() {
        playing = false;
        if (line != null) {
            line.stop();
            line.close();
        }
        if (playbackThread != null) {
            playbackThread.interrupt(); // break sleep/read
        }
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        int channels = inFormat.getChannels();
        float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, channels, channels * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while (playing && (bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
            line.write(buffer, 0, bytesRead);
        }
    }
}
