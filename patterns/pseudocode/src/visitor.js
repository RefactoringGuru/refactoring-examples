// EN: A complex hierarchy of components.
// 
// RU: Сложная иерархия компонентов.
interface Graphic is
    method move(x, y)
    method draw()
    method accept(v: Visitor)

class Shape implements Graphic is
    field id

    // EN: It is crucial to implement the accept() method in every single
    // component, not just a base class. It helps the program to pick a proper
    // method on the visitor class in case if a given component's type
    // is unknow.
    // 
    // RU: Метод принятия посетителя должен быть реализован в каждом компоненте,
    // а не только в базовом классе. Это поможет программе определить какой
    // метод посетителя нужно вызвать, в случае если вы не знаете
    // тип компонента.
    method accept(v: Visitor) is
        v.visitDot(this);

class Dot extends Shape is
    field x, y
    // EN: ...
    // 
    // RU: ...
    method accept(v: Visitor) is
        v.visitDot(this);

class Circle extends Dot is
    field radius
    // EN: ...
    // 
    // RU: ...
    method accept(v: Visitor) is
        v.visitCircle(this);

class Rectangle extends Shape is
    field width, height
    // EN: ...
    // 
    // RU: ...
    method accept(v: Visitor) is
        v.visitRectangle(this);

class CompoundGraphic implements Graphic is
    field children: array of Graphic
    // EN: ...
    // 
    // RU: ...
    method accept(v: Visitor) is
        v.visitCompoundGraphic(this);


// EN: Visitor interface must have visiting methods for the every single
// component. Note that each time you add a new class to the component history,
// you will need to add a method to the visitor classes. In this case, you might
// consider avoiding visitor altogether.
// 
// RU: Интерфейс посетителей должен содержать методы посещения каждого
// компонента. Важно, чтобы иерархия компонентов менялась редко, так как при
// добавлении нового компонента придётся менять всех существующих посетителей.
interface Visitor is
    method visitDot(d: Dot)
    method visitCircle(c: Circle)
    method visitRectangle(r: Rectangle)
    method visitCompoundGraphic(cs: CompoundGraphic)

// EN: Concrete visitor adds a single operation to the entire hierarchy of
// components. Which means that if you need to add multiple operations, you will
// have to create several visitor.
// 
// RU: Конкретный посетитель реализует одну операцию для всей иерархии
// компонентов. Новая операция = новый посетитель. Посетитель выгодно применять,
// когда новые компоненты добавляются очень редко, а команды добавляются
// очень часто.
class XMLExportVisitor is
    method visitDot(d: Dot) is
        Export dot's id and center coordinates.

    method visitCircle(c: Circle) is
        Export circle's id, center coordinates and radius.

    method visitRectangle(r: Rectangle) is
        Export rectangle's id, left-top coordinates, width and height.

    method visitCompoundGraphic(cg: CompoundGraphic) is
        Export shape's id and the list of children ids.


// EN: Application can use visitor along with any set of components without
// checking their type first. Double dispatch mechanism guarantees that a proper
// visiting method will be called for any given component.
// 
// RU: Приложение может применять посетителя к любому набору объектов
// компонентов, даже не уточняя их типы. Нужный метод посетителя будет выбран
// благодаря проходу через метод accept.
class Application is
    field allGraphics: array of Graphic

    method export() is
        exportVisitor = new XMLExportVisitor()

        foreach (allGraphics as graphic)
            graphics.accept(exportVisitor)
