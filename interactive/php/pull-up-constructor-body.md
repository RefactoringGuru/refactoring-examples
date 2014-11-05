pull-up-constructor-body:php

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
  protected $name;
  protected $id;
}
   
class Manager extends Employee {
  // ...
  private $grade;
  public function __construct($name, $id, $grade) {
    $this->name = $name;
    $this->id = $id;
    $this->grade = $grade;
  }
}
```

###

```
class Employee {
  // ...
  protected $name;
  protected $id;
  protected function __construct($name, $id) {
    $this->name = $name;
    $this->id = $id;
  }
}
   
class Manager extends Employee {
  // ...
  private $grade;
  public function __construct($name, $id, $grade) {
    parent::__construct($name, $id);
    $this->grade = $grade;
  }
}
```

###

Set step 1

#|ru| Начнём с классов менеджера и служащего. В данном случае <code>Employee</code> вообще не имеет конструктора, а его поля заполняются в классе <code>Manager</code>, который реально используется в программе.
#|en| Start with the manager and employee classes. In this case, <code>Employee</code> does not have any constructor and its fields are filled in the <code>Manager</code> class, which actually is used in the program.
#|uk| Почнемо з класів менеджера і службовця. В даному випадку <code>Employee</code> взагалі не має конструктора, а його поля заповнюються в класі <code>Manager</code>, який реально використовується в програмі.

#|ru| Таким образом, если мы захотим создать ещё один подкласс <code>Employee</code>, нам придётся дублировать части конструктора, чтобы инициализировать поля <code>Employee</code>.
#|en| So if we want to create another <code>Employee</code> subclass, we must duplicate parts of the constructor in order to initialize the <code>Employee</code> fields.
#|uk| Таким чином, якщо ми захочемо створити ще один підклас <code>Employee</code>, нам доведеться дублювати частини конструктора, щоб ініціалізувати поля <code>Employee</code>.

#|ru| Вместо этого мы можем поднять часть тела конструктора <code>Manager</code> в его суперкласс.
#|en| Instead, we can pull up part of the body of the <code>Manager</code> constructor to its superclass.
#|uk| Замість цього ми можемо підняти частину тіла конструктора <code>Manager</code> в його суперклас.

Go to the end of "Employee"

#|ru| Определим конструктор и сделаем его защищённым. Это даст знать другим программистам, что его нужно вызывать в подклассах.
#|en| Define the constructor and make it protected. This lets other programmers know that it needs to be called in subclasses.
#|uk| Визначимо конструктор і зробимо його захищеним. Це дасть знати іншим програмістам, що його потрібно викликати в підкласах.

Print:
```

  protected function __construct($name, $id) {
    $this->name = $name;
    $this->id = $id;
  }
```

Set step 3

#|ru| После того как новый конструктор стал доступен, его можно вызвать из конструктора <code>Manager</code>.
#|en| After the new constructor becomes available, it can be called from the <code>Manager</code> constructor.
#|uk| Після того як новий конструктор став доступний, його можна викликати з конструктора <code>Manager</code>.

Select in "Manager":
```
    $this->name = $name;
    $this->id = $id;

```

Replace:
```
    parent::__construct($name, $id);

```

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.