consolidate-conditional-expression:java

###

1. Объедините множество условий в одном с помощью операторов <code>и</code> и <code>или</code>.

2. <a href="/extract-method">Извлеките метод</a> из условия оператора и назовите его так, чтобы он отражал суть проверяемого выражения.



###

```
class Payout {
  // ...
  
  public int seniority;
  public int monthsDisabled;
  public bool isPartTime;

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
  public bool isPartTime;

  public double disabilityAmount() {
    if (isNotEligibleForDisability()) {
      return 0;
    }
    // compute the disability amount
    // ...
  }
  private bool isNotEligibleForDisability() {
    return (seniority < 2) || (monthsDisabled > 12) || (isPartTime);
  }
   
  public double vacationAmount() {
    return (onVacation() && lengthOfService() > 10) ? 1 : 0.5;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Объединение условных операторов</i> на примере метода вычисления размера выходного пособия при получения сотрудником травмы.

Select "if" in "disabilityAmount"
+ Select "return 0" in "disabilityAmount"

# Как видите, у нас есть ряд условий, возвращающих одинаковый результат.

# Эти проверки можно объеденить в одно выражение с помощью операции «или».

Go to:
```
}|||
    // compute the disability amount
```

Print:
```

    if ((seniority < 2) || (monthsDisabled > 12) || (isPartTime)) {
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

Select "(seniority < 2) || (monthsDisabled > 12) || (isPartTime)"

# Это условие выглядит слишком длинным и непонятным на первый взгляд. Поэтому можно <a href="/extract-method">выделить его в новый метод</a>, сообщить о том, что именно ищет условный оператор (нетрудоспособность не оплачивается).

Go to after "disabilityAmount"

Print:
```

  private bool isNotEligibleForDisability() {
    return (seniority < 2) || (monthsDisabled > 12) || (isPartTime);
  }
```

Select "(seniority < 2) || (monthsDisabled > 12) || (isPartTime)" in "disabilityAmount"

Wait 500ms

Print "isNotEligibleForDisability()"

#C Запускаем компиляцию и тестирование.

#S Отлично, все работает!

Select "if" in "vacationAmount"

# Предыдущий пример демонстрировал операцию <code>или</code>, но то же самое можно делать с помощью <code>и</code>.

# Эти условия можно заменить следующим образом:

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

# Если рассматриваемая процедура лишь проверяет условие и возвращает значение, мы можем ещё более упростить код с помощью тернарного оператора.

Go to the end of "vacationAmount"

Select:
```
    if (onVacation() && lengthOfService() > 10) {
      return 1;
    }
    else {
      return 0.5;
    }
```

Print:
```
    return (onVacation() && lengthOfService() > 10) ? 1 : 0.5;
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.