// Базовый прототип.
abstract class Shape is
    field X: int
    field Y: int
    field color: string

    // Копирование всех полей объекта происходит в конструкторе.
    method Shape(target: Shape) is
        if (target != null) then
            this.X = target.X;
            this.Y = target.Y;
            this.color = target.color;

    // Результатом операции клонирования всегда будет объект из иерархии
    // классов Shape.
    abstract method clone(): Shape


// Конкретный прототип. Метод клонирования создаёт новый объект и передаёт в его
// конструктор для копирования собственный объект. Этим мы пытаемся получить
// атомарность операции клонирования. В данной реализации, пока не выполнится
// конструктор, нового объекта ещё не существует. Но как только конструктор
// завершён, мы получаем полностью готовый объект-клон, а не пустой объект,
// который нужно ещё заполнить.
class Rectangle extends Shape is
    field width: int
    field height: int

    method Rectangle(target: Rectangle) is
        Call parent constructor
        if (target != null) then
            this.width = target.width;
            this.height = target.height;

    method clone(): Shape is
        return new Rectangle(this)


class Circle extends Shape is
    field radius: int

    method Circle(target: Circle) is
        Call parent constructor
        if (target != null) then
            this.radius = target.radius;

    method clone(): Shape is
        return new Circle(this)


// Клиентский код.
class Application is
    field shapes: array of Shape

    method constructor() is
        Circle circle = new Circle();
        circle.X = 10
        circle.Y = 20
        circle.radius = 15
        shapes.add(circle);

        Circle anotherCircle = circle.clone();
        shapes.add(anotherCircle);
        // anotherCircle будет содержать точную копию circle.

        Rectangle rectangle = new Rectangle();
        rectangle.width = 10
        rectangle.height = 20
        shapes.add(rectangle);

    method businessLogic() is
        // Неочевидным плюс Прототипа в том, что вы можете клонировать набор
        // объектов не зная их конкретных классов.
        Array shapesCopy = new Array of Shapes.

        // Например, мы не знаем какие конкретно объекты находятся внутри
        // массива shapes, так как он объявлен с типом Shape. Но благодаря
        // полиморфизму, мы можем клонировать все объекты «вслепую». Будет
        // выполнен метод clone() того класса, которым является этот объект.
        foreach shapes as shape do
            shapesCopy.add(shape.clone())

        // shapesCopy будет содержать точные копии всех объектов.
