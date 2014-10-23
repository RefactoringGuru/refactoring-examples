extract-method:php

###

1.ru. Создайте новый метод и назовите его так, чтобы название отражало суть того, что будет делать этот метод.
1.uk. Створіть новий метод. Потурбуйтеся, щоб його назва відбивала суть того, що робитиме цей метод.

2.ru. Скопируйте беспокоящий вас фрагмент кода в новый метод. Удалите этот фрагмент из старого места и замените вызовом вашего нового метода.
2.uk. Скопіюйте фрагмент коду, що вас цікавить, в новий метод. Видаліть цей фрагмент із старого місця і замініть викликом вашого нового методу.

3.ru. В новом методе создайте параметры для передачи значений из исходного метода.
3.uk. У новому методі створіть параметри для передачі значень з вихідного методу.

4.ru. Передайте результаты выполнения, и другие изменяемые данные, обратно в исходный метод.
4.uk. Передайте результати виконання, та інші змінювані дані, назад в вихідний метод.



###

```
function printOwing() {
  $e = $this->orders->elements();
  $outstanding = 0;

  // print banner
  print("*****************************\n");
  print("****** Customer totals ******\n");
  print("*****************************\n");

  // print owings
  while ($e->hasMoreElements()) {
    $each = $e->nextElement();
    $outstanding += $each->getAmount();
  }

  // print details
  print("name: " . $this->name);
  print("amount: " . $outstanding);
}
```

###

```
function printOwing() {
  $this->printBanner();
  $outstanding = $this->getOutstanding();
  $this->printDetails($outstanding);
}

function printBanner() {
  print("*****************************\n");
  print("****** Customer totals ******\n");
  print("*****************************\n");
}

function printDetails($outstanding) {
  print("name: " . $this->name);
  print("amount: " . $outstanding);
}

function getOutstanding() {
  $e = $this->orders->elements();
  $outstanding = 0;
  while ($e->hasMoreElements()) {
    $each = $e->nextElement();
    $outstanding += $each->getAmount();
  }
  return $outstanding;
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Извлечение метода</i> на примере этой функции.
#|uk| Давайте розглянемо <i>Витяг методу<i> на прикладі цієї функції.

Select in "printOwing":
```
  // print banner
  print("*****************************\n");
  print("****** Customer totals ******\n");
  print("*****************************\n");
```

#|ru| Для начала – простейший случай. Код, выводящий баннер, легко выделить при помощи копирования и вставки.
#|uk| Спершу – найпростіший випадок. Код, що виводить банер, легко виділити за допомогою копіювання і вставки.

Wait 500ms

Go to the end of file

Print:
```


function printBanner() {
  print("*****************************\n");
  print("****** Customer totals ******\n");
  print("*****************************\n");
}
```

Set step 2

Select in "printOwing":
```
  // print banner
  print("*****************************\n");
  print("****** Customer totals ******\n");
  print("*****************************\n");
```

#|ru| Заменяем код в исходном методе вызовом нового метода.
#|uk| Замінюємо код у вихідному методі викликом нового методу.

Remove selected

Print:
```
  $this->printBanner();
```

#C|ru| Запускаем авто-тесты, чтобы убедиться, что всё работает.
#S Ура, мы успешно извлекли первый метод.

#C|uk| Запускаємо авто-тести, щоб переконатися, що все працює.
#S Ура, ми успішно витягли перший метод.

Set step 3

Select:
```
  // print details
  print("name: " . $this->name);
  print("amount: " . |||$outstanding|||);

```

#|ru| Дальше — сложнее. Основная проблема с извлечением сложных методов кроется в локальных переменных.
#|uk| Далі — складніше. Основна проблема з відокремленням складних методів криється в локальних змінних.

Select in "printOwing":
```
  // print details
  print("name: " . $this->name);
  print("amount: " . $outstanding);

```

#|ru| Давайте попытаемся извлечь метод вывода деталей.
#|uk| Давайте спробуємо відокремити метод виводу деталей.

Wait 500ms

Go to the end of file

Print:
```


