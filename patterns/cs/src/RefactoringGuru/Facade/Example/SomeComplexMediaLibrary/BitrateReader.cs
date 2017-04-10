using System;

namespace RefactoringGuru.Facade.Example.SomeComplexMediaLibrary
{
    class BitrateReader
    {
        public static VideoFile Read(VideoFile file, ICodec codec)
        {
            Console.WriteLine("BitrateReader: reading file...");
            return file;
        }

        public static VideoFile Convert(VideoFile buffer, ICodec codec)
        {
            Console.WriteLine("BitrateReader: writing file...");
            return buffer;
        }
    }
}
