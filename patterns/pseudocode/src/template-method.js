class GameAI is
    // EN: Template method should be defined in a base class. Its body is a set
    // of method class in a defined order. Usually, they are the steps of
    // some algorithm.
    // 
    // RU: Шаблонный метод должен быть задан в базовом классе. Он состоит из
    // вызовов методов в определённом порядке. Чаще всего эти методы являются
    // шагами некоего алгоритма.
    method turn() is
        collectResources()
        buildStructures()
        buildUnits()
        attack()

    // EN: Some of these steps may be implemented right in a base class.
    // 
    // RU: Некоторые из этих методов могут быть реализованы прямо в
    // базовом классе.
    method collectResources() is
        foreach this.structures
            structure.collect()

    // EN: And some of them could be defined as abstract.
    // 
    // RU: А некоторые могут быть полностью абстрактными.
    abstract method buildStructures()
    abstract method buildUnits()

    // EN: By the way, a class can have several template methods.
    // 
    // RU: Кстати, шаблонных методов в классе может быть несколько.
    method attack() is
        enemy = closestEnemy()
        if (enemy == null)
            sendScouts(map.center)
        else
            sendWarriors(enemy.position)

    abstract method sendScouts(position)
    abstract method sendWarriors(position)

// EN: Subclasses can provide their own steps implementation as long as they
// don't change the template method.
// 
// RU: Подклассы могут предоставлять свою реализацию шагов алгоритма, не изменяя
// сам шаблонный метод.
class OrcsAI extends GameAI is
    method buildStructures() is
        If enough resources then
            Build farms, then barracks, then stronghold.

    method buildUnits() is
        If enough resources
            If scouts not exist, build 1 peon.
            Else build grunt.

    // EN: ...
    // 
    // RU: ...

    method sendScouts(position) is
        If scouts exists, send scouts to position.

    method sendWarriors(position) is
        If grunts are more than 5, then send warriors to position.

// EN: Subclasses may not only implement abstract steps but also override
// default steps from the base class.
// 
// RU: Подклассы могут не только реализовывать абстрактные шаги, но и
// переопределять шаги, уже реализованные в базовом классе.
class MonstersAI extends GameAI is
    method collectResources() is
        Do nothing.

    method buildStructures() is
        Do nothing.

    method buildUnits() is
        Do nothing.
