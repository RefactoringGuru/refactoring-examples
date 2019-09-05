inline-class:java

###

1.ru. Создайте в классе-приёмнике публичные поля и методы, которые есть в классе-доноре. Методы должны обращаться к аналогичным методам класса-донора.
1.en. In the recipient class, create the public fields and methods present in the donor class. Methods should refer to the equivalent methods of the donor class.
1.uk. Створіть в класі-приймачі публічні поля і методи, такі ж, як в класі-донорі. Методи повинні звертатися до аналогічних методів класу-донору.

2.ru. Замените все обращения к классу-донору обращениями к полям и методам класса-приёмника.
2.en. Replace all references to the donor class with references to the fields and methods of the recipient class.
2.uk. Замініть усі звернення до класу-донору зверненнями до полів і методів класу-приймача.

3.ru. Используйте <a href="/move-method">перемещение метода</a> и <a href="/move-field">перемещение поля</a> для перемещения функциональности в класс-приёмник из исходного класса. Продолжаем делать это, пока в исходном классе ничего не останется.
3.en. Use <a href="/move-field">move field</a> and <a href="/move-method">move field</a> for moving functionality from the original class to the recipient class. Continue doing so until nothing remains in the original class.
3.uk. Використовуйте <a href="/move-method"> переміщення методу</a> і <a href="/move-field"> переміщення поля</a> для переміщення функціональності в клас-приймач з вихідного класу. Продовжуємо робити це, поки у вихідному класі нічого не залишиться.

4.ru. Удалите исходный класс.
4.en. Delete the original class.
4.uk. Видаліть початковий клас.



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

Set step 1

#|ru| Давайте рассмотрим <i>Встраивание класса</i> на примере класса личности и телефонного номера, который в нём используется.
#|en| Let's look at <i>Inline Class</i> using the person class and its phone number as an example.
#|uk| Давайте розглянемо <i>Вбудовування класу</i> на прикладі класу особистості і телефонного номера, який в ньому використовується.

Select name of "TelephoneNumber"

#|ru| Мы хотим включить класс <code>TelephoneNumber</code> обратно в класс <code>Person</code>, так как он потерял свою актуальность для наших задач.
#|en| We want to include the <code>TelephoneNumber</code> class back in the <code>Person</code> class, since it become unnecessary complex for our needs.
#|uk| Ми хочемо включити клас <code>TelephoneNumber</code> назад в клас <code>Person</code>, оскільки він втратив свою актуальність для наших задач.

Go to the end of "Person"

#|ru| Начнём с объявления в классе <code>Person</code> всех видимых методов класса телефонного номера.
#|en| We start by declaring all visible methods of the phone number class in the <code>Person</code> class.
#|uk| Почнемо з оголошення в класі <code>Person</code> всіх видимих методів класу телефонного номера.

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

#|ru| На первом этапе все эти методы будут делегировать работу объекту телефонного номера.
#|en| For the first step, all these methods will delegate to the phone number object.
#|uk| На першому етапі всі ці методи будуть делегувати роботу об'єкту телефонного номеру.

Set step 2

Select "martin.getOfficeTelephone().setAreaCode("781")"

#|ru| Теперь найдём все случаи использования класса телефонного номера в клиентском коде и заменим его использованием класса <code>Person</code>
#|en| Now find all cases where the phone number class is used in client code and replace it with calls to the delegate methods in <code>Person</code>.
#|uk| Тепер знайдемо всі випадки використання класу телефонного номера в клієнтському коді та замінимо його використанням класу <code>Person</code>

Print "martin.setAreaCode("781")"

Set step 3

#|ru| После этого мы со спокойным сердцем можем выполнять <a href="/ru/move-method">перемещение метода</a> и завершать <a href="/move-field">перемещение поля</a> для перемещения всех полей и методов в класс <code>Person</code>. Эти перемещения можно делать по одному либо все сразу, если их не так много.
#|en| We can then proceed to <a href="/move-method">Move Method</a> and <a href="/move-field">Move Field</a> for moving all fields and methods to the <code>Person</code> class. These changes can be done one by one or, if there are not too many, all at once.
#|uk| Після цього ми зі спокійним серцем можемо виконувати <a href="/uk/move-method">переміщення методу</a> і завершувати <a href="/move-field">переміщення поля</a> для переміщення всіх полів і методів в клас <code>Person</code>. Ці переміщення можна робити по одному або всі відразу, якщо їх не так багато.

Select:
```
  private String number;
  private String areaCode;


```

#|ru| Сначала перемещаем поля.
#|en| First move the fields.
#|uk| Спочатку переміщуємо поля.

Remove selected

Go to " new TelephoneNumber();|||"

Print:
```

  private String number;
  private String areaCode;
```

Select body of "getAreaCode" in "TelephoneNumber"

#|ru| Потом переносим каждый метод…
#|en| Then move each method…
#|uk| Потім переносимо кожен метод…

Wait 500ms

Select body of "getAreaCode" in "Person"

Replace:
```
    return areaCode;
```

Select whole of "getAreaCode" in "TelephoneNumber"

Remove selected


Select body of "setAreaCode" in "TelephoneNumber"

#|ru| …один за другим…
#|en| …one by one…
#|uk| …один за іншим…

Wait 500ms

Select body of "setAreaCode" in "Person"

Replace:
```
    areaCode = arg;
```

Select whole of "setAreaCode" in "TelephoneNumber"

Remove selected


Select body of "getNumber" in "TelephoneNumber"

#|ru| …переносим все методы…
#|en| …move all the methods…
#|uk| …переносимо всі методи…

Wait 500ms

Select body of "getNumber" in "Person"

Replace:
```
    return number;
```

Select whole of "getNumber" in "TelephoneNumber"

Remove selected

Select body of "setNumber" in "TelephoneNumber"

#|ru| …все до последнего…
#|en| …each and every one…
#|uk| …все до останнього…


Wait 500ms

Select body of "setNumber" in "Person"

Replace:
```
    number = arg;
```

Select whole of "setNumber" in "TelephoneNumber"

Remove selected

Select body of "getTelephoneNumber" in "TelephoneNumber"

#|ru| … и, наконец, последний геттер самого номера.
#|en| …and finally the last getter of the phone number itself.
#|uk| …і, нарешті, останній геттер самого номера.

Wait 500ms

Select body of "getTelephoneNumber" in "Person"

Replace:
```
    return ("(" + areaCode + ") " + number);
```

Select whole of "getTelephoneNumber" in "TelephoneNumber"

Remove selected

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
+ Select whole "getOfficeTelephone"

#|ru| Начнём с удаления его поля и геттера в классе <code>Person</code>.
#|en| Start by removing its field and getter in the <code>Person</code> class.
#|uk| Почнемо з видалення його поля і геттера в класі <code>Person</code>.

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