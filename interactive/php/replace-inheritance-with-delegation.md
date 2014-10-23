replace-inheritance-with-delegation:php

###

1.ru. Создайте поле в подклассе для содержания суперкласса. На первом этапе поместите в него текущий объект.
1.uk. Створіть поле в підкласі для утримання суперкласу. На першому етапі додайте в нього поточний об'єкт.

2.ru. Измените методы подкласса так, чтобы они использовали объект суперкласса, вместо <code>this</code>.
2.uk. Змініть методи підкласу так, щоб вони використовували об'єкт суперкласу, замість <code>this</code>.

3.ru. Для методов, которые были унаследованы из суперкласса и которые вызывается в клиентском коде, в подклассе нужно создать простые делегирующие методы.
3.uk. Для методів, які були успадковані з суперкласу і які викликаються в клієнтському коді, в підкласі треба створити прості делегуючі методи.

4.ru. Уберите объявление наследования из подкласса.
4.uk. Приберіть оголошення спадкоємства з підкласу.

5.ru. Измените код инициализации поля, в котором хранится бывший суперкласс, созданием нового объекта.
5.uk. Змініть код ініціалізації поля-делегата новим об' єктом суперкласу.



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

#|ru| Рассмотрим рефакторинг на примере класса автомобилей <code>Car</code>, который наследуется от класса двигателей <code>Engine</code>.
#|uk| Розглянемо рефакторинг на прикладі класу автомобілів <code>Car</code>, який успадковується від класу двигунів <code>Engine</code>.

Select "$this->getCV()" in "Car"

#|ru| Сначала идея наследования казалась хорошей и оправданной, но в итоге выяснилось, что автомобили используют только одно свойство двигателя (а именно, объем).
#|uk| Спочатку ідея успадкування здавалася гарною і виправданою, але в підсумку з'ясувалося, що автомобілі використовують тільки одну властивість двигуна (а саме, обсяг).

Go to the start of "Car"

#|ru| Таким образом, было бы эффективней использовать делегирование к классу <code>Engine</code> для получения нужных свойств или методов.
#|uk| Таким чином, було б ефективніше використовувати делегування до класу <code>Engine</code> для отримання потрібних властивостей або методів.

#|ru| Начнём рефакторинг с создания поля для хранения ссылки на суперкласс.
#|uk| Почнемо рефакторинг зі створення поля для зберігання посилання на суперклас.

Go to "private $model;|||"

Print:
```

  protected $engine;
```

#|ru| Пока что будем заполнять это поле текущим объектом (это можно сделать в конструкторе).
#|uk| Поки що будемо заповнювати це поле поточним об'єктом (це можна зробити в конструкторі).

Go to before "getName"

Print:
```

  public function __construct() {
    $this->engine = $this;
  }
```

Set step 2

Select "$this->getCV()"

#|ru| Теперь следует изменить все обращения к полям и методам суперкласса так, чтобы они обращались к созданному полю. В нашем случае, это происходит только в одном месте.
#|uk| Тепер слід змінити всі звернення до полів і методів суперкласу так, щоб вони зверталися до створеного поля. У нашому випадку, це відбувається тільки в одному місці.

Print "$this->engine->getCV()"

Set step 4

Select " extends Engine"

#|ru| Теперь можно убрать объявление наследование из класса <code>Car</code>.
#|uk| Тепер можна прибрати оголошення успадкування з класу <code>Car</code>.

Remove selected

Set step 5

#|ru| После этого остаётся только создать новый объект двигателей для заполнения поля связанного объекта.
#|uk| Після цього залишається тільки створити новий об'єкт двигунів для заповнення поля пов'язаного об'єкта.

Select "engine = |||$this|||"

Replace "new Engine()"

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.