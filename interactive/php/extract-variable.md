extract-variable:php

###

1.ru. Создайте локальную переменную и передайте ей необходимое значение.
1.en. Create a local variable and give it the necessary value.
1.uk. Створіть локальну змінну і передайте їй необхідне значення.

2.ru. Замените исходное выражение вашей новой переменной.
2.en. Replace the original expression with your new variable.
2.uk. Замініть вихідний вираз вашої нової змінної.



###

```
public function price() {
  // Price consists of: base price - discount + shipping cost
  return $this->quantity * $this->itemPrice -
    Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05 +
    Math.min($this->quantity * $this->itemPrice * 0.1, 100.0);
}
```

###

```
public function price() {
  $basePrice = $this->quantity * $this->itemPrice;
  $quantityDiscount = Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05;
  $shipping = Math.min($basePrice * 0.1, 100.0);
  return $basePrice - $quantityDiscount + $shipping;
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Извлечение переменной</i> на примере этого простого метода.
#|en| Let's look at <i>Extract Variable</i> using this simple method as an example.
#|uk| Давайте розглянемо <i>Відокремлення змінної</i> на прикладі цього простого методу.

#|ru| Как видите, метод состоит из одного громадного выражения, в котором сам черт ногу сломит.
#|en| As you see, the method contains a single enormous expression capable of throwing off both police dogs and the most determined programmers.
#|uk| Як бачите, метод складається з одного величезного виразу, в якому сам чорт ногу зломить.

#|ru| Давайте разобьём это выражение на отдельные части, при этом каждую из частей поместим в отдельную переменную.
#|en| Let's split this expression into separate parts, placing each part in a separate variable.
#|uk| Давайте розіб'ємо цей вираз на окремі частини, при цьому кожну з частин помістимо в окрему змінну.

Select "$this->quantity * $this->itemPrice"

#|ru| Сперва определим базовую цену <code>basePrice</code> как количество товаров в заказе, умноженное на стоимость одной единицы…
#|en| First define the <code>basePrice</code> as the number of products in the order, multiplied by the unit cost…
#|uk| Спершу визначимо базову ціну <code>basePrice</code> як кількість товарів в замовленні, помножене на вартість однієї одиниці…

Set step 2

Go to "shipping cost|||"

Print:
```

  $basePrice = $this->quantity * $this->itemPrice;
```

Select 2nd "$this->quantity * $this->itemPrice"
+ Select 3rd "$this->quantity * $this->itemPrice"

#|ru| …и применим новую переменную в формуле. Данное выражение повторяется несколько раз, поэтому заменим все одинаковые вычисления переменной.
#|en| …and use the new variable in the formula. The expression is repeated several times so we will replace all identical calculations with the variable.
#|uk| …і застосуємо нову змінну у формулі. Даний вираз повторюється кілька разів, тому замінимо всі однакові обчислення змінної.

Print "$basePrice"

#C|ru| Давайте запустим тесты и удостоверимся, что все хорошо.
#S Всё отлично, можем продолжать!

#C|en| Let's test and make sure that everything is OK.
#S Everything is OK! We can keep going.

#C|uk| Давайте запустимо тести і переконався, що все добре.
#S Все добре, можемо продовжувати.

Select:
```
    Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05 +
    Math.min($basePrice * 0.1, 100.0);
```

#|ru| Теперь необходимо заменить переменными оставшиеся части сложного выражения.
#|en| Now replace the remaining parts of the complex expression with variables.
#|uk| Тепер необхідно замінити змінними частини складного виразу, що залишилися.

Select:
```
Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05
```

#|ru| Определим скидку за количество купленного товара <code>quantityDiscount</code>, а расчёт вынесем в новую переменную.
#|en| Define the <code>quantityDiscount</code> and move calculation to a new variable.
#|uk| Визначимо знижку за кількість придбанного товару <code>quantityDiscount</code>, а розрахунок винесемо в нову змінну.

Go to "$this->quantity * $this->itemPrice;|||"

Print:
```

  $quantityDiscount = Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05;
```

Select:
```

    Math.max(0, $this->quantity - 500) * $this->itemPrice * 0.05
```

Replace " $quantityDiscount"

#C|ru| Готово, теперь запустим тесты и удостоверимся, что не возникло ошибок.
#S Всё отлично, можем продолжать!

#C|en| All done! Let's test for any possible errors.
#S Everything is OK! We can keep going.

#C|uk| Готово, тепер запустимо тести і переконався, що не виникло помилок.
#S Все добре, можемо продовжувати.

Select:
```
Math.min($basePrice * 0.1, 100.0)
```

#|ru| Последняя часть расчётов  – это доставка <code>shipping</code>. Здесь также используем отдельную переменную.
#|en| The final part of calculation is <code>shipping</code>. We use a separate variable here as well.
#|uk| Остання частина розрахунків – це доставка <code>shipping</code>. Тут також використаємо окрему змінну.

Go to "$this->itemPrice * 0.05;|||"

Print:
```

  $shipping = Math.min($basePrice * 0.1, 100.0);
```

Select:
```

    Math.min($basePrice * 0.1, 100.0)
```

Replace " $shipping"

Select:
```
  // Price consists of: base price - discount + shipping cost

```

#|ru| Кстати, теперь выражение стало вполне очевидным, поэтому комментарий можно убрать.
#|en| Since the expression is now obvious and intuitive, we can remove the comment.
#|uk| До речі, тепер вираз став цілком очевидним, тому коментар можна прибрати.

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