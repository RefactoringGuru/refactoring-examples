remove-middle-man:csharp

###

1. Создайте геттер для доступа к объекту <i>класса-делегата</i> из объекта <i>класса-сервера</i>.

2. Замените вызовы делегирующих методов <i>класса-сервера</i>, прямыми вызовами методов <i>класса-делегата</i>.



###

```
public class Person
{
  private Department department;

  public Person GetManager()
  {
    return department.Manager;
  }
}

public class Department
{
  private string chargeCode;

  public Person Manager
  {
    get;
  }
  
  public Department(Person manager)
  {
    this.Manager = manager;
  }
  
  //...
}

// Somewhere in client code
manager = john.GetManager();
```

###

```
public class Person
{
  private Department department;

  public Department Department
  {
    get {
      return department;
    }
  }
}

public class Department
{
  private string chargeCode;

  public Person Manager
  {
    get;
  }
  
  public Department(Person manager)
  {
    this.Manager = manager;
  }
  
  //...
}

// Somewhere in client code
manager = john.Department.Manager;
```

###

Set step 1

# Давайте рассмотрим <i>Удаление посредника</i> на примере классов, представляющих работника и его отдел (обратная ситуация по отношению к примеру <i>Сокрытие делегирования</i>).

Select "manager = john.GetManager();"

# Чтобы узнать, кто является менеджером некоторого лица, клиент делает запрос в самом классе <code>Person</code>.

Select body of "GetManager"

# Это простая конструкция, инкапсулирующая экземпляр класса <code>Department</code>. Однако при наличии множества методов, устроенных таким образом, в классе <code>Person</code> окажется слишком много простых делегирований. В этом случае такое посредничество лучше удалить.

Select "Department |||department|||;"

# Для начала создадим публичное свойство для доступа к делегату.

Go to before "GetManager"

Print:
```

  public Department Department
  {
    get {
      return department;
    }
  }
```
Set step 2

Select name "GetManager"

# После этого нужно по очереди пересмотреть каждый делегирующий метод <code>Person</code> и найти использующий его код. Этот код следует изменить так, чтобы он сначала получал класс-делегат (<code>Department</code>), после чего через него уже напрямую обращался к интересующим свойствам или методам.

# Вот как это будет выглядеть для метода получения менеджера.

Select "john.|||GetManager()|||"

# Сначала находим места, где он используется.

# Затем изменяем этот код таким образом, чтобы он получал свойство делегата напрямую.

Print "Department.Manager"

Select whole "GetManager"

# После всех замен делегирующий метод <code>GetManager()</code> можно удалить из класса <code>Person</code>.

Remove selected

# И напоследок: не обязательно удалять все делегирующие методы. Может оказаться удобнее сохранить некоторые из делегирований. Действуйте по ситуации.

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.