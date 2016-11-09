package refactoring_guru.patterns.fasad.example;

public class CodecFactory {
    public static Codec extract(VideoFile file) {
        String type = file.getCodecType();
        if (type.equals("mp4")) {
            return new MPEG4CompressionCodec();
        }
        return new OggCompressionCodec();
    }
}
