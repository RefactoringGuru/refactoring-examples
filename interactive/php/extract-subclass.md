extract-subclass:php

###

1. Создайте новый подкласс из интересующего вас класса.

2. Если для создания объектов из подкласса будут нужны какие-то дополнительные данные, создайте конструктор и дополните его нужными параметрами. Не забудьте вызвать родительскую реализацию конструктора.

3. Найдите все вызовы конструктора родительского класса. В тех случаях, когда требуется функциональность подкласса, замените родительский конструктор конструктором подкласса.

4. Переместите нужные методы и поля из родительского класса в подкласс. Используйте для этого <a href="/push-down-method">спуск метода</a> и <a href="/push-down-field">спуск поля</a>. Проще всего начинать перенос с методов. Так  поля будут доступны для них все время:  из родительского класса до переноса, и из самого подкласса после окончания переноса.

5. После того как подкласс готов, найдите все старые поля, которые управляли тем, какой набор функций должен выполняться. Эти поля можно удалить, заменив полиморфизмом все условные операторы, в которых они использовались.



###

```
class JobItem {
  private $quantity;
  private $unitPrice;
  private $employee; // Employee
  private $isLabor;

  public function __construct($quantity, $unitPrice, $isLabor, Employee $employee) {
    $this->quantity = $quantity;
    $this->unitPrice = $unitPrice;
    $this->isLabor = $isLabor;
    $this->employee = $employee;
  }
  public function getTotalPrice() {
    return $this->quantity * $this->getUnitPrice();
  }
  public function getQuantity() {
    return $this->quantity;
  }
  public function getUnitPrice() {
    return ($this->isLabor) ?
      $this->employee->getRate() : $this->unitPrice;
  }
  public function getEmployee() {
    return $this->employee;
  }
}

class Employee {
  private $rate;
  public function __construct($rate) {
    $this->rate = $rate;
  }
  public function getRate() {
    return $this->rate;
  }
}

// Somewhere in client code
$kent = new Employee(50);
$j1 = new JobItem(5, 0, true, kent);
$j2 = new JobItem(15, 10, false, null);
$total = $j1->getTotalPrice() + $j2->getTotalPrice();
```

###

```
abstract class JobItem {
  private $quantity;

  public function __construct($quantity) {
    $this->quantity = $quantity;
  }
  public function getTotalPrice() {
    return $this->quantity * $this->getUnitPrice();
  }
  public function getQuantity() {
    return $this->quantity;
  }
  public abstract function getUnitPrice();
}
class PartsItem extends JobItem {
  protected $unitPrice;

  public function __construct($quantity, $unitPrice) {
    parent::__construct($quantity);
    $this->unitPrice = $unitPrice;
  }
  public function getUnitPrice() {
    return $this->unitPrice;
  }
}
class LaborItem extends JobItem {
  protected $employee; // Employee

  public function __construct($quantity, Employee $employee) {
    parent::__construct($quantity);
    $this->employee = $employee;
  }
  public function getEmployee() {
    return $this->employee;
  }
  public function getUnitPrice() {
    return $employee->getRate();
  }
}

class Employee {
  private $rate;
  public function __construct($rate) {
    $this->rate = $rate;
  }
  public function getRate() {
    return $this->rate;
  }
}

// Somewhere in client code
$kent = new Employee(50);
$j1 = new LaborItem(5, kent);
$j2 = new PartsItem(15, 10);
$total = $j1->getTotalPrice() + $j2->getTotalPrice();
```

###

Set step 1

# Начнём с класса выполняемых работ, который определяет цены операций, выполняемых в местном гараже.

Select name of "getUnitPrice"

# Цена работы может состоять как из фиксированной цены (например, платы за заказ каких-то деталей), так и оплаты времени механика, цена которой может браться напрямую из класса <code>Employee</code>.

# При этом цена вычисляется разными способами в пределах одного класса, что меня и смущает.

# Было бы здорово выделить из этого класса подкласс <code>LaborItem</code>, и переместить в него все поведения, связанные с ручной работой, а в оригинальном классе оставить только расчёты фиксированной работы.

