using System;
using Builder.Example.Cars;

namespace Builder.Example.Components
{
    /// EN: Just another feature of a car.
	/// 
	/// RU: Одна из фишек автомобиля.
	public class TripComputer
    {
        public virtual void Condition()
        {
            if (Engine.IsStarted())
            {
                Console.WriteLine("Car is started");
            }
            else
            {
                Console.WriteLine("Car isn't started");
            }
        }

        public void FuelLevel()
        {
            Console.WriteLine("Level of fuel - " + Car.GetFuel());
        }
    }
}
