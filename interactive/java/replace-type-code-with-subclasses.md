replace-type-code-with-subclasses:java

###

1.ru. Используйте <a href="/self-encapsulate-field">самоинкапсуляцию поля</a> для создания геттера для поля, которое содержит кодирование типа.
1.en. Use <a href="/self-encapsulate-field">Self Encapsulate Field</a> to create a getter for the field that contains type code.
1.uk. Використайте <a href="/self-encapsulate-field">самоінкапсуляцію поля</a> для створення геттера для поля, яке містить кодування типу.

2.ru. Сделайте конструктор суперкласса приватным. Создайте статический фабричный метод с теми же параметрами, что и конструктор суперкласса.
2.en. Make the superclass constructor private. Create a static factory method with the same parameters as the superclass constructor.
2.uk. Зробіть конструктор суперкласу приватним. Створіть статичний фабричний метод з тими ж параметрами, що і конструктор суперкласу.

3.ru. Для каждого значения кодированного типа, создайте свой подкласс. В нем переопределите геттер закодированного поля так, чтобы он возвращал соответствующее значение закодированного типа.
3.en. Create a unique subclass for each value of the coded type. In it, redefine the getter of the coded type so that it returns the corresponding value of the coded type.
3.uk. Зробіть конструктор суперкласу приватним. Створіть статичний фабричний метод з тими ж параметрами, що і конструктор суперкласу. Він обов'язково повинен містити параметр, який набуватиме стартових значень закодованого типу. Залежно від цього параметру фабричний метод створюватиме об'єкти різних підкласів. Для цього в його коді доведеться створити великий умовний оператор, але, принаймні, він буде єдиним, який дійсно потрібний, про усе інше надалі зможуть потурбуватися підкласи і поліморфізм.

4.ru. Удалите поле с закодированным типом из суперкласса, его геттер сделайте абстрактным.
4.en. Delete the field with type code from the superclass. Make its getter abstract.
4.uk. Видаліть поле із закодованим типом з суперкласу, його геттер зробіть абстрактним.

5.ru. Теперь, когда у вас появились подклассы, можете начинать перемещать поля и методы из суперкласса в соответствующие подклассы.
5.en. Now that you have subclasses, you can start to move the fields and methods from the superclass to corresponding subclasses
5.uk. Тепер, коли у вас з'явилися підкласи, можете починати переміщати поля і методи з суперкласу у відповідні підкласи.

6.ru. Когда все что можно перемещено, используйте <a href="/replace-conditional-with-polymorphism">замену условных операторов полиморфизмом</a>, чтобы окончательно избавиться от условных операторов, использующий закодированный тип.
6.en. When everything moveable has been moved, use <a href="/replace-conditional-with-polymorphism">Replace Conditional with Polymorphism</a> in order to get rid of conditionals that use type code once and for all.
6.uk. Коли усі потрібні дані будуть перенесені, використайте <a href="/replace-conditional-with-polymorphism">заміну умовних операторів поліморфізмом</a>, щоб остаточно позбавитися від умовних операторів, які використовують закодований тип.



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
abstract class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  abstract public int getType();

  public static Employee create(int type) {
    switch (type) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manger();
    }
  }
  private Employee(int arg) {
    type = arg;
  }

  public int monthlySalary;
  public int payAmount() {
    return monthlySalary;
  }
}

class Engineer extends Employee {
  public int getType() {
    return Employee.ENGINEER;
  }
}

class Salesman extends Employee {
  public int commission;
  public int getType() {
    return Employee.SALESMAN;
  }
  public int payAmount() {
    return monthlySalary + commission;
  }
}

