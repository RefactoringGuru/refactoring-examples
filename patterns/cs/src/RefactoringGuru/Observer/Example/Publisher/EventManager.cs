using System;
using System.Collections.Generic;
using System.IO;
using RefactoringGuru.Observer.Example.Listeners;

namespace RefactoringGuru.Observer.Example.Publisher
{
    class EventManager
    {
        Dictionary<String, List<EventListener>> listeners = new Dictionary<string, List<EventListener>>();

        public EventManager(String[] operations)
        {
            foreach (string str in operations)
            {
                this.listeners.Add(str, new List<EventListener>());
            }
        }

        public void Subscribe(String eventType, EventListener listener)
        {
            List<EventListener> users = listeners[eventType];
            users.Add(listener);
        }

        public void Unsubscribe(String eventType, EventListener listener)
        {
            List<EventListener> users = listeners[eventType];
            int index = users.IndexOf(listener);
            users.RemoveAt(index);
        }

        public void Notify(String eventType, FileInfo file)
        {
            IList<EventListener> users = listeners[eventType];
            foreach (var listener in users)
            {
                listener.Update(eventType, file);
            }
        }
    }
}