function printDetails() {
  print("name: " . $this->name);
  print("amount: " . $outstanding);
}
```

Select in "printOwing":
```
  // print details
  print("name: " . $this->name);
  print("amount: " . $outstanding);
```

Remove selected

Print "  $this->printDetails();"

#C|ru| Давайте запустим тестирование.
#F Ошибка! Переменная <code>outstanding</code> в методе <code>printDetails()</code> не определена.

#C|uk| Давайте запустимо тестування.
#F Помилка! Змінна <code>outstanding</code> в методі <code>printDetails()</code> не визначена.

Select "$outstanding" in "printDetails"

#|ru| Да, действительно, мы перенесли переменную <code>outstanding</code> из исходного метода, но в новом методе ей не присваивается никакого значения.
#|uk| Так, дійсно, ми перенесли змінну <code>outstanding</code> з вихідного методу, але в новому методі їй не присвоюється ніякого значення.

#|ru| Лучшее решение –  сделать эту переменную параметром метода, и передавать её значение из исходного метода.
#|uk| Рішенням буде зробити цю змінну параметром методу, і передавати її значення з вихідного методу.

Go to parameters of "printDetails"

Print "$outstanding"

Go to text "printDetails(|||)" in "printOwing"

Print "$outstanding"

#C|ru| Запустим авто-тесты сейчас.
#S Отлично, всё работает! Двигаемся дальше.

#C|uk| Запустимо авто-тести зараз.
#S Супер, все працює! Рухаємося далі.

Set step 4

Select in "printOwing":
```
  $e = $this->orders->elements();
  $outstanding = 0;
```
+ Select in "printOwing":
```
  // print owings
  while ($e->hasMoreElements()) {
    $each = $e->nextElement();
    $outstanding += $each->getAmount();
  }
```

#|ru| Теперь давайте попробуем извлечь код расчёта задолженности.
#|uk| Тепер давайте спробуємо відокремити код розрахунку заборгованості.


Wait 500ms

Go to the end of file

Print:
```


function getOutstanding() {
  $e = $this->orders->elements();
  $outstanding = 0;
  while ($e->hasMoreElements()) {
    $each = $e->nextElement();
    $outstanding += $each->getAmount();
  }
}
```

Select in "printOwing":
```
  $e = $this->orders->elements();
  $outstanding = 0;


```

Remove selected

Select in "printOwing":
```

  // print owings
  while ($e->hasMoreElements()) {
    $each = $e->nextElement();
    $outstanding += $each->getAmount();
  }

```

Remove selected

Print "  $this->getOutstanding();"

Select in "getOutstanding":
```
  |||$e||| = $this->orders->elements();
  |||$outstanding||| = 0;
```

#|ru| Здесь дополнительные сложности создаются локальными переменными, которым присваиваются новые значения. Вполне возможно, что эти значения могли использоваться в оставшемся коде основного метода.
#|uk| Тут додаткові складності створюються локальними змінними, яким присвоюються нові значення. Цілком можливо, що ці значення могли використовуватися в залишившемуся коді основного методу.

#|ru| Если значение присваивается параметру, от этого можно избавиться, применив «удаление присваивания параметрам».
#|uk| Якщо значення присвоюється параметру, від цього можна позбутися, застосувавши «видалення присвоювання параметрами».

Select in "getOutstanding":
```
  |||$outstanding||| = 0;
```

#|ru|<+ Следует проверить каждую из переменных.
#|uk|<+ Слід перевірити кожну з змінних.

+ Select in "printOwing":
```
  $this->printDetails(|||$outstanding|||);
```

#|ru|<= В нашем случае проблему создаёт переменная <code>outstanding</code>, которая потом используется в вызове <code>PrintDetails()</code>.
#|uk|<= В нашому випадку, проблему створює змінна <code>outstanding</code>, яка потім використовується у виклику <code>PrintDetails()</code>.

#|ru|< Передадим её обратно в исходный метод.
#|uk|< Передамо її назад у вихідний метод.

Go to the end of "getOutstanding"

Print:
```

  return $outstanding;
```

Go to text "|||$this->getOutstanding()" in "printOwing"

Print "$outstanding = "

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.