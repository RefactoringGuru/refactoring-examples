using System;
using System.IO;

namespace RefactoringGuru.Observer.Example.Listeners
{
    interface EventListener
    {
        void Update(String eventType, FileInfo file);
    }
}
