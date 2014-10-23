replace-parameter-with-explicit-methods:java

###

1.ru. Для каждого варианта исполнения метода создайте свой метод. Запускайте эти методы в зависимости от значения параметра в основном методе.
1.uk. Для кожного варіанту виконання методу створіть свій метод. Запускайте ці методи залежно від значення параметра в основному методі.

2.ru. Найдите все места, где вызывается оригинальный метод. Подставьте туда вызов одного из новых методов в зависимости от передающегося параметра.
2.uk. Знайдіть усі місця, де викликається оригінальний метод. Підставте туди виклик одного з нових методів залежно від параметра, що передається.

3.ru. Когда не останется ни одного вызова оригинального метода, его можно будет удалить.
3.uk. Коли не залишиться жодного виклику оригінального методу, його можна буде видалити.



###

```
class Order {
  // ...
  public static final int FIXED_DISCOUNT = 0;
  public static final int PERCENT_DISCOUNT = 1;

  public double applyDiscount(int type, double discount) {
    switch (type) {
      case FIXED_DISCOUNT:
        price -= discount;
      case PERCENT_DISCOUNT:
        price *= discount;
      default:
        throw new IllegalArgumentException('Invalid discount type');
    }
  }
}

// Somewhere in client code
if (weekend) {
  order.applyDiscount(Order.FIXED_DISCOUNT, 10);
}
if (order.items.size() > 5) {
  order.applyDiscount(Order.PERCENT_DISCOUNT, 0.2);
}
```

###

```
class Order {
  // ...
  public double applyFixedDiscount(double discount) {
    price -= discount;
  }
  public double applyPercentDiscount(double discount) {
    price *= discount;
  }
}

// Somewhere in client code
if (weekend) {
  order.applyFixedDiscount(10);
}
if (order.items.size() > 5) {
  order.applyPercentDiscount(0.2);
}
```

###

Set step 1

Select name of "Order"

#|ru| Рассмотрим данный рефакторинг на примере класса заказа.
#|uk| Розглянемо даний рефакторинг на прикладі класу замовлення.

#|ru| В этом классе есть метод применения скидки, который может работать как с фиксированными скидками, так и с процентными.
#|uk| В цьому класі є метод застосування знижки, який може працювати як з фіксованими знижками, так і з процентними.

#|ru| Начнём рефакторинг с выделения каждого варианта исполнения в отдельный метод.
#|uk| Почнемо рефакторинг з виділення кожного варіанту виконання в окремий метод.

Select "price -= discount;"

Wait 1000ms

Go to after "applyDiscount"

Print:
```

  public double applyFixedDiscount(double discount) {
    price -= discount;
  }
```

Select "price *= discount;"

Wait 1000ms

Go to after "applyFixedDiscount"

Print:
```

  public double applyPercentDiscount(double discount) {
    price *= discount;
  }
```

Set step 2

#|ru| Теперь найдём все места, где вызывается оригинальный метод, и заменим их вызовами наших новых методов.
#|uk| Тепер знайдемо всі місця, де викликається оригінальний метод, і замінимо їх викликами наших нових методів.

Select "applyDiscount(Order.FIXED_DISCOUNT, "

Replace "applyFixedDiscount("

Wait 1000ms

Select "applyDiscount(Order.PERCENT_DISCOUNT, "

Replace "applyPercentDiscount("

#C|ru| Запускаем компиляцию и тестирование, чтобы убедиться в отсутствии ошибок.
#S Отлично, все работает!

#C|uk| Запускаємо компіляцію і тестування, щоб переконатися у відсутності помилок.
#S Супер, все працює.

Set step 3

#|ru| После всех замен останется удалить оригинальный метод, а также лишние теперь константы.
#|uk| Після всіх замін залишиться видалити оригінальний метод, а також зайві тепер константи.

Select whole "applyDiscount"

Remove selected

Wait 1000ms

Select:
```
  public static final int FIXED_DISCOUNT = 0;
  public static final int PERCENT_DISCOUNT = 1;


```
Remove selected

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.