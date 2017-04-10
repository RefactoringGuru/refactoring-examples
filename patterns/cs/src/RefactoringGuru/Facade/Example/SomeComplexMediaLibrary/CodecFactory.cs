using System;

namespace RefactoringGuru.Facade.Example.SomeComplexMediaLibrary
{
    class CodecFactory
    {
        public static ICodec Extract(VideoFile file)
        {
            String type = file.GetCodecType();
            if (type.Equals("mp4"))
            {
                Console.WriteLine("CodecFactory: extracting mpeg audio...");
                return new MPEG4CompressionCodec();
            }
            else
            {
                Console.WriteLine("CodecFactory: extracting ogg audio...");
                return new OggCompressionCodec();
            }
        }
    }
}
