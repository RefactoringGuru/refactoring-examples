replace-conditional-with-polymorphism:csharp

###

1.ru. Если условный оператор находится в методе, который выполняет ещё какие-то действия, <a href="/extract-method">извлеките его в новый метод</a>.
1.en. If the conditional is in a method that performs other actions as well, perform <a href="/extract-method">Extract Method</a>.
1.uk. Якщо умовний оператор знаходиться в методі, який виконує ще якісь дії, <a href="/extract-method">витягніть його в новий метод</a>.

2.ru. Для каждого подкласса иерархии, переопределите метод, содержащий условный оператор, и скопируйте туда код соответствующей ветки оператора.
2.en. For each hierarchy subclass, redefine the method that contains the conditional and copy the code of the corresponding conditional branch to that location.
2.uk. Для кожного підкласу ієрархії потібно перевизначити метод, ща містить умовний оператор, і скопіювати туди код відповідної гілки оператора.

3.ru. Удалите эту ветку из условного оператора.
3.en. Delete this branch from the conditional.
3.uk. Видаліть цю гілку з умовного оператора.

4.ru. Повторяйте замену, пока условный оператор не опустеет. Затем удалите условный оператор и объявите метод абстрактным.
4.en. Repeat replacement until the conditional is empty. Then delete the conditional and declare the method abstract.
4.uk. Повторюйте заміну, поки умовний оператор не спорожніє. Потім видалите умовний оператор і оголосите метод абстрактним.



###

```
public class Employee
{
  // ...
  private EmployeeType type;

  public int EmployeeCode
  {
    get{ return type.EmployeeCode; }
  }
  public int MonthlySalary
  { get; set; }
  public int Commission
  { get; set; }
  public int Bonus
  { get; set; }

  public int PayAmount()
  {
    switch (this.EmployeeCode)
    {
      case EmployeeType.ENGINEER:
        return MonthlySalary;
      case EmployeeType.SALESMAN:
        return MonthlySalary + Commission;
      case EmployeeType.MANAGER:
        return MonthlySalary + Bonus;
      default:
        throw new Exception("Incorrect Employee Code");
    }
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
}

public class Engineer: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return EmployeeType.ENGINEER; }
  }
}

public class Salesman: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return EmployeeType.SALESMAN; }
  }
}

public class Manager: EmployeeType
{
  public override int EmployeeCode
  {
    get{ return EmployeeType.MANAGER; }
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
  }
  public int MonthlySalary
  { get; set; }
  public int Commission
  { get; set; }
  public int Bonus
  { get; set; }

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

#|ru| Этот рефакторинг мы рассмотрим на примере кода классов расчёта зарплаты для разных типов служащих (см. <a href="/replace-type-code-with-state-strategy">замены кодирования типом состоянием/стратегией</a>).
#|en| Let's take a look at this refactoring in the context of code for calculating payroll for different types of employees (see <a href="/replace-type-code-with-state-strategy">Replace Type Code with State/Strategy</a>).
#|uk| Цей рефакторинг ми розглянемо на прикладі коду класів розрахунку зарплати для різних типів службовців (см. <a Href="/replace-type-code-with-state-strategy">заміни кодування типом станом / стратегією</a>).


Select body of "PayAmount"

#|ru| Давайте попытаемся избавиться от условного оператора внутри метода <code>PayAmount()</code>.
#|en| See that big conditional inside the <code>PayAmount()</code> method? Let's try to get rid of it.
#|uk| Давайте спробуємо позбутися від умовного оператора всередині методу <code>PayAmount()</code>.


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