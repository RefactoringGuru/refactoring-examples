replace-type-code-with-state-strategy:csharp

###

1.ru. Используйте <a href="/self-encapsulate-field">самоинкапсуляцию поля</a> для создания геттера для поля, которое содержит кодирование типа.
1.en. Use <a href="/self-encapsulate-field">Self Encapsulate Field</a> to create a getter for the field that contains type code.
1.uk. Використайте <a href="/self-encapsulate-field">самоінкапсуляцію поля</a> для створення геттера для поля, яке містить кодування типу.

2.ru. Создайте новый класс, который будет играть роль <i>состояния</i> (или <i>стратегии</i>). Создайте в нем абстрактный геттер закодированного поля.
2.en. Create a new class that will play the role of <i>state</i> (or <i>strategy</i>). Create an abstract getter of the coded field in it.
2.uk. Створіть новий клас, який гратиме роль <i>стану</i> (або <i>стратегії</i>). Створіть у ньому абстрактний геттер закодованого поля.

3.ru. Создайте подклассы состояния для каждого значения закодированного типа.
3.en. Create state subclasses for each value of the coded type.
3.uk. Створіть підкласи стану для кожного значення закодованого типу.

4.ru. В абстрактном классе состояния создайте статический фабричный метод, принимающий в параметре значение закодированного типа. В зависимости от этого параметра фабричный метод будет создавать объекты различных состояний. Для этого в его коде придётся создать большой условный оператор, но он будет единственным по завершению рефакторинга.
4.en. In the abstract state class, create a static factory method that accepts the value of the coded type as a parameter. Depending on this parameter, the factory method will create objects of various states. For this, in its code create a large conditional; it will be the only one when refactoring is complete.
4.uk. У абстрактному класі стану створіть статичний фабричний метод, що набуває в параметрі значення закодованого типу. Залежно від цього параметра фабричний метод створюватиме об'єкти різних станів. Для цього в його коді доведеться створити великий умовний оператор, але він буде єдиним після завершення рефакторингу.

5.ru. В исходном классе поменяйте тип закодированного поля на класс-состояние. В сеттере этого поля вызывайте фабричный метод состояния для получения новых объектов состояний.
5.en. In the original class, change the type of the coded field to the state class. In the field's setter, call the factory state method for getting new state objects.
5.uk. У початковому класі поміняйте тип закодованого поля на клас-стан. У сетерові цього поля викликайте фабричний метод стану для отримання нових об'єктів станів.

6.ru. Переместите поля и методы из суперкласса в соответствующие подклассы-состояния.
6.en. Move the fields and methods from the superclass to the corresponding state subclasses.
6.uk. Перемістіть поля та методи з суперкласу у відповідні підкласи-стану.

7.ru. Когда все что можно перемещено, используйте <a href="/replace-conditional-with-polymorphism">замену условных операторов полиморфизмом</a>, чтобы окончательно избавиться от условных операторов, использующий закодированный тип.
7.en. When everything movable has been moved, use <a href="/replace-conditional-with-polymorphism">Replace Conditional with Polymorphism</a> in order to get rid of conditionals that use type code once and for all.
7.uk. Коли усі потрібні дані будуть перенесені, використайте <a href="/replace-conditional-with-polymorphism">заміну умовних операторів поліморфізмом</a>, щоб остаточно позбутися від умовних операторів, які використовують закодований тип.



###

```
public class Employee
{
  // ...
  public const int ENGINEER = 0,
                   SALESMAN = 1,
                   MANAGER = 2;

  public int type;

  public int MonthlySalary
  { get; set; }
  public int Commission
  { get; set; }
  public int Bonus
  { get; set; }

  public Employee(int type)
  {
    this.type = type;
  }

  public int PayAmount()
  {
    switch (type)
    {
      case ENGINEER:
        return MonthlySalary;
      case SALESMAN:
        return MonthlySalary + Commission;
      case MANAGER:
        return MonthlySalary + Bonus;
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
}
```

###

