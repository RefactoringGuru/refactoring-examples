using System;

namespace Guru.Refactoring.Patterns.FactoryMethod.Example
{
    class WebButton : Button
    {
        public void OnClick(OnClickButtonArgs args)
        {
            // Bind native click event
            throw new NotImplementedException();
        }

        public void Render(Size size, Position position)
        {
            // Draw a windows style button
            throw new NotImplementedException();
        }
    }
}