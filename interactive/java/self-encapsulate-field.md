self-encapsulate-field:java

###

1. Создайте геттер (и опциональный сеттер) для поля.

2. Найдите все обращения к полю и замените их вызовами геттера и сеттера.



###

```
class IntRange {
  private int low, high;

  public boolean includes(int arg) {
    return arg >= low && arg <= high;
  }
  public void grow(int factor) {
    high = high * factor;
  }
  public IntRange(int low, int high) {
    this.low = low;
    this.high = high;
  }
}
```

###

```
class IntRange {
  private int low, high;

  int getLow() {
    return low;
  }
  int getHigh() {
    return high;
  }
  void setLow(int arg) {
    low = arg;
  }
  void setHigh(int arg) {
    high = arg;
  }
  public boolean includes(int arg) {
    return arg >= getLow() && arg <= getHigh();
  }
  public void grow(int factor) {
    setHigh(getHigh() * factor);
  }
  public IntRange(int low, int high) {
    this.low = low;
    this.high = high;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Самоинкапсуляцию</i> на примере класса диапазонов.<br/><br/>Самоинкапсуляция заключается в замене прямого доступа к полям вызовами методов доступа даже в методах самого класса.

Go to before "includes"

# Эти методы доступа нужно создать, если их ещё нет. Итак, создадим геттеры и сеттеры в нашем классе.

Print:
```

  int getLow() {
    return low;
  }
  int getHigh() {
    return high;
  }
  void setLow(int arg) {
    low = arg;
  }
  void setHigh(int arg) {
    high = arg;
  }
```

Set step 2

Select "low" in "includes"
+ Select "high" in "includes"
+ Select "high" in "grow"

# В нашем случае, есть несколько методов, которые используют прямой доступ к полям.

# Для завершения самоинкапсуляции, нам нужно заменить эти обращения вызовами геттеров и сеттеров. Давайте проделаем это для каждого из них.

Select "low" in "includes"

Print "getLow()"

Wait 500ms

Select "high" in "includes"

Print "getHigh()"

Wait 500ms

Select "high = " in "grow"

Print "setHigh("

Wait 500ms

Go to "|||;" in "grow"

Print ")"

Wait 500ms

Select "(|||high|||" in "grow"

Print "getHigh()"

Select "this.low"
Select "this.high"

# Как вы могли заметить, мы не трогали присваивания в конструкторе. Часто предполагается, что сеттер применяется уже после создания объекта, поэтому его поведение может быть иным, чем во время инициализации.

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.