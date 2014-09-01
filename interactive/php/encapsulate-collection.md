encapsulate-collection:php

###

1. Создайте методы для добавления и удаления элементов коллекции.

2. Присвойте полю пустую коллекцию в качестве начального значения.

3. Найдите вызовы сеттера поля коллекции. Измените сеттер так, чтобы он использовал операции добавления и удаления элементов.

4. Найдите вызовы геттера коллекции, ведущие к изменению коллекции. Поменяйте этот код так, чтобы там использовались новые методы добавления и удаления элементов коллекции.

5. Измените геттер, чтобы он возвращал представление коллекции, доступное только для чтения.

6. Обследуйте клиентский код, использующий коллекцию, в поисках кода, который бы лучше смотрелся внутри самого класса коллекции.



###

```
class Course {
  public __construct($name, $isAdvanced) {
    // ...
  }
  public function isAdvanced() {
    // ...
  }
}

class Person {
  private $courses; // SplObjectStorage

  public function getCourses() {
    return $this->courses;
  }
  public function setCourses(SplObjectStorage $arg) {
    $this->courses = $arg;
  }
}

// Клиентский код
$kent = new Person();
$s = new SplObjectStorage();
$s->attach(new Course("Smalltalk Programming", false));
$s->attach(new Course("Appreciating Single Malts", true));
$kent->setCourses($s);
assert(2 === $kent->getCourses()->count());
$refact = new Course("Refactoring", true);
$kent->getCourses()->attach($refact);
$kent->getCourses()->attach(new Course("Brutal Sarcasm", false));
assert(4 === $kent->getCourses()->count());
$kent->getCourses()->detach($refact);
assert(3 === $kent->getCourses()->count());

$count = 0;
foreach ($kent->getCourses() as $course) {
  if ($course->isAdvanced()) {
    $count++;
  }
}
print("Advanced courses: " . $count);

```

###

```
class Course {
  public __construct($name, $isAdvanced) {
    // ...
  }
  public function isAdvanced() {
    // ...
  }
}

class Person {
  private $courses; // SplObjectStorage

  public __construct() {
    $this->courses = new SplObjectStorage();
  }
  public function getCourses() {
    return clone $this->courses;
  }
  public function initializeCourses(SplObjectStorage $arg) {
    assert($this->courses->count() > 0, "Courses are not empty");
    $this->courses->addAll($arg);
  }
  public function addCourse($arg) {
    $this->courses->attach($arg);
  }
  public function removeCourse($arg) {
    $this->courses->detach($arg);
  }
  public function numberOfAdvancedCourses() {
    $count = 0;
    foreach ($this->courses as $course) {
      if ($course->isAdvanced()) {
        $count++;
      }
    }
    return $count;
  }
  public function numberOfCourses() {
    return $this->courses->count();
  }
}

// Клиентский код
$kent = new Person();
$kent->addCourse(new Course("Smalltalk Programming", false));
$kent->addCourse(new Course("Appreciating Single Malts", true));
assert(2 === $kent->numberOfCourses());
$refact = new Course("Refactoring", true);
$kent->addCourse($refact);
$kent->addCourse(new Course("Brutal Sarcasm", false));
assert(4 === $kent->numberOfCourses());
$kent->removeCourse($refact);
assert(3 === $kent->numberOfCourses());

print("Advanced courses: " . $kent->numberOfAdvancedCourses());

```

###

Set step 1

# Давайте рассмотрим <i>Инкапсуляцию коллекции</i> на примере каталога обучающих курсов.

Select name of "Course"

# Класс курсов довольно прост.

Select name of "Person"

# Кроме того есть ещё класс посетителей курсов.

Go to "$kent = |||new Person();"

# При таком интерфейсе клиенты добавляют курсы с помощью следующего кода.

Go to the end of "class Person"

# Итак, первым делом нужны надлежащие методы модификации для этой коллекции.

Print:
```

  public function addCourse($arg) {
    $this->courses->attach($arg);
  }
  public function removeCourse($arg) {
    $this->courses->detach($arg);
  }
```

Set step 2

Go to before "getCourses"

# Также давайте облегчим также себе жизнь, проинициализировав поле:

Print:
```

  public __construct() {
    $this->courses = new SplObjectStorage();
  }
```

Set step 3

Select name of "setCourses"

