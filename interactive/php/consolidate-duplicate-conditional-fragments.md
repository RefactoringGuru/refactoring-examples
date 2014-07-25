consolidate-duplicate-conditional-fragments:php

###

1. Если дублирующие участки находятся вначале веток оператора, вынесите их перед условным оператором.

2. Если такой код выполняется в конце веток, поместите его после условного оператора.

3. Если дублирующий код расположен случайным образом внутри веток, вам нужно для начала попытаться передвинуть его в начало или в конец ветки, в зависимости от того, меняет ли он результат последующего кода.

4. Дублирующий фрагмент кода более одной строки можно попытаться <a href="/extract-method">извлечь в новый метод</a>, если в этом есть смысл.



###

```
function sendEmailPromotion($email, $text) {
  // ...
  if (isSpecialDeal()) {
    $total = $price * 0.95;
    validateEmailAddress($email);
    sendEmail(formatEmail($text, $total));
  }
  else {
    validateEmailAddress($email);
    $total = $price * 0.98;
    sendEmail(formatEmail($text, $total));
  }
}
```

###

```
function sendEmailPromotion($email, $text) {
  // ...
  validateEmailAddress($email);
  if (isSpecialDeal()) {
    $total = $price * 0.95;
  }
  else {
    $total = $price * 0.98;
  }
  sendEmail(formatEmail($text, $total));
}
```

###

Set step 1

Select "sendEmail(formatEmail($text, $total));"
+ Select "validateEmailAddress($email);"

# Как видите, в нашем методе есть условный оператор с повторяющимся кодом в разных ветках.

# Попробуем вынести этот код за пределы условного оператора. Начнём с <code>sendEmail()</code>.

Select:
```
    sendEmail(formatEmail($text, $total));

```

# Вызов этой функции лежит в конце обоих веток условного оператора. Это значит, что мы можем перенести вызов после условия.

Remove selected

Go to the end of "sendEmailPromotion"

Print:
```

  sendEmail(formatEmail($text, $total));
```

#C Запускаем компиляцию и тестирование.

#S Пока что всё работает!

Select:
```
    validateEmailAddress($email);

```

# Теперь попытаемся вынести <code>validateEmailAddress()</code>. Эти вызовы находятся в разных местах, поэтому нужно подумать, будет ли уместным вообще их двигать. В нашем случае, валижадию можно проводить где угодно, но лучше всего — ближе к началу метода. Поэтому и перемещаём её туда.

Remove selected

Go to:
```
  // ...|||
  if
```

Print:
```

  validateEmailAddress($email);
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.