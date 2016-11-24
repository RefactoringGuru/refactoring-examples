// Объект легковес. Каждый такой объект будет занимать много памяти, но их будет
// всего несколько штук на весь лес.
class TreeType is
    field name
    field color
    field texture
    constructor Tree(name, color, texture) { ... }
    method draw(canvas, x, y) is
        Create a bitmap from type, color and texture.
        Draw bitmap on canvas at X and Y.

// Фабрика легковесов решает когда нужно создать новый легковес, а когда можно
// обойтись существующим.
class TreeFactory is
    static field treeTypes: collection of tree types
    static method getTreeType(name, color, texture) is
        result = treeTypes.find(name, color, texture)
        if (result == null)
            tree = new TreeType(name, color, texture)
            treeTypes.add(tree)
        return tree

// Контекстный объект, из которого мы выделили легковес TreeType. В программе
// могут быть тысячи объектов Tree, так как накладные расходы на их хранение
// совсем небольшие — порядка трёх целых чисел (две координаты и ссылка).
class Tree is
    field x,y
    field type: TreeType
    constructor Tree(x, y, type) { ... }
    method draw(canvas) is
        type.draw(canvas, this.x, this.y)

// Классы Tree и Forest являются клиентами легковеса. При желании их можно слить
// в один класс, если класс Tree нет нужды расширять далее.
class Forest is
    field trees: collection of Trees

    method plantTree(x, y, name, color, texture) is
        type = TreeFactory.getTreeType(name, color, texture)
        tree = new Tree(x, y, type);
        trees.add(tree)

    method draw(canvas) is
        foreach tree in trees
            tree.draw(canvas)
