replace-subclass-with-fields:php

###

1. Примените к подклассам <a href="/replace-constructor-with-factory-method">замену конструктора фабричным методом</a>.

2. Замените вызовы конструкторов подклассов вызовами фабричного метода суперкласса.

3. Объявите в суперклассе поля для хранения значений каждого из методов подклассов, возвращающих константные значения.

4. Создайте защищённый конструктор суперкласса для инициализации новых полей.

5. Создайте или модифицируйте имеющиеся конструкторы подклассов, чтобы они вызывали новый конструктор суперкласса.

6. Реализуйте каждый константный метод в родительском классе так, чтобы он возвращал значение соответствующего поля, а затем удалите метод из подкласса.

7. Если конструктор подкласса имеет какую-то дополнительную функциональность,  примените <a href="/inline-method">встраивание метода</a> для встраивания его конструктора в фабричный метод суперкласса.

8. Удалите подкласс.



###

```
abstract class Person {
  abstract function isMale();
  abstract function getCode();
}

class Male extends Person {
  function isMale() {
    return true;
  }
  function getCode() {
    return 'M';
  }
}
class Female extends Person {
  function isMale() {
    return false;
  }
  function getCode() {
    return 'F';
  }
}

// Клиентский код
$kent = new Male();
print("Person's gender is: " . $kent->getCode());

```

###

```
class Person {
  static function createMale() {
    return new Person(true, 'M');
  }
  static function createFemale() {
    return new Person(false, 'F');
  }
  protected Person($isMale, $code) {
    $this->isMale = $isMale;
    $this->code = $code;
  }

  private $isMale;
  private $code;

  function isMale() {
    return $this->isMale;
  }
  function getCode() {
    return $this->code;
  }
}


// Клиентский код
$kent = Person::createMale();
print("Person's gender is: " . $kent->getCode());

```

###

Set step 1

# Рассмотрим рефакторинг <i>Замена подкласса полями</i> на примере всё того же класса человека и его подклассов, выделенных по признаку пола.

Select "return true"
+ Select "return false"
+ Select "return 'M'"
+ Select "return 'F'"

# Единственное различие между подклассами здесь в том, что в них есть реализации абстрактного метода, возвращающие жёстко закодированные константы. От таких классов лучше избавиться.

Go to the beginning of "Person"

# Сначала следует применить <a href="/replace-constructor-with-factory-method">Замену конструктора фабричным методом</a>. В данном случае нам нужен фабричный метод для каждого подкласса.

Print:
```

  static function createMale() {
    return new Male();
  }
  static function createFemale() {
    return new Female();
  }
```

Set step 2

Select "$kent = |||new Male()|||"

# После этого следует заменить все вызовы конструкторов подклассов вызовами соответствующих фабричных методов.

Print "Person::createMale()"

# После замены всех этих вызовов в коде не должно остаться упоминаний подклассов.

Set step 3

Go to after "createFemale"

# Теперь в родительском классе объявим поля для каждого метода, возвращавшего константы в подклассах.

Print:
```


  private $isMale;
  private $code;

```

Set step 4

Go to after "createFemale"

# Добавляем в родительский класс защищённый конструктор.

Print:
```

  protected Person($isMale, $code) {
    $this->isMale = $isMale;
    $this->code = $code;
  }
```

Set step 5

Go to the start of "Male"

# Добавляем конструкторы, вызывающие этот новый конструктор в подклассах.

Print:
```

  function __construct() {
    parent::__construct(true, 'M');
  }
```

Go to the start of "Female"

Print:
```

  function __construct() {
    parent::__construct(false, 'F');
  }
```

#C После этого можно выполнить тестирование.

#S Всё отлично, можно продолжать.

Set step 6

Select "  abstract function isMale();"

# Поля создаются и инициализируются, но пока они не используются. Теперь мы можем ввести поля в игру, поместив в родительском классе методы доступа и удалив методы подклассов.

Print:
```
  function isMale() {
    return $this->isMale;
  }
```

Wait 500ms

Select whole "isMale" in "Male"

Remove selected

Wait 500ms

Select whole "isMale" in "Female"

Remove selected

Select "  abstract function getCode();"

Replace:
```
  function getCode() {
    return $this->code;
  }
```

Wait 500ms

Select whole "getCode" in "Male"

Remove selected

Wait 500ms

Select whole "getCode" in "Female"

Remove selected

Set step 7

Select "|||abstract||| class Person"
+ Select "new Male()"
+ Select "new Female()"

# В итоге все подклассы оказываются пустыми, поэтому мы снимаем пометку abstract с класса Person и с помощью <a href="/inine-method">Встраивания метода</a> встраиваем конструктор подкласса в родительский класс.

Select "|||abstract |||class Person"

Remove selected

Wait 500ms

Select "new Male()"

Replace "new Person(true, 'M')"

Set step 8

Select whole "Male"

# После этого класс <code>Male</code> можно спокойно удалить.

Remove selected

#C Проведём тестирование, чтобы убедиться, что никакой другой код не сломался.

#S Всё хорошо, можно проделать ту же операцию с классом <code>Female</code>


Select "new Female()"

Replace "new Person(false, 'F')"

Wait 500ms

Select whole "Female"

Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.