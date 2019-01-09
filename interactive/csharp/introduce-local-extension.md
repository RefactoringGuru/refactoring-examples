introduce-local-extension:csharp

###

1.ru. Создайте новый класс-расширение и сделайте его обёрткой служебного класса.
1.en. Create a new extension class and make it the inheritor of the utility class.
1.uk. Створіть новий класс-розширення і зробіть його обгорткою службового класу.

2.ru. Создайте конструкторы, дублирующие необходимые конструкторы служебного класса.
2.en. Create a constructor that uses the parameters of the constructor of the utility class.
2.uk. Створіть конструктори, дублюючі необхідні конструктори службового класу.

3.ru. Создайте альтернативный «конвертирующий» конструктор, который принимает в параметрах только объект оригинального класса.
3.en. Create an alternative "converting" constructor that accepts only an object of the original class in its parameters.
3.uk. Створіть альтернативний «конвертуючий» конструктор, який приймає в параметрах тільки об'єкт оригінального класу.

4.ru. Создайте в классе новые расширенные методы. Переместите в него внешние методы из других классов, либо удалите их, если расширение уже имеет такой функционал.
4.en. Create new extended methods in the class. Move foreign methods from other classes to this class or else delete the foreign methods if their functionality is already present in the extension.
4.uk. Створіть в класі нові розширені методи. Перемістіть в нього зовнішні методи з інших класів, або видалите їх, якщо розширення вже має такий функціонал.

5.ru. Замените использование служебного класса новым классом-расширением в тех местах, где нужна расширенная функциональность.
5.en. Replace use of the utility class with the new extension class in places where its functionality is needed.
5.uk. Замініть використання службового класу новим класом-розширенням в тих місцях, де потрібна розширена функціональність.



###

```
public class Account
{
  // ...
  double SchedulePayment()
  {
    DateTime paymentDate = GetNearFirstDate(previousDate);

    // Issue a payment using paymentDate.
    // ...
  }

  // TODO: Foreign method. Should be in the DateTime class.
  public static DateTime GetNearFirstDate(DateTime date)
  {
    if (date.Day == 1)
      return date;

    DateTime nextDate = date.AddMonths(1);
    
    return new DateTime(nextDate.Year, nextDate.Month, 1);
  }
}
```

###

```
public class Account
{
  // ...
  double SchedulePayment()
  {
    DateTime paymentDate = new MfDateTimeWrap(previousDate).GetNearFirstDate();

    // Issue a payment using paymentDate.
    // ...
  }
}

// Local extension class.
public class MfDateTimeWrap
{
  private DateTime date;

  public MfDateTimeWrap(): this(new DateTime())
  {}
  public MfDateTimeWrap(DateTime date)
  {
    this.date = date;
  }

  public DateTime GetNearFirstDate()
  {
    if (this.date.Day == 1)
      return this.date;

    DateTime nextDate = this.date.AddMonths(1);
    
    return new DateTime(nextDate.Year, nextDate.Month, 1);
  }
}
```

###

Set step 1

#|ru| В предыдущем примере <a href="/ru/introduce-foreign-method">Введение внешнего метода</a> мы использовали <i>методы-расширения</i>, чтобы добавить требуемую функциональность структуре <code>DateTime</code>.
#|en| In the previous example <a href="/introduce-foreign-method">Introduce Foreign Method</a>, we used <i>extension methods</i> to add necessary functionality to the <code>DateTime</code> structure.
#|uk| У попередньому прикладі <a href="/uk/introduce-foreign-method">Введення зовнішнього методу</a> ми використовували <i> методи-розширення</i>, щоб додати необхідну функціональність структурі <code>DateTime</code>.

#|ru| Этот способ хорош, если задача ограничивается добавлением методов. Однако если необходимо расширить функционал класса добавлением свойств, или же переопределить у него какой-нибудь из имеющихся методов, то следует воспользоваться рефакторингом <i>Введение локального расширения</i>, который мы сейчас и рассмотрим.
#|en| This choice is good if the task is limited to just adding methods. If you need to extend the functionality of a class by adding properties or redefining any of its existing methods, then use <i>Introduce Local Extension</i>, which we will now examine in greater detail.
#|uk| Цей спосіб хороший, якщо задача обмежується додаванням методів. Однак якщо необхідно розширити функціонал класу додаванням властивостей, або ж перевизначити у нього який-небудь з наявних методів, то слід скористатися рефакторингом <i>Введення локального розширення</i>, який ми зараз і розглянемо.

#|ru| Возьмём за основу код из предыдущего примера, в котором требовалось расширить функционал структуры <code>DateTime</code>. <i>Введение локального расширения</i> можно осуществить двумя способами: через создание класса-наследника либо через создание класса-обёртки. <code>DateTime</code> является структурой, и поэтому наследование от него не допускается, так что мы пойдем по пути «обёртывания».
#|en| Let's start with the code from the previous example, in which we needed to extend the functionality of the  <code>DateTime</code> structure. <i>Introduce Local Extension</i> can be performed in two ways: by creating a child class or creating a wrapper class. <code>DateTime</code> is a structure and, for this reason, does not support inheritance, so we will choose wrapping.
#|uk| Візьмемо за основу код з попереднього прикладу, в якому вимагалося розширити функціонал структури <code>DateTime</code>. <i>Введення локального розширення</i> можна здійснити двома способами: через створення класу-спадкоємця або через створення класу-обгортки. <code>DateTime</code> є структурою, і тому успадкування від нього не допускається, так що ми підемо по шляху «обгортання».

