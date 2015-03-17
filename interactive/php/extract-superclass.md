extract-superclass:php

###

1.ru. Создайте абстрактный суперкласс.
1.en. Create an abstract superclass.
1.uk. Створіть абстрактний суперклас.

2.ru. Используйте <a href="/pull-up-field">подъём поля</a>, <a href="/pull-up-method">подъём метода</a> и <a href="/pull-up-constructor-body">подъём тела конструктора</a> для перемещения общей функциональности в суперкласс. Лучше начинать с полей, т.к. помимо общих полей, вам нужно будет перенести те из них, которые используются в общих методах.
2.en. Use <a href="/pull-up-field">Pull Up Field</a>, <a href="/pull-up-method">Pull Up Method</a>, and <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to move the common functionality to a superclass. Start with the fields, since in addition to the common fields you will need to move the fields that are used in the common methods.
2.uk. Використайте <a href="/pull-up-field">підйом поля</a>, <a href="/pull-up-method">підйом методу</a> і <a href="/pull-up-constructor-body">підйом тіла конструктора</a> для переміщення загальної функціональності в суперклас. Краще розпочинати з полів, оскільки окрім загальних полів, вам треба буде перенести ті з них, які використовуються в загальних методах.

3.ru. Стоит поискать места в клиентском коде, в которых можно заменить использование подклассов вашим общим классом (например, в объявлениях типов).
3.en. Look for places in the client code where use of subclasses can be replaced with your new class (such as in type declarations).
3.uk. Варто пошукати місця в клієнтському коді, в яких можна замінити використання підкласів вашим загальним класом (наприклад, в оголошеннях типів).



###

```
class Employee {
  private $name;
  private $annualCost;
  private $id;

  public function __construct($name, $id, $annualCost) {
    $this->name = $name;
    $this->id = $id;
    $this->annualCost = $annualCost;
  }
  public function getAnnualCost() {
    return $this->annualCost;
  }
  public function getId() {
    return $this->id;
  }
  public function getName() {
    return $this->name;
  }
}

class Department {
  private $name;
  private $staff = array();

  public function __construct($name) {
    $this->name = $name;
  }
  public function getTotalAnnualCost() {
    $result = 0;
    foreach ($this->staff as $each) {
      $result += $each->getAnnualCost();
    }
    return $result;
  }
  public function getHeadCount() {
    return count($staff);
  }
  public function getStaff() {
    return $this->staff;
  }
  public function addStaff(Employee $arg) {
    $this->staff[] = $arg;
  }
  public function getName() {
    return $this->name;
  }
}
```

###

```
abstract class Party {
  protected $name;

  protected function __construct($name) {
    $this->name = $name;
  }
  public function getName() {
    return $this->name;
  }
  public abstract function getAnnualCost();
  public abstract function getHeadCount();
}

class Employee extends Party {
  private $annualCost;
  private $id;

  public function __construct($name, $id, $annualCost) {
    parent::__construct($name)
    $this->id = $id;
    $this->annualCost = $annualCost;
  }
  public function getAnnualCost() {
    return $this->annualCost;
  }
  public function getId() {
    return $this->id;
  }
  public function getHeadCount() {
    return 1;
  }
}

class Department extends Party {
  private $staff = array();

  public function __construct($name) {
    parent::__construct($name)
  }
  public function getAnnualCost() {
    $result = 0;
    foreach ($this->staff as $each) {
      $result += $each->getAnnualCost();
    }
    return $result;
  }
  public function getHeadCount() {
    $headCount = 0;
    foreach ($this->staff as $each) {
      $headCount += $each->getHeadCount();
    }
  }
  public function getStaff() {
    return $this->staff;
  }
  public function addStaff(Party $arg) {
    $this->staff[] = $arg;
  }
}
```

###

Set step 1

Select name of "Employee"
+ Select name of "Department"

#|ru| Рассмотрим этот рефакторинг на примере классов служащих и отдела.
#|en| Let's look at <i>Extract Superclass</i> using the example of employees and their department.
#|uk| Розглянемо цей рефакторинг на прикладі класів службовців та відділу.

Select "private $name"

#|ru| У этих классов есть несколько общих черт. Во-первых, как у служащих, так и у отделов есть имена или названия.
#|en| These classes have several traits in common. First, as with employees, departments also have names.
#|uk| У цих класів є кілька спільних рис. По-перше, як у службовців, так і у відділів є імена або назви.

Select "private $annualCost"
+ Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"

#|ru| Во-вторых, для обоих классов есть годовой бюджет (annualcost), хотя методы его расчёта слегка различаются.
#|en| Second, for both classes there is an annual budget (<code>annualcost</code), although the calculation methods are slightly different.
#|uk| По-друге, для обох класів є річний бюджет (annualcost), хоча методи його розрахунку трошки розрізняються.

