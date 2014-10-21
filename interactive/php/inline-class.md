inline-class:php

###

1. Создайте в классе-приёмнике публичные поля и методы, которые есть в классе-доноре. Методы должны обращаться к аналогичным методам класса-донора.

2. Замените все обращения к классу-донору обращениями к полям и методам класса-приёмника.

3. Используйте <a href="/move-method">перемещение метода</a> и <a href="/move-field">перемещение поля</a> для перемещения функциональности в класс-приёмник из исходного класса. Продолжаем делать это, пока в исходном классе ничего не останется.

4. Удалите исходный класс.



###

```
class Person {
  private $name;
  private $officeTelephone; // TelephoneNumber
  
  public function __construct() {
    $this->officeTelephone = new TelephoneNumber();
  }
  
  public function getName() {
    return $this->name;
  }
  public function getOfficeTelephone() {
    return $this->officeTelephone;
  }
  public function getTelephoneNumber() {
    return $this->officeTelephone->getTelephoneNumber();
  }
}

class TelephoneNumber {
  private $areaCode;
  private $number;

  public function getAreaCode() {
    return $this->areaCode;
  }
  public function setAreaCode($arg) {
    $this->areaCode = $arg;
  }
  public function getNumber() {
    return $this->number;
  }
  public function setNumber($arg) {
    $this->number = $arg;
  }
  public function getTelephoneNumber() {
    return ("(" + $this->areaCode + ") " + $this->number);
  }
}

// Somewhere in client code
$martin = new Person();
$martin->getOfficeTelephone()->setAreaCode("781");
```

###

```
class Person {
  private $name;
  private $areaCode;
  private $number;
  
  public function getName() {
    return $this->name;
  }
  public function getTelephoneNumber() {
    return ("(" + $this->areaCode + ") " + $this->number);
  }
  public function getAreaCode() {
    return $this->areaCode;
  }
  public function setAreaCode($arg) {
    $this->areaCode = $arg;
  }
  public function getNumber() {
    return $this->number;
  }
  public function setNumber($arg) {
    $this->number = $arg;
  }
}


// Somewhere in client code
$martin = new Person();
$martin->setAreaCode("781");
```

###

Set step 1

# Давайте рассмотрим <i>Встраивание класса</i> на примере класса личности и телефонного номера, который в нём используется.

Select name of "TelephoneNumber"

# Мы хотим включить класс <code>TelephoneNumber</code> обратно в класс <code>Person</code>, так как он потерял свою актуальность для наших задач.

Go to the end of "Person"

# Начнём с объявления в классе <code>Person</code> всех видимых методов класса телефонного номера.

Print:
```

  public function getAreaCode() {
    return $this->officeTelephone->getAreaCode();
  }
  public function setAreaCode($arg) {
    $this->officeTelephone->setAreaCode($arg);
  }
  public function getNumber() {
    return $this->officeTelephone->getNumber();
  }
  public function setNumber($arg) {
    $this->officeTelephone->setNumber($arg);
  }
```

Select "officeTelephone" in "getAreaCode"
+Select "officeTelephone" in "setAreaCode"
+Select "officeTelephone" in "getNumber"
+Select "officeTelephone" in "setNumber"

# На первом этапе все эти методы будут делегировать работу объекту телефонного номера.

Set step 2

Select "$martin->getOfficeTelephone()->setAreaCode("781")"

# Теперь найдём все случаи использования класса телефонного номера в клиентском коде и заменим его использованием класса <code>Person</code>

Print "$martin->setAreaCode("781")"

Set step 3

# После этого мы со спокойным сердцем можем выполнять <a href="/move-method">перемещение метода</a> и завершать <a href="/move-field">перемещение поля</a> для перемещения всех полей и методов в класс <code>Person</code>. Эти перемещения можно делать по одному либо все сразу, если их не так много.

Select:
```
  private $areaCode;
  private $number;


```

# Сначала перемещаем поля.

Remove selected

Go to "private $name;|||"

Print:
```

  private $areaCode;
  private $number;
```

Select body of "getAreaCode" in "TelephoneNumber"

# Потом переносим каждый метод...

Wait 500ms

Select body of "getAreaCode" in "Person"

Replace:
```
    return $this->areaCode;
```

Select whole of "getAreaCode" in "TelephoneNumber"

Remove selected


Select body of "setAreaCode" in "TelephoneNumber"

# ...один за другим...

Wait 500ms

Select body of "setAreaCode" in "Person"

Replace:
```
    $this->areaCode = $arg;
```

Select whole of "setAreaCode" in "TelephoneNumber"

Remove selected


Select body of "getNumber" in "TelephoneNumber"

# ...переносим все методы...

Wait 500ms

Select body of "getNumber" in "Person"

Replace:
```
    return $this->number;
```

Select whole of "getNumber" in "TelephoneNumber"

Remove selected

Select body of "setNumber" in "TelephoneNumber"

# ...все до последнего...


Wait 500ms

Select body of "setNumber" in "Person"

Replace:
```
    $this->number = $arg;
```

Select whole of "setNumber" in "TelephoneNumber"

Remove selected

Select body of "getTelephoneNumber" in "TelephoneNumber"

# ... и, наконец, последний геттер самого номера.

Wait 500ms

Select body of "getTelephoneNumber" in "Person"

Replace:
```
    return ("(" + $this->areaCode + ") " + $this->number);
```

Select whole of "getTelephoneNumber" in "TelephoneNumber"

Remove selected

#C Запустим тесты, чтобы убедиться, что код всё ещё работает исправно.

#S Всё хорошо, можно продолжать.

Set step 4

Select whole "TelephoneNumber"

# На текущем этапе нам осталось только удалить класс <code>TelephoneNumber</code> из программы.

Select:
```
  private $officeTelephone; // TelephoneNumber
  
  public function __construct() {
    $this->officeTelephone = new TelephoneNumber();
  }

```
+ Select whole "getOfficeTelephone"

# Начнём с удаления его поля и геттера в классе <code>Person</code>.

Remove selected

Select whole "TelephoneNumber"

# Всё, больше ничего не удерживает нас от удаления самого класса. Спасибо за службу, <code>TelephoneNumber</code>, мы будем вспоминать о тебе только хорошее!

Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.