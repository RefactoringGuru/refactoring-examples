replace-type-code-with-class:php

###

1. Создайте новый класс и дайте ему понятное название, соответствующее предназначению закодированного типа.

2. В <i>класс типа</i> скопируйте поле, содержащее кодирование типа, и сделайте его приватным. Кроме того, создайте для этого поля геттер. Устанавливаться значение в это поле будет только из конструктора.

3. Для каждого значения закодированного типа, создайте статический метод в <i>классе типа</i>.

4. В исходном классе, замените тип закодированного поля на <i>класс типа</i>. Создавайте новый объект этого типа в конструкторе, а также в сеттере поля. Измените геттер поля так, чтобы он вызывал геттер <i>класса типа</i>.

5. Замените любые упоминания значений закодированного типа вызовами соответствующих статических методов <i>класса типа</i>.

6. Удалите константы закодированного типа из исходного класса.



###

```
class Person {
  const О = 0;
  const A = 1;
  const B = 2;
  const AB = 3;

  private $bloodGroup; // int

  public function __construct($code) {
    $this->bloodGroup = $code;
  }
  public function setBloodGroup($code) {
    $this->bloodGroup = $code;
  }
  public function getBloodGroup() {
    return $this->bloodGroup;
  }
}

// Somewhere in client code.
$parent = new Person(Person::O);
if ($parent->getBloodGroup() == Person::AB) {
  // ...
}
$child->setBloodGroup($parent->getBloodGroup());
```

###

```
class Person {
  private $bloodGroup; // BloodGroup

  public function __construct(BloodGroup $bloodGroup) {
    $this->bloodGroup = $bloodGroup;
  }
  public function setBloodGroup(BloodGroup $bloodGroup) {
    $this->bloodGroup = $bloodGroup;
  }
  public function getBloodGroup() {
    return $this->bloodGroup;
  }
}

class BloodGroup {
  private $code;

  private function __construct($arg) {
    $this->code = $arg;
  }
  public function getCode() {
    return $this->code;
  }

  public static function O() {
    return new BloodGroup(0);
  }
  public static function A() {
    return new BloodGroup(1);
  }
  public static function B() {
    return new BloodGroup(2);
  }
  public static function AB() {
    return new BloodGroup(3);
  }
}

// Somewhere in client code.
$parent = new Person(BloodGroup::O());
if ($parent->getBloodGroup() == BloodGroup::AB()) {
  // ...
}
$child->setBloodGroup($parent->getBloodGroup());
```

###

Set step 1

# Рассмотрим рефакторинг <i>Замена кодирования типа классом</i> на примере класса человека, в котором есть поля группы крови.

Select:
```
  const |||О = 0|||;
  const |||A = 1|||;
  const |||B = 2|||;
  const |||AB = 3|||;
```

# Группы крови закодированы в четырёх константах этого класса.

Go to after "Person"

# Рефакторинг мы начинаем с того, что создаём новый класс <code>BloodGroup</code>, который будет отвечать за группы крови.

Type:
```


class BloodGroup {
}
```

Set step 2

# В этот класс мы помещаем поле группы крови из класса <code>Person</code>, конструктор, инициализирующий значение поля, а также его геттер.

Go to the end of "BloodGroup"

Type:
```

  private $code;

  private function __construct($arg) {
    $this->code = $arg;
  }
  public function getCode() {
    return $this->code;
  }
```

Set step 3

# Теперь, создаём статические методы для каждого из значений закодированного типа из оригинального класса. Эти методы должны возвращать экземпляры класса <code>BloodGroup</code>.

Go to the end of "class BloodGroup"

Print:
```


  public static function O() {
    return new BloodGroup(0);
  }
  public static function A() {
    return new BloodGroup(1);
  }
  public static function B() {
    return new BloodGroup(2);
  }
  public static function AB() {
    return new BloodGroup(3);
  }
```

#C Можем запустить тестирование, чтобы убедиться в правильности кода.

#S Всё хорошо, можем продолжать.

Set step 4

Select:
```
  private $bloodGroup; // |||int|||
```

# Теперь в исходном классе, заменим тип закодированного поля на <code>BloodGroup</code>.


Type:
```
BloodGroup
```


Select:
```
  public function __construct($code) {
    $this->bloodGroup = |||$code|||;
  }
  public function setBloodGroup($code) {
    $this->bloodGroup = |||$code|||;
  }
```

# Соответствено нужно поменять код конструктора и сеттера.

Type:
```
new BloodGroup($code)
```

Go to:
```
return $this->bloodGroup|||;
```

# Затем, изменяем геттер поля так, чтобы он вызывал геттер класса <code>BloodGroup</code>

Print "->getCode()"

Set step 5

Select:
```
  const |||О = 0|||;
  const |||A = 1|||;
  const |||B = 2|||;
  const |||AB = 3|||;
```

# Тепперь настала пора заменить любые упоминания значений закодированного типа вызовами соответствующих статических методов <i>класса типа</i> <code>BloodGroup</code>.

Select "new Person(|||Person::O|||);"

Wait 500ms

Type "BloodGroup::O()->getCode()"

Wait 500ms

Select "$parent->getBloodGroup() == |||Person::AB|||"

Wait 500ms

Type "BloodGroup::AB()->getCode()"

#C После всех замен, стоит запустить тесты.

#S Всё работает, отлично!

Select parameters in "__construct"
+ Select parameters in "setBloodGroup"
+ Select:
```
return $this->bloodGroup|||->getCode()|||;
```

# После всех замен, нужно постараться вообще избавиться от использования числовых кодов <code>BloodGroup</code> и использовать объекты вместо этого. Давайте попробуем сделать это в классе <code>Person</code>.

Select parameters in "__construct"
+ Select parameters in "setBloodGroup"

Replace "BloodGroup $bloodGroup"

Wait 500ms

Select:
```
new BloodGroup($code)
```

Replace "$bloodGroup"

Wait 500ms

Select:
```
return $this->bloodGroup|||->getCode()|||;
```

Remove selected

Select:
```
$parent = new Person(BloodGroup::O()|||->getCode()|||);
if ($parent->getBloodGroup() == BloodGroup::AB()|||->getCode()|||) {
```

# После этих изменений, вероятно, сломается клиентский код, но это просто исправить, избавившить от кодов и там.

Remove selected


Set step 6
Select:
```
  const О = 0;
  const A = 1;
  const B = 2;
  const AB = 3;


```

# Напоследок, можно удалить константы из класса <code>Person</code>.

Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.