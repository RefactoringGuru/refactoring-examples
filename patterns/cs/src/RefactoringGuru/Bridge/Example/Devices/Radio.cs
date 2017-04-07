using System;
using RefactoringGuruBridge.Example.Devices;

namespace RefactoringGuru.Bridge.Example.Devices
{
    class Radio : IDevice
    {
        private bool on = false;
        private int volume = 30;
        private int channel = 1;
        
        public bool IsEnabled()
        {
            return on;
        }
        
        public void Enable()
        {
            on = true;
        }
        
        public void Disable()
        {
            on = false;
        }
        
        public int GetVolume()
        {
            return volume;
        }
        
        public void SetVolume(int volume)
        {
            if (volume > 100)
            {
                volume = 100;
            }
            else if (volume < 0)
            {
                volume = 0;
            }
            this.volume = volume;
        }
        
        public int GetChannel()
        {
            return channel;
        }
        
        public void SetChannel(int channel)
        {
            this.channel = channel;
        }
        
        public void PrintStatus()
        {
            Console.WriteLine("------------------------------------");
            Console.WriteLine("| I'm radio.");
            Console.WriteLine("| I'm " + (on ? "enabled" : "disabled"));
            Console.WriteLine("| Current volume is " + volume + "%");
            Console.WriteLine("| Current channel is " + channel);
            Console.WriteLine("------------------------------------\n");
        }
    }
}
