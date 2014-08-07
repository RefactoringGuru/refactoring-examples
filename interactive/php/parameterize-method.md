parameterize-method:php

###

1. Создайте новый метод с параметром и поместите в него общий для всех методов код, применяя <a href="extract-method">извлечение метода</a>.

2. В коде нового метода отличающееся значение замените параметром.

3. Для каждого старого метода найдите места, где они вызываются, и поменяйте их вызовы на вызовы нового метода с параметром. После чего старый метод можно удалить.



###

```
class Employee {
  // ...
  public function promoteToManager() {
    $this->type = Employee::MANAGER;
    $this->salary *= 1.5;
  }
  public function tenPercentRaise() {
    $this->salary *= 1.1;
  }
  public function fivePercentRaise() {
    $this->salary *= 1.05;
  }
}

// Somewhere in client code
if ($employee->yearsOfExperience > 5) {
  if (count($employee->clients) > 10) {
    $employee->promoteToManager();
  }
  else {
    $employee->fivePercentRaise();
  }
}
```

###

```
class Employee {
  // ...
  public function promoteToManager() {
    $this->type = Employee::MANAGER;
    $this->raise(0.5);
  }
  public function raise($factor) {
    $this->salary *= (1 + $factor);
  }
}

// Somewhere in client code
if ($employee->yearsOfExperience > 5) {
  if (count($employee->clients) > 10) {
    $employee->promoteToManager();
  }
  else {
    $employee->raise(0.05);
  }
}
```

###

Set step 1

# Начём рефакторинг с поиска повторяющегося кода.

Select "$this->salary *= 1.5"
+ Select "$this->salary *= 1.1"
+ Select "$this->salary *= 1.05"

# В нашем случае это будет код повышения зарплаты, причем отличается он только коэффициентом повышения.

Set step 2

Go to the end of "Employee"

# Итак, создадим новый метод с параметром, в который будем подавать коэффициент повышения зарплаты.

Print:
```

  public function raise($factor) {
    $this->salary *= (1 + $factor);
  }
```

# Теперь заменим повторяющийся код вызовами нашего метода с корректным параметром.

Select "$this->salary *= 1.5"

Print "$this->raise(0.5)"

Wait 500ms

Select "$this->salary *= 1.1"

Print "$this->raise(0.1)"

Wait 500ms

Select "$this->salary *= 1.05"

Print "$this->raise(0.05)"

Select name of "tenPercentRaise"
+ Select name of "fivePercentRaise"

# Теперь можно избавиться от «ленивых» методов, которые только делегируют выполнение методу с параметром.

Select "$employee->|||fivePercentRaise()|||"

# Сначала нужно найти все их вызовы и заменить их вызовами метода с параметром.

Print "raise(0.05)"

Select whole "fivePercentRaise"
+ Select whole "tenPercentRaise"

# После всех замен можно удалить сами методы.

Remove selected

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.