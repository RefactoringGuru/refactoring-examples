using System;

namespace Builder.Example.Components
{
    /**
     * EN: Just another feature of a car.
     * 
     * RU: Одна из фишек автомобиля.
     */
    public class Engine
    {
        private double volume;
        private double mileage;
        private static bool started;

        public Engine(double volume, double mileage)
        {
            this.volume = volume;
            this.mileage = mileage;
        }

        public void On()
        {
            started = true;
        }

        public void Of()
        {
            started = false;
        }

        public static bool IsStarted()
        {
            return started;
        }

        public void Go(double mileage)
        {
            if (started)
            {
                this.mileage += mileage;
            }
            else
            {
                Console.Error.WriteLine("Cannot go(), you must start engine first!");
            }
        }

        public double GetVolume()
        {
            return volume;
        }

        public double GetMileage()
        {
            return mileage;
        }
    }
}
