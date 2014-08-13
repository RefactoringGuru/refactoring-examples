decompose-conditional:csharp

###

1. Выделите условие в отдельный метод с помощью <a href="/extract-method">выделения метода</a>.

2. Повторите выделение для <code>then</code> и <code>else</code> части оператора.



###

```
class Stadium
{
  // ...
  public double summerRate;
  public double winterRate;
  public double winterServiceCharge;

  public double GetTicketPrice(DateTime date, int quantity)
  {
    double charge;

    if (date < SUMMER_START || date > SUMMER_END)
    {
      charge = quantity * winterRate + winterServiceCharge;
    }
    else
    {
      charge = quantity * summerRate;
    }

    return charge;
  }
}
```

###

```
class Stadium
{
  // ...
  public double summerRate;
  public double winterRate;
  public double winterServiceCharge;

  public double GetTicketPrice(DateTime date, int quantity)
  {
    double charge;

    if (NotSummer(date))
    {
      charge = WinterCharge(quantity);
    }
    else
    {
      charge = SummerCharge(quantity);
    }

    return charge;
  }

  private bool NotSummer(DateTime date)
  {
    return date < SUMMER_START || date > SUMMER_END;
  }
  private double WinterCharge(int quantity)
  {
    return quantity * winterRate + winterServiceCharge;
  }
  private double SummerCharge(int quantity)
  {
    return quantity * summerRate;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Разбиение условного оператора</i> на примере вычисления стоимости входного билета на стадион.

Select name of "GetTicketPrice"
+ Select "SUMMER_START"
+ Select "SUMMER_END"

# Стоимость отличается в зависимости от сезона — есть отдельный зимний и летний тариф.

# Наша задача — сделать этот условный оператор проще для понимания. Начнём с выделения условия в новый метод с более очевидным названием.

Go to the end of "Stadium"

Print:
```


  private bool NotSummer(DateTime date)
  {
  }
```

Select "date < SUMMER_START || date > SUMMER_END"

Wait 500ms

Go to the end of "NotSummer"

Print:
```

    return date < SUMMER_START || date > SUMMER_END;
```

Wait 500ms

Select "date < SUMMER_START || date > SUMMER_END" in "GetTicketPrice"

Remove selected

Print "NotSummer(date)"


#C Запускаем компиляцию и тестирование, чтобы убедиться в отсутствии ошибок.

#S Отлично, все работает!

Select "NotSummer(date)" in "GetTicketPrice"

# Условие стало очевидней, не правда ли? Многие программисты в таких ситуациях не выделяют части, образующие условие. Часто условия кажутся короткими и не стоящими такого труда.

# Тем не менее, несмотря на краткость условия, нередко существует большой разрыв между смыслом кода и его телом. Приходится смотреть в код и разбираться в том, что он делает. В данном случае это сделать нетрудно, но даже здесь выделенный метод более похож на комментарий.

Set step 2

Select "charge = quantity * winterRate + winterServiceCharge;"

# Теперь возьмёмся за тело условного оператора. Сначала выделим в новый метод всё, что находится внутри <code>then</code>

Go to the end of "Stadium"

Print:
```

  private double WinterCharge(int quantity)
  {
    return quantity * winterRate + winterServiceCharge;
  }
```

Select "charge = quantity * winterRate + winterServiceCharge;"

Replace "charge = WinterCharge(quantity);"


Select "charge = quantity * summerRate;"

# После этого возьмёмся за <code>else</code>


Go to the end of "Stadium"

Print:
```

  private double SummerCharge(int quantity)
  {
    return quantity * summerRate;
  }
```

Select "charge = quantity * summerRate;"

Replace "charge = SummerCharge(quantity);"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.