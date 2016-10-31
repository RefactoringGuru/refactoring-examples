package refactoring.guru.patterns.builder.example.builders;

import refactoring.guru.patterns.builder.example.cars.Car;
import refactoring.guru.patterns.builder.example.cars.Type;
import refactoring.guru.patterns.builder.example.components.Engine;
import refactoring.guru.patterns.builder.example.components.GPSNavigator;
import refactoring.guru.patterns.builder.example.components.Transmission;
import refactoring.guru.patterns.builder.example.components.TripComputer;


public class CarBuilder implements Builder {
    private Type type;
    private int seats;
    private Engine engine;
    private Transmission transmission;
    private TripComputer tripComputer;
    private GPSNavigator gpsNavigator;

    // setters
    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    @Override
    public void setTripComputer(TripComputer tripComputer) {
        this.tripComputer = tripComputer;
    }

    @Override
    public void setGPSNavigator(GPSNavigator gpsNavigator) {
        this.gpsNavigator = gpsNavigator;
    }

    public Car getResult() {
        return new Car(type, seats, engine, transmission, tripComputer, gpsNavigator);
    }
}
