using RefactoringGuru.Builder.Example.Cars;
using RefactoringGuru.Builder.Example.Components;

namespace RefactoringGuru.Builder.Example.Builders
{
    /**
     * EN: Unlike other creational patterns, Builder can construct unrelated
     * products, which don't have the common interface.
     * 
     * In this case we build a user manual for a car, using the same steps as we
     * built a car. This allows to produce manuals for specific car models,
     * configured with different features.
     * 
     * RU: В отличие от других создающих паттернов, Строители могут создавать
     * совершенно разные продукты, не имеющие общего интерфейса.
     * 
     * В данном случае мы производим руководство пользователя автомобиля с помощью
     * тех же шагов, что и сами автомобили. Это устройство позволит создавать
     * руководства под конкретные модели автомобилей, содержащие те или иные фичи.
     */
    public class CarManualBuilder : Builder
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

        public Manual getResult()
        {
            return new Manual(type, seats, engine, transmission, tripComputer, gpsNavigator);
        }
    }
}
