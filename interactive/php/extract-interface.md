extract-interface:php

###

1. Создайте пустой интерфейс.

2. Объявите общие операции в интерфейсе.

3. Объявите нужные классы как реализующие этот интерфейс.

4. Измените объявление типов в клиентском коде так, чтобы они использовали новый интерфейс.



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

# У нас есть класс учёта отработанного времени <code>TimeSheet</code>, который генерирует начисления оплаты для служащих. 

Select "$employee->getRate()"
+ Select "$employee->hasSpecialSkill()"

# Для этого ему необходимо знать ставку оплаты служащего и наличие у того особых навыков:

# У служащего есть много других характеристик помимо информации о ставке оплаты и специальных навыках, но в данном приложении требуются только они. Тот факт, что требуется только это подмножество, можно подчеркнуть, определив для него интерфейс:

Go to before "Employee"

Print:
```

interface Billable {
  public function getRate();
  public function hasSpecialSkill();
}

```

Go to "class Employee|||"

# После этого можно объявить Employee как реализующий этот интерфейс:

Print " implements Billable"

Select "|||Employee||| $employee"

# Когда это сделано, можно изменить объявление метода <code>charge</code>, чтобы показать, что используется только эта часть поведения Employee:

Print "Billable"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

# В данном случае получена скромная выгода в виде документированности кода. Такая выгода не стоит труда для одного метода, но если бы несколько классов стали применять интерфейс <code>Billable</code>, это было бы полезно.

# Крупная выгода появится, когда мы захотим выставлять счета ещё и для компьютеров. Все, что для этого нужно – реализовать в них интерфейс <code>Billable</code>, и тогда можно включать компьютеры в табель учёта времени.

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.