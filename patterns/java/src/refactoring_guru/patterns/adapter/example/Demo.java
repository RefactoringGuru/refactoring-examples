package refactoring_guru.patterns.adapter.example;

public class Demo {
    public static void main(String[] args) {
        RoundHole hole = new RoundHole(5);
        RoundPeg rpeg = new RoundPeg(5);
        hole.fits(rpeg); // true

        SquarePeg smallSqPeg = new SquarePeg(2);
        SquarePeg largeSqPeg = new SquarePeg(20);
//        hole.fits(smallSqPeg); // CTE
//        hole.fits(largeSqPeg); // CTE

        SquarePegAdapter smallSqPegAdapter = new SquarePegAdapter(smallSqPeg);
        SquarePegAdapter largeSqPegAdapter = new SquarePegAdapter(largeSqPeg);
        hole.fits(smallSqPegAdapter); // true
        hole.fits(largeSqPegAdapter); // false
    }
}
