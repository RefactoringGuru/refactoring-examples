using System;

namespace RefactoringGuru.PrototypeEample.Example.Shapes
{
    abstract class Shape
    {
        public int x;
        public int y;
        public String color;

        public Shape() { }

        public Shape(Shape target)
        {
            if (target != null)
            {
                this.x = target.x;
                this.y = target.y;
                this.color = target.color;
            }
        }

        public abstract Shape Clone();
        
        public bool Equals(Object object2)
        {
            if (!(object2.GetType() == typeof(Shape)))
            {
                return false;
            }
            Shape shape2 = (Shape) object2;
            return shape2.x == x && shape2.y == y && shape2.color == color;
        }
    }
}
