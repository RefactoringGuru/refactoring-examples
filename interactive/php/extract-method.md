extract-method:php

###

1. Создайте новый метод и назовите его так, чтобы название отражало суть того, что будет делать этот метод.

2. Скопируйте, беспокоящий вас, фрагмент кода в новый метод. Удалите этот фрагмент из старого места и замените вызовом вашего нового метода.

3. В новом методе создайте параметры для передачи значений из исходного метода.

4. Передайте результаты выполнения, и другие изменяемые данные, обратно в исходный метод.



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

# Давайте рассмотрим <i>Извлечение метода</i> на примере этой функции.

Select in "printOwing":
```
  // print banner
  print("*****************************\n");
  print("****** Customer totals ******\n");
  print("*****************************\n");
```

# Сперва — простейший случай. Код, выводящий баннер, легко выделить с помощью копирования и вставки.

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

# Заменяем код в исходном методе вызовом нового метода.

Remove selected

Print:
```
  $this->printBanner();
```

#C Запускаем авто-тесты, чтобы убедиться, что всё работает.

#S Ура, мы успешно извлекли первый метод.

Set step 3

Select:
```
  // print details
  print("name: " . $this->name);
  print("amount: " . |||$outstanding|||);

```

# Дальше — сложнее. Основная проблема с извлечением сложных методов кроется в локальных переменных.

Select in "printOwing":
```
  // print details
  print("name: " . $this->name);
  print("amount: " . $outstanding);

```

# Давайте попытаемся извлечь метод вывода деталей.

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

#C Давайте запустим тестирование.

#F Ошибка! Переменная <code>outstanding</code> в методе <code>printDetails()</code> не определена.

Select "$outstanding" in "printDetails"

# Да, действительно, мы перенесли переменную <code>outstanding</code> из исходного метода, но в новом методе ей не присваивается никакого значение.

# Решением будет сделать эту переменную параметром метода, и передавать ее значение из исходного метода.

Go to parameters of "printDetails"

Print "$outstanding"

Go to text "printDetails(|||)" in "printOwing"

Print "$outstanding"

#C Запустим авто-тесты сейчас.

#S Отлично, всё работает! Двигаемся дальше.

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

# Теперь давайте попробуем извлечь код рассчёта задолженности.


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

# Здесь дополнительные сложности создаются локальными переменными, которым присваиваются новые значения. Вполне возможно, что эти значения могли использоваться в оставшемся коде основного метода.

# Если значение присваивается параметру, от этого можно избавиться, применив «удаление присваивания параметрам».

Select in "getOutstanding":
```
  |||$outstanding||| = 0;
```

#<+ Следует проверить каждую из переменных.

+ Select in "printOwing":
```
  $this->printDetails(|||$outstanding|||);
```

#<= В нашем случае, проблему создаёт переменная <code>outstanding</code>, которая потом используется в вызове <code>printDetails()</code>.

#< Передадим её обратно в исходный метод.

Go to the end of "getOutstanding"

Print:
```

  return $outstanding;
```

Go to text "|||$this->getOutstanding()" in "printOwing"

Print "$outstanding = "

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.