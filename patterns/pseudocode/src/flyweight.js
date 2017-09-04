// EN: The Flyweight class contains only a portion of state that describes a
// tree. These field store values that hardly unique for each particular tree.
// You won't find here tree coordinates, however textures and colors shared
// between multiple are here. And since this data is usually BIG, you'd waste a
// lot of memory by keeping it in each tree object. That's why we extract
// texture, colors and other data to a separate flyweight class that can be
// referenced from all those similar trees.
// 
// RU: Этот класс-легковес содержит часть полей, которые описывают деревья. Эти
// поля не уникальные для каждого дерева в отличие, например, от координат —
// несколько деревьев могут иметь ту же текстуру. Поэтому мы переносим
// повторяющиеся данные в один единственный объект и ссылаемся на него из
// конкретных деревьев.
class TreeType is
    field name
    field color
    field texture
    constructor Tree(name, color, texture) { ... }
    method draw(canvas, x, y) is
        Create a bitmap from type, color and texture.
        Draw bitmap on canvas at X and Y.

// EN: Flyweight factory decides whether to re-use existing flyweight or create
// a new object.
// 
// RU: Фабрика легковесов решает когда нужно создать новый легковес, а когда
// можно обойтись существующим.
class TreeFactory is
    static field treeTypes: collection of tree types
    static method getTreeType(name, color, texture) is
        type = treeTypes.find(name, color, texture)
        if (type == null)
            type = new TreeType(name, color, texture)
            treeTypes.add(type)
        return type

// EN: Context object contains extrinsic part of tree state. Application can
// create billions of these since they are pretty thin: just two integer
// coordinates and one reference.
// 
// RU: Контекстный объект, из которого мы выделили легковес TreeType. В
// программе могут быть тысячи объектов Tree, так как накладные расходы на их
// хранение совсем небольшие — порядка трёх целых чисел (две координаты
// и ссылка).
class Tree is
    field x,y
    field type: TreeType
    constructor Tree(x, y, type) { ... }
    method draw(canvas) is
        type.draw(canvas, this.x, this.y)

// EN: Tree and Forest classes are Flyweight's clients. You might merge them
// together if you don't plan to develop a Tree class any further.
// 
// RU: Классы Tree и Forest являются клиентами Легковеса. При желании их можно
// слить в один класс, если класс Tree нет нужды расширять далее.
class Forest is
    field trees: collection of Trees

    method plantTree(x, y, name, color, texture) is
        type = TreeFactory.getTreeType(name, color, texture)
        tree = new Tree(x, y, type);
        trees.add(tree)

    method draw(canvas) is
        foreach tree in trees
            tree.draw(canvas)
