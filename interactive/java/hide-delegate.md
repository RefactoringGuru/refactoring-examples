hide-delegate:java

###

1. Для каждого метода <i>класса-делегата</i>, вызываемого в коде, надо создать метод в <i>классе-сервере</i>, который бы делегировал вызовы <i>классу-делегату</i>.

2. Измените код Клиента так, чтобы он вызывал методы <i>класса-сервера</i>.

3. Если после всех изменений необходимости в прямом доступе к <i>классу-делегату</i> нет, то можно убрать доступ к <i>классу-делегату</i> из <i>класса-сервера</i>.



###

```
class Person {
  Department department;

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
  Department department;

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

# Давайте рассмотрим <i>Сокрытие делегирования</i> на примере классов, представляющих работника и его отдел.

Select "manager = john.getDepartment().getManager();"

# Если клиенту требуется узнать, кто является менеджером некоторого лица, он должен сначала узнать, в каком отделе это лицо работает.

# Так клиентскому коду открывается характер работы класса <code>Department</code> и то, что в нем хранятся данные о менеджере.

Set step 2

Go to the end of "Person"

# Эту связь можно сократить, скрыв от клиента класс <code>Department</code> при помощи создания простого делегирующего метода в <code>Person</code>.

Print:
```

  public Person getManager() {
    return department.getManager();
  }
```

Set step 3

Select "john.getDepartment().getManager();"

# Теперь необходимо модифицировать код таким образом, чтобы в нем использовался новый метод.

Print "john.getManager();"

Select whole "getDepartment"

# После того, как все необходимые методы делегированы, можно удалить метод в классе <code>Person</code>, который предоставлял доступ к экземпляру <code>Department</code>.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.