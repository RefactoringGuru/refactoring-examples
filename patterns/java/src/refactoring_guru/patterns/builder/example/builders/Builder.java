package refactoring_guru.patterns.builder.example.builders;

import refactoring_guru.patterns.builder.example.cars.Type;
import refactoring_guru.patterns.builder.example.components.*;

public interface Builder {
    public void setType(Type type);
    public void setSeats(int seats);
    public void setEngine (Engine engine);
    public void setTransmission(Transmission transmission);
    public void setTripComputer(TripComputer tripComputer);
    public void setGPSNavigator(GPSNavigator gpsNavigator);
}
