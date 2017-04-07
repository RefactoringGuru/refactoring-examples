using System;
using RefactoringGuru.Bridge.Example.Devices;
using RefactoringGuru.Bridge.Example.Remotes;
using RefactoringGuruBridge.Example.Devices;
using RefactoringGuruBridge.Example.Remotes;

namespace Bridge
{
    class Program
    {
        static void Main(string[] args)
        {
            TestDevice(new TV());
            TestDevice(new Radio());

            Console.ReadKey();
        }

        public static void TestDevice(IDevice device)
        {
            Console.WriteLine("Tests with basic remote.");
            BasicRemote basicRemote = new BasicRemote(device);
            basicRemote.Power();
            device.PrintStatus();

            Console.WriteLine("Tests with advanced remote.");
            AdvancedRemote advancedRemote = new AdvancedRemote(device);
            advancedRemote.Power();
            advancedRemote.Mute();
            device.PrintStatus();
        }
    }
}
