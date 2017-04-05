using System;
using System.Collections.Generic;
using System.Linq;
using RefactoringGuru.PrototypeEample.Example.Shapes;

namespace RefactoringGuru.PrototypeEample
{
    class Program
    {
        static void Main(string[] args)
        {
            IList<Shape> shapes = new List<Shape>();
            IList<Shape> shapesCopy = new List<Shape>();

            Circle circle = new Circle
            {
                x = 10,
                y = 20,
                radius = 15
            };
            shapes.Add(circle);

            Circle anotherCircle = (Circle)circle.Clone();
            shapes.Add(anotherCircle);

            Rectangle rectangle = new Rectangle
            {
                width = 10,
                height = 20
            };
            shapes.Add(rectangle);

            CloneAndCompare(shapes, shapesCopy);

            Console.ReadKey();
        }

        private static void CloneAndCompare(IList<Shape> shapes, IList<Shape> shapesCopy)
        {
            foreach (var shape in shapes)
            {
                shapesCopy.Add(shape.Clone());
            }

            for (int i = 0; i < shapes.Count; i++)
            {
                if (shapes.ElementAt(i) != shapesCopy.ElementAt(i))
                {
                    Console.WriteLine(i + ": Shapes are different objects (yay!)");
                    if (shapes.ElementAt(i).Equals(shapesCopy.ElementAt(i)))
                    {
                        Console.WriteLine(i + ": And they are identical (yay!)");
                    }
                    else
                    {
                        Console.WriteLine(i + ": But they are not identical (booo!)");
                    }
                }
                else
                {
                    Console.WriteLine(i + ": Shape objects are the same (booo!)");
                }
            }
        }
    }
}
