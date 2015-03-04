encapsulate-collection:php

###

1.ru. Создайте методы для добавления и удаления элементов коллекции.
1.en. Create methods for adding and deleting collection elements.
1.uk. Створіть методи для додавання і видалення елементів колекції.

2.ru. Присвойте полю пустую коллекцию в качестве начального значения.
2.en. Assign an empty collection to the field as the initial value.
2.uk. Присвойте полю порожню колекцію в якості початкового значення.

3.ru. Найдите вызовы сеттера поля коллекции. Измените сеттер так, чтобы он использовал операции добавления и удаления элементов.
3.en. Find the setter calls for the collection field. Change the setter so that it uses element add/remove operations.
3.uk. Знайдіть виклики сетера поля колекції. Змініть сетер так, щоб він використовував операції додавання і видалення елементів.

4.ru. Найдите вызовы геттера коллекции, ведущие к её изменению. Поменяйте этот код так, чтобы там использовались новые методы добавления и удаления элементов коллекции.
4.en. Find all calls of the collection getter after which the collection is changed. Change the code so that it uses your new methods for adding and deleting elements from the collection.
4.uk. Знайдіть усі виклики геттера колекції, після яких відбувається зміна колекції. Поміняйте цей код так, щоб там використовувалися ваші нові методи додавання і видалення елементів колекції.

5.ru. Измените геттер, чтобы он возвращал представление коллекции, доступное только для чтения.
5.en. Change the getter so that it returns a read-only representation of the collection.
5.uk. Змініть геттер так, щоб він повертав представлення колекції, доступне тільки для читання.

6.ru. Исследуйте клиентский код, использующий коллекцию, в поисках кода, который бы лучше смотрелся внутри самого класса коллекции.
6.en. Inspect the client code that uses the collection for code that would look better inside of the collection class itself.
6.uk. Обстежте клієнтський код, що використовує колекцію, у пошуках ділянки, яку краще перемістити всередину самого класу колекції.



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

// Client code
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

// Client code
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

#|ru| Давайте рассмотрим <i>Инкапсуляцию коллекции</i> на примере каталога обучающих курсов.
#|en| Let's look at <i>Encapsulate Collection</i>, using a catalog of training courses as our example.
#|uk| Давайте розглянемо <i>Інкапсуляцію колекції<i> на прикладі каталогу навчальних курсів.

Select name of "Course"

#|ru| Класс курсов довольно прост.
#|en| The class of courses is rather simple.
#|uk| Клас курсів досить простий.

Select name of "Person"

#|ru| Кроме того есть ещё класс посетителей курсов.
#|en| Plus there is also a class of course students.
#|uk| Крім того є ще клас відвідувачів курсів.

Go to "$kent = |||new Person();"

#|ru| При таком интерфейсе клиенты добавляют курсы с помощью следующего кода.
#|en| With this interface, clients add courses via the following code.
#|uk| При такому інтерфейсі клієнти додають курси за допомогою наступного коду.

Go to the end of "class Person"

#|ru| Итак, первым делом нужны надлежащие методы модификации для этой коллекции.
#|en| Thus, first we need proper modification methods for this collection.
#|uk| Отже, насамперед потрібні належні методи модифікації для цієї колекції.

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

#|ru| Также давайте облегчим себе жизнь, проинициализировав поле:
#|en| We can also make life easier for ourselves by initializing the field:
#|uk| Також давайте полегшимо собі життя, проініціалізуваши поле:

Print:
```

  public __construct() {
    $this->courses = new SplObjectStorage();
  }
```

Set step 3

Select name of "setCourses"

#|ru| Теперь посмотрим на пользователей сеттера <code>setCourses</code>. Если клиентов много и сеттер интенсивно используется, необходимо заменить тело метода так, чтобы в нем использовались операции добавления и удаления.
#|en| Now look at the uses of the <code>setCourses</code> setter. If there are many clients and the setter is used intensively, replace the method body so that it uses add/remove operations.
#|uk| Тепер подивимося на користувачів сетера <code>setCourses</code>. Якщо клієнтів багато і сетер інтенсивно використовується, необхідно замінити тіло методу так, щоб в ньому використовувалися операції додавання і видалення.

Select "$kent->setCourses($s)"

#|ru| Сложность этой процедуры зависит от способа использования сеттера коллекции. В простейшем из них клиент инициализирует значения с помощью сеттера, т.е. до его применения курсов не существует.
#|en| The complexity of this procedure depends on the way in which the collection setter is used. In the most simple case, the client initializes values by using the setter, meaning that courses do not exist prior to use of the method.
#|uk| Складність цієї процедури залежить від способу використання сетера колекції. У найпростішому з них клієнт ініціалізує значення за допомогою сетера, тобто до застосування методу курсів не існує.

Select body of "setCourses"

#|ru| В этом случае нужно изменить тело сеттера так, чтобы в нем использовался метод добавления.
#|en| If this is the case, change the setter body so that it uses collection's <code>add</code> method.
#|uk| В цьому випадку потрібно змінити тіло сетера так, щоб в ньому використовувався метод додавання.

Print:
```
    assert($this->courses->count() > 0, "Courses are not empty");
    foreach ($arg as $item) {
      $this->courses->attach($item);
    }
```

Select name of "setCourses"

#|ru| После такой модификации стоит с помощью <a href="/rename-method">переименования метода</a> сделать намерения более ясными.
#|en| After this modification, use <a href="/rename-method">Rename Method</a> to make your intentions obvious.
#|uk| Після такої модифікації варто за допомогою <a href="/rename-method">перейменування методу</a> зробити наміри більш зрозумілими.

Select "setCourses"

Replace "initializeCourses"

