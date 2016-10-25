// Строитель может создавать различные продукты, используя один и тот же
// процесс строительства.
class Car is
    Can have GPS, trip computer and various numbers of seats.
    Can be a city car, a sports car, or a cabriolet.

class Manual is
    Textual representation of a car.


// Вы задаете общие шаги строительства интерфейсе Строителя.
interface Builder is
    method setSeats(number)
    method setEngine(engine: Engine)
    method setTripComputer()
    method setGPS()


// Все конкретные строители реализуют общий интерфейс по-своему.
class CarBuilder implements Builder is
    method setSeats(number) is
        Tell the builder the number of seats.
    method setEngine(engine: Engine) is
        Tell the builder the number of seats.
    method setTripComputer() is
        Install a trip computer.
    method setGPS() is
        Install a global positioning system.
    method getResult(): RealCar is
        Construct and return a real car.


class CarManualBuilder implements Builder is
    method setSeats(number) is
        Document car seats features.
    method setEngine(engine: Engine) is
        Add an engine instruction.
    method setTripComputer() is
        Add a trip computer instruction.
    method setGPS() is
        Add GPS instruction.
    method getResult(): Manual is
        Get manual contents.

// Директор знает в какой последовательности заставлять работать Строителя.
// Но не знает какой продукт получится в итоге.
class Director is
    method constructSportsCar(builder: Builder) is
        builder.setSeats(2)
        builder.setEngine(new SportEngine())
        builder.setTripComputer()
        builder.setGPS()


// Директор получает объект строителя от клиента (приложения). Приложение
// само знает какой строитель использовать, чтобы получить нужный продукт.
class Application
    method makeCar is
        director = new Director();

        CarBuilder builder = new CarBuilder();
        director.constructSportsCar(builder);
        Car car = builder.getResult();

        CarManualBuilder builder = new CarManualBuilder();
        director.constructSportsCar(builder);

        // Готовый продукт возвращает строитель, так как другие классы не имеют
        // привязки к конкретным продуктам.
        Manual manual = builder.getResult();
