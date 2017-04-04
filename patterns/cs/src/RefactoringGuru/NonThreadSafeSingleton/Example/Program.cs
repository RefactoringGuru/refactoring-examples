using System;
using System.Threading;

namespace NonThreadSafeSingleton.Example
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("If you see the same value, then singleton was reused (yay!)" + "\n" +
                "If you see different values, then 2 singletons were created (booo!!)" + "\n\n" +
                "RESULT:" + "\n");
            Singleton singleton = Singleton.GetInstance("FOO");
            Singleton anotherSingleton = Singleton.GetInstance("BAR");
            Console.WriteLine(singleton.value);
            Console.WriteLine(anotherSingleton.value);

            Console.ReadKey();

            Console.WriteLine("\nIf you see the same value, then singleton was reused (yay!)" + "\n" +
                "If you see different values, then 2 singletons were created (booo!!)" + "\n\n" +
                "RESULT:" + "\n");
            Thread foo = new Thread(new ThreadFoo().Run);
            Thread bar = new Thread(new ThreadBar().Run);
            foo.Start();
            bar.Start();

            Console.ReadKey();
        }

        internal class ThreadFoo
        {
            public void Run()
            {
                Singleton singleton = Singleton.GetInstance("FOO");
                Console.WriteLine(singleton.value);
            }
        }

        internal class ThreadBar
        {
            public void Run()
            {
                Singleton singleton = Singleton.GetInstance("BAR");
                Console.WriteLine(singleton.value);
            }
        }
    }
}