#|ru| Поэтому имеет смысл выделить эти моменты в общий родительский класс.
#|en| For this reason, it would be good to extract these aspects to a shared parent class.
#|uk| Тому має сенс виділити ці моменти в загальний батьківський клас.

Go to before "Employee"

#|ru| Итак, на первом этапе создаём новый родительский класс, а имеющиеся классы определяем как его подклассы.
#|en| To start, we create a new parent class, and we define the existing classes as subclasses of it.
#|uk| Отже, на першому етапі створюємо новий батьківський клас, а наявні класи визначаємо як його підкласи.

Print:
```
abstract class Party {
}


```

Go to "class Employee|||"

Print " extends Party"

Wait 500ms

Go to "class Department|||"

Print " extends Party"

Select:
```
  private $name;
```

#|ru| Теперь всё готово, чтобы начать поднимать функции в родительский класс. Обычно проще сначала выполнить <a href="/ru/pull-up-field">Подъем полей</a>.
#|en| Now we can start pulling up functions to the parent class. Usually it is simpler to employ <a href="/pull-up-field">Pull Up Field</a> first.
#|uk| Тепер все готово, щоб почати піднімати функції в батьківський клас. Зазвичай простіше спочатку виконати <a href="/uk/pull-up-field">Підйом поля</a>.

Go to start of "Party"

Print:
```

  protected $name;
```

Select:
```
  private $name;

```

Remove selected

Select whole of "getName"

#|ru| После этого можно применить <a href="/ru/pull-up-method">Подъем метода</a> к методам доступа этого поля.
#|en| Then use <a href="/pull-up-method">Pull Up Method</a> on the methods for accessing the field.
#|uk| Після цього можна застосувати <a href="/uk/pull-up-method">Підйом методу</a> до методів доступу цього поля.

Go to end of "Party"

Print:
```


  public function getName() {
    return $this->name;
  }
```

Wait 500ms

Select in "Employee":
```

  public function getName() {
    return $this->name;
  }
```
+ Select in "Department":
```

  public function getName() {
    return $this->name;
  }
```

Remove selected

Select:
```
$this->name = $name;
```

#|ru| Лучше, чтобы поля были защищёнными от публики, но для этого сперва нужно выполнить <a href="/ru/pull-up-constructor-body">Подъем тела конструктора</a>, чтобы инициализировать их.
#|en| The fields should be protected from the public, but for this we must first do <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to initialize them.
#|uk| Краще, щоб поля були захищеними від публіки, але для цього спершу потрібно виконати <a href="/uk/pull-up-constructor-body">Підйом тіла конструктора</a>, щоб ініціалізувати їх.

Go to before "getName" in "Party"

Print:
```

  protected function __construct($name) {
    $this->name = $name;
  }
```

#|ru| В подклассах теперь можно убрать инициализацию поля, заменив её вызовами родительского конструктора.
#|en| In the subclasses, we can go ahead and remove code initialization, placing parent constructor calls there instead.
#|uk| В підкласах тепер можна прибрати ініціалізацію поля, замінивши її викликами батьківського конструктора.

Select "$this->name = $name;" in "Employee"
+ Select "$this->name = $name;" in "Department"

Replace "parent::__construct($name)"

#|ru| На этом перенос имени окончен, и можно взяться за годовой бюджет.
#|en| The name has been moved, which leaves us only the annual budget.
#|uk| На цьому перенесення імені закінчено, і можна взятися за річний бюджет.

Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"

#|ru| Методы <code>getTotalAnnualCost</code> и <code>getAnnualCost</code> имеют одинаковое назначение, поэтому у них должно быть одинаковое название. Сначала применим <a href="/rename-method">Переименование метода</a>, чтобы привести их к одному и тому же названию.
#|en| The <code>getTotalAnnualCost</code> and <code>getAnnualCost</code> methods have the same purpose, so they should have the same name. Use <a href="/rename-method">Rename Method</a> to give them the same name.
#|uk| Методи <code>getTotalAnnualCost</code> і <code>getAnnualCost</code> мають однакове призначення, тому у них має бути однакова назва. Спочатку застосуємо <a href="/uk/rename-method">Перейменування методу</a>, щоб привести їх до однієї і тієї ж назвою.

Select name of "getTotalAnnualCost"

Replace "getAnnualCost"

#|ru| Тела методов пока что различаются, поэтому мы не можем применить <a href="/ru/pull-up-method">Подъем метода</a>. С другой стороны, мы можем объявить в родительском классе абстрактный метод с таким же именем.
#|en| The bodies of the methods are currently different, so we cannot use <a href="/pull-up-method">Pull Up Method</a>. On the other hand, we can declare an abstract method with the same name in the parent class.
#|uk| Тіла методів поки що розрізняються, тому ми не можемо застосувати <a href="/uk/pull-up-method">Підйом методу</a>. З іншого боку, ми можемо оголосити в батьківському класі абстрактний метод з таким же ім'ям.

