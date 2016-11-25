// EN: Base prototype.
// 
// RU: Базовый прототип.
abstract class Shape is
    field X: int
    field Y: int
    field color: string

    // EN: A fresh object is initialized with values from the old object in
    // the constructor.
    // 
    // RU: Копирование всех полей объекта происходит в конструкторе.
    method Shape(target: Shape) is
        if (target != null) then
            this.X = target.X;
            this.Y = target.Y;
            this.color = target.color;

    // EN: Clone operation always returns one of the Shape subclasses.
    // 
    // RU: Результатом операции клонирования всегда будет объект из иерархии
    // классов Shape.
    abstract method clone(): Shape


// EN: Concrete prototype. Cloning method creates a new object and passes itself
// to the constructor. Until constructor is finished, has a reference to a fresh
// clone. Therefore, nobody has access to a partly built clone. This helps to
// make the cloning result consistent.
// 
// RU: Конкретный прототип. Метод клонирования создаёт новый объект и передаёт в
// его конструктор для копирования собственный объект. Этим мы пытаемся получить
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


// EN: Somewhere in client code.
// 
// RU: Где-то в клиентском коде.
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
        // EN: anotherCircle is the exact copy of circle.
        // 
        // RU: anotherCircle будет содержать точную копию circle.

        Rectangle rectangle = new Rectangle();
        rectangle.width = 10
        rectangle.height = 20
        shapes.add(rectangle);

    method businessLogic() is
        // EN: Prototype rocks because it allows producing an object copy
        // without knowing anything about its type.
        // 
        // RU: Неочевидным плюс Прототипа в том, что вы можете клонировать набор
        // объектов, не зная их конкретных классов.
        Array shapesCopy = new Array of Shapes.

        // EN: For instance, we don't know exact types of elements in shapes
        // array. We know they all of them are Shapes, that's all. But thanks to
        // polymorphism, when we call a clone method, it runs the method defined
        // in the class of that object. That's why we get proper clones instead
        // of the set of simple Shape objects.
        // 
        // RU: Например, мы не знаем какие конкретно объекты находятся внутри
        // массива shapes, так как он объявлен с типом Shape. Но благодаря
        // полиморфизму, мы можем клонировать все объекты «вслепую». Будет
        // выполнен метод clone() того класса, которым является этот объект.
        foreach shapes as shape do
            shapesCopy.add(shape.clone())

        // EN: shapesCopy will contain exact copies of shape array children.
        // 
        // RU: shapesCopy будет содержать точные копии элементов массива shapes.
