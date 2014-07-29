replace-conditional-with-polymorphism:php

###

1. Если условный оператор находится в методе, который выполняет ещё какие-то действия, <a href="/extract-method">извлеките его в новый метод</a>.

2. Для каждого подкласса иерархии, переопределите метод, содержащий условный оператор, и скопируйте туда код соответствующей ветки оператора.

3. Удалите эту ветку из условного оператора.

4. Повторяйте замену, пока условный оператор не опустеет. Затем удалите условный оператор и объявите метод абстрактным.



###

```
class Employee {
  // ...  
  private $type; // EmployeeType
  public function getTypeCode() {
    return $this->type->getTypeCode();
  }

  public $monthlySalary;
  public $commission;
  public $bonus;
  public function payAmount() {
    switch ($this->getTypeCode()) {
      case EmployeeType::$ENGINEER:
        return $this->monthlySalary;
      case EmployeeType::$SALESMAN:
        return $this->monthlySalary + $this->commission;
      case EmployeeType::$MANAGER:
        return $this->monthlySalary + $this->bonus;
      default:
        throw new Exception("Incorrect Employee Code");
    }
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
}
class Salesman extends EmployeeType {
  public function getTypeCode() {
    return EmployeeType::$SALESMAN;
  }
}
class Manager extends EmployeeType {
  public function getTypeCode() {
    return EmployeeType::$MANAGER;
  }
}
```

###

```
class Employee {
  // ...  
  private $type; // EmployeeType
  public function getTypeCode() {
    return $this->type->getTypeCode();
  }

  public $monthlySalary;
  public $commission;
  public $bonus;
  public function payAmount() {
    return $type->payAmount($this);
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

  abstract public function payAmount(Employee $employee);
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

# Этот рефакторинг мы рассмотрим на примере кода классов рассчёта зарплаты для разных типов служащих (см. <a href="/replace-type-code-with-state-strategy">замены кодирования типом состоянием/стратегией</a>).

Select body of "payAmount"

# Давайте попытаемся избавиться от условного оператора внутри метода <code>payAmount()</code>.

# Сперва выделим реализацию <code>payAmount</code> в новый метод в классе типа <code>EmployeeType</code>. Сделаем это, чтобы иметь общую точку доступа к данному методу в подклассах.

Go to the end of "EmployeeType"

Print:
```


  public function payAmount() {
    switch ($this->getTypeCode()) {
      case EmployeeType::$ENGINEER:
        return $this->monthlySalary;
      case EmployeeType::$SALESMAN:
        return $this->monthlySalary + $this->commission;
      case EmployeeType::$MANAGER:
        return $this->monthlySalary + $this->bonus;
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
```

Select "monthlySalary" in "EmployeeType"
+Select "commission" in "EmployeeType"
+Select "bonus" in "EmployeeType"

# Нам нужны данные из объекта <code>Employee</code>, поэтому создадим в методе параметр, в который будет передаваться основной объект <code>Employee</code>.

Go to "payAmount(|||) {" in "EmployeeType"

Print "Employee $employee"

Select "$this->monthlySalary" in "EmployeeType"

Print "$employee->monthlySalary"

Select "$this->commission" in "EmployeeType"

Print "$employee->commission"

Select "$this->bonus" in "EmployeeType"

Print "$employee->bonus"

Select body of "payAmount"

# После этих действий, мы можем настроить делегирование из класса <code>Employee</code>.

Print "    return $type->payAmount($this);"

Set step 2

Select name of "payAmount"

# После этого займёмся перемещением кода в подклассы. Создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплаты для соответствующих типов служащих.

Go to the end of "class Engineer"

Print:
```

  public function payAmount(Employee $employee) {
    return $employee->monthlySalary;
  }
```

Wait 1000ms

Set step 3

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

Set step 4

Select body of "payAmount"

# После того как методы созданы, можно сделать метод <code>payAmount</code> в <code>EmployeeType</code> абстрактным.

Select:
```
  public function payAmount(Employee $employee) {
    switch ($this->getTypeCode()) {
      case EmployeeType::$ENGINEER:
        return $employee->monthlySalary;
      case EmployeeType::$SALESMAN:
        return $employee->monthlySalary + $employee->commission;
      case EmployeeType::$MANAGER:
        return $employee->monthlySalary + $employee->bonus;
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
```

Print:
```
  abstract public function payAmount(Employee $employee);
```

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.