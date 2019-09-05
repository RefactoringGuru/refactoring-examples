inline-class:csharp

###

1.ru. Создайте в классе-приёмнике публичные свойства и методы, которые есть в классе-доноре. Методы должны обращаться к аналогичным методам класса-донора.
1.en. In the recipient class, create the public properties and methods that are in the donor class. Methods should refer to the equivalent methods of the donor class.
1.uk. Створіть в класі-приймачі публічні властивості та методи, які є в класі-донорі. Методи повинні звертатися до аналогічних методів класу-донора.

2.ru. Замените все обращения к классу-донору обращениями к свойствам и методам класса-приёмника.
2.en. Replace all references to the donor class with references to the properties and methods of the recipient class.
2.uk. Замініть всі звернення до класу-донору зверненнями до властивостей і методів класу-приймача.

3.ru. Используйте <a href="/move-method">перемещение метода</a> и <a href="/move-field">перемещение поля</a> для перемещения функциональности в класс-приёмник из исходного класса. Продолжаем делать это, пока в исходном классе ничего не останется.
3.en. Use <a href="/move-field">move field</a> and <a href="/move-method">move field</a> for moving functionality from the original class to the recipient class. Continue doing so until nothing remains in the original class.
3.uk. Використовуйте <a href="/move-method"> переміщення методу</a> і <a href="/move-field"> переміщення поля</a> для переміщення функціональності в клас-приймач з вихідного класу. Продовжуємо робити це, поки у вихідному класі нічого не залишиться.

4.ru. Удалите исходный класс.
4.en. Delete the original class.
4.uk. Видаліть початковий клас.



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

Set step 1

#|ru| Давайте рассмотрим <i>Встраивание класса</i> на примере класса личности и телефонного номера, который в нём используется.
#|en| Let's look at <i>Inline Class</i> using the person class and its phone number as an example.
#|uk| Давайте розглянемо <i>Вбудовування класу</i> на прикладі класу особистості і телефонного номера, який в ньому використовується.

Select name of "TelephoneNumber"

#|ru| Мы хотим включить класс <code>TelephoneNumber</code> обратно в класс <code>Person</code>, так как он потерял свою актуальность для наших задач.
#|en| We want to include the <code>TelephoneNumber</code> class back in the <code>Person</code> class, since it become unnecessary complex for our needs.
#|uk| Ми хочемо включити клас <code>TelephoneNumber</code> назад в клас <code>Person</code>, оскільки він втратив свою актуальність для наших задач.

Go to:
```
get;
  }|||
```

#|ru| Начнём с объявления в классе <code>Person</code> всех видимых свойств класса телефонного номера.
#|en| Start by declaring all visible properties of the phone number class in the <code>Person</code> class.
#|uk| Почнемо з оголошення в класі <code>Person</code> всіх видимих властивостей класу телефонного номера.

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

#|ru| На первом этапе все эти свойства будут делегировать работу объекту телефонного номера.
#|en| Initially all these properties will delegate to the phone number object.
#|uk| На першому етапі всі ці властивості будуть делегувати роботу об'єкту телефонного номера.

Set step 2

Select "martin.OfficeTelephone.AreaCode = "781""

#|ru| Теперь найдём все случаи использования класса телефонного номера в клиентском коде и заменим его использованием класса <code>Person</code>
#|en| Now find all cases where the phone number class is used in client code and replace it with calls to the delegate methods in <code>Person</code>.
#|uk| Тепер знайдемо всі випадки використання класу телефонного номера в клієнтському коді та замінимо його використанням класу <code>Person</code>

Print "martin.AreaCode = "781""

Set step 3

#|ru| После этого мы со спокойным сердцем можем выполнять <a href="/ru/move-method">перемещение метода</a> и завершать <a href="/move-field">перемещение поля</a>, настраивая геттеры и сеттеры для работы внутри класса <code>Person</code>. Эти перемещения можно делать по одному либо все сразу, если их не так много.
#|en| Then we can proceed to <a href="/move-method">Move Method</a> and finish <a href="/move-field">Move Field</a>, configuring getters and setters to work inside the <code>Person</code> class. These moves can be performed one by one or all at once, if there are not too many of them.
#|uk| Після цього ми зі спокійним серцем можемо виконувати <a href="/uk/move-method">переміщення методу</a> і завершувати <a href="/move-field">переміщення поля</a>, налаштовуючи геттери і сеттери для роботи всередині класу <code>Person</code>. Ці переміщення можна робити поступово один за одним, або всі відразу, якщо їх не так багато.

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

#|ru| Сперва модифицируем геттеры и сеттеры перенесенных свойств.
#|en| First modify the getters and setters for the moved properties.
#|uk| Спершу модифікуємо геттери і сеттери перенесених властивостей.

Print ";"

Wait 500ms

Select in "TelephoneNumber":
```
return "(" + AreaCode + ") " + Number;
```

#|ru| Затем переносим методы.
#|en| Then move the methods.
#|uk| Потім переносимо методи.

Wait 500ms

Select in "Person":
```
return officeTelephone.GetTelephoneNumber();
```

Replace:
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

#|ru| После того как вся функциональная часть перемещена, можно удалить неиспользуемые свойства и методы старого класса.
#|en| Once the functionality has been moved, remove the unused properties and methods of the old class.
#|uk| Після того як вся функціональна частина переміщена, можна видалити  властивості та методи старого класу, які тепер не використовуються.

Remove selected

Wait 500ms

#C|ru| Запустим компиляцию и тестирование, чтобы убедиться, что код всё ещё работает исправно.
#S Всё хорошо, можно продолжать.

#C|en| Now is a good time to compile and test, to make sure the code is still working correctly.
#S All is well, so let's continue.

#C|uk| Запустимо компіляцію і тестування, щоб переконатися, що код все ще працює справно.
#S Все добре, можна продовжувати.

Set step 4

Select whole "TelephoneNumber"

#|ru| На текущем этапе нам осталось только удалить класс <code>TelephoneNumber</code> из программы.
#|en| At this point, we need only to remove the <code>TelephoneNumber</code> class from the program.
#|uk| На поточному етапі нам залишилося тільки видалити клас <code>TelephoneNumber</code> з програми.

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

#|ru| Начнём с удаления его поля и свойства в классе <code>Person</code>.
#|en| Start by removing its field and property in the <code>Person</code> class.
#|uk| Почнемо з видалення його поля і властивості в класі <code>Person</code>.

Remove selected

Select whole "TelephoneNumber"

#|ru| Всё, больше ничего не удерживает нас от удаления самого класса. Спасибо за службу, <code>TelephoneNumber</code>, мы будем вспоминать о тебе только хорошее!
#|en| Voila! Nothing is holding us back now from removing the class itself. Thank you for the good times, <code>TelephoneNumber</code>, they were good indeed!
#|uk| Все, більше нічого не утримує нас від видалення самого класу. Дякуємо за службу, <code>TelephoneNumber</code>, ми будемо згадувати про тебе тільки хороше!

Remove selected

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.