using RefactoringGuru.Builder.Example.Cars;
using RefactoringGuru.Builder.Example.Components;

namespace RefactoringGuru.Builder.Example.Builders
{
    /**
     * EN: Concrete builders implement steps defined in the common interface.
     * 
     * RU: Конкретные строители реализуют шаги, объявленные в общем интерфейсе.
     */
    public class CarBuilder : Builder
    {
        private Cars.Type type;
        private int seats;
        private Engine engine;
        private Transmission transmission;
        private TripComputer tripComputer;
        private GPSNavigator gpsNavigator;

        public void SetType(Cars.Type type)
        {
            this.type = type;
        }

        public void SetSeats(int seats)
        {
            this.seats = seats;
        }

        public void SetEngine(Engine engine)
        {
            this.engine = engine;
        }

        public void SetTransmission(Transmission transmission)
        {
            this.transmission = transmission;
        }

        public void SetTripComputer(TripComputer tripComputer)
        {
            this.tripComputer = tripComputer;
        }

        public void SetGpsNavigator(GPSNavigator gpsNavigator)
        {
            this.gpsNavigator = gpsNavigator;
        }

        public Car GetResult()
        {
            return new Car(type, seats, engine, transmission, tripComputer, gpsNavigator);
        }
    }
}
