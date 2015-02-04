extract-interface:php

###

1.ru. Создайте пустой интерфейс.
1.en. Create an empty interface.
1.uk. Створіть порожній інтерфейс.

2.ru. Объявите общие операции в интерфейсе.
2.en. Declare common operations in the interface.
2.uk. Оголосіть загальні операції в інтерфейсі.

3.ru. Объявите нужные классы как реализующие этот интерфейс.
3.en. Declare the necessary classes as implementing the interface.
3.uk. Оголосіть потрібні класи, які будуть реалізовуюти цей інтерфейс.

4.ru. Измените объявление типов в клиентском коде так, чтобы они использовали новый интерфейс.
4.en. Change type declarations in the client code to use the new interface.
4.uk. Змініть оголошення типів в клієнтському коді так, щоб вони використовували новий інтерфейс.



###

```
class TimeSheet {
  // ...
  public function charge(Employee $employee, $days) {
    $base = $employee->getRate() * $days;
    if ($employee->hasSpecialSkill()) {
      return $base * 1.05;
    }
    else {
      return $base;
    }
  }
}

class Employee {
  // ...
  public function getRate() {
    // ...
  }
  public function hasSpecialSkill() {
    // ...
  }
}
```

###

```
class TimeSheet {
  // ...
  public function charge(Billable $employee, $days) {
    $base = $employee->getRate() * $days;
    if ($employee->hasSpecialSkill()) {
      return $base * 1.05;
    }
    else {
      return $base;
    }
  }
}

interface Billable {
  public function getRate();
  public function hasSpecialSkill();
}

class Employee implements Billable {
  // ...
  public function getRate() {
    // ...
  }
  public function hasSpecialSkill() {
    // ...
  }
}
```

###

Set step 1

#|ru| У нас есть класс учёта отработанного времени <code>TimeSheet</code>, который генерирует начисления оплаты для служащих.
#|en| Say, we have a <code>TimeSheet</code> class that is used for payroll.
#|uk| У нас є клас обліку відпрацьованого часу <code>TimeSheet</code>, який генерує нарахування оплати для службовців. 

Select "$employee->getRate()"
+ Select "$employee->hasSpecialSkill()"

#|ru| Для этого ему необходимо знать ставку оплаты служащего и наличие у того особых навыков:
#|en| For this, the class must know an employee's rate of pay and any special skills:
#|uk| Для цього йому необхідно знати ставку оплати службовця і наявність у того особливих навичок:

#|ru| У служащего есть много других характеристик помимо информации о ставке оплаты и специальных навыках, но в данном приложении требуются только они. Тот факт, что требуется только это подмножество, можно подчеркнуть, определив для него интерфейс:
#|en| The employee has many other characteristics, in addition, to pay rate and special skills, but only the latter two are needed in this application. The fact that only this subset is needed can be emphasized by defining an interface for it:
#|uk| У службовця є багато інших характеристик окрім інформації про ставку оплати і спеціальні навички, але в даному додатку потрібні тільки вони. Той факт, що вимагається тільки це підмножина, можна підкреслити, визначивши для нього інтерфейс:

Go to before "Employee"

Print:
```

interface Billable {
  public function getRate();
  public function hasSpecialSkill();
}

```

Go to "class Employee|||"

#|ru| После этого можно объявить Employee как реализующий этот интерфейс:
#|en| Then we declare Employee as implementing this interface:
#|uk| Після цього можна оголосити Employee як реалізуючий цей інтерфейс:

Print " implements Billable"

Select "|||Employee||| $employee"

#|ru| Когда это сделано, можно изменить объявление метода <code>charge</code>, чтобы показать, что используется только эта часть поведения Employee:
#|en| Then we can change the declaration of the <code>charge</code> method to show that only part of the Employee behavior is used:
#|uk| Коли це зроблено, можна змінити оголошення методу <code>charge</code>, щоб показати, що використовується тільки ця частина поведінки Employee:

Print "Billable"

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

#|ru| В данном случае получена скромная выгода в виде документированности кода. Такая выгода не стоит труда для одного метода, но если бы несколько классов стали применять интерфейс <code>Billable</code>, это было бы полезно.
#|en| In this case, a hidden benefit appears, in the form of in-code documentation. This benefit is not worth the work if talking about just one method, but if several classes start to use the <code>Billable</code> interface, this can be rather valuable.
#|uk| В даному випадку отримана скромна вигода у вигляді документованості коду. Така вигода не варта праці для одного методу, але якби кілька класів стали застосовувати інтерфейс <code>Billable</code>, це було б корисно.

#|ru| Крупная выгода появится, когда мы захотим выставлять счета ещё и для компьютеров. Все, что для этого нужно – реализовать в них интерфейс <code>Billable</code>, и тогда можно включать компьютеры в табель учёта времени.
#|en| A major payoff comes when we want to invoice cost for office equipment as well. All we need to do is implement the <code>Billable</code> interface in those classes. After that, we can include computers cost in the timesheet.
#|uk| Крупна вигода з'явиться, коли ми захочемо виставляти рахунки ще й для комп'ютерів. Все, що для цього потрібно – реалізувати в них інтерфейс <code>Billable</code>, і тоді можна включати комп'ютери в табель обліку часу.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.