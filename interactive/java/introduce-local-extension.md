introduce-local-extension:java

###

1.ru. Создайте новый класс-расширение и сделайте его наследником служебного класса.
1.en. Create a new extension class and make it the inheritor of the utility class.
1.uk. Створіть новий клас-розширення і зробіть його спадкоємцем службового класу.

2.ru. Создайте конструктор, использующий параметры конструктора служебного класса.
2.en. Create a constructor that uses the parameters of the constructor of the utility class.
2.uk. Створіть конструктор, який використовує параметри конструктора службового класу.

3.ru. Создайте альтернативный «конвертирующий» конструктор, который принимает в параметрах только объект оригинального класса.
3.en. Create an alternative "converting" constructor that accepts only an object of the original class in its parameters.
3.uk. Створіть альтернативний «конвертуючий» конструктор, який приймає в параметрах тільки об'єкт оригінального класу.

4.ru. Создайте в классе новые расширенные методы. Переместите в него внешние методы из других классов, либо удалите их, если расширение уже имеет такой функционал.
4.en. Create new extended methods in the class. Move foreign methods from other classes to this class or else delete the foreign methods if their functionality is already present in the extension.
4.uk. Створіть в класі нові розширені методи. Перемістіть в нього зовнішні методи з інших класів, або видалите їх, якщо розширення вже має такий функціонал.

5.ru. Замените использование служебного класса новым классом-расширением в тех местах, где нужна расширенная функциональность.
5.en. Replace use of the utility class with the new extension class in places where its functionality is needed.
5.uk. Замініть використання службового класу новим класом-розширенням в тих місцях, де потрібна розширена функціональність.



###

```
class Account {
  // ...
  double schedulePayment() {
    Date paymentDate = nextWeek(previousDate);

    // Issue a payment using paymentDate.
    // ...
  }

  /**
   * Foreign method. Should be on Date.
   */
  private static Date nextWeek(Date arg) {
    return new Date(arg.getYear(), arg.getMonth(), arg.getDate() + 7);
  }
}
```

###

```
class Account {
  // ...
  double schedulePayment() {
    Date paymentDate = new MfDateSub(previousDate).nextWeek();

    // Issue a payment using paymentDate.
    // ...
  }
}

// Local extension class.
class MfDateSub extends Date {
  public MfDateSub(String dateString) {
    super(dateString);
  }
  public MfDateSub(Date arg) {
    super(arg.getTime());
  }
  public Date nextWeek() {
    return new Date(getYear(), getMonth(), getDate() + 7);
  }
}
```

###

Set step 1

#|ru| <i>Введение локального расширения</i> можно осуществить двумя способами: через создание класса-наследника либо через создание класса-обёртки. В этом примере мы пойдём путём наследования.
#|en| <i>Introduction of a local extension</i> can be performed in two ways: by creating either subclass or a wrapper class. In this example, we will use inheritance.
#|uk| <i>Введення локального розширення<i> можна здійснити двома способами: через створення класу-спадкоємця або класу-обгортки. В цьому прикладі ми підемо шляхом наслідування.

#|ru| Для начала давайте создадим новый класс дат, как подкласс оригинального класса <code>Date</code>
#|en| First, we create a new subclass of the original <code>Date</code> class.
#|uk| Спочатку створимо новий клас дат, як підклас оригінального класу <code>Date</code>

Go to the end of file

Print:
```


// Local extension class.
class MfDateSub extends Date {
}
```

Set step 2

Go to the start of "MfDateSub"

#|ru| Затем надо повторить конструкторы оригинала путём простого делегирования.
#|en| Then repeat the original's constructors via simple delegation.
#|uk| Потім треба повторити конструктори оригіналу шляхом простого делегування.

Print:
```

  public MfDateSub(String dateString) {
    super(dateString);
  }
```

Set step 3

#|ru| Теперь добавляется конвертирующий конструктор, принимающий оригинал в качестве аргумента.
#|en| Now wee should add a converting constructor that accepts the original as an argument.
#|uk| Тепер додається конвертируючий конструктор, який приймає оригінал як аргумент.

Go to the end of "MfDateSub"

Print:
```

  public MfDateSub(Date arg) {
    super(arg.getTime());
  }
```

Set step 4

Select whole "nextWeek"

#|ru| Когда конструкторы класса готовы, можно добавить в него новые методы или перенести внешние методы из других классов. Давайте перенесём метод <code>nextWeek()</code>, воспользовавшись <a href="/move-method">перемещением метода</a>.
#|en| When the class constructors are ready, you can add new methods to it or move foreign methods form other classes. Let's move the <code>nextWeek()</code> method with the help of <a href="/move-method">Move Method</a>.
#|uk| Коли конструктори класу готові, можна додати в нього нові методи або перенести зовнішні методи з інших класів . Давайте перенесемо метод <code>nextWeek()</code>, скориставшись <a href="/move-method">переміщенням методу</a>.

Go to the end of "MfDateSub"

Print:
```

  private static Date nextWeek(Date arg) {
    return new Date(arg.getYear(), arg.getMonth(), arg.getDate() + 7);
  }
```

Select parameters of "nextWeek" in "MfDateSub"

#|ru| Параметр метода нам теперь не нужен, т.к. метод находится внутри наследника <code>Date</code>. Соответственно, нужные данные можно взять из собственного объекта.
#|en| The method parameter is no longer needed since the method is inside the <code>Date</code> subclass. Thus, the needed data can be taken from its own object.
#|uk| Параметр методу нам тепер не потрібен, тому що метод знаходиться всередині спадкоємця <code>Date</code>. Відповідно, потрібні дані можна взяти з власного об'єкта.

Remove selected

Select "arg." in "nextWeek" in "MfDateSub"

Remove selected

Select "|||private static||| Date nextWeek" in "MfDateSub"

#|ru| Кроме того, метод перестаёт быть статическим и приватным, ведь нам надо иметь возможность вызывать его из других классов.
#|en| In addition, the method stops being static and private – after all, we need to be able to call it from other classes.
#|uk| Крім того, метод перестає бути статичним і приватним, адже нам треба мати можливість викликати його з інших класів.

Replace "public"

Wait 500ms

Set step 5

Select "nextWeek(previousDate)"

#|ru| Изменим код, использующий внешний метод, чтобы вместо метода он использовал новый класс-расширение.
#|en| Now we replace all usages of the old foreign method with our new extension class.
#|uk| Змінимо код, що використовує зовнішній метод так, щоб він замість цього використовував новий клас-розширення.

Print "new MfDateSub(previousDate).nextWeek()"

Select whole "nextWeek" in "Account"
+ Select:
```

  /**
   * Foreign method. Should be on Date.
   */

```
#|ru| После всех замен внешний метод можно удалить.
#|en| After all changes are complete, we remove the external method from the client class.
#|uk| Після всіх замін зовнішній метод можна видалити.

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