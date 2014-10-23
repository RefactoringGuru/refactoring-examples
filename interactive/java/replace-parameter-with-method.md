replace-parameter-with-method:java

###

1.ru. Убедитесь, что код получения значения не использует параметров из текущего метода, т.к. они будут недоступны внутри другого метода, из-за чего перенос будет невозможен.
1.uk. Переконайтеся, що код отримання значення не використовує параметрів з поточного методу, оскільки вони будуть недоступні усередині іншого методу, через що перенесення буде неможливе.

2.ru. Если код получения значения сложнее, чем один вызов какого-то метода или функции, примените <a href="/extract-method">извлечение метода</a>, чтобы выделить этот код в новый метод и сделать вызов простым.
2.uk. Якщо код отримання значення складніший, ніж один виклик якогось методу або функції, застосуйте <a href="/extract-method">відокремлення методу</a>, щоб виділити цей код в новий метод і зробити виклик простішим.

3.ru. В коде главного метода замените все обращения к заменяемому параметру вызовами метода получения значения.
3.uk. У коді головного методу замініть усі звернення до замінюваного параметра викликами методу отримання значення.

4.ru. Используйте <a href="/remove-parameter">удаление параметра</a>, чтобы удалить неиспользуемый теперь параметр.
4.uk. Використайте <a href="/remove-parameter">видалення параметра</a>, щоб видалити невживаний тепер параметр.



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

#|ru| Рассмотрим этот рефакторинг на ещё одном примере расчёта цены заказа.
#|uk| Розглянемо цей рефакторинг на ще одному прикладі розрахунку ціни замовлення.

#|ru| Метод получения скидки (<code>discountedPrice</code>) сейчас практически нельзя использовать в отрыве от метода получения цены (<code>getPrice</code>), т.к. перед этим нужно получить значения всех параметров.
#|uk| Метод отримання знижки (<code>discountedPrice</code>) зараз практично не можна використовувати у відриві від методу отримання ціни (<code>getPrice</code>), так як перед цим потрібно отримати значення всіх параметрів.

#|ru| А что если вообще избавиться от параметров в <code>discountedPrice</code>? Давайте попробуем это сделать.
#|uk| А що якщо взагалі позбутися параметрів в <code>discountedPrice</code>? Давайте спробуємо це зробити.

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

#|ru| Для начала, выделим расчёт <code>discountLevel</code> в собственный метод.
#|uk| Для початку, виділимо розрахунок <code>discountLevel</code> у власний метод.

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

#|ru| Теперь мы можем использовать этот метод вместо параметра в методе расчёта скидки.
#|uk| Тепер ми можемо використовувати цей метод замість параметра в методі розрахунку знижки.

Print "getDiscountLevel()"

Set step 4

#|ru| Нужда в одном из параметров отпала, можем применить <a href="/remove-parameter">удаление параметра</a>.
#|uk| Необхідність в одному з параметрів відпала, можемо застосувати <a href="/remove-parameter">видалення параметра</a>.

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

#|ru| После этого можно очистить код от упоминаний более не используемой временной переменной.
#|uk| Після цього можна очистити код від згадок про не використовувану тимчасову змінну.

Remove selected

#C|ru| Запускаем компиляцию и тестирование.
#S Отлично, все работает, продолжаем!

#C|uk| Запускаємо компіляцію і тестування.
#S Супер, все працює, продовжуємо.

#|ru| Итак, один параметр ушёл. Давайте попробуем избавиться и от второго.
#|uk| Отже, один параметр пішов. Давайте спробуємо позбутися і від другого.

Select "quantity * itemPrice"

#|ru| Попробуем выделить расчёт базовой цены в собственный метод.
#|uk| Спробуємо виділити розрахунок базової ціни у власний метод.

Go to after "getDiscountLevel"

Print:
```

  private double getBasePrice() {
    return quantity * itemPrice;
  }
```

Select "basePrice" in body of "discountedPrice"

#|ru| Теперь используем этот метод в <code>discountedPrice</code>.
#|uk| Тепер використовуємо цей метод в <code>discountedPrice</code>.

Print "getBasePrice()"

#|ru| Как и прежде, теперь мы можем избавиться и от этого параметра.
#|uk| Як і колись, тепер ми можемо позбутися і від цього параметра.

Select "int basePrice" in parameters of "discountedPrice"

Remove selected

Wait 1000ms

Select "discountedPrice(|||basePrice|||)"

Remove selected

Select:
```
    int basePrice = quantity * itemPrice;

```

#|ru| После этого чистим код оригинального метода…
#|uk| Після цього чистимо код оригінального методу...

Remove selected

Select body of "getPrice"

#|ru| ...или немного красивее:
#|uk| ...або трохи красивіше:

Print "    return discountedPrice();"


#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.