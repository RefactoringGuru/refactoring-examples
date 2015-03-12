hide-delegate:php

###

1.ru. Для каждого метода <i>класса-делегата</i>, вызываемого в коде, надо создать метод в <i>классе-сервере</i>, который бы делегировал вызовы <i>классу-делегату</i>.
1.en. For each <i>delegate class</i> method that is called in the code, create a method in the <i>server class</i> that delegates calls to the <i>delegate class</i>.
1.uk. Для кожного методу <i>класу-делегата</i>, що викликається в коді, треба створити метод в <i>класі-сервері</i>, який би делегував виклики <i>класу-делегату</i>.

2.ru. Измените код клиента так, чтобы он вызывал методы <i>класса-сервера</i>.
2.en. Change the client code so that it calls the methods of the <i>server-class</i>.
2.uk. Змініть код клієнта так, щоб він викликав методи <i>класу-сервера</i>.

3.ru. Если после всех изменений необходимости в прямом доступе к <i>классу-делегату</i> нет, то можно убрать доступ к <i>классу-делегату</i> из <i>класса-сервера</i>.
3.en. If after all these changes you no longer need direct access to the <i>delegate class</i>, you can proceed to remove access to the <i>delegate class</i> from the <i>server class</i>. 
3.uk. Якщо після всіх змін необхідності в прямому доступі до <i>класу-делегату</i> немає, то можна прибрати доступ до <i>класу-делегату</i> з <i>класу-сервера</i>.



###

```
class Person {
  private $department; // Department

  public function getDepartment() {
    return $this->department;
  }
  public function setDepartment(Department $arg) {
    $this->department = $arg;
  }
}

class Department {
  private $chargeCode;
  private $manager; // Person

  public function __construct(Person $manager) {
    $this->manager = $manager;
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

```
class Person {
  private $department; // Department

  public function setDepartment(Department $arg) {
    $this->department = $arg;
  }
  public function getManager() {
    return $this->department->getManager();
  }
}

class Department {
  private $chargeCode;
  private $manager; // Person

  public function __construct(Person $manager) {
    $this->manager = $manager;
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

Set step 1

#|ru| Давайте рассмотрим <i>Сокрытие делегирования</i> на примере классов, представляющих работника и его отдел.
#|en| Let's look at <i>Hide Delegate</i> using the classes representing an employee and the employee's department as an example.
#|uk| Давайте розглянемо <i>Приховування делегування<i> на прикладі класів, що представляють працівника та його відділ.

Select "$manager = $john->getDepartment()->getManager();"

#|ru| Если клиенту требуется узнать, кто является менеджером некоторого лица, он должен сначала узнать, в каком отделе это лицо работает.
#|en| If the client needs to know the manager of a certain employee, the client must first know in which department that person works.
#|uk| Якщо клієнтові потрібно дізнатися, хто є менеджером деякої особи, він повинен спочатку дізнатися, в якому відділі ця особа працює.

#|ru| Так клиентскому коду открывается характер работы класса <code>Department</code> и то, что в нем хранятся данные о менеджере.
#|en| That way, along with the manager value, client code gets full access to the <code>Department</code> object and its other fields. If that doesn't look very safe to you… you're right.
#|uk| Так клієнтському коду відкривається характер роботи класу <code>Department</code> і те, що в ньому зберігаються дані про менеджера.

Set step 2

Go to the end of "Person"

#|ru| Эту связь можно сократить, скрыв от клиента класс <code>Department</code> при помощи создания простого делегирующего метода в <code>Person</code>.
#|en| This association can be reduced by hiding the <code>Department</code> class from the client, by creating a simple delegate method in <code>Person</code>.
#|uk| Цей зв'язок можна скоротити, приховавши від клієнта клас <code>Department</code> за допомогою створення простого делегуючого методу в <code>Person</code>.

Print:
```

  public function getManager() {
    return $this->department->getManager();
  }
```

Set step 3

Select "$john->getDepartment()->getManager();"

#|ru| Теперь необходимо модифицировать код таким образом, чтобы в нем использовался новый метод.
#|en| Now let's modify the code so that it uses the new method.
#|uk| Тепер необхідно модифікувати код таким чином, щоб у ньому використовувався новий метод.

Print "$john->getManager();"

Select whole "getDepartment"

#|ru| После того как все необходимые методы делегированы, можно удалить метод в классе <code>Person</code>, который предоставлял доступ к экземпляру <code>Department</code>.
#|en| Once all necessary methods have been delegated, we can remove the method in the <code>Person</code> class that provided access to the <code>Department</code> instance.
#|uk| Після того як всі необхідні методи делеговані, можна видалити метод в класі <code>Person</code>, який надавав доступ до примірника <code>Department</code>.

Remove selected

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