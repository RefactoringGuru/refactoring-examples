extract-method:csharp

###

1. Создайте новый метод и назовите его так, чтобы название отражало суть того, что будет делать этот метод.

2. Скопируйте, беспокоящий вас, фрагмент кода в новый метод. Удалите этот фрагмент из старого места и замените вызовом вашего нового метода.

3. В новом методе создайте параметры для передачи значений из исходного метода.

4. Передайте результаты выполнения, и другие изменяемые данные, обратно в исходный метод.



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

# Давайте рассмотрим <i>Извлечение метода</i> на примере этой функции.

Select in "PrintOwing":
```
  // print banner
  Console.WriteLine ("*****************************");
  Console.WriteLine ("****** Customer totals ******");
  Console.WriteLine ("*****************************");
```

# Сперва — простейший случай. Код, выводящий баннер, легко выделить с помощью копирования и вставки.

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

# Заменяем код в исходном методе вызовом нового метода.

Remove selected

Print:
```
  PrintBanner();
```

#C Запускаем компиляцию, чтобы убедиться, что всё работает.

#S Ура, мы успешно извлекли первый метод.

Set step 3

Select:
```
  // print details
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + |||outstanding|||);
```

# Дальше — сложнее. Основная проблема с извлечением сложных методов кроется в локальных переменных.

Select in "PrintOwing":
```
  // print details
  Console.WriteLine("name: " + name);
  Console.WriteLine("amount: " + outstanding);
```

# Давайте попытаемся извлечь метод вывода деталей.

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

#C Давайте запустим компиляцию.

#F Ошибка! Переменная <code>outstanding</code> в методе <code>PrintDetails()</code> не определена.

Select in "PrintDetails" text "outstanding"

# Да, действительно, мы перенесли переменную <code>outstanding</code> из исходного метода, но в новом методе ей не присваивается никакого значение.

# Решением будет сделать эту переменную параметром метода, и передавать её значение из исходного метода.

Go to text "void PrintDetails(|||)"

Print "double outstanding"

Go to text "PrintDetails(|||)" in "PrintOwing"

Print "outstanding"

#C Запустим компиляцию сейчас.

#S Отлично, всё работает! Двигаемся дальше.

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

# Теперь давайте попробуем извлечь код рассчёта задолженности.


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

# Здесь дополнительные сложности создаются локальными переменными, которым присваиваются новые значения. Вполне возможно, что эти значения могли использоваться в оставшемся коде основного метода.

# Если значение присваивается параметру, от этого можно избавиться, применив «удаление присваивания параметрам».

Select in "GetOutstanding":
```
  double |||outstanding||| = 0.0;
```

#<+ Следует проверить каждую из переменных.

+ Select in "PrintOwing":
```
  PrintDetails(|||outstanding|||);
```

#<= В нашем случае, проблему создаёт переменная <code>outstanding</code>, которая потом используется в вызове <code>PrintDetails()</code>.

#< Передадим её обратно в исходный метод.

Select type of "GetOutstanding"

Replace "double"

Go to the end of "GetOutstanding"

Print:
```


  return outstanding;
```

Go to text "|||GetOutstanding()" in "PrintOwing"

Print "double outstanding = "

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.