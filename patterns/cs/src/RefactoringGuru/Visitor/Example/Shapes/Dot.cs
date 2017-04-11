using System;
using RefactoingGuru.Visitor.Example.Visitor;

namespace RefactoingGuru.Visitor.Example.Shapes
{
    class Dot : Shape
    {
        public int id;
        public int x;
        public int y;

        public Dot() {}

        public Dot(int id, int x, int y)
        {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public void Move(int x, int y)
        {
            // move shape
        }

        public void Draw()
        {
            // draw shape
        }

        public String Accept(IVisitor visitor)
        {
            return visitor.VisitDot(this);
        }

        public int GetX()
        {
            return x;
        }

        public int GetY()
        {
            return y;
        }

        public int GetId()
        {
            return id;
        }
    }
}
