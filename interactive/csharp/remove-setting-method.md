remove-setting-method:csharp

###

1.ru. Значение поля должно меняться только в конструкторе. Если конструктор не содержит параметра для установки значения, нужно его добавить.
1.en. The value of a field should be changeable only in the constructor. If the constructor does not contain a parameter for setting the value, add one.
1.uk. Значення поля повинне мінятися тільки в конструкторі. Якщо конструктор не містить параметра для установки значення, треба його додати.

2.ru. Если код сеттера чересчур сложен, то имеет смысл вынести его в отдельный метод инициализации.
2.en. If the code in the setter is too complicated, it makes sense to make it into a separate initialization method.
2.uk. Якщо код сетера надто складний, то має сенс винести його в окремий метод ініціалізації.

3.ru. Если в подклассах производилась инициализация закрытых полей родительского класса, то замените её на вызов конструктора родительского класса.
3.en. If subclasses initialize private fields of the parent class, then replace it with a call to the constructor of the parent class.
3.uk. Якщо в підкласах проводилася ініціалізація закритих полів батьківського класу, то замініть її на виклик конструктора батьківського класу.



###

```
public class Account
{
  // ...
  private string id;

  public string Id
  {
    get{ return id; }
    set{ id = value; }
  }
  
  public Account(string id)
  {
    Id = id;
  }
}
```

###

```
public class Account
{
  // ...
  private string id;

  public string Id
  {
    get{ return id; }
    private set{ id = value; }
  }

  public Account(string id)
  {
    InitializeId(id);
  }
  protected void InitializeId(string id)
  {
    Id = "ID" + id;
  }
}

public class InterestAccount: Account
{
  private double interestRate;

  public InterestAccount(string id, double interestRate)
  {
    InitializeId(id);
    this.interestRate = interestRate;
  }
}
```

###

Set step 1

#|ru| Рассмотрим <b>удаление сеттера</b> на простом примере. У нас есть класс банковского счета. В нем есть поле идентификатора, который должен создаваться только один раз и больше не меняться.
#|en| Let's look at <b>Remove Setter Method</b> using a simple example of a bank account class. The class has an ID field that should be created once and never change again.
#|uk| Розглянемо <b>видалення сетера</b> на простому прикладі. У нас є клас банківського рахунку. У ньому є поле ідентифікатора, який повинен створюватися тільки один раз і більше не змінюватися.

Select "|||public||| string"
+Select "set"

#|ru| Сейчас в классе есть публичный сеттер. Вот его мы и попытаемся убрать.
#|en| Now the class currently has a public setter for that field, which we want to eliminate.
#|uk| Зараз в класі є публічний сетер. Ось його ми і спробуємо прибрати.

#|ru| Самым простым (и зачастую правильным) решением является изменение области видимости сеттера на приватную.
#|en| The simplest solution would be to integrate the setter's code into the constructor.
#|uk| Найпростішим рішенням є зміна області видимості сетера на приватну.

Go to "|||set{"

Print "private "

#|ru| В большинстве случаев этого достаточно. Однако если есть желание, можно и вовсе удалить сеттер, переместив код инициализации поля в конструктор.
#|en| In most cases this is enough. However, if you wish, you can remove the setter by moving the initialization code field in the constructor.
#|uk| У більшості випадків цього достатньо. Однак якщо є бажання, можна і зовсім видалити сетер, перемістивши код ініціалізації поля в конструктор.

Select body of "public Account"

Replace:
```
    this.id = id;
```

Select:
```
    private set{ id = value; }

```

Remove selected

Set step 2

Select name of "Account"

#|ru| Для такого простого случая мы уже все сделали, но бывают и другие, более сложные случаи.
#|en| In effect, we have already done everything for a case as simple as this one. But there are other, more difficult cases.
#|uk| Для такого простого випадку ми вже все зробили, але бувають і інші, більш складні випадки.

Select:
```
}
  
  
```
+Select whole "public Account" 

Replace instant:
```
  private set{ id = "ID" + value; }
  }

  public Account(string id)
  {
    Id = id;
  }

```

Select "{ id = "ID" + value; }"

#|ru|< Например, когда сеттер выполняет какие-то вычисления над аргументом.
#|en|< For example, what if the setter performs calculations on an argument.
#|uk|< Наприклад, коли сетер виконує якісь обчислення над аргументом.

#|ru|< Если изменение простое, как в данном случае, его можно оставить в сеттере.
#|en|< If the change is simple, as it is here, it can be left in the setter.
#|uk|< Якщо зміна проста, як в даному випадку, його можна залишити в сетера.

#|ru|< Однако, если изменение сложное, состоит из вызовов нескольких методов, лучше создать новый метод для инициализации значения.
#|en|< However, if the change is complex and consists of calls to several methods, it is better to create a new method for initializing the value.
#|uk|< Однак, якщо зміна складна, складається з викликів декількох методів, краще створити новий метод для ініціалізації значення.

Go to end of "Account"

Print:
```

  private void InitializeId(string id)
  {
    Id = "ID" + id;
  }
```

Select "|||"ID" + |||value;"

Wait 500ms

Remove selected

Wait 500ms

Select body of "public Account"

Replace "    InitializeId(id);"

Wait 500ms

Set step 3

Go to the end of file

#|ru| Отлично, давайте разберём ещё одну ситуацию.
#|en| Excellent. Now let's review one more case.
#|uk| Дуже добре, давайте розберемо ще одну ситуацію.

Print instant:
```


public class InterestAccount: Account
{
  private double interestRate;

  public InterestAccount(string id, double interestRate)
  {
    Id = id;
    this.interestRate = interestRate;
  }
}
```

Select name of "InterestAccount"
+Select "Id = id;" in "InterestAccount"

#|ru| Она возникает, когда есть подклассы, инициализирующие закрытые переменные родительского класса.
#|en| Another unpleasant situation arises when there are subclasses initializing private variables of a parent class.
#|uk| Вона виникає, коли є підкласи, які ініціалізують закриті змінні батьківського класу.

Select "Id = id;" in "InterestAccount"

#|ru| Тогда вместо вызова сеттера стоит вызывать родительский конструктор.
#|en| Then, instead of calling a setter, we should call the parent constructor.
#|uk| Тоді замість виклику сетера варто викликати батьківський конструктор.

Select:
```
    Id = id;

```

Remove selected

Go to "interestRate)|||"

Print ": base(id)"

Select "base(id)"

#|ru| В случаях, когда это невозможно, остаётся вызвать нужный метод инициализации, хотя сначала следует позаботиться о том, чтобы он был доступен для подклассов.
#|en| If that is impossible, we must call the proper initialization method. By the way, if it's private, you should make it protected first.
#|uk| У випадках, коли це неможливо, залишається викликати потрібний метод ініціалізації, хоча спочатку слід подбати про те, щоб він був доступний для підкласів.

Select visibility of "InitializeId"

Replace "protected"

Wait 500ms

Select ": base(id)"

Remove selected

Go to start of "public InterestAccount" 

Print:
```

    InitializeId(id);
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