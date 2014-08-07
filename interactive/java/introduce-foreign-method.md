introduce-foreign-method:java

###

1. Создайте новый метод в клиентском классе.

2. В этом методе создайте параметр, в который будет передаваться объект служебного класса.

3. Извлеките волнующие вас участки кода в этот метод и замените их вызовами метода.

4. Оставьте в комментарии к этому методу метку <i>Foreight method</i> и призыв поместить этот метод в служебный класс, если такая возможность появится в дальнейшем.



###

```
class Account {
  // ...
  double schedulePayment() {
    Date paymentDate = new Date(previousDate.getYear(), previousDate.getMonth(), previousDate.getDate() + 7);

    // Issue a payment using paymentDate.
    // ...
  }
}
```

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
  public static Date nextWeek(Date arg) {
    return new Date(arg.getYear(), arg.getMonth(), arg.getDate() + 7);
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Введение внешнего метода</i> на примере класса банковского счета.

Select "Date paymentDate = new Date(previousDate.getYear(), previousDate.getMonth(), previousDate.getDate() + 7)"

# В этом классе есть некий код, который открывает новый период выставления счетов через неделю от текущего времени.

# Было бы идеально, если бы класс <code>Date</code> имел метод возврата даты через семь дней (например, <code>previousDate.nextWeek()</code>), но он его не имеет, да и к тому же, мы не можем его изменить, т.к. он стандартный.

Go to the end of "Account"

# Тем не менее, мы можем создать такой «внешний» метод в своём собственном классе.

Print:
```


  public Date nextWeek() {
    return new Date(previousDate.getYear(), previousDate.getMonth(), previousDate.getDate() + 7);
  }
```

Set step 2

Go to parameters of "nextWeek"

# Чтобы метод был более универсальным, в него следует добавить параметр класса <code>Date</code>. По сути, мы будем расширять функциональность объекта, который подаётся в этом параметре.

Print "Date arg"

Select "previousDate" in "nextWeek"

Print "arg"

Go to type of "nextWeek"

# Кроме того, следует объявить метод статическим, дабы к нему был доступ и из другого кода, не связанного с <code>Account</code>.

Print "static "

Set step 3

Select "new Date(previousDate.getYear(), previousDate.getMonth(), previousDate.getDate() + 7)"

# Теперь метод можно использовать в остальном коде.

Print "nextWeek(previousDate)"

Set step 4

Go to before "nextWeek"

# В качестве последнего штриха, следует добавить комментарий к «внешнему методу» о его природе. В дальнейшем это позволит избежать путаницы с его использованием. Кроме того, если в программе будет создан новый класс для хранения дополнительных функций с датами, этот метод будет легко найти и переместить в лучшее место.

Print:
```

  /**
   * Foreign method. Should be on Date.
   */
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.