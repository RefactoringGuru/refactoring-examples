replace-temp-with-query:java

###

1. Убедитесь, что переменной в пределах метода присваивается значение только один раз.

2. Используйте <b>извлечение метода</b> для того, чтобы переместить интересующее выражение в новый метод.

3. Замените использование переменной вызовом вашего нового метода.



###

```
class Product {
  // ...
  double getPrice() {
    int basePrice = quantity * itemPrice;
    double discountFactor;
    if (basePrice > 1000) {
      discountFactor = 0.95;
    }
    else {
      discountFactor = 0.98;
    }
    return basePrice * discountFactor;
  }
}
```

###

```
class Product {
  // ...
  double getPrice() {
    return basePrice() * discountFactor();
  }
  private int basePrice() {
    return quantity * itemPrice;
  }
  private double discountFactor() {
    if (basePrice() > 1000) {
      return 0.95;
    }
    else {
      return 0.98;
    }
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Замену переменной вызовом метода</i> на примере этого простого метода.

Select "int |||basePrice|||"
+Select "double |||discountFactor|||"

# Я намерен по очереди заменить переменные <code>basePrice</code> и <code>discountFactor</code> вызовами соответствующих методов.

# Итак, для начала, нужно убедиться, что переменным в пределах метода значение присваивается только один раз.

# В данном случае это и так видно, но чтобы обезопасить себя, можно объявить эти переменные с ключевым словом <code>final</code>. После этого, если переменной таки присваиваются другие значения, нам об этом сообщит компилятор.

Go to "|||int basePrice"

Print "final "

Wait 500ms

Go to "|||double discountFactor"

Print "final "

#C Давайте запустим компиляцию и удостоверимся, что все хорошо.

#S <b>Все отлично, можем продолжать!</b><br/><br/>Имейте в виду, эта проверка очень важна. При возникновении проблем на этом шаге, следует воздержаться от проведения данного рефакторинга.

Set step 2

Select "basePrice = quantity * itemPrice"

# Итак, на втором шаге, создадим метод <code>basePrice()</code> и перенесём в него выражение, формирующее переменную <code>basePrice</code>.

Go to the end of "Product"

Print:
```

  private int basePrice() {
    return quantity * itemPrice;
  }
```

Select "basePrice = |||quantity * itemPrice|||"

# Теперь вызов метода можно использовать вместо первоначального выражения. Таким образом, у нас теперь есть новый метод, а весь старый код все ещё в рабочем состоянии.

Print "basePrice()"

Set step 3

Select "(|||basePrice||| >"

# Самое время начать заменять переменную непосредственным вызовом метода.

# Заменим первую переменную, а затем запустим компиляцию, чтобы убедиться, что ничего не сломалось.

Print "basePrice()"

#C Запускаем компиляцию и тестирование.

#S Всё отлично, можно продолжать!

Select "return |||basePrice|||"

# Выполним следующую замену.

Print "basePrice()"

#C Запускаем компиляцию и тестирование.

#S Всё отлично, можно продолжать!

Select:
```
    final int basePrice = basePrice();

```

# Прошлая замена была последней, поэтому мы можем удалить объявление переменной.

Remove selected

Select "double |||discountFactor|||"

# С первой переменной покончено, и мы можем повторить все действия для выделения <code>discountFactor()</code>.

Go to the end of "Product"

# Создаём новый метод.

Print:
```

  private double discountFactor() {
    if (basePrice() > 1000) {
      return 0.95;
    }
    else {
      return 0.98;
    }
  }
```

Go to "double discountFactor|||;"

# ...используем его для инициализации переменной

Print " = discountFactor()"

Select:
```
    if (basePrice() > 1000) {
      discountFactor = 0.95;
    }
    else {
      discountFactor = 0.98;
    }

```

#^ ...и удаляем лишний теперь код...

Remove selected

# Обратите внимание на то, как трудно было бы выделить <code>discountFactor</code> без предварительной замены <code>basePrice</code> вызовом метода.

# В итоге приходим к следующему виду метода <code>getPrice()</code>

Select:
```
    final double discountFactor = discountFactor();

```

Remove selected

Select "discountFactor" in "getPrice"

Replace "discountFactor()"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.