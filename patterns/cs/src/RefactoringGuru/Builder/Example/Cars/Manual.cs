using System.Text;
using RefactoringGuru.Builder.Example.Components;

namespace RefactoringGuru.Builder.Example.Cars
{
    /**
     * EN: Car manual is another product. Note that it does not have the same
     * ancestor as a Car. They are not related.
     * 
     * RU: Руководство автомобиля — это второй продукт. Заметьте, что руководство и
     * сам автомобиль не имеют общего родительского класса. По сути, они независимы.
     */
    public class Manual
    {
        private Cars.Type type;
        private int seats;
        private Engine engine;
        private Transmission transmission;
        private TripComputer tripComputer;
        private GPSNavigator gpsNavigator;

        public Manual(Type type, int seats, Engine engine, Transmission transmission, TripComputer tripComputer, GPSNavigator gpsNavigator)
        {
            this.type = type;
            this.seats = seats;
            this.engine = engine;
            this.transmission = transmission;
            this.tripComputer = tripComputer;
            this.gpsNavigator = gpsNavigator;
        }

        public virtual string Print()
        {
            StringBuilder info = new StringBuilder();
            info.Append("Type of car: " + type + "\n");
            info.Append("Count of seats: " + seats + "\n");
            info.Append("Engine: volume - " + engine.GetVolume() + "; mileage - " + engine.GetMileage() + "\n");
            info.Append("Transmission: " + transmission + "\n");
            info.Append("Trip Computer: Trip Computer" + "\n");
            info.Append("GPS Navigator: GPS Navigator" + "\n");
            return info.ToString();
        }
    }
}
