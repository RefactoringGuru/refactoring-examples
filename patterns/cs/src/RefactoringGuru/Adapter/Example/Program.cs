using System;
using Adapter.Example.Adapters;
using Adapter.Example.Round;
using Adapter.Example.Square;

namespace Adapter.Example
{
    /**
    * EN: Somewhere in client code...
    * 
    * RU: Где-то в клиентском коде...
    */
    class Program
    {
        static void Main()
        {
            // EN: Round fits round, no surprise.
            // 
            // RU: Круглое к круглому — всё работает.
            RoundHole hole = new RoundHole(5);
            RoundPeg rpeg = new RoundPeg(5);
            if (hole.fits(rpeg))
            {
                Console.WriteLine("Round peg r5 fits round hole r5.");
            }

            SquarePeg smallSqPeg = new SquarePeg(2);
            SquarePeg largeSqPeg = new SquarePeg(20);
            // EN: hole.fits(smallSqPeg); // Won't compile.
            // 
            // RU: hole.fits(smallSqPeg); // Не скомпилируется.

            // EN: Adapter solves the problem.
            // 
            // RU: Адаптер решит проблему.
            SquarePegAdapter smallSqPegAdapter = new SquarePegAdapter(smallSqPeg);
            SquarePegAdapter largeSqPegAdapter = new SquarePegAdapter(largeSqPeg);
            if (hole.fits(smallSqPegAdapter))
            {
                Console.WriteLine("Square peg w2 fits round hole r5.");
            }
            if (hole.fits(largeSqPegAdapter))
            {
                Console.WriteLine("Square peg w20 does not fit into round hole r5.");
            }

            Console.ReadKey();
        }
    }
}
