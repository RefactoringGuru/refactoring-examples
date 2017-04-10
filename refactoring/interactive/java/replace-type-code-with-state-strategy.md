replace-type-code-with-state-strategy:java

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
7.uk. Коли усі потрібні дані будуть перенесені, використайте <a href="/replace-conditional-with-polymorphism">заміну умовних операторів поліморфізмом</a>, щоб остаточно позбавитися від умовних операторів, які використовують закодований тип.



###

```
class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  public int type;

  public Employee(int arg) {
    type = arg;
  }

  public int monthlySalary;
  public int commission;
  public int bonus;
  public int payAmount() {
    switch (type) {
      case ENGINEER:
        return monthlySalary;
      case SALESMAN:
        return monthlySalary + commission;
      case MANAGER:
        return monthlySalary + bonus;
      default:
        throw new RuntimeException("Incorrect Employee Code");
    }
  }
}
```

###

```
class Employee {
  // ...
  private EmployeeType type;

  public Employee(int arg) {
    type = EmployeeType.newType(arg);
  }
  public int getTypeCode() {
    return type.getTypeCode();
  }
  public void setTypeCode(int arg) {
    type = EmployeeType.newType(arg);
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
  @Override public int getTypeCode() {
    return EmployeeType.ENGINEER;
  }
  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
}
class Salesman extends EmployeeType {
  @Override public int getTypeCode() {
    return EmployeeType.SALESMAN;
  }
  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
}
class Manager extends EmployeeType {
  @Override public int getTypeCode() {
    return EmployeeType.MANAGER;
  }
  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
}
```

###

Set step 1

#|ru| Рассмотрим рефакторинг <i>Замена кодирования типа состоянием/стратегией</i> на примере всё того же класса зарплаты служащего. У нас есть несколько типов служащих, на основе которых вычисляется размер зарплаты каждого конкретного служащего.
#|en| Let's look at <i>Replace Type Code with State/Strategy</i> in the context of the payroll class considered earlier. We have several types of employees; these types are used to calculate the salary amount for each particular employee.
#|uk| Розглянемо рефакторинг <i>Заміна кодування типу станом / стратегією<i> на прикладі все того ж класу зарплати службовця. У нас є кілька типів службовців, на основі яких обчислюється розмір зарплати кожного конкретного службовця.

Select "public int |||type|||"

#|ru| Начнём с <a href="/ru/self-encapsulate-field">самоинкапсуляции поля</a> типа служащего.
#|en| Let's start by applying <a href="/self-encapsulate-field">Self-Encapsulate Field</a> to the employee type.
#|uk| Почнемо з <a href="/uk/self-encapsulate-field">самоінкапсуляціі поля</a> типу службовця.

Select "|||public||| int type"

Replace "private"

Go to after "public Employee"

Print:
```

  public int getType() {
    return type;
  }
  public void setType(int arg) {
    type = arg;
  }
```

Wait 500ms

Select "switch (|||type|||) {"

Replace "getType()"


Select whole "setType"

#|ru| Предполагается, что это замечательная прогрессивная компания, позволяющая менеджерам вырастать до инженеров. Поэтому код типа изменяемый, и применять подклассы для избавления от кодирования типа нельзя, что приводит нас к применению паттерна <a href="http://sourcemaking.com/design_patterns/state">Состояние</a>.
#|en| We assume that the company is progressive and enlightened and so allows its managers to ascend to engineers. So the type code can be changed and using subclasses to eliminate type coding is not possible. This causes us to use the <a href="http://sourcemaking.com/design_patterns/state">State</a> pattern.
#|uk| Передбачається, що це чудова прогресивна компанія, що дозволяє менеджерам виростати до інженерів. Тому код типу змінюваний, і застосовувати підкласи для позбавлення від кодування типу не можна, що приводить нас до застосування патерну <a href="http://sourcemaking.com/design_patterns/state">Стан</a>.

Set step 2

Go to the end of file

#|ru| Итак, объявим класс состояния (как абстрактный класс с абстрактным методом возврата кода типа).
#|en| Declare the state class (as an abstract class with an abstract method for returning type code).
#|uk| Отже, оголосимо клас стану (як абстрактний клас з абстрактним методом повернення коду типу).

Print:
```


abstract class EmployeeType {
  abstract public int getTypeCode();
}
```

Set step 3

#|ru| Теперь создадим подклассы для каждого из типов служащих.
#|en| Now create subclasses for each type of employee.
#|uk| Тепер створимо підкласи для кожного з типів службовців.


Print:
```

class Engineer extends EmployeeType {
  @Override public int getTypeCode() {
    return Employee.ENGINEER;
  }
}
class Salesman extends EmployeeType {
  @Override public int getTypeCode() {
    return Employee.SALESMAN;
  }
}
class Manager extends EmployeeType {
  @Override public int getTypeCode() {
    return Employee.MANAGER;
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

  public static EmployeeType newType(int code) {
    switch (code) {
      case Employee.ENGINEER:
        return new Engineer();
      case Employee.SALESMAN:
        return new Salesman();
      case Employee.MANAGER:
        return new Manager();
      default:
        throw new IllegalArgumentException("Incorrect Employee Code");
    }
  }
```