# Итак, создаём новый класс.

Go to after "JobItem"

Print:
```

class LaborItem extends JobItem {
}
```


Set step 2

# Теперь, начинаем спускать методы, свойственные только для подкласса.

Select name of "__construct" in "JobItem"

# Прежде всего, нам нужен конструктор для этого класса, потому что в <code>JobItem</code> нет нужного нам конструктора, который бы принимал только объект рабочего и количество потраченных часов.

# Пока что для этого скопируем сигнатуру родительского конструктора.

Go to the start of "LaborItem"

Print:
```

  public function __construct($quantity, $unitPrice, $isLabor, Employee $employee) {
    parent::__construct($quantity, $unitPrice, $isLabor, $employee);
  }
```

# Этого достаточно, чтобы новый подкласс перестал выдавать ошибки. Однако этот конструктор путаный: одни аргументы нужны для <code>LaborItem</code>, а другие – нет. Мы это исправим, но позднее.

Set step 3

Select 1st "new JobItem"

# На следующем этапе осуществляется поиск обращений к конструктору <code>JobItem</code> и случаи, когда вместо него следует вызывать конструктор <code>LaborItem</code>.

Print "new LaborItem"

# На данном этапе мы не трогаем тип переменной, а изменил лишь тип конструктора.

# Это вызвано тем, что новый тип следует использовать только там, где это необходимо. В данный момент у нас нет специфического интерфейса для подкласса, поэтому лучше пока не объявлять какие-либо разновидности.

Select parameters of "__construct" in "JobItem"
+ Select parameters of "__construct" in "LaborItem"

# Теперь подходящее время, чтобы привести в порядок списки параметров конструктора. К каждому из них применим <a href="/remove-parameter">удаление параметров</a>.

Select visibility of "__construct" in "JobItem"

# Сначала обращаемся к родительскому классу. Создаём новый конструктор и объявляем прежний защищенным (подклассу он по-прежнему нужен)

Go to "$isLabor|||, Employee $employee" in "JobItem"

Print " = false"

Go to "Employee $employee|||" in "JobItem"

Print " = null"

Select ", false, null"

# Внешние вызовы конструктора теперь должны использовать новый конструктор.

Remove selected

#C Выполним тестирование, чтобы удостовериться в отсутствии ошибок.

#S Всё отлично, можем продолжать.

Select "($quantity, |||$unitPrice, $isLabor, |||Employee $employee)"

# Теперь применим <a href="/remove-parameter">удаление параметров</a> к конструктору подкласса, чтобы избавиться от ненужных параметров.

Remove selected

Wait 500ms

Select "new LaborItem(5, |||0, true, |||kent)"

Remove selected

Wait 500ms

Select "parent::__construct($quantity, |||$unitPrice|||, $isLabor, $employee)"

Replace "0"

Wait 500ms

Select "parent::__construct($quantity, 0, |||$isLabor|||, $employee)"

Replace "true"

Set step 4

Select name of "JobItem"

# Теперь мы можем начать спускать в подкласс части <code>JobItem</code>. Сперва займёмся методами.

Select whole of "getEmployee"

# Начнём с применения <a href="/push-down-method">Спуска метода</a> к <code>getEmployee</code>.

Remove selected

Go to the end of "LaborItem"

Print:
```

  public function getEmployee() {
    return $this->employee;
  }
```

Select "|||private||| $employee;"

# Поскольку поле <code>employee</code> позднее будет спущено в подкласс, пока объявим его защищённым.

Print "protected"

Select ", Employee $employee = null" in "JobItem"
+ Select "|||, $employee|||)"


# После того как поле <code>employee</code> защищено, мы можем привести в порядок конструкторы, чтобы <code>employee</code> инициализировалось только в подклассе, куда оно спускается.

Remove selected

Select:
```
    $this->employee = $employee;

```

Remove selected

Go to the end of "__construct" in "LaborItem"

Print:
```

    $this->employee = $employee;
```

Set step 5

