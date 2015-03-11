replace-parameter-with-explicit-methods:php

###

1.ru. Для каждого варианта исполнения метода создайте свой метод. Запускайте эти методы в зависимости от значения параметра в основном методе.
1.en. For each variant of the method, create a separate method. Run these methods based on the value of a parameter in the main method.
1.uk. Для кожного варіанту виконання методу створіть свій метод. Запускайте ці методи залежно від значення параметра в основному методі.

2.ru. Найдите все места, где вызывается оригинальный метод. Подставьте туда вызов одного из новых методов в зависимости от передающегося параметра.
2.en. Find all places where the original method is called. In these places, place a call for one of the new parameter-dependent variants.
2.uk. Знайдіть усі місця, де викликається оригінальний метод. Підставте туди виклик одного з нових методів залежно від параметра, що передається.

3.ru. Когда не останется ни одного вызова оригинального метода, его можно будет удалить.
3.en. When no calls to the original method remain, delete it.
3.uk. Коли не залишиться жодного виклику оригінального методу, його можна буде видалити.



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
        break;
      case Order::PERCENT_DISCOUNT:
        $this->price *= $discount;
        break;
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

#|ru| Рассмотрим данный рефакторинг на примере класса заказа.
#|en| Let's look at this technique using an order class as an example.
#|uk| Розглянемо даний рефакторинг на прикладі класу замовлення.

Select name of "applyDiscount"

#|ru| В этом классе есть метод применения скидки, который может работать как с фиксированными скидками, так и с процентными.
#|en| This class has a method for applying discounts that handle both fixed discounts and percentage-based ones.
#|uk| В цьому класі є метод застосування знижки, який може працювати як з фіксованими знижками, так і з процентними.

#|ru| Начнём рефакторинг с выделения каждого варианта исполнения в отдельный метод.
#|en| Let's start refactoring by extracting each version to a separate method.
#|uk| Почнемо рефакторинг з виділення кожного варіанту виконання в окремий метод.

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

#|ru| Теперь найдём все места, где вызывается оригинальный метод, и заменим их вызовами наших новых методов.
#|en| Now find all places where the original method is called, replacing them with calls to our new methods.
#|uk| Тепер знайдемо всі місця, де викликається оригінальний метод, і замінимо їх викликами наших нових методів.

Select "applyDiscount(Order::FIXED_DISCOUNT, "

Replace "applyFixedDiscount("

Wait 1000ms

Select "applyDiscount(Order::PERCENT_DISCOUNT, "

Replace "applyPercentDiscount("

#C|ru| Запускаем тестирование, чтобы убедиться в отсутствии ошибок.
#S Отлично, все работает!

#C|en| Let's launch autotests to check for errors in code.
#S Wonderful, it's all working!

#C|uk| Запускаємо тестування, щоб переконатися у відсутності помилок.
#S Супер, все працює.

Set step 3

#|ru| После всех замен останется удалить оригинальный метод, а также лишние теперь константы.
#|en| Once changes are complete, remove the original method and now-useless constants.
#|uk| Після всіх замін залишиться видалити оригінальний метод, а також зайві тепер константи.

Select whole "applyDiscount"

Remove selected

Wait 1000ms

Select:
```
  const FIXED_DISCOUNT = 0;
  const PERCENT_DISCOUNT = 1;


```
Remove selected

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.