```
public class Employee
{
  // ...
  private EmployeeType type;

  public int EmployeeCode
  {
    get{ return type.EmployeeCode; }
    set{ type = EmployeeType.Create(value); }
  }
  public int MonthlySalary
  { get; set; }
  public int Commission
  { get; set; }
  public int Bonus
  { get; set; }

  public Employee(int employeeCode)
  {
    this.type = EmployeeType.Create(employeeCode);
  }

  public int PayAmount()
  {
    return type.PayAmount(this);
  }
}

public abstract class EmployeeType
{
  public const int ENGINEER = 0,
                   SALESMAN = 1,
                   MANAGER = 2;

  public abstract int EmployeeCode
  { get; }

  public static EmployeeType Create(int code)
  {
    switch (code)
    {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }

  public abstract int PayAmount(Employee employee);
}

public class Engineer: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return EmployeeType.ENGINEER; }
  }

  public override int PayAmount(Employee employee)
  {
    return employee.MonthlySalary;
  }
}

public class Salesman: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return EmployeeType.SALESMAN; }
  }

  public override int PayAmount(Employee employee)
  {
    return employee.MonthlySalary + employee.Commission;
  }
}

public class Manager: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return EmployeeType.MANAGER; }
  }

  public override int PayAmount(Employee employee)
  {
    return employee.MonthlySalary + employee.Bonus;
  }
}
```

###

Set step 1

#|ru| Рассмотрим рефакторинг <i>Замена кодирования типа состоянием/стратегией</i> на примере всё того же класса зарплаты служащего. У нас есть несколько типов служащих, на основе которых вычисляется размер зарплаты каждого конкретного служащего.
#|en| Let's look at <i>Replace Type Code with State/Strategy</i> in the context of the payroll class considered earlier. We have several types of employees; these types are used to calculate the salary amount for each particular employee.
#|uk| Розглянемо рефакторинг <i>Заміна кодування типу станом / стратегією</i> на прикладі все того ж класу зарплати службовця. У нас є кілька типів службовців, на основі яких обчислюється розмір зарплати кожного конкретного службовця.

Select "public int |||type|||"

#|ru| Начнём с <a href="/ru/self-encapsulate-field">самоинкапсуляции поля</a> типа служащего.
#|en| Let's start by applying <a href="/self-encapsulate-field">Self-Encapsulate Field</a> to the employee type.
#|uk| Почнемо з <a href="/uk/self-encapsulate-field">самоінкапсуляціі поля</a> типу службовця.

Select "|||public||| int type"

Replace "private"

Go to:
```
|||
  public int MonthlySalary
```
Print:
```

  public int Type
  {
    get{ return type; }
    set{ type = value; }
  }
```

Wait 500ms

Select "switch (|||type|||)"

Replace "this.Type"

Select "set{ type = value; }"

#|ru|^ Предполагается, что это замечательная прогрессивная компания, позволяющая менеджерам вырастать до инженеров. Поэтому код типа изменяемый, и применять подклассы для избавления от кодирования типа нельзя, что приводит нас к применению паттерна <a href="https://refactoring.guru/ru/design-patterns/state">Состояние</a>.
#|en|^ We assume that the company is progressive and enlightened and so allows its managers to ascend to engineers. So the type code can be changed and using subclasses to eliminate type coding is not possible. This causes us to use the <a href="https://refactoring.guru/design-patterns/state">State</a> pattern.
#|uk|^ Передбачається, що це чудова прогресивна компанія, що дозволяє менеджерам виростати до інженерів. Тому код типу змінюваний, і застосовувати підкласи для позбавлення від кодування типу не можна, що приводить нас до застосування патерну <a href="https://refactoring.guru/design-patterns/state">Стан</a>.

Set step 2

Go to the end of file

#|ru| Итак, объявим класс состояния (как абстрактный класс с абстрактным свойством, возвращающим код типа).
#|en| Declare the state class (as an abstract class with an abstract property for returning type code).
#|uk| Отже, оголосимо клас стану (як абстрактний клас з абстрактним властивістю, що повертає код типу).

Print:
```


public abstract class EmployeeType
{
  public abstract int EmployeeCode
  { get; }
}
```

Set step 3

#|ru| Теперь создадим подклассы для каждого из типов служащих.
#|en| Now create subclasses for each type of employee.
#|uk| Тепер створимо підкласи для кожного з типів службовців.


Print:
```


public class Engineer: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return Employee.ENGINEER; }
  }
}

public class Salesman: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return Employee.SALESMAN; }
  }
}

public class Manager: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return Employee.MANAGER; }
  }
}
```

Set step 4

Go to the end of "EmployeeType"

#|ru| Далее создадим в классе состояния статический метод, который будет по переданному коду типа возвращать экземпляр соответствующего подкласса.
#|en| Create a static method in the state class. It will return an instance of the necessary subclass, depending on the value accepted.
#|uk| Далі створимо в класі стану статичний метод, який буде по переданому кодом типу повертати екземпляр відповідного підкласу.

