using System.Collections.Generic;
using RefactoingGuru.Visitor.Example.Shapes;
using RefactoingGuru.Visitor.Example.Visitor;

namespace RefactoingGuru.Visitor.Example.Shapes
{
    class CompoundShape : Shape
    {
        public int id;
        public List<Shape> children = new List<Shape>();

        public CompoundShape(int id)
        {
            this.id = id;
        }

        public void Move(int x, int y)
        {
            // move shape
        }

        public void Draw()
        {
            // draw shape
        }

        public int GetId()
        {
            return id;
        }

        public string Accept(IVisitor visitor)
        {
            return visitor.VisitCompoundGraphic(this);
        }

        public void Add(Shape shape)
        {
            children.Add(shape);
        }
    }
}
