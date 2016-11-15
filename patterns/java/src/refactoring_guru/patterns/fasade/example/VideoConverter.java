package refactoring_guru.patterns.fasade.example;

import refactoring_guru.patterns.fasade.example.some_media_library.*;

import java.io.File;

public class VideoConverter {
    public File convertVideo(String fileName, String format) {
        VideoFile file = new VideoFile(fileName);
        Codec sourceCodec = CodecFactory.extract(file);
        Codec destinationCodec;
        if (format.equals("mp4")) {
            destinationCodec = new OggCompressionCodec();
        } else {
            destinationCodec = new MPEG4CompressionCodec();
        }
        VideoFile buffer = BitrateReader.read(file, sourceCodec);
        VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
        File result = (new AudioMixer()).fix(intermediateResult);
        return result;
    }
}
