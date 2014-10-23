parameterize-method:php

###

1.ru. Создайте новый метод с параметром и поместите в него общий для всех методов код, применяя <a href="/extract-method">извлечение метода</a>.
1.uk. Створіть новий метод з параметром і помістіть в нього загальний для всіх методів код, застосовуючи <a href="/extract-method"> витяг методу</a>.

2.ru. В коде нового метода отличающееся значение замените параметром.
2.uk. У коді нового методу значення, що відрізняється, заміните на параметр.

3.ru. Для каждого старого метода найдите места, где они вызываются, и поменяйте их вызовы на вызовы нового метода с параметром. После чего старый метод можно удалить.
3.uk. Для кожного старого методу знайдіть місця, де він викликається, і поміняйте його виклики на виклики нового методу з параметром. Після чого старий метод можна видалити.



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

#|ru| Начнём рефакторинг с поиска повторяющегося кода.
#|uk| Почнемо рефакторинг з пошуку коду, який повторюється декілька разів.

Select "$this->salary *= 1.5"
+ Select "$this->salary *= 1.1"
+ Select "$this->salary *= 1.05"

#|ru| В нашем случае это будет код повышения зарплаты, причём отличается он только коэффициентом повышения.
#|uk| В нашому випадку це буде код підвищення зарплати, причому відрізняється він тільки коефіцієнтом підвищення.

Set step 2

Go to the end of "Employee"

#|ru| Итак, создадим новый метод с параметром, в который будем подавать коэффициент повышения зарплаты.
#|uk| Отже, створимо новий метод з параметром, в який будемо подавати коефіцієнт підвищення зарплати.

Print:
```

  public function raise($factor) {
    $this->salary *= (1 + $factor);
  }
```

#|ru| Заменим повторяющийся код вызовами нашего метода с корректным параметром.
#|uk| Замінимо  код, який повторюється, викликами нашого методу з коректним параметром.

Select "$this->salary *= 1.5"

Replace "$this->raise(0.5)"

Wait 500ms

Select "$this->salary *= 1.1"

Replace "$this->raise(0.1)"

Wait 500ms

Select "$this->salary *= 1.05"

Replace "$this->raise(0.05)"

Select name of "tenPercentRaise"
+ Select name of "fivePercentRaise"

#|ru| Теперь можно избавиться от «ленивых» методов, которые только делегируют выполнение методу с параметром.
#|uk| Тепер можна позбутися «ледачих» методів, які тільки делегують виконання методу з параметром.

Select "$employee->|||fivePercentRaise()|||"

#|ru| Сначала нужно найти все их вызовы и заменить их вызовами метода с параметром.
#|uk| Спочатку потрібно знайти всі їхні виклики і замінити їх викликами методу з параметром.

Print "raise(0.05)"

Select whole "fivePercentRaise"
+ Select whole "tenPercentRaise"

#|ru| После всех замен можно удалить сами методы.
#|uk| Після всіх замін можна видалити самі методи.

Remove selected

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.