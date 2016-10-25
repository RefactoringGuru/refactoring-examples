> TODO

# Требования ко всем примерам

1. Весь код должен соответствовать [стандарту стилей Microsoft для C#](https://msdn.microsoft.com/en-us/library/ff926074.aspx)

    Старайтесь соблюдать hard wrap на 80 символов, чтобы потом код можно было смотреть на сайте без горизонтальной прокрутки. 
    
2. Каждый пример должен находиться внутри пакета, RefactoringGuru.Patterns.{название паттерна}.{название примера}, например:

    ```cs
    namespace RefactoringGuru.Patterns.FactoryMethod.Example.Buttons
    {
    
    class Button
    {
    ...
    ```

3. Классы следует разбивать по файлам.

4. Старайтесь группировать файлы по под-пакетам так, чтобы показывать импорты этих файлов в других файлах.

    Благодаря этому, юзеру будут очевидней зависимости между классами. Например:

    ```cs
    namespace RefactoringGuru.Patterns.FactoryMethod.Example.Buttons
    {
        class Button
        {
        ...
    ```
    
    ```cs
    namespace RefactoringGuru.Patterns.FactoryMethod.Example.Factories
    {
        using RefactoringGuru.Patterns.FactoryMethod.Example.Buttons.Button;
    
        class Factory
        {
        ...
    ```