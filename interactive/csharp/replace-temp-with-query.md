replace-temp-with-query:csharp

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
public class Product
{
  // ...
  public double GetPrice()
  {
    int basePrice = quantity * itemPrice;
    double discountFactor;

    if (basePrice > 1000)
    {
      discountFactor = 0.95;
    }
    else
    {
      discountFactor = 0.98;
    }

    return basePrice * discountFactor;
  }
}
```

###

```
public class Product
{
  // ...
  public double GetPrice()
  {
    return BasePrice() * DiscountFactor();
  }
  private int BasePrice()
  {
    return quantity * itemPrice;
  }
  private double DiscountFactor()
  {
    if (BasePrice() > 1000)
    {
      return 0.95;
    }
    return 0.98;
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

#|ru| В данном случае это и так видно, но чтобы обезопасить себя, можно временно объявить эти переменные константами (используя для этого ключевое слово <code>const</code>). В таком случае компилятор укажет все места, где константам пытаются повторно присвоить значения.
#|en| In this example, there is no potential confusion. But to make sure, you can temporarily declare these variables to be constants (using the <code>const</code> keyword). Then the compiler will indicate all places where attempts to assign values are made.
#|uk| В даному випадку це і так видно, але на всяк випадок, можна тимчасово оголосити ці змінні константами (використовуючи для цього ключове слово <code>const</code>). В такому випадку компілятор вкаже всі місця, де константам намагаються повторно привласнити значення.

Go to "|||int basePrice"

Print "const "

Wait 500ms

Go to "|||double discountFactor"

Print "const "

Select "const double |||discountFactor|||"

#|ru| Обратите внимание, что если переменная не была проинициализирована при объявлении, то при её превращении в константу, надо эту инициализацию произвести (иначе будет ошибка объявления константы). Для этого присвойте ей любое временное значение.
#|en| Note that if a variable was not initialized at declaration time, when it turns into a constant you need to perform this initialization (or else a constant declaration error will occur). So assign any temporary value to it.
#|uk| Зверніть увагу, що якщо змінна не була проініціалізувати при оголошенні, то при її перетворенні на константу, треба цю ініціалізацію зробити (інакше буде помилка оголошення константи). Для цього надайте їй будь-яке тимчасове значення.

Go to "double discountFactor|||"

Print " = 0"

#C|ru| Давайте запустим компиляцию и посмотрим, присваиваются ли нашим переменным ещё значения.
#F Ошибка! Левая часть выражения присваивания должна быть переменной, свойством или индексатором (<code>line 11</code>, <code>line 15</code>).

#C|en| Let's compile and see whether our variables are assigned other values.
#F Error! The left part of the assignment must be a variable, property or Indexer (<code>line 11</code>, <code>line 15</code>).

#C|uk| Давайте запустимо компіляцію і подивимося, чи присвоюються нашим змінним ще значення.
#F Помилка! Ліва частина виразу присвоювання повинна бути змінною, властивістю або індексатором (<code>line 11</code>, <code>line 15</code>).

Select "      discountFactor = 0.95;"
+Select "      discountFactor = 0.98;"

#|ru| Что ж, компилятор указывает нам на две строки, где происходит попытка изменить значение константы. Ошибки относятся только к <code>discountFactor</code>. Вспомним, что мы присвоили ей временное значение, а, значит, одна ошибка присваивания должна быть в любом случае. Но почему компилятор указывает на два места, а не на одно? После внимательного осмотра становится понятно, что это инициализация переменной во взаимоисключающих ветках <code>if-else</code>, поэтому значение будет присвоено только один раз. А раз это так, то можем смело приступать к рефакторингу.
#|en| The compiler now draws our attention to two lines where an attempt to change the value of the constant is made. The errors relate only to <code>discountFactor</code>. Remember that we assigned a temporary value to it, meaning that there should be one assignment error in any case. But why does the compiler indicate two places, not one? After careful review, it becomes clear that initialization of the variable takes place in mutually exclusive <code>if-else</code> branches, so a value will be assigned only once. That being true, we can proceed to refactoring.
#|uk| Що ж, компілятор вказує нам на два рядки, де відбувається спроба змінити значення константи. Помилки відносяться тільки до <code>discountFactor</code>. Згадаймо, що ми присвоїли їй тимчасове значення, а, значить, одна помилка присвоювання повинна бути в будь-якому випадку. Але чому компілятор вказує на два місця, а не на одне? Після уважного огляду стає зрозуміло, що це ініціалізація змінної у взаємовиключних гілках <code>if-else</code>, тому значення буде присвоєно лише один раз. А раз це так, то можемо сміливо приступати до рефакторингу.

Select "const "
+Select "double discountFactor||| = 0|||"

#|ru| После того как мы убедились, что интересующие нас переменные получают свои значения лишь единожды, надо вернуть их в исходное состояние.
#|en| After we made sure that the variables of interest get their values once and only once, we need to return them to their original state.
#|uk| Після того як ми переконалися, змінні, які нас цікавлять, отримують свої значення лише одноразово, треба повернути їх в початковий стан.

Wait 500ms

Remove selected

#C|ru| Давайте запустим компиляцию и удостоверимся, что мы не изменили ничего лишнего.
#S <b>Все отлично, можем продолжать!</b><br/><br/>Имейте в виду, эта проверка переменных очень важна. При возникновении проблем на этом шаге, следует воздержаться от проведения данного рефакторинга.

#C|en| Compile and make sure that we have not made any stray changes.
#S <b>Everything's A-OK! We can keep going.</b><br/><br/>Keep in mind that this variable check is very important! If issues arise during this step, you should refrain from using this refactoring technique.

#C|uk| Давайте запустимо компіляцію і переконаємося, що ми не змінили нічого зайвого.
#S <b>Все відмінно, можемо продовжувати!</b><br/><br/>Майте на увазі, ця перевірка змінних дуже важлива. При виникненні проблем на цьому кроці, слід утриматися від проведення даного рефакторинга.

Set step 2

Select "basePrice = quantity * itemPrice"

#|ru| Итак, на втором шаге, создадим метод <code>BasePrice()</code> и перенесём в него выражение, формирующее переменную <code>basePrice</code>.
#|en| For the second step, we create the <code>BasePrice()</code> method and move the expression that forms the <code>basePrice</code> variable to it.
#|uk| Отже, на другому кроці, створимо метод <code>BasePrice()</code> і перенесемо в нього вираз, який формує змінну <code>basePrice</code>.

Go to the end of "Product"

Print:
```

  private int BasePrice()
  {
    return quantity * itemPrice;
  }
```

Select "basePrice = |||quantity * itemPrice|||"

#|ru| Теперь вызов метода можно использовать вместо первоначального выражения. Таким образом, у нас теперь есть новый метод, а весь старый код все ещё в рабочем состоянии.
#|en| Now we can use a method call instead of the initial expression. Thus, we now have a new method and all of the old code still works.
#|uk| Тепер виклик методу можна використовувати замість початкового виразу. Таким чином, у нас тепер є новий метод, а весь старий код все ще в робочому стані.

Print "BasePrice()"

Set step 3

Select "(|||basePrice||| >"

#|ru| Самое время начать заменять переменную непосредственным вызовом метода.
#|en| Now is the perfect time to replace the variable with a direct method call.
#|uk| Саме час почати замінювати змінну безпосереднім викликом методу.

#|ru| Заменим первую переменную, а затем запустим компиляцию, чтобы убедиться, что ничего не сломалось.
#|en| Replace the first variable and then compile to make sure that nothing is broken.
#|uk| Замінимо першу змінну, а потім запустимо компіляцію, щоб переконатися, що нічого не зламалося.

Print "BasePrice()"

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

Print "BasePrice()"

#C|ru| Запускаем компиляцию и тестирование.
#S Всё отлично, можем продолжать!

#C|en| Let's run the compiler and auto-tests.
#S Everything is OK! We can keep going.

#C|uk| Запускаємо компіляцію і тестування.
#S Все добре, можна продовжувати.

Select:
```
    int basePrice = BasePrice();

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

  private double DiscountFactor()
  {
    if (BasePrice() > 1000)
    {
      return 0.95;
    }
    return 0.98;
  }
```

Go to "double discountFactor|||;"

#|ru| …используем его для инициализации переменной…
#|en| …use it to initialize the variable…
#|uk| …використовуємо його для ініціалізації змінної…

Print " = DiscountFactor()"

Select:
```

    if (BasePrice() > 1000)
    {
      discountFactor = 0.95;
    }
    else
    {
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

#|ru| В итоге приходим к следующему виду метода <code>GetPrice()</code>.
#|en| Ultimately the <code>GetPrice()</code> method comes to look as follows.
#|uk| У підсумку приходимо до наступного вигляду методу <code>GetPrice()</code>.

Select:
```
    double discountFactor = DiscountFactor();

```

Remove selected

Select "discountFactor" in "GetPrice"

Replace "DiscountFactor()"

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