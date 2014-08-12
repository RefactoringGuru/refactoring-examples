replace-nested-conditional-with-guard-clauses:java

###

1. Выделите граничные условия, которые приводят к вызову исключения или немедленному возвращению значения из метода. Переместите эти условия в начало метода.

2. После того, как с переносами покончено, и все тесты стали проходить, проверьте, можно ли использовать <a href="/consolidate-conditional-expression">объединение условных операторов</a> для граничных условных операторов, ведущих к одинаковым исключениям или возвращаемым значениям.



###

```
class Payout {
  // ...
  double getPayAmount() {
    double result = 0;
    if (isDead) {
      result = deadAmount();
    }
    else {
      if (isSeparated) {
        result = separatedAmount();
      }
      else {
        if (isRetired) {
          result = retiredAmount();
        }
        else {
          result = normalPayAmount();
        }
      }
    }
    return result;
  }
}
```

###

```
class Payout {
  // ...
  double getPayAmount() {
    if (isDead) {
      return deadAmount();
    }
    if (isSeparated) {
      return separatedAmount();
    }
    if (isRetired) {
      return retiredAmount();
    }
    return normalPayAmount();
  }
}
```

###

Set step 1

# Представьте себе работу системы начисления зарплаты с особыми правилами для служащих, которые умерли, проживают раздельно или вышли на пенсию. Такие случаи необычны, но могут встретиться.

Select "isDead"
+ Select "isSeparated"
+ Select "isRetired"

#+ В этом коде проверки особых условий...

Select "result = normalPayAmount()"

#<= ...скрывают за собой выполнение обычных действий, поэтому при использовании граничных операторов код станет яснее.

Select "|||result =||| deadAmount();"

Print "return"

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return result;
```

Remove selected

Select:
```
      if (isSeparated) {
        result = separatedAmount();
      }
      else {
        if (isRetired) {
          result = retiredAmount();
        }
        else {
          result = normalPayAmount();
        }
      }
```

Deindent

Select "isSeparated"

# Продолжаем делать замены по одной за шаг.

Select "|||result =||| separatedAmount();"

Print "return"

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return result;
```

Remove selected

Select:
```
      if (isRetired) {
        result = retiredAmount();
      }
      else {
        result = normalPayAmount();
      }
```

Deindent

Select "isRetired"

# И последний раз.

Select "|||result =||| retiredAmount();"

Print "return"

Select:
```
    }
|||    else {
|||
```

+Select:
```
|||    }
|||    return result;
```

Remove selected

Select:
```
      result = normalPayAmount();
```

Deindent

Select:
```
    double result = 0;

```

# После всех этих изменений можно вообще избавиться от переменной <code>result</code>

Remove selected

Wait 500ms

Select "result ="
Print "return"

Wait 500ms

Select:
```
    return result;

```
Remove selected

# Вложенный условный код часто пишут программисты, которых учили, что в методе должна быть только одна точка выхода. На самом деле это слишком упрощённое (да и устаревшее) правило.

# Если во время выполнения, метод больше не представляет для нас интереса, лучше выйти из него как можно скорее. Заставляя читателя рассматривать пустой блок <code>else</code>, вы только создаёте преграды на пути понимания кода.

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.