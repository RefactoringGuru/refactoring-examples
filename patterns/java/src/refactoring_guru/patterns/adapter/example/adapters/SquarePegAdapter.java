package refactoring_guru.patterns.adapter.example.adapters;

import refactoring_guru.patterns.adapter.example.round.RoundPeg;
import refactoring_guru.patterns.adapter.example.square.SquarePeg;

public class SquarePegAdapter extends RoundPeg {
    private SquarePeg peg;

    public SquarePegAdapter(SquarePeg peg) {
        this.peg = peg;
    }

    @Override
    public double getRadius() {
        double result;
        result = (Math.sqrt(Math.pow((peg.getWidth() / 2), 2) * 2));
        return result;
    }
}
