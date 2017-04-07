namespace RefactoringGuruBridge.Example.Devices
{
    interface IDevice
    {
        bool IsEnabled();

        void Enable();

        void Disable();

        int GetVolume();

        void SetVolume(int percent);

        int GetChannel();

        void SetChannel(int channel);

        void PrintStatus();
    }
}
