introduce-foreign-method:csharp

###

1. Создайте новый метод в клиентском классе.

2. В этом методе создайте параметр, в который будет передаваться объект служебного класса.

3. Извлеките волнующие вас участки кода в этот метод и замените их вызовами метода.

4. Оставьте в комментарии к этому методу метку <i>Foreight method</i> и призыв поместить этот метод в служебный класс, если такая возможность появится в дальнейшем.



###

```
public class Account
{
  // ...
  double SchedulePayment()
  {
    DateTime paymentDate;

    if (previousDate.Day != 1)
    {
      paymentDate = previousDate.AddMonths(1);
      paymentDate.Day = 1;
    }
    else
      paymentDate = previousDate;

    // Issue a payment using paymentDate.
    // ...
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
    DateTime paymentDate = GetNearFirstDate(previousDate);

    // Issue a payment using paymentDate.
    // ...
  }

  //TODO: Foreign method. Should be on DateTime.
  public static DateTime GetNearFirstDate(DateTime date)
  {
    if (date.Day == 1)
      return date;

    DateTime result = date.AddMonths(1);
    result.Day = 1;

    return result;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Введение внешнего метода</i> на примере класса банковского счета.

Select:
```
    if (previousDate.Day != 1)
    {
      paymentDate = previousDate.AddMonths(1);
      paymentDate.Day = 1;
    }
    else
      paymentDate = previousDate;
```

# В этом классе есть некий код, который открывает новый период выставления счетов только первого числа ближайшего месяца.

# Было бы идеально, если бы класс <code>DateTime</code> имел метод получения даты ближайшего указанного числа (например, <code>previousDate.GetNearDate()</code>), но он его не имеет, да и к тому же мы не можем его изменить, т.к. он стандартный.

Go to the end of "Account"

# Тем не менее, мы можем создать такой «внешний» метод в своём собственном классе.

Print:
```


  public DateTime GetNearFirstDate()
  {
    if (previousDate.Day == 1)
      return previousDate;

    DateTime result = previousDate.AddMonths(1);
    result.Day = 1;

    return result;
  }
```

Set step 2

Go to parameters of "GetNearFirstDate"

# Чтобы метод был более универсальным, в него следует добавить параметр класса <code>DateTime</code>. По сути, мы будем расширять функциональность объекта, который подаётся в этом параметре.

Print "DateTime date"

Select "previousDate" in "GetNearFirstDate"

Wait 500ms

Print "date"

Go to type of "GetNearFirstDate"

# Также следует объявить метод статическим, дабы к нему был доступ и из другого кода, не связанного с <code>Account</code>.

Print "static "

Set step 3

Select:
```
;

    if (previousDate.Day != 1)
    {
      paymentDate = previousDate.AddMonths(1);
      paymentDate.Day = 1;
    }
    else
      paymentDate = previousDate;
```

# Теперь метод можно использовать в остальном коде.

Print " = GetNearFirstDate(previousDate);"

Set step 4

Go to before "GetNearFirstDate"

# В качестве последнего штриха, следует добавить комментарий к «внешнему методу» о его природе. В дальнейшем это позволит избежать путаницы с его использованием. Кроме того, если в программе будет создан новый класс для хранения дополнительных функций с датами, этот метод будет легко найти и переместить в лучшее место.

Print:
```

  //TODO: Foreign method. Should be on DateTime.
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.