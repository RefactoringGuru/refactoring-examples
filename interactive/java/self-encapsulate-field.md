self-encapsulate-field:java

###

1.ru. Создайте геттер (и опциональный сеттер) для поля.
1.en. Create a getter (and optional setter) for the field.
1.uk. Створіть геттер (і, опціонально, сетер) для поля.

2.ru. Найдите все прямые обращения к полю и замените их вызовами геттера и сеттера.
2.en. Find all direct invocations of the field and replace them with getter and setter calls.
2.uk. Знайдіть усі прямі звернення до поля і замініть їх викликами геттера і сетера.



###

```
class IntRange {
  private int low, high;

  public boolean includes(int arg) {
    return arg >= low && arg <= high;
  }
  public void grow(int factor) {
    high = high * factor;
  }
  public IntRange(int low, int high) {
    this.low = low;
    this.high = high;
  }
}
```

###

```
class IntRange {
  private int low, high;

  int getLow() {
    return low;
  }
  int getHigh() {
    return high;
  }
  void setLow(int arg) {
    low = arg;
  }
  void setHigh(int arg) {
    high = arg;
  }
  public boolean includes(int arg) {
    return arg >= getLow() && arg <= getHigh();
  }
  public void grow(int factor) {
    setHigh(getHigh() * factor);
  }
  public IntRange(int low, int high) {
    this.low = low;
    this.high = high;
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Самоинкапсуляцию</i> на примере класса диапазонов.<br/><br/>Самоинкапсуляция заключается в реализации доступа к полям через методы доступа даже в методах самого класса.
#|en| Let's look at <i>Self-Encapsulation</i> using the example of a range class.<br/><br/>Self-encapsulation differs from regular encapsulation by requiring even its own class to access fields through getters and setters.
#|uk| Давайте розглянемо <i>Самоінкапсуляцію</i> на прикладі класу діапазонів.<br/><br/>Самоінкапсуляція полягає в реалізації доступу до полів через методи доступу навіть у методах самого класу.

Go to before "includes"

#|ru| Эти методы доступа нужно создать, если их ещё нет. Итак, создадим геттеры и сеттеры в нашем классе.
#|en| These access methods must be created if they do not yet exist. So go ahead and create getters and setters in our class.
#|uk| Ці методи доступу потрібно створити, якщо їх ще немає. Отже, створимо геттери та сеттери в нашому класі.

Print:
```

  int getLow() {
    return low;
  }
  int getHigh() {
    return high;
  }
  void setLow(int arg) {
    low = arg;
  }
  void setHigh(int arg) {
    high = arg;
  }
```

Set step 2

Select "low" in "includes"
+ Select "high" in "includes"
+ Select "high" in "grow"

#|ru| В нашем примере есть несколько методов, которые используют прямой доступ к полям.
#|en| Our example has several methods that use direct access to fields.
#|uk| У нашому прикладі є декілька методів, які використовують прямий доступ до полів.

#|ru| Для завершения самоинкапсуляции давайте заменим все обращения к полям в этих методах вызовами геттеров и сеттеров.
#|en| To finish self-encapsulation, let's replace all references to fields in these methods with getter and setter calls.
#|uk| Для завершення самоінкапсуляціі давайте замінимо всі звернення до полів в цих методах викликами геттеров і сеттерів.

Select "low" in "includes"

Replace "getLow()"

Wait 500ms

Select "high" in "includes"

Replace "getHigh()"

Wait 500ms

Select "high = " in "grow"

Replace "setHigh("

Wait 500ms

Go to "|||;" in "grow"

Print ")"

Wait 500ms

Select "(|||high|||" in "grow"

Replace "getHigh()"

Select "this.low"
+Select "this.high"

#|ru| Как вы могли заметить, мы не трогали присваивания в конструкторе. Часто предполагается, что сеттер применяется уже после создания объекта, поэтому его поведение может быть иным, чем во время инициализации.
#|en| As you may have noticed, we did not touch the assignment in the constructor. It is often assumed that a setter is used after an object has already been created, so its behavior may be different than during initialization.
#|uk| Як ви могли помітити, ми не чіпали присвоювання в конструкторі. Часто передбачається, що сетер застосовується вже після створення об'єкта, тому його поведінка може бути іншою, ніж під час ініціалізації.

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