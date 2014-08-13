replace-parameter-with-explicit-methods:php

###

1. Для каждого варианта исполнения метода создайте свой метод. Запускайте эти методы в зависимости от значения параметра в основном методе.

2. Найдите все места, где вызывается оригинальный метод. Подставьте туда вызов одного из новых методов в зависимости от передающегося параметра.

3. Когда не останется ни одного вызова оригинального метода, его можно будет удалить.



###

```
class Order {
  // ...
  const FIXED_DISCOUNT = 0;
  const PERCENT_DISCOUNT = 1;

  public function applyDiscount($type, $discount) {
    switch ($type) {
      case Order::FIXED_DISCOUNT:
        $this->price -= $discount;
      case Order::PERCENT_DISCOUNT:
        $this->price *= $discount;
      default:
        throw new Exception('Invalid discount type');
    }
  }
}

// Somewhere in client code
if ($weekend) {
  $order->applyDiscount(Order::FIXED_DISCOUNT, 10);
}
if (count($order->items) > 5) {
  $order->applyDiscount(Order::PERCENT_DISCOUNT, 0.2);
}
```

###

```
class Order {
  // ...
  public function applyFixedDiscount($discount) {
    $this->price -= $discount;
  }
  public function applyPercentDiscount($discount) {
    $this->price *= $discount;
  }
}

// Somewhere in client code
if ($weekend) {
  $order->applyFixedDiscount(10);
}
if (count($order->items) > 5) {
  $order->applyPercentDiscount(0.2);
}
```

###

Set step 1

Select name of "Order"

# Рассмотрим данный рефакторинг на примере класса заказа.

# В этом классе есть метод применения скидки, который может работать как с фиксированными скидками, так и с процентными.

# Начнём рефакторинг с выделения каждого варианта исполнения в отдельный метод.

Select "$this->price -= $discount;"

Wait 1000ms

Go to after "applyDiscount"

Print:
```

  public function applyFixedDiscount($discount) {
    $this->price -= $discount;
  }
```

Select "$this->price *= $discount;"

Wait 1000ms

Go to after "applyFixedDiscount"

Print:
```

  public function applyPercentDiscount($discount) {
    $this->price *= $discount;
  }
```

Set step 2

# Теперь найдём все места, где вызывается оригинальный метод и заменим их вызовами наших новых методов.

Select "applyDiscount(Order::FIXED_DISCOUNT, "

Replace "applyFixedDiscount("

Wait 1000ms

Select "applyDiscount(Order::PERCENT_DISCOUNT, "

Replace "applyPercentDiscount("

#C Запускаем тестирование, чтобы убедиться в отсутствии ошибок.

#S Отлично, все работает!

Set step 3

# После всех замен останется удалить оригинальный метод, а также лишние теперь константы.

Select whole "applyDiscount"

Remove selected

Wait 1000ms

Select:
```
  const FIXED_DISCOUNT = 0;
  const PERCENT_DISCOUNT = 1;


```
Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.