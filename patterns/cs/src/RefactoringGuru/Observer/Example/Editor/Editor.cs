using System;
using System.IO;
using RefactoringGuru.Observer.Example.Publisher;

namespace RefactoringGuru.Observer.Example.Editor
{
    class Editor
    {
        public EventManager events;
        private FileInfo file;

        public Editor()
        {
            String[] args = { "open", "save" };
            this.events = new EventManager(args);
        }

        public void OpenFile(String filePath)
        {
            this.file = new FileInfo(filePath);
            events.Notify("open", file);
        }

        public void SaveFile()
        {
        if (this.file != null) {
                events.Notify("save", file);
            } else {
                throw new Exception("Please open a file first.");
            }
        }
    }
}
