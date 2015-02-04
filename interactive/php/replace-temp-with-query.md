replace-temp-with-query:php

###

1.ru. Убедитесь, что переменной в пределах метода присваивается значение только один раз.
1.en. Make sure that a value is assigned to the variable once and only once within the method.
1.uk. Переконайтеся, що змінній в межах методу привласнюється значення тільки один раз.

2.ru. Используйте <b>извлечение метода</b> для того, чтобы переместить интересующее выражение в новый метод.
2.en. Use <b>extract method</b> to move the expression in question to the new method.
2.uk. Використовуйте <b>витяг методу</b> для того, щоб перемістити цікавить вираження в новий метод.

3.ru. Замените использование переменной вызовом вашего нового метода.
3.en. Replace the variable with a query to your new method.
3.uk. Замініть використання змінної викликом вашого нового методу.



###

```
class Product {
  // ...
  function getPrice() {
    $basePrice = $this->quantity * $this->itemPrice;
    if ($basePrice > 1000) {
      $discountFactor = 0.95;
    }
    else {
      $discountFactor = 0.98;
    }
    return $basePrice * $discountFactor;
  }
}
```

###

```
class Product {
  // ...
  function getPrice() {
    return $this->basePrice() * $this->discountFactor();
  }
  private function basePrice() {
    return $this->quantity * $this->itemPrice;
  }
  private function discountFactor() {
    if ($this->basePrice() > 1000) {
      return 0.95;
    }
    else {
      return 0.98;
    }
  }
}
```

###

Set step 1

#|ru| Рассмотрим <i>Замену переменной вызовом метода</i> на примере этого простого метода.
#|en| Let's look at <i>Replace Temp with Query</i> using a simple method as an example.
#|uk| Розглянемо <i>Заміну змінної викликом методу<i> на прикладі цього простого методу.

Select "|||$basePrice||| = "
+Select "|||$discountFactor||| ="

#|ru| Давайте по очереди заменим переменные <code>basePrice</code> и <code>discountFactor</code> вызовами соответствующих методов.
#|en| Replace the variables <code>basePrice</code> and <code>discountFactor</code> one by one with calls to the respective methods.
#|uk| Давайте по черзі замінимо змінні <code>basePrice</code> і <code>discountFactor</code> викликами відповідних методів.

Select "$basePrice = "

#|ru| Для начала нужно убедиться, что переменным в пределах метода значение присваивается только один раз.
#|en| First, make sure that there is just one value assignment to the variable within the method.
#|uk| Для початку потрібно переконатися, що змінним в межах методу значення присвоюється тільки одноразово.

#|ru| В данном случае всё так и есть, можем продолжать.
#|en| This is all true in our case, so we can continue.
#|uk| В даному випадку все так і є, можемо продовжувати.

Set step 2

Select "$basePrice = $this->quantity * $this->itemPrice"

#|ru| Итак, на втором шаге, создадим метод <code>basePrice()</code> и перенесём в него выражение, формирующее переменную <code>basePrice</code>.
#|en| For the second step, we create a <code>basePrice()</code> method and move the expression forming the <code>basePrice</code> variable to it.
#|uk| Отже, на другому кроці, створимо метод <code>basePrice()</code> і перенесемо в нього вираз, яке формує змінну <code>basePrice</code>.

Go to the end of "Product"

Print:
```

  private function basePrice() {
    return $this->quantity * $this->itemPrice;
  }
```

Select "$this->quantity * $this->itemPrice" in "getPrice"

#|ru| Теперь вызов метода можно использовать вместо первоначального выражения. Таким образом, у нас теперь есть новый метод, а весь старый код все ещё в рабочем состоянии.
#|en| Now we can use a method call instead of the initial expression. Thus, we now have a new method and all of the old code still works.
#|uk| Тепер виклик методу можна використовувати замість початкового виразу. Таким чином, у нас тепер є новий метод, а весь старий код все ще в робочому стані.

Print "$this->basePrice()"

Set step 3

Select "(|||$basePrice||| >"

#|ru| Самое время начать заменять переменную непосредственным вызовом метода.
#|en| Now is the perfect time to replace the variable with a direct method call.
#|uk| Саме час почати замінювати змінну безпосереднім викликом методу.

#|ru| Заменим первую переменную, а затем запустим авто-тесты, чтобы убедиться, что ничего не сломалось.
#|en| Replace the first variable and then run autotests to make sure that nothing is broken.
#|uk| Замінимо першу змінну, а потім запустимо авто-тести, щоб переконатися, що нічого не зламалося.

Print "$this->basePrice()"

#C|ru| Запускаем тестирование.
#S Всё отлично, можем продолжать!

#C|en| Let's start testing.
#S Everything is OK! We can keep going.

#C|uk| Запускаємо тестування.
#S Все добре, можна продовжувати.

Select "return |||$basePrice|||"

#|ru| Выполним следующую замену.
#|en| Perform the next replacement.
#|uk| Виконаємо наступну заміну.

Print "$this->basePrice()"

#C|ru| Запускаем тестирование.
#S Всё отлично, можем продолжать!

#C|en| Let's start testing.
#S Everything is OK! We can keep going.

#C|uk| Запускаємо тестування.
#S Все добре, можна продовжувати.

Select:
```
    $basePrice = $this->basePrice();

```

#|ru| Прошлая замена была последней, поэтому мы можем удалить объявление переменной.
#|en| The previous replacement was the last one, so we can remove the variable declaration.
#|uk| Минула заміна була останньою, тому ми можемо видалити оголошення змінної.

Remove selected

Select "$discountFactor"

#|ru| С первой переменной покончено, и мы можем повторить все действия для выделения <code>discountFactor</code>.
#|en| The first variable is done. We can repeat all this to extract <code>discountFactor</code>.
#|uk| З першою змінною закінчили, і тепер ми можемо повторити всі дії для виділення <code>discountFactor</code>.

Go to the end of "Product"

#|ru| Создаём новый метод…
#|en| Create a new method…
#|uk| Створюємо новий метод…

Print:
```

  private function discountFactor() {
    if ($this->basePrice() > 1000) {
      return 0.95;
    }
    else {
      return 0.98;
    }
  }
```

Select in "getPrice":
```
    if ($this->basePrice() > 1000) {
      $discountFactor = 0.95;
    }
    else {
      $discountFactor = 0.98;
    }
```

#|ru| …используем его для инициализации переменной и удаляем лишний теперь код…
#|en| …use it to initialize the variable and remove old code…
#|uk| …використовуємо його для ініціалізації змінної і видаляємо зайвий тепер код…

Print "    $discountFactor = $this->discountFactor();"

#|ru| Обратите внимание на то, как трудно было бы выделить <code>discountFactor</code> без предварительной замены <code>basePrice</code> вызовом метода.
#|en| Note how difficult it would have been to extract <code>discountFactor</code> if we had not first replaced <code>basePrice</code> with a method call.
#|uk| Зверніть увагу на те, як важко було б виділити <code>discountFactor</code> без попередньої заміни <code>basePrice</code> викликом методу.

#|ru| В итоге приходим к следующему виду метода <code>getPrice()</code>.
#|en| Ultimately the <code>getPrice()</code> method comes to look as follows.
#|uk| У підсумку приходимо до наступного вигляду методу <code>getPrice()</code>.

Select:
```
    $discountFactor = $this->discountFactor();

```

Remove selected

Select "$discountFactor" in "getPrice"

Replace "$this->discountFactor()"

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