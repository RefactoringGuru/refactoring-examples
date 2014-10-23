introduce-local-extension:csharp

###

1. Создайте новый класс-расширение и сделайте его обёрткой служебного класса.

2. Создайте конструкторы, дублирующие необходимые конструкторы служебного класса.

3. Создайте альтернативный «конвертирующий» конструктор, который принимает в параметрах только объект оригинального класса.

4. Создайте в классе новые расширенные методы. Переместите в него внешние методы из других классов, либо удалите их, если расширение уже имеет такой функционал.

5. Замените использование служебного класса новым классом-расширением в тех местах, где нужна расширенная функциональность.



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

  //TODO: Foreign method. Should be on DateTime.
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

# В предыдущем примере <a href="/introduce-foreign-method">Введение внешнего метода</a> мы использовали <i>методы-расширения</i>, чтобы добавить требуемую функциональность структуре <code>DateTime</code>.

# Этот способ хорош, если задача ограничивается добавлением методов. Однако если необходимо расширить функционал класса добавлением свойств, или же переопределить у него какой-нибудь из имеющихся методов, то следует воспользоваться рефакторингом <i>Введение локального расширения</i>, который мы сейчас и рассмотрим.

# Возьмём за основу код из предыдущего примера, в котором требовалось расширить функционал структуры <code>DateTime</code>. <i>Введение локального расширения</i> можно осуществить двумя способами: через создание класса-наследника либо через создание класса-обёртки. <code>DateTime</code> является структурой, и поэтому наследование от него не допускается, так что мы пойдем по пути «обёртывания».

Go to the end of file

# Для начала давайте создадим новый класс-обёртку.

Print:
```


// Local extension class.
public class MfDateTimeWrap
{
}
```

Set step 2

Go to the start of "MfDateTimeWrap"

# Затем добавим в него приватное поле того типа, который мы собираемся «обернуть».

Print:
```

  private DateTime date;
```

Go to the end of "MfDateTimeWrap"

# Создадим дублирующие конструкторы, которые будут делегировать вызовы конструкторов созданного поля. При этом нет необходимости дублировать все конструкторы поля, достаточно лишь тех, которые используются в клиентском коде. Для примера давайте реализуем конструктор без параметров.

Print:
```


  public MfDateTimeWrap()
  {
    this.date = new DateTime();
  }
```

Set step 3

# Теперь добавим конвертирующий конструктор, принимающий в качестве параметра объект <code>DateTime</code>.

Go to the end of "MfDateTimeWrap"

Print:
```

  public MfDateTimeWrap(DateTime date)
  {
    this.date = date;
  }
```

Select "public |||MfDateTimeWrap|||()"

# После чего можем перенаправить в него вызовы с дублирующих конструкторов.

Select:
```

    this.date = new DateTime();
  
```

Remove selected

Go to "public MfDateTimeWrap()|||"

Replace ": this(new DateTime())"

Set step 4

Select whole "GetNearFirstDate"

# Когда конструкторы класса готовы, можно добавить в него новые методы или перенести внешние методы из других классов. Давайте перенесём метод <code>GetNearFirstDate()</code>, воспользовавшись <a href="/move-method">перемещением метода</a>.

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

# Параметр метода нам теперь не нужен, т.к. метод находится внутри класса-обёртки, и нужные данные можно получить непосредственно из поля <code>date</code>.

Remove selected

Wait 500ms

Select "(||||||date." in "GetNearFirstDate" in "MfDateTimeWrap"
+ Select "||||||date;" in "GetNearFirstDate" in "MfDateTimeWrap"
+ Select "= ||||||date." in "GetNearFirstDate" in "MfDateTimeWrap"

Print "this."

Select "|||static |||DateTime GetNearFirstDate" in "MfDateTimeWrap"

# Кроме того, метод перестаёт быть статическим.

Remove selected

Set step 5

Select "GetNearFirstDate(previousDate)"

# Изменим код, использующий внешний метод, чтобы вместо него он использовал новый класс-расширение.

Print "new MfDateTimeWrap(previousDate).GetNearFirstDate()"

Select whole "GetNearFirstDate" in "Account"
+ Select:
```

  //TODO: Foreign method. Should be on DateTime.

```
# После всех замен внешний метод можно удалить.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.