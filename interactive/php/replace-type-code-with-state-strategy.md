replace-type-code-with-state-strategy:php

###

1. Используйте <a href="self-encapsulate-field">самоинкапсуляцию поля</a> для создания геттера для поля, которое содержит кодирование типа.

2. Создайте новый класс, который будет играть роль <i>состояния</i> (или <i>стратегии</i>). Создайте в нем абстрактный геттер закодированного поля.

3. Создайте подклассы состояния для каждого значения закодированного типа.

4. В абстрактном классе состояния, создайте статический фабричный метод, принимающий в параметре значение закодированного типа. В зависимости от этого параметра, фабричные метод будет создавать объекты различных состояний. Для этого в его коде придётся создать большой условный оператор, но он будет единственным по завершению рефакторинга.

5. В исходном классе, поменяйте тип закодированного поля на класс-состояние. В сеттере этого поля, вызывайте фабричный метод состояния для получения новых объектов состояний.

6. Переместите поля и методы из суперкласса в соответствующие подклассы-состояния.

7. Когда все что можно перемещено, используйте <a href="/replace-conditional-with-polymorphism">замену условного оператора полиморфизмом</a>, чтобы окончательно избавиться от условных операторов, использующий закодированный тип.



###

```
class Employee {
  // ...
  static $ENGINEER = 0;
  static $SALESMAN = 1;
  static $MANAGER = 2;
  
  public $type;

  public function __construct($arg) {
    $this->type = $arg;
  }

  public $monthlySalary;
  public $commission;
  public $bonus;
  public function payAmount() {
    switch ($this->type) {
      case self::$ENGINEER:
        return $this->monthlySalary;
      case self::$SALESMAN:
        return $this->monthlySalary + $this->commission;
      case self::$MANAGER:
        return $this->monthlySalary + $this->bonus;
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
}
```

###

```
class Employee {
  // ...
  private $type; // EmployeeType

  public function __construct($arg) {
    $this->type = EmployeeType::newType($arg);
  }
  public function getType() {
    return $this->type->getTypeCode();
  }
  public function setType($arg) {
    $this->type = EmployeeType::newType($arg);
  }

  public $monthlySalary;
  public $commission;
  public $bonus;
  public function payAmount() {
    return $this->type->payAmount($this);
  }
}

abstract class EmployeeType {
  static $ENGINEER = 0;
  static $SALESMAN = 1;
  static $MANAGER = 2;

  abstract public function getTypeCode();
  public static function newType($code) {
    switch ($code) {
      case self::$ENGINEER:
        return new Engineer();
      case self::$SALESMAN:
        return new Salesman();
      case self::$MANAGER:
        return new Manager();
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
}
class Engineer extends EmployeeType {
  public function getTypeCode() {
    return EmployeeType::$ENGINEER;
  }
  public function payAmount(Employee $employee) {
    return $employee->monthlySalary;
  }
}
class Salesman extends EmployeeType {
  public function getTypeCode() {
    return EmployeeType::$SALESMAN;
  }
  public function payAmount(Employee $employee) {
    return $employee->monthlySalary + $employee->commission;
  }
}
class Manager extends EmployeeType {
  public function getTypeCode() {
    return EmployeeType::$MANAGER;
  }
  public function payAmount(Employee $employee) {
    return $employee->monthlySalary + $employee->bonus;
  }
}
```

###

Set step 1

# Рассмотрим рефакторинг <i>Замена кодирования типа состоянием/стратегией</i> на примере всё того же класса зарплаты служащего. У нас есть несколько типов служащих, в зависимости от чего, вычисляется размер их зарплаты.

Select "public |||$type|||"

# Начнем с <a href="/self-encapsulate-field">самоинкапсуляции поля</a> типа служащего.

Select "|||public||| $type"

Wait 500ms

Print "private"

Go to after "__construct"

Print:
```

  public function getType() {
    return $this->type;
  }
  public function setType($arg) {
    $this->type = $arg;
  }
```

Select whole "setType"

