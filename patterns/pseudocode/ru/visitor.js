// Сложная иерархия компонентов.
interface Graphic is
    method move(x, y)
    method draw()
    method accept(v: Visitor)

class Shape implements Graphic is
    field id

    // Метод принятия посетителя должен быть реализован в каждом компоненте, а
    // не только в базовом классе. Это поможет программе определить какой метод
    // посетителя нужно вызвать, в случае если вы не знаете тип компонента.
    method accept(v: Visitor) is
        v.visitDot(this);

class Dot extends Shape is
    field x, y
    // ...
    method accept(v: Visitor) is
        v.visitDot(this);

class Circle extends Dot is
    field radius
    // ...
    method accept(v: Visitor) is
        v.visitCircle(this);

class Rectangle extends Shape is
    field width, height
    // ...
    method accept(v: Visitor) is
        v.visitRectangle(this);

class CompoundGraphic implements Graphic is
    field children: array of Graphic
    // ...
    method accept(v: Visitor) is
        v.visitCompoundGraphic(this);


// Интерфейс посетителей должен содержать методы посещения каждого компонента.
// Важно, чтобы иерархия компонентов менялась редко, так как при добавлении
// нового компонента придётся менять всех существующих посетителей.
interface Visitor is
    method visitDot(d: Dot)
    method visitCircle(c: Circle)
    method visitRectangle(r: Rectangle)
    method visitCompoundGraphic(cs: CompoundGraphic)

// Конкретный посетитель реализует одну операцию для всей иерархии компонентов.
// Новая операция = новый посетитель. Посетитель выгодно применять, когда новые
// компоненты добавляются очень редко, а команды добавляются очень часто.
class XMLExportVisitor is
    method visitDot(d: Dot) is
        Export dot's id and center coordinates.

    method visitCircle(c: Circle) is
        Export circle's id, center coordinates and radius.

    method visitRectangle(r: Rectangle) is
        Export rectangle's id, left-top coordinates, width and height.

    method visitCompoundGraphic(cg: CompoundGraphic) is
        Export shape's id and the list of children ids.


// Приложение может применять посетителя к любому набору объектов компонентов,
// даже не уточняя их типы. Нужный метод посетителя будет выбран благодаря
// проходу через метод accept.
class Application is
    field allGraphics: array of Graphic

    method export() is
        exportVisitor = new XMLExportVisitor()

        foreach (allGraphics as graphic)
            graphics.accept(exportVisitor)
