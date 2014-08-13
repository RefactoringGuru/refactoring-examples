replace-temp-with-query:csharp

###

1. Убедитесь, что переменной в пределах метода присваивается значение только один раз.

2. Используйте <b>извлечение метода</b> для того, чтобы переместить интересующее выражение в новый метод.

3. Замените использование переменной вызовом вашего нового метода.



###

```
public class Product
{
  // ...
  public double GetPrice()
  {
    int basePrice = quantity * itemPrice;
    double discountFactor;

    if (basePrice > 1000)
    {
      discountFactor = 0.95;
    }
    else
    {
      discountFactor = 0.98;
    }

    return basePrice * discountFactor;
  }
}
```

###

```
public class Product
{
  // ...
  public double GetPrice()
  {
    return BasePrice() * DiscountFactor();
  }
  private int BasePrice()
  {
    return quantity * itemPrice;
  }
  private double DiscountFactor()
  {
    if (BasePrice() > 1000)
    {
      return 0.95;
    }
    return 0.98;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Замену переменной вызовом метода</i> на примере этого простого метода.

Select "int |||basePrice|||"
+Select "double |||discountFactor|||"

# Я намерен по очереди заменить переменные <code>basePrice</code> и <code>discountFactor</code> вызовами соответствующих методов.

# Для начала нужно убедиться, что переменным в пределах метода значение присваивается только один раз.

# В данном случае это и так видно, но чтобы обезопасить себя, можно временно объявить эти переменные константами (используя для этого ключевое слово <code>const</code>). В таком случае компилятор укажет все места, где константам пытаются повторно присвоить значения.

Go to "|||int basePrice"

Print "const "

Wait 500ms

Go to "|||double discountFactor"

Print "const "

Select "const double |||discountFactor|||"

# Обратите внимание, что если переменная не была проинициализирована при объявлении, то при ее превращении в константу, надо эту инициализацию произвести (иначе будет ошибка объявления константы). Для этого присвойте ей любое временное значение.

Go to "double discountFactor|||"

Print " = 0"

#C Давайте запустим компиляцию и посмотрим, присваиваются ли нашим переменным еще значения.

#F Ошибка! Левая часть выражения присваивания должна быть переменной, свойством или индексатором (<code>line 11</code>, <code>line 15</code>).

Select "      discountFactor = 0.95;"
+Select "      discountFactor = 0.98;"

# Чтож, компилятор указывает нам на две строки, где происходит попытка изменить значение константы. Ошибки относятся только к <code>discountFactor</code>. Вспомним, что мы присвоили ей временное значение, а значит одна ошибка присваивания должна быть в любом случае. Но почему компилятор указывает на два места, а не на одно? После внимательного осмотра становится понятно, что это инициализация переменной во взаимоисключающих ветках <code>if-else</code>, поэтому значение будет присвоено только один раз. А раз это так, то можем смело приступать к рефакторингу.

Select "const "
+Select "double discountFactor||| = 0|||"

# После того как мы убедились, что интересующие нас переменные получают свои значения лишь единожды, надо вернуть их в исходное состояние.

Wait 500ms

Remove selected

#C Давайте запустим компиляцию и удостоверимся, что мы не изменили ничего лишнего.

#S <b>Все отлично, можем продолжать!</b><br/><br/>Имейте в виду, эта проверка переменных очень важна. При возникновении проблем на этом шаге, следует воздержаться от проведения данного рефакторинга.

Set step 2

Select "basePrice = quantity * itemPrice"

# Итак, на втором шаге, создадим метод <code>BasePrice()</code> и перенесём в него выражение, формирующее переменную <code>basePrice</code>.

Go to the end of "Product"

Print:
```

  private int BasePrice()
  {
    return quantity * itemPrice;
  }
```

Select "basePrice = |||quantity * itemPrice|||"

# Теперь вызов метода можно использовать вместо первоначального выражения. Таким образом, у нас теперь есть новый метод, а весь старый код все ещё в рабочем состоянии.

Print "BasePrice()"

Set step 3

Select "(|||basePrice||| >"

# Самое время начать заменять переменную непосредственным вызовом метода.

# Заменим первую переменную, а затем запустим компиляцию, чтобы убедиться, что ничего не сломалось.

Print "BasePrice()"

#C Запускаем компиляцию и тестирование.

#S Всё отлично, можно продолжать!

Select "return |||basePrice|||"

# Выполним следующую замену.

Print "BasePrice()"

#C Запускаем компиляцию и тестирование.

#S Всё отлично, можно продолжать!

Select:
```
    int basePrice = BasePrice();

```

# Прошлая замена была последней, поэтому мы можем удалить объявление переменной.

Remove selected

Select "double |||discountFactor|||"

# С первой переменной покончено, и мы можем повторить все действия для выделения <code>discountFactor</code>.

Go to the end of "Product"

# Создаём новый метод.

Print:
```

  private double DiscountFactor()
  {
    if (BasePrice() > 1000)
    {
      return 0.95;
    }
    return 0.98;
  }
```

Go to "double discountFactor|||;"

# ...используем его для инициализации переменной

Print " = DiscountFactor()"

Select:
```

    if (BasePrice() > 1000)
    {
      discountFactor = 0.95;
    }
    else
    {
      discountFactor = 0.98;
    }


```

#^ ...и удаляем код, ставший теперь ненужным...

Remove selected

# Обратите внимание на то, как трудно было бы выделить <code>discountFactor</code> без предварительной замены <code>basePrice</code> вызовом метода.

# В итоге приходим к следующему виду метода <code>GetPrice()</code>

Select:
```
    double discountFactor = DiscountFactor();

```

Remove selected

Select "discountFactor" in "GetPrice"

Replace "DiscountFactor()"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.