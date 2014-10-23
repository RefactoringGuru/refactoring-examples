introduce-foreign-method:csharp

###

1.ru. Создайте новый метод в клиентском классе.
1.uk. Створіть новий метод в клієнтському класі.

2.ru. В этом методе создайте параметр, в который будет передаваться объект служебного класса.
2.uk. У цьому методі створіть параметр, в який передаватиметься об'єкт службового класу. Якщо цей об'єкт може бути отриманий з клієнтського класу, параметр можна не створювати.

3.ru. Извлеките волнующие вас участки кода в этот метод и замените их вызовами метода.
3.uk. Витягніть ділянки коду, які вам потрібні, в цей метод і замініть їх викликами методу.

4.ru. Оставьте в комментарии к этому методу метку <i>Foreign method</i> и призыв поместить этот метод в служебный класс, если такая возможность появится в дальнейшем.
4.uk. Залиште в коментарі до цього методу мітку <i>Foreign method</i> і заклик помістити цей метод в службовий клас, якщо така можливість з'явиться в подальшому.



###

```
public class Account
{
  // ...
  private double SchedulePayment()
  {
    DateTime paymentDate;

    if (previousDate.Day != 1)
    {
      paymentDate = previousDate.AddMonths(1);
      paymentDate = new DateTime(paymentDate.Year, paymentDate.Month, 1);
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
  private double SchedulePayment()
  {
    DateTime paymentDate = previousDate.GetNearFirstDate();

    // Issue a payment using paymentDate.
    // ...
  }
}

public static class TypeExtensions
{
  public static DateTime GetNearFirstDate(this DateTime date)
  {
    if (date.Day == 1)
      return date;

    DateTime nextDate = date.AddMonths(1);
    
    return new DateTime(nextDate.Year, nextDate.Month, 1);
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Введение внешнего метода</i> на примере класса банковского счета.
#|uk| Давайте розглянемо <i>Запровадження зовнішнього методу<i> на прикладі класу банківського рахунку.

Select:
```
    if (previousDate.Day != 1)
    {
      paymentDate = previousDate.AddMonths(1);
      paymentDate = new DateTime(paymentDate.Year, paymentDate.Month, 1);
    }
    else
      paymentDate = previousDate;
```

#|ru| В этом классе есть некий код, который открывает новый период выставления счетов только первого числа ближайшего месяца.
#|uk| В цьому класі є якийсь код, який відкриває новий період виставлення рахунків тільки першого числа найближчого місяця.

#|ru| Было бы идеально, если бы класс <code>DateTime</code> имел метод получения даты ближайшего указанного числа (например, <code>previousDate.GetNearDate()</code>), но он его не имеет.
#|uk| Було б ідеально, якби клас <code>DateTime</code> мав метод отримання дати найближчого зазначеного числа (наприклад, <code>previousDate.GetNearDate()</code>), але він його не має.

Go to the end of "Account"

#|ru| Однако мы вполне можем создать такой «внешний» метод в своём собственном классе.
#|uk| Однак ми цілком можемо створити такий «зовнішній» метод у своєму власному класі.

Print:
```


  public DateTime GetNearFirstDate()
  {
    if (previousDate.Day == 1)
      return previousDate;

    DateTime nextDate = previousDate.AddMonths(1);
    
    return new DateTime(nextDate.Year, nextDate.Month, 1);
  }
```

Set step 2

Go to parameters of "GetNearFirstDate"

#|ru| Чтобы метод был более универсальным, в него следует добавить параметр класса <code>DateTime</code>. По сути, мы будем расширять функциональность объекта, который подаётся в этом параметре.
#|uk| Щоб метод був більш універсальним, в нього слід додати параметр класу <code>DateTime</code>. По суті, ми будемо розширювати функціональність об'єкта, який подається в цьому параметрі.

Print "DateTime date"

Select "previousDate" in "GetNearFirstDate"

Replace "date"

Go to type of "GetNearFirstDate"

#|ru| Также следует объявить метод статическим, чтобы к нему был доступ и из другого кода, не связанного с <code>Account</code>.
#|uk| Також слід оголосити метод статичним, щоб до нього був доступ і з іншого коду, не пов'язаного з <code>Account</code>.

Print "static "

Set step 3

Select:
```
;

    if (previousDate.Day != 1)
    {
      paymentDate = previousDate.AddMonths(1);
      paymentDate = new DateTime(paymentDate.Year, paymentDate.Month, 1);
    }
    else
      paymentDate = previousDate;
```

#|ru| Теперь метод можно использовать в остальном коде.
#|uk| Тепер метод можна використовувати в іншому коді.

Print " = GetNearFirstDate(previousDate);"

Set step 4

Go to before "GetNearFirstDate"

#|ru| Давайте добавим уточняющий комментарий к «внешнему методу». В дальнейшем это позволит избежать путаницы с его использованием. Кроме того, если в программе будет создан новый класс для хранения дополнительных функций с датами, этот метод будет легко найти и переместить в лучшее место.
#|uk| Давайте додамо уточнюючий коментар до «зовнішнього методу». Надалі це дозволить уникнути плутанини з його використанням. Крім того, якщо в програмі буде створено новий клас для зберігання додаткових функцій з датами, цей метод буде легко знайти і перемістити в краще місце.

Print:
```

  //TODO: Foreign method. Should be on DateTime.
```

Go to after "Account"

#|ru| В принципе, таким лучшим местом мог бы стать класс, расширяющий возможности самого <code>DateTime</code>. Давайте напоследок реализуем его, для чего воспользуемся <i>методами расширения</i>, которые доступны в нашем арсенале, начиная с версии C# 3.0.
#|uk| В принципі, таким кращим місцем міг би стати клас, який розширює можливості самого <code>DateTime</code>. Давайте наостанок реалізуємо його, для чого скористаємося <i>методами розширення<i>, які доступні в нашому арсеналі, починаючи з версії C # 3.0.

Print:
```


public static class TypeExtensions
{
}
```

Select name of "GetNearFirstDate" in "Account"

#|ru| Перенесем в него наш метод...
#|uk| Перенесемо в нього наш метод...

Go to the end of "TypeExtensions"

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

Select in "Account":
```


  //TODO: Foreign method. Should be on DateTime.
  public static DateTime GetNearFirstDate(DateTime date)
  {
    if (date.Day == 1)
      return date;

    DateTime nextDate = date.AddMonths(1);
    
    return new DateTime(nextDate.Year, nextDate.Month, 1);
  }
```

#|ru| ...удалим его из исходного класса...
#|uk| ...видалимо його з вихідного класу...

Remove selected

Select parameters of "GetNearFirstDate"

#|ru| ...в качестве первого параметра метод расширения должен принимать экземпляр того класса, для которого он будет вызван.
#|uk| ...в якості першого параметру метод розширення повинен приймати примірник того класу, для якого він буде викликаний.

Select "||||||DateTime" in parameters of "GetNearFirstDate"

Print "this "

Select "GetNearFirstDate(previousDate)"

#|ru| Замечательно, осталось только прописать вызов нашего метода расширения в клиентском коде.
#|uk| Чудово, залишилося тільки прописати виклик нашого методу розширення в клієнтському коді.

Print "previousDate.GetNearFirstDate()"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.