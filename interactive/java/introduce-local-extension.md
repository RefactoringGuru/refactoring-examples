introduce-local-extension:java

###

1. Создайте новый класс-расширение и сделайте его наследником служебного класса.

2. Создайте конструктор, использующий параметры конструктора служебного класса.

3. Создайте альтернативный «конвертирующий» конструктор, который принимает в параметрах только объект оригинального класса.

4. Создайте в классе новые расширенные методы. Переместите в него внешние методы из других классов, либо удалите их, если расширение уже имеет такой функционал.

5. Замените использование служебного класса новым классом-расширением в тех местах, где нужна расширенная функциональность.



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
  private Date nextWeek() {
    return new Date(getYear(), getMonth(), getDate() + 7);
  }
}
```

###

Set step 1

# <i>Введение локального расширения</i> можно осуществить двумя способами: через создание класса-наследника либо класса-обёртки. В этом примере мы пойдём путём наследования.

# Сначала создадим новый класс дат, как подкласс оригинального класса <code>Date</code>

Go to the end of file

Print:
```


// Local extension class.
class MfDateSub extends Date {
}
```

Set step 2

Go to the start of "MfDateSub"

# Затем надо повторить конструкторы оригинала путём простого делегирования.

Print:
```

  public MfDateSub(String dateString) {
    super(dateString);
  }
```

Set step 3

# Теперь добавляется конвертирующий конструктор, принимающий оригинал в качестве аргумента.

Go to the end of "MfDateSub"

Print:
```

  public MfDateSub(Date arg) {
    super(arg.getTime());
  }
```

Set step 4

Select whole "nextWeek"

# После того, как конструкторы готовы, можно добавить в класс новые методы, либо с помощью <a href="/move-method">перемещения метода</a> перенести в расширение имеющиеся внешние методы из других классов, что мы и сделаем.

Go to the end of "MfDateSub"

Print:
```

  private static Date nextWeek(Date arg) {
    return new Date(arg.getYear(), arg.getMonth(), arg.getDate() + 7);
  }
```

Select parameters of "nextWeek" in "MfDateSub"

# Параметр метода нам теперь не нужен, т.к. метод находится внутри наследника <code>Date</code>. Соответственно, нужные данные можно взять из собственного объекта.

Remove selected

Select "arg." in "nextWeek" in "MfDateSub"

Remove selected

Select "|||static |||Date nextWeek"

# Кроме того, метод перестаёт быть статическим.

Remove selected

Set step 5

Select "nextWeek(previousDate)"

# Теперь изменим код, использующий внешний метод так, чтобы он вместо этого использовал новый класс-расширение.

Print "new MfDateSub(previousDate).nextWeek()"

Select whole "nextWeek" in "Account"
+ Select:
```

  /**
   * Foreign method. Should be on Date.
   */

```
# После всех замен внешний метод можно удалить из клиентского класса.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.