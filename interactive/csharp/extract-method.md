extract-method:csharp

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
void PrintOwing()
{
  List<Order> orders = orders.Elements;
  double outstanding = 0.0;

  // print banner
  Console.WriteLine ("*****************************");
  Console.WriteLine ("****** Customer totals ******");
  Console.WriteLine ("*****************************");

  // print owings
  foreach (Order order in orders)
  {
    outstanding += order.GetAmount();
  }

  // print details
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + outstanding);
}
```

###

```
void PrintOwing()
{
  PrintBanner();
  double outstanding = GetOutstanding();
  PrintDetails(outstanding);
}

void PrintBanner()
{
  Console.WriteLine("*****************************");
  Console.WriteLine("****** Customer totals ******");
  Console.WriteLine("*****************************");
}

void PrintDetails(double outstanding)
{
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + outstanding);
}

double GetOutstanding()
{
  List<Order> orders = orders.Elements;
  double outstanding = 0.0;

  foreach (Order order in orders)
  {
    outstanding += order.GetAmount();
  }

  return outstanding;
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Извлечение метода</i> на примере этой функции.
#|uk| Давайте розглянемо <i>Витяг методу<i> на прикладі цієї функції.

Select in "PrintOwing":
```
  // print banner
  Console.WriteLine ("*****************************");
  Console.WriteLine ("****** Customer totals ******");
  Console.WriteLine ("*****************************");
```

#|ru| Для начала – простейший случай. Код, выводящий баннер, легко выделить при помощи копирования и вставки.
#|uk| Спершу – найпростіший випадок. Код, що виводить банер, легко виділити за допомогою копіювання і вставки.

Wait 500ms

Go to the end of file

Print:
```


void PrintBanner()
{
  Console.WriteLine("*****************************");
  Console.WriteLine("****** Customer totals ******");
  Console.WriteLine("*****************************");
}
```

Set step 2

Select in "PrintOwing":
```
  // print banner
  Console.WriteLine ("*****************************");
  Console.WriteLine ("****** Customer totals ******");
  Console.WriteLine ("*****************************");
```

#|ru| Заменяем код в исходном методе вызовом нового метода.
#|uk| Замінюємо код у вихідному методі викликом нового методу.

Remove selected

Print:
```
  PrintBanner();
```

#C|ru| Запускаем компиляцию, чтобы убедиться, что всё работает.
#S Ура, мы успешно извлекли первый метод.

#C|uk| Запускаємо компіляцію, щоб переконатися, що все працює.
#S Ура, ми успішно витягли перший метод.

Set step 3

Select:
```
  // print details
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + |||outstanding|||);
```

#|ru| Дальше — сложнее. Основная проблема с извлечением сложных методов кроется в локальных переменных.
#|uk| Далі — складніше. Основна проблема з відокремленням складних методів криється в локальних змінних.

Select in "PrintOwing":
```
  // print details
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + outstanding);
```

#|ru| Давайте попытаемся извлечь метод вывода деталей.
#|uk| Давайте спробуємо відокремити метод виводу деталей.

Wait 500ms

Go to the end of file

Print:
```


void PrintDetails()
{
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + outstanding);
}
```

Select in "PrintOwing":
```
  // print details
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + outstanding);
```

Remove selected

Print "  PrintDetails();"

#C|ru| Давайте запустим компиляцию.
#F Ошибка! Переменная <code>outstanding</code> в методе <code>PrintDetails()</code> не определена.

#C|uk| Давайте запустимо компіляцію.
#F Помилка! Змінна <code>outstanding</code> в методі <code>PrintDetails()</code> не визначена.

Select in "PrintDetails" text "outstanding"

#|ru| Да, действительно, мы перенесли переменную <code>outstanding</code> из исходного метода, но в новом методе ей не присваивается никакого значения.
#|uk| Так, дійсно, ми перенесли змінну <code>outstanding</code> з вихідного методу, але в новому методі їй не присвоюється ніякого значення.

#|ru| Лучшее решение –  сделать эту переменную параметром метода, и передавать её значение из исходного метода.
#|uk| Рішенням буде зробити цю змінну параметром методу, і передавати її значення з вихідного методу.

Go to text "void PrintDetails(|||)"

Print "double outstanding"

Go to text "PrintDetails(|||)" in "PrintOwing"

Print "outstanding"

#C|ru| Запустим компиляцию сейчас.
#S Отлично, всё работает! Двигаемся дальше.

#C|uk| Запустимо компіляцію зараз.
#S Супер, все працює! Рухаємося далі.

Set step 4

Select in "PrintOwing":
```
  List<Order> orders = orders.Elements;
  double outstanding = 0.0;
```
+ Select in "PrintOwing":
```
  // print owings
  foreach (Order order in orders)
  {
    outstanding += order.GetAmount();
  }
```

#|ru| Теперь давайте попробуем извлечь код расчёта задолженности.
#|uk| Тепер давайте спробуємо відокремити код розрахунку заборгованості.


Wait 500ms

Go to the end of file

Print:
```


void GetOutstanding()
{
  List<Order> orders = orders.Elements;
  double outstanding = 0.0;

  foreach (Order order in orders)
  {
    outstanding += order.GetAmount();
  }
}
```

Select in "PrintOwing":
```
  List<Order> orders = orders.Elements;
  double outstanding = 0.0;


```

Remove selected

Select in "PrintOwing":
```

  // print owings
  foreach (Order order in orders)
  {
    outstanding += order.GetAmount();
  }

```

Remove selected

Print "  GetOutstanding();"

Select in "GetOutstanding":
```
  List<Order> |||orders||| = orders.Elements;
  double |||outstanding||| = 0.0;
```

#|ru| Здесь дополнительные сложности создаются локальными переменными, которым присваиваются новые значения. Вполне возможно, что эти значения могли использоваться в оставшемся коде основного метода.
#|uk| Тут додаткові складності створюються локальними змінними, яким присвоюються нові значення. Цілком можливо, що ці значення могли використовуватися в залишившемуся коді основного методу.

#|ru| Если значение присваивается параметру, от этого можно избавиться, применив «удаление присваивания параметрам».
#|uk| Якщо значення присвоюється параметру, від цього можна позбутися, застосувавши «видалення присвоювання параметрами».

Select in "GetOutstanding":
```
  double |||outstanding||| = 0.0;
```

#|ru|<+ Следует проверить каждую из переменных.
#|uk|<+ Слід перевірити кожну з змінних.

+ Select in "PrintOwing":
```
  PrintDetails(|||outstanding|||);
```

#|ru|<= В нашем случае проблему создаёт переменная <code>outstanding</code>, которая потом используется в вызове <code>PrintDetails()</code>.
#|uk|<= В нашому випадку, проблему створює змінна <code>outstanding</code>, яка потім використовується у виклику <code>PrintDetails()</code>.

#|ru|< Передадим её обратно в исходный метод.
#|uk|< Передамо її назад у вихідний метод.

Select type of "GetOutstanding"

Replace "double"

Go to the end of "GetOutstanding"

Print:
```


  return outstanding;
```

Go to text "|||GetOutstanding()" in "PrintOwing"

Print "double outstanding = "

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.