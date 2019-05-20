replace-constructor-with-factory-method:java

###

1.ru. Создайте фабричный метод. Поместите в его тело вызова текущего конструктора.
1.en. Create a factory method. Place a call to the current constructor in it.
1.uk. Створіть фабричний метод. Помістіть його в тіло виклику поточного конструктора.

2.ru. Замените все вызовы конструктора вызовами фабричного метода.
2.en. Replace all constructor calls with calls to the factory method.
2.uk. Замініть усі виклики конструктора викликами фабричного методу.

3.ru. Объявите конструктор приватным.
3.en. Declare the constructor private.
3.uk. Оголосіть конструктор приватним.

4.ru. Обследуйте код конструктора и попытайтесь вынести в фабричный метод тот код, который не относится к непосредственному конструированию объекта текущего класса.
4.en. Investigate the constructor code and try to isolate the code not directly related to constructing an object of the current class, moving such code to the factory method.
4.uk. Обстежте код конструктора і спробуйте винести у фабричний метод той код, який не відноситься до безпосереднього конструювання об'єкту поточного класу.



###

```
class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  public Employee(int type) {
    this.type = type;
  }
}

// Some clinet code.
Employee eng = new Employee(Employee.ENGINEER);
```

###

```
class Employee {
  // ...
  static final int ENGINEER = 0;
  static final int SALESMAN = 1;
  static final int MANAGER = 2;

  public static Employee create(int type) {
    switch (type) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        return new Employee(type);
    }
  }
  private Employee(int type) {
    this.type = type;
  }
}
class Engineer extends Employee {
  // ...
}
class Salesman extends Employee {
  // ...
}
class Manager extends Employee {
  // ...
}

// Some clinet code.
Employee eng = Employee.create(Employee.ENGINEER);
```

###

Set step 1

#|ru| Допустим, у нас есть класс для создания сотрудников,…
#|en| Say, we have a class for creating employees…
#|uk| Припустимо, у нас є клас для створення співробітників,…

Select parameters in "public Employee"

#|ru|< …в котором тип сотрудника задаётся параметром конструктора.
#|en|< …in which the employee type is set via constructor's parameter.
#|uk|< …в якому тип співробітника задається параметром конструктора.

Select "new Employee"

#|ru| Клиентский код вызывает этот конструктор напрямую.
#|en| The client code calls the constructor directly.
#|uk| Клієнтський код викликає цей конструктор безпосередньо.

#|ru| Предположим, мы захотели создать подклассы для каждого типа сотрудников.
#|en| So what if we wanted to create subclasses for each type of employee?
#|uk| Припустимо, ми захотіли створити підкласи для кожного типу співробітників.

Go to after "Employee"

Print:
```

class Engineer extends Employee {
  // ...
}
class Salesman extends Employee {
  // ...
}
class Manager extends Employee {
  // ...
}
```

Select "new Employee"

#|ru| Что случилось бы с этим кодом? Его пришлось бы переписать, так как мы не можем возвращать ничего другого из конструктора <code>Employee</code>, кроме объектов <code>Employee</code> (а нам нужен <code>Engineer</code>).
#|en| We would have to rewrite it, since we cannot return anything from the <code>Employee</code> constructor other than <code>Employee</code> objects (and we need <code>Engineer</code>).
#|uk| Що сталося б з цим кодом? Його довелося б переписати, оскільки ми не можемо повертати нічого іншого з конструктора <code>Employee</code>, крім об'єктів <code>Employee</code> (а нам потрібен <code>Engineer</code>).

#|ru| Но если потом что-то снова поменяется, нам придется создавать ещё больше подклассов, и, вполне возможно, что вызовы конструкторов снова придётся править.
#|en| And if something changes again later, we will have to create even more subclasses and may well have to adjust the constructor calls… again.
#|uk| Але якщо потім щось знову поміняється, нам доведеться створювати ще більше підкласів, і, цілком можливо, що виклики конструкторів знову доведеться правити.

#|ru| Альтернативой этому является создание <b>фабричного метода</b> – специального статического метода, который бы возвращал объекты разных классов в зависимости от параметров.
#|en| The alternative is to create a <b>factory method</b> – a special static method that returns objects of different classes depending on particular parameters.
#|uk| Альтернативою цьому є створення <b>фабричного методу</b> – спеціального статичного методу, який би повертав об'єкти різних класів в залежності від параметрів.

Go to before "public Employee"

#|ru| Класс <code>Employee</code> является лучшим местом для хранения фабричного метода, так как он, скорее всего, переживёт любые изменения подклассов.
#|en| The <code>Employee</code> class is the best place to store the factory method because it will probably survive any changes in the subclasses.
#|uk| Клас <code>Employee</code> є кращим місцем для зберігання фабричного методу, оскільки він, швидше за все, переживе будь-які зміни підкласів.

Print:
```

  public static Employee create(int type) {
    return new Employee(type);
  }
```

#|ru| На данном этапе фабричный метод будет вызывать текущий конструктор, но мы изменим это в ближайшем будущем.
#|en| At this stage, the factory method will call the current constructor but we will soon change this.
#|uk| На даному етапі фабричний метод викликатиме поточний конструктор, але ми змінимо це в найближчому майбутньому.

Set step 2

Select "eng = |||new Employee|||"

#|ru| Теперь нужно найти все прямые вызовы конструкторов и заменить их вызовами фабричного метода.
#|en| Now find all direct calls to the constructors and replace them with calls to the factory method.
#|uk| Тепер потрібно знайти всі прямі виклики конструкторів і замінити їх викликами фабричного методу.

Print "Employee.create"

Set step 3

Select visibility of "public Employee"

#|ru| После всех замен конструктор можно скрыть от посторонних глаз, сделав его приватным.
#|en| Once the changes are complete, you can hide the constructor from outside eyes by making it private.
#|uk| Після всіх замін конструктор можна приховати від сторонніх очей, зробивши його приватним.

Print "private"

Set step 4

Select body of "create"

#|ru| После этого можно создать в фабричном методе условный оператор, который будет возвращать объект нужного класса в зависимости от параметра.
#|en| In addition, you can create a conditional in the factory method to return an object of the necessary class depending on the parameter passed.
#|uk| Після цього можна створити у фабричному методі умовний оператор, який буде повертати об'єкт потрібного класу залежно від параметра.

Print:
```
    switch (type) {
      case ENGINEER:
        return new Engineer();
      case SALESMAN:
        return new Salesman();
      case MANAGER:
        return new Manager();
      default:
        return new Employee(type);
    }
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