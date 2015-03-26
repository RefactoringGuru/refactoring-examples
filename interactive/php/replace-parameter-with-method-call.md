replace-parameter-with-method-call:php

###

1.ru. Убедитесь, что код получения значения не использует параметров из текущего метода, т.к. они будут недоступны внутри другого метода, из-за чего перенос будет невозможен.
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

#|ru| Рассмотрим этот рефакторинг на ещё одном примере расчёта цены заказа.
#|en| Let's look at this refactoring using yet another order price example.
#|uk| Розглянемо цей рефакторинг на ще одному прикладі розрахунку ціни замовлення.

Select name of "getPrice"
+Select name of "discountedPrice"

#|ru|^ Метод получения скидки (<code>discountedPrice</code>) сейчас практически нельзя использовать в отрыве от метода получения цены (<code>getPrice</code>), т.к. перед этим нужно получить значения всех параметров.
#|en|^ The method for getting the discount (<code>discountedPrice</code>) is currently nearly impossible to use separately from the method for getting the price (<code>getPrice</code>), since you must get the values of all parameters prior to it.
#|uk|^ Метод отримання знижки (<code>discountedPrice</code>) зараз практично не можна використовувати у відриві від методу отримання ціни (<code>getPrice</code>), так як перед цим потрібно отримати значення всіх параметрів.

Select parameters of "discountedPrice"

#|ru| А что если вообще избавиться от параметров в <code>discountedPrice</code>? Давайте попробуем это сделать.
#|en| But what if we eliminate all parameters in <code>discountedPrice</code>? Let's try.
#|uk| А що якщо взагалі позбутися параметрів в <code>discountedPrice</code>? Давайте спробуємо це зробити.

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

#|ru| Для начала, выделим расчёт параметра <code>discountLevel</code> в собственный метод.
#|en| To start, we extract <code>discountLevel</code> to its own method.
#|uk| Для початку, виділимо розрахунок параметра <code>discountLevel</code> у власний метод.

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

#|ru| Теперь мы можем использовать этот метод вместо параметра в методе расчёта скидки.
#|en| Now we can use this method instead of this parameter in the discount calculation method.
#|uk| Тепер ми можемо використовувати цей метод замість параметра в методі розрахунку знижки.

Print "$this->getDiscountLevel()"

Set step 4

Select ", $discountLevel" in parameters of "discountedPrice"

#|ru| Нужда в одном из параметров отпала, можем применить <a href="/remove-parameter">удаление параметра</a>.
#|en| One of the parameters is no longer needed so we can use <a href="/remove-parameter">Remove Parameter</a>
#|uk| Необхідність в одному з параметрів відпала, можемо застосувати <a href="/remove-parameter">видалення параметра</a>.

Remove selected

Wait 500ms

Select ", $discountLevel"

Wait 500ms

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

#|ru| После этого можно удалить не нужный более расчёт параметра.
#|en| We can then remove parameter calculation, which is no longer used.
#|uk| Після цього можна видалити непотрібний більш розрахунок параметра.

Remove selected

#C|ru| Запускаем тестирование.
#S Отлично, все работает, продолжаем!

#C|en| Let's start testing.
#S Everything is good! Let's continue.

#C|uk| Запускаємо тестування.
#S Супер, все працює, продовжуємо.

Select parameters of "discountedPrice"

#|ru| Итак, один параметр ушёл. Давайте попробуем избавиться и от второго.
#|en| One parameter, one more to go…
#|uk| Отже, один параметр пішов. Давайте спробуємо позбутися і від другого.

Select "$this->quantity * $this->itemPrice"

#|ru| Попробуем выделить расчёт базовой цены в собственный метод.
#|en| Let's extract the base price calculation to its own method.
#|uk| Спробуємо виділити розрахунок базової ціни у власний метод.

Go to after "getDiscountLevel"

Print:
```

  private function getBasePrice() {
    return $this->quantity * $this->itemPrice;
  }
```

Select "$basePrice" in body of "discountedPrice"

#|ru| Теперь используем этот метод в <code>discountedPrice</code>.
#|en| Now use this method in <code>discountedPrice</code>.
#|uk| Тепер використовуємо цей метод в <code>discountedPrice</code>.

Print "$this->getBasePrice()"

Wait 250ms

Select "$basePrice" in parameters of "discountedPrice"

#|ru| После чего мы можем избавиться и от этого параметра.
#|en| As before, we can now get rid of this parameter as well.
#|uk| Після чого ми можемо позбутися і від цього параметра.

Remove selected

Select "discountedPrice(|||$basePrice|||)"

Wait 500ms

Remove selected

Wait 500ms

Select:
```
    $basePrice = $this->quantity * $this->itemPrice;

```

#|ru| Очищаем код метода получения цены…
#|en| Then clean up the code of the method for getting the price…
#|uk| Очищаємо код методу отримання ціни…

Remove selected

Select body of "getPrice"

#|ru| …или немного красивее:
#|en| …or if we make it a bit more pretty:
#|uk| …або трохи красивіше:

Print "    return $this->discountedPrice();"


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