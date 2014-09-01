extract-method:java

###

1. Создайте новый метод и назовите его так, чтобы название отражало суть того, что будет делать этот метод.

2. Скопируйте, беспокоящий вас, фрагмент кода в новый метод. Удалите этот фрагмент из старого места и замените вызовом вашего нового метода.

3. В новом методе создайте параметры для передачи значений из исходного метода.

4. Передайте результаты выполнения, и другие изменяемые данные, обратно в исходный метод.



###

```
void printOwing() {
  Enumeration elements = orders.elements();
  double outstanding = 0.0;

  // print banner
  System.out.println ("*****************************");
  System.out.println ("****** Customer totals ******");
  System.out.println ("*****************************");

  // print owings
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }

  // print details
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}
```

###

```
void printOwing() {
  printBanner();
  double outstanding = getOutstanding();
  printDetails(outstanding);
}

void printBanner() {
  System.out.println("*****************************");
  System.out.println("****** Customer totals ******");
  System.out.println("*****************************");
}

void printDetails(double outstanding) {
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}

double getOutstanding() {
  Enumeration elements = orders.elements();
  double outstanding = 0.0;
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }
  return outstanding;
}
```

###

Set step 1

# Давайте рассмотрим <i>Извлечение метода</i> на примере этой функции.

Select in "printOwing":
```
  // print banner
  System.out.println ("*****************************");
  System.out.println ("****** Customer totals ******");
  System.out.println ("*****************************");
```

# Для начала – простейший случай. Код, выводящий баннер, легко выделить при помощи копирования и вставки.

Wait 500ms

Go to the end of file

Print:
```


void printBanner() {
  System.out.println("*****************************");
  System.out.println("****** Customer totals ******");
  System.out.println("*****************************");
}
```

Set step 2

Select in "printOwing":
```
  // print banner
  System.out.println ("*****************************");
  System.out.println ("****** Customer totals ******");
  System.out.println ("*****************************");
```

# Заменяем код в исходном методе вызовом нового метода.

Remove selected

Print:
```
  printBanner();
```

#C Запускаем компиляцию, чтобы убедиться, что всё работает.

#S Ура, мы успешно извлекли первый метод.

Set step 3

Select:
```
  // print details
  System.out.println("name: " + name);
  System.out.println("amount: " + |||outstanding|||);
```

# Дальше — сложнее. Основная проблема с извлечением сложных методов кроется в локальных переменных.

Select in "printOwing":
```
  // print details
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
```

# Давайте попытаемся извлечь метод вывода деталей.

Wait 500ms

Go to the end of file

Print:
```


void printDetails() {
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}
```

Select in "printOwing":
```
  // print details
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
```

Remove selected

Print "  printDetails();"

#C Давайте запустим компиляцию.

#F Ошибка! Переменная <code>outstanding</code> в методе <code>printDetails()</code> не определена.

Select in "printDetails" text "outstanding"

# Да, действительно, мы перенесли переменную <code>outstanding</code> из исходного метода, но в новом методе ей не присваивается никакого значения.

# Лучшее решение –  сделать эту переменную параметром метода, и передавать её значение из исходного метода.

Go to text "void printDetails(|||)"

Print "double outstanding"

Go to text "printDetails(|||)" in "printOwing"

Print "outstanding"

#C Запустим компиляцию сейчас.

#S Отлично, всё работает! Двигаемся дальше.

Set step 4

Select in "printOwing":
```
  Enumeration elements = orders.elements();
  double outstanding = 0.0;
```
+ Select in "printOwing":
```
  // print owings
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }
```

# Теперь давайте попробуем извлечь код расчёта задолженности.


Wait 500ms

Go to the end of file

Print:
```


void getOutstanding() {
  Enumeration elements = orders.elements();
  double outstanding = 0.0;
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }
}
```

Select in "printOwing":
```
  Enumeration elements = orders.elements();
  double outstanding = 0.0;


```

Remove selected

Select in "printOwing":
```

  // print owings
  while (elements.hasMoreElements()) {
    Order each = (Order) elements.nextElement();
    outstanding += each.getAmount();
  }

```

Remove selected

Print "  getOutstanding();"

Select in "getOutstanding":
```
  Enumeration |||elements||| = orders.elements();
  double |||outstanding||| = 0.0;
```

# Здесь дополнительные сложности создаются локальными переменными, которым присваиваются новые значения. Вполне возможно, что эти значения могли использоваться в оставшемся коде основного метода.

# Если значение присваивается параметру, от этого можно избавиться, применив «удаление присваивания параметрам».

Select in "getOutstanding":
```
  double |||outstanding||| = 0.0;
```

#<+ Следует проверить каждую из переменных.

+ Select in "printOwing":
```
  printDetails(|||outstanding|||);
```

#<= В нашем случае проблему создаёт переменная <code>outstanding</code>, которая потом используется в вызове <code>PrintDetails()</code>.

#< Передадим её обратно в исходный метод.

Select type of "GetOutstanding"

Replace:
```
double
```

Go to the end of "getOutstanding"

Print:
```

  return outstanding;
```

Go to text "|||getOutstanding()" in "printOwing"

Print "double outstanding = "

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.