replace-constructor-with-factory-method:php

###

1. Создайте фабричный метод. Поместите в его тело вызова текущего конструктора.

2. Замените все вызовы конструктора вызовами фабричного метода.

3. Объявите конструктор приватным.

4. Обследуйте код конструктора и попытайтесь вынести в фабричный метод тот код, который не относится к непосредственному конструированию объекта текущего класса.



###

```
class Employee {
  // ...
  const ENGINEER = 0;
  const SALESMAN = 1;
  const MANAGER = 2;

  public function __construct($type) {
    $this->type = $type;
  }
}

// Some clinet code.
$eng = new Employee(Employee::ENGINEER);
```

###

```
class Employee {
  // ...
  const ENGINEER = 0;
  const SALESMAN = 1;
  const MANAGER = 2;

  public static function create($type) {
    switch ($type) {
      case self::ENGINEER:
        return new Engineer();
      case self::SALESMAN:
        return new Salesman();
      case self::MANAGER:
        return new Manger();
      default:
        return new Employee($type);
    }
  }
  private function __construct($type) {
    $this->type = $type;
  }
}
class Engineer extends Employee {
  // ...
}
class Salesman extends Employee {
  // ...
}
class Manager extends Employee {
  // ...
}

// Some clinet code.
$eng = Employee::create(Employee::ENGINEER);
```

###

Set step 1

# Допустим, у нас есть класс для создания сотрудников,

Select parameters in "__construct"

#< ...в котором тип сотрудника задаётся параметром конструктора.

Select "new Employee"

# Клиентский код вызывает этот конструктор напрямую.

# Предположим, мы захотели создать подклассы для каждого типа сотрудников.

Go to after "Employee"

Print:
```

class Engineer extends Employee {
  // ...
}
class Salesman extends Employee {
  // ...
}
class Manager extends Employee {
  // ...
}
```

Select "new Employee"

# Что случилось бы с этим кодом? Его пришлось бы переписать, т.к. мы не можем возвращать ничего другого из конструктора <code>Employee</code>, кроме как объектов <code>Employee</code> (а нам нужен <code>Engineer</code>).

# Но если потом что-то снова поменялось, и мы создали бы ещё больше подклассов, вполне возможно, что вызовы конструкторов снова пришлось бы править.

# Альтернативой этому является создание <b>фабричного метода</b> – специального статического метода, который бы возвращал объекты разных классов, в зависимости от параметров.

Go to before "__construct"

# Класс <code>Employee</code> является лучшим метом для хранения фабричного метода, так как он, скорее всего, переживёт любые изменения подклассов.

Print:
```

  public static function create($type) {
    return new Employee($type);
  }
```

# На данном этапе фабричный метод будет вызывать текущий конструктор, но мы изменим это в ближайшем будущем.

Set step 2

Select "new Employee"

# Теперь нужно найти все прямые вызовы конструкторов и заменить их вызовами фабричного метода.

Print "Employee::create"

Set step 3

Select visibility of "__construct"

# После всех замен, конструктор можно скрыть от посторонних глаз, сделав его приватным.

Print "private"

Set step 4

Select body of "create"

# После этого можно создать в фабричном методе условный оператор, который будет возвращать объект нужного класса, в зависимости от параметра.

Print:
```
    switch ($type) {
      case self::ENGINEER:
        return new Engineer();
      case self::SALESMAN:
        return new Salesman();
      case self::MANAGER:
        return new Manger();
      default:
        return new Employee($type);
    }
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.