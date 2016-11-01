package refactoring.guru.patterns.builder.example.components;

import refactoring.guru.patterns.builder.example.cars.Car;

public class TripComputer {

    public void condition() {
        if (Engine.isStarted()) {
            System.out.println("Car is started");
        } else {
            System.out.println("Car isn't started");
        }
    }

    public void fuelLevel() {
        System.out.println("Level of fuel - " + Car.getFuel());
    }
}
