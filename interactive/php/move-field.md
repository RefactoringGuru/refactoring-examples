move-field:php

###

1. Инкапсулируйте поле, если оно публичное.

2. Создайте копию поля и методы для доступа к нему в целевом классе.

3. Определите, как вы будете обращаться к целевому классу. Вполне возможно, что у вас уже есть поле или метод, которые возвращают подходящий экземпляр класса, но если таковых нет, то нужно будет создать (достаточно одного из них).

4. Замените все обращения к старому полю на соответствующие вызовы методов в целевом классе.

5. Удалите поле в исходном классе.



###

```
class Account {
  // ...
  private $type; // AccountType
  private $interestRate;

  public function interestForAmount_days($amount, $days) {
    return $this->interestRate * $amount * $days / 365.0;
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
  private $type; // AccountType

  public function interestForAmount_days($amount, $days) {
    return $this->getInterestRate() * $amount * $days / 365.0;
  }
  public function interestForBigFamily($familySize) {
    return $this->getInterestRate() / $familySize;
  }

  // other 10 methods, which use getInterestRate()

  private function getInterestRate() {
    return $this->type->getInterestRate();
  }
  private function setInterestRate($arg) {
    $this->type->setInterestRate($arg);
  }
}

class AccountType {
  // ...
  private $interestRate;

  public function getInterestRate() {
    return $this->interestRate;
  }
  public function setInterestRate($arg) {
    $this->interestRate = $arg;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Перемещение поля</i> на примере класса банковского счета.

Select "private |||$interestRate|||"

# Мы хотим переместить поле процентной ставки <code>interestRate</code> в класс типа счета <code>AccountType</code>.

Select name of "interestForAmount_days"

# Есть несколько методов, обращающихся к этому полю, одним из которых является метод <code>interestForAmount_days()</code>.

Set step 2

Go to the end of "AccountType"

# Создадим такое же поле, а также методы доступа к нему в целевом классе.

Print:
```

  private $interestRate;

  public function getInterestRate() {
    return $this->interestRate;
  }
  public function setInterestRate($arg) {
    $this->interestRate = $arg;
  }
```

#C На всякий случай запустим авто-тесты.

#S Все хорошо, можно продолжать.

Set step 3

Select "private |||$type|||"

# В рассматриваемом примере в классе <code>Account</code> есть поле для доступа к объекту типа счета, поэтому мы можем обращаться к перемещённому полю через него.

Set step 4

# Заменим все обращения к старому полю на соответствующие вызовы методов в целевом классе.

Select "interestRate" in "interestForAmount_days"

Replace "type->interestRate"

Set step 5

Select in "Account":
```
  private $interestRate;

```

# После осуществления всех замен исходное поле можно удалить.

Remove selected

#C Тут же стоит запустить авто-тесты, чтобы обнаружить потенциальные ошибки.

#S Все отлично, код исправно работает.

# Стоит упомянуть, что если в классе есть много методов, которые используют перемещаемое поле, то имеет смысл его «самоинкапсулировать», чтобы впоследствии упростить рефакторинг. Давайте рассмотрим быстрый пример.

Select whole "Account"

Replace instantly:
```
class Account {
  // ...
  private $type; // AccountType
  private $interestRate;

  public function interestForAmount_days($amount, $days) {
    return $this->getInterestRate() * $amount * $days / 365.0;
  }
  public function interestForBigFamily($familySize) {
    return $this->getInterestRate() / $familySize;
  }

  // other 10 methods, which use getInterestRate()

  private function getInterestRate() {
    return $this->interestRate;
  }
  private function setInterestRate($arg) {
    $this->interestRate = $arg;
  }
}

```

Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"
+ Select "other 10 methods"

# В этом случае вам не придётся делать замену во всех методах сразу...

Select "$this->interestRate = $arg" in "setInterestRate"
+Select "return $this->interestRate" in "getInterestRate"

# ...а всего лишь в методах доступа (геттере и сеттере).

Select "return $this->interestRate" in "getInterestRate"

Replace "return $this->type->getInterestRate()"

Select "$this->interestRate = $arg" in "setInterestRate"

Replace "$this->type->setInterestRate($arg)"

Select in "Account":
```
  private $interestRate;

```

# После чего исходное поле можно удалить.

Remove selected

Select name of "interestForAmount_days"
+ Select name of "interestForBigFamily"

# Позднее при желании можно выполнить переадресацию для клиентов методов доступа, чтобы они использовали новый объект.

Select name of "Account"

# Применение самоинкапсуляции позволяет выполнять рефакторинг более мелкими шагами. Это удобно, когда класс подвергается значительной переделке.

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.