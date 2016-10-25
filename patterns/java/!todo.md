> TODO

# Требования ко всем примерам

0. Весь код должен соответствовать [стандарту стилей Google для Java](https://google.github.io/styleguide/javaguide.html)

    Старайтесь соблюдать hard wrap на 80 символов, чтобы потом код можно было смотреть на сайте без горизонтальной прокрутки. 

1. Каждый пример должен находиться внутри пакета, refactoring_guru.patterns.{название паттерна}.{название примера}, например:

```java
package refactoring_guru.patterns.factory_method.example.buttons;

class Button {
...
```

2. Классы следует разбивать по файлам.

3. Старайтесь группировать файлы по под-пакетам так, чтобы показывать импорты этих файлов в других файлах.

    Благодаря этому, юзеру будут очевидней зависимости между классами. Например:

```java
package refactoring_guru.patterns.factory_method.example.buttons;

class Button {
...
```

```java
package refactoring_guru.patterns.factory_method.example.factories;

import refactoring_guru.patterns.factory_method.example.buttons.Button;

class Factory {
...
```