replace-type-code-with-subclasses:java

###

1. Используйте <a href="/self-encapsulate-field">самоинкапсуляцию поля</a> для создания геттера для поля, которое содержит кодирование типа.

2. Сделайте конструктор суперкласса приватным. Создайте статический фабричный метод с теми же параметрами, что и конструктор суперкласса.

3. Для каждого значения кодированного типа, создайте свой подкласс. В нем переопределите геттер закодированного поля так, чтобы он возвращал соответствующее значение закодированного типа.

4. Удалите поле с закодированным типом из суперкласса, его геттер сделайте абстрактным.

5. Теперь, когда у вас появились подклассы, можете начинать перемещать поля и методы из суперкласса в соответствующие подклассы.

6. Когда все что можно перемещено, используйте <a href="/replace-conditional-with-polymorphism">замену условных операторов полиморфизмом</a>, чтобы окончательно избавиться от условных операторов, использующий закодированный тип.



###

```
class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  public int type;

  public Employee(int arg) {
    type = arg;
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
  public int payAmount() {
    switch (type) {
      case ENGINEER:
        return monthlySalary;
      case SALESMAN:
        return monthlySalary + commission;
      case MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
}
```

###

```
abstract class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract public int getType();

  public static Employee create(int type) {
    switch (type) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manger();
    }
  }
  private Employee(int arg) {
    type = arg;
  }

  public int monthlySalary;
  public int payAmount() {
    return monthlySalary;
  }
}

class Engineer extends Employee {
  public int getType() {
    return Employee.ENGINEER;
  }
}

class Salesman extends Employee {
  public int commission;
  public int getType() {
    return Employee.SALESMAN;
  }
  public int payAmount() {
    return monthlySalary + commission;
  }
}

class Manager extends Employee {
  public int bonus;
  public int getType() {
    return Employee.MANAGER;
  }
  public int payAmount() {
    return monthlySalary + bonus;
  }
}
```

###

Set step 1

# Рассмотрим рефакторинг <i>Замена кодирования типа подклассами</i> на примере класса зарплаты служащего. У нас есть несколько типов служащих, в зависимости от которых вычисляется размер зарплаты.

Select "public int |||type|||"
+ Select name of "payAmount"

# Начнём с <a href="/self-encapsulate-field">самоинкапсуляции поля</a> типа служащего.

Select "|||public||| int type"

Replace "private"

Go to before "public Employee"

Print:
```

  public int getType() {
    return type;
  }

```

Select "switch (|||type|||)"

Replace "getType()"

Set step 2

Go to before "public Employee"

# Поскольку конструктор <code>Employee</code> использует код типа в качестве параметра, надо заменить его фабричным методом.

Print:
```

  public static Employee create(int type) {
    return new Employee(type);
  }
```

Wait 500ms

Select "|||public||| Employee"

Replace "private"

Set step 3

Select "ENGINEER"

# Теперь можно приступить к преобразованию <code>Engineer</code> в подкласс. Сначала создаётся сам подкласс…

Go to the end of file

Print:
```


class Engineer extends Employee {
}
```

Go to the end of "Engineer"

# …а потом замещающий метод для кода типа.

Print:
```

  public int getType() {
    return Employee.ENGINEER;
  }
```

Select body of "create"

# Необходимо также заменить фабричный метод, чтобы он создавал надлежащий объект.

Print:
```
    switch (type) {
      case ENGINEER:
        return new Engineer();
      default:
        return new Employee(type);
    }
```

Go to the end of file

# Продолжаем эти действия поочерёдно, пока все коды не будут заменены подклассами.

Print:
```


class Salesman extends Employee {
  public int getType() {
    return Employee.SALESMAN;
  }
}
```

Go to:
```
      case ENGINEER:
        return new Engineer();|||
```

Print:
```

      case SALESMAN:
        return new Salesman();
```

Wait 1000ms

Go to the end of file

Print:
```


class Manager extends Employee {
  public int getType() {
    return Employee.MANAGER;
  }
}
```

Go to:
```
      case SALESMAN:
        return new Salesman();|||
```

Print:
```

      case MANAGER:
        return new Manger();
```

Select:
```
  private int type;


```

Set step 4

# После этого можно избавиться от поля с кодом типа в <code>Employee</code>...

Remove selected


Go to:
```
  |||public int getType() {
    return type;
  }
```

# ...и сделать <code>getType</code> абстрактным методом.

Print "abstract "

Select:
```
  abstract public int getType()||| {
    return type;
  }|||
```

Replace ";"

Go to before "Employee"

# Это сделает и класс <code>Employee</code> абстрактным.

Print "abstract "

# После этих изменений, мы больше не можем создавать объекты <code>Employee</code> как реализацию по умолчанию, поэтому важно помнить, что избавляться от поля типа стоит только после создания всех подклассов.

Select:
```
      default:
        return new Employee(type);

```

Remove selected


Select "switch (type) {" in "create"

# Обратите внимание, что в итоге мы создали ещё один большой оператор <code>switch</code>. Вообще  –  <a href="/smells/switch-statements">это плохо</a>, но после завершения рефакторинга он будет единственным оставшимся в коде.

Set step 5

# Теперь, после создания подклассов, следует применить <a href="/push-down-method">Спуск метода</a> и <a href="/push-down-field">Спуск поля</a> ко всем методам и полям, которые относятся только к конкретным типам служащих.

Select:
```
  public int monthlySalary;
  public int commission;
  public int bonus;
```
+ Select name of "payAmount"

# В нашем случае создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплаты для соответствующих типов служащих.

Select:
```
  public int commission;

```

Remove selected

Go to the start of "Salesman"

Print:
```

  public int commission;
```

Select:
```
      case SALESMAN:
        return monthlySalary + commission;

```

Remove selected

Go to the end of "Salesman"

Print:
```

  public int payAmount() {
    return monthlySalary + commission;
  }
```

Wait 500ms

Select:
```
  public int bonus;

```

# Проделаем те же действия с классом менеджеров.

Remove selected

Go to the start of "Manager"

Print:
```

  public int bonus;
```

Select:
```
      case MANAGER:
        return monthlySalary + bonus;

```

Remove selected


Go to the end of "Manager"

Print:
```

  public int payAmount() {
    return monthlySalary + bonus;
  }
```

Set step 6

Select body of "payAmount"

# После перемещения всего кода по подклассам вы можете либо объявить метод в суперклассе абстрактным, либо оставить в нём реализацию по умолчанию (так и сделаем).

Print:
```
    return monthlySalary;
```



#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.