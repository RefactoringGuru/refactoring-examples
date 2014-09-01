remove-middle-man:java

###

1. Создайте геттер для доступа к объекту <i>класса-делегата</i> из объекта <i>класса-сервера</i>.

2. Замените вызовы делегирующих методов <i>класса-сервера</i>, прямыми вызовами методов <i>класса-делегата</i>.



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

  public Department(Person arg) {
    manager = arg;
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

  public Department(Person arg) {
    manager = arg;
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

Set step 1

# Давайте рассмотрим <i>Удаление посредника</i> на примере классов, представляющих работника и его отдел (обратная ситуация по отношению к примеру <i>Сокрытие делегирования</i>).

Select "manager = john.getManager();"

# Чтобы узнать, кто является менеджером некоторого лица, клиент делает запрос в самом классе <code>Person</code>.

Select body of "getManager"

# Это простая конструкция, инкапсулирующая экземпляр класса <code>Department</code>. Однако при наличии множества методов, устроенных таким образом, в классе <code>Person</code> окажется слишком много простых делегирований. В этом случае такое посредничество лучше удалить.

Go to before "setDepartment"

# Для начала создадим метод для доступа к делегату.

Print:
```

  public Department getDepartment() {
    return department;
  }
```
Set step 2

# После этого нужно по очереди пересмотреть каждый делегирующий метод <code>Person</code> и найти использующий его код. Этот код следует изменить так, чтобы он сначала получал класс-делегат (<code>Department</code>), после чего через него уже напрямую вызывал нужный метод.

Select name "getManager"

# Вот как это будет выглядеть для метода получения менеджера.

Select "john.|||getManager()|||"

# Сначала находим места, где он используется.

# Затем изменяем этот код таким образом, чтобы он вызывал методы делегата напрямую.

Print "getDepartment().getManager()"

Select whole "getmanager"

# После всех замен делегирующий метод <code>GetManager()</code> можно удалить из класса <code>Person</code>.

Remove selected

# И напоследок: не обязательно удалять все делегирующие методы. Может оказаться удобнее сохранить некоторые из делегирований. Действуйте по ситуации.

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.