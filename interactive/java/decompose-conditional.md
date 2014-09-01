decompose-conditional:java

###

1. Выделите условие в отдельный метод с помощью <a href="/extract-method">выделения метода</a>.

2. Повторите выделение для <code>then</code> и <code>else</code> части оператора.



###

```
class Stadium {
  // ...
  public double summerRate;
  public double winterRate;
  public double winterServiceCharge;

  public double getTicketPrice(Date date, int quantity) {
    double charge;
    if (date.before(SUMMER_START) || date.after(SUMMER_END)) {
      charge = quantity * winterRate + winterServiceCharge;
    }
    else {
      charge = quantity * summerRate;
    }
    return charge;
  }
}
```

###

```
class Stadium {
  // ...
  public double summerRate;
  public double winterRate;
  public double winterServiceCharge;

  public double getTicketPrice(Date date, int quantity) {
    double charge;
    if (notSummer(date)) {
      charge = winterCharge(quantity);
    }
    else {
      charge = summerCharge(quantity);
    }
    return charge;
  }

  private boolean notSummer(Date date) {
    return date.before(SUMMER_START) || date.after(SUMMER_END);
  }
  private double winterCharge(int quantity) {
    return quantity * winterRate + winterServiceCharge;
  }
  private double summerCharge(int quantity) {
    return quantity * summerRate;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Разбиение условного оператора</i> на примере вычисления стоимости входного билета на стадион.

Select name of "getTicketPrice"
+ Select "SUMMER_START"
+ Select "SUMMER_END"

# Стоимость отличается в зависимости от сезона: есть отдельный зимний и летний тариф.

# Наша задача — сделать этот условный оператор проще для понимания. Начнём с выделения условия в новый метод с более очевидным названием.

Go to the end of "Stadium"

Print:
```


  private boolean notSummer(Date date) {
  }
```

Select "date.before(SUMMER_START) || date.after(SUMMER_END)"

Wait 500ms

Go to the end of "notSummer"

Print:
```

    return date.before(SUMMER_START) || date.after(SUMMER_END);
```

Wait 500ms

Select "date.before(SUMMER_START) || date.after(SUMMER_END)" in "getTicketPrice"

Remove selected

Print "notSummer(date)"


#C Запускаем компиляцию и тестирование, чтобы убедиться в отсутствии ошибок.

#S Отлично, все работает!

Select "notSummer(date)" in "getTicketPrice"

# Условие стало очевидней, не правда ли? Многие программисты в таких ситуациях не выделяют части, образующие условие. Часто условия кажутся короткими и не стоящими такого труда.

# Тем не менее, несмотря на краткость условия, нередко существует большой разрыв между смыслом кода и его телом. Приходится смотреть в код и разбираться в том, что он делает. В данном случае это сделать нетрудно, но даже здесь выделенный метод более похож на комментарий.

Set step 2

Select "charge = quantity * winterRate + winterServiceCharge;"

# Теперь возьмёмся за тело условного оператора. Сначала выделим в новый метод всё, что находится внутри <code>then</code>

Go to the end of "Stadium"

Print:
```

  private double winterCharge(int quantity) {
    return quantity * winterRate + winterServiceCharge;
  }
```

Select "charge = quantity * winterRate + winterServiceCharge;"

Replace "charge = winterCharge(quantity);"


Select "charge = quantity * summerRate;"

# После этого возьмёмся за <code>else</code>


Go to the end of "Stadium"

Print:
```

  private double summerCharge(int quantity) {
    return quantity * summerRate;
  }
```

Select "charge = quantity * summerRate;"

Replace "charge = summerCharge(quantity);"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.