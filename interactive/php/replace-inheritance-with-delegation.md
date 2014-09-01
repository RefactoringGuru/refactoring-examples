replace-inheritance-with-delegation:php

###

1. Создайте поле в подклассе для содержания суперкласса. На первом этапе поместите в него текущий объект.

2. Измените методы подкласса так, чтобы они использовали объект суперкласса, вместо <code>this</code>.

3. Для методов, которые были унаследованы из суперкласса и которые вызывается в клиентском коде, в подклассе нужно создать простые делегирующие методы.

4. Уберите объявление наследования из подкласса.

5. Измените код инициализации поля, в котором хранится бывший суперкласс, созданием нового объекта.



###

```
class Engine {
  //...
  private $fuel;
  private $CV;

  public function getFuel() {
    return $this->fuel;
  }
  public function setFuel($fuel) {
    $this->fuel = $fuel;
  }
  public function getCV() {
    return $this->CV;
  }
  public function setCV($cv) {
    $this->CV = $cv;
  }
}

class Car extends Engine {
  // ...
  private $brand;
  private $model;

  public function getName() {
    return $this->brand . ' ' . $this->model . ' (' . $this->getCV() . 'CV)';
  }
  public function getModel() {
    return $this->model;
  }
  public function setModel($model) {
    $this->model = $model;
  }
  public function getBrand() {
    return $this->brand;
  }
  public function setBrand($brand) {
    $this->brand = $brand;
  }
}
```

###

```
class Engine {
  //...
  private $fuel;
  private $CV;

  public function getFuel() {
    return $this->fuel;
  }
  public function setFuel($fuel) {
    $this->fuel = $fuel;
  }
  public function getCV() {
    return $this->CV;
  }
  public function setCV($cv) {
    $this->CV = $cv;
  }
}

class Car {
  // ...
  private $brand;
  private $model;
  protected $engine;

  public function __construct() {
    $this->engine = new Engine();
  }
  public function getName() {
    return $this->brand . ' ' . $this->model . ' (' . $this->engine->getCV() . 'CV)';
  }
  public function getModel() {
    return $this->model;
  }
  public function setModel($model) {
    $this->model = $model;
  }
  public function getBrand() {
    return $this->brand;
  }
  public function setBrand($brand) {
    $this->brand = $brand;
  }
}
```

###

Set step 1

# Рассмотрим рефакторинг на примере класса автомобилей <code>Car</code>, который наследуется от класса двигателей <code>Engine</code>.

Select "$this->getCV()" in "Car"

# Сначала идея наследования казалась хорошей и оправданной, но в итоге выяснилось, что автомобили используют только одно свойство двигателя (а именно, объем).

Go to the start of "Car"

# Таким образом, было бы эффективней использовать делегирование к классу <code>Engine</code> для получения нужных свойств или методов.

# Начнём рефакторинг с создания поля для хранения ссылки на суперкласс.

Go to "private $model;|||"

Print:
```

  protected $engine;
```

# Пока что будем заполнять это поле текущим объектом (это можно сделать в конструкторе).

Go to before "getName"

Print:
```

  public function __construct() {
    $this->engine = $this;
  }
```

Set step 2

Select "$this->getCV()"

# Теперь следует изменить все обращения к полям и методам суперкласса так, чтобы они обращались к созданному полю. В нашем случае, это происходит только в одном месте.

Print "$this->engine->getCV()"

Set step 4

Select " extends Engine"

# Теперь можно убрать объявление наследование из класса <code>Car</code>.

Remove selected

Set step 5

# После этого остаётся только создать новый объект двигателей для заполнения поля связанного объекта.

Select "engine = |||$this|||"

Replace "new Engine()"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.