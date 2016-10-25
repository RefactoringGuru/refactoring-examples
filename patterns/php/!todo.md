> TODO

# Требования ко всем примерам

0. Весь код должен соответствовать стандарту стилей [PSR-2](https://github.com/php-fig/fig-standards/blob/master/accepted/PSR-2-coding-style-guide.md)

    Старайтесь соблюдать hard wrap на 80 символов, чтобы потом код можно было смотреть на сайте без горизонтальной прокрутки. 

1. Каждый пример должен находиться внутри неймспейса, RefactoringGuru/Patterns/{название паттерна}/{название примера}, например:

```php
<?php

namespace RefactoringGuru/Patterns/FactoryMethod/Example/Buttons;

class Button {
...
```

3. Старайтесь группировать файлы по под-пакетам так, чтобы показывать импорты этих файлов в других файлах.

    Благодаря этому, юзеру будут очевидней зависимости между классами. Например:

```php
<?php

namespace RefactoringGuru/Patterns/FactoryMethod/Example/Buttons;

class Button {
...
```

```php
<?php

namespace RefactoringGuru/Patterns/FactoryMethod/Example/Dialogs;

use RefactoringGuru/Patterns/FactoryMethod/Example/Buttons/Button;

class Dialog {
...
```
