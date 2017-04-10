using System;
using System.IO;
using RefactoringGuru.Facade.Example.SomeComplexMediaLibrary;

namespace RefactoringGuru.Facade.Example.Facade
{
    class VideoConversionFacade
    {
        public FileInfo ConvertVideo(String fileName, String format)
        {
            Console.WriteLine("VideoConversionFacade: conversion started.");
            VideoFile file = new VideoFile(fileName);
            ICodec sourceCodec = CodecFactory.Extract(file);
            ICodec destinationCodec;
            if (format.Equals("mp4"))
            {
                destinationCodec = new OggCompressionCodec();
            }
            else
            {
                destinationCodec = new MPEG4CompressionCodec();
            }
            VideoFile buffer = BitrateReader.Read(file, sourceCodec);
            VideoFile intermediateResult = BitrateReader.Convert(buffer, destinationCodec);
            FileInfo result = (new AudioMixer()).Fix(intermediateResult);
            Console.WriteLine("VideoConversionFacade: conversion completed.");
            Console.ReadKey();
            return result;
        }
    }
}
