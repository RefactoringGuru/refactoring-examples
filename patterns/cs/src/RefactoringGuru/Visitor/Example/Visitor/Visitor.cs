using System;
using RefactoingGuru.Visitor.Example.Shapes;

namespace RefactoingGuru.Visitor.Example.Visitor
{
    interface IVisitor
    {
        String VisitDot(Dot dot);

        String VisitCircle(Circle circle);

        String VisitRectangle(Rectangle rectangle);

        String VisitCompoundGraphic(CompoundShape cg);
    }
}
