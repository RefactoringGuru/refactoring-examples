replace-parameter-with-method-call:csharp

###

1.ru. Убедитесь, что код получения значения не использует параметров из текущего метода, так как они будут недоступны внутри другого метода, из-за чего перенос будет невозможен.
1.en. Make sure that the value-getting code does not use parameters from the current method, since they will be unavailable from inside another method. If so, moving the code is not possible.
1.uk. Переконайтеся, що код отримання значення не використовує параметрів з поточного методу, оскільки вони будуть недоступні усередині іншого методу, через що перенесення буде неможливе.

2.ru. Если код получения значения сложнее, чем один вызов какого-то метода или функции, примените <a href="/extract-method">извлечение метода</a>, чтобы выделить этот код в новый метод и сделать вызов простым.
2.en. If the relevant code is more complicated than a single method or function call, use <a href="/extract-method">Extract Method</a> to isolate this code in a new method and make the call simple.
2.uk. Якщо код отримання значення складніший, ніж один виклик якогось методу або функції, застосуйте <a href="/extract-method">відокремлення методу</a>, щоб виділити цей код в новий метод і зробити виклик простішим.

3.ru. В коде главного метода замените все обращения к заменяемому параметру вызовами метода получения значения.
3.en. In the code of the main method, replace all references to the parameter being replaced with calls to the method that gets the value.
3.uk. У коді головного методу замініть усі звернення до замінюваного параметра викликами методу отримання значення.

4.ru. Используйте <a href="/remove-parameter">удаление параметра</a>, чтобы удалить неиспользуемый теперь параметр.
4.en. Use <a href="/remove-parameter">Remove Parameter</a> to eliminate the now-unused parameter.
4.uk. Використайте <a href="/remove-parameter">видалення параметра</a>, щоб видалити невживаний тепер параметр.



###

```
public class Order
{
  // ...
  public double GetPrice()
  {
    int basePrice = Quantity * ItemPrice;
    int discountLevel;

    if (Quantity > 100)
      discountLevel = 2;
    else
      discountLevel = 1;

    double finalPrice = DiscountedPrice(basePrice, discountLevel);
    return finalPrice;
  }
  private double DiscountedPrice(int basePrice, int discountLevel)
  {
    if (discountLevel == 2)
      return basePrice * 0.1;
    else
      return basePrice * 0.05;
  }
}
```

###

```
public class Order
{
  // ...
  public double GetPrice()
  {
    return DiscountedPrice();
  }
  private double DiscountedPrice()
  {
    if (GetDiscountLevel() == 2)
      return GetBasePrice() * 0.1;
    else
      return GetBasePrice() * 0.05;
  }
  private int GetDiscountLevel()
  {
    if (Quantity > 100)
      return 2;
    else
      return 1;
  }
  private double GetBasePrice()
  {
    return Quantity * ItemPrice;
  }
}
```

###

Set step 1

#|ru| Рассмотрим этот рефакторинг на ещё одном примере расчёта цены заказа.
#|en| Let's look at this refactoring using yet another order price example.
#|uk| Розглянемо цей рефакторинг на ще одному прикладі розрахунку ціни замовлення.

Select name of "GetPrice"
+Select name of "DiscountedPrice"

#|ru|^ Метод получения скидки <code>DiscountedPrice()</code> сейчас практически нельзя использовать в отрыве от метода получения цены <code>GetPrice()</code>, так как перед этим нужно получить значения всех параметров.
#|en|^ The method for getting the discount <code>DiscountedPrice()</code> is currently nearly impossible to use separately from the method for getting the price <code>GetPrice()</code>, since you must get the values of all parameters prior to it.
#|uk|^ Метод отримання знижки <code>DiscountedPrice()</code> зараз практично не можна використовувати у відриві від методу отримання ціни <code>GetPrice()</code>, бо перед цим потрібно отримати значення всіх параметрів.

Select parameters of "DiscountedPrice"

#|ru| А что если вообще избавиться от параметров в <code>DiscountedPrice()</code>? Давайте попробуем это сделать.
#|en| But what if we eliminate all parameters in <code>DiscountedPrice()</code>? Let's try.
#|uk| А що якщо взагалі позбутися параметрів в <code>DiscountedPrice()</code>? Давайте спробуємо це зробити.

