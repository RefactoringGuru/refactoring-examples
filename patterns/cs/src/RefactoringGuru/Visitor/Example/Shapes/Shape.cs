using System;
using RefactoingGuru.Visitor.Example.Visitor;

namespace RefactoingGuru.Visitor.Example.Shapes
{
    interface Shape
    {
        void Move(int x, int y);
        void Draw();
        String Accept(IVisitor visitor);
    }
}
