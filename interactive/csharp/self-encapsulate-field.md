self-encapsulate-field:csharp

###

1.ru. Создайте свойство, инкапсулирующее поле.
1.en. Create a property that encapsulates the field.
1.uk. Створіть властивість, яка інкапсулює поле.

2.ru. Найдите все обращения к полю и замените их вызовами соответствующих свойств.
2.en. Find all references to the field and replace them with calls for the relevant properties.
2.uk. Знайдіть всі звернення до поля і замініть їх викликами відповідних властивостей.

3.ru. Если свойство лишь делегирует доступ к полю, не внося при этом дополнительной логики, то можете преобразовать его в автореализуемое свойство.
3.en. If a property merely delegates access to a field without contributing to the logic, you can convert it to an auto-implemented property.
3.uk. Якщо властивість лише делегує доступ до поля, не вносячи при цьому додаткової логіки, то можете перетворити її в автореалізуєму властивість.



###

```
public class IntRange
{
  private int low, high;

  public IntRange(int low, int high)
  {
    this.low = low;
    this.high = high;
  }

  public bool Includes(int arg)
  {
    return arg >= low && arg <= high;
  }
  public void Grow(int factor)
  {
    high = high * factor;
  }
}
```

###

```
public class IntRange
{
  private int Low
  {
    get;
    set;
  }
  private int High
  {
    get;
    set;
  }

  public IntRange(int low, int high)
  {
    this.Low = low;
    this.High = high;
  }

  public bool Includes(int arg)
  {
    return arg >= Low && arg <= High;
  }
  public void Grow(int factor)
  {
    High = High * factor;
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Самоинкапсуляцию</i> на примере класса диапазонов.<br/><br/>Самоинкапсуляция заключается в реализации доступа к полям через свойства, в том числе, в методах самого класса.
#|en| Let's look at <i>Self-Encapsulation</i> using the example of a range class.<br/><br/>Self-encapsulation differs from regular encapsulation by requiring even its own class to access fields through getters and setters.
#|uk| Давайте розглянемо <i>Самоінкапсуляцію<i> на прикладі класу діапазонів.<br/><br/>Самоінкапсуляція полягає в реалізації доступу до полів через властивості, в тому числі, в методах самого класу.

Select "private int |||low|||,"
+Select ", |||high|||;"

#|ru| Для начала создадим свойства, инкапсулирующие наши поля. Эти свойства можно сделать приватными, если к ним не требуется доступа извне.
#|en| To start, let's create properties that encapsulate our fields. These properties can be made private if external access to them is not needed.
#|uk| Для початку створимо властивості, які інкапсулюють наші поля. Ці властивості можна зробити приватними, якщо до них не потрібен доступ ззовні.

Go to before "IntRange" in "IntRange"

Print:
```

  private int Low
  {
    get { return low; }
    set { low = value; }
  }
  private int High
  {
    get { return high; }
    set { high = value; }
  }

```

Set step 2

Select "low" in "Includes"
+ Select "high" in "Includes"
+ Select "high" in "Grow"

#|ru| В нашем примере есть несколько методов, которые используют прямой доступ к полям.
#|en| Our example has several methods that use direct access to fields.
#|uk| У нашому прикладі є декілька методів, які використовують прямий доступ до полів.

#|ru| Для завершения самоинкапсуляции давайте заменим все обращения к полям в этих методах на обращения к соответствующим свойствам.
#|en| Complete self-encapsulation by replacing all references to fields in these methods with references to the corresponding properties.
#|uk| Для завершення самоінкапсуляціі давайте замінимо всі звернення до полів в цих методах на звернення до відповідних властивостей.

Select "low" in "Includes"

Replace "Low"

Wait 500ms

Select "high" in "Includes"

Replace "High"

Wait 500ms

Select "high" in "Grow"

Replace "High"

Select "this.low"
+Select "this.high"

#|ru| Как вы могли заметить, мы еще не производили замену полей в конструкторе. Дело в том, что иногда в сеттерах может быть прописана логика, отличная от простого присваивания (которое требуется в конструкторе).
#|en| As you may have noted, we have not yet replaced the fields in the constructor. This is because sometimes setters may have logic that works differently than simple assignment (which is what the constructor needs).
#|uk| Як ви могли помітити, ми ще не проводили заміну полів в конструкторі. Справа в тому, що іноді в сеттерах може бути прописана логіка, відмінна від простого присвоювання (яке потрібне в конструкторі). 

Select "low = value;"
+Select "high = value;"

#|ru|^ Поэтому, только убедившись, что наши сеттеры имеют стандартную логику присваивания, производим замену в конструкторе.
#|en|^ For this reason, we make sure that our setters have standard assignment logic before performing replacement in the constructor.
#|uk|^ Тому, тільки переконавшись, що наші сеттери мають стандартну логіку присвоювання, можемо здыйснювати заміну в конструкторі.

Select "this.|||low|||"

Replace "Low"

Select "this.|||high|||"

Replace "High"

Set step 3

#|ru| На этом рефакторинг можно считать законченным, но мы добавим коду еще немного лаконичности - преобразуем наши свойства в <i>автореализуемые свойства</i>.
#|en| The refactoring is now technically complete, but we can tighten up the code a bit by converting our properties to <i>auto-implemented properties</i>.
#|uk| На цьому рефакторинг можна вважати закінченим, але ми додамо коду ще трохи лаконічності – перетворимо наші властивості в <i>автореалізуємі властивості<i>.

Select " { return low; }"
+Select " { low = value; }"
+Select " { return high; }"
+Select " { high = value; }"

#|ru| Для этого удалим в свойствах тела геттеров и сеттеров…
#|en| So we remove getter and setter bodies in the properties…
#|uk| Для цього видалимо у властивостях тіла геттерів та сеттерів…

Replace ";"

Select:
```
  private int low, high;


```

#|ru|^ …после чего удалим ненужные больше поля <code>low</code> и <code>high</code>.
#|en|^ …and then remove <code>low</code> and <code>high</code> fields that are no longer necessary.
#|uk|^ …після чого видалимо непотрібні більше поля <code>low</code> і <code>high</code>.

Remove selected

#C|ru| Все! Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Hurray! Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Все! Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.