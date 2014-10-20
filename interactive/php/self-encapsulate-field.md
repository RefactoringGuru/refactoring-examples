self-encapsulate-field:php

###

1. Создайте геттер (и опциональный сеттер) для поля.

2. Найдите все прямые обращения к полю и замените их вызовами геттера и сеттера.



###

```
class IntRange {
  private $low;
  private $high;

  public function includes($arg) {
    return $arg >= $this->low && $arg <= $this->high;
  }
  public function grow($factor) {
    $this->high = $this->high * $factor;
  }
  public function __construct($low, $high) {
    $this->low = $low;
    $this->high = $high;
  }
}
```

###

```
class IntRange {
  private $low;
  private $high;

  public function getLow() {
    return $this->low;
  }
  public function getHigh() {
    return $this->high;
  }
  public function setLow($arg) {
    $this->low = $arg;
  }
  public function setHigh($arg) {
    $this->high = $arg;
  }
  public function includes($arg) {
    return $arg >= $this->getLow() && $arg <= $this->getHigh();
  }
  public function grow($factor) {
    $this->setHigh($this->getHigh() * $factor);
  }
  public function __construct($low, $high) {
    $this->low = $low;
    $this->high = $high;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Самоинкапсуляцию</i> на примере класса диапазонов.<br/><br/>Самоинкапсуляция заключается в реализации доступа к полям через методы доступа даже в методах самого класса.

Go to before "includes"

# Эти методы доступа нужно создать, если их ещё нет. Итак, создадим геттеры и сеттеры в нашем классе.

Print:
```

  public function getLow() {
    return $this->low;
  }
  public function getHigh() {
    return $this->high;
  }
  public function setLow($arg) {
    $this->low = $arg;
  }
  public function setHigh($arg) {
    $this->high = $arg;
  }
```

Set step 2

Select "low" in "includes"
+ Select "high" in "includes"
+ Select "high" in "grow"

# В нашем примере есть несколько методов, которые используют прямой доступ к полям.

# Для завершения самоинкапсуляции давайте заменим все обращения к полям в этих методах вызовами геттеров и сеттеров.

Select "low" in "includes"

Replace "getLow()"

Wait 500ms

Select "high" in "includes"

Replace "getHigh()"

Wait 500ms

Select "high = " in "grow"

Replace "setHigh("

Wait 500ms

Go to "|||;" in "grow"

Print ")"

Wait 500ms

Select "($this->|||high|||" in "grow"

Replace "getHigh()"

Select "$this->low" in "__construct"
+Select "$this->high" in "__construct"

# Как вы могли заметить, мы не трогали присваивания в конструкторе. Часто предполагается, что сеттер применяется уже после создания объекта, поэтому его поведение может быть иным, чем во время инициализации.

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.