Go to the end of file

#|ru| Для начала давайте создадим новый класс-обёртку.
#|en| First, we create a new wrapper class.
#|uk| Для початку давайте створимо новий клас-обгортку.

Print:
```


// Local extension class.
public class MfDateTimeWrap
{
}
```

Set step 2

Go to the start of "MfDateTimeWrap"

#|ru| Затем добавим в него приватное поле того типа, который мы собираемся «обернуть».
#|en| Add a private field to it for the type that we want to place in a wrapper.
#|uk| Потім додамо в нього приватне поле того типу, який ми збираємося обгорнути.

Print:
```

  private DateTime date;
```

Go to the end of "MfDateTimeWrap"

#|ru| Создадим дублирующие конструкторы, которые будут делегировать вызовы конструкторов созданного поля. При этом нет необходимости дублировать все конструкторы поля, достаточно лишь тех, которые используются в клиентском коде. Для примера давайте реализуем конструктор без параметров.
#|en| Create duplicate constructors that will delegate calls to constructors of the field we have created. There is no need to duplicate all field constructors – just the ones used in the client code. For example, let's implement a constructor without parameters.
#|uk| Створимо дублюючі конструктори, які будуть делегувати виклики конструкторів створеного поля. При цьому немає необхідності дублювати всі конструктори поля, достатньо лише тих, які використовуються в клієнтському коді. Для прикладу давайте реалізуємо конструктор без параметрів.

Print:
```


  public MfDateTimeWrap()
  {
    this.date = new DateTime();
  }
```

Set step 3

#|ru| Теперь добавим конвертирующий конструктор, принимающий в качестве параметра объект <code>DateTime</code>.
#|en| Now add a converting constructor that accepts the original <code>DateTime</code> object as an argument.
#|uk| Тепер додамо конвертуючий конструктор, який приймає як параметр об'єкт <code>DateTime</code>.

Go to the end of "MfDateTimeWrap"

Print:
```

  public MfDateTimeWrap(DateTime date)
  {
    this.date = date;
  }
```

Select "public |||MfDateTimeWrap|||()"

#|ru| После чего можем перенаправить в него вызовы с дублирующих конструкторов.
#|en| Redirect the calls from duplicate constructors to it.
#|uk| Після чого можемо перенаправити в нього виклики з дублюючих конструкторів.

Select:
```

    this.date = new DateTime();
  
```

Remove selected

Go to "public MfDateTimeWrap()|||"

Replace ": this(new DateTime())"

Set step 4

Select whole "GetNearFirstDate"

#|ru| Когда конструкторы класса готовы, можно добавить в него новые методы или перенести внешние методы из других классов. Давайте перенесём метод <code>GetNearFirstDate()</code>, воспользовавшись <a href="/ru/move-method">перемещением метода</a>.
#|en| When the class constructors are ready, you can add new methods to it or move foreign methods form other classes. Let's move the <code>GetNearFirstDate()</code> method with the help of <a href="/move-method">Move Method</a>.
#|uk| Коли конструктори класу готові, можна додати в нього нові методи або перенести зовнішні методи з інших класів . Давайте перенесемо метод <code> GetNearFirstDate()</code>, скориставшись <a href="/uk/move-method">переміщенням методу</a>.

Go to the end of "MfDateTimeWrap"

Print:
```


  public static DateTime GetNearFirstDate(DateTime date)
  {
    if (date.Day == 1)
      return date;

    DateTime nextDate = date.AddMonths(1);
    
    return new DateTime(nextDate.Year, nextDate.Month, 1);
  }
```

Select parameters of "GetNearFirstDate" in "MfDateTimeWrap"

#|ru| Параметр метода нам теперь не нужен, так как метод находится внутри класса-обёртки, и нужные данные можно получить непосредственно из поля <code>date</code>.
#|en| The method parameter is no longer needed since the method is inside the <code>DateTime</code> wrapper. Thus, the required data can be taken from its own object.
#|uk| Параметр методу нам тепер не потрібен, тому що метод знаходиться всередині класу-обгортки, і потрібні дані можна отримати безпосередньо з поля <code>date</code>.

Remove selected

Wait 500ms

Select "(||||||date." in "GetNearFirstDate" in "MfDateTimeWrap"
+ Select "||||||date;" in "GetNearFirstDate" in "MfDateTimeWrap"
+ Select "= ||||||date." in "GetNearFirstDate" in "MfDateTimeWrap"

Print "this."

Select "|||static |||DateTime GetNearFirstDate" in "MfDateTimeWrap"

#|ru| Кроме того, метод перестаёт быть статическим.
#|en| The method is no longer static.
#|uk| Крім того, метод перестає бути статичним.

Remove selected

Set step 5

Select "GetNearFirstDate(previousDate)"

#|ru| Изменим код, использующий внешний метод, чтобы вместо него он использовал новый класс-расширение.
#|en| Now change the code that uses the foreign method so that it uses the new extension class instead.
#|uk| Тепер змінимо код, що використовує зовнішній метод так, щоб він замість цього використовував новий клас-розширення.

Print "new MfDateTimeWrap(previousDate).GetNearFirstDate()"

Select whole "GetNearFirstDate" in "Account"
+ Select:
```

  // TODO: Foreign method. Should be in the DateTime class.

```
#|ru| После всех замен внешний метод можно удалить.
#|en| After all changes are complete, we remove the external method from the client class.
#|uk| Після всіх замін зовнішній метод можна видалити.

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