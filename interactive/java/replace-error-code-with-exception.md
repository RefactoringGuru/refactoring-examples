replace-error-code-with-exception:java

###

1. Найдите все вызовы метода, возвращающего код ошибки, и оберните его в <code>try</code>/<code>catch</code> блоки вместо проверки кода ошибки.

2. Внутри метода  вместо возвращения кода ошибки выбрасывайте исключение.

3. Измените сигнатуру метода так, чтобы она содержала информацию о выбрасываемом исключении (секция <code>@throws</code>).



###

```
class Account {
  // ...
  private int balance;

  public int withdraw(int amount) {
    if (amount > balance) {
      return -1;
    }
    else {
      balance -= amount;
      return 0;
    }
  }
}

// Somewhere in client code.
if (account.withdraw(amount) == -1) {
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
  private int balance;

  public int withdraw(int amount) throws BalanceException {
    if (amount > balance) {
      throw new BalanceException();
    }
    balance -= amount;
  }
}
class BalanceException extends Exception {}

// Somewhere in client code.
try {
  account.withdraw(amount);
  doTheUsualThing();
} catch (BalanceException e) {
  handleOverdrawn();
}
```

###

Set step 1

# Рассмотрим рефакторинг на примере метода снятия денег с банковского счета.

Go to "if (amount > balance) {|||"

#<+ В нашем случае, при попытке снять больше денег, чем есть на счету, генерируется код ошибки (<code>-1</code>)

Select "account.withdraw(amount) == -1"

#= ...который затем проверяется в клиентском коде.

# Заменим все это выбрасыванием исключения, с последующим «отловом» его в клиентском коде.

Go to after "Account"

# Итак, первым делом можно создать новый класс исключения, который будет легче отлавливать.

Print:
```

class BalanceException extends Exception {}
```

# Затем, обернем код вызова нашего метода в <code>try</code>/<code>catch</code> блоки.

Select:
```
if (account.withdraw(amount) == -1) {
  handleOverdrawn();
}
else {
  doTheUsualThing();
}
```

Wait 500ms

Print:
```
try {
  account.withdraw(amount);
  doTheUsualThing();
} catch (BalanceException e) {
  handleOverdrawn();
}
```

Set step 2

# После этого изменяем метод так, чтобы он выбрасывал исключение, вместо возврата кода ошибки.

Select:
```
      return -1;
```

Wait 500ms

Print:
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
|||      balance -= amount;
|||    }
|||
```

# Этот код можно немного упростить, убрав <code>else</code>.

Remove selected

Select:
```
      balance -= amount;
```

Deindent

Select name of "Account"

# Неудобство этого шага в том, что мы вынуждены изменить все обращения к методу и сам метод за один шаг, иначе компилятор нас накажет. Если мест вызова много, то придется выполнять большую модификацию без промежуточных компиляции и тестирования.

# В таких случаях лучше создать новый метод, переместить в него код старого, включив в него исключения. Код старого метода заменить <code>try</code>/<code>catch</code> блоками, которые возвращают коды ошибки. После этого код останется рабочим, а вы можете один за одним заменять обработчики кодов ошибок вызовами нового метода и блоками <code>try</code>/<code>catch</code>.

Set step 3

Go to "withdraw(int amount)|||"

# Как бы то ни было, нам осталось обновить сигнатуру метода, сообщив, что метод теперь выбрасывает исключение.

Print " throws BalanceException"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.