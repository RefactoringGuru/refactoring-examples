extract-variable:php

###

1. Создайте локальную переменную и передайте ей необходимое значение.

2. Замените исходное выражение вашей новой переменной.



###

```
public function price() {
  // Price consists of: base price - discount + shipping cost
  return $this->quantity * $this->itemPrice -
    Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05 +
    Math.min($this->quantity * $this->itemPrice * 0.1, 100.0);
}
```

###

```
public function price() {
  $basePrice = $this->quantity * $this->itemPrice;
  $quantityDiscount = Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05;
  $shipping = Math.min($basePrice * 0.1, 100.0);
  return $basePrice - $quantityDiscount + $shipping;
}
```

###

Set step 1

# Давайте рассмотрим <i>Извлечение переменной</i> на примере этого простого метода.

# Как видите, метод состоит из одного громадного выражения, в котором сам черт ногу сломит.

# Давайте разобьём это выражение на отдельные части, при этом каждую из частей поместим в отдельную переменную.

Select "$this->quantity * $this->itemPrice"

# Сперва определим базовую цену <code>basePrice</code> как количество товаров в заказе, умноженное на стоимость одной единицы…

Set step 2

Go to "shipping cost|||"

Print:
```

  $basePrice = $this->quantity * $this->itemPrice;
```

Select 2nd "$this->quantity * $this->itemPrice"
+ Select 3rd "$this->quantity * $this->itemPrice"

# ...и применим новую переменную в формуле. Данное выражение повторяется несколько раз, поэтому заменим все одинаковые вычисления переменной.

Print "$basePrice"

#C Давайте запустим тесты и удостоверимся, что все хорошо.

#S Все отлично, можем продолжать!

Select:
```
    Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05 +
    Math.min($basePrice * 0.1, 100.0);
```

# Теперь необходимо заменить переменными оставшиеся части сложного выражения.

Select:
```
Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05
```

# Определим скидку за количество купленного товара <code>quantityDiscount</code>, а расчёт вынесем в новую переменную.

Go to "$this->quantity * $this->itemPrice;|||"

Print:
```

  $quantityDiscount = Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05;
```

Select:
```

    Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05
```

Replace " $quantityDiscount"

#C Готово, теперь запустим тесты и удостоверимся, что не возникло ошибок.

#S Все отлично, можем продолжать!

Select:
```
Math.min($basePrice * 0.1, 100.0)
```

# Последняя часть расчётов  – это доставка <code>shipping</code>. Здесь также используем отдельную переменную.

Go to "$this->itemPrice * 0.05;|||"

Print:
```

  $shipping = Math.min($basePrice * 0.1, 100.0);
```

Select:
```

    Math.min($basePrice * 0.1, 100.0)
```

Replace " $shipping"

Select:
```
  // Price consists of: base price - discount + shipping cost

```

# Кстати, теперь выражение стало вполне очевидным, поэтому комментарий можно убрать.

Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.