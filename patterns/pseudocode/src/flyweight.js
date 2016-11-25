// EN: This flyweight class contains part of the fields, which describe a tree.
// These fields values are not unique for each tree; you won't find tree
// coordinates there. But several trees can have the same texture. And since
// that texture is usually BIG, you're wasting a lot of memory by keeping it
// million times for similar trees. That's why we extract texture to a separate
// object, which can be referenced from all those similar trees.
// 
// RU: Этот класс-легковес содержит часть полей, которые описывают деревья. Эти
// поля не уникальные для каждого дерева как, например, координаты. Например,
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

// EN: Flyweight factory decides when to re-use existing flyweight and when to
// create a new one.
// 
// RU: Фабрика легковесов решает когда нужно создать новый легковес, а когда
// можно обойтись существующим.
class TreeFactory is
    static field treeTypes: collection of tree types
    static method getTreeType(name, color, texture) is
        result = treeTypes.find(name, color, texture)
        if (result == null)
            tree = new TreeType(name, color, texture)
            treeTypes.add(tree)
        return tree

// EN: Context object keeps unique part of tree's state. Application can create
// billions of these since they are pretty thin–just two integer coordinates and
// one reference.
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
// RU: Классы Tree и Forest являются клиентами легковеса. При желании их можно
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