# Предполагается, что это замечательная, прогрессивная компания, позволяющая менеджерам вырастать до инженеров. Поэтому код типа изменяемый, и применять подклассы для избавления от кодирования типа нельзя, что приводит нас к применении паттерна <a href="http://sourcemaking.com/design_patterns/state">Состояние</a>.

Set step 2

Go to the end of file

# Итак, объявим класс состояния (как абстрактный класс с абстрактным методом возврата кода типа).

Print:
```


abstract class EmployeeType {
  abstract public function getTypeCode();
}
```

Set step 3

# Теперь создадим подклассы для каждого из типов служащих.


Print:
```

class Engineer extends EmployeeType {
  public function getTypeCode() {
    return Employee::$ENGINEER;
  }
}
class Salesman extends EmployeeType {
  public function getTypeCode() {
    return Employee::$SALESMAN;
  }
}
class Manager extends EmployeeType {
  public function getTypeCode() {
    return Employee::$MANAGER;
  }
}
```

Set step 4

Go to the end of "EmployeeType"

# Теперь создадим статический метод в классе состояния, который будет возвращать экземпляр нужного подкласса в зависимости от подаваемого значения.

Print:
```

  public static function newType($code) {
    switch ($code) {
      case Employee::$ENGINEER:
        return new Engineer();
      case Employee::$SALESMAN:
        return new Salesman();
      case Employee::$MANAGER:
        return new Manager();
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
```

Select "switch ($code)"

# Как видите, мы вносим здесь большой оператор <code>switch</code>. Это не очень хорошая новость, но зато по завершению рефакторинга этот оператор окажется единственным в коде и будет выполняться только при изменении типа.

#C Запустим тестирование, чтобы убедиться в отсутствии ошибок.

#S Всё отлично, можно продолжать.

Set step 5

Go to "private $type;|||"

# Теперь нужно фактически подключить созданные подклассы к <code>Employee</code>, модифицируя методы доступа к коду типа и конструктор.

Print " // EmployeeType"

Wait 500ms

Select:
```
  public function getType() {
    return $this->|||type|||;
  }
```

Wait 500ms

Print "type->getTypeCode()"

Wait 500ms

Select:
```
    $this->type = |||$arg|||;
```

# Тело сеттера и конструктора меняем на вызов фабричного метода.

Print "EmployeeType::newType($arg)"


Select:
```

  static $ENGINEER = 0;
  static $SALESMAN = 1;
  static $MANAGER = 2;

```

# В завершение этого шага, можно перенести все константы кода типа из <code>Employee</code> в <code>EmployeeType</code>

Remove selected

Go to the beginning of "EmployeeType"

Print:
```

  static $ENGINEER = 0;
  static $SALESMAN = 1;
  static $MANAGER = 2;

```

Wait 500ms

Select "|||Employee|||::$" in "newType"

Print "self"

Wait 500ms

Select "|||self|||::$" in "payAmount"

Print "EmployeeType"

Wait 500ms

Select "|||Employee|||::$" in "Engineer"
+ Select "|||Employee|||::$" in "Manager"
+ Select "|||Employee|||::$" in "Salesman"

Wait 500ms

Type "EmployeeType"

Set step 6

# На этом этапе всё готово, чтобы начать перемещать методы и поля, нужные только для определённых типов служащих, в соответствующие классы.

Select name of "payAmount"

# В нашем случае, создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплаты для соответствующих типов служащих.

Go to the end of "class Engineer"

Print:
```

  public function payAmount(Employee $employee) {
    return $employee->monthlySalary;
  }
```

Wait 1000ms

Go to the end of "class Salesman"

Print:
```

  public function payAmount(Employee $employee) {
    return $employee->monthlySalary + $employee->commission;
  }
```

Wait 1000ms


Go to the end of "class Manager"

Print:
```

  public function payAmount(Employee $employee) {
    return $employee->monthlySalary + $employee->bonus;
  }
```

Set step 7

Select body of "payAmount"

# После того как методы созданы, можно применить <a href="/replace-conditional-with-polymorphism">замену условного оператора полиморфизмом</a> к оператору <code>switch</code> в методе <code>payAmount</code>.

Print:
```
    return $this->type->payAmount($this);
```

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.