using System;

namespace RefactoringGuru.PrototypeEample.Example.Shapes
{
    class Rectangle : Shape
    {
        public int width;
        public int height;

        public Rectangle() { }

        public Rectangle(Rectangle target) : base(target)
        {
            if (target != null)
            {
                width = target.width;
                height = target.height;
            }
        }

        public override Shape Clone()
        {
            return new Rectangle(this);
        }

        public bool Equals(Object object2)
        {
            if (!(object2.GetType() == typeof(Rectangle)) || !base.Equals(object2))
            {
                return false;
            }
            Rectangle shape2 = (Rectangle)object2;
            return shape2.width == width && shape2.height == height;
        }
    }
}