class Manager extends Employee {
  public int bonus;
  public int getType() {
    return Employee.MANAGER;
  }
  public int payAmount() {
    return monthlySalary + bonus;
  }
}
```

###

Set step 1

#|ru| Рассмотрим рефакторинг <i>Замена кодирования типа подклассами</i> на примере класса зарплаты служащего. У нас есть несколько типов служащих, в зависимости от которых вычисляется размер зарплаты.
#|en| Let's look at <i>Replace Type Code With Subclasses</i>, using an payroll class as our example. We have several types of employees, which affects their salary values.
#|uk| Розглянемо рефакторинг <i>Заміна кодування типу підклассами<i> на прикладі класу зарплати службовця. У нас є кілька типів службовців, залежно від яких обчислюється розмір зарплати.

Select "public int |||type|||"
+ Select name of "payAmount"

#|ru| Начнём с <a href="/self-encapsulate-field">самоинкапсуляции поля</a> типа служащего.
#|en| We start by applying <a href="/self-encapsulate-field">Self-Encapsulate Field</a> to the employee type.
#|uk| Почнемо з <a href="/self-encapsulate-field">самоінкапсуляціі поля</a> типу службовця.

Select "|||public||| int type"

Replace "private"

Go to before "public Employee"

Print:
```

  public int getType() {
    return type;
  }

```

Select "switch (|||type|||)"

Replace "getType()"

Set step 2

Go to before "public Employee"

#|ru| Поскольку конструктор <code>Employee</code> использует код типа в качестве параметра, надо заменить его фабричным методом.
#|en| Since the <code>Employee</code> constructor uses type code as a parameter, we should replace it with a factory method.
#|uk| Оскільки конструктор <code>Employee</code> використовує код типу як параметр, треба замінити його фабричним методом.

Print:
```

  public static Employee create(int type) {
    return new Employee(type);
  }
```

Wait 500ms

Select "|||public||| Employee"

Replace "private"

Set step 3

Select "ENGINEER"

#|ru| Теперь можно приступить к преобразованию <code>Engineer</code> в подкласс. Сначала создаётся сам подкласс…
#|en| Now we can start converting <code>Engineer</code> to a subclass. First create the subclass itself…
#|uk| Тепер можна приступити до перетворення <code>Engineer</code> в підклас. Спочатку створюється сам підклас…

Go to the end of file

Print:
```


class Engineer extends Employee {
}
```

Go to the end of "Engineer"

#|ru| …а потом замещающий метод для кода типа.
#|en| …then create the method to replace the type code.
#|uk| …А потім заміщуючий метод для коду типу.

Print:
```

  public int getType() {
    return Employee.ENGINEER;
  }
```

Select body of "create"

#|ru| Необходимо также заменить фабричный метод, чтобы он создавал надлежащий объект.
#|en| We need to replace the factory method as well so that it creates the necessary object.
#|uk| Необхідно також замінити фабричний метод, щоб він створював належний об'єкт.

Print:
```
    switch (type) {
      case ENGINEER:
        return new Engineer();
      default:
        return new Employee(type);
    }
```

Go to the end of file

#|ru| Продолжаем эти действия поочерёдно, пока все коды не будут заменены подклассами.
#|en| Continue these actions one by one, until all code has been replaced by subclasses.
#|uk| Продовжуємо виконувати ці дії по черзі, поки всі коди не  будуть замінені підклассами.

Print:
```


class Salesman extends Employee {
  public int getType() {
    return Employee.SALESMAN;
  }
}
```

Go to:
```
      case ENGINEER:
        return new Engineer();|||
```

Print:
```

      case SALESMAN:
        return new Salesman();
```

Wait 1000ms

Go to the end of file

Print:
```


class Manager extends Employee {
  public int getType() {
    return Employee.MANAGER;
  }
}
```

Go to:
```
      case SALESMAN:
        return new Salesman();|||
```

Print:
```

      case MANAGER:
        return new Manger();
```

Select:
```
  private int type;


```

Set step 4

#|ru| После этого можно избавиться от поля с кодом типа в <code>Employee</code>…
#|en| Then we can eliminate the field with type code in <code>Employee</code>…
#|uk| Після цього можна позбутися від поля з кодом типу в <code>Employee</code>…

Remove selected


Go to:
```
  |||public int getType() {
    return type;
  }
```

#|ru| …и сделать <code>getType</code> абстрактным методом.
#|en| …and make <code>getType</code> an abstract method.
#|uk| …і зробити <code>getType</code> абстрактним методом.

Print "abstract "

Select:
```
  abstract public int getType()||| {
    return type;
  }|||
