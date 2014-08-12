replace-parameter-with-method:php

###

1. Убедитесь, что код получения значения не использует параметров из текущего метода, т.к. они будут недоступны внутри другого метода, из-за чего перенос будет невозможен.

2. Если код получения значения сложнее, чем один вызов какого-то метода или функции, примените <a href="/extract-method">извлечение метода</a>, чтобы выделить этот код в новый метод и сделать вызов простым.

3. В коде главного метода замените все обращения к заменяемому параметру вызовами метода получения значения.

4. Используйте <a href="/remove-parameter">удаление параметра</a>, чтобы удалить неиспользуемый теперь параметр.



###

```
class Order {
  // ...
  public function getPrice() {
    $basePrice = $this->quantity * $this->itemPrice;
    if ($this->quantity > 100) {
      $discountLevel = 2;
    }
    else {
      $discountLevel = 1;
    }
    $finalPrice = $this->discountedPrice($basePrice, $discountLevel);
    return $finalPrice;
  }
  private function discountedPrice($basePrice, $discountLevel) {
    if ($discountLevel == 2) {
      return $basePrice * 0.1;
    }
    else {
      return $basePrice * 0.05;
    }
  }
}
```

###

```
class Order {
  // ...
  public function getPrice() {
    return $this->discountedPrice();
  }
  private function discountedPrice() {
    if ($this->getDiscountLevel() == 2) {
      return $this->getBasePrice() * 0.1;
    }
    else {
      return $this->getBasePrice() * 0.05;
    }
  }
  private function getDiscountLevel() {
    if ($this->quantity > 100) {
      return 2;
    }
    else {
      return 1;
    }
  }
  private function getBasePrice() {
    return $this->quantity * $this->itemPrice;
  }
}
```

###

Set step 1

# Рассмотрим этот рефакторинг на ещё одном примере расчёта цены заказа.

# Метод получения скидки (<code>discountedPrice</code>) сейчас практически нельзя использовать в отрыве от метода получения цены (<code>getPrice</code>), т.к. перед этим нужно получить значения всех параметров.

# А что если вообще избавиться от параметров в <code>discountedPrice</code>? Давайте попробуем это сделать.

Select:
```
    if ($this->quantity > 100) {
      $discountLevel = 2;
    }
    else {
      $discountLevel = 1;
    }

```

Set step 2

# Для начала, выделим расчет <code>discountLevel</code> в собственный метод.

Go to after "discountedPrice"

Print:
```

  private function getDiscountLevel() {
    if ($this->quantity > 100) {
      return 2;
    }
    else {
      return 1;
    }
  }
```

Set step 3

Select "$discountLevel" in body of "discountedPrice"

# Теперь мы можем использовать этот метод вместо параметра в методе расчёта скидки.

Print "$this->getDiscountLevel()"

Set step 4

# Нужда в одном из параметров отпала, можем применить <a href="/remove-parameter">удаление параметра</a>.

Select ", $discountLevel" in parameters of "discountedPrice"

Remove selected

Wait 1000ms

Select ", $discountLevel"

Remove selected

Select:
```
    if ($this->quantity > 100) {
      $discountLevel = 2;
    }
    else {
      $discountLevel = 1;
    }

```

# После этого можно очистить код от упоминаний более не используемой временной переменной.

Remove selected

#C Запускаем тестирование.

#S Отлично, все работает, продолжаем!

# Итак, один параметр ушёл. Давайте попробуем избавиться и от второго.

Select "$this->quantity * $this->itemPrice"

# Попробуем выделить расчёт базовой цены в собственный метод.

Go to after "getDiscountLevel"

Print:
```

  private function getBasePrice() {
    return $this->quantity * $this->itemPrice;
  }
```

Select "$basePrice" in body of "discountedPrice"

# Теперь используем этот метод в <code>discountedPrice</code>.

Print "$this->getBasePrice()"

# Как и прежде, теперь мы можем избавиться и от этого параметра.

Select "$basePrice" in parameters of "discountedPrice"

Remove selected

Select "discountedPrice(|||$basePrice|||)"

Wait 1000ms

Remove selected

Select:
```
    $basePrice = $this->quantity * $this->itemPrice;

```

# После этого чистим код оригинального метода.

Remove selected

Select body of "getPrice"

# ...или немного красивее:

Print "    return $this->discountedPrice();"


#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.