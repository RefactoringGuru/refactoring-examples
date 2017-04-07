using System;
using RefactoringGuruBridge.Example.Devices;

namespace RefactoringGuru.Bridge.Example.Remotes
{
    class BasicRemote : IRemote
    {
        protected IDevice device;

        public BasicRemote() { }

        public BasicRemote(IDevice device)
        {
            this.device = device;
        }

        public void Power()
        {
            Console.WriteLine("Remote: power toggle");
            if (device.IsEnabled())
            {
                device.Disable();
            }
            else
            {
                device.Enable();
            }
        }

        public void VolumeUp()
        {
            Console.WriteLine("Remote: volume up");
            device.SetVolume(device.GetVolume() + 10);
        }

        public void VolumeDown()
        {
            Console.WriteLine("Remote: volume down");
            device.SetVolume(device.GetVolume() - 10);
        }

        public void ChannelDown()
        {
            Console.WriteLine("Remote: channel down");
            device.SetChannel(device.GetChannel() - 1);
        }

        public void ChannelUp()
        {
            Console.WriteLine("Remote: channel up");
            device.SetChannel(device.GetChannel() + 1);
        }
    }
}
