replace-nested-conditional-with-guard-clauses:php

###

1. Выделите граничные условия, которые приводят к вызову исключения или немедленному возвращению значения из метода. Переместите эти условия в начало метода.

2. После того, как с переносами покончено, и все тесты стали проходить, проверьте, можно ли использовать <a href="/consolidate-conditional-expression">объединение условных операторов</a> для граничных условных операторов, ведущих к одинаковым исключениям или возвращаемым значениям.



###

```
class Payout {
  // ...
  function getPayAmount() {
    $result = 0;
    if ($this->isDead) {
      $result = $this->deadAmount();
    }
    else {
      if ($this->isSeparated) {
        $result = $this->separatedAmount();
      }
      else {
        if ($this->isRetired) {
          $result = $this->retiredAmount();
        }
        else {
          $result = $this->normalPayAmount();
        }
      }
    }
    return $result;
  }
}
```

###

```
class Payout {
  // ...
  function getPayAmount() {
    if ($this->isDead) {
      return $this->deadAmount();
    }
    if ($this->isSeparated) {
      return $this->separatedAmount();
    }
    if ($this->isRetired) {
      return $this->retiredAmount();
    }
    return $this->normalPayAmount();
  }
}
```

###

Set step 1

# Представьте себе работу системы начисления зарплаты с особыми правилами для служащих, которые умерли, проживают раздельно или вышли на пенсию. Такие случаи необычны, но могут встретиться.

Select "$this->isDead"
+ Select "$this->isSeparated"
+ Select "$this->isRetired"

#+ В этом коде проверки особых условий...

Select "$result = $this->normalPayAmount()"

#<= ...скрывают за собой выполнение обычных действий. Поэтому при использовании граничных операторов код станет яснее.

Select "|||$result =||| $this->deadAmount();"

Replace "return"

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return $result;
```

Remove selected

Select:
```
      if ($this->isSeparated) {
        $result = $this->separatedAmount();
      }
      else {
        if ($this->isRetired) {
          $result = $this->retiredAmount();
        }
        else {
          $result = $this->normalPayAmount();
        }
      }
```

Deindent

Select "$this->isSeparated"

# Продолжаем делать замены по одной за шаг.

Select "|||$result =||| $this->separatedAmount();"

Replace "return"

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return $result;
```

Remove selected

Select:
```
      if ($this->isRetired) {
        $result = $this->retiredAmount();
      }
      else {
        $result = $this->normalPayAmount();
      }
```

Deindent

Select "$this->isRetired"

# И последний раз.

Select "|||$result =||| $this->retiredAmount();"

Replace "return"

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return $result;
```

Remove selected

Select:
```
    $result = $this->normalPayAmount();
```

Deindent

Select:
```
    $result = 0;

```

# После всех этих изменений можно вообще избавиться от переменной <code>result</code>.

Remove selected

Wait 500ms

Select "$result ="

Replace "return"

Wait 500ms

Select:
```
    return $result;

```
Remove selected

# Вложенный условный код часто пишут программисты, которых учили, что в методе должна быть только одна точка выхода. На самом деле это слишком упрощённое (да и устаревшее) правило.

# Если во время выполнения метод больше не представляет для нас интереса, лучше выйти из него как можно скорее. Заставляя читателя рассматривать пустой блок <code>else</code>, вы только создаёте преграды на пути понимания кода.

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.