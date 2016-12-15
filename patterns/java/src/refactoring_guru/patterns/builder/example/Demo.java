package refactoring_guru.patterns.builder.example;

import refactoring_guru.patterns.builder.example.builders.CarBuilder;
import refactoring_guru.patterns.builder.example.builders.CarManualBuilder;
import refactoring_guru.patterns.builder.example.cars.Car;
import refactoring_guru.patterns.builder.example.cars.Manual;

public class Demo {
    public static void main(String[] args) {
        Director director = new Director();

        CarBuilder builder = new CarBuilder();
        director.constructSportsCar(builder);
        Car car = builder.getResult();
        System.out.println("Car built:\n" + car.getType());

        CarManualBuilder manualBuilder = new CarManualBuilder();
        director.constructSportsCar(manualBuilder);
        Manual carManual = manualBuilder.getResult();
        System.out.println("\nCar manual built:\n" + carManual.print());
    }
}
