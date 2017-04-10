using System;
using System.IO;

namespace RefactoringGuru.Observer.Example.Listeners
{
    class LogOpenListener : EventListener
    {
        private FileInfo log;

        public LogOpenListener(String fileName)
        {
            this.log = new FileInfo(fileName);
        }
        public void Update(string eventType, FileInfo file)
        {
            Console.WriteLine("Save to log " + log + ": Someone has performed " + eventType + " operation with the following file: " + file.Name);
        }
    }
}
