replace-delegation-with-inheritance:php

###

1. Сделайте класс подклассом класса-делегата.

2. В поле, содержащее ссылку на объект-делегат, поставьте текущий объект.

3. Один за другим удаляйте методы с простой делегацией. Если у них отличались названия, используйте <a href="/rename-method">переименование метода</a> чтобы привести все методы к одному названию.

4. Замените все обращения к полю-делегату обращениями к текущему объекту.

5. Удалите поле-делегат.



###

```
class Person {
  private $name;

  public String getName() {
    return $this->name;
  }
  public void setName($name) {
    $this->name = $name;
  }
  public String getLastName() {
    return substr($this->name, 0, strrpos($this->name, ' ') + 1);
  }
}

class Employee {
  protected $person;

  public function __construct() {
    $this->person = new Person();
  }
  public function getName() {
    return $this->person->getName();
  }
  public function setName($name) {
    $this->person->setName($name);
  }
  public function toString () {
    return "Emp: " . $this->person->getLastName();
  }
}
```

###

```
class Person {
  private $name;

  public String getName() {
    return $this->name;
  }
  public void setName($name) {
    $this->name = $name;
  }
  public String getLastName() {
    return substr($this->name, 0, strrpos($this->name, ' ') + 1);
  }
}

class Employee extends Person {
  public function toString () {
    return "Emp: " . $this->getLastName();
  }
}
```

###

Set step 1

# У нас есть класс служащего <code>Employee</code>, который делегирует некоторою работу классу личности <code>Person</code>.

# Здесь наследование было бы уместнее, так как классу служащих необходимы практически все данные из <code>Person</code>.

# Начинаем рефакторинг с объявления наследования в классе <code>Employee</code>

Go to "class Employee|||"

Print " extends Person"

#C Здесь стоит запустить компиляцию, чтобы убедиться в отсутствии конфликтующих методов. Они возникают, если методы с одинаковым именем возвращают значения различных типов или генерируют разные исключительные ситуации. Все проблемы такого рода исправляются с помощью <a href="/rename-method">Переименования метода</a>.

#S В данном простом примере таких затруднений не возникает.

Set step 2

Select "new Person()"

# Следующим шагом заставляем поле делегирования ссылаться на сам объект.

Print "$this"

Set step 3

Select whole "getName" in "Employee"
+ Select whole "setName" in "Employee"

# Кроме того, мы должны удалить из <code>Employee</code> все простые методы делегирования, такие, как <code>getName</code> и <code>setName</code>. Если их оставить, возникнет ошибка переполнения стека, вызванная бесконечной рекурсией.

Remove selected

Set step 4

Select "person->"

# Далее следует избавиться от обращений к связанному полю, заменив их вызовами из собственного класса.

Remove selected

Set step 5

Select:
```
  protected $person;

  public function __construct() {
    $this->person = $this;
  }

```

# После всех замен поле связанного объекта, а также код его инициализации становятся бесполезными и их можно убрать.

Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.