inline-class:csharp

###

1. Создайте в классе-приемнике публичные свойства и методы, которые есть в классе-доноре. Методы должны обращаться к аналогичным методам класса-донора.

2. Замените все обращения к классу-донору обращениями к свойствам и методам класса-приемника.

3. Используйте <a href="/move-method">перемещение метода</a> и <a href="/move-field">перемещение поля</a> для перемещения функциональности в класс-приемник из исходного класса. Продолжаем делать это, пока в исходном классе ничего не останется.

4. Удалите исходный класс.



###

```
public class Person
{
  private TelephoneNumber officeTelephone = new TelephoneNumber();

  public string Name
  {
    get;
  }
  public TelephoneNumber OfficeTelephone
  {
    get {
      return officeTelephone;
    }
  }
  
  public string GetTelephoneNumber()
  {
    return officeTelephone.GetTelephoneNumber();
  }
}

public class TelephoneNumber
{
  public string AreaCode
  {
    get;
    set;
  }
  public string Number
  {
    get;
    set;
  }

  public string GetTelephoneNumber()
  {
    return "(" + AreaCode + ") " + Number;
  }
}

// Somewhere in client code
Person martin = new Person();
martin.OfficeTelephone.AreaCode = "781";
```

###

```
public class Person
{
  public string Name
  {
    get;
  }
  public string AreaCode
  {
    get;
    set;
  }
  public string Number
  {
    get;
    set;
  }
  
  public string GetTelephoneNumber()
  {
    return "(" + AreaCode + ") " + Number;
  }
}


// Somewhere in client code
Person martin = new Person();
martin.AreaCode = "781";
```

###

# Давайте рассмотрим <i>Встраивание класса</i> на примере класса личности и телефонного номера, который в нём используется.

Select name of "TelephoneNumber"

# Мы хотим включить класс <code>TelephoneNumber</code> обратно в класс <code>Person</code>, так как он потерял свою актуальность для наших задач.

Go to:
```
get;
  }|||
```

# Начнем с объявления в классе <code>Person</code> всех видимых свойств класса телефонного номера.

Print:
```

  public string AreaCode
  {
    get {
      return officeTelephone.AreaCode;
    }
    set {
      officeTelephone.AreaCode = value;
    }
  }
  public string Number
  {
    get {
      return officeTelephone.Number;
    }
    set {
      officeTelephone.Number = value;
    }
  }
```

Select "return |||officeTelephone.AreaCode|||;"
+Select "|||officeTelephone.AreaCode||| = value;"
+Select "return |||officeTelephone.Number|||;"
+Select "|||officeTelephone.Number||| = value;"

# На первом этапе все эти свойства будут делегировать работу объекту телефонного номера.

Set step 2

Select "martin.OfficeTelephone.AreaCode = "781""

# Теперь найдём все случаи использования класса телефонного номера в клиентском коде и заменим его на использование класса <code>Person</code>

Print "martin.AreaCode = "781""

Set step 3

# После этого мы со спокойным сердцем можем выполнять <a href="/move-method">перемещение метода</a> и завершать <a href="/move-field">перемещение поля</a>, настраивая геттеры и сеттеры для работы внутри класса <code>Person</code>. Эти перемещения можно делать по-одному, либо все сразу, если их не так много.

Select:
```
 {
      return officeTelephone.AreaCode;
    }
```
+Select:
```
 {
      officeTelephone.AreaCode = value;
    }
```
+Select:
```
 {
      return officeTelephone.Number;
    }
```
+Select:
```
 {
      officeTelephone.Number = value;
    }
```

# Сперва модифицируем геттеры и сеттеры перенесенных свойств.

Print ";"

Wait 500ms

Select in "TelephoneNumber":
```
return "(" + AreaCode + ") " + Number;
```

# Затем переносим методы.

Wait 500ms

Select in "Person":
```
return officeTelephone.GetTelephoneNumber();
```

Print:
```
return "(" + AreaCode + ") " + Number;
```

Wait 500ms

Select in "TelephoneNumber":
```
  public string AreaCode
  {
    get;
    set;
  }
  public string Number
  {
    get;
    set;
  }

  public string GetTelephoneNumber()
  {
    return "(" + AreaCode + ") " + Number;
  }

```

# После того, как вся функциональная часть перемещена, можем удалить неиспользуемые свойства и методы старого класса.

Remove selected

Wait 500ms

#C Запустим компиляцию и тестирование, чтобы убедиться, что код всё ещё работает исправно.

#S Всё хорошо, можно продолжать.

Set step 4

Select whole "TelephoneNumber"

# На текущем этапе нам осталось только удалить класс <code>TelephoneNumber</code> из программы.

Select:
```
  private TelephoneNumber officeTelephone = new TelephoneNumber();


```
+ Select:
```
  public TelephoneNumber OfficeTelephone
  {
    get {
      return officeTelephone;
    }
  }

```

# Начнём с удаления его поля и свойства в классе <code>Person</code>.

Remove selected

Select whole "TelephoneNumber"

# Всё, больше ничего не удерживает нас от удаления самого класса. Спасибо за службу, <code>TelephoneNumber</code>, мы будем вспоминать о тебе только хорошее!

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.