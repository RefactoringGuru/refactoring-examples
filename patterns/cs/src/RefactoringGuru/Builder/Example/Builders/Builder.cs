using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Builder.Example.Components;

namespace Builder.Example.Builders
{
    public interface Builder
    {
        void SetType(Cars.Type type);
        void SetSeats(int seats);
        void SetEngine(Engine engine);
        void SetTransmission(Transmission transmission);
        void SetTripComputer(TripComputer tripComputer);
        void SetGpsNavigator(GPSNavigator gpsNavigator);
    }
}