Select "private $isLabor;"

# Поле <code>isLabor</code> применяется для указания информации, которая теперь присуща иерархии, поэтому можно удалить это поле.<br/><br/>Лучше всего сделать это, сначала применив <a href="/self-encapsulate-field">Самоинкапсуляцию поля</a>, а затем изменив метод доступа, чтобы применить полиморфный константный метод (это такой метод, посредством которого каждая реализация возвращает (своё) фиксированное значение)

# Итак, объявим геттеры <code>isLabor</code> в обоих классах.

Go to the end of "JobItem"

Print:
```

  protected function isLabor() {
    return false;
  }
```

Go to the end of "LaborItem"

Print:
```

  protected function isLabor() {
    return true;
  }
```

# Теперь заменим использование поля вызовами этих геттеров.

Select "isLabor" in "getUnitPrice"

Replace "isLabor()"

Select:
```
  private $isLabor;

```
+ Select ", $isLabor = false"
+ Select "|||, true|||)"
+ Select:
```
    $this->isLabor = $isLabor;

```

# После этого можно избавиться от поля <code>isLabor</code>.

Remove selected

Select "isLabor" in "getUnitPrice"

# Теперь можно посмотреть на пользователей методов <code>isLabor</code>. Они должны быть подвергнуты рефакторингу <a href="/replace-conditional-with-polymorphism">Замена условного оператора полиморфизмом</a>.

Select body of "getUnitPrice"

Replace "    return $this->unitPrice;"

Wait 500ms

Go to the end of "LaborItem"

Print:
```

  public function getUnitPrice() {
    return $employee->getRate();
  }
```

Select whole "isLabor" in "JobItem"
+Select whole "isLabor" in "LaborItem"

# После этого становится видно, что методы <code>isLabor</code> теперь нигде не используются и их можно удалить из всех классов.

Remove selected

Select name of "JobItem"

# После того как группа методов, использующих некоторые данные, перемещена в подкласс, к этим данным можно применить <a href="/push-down-field">Спуск поля</a>. Если я не могу применить его из-за того, что эти данные используются некоторым методом, это свидетельствует о необходимости продолжить работу с методами, применяя <a href="/push-down-method">Спуск метода</a> или <a href="/replace-conditional-with-polymorphism">Замену условного оператора полиморфизмом</a>.

# В нашем случае, все готово, чтобы переместить поле <code>employee</code> в <code>LaborItem</code>.

Select:
```
  protected $employee; // Employee

```

Remove selected

Go to start of "LaborItem"

Print:
```

  protected $employee; // Employee

```

Select:
```
  private $unitPrice;
```

#C Выполним тестирование, чтобы удостовериться в отсутствии ошибок.

#S Всё отлично, можем продолжать.

# Итак, мы справились с извлечением <code>LaborItem</code>. Но есть ещё один момент. Поскольку цена запчастей <code>unitPrice</code> используется только в классе <code>JobItem</code>, и не нужна в <code>LaborItem</code>, мы можем пойти дальше и снова применить к <code>JobItem</code> <a href="/extract-subclass">Выделение подкласса</a> и создать класс, представляющий запчасти.

Go to after "JobItem"

Print:
```

class PartsItem extends JobItem {
  protected $unitPrice;

  public function __construct($quantity, $unitPrice) {
    parent::__construct($quantity);
    $this->unitPrice = $unitPrice;
  }
  public function getUnitPrice() {
    return $this->unitPrice;
  }
}
```

Wait 500ms

Select "new JobItem"

Replace "new PartsItem"

Wait 500ms

Select in "JobItem":
```
  private $unitPrice;

```
+Select ", 0"
+Select ", $unitPrice" in "JobItem"
+Select in "JobItem":
```
    $this->unitPrice = $unitPrice;

```

Remove selected

Wait 500ms

Select whole "getUnitPrice"

Replace:
```
  public abstract function getUnitPrice();

```


Select type of "JobItem"

# В итоге класс <code>JobItem</code> станет абстрактным.

Replace "abstract class"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.