Select "switch (code)"

#|ru| Как вы могли заметить, мы создали большой оператор <code>switch</code>. Это не очень хорошо, однако по завершению рефакторинга данный оператор окажется единственным в коде и будет выполняться только при изменении типа.
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

#|ru| Теперь нужно подключить созданные подклассы к <code>Employee</code>, модифицируя методы доступа к коду типа и конструктор.
#|en| Now we need to connect the created subclasses to <code>Employee</code> by modifying the access methods for the type code and constructor.
#|uk| Тепер потрібно підключити створені підкласи до <code>Employee</code>, модифікуючи методи доступу до коду типу та конструктор.

Print "EmployeeType"

Wait 500ms

Select:
```
  public int getType() {
    return |||type|||;
  }
```

Replace "type.getTypeCode()"

Wait 500ms

Select:
```
    type = |||arg|||;
```


#|ru| Тело сеттера и конструктор меняем на вызов фабричного метода.
#|en| The setter body and constructor are replaced with a call to the factory method.
#|uk| Тіло сетера і конструктор міняємо на виклик фабричного методу.

Print "EmployeeType.newType(arg)"

Select name of "setType"
+ Select name of "getType"

#|ru| Так как методы доступа теперь возвращают код, а не сам объект типа, стоит переименовать их, чтобы избавить будущего читателя от непонимания.
#|en| Since access methods now return a code, not the type object itself, we should rename them to make things clear to future readers.
#|uk| Так як методи доступу тепер повертають код, а не сам об'єкт типу, варто перейменувати їх, щоб позбавити майбутнього читача від нерозуміння.

Select "setType("

Replace "setTypeCode("

Select "getType("

Replace "getTypeCode("



Select:
```

  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

```

#|ru| В завершение этого шага можно перенести все константы кода типа из <code>Employee</code> в <code>EmployeeType</code>
#|en| We complete this step by moving all type code constants from <code>Employee</code> to <code>EmployeeType</code>.
#|uk| На завершення цього кроку можна перенести всі константи коду типу з <code>Employee</code> в <code>EmployeeType</code>

Remove selected

Go to the beginning of "EmployeeType"

Print:
```

  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

```

Wait 500ms

Select "Employee." in "newType"

Remove selected

Wait 500ms

Select:
```
      case||| |||ENGINEER:
        return monthlySalary;
      case||| |||SALESMAN:
        return monthlySalary + commission;
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

Select body of "payAmount"

#|ru| Сначала выделим реализацию <code>payAmount</code> в новый метод в классе типа.
#|en| First extract the implementation of <code>payAmount</code> to a new method in a type class.
#|uk| Спочатку виділимо реалізацію <code>payAmount</code> в новий метод в класі типу.

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
#|en| We need datа from the <code>Employee</code> object, so in the method we create the parameter to which the main <code>Employee</code> object will be passed.
#|uk| Нам потрібні дані з об'єкта <code>Employee</code>, тому створимо в методі параметр, в який буде передаватися основний об'єкт <code>Employee</code>.

Go to "payAmount(|||) {" in "EmployeeType"

Print "Employee employee"

Select "monthlySalary" in "EmployeeType"

Replace "employee.monthlySalary"

Select "commission" in "EmployeeType"

Replace "employee.commission"

Select "bonus" in "EmployeeType"

Replace "employee.bonus"

Select body of "payAmount"

#|ru| После этих действий мы можем настроить делегирование из класса <code>Employee</code>.
#|en| After these actions, we can set up delegation from the <code>Employee</code> class.
#|uk| Після цих дій ми можемо налаштувати делегування з класу <code>Employee</code>.

Print "    return type.payAmount(this);"

#|ru| После этого займёмся перемещением кода в подклассы. Создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплат для соответствующих типов служащих.
#|en| Then start moving code to subclasses. Create <code>payAmount</code> methods in each of the subclasses and move payroll calculations there for the relevant employee types.
#|uk| Після цього займемося переміщенням коду в підкласи. Створимо методи <code>payAmount</code> в кожному з підкласів і перемістимо туди розрахунки зарплати для відповідних типів службовців.

Go to the end of "class Engineer"

Print:
```

  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary;
  }
```

Wait 1000ms

Go to the end of "class Salesman"

Print:
```

  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.commission;
  }
```

Wait 1000ms


Go to the end of "class Manager"

Print:
```

  @Override public int payAmount(Employee employee) {
    return employee.monthlySalary + employee.bonus;
  }
```

Set step 7

Select name of "payAmount" in "EmployeeType"

#|ru| После того как методы созданы, можно сделать метод <code>payAmount</code> в <code>EmployeeType</code> абстрактным.
#|en| Now that the methods have been created, you can make the <code>payAmount</code> method in <code>EmployeeType</code>  abstract.
#|uk| Після того як методи створені, можна зробити метод <code>payAmount</code> в <code>EmployeeType</code> абстрактним.

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