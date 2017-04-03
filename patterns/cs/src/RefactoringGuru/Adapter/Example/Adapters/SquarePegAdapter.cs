using System;
using Adapter.Example.Round;
using Adapter.Example.Square;

namespace Adapter.Example.Adapters
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

        public double getRadius()
        {
            double result;
            // EN: Calculate a minimum circle radius, which can fit this peg.
            // 
            // RU: Рассчитываем минимальный радиус, в который пролезет этот колышек.
            result = (Math.Sqrt(Math.Pow((peg.getWidth() / 2), 2) * 2));
            return result;
        }
    }
}
