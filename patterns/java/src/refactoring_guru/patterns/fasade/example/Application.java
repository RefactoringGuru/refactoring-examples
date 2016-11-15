package refactoring_guru.patterns.fasade.example;

public class Application {
    public static void main(String[] args) {
        VideoConverter converter = new VideoConverter();
        File mp4Video = converter.convertVideo("youtubevideo.ogg", "mp4");
    }
}
