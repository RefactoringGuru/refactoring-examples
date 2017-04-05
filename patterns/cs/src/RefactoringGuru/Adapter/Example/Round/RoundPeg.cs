namespace RefactoringGuru.Adapter.Example.Round
{
    /**
     * EN: RoundPegs are compatible with RoundHoles.
     * 
     * RU: КруглыеКолышки совместимы с КруглымиОтверстиями.
     */
    class RoundPeg
    {
        private double radius;

        public RoundPeg() { }

        public RoundPeg(double radius)
        {
            this.radius = radius;
        }

        public double GetRadius()
        {
            return radius;
        }
    }
}
