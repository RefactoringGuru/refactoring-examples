replace-type-code-with-subclasses:php

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
  const ENGINEER = 0;
  const SALESMAN = 1;
  const MANAGER = 2;

  public $type;

  public function __construct($arg) {
    $this->type = $arg;
  }

  public $monthlySalary;
  public $commission;
  public $bonus;
  public function payAmount() {
    switch ($this->type) {
      case self::ENGINEER:
        return $this->monthlySalary;
      case self::SALESMAN:
        return $this->monthlySalary + $this->commission;
      case self::MANAGER:
        return $this->monthlySalary + $this->bonus;
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
  const ENGINEER = 0;
  const SALESMAN = 1;
  const MANAGER = 2;

  abstract public function getType();

  public static function create($type) {
    switch ($type) {
      case self::ENGINEER:
        return new Engineer();
      case self::SALESMAN:
        return new Salesman();
      case self::MANAGER:
        return new Manager();
    }
  }
  private function __construct($arg) {
    $this->type = $arg;
  }

  public $monthlySalary;
  public function payAmount() {
    return $this->monthlySalary;
  }
}

class Engineer extends Employee {
  public function getType() {
    return Employee::ENGINEER;
  }
}

class Salesman extends Employee {
  public $commission;
  public function getType() {
    return Employee::SALESMAN;
  }
  public function payAmount() {
    return $this->monthlySalary + $this->commission;
  }
}

class Manager extends Employee {
  public $bonus;
  public function getType() {
    return Employee::MANAGER;
  }
  public function payAmount() {
    return $this->monthlySalary + $this->bonus;
  }
}
```

###

Set step 1

# Рассмотрим рефакторинг <i>Замена кодирования типа подклассами</i> на примере класса зарплаты служащего. У нас есть несколько типов служащих, в зависимости от которых вычисляется размер зарплаты.

Select "public |||$type|||"
+ Select name of "payAmount"

# Начнём с <a href="/self-encapsulate-field">самоинкапсуляции поля</a> типа служащего.

Select "|||public||| $type"

Replace "private"

Go to before "__construct"

Print:
```

  public function getType() {
    return $this->type;
  }

```

Select "switch (|||$this->type|||)"

Replace "$this->getType()"

Set step 2

Go to before "__construct"

# Поскольку конструктор <code>Employee</code> использует код типа в качестве параметра, надо заменить его фабричным методом.

Print:
```

  public static function create($type) {
    return new Employee($type);
  }
```

Wait 500ms

Select visibility of "__construct"

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

  public function getType() {
    return Employee::ENGINEER;
  }
```

Select body of "create"

# Необходимо также заменить фабричный метод, чтобы он создавал надлежащий объект.

Print:
```
    switch ($type) {
      case self::ENGINEER:
        return new Engineer();
      default:
        return new Employee($type);
    }
```

Go to the end of file

# Продолжаем эти действия поочерёдно, пока все коды не будут заменены подклассами.

Print:
```


class Salesman extends Employee {
  public function getType() {
    return Employee::SALESMAN;
  }
}
```

Go to:
```
      case self::ENGINEER:
        return new Engineer();|||
```

Print:
```

      case self::SALESMAN:
        return new Salesman();
```

Wait 1000ms

Go to the end of file

Print:
```


class Manager extends Employee {
  public function getType() {
    return Employee::MANAGER;
  }
}
```

Go to:
```
      case self::SALESMAN:
        return new Salesman();|||
```

Print:
```

      case self::MANAGER:
        return new Manager();
```

Select:
```
  private $type;


```

Set step 4

# После этого можно избавиться от поля с кодом типа в <code>Employee</code>...

Remove selected


Go to:
```
  |||public function getType() {
    return $this->type;
  }
```

# ...и сделать <code>getType</code> абстрактным методом.

Print "abstract "

Select:
```
  abstract public function getType()||| {
    return $this->type;
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
        return new Employee($type);

```

Remove selected

Select "switch ($type) {" in "create"

# Обратите внимание, что мы создали ещё один большой оператор <code>switch</code>. Вообще <a href="/smells/switch-statements">это плохо</a>, но после завершения рефакторинга он будет единственным оставшимся в коде.

Set step 5

# Теперь, после создания подклассов, следует применить <a href="/push-down-method">Спуск метода</a> и <a href="/push-down-field">Спуск поля</a> ко всем методам и полям, которые относятся только к конкретным типам служащих.

Select:
```
  public $monthlySalary;
  public $commission;
  public $bonus;
```
+ Select name of "payAmount"

# В нашем случае создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплаты для соответствующих типов служащих.

Select:
```
  public $commission;

```

Remove selected

Go to the start of "Salesman"

Print:
```

  public $commission;
```

Select:
```
      case self::SALESMAN:
        return $this->monthlySalary + $this->commission;

```

Remove selected

Go to the end of "Salesman"

Print:
```

  public function payAmount() {
    return $this->monthlySalary + $this->commission;
  }
```

Wait 500ms

Select:
```
  public $bonus;

```

# Проделаем те же действия с классом менеджеров.

Remove selected

Go to the start of "Manager"

Print:
```

  public $bonus;
```

Select:
```
      case self::MANAGER:
        return $this->monthlySalary + $this->bonus;

```

Remove selected


Go to the end of "Manager"

Print:
```

  public function payAmount() {
    return $this->monthlySalary + $this->bonus;
  }
```

Set step 6

Select body of "payAmount"

# После перемещения всего кода по подклассам вы можете либо объявить метод в суперклассе абстрактным, либо оставить в нём реализацию по умолчанию (так и сделаем).

Print:
```
    return $this->monthlySalary;
```



#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.