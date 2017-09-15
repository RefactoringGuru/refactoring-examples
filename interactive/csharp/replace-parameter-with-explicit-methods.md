replace-parameter-with-explicit-methods:csharp

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
public class Order
{
  // ...
  public const int FIXED_DISCOUNT = 0,
                   PERCENT_DISCOUNT = 1;

  public void ApplyDiscount(int type, double discount)
  {
    switch (type)
    {
      case FIXED_DISCOUNT:
        Price -= discount;
        break;
      case PERCENT_DISCOUNT:
        Price *= discount;
        break;
      default:
        throw new Exception("Invalid discount type");
    }
  }
}

// Somewhere in client code
if (weekend)
{
  order.ApplyDiscount(Order.FIXED_DISCOUNT, 10);
}
if (order.Items.Count > 5)
{
  order.ApplyDiscount(Order.PERCENT_DISCOUNT, 0.2);
}
```

###

```
public class Order
{
  // ...
  public void ApplyFixedDiscount(double discount)
  {
    Price -= discount;
  }
  public void ApplyPercentDiscount(double discount)
  {
    Price *= discount;
  }
}

// Somewhere in client code
if (weekend)
{
  order.ApplyFixedDiscount(10);
}
if (order.Items.Count > 5)
{
  order.ApplyPercentDiscount(0.2);
}
```

###

Set step 1

Select name of "Order"

#|ru| Рассмотрим данный рефакторинг на примере класса заказа.
#|en| Let's look at this technique using an order class as an example.
#|uk| Розглянемо даний рефакторинг на прикладі класу замовлення.

Select name of "ApplyDiscount"

#|ru| В этом классе есть метод применения скидки, который может работать как с фиксированными скидками, так и с процентными.
#|en| This class has a method for applying discounts that handle both fixed discounts and percentage-based ones.
#|uk| В цьому класі є метод застосування знижки, який може працювати як з фіксованими знижками, так і з процентними.

#|ru| Начнём рефакторинг с выделения каждого варианта исполнения в отдельный метод.
#|en| Let's start refactoring by extracting each version to a separate method.
#|uk| Почнемо рефакторинг з виділення кожного варіанту виконання в окремий метод.

Select "Price -= discount;"

Wait 1000ms

Go to after "ApplyDiscount"

Print:
```

  public void ApplyFixedDiscount(double discount)
  {
    Price -= discount;
  }
```

Select "Price *= discount;"

Wait 1000ms

Go to after "ApplyFixedDiscount"

Print:
```

  public void ApplyPercentDiscount(double discount)
  {
    Price *= discount;
  }
```

Set step 2

#|ru| Теперь найдём все места, где вызывается оригинальный метод, и заменим их вызовами наших новых методов.
#|en| Now find all places where the original method is called, replacing them with calls to our new methods.
#|uk| Тепер знайдемо всі місця, де викликається оригінальний метод, і замінимо їх викликами наших нових методів.

Select "ApplyDiscount(Order.FIXED_DISCOUNT, "

Replace "ApplyFixedDiscount("

Wait 1000ms

Select "ApplyDiscount(Order.PERCENT_DISCOUNT, "

Replace "ApplyPercentDiscount("

#C|ru| Запускаем компиляцию и тестирование, чтобы убедиться в отсутствии ошибок.
#S Отлично, все работает!

#C|en| Let's compile and test to check for errors in code.
#S Wonderful, it's all working!

#C|uk| Запускаємо компіляцію і тестування, щоб переконатися у відсутності помилок.
#S Супер, все працює.

Set step 3

#|ru| После всех замен останется удалить оригинальный метод, а также лишние теперь константы.
#|en| Once changes are complete, remove the original method and now-useless constants.
#|uk| Після всіх замін залишиться видалити оригінальний метод, а також зайві тепер константи.

Select whole "ApplyDiscount"

Wait 250ms

Remove selected

Wait 1000ms

Select:
```
  public const int FIXED_DISCOUNT = 0,
                   PERCENT_DISCOUNT = 1;


```

Wait 250ms

Remove selected

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.