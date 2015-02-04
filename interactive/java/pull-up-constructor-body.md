pull-up-constructor-body:java

###

1.ru. Создайте конструктор в суперклассе.
1.en. Create a constructor in a superclass.
1.uk. Створіть конструктор в суперкласі.

2.ru. Извлеките общий код из начала конструктора каждого из подклассов в конструктор суперкласса. Перед этим действием стоит попробовать поместить как можно больше общего кода в начало конструктора.
2.en. Extract the common code from the beginning of the constructor of each subclass to the superclass constructor. Before doing so, try to move as much common code as possible to the beginning of the constructor.
2.uk. Витягніть загальний код з початку конструктора кожного з підкласів в конструктор суперкласу. Перед цією дією варто спробувати помістити якомога більше загального коду в початок конструктора.

3.ru. Поместите вызов конструктора суперкласса первой строкой в конструкторах подклассов.
3.en. Place the call for the superclass constructor in the first line in the subclass constructors.
3.uk. Розташуйте виклик конструктора суперкласу першим рядком в конструкторах підкласів.



###

```
class Employee {
  // ...
  protected String name;
  protected String id;
}
   
class Manager extends Employee {
  // ...
  private int grade;
  public Manager(String name, String id, int grade) {
    this.name = name;
    this.id = id;
    this.grade = grade;
  }
}
```

###

```
class Employee {
  // ...
  protected String name;
  protected String id;
  protected Employee(String name, String id) {
    this.name = name;
    this.id = id;
  }
}
   
class Manager extends Employee {
  // ...
  private int grade;
  public Manager(String name, String id, int grade) {
    super(name, id);
    this.grade = grade;
  }
}
```

###

Set step 1

#|ru| Начнём с классов менеджера и служащего. В данном случае <code>Employee</code> вообще не имеет конструктора, а его поля заполняются в классе <code>Manager</code>, который реально используется в программе.
#|en| Let's look at <i>Pull Up Constructor Body</i> using manager and employee classes. In this case, <code>Employee</code> does not have any constructor and its fields are filled in the <code>Manager</code> class, which is actually used in the program.
#|uk| Почнемо з класів менеджера і службовця. В даному випадку <code>Employee</code> взагалі не має конструктора, а його поля заповнюються в класі <code>Manager</code>, який реально використовується в програмі.

#|ru| Таким образом, если мы захотим создать ещё один подкласс <code>Employee</code>, нам придётся дублировать части конструктора, чтобы инициализировать поля <code>Employee</code>.
#|en| So if we want to create another <code>Employee</code> subclass, we must duplicate parts of the constructor in order to initialize the <code>Employee</code> fields.
#|uk| Таким чином, якщо ми захочемо створити ще один підклас <code>Employee</code>, нам доведеться дублювати частини конструктора, щоб ініціалізувати поля <code>Employee</code>.

#|ru| Вместо этого мы можем поднять часть тела конструктора <code>Manager</code> в его суперкласс.
#|en| Instead, we can pull up part of the body of the <code>Manager</code> constructor to its superclass.
#|uk| Замість цього ми можемо підняти частину тіла конструктора <code>Manager</code> в його суперклас.

Go to the end of "Employee"

#|ru| Определим конструктор и сделаем его защищённым. Это даст знать другим программистам, что его нужно вызывать в подклассах.
#|en| Let's define the constructor and make it protected. That will work as default implementation and let subclasses call it inside their own constructors.
#|uk| Визначимо конструктор і зробимо його захищеним. Це дасть знати іншим програмістам, що його потрібно викликати в підкласах.

Print:
```

  protected Employee(String name, String id) {
    this.name = name;
    this.id = id;
  }
```

Set step 3

#|ru| После того как новый конструктор стал доступен, его можно вызвать из конструктора <code>Manager</code>.
#|en| At this point, the new constructor can be called inside <code>Manager</code> constructor as <code>super</code>.
#|uk| Після того як новий конструктор став доступний, його можна викликати з конструктора <code>Manager</code>.

Select in "Manager":
```
    this.name = name;
    this.id = id;

```

Replace:
```
    super(name, id);

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