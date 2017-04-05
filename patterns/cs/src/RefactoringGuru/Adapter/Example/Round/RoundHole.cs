namespace RefactoringGuru.Adapter.Example.Round
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

        public double GetRadius()
        {
            return radius;
        }

        public bool Fits(RoundPeg peg)
        {
            bool result;
            result = this.GetRadius() >= peg.GetRadius();
            return result;
        }
    }
}
