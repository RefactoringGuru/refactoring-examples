move-field:java

###

1. Инкапсулируйте поле, если оно публичное.

2. Создайте такое же поле с методами доступа в классе-приёмнике.

3. Определите, как вы будете обращаться к классу-получателю. Вполне возможно, у вас уже есть поле или метод, которые возвращают подходящий объект, но если нет, нужно будет написать новый метод или поле, в котором бы хранился объект класса-получателя.

4. Замените все обращения к старому полю на соответствующие вызовы методов в классе-получателе.

5. Удалите поле в исходном классе.



###

```
class Account {
  // ...
  private AccountType type;
  private double interestRate;

  double interestForAmount_days(double amount, int days) {
    return interestRate * amount * days / 365;
  }
}

class AccountType {
  // ...
}
```

###

```
class Account {
  // ...
  private AccountType type;

  double interestForAmount_days(double amount, int days) {
    return getInterestRate() * amount * days / 365;
  }
  double interestForBigFamily(double familySize) {
    return getInterestRate() / familySize;
  }

  // other 10 methods, which use getInterestRate()

  private double getInterestRate() {
    return type.getInterestRate();
  }
  private void setInterestRate(double arg) {
    type.setInterestRate(arg);
  }
}

class AccountType {
  // ...
  private double interestRate;

  double getInterestRate() {
    return interestRate;
  }
  void setInterestRate(double arg) {
    interestRate = arg;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Перемещение поля</i> на примере класса банковского счета.

Select "double |||interestRate|||"

# Я хочу переместить поле процентной ставки <code>interestRate</code> в класс типа счета <code>AccountType</code>.

Select name of "interestForAmount_days"

# Есть несколько методов, обращающихся к этому полю, одним из которых является метод <code>interestForAmount_days()</code>.

Set step 2

Go to the end of "AccountType"

# Создадим такое же поле, а также методы доступа к нему в целевом классе.

Print:
```

  private double interestRate;

  double getInterestRate() {
    return interestRate;
  }
  void setInterestRate(double arg) {
    interestRate = arg;
  }
```

#C Запустим компиляцию, на всякий случай.

#S Все хорошо, можно продолжать.

Set step 3

Select "AccountType |||type|||" in "Account"

# В данном случае, в классе <code>Account</code> есть поле для доступа к объекту типа счета, поэтому мы можем обращаться к перемещённому полю через него.

Set step 4

# Заменим все обращения к старому полю на соответствующие вызовы методов в классе-получателе.

Select "interestRate" in "interestForAmount_days"

Replace "type.getInterestRate()"

Set step 5

Select in "Account":
```
  private double interestRate;

```

# После осуществления всех замен, исходное поле можно удалить.

Remove selected

#C Тут же стоит запустить компиляцию и тесты, чтобы обнаружить потенциальные ошибки.

#S Все отлично, код исправно работает.

# Стоит упомянуть, что если в классе есть много методов, которые используют перемещаемое поле, то перед рефакторингом его следует «само-инкапсулировать», дабы упростить рефакторинг. Давайте рассмотрим быстрый пример.

Select whole "Account"

Replace instantly:
```
class Account {
  // ...
  private AccountType type;
  private double interestRate;

  double interestForAmount_days(double amount, int days) {
    return getInterestRate() * amount * days / 365;
  }
  double interestForBigFamily(double familySize) {
    return getInterestRate() / familySize;
  }

  // other 10 methods, which use getInterestRate()

  private double getInterestRate() {
    return interestRate;
  }
  private void setInterestRate(double arg) {
    interestRate = arg;
  }
}

```

Select name of "Account"
+ Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"
+ Select "other 10 methods"

# В этом случае, вам не придётся делать замену во всех методах сразу...

Select "interestRate = arg" in "setInterestRate"
+Select "return interestRate" in "getInterestRate"

# ...а всего лишь в методах доступа (геттере и сеттере).

Select "return interestRate" in "getInterestRate"

Replace "return type.getInterestRate()"

Select "interestRate = arg" in "setInterestRate"

Replace "type.setInterestRate(arg)"

Select in "Account":
```
  private double interestRate;

```

# После чего исходное поле можно удалить.

Remove selected

Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"

# Позднее при желании можно выполнить переадресацию для клиентов методов доступа, чтобы они использовали новый объект.

Select name of "Account"

# Применение самоинкапсуляции позволяет выполнять рефакторинг более мелкими шагами. Это удобно, когда класс подвергается значительной переделке.

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.