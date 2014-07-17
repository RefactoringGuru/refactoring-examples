replace-type-code-with-state-strategy:java

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
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  private int type;

  public Employee(int arg) {
    type = arg;
  }
  int payAmount() {
    switch (type) {
      case ENGINEER:
        return monthlySalary;
      case SALESMAN:
        return monthlySalary + commission;
      case MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Не тот служащий");
    }
  }
}
```

###

```
class Employee {
  // ...
  private EmployeeType type;

  public Employee(int arg) {
    type = arg;
  }
  int getType() {
    return type.getTypeCode();
  }
  void setType(int arg) {
    type = EmployeeType.newType(arg);
  }
  int payAmount() {
    return type.payAmount(this);
  }
}

abstract class EmployeeType {
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract int getTypeCode();
  static EmployeeType newType(int code) {
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
  int getTypeCode () {
    return EmployeeType.ENGINEER;
  }
  int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
}
class Salesman extends EmployeeType {
  int getTypeCode () {
    return EmployeeType.SALESMAN;
  }
  int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
}
class Manager extends EmployeeType {
  int getTypeCode () {
    return EmployeeType.MANAGER;
  }
  int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
}
```

###


Set step 1

# Рассмотрим рефакторинг <i>Замена кодирования типа состоянием/стратегией</i> на примере всё того же класса зарплаты служащего. У нас есть несколько типов служащих, в зависимости от чего, вычисляется размер их зарплаты.

Select "private int |||type|||"

# Начнем с <a href="/self-encapsulate-field">самоинкапсуляции поля</a> типа служащего.

Go to after "public Employee"

Print:
```

  int getType() {
    return type;
  }
  void setType(int arg) {
    type = arg;
  }
```

Select:
```
  void setType(int arg) {
    type = arg;
  }
```

# Предполагается, что это замечательная, прогрессивная компания, позволяющая менеджерам вырастать до инженеров. Поэтому код типа изменяемый, и применять подклассы для избавления от кодирования типа нельзя, что приводит нас к применении паттерна <a href="http://sourcemaking.com/design_patterns/state">Состояние</a>.

Set step 2

Go to the end of file

# Итак, объявим класс состояния (как абстрактный класс с абстрактным методом возврата кода типа).

Print:
```


abstract class EmployeeType {
  abstract int getTypeCode();
}
```

Set step 3

# Теперь создадим подклассы для каждого из типов служащих.


Print:
```

class Engineer extends EmployeeType {
  int getTypeCode () {
    return Employee.ENGINEER;
  }
}
class Salesman extends EmployeeType {
  int getTypeCode () {
    return Employee.SALESMAN;
  }
}
class Manager extends EmployeeType {
  int getTypeCode () {
    return Employee.MANAGER;
  }
}
```

Set step 4

Go to the end of "EmployeeType"

# Теперь создадим статический метод в классе состояния, который будет возвращать экземпляр нужного подкласса в зависимости от подаваемого значения.

Print:
```

  static EmployeeType newType(int code) {
    switch (code) {
      case Employee.ENGINEER:
        return new Engineer();
      case Employee.SALESMAN:
        return new Salesman();
      case Employee.MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Incorrect Employee Code");
    }
  }
```

Select "switch (code)"

# Как видите, мы вносим здесь большой оператор <code>switch</code>. Это не очень хорошая новость, но зато по завершению рефакторинга этот оператор окажется единственным в коде и будет выполняться только при изменении типа.

#C Запустим компиляцию и тестирование, чтобы убедиться в отсутствии ошибок.

#S Всё отлично, можно продолжать.

Set step 5

Select "private |||int||| type"

# Теперь нужно фактически подключить созданные подклассы к <code>Employee</code>, модифицируя методы доступа к коду типа.

Print "EmployeeType"

Wait 500ms

Select:
```
  int getType() {
    return |||type|||;
  }
```

Wait 500ms

Print "type.getTypeCode()"

Wait 500ms

Select:
```
  void setType(int arg) {
    type = |||arg|||;
  }
```

# Тело сеттера меняем на вызов фабричного метода.

Print "EmployeeType.newType(arg)"

# В завершение этого шага, можно перенести все константы кода типа из <code>Employee</code> в <code>EmployeeType</code>

Select:
```

  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

```

Remove selected

Go to the beginning of "EmployeeType"

Print:
```

  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

```

Wait 500ms

Select:
```
      case||| |||ENGINEER:
        return monthlySalary;
      case||| |||SALESMAN:
        return monthlySalary + commission;
      case||| |||MANAGER:
```

Print " EmployeeType."

Wait 500ms

Select "|||Employee|||.ENGINEER" in "Engineer"
+ Select "|||Employee|||.MANAGER" in "Manager"
+ Select "|||Employee|||.SALESMAN" in "Salesman"

Wait 500ms

Type "EmployeeType"

Wait 500ms

Select "Employee." in "newType"

Remove selected

Set step 6

# На этом этапе всё готово, чтобы начать перемещать методы и поля, нужные только для определённых типов служащих, в соответствующие классы.

Select "payAmount()"

# В нашем случае, создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплаты для соответствующих типов служащих.

Go to the end of "class Engineer"

Print:
```

  int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
```

Wait 1000ms

Go to the end of "class Salesman"

Print:
```

  int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
```

Wait 1000ms


Go to the end of "class Manager"

Print:
```

  int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
```

Set step 7

Select:
```
    switch (type) {
      case EmployeeType.ENGINEER:
        return monthlySalary;
      case EmployeeType.SALESMAN:
        return monthlySalary + commission;
      case EmployeeType.MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Не тот служащий");
    }
```

# После того как методы созданы, можно применить <a href="/replace-conditional-with-polymorphism">замену условного оператора полиморфизмом</a> к оператору <code>switch</code> в методе <code>payAmount</code>.

Print:
```
    return type.payAmount(this);
```


#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
