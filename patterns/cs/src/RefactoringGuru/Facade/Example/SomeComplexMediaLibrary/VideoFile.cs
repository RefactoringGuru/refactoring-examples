using System;

namespace RefactoringGuru.Facade.Example.SomeComplexMediaLibrary
{
    class VideoFile
    {
        private String name;
        private String codecType;

        public VideoFile(String name)
        {
            this.name = name;
            int index = name.IndexOf('.');
            int length = name.Length - (name.IndexOf('.') + 1);
            codecType = name.Substring(index, length);
        }

        public String GetCodecType()
        {
            return codecType;
        }

        public String GetName()
        {
            return name;
        }
    }
}
