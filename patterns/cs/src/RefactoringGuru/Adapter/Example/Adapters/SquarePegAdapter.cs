using System;
using RefactoringGuru.Adapter.Example.Round;
using RefactoringGuru.Adapter.Example.Square;

namespace RefactoringGuru.Adapter.Example.Adapters
{
    /**
     * EN: Adapter allows fitting square pegs into round holes.
     * 
     * RU: Адаптер позволяет использовать КвадратныеКолышки и
     * КруглыеОтверстия вместе.
     */
    class SquarePegAdapter : RoundPeg
    {
        private SquarePeg peg;

        public SquarePegAdapter(SquarePeg peg)
        {
            this.peg = peg;
        }

        public double GetRadius()
        {
            double result;
            // EN: Calculate a minimum circle radius, which can fit this peg.
            // 
            // RU: Рассчитываем минимальный радиус, в который пролезет этот колышек.
            result = (Math.Sqrt(Math.Pow((peg.GetWidth() / 2), 2) * 2));
            return result;
        }
    }
}