# Теперь посмотрим на пользователей сеттера <code>setCourses</code>. Если клиентов много и сеттер интенсивно используется, необходимо заменить тело метода так, чтобы в нем использовались операции добавления и удаления.

Select "$kent->setCourses($s)"

# Сложность этой процедуры зависит от способа использования сеттера коллекции. В простейшем из них клиент инициализирует значения с помощью сеттера, т.е. до применения метода курсов не существует.

Select body of "setCourses"

# В этом случае нужно изменить тело сеттера так, чтобы в нем использовался метод добавления.

Print:
```
    assert($this->courses->count() > 0, "Courses are not empty");
    foreach ($arg as $item) {
      $this->courses->attach($item);
    }
```

Select name of "setCourses"

# После такой модификации стоит с помощью <a href="/rename-method">переименования метода</a> сделать намерения более ясными.

Select "setCourses"

Replace "initializeCourses"

# В общем случае мы должны сначала прибегнуть к методу удаления и убрать все элементы, а затем добавлять новые. Однако это происходит редко (как и бывает с общими случаями).

# Если мы знаем, что другого поведения при добавлении элементов во время инициализации нет, можно убрать цикл и применить addAll.


Select:
```
    foreach ($arg as $item) {
      $this->courses->attach($item);
    }
```

Replace:
```
    $this->courses->addAll($arg);
```

# Стоит упомянуть, что мы не можем просто присвоить значение множеству, даже если предыдущее множество было пустым. Если клиент соберётся модифицировать множество после того, как передаст его, это станет нарушением инкапсуляции. Поэтому мы должны создать копию.

Select:
```
$s = new SplObjectStorage();

```

# Если клиенты просто создают множество и пользуются методом установки, мы можем заставить их пользоваться методами добавления и удаления непосредственно и полностью убрать вызов метода инициализации.


Remove selected

Select:
```
|||$s->attach|||(new Course("Smalltalk Programming", false));
|||$s->attach|||(new Course("Appreciating Single Malts", true));
```

Replace "$kent->addCourse"

Select:
```

$kent->initializeCourses($s);
```

Remove selected

Set step 4


Select "getCourses()->attach"
+ Select "getCourses()->detach"

# Теперь нужно рассмотреть, кто использует геттер коллекции. В первую очередь нас должны интересовать случаи модификации коллекции с его помощью.

# Такие вызовы следует заменять вызовами метода добавления или удаления курсов.

Select "getCourses()->attach"

Replace "addCourse"

Wait 500ms

Select "getCourses()->detach"

Replace "removeCourse"

Set step 5

Select:
```
return |||$this->courses|||;
```

# Последним штрихом следует изменить тело геттера так, чтобы он возвращал значение, доступное только для чтения (другими словами неизменяемое представление коллекции).


Print:
```
clone $this->courses
```

#C Запустим тестирование, чтобы убедиться в отсутствии ошибок.

#S Отлично, все работает!

Select:
```
private |||$courses|||
```

# После этого коллекцию можно считать полностью инкапсулированной. Никто не сможет изменить её элементы, кроме как через методы <code>Person</code>.

Set step 6

Select:
```
$count = 0;
foreach ($kent->getCourses() as $course) {
  if ($course->isAdvanced()) {
    $count++;
  }
}

```

# После того как для класса <code>Person</code> был создан корректный интерфейс, мы можем заняться перемещением релевантного кода в этот класс. Вот пример такого кода.

# Применим <a href="/extract-method">извлечение метода</a> к этому коду, чтобы переместить его в <code>Person</code>.

Go to the end of "class Person"

Print:
```

  public function numberOfAdvancedCourses() {
    $count = 0;
    foreach ($this->courses as $course) {
      if ($course->isAdvanced()) {
        $count++;
      }
    }
    return $count;
  }
```

Select:
```
$count = 0;
foreach ($kent->getCourses() as $course) {
  if ($course->isAdvanced()) {
    $count++;
  }
}

```

Remove selected

Select:
```
print("Advanced courses: " . |||$count|||);
```

Replace "$kent->numberOfAdvancedCourses()"

Select "$kent->getCourses()->count()"

# Часто встречается такой код.

Go to the end of "Person"

# Его можно заменить более читабельной версией.

Print:
```

  public function numberOfCourses() {
    return $this->courses->count();
  }
```

Select "$kent->getCourses()->count()"

Replace "$kent->numberOfCourses()"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.