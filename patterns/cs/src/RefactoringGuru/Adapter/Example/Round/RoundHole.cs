namespace Adapter.Example.Round
{
    /**
     * EN: RoundHoles are compatible with RoundPegs.
     * 
     * RU: КруглоеОтверстие совместимо с КруглымиКолышками.
     */
    class RoundHole
    {
        private double radius;

        public RoundHole(double radius)
        {
            this.radius = radius;
        }

        public double getRadius()
        {
            return radius;
        }

        public bool fits(RoundPeg peg)
        {
            bool result;
            result = this.getRadius() >= peg.getRadius();
            return result;
        }
    }
}