Go to the end of "Party"

Print:
```

  public abstract function getAnnualCost();
```

Select name of "Party"

#|ru| Осуществив все эти изменения, давайте рассмотрим клиентов обоих классов, чтобы выяснить, можно ли изменить их так, чтобы они использовали новый родительский класс.
#|en| Having made these changes, let's look at clients of both classes to determine whether we can make them use the new parent class.
#|uk| Здійснивши всі ці зміни, давайте розглянемо клієнтів обох класів, щоб з'ясувати, чи можна змінити їх так, щоб вони використовували новий батьківський клас.

Select body of "getAnnualCost" in "Department"

#|ru| Одним из клиентов этих классов является сам класс <code>Department</code>, содержащий коллекцию классов служащих. Метод <code>getAnnualCost</code> использует только метод подсчёта годового бюджета, который теперь объявлен в <code>Party</code>.
#|en| One of the clients of the classes is the <code>Department</code> class itself, which contains a collection of employee classes. The <code>getAnnualCost</code> method uses only the annual budget calculation method, which is now declared in <code>Party</code>.
#|uk| Одним з клієнтів цих класів є сам клас <code>Department</code>, що містить колекцію класів службовців. Метод <code>getAnnualCost</code> використовує тільки метод підрахунку річного бюджету, який тепер оголошений в <code>Party</code>.

#|ru| Такое поведение открывает новую возможность. Мы можем рассматривать применение паттерна <a href="http://sourcemaking.com/design_patterns/composite">компоновщик</a> к <code>Department</code> и <code>Employee</code>.
#|en| This behavior offers a new opportunity. We can consider using the <a href="http://sourcemaking.com/design_patterns/composite">Composite</a> pattern on <code>Department</code> and <code>Employee</code>. 
#|uk| Така поведінка відкриває нову можливість. Ми можемо розглядати застосування патерну <a href="http://sourcemaking.com/design_patterns/composite">компоновщик</a> до <code>Department</code> і <code>Employee</code>.

#|ru| Это позволит включать один отдел в другой. В результате создаётся новая функциональность, так что нельзя, строго говоря, называть эти действия рефакторингом.
#|en| That allows including one department in another. The result is new functionality, so strictly speaking this goes beyond refactoring.
#|uk| Це дозволить включати один відділ в інший. В результаті створюється нова функціональність, так що не можна, строго кажучи, називати ці дії рефакторингом.

#|ru| Тем не менее, если бы требовалась компоновка, мы получили бы её, изменив тип поля <code>staff</code>, чтобы картина была нагляднее.
#|en| Be that as it may, if the Composite pattern were necessary, we would get it by changing the type of the <code>staff</code> field.
#|uk| Проте, якби була потрібна компоновка, ми отримали б її, змінивши тип поля <code>staff</code>, щоб картина була наочніше.

Select "Employee" in "Department"

#|ru| Такое изменение повлекло бы соответствующее изменение имени <code>addStaff</code> и замену параметра на <code>Party</code>.
#|en| This change would entail a corresponding change in the name of <code>addStaff</code> and change of the parameter to <code>Party</code>.
#|uk| Така зміна спричинила б відповідну зміну імені <code>addStaff</code> і заміну параметра на <code>Party</code>.

Print "Party"

Select body of "getHeadCount"

#|ru| В окончательной редакции метод <code>headCount</code> должен быть сделан рекурсивным.
#|en| In the final version, the <code>headCount</code> method should be made recursive.
#|uk| В остаточній редакції метод <code>headCount</code> повинен бути зроблений як рекурсивний.

Print:
```
    $headCount = 0;
    foreach ($this->staff as $each) {
      $headCount += $each->getHeadCount();
    }
```

#|ru| Но для того, чтобы этот метод заработал, необходимо объявить аналогичный метод в <code>Employee</code>, который просто возвращает <code>1</code>.
#|en| But for this method to work, we must declare an equivalent method in <code>Employee</code> that simply returns <code>1</code>.
#|uk| Але для того, щоб цей метод заробив, необхідно оголосити аналогічний метод в <code>Employee</code>, який просто повертає <code>1</code>.

Go to the end of "Employee"

Print:
```

  public function getHeadCount() {
    return 1;
  }
```

Go to the end of "Party"

#|ru| Этот метод теперь тоже следует объявить абстрактным в родительском классе.
#|en| This method should also be declared abstract in the parent class.
#|uk| Цей метод тепер теж слід оголосити абстрактним в батьківському класі.

Print:
```

  public abstract function getHeadCount();
```

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