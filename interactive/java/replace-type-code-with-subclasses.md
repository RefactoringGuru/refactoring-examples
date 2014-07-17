replace-type-code-with-subclasses:java

###

1. Используйте <a href="self-encapsulate-field">самоинкапсуляцию поля</a> для создания геттера для поля, которое содержит кодирование типа.

2. Сделайте конструктор суперкласса приватным. Создайте статический фабричный метод с теми же параметрами, что и конструктор суперкласса.

3. Для каждого значения кодированного типа, создайте свой подкласс. В нем переопределите геттер закодированного поля так, чтобы он возвращал соответствующее значение закодированного типа.

4. Удалите поле с закодированным типом из суперкласса, его геттер сделайте абстрактным.

5. Теперь, когда у вас появились подклассы, можете начинать перемещать поля и методы из суперкласса в соответствующие подклассы.

6. Когда все что можно перемещено, используйте <a href="replace-conditional-with-polymorphism">замену условных операторов полиморфизмом</a>, чтобы окончательно избавиться от условных операторов, использующий закодированный тип.

###

```
class Employee {
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  private int type;

  public Employee(int arg) {
    type = arg;
  }
}
```

###

```
class Employee {
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract int getType();

  static Employee create(int type) {
    if (type == ENGINEER) {
      return new Engineer();
    }
    else if (type == SALESMAN) {
      return new Salesman();
    }
    else if (type == MANAGER) {
      return new Manger();
    }
    else {
      return new Employee(type);
    }
  }
  private Employee(int arg) {
    type = arg;
  }
}

class Engineer extends Employee {
  int getType() {
    return Employee.ENGINEER;
  }
}

class Salesman extends Employee {
  int getType() {
    return Employee.SALESMAN;
  }
}

class Manager extends Employee {
  int getType() {
    return Employee.MANAGER;
  }
}
```

###

Set step 1

# Рассмотрим рефакторинг <i>Замена кодирования типа подклассами</i> на примере класса зарплаты служащего. У нас есть несколько типов служащих, в зависимости от чего, вычисляется размер зарплаты.

Select "private int |||type|||"

# Начнём с <a href="/self-encapsulate-field">самоинкапсуляции поля</a> типа служащего.

Go to before "public Employee"

Print:
```

  int getType() {
    return type;
  }

```

Set step 2

Go to before "public Employee"

# Поскольку конструктор <code>Employee</code> использует код типа в качестве параметра, надо заменить его фабричным методом.

Print:
```

  static Employee create(int type) {
    return new Employee(type);
  }

```

Wait 500ms

Select "|||public||| Employee"

Wait 500ms

Print "private"

Set step 3

Select "ENGINEER"

# Теперь можно приступить к преобразованию <code>Engineer</code> в подкласс. Сначала создаётся сам подкласс.

Go to the end of file

Print:
```


class Engineer extends Employee {
}
```

Go to the end of "Engineer"

# А потом, замещающий метод для кода типа.

Print:
```

  int getType() {
    return Employee.ENGINEER;
  }
```

Select:
```
  static Employee create(int type) {
|||    return new Employee(type);|||
  }
```

# Необходимо также заменить фабричный метод, чтобы он создавал надлежащий объект.

Print:
```
    if (type == ENGINEER) {
      return new Engineer();
    }
    else {
      return new Employee(type);
    }
```

Go to the end of file

# Продолжаем поочерёдно, пока все коды не будут заменены подклассами.

Print:
```


class Salesman extends Employee {
  int getType() {
    return Employee.SALESMAN;
  }
}
```

Go to:
```
    if (type == ENGINEER) {
      return new Engineer();
    }|||
    else {
      return new Employee(type);
    }
```

Print:
```

    else if (type == SALESMAN) {
      return new Salesman();
    }
```

Wait 1000ms

Go to the end of file

Print:
```


class Manager extends Employee {
  int getType() {
    return Employee.MANAGER;
  }
}
```

Go to:
```
    else if (type == SALESMAN) {
      return new Salesman();
    }|||
    else {
      return new Employee(type);
    }
```

Print:
```

    else if (type == MANAGER) {
      return new Manger();
    }
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
  |||int getType() {
    return type;
  }
```

# ...и сделать <code>getType</code> абстрактным методом.

Print "abstract "

Select:
```
  abstract int getType()||| {
    return type;
  }|||
```

Print ";"

Select "static Employee |||create|||"

# После этого фабричный метод можно переписать в виде оператора <code>switch</code>

Go to the beginning of "Employee create"

Print:
```

    switch (type) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Недопустимое значение кода типа");
    }
```

Select:
```
    if (type == ENGINEER) {
      return new Engineer();
    }
    else if (type == SALESMAN) {
      return new Salesman();
    }
    else if (type == MANAGER) {
      return new Manger();
    }
    else {
      return new Employee(type);
    }

```

Remove selected

# Конечно, такого оператора switch желательно было бы избежать. Но он здесь лишь один и используется только при создании объекта.

Set step 5

# Естественно, что после создания подклассов следует применить <a href="/push-down-method">Спуск метода</a> и <a href="/push-down-field">Спуск поля</a> ко всем методам и полям, которые относятся только к конкретным типам служащих.

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
