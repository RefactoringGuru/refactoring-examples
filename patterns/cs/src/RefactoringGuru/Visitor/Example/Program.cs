using System;
using RefactoingGuru.Visitor.Example.Shapes;
using RefactoingGuru.Visitor.Example.Visitor;

namespace RefactoingGuru.Visitor
{
    class Program
    {
        static void Main(string[] args)
        {
            Dot dot = new Dot(1, 10, 55);
            Circle circle = new Circle(2, 23, 15, 10);
            Rectangle rectangle = new Rectangle(3, 10, 17, 20, 30);

            CompoundShape compoundShape = new CompoundShape(4);
            compoundShape.Add(dot);
            compoundShape.Add(circle);
            compoundShape.Add(rectangle);

            CompoundShape c = new CompoundShape(5);
            c.Add(dot);
            compoundShape.Add(c);

            Export(circle, compoundShape);

            Console.ReadKey();
        }

        public static void Export(params Shape[] shapes)
        {
            XMLExportVisitor exportVisitor = new XMLExportVisitor();
            Console.WriteLine(exportVisitor.Export(shapes));
        }
    }
}
