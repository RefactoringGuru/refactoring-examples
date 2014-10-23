replace-nested-conditional-with-guard-clauses:php

###

1.ru. Выделите граничные условия, которые приводят к вызову исключения или немедленному возвращению значения из метода. Переместите эти условия в начало метода.
1.uk. Виділіть граничні умови, які призводять до виклику виключення або негайного повернення значення з методу. Перемістіть ці умови в початок методу.

2.ru. После того, как с переносами покончено, и все тесты стали проходить, проверьте, можно ли использовать <a href="/consolidate-conditional-expression">объединение условных операторов</a> для граничных условных операторов, ведущих к одинаковым исключениям или возвращаемым значениям.
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
#|uk| Уявіть собі роботу системи нарахування зарплати з особливими правилами для службовців, які померли, проживають окремо або вийшли на пенсію. Такі випадки незвичайні, але можуть зустрітися.

Select "$this->isDead"
+ Select "$this->isSeparated"
+ Select "$this->isRetired"

#|ru|+ В этом коде проверки особых условий...
#|uk|+ У цьому коді перевірки особливих умов...

Select "$result = $this->normalPayAmount()"

#|ru|<= ...скрывают за собой выполнение обычных действий. Поэтому при использовании граничных операторов код станет яснее.
#|uk|<= ...приховують за собою виконання звичайних дій. Тому при використанні граничних операторів код стане ясніше.

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
#|uk| Вкладений умовний код часто пишуть програмісти, яких вчили, що в методі повинна бути тільки одна точка виходу. Насправді це занадто спрощене (та й застаріле) правило.

#|ru| Если во время выполнения метод больше не представляет для нас интереса, лучше выйти из него как можно скорее. Заставляя читателя рассматривать пустой блок <code>else</code>, вы только создаёте преграды на пути понимания кода.
#|uk| Якщо під час виконання метод більше не становить для нас інтересу, краще вийти з нього якомога швидше. Змушуючи читача розглядати порожній блок <code>else</code>, ви тільки створюєте перепони на шляху розуміння коду.

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.