using System;

namespace Guru.Refactoring.Patterns.FactoryMethod.Example
{
    interface Button
    {
        void Render(Size size, Position position);
        void OnClick(OnClickButtonArgs args);
    }
}