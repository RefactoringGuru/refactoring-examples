move-field:csharp

###

1.ru. Инкапсулируйте поле, если оно публичное.
1.en. If the field is public, encapsulate it.
1.uk. Інкапсулюйте поле, якщо воно є публічним.

2.ru. Создайте копию поля и свойство для доступа к нему в целевом классе.
2.en. Create a copy of the field and property for accessing it in the target class.
2.uk. Створіть копію поля і властивість для доступу до нього в цільовому класі.

3.ru. Определите, как вы будете обращаться к целевому классу. Вполне возможно, что у вас уже есть поле или метод, которые возвращают подходящий экземпляр класса, но если таковых нет, то нужно будет создать (достаточно одного из них).
3.en. Decide how you will refer to the target class. It is quite possible that you already have a field or method that returns an appropriate class instance, but if not, you will need to create one of these.
3.uk. Визначте, як ви будете звертатися до цільового класу. Цілком можливо, що у вас вже є поле або метод, які повертають відповідний екземпляр класу, але якщо таких немає, то потрібно буде створити (досить одного з них).

4.ru. Замените все обращения к старому полю на соответствующие обращения к полю в целевом классе.
4.en. Replace all references to the old field with the relevant references to the field in the target class.
4.uk. Замініть всі звернення до старого поля на відповідні звернення до поля в цільовому класі.

5.ru. Удалите поле в исходном классе.
5.en. Delete the field in the original class.
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
#|en| Let's look at <i>Move Field</i> using a bank account class as our example.
#|uk| Давайте розглянемо <i>Переміщення поля</i> на прикладі класу банківського рахунку.

Select "double |||interestRate|||"

#|ru| Мы хотим переместить поле процентной ставки <code>interestRate</code> в класс типа счета <code>AccountType</code>.
#|en| We want to move the <code>interestRate</code> field to the <code>AccountType</code> class.
#|uk| Ми хочемо перемістити поле процентної ставки <code>interestRate</code> в клас типу рахунку <code>AccountType</code>.

Select name of "InterestForAmountDays"

#|ru| Есть несколько методов, обращающихся к этому полю, одним из которых является метод <code>InterestForAmountDays()</code>.
#|en| Several methods refer to this field. One of them is the <code>InterestForAmountDays()</code> method.
#|uk| Є кілька методів, що звертаються до цього поля, одним з яких є метод <code>InterestForAmountDays()</code>.

Set step 2

Go to the end of "AccountType"

#|ru| Создадим копию поля в целевом классе…
#|en| Create a copy of the field in the target class…
#|uk| Створимо копію поля в цільовому класі…

Print:
```

  private double interestRate;
```

#|ru| …а также создадим публичное свойство для доступа к этому полю извне.
#|en| …and also create a public property for accessing this field externally.
#|uk| …а також створимо публічну властивість для доступу до цього поля ззовні.

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
#|en| Incidentally, this is a good time to mention auto-implemented properties, which have been with us ever since C# 3.0. Replace our field/property relationship with an auto-implemented property.
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

#C|en| To stay on the safe side, compile and test after each change.
#S All is well, so let's continue.

#C|uk| На всякий випадок запустимо компіляцію.
#S Все добре, можна продовжувати.

Set step 3

Select "AccountType |||type|||" in "Account"

#|ru| В рассматриваемом примере в классе <code>Account</code> есть поле для доступа к объекту типа счета, поэтому мы можем обращаться к перемещённому полю через него.
#|en| In our example, the <code>Account</code> class contains a field for accessing the account type object. For this reason, we can access the moved field through it.
#|uk| У розглянутому прикладі в класі <code>Account</code> є поле для доступу до об'єкта типу рахунку, тому ми можемо звертатися до переміщеного поля через нього.

Set step 4

Select "interestRate" in "InterestForAmountDays"

#|ru|^ Заменим все обращения к старому полю <code>interestRate</code> на соответствующие обращения к свойству в целевом классе.
#|en|^ Replace all references to the old <code>interestRate</code> field with the appropriate references to the property in the target class.
#|uk|^ Замінимо всі звернення до старого поля <code>interestRate</code> на відповідні звернення до властивості в цільовому класі.

Replace "type.InterestRate"

Set step 5

Select in "Account":
```
  private double interestRate;

```

#|ru| После осуществления всех замен исходное поле можно удалить.
#|en| Once changes are complete, we can remove the original field.
#|uk| Після здійснення всіх замін вихідне поле можна видалити.

Remove selected

#C|ru| Тут же стоит запустить компиляцию и тесты, чтобы обнаружить потенциальные ошибки.
#S Все отлично, код исправно работает.

#C|en| Let's compile and test for potential errors.
#S Outstanding. The code is doing what we intended.

#C|uk| Тут же варто запустити компіляцію і тести, щоб виявити потенційні помилки.
#S Все добре, код справно працює.

Select name of "Account"

#|ru| Стоит упомянуть, что если в классе есть много методов, которые используют перемещаемое поле, то имеет смысл его «самоинкапсулировать» в приватное свойство, чтобы впоследствии упростить рефакторинг. Давайте рассмотрим пример.
#|en| Remember that if a class has many methods that use the moved field, you may want to self-encapsulate it into a private property to simplify future refactoring tasks. Here is one example.
#|uk| Варто згадати, що якщо в класі є багато методів, які використовують поле, яке переміщується, то має сенс «самоінкапсулювати» його в приватну властивість, щоб згодом спростити рефакторинг. Давайте розглянемо приклад.

Go to "private AccountType type;|||"

#|ru| Вернем наше поле и произведем его "самоинкапсуляцию".
#|en| Bring our field back and self-encapsulate it.
#|uk| Повернемо наше поле і зробимо його "самоінкапсуляцію".

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
#|en| Bring back use of the field as well in the methods that now access it via the property we created.
#|uk| Також повернемо використання цього поля в методах, які тепер звертаються до нього через створену нами властивість.

Replace "InterestRate"

Go to the end of "Account"

#|ru| И, наконец, для полноты картины добавим еще методов.
#|en| And finally, for the full effect, add more methods.
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
#|en| If we now needed to move the field, we would not need to change the content of the methods.
#|uk| Тепер, якщо виникне необхідність перемістити поле, нам не доведеться міняти вміст методів.

Select "return interestRate;" in "Account"
+Select "interestRate = value;" in "Account"

#|ru| Достаточно будет поправить геттер и сеттер того свойства, в которое инкапсулировано наше поле.
#|en| Simply adjust the getter and setter of the property into which our field has been encapsulated.
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
#|en| Then you can (again) remove the original field.
#|uk| Після чого вихідне поле можна (знову) видалити.

Remove selected

Select "private double |||InterestRate|||" in "Account"

#|ru|^ Применение самоинкапсуляции позволяет выполнять рефакторинг более мелкими шагами. Это удобно, когда класс подвергается значительной переделке.
#|en|^ Self-encapsulating allows refactoring via baby steps. And when a class is undergoing major changes, that is a good thing.
#|uk|^ Застосування самоінкапсуляціі дозволяє виконувати рефакторинг дрібнішими кроками. Це зручно, коли клас зазнає значної переробки.

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