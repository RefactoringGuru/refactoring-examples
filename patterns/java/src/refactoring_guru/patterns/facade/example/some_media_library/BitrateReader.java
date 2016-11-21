package refactoring_guru.patterns.facade.example.some_media_library;

public class BitrateReader {
    public static VideoFile read(VideoFile file, Codec codec) {
        // analysis
        // reading
        // some operation
        return file;
    }

    public static VideoFile convert(VideoFile buffer, Codec codec) {
        // analysis
        // some operation
        // converting
        return buffer;
    }
}
