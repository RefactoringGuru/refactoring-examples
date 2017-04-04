using System;
using Builder.Example.Builders;
using Builder.Example.Cars;
using Builder.Example.Director;

namespace Builder
{
    /**
     * EN: Demo class. Everything comes together here.
     * 
     * RU: Демо-класс. Здесь всё сводится воедино.
     */
    class Program
    {
        public static void Main()
        {
            Director director = new Director();

            // EN: Director gets the concrete builder object from the client
            // (application code). That's because application knows better which
            // builder to use to get a specific product.
            // 
            // RU: Директор получает объект конкретного строителя от клиента
            // (приложения). Приложение само знает какой строитель использовать,
            // чтобы получить нужный продукт.
            CarBuilder builder = new CarBuilder();
            director.ConstructSportsCar(builder);

            // EN: The final product is often retrieved from a builder object, since
            // Director is not aware and not dependent on concrete builders
            // and products.
            // 
            // RU: Готовый продукт возвращает строитель, так как Директор чаще всего
            // не знает и не зависит от конкретных классов строителей и продуктов.
            Car car = builder.GetResult();
            Console.WriteLine("Car built:\n" + car.GetType());

            CarManualBuilder manualBuilder = new CarManualBuilder();

            // EN: Director may know several building recipes.
            // 
            // RU: Директор может знать больше одного рецепта строительства.
            director.ConstructSuv(manualBuilder);
            Manual carManual = manualBuilder.getResult();
            Console.WriteLine("\nCar manual built:\n" + carManual.Print());

            Console.ReadKey();
        }
    }
}
