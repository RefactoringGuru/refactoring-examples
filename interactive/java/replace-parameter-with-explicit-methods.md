replace-parameter-with-explicit-methods:java

###

1. Для каждого варианта исполнения метода создайте свой метод. Запускайте эти методы в зависимости от значения параметра в основном методе.

2. Найдите все места, где вызывается оригинальный метод. Подставьте туда вызов одного из новых методов в зависимости от передающегося параметра.

3. Когда не останется ни одного вызова оригинального метода, его можно будет удалить.



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

# Рассмотрим данный рефакторинг на примере класса заказа.

# В этом классе есть метод применения скидки, который может работать как с фиксированными скидками, так и с процентными.

# Начнём рефакторинг с выделения каждого варианта исполнения в отдельный метод.

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

# Теперь найдём все места, где вызывается оригинальный метод и заменим их вызовами наших новых методов.

Select "applyDiscount(Order.FIXED_DISCOUNT, "

Replace "applyFixedDiscount("

Wait 1000ms

Select "applyDiscount(Order.PERCENT_DISCOUNT, "

Replace "applyPercentDiscount("

#C Запускаем компиляцию и тестирование, чтобы убедиться в отсутствии ошибок.

#S Отлично, все работает!

Set step 3

# После всех замен останется удалить оригинальный метод, а также лишние теперь константы.

Select whole "applyDiscount"

Remove selected

Wait 1000ms

Select:
```
  public static final int FIXED_DISCOUNT = 0;
  public static final int PERCENT_DISCOUNT = 1;


```
Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.