import java.util.Scanner;

// Core Interface for Music Sources (Adapter Target)
interface MusicSource {
    void play();
    String getDescription();
}

// Concrete Music Source: Local File (Adaptee)
class LocalFile {
    private String filePath;

    public LocalFile(String filePath) {
        this.filePath = filePath;
    }

    public void playLocal() {
        System.out.println("Playing local file: " + filePath);
    }
}

// Adapter for Local File
class LocalFileAdapter implements MusicSource {
    private LocalFile localFile;

    public LocalFileAdapter(LocalFile localFile) {
        this.localFile = localFile;
    }

    @Override
    public void play() {
        localFile.playLocal();
    }

    @Override
    public String getDescription() {
        return "Local File";
    }
}

// Concrete Music Source: Spotify (Adaptee)
class SpotifyService {
    private String trackId;

    public SpotifyService(String trackId) {
        this.trackId = trackId;
    }

    public void streamTrack() {
        System.out.println("Streaming Spotify track ID: " + trackId);
    }
}

// Adapter for Spotify
class SpotifyAdapter implements MusicSource {
    private SpotifyService spotify;

    public SpotifyAdapter(SpotifyService spotify) {
        this.spotify = spotify;
    }

    @Override
    public void play() {
        spotify.streamTrack();
    }

    @Override
    public String getDescription() {
        return "Spotify Track";
    }
}

// Concrete Music Source: Radio Station (Adaptee)
class RadioStation {
    private String frequency;

    public RadioStation(String frequency) {
        this.frequency = frequency;
    }

    public void tuneIn() {
        System.out.println("Tuning into radio station at frequency: " + frequency + " MHz");
    }
}

// Adapter for Radio Station
class RadioAdapter implements MusicSource {
    private RadioStation radio;

    public RadioAdapter(RadioStation radio) {
        this.radio = radio;
    }

    @Override
    public void play() {
        radio.tuneIn();
    }

    @Override
    public String getDescription() {
        return "Radio Station";
    }
}

// Decorator: Abstract Music Source Decorator
abstract class MusicSourceDecorator implements MusicSource {
    protected MusicSource decoratedSource;

    public MusicSourceDecorator(MusicSource source) {
        this.decoratedSource = source;
    }

    @Override
    public void play() {
        decoratedSource.play();
    }

    @Override
    public String getDescription() {
        return decoratedSource.getDescription();
    }
}

// Concrete Decorator: Equalizer
class EqualizerDecorator extends MusicSourceDecorator {
    public EqualizerDecorator(MusicSource source) {
        super(source);
    }

    @Override
    public void play() {
        super.play();
        applyEqualizer();
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " with Equalizer";
    }

    private void applyEqualizer() {
        System.out.println("Applying equalizer settings...");
    }
}

// Concrete Decorator: Volume Boost
class VolumeBoostDecorator extends MusicSourceDecorator {
    public VolumeBoostDecorator(MusicSource source) {
        super(source);
    }

    @Override
    public void play() {
        super.play();
        boostVolume();
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " with Volume Boost";
    }

    private void boostVolume() {
        System.out.println("Boosting volume...");
    }
}

// Facade: MusicPlayerFacade
class MusicPlayerFacade {
    private MusicSource musicSource;

    public void setMusicSource(MusicSource source) {
        this.musicSource = source;
    }

    public void playMusic() {
        if (musicSource != null) {
            System.out.println("Now playing: " + musicSource.getDescription());
            musicSource.play();
        } else {
            System.out.println("No music source selected.");
        }
    }

    public void playWithEffects(boolean useEqualizer, boolean useVolumeBoost) {
        if (musicSource == null) {
            System.out.println("No music source selected.");
            return;
        }
        MusicSource enhancedSource = musicSource;
        if (useEqualizer) {
            enhancedSource = new EqualizerDecorator(enhancedSource);
        }
        if (useVolumeBoost) {
            enhancedSource = new VolumeBoostDecorator(enhancedSource);
        }
        System.out.println("Now playing: " + enhancedSource.getDescription());
        enhancedSource.play();
    }
}

// Main Class with User Input
public class MusicStreamingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MusicPlayerFacade player = new MusicPlayerFacade();

        System.out.println("Choose music source (Local, Spotify, Radio): ");
        String sourceType = scanner.nextLine();

        MusicSource source = null;
        switch (sourceType.toLowerCase()) {
            case "local":
                System.out.println("Enter file path: ");
                String filePath = scanner.nextLine();
                source = new LocalFileAdapter(new LocalFile(filePath));
                break;
            case "spotify":
                System.out.println("Enter Spotify track ID: ");
                String trackId = scanner.nextLine();
                source = new SpotifyAdapter(new SpotifyService(trackId));
                break;
            case "radio":
                System.out.println("Enter radio frequency (e.g., 98.5): ");
                String frequency = scanner.nextLine();
                source = new RadioAdapter(new RadioStation(frequency));
                break;
            default:
                System.out.println("Invalid source. Exiting.");
                scanner.close();
                return;
        }

        player.setMusicSource(source);

        System.out.println("Apply equalizer? (yes/no): ");
        boolean useEqualizer = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.println("Apply volume boost? (yes/no): ");
        boolean useVolumeBoost = scanner.nextLine().equalsIgnoreCase("yes");

        if (useEqualizer || useVolumeBoost) {
            player.playWithEffects(useEqualizer, useVolumeBoost);
        } else {
            player.playMusic();
        }

        scanner.close();
    }
}