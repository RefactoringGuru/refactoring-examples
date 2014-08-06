inline-class:java

###

1. Создайте в классе-приемнике публичные поля и методы, которые есть в классе-доноре. Методы должны обращаться к аналогичным методам класса-донора.

2. Замените все обращения к классу-донору обращениями к полям и методам класса-приемника.

3. Используйте <a href="/move-method">перемещение метода</a> и <a href="/move-field">перемещение поля</a> для перемещения функциональности в класс-приемник из исходного класса. Продолжаем делать это, пока в исходном классе ничего не останется.

4. Удалите исходный класс.



###

```
class Person {
  private String name;
  private TelephoneNumber officeTelephone = new TelephoneNumber();

  public String getName() {
    return name;
  }
  public String getTelephoneNumber() {
    return officeTelephone.getTelephoneNumber();
  }
  public TelephoneNumber getOfficeTelephone() {
    return officeTelephone;
  }
}

class TelephoneNumber {
  private String number;
  private String areaCode;

  public String getAreaCode() {
    return areaCode;
  }
  public void setAreaCode(String arg) {
    areaCode = arg;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String arg) {
    number = arg;
  }
  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
  }
}

// Somewhere in client code
Person martin = new Person();
martin.getOfficeTelephone().setAreaCode("781");
```

###

```
class Person {
  private String name;
  private String number;
  private String areaCode;

  public String getName() {
    return name;
  }
  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
  }
  public String getAreaCode() {
    return areaCode;
  }
  public void setAreaCode(String arg) {
    areaCode = arg;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String arg) {
    number = arg;
  }
}


// Somewhere in client code
Person martin = new Person();
martin.setAreaCode("781");
```

###

# Давайте рассмотрим <i>Встраивание класса</i> на примере класса личности и телефонного номера, который в нём используется.

Select name of "TelephoneNumber"

# Мы хотим включить класс <code>TelephoneNumber</code> обратно в класс <code>Person</code>, так как он потерял свою актуальность для наших задач.

Go to the end of "Person"

# Начнем с объявления в классе <code>Person</code> всех видимых методов класса телефонного номера.

Print:
```

  public String getAreaCode() {
    return officeTelephone.getAreaCode();
  }
  public void setAreaCode(String arg) {
    officeTelephone.setAreaCode(arg);
  }
  public String getNumber() {
    return officeTelephone.getNumber();
  }
  public void setNumber(String arg) {
    officeTelephone.setNumber(arg);
  }
```

Select "officeTelephone" in "getAreaCode"
+Select "officeTelephone" in "setAreaCode"
+Select "officeTelephone" in "getNumber"
+Select "officeTelephone" in "setNumber"

# На первом этапе все эти методы будут делегировать работу объекту телефонного номера.

Set step 2

Select "martin.getOfficeTelephone().setAreaCode("781")"

# Теперь найдём все случаи использования класса телефонного номера в клиентском коде и заменим его на использование класса <code>Person</code>

Print "martin.setAreaCode("781")"

Set step 3

# После этого мы со спокойным сердцем можем выполнять <a href="/move-method">перемещение метода</a> и <a href="/move-field">перемещение поля</a> для реального перемещения всех полей и методов в класс <code>Person</code>. Эти перемещения можно делать по-одному, либо все сразу, если их не так много.

Select:
```
  private String number;
  private String areaCode;


```

# Сперва перемещаем поля.

Remove selected

Go to " new TelephoneNumber();|||"

Print:
```

  private String number;
  private String areaCode;
```

Select body of "getAreaCode" in "TelephoneNumber"

# Потом переносим каждый метод...

Wait 500ms

Select body of "getAreaCode" in "Person"

Print:
```
    return areaCode;
```

Select whole of "getAreaCode" in "TelephoneNumber"

Remove selected


Select body of "setAreaCode" in "TelephoneNumber"

# ...один за другим...

Wait 500ms

Select body of "setAreaCode" in "Person"

Print:
```
    areaCode = arg;
```

Select whole of "setAreaCode" in "TelephoneNumber"

Remove selected


Select body of "getNumber" in "TelephoneNumber"

# ...переносим все методы...

Wait 500ms

Select body of "getNumber" in "Person"

Print:
```
    return number;
```

Select whole of "getNumber" in "TelephoneNumber"

Remove selected

Select body of "setNumber" in "TelephoneNumber"

# ...все до последнего...


Wait 500ms

Select body of "setNumber" in "Person"

Print:
```
    number = arg;
```

Select whole of "setNumber" in "TelephoneNumber"

Remove selected

Select body of "getTelephoneNumber" in "TelephoneNumber"

# ... и, наконец, последний геттер самого номера.

Wait 500ms

Select body of "getTelephoneNumber" in "Person"

Print:
```
    return ("(" + areaCode + ") " + number);
```

Select whole of "getTelephoneNumber" in "TelephoneNumber"

Remove selected

#C Запустим компиляцию и тестирование, чтобы убедиться, что код всё ещё работает исправно.

#S Всё хорошо, можно продолжать.

Set step 4

Select whole "TelephoneNumber"

# На текущем этапе нам осталось только удалить класс <code>TelephoneNumber</code> из программы.

Select:
```
  private TelephoneNumber officeTelephone = new TelephoneNumber();

```
+ Select whole "getOfficeTelephone"

# Начнём с удаления его поля и геттера в классе <code>Person</code>.

Remove selected

# Всё, теперь нас ничего не держит от удаления самого класса. Спасибо за службу, <code>TelephoneNumber</code>, мы будем вспоминать о тебе только хорошее!

Select whole "TelephoneNumber"

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.