Print:
```


  public static EmployeeType Create(int code)
  {
    switch (code)
    {
      case Employee.ENGINEER:
        return new Engineer();
      case Employee.SALESMAN:
        return new Salesman();
      case Employee.MANAGER:
        return new Manager();
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
```

Select "switch (code)"

#|ru| ***Как вы могли заметить, мы создали большой оператор <code>switch</code>. Это не очень хорошо, однако по завершению рефакторинга данный оператор окажется единственным в коде и будет выполняться только при изменении типа.
#|en| As you can see, here we are introducing a large <code>switch</code> operator. That's not great news, but once we are done with refactoring, this operator will be the only one in the code and will be run only when a type is changed.
#|uk| Як ви могли помітити, ми створили великий оператор <code>switch</code>. Це не дуже добре, проте по завершенню рефакторінга даний оператор виявиться єдиним у коді і буде виконуватися тільки при зміні типу.

#C|ru| Запустим компиляцию и тестирование, чтобы убедиться в отсутствии ошибок.
#S Всё отлично, можем продолжать!

#C|en| Let's compile and test to check for errors in the code.
#S Everything is OK! We can keep going.

#C|uk| Запустимо компіляцію і тестування, щоб переконатися у відсутності помилок.
#S Все добре, можна продовжувати.

Set step 5

Select "private |||int||| type"

#|ru| Теперь нужно подключить созданные подклассы к <code>Employee</code>, модифицируя свойство, инкапсулирующее поле кода типа и конструктор.
#|en| Now we need to connect the created subclasses to <code>Employee</code> by modifying the access property for the type code and constructor.
#|uk| Тепер потрібно підключити створені підкласи до <code>Employee</code>, модифікуючи властивість, інкапсулюють поле коду типу та конструктор.

Print "EmployeeType"

Wait 500ms

Select "get{ return |||type|||"

Replace "type.EmployeeCode"

Wait 500ms

Select "set{ type = |||value|||"
+Select "this.type = |||type|||;"

#|ru| Тело сеттера и конструктор меняем на вызов фабричного метода.
#|en| The setter body and constructor are replaced with a call to the factory method.
#|uk| Тіло сетера і конструктор міняємо на виклик фабричного методу.

Select "set{ type = |||value|||"

Replace "EmployeeType.Create(value)"

Select "(int |||type|||)"

Replace "employeeCode"

Select "this.type = |||type|||;"

Replace "EmployeeType.Create(employeeCode)"

Wait 500ms

Select "public int |||Type|||"

#|ru| Так как свойство теперь возвращает код, а не сам объект типа, стоит переименовать его, чтобы избавить будущего читателя от непонимания.
#|en| Since property now return a code, not the type object itself, we should rename it to make things clear to future readers.
#|uk| Оскільки властивість тепер повертає код, а не сам об'єкт типу, варто перейменувати його, щоб позбавити майбутнього читача від нерозуміння.

Replace "EmployeeCode"

Select "switch (this.|||Type|||)"

Replace "EmployeeCode"

Wait 500ms

Select:
```

  public const int ENGINEER = 0,
                   SALESMAN = 1,
                   MANAGER = 2;

```

#|ru| В завершение этого шага можно перенести все константы кода типа из <code>Employee</code> в <code>EmployeeType</code>
#|en| We complete this step by moving all type code constants from <code>Employee</code> to <code>EmployeeType</code>.
#|uk| На завершення цього кроку можна перенести всі константи коду типу з <code>Employee</code> в <code>EmployeeType</code>

Remove selected

Go to the beginning of "EmployeeType"

Print:
```

  public const int ENGINEER = 0,
                   SALESMAN = 1,
                   MANAGER = 2;

```

Wait 500ms

Select "Employee." in "Create"

Remove selected

Wait 500ms

Select:
```
      case||| |||ENGINEER:
        return MonthlySalary;
      case||| |||SALESMAN:
        return MonthlySalary + Commission;
      case||| |||MANAGER:
```

Replace " EmployeeType."

Wait 500ms

Select "|||Employee|||.ENGINEER" in "Engineer"
+ Select "|||Employee|||.MANAGER" in "Manager"
+ Select "|||Employee|||.SALESMAN" in "Salesman"

