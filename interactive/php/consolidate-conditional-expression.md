consolidate-conditional-expression:php

###

1.ru. Объедините множество условий в одном с помощью операторов <code>и</code> и <code>или</code>.
1.en. Consolidate the conditionals in a single expression by using <code>AND</code> and <code>OR</code>.
1.uk. Об'єднайте декілька умов в одному за допомогою операторів <code>і</code> та <code>або</code>.

2.ru. <a href="/extract-method">Извлеките метод</a> из условия оператора и назовите его так, чтобы он отражал суть проверяемого выражения.
2.en. Perform <a href="/extract-method">Extract Method</a> on the operator conditions and give the method a name that reflects the expression's purpose.
2.uk. <a href="/extract-method">Відокремте метод</a> від умови оператора і назвіть його так, щоб він відображав суть виразу, який перевірявся.



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

#|ru| Давайте рассмотрим <i>Объединение условных операторов</i> на примере метода вычисления размера выходного пособия при получении сотрудником травмы.
#|en| Let's look at <i>Consolidate Conditional Expression</i>, using as our example the method for calculating workman's compensation for an injured worker.
#|uk| Давайте розглянемо <i>Об'єднання умовних операторів<i> на прикладі методу обчислення розміру вихідної допомоги при отриманні співробітником травми.

Select "if" in "disabilityAmount"
+ Select "return 0" in "disabilityAmount"

#|ru| Как видите, у нас есть ряд условий, возвращающих одинаковый результат.
#|en| As you see, there are a number of conditions that return an identical result.
#|uk| Як бачите, у нас є ряд умов, які повертають однаковий результат.

#|ru| Эти проверки можно объединить в одно выражение с помощью операции «или».
#|en| These checks can be merged into a single expression using the <code>or</code> operator.
#|uk| Ці перевірки можна об'єднати в один вираз за допомогою операції «або».

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

#|ru| Это условие выглядит слишком длинным и непонятным на первый взгляд. Поэтому можно <a href="/extract-method">выделить его в новый метод</a> и сообщить о том, что именно ищет условный оператор (нетрудоспособность не оплачивается).
#|en| This condition looks too long and hard to comprehend at first glance. So we can <a href="/extract-method">Extract Method</a> and say what the conditional is looking for (no compensation to be paid).
#|uk| Ця умова виглядає занадто довгою і незрозумілою на перший погляд. Тому можна <a href="/extract-method">виділити її в новий метод</a> і повідомити про те, що саме шукає умовний оператор (непрацездатність не оплачується).

Go to after "disabilityAmount"

Print:
```

  private function isNotEligibleForDisability() {
    return ($this->seniority < 2) || ($this->monthsDisabled > 12) || ($this->isPartTime);
  }
```

Select "($this->seniority < 2) || ($this->monthsDisabled > 12) || ($this->isPartTime)" in "disabilityAmount"

Replace "$this->isNotEligibleForDisability()"

#C|ru| Запускаем тестирование.
#S Отлично, все работает!

#C|en| Let's start testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо тестування.
#S Супер, все працює.

Select "if" in "vacationAmount"

#|ru| Предыдущий пример демонстрировал операцию <code>или</code>, но то же самое можно делать с помощью <code>и</code>.
#|en| The previous example demonstrated the <code>or</code> operation but the same thing can be done using <code>and</code>.
#|uk| Попередній приклад демонстрував операцію <code>або</code>, але те ж саме можна робити за допомогою <code>і</code>.

#|ru| Эти условия можно заменить следующим образом:
#|en| These conditions can be replaced as follows:
#|uk| Ці умови можна замінити таким чином:

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

#|ru| Если рассматриваемая процедура лишь проверяет условие и возвращает значение, мы можем ещё более упростить код с помощью тернарного оператора.
#|en| If the procedure you are reviewing only checks a condition and returns a value, we can simplify the code to a greater degree by using a ternary operator.
#|uk| Якщо розглянута процедура лише перевіряє умову і повертає значення, ми можемо ще більш спростити код за допомогою тернарного оператора.

Select body of "vacationAmount"

Replace:
```
    return ($this->onVacation() && $this->lengthOfService() > 10) ? 1 : 0.5;
```

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.