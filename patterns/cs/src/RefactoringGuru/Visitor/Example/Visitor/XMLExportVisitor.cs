using System;
using System.Text;
using RefactoingGuru.Visitor.Example.Shapes;

namespace RefactoingGuru.Visitor.Example.Visitor
{
    class XMLExportVisitor : IVisitor
    {

        public String Export(params Shape[] args)
        {
            StringBuilder sb = new StringBuilder();
            foreach (var shape in args)
            {
                sb.Append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
                sb.Append(shape.Accept(this) + "\n");
                Console.WriteLine(sb.ToString());
                sb.Length = 0;
            }
            return sb.ToString();
        }

        public string VisitDot(Dot d)
        {
            return "<dot>" + "\n" +
                "    <id>" + d.GetId() + "</id>" + "\n" +
                "    <x>" + d.GetX() + "</x>" + "\n" +
                "    <y>" + d.GetY() + "</y>" + "\n" +
                "</dot>";
        }

        public string VisitCircle(Circle c)
        {
            return "<circle>" + "\n" +
                "    <id>" + c.GetId() + "</id>" + "\n" +
                "    <x>" + c.GetX() + "</x>" + "\n" +
                "    <y>" + c.GetY() + "</y>" + "\n" +
                "    <radius>" + c.GetRadius() + "</radius>" + "\n" +
                "</circle>";
        }

        public string VisitRectangle(Rectangle r)
        {
            return "<rectangle>" + "\n" +
                "    <id>" + r.GetId() + "</id>" + "\n" +
                "    <x>" + r.GetX() + "</x>" + "\n" +
                "    <y>" + r.GetY() + "</y>" + "\n" +
                "    <width>" + r.GetWidth() + "</width>" + "\n" +
                "    <height>" + r.GetHeight() + "</height>" + "\n" +
                "</rectangle>";
        }

        public string VisitCompoundGraphic(CompoundShape cg)
        {
            return "<compound_graphic>" + "\n" +
                "   <id>" + cg.GetId() + "</id>" + "\n" +
                _visitCompoundGraphic(cg) +
                "</compound_graphic>";
        }

        private String _visitCompoundGraphic(CompoundShape cg)
        {
            StringBuilder sb = new StringBuilder();
            foreach (var shape in cg.children)
            {
                String obj = shape.Accept(this);
                // Proper indentation for sub-objects.
                obj = "    " + obj.Replace("\n", "\n    ") + "\n";
                sb.Append(obj);
            }
            return sb.ToString();
        }
    }
}
