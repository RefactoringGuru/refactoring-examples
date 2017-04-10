using System;
using System.IO;
using RefactoringGuru.Facade.Example.Facade;

namespace RefactoringGuru.Facade
{
    class Program
    {
        static void Main(string[] args)
        {
            VideoConversionFacade converter = new VideoConversionFacade();
            FileInfo mp4Video = converter.ConvertVideo("youtubevideo.ogg", "mp4");
            // ...
        }
    }
}
