package refactoring.guru.patterns.builder.example.builders;

import refactoring.guru.patterns.builder.example.cars.Type;
import refactoring.guru.patterns.builder.example.components.Engine;
import refactoring.guru.patterns.builder.example.components.GPSNavigator;
import refactoring.guru.patterns.builder.example.components.Transmission;
import refactoring.guru.patterns.builder.example.components.TripComputer;

public interface Builder {
    public abstract void setType(Type type);
    public abstract void setSeats(int seats);
    public abstract void setEngine (Engine engine);
    public abstract void setTransmission(Transmission transmission);
    public abstract void setTripComputer(TripComputer tripComputer);
    public abstract void setGPSNavigator(GPSNavigator gpsNavigator);
}
