replace-parameter-with-method:java

###

1. Убедитесь, что код получения значения не использует параметров из текущего метода, т.к. они будут недоступны внутри другого метода, из-за чего перенос будет невозможен.

2. Если код получения значения сложнее, чем один вызов какого-то метода или функции, примените <a href="/extract-method">извлечение метода</a>, чтобы выделить этот код в новый метод и сделать вызов простым.

3. В коде главного метода замените все обращения к заменяемому параметру вызовами метода получения значения.

4. Используйте <a href="/remove-parameter">удаление параметра</a>, чтобы удалить неиспользуемый теперь параметр.



###

```
class Order {
  // ...
  public double getPrice() {
    int basePrice = quantity * itemPrice;
    int discountLevel;
    if (quantity > 100) {
      discountLevel = 2;
    }
    else {
      discountLevel = 1;
    }
    double finalPrice = discountedPrice(basePrice, discountLevel);
    return finalPrice;
  }
  private double discountedPrice(int basePrice, int discountLevel) {
    if (discountLevel == 2) {
      return basePrice * 0.1;
    }
    else {
      return basePrice * 0.05;
    }
  }
}
```

###

```
class Order {
  // ...
  public double getPrice() {
    return discountedPrice();
  }
  private double discountedPrice() {
    if (getDiscountLevel() == 2) {
      return getBasePrice() * 0.1;
    }
    else {
      return getBasePrice() * 0.05;
    }
  }
  private int getDiscountLevel() {
    if (quantity > 100) {
      return 2;
    }
    else {
      return 1;
    }
  }
  private double getBasePrice() {
    return quantity * itemPrice;
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
    int discountLevel;
    if (quantity > 100) {
      discountLevel = 2;
    }
    else {
      discountLevel = 1;
    }

```

Set step 2

# Для начала, выделим расчёт <code>discountLevel</code> в собственный метод.

Go to after "discountedPrice"

Print:
```

  private int getDiscountLevel() {
    if (quantity > 100) {
      return 2;
    }
    else {
      return 1;
    }
  }
```

Set step 3

Select "discountLevel" in body of "discountedPrice"

# Теперь мы можем использовать этот метод вместо параметра в методе расчёта скидки.

Print "getDiscountLevel()"

Set step 4

# Нужда в одном из параметров отпала, можем применить <a href="/remove-parameter">удаление параметра</a>.

Select ", int discountLevel"

Remove selected

Select ", discountLevel"

Wait 1000ms

Remove selected

Select:
```
    int discountLevel;
    if (quantity > 100) {
      discountLevel = 2;
    }
    else {
      discountLevel = 1;
    }

```

# После этого можно очистить код от упоминаний более не используемой временной переменной.

Remove selected

#C Запускаем компиляцию и тестирование.

#S Отлично, все работает, продолжаем!

# Итак, один параметр ушёл. Давайте попробуем избавиться и от второго.

Select "quantity * itemPrice"

# Попробуем выделить расчёт базовой цены в собственный метод.

Go to after "getDiscountLevel"

Print:
```

  private double getBasePrice() {
    return quantity * itemPrice;
  }
```

Select "basePrice" in body of "discountedPrice"

# Теперь используем этот метод в <code>discountedPrice</code>.

Print "getBasePrice()"

# Как и прежде, теперь мы можем избавиться и от этого параметра.

Select "int basePrice" in parameters of "discountedPrice"

Remove selected

Wait 1000ms

Select "discountedPrice(|||basePrice|||)"

Remove selected

Select:
```
    int basePrice = quantity * itemPrice;

```

# После этого чистим код оригинального метода…

Remove selected

Select body of "getPrice"

# ...или немного красивее:

Print "    return discountedPrice();"


#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.