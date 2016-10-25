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

    // Результатом операции клонирования всегда будет объект из
    // иерархии классов Shape.
    abstract method clone(): Shape

// Конкретный прототип. Метод клонирования создаёт новый объект и передает
// в его конструктор для копирования собственный объект.
// Этим мы пытаемся получить атомарность операции клонирования. В данной
// реализации, пока не выполнится конструктор, нового объекта еще не
// существует. Но как только конструктор завершен, мы получаем полностью
// готовый объект-клон, а не пустой объект, который нужно ещё заполнить.
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
    method main() is
        Circle circle = new Circle();
        circle.X = 10
        circle.Y = 20
        circle.radius = 15

        Shape newShape = circle.clone()
        // newShape будет полной копией объекта circle
