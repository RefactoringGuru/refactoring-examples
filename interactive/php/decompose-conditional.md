decompose-conditional:php

###

1. Выделите условие в отдельный метод с помощью <a href="/extract-method">выделения метода</a>.

2. Повторите выделение для <code>then</code> и <code>else</code> части оператора.



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

# Давайте рассмотрим <i>Разбиение условного оператора</i> на примере вычисления стоимости входного билета на стадион.

Select name of "getTicketPrice"

# Стоимость отличается в зависимости от сезона — есть отдельный зимний и летний тариф.

# Наша задача — сделать этот условный оператор проще для понимания. Начнём с выделения условия в новый метод с более очевидным названием.

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


#C Запускаем тестирование, чтобы убедиться в отсутствии ошибок.

#S Отлично, все работает!

Select "$this->notSummer($date)" in "getTicketPrice"

# Условие стало очевидней, не правда ли? Многие программисты в таких ситуациях не выделяют части, образующие условие. Часто условия кажутся короткими и не стоящими такого труда.

# Тем не менее, несмотря на краткость условия, нередко существует большой разрыв между смыслом кода и его телом. Приходится смотреть в код и разбираться в том, что он делает. В данном случае это сделать нетрудно, но даже здесь выделенный метод более похож на комментарий.

Set step 2

Select "$charge = $quantity * $this->winterRate + $this->winterServiceCharge;"

# Теперь возьмёмся за тело условного оператора. Сначала выделим в новый метод всё, что находится внутри <code>then</code> 

Go to the end of "Stadium"

Print:
```

  private function winterCharge($quantity) {
    return $quantity * $this->winterRate + $this->winterServiceCharge;
  }
```

Select "$charge = $quantity * $this->winterRate + $this->winterServiceCharge;"

Wait 500ms

Print "$charge = $this->winterCharge($quantity);"


Select "$charge = $quantity * $this->summerRate;"

# После этого, возьмёмся за <code>else</code>


Go to the end of "Stadium"

Print:
```

  private function summerCharge($quantity) {
    return $quantity * $this->summerRate;
  }
```

Select "$charge = $quantity * $this->summerRate;"

Wait 500ms

Print "$charge = $this->summerCharge($quantity);"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.