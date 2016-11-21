// Классы сложного фреймворка конвертации видео.

class VideoFile
// ...

class OggCompressionCodec
// ...

class MPEG4CompressionCodec
// ...

class CodecFactory
// ...

class BitrateReader
// ...

class AudioMixer
// ...


// Фасад, облегчающий работу с видео-фреймворком. Фасад не имеет всей
// функциональности фреймворка, но зато скрывает его сложность от клиентов.
class VideoConvertor is
    method convertVideo(filename, format):File is
        file = new VideoFile(filename)
        sourceCodec = new CodecFactory.extract(file)
        if (format == "mp4")
          distinationCodec = new MPEG4CompressionCodec()
        else
          distinationCodec = new OggCompressionCodec()
        buffer = BitrateReader.read(filename, sourceCodec);
        result = BitrateReader.convert(buffer, distinationCodec);
        result = (new AudioMixer()).fix(result);
        return new File(result)

// Приложение не зависит от сложного фреймворка конвертации видео.
class Application is
    method main() is
        convertor = new VideoConvertor();
        mp4video = convertor.convertVideo("youtubevideo.ogg", "mp4")
