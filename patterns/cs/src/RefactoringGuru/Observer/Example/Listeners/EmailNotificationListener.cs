using System;
using System.IO;

namespace RefactoringGuru.Observer.Example.Listeners
{
    class EmailNotificationListener : EventListener
    {
        private String email;

        public EmailNotificationListener(String email)
        {
            this.email = email;
        }
        public void Update(string eventType, FileInfo file)
        {
            Console.WriteLine("Email to " + email + ": Someone has performed " + eventType + " operation with the following file: " + file.Name);
        }
    }
}
