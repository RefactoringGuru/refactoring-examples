replace-conditional-with-polymorphism:java

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
class Employee {
  // ...
  private EmployeeType type;
  public int getTypeCode() {
    return type.getTypeCode();
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
  public int payAmount() {
    switch (getTypeCode()) {
      case EmployeeType.ENGINEER:
        return monthlySalary;
      case EmployeeType.SALESMAN:
        return monthlySalary + commission;
      case EmployeeType.MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
}

abstract class EmployeeType {
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract public int getTypeCode();
  public static EmployeeType newType(int code) {
    switch (code) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Incorrect Employee Code");
    }
  }
}
class Engineer extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.ENGINEER;
  }
}
class Salesman extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.SALESMAN;
  }
}
class Manager extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.MANAGER;
  }
}
```

###

```
class Employee {
  // ...
  private EmployeeType type;
  public int getTypeCode() {
    return type.getTypeCode();
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
  public int payAmount() {
    return type.payAmount(this);
  }
}

abstract class EmployeeType {
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract public int getTypeCode();
  public static EmployeeType newType(int code) {
    switch (code) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Incorrect Employee Code");
    }
  }

  abstract public int payAmount(Employee employee);
}
class Engineer extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.ENGINEER;
  }
  public int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
}
class Salesman extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.SALESMAN;
  }
  public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
}
class Manager extends EmployeeType {
  public int getTypeCode() {
    return EmployeeType.MANAGER;
  }
  public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
}
```

###

Set step 1

#|ru| Этот рефакторинг мы рассмотрим на примере кода классов расчёта зарплаты для разных типов служащих (см. <a href="/replace-type-code-with-state-strategy">замены кодирования типом состоянием/стратегией</a>).
#|en| Let's take a look at this refactoring in the context of code for calculating payroll for different types of employees (see <a href="/replace-type-code-with-state-strategy">Replace Type Code with State/Strategy</a>).
#|uk| Цей рефакторинг ми розглянемо на прикладі коду класів розрахунку зарплати для різних типів службовців (см. <a Href="/replace-type-code-with-state-strategy">заміни кодування типом станом / стратегією</a>).


Select body of "payAmount"

#|ru| Давайте попытаемся избавиться от условного оператора внутри метода <code>payAmount()</code>.
#|en| See that big conditional inside the <code>payAmount()</code> method? Let's try to get rid of it.
#|uk| Давайте спробуємо позбутися від умовного оператора всередині методу <code>payAmount()</code>.

#|ru| Сначала выделим реализацию <code>payAmount</code> в новый метод в классе типа <code>EmployeeType</code>. Сделаем это, чтобы иметь общую точку доступа к данному методу в подклассах.
#|en| First, extract the implementation of <code>payAmount</code> to a new method in a class like <code>EmployeeType</code>. This gives us a common access point for this method in the subclasses.
#|uk| Спочатку виділимо реалізацію <code>payAmount</code> в новий метод в класі типу <code>EmployeeType</code>. Зробимо це, щоб мати спільну точку доступу до даного методу в підкласах.

Go to the end of "EmployeeType"

Print:
```


  public int payAmount() {
    switch (getTypeCode()) {
      case EmployeeType.ENGINEER:
        return monthlySalary;
      case EmployeeType.SALESMAN:
        return monthlySalary + commission;
      case EmployeeType.MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
```

Select "monthlySalary" in "EmployeeType"
+Select "commission" in "EmployeeType"
+Select "bonus" in "EmployeeType"

#|ru| Нам нужны данные из объекта <code>Employee</code>, поэтому создадим в методе параметр, в который будет передаваться основной объект <code>Employee</code>.
#|en| We need data from the <code>Employee</code> object. For this reason we create a parameter in method and pass the <code>Employee</code> object in it.
#|uk| Нам потрібні дані з об'єкта <code>Employee</code>, тому створимо в методі параметр, в який буде передаватися основний об'єкт <code>Employee</code>.

Go to "public int payAmount(|||) {" in "EmployeeType"

Print "Employee employee"

Select "monthlySalary" in "EmployeeType"

Replace "employee.monthlySalary"

Select "commission" in "EmployeeType"

Replace "employee.commission"

Select "bonus" in "EmployeeType"

Replace "employee.bonus"

Select body of "payAmount"

#|ru| После этих действий мы можем настроить делегирование из класса <code>Employee</code>.
#|en| Now, we can set up delegation from the <code>Employee</code> class.
#|uk| Після цих дій ми можемо налаштувати делегування з класу <code>Employee</code>.

Print "    return type.payAmount(this);"

Set step 2

Select name of "payAmount"

#|ru| После этого займёмся перемещением кода в подклассы. Создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплаты для соответствующих типов служащих.
#|en| Then start moving code to subclasses. Create <code>payAmount</code> methods in each of the subclasses and move payroll calculations there for the relevant employee types.
#|uk| Після цього займемося переміщенням коду в підкласи. Створимо методи <code>payAmount</code> в кожному з підкласів і перемістимо туди розрахунки зарплати для відповідних типів службовців.

Go to the end of "class Engineer"

Print:
```

  public int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
```

Wait 1000ms

Set step 3

Go to the end of "class Salesman"

Print:
```

  public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
```

Wait 1000ms


Go to the end of "class Manager"

Print:
```

  public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
```

Set step 4

Select body of "payAmount"

#|ru| После того, как методы созданы, можно сделать метод <code>payAmount</code> в <code>EmployeeType</code> абстрактным.
#|en| Now that the methods have been created, we can make the <code>payAmount</code> method in <code>EmployeeType</code>  abstract.
#|uk| Після того, як методи створені, можна зробити метод <code>payAmount</code> в <code>EmployeeType</code> абстрактним.

Select:
```
  public int payAmount(Employee employee) {
    switch (getTypeCode()) {
      case EmployeeType.ENGINEER:
        return employee.monthlySalary;
      case EmployeeType.SALESMAN:
        return employee.monthlySalary + employee.commission;
      case EmployeeType.MANAGER:
        return employee.monthlySalary + employee.bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
```

Replace:
```
  abstract public int payAmount(Employee employee);
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