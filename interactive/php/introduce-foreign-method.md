introduce-foreign-method:php

###

1. Создайте новый метод в клиентском классе.

2. В этом методе создайте параметр, в который будет передаваться объект служебного класса.

3. Извлеките волнующие вас участки кода в этот метод и замените их вызовами метода.

4. Оставьте в комментарии к этому методу метку <i>Foreign method</i> и призыв поместить этот метод в служебный класс, если такая возможность появится в дальнейшем.



###

```
class Account {
  // ...
  public function schedulePayment() {
    $previousDate = clone $this->previousDate;
    $paymentDate = $previousDate->modify('+7 days');

    // Issue a payment using paymentDate.
    // ...
  }
}
```

###

```
class Account {
  // ...
  public function schedulePayment() {
    $paymentDate = $this->nextWeek($this->previousDate);
    // Issue a payment using paymentDate.
    // ...
  }

  /**
   * Foreign method. Should be on Date.
   */
  private static function nextWeek(DateTime $arg) {
  	$previousDate = clone $arg;
  	return $previousDate->modify('+7 days');
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Введение внешнего метода</i> на примере класса банковского счета.

Select:
```
    $previousDate = clone $this->previousDate;
    $paymentDate = $previousDate->modify('+7 days');

```

# В этом классе есть некий код, который открывает новый период выставления счетов через неделю от текущего времени.

# Было бы идеально, если бы класс <code>DateTime</code> имел метод получения даты через семь дней (например, <code>previousDate.nextWeek()</code>), но он его не имеет, да и к тому же, мы не можем его изменить, т.к. он стандартный.

Go to the end of "Account"

# Однако мы вполне можем создать такой «внешний» метод в своём собственном классе.

Print:
```


  private function nextWeek() {
  	$previousDate = clone $this->previousDate;
  	return $previousDate->modify('+7 days');
  }
```

Set step 2

Go to parameters of "nextWeek"

# Чтобы метод был более универсальным, в него следует добавить параметр класса <code>Date</code>. По сути, мы будем расширять функциональность объекта, который подаётся в этом параметре.

Print "DateTime $arg"

Select "$this->previousDate" in "nextWeek"

Replace "$arg"

Go to type of "nextWeek"

# Также следует объявить метод статическим, чтобы к нему был доступ и из другого кода, не связанного с <code>Account</code>.

Print "static "

Set step 3

Select:
```
    $previousDate = clone $this->previousDate;
    $paymentDate = $previousDate->modify('+7 days');

```

# Теперь метод можно использовать в остальном коде.

Print "    $paymentDate = $this->nextWeek($this->previousDate);"

Set step 4

Go to before "nextWeek"

# В качестве последнего штриха следует добавить комментарий к «внешнему методу» о его природе. В дальнейшем это позволит избежать путаницы с его использованием. Кроме того, если в программе будет создан новый класс для хранения дополнительных функций с датами, этот метод будет легко найти и переместить в лучшее место.

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