```

Replace ";"

Go to before "Employee"

#|ru| Это сделает и класс <code>Employee</code> абстрактным.
#|en| This will make the <code>Employee</code> class abstract as well.
#|uk| Це зробить і клас <code>Employee</code> абстрактним.

Print "abstract "

#|ru| После этих изменений, мы больше не можем создавать объекты <code>Employee</code> как реализацию по умолчанию, поэтому важно помнить, что избавляться от поля типа стоит только после создания всех подклассов.
#|en| After all these changes, we can no longer create <code>Employee</code> objects as the default implementation. So it is important to remember to get rid of the type field only after creating all subclasses.
#|uk| Після цих змін, ми більше не можемо створювати об'єкти <code>Employee</code> як типову реалізацію, тому важливо пам'ятати, що позбуватися від поля типу варто тільки після створення всіх підкласів.

Select:
```
      default:
        return new Employee(type);

```

Remove selected


Select "switch (type) {" in "create"

#|ru| Обратите внимание, что в итоге мы создали ещё один большой оператор <code>switch</code>. Вообще  –  <a href="/smells/switch-statements">это плохо</a>, но после завершения рефакторинга он будет единственным оставшимся в коде.
#|en| Note that we ended up creating another big <code>switch</code> operator. Generally speaking this <a href="/smells/switch-statements">gives off a bad whiff</a> but once refactoring is done, it will be the only operator remaining in the code.
#|uk| Зверніть увагу, що в підсумку ми створили ще один великий оператор <code>switch</code>. Взагалі – <a href="/smells/switch-statements">це погано</a>, але після завершення рефакторинга він буде єдиним залишившимся в коді.

Set step 5

#|ru| Теперь, после создания подклассов, следует применить <a href="/push-down-method">Спуск метода</a> и <a href="/push-down-field">Спуск поля</a> ко всем методам и полям, которые относятся только к конкретным типам служащих.
#|en| After creating the subclasses, use <a href="/push-down-method">Push Down Method</a> and <a href="/push-down-field">Push Down Field</a> on all methods and fields that relate to only specific types of employees.
#|uk| Тепер, після створення підкласів, слід застосувати <a href="/push-down-method">Спуск методу</a> і <a href="/push-down-field">Спуск поля</a> до всіх методів та полей, що відносяться тільки до конкретних типів службовців.

Select:
```
  public int monthlySalary;
  public int commission;
  public int bonus;
```
+ Select name of "payAmount"

#|ru| В нашем случае создадим методы <code>payAmount</code> в каждом из подклассов и переместим туда расчёты зарплаты для соответствующих типов служащих.
#|en| In our case, we will create <code>payAmount</code> methods in each of the subclasses and move payroll calculations there for the relevant types of employees.
#|uk| В нашому випадку створимо методи <code>payAmount</code> в кожному з підкласів і перемістимо туди розрахунки зарплати для відповідних типів службовців.

Select:
```
  public int commission;

```

Remove selected

Go to the start of "Salesman"

Print:
```

  public int commission;
```

Select:
```
      case SALESMAN:
        return monthlySalary + commission;

```

Remove selected

Go to the end of "Salesman"

Print:
```

  public int payAmount() {
    return monthlySalary + commission;
  }
```

Wait 500ms

Select:
```
  public int bonus;

```

#|ru| Проделаем те же действия с классом менеджеров.
#|en| Do the same thing with the manager class.
#|uk| Проробимо ті ж дії з класом менеджерів.

Remove selected

Go to the start of "Manager"

Print:
```

  public int bonus;
```

Select:
```
      case MANAGER:
        return monthlySalary + bonus;

```

Remove selected


Go to the end of "Manager"

Print:
```

  public int payAmount() {
    return monthlySalary + bonus;
  }
```

Set step 6

Select body of "payAmount"

#|ru| После перемещения всего кода по подклассам вы можете либо объявить метод в суперклассе абстрактным, либо оставить в нём реализацию по умолчанию (так и сделаем).
#|en| After all the code has been moved to the subclasses, you can either declare the method in the superclass abstract or else leave the default implementation there (which is what we will do).
#|uk| Після переміщення всього коду до підкласів ви можете або оголосити метод в суперкласі абстрактним, або залишити в ньому типову реалізацію  (так і зробимо).

Print:
```
    return monthlySalary;
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