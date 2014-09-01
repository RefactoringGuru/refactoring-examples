consolidate-conditional-expression:php

###

1. Объедините множество условий в одном с помощью операторов <code>и</code> и <code>или</code>.

2. <a href="/extract-method">Извлеките метод</a> из условия оператора и назовите его так, чтобы он отражал суть проверяемого выражения.



###

```
class Payout {
  // ...

  public $seniority;
  public $monthsDisabled;
  public $isPartTime;

  public function disabilityAmount() {
    if ($this->seniority < 2) {
      return 0;
    }
    if ($this->monthsDisabled > 12) {
      return 0;
    }
    if ($this->isPartTime) {
      return 0;
    }
    // compute the disability amount
    // ...
  }

  public function vacationAmount() {
    if ($this->onVacation()) {
      if ($this->lengthOfService() > 10) {
        return 1;
      }
    }
    return 0.5;
  }
}
```

###

```
class Payout {
  // ...

  public $seniority;
  public $monthsDisabled;
  public $isPartTime;

  public function disabilityAmount() {
    if ($this->isNotEligibleForDisability()) {
      return 0;
    }
    // compute the disability amount
    // ...
  }
  private function isNotEligibleForDisability() {
    return ($this->seniority < 2) || ($this->monthsDisabled > 12) || ($this->isPartTime);
  }

  public function vacationAmount() {
    return ($this->onVacation() && $this->lengthOfService() > 10) ? 1 : 0.5;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Объединение условных операторов</i> на примере метода вычисления размера выходного пособия при получении сотрудником травмы.

Select "if" in "disabilityAmount"
+ Select "return 0" in "disabilityAmount"

# Как видите, у нас есть ряд условий, возвращающих одинаковый результат.

# Эти проверки можно объединить в одно выражение с помощью операции «или».

Go to:
```
}|||
    // compute the disability amount
```

Print:
```

    if (($this->seniority < 2) || ($this->monthsDisabled > 12) || ($this->isPartTime)) {
      return 0;
    }
```

Select:
```
    if ($this->seniority < 2) {
      return 0;
    }
    if ($this->monthsDisabled > 12) {
      return 0;
    }
    if ($this->isPartTime) {
      return 0;
    }

```

Remove selected

Set step 2

Select "($this->seniority < 2) || ($this->monthsDisabled > 12) || ($this->isPartTime)"

# Это условие выглядит слишком длинным и непонятным на первый взгляд. Поэтому можно <a href="/extract-method">выделить его в новый метод</a> и сообщить о том, что именно ищет условный оператор (нетрудоспособность не оплачивается).

Go to after "disabilityAmount"

Print:
```

  private function isNotEligibleForDisability() {
    return ($this->seniority < 2) || ($this->monthsDisabled > 12) || ($this->isPartTime);
  }
```

Select "($this->seniority < 2) || ($this->monthsDisabled > 12) || ($this->isPartTime)" in "disabilityAmount"

Replace "$this->isNotEligibleForDisability()"

#C Запускаем тестирование.

#S Отлично, все работает!

Select "if" in "vacationAmount"

# Предыдущий пример демонстрировал операцию <code>или</code>, но то же самое можно делать с помощью <code>и</code>.

# Эти условия можно заменить следующим образом:

Go to the end of "vacationAmount"

Print:
```


    if ($this->onVacation() && $this->lengthOfService() > 10) {
      return 1;
    }
    else {
      return 0.5;
    }
```

Select:
```
    if ($this->onVacation()) {
      if ($this->lengthOfService() > 10) {
        return 1;
      }
    }
    return 0.5;


```

Remove selected

# Если рассматриваемая процедура лишь проверяет условие и возвращает значение, мы можем ещё более упростить код с помощью тернарного оператора.

Select body of "vacationAmount"

Replace:
```
    return ($this->onVacation() && $this->lengthOfService() > 10) ? 1 : 0.5;
```

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.