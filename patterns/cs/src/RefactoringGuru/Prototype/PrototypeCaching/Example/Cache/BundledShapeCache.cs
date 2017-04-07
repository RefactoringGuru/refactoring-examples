using System;
using System.Collections.Generic;
using RefactoringGuru.PrototypeCashing.Example.Shapes;

namespace RefactoringGuru.PrototypeCaching.Example.Cache
{
    class BundledShapeCache
    {
        private Dictionary<String, Shape> cache = new Dictionary<String, Shape>();

        public BundledShapeCache()
        {
            Circle circle = new Circle();
            circle.x = 5;
            circle.y = 7;
            circle.radius = 45;
            circle.color = "Green";

            Rectangle rectangle = new Rectangle();
            rectangle.x = 6;
            rectangle.y = 9;
            rectangle.width = 8;
            rectangle.height = 10;
            rectangle.color = "Blue";

            cache.Add("Big green circle", circle);
            cache.Add("Medium blue rectangle", rectangle);
        }

        public Shape Put(String key, Shape shape) 
        {
            cache.Add(key, shape);
            return shape;
        }

        public Shape Get(String key)
        {
            Shape shape = cache[key];
            return shape.Clone();
        }
    }
}