#|ru| В общем случае мы должны сначала прибегнуть к методу удаления и убрать все элементы, а затем добавлять новые. Однако это происходит редко (как и бывает с общими случаями).
#|en| But in a general case, this method should first remove all existing collection items, and then add new ones.
#|uk| У загальному випадку ми повинні спочатку вдатися до методу видалення і прибрати всі елементи, а потім додавати нові. Однак це відбувається рідко (як і буває з загальними випадками).

#|ru| Если мы знаем, что другого поведения при добавлении элементов во время инициализации нет, можно убрать цикл и применить addAll.
#|en| If we know that there is no other behavior when elements are added during initialization, we can get rid of the cycle and use addAll.
#|uk| Якщо ми знаємо, що іншої поведінки при додаванні елементів під час ініціалізації немає, можна прибрати цикл і застосувати addAll.


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

#|ru| Стоит упомянуть, что мы не можем просто присвоить значение множеству, даже если предыдущее множество было пустым. Если клиент соберётся модифицировать множество после того, как передаст его, это станет нарушением инкапсуляции. Поэтому мы должны создать копию.
#|en| Remember that we cannot simply assign a value to a set even if the previous set was empty. If the client plans to modify the set after passing it, this will violate encapsulation. So we always have to create copies.
#|uk| Варто згадати, що ми не можемо просто привласнити значення множини, навіть якщо попередня множина була порожньою. Якщо клієнт збереться модифікувати множину після того, як передасть її, це стане порушенням інкапсуляції. Тому ми повинні створити копію.

Select:
```
$s = new SplObjectStorage();

```

#|ru| Если клиенты просто создают множество и пользуются методом установки, мы можем заставить их пользоваться методами добавления и удаления непосредственно…
#|en| If client code simply creates a set and uses its setter, we should force it to use add/remove methods instead…
#|uk| Якщо клієнти просто створюють множину і користуються методом установки, ми можемо змусити їх користуватися методами додавання та видалення безпосередньо ,а також повністю прибрати виклик методу ініціалізації.

Remove selected

Select:
```
|||$s->attach|||(new Course("Smalltalk Programming", false));
|||$s->attach|||(new Course("Appreciating Single Malts", true));
```

Replace "$kent->addCourse"

Wait 500ms

Select:
```
$kent->initializeCourses($s);

```

#|ru| …и полностью убрать вызов метода инициализации.
#|en| …and get rid of the call to the initialization method completely.
#|uk| …і повністю прибрати виклик методу ініціалізації.

Remove selected

Set step 4


Select "getCourses()->attach"
+ Select "getCourses()->detach"

#|ru| Теперь нужно рассмотреть, кто использует геттер коллекции. В первую очередь нас должны интересовать случаи модификации коллекции с его помощью.
#|en| Now let's see who is using the collection's getter. Our foremost interest should go to cases when it is used to modify the collection.
#|uk| Тепер потрібно розглянути, хто використовує геттер колекції. В першу чергу нас повинні цікавити випадки модифікації колекції з його допомогою.

#|ru| Такие вызовы следует заменять вызовами метода добавления или удаления курсов.
#|en| We need to replace such calls with add/remove calls.
#|uk| Такі виклики слід замінювати викликами методу додавання або видалення курсів.

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

#|ru| Последним штрихом следует изменить тело геттера так, чтобы он возвращал значение, доступное только для чтения (другими словами неизменяемое представление коллекции).
#|en| As the finishing touch, let's change the getter's body so that it returns a read-only value (an immutable representation of the collection).
#|uk| Останнім штрихом слід змінити тіло геттера так, щоб він повертав значення, доступне тільки для читання (іншими словами незмінне уявлення колекції).


Print:
```
clone $this->courses
```

#C|ru| Запустим тестирование, чтобы убедиться в отсутствии ошибок.
#S Отлично, все работает!

#C|en| Let's launch autotests to check for errors in code.
#S Wonderful, it's all working!

#C|uk| Запустимо тестування, щоб переконатися у відсутності помилок.
#S Супер, все працює.

Select:
```
private |||$courses|||
```

#|ru| После этого коллекцию можно считать полностью инкапсулированной. Никто не сможет изменить её элементы, кроме как через методы <code>Person</code>.
#|en| At this point, we can consider the collection fully encapsulated. Nobody can change its elements other than by using the <code>Person</code> class' method.
#|uk| Після цього колекцію можна вважати повністю інкапсульованою. Ніхто не зможе змінити її елементи, крім як через методи <code>Person</code>.

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

#|ru| После того как для класса <code>Person</code> был создан корректный интерфейс, мы можем заняться перемещением релевантного кода в этот класс. Вот пример такого кода.
#|en| Now that we have a proper interface for the <code>Person</code> class, we can start moving the relevant code to this class. Here is an example of the code.
#|uk| Після того як для класу <code>Person</code> був створений коректний інтерфейс, ми можемо зайнятися переміщенням релевантного коду в цей клас. Ось приклад такого коду.

#|ru| Применим <a href="/extract-method">извлечение метода</a> к этому коду, чтобы переместить его в <code>Person</code>.
#|en| Apply <a href="/extract-method">Extract Method</a> to the code to move it to <code>Person</code>.
#|uk| Застосуємо <a href="/extract-method">відокремлення методу</a> до цього коду, щоб перемістити його в <code>Person</code>.

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

#|ru| Часто встречается такой код.
#|en| Take a loot at this code. It's quite frequently encountered.
#|uk| Часто зустрічається такий код.

Go to the end of "Person"

#|ru| Его можно заменить более читабельной версией.
#|en| It can be replaced with a more readable version.
#|uk| Його можна замінити більш читабельною версією.

Print:
```

  public function numberOfCourses() {
    return $this->courses->count();
  }
```

Select "$kent->getCourses()->count()"

Replace "$kent->numberOfCourses()"

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.