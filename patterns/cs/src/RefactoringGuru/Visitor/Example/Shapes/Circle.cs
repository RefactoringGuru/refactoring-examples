using System;
using RefactoingGuru.Visitor.Example.Visitor;

namespace RefactoingGuru.Visitor.Example.Shapes
{
    class Circle : Dot
    {
        public int radius;

        public Circle(int id, int x, int y, int radius)
        {
            this.id = id;
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        public String Accept(IVisitor visitor)
        {
            return visitor.VisitCircle(this);
        }

        public int GetRadius()
        {
            return radius;
        }
    }
}
