using RefactoringGuru.Builder.Example.Components;

namespace RefactoringGuru.Builder.Example.Builders
{
    /**
     * EN: Builder interface defines all possible ways to configure a product.
     * 
     * RU: Интерфейс Строителя объявляет все возможные этапы и шаги
     * конфигурации продукта.
     */
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
