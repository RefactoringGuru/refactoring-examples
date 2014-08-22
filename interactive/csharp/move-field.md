move-field:csharp

###

1. Инкапсулируйте поле, если оно публичное.

2. Создайте копию поля и свойство для доступа к нему в целевом классе.

3. Определите, как вы будете обращаться к целевому классу. Вполне возможно, что у вас уже есть поле или метод, которые возвращают подходящий экземпляр класса, но если таковых нет, то нужно будет создать (достаточно одного из них).

4. Замените все обращения к старому полю на соответствующие обращения к полю в целевом классе.

5. Удалите поле в исходном классе.



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

# Давайте рассмотрим <i>Перемещение поля</i> на примере класса банковского счета.

Select "double |||interestRate|||"

# Я хочу переместить поле процентной ставки <code>interestRate</code> в класс типа счета <code>AccountType</code>.

Select name of "InterestForAmountDays"

# Есть несколько методов, обращающихся к этому полю, одним из которых является метод <code>InterestForAmountDays()</code>.

Set step 2

Go to the end of "AccountType"

# Создадим копию поля в целевом классе...

Print:
```

  private double interestRate;
```

# ...а также создадим публичное свойство для доступа к этому полю извне.

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

# Кстати, в данном случае уместно будет вспомнить про автореализуемые свойства, которые появились еще в далеком C# 3.0. Заменим нашу связку "поле-свойство" на автореализуемое свойство.

Print:
```
  public double InterestRate
  {
    get;
    set;
  }
```

#C На всякий случай запустим компиляцию.

#S Все хорошо, можно продолжать.

Set step 3

Select "AccountType |||type|||" in "Account"

# В рассматриваемом примере, в классе <code>Account</code> есть поле для доступа к объекту типа счета, поэтому мы можем обращаться к перемещённому полю через него.

Set step 4

Select "interestRate" in "InterestForAmountDays"

#^ Заменим все обращения к старому полю <code>interestRate</code> на соответствующие обращения к свойству в целевом классе.

Replace "type.InterestRate"

Set step 5

Select in "Account":
```
  private double interestRate;

```

# После осуществления всех замен, исходное поле можно удалить.

Remove selected

#C Тут же стоит запустить компиляцию и тесты, чтобы обнаружить потенциальные ошибки.

#S Все отлично, код исправно работает.

Select name of "Account"

# Стоит упомянуть, что если в классе есть много методов, которые используют перемещаемое поле, то имеет смысл это поле «само-инкапсулировать» в приватное свойство, дабы впоследствии упростить рефакторинг. Давайте рассмотрим пример.

Go to "private AccountType type;|||"

# Вернем наше поле и произведем его "само-инкапсуляцию".

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

# Также вернем использование этого поля в методах, которые теперь обращаются к нему через созданное нами свойство.

Replace "InterestRate"

Go to the end of "Account"

# И, наконец, для полноты картины добавим еще методов.

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

# Теперь, если возникнет необходимость переместить поле, нам не придётся менять содержимое методов...

Select "return interestRate;" in "Account"
+Select "interestRate = value;" in "Account"

# ...достаточно будет поправить геттер и сеттер того свойства, в которое инкапсулировано наше поле.

Select "return interestRate;" in "Account"

Replace "return type.InterestRate;"

Select "interestRate = value;" in "Account"

Replace "type.InterestRate = value;"

Select in "Account":
```
  private double interestRate;

```

# После чего исходное поле можно (опять) удалить.

Remove selected

Select "private double |||InterestRate|||" in "Account"

#^ Применение самоинкапсуляции позволяет выполнять рефакторинг более мелкими шагами. Это удобно, когда класс подвергается значительной переделке.

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.