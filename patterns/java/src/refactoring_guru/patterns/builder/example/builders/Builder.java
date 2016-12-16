package refactoring_guru.patterns.builder.example.builders;

import refactoring_guru.patterns.builder.example.cars.Type;
import refactoring_guru.patterns.builder.example.components.*;

/**
 * EN: Builder interface defines all possible ways to configure a product.
 * 
 * RU: Интерфейс Строителя объявляет все возможные этапы и шаги
 * конфигурации продукта.
 */
public interface Builder {
    public void setType(Type type);
    public void setSeats(int seats);
    public void setEngine (Engine engine);
    public void setTransmission(Transmission transmission);
    public void setTripComputer(TripComputer tripComputer);
    public void setGPSNavigator(GPSNavigator gpsNavigator);
}
