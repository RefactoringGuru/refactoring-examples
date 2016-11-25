// EN: Builder can create different products using the same building process.
// 
// RU: Строитель может создавать различные продукты, используя один и тот же
// процесс строительства.
class Car is
    Can have GPS, trip computer and various numbers of seats.
    Can be a city car, a sports car, or a cabriolet.

class Manual is
    Textual representation of a car.


// EN: You define common building steps in Builder interface.
// 
// RU: Вы задаёте общие шаги строительства в интерфейсе Строителя.
interface Builder is
    method setSeats(number)
    method setEngine(engine: Engine)
    method setTripComputer()
    method setGPS()

// EN: Concrete builders implement that interface differently.
// 
// RU: Все конкретные строители реализуют общий интерфейс по-своему.
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

// EN: Unlike other creational patterns, Builder can construct unrelated
// products, which don't have the common interface.
// 
// RU: В отличие от других создающих паттернов, Строители могут создавать
// совершенно разные продукты, не имеющие общего интерфейса.
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


// EN: Director defines the order of building steps. It works with a builder
// object through common Builder interface. Therefore it may not know what
// product is being built.
// 
// RU: Директор знает в какой последовательности заставлять работать строителя.
// Он работает с ним через общий интерфейс Строителя. Из-за этого, он может не
// знать какой продукт строится.
class Director is
    method constructSportsCar(builder: Builder) is
        builder.setSeats(2)
        builder.setEngine(new SportEngine())
        builder.setTripComputer()
        builder.setGPS()


// EN: Director gets the concrete builder object from the client (application
// code). That's because application knows better which builder to use to get a
// specific product.
// 
// RU: Директор получает объект конкретного строителя от клиента (приложения).
// Приложение само знает какой строитель использовать, чтобы получить
// нужный продукт.
class Application is
    method makeCar is
        director = new Director();

        CarBuilder builder = new CarBuilder();
        director.constructSportsCar(builder);
        Car car = builder.getResult();

        CarManualBuilder builder = new CarManualBuilder();
        director.constructSportsCar(builder);

        // EN: The final product is often retrieved from a builder object, since
        // Director is not aware and not dependent on concrete builders
        // and products.
        // 
        // RU: Готовый продукт возвращает строитель, так как Директор чаще всего
        // не знает и не зависит от конкретных классов строителей и продуктов.
        Manual manual = builder.getResult();
