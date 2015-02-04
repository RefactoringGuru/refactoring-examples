remove-middle-man:php

###

1.ru. Создайте геттер для доступа к объекту <i>класса-делегата</i> из объекта <i>класса-сервера</i>.
1.en. Create a getter for accessing the <i>delegate-class</i> object from the <i>server-class</i> object.
1.uk. Створіть геттер для доступу до об'єкту <i>класу-делегату</i> з об'єкту <i>класу-сервера</i>.

2.ru. Замените вызовы делегирующих методов <i>класса-сервера</i> прямыми вызовами методов <i>класса-делегата</i>.
2.en. Replace calls to delegating methods in the <i>server-class</i> with direct calls for methods in the <i>delegate-class</i>.
2.uk. Замініть виклики делегуючих методів <i>класу-серверу</i> прямими викликами методів <i>класу-делегату</i>.



###

```
class Person {
  private $department; // Department

  public function setDepartment($arg) {
    $this->department = $arg;
  }
  public function getManager() {
    return $this->department->getManager();
  }
}

class Department {
  private $chargeCode;
  private $manager; // Person

  public function __construct(Person $arg) {
    $this->manager = $arg;
  }
  public function getManager() {
    return $this->manager;
  }

  //…
}

// Somewhere in client code
$manager = $john->getManager();
```

###

```
class Person {
  private $department; // Department

  public function getDepartment() {
    return $this->department;
  }
  public function setDepartment($arg) {
    $this->department = $arg;
  }
}

class Department {
  private $chargeCode;
  private $manager; // Person

  public function __construct(Person $arg) {
    $this->manager = $arg;
  }
  public function getManager() {
    return $this->manager;
  }

  //…
}

// Somewhere in client code
$manager = $john->getDepartment()->getManager();
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Удаление посредника</i> на примере классов, представляющих работника и его отдел (обратная ситуация по отношению к примеру <i>Сокрытие делегирования</i>).
#|en| Let's look at <i>Remove Middle Man</i>, using the example of classes that represent an employee and the employee's department (the reverse of the situation in the <i>Hide Delegate</i> example).
#|uk| Давайте розглянемо <i>Видалення посередника<i> на прикладі класів, що представляють працівника і його відділ (зворотня ситуація по відношенню до прикладу <i>Приховування делегування<i>).

Select "$manager = $john->getManager();"

#|ru| Чтобы узнать, кто является менеджером некоторого лица, клиент делает запрос в самом классе <code>Person</code>.
#|en| To learn who a person's manager is, the client makes a query in the <code>Person</code> class itself.
#|uk| Щоб дізнатися, хто є менеджером деякої особи, клієнт робить запит в самому класі <code>Person</code>.

Select body of "getManager"

#|ru| Это простая конструкция, инкапсулирующая экземпляр класса <code>Department</code>. Однако при наличии множества методов, устроенных таким образом, в классе <code>Person</code> окажется слишком много простых делегирований. В этом случае такое посредничество лучше удалить.
#|en| This is a simple structure that encapsulates an instance of the <code>Department</code> class. But if there are many such methods, the <code>Person</code> class will have too many simple delegates. In this case, we should get rid of the middle men.
#|uk| Це проста конструкція, інкапсулює екземпляр класу <code>Department</code>. Однак при наявності безлічі методів, влаштованих таким чином, в класі <code>Person</code> виявиться занадто багато простих делегування. У цьому випадку таке посередництво краще видалити.

Go to before "setDepartment"

#|ru| Для начала создадим метод для доступа к делегату.
#|en| To start, let's create a method for delegate access.
#|uk| Для початку створимо метод для доступу до делегату.

Print:
```

  public function getDepartment() {
    return $this->department;
  }
```
Set step 2

#|ru| После этого нужно по очереди пересмотреть каждый делегирующий метод <code>Person</code> и найти использующий его код. Этот код следует изменить так, чтобы он сначала получал класс-делегат (<code>Department</code>), после чего через него уже напрямую вызывал нужный метод.
#|en| Then, review each delegate method of <code>Person</code> and find the code that uses it. Modify the code so that it first gets the delegate class (<code>Department</code>), and then directly calls the necessary method through the delegate method.
#|uk| Після цього потрібно по черзі переглянути кожен делегуючий метод <code>Person</code> і знайти код,що його використовує. Цей код слід змінити так, щоб він спочатку отримував клас-делегат (<code>Department</code>), після чого через нього вже безпосередньо викликав потрібний метод.

Select name "getManager"

#|ru| Вот как это будет выглядеть для метода получения менеджера.
#|en| Here is how it will look for the method for getting the manager.
#|uk| Ось як це буде виглядати для методу отримання менеджера.

Select "$john->|||getManager()|||"

#|ru| Сначала находим места, где он используется.
#|en| First find the places where it is used.
#|uk| Спочатку знаходимо місця, де він використовується.

#|ru| Затем изменяем этот код таким образом, чтобы он вызывал методы делегата напрямую.
#|en| Then change the code so that it calls delegate methods directly.
#|uk| Потім змінюємо цей код таким чином, щоб він викликав методи делегата безпосередньо.

Print "getDepartment()->getManager()"

Select whole "getManager"

#|ru| После всех замен делегирующий метод <code>getManager()</code> можно удалить из класса <code>Person</code>.
#|en| After all replacements are done, the <code>getManager()</code> delegate method can be removed from the <code>Person</code> class.
#|uk| Після всіх замін делегуючий метод <code>getManager()</code> можна видалити з класу <code>Person</code>.

Remove selected

#|ru| И напоследок: не обязательно удалять все делегирующие методы. Может оказаться удобнее сохранить некоторые из делегирований. Действуйте по ситуации.
#|en| And finally: removing all delegate methods is not always necessary. It may be more convenient to maintain some delegation, so see what is best for your particular situation.
#|uk| І наостанок: не обов'язково видаляти усі делегуючи методи. Може виявитися зручнішим зберегти деякі з делегувань. Дійте за обставинами.

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