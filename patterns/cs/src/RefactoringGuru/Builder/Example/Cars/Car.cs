using RefactoringGuru.Builder.Example.Components;

namespace RefactoringGuru.Builder.Example.Cars
{
   /** 
    * EN: Car is a product class.
    * 
    * RU: Автомобиль — это класс продукта.
    */
    public class Car
    {
        private Cars.Type type;
        private int seats;
        private Engine engine;
        private Transmission transmission;
        private TripComputer tripComputer;
        private GPSNavigator gpsNavigator;
        private static double fuel;

        public Car(Cars.Type type, int seats, Engine engine, Transmission transmission, TripComputer tripComputer, GPSNavigator gpsNavigator)
        {
            this.type = type;
            this.seats = seats;
            this.engine = engine;
            this.transmission = transmission;
            this.tripComputer = tripComputer;
            this.gpsNavigator = gpsNavigator;
        }

        public void SetFuel(double value)
        {
            fuel = value;
        }

        public Type GetType()
        {
            return type;
        }

        public int GetSeats()
        {
            return seats;
        }

        public Engine GetEngine()
        {
            return engine;
        }

        public Transmission GetTransmission()
        {
            return transmission;
        }

        public TripComputer GetTripComputer()
        {
            return tripComputer;
        }

        public GPSNavigator GetGpsNavigator()
        {
            return gpsNavigator;
        }

        public static double GetFuel()
        {
            return fuel;
        }
    }
}
