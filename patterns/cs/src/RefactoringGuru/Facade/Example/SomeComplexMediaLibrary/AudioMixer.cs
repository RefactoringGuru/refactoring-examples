using System;
using System.IO;

namespace RefactoringGuru.Facade.Example.SomeComplexMediaLibrary
{
    class AudioMixer
    {
        public FileInfo Fix(VideoFile result)
        {
            Console.WriteLine("AudioMixer: fixing audio...");
            return new FileInfo("tmp");
        }
    }
}
