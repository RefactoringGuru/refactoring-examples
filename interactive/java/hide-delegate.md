hide-delegate:java

###

1.ru. Для каждого метода <i>класса-делегата</i>, вызываемого в коде, надо создать метод в <i>классе-сервере</i>, который бы делегировал вызовы <i>классу-делегату</i>.
1.uk. Для кожного методу <i>класу-делегата</i>, що викликається в коді, треба створити метод в <i>класі-сервері</i>, який би делегував виклики <i>класу-делегату</i>.

2.ru. Измените код клиента так, чтобы он вызывал методы <i>класса-сервера</i>.
2.uk. Змініть код клієнта так, щоб він викликав методи <i>класу-сервера</i>.

3.ru. Если после всех изменений необходимости в прямом доступе к <i>классу-делегату</i> нет, то можно убрать доступ к <i>классу-делегату</i> из <i>класса-сервера</i>.
3.uk. Якщо після всіх змін необхідності в прямому доступі до <i>класу-делегату</i> немає, то можна прибрати доступ до <i>класу-делегату</i> з <i>класу-сервера</i>.



###

```
class Person {
  private Department department;

  public Department getDepartment() {
    return department;
  }
  public void setDepartment(Department arg) {
    department = arg;
  }
}

class Department {
  private String chargeCode;
  private Person manager;

  public Department(Person manager) {
    this.manager = manager;
  }
  public Person getManager() {
    return manager;
  }

  //...
}

// Somewhere in client code
manager = john.getDepartment().getManager();
```

###

```
class Person {
  private Department department;

  public void setDepartment(Department arg) {
    department = arg;
  }
  public Person getManager() {
    return department.getManager();
  }
}

class Department {
  private String chargeCode;
  private Person manager;

  public Department(Person manager) {
    this.manager = manager;
  }
  public Person getManager() {
    return manager;
  }

  //...
}

// Somewhere in client code
manager = john.getManager();
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Сокрытие делегирования</i> на примере классов, представляющих работника и его отдел.
#|uk| Давайте розглянемо <i>Приховування делегування<i> на прикладі класів, що представляють працівника та його відділ.

Select "manager = john.getDepartment().getManager();"

#|ru| Если клиенту требуется узнать, кто является менеджером некоторого лица, он должен сначала узнать, в каком отделе это лицо работает.
#|uk| Якщо клієнтові потрібно дізнатися, хто є менеджером деякої особи, він повинен спочатку дізнатися, в якому відділі ця особа працює.

#|ru| Так клиентскому коду открывается характер работы класса <code>Department</code> и то, что в нем хранятся данные о менеджере.
#|uk| Так клієнтському коду відкривається характер роботи класу <code>Department</code> і те, що в ньому зберігаються дані про менеджера.

Set step 2

Go to the end of "Person"

#|ru| Эту связь можно сократить, скрыв от клиента класс <code>Department</code> при помощи создания простого делегирующего метода в <code>Person</code>.
#|uk| Цей зв'язок можна скоротити, приховавши від клієнта клас <code>Department</code> за допомогою створення простого делегуючого методу в <code>Person</code>.

Print:
```

  public Person getManager() {
    return department.getManager();
  }
```

Set step 3

Select "john.getDepartment().getManager();"

#|ru| Теперь необходимо модифицировать код таким образом, чтобы в нем использовался новый метод.
#|uk| Тепер необхідно модифікувати код таким чином, щоб у ньому використовувався новий метод.

Print "john.getManager();"

Select whole "getDepartment"

#|ru| После того, как все необходимые методы делегированы, можно удалить метод в классе <code>Person</code>, который предоставлял доступ к экземпляру <code>Department</code>.
#|uk| Після того, як всі необхідні методи делеговані, можна видалити метод в класі <code>Person</code>, який надавав доступ до примірника <code>Department</code>.

Remove selected

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.