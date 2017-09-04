// EN: Some classes of a complex 3rd-party video conversion framework. We don't
// control that code, therefore can't simplify it.
// 
// RU: Классы сложного стороннего фреймворка конвертации видео. Мы не
// контролируем этот код, поэтому не можем его упростить.

class VideoFile
// EN: ...
// 
// RU: ...

class OggCompressionCodec
// EN: ...
// 
// RU: ...

class MPEG4CompressionCodec
// EN: ...
// 
// RU: ...

class CodecFactory
// EN: ...
// 
// RU: ...

class BitrateReader
// EN: ...
// 
// RU: ...

class AudioMixer
// EN: ...
// 
// RU: ...


// EN: To defeat the complexity, we create a Facade class, which hides all of
// the framework's complexity behind a simple interface. It is a trade-off
// between functionality and simplicity.
// 
// RU: Вместо этого, мы создаём Фасад — простой интерфейс для работы со сложным
// фреймворком. Фасад не имеет всей функциональности фреймворка, но зато
// скрывает его сложность от клиентов.
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

// EN: Application classes don't depend on a billion classes provided by the
// complex framework. Also, if you happen to decide to switch framework, you
// will only need to rewrite the facade class.
// 
// RU: Приложение не зависит от сложного фреймворка конвертации видео. Кстати,
// если вы вдруг решите сменить фреймворк, вам нужно будет переписать только
// класс фасада.
class Application is
    method main() is
        convertor = new VideoConvertor();
        mp4video = convertor.convertVideo("youtubevideo.ogg", "mp4");
        mp4video.save();
