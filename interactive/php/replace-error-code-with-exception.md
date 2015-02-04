replace-error-code-with-exception:php

###

1.ru. Найдите все вызовы метода, возвращающего код ошибки, и оберните его в <code>try</code>/<code>catch</code> блоки вместо проверки кода ошибки.
1.en. Find all calls to a method that returns error codes and, instead of checking for an error code, wrap it in <code>try</code>/<code>catch</code> blocks.
1.uk. Знайдіть усі виклики методу, що повертає код помилки, і оберніть його в <code>try</code>/<code>catch</code> блоки замість перевірки коду помилки.

2.ru. Внутри метода  вместо возвращения кода ошибки выбрасывайте исключение.
2.en. Inside the method, instead of returning an error code, throw an exception.
2.uk. Усередині методу замість повернення коду помилки викидайте виключення.

3.ru. Измените сигнатуру метода так, чтобы она содержала информацию о выбрасываемом исключении (секция <code>@throws</code>).
3.en. Change the method signature so that it contains information about the exception being thrown (<code>@throws</code> section).
3.uk. Змініть сигнатуру методу так, щоб вона містила інформацію про виключення, що викидається (секція <code>@throws</code>).



###

```
class Account {
  // ...
  private $balance;

  /**
   * Withdraw money from account.
   * @param int $amount Amount to withdraw.
   * @return Zero on success, -1 on error.
   */
  public function withdraw($amount) {
    if ($amount > $this->balance) {
      return -1;
    }
    else {
      $this->balance -= $amount;
      return 0;
    }
  }
}

// Somewhere in client code.
if ($account->withdraw($amount) == -1) {
  handleOverdrawn();
}
else {
  doTheUsualThing();
}
```

###

```
class Account {
  // ...
  private $balance;

  /**
   * Withdraw money from account.
   * @param int $amount Amount to withdraw.
   * @throws BalanceException
   */
  public function withdraw($amount) {
    if ($amount > $this->balance) {
      throw new BalanceException();
    }
    $this->balance -= $amount;
  }
}
class BalanceException extends Exception {}

// Somewhere in client code.
try {
  $account->withdraw($amount);
  doTheUsualThing();
} catch (BalanceException $e) {
  handleOverdrawn();
}
```

###

Set step 1

#|ru| Рассмотрим рефакторинг на примере метода снятия денег с банковского счета.
#|en| Let's look at this refactoring in the context of withdrawals from a bank account.
#|uk| Розглянемо рефакторинг на прикладі методу зняття грошей з банківського рахунку.

Go to "if ($amount > $this->balance) {|||"

#|ru|<+ В нашем случае, при попытке снять больше денег, чем есть на счету, генерируется код ошибки (<code>-1</code>),…
#|en|<+ If a customer attempts to withdraw more money than his or her current balance allows, an error code will be returned (<code>-1</code>)…
#|uk|<+ В нашому випадку, при спробі зняти більше грошей, ніж є на рахунку, генерується код помилки (<code>-1</code>),…

Select "$account->withdraw($amount) == -1"

#|ru|= …который затем проверяется в клиентском коде.
#|en|= …which is then checked in the client code.
#|uk|= …який потім перевіряється в клієнтському коді.

#|ru| Заменим все это выбрасыванием исключения с последующим «отловом» его в клиентском коде.
#|en| We can replace all this by throwing an exception and then "catching" it in the client code.
#|uk| Замінимо все це викиданням винятку з наступним «виловом» його в клієнтському коді.

Go to after "Account"

#|ru| Итак, первым делом можно создать новый класс исключения, который будет легче отлавливать.
#|en| First, we create a new exception class.
#|uk| Отже, першим ділом можна створити новий клас винятку, який буде легше відловлювати.

Print:
```

class BalanceException extends Exception {}
```

#|ru| Затем обернём код вызова нашего метода в <code>try</code>/<code>catch</code> блоки.
#|en| Then, wrap our method body with the <code>try</code>/<code>catch</code> block.
#|uk| Потім обгорнемо код виклику нашого методу в <code>try</code>/<code>catch</code> блоки.

Select:
```
if ($account->withdraw($amount) == -1) {
  handleOverdrawn();
}
else {
  doTheUsualThing();
}
```

Replace:
```
try {
  $account->withdraw($amount);
  doTheUsualThing();
} catch (BalanceException $e) {
  handleOverdrawn();
}
```

Set step 2

#|ru| После этого изменяем метод так, чтобы он выбрасывал исключение вместо возврата кода ошибки.
#|en| After that, change the method so that it throws an exception instead of returning an error code.
#|uk| Після цього змінюємо метод так, щоб він викидав виняток замість повернення коду помилки.

Select:
```
      return -1;
```

Replace:
```
      throw new BalanceException();
```

Wait 500ms

Select:
```
      return 0;

```

Remove selected

Select:
```
|||    else {
|||      $this->balance -= $amount;
|||    }
|||
```

#|ru| Такой код можно немного упростить, убрав <code>else</code>.
#|en| This code can be simplified a bit if we remove <code>else</code>.
#|uk| Такий код можна трохи спростити, прибравши <code>else</code>.

Remove selected

Select:
```
      $this->balance -= $amount;
```

Deindent

Select name of "Account"

#|ru| Неудобство этого шага в том, что мы вынуждены изменить все обращения к методу и сам метод за один шаг, иначе компилятор нас накажет. Если мест вызова много, то придётся выполнять большую модификацию без промежуточного тестирования.
#|en| This step is not very safe because we are forced to change all references to the method, as well as the method itself, in a single step. Otherwise, the compiler will shake its head at us in disapproval. If there are many calls, we will have to make a mammoth modification without any intermediate compilation or testing.
#|uk| Незручність цього кроку в тому, що ми змушені змінити всі звернення до методу і сам метод за один крок, інакше компілятор нас покарає. Якщо місць виклику багато, то доведеться виконувати велику модифікацію без проміжних компіляцій та тестуваннь.

#|ru| В таких случаях лучше создать новый метод, переместить в него код старого, включив в него исключения. Код старого метода заменить <code>try</code>/<code>catch</code> блоками, которые возвращают коды ошибки. После этого код останется рабочим, а вы сможете один за другим заменять обработчики кодов ошибок вызовами нового метода и блоками <code>try</code>/<code>catch</code>.
#|en| In such cases, it is better to create a new method and place the code of the old one inside it, including exceptions. Replace the code of the old method with <code>try</code>/<code>catch</code> blocks that return error codes. After this, the code will remain functional and we could replace error code handlers, one by one, with calls to the new method and <code>try</code>/<code>catch</code> blocks.
#|uk| В таких випадках краще створити новий метод, перемістити в нього код старого, включивши в нього винятки. Код старого методу замінити <code>try</code>/<code>catch</code> блоками, які повертають коди помилки. Після цього код залишиться робочим, а ви зможете один за іншим замінювати обробники кодів помилок викликами нового методу і блоками <code>try</code>/<code>catch</code>.

Set step 3

Select:
```
@return Zero on success, -1 on error.
```
#|ru| Как бы то ни было, нам осталось только обновить документацию метода, сообщив, что метод теперь выбрасывает исключение.
#|en| After all of the changes, we must update the method's signature, indicating that the method now throws exceptions.
#|uk| Як би там не було, нам залишилося тільки оновити документацію методу, повідомивши, що метод тепер викидає виключення.

Print:
```
@throws BalanceException
```

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.