Wait 500ms

Type "EmployeeType"

Set step 6

#|ru| Теперь всё готово для применения <a href="/replace-conditional-with-polymorphism">замены условного оператора полиморфизмом</a>.
#|en| Everything is now ready for applying <a href="/replace-conditional-with-polymorphism">Replace Conditional With Polymorphism</a>.
#|uk| Тепер все готово для застосування <a href="/uk/replace-conditional-with-polymorphism">заміни умовного оператора поліморфізмом</a>.

Select body of "PayAmount"

#|ru| Сначала выделим реализацию <code>PayAmount()</code> в новый метод в классе типа.
#|en| First extract the implementation of <code>PayAmount()</code> to a new method in a type class.
#|uk| Спочатку виділимо реалізацію <code>PayAmount()</code> в новий метод в класі типу.

Go to the end of "EmployeeType"

Print:
```


  public int PayAmount()
  {
    switch (EmployeeCode)
    {
      case ENGINEER:
        return MonthlySalary;
      case SALESMAN:
        return MonthlySalary + Commission;
      case MANAGER:
        return MonthlySalary + Bonus;
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
```

Select "MonthlySalary" in "EmployeeType"
+Select "Commission" in "EmployeeType"
+Select "Bonus" in "EmployeeType"

#|ru| Нам нужны данные из объекта <code>Employee</code>, поэтому создадим в методе параметр, в который будет передаваться основной объект <code>Employee</code>.
#|en| We need datа from the <code>Employee</code> object, so in the method we create the parameter to which the main <code>Employee</code> object will be passed.
#|uk| Нам потрібні дані з об'єкта <code>Employee</code>, тому створимо в методі параметр, в який буде передаватися основний об'єкт <code>Employee</code>.

Go to "PayAmount(|||)" in "EmployeeType"

Print "Employee employee"

Select "MonthlySalary" in "EmployeeType"

Replace "employee.MonthlySalary"

Select "Commission" in "EmployeeType"

Replace "employee.Commission"

Select "Bonus" in "EmployeeType"

Replace "employee.Bonus"

Select body of "PayAmount"

#|ru| После этих действий мы можем настроить делегирование из класса <code>Employee</code>.
#|en| After these actions, we can set up delegation from the <code>Employee</code> class.
#|uk| Після цих дій ми можемо налаштувати делегування з класу <code>Employee</code>.

Print "    return type.PayAmount(this);"

#|ru| После этого займёмся перемещением кода в подклассы. Создадим методы <code>PayAmount()</code> в каждом из подклассов и переместим туда расчёты зарплат для соответствующих типов служащих.
#|en| Then start moving code to subclasses. Create <code>PayAmount()</code> methods in each of the subclasses and move payroll calculations there for the relevant employee types.
#|uk| Після цього займемося переміщенням коду в підкласи. Створимо методи <code>PayAmount()</code> в кожному з підкласів і перемістимо туди розрахунки зарплати для відповідних типів службовців.

Go to the end of "class Engineer"

Print:
```


  public override int PayAmount(Employee employee)
  {
    return employee.MonthlySalary;
  }
```

Wait 1000ms

Go to the end of "class Salesman"

Print:
```


  public override int PayAmount(Employee employee)
  {
    return employee.MonthlySalary + employee.Commission;
  }
```

Wait 1000ms


Go to the end of "class Manager"

Print:
```


  public override int PayAmount(Employee employee)
  {
    return employee.MonthlySalary + employee.Bonus;
  }
```

Set step 7

Select name of "PayAmount" in "EmployeeType"

#|ru| После того как методы созданы, можно сделать метод <code>PayAmount()</code> в <code>EmployeeType</code> абстрактным.
#|en| Now that the methods have been created, you can make the <code>PayAmount()</code> method in <code>EmployeeType</code>  abstract.
#|uk| Після того як методи створені, можна зробити метод <code>PayAmount()</code> в <code>EmployeeType</code> абстрактним.

Select:
```
  public int PayAmount(Employee employee)
  {
    switch (EmployeeCode)
    {
      case ENGINEER:
        return employee.MonthlySalary;
      case SALESMAN:
        return employee.MonthlySalary + employee.Commission;
      case MANAGER:
        return employee.MonthlySalary + employee.Bonus;
      default:
        throw new Exception("Incorrect Employee Code");
    }
  }
```

Replace:
```
  public abstract int PayAmount(Employee employee);
```

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.