Select:
```
    int discountLevel;

    if (Quantity > 100)
      discountLevel = 2;
    else
      discountLevel = 1;

```

Set step 2

#|ru| Для начала, выделим расчёт параметра <code>discountLevel</code> в собственный метод.
#|en| To start, we extract <code>discountLevel</code> to its own method.
#|uk| Для початку, виділимо розрахунок параметра <code>discountLevel</code> у власний метод.

Go to after "DiscountedPrice"

Print:
```

  private int GetDiscountLevel()
  {
    if (Quantity > 100)
      return 2;
    else
      return 1;
  }
```

Set step 3

Select "discountLevel" in body of "DiscountedPrice"

#|ru| Теперь мы можем использовать этот метод вместо параметра в методе расчёта скидки.
#|en| Now we can use this method instead of this parameter in the discount calculation method.
#|uk| Тепер ми можемо використовувати цей метод замість параметра в методі розрахунку знижки.

Print "GetDiscountLevel()"

Set step 4

Select ", int discountLevel" in parameters of "DiscountedPrice"

#|ru| Нужда в одном из параметров отпала, можем применить <a href="/remove-parameter">удаление параметра</a>.
#|en| One of the parameters is no longer needed so we can use <a href="/remove-parameter">Remove Parameter</a>
#|uk| Необхідність в одному з параметрів відпала, можемо застосувати <a href="/remove-parameter">видалення параметра</a>.

Remove selected

Select ", discountLevel"

Wait 500ms

Remove selected

Wait 500ms

Select:
```
    int discountLevel;

    if (Quantity > 100)
      discountLevel = 2;
    else
      discountLevel = 1;

```

#|ru| После этого можно удалить не нужный более расчёт параметра.
#|en| We can then remove parameter calculation, which is no longer used.
#|uk| Після цього можна видалити непотрібний більш розрахунок параметра.

Remove selected

#C|ru| Запускаем компиляцию и тестирование.
#S Отлично, все работает, продолжаем!

#C|en| Let's run the compiler and auto-tests.
#S Everything is good! Let's continue.

#C|uk| Запускаємо компіляцію і тестування.
#S Супер, все працює, продовжуємо.

Select parameters of "DiscountedPrice"

#|ru| Итак, один параметр ушёл. Давайте попробуем избавиться и от второго.
#|en| One parameter, one more to go…
#|uk| Отже, один параметр пішов. Давайте спробуємо позбутися і від другого.

Select "Quantity * ItemPrice"

#|ru| Попробуем выделить расчёт базовой цены в собственный метод.
#|en| Let's extract the base price calculation to its own method.
#|uk| Спробуємо виділити розрахунок базової ціни у власний метод.

Go to after "GetDiscountLevel"

Print:
```

  private double GetBasePrice()
  {
    return Quantity * ItemPrice;
  }
```

Select "basePrice" in body of "DiscountedPrice"

#|ru| Теперь используем этот метод в <code>DiscountedPrice</code>.
#|en| Now use this method in <code>DiscountedPrice</code>.
#|uk| Тепер використовуємо цей метод в <code>DiscountedPrice</code>.

Print "GetBasePrice()"

Wait 250ms

Select "int basePrice" in parameters of "DiscountedPrice"

#|ru| После чего мы можем избавиться и от этого параметра.
#|en| As before, we can now get rid of this parameter as well.
#|uk| Після чого ми можемо позбутися і від цього параметра.

Remove selected

Select "DiscountedPrice(|||basePrice|||)"

Wait 500ms

Remove selected

Wait 500ms

Select:
```
    int basePrice = Quantity * ItemPrice;


```

#|ru| Очищаем код метода получения цены…
#|en| Then clean up the code of the method for getting the price…
#|uk| Очищаємо код методу отримання ціни…

Remove selected

Select body of "GetPrice"

#|ru| …или немного красивее:
#|en| …or if we make it a bit more pretty:
#|uk| …або трохи красивіше:

Print "    return DiscountedPrice();"


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