using Builder.Example.Components;

namespace Builder.Example.Director
{
    /// EN: Director defines the order of building steps. It works with a builder
	/// object through common Builder interface. Therefore it may not know what
	/// product is being built.
	/// 
	/// RU: Директор знает в какой последовательности заставлять работать строителя.
	/// Он работает с ним через общий интерфейс Строителя. Из-за этого, он может не
	/// знать какой конкретно продукт сейчас строится.
	public class Director
    {
        public void ConstructSportsCar(Builders.Builder builder)
        {
            builder.SetType(Cars.Type.SPORTS_CAR);
            builder.SetSeats(2);
            builder.SetEngine(new Engine(3.0, 0));
            builder.SetTransmission(Transmission.SEMI_AUTOMATIC);
            builder.SetTripComputer(new TripComputer());
            builder.SetGpsNavigator(new GPSNavigator());
        }

        public void ConstructCityCar(Builders.Builder builder)
        {
            builder.SetType(Cars.Type.CITY_CAR);
            builder.SetSeats(2);
            builder.SetEngine(new Engine(1.2, 0));
            builder.SetTransmission(Transmission.AUTOMATIC);
            builder.SetTripComputer(new TripComputer());
            builder.SetGpsNavigator(new GPSNavigator());
        }

        public virtual void ConstructSuv(Builders.Builder builder)
        {
            builder.SetType(Cars.Type.SUV);
            builder.SetSeats(4);
            builder.SetEngine(new Engine(2.5, 0));
            builder.SetTransmission(Transmission.MANUAL);
            builder.SetGpsNavigator(new GPSNavigator());
        }
    }
}
