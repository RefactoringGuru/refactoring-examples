replace-conditional-with-polymorphism:java

###

1. Если условный оператор находится в методе, который выполняет ещё какие-то действия, <a href="/extract-method">извлеките его в новый метод</a>.

2. Для каждого подкласса иерархии, переопределите метод, содержащий условный оператор, и скопируйте туда код соответствующей ветки оператора.

3. Удалите эту ветку из условного оператора.

4. Повторяйте замену, пока условный оператор не опустеет. Затем удалите условный оператор и объявите метод абстрактным.



###

```
class Employee {
  // ...
  private EmployeeType type;
  public int getTypeCode() {
    return type.getTypeCode();
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
  public int payAmount() {
    switch (getTypeCode()) {
      case EmployeeType.ENGINEER:
        return monthlySalary;
      case EmployeeType.SALESMAN:
        return monthlySalary + commission;
      case EmployeeType.MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
}

abstract class EmployeeType {
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract public int getTypeCode();
  public static EmployeeType newType(int code) {
    switch (code) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Incorrect Employee Code");
    }
  }
}
class Engineer extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.ENGINEER;
  }
}
class Salesman extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.SALESMAN;
  }
}
class Manager extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.MANAGER;
  }
}
```

###

```
class Employee {
  // ...
  private EmployeeType type;
  public int getTypeCode() {
    return type.getTypeCode();
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
  public int payAmount() {
    return type.payAmount(this);
  }
}

abstract class EmployeeType {
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract public int getTypeCode();
  public static EmployeeType newType(int code) {
    switch (code) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Incorrect Employee Code");
    }
  }

  abstract public int payAmount(Employee employee)
}
class Engineer extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.ENGINEER;
  }
  public int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
}
class Salesman extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.SALESMAN;
  }
  public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
}
class Manager extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.MANAGER;
  }
  public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
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


  public int payAmount() {
    switch (getTypeCode()) {
      case EmployeeType.ENGINEER:
        return monthlySalary;
      case EmployeeType.SALESMAN:
        return monthlySalary + commission;
      case EmployeeType.MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
```

Select "monthlySalary" in "EmployeeType"
+Select "commission" in "EmployeeType"
+Select "bonus" in "EmployeeType"

# Нам нужны данные из объекта <code>Employee</code>, поэтому создадим в методе параметр, в который будет передаваться основной объект <code>Employee</code>.

Go to "public int payAmount(|||) {" in "EmployeeType"

Print "Employee employee"

Select "monthlySalary" in "EmployeeType"

Print "employee.monthlySalary"

Select "commission" in "EmployeeType"

Print "employee.commission"

Select "bonus" in "EmployeeType"

Print "employee.bonus"

Select body of "payAmount"

# После этих действий, мы можем настроить делегирование из класса <code>Employee</code>.

Print "    return type.payAmount(this);"

Set step 2

Select name of "payAmount"

# После этого займёмся перемещением кода в подклассы. Создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплаты для соответствующих типов служащих.

Go to the end of "class Engineer"

Print:
```

  public int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
```

Wait 1000ms

Set step 3

Go to the end of "class Salesman"

Print:
```

  public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
```

Wait 1000ms


Go to the end of "class Manager"

Print:
```

  public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
```

Set step 4

Select body of "payAmount"

# После того как методы созданы, можно сделать метод <code>payAmount</code> в <code>EmployeeType</code> абстрактным.

Select:
```
  public int payAmount(Employee employee) {
    switch (getTypeCode()) {
      case EmployeeType.ENGINEER:
        return employee.monthlySalary;
      case EmployeeType.SALESMAN:
        return employee.monthlySalary + employee.commission;
      case EmployeeType.MANAGER:
        return employee.monthlySalary + employee.bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
```

Print:
```
  abstract public int payAmount(Employee employee);
```


#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.