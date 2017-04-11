using RefactoingGuru.Visitor.Example.Shapes;
using RefactoingGuru.Visitor.Example.Visitor;

namespace RefactoingGuru.Visitor.Example.Shapes
{
    class Rectangle : Shape
    {
        public int id;
        public int x;
        public int y;
        public int width;
        public int height;

        public Rectangle(int id, int x, int y, int width, int height)
        {
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void Move(int x, int y)
        {
            // move shape
        }

        public void Draw()
        {
            // draw shape
        }

        public string Accept(IVisitor visitor)
        {
            return visitor.VisitRectangle(this);
        }

        public int GetId()
        {
            return id;
        }

        public int GetX()
        {
            return x;
        }

        public int GetY()
        {
            return y;
        }

        public int GetWidth()
        {
            return width;
        }

        public int GetHeight()
        {
            return height;
        }
    }
}
