change-reference-to-value:php

###

1. Обеспечьте неизменяемость объекта. Объект не должен иметь сеттеров или других методов, меняющих его состояние и данные (в этом может помочь <a href="/remove-setting-method">удаление сеттера</a>). Единственное место, где полям объекта-значения присваиваются какие-то данные, должен быть конструктор.

2. Проверьте, возможно ли удалить фабричный метод и сделать конструктор объекта публичным.



###

```
class Customer {
  private $name;
  private $birthDate;

  public function getName() {
    return $this->name;
  }
  public function getBirthDate() {
    return $this->birthday;
  }
  public function setBirthDate(DateTime $birthDate) {
    $this->birthDate = $birthDate;
  }
  private function __construct($name) {
    $this->name = $name;
  }

  private static $instances = array();

  public static function get($name) {
    if (!isset($this->instances[$name])) {
      $value = new Currency($name);
      $this->instances[$name] = $value;
    }
    else {
      $value = $this->instances[$name]
    }
    return value;
  }
}

// Somewhere in client code
$john = Customer::get("John Smith");
$john->setBirthDate(new DateTime("1985-01-01"));
```

###

```
class Customer {
  private $name;
  private $birthDate;

  public function getName() {
    return $this->name;
  }
  public function getBirthDate() {
    return $this->birthday;
  }
  public function __construct($name, DateTime birthDate) {
    $this->name = $name;
    $this->birthDate = $birthDate;
  }
}

// Somewhere in client code
$john = new Customer("John Smith", new Date(1985, 1, 1));
```

###

Set step 1

# Давайте рассмотрим <i>Замену ссылки значением</i> на примере класса покупателя.

Select "private |||$name|||"
+ Select "private |||$birthDate|||"

# Этот класс содержит имя и день рождения покупателя. Это класс порождает объекты-ссылки, другими словами для одного реального покупателя создаётся только один экземпляр класса <code>Customer</code>.

Select "Customer::get("John Smith")"

# Таким образом, для получения экземпляра может использоваться следующий код.

Select visibility of "__construct"

# Класс <code>Customer</code> ведёт реестр своих экземпляров. Я не могу просто обратиться к конструктору (вот почему он закрытый).

Select name of "get"

# Вместо этого, я вызываю статический фабричный метод, который ищет покупателя среди уже созданных объектов. И только в случае, если такой объект ещё не создан, фабричный метод запускает реальный конструктор, а затем добавляет созданный объект в реестр.

# Теперь, допустим, у вас есть несколько заказов, ссылающихся на одного и того же клиента. Внезапно, код одного из заказов меняет значенияе даты рождения клиента. Так как оба заказа ссылаются на один тот же объект клиента, новая дата рождения будет доступна и из другого заказа.

# Было ли бы такое возможно, если каждый заказ имел бы разные экземпляры класса <code>Customer</code>? Пожалуй, нет. Вот почему главным требованием этого рефакторинга является приведение класса к неизменяемому виду. В некоторых случаях это вообще невозможно и от рефакторинга нужно отказаться.

Select whole "setBirthDate"

# Следуя этой логике, нужно убрать сеттер поля даты рождения и инициализировать значение этого поля в конструкторе. Применим рефакторинг <a href="/remove-setting-method">удаление сеттера</a>.

Remove selected

Go to the end of parameters of "__construct"

Print ", DateTime birthDate"

Go to the end of "__construct"

Print:
```

    $this->birthDate = $birthDate;
```

Select:
```

$john->setBirthDate(new DateTime("1985-01-01"));
```

# Так как сеттера в классе теперь нет, нам нужно удалить его использование в клиентском коде. Действие этого сеттера пока нечем компенсировать, но неволнуйтесь, мы рассмотрим это чуточку позже.

Remove selected

Set step 2

Select:
```

  private static $instances = array();


```
+ Select whole "get"

# Остался последний штрих. Так как у нас теперь нет нужды хранить реестр созданных объектов, фабричный метод можно удалить, а конструктор сделать публичным.

Remove selected

Select visibility of "__construct"

Replace "public"

Select "Customer::get("John Smith")"

# После всех этих изменений, клиентский код тоже изменится.

Print "new Customer("John Smith", new Date(1985, 1, 1))"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.