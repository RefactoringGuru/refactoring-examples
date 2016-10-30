package refactoring.guru.patterns.builder.example;

import refactoring.guru.patterns.builder.example.builders.CarBuilder;
import refactoring.guru.patterns.builder.example.builders.CarManualBuilder;
import refactoring.guru.patterns.builder.example.cars.Car;
import refactoring.guru.patterns.builder.example.cars.Manual;

public class Application {
    public void constructCar() {
        Director director = new Director();

        CarBuilder builder = new CarBuilder();
        director.constructSportCar(builder);
        Car car = builder.getResult();
        System.out.println(car.getType());

        CarManualBuilder manualBuilder = new CarManualBuilder();
        director.constructCabrioletCar(manualBuilder);
        Manual manualCar = manualBuilder.getResult();
        System.out.println("\n" + manualCar.print());
    }
}
