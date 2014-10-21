replace-temp-with-query:php

###

1. Убедитесь, что переменной в пределах метода присваивается значение только один раз.

2. Используйте <b>извлечение метода</b> для того, чтобы переместить интересующее выражение в новый метод.

3. Замените использование переменной вызовом вашего нового метода.



###

```
class Product {
  // ...
  function getPrice() {
    $basePrice = $this->quantity * $this->itemPrice;
    if ($basePrice > 1000) {
      $discountFactor = 0.95;
    }
    else {
      $discountFactor = 0.98;
    }
    return $basePrice * $discountFactor;
  }
}
```

###

```
class Product {
  // ...
  function getPrice() {
    return $this->basePrice() * $this->discountFactor();
  }
  private function basePrice() {
    return $this->quantity * $this->itemPrice;
  }
  private function discountFactor() {
    if ($this->basePrice() > 1000) {
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

# Рассмотрим <i>Замену переменной вызовом метода</i> на примере этого простого метода.

Select "|||$basePrice||| = "
+Select "|||$discountFactor||| ="

# Давайте по очереди заменим переменные <code>basePrice</code> и <code>discountFactor</code> вызовами соответствующих методов.

Select "$basePrice = "

# Для начала нужно убедиться, что переменным в пределах метода значение присваивается только один раз.

# В данном случае всё так и есть, можем продолжать.

Set step 2

Select "$basePrice = $this->quantity * $this->itemPrice"

# Итак, на втором шаге, создадим метод <code>basePrice()</code> и перенесём в него выражение, формирующее переменную <code>basePrice</code>.

Go to the end of "Product"

Print:
```

  private function basePrice() {
    return $this->quantity * $this->itemPrice;
  }
```

Select "$this->quantity * $this->itemPrice" in "getPrice"

# Теперь вызов метода можно использовать вместо первоначального выражения. Таким образом, у нас теперь есть новый метод, а весь старый код все ещё в рабочем состоянии.

Print "$this->basePrice()"

Set step 3

Select "(|||$basePrice||| >"

# Самое время начать заменять переменную непосредственным вызовом метода.

# Заменим первую переменную, а затем запустим авто-тесты, чтобы убедиться, что ничего не сломалось.

Print "$this->basePrice()"

#C Запускаем тестирование.

#S Всё отлично, можно продолжать!

Select "return |||$basePrice|||"

# Выполним следующую замену.

Print "$this->basePrice()"

#C Запускаем тестирование.

#S Всё отлично, можно продолжать!

Select:
```
    $basePrice = $this->basePrice();

```

# Прошлая замена была последней, поэтому мы можем удалить объявление переменной.

Remove selected

Select "$discountFactor"

# С первой переменной покончено, и мы можем повторить все действия для выделения <code>discountFactor</code>.

Go to the end of "Product"

# Создаём новый метод…

Print:
```

  private function discountFactor() {
    if ($this->basePrice() > 1000) {
      return 0.95;
    }
    else {
      return 0.98;
    }
  }
```

Select in "getPrice":
```
    if ($this->basePrice() > 1000) {
      $discountFactor = 0.95;
    }
    else {
      $discountFactor = 0.98;
    }
```

# ...используем его для инициализации переменной и удаляем лишний теперь код...

Print "    $discountFactor = $this->discountFactor();"

# Обратите внимание на то, как трудно было бы выделить <code>discountFactor</code> без предварительной замены <code>basePrice</code> вызовом метода.

# В итоге приходим к следующему виду метода <code>getPrice()</code>.

Select:
```
    $discountFactor = $this->discountFactor();

```

Remove selected

Select "$discountFactor" in "getPrice"

Replace "$this->discountFactor()"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.