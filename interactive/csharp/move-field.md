move-field:csharp

###

1.ru. Инкапсулируйте поле, если оно публичное.
1.uk. Інкапсулюйте поле, якщо воно є публічним.

2.ru. Создайте копию поля и свойство для доступа к нему в целевом классе.
2.uk. Створіть копію поля і властивість для доступу до нього в цільовому класі.

3.ru. Определите, как вы будете обращаться к целевому классу. Вполне возможно, что у вас уже есть поле или метод, которые возвращают подходящий экземпляр класса, но если таковых нет, то нужно будет создать (достаточно одного из них).
3.uk. Визначте, як ви будете звертатися до цільового класу. Цілком можливо, що у вас вже є поле або метод, які повертають відповідний екземпляр класу, але якщо таких немає, то потрібно буде створити (досить одного з них).

4.ru. Замените все обращения к старому полю на соответствующие обращения к полю в целевом классе.
4.uk. Замініть всі звернення до старого поля на відповідні звернення до поля в цільовому класі.

5.ru. Удалите поле в исходном классе.
5.uk. Видаліть поле в початковому класі.



###

```
public class Account
{
  // ...
  private AccountType type;
  private double interestRate;

  public double InterestForAmountDays(double amount, int days)
  {
    return interestRate * amount * days / 365.0;
  }
}

public class AccountType
{
  // ...
}
```

###

```
public class Account
{
  // ...
  private AccountType type;

  private double InterestRate
  {
    get {
      return type.InterestRate;
    }
    set {
      type.InterestRate = value;
    }
  }

  public double InterestForAmountDays(double amount, int days)
  {
    return InterestRate * amount * days / 365.0;
  }
  public double InterestForBigFamily(double familySize)
  {
    return InterestRate / familySize;
  }

  // other 10 methods, which use InterestRate
}

public class AccountType
{
  // ...
  public double InterestRate
  {
    get;
    set;
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Перемещение поля</i> на примере класса банковского счета.
#|uk| Давайте розглянемо <i>Переміщення поля<i> на прикладі класу банківського рахунку.

Select "double |||interestRate|||"

#|ru| Мы хотим переместить поле процентной ставки <code>interestRate</code> в класс типа счета <code>AccountType</code>.
#|uk| Ми хочемо перемістити поле процентної ставки <code>interestRate</code> в клас типу рахунку <code>AccountType</code>.

Select name of "InterestForAmountDays"

#|ru| Есть несколько методов, обращающихся к этому полю, одним из которых является метод <code>InterestForAmountDays()</code>.
#|uk| Є кілька методів, що звертаються до цього поля, одним з яких є метод <code>InterestForAmountDays()</code>.

Set step 2

Go to the end of "AccountType"

#|ru| Создадим копию поля в целевом классе...
#|uk| Створимо копію поля в цільовому класі...

Print:
```

  private double interestRate;
```

#|ru| ...а также создадим публичное свойство для доступа к этому полю извне.
#|uk| ...а також створимо публічну властивість для доступу до цього поля ззовні.

Go to the end of "AccountType"

Print:
```


  public double InterestRate
  {
    get {
      return interestRate;
    }
    set {
      interestRate = value;
    }
  }
```

Select:
```
  private double interestRate;

  public double InterestRate
  {
    get {
      return interestRate;
    }
    set {
      interestRate = value;
    }
  }
```

#|ru| Кстати, в данном случае уместно будет вспомнить про автореализуемые свойства, которые появились еще в далеком C# 3.0. Заменим нашу связку "поле-свойство" на автореализуемое свойство.
#|uk| Доречі, в даному випадку доречно буде згадати про автореалізуємі властивості, які з'явилися ще в далекому C# 3.0. Замінимо нашу зв'язку «поле-властивість» на автореалізуєму властивість.

Print:
```
  public double InterestRate
  {
    get;
    set;
  }
```

#C|ru| На всякий случай запустим компиляцию.
#S Все хорошо, можно продолжать.

#C|uk| На всякий випадок запустимо компіляцію.
#S Все добре, можна продовжувати.

Set step 3

Select "AccountType |||type|||" in "Account"

#|ru| В рассматриваемом примере в классе <code>Account</code> есть поле для доступа к объекту типа счета, поэтому мы можем обращаться к перемещённому полю через него.
#|uk| У розглянутому прикладі в класі <code>Account</code> є поле для доступу до об'єкта типу рахунку, тому ми можемо звертатися до переміщеного поля через нього.

Set step 4

Select "interestRate" in "InterestForAmountDays"

#|ru|^ Заменим все обращения к старому полю <code>interestRate</code> на соответствующие обращения к свойству в целевом классе.
#|uk|^ Замінимо всі звернення до старого поля <code>interestRate</code> на відповідні звернення до властивості в цільовому класі.

Replace "type.InterestRate"

Set step 5

Select in "Account":
```
  private double interestRate;

```

#|ru| После осуществления всех замен исходное поле можно удалить.
#|uk| Після здійснення всіх замін вихідне поле можна видалити.

Remove selected

#C|ru| Тут же стоит запустить компиляцию и тесты, чтобы обнаружить потенциальные ошибки.
#S Все отлично, код исправно работает.

#C|uk| Тут же варто запустити компіляцію і тести, щоб виявити потенційні помилки.
#S Все добре, код справно працює.

Select name of "Account"

#|ru| Стоит упомянуть, что если в классе есть много методов, которые используют перемещаемое поле, то имеет смысл его «самоинкапсулировать» в приватное свойство, чтобы впоследствии упростить рефакторинг. Давайте рассмотрим пример.
#|uk| Варто згадати, що якщо в класі є багато методів, які використовують поле, яке переміщується, то має сенс «самоінкапсулювати» його в приватну властивість, щоб згодом спростити рефакторинг. Давайте розглянемо приклад.

Go to "private AccountType type;|||"

#|ru| Вернем наше поле и произведем его "самоинкапсуляцию".
#|uk| Повернемо наше поле і зробимо його "​​самоінкапсуляцію".

Print:
```

  private double interestRate;

  private double InterestRate
  {
    get {
      return interestRate;
    }
    set {
      interestRate = value;
    }
  }
```

Select "type.InterestRate" in "InterestForAmountDays"

#|ru| Также вернем использование этого поля в методах, которые теперь обращаются к нему через созданное нами свойство.
#|uk| Також повернемо використання цього поля в методах, які тепер звертаються до нього через створену нами властивість.

Replace "InterestRate"

Go to the end of "Account"

#|ru| И, наконец, для полноты картины добавим еще методов.
#|uk| І, нарешті, для повноти картини додамо ще методів.

Print:
```

  public double InterestForBigFamily(double familySize)
  {
    return InterestRate / familySize;
  }

  // other 10 methods, which use InterestRate
```

Select name of "InterestForAmountDays"
+ Select name of "InterestForBigFamily"
+ Select "other 10 methods"

#|ru| Теперь, если возникнет необходимость переместить поле, нам не придётся менять содержимое методов.
#|uk| Тепер, якщо виникне необхідність перемістити поле, нам не доведеться міняти вміст методів.

Select "return interestRate;" in "Account"
+Select "interestRate = value;" in "Account"

#|ru| Достаточно будет поправить геттер и сеттер того свойства, в которое инкапсулировано наше поле.
#|uk| Досить буде поправити геттер і сетер тієї  властивості, в яке інкапсульоване наше поле.

Select "return interestRate;" in "Account"

Replace "return type.InterestRate;"

Select "interestRate = value;" in "Account"

Replace "type.InterestRate = value;"

Select in "Account":
```
  private double interestRate;

```

#|ru| После чего исходное поле можно (опять) удалить.
#|uk| Після чого вихідне поле можна (знову) видалити.

Remove selected

Select "private double |||InterestRate|||" in "Account"

#|ru|^ Применение самоинкапсуляции позволяет выполнять рефакторинг более мелкими шагами. Это удобно, когда класс подвергается значительной переделке.
#|uk|^ Застосування самоінкапсуляціі дозволяє виконувати рефакторинг дрібнішими кроками. Це зручно, коли клас зазнає значної переробки.

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.