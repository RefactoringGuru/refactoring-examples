decompose-conditional:php

###

1.ru. Выделите условие в отдельный метод с помощью <a href="/extract-method">выделения метода</a>.
1.en. Extract the conditional to a separate method via <a href="/extract-method">Extract Method</a>.
1.uk. Виділіть умову в окремий метод за допомогою <a href="/extract-method">відокремлення методу</a>.

2.ru. Повторите выделение для <code>then</code> и <code>else</code> части оператора.
2.en. Repeat the process for the <code>then</code> and <code>else</code> blocks.
2.uk. Повторіть видокремлення для частин оператора then і else.



###

```
class Stadium {
  // ...
  public $summerRate;
  public $winterRate;
  public $winterServiceCharge;

  public function getTicketPrice(DateTime $date, $quantity) {
    if ($date->format("m") < "06" || $date->format("m") > "08") {
      $charge = $quantity * $this->winterRate + $this->winterServiceCharge;
    }
    else {
      $charge = $quantity * $this->summerRate;
    }
    return $charge;
  }
}
```

###

```
class Stadium {
  // ...
  public $summerRate;
  public $winterRate;
  public $winterServiceCharge;

  public function getTicketPrice(DateTime $date, $quantity) {
    if ($this->notSummer($date)) {
      $charge = $this->winterCharge($quantity);
    }
    else {
      $charge = $this->summerCharge($quantity);
    }
    return $charge;
  }

  private function notSummer(DateTime $date) {
    return $date->format("m") < "06" || $date->format("m") > "08";
  }
  private function winterCharge($quantity) {
    return $quantity * $this->winterRate + $this->winterServiceCharge;
  }
  private function summerCharge($quantity) {
    return $quantity * $this->summerRate;
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Разбиение условного оператора</i> на примере вычисления стоимости входного билета на стадион.
#|en| Let's look at <i>Decompose Conditional</i> by looking at how to calculate the cost of a stadium ticket.
#|uk| Давайте розглянемо <i>Розбиття умовного оператора<i> на прикладі обчислення вартості вхідного квитка на стадіон.

Select name of "getTicketPrice"

#|ru| Стоимость отличается в зависимости от сезона: есть отдельный зимний и летний тариф.
#|en| The cost depends on the season (winter or summer).
#|uk| Вартість відрізняється в залежності від сезону – є окремий зимовий та літній тариф.

#|ru| Наша задача — сделать этот условный оператор проще для понимания. Начнём с выделения условия в новый метод с более очевидным названием.
#|en| Our task is to make this conditional easier to understand. We can start by extracting the condition to a new method with a more obvious name.
#|uk| Наше завдання – зробити цей умовний оператор простіше для розуміння. Почнемо з виділення умови в новий метод з більш очевидним назвою.

Go to the end of "Stadium"

Print:
```


  private function notSummer(DateTime $date) {
  }
```

Select "$date->format("m") < "06" || $date->format("m") > "08""

Wait 500ms

Go to the end of "notSummer"

Print:
```

    return $date->format("m") < "06" || $date->format("m") > "08";
```

Wait 500ms

Select "$date->format("m") < "06" || $date->format("m") > "08"" in "getTicketPrice"

Remove selected

Print "$this->notSummer($date)"


#C|ru| Запускаем тестирование, чтобы убедиться в отсутствии ошибок.
#S Отлично, все работает!

#C|en| Let's launch autotests to check for errors in code.
#S Wonderful, it's all working!

#C|uk| Запускаємо тестування, щоб переконатися у відсутності помилок.
#S Супер, все працює.

Select "$this->notSummer($date)" in "getTicketPrice"

#|ru| Условие стало очевидней, не правда ли? Многие программисты в таких ситуациях не выделяют части, образующие условие. Часто условия кажутся короткими и не стоящими такого труда.
#|en| The condition is clearer now, yes? Many programmers in such situations do not extract the components of the conditional. Often the conditions seem short and not worth the effort.
#|uk| Умова стала очевиднішою , чи не так? Багато програмістів в таких ситуаціях не виділяють частини, що утворюють умову. Часто умови здаються короткими і не вартими такої праці.

#|ru| Тем не менее, несмотря на краткость условия, нередко существует большой разрыв между смыслом кода и его телом. Приходится смотреть в код и разбираться в том, что он делает. В данном случае это сделать нетрудно, но даже здесь выделенный метод более похож на комментарий.
#|en| But no matter how short the condition is, there is often a big difference between the purpose of the code and its body. Figuring this out requires looking at the code in detail. In this case, doing so is easy but even here the extracted method looks more like a comment.
#|uk| Тим не менш, незважаючи на стислість умови, нерідко існує великий розрив між сенсом коду і його тілом. Доводиться дивитися в код і розбиратися в тому, що він робить. В даному випадку це зробити неважко, але навіть тут виділений метод більш схожий на коментар.

Set step 2

Select "$charge = $quantity * $this->winterRate + $this->winterServiceCharge;"

#|ru| Теперь возьмёмся за тело условного оператора. Сначала выделим в новый метод всё, что находится внутри <code>then</code>
#|en| Now we turn to the body of the conditional. First we extract everything inside <code>then</code> to a new method.
#|uk| Тепер візьмемося за тіло умовного оператора. Спочатку виділимо в новий метод все, що знаходиться всередині <code>then</code>

Go to the end of "Stadium"

Print:
```

  private function winterCharge($quantity) {
    return $quantity * $this->winterRate + $this->winterServiceCharge;
  }
```

Select "$charge = $quantity * $this->winterRate + $this->winterServiceCharge;"

Replace "$charge = $this->winterCharge($quantity);"


Select "$charge = $quantity * $this->summerRate;"

#|ru| После этого возьмёмся за <code>else</code>
#|en| Then we turn our attention to <code>else</code>.
#|uk| Після цього візьмемося за <code>else</code>


Go to the end of "Stadium"

Print:
```

  private function summerCharge($quantity) {
    return $quantity * $this->summerRate;
  }
```

Select "$charge = $quantity * $this->summerRate;"

Replace "$charge = $this->summerCharge($quantity);"

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