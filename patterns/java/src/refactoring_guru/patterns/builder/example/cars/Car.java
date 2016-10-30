package refactoring.guru.patterns.builder.example.cars;

import refactoring.guru.patterns.builder.example.components.Engine;
import refactoring.guru.patterns.builder.example.components.GPSNavigator;
import refactoring.guru.patterns.builder.example.components.Transmission;
import refactoring.guru.patterns.builder.example.components.TripComputer;

public class Car {
    private final Type type;
    private final int seats;
    private final Engine engine;
    private final Transmission transmission;
    private final TripComputer tripComputer;
    private final GPSNavigator gpsNavigator;
    private static double fuel = 0;

    public Car(Type type, int seats, Engine engine, Transmission transmission,
               TripComputer tripComputer, GPSNavigator gpsNavigator) {
        this.type = type;
        this.seats = seats;
        this.engine = engine;
        this.transmission = transmission;
        this.tripComputer = tripComputer;
        this.gpsNavigator = gpsNavigator;
    }

    // setters
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    // getters
    public Type getType() {
        return type;
    }

    public int getSeats() {
        return seats;
    }

    public Engine getEngine() {
        return engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public TripComputer getTripComputer() {
        return tripComputer;
    }

    public GPSNavigator getGpsNavigator() {
        return gpsNavigator;
    }

    public static double getFuel() {
        return fuel;
    }
}
