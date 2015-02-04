parameterize-method:java

###

1.ru. Создайте новый метод с параметром и поместите в него общий для всех методов код, применяя <a href="/extract-method">извлечение метода</a>.
1.en. Create a new method with a parameter and move code that is shared by all methods to it, by using <a href="/extract-method">extract method</a>.
1.uk. Створіть новий метод з параметром і помістіть в нього загальний для всіх методів код, застосовуючи <a href="/extract-method"> витяг методу</a>.

2.ru. В коде нового метода отличающееся значение замените параметром.
2.en. In the code of the new method, replace the special/differing value with a parameter.
2.uk. У коді нового методу значення, що відрізняється, заміните на параметр.

3.ru. Для каждого старого метода найдите места, где они вызываются, и поменяйте их вызовы на вызовы нового метода с параметром. После чего старый метод можно удалить.
3.en. For each old method, find the places where it is called, replacing these calls with calls to the new method that include a parameter. Then delete the old method.
3.uk. Для кожного старого методу знайдіть місця, де він викликається, і поміняйте його виклики на виклики нового методу з параметром. Після чого старий метод можна видалити.



###

```
class Employee {
  // ...
  public void promoteToManager() {
    type = Employee.MANAGER;
    salary *= 1.5;
  }
  public void tenPercentRaise() {
    salary *= 1.1;
  }
  public void fivePercentRaise() {
    salary *= 1.05;
  }
}

// Somewhere in client code
if (employee.yearsOfExperience > 5) {
  if (employee.clients.size() > 10) {
    employee.promoteToManager();
  }
  else {
    employee.fivePercentRaise();
  }
}
```

###

```
class Employee {
  // ...
  public void promoteToManager() {
    type = Employee.MANAGER;
    raise(0.5);
  }
  public void raise(double factor) {
    salary *= (1 + factor);
  }
}

// Somewhere in client code
if (employee.yearsOfExperience > 5) {
  if (employee.clients.size() > 10) {
    employee.promoteToManager();
  }
  else {
    employee.raise(0.05);
  }
}
```

###

Set step 1

#|ru| Начнём рефакторинг с поиска повторяющегося кода.
#|en| Start refactoring by searching for repeating code.
#|uk| Почнемо рефакторинг з пошуку коду, який повторюється декілька разів.

Select "salary *= 1.5"
+ Select "salary *= 1.1"
+ Select "salary *= 1.05"

#|ru| В нашем случае это будет код повышения зарплаты, причём отличается он только коэффициентом повышения.
#|en| In our case, this is the code for increasing salaries, which differs only by the increase coefficient.
#|uk| В нашому випадку це буде код підвищення зарплати, причому відрізняється він тільки коефіцієнтом підвищення.

Set step 2

Go to the end of "Employee"

#|ru| Итак, создадим новый метод с параметром, в который будем подавать коэффициент повышения зарплаты.
#|en| Let's start by creating a new method with the parameter. Later on, we will send the salary increase coefficient there.
#|uk| Отже, створимо новий метод з параметром, в який будемо подавати коефіцієнт підвищення зарплати.

Print:
```

  public void raise(double factor) {
    salary *= (1 + factor);
  }
```

#|ru| Заменим повторяющийся код вызовами нашего метода с корректным параметром.
#|en| Replace the repeating code with calls to our method with the correct parameter.
#|uk| Замінимо  код, який повторюється, викликами нашого методу з коректним параметром.

Select "salary *= 1.5"

Replace "raise(0.5)"

Wait 500ms

Select "salary *= 1.1"

Replace "raise(0.1)"

Wait 500ms

Select "salary *= 1.05"

Replace "raise(0.05)"

Select name of "tenPercentRaise"
+ Select name of "fivePercentRaise"

#|ru| Теперь можно избавиться от «ленивых» методов, которые только делегируют выполнение методу с параметром.
#|en| Now, let's get rid of "lazy" methods that only delegate to the method with parameter.
#|uk| Тепер можна позбутися «ледачих» методів, які тільки делегують виконання методу з параметром.

Select "employee.|||fivePercentRaise()|||"

#|ru| Сначала нужно найти все их вызовы и заменить их вызовами метода с параметром.
#|en| First, find all their calls and replace them with calls to our new method with parameter.
#|uk| Спочатку потрібно знайти всі їхні виклики і замінити їх викликами методу з параметром.

Print "raise(0.05)"

Select whole "fivePercentRaise"
+ Select whole "tenPercentRaise"

#|ru| После всех замен можно удалить сами методы.
#|en| After the changes are complete, you can remove the methods themselves.
#|uk| Після всіх замін можна видалити самі методи.

Remove selected

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