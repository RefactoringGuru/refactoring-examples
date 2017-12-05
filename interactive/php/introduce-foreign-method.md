introduce-foreign-method:php

###

1.ru. Создайте новый метод в клиентском классе.

1.en. Create a new method in the client class.

1.uk. Створіть новий метод в клієнтському класі.

2.ru. В этом методе создайте параметр, в который будет передаваться объект служебного класса.

2.en. In this method, create a parameter to which the object of the utility class will be passed. If this object can be obtained from the client class, you do not have to create such a parameter.

2.uk. У цьому методі створіть параметр, в який передаватиметься об'єкт службового класу. Якщо цей об'єкт може бути отриманий з клієнтського класу, параметр можна не створювати.

3.ru. Извлеките волнующие вас участки кода в этот метод и замените их вызовами метода.

3.en. Extract the relevant code fragments to this method and replace them with method calls.

3.uk. Витягніть ділянки коду, які вам потрібні, в цей метод і замініть їх викликами методу.

4.ru. Оставьте в комментарии к этому методу метку <i>Foreign method</i> и призыв поместить этот метод в служебный класс, если такая возможность появится в дальнейшем.

4.en. Leave a comment for the method, labeling it as <i>Foreign method</i> and requesting that the method be placed in a utility class if this becomes possible in the future.

4.uk. Залиште в коментарі до цього методу мітку <i>Foreign method</i> і заклик помістити цей метод в службовий клас, якщо така можливість з'явиться в подальшому.



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
    $paymentDate = self::nextWeek($this->previousDate);
    // Issue a payment using paymentDate.
    // ...
  }

  /**
   * Foreign method. Should be in the DateTime class.
   */
  private static function nextWeek(DateTime $arg) {
  	$previousDate = clone $arg;
  	return $previousDate->modify('+7 days');
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Введение внешнего метода</i> на примере класса банковского счета.
#|en| Let's look at <i>Introduce Foreign Method</i> using the example of a bank account class.
#|uk| Давайте розглянемо <i>Запровадження зовнішнього методу<i> на прикладі класу банківського рахунку.

Select:
```
    $previousDate = clone $this->previousDate;
    $paymentDate = $previousDate->modify('+7 days');

```

#|ru| В этом классе есть некий код, который открывает новый период выставления счетов через неделю от текущего времени.
#|en| This class has code that opens a new billing period one week in the future from the current time.
#|uk| В цьому класі є якийсь код, який відкриває новий період виставлення рахунків через тиждень від поточного часу.

#|ru| Было бы идеально, если бы класс <code>DateTime</code> имел метод получения даты через семь дней (например, <code>$previousDate.nextWeek()</code>), но он его не имеет, да и к тому же, мы не можем его изменить, т.к. он стандартный.
#|en| Ideally, the <code>DateTime</code> class would have a method for getting a date seven days in the future (something resembling <code>$previousDate->nextWeek()</code>) but it does not, and what's more, it is standard so we cannot change it.
#|uk| Було б ідеально, якби клас <code>DateTime</code> мав метод отримання дати через сім днів (наприклад, <code>$previousDate.nextWeek()</code>), але він його не має, та й до того ж , ми не можемо його змінити, бо він стандартний.

Go to the end of "Account"

#|ru| Однако мы вполне можем создать такой «внешний» метод в своём собственном классе.
#|en| What we can do though is create a "foreign" method in its own class.
#|uk| Однак ми цілком можемо створити такий «зовнішній» метод у своєму власному класі.

Print:
```


  private function nextWeek() {
  	$previousDate = clone $this->previousDate;
  	return $previousDate->modify('+7 days');
  }
```

Set step 2

Go to parameters of "nextWeek"

#|ru| Чтобы метод был более универсальным, в него следует добавить параметр класса <code>DateTime</code>. По сути, мы будем расширять функциональность объекта, который подаётся в этом параметре.
#|en| To make the method more universal, we will add a parameter of the <code>DateTime</code> class to it. Essentially, we are extending the functionality of the object passed in this parameter.
#|uk| Щоб метод був більш універсальним, в нього слід додати параметр класу <code>DateTime</code>. По суті, ми будемо розширювати функціональність об'єкта, який подається в цьому параметрі.

Print "DateTime $arg"

Select "$this->previousDate" in "nextWeek"

Replace "$arg"

Go to type of "nextWeek"

#|ru| Также следует объявить метод статическим, чтобы его можно было вызывать и из статических методов класса, если такие существуют сейчас или будут созданы в будущем.
#|en| You should also declare the method static to make it accessible from other static methods of <code>Account</code> should any exists now or in future.
#|uk| Також слід оголосити метод статичним, щоб його можна було викликати і з статичних методів классу, якщо такі існують, або з'являться в майбутньому.

Print "static "

Set step 3

Select:
```
    $previousDate = clone $this->previousDate;
    $paymentDate = $previousDate->modify('+7 days');

```

#|ru| Теперь метод можно использовать в остальном коде.
#|en| The method can now be used in the other code.
#|uk| Тепер метод можна використовувати в іншому коді.

Print "    $paymentDate = self::nextWeek($this->previousDate);"

Set step 4

Go to before "nextWeek"

#|ru| В качестве последнего штриха следует добавить комментарий к «внешнему методу» о его природе. В дальнейшем это позволит избежать путаницы с его использованием. Кроме того, если в программе будет создан новый класс для хранения дополнительных функций с датами, этот метод будет легко найти и переместить в лучшее место.
#|en| For the finishing touch, let's add a comment to the "foreign method" about its purpose and our intentions. That will help to avoid potential confusion regarding its use. And if a new class is created in the program later for storing additional date-related functions, this method can be easily found and moved to a better place.
#|uk| В якості останнього штриха слід додати коментар до «зовнішнього методу» про його природу. Надалі це дозволить уникнути плутанини з його використанням. Крім того, якщо в програмі буде створено новий клас для зберігання додаткових функцій з датами, цей метод буде легко знайти і перемістити в краще місце.

Print:
```

  /**
   * Foreign method. Should be in the DateTime class.
   */
```

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.