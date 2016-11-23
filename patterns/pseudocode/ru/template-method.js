class GameAI is
    // Шаблонный метод задаётся в базовом классе.
    method turn() is
        collectResources()
        buildStructures()
        buildUnits()
        attack()

    // Некоторые методы имеют реализацию в базовом классе.
    method collectResources() is
        foreach this.structures
            structure.collect()

    // А некоторые могут быть полностью абстрактными.
    abstract method buildStructures()
    abstract method buildUnits()

    // Шаблонных методов в классе может быть несколько.
    method attack() is
        enemy = closestEnemy()
        if (enemy == null)
            sendScouts(map.center)
        else
            sendWarriors(enemy.position)

    abstract method sendScouts(position)
    abstract method sendWarriors(position)

// Подклассы содержат реализацию шагов алгоритма.
class OrcsAI extends GameAI is
    method buildStructures() is
        If enough resources then
            Build farms, then barracks, then stronghold.

    method buildUnits() is
        If enough resources
            If scouts not exist, build 1 peon.
            Else build grunt.

    // ...

    method sendScouts(position) is
        If scouts exists, send scouts to position.

    method sendWarriors(position) is
        If grunts are more than 5, then send warriors to position.

// Подклассы не только реализуют абстрактные шаги шаблонного метода, но и могут
// переопределить шаги по-умолчанию.
class MonstersAI extends GameAI is
    method collectResources() is
        Do nothing.

    method buildStructures() is
        Do nothing.

    method buildUnits() is
        Do nothing.
