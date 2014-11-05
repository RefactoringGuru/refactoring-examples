remove-middle-man:csharp

###

1.ru. Создайте геттер для доступа к объекту <i>класса-делегата</i> из объекта <i>класса-сервера</i>.
1.en. Create a getter for accessing the <i>delegate-class</i> object from the <i>server-class</i> object.
1.uk. Створіть геттер для доступу до об'єкту <i>класу-делегату</i> з об'єкту <i>класу-сервера</i>.

2.ru. Замените вызовы делегирующих методов <i>класса-сервера</i> прямыми вызовами методов <i>класса-делегата</i>.
2.en. Replace calls to delegating methods in the <i>server-class</i> with direct calls for methods in the <i>delegate-class</i>.
2.uk. Замініть виклики делегуючих методів <i>класу-серверу</i> прямими викликами методів <i>класу-делегату</i>.



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

#|ru| Давайте рассмотрим <i>Удаление посредника</i> на примере классов, представляющих работника и его отдел (обратная ситуация по отношению к примеру <i>Сокрытие делегирования</i>).
#|en| Let's look at <i>Remove Middle Man</i>, using the example of classes that represent an employee and the employee's department (the reverse of the situation in the <i>Hide Delegate</i> example).
#|uk| Давайте розглянемо <i>Видалення посередника<i> на прикладі класів, що представляють працівника і його відділ (зворотня ситуація по відношенню до прикладу <i>Приховування делегування<i>).

Select "manager = john.GetManager();"

#|ru| Чтобы узнать, кто является менеджером некоторого лица, клиент делает запрос в самом классе <code>Person</code>.
#|en| To learn who a person's manager is, the client makes a query in the <code>Person</code> class itself.
#|uk| Щоб дізнатися, хто є менеджером деякої особи, клієнт робить запит в самому класі <code>Person</code>.

Select body of "GetManager"

#|ru| Это простая конструкция, инкапсулирующая экземпляр класса <code>Department</code>. Однако при наличии множества методов, устроенных таким образом, в классе <code>Person</code> окажется слишком много простых делегирований. В этом случае такое посредничество лучше удалить.
#|en| This is a simple structure that encapsulates an instance of the <code>Department</code> class. But if there are many such methods, the <code>Person</code> class will have too many simple delegates. In this case, we should get rid of the middle men.
#|uk| Це проста конструкція, інкапсулює екземпляр класу <code>Department</code>. Однак при наявності безлічі методів, влаштованих таким чином, в класі <code>Person</code> виявиться занадто багато простих делегування. У цьому випадку таке посередництво краще видалити.

Select "Department |||department|||;"

#|ru| Для начала создадим публичное свойство для доступа к делегату.
#|en| First create a public property for delegate access.
#|uk| Для початку створимо публічну властивість для доступу до делегату.

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

#|ru| После этого нужно по очереди пересмотреть каждый делегирующий метод <code>Person</code> и найти использующий его код. Этот код следует изменить так, чтобы он сначала получал класс-делегат (<code>Department</code>), после чего через него уже напрямую обращался к интересующим свойствам или методам.
#|en| Then review each delegate method for <code>Person</code> and find the code that uses it. Change the code in each case so that it first gets the delegate class (<code>Department</code>) and then uses it to directly access needed properties or methods.
#|uk| Після цього потрібно по черзі переглянути кожен делегируючий метод <code>Person</code> та знайти код, що використовує його. Цей код слід змінити так, щоб він спочатку отримував клас-делегат (<code>Department</code>), після чого через нього вже прямо звертався до цікавлячих властивостей або методів.

#|ru| Вот как это будет выглядеть для метода получения менеджера.
#|en| Here is how it will look for the method for getting the manager.
#|uk| Ось як це буде виглядати для методу отримання менеджера.

Select "john.|||GetManager()|||"

#|ru| Сначала находим места, где он используется.
#|en| First find the places where it is used.
#|uk| Спочатку знаходимо місця, де він використовується.

#|ru| Затем изменяем этот код таким образом, чтобы он получал свойство делегата напрямую.
#|en| Then change the code so that it gets the delegate property directly.
#|uk| Потім змінюємо цей код таким чином, щоб він отримував властивість делегата безпосередньо.

Print "Department.Manager"

Select whole "GetManager"

#|ru| После всех замен делегирующий метод <code>GetManager()</code> можно удалить из класса <code>Person</code>.
#|en| After all replacements are done, the <code>GetManager()</code> delegate method can be removed from the <code>Person</code> class.
#|uk| Після всіх замін делегуючий метод <code>GetManager()</code> можна видалити з класу <code>Person</code>.

Remove selected

#|ru| И напоследок: не обязательно удалять все делегирующие методы. Может оказаться удобнее сохранить некоторые из делегирований. Действуйте по ситуации.
#|en| And finally: removing all delegate methods is not necessary. It may be more convenient to maintain some delegation, so see what is best for your particular situation.
#|uk| І наостанок: не обов'язково видаляти усі делегуючи методи. Може виявитися зручнішим зберегти деякі з делегувань. Дійте за обставинами.

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's run the final compile.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.