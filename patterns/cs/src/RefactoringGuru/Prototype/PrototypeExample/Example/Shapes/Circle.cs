using System;

namespace RefactoringGuru.PrototypeEample.Example.Shapes
{
    class Circle : Shape
    {
        public int radius;

        public Circle() { }

        public Circle(Circle target) : base(target)
        {
            if (target != null)
            {
                radius = target.radius;
            }
        }

        public override Shape Clone()
        {
            return new Circle(this);
        }

        public new bool Equals(Object object2)
        {
            if (!(object2.GetType() == typeof(Circle)) || !base.Equals(object2))
            {
                return false;
            }
            Circle shape2 = (Circle)object2;
            return shape2.radius == radius;
        }
    }
}
