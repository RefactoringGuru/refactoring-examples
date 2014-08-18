extract-superclass:php

###

1. Создайте абстрактный суперкласс.

2. Используйте <a href="/pull-up-field">подъём поля</a>, <a href="/pull-up-method">подъём метода</a> и <a href="/pull-up-constructor-body">подъём тела конструктора</a> для перемещения общей функциональности в суперкласс. Лучше начинать с полей, т.к. помимо общих полей, вам нужно будет перенести те из них, которые используются в общих методах.

3. Стоит поискать места в клиентском коде, в которых можно заменить использование подклассов вашим общим классом (например, в объявлениях типов).



###

```
class Employee {
  private $name;
  private $annualCost;
  private $id;

  public function __construct($name, $id, $annualCost) {
    $this->name = $name;
    $this->id = $id;
    $this->annualCost = $annualCost;
  }
  public function getAnnualCost() {
    return $this->annualCost;
  }
  public function getId() {
    return $this->id;
  }
  public function getName() {
    return $this->name;
  }
}

class Department {
  private $name;
  private $staff = array();

  public function __construct($name) {
    $this->name = $name;
  }
  public function getTotalAnnualCost() {
    $result = 0;
    foreach ($this->staff as $each) {
      $result += $each->getAnnualCost();
    }
    return $result;
  }
  public function getHeadCount() {
    return count($staff);
  }
  public function getStaff() {
    return $this->staff;
  }
  public function addStaff(Employee $arg) {
    $this->staff[] = $arg;
  }
  public function getName() {
    return $this->name;
  }
}
```

###

```
abstract class Party {
  protected $name;

  protected function __construct($name) {
    $this->name = $name;
  }
  public function getName() {
    return $this->name;
  }
  public abstract function getAnnualCost();
  public abstract function getHeadCount();
}

class Employee extends Party {
  private $annualCost;
  private $id;

  public function __construct($name, $id, $annualCost) {
    parent::__construct($name)
    $this->id = $id;
    $this->annualCost = $annualCost;
  }
  public function getAnnualCost() {
    return $this->annualCost;
  }
  public function getId() {
    return $this->id;
  }
  public function getHeadCount() {
    return 1;
  }
}

class Department extends Party {
  private $staff = array();

  public function __construct($name) {
    parent::__construct($name)
  }
  public function getAnnualCost() {
    $result = 0;
    foreach ($this->staff as $each) {
      $result += $each->getAnnualCost();
    }
    return $result;
  }
  public function getHeadCount() {
    $headCount = 0;
    foreach ($this->staff as $each) {
      $headCount += $each->getHeadCount();
    }
  }
  public function getStaff() {
    return $this->staff;
  }
  public function addStaff(Party $arg) {
    $this->staff[] = $arg;
  }
}
```

###

Set step 1

Select name of "Employee"
+ Select name of "Department"

# Рассмотрим этот рефакторинг на примере классов служащих и отдела.

Select "private $name"

# У этих классов есть несколько общих черт. Во-первых, как у служащих, так и у отделов есть имена или названия.

Select "private $annualCost"
+ Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"

# Во-вторых, для обоих есть годовой бюджет (annualcost), хотя методы их расчёта слегка различаются.

# Посему, имеет смысл выделить эти моменты в общий родительский класс.

Go to before "Employee"

# Итак, на первом этапе создаём новый родительский класс, а имеющиеся классы определяем как его подклассы.

Print:
```
abstract class Party {
}


```

Go to "class Employee|||"

Print " extends Party"

Wait 500ms

Go to "class Department|||"

Print " extends Party"

Select:
```
  private $name;
```

# Теперь всё готово, чтобы начать поднимать функции в родительский класс. Обычно проще сначала выполнить <a href="/pull-up-field">Подъем полей</a>.

Go to start of "Party"

Print:
```

  protected $name;
```

Select:
```
  private $name;

```

Remove selected

Select whole of "getName"

# После этого можно применить <a href="/pull-up-method">Подъем метода</a> к методам доступа этого поля.

Go to end of "Party"

Print:
```


  public function getName() {
    return $this->name;
  }
```

Wait 500ms

Select in "Employee":
```

  public function getName() {
    return $this->name;
  }
```
+ Select in "Department":
```

  public function getName() {
    return $this->name;
  }
```

Remove selected

Select:
```
$this->name = $name;
```

# Лучше, чтобы поля были защищёнными от публики, но для этого сперва нужно выполнить <a href="/pull-up-constructor-body">Подъем тела конструктора</a>, чтобы инициализировать их.

Go to before "getName" in "Party"

Print:
```

  protected function __construct($name) {
    $this->name = $name;
  }
```

# В подклассах теперь можно убрать инициализацию поля, заменив её вызовами родительского конструктора.

Select "$this->name = $name;" in "Employee"
+ Select "$this->name = $name;" in "Department"

Replace "parent::__construct($name)"

# На этом перенос имени окончен и можно взяться за годовой бюджет.

Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"

# Методы <code>getTotalAnnualCost</code> и <code>getAnnualCost</code> имеют одинаковое назначение, поэтому у них должно быть одинаковое название. Сначала применим <a href="/rename-method">Переименование метода</a>, чтобы привести их к одному и тому же названию.

Select name of "getTotalAnnualCost"

Replace "getAnnualCost"

# тела методов пока что различаются, поэтому мы не можем применить <a href="/pull-up-method">Подъем метода</a>. С другой стороны, мы можем объявить в родительском классе абстрактный метод с таким же именем.

Go to the end of "Party"

Print:
```

  public abstract function getAnnualCost();
```

Select name of "Party"

# Осуществив все эти изменения, я рассматриваю клиентов обоих классов, чтобы выяснить, можно ли изменить их так, чтобы они использовали новый родительский класс.

Select body of "getAnnualCost" in "Department"

# Одним из клиентов этих классов является сам класс <code>Department</code>, содержащий коллекцию классов служащих. Метод <code>getAnnualCost</code> использует только метод подсчёта годового бюджета, который теперь объявлен в <code>Party</code>.

# Такое поведение открывает новую возможность. Я могу рассматривать применение паттерна <a href="http://sourcemaking.com/design_patterns/composite">компоновщик</a> к <code>Department</code> и <code>Employee</code>.

# Это позволит мне включать один отдел в другой. В результате создаётся новая функциональность, так что нельзя, строго говоря, назвать это рефакторингом.

# Тем не менее, если бы требовалась компоновка, я получил бы её, изменив тип поля <code>staff</code>, чтобы картина была нагляднее.

Select "Employee" in "Department"

# Такое изменение повлекло бы соответствующее изменение имени <code>addStaff</code> и замену параметра на <code>Party</code>.

Print "Party"

Select body of "getHeadCount"

# В окончательной редакции метод <code>headCount</code> должен быть сделан рекурсивным.

Print:
```
    $headCount = 0;
    foreach ($this->staff as $each) {
      $headCount += $each->getHeadCount();
    }
```

# Но для того, чтобы этот метод заработал, необходимо объявить аналогичный метод в <code>Employee</code>, который просто возвращает <code>1</code>.

Go to the end of "Employee"

Print:
```

  public function getHeadCount() {
    return 1;
  }
```

Go to the end of "Party"

# Этот метод теперь тоже следует объявить абстрактным в родительском классе.

Print:
```

  public abstract function getHeadCount();
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.