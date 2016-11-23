// Новые совместимые классы: КруглоеОтверстие и КруглыйКолышек.
class RoundHole is
    constructor RoundHole(radius) { ... }
    method getRadius
    method fits(peg: RoundPeg) is
        return this.getRadius() >= peg.radius()

class RoundPeg is
    constructor RoundPeg(radius) { ... }

    method getRadius() is
        Return the peg radius.


// Старый несовместимый класс: КвадратныйКолышек.
class SquarePeg is
    constructor SquarePeg(width) { ... }

    method getWidth() is
        Return the square peg width.


// Адаптер, позволяющий использовать квадратные колышки и круглые
// отверстия вместе.
class SquarePegAdapter extends RoundPeg is
    field peg: SquarePeg

    constructor SquarePegAdapter(peg: SquarePeg) is
        this.peg = peg

    method getRadius() is
        return Math.sqrt(Math.pow((peg.getWidth()/2), 2) * 2);


// Где-то в клиентском коде.
hole = new RoundHole(5)
rpeg = new RoundPeg(5)
hole.fits(rpeg) // true

small_sqpeg = new SquarePeg(2)
large_sqpeg = new SquarePeg(5)
hole.fits(small_sqpeg) // не будет компилироваться из-за ошибки типов
hole.fits(small_sqpeg) // не будет компилироваться из-за ошибки типов

small_sqpeg_adapter = new SquarePegAdapter(small_sqpeg)
large_sqpeg_adapter = new SquarePegAdapter(large_sqpeg)
hole.fits(small_sqpeg_adapter) // true
hole.fits(large_sqpeg_adapter) // false
