// EN: Classes with compatible interfaces: RoundHole and RoundPeg.
// 
// RU: Классы с совместимыми интерфейсами: КруглоеОтверстие и КруглыйКолышек.
class RoundHole is
    constructor RoundHole(radius) { ... }
    method getRadius
    method fits(peg: RoundPeg) is
        return this.getRadius() >= peg.radius()

class RoundPeg is
    constructor RoundPeg(radius) { ... }

    method getRadius() is
        Return the peg radius.


// EN: Obsolete incompatible class: SquarePeg.
// 
// RU: Устаревший несовместимый класс: КвадратныйКолышек.
class SquarePeg is
    constructor SquarePeg(width) { ... }

    method getWidth() is
        Return the square peg width.


// EN: Adapter allows fitting square pegs into round holes.
// 
// RU: Адаптер позволяет использовать квадратные колышки и круглые
// отверстия вместе.
class SquarePegAdapter extends RoundPeg is
    field peg: SquarePeg

    constructor SquarePegAdapter(peg: SquarePeg) is
        this.peg = peg

    method getRadius() is
        return Math.sqrt(Math.pow((peg.getWidth()/2), 2) * 2);


// EN: Somewhere in client code.
// 
// RU: Где-то в клиентском коде.
hole = new RoundHole(5)
rpeg = new RoundPeg(5)
hole.fits(rpeg) // EN: true
 // 
 // RU: true

small_sqpeg = new SquarePeg(2)
large_sqpeg = new SquarePeg(5)
hole.fits(small_sqpeg) // EN: won't compile (incompatible types)
 // 
 // RU: не будет компилироваться из-за ошибки типов

small_sqpeg_adapter = new SquarePegAdapter(small_sqpeg)
large_sqpeg_adapter = new SquarePegAdapter(large_sqpeg)
hole.fits(small_sqpeg_adapter) // EN: true
 // 
 // RU: true
hole.fits(large_sqpeg_adapter) // EN: false
 // 
 // RU: false
