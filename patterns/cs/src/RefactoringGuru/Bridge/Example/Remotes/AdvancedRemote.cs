using System;
using RefactoringGuru.Bridge.Example.Remotes;
using RefactoringGuruBridge.Example.Devices;

namespace RefactoringGuruBridge.Example.Remotes
{
    class AdvancedRemote : BasicRemote
    {
        public AdvancedRemote(IDevice device)
        {
            base.device = device;
        }

        public void Mute()
        {
            Console.WriteLine("Remote: mute");
            device.SetVolume(0);
        }
    }
}
