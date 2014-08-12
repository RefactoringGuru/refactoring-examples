remove-setting-method:php

###

1. Значение поля должно меняться только в конструкторе. Если конструктор не содержит параметра для установки значения, нужно его добавить.

2. Найдите все вызовы сеттера.<ul><li>Если вызов сеттера стоит сразу после вызова конструктора текущего класса, переместите его аргумент в вызов конструктора и удалите сеттер.</li><li>Вызовы сеттера в конструкторе замените на прямой доступ к полю.</li></ul>

3. Удалите сеттер.



###

```
class Account {
  // ...
  private $id;
  
  public function __construct($id) {
    $this->setId($id);
  }
  public function setId($id) {
    $this->id = $id;
  }
}
```

###

```
class Account {
  // ...
  private $id;
  
  public function __construct($id) {
    $this->initializeId($id);
  }
  protected function initializeId($id) {
    $this->id = 'ID' . $id;
  }
}

class InterestAccount extends Account {
  private $interestRate;
  public function __construct($id, $interestRate) {
    $this->initializeId($id);
    $this->interestRate = $interestRate;
  }
}
```

###

Set step 1

# Рассмотрим <b>удаленгие сеттера</b> на таком простом примере — у нас есть класс банковского счета. В нем есть поле идентификатора, который должен создаваться только один раз и больше не меняться.

Select name of "setId"

# Тем не менее, сейчас в классе есть сеттер. Вот его мы и попытаемся убрать.

Select body of "setId"

# Самым простым решением было бы интегрировать код сеттера в конструктор.

Select body of "__construct"

Print:
```
    $this->id = $id;
```

Select whole "setId"

Remove selected

Select name of "Account"

# По сути, для такого простого случая, мы уже все сделали. Но бывают и другие, более сложные случаи. 

Select whole "__construct"

Print instant:
```
  public function __construct($id) {
    $this->setId($id);
  }
  public function setId($id) {
    $this->id = 'ID' . $id;
  }

```

Select body of "setId"

#< Например, если сеттер выполняет какие-то вычисления над аргументом:

#< Если изменение простое, как в этом случае, его тоде можно вынести в конструктор.

#< Однако, если изменение сложное, состоит из вызовов нескольких методов, лучше создать новый метод для инициализации значения.

Select visibility of "setId"

Wait 500ms

Print "private"

Wait 500ms

Select "setId"

Wait 500ms

Print "initializeId"

Set step 2

Go to the end of file

# Отлично, давайте рассмотрим еще один случай.

Print instant:
```


class InterestAccount extends Account {
  private $interestRate;
  public function __construct($id, $interestRate) {
    $this->setId($id);
    $this->interestRate = $interestRate;
  }
}
```

Select name of "InterestAccount"

# Ещё один неприятный случай возникает, когда есть подклассы, инициализирующие закрытые переменные родительского класса.

Select "$this->setId($id)"

# В этом случае, вместо вызова сеттера стоит вызывать родительский конструктор.

Print "parent::__construct($id)"

Select "parent::__construct($id)"

# В случаях, когда это невозможно, остется вызвать нужный метод инициализации, хотя сперва следует позаботиться о том, чтобы он был доступен для подклассов.

Select visibility of "initializeId"

Wait 500ms

Print "protected"

Wait 500ms

Select "parent::__construct($id)"

Wait 500ms

Print "$this->initializeId($id)"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.