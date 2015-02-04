replace-nested-conditional-with-guard-clauses:php

###

1.ru. Выделите граничные условия, которые приводят к вызову исключения или немедленному возвращению значения из метода. Переместите эти условия в начало метода.
1.en. Isolate all guard clauses that lead to calling an exception or immediate return of a value from the method. Place these conditions at the beginning of the method.
1.uk. Виділіть граничні умови, які призводять до виклику виключення або негайного повернення значення з методу. Перемістіть ці умови в початок методу.

2.ru. После того, как с переносами покончено, и все тесты стали проходить, проверьте, можно ли использовать <a href="/consolidate-conditional-expression">объединение условных операторов</a> для граничных условных операторов, ведущих к одинаковым исключениям или возвращаемым значениям.
2.en. After rearrangement is complete and all tests are successfully completed, see whether you can use <a href="/consolidate-conditional-expression">Consolidate Conditional Expression</a> for guard clauses that lead to the same exceptions or returned values.
2.uk. Після того, як з перенесеннями покінчено, і усі тести стали проходити, перевірте, чи можна використати <a href="/consolidate-conditional-expression">ооб'єднання умовних операторів</a> для граничних умовних операторів, що ведуть до однакових виключень або повертаних значень.



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

#|ru| Представьте себе работу системы начисления зарплаты с особыми правилами для служащих, которые умерли, проживают раздельно или вышли на пенсию. Такие случаи необычны, но могут встретиться.
#|en| Imagine a payroll system with special rules for employees who have passed away, live on their own, or have retired. These cases are unusual but do occur.
#|uk| Уявіть собі роботу системи нарахування зарплати з особливими правилами для службовців, які померли, проживають окремо або вийшли на пенсію. Такі випадки незвичайні, але можуть зустрітися.

Select "$this->isDead"
+ Select "$this->isSeparated"
+ Select "$this->isRetired"

#|ru|+ В этом коде проверки особых условий...
#|en|+ This code for checking special conditions…
#|uk|+ У цьому коді перевірки особливих умов...

Select "$result = $this->normalPayAmount()"

#|ru|<= ...приводят к выполнению методов, результаты которых возвращаются в конце метода без дополнительной обработки. Это означает, что мы можем ввести граничные операторы и возвращать значения сразу же при прохождении какого-то условия.
#|en|<= …is concealing performance of ordinary actions. So using borderline operators will make the code more obvious.
#|uk|<= ...призводять до виконання методів, результати яких повертаються в кінці методу без додаткової обробки. Це означає, що ми можемо ввести граничні оператори та повертати значення відразу ж при проходженні якогось умови.

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

#|ru| Продолжаем делать замены по одной за шаг.
#|en| Continue performing replacements, one at a time.
#|uk| Продовжуємо робити заміни по одній за крок.

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

#|ru| И последний раз.
#|en| And the last one.
#|uk| І останній раз.

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

#|ru| После всех этих изменений можно вообще избавиться от переменной <code>result</code>.
#|en| After these changes, you can get rid of the <code>result</code> variable entirely.
#|uk| Після всіх цих змін можна взагалі позбутися змінної <code>result</code>.

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

#|ru| Вложенный условный код часто пишут программисты, которых учили, что в методе должна быть только одна точка выхода. На самом деле это слишком упрощённое (да и устаревшее) правило.
#|en| Inline conditionals are often written by programmers taught that a method should contain only one exit point. But in reality, this rule is simplistic and obsolete.
#|uk| Вкладений умовний код часто пишуть програмісти, яких вчили, що в методі повинна бути тільки одна точка виходу. Насправді це занадто спрощене (та й застаріле) правило.

#|ru| Если во время выполнения метод больше не представляет для нас интереса, лучше выйти из него как можно скорее. Заставляя читателя рассматривать пустой блок <code>else</code>, вы только создаёте преграды на пути понимания кода.
#|en| If a method is no longer of interest at runtime, it is best to exit it as soon as possible. Forcing the reader to go over an empty <code>else</code> block only throws up roadblocks to understanding your intentions.
#|uk| Якщо під час виконання метод більше не становить для нас інтересу, краще вийти з нього якомога швидше. Змушуючи читача розглядати порожній блок <code>else</code>, ви тільки створюєте перепони на шляху розуміння коду.

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