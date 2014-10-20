replace-array-with-object:php

###

1. Создайте новый класс, который будет содержать данные из массива. Поместите в него сам массив как публичное поле.

2. Создайте поле для хранения объекта этого класса в исходном классе.

3. В новом классе, один за другим создайте методы доступа для всех элементов массива.

4. Когда методы доступа созданы для всех ячеек, сделайте массив приватным.

5. Для каждого элемента массива, создайте приватное поле в классе и измените методы доступа так, чтобы они использовали это поле вместе массива.

6. Когда с этим покончено, удалите массив.



###

```
class Tournament {
  public $row = array();
  
  public function __construct() {
    $this->row[0] = "Liverpool";
    $this->row[1] = "15";
  }
  public function displayScore() {
    $name = $this->row[0];
    $score = intval($this->row[1]);
    // ...
  }
}
```

###

```
class Tournament {
  public row; // Performance
  
  public function __construct() {
    $this->row = new Performance();
    $this->row->setName("Liverpool");
    $this->row->setScore("15");
  }
  public function displayScore() {
    $name = $this->row->getName();
    $score = $this->row->getScore();
    // ...
  }
}

class Performance {
  private $name;
  private $score;

  public function getName() {
    return $this->name;
  }
  public void setName($arg) {
    $this->name = $arg;
  }
  public function getScore() {
    return $this->score;
  }
  public function setScore($arg) {
    $this->score = intval($arg);
  }
}
```

###

Set step 1

# Давайте рассмотрим рефакторинг <i>Замена массива объектом</i> на примере класса, хранящего название спортивной команды и количество заработанных ею очков.

Go to the end of file

# Превращение массива в объект начинается с создания класса.

Print:
```


class Performance {
}
```

Go to the start of "Performance"

# На первом этапе мы добавляем в новый класс такое же поле массив, как и в оригинале. Не беспокойтесь, мы избавимся от него чуть позже.

Print:
```

  public $data = array();
```

Set step 2

Select name of "Tournament"

# Теперь нужно найти места, в которых идут обращения к этому массиву, и заменить их обращениями к вашему новому классу.

Select "public $row = array();"

# В первую очередь создаём сам объект в том месте, где был инициирован массив данных.

Print:
```
public row; // Performance
```

Go to start of "__construct"

Print:
```

    $this->row = new Performance();
```

Select "|||row|||[" in "__construct"
+Select "|||row|||[" in "displayScore"

# Теперь нужно заменить код, использующий массив.

Replace "row->data"

Set step 3

Go to the end of "Performance"

# После этого в класс данных добавляем геттеры и сеттеры с более содержательными именами. Начнём с поля названия команды.

Print:
```


  public function getName() {
    return $this->data[0];
  }
  public void setName($arg) {
    $this->data[0] = $arg;
  }
```

Select "$this->row->data[0] = "Liverpool""
+ Select "$name = $this->row->data[0]"

# Теперь нужно поочерёдно заменить код присваивания значениям элементов массива так, чтобы везде применялись методы класса <code>Perfomance</code>.

Select "$this->row->data[0] = "Liverpool""

Replace "$this->row->setName("Liverpool")"

Wait 500ms

Select "$this->row->data[0]"

Replace "$this->row->getName()"

Go to the end of "Performance"

# То же самое можно проделать со вторым элементом.

Print:
```

  public function getScore() {
    return intval($this->data[1]);
  }
  public function setScore($arg) {
    $this->data[1] = $arg;
  }
```


Select "$this->row->data[1] = "15""

Replace "$this->row->setScore("15")"

Wait 500ms

Select "intval($this->row->data[1])"

Replace "$this->row->getScore()"

Set step 4

Select "|||public||| $data"

# Выполнив эти действия для всех элементов, можно объявить массив приватным.

Replace "private"

Set step 5

# Теперь главная часть этого рефакторинга – замена интерфейса – завершена. Однако полезно будет также заменить массив внутри класса данных.

Select name of "getName"
+ Select name of "setName"

# Это можно сделать, добавив поля для всех элементов массива и переориентировав методы доступа на их использование. Для начала преобразуем поле названия команды.

Go to "array();|||"

Print:
```

  private $name;
```

Select "data[0]"

Replace "name"

Select name of "getScore"
+ Select name of "setScore"

# А затем – поле для хранения командного счёта.

Go to "$name;|||"

Print:
```

  private $score;
```

Select "intval($this->data[1])"

Replace "$this->score"

Select "$this->data[1] = $arg"

Replace "$this->score = intval($arg)"

Set step 6

Select:
```
  private $data = array();

```
# Выполнив замену для всех элементов массива, можно удалить и само объявление массива из класса.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.