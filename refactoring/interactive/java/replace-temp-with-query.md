replace-temp-with-query:java

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
  public double getPrice() {
    int basePrice = quantity * itemPrice;
    double discountFactor;
    if (basePrice > 1000) {
      discountFactor = 0.95;
    }
    else {
      discountFactor = 0.98;
    }
    return basePrice * discountFactor;
  }
}
```

###

```
class Product {
  // ...
  public double getPrice() {
    return basePrice() * discountFactor();
  }
  private int basePrice() {
    return quantity * itemPrice;
  }
  private double discountFactor() {
    if (basePrice() > 1000) {
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

Select "int |||basePrice|||"
+Select "double |||discountFactor|||"

#|ru| Давайте по очереди заменим переменные <code>basePrice</code> и <code>discountFactor</code> вызовами соответствующих методов.
#|en| Replace the variables <code>basePrice</code> and <code>discountFactor</code> one by one with calls to the respective methods.
#|uk| Давайте по черзі замінимо змінні <code>basePrice</code> і <code>discountFactor</code> викликами відповідних методів.

#|ru| Для начала нужно убедиться, что переменным в пределах метода значение присваивается только один раз.
#|en| First, make sure that there is just one value assignment to the variable within the method.
#|uk| Для початку потрібно переконатися, що змінним в межах методу значення присвоюється тільки одноразово.

#|ru| В данном случае это и так видно, но чтобы обезопасить себя, можно объявить эти переменные с ключевым словом <code>final</code>. В таком случае компилятор укажет все места, где переменным пытаются повторно присвоить значения.
#|en| That is already apparent here, but to be safe, we can declare these variables with the keyword <code>final</code>. In this case, the compiler will flag all places where attempts are made to re-assign values to variables.
#|uk| В даному випадку це і так видно, але щоб переконатися в цьому напевно, можна оголосити ці змінні з ключовим словом <code>final</code>. В такому випадку компілятор вкаже всі місця, де змінним намагаються повторно привласнити значення.

Go to "|||int basePrice"

Print "final "

Wait 500ms

Go to "|||double discountFactor"

Print "final "

#C|ru| Давайте запустим компиляцию и удостоверимся, что все хорошо.
#S <b>Все отлично, можем продолжать!</b><br/><br/>Имейте в виду, эта проверка очень важна. При возникновении проблем на этом шаге, следует воздержаться от проведения данного рефакторинга.

#C|en| Compile and verify that nothing has gone astray.
#S <b>Everything's A-OK! We can keep going.</b><br/><br/>Keep in mind that this check is very important! If issues arise during this step, you should refrain from using this refactoring technique.

#C|uk| Давайте запустимо компіляцію і переконався, що все добре.
#S <b>Все добре, можемо продовжувати!</b><br/><br/>Майте на увазі, ця перевірка дуже важлива. При виникненні проблем на цьому кроці, слід утриматися від проведення даного рефакторинга.

Set step 2

Select "basePrice = quantity * itemPrice"

#|ru| Итак, на втором шаге, создадим метод <code>basePrice()</code> и перенесём в него выражение, формирующее переменную <code>basePrice</code>.
#|en| For the second step, we create a <code>basePrice()</code> method and move the expression forming the <code>basePrice</code> variable to it.
#|uk| Отже, на другому кроці, створимо метод <code>basePrice()</code> і перенесемо в нього вираз, яке формує змінну <code>basePrice</code>.

Go to the end of "Product"

Print:
```

  private int basePrice() {
    return quantity * itemPrice;
  }
```

Select "basePrice = |||quantity * itemPrice|||"

#|ru| Теперь вызов метода можно использовать вместо первоначального выражения. Таким образом, у нас теперь есть новый метод, а весь старый код все ещё в рабочем состоянии.
#|en| Now we can use a method call instead of the initial expression. Thus, we now have a new method and all of the old code still works.
#|uk| Тепер виклик методу можна використовувати замість початкового виразу. Таким чином, у нас тепер є новий метод, а весь старий код все ще в робочому стані.

Print "basePrice()"

Set step 3

Select "(|||basePrice||| >"

#|ru| Самое время начать заменять переменную непосредственным вызовом метода.
#|en| Now is the perfect time to replace the variable with a direct method call.
#|uk| Саме час почати замінювати змінну безпосереднім викликом методу.

#|ru| Заменим первую переменную, а затем запустим компиляцию, чтобы убедиться, что ничего не сломалось.
#|en| Replace the first variable and then compile to make sure that nothing is broken.
#|uk| Замінимо першу змінну, а потім запустимо компіляцію, щоб переконатися, що нічого не зламалося.

Print "basePrice()"

#C|ru| Запускаем компиляцию и тестирование.
#S Всё отлично, можем продолжать!

#C|en| Let's run the compiler and auto-tests.
#S Everything is OK! We can keep going.

#C|uk| Запускаємо компіляцію і тестування.
#S Все добре, можна продовжувати.

Select "return |||basePrice|||"

#|ru| Выполним следующую замену.
#|en| Perform the next replacement.
#|uk| Виконаємо наступну заміну.

Print "basePrice()"

#C|ru| Запускаем компиляцию и тестирование.
#S Всё отлично, можем продолжать!

#C|en| Let's run the compiler and auto-tests.
#S Everything is OK! We can keep going.

#C|uk| Запускаємо компіляцію і тестування.
#S Все добре, можна продовжувати.

Select:
```
    final int basePrice = basePrice();

```

#|ru| Прошлая замена была последней, поэтому мы можем удалить объявление переменной.
#|en| The previous replacement was the last one, so we can remove the variable declaration.
#|uk| Минула заміна була останньою, тому ми можемо видалити оголошення змінної.

Remove selected

Select "double |||discountFactor|||"

#|ru| С первой переменной покончено, и мы можем повторить все действия для выделения <code>discountFactor</code>.
#|en| The first variable is done. We can repeat all this to extract <code>discountFactor</code>.
#|uk| З першою змінною закінчили, і тепер ми можемо повторити всі дії для виділення <code>discountFactor</code>.

Go to the end of "Product"

#|ru| Создаём новый метод…
#|en| Create a new method…
#|uk| Створюємо новий метод…

Print:
```

  private double discountFactor() {
    if (basePrice() > 1000) {
      return 0.95;
    }
    else {
      return 0.98;
    }
  }
```

Go to "double discountFactor|||;"

#|ru| …используем его для инициализации переменной…
#|en| …use it to initialize the variable…
#|uk| …використовуємо його для ініціалізації змінної…

Print " = discountFactor()"

Select:
```
    if (basePrice() > 1000) {
      discountFactor = 0.95;
    }
    else {
      discountFactor = 0.98;
    }

```

#|ru|^ …и удаляем код, ставший теперь ненужным.
#|en|^ …and remove the code that is no longer necessary.
#|uk|^ …та видаляємо код, що став тепер непотрібним.

Remove selected

#|ru| Обратите внимание на то, как трудно было бы выделить <code>discountFactor</code> без предварительной замены <code>basePrice</code> вызовом метода.
#|en| Note how difficult it would have been to extract <code>discountFactor</code> if we had not first replaced <code>basePrice</code> with a method call.
#|uk| Зверніть увагу на те, як важко було б виділити <code>discountFactor</code> без попередньої заміни <code>basePrice</code> викликом методу.

#|ru| В итоге приходим к следующему виду метода <code>getPrice()</code>.
#|en| Ultimately the <code>getPrice()</code> method comes to look as follows.
#|uk| У підсумку приходимо до наступного вигляду методу <code>getPrice()</code>.

Select:
```
    final double discountFactor = discountFactor();

```

Remove selected

Select "discountFactor" in "getPrice"

Replace "discountFactor()"

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