consolidate-conditional-expression:java

###

1.ru. Объедините множество условий в одном с помощью операторов <code>И</code> и <code>ИЛИ</code>.
1.en. Consolidate the conditionals in a single expression by using <code>AND</code> and <code>OR</code>.
1.uk. Об'єднайте декілька умов в одному за допомогою операторів <code>І</code> та <code>АБО</code>.

2.ru. <a href="/extract-method">Извлеките метод</a> из условия оператора и назовите его так, чтобы он отражал суть проверяемого выражения.
2.en. Perform <a href="/extract-method">Extract Method</a> on the operator conditions and give the method a name that reflects the expression's purpose.
2.uk. <a href="/extract-method">Відокремте метод</a> від умови оператора і назвіть його так, щоб він відображав суть виразу, який перевірявся.



###

```
class Payout {
  // ...

  public int seniority;
  public int monthsDisabled;
  public boolean isPartTime;

  public double disabilityAmount() {
    if (seniority < 2) {
      return 0;
    }
    if (monthsDisabled > 12) {
      return 0;
    }
    if (isPartTime) {
      return 0;
    }
    // compute the disability amount
    // ...
  }

  public double vacationAmount() {
    if (onVacation()) {
      if (lengthOfService() > 10) {
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

  public int seniority;
  public int monthsDisabled;
  public boolean isPartTime;

  public double disabilityAmount() {
    if (isNotEligibleForDisability()) {
      return 0;
    }
    // compute the disability amount
    // ...
  }
  private boolean isNotEligibleForDisability() {
    return seniority < 2 || monthsDisabled > 12 || isPartTime;
  }

  public double vacationAmount() {
    return (onVacation() && lengthOfService() > 10) ? 1 : 0.5;
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Объединение условных операторов</i> на примере метода вычисления размера выходного пособия при получении сотрудником травмы.
#|en| Let's look at <i>Consolidate Conditional Expression</i>, using the method for calculating workman's injury compensation.
#|uk| Давайте розглянемо <i>Об'єднання умовних операторів<i> на прикладі методу обчислення розміру вихідної допомоги при отриманні співробітником травми.

Select "if" in "disabilityAmount"
+ Select "return 0" in "disabilityAmount"

#|ru| Как видите, у нас есть ряд условий, возвращающих одинаковый результат.
#|en| As you see, there are a number of conditions that return an identical result.
#|uk| Як бачите, у нас є ряд умов, які повертають однаковий результат.

#|ru| Эти проверки можно объединить в одно выражение с помощью операции <code>ИЛИ</code>.
#|en| We can merge these checks into a single expression using the <code>OR</code> operator.
#|uk| Ці перевірки можна об'єднати в один вираз за допомогою операції <code>АБО</code>.

Go to:
```
}|||
    // compute the disability amount
```

Print:
```

    if (seniority < 2 || monthsDisabled > 12 || isPartTime) {
      return 0;
    }
```

Select:
```
    if (seniority < 2) {
      return 0;
    }
    if (monthsDisabled > 12) {
      return 0;
    }
    if (isPartTime) {
      return 0;
    }

```

Remove selected

Set step 2

Select "seniority < 2 || monthsDisabled > 12 || isPartTime"

#|ru| Это условие выглядит слишком длинным и непонятным на первый взгляд. Поэтому можно <a href="/extract-method">выделить его в новый метод</a>, сообщив в названии, что именно ищет условный оператор (нетрудоспособность не оплачивается).
#|en| This condition looks too long and hard to comprehend. So we can <a href="/extract-method">Extract Method</a> and make more clear what the conditional is looking for (no compensation to be paid).
#|uk| Ця умова виглядає занадто довгою і незрозумілою на перший погляд. Тому можна <a href="/extract-method">виділити її в новий метод</a> і повідомити про те, що саме шукає умовний оператор (непрацездатність не оплачується).

Go to after "disabilityAmount"

Print:
```

  private boolean isNotEligibleForDisability() {
    return seniority < 2 || monthsDisabled > 12 || isPartTime;
  }
```

Select "seniority < 2 || monthsDisabled > 12 || isPartTime" in "disabilityAmount"

Replace "isNotEligibleForDisability()"

#C|ru| Запускаем компиляцию и тестирование.
#S Отлично, все работает!

#C|en| Let's run the compiler and auto-tests.
#S Wonderful, it's all working!

#C|uk| Запускаємо компіляцію і тестування.
#S Супер, все працює.

Select "if" in "vacationAmount"

#|ru| Предыдущий пример демонстрировал операцию <code>ИЛИ</code>, но то же самое можно делать с помощью <code>И</code>.
#|en| The previous example demonstrated the <code>OR</code> operation but the same thing can be done using <code>AND</code>.
#|uk| Попередній приклад демонстрував операцію <code>АБО</code>, але те ж саме можна робити за допомогою <code>І</code>.

#|ru| Эти условия можно заменить следующим образом:
#|en| These conditions can be replaced as follows:
#|uk| Ці умови можна замінити таким чином:

Go to the end of "vacationAmount"

Print:
```


    if (onVacation() && lengthOfService() > 10) {
      return 1;
    }
    else {
      return 0.5;
    }
```

Select:
```
    if (onVacation()) {
      if (lengthOfService() > 10) {
        return 1;
      }
    }
    return 0.5;


```

Remove selected

Select body of "vacationAmount"

#|ru| Если рассматриваемая процедура лишь проверяет условие и возвращает значение, мы можем ещё больше упростить код с помощью тернарного оператора.
#|en| If the code only checks a condition and returns a value, we can simplify it to a greater degree by using a ternary operator.
#|uk| Якщо розглянута процедура лише перевіряє умову і повертає значення, ми можемо ще більш спростити код за допомогою тернарного оператора.

Replace:
```
    return (onVacation() && lengthOfService() > 10) ? 1 : 0.5;
```

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