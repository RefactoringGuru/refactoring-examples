using System;
using RefactoringGuru.Observer.Example.Editor;
using RefactoringGuru.Observer.Example.Listeners;

namespace RefactoringGuru.Observer
{
    class Program
    {
        static void Main(string[] args)
        {
            Editor editor = new Editor();
            editor.events.Subscribe("open", new LogOpenListener("/path/to/log/file.txt"));
            editor.events.Subscribe("save", new EmailNotificationListener("admin@example.com"));

            editor.OpenFile("test.txt");
            editor.SaveFile();

            Console.ReadKey();
        }
    }
}
