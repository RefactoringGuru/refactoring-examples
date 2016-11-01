package refactoring.guru.patterns.builder.example;

import refactoring.guru.patterns.builder.example.builders.Builder;
import refactoring.guru.patterns.builder.example.cars.Type;
import refactoring.guru.patterns.builder.example.components.Engine;
import refactoring.guru.patterns.builder.example.components.GPSNavigator;
import refactoring.guru.patterns.builder.example.components.Transmission;
import refactoring.guru.patterns.builder.example.components.TripComputer;

public class Director {

    public void constructSportCar(Builder builder) {
        builder.setType(Type.SPORT_CAR);
        builder.setSeats(2);
        builder.setEngine(new Engine(3.0, 0));
        builder.setTransmission(Transmission.SEMI_AUTOMATIC);
        builder.setTripComputer(new TripComputer());
        builder.setGPSNavigator(new GPSNavigator());
    }

    public void constructCityCar(Builder builder) {
        builder.setType(Type.CITY_CAR);
        builder.setSeats(5);
        builder.setEngine(new Engine(1.8, 0));
        builder.setTransmission(Transmission.MANUAL);
        builder.setTripComputer(new TripComputer());
        builder.setGPSNavigator(new GPSNavigator());
    }

    public void constructCabrioletCar(Builder builder) {
        builder.setType(Type.CABRIOLET);
        builder.setSeats(4);
        builder.setEngine(new Engine(2.5, 0));
        builder.setTransmission(Transmission.AUTOMATIC);
        builder.setTripComputer(new TripComputer());
        builder.setGPSNavigator(new GPSNavigator());
    }
}
