introduce-foreign-method:csharp

###

1.ru. Создайте новый метод в клиентском классе.
1.en. Create a new method in the client class.
1.uk. Створіть новий метод в клієнтському класі.

2.ru. В этом методе создайте параметр, в который будет передаваться объект служебного класса.
2.en. In this method, create a parameter to which the object of the utility class will be passed. If this object can be obtained from the client class, you do not have to create such a parameter.
2.uk. У цьому методі створіть параметр, в який передаватиметься об'єкт службового класу. Якщо цей об'єкт може бути отриманий з клієнтського класу, параметр можна не створювати.

3.ru. Извлеките волнующие вас участки кода в этот метод и замените их вызовами метода.
3.en. Extract the relevant code fragments to this method and replace them with method calls.
3.uk. Витягніть ділянки коду, які вам потрібні, в цей метод і замініть їх викликами методу.

4.ru. Оставьте в комментарии к этому методу метку <i>Foreign method</i> и призыв поместить этот метод в служебный класс, если такая возможность появится в дальнейшем.
4.en. Leave a comment for the method, labeling it as <i>Foreign method</i> and requesting that the method be placed in a utility class if this becomes possible in the future.
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
#|en| Let's look at <i>Introduce Foreign Method</i> using the example of a bank account class.
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
#|en| The class contains code that opens a new billing period only on the first day of the upcoming month.
#|uk| В цьому класі є якийсь код, який відкриває новий період виставлення рахунків тільки першого числа найближчого місяця.

#|ru| Было бы идеально, если бы класс <code>DateTime</code> имел метод получения даты ближайшего указанного числа (например, <code>previousDate.GetNearDate()</code>), но он его не имеет.
#|en| Ideally, the <code>DateTime</code> class would have a method for getting the next relevant date (for example, <code>previousDate.GetNearDate()</code>). However, it does not have one.
#|uk| Було б ідеально, якби клас <code>DateTime</code> мав метод отримання дати найближчого зазначеного числа (наприклад, <code>previousDate.GetNearDate()</code>), але він його не має.

Go to the end of "Account"

#|ru| Однако мы вполне можем создать такой «внешний» метод в своём собственном классе.
#|en| What we can do though is create a "foreign" method in its own class.
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
#|en| To make the method more universal, add a <code>DateTime</code> class parameter. In essence, we will be broadening the functionality of the object sent in this parameter.
#|uk| Щоб метод був більш універсальним, в нього слід додати параметр класу <code>DateTime</code>. По суті, ми будемо розширювати функціональність об'єкта, який подається в цьому параметрі.

Print "DateTime date"

Select "previousDate" in "GetNearFirstDate"

Replace "date"

Go to type of "GetNearFirstDate"

#|ru| Также следует объявить метод статическим, чтобы к нему был доступ и из другого кода, не связанного с <code>Account</code>.
#|en| You should also declare the method static to make it accessible from other code not associated with <code>Account</code>.
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
#|en| The method can now be used in the remaining code.
#|uk| Тепер метод можна використовувати в іншому коді.

Print " = GetNearFirstDate(previousDate);"

Set step 4

Go to before "GetNearFirstDate"

#|ru| Давайте добавим уточняющий комментарий к «внешнему методу». В дальнейшем это позволит избежать путаницы с его использованием. Кроме того, если в программе будет создан новый класс для хранения дополнительных функций с датами, этот метод будет легко найти и переместить в лучшее место.
#|en| Let's add a comment to clarify the "foreign method". This will avoid potential confusion in the future. In addition, if a new class is created in the program for storing additional functions with dates, this method can be easily found and moved to a better place.
#|uk| Давайте додамо уточнюючий коментар до «зовнішнього методу». Надалі це дозволить уникнути плутанини з його використанням. Крім того, якщо в програмі буде створено новий клас для зберігання додаткових функцій з датами, цей метод буде легко знайти і перемістити в краще місце.

Print:
```

  //TODO: Foreign method. Should be on DateTime.
```

Go to after "Account"

#|ru| В принципе, таким лучшим местом мог бы стать класс, расширяющий возможности самого <code>DateTime</code>. Давайте напоследок реализуем его, для чего воспользуемся <i>методами расширения</i>, которые доступны в нашем арсенале, начиная с версии C# 3.0.
#|en| Generally, such a "better place" might be a place that expands the features of <code>DateTime</code>. We can implement it by using <i>extension methods</i>, a tool available in our arsenal since C# 3.0.
#|uk| В принципі, таким кращим місцем міг би стати клас, який розширює можливості самого <code>DateTime</code>. Давайте наостанок реалізуємо його, для чого скористаємося <i>методами розширення<i>, які доступні в нашому арсеналі, починаючи з версії C # 3.0.

Print:
```


public static class TypeExtensions
{
}
```

Select name of "GetNearFirstDate" in "Account"

#|ru| Перенесем в него наш метод...
#|en| Move our method to it…
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
#|en| …remove it from the original class…
#|uk| ...видалимо його з вихідного класу...

Remove selected

Select parameters of "GetNearFirstDate"

#|ru| ...в качестве первого параметра метод расширения должен принимать экземпляр того класса, для которого он будет вызван.
#|en| …as the first parameter, the extension method should accept the instance of the class for which it will be called.
#|uk| ...в якості першого параметру метод розширення повинен приймати примірник того класу, для якого він буде викликаний.

Select "||||||DateTime" in parameters of "GetNearFirstDate"

Print "this "

Select "GetNearFirstDate(previousDate)"

#|ru| Замечательно, осталось только прописать вызов нашего метода расширения в клиентском коде.
#|en| Excellent. Now just put a call to our extension method in the client code.
#|uk| Чудово, залишилося тільки прописати виклик нашого методу розширення в клієнтському коді.

Print "previousDate.GetNearFirstDate()"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's run the final compile.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.