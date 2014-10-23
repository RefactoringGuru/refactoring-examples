inline-class:php

###

1.ru. Создайте в классе-приёмнике публичные поля и методы, которые есть в классе-доноре. Методы должны обращаться к аналогичным методам класса-донора.
1.uk. Створіть в класі-приймачі публічні поля і методи, такі ж, як в класі-донорі. Методи повинні звертатися до аналогічних методів класу-донору.

2.ru. Замените все обращения к классу-донору обращениями к полям и методам класса-приёмника.
2.uk. Замініть усі звернення до класу-донору зверненнями до полів і методів класу-приймача.

3.ru. Используйте <a href="/move-method">перемещение метода</a> и <a href="/move-field">перемещение поля</a> для перемещения функциональности в класс-приёмник из исходного класса. Продолжаем делать это, пока в исходном классе ничего не останется.
3.uk. Використовуйте <a href="/move-method"> переміщення методу</a> і <a href="/move-field"> переміщення поля</a> для переміщення функціональності в клас-приймач з вихідного класу. Продовжуємо робити це, поки у вихідному класі нічого не залишиться.

4.ru. Удалите исходный класс.
4.uk. Видаліть початковий клас.



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

#|ru| Давайте рассмотрим <i>Встраивание класса</i> на примере класса личности и телефонного номера, который в нём используется.
#|uk| Давайте розглянемо <i>Вбудовування класу<i> на прикладі класу особистості і телефонного номера, який в ньому використовується.

Select name of "TelephoneNumber"

#|ru| Мы хотим включить класс <code>TelephoneNumber</code> обратно в класс <code>Person</code>, так как он потерял свою актуальность для наших задач.
#|uk| Ми хочемо включити клас <code>TelephoneNumber</code> назад в клас <code>Person</code>, так як він втратив свою актуальність для наших задач.

Go to the end of "Person"

#|ru| Начнём с объявления в классе <code>Person</code> всех видимых методов класса телефонного номера.
#|uk| Почнемо з оголошення в класі <code>Person</code> всіх видимих ​​методів класу телефонного номера.

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

#|ru| На первом этапе все эти методы будут делегировать работу объекту телефонного номера.
#|uk| На першому етапі всі ці методи будуть делегувати роботу об'єкту телефонного номеру.

Set step 2

Select "$martin->getOfficeTelephone()->setAreaCode("781")"

#|ru| Теперь найдём все случаи использования класса телефонного номера в клиентском коде и заменим его использованием класса <code>Person</code>
#|uk| Тепер знайдемо всі випадки використання класу телефонного номера в клієнтському коді та замінимо його використанням класу <code>Person</code>

Print "$martin->setAreaCode("781")"

Set step 3

#|ru| После этого мы со спокойным сердцем можем выполнять <a href="/move-method">перемещение метода</a> и завершать <a href="/move-field">перемещение поля</a> для перемещения всех полей и методов в класс <code>Person</code>. Эти перемещения можно делать по одному либо все сразу, если их не так много.
#|uk| Після цього ми зі спокійним серцем можемо виконувати <a href="/move-method">переміщення методу</a> і завершувати <a href="/move-field">переміщення поля</a> для переміщення всіх полів і методів в клас <code>Person</code>. Ці переміщення можна робити по одному або всі відразу, якщо їх не так багато.

Select:
```
  private $areaCode;
  private $number;


```

#|ru| Сначала перемещаем поля.
#|uk| Спочатку переміщуємо поля.

Remove selected

Go to "private $name;|||"

Print:
```

  private $areaCode;
  private $number;
```

Select body of "getAreaCode" in "TelephoneNumber"

#|ru| Потом переносим каждый метод...
#|uk| Потім переносимо кожен метод...

Wait 500ms

Select body of "getAreaCode" in "Person"

Replace:
```
    return $this->areaCode;
```

Select whole of "getAreaCode" in "TelephoneNumber"

Remove selected


Select body of "setAreaCode" in "TelephoneNumber"

#|ru| ...один за другим...
#|uk| ...один за іншим...

Wait 500ms

Select body of "setAreaCode" in "Person"

Replace:
```
    $this->areaCode = $arg;
```

Select whole of "setAreaCode" in "TelephoneNumber"

Remove selected


Select body of "getNumber" in "TelephoneNumber"

#|ru| ...переносим все методы...
#|uk| ...переносимо всі методи...

Wait 500ms

Select body of "getNumber" in "Person"

Replace:
```
    return $this->number;
```

Select whole of "getNumber" in "TelephoneNumber"

Remove selected

Select body of "setNumber" in "TelephoneNumber"

#|ru| ...все до последнего...
#|uk| ...все до останнього...


Wait 500ms

Select body of "setNumber" in "Person"

Replace:
```
    $this->number = $arg;
```

Select whole of "setNumber" in "TelephoneNumber"

Remove selected

Select body of "getTelephoneNumber" in "TelephoneNumber"

#|ru| ... и, наконец, последний геттер самого номера.
#|uk| ...і, нарешті, останній геттер самого номера.

Wait 500ms

Select body of "getTelephoneNumber" in "Person"

Replace:
```
    return ("(" + $this->areaCode + ") " + $this->number);
```

Select whole of "getTelephoneNumber" in "TelephoneNumber"

Remove selected

#C|ru| Запустим тесты, чтобы убедиться, что код всё ещё работает исправно.
#S Всё хорошо, можно продолжать.

#C|uk| Запустимо тести, щоб переконатися, що код все ще працює справно.
#S Все добре, можна продовжувати.

Set step 4

Select whole "TelephoneNumber"

#|ru| На текущем этапе нам осталось только удалить класс <code>TelephoneNumber</code> из программы.
#|uk| На поточному етапі нам залишилося тільки видалити клас <code>TelephoneNumber</code> з програми.

Select:
```
  private $officeTelephone; // TelephoneNumber
  
  public function __construct() {
    $this->officeTelephone = new TelephoneNumber();
  }

```
+ Select whole "getOfficeTelephone"

#|ru| Начнём с удаления его поля и геттера в классе <code>Person</code>.
#|uk| Почнемо з видалення його поля і геттера в класі <code>Person</code>.

Remove selected

Select whole "TelephoneNumber"

#|ru| Всё, больше ничего не удерживает нас от удаления самого класса. Спасибо за службу, <code>TelephoneNumber</code>, мы будем вспоминать о тебе только хорошее!
#|uk| Все, більше нічого не утримує нас від видалення самого класу. Дякуємо за службу, <code>TelephoneNumber</code>, ми будемо згадувати про тебе тільки хороше!

Remove selected

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.