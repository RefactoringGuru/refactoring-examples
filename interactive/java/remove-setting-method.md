remove-setting-method:java

###

1.ru. Значение поля должно меняться только в конструкторе. Если конструктор не содержит параметра для установки значения, нужно его добавить.
1.en. The value of a field should be changeable only in the constructor. If the constructor does not contain a parameter for setting the value, add one.
1.uk. Значення поля повинне мінятися тільки в конструкторі. Якщо конструктор не містить параметра для установки значення, треба його додати.

2.ru. Найдите все вызовы сеттера.<ul><li>Если вызов сеттера стоит сразу после вызова конструктора текущего класса, переместите его аргумент в вызов конструктора и удалите сеттер.</li><li>Вызовы сеттера в конструкторе замените на прямой доступ к полю.</li></ul>
2.en. Find all setter calls.<ul><li>If the setter call is immediately before the call to the constructor of the current class, move its argument to the constructor call and delete the setter.</li><li>Replace setter calls in the constructor with direct access to the field.</li></ul>
2.uk. Знайдіть всі виклики сетера. <Ul><li>Якщо виклик сетера знаходиться відразу після виклику конструктора поточного класу, перемістіть його аргумент на виклик конструктора і видаліть сетер.</Li> <li>Виклики сетера в конструкторі замініть на прямий доступ до поля.</li></ul>

3.ru. Удалите сеттер.
3.en. Delete the setter.
3.uk. Видаліть сетер.



###

```
class Account {
  // ...
  private String id;

  public Account(String id) {
    setId(id);
  }
  public void setId(String id) {
    this.id = id;
  }
}
```

###

```
class Account {
  // ...
  private String id;

  public Account(String id) {
    initializeId(id);
  }
  protected void initializeId(String id) {
    this.id = "ID" + id;
  }
}

class InterestAccount extends Account {
  private double interestRate;
  public InterestAccount(String id, double interestRate) {
    initializeId(id);
    this.interestRate = interestRate;
  }
}
```

###

Set step 1

#|ru| Рассмотрим <b>удаление сеттера</b> на таком простом примере. У нас есть класс банковского счета. В нем есть поле идентификатора, который должен создаваться только один раз и больше не меняться.
#|en| Let's look at <b>Remove Setter Method</b> using a simple example. We have a bank account class. The class has an ID field that should be created once and never change again.
#|uk| Розглянемо <b>видалення сетера</ b> на такому простому прикладі. У нас є клас банківського рахунку. У ньому є поле ідентифікатора, який повинен створюватися тільки один раз і більше не змінюватися.

Select name of "setId"

#|ru| Тем не менее, сейчас в классе есть сеттер. Вот его мы и попытаемся убрать.
#|en| However, the class currently has a setter, which we want to eliminate.
#|uk| Тим не менш, зараз в класі є сетер. Ось його ми і спробуємо прибрати.

Select body of "setId"

#|ru| Самым простым решением было бы интегрировать код сеттера в конструктор.
#|en| The simplest solution would be to integrate the setter code into the constructor.
#|uk| Найпростішим рішенням було б інтегрувати код сетера в конструктор.

Select body of "public Account"

Replace:
```
    this.id = id;
```

Select whole "setId"

Remove selected

Select name of "Account"

#|ru| По сути, для такого простого случая мы уже все сделали. Но бывают и другие, более сложные случаи.
#|en| In effect, we have already done everything for a case as simple as this one. But there are other, more difficult cases.
#|uk| Взагалі-то, для такого простого випадку ми вже все зробили. Але бувають і інші, більш складні випадки.

Select whole "public Account"

Replace instant:
```
  public Account(String id) {
    setId(id);
  }
  public void setId(String id) {
    this.id = "ID" + id;
  }

```

Select body of "setId"

#|ru|< Например, если сеттер выполняет какие-то вычисления над аргументом:
#|en|< What if, for example, the setter performs calculations on an argument:
#|uk|< Наприклад, якщо сетер виконує якісь обчислення над аргументом:

#|ru|< Если изменение простое, как в этом случае, его тоже можно вынести в конструктор.
#|en|< If the change is simple, as it is here, it too can be moved to the constructor.
#|uk|< Якщо зміна проста, як в цьому випадку, її теж можна винести в конструктор.

#|ru|< Однако, если изменение сложное, состоит из вызовов нескольких методов, лучше создать новый метод для инициализации значения.
#|en|< If the change is complex, however, and consists of calls to several methods, it is better to create a new method for initializing the value.
#|uk|< Однак, якщо зміна складна, складається з викликів декількох методів, краще створити новий метод для ініціалізації значення.

Select visibility of "setId"

Replace "private"

Wait 500ms

Select "setId"

Replace "initializeId"

Set step 2

Go to the end of file

#|ru| Отлично, давайте рассмотрим ещё один случай.
#|en| Excellent. Now for one more case.
#|uk| Дуже добре, давайте розглянемо ще один випадок.

Print instant:
```


class InterestAccount extends Account {
  private double interestRate;
  public InterestAccount(String id, double interestRate) {
    setId(id);
    this.interestRate = interestRate;
  }
}
```

Select name of "InterestAccount"

#|ru| Ещё один неприятный случай возникает, когда есть подклассы, инициализирующие закрытые переменные родительского класса.
#|en| Another unpleasant situation arises when there are subclasses initializing private variables of a parent class.
#|uk| Ще один неприємний випадок виникає, коли є підкласи, які ініціалізують закриті змінні батьківського класу.

Select "setId(id)"

#|ru| Тогда вместо вызова сеттера стоит вызывать родительский конструктор.
#|en| Then, instead of calling a setter, you should call the parent constructor.
#|uk| Тоді замість виклику сетера варто викликати батьківський конструктор.

Print "super(id)"

Select "super(id)"

#|ru| В случаях, когда это невозможно, остаётся вызвать нужный метод инициализации, хотя сначала следует позаботиться о том, чтобы он был доступен для подклассов.
#|en| When that is impossible, you must call the necessary initialization method although first you should make sure it is available for the subclasses.
#|uk| У випадках, коли це неможливо, залишається викликати потрібний метод ініціалізації, хоча спочатку слід подбати про те, щоб він був доступний для підкласів.

Select visibility of "initializeId"

Replace "protected"

Wait 500ms

Select "super(id)"

Replace "initializeId(id)"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's run the final compile.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.