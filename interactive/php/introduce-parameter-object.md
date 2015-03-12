introduce-parameter-object:php

###

1.ru. Создайте новый класс, который будет представлять вашу группу параметров. Сделайте так, чтобы данные объектов этого класса нельзя было изменить после создания.
1.en. Create a new class that will represent your group of parameters. Make the class immutable.
1.uk. Створіть новий клас, який представлятиме вашу групу параметрів. Зробіть так, щоб дані об'єктів цього класу не можна було змінити після створення (make classes immutable).

2.ru. В методе, к которому применяем рефакторинг, <a href="/add-parameter">добавьте новый параметр</a>, в котором будет передаваться ваш объект-параметр. Во всех вызовах метода передавайте в этот параметр объект, создаваемый из старых параметров метода.
2.en. In the method that you want to refactor, use <a href="/add-parameter">Add Parameter</a>, which is where your parameter object will be passed. In all method calls, pass the object created from old method parameters to this parameter.
2.uk. В метод, до якого застосовується рефакторинг, <a href="/add-parameter">додайте новий параметр</a>, у якому передаватиметься ваш об'єкт-параметр. В усіх викликах методу передавайте в цей параметр об'єкт, що створюється із старих параметрів методу.

3.ru. Теперь начинайте по одному удалять старые параметры из метода, заменяя их в коде полями объекта-параметра. Тестируйте программу после каждой замены параметра.
3.en. Now start deleting old parameters from the method one by one, replacing them in the code with fields of the parameter object. Test the program after each parameter replacement.
3.uk. Тепер починайте по одному видаляти старі параметри з методу, замінюючи їх в коді полями об'єкту-параметра. Тестуйте програму після кожної заміни параметра.

4.ru. По окончанию оцените, есть ли возможность и смысл перенести какую-то часть метода (а иногда и весь метод) в класс объекта-параметра. Если так, используйте <a href="/move-method">перемещение метода</a> или <a href="/extract-method">извлечение метода</a>, чтобы осуществить перенос.
4.en. When done, see whether there is any point in moving a part of the method (or sometimes even the whole method) to a parameter object class. If so, use <a href="/move-method">Move Method</a> or <a href="/extract-method">Extract Method</a>.
4.uk. По закінченню оціните, чи є можливість і сенс перенести якусь частину методу (а іноді і увесь метод) в клас об'єкту-параметра. Якщо так, використайте <a href="/move-method">переміщення методу</a> або <a href="/extract-method">відокремлення методу</a>, щоб здійснити перенесення.



###

```
class Account {
  // ...
  private $transactions = array();

  public function getFlowBetween(DateTime $start, DateTime $end) {
    $result = 0;
    Enumeration e = transactions.elements();
    foreach ($this->transactions as $transaction) {
      if ($transaction->getDate() == $start ||
          $transaction->getDate() == $end ||
          ($transaction->getDate() > $start && $transaction->getDate() < $end))
      {
        $result += $transaction->getValue();
      }
    }
    return $result;
  }
}

class Transaction {
  private $chargeDate; // DateTime
  private $value;

  public function __construct($value, DateTime $chargeDate) {
    $this->value = $value;
    $this->chargeDate = $chargeDate;
  }
  public function getDate() {
    return $this->chargeDate;
  }
  public function getValue() {
    return $this->value;
  }
}

// Somewhere in client code…
$flow = $account->getFlowBetween($startDate, $endDate);
```

###

```
class Account {
  // ...
  private $transactions = array();

  public function getFlowBetween(DateRange $range) {
    $result = 0;
    Enumeration e = transactions.elements();
    foreach ($this->transactions as $transaction) {
      if ($range->includes($transaction->getDate()))
      {
        $result += $transaction->getValue();
      }
    }
    return $result;
  }
}

class Transaction {
  private $chargeDate; // DateTime
  private $value;

  public function __construct($value, DateTime $chargeDate) {
    $this->value = $value;
    $this->chargeDate = $chargeDate;
  }
  public function getDate() {
    return $this->chargeDate;
  }
  public function getValue() {
    return $this->value;
  }
}

class DateRange {
  private $start; // DateTime
  private $end; // DateTime

  public DateRange(DateTime $start, DateTime $end) {
    $this->start = $start;
    $this->end = $end;
  }
  public function getStart() {
    return $this->start;
  }
  public function getEnd() {
    return $this->end;
  }
  public function includes(DateTime $arg) {
    return ($arg == $this->start || $arg == $this->end || ($arg > $this->start && $arg < $this->end));
  }
}

// Somewhere in client code…
$flow = $account->getFlowBetween(new DateRange($startDate, $endDate));
```

###

Set step 1

#|ru| Рассмотрим этот рефакторинг на примере класса банковского счета и его транзакций.
#|en| Let's look at this refactoring, using the bank account and transactions classes.
#|uk| Розглянемо цей рефакторинг на прикладі класу банківського рахунку та його транзакцій.

Select name of "getFlowBetween"

#|ru| Нас интересует метод получения суммы транзакций за указанный период времени.
#|en| We are interested in the method for getting the total for all transactions during an indicated period of time.
#|uk| Нас цікавить метод отримання суми транзакцій за вказаний період часу.

Select parameters in "getFlowBetween"

#|ru| Как видите, метод принимает в качестве параметров диапазон из двух дат. Это довольно частая картина и было бы неплохо вместо передачи двух дат передавать объект диапазона дат.
#|en| As you can see, the method takes a range of two dates as its parameters. Pretty common situation? But instead of passing two dates, it would be better to pass a single date range object.
#|uk| Як бачите, метод приймає в якості параметрів діапазон з двох дат. Це досить часта картина і було б непогано замість передачі двох дат передавати об'єкт діапазону дат.

#|ru| В дальнейшем в такой объект можно было бы перенести операции по проверке вхождения даты в диапазон и прочее.
#|en| In future, that will allow us to move date range behaviors into their own class.
#|uk| Надалі в такий об'єкт можна було б перенести операції з перевірки щодо входження дати в діапазон та інше.

Go to after "Transaction"

#|ru| Итак, создадим простой класс диапазонов.
#|en| Let's begin with creating a simple range class.
#|uk| Отже, створимо простий клас діапазонів.

Print:
```


class DateRange {
  private $start; // DateTime
  private $end; // DateTime

  public DateRange(DateTime $start, DateTime $end) {
    $this->start = $start;
    $this->end = $end;
  }
  public function getStart() {
    return $this->start;
  }
  public function getEnd() {
    return $this->end;
  }
}
```

Select "private" in "DateRange"

#|ru| Заметьте, что класс сделан неизменяемым, т.е. поменять даты диапазона после его создания невозможно, так как поля дат объявлены как приватные, а сеттеров для них мы не создали.
#|en| The class will be immutable: the dates of the range cannot be changed after it is created, since the date fields are declared as private and we did not create setters for them.
#|uk| Зауважте, що клас є незмінним, тобто поміняти дати діапазону після його створення неможливо, так як поля дат оголошені приватними, а сеттерів для них ми не створили.

#|ru| Этот шаг позволит избежать многих ошибок связанных с передачей объектов в параметрах по ссылкам.
#|en| This way you could avoid many errors related to passing objects in method parameters via references.
#|uk| Цей крок дозволить уникнути багатьох помилок пов'язаних з передачею об'єктів в параметрах за посиланнями.

Set step 2

Go to the parameters end of "getFlowBetween"

#|ru| Теперь мы готовы добавить параметр диапазона в метод получения суммы транзакций.
#|en| Now we can add a range parameter to the method for getting the transaction total.
#|uk| Тепер ми готові додати параметр діапазону в метод отримання суми транзакцій.

Print ", DateRange $range"

#|ru| Находим все места, где вызывается этот метод, и дописываем в этих вызовах новый параметр, а именно объект, созданный из уже подаваемых в метод дат.
#|en| Now, let's find all places where the method is called. In these calls, add a new parameter – specifically, an object created from the dates already given to the method.
#|uk| Знаходимо всі місця, де викликається цей метод, і дописуємо в цих викликах новий параметр, а саме об'єкт, створений з дат, які вже подаються в метод.

Go to ", $endDate|||"

Print ", new DateRange($startDate, $endDate)"

Set step 3

#|ru| Теперь, когда новый параметр уже на месте, вернёмся в описание метода и попробуем избавиться в нем от старых параметров дат.
#|en| The new parameter is in place, so we return to the method description and try to get rid of the old date parameters there.
#|uk| Тепер, коли новий параметр вже на місці, повернемося в опис методу і спробуємо позбутися в ньому від старих параметрів дат.

Select "DateTime $start" in parameters of "getFlowBetween"

#|ru| Сначала займёмся параметром <code>start</code>.
#|en| First take care of the <code>start</code> parameter.
#|uk| Спочатку візьмемо до уваги  параметр <code>start</code>.

Select "$start" in body of "getFlowBetween"

Replace "$range->getStart()"

Select "DateTime $start, " in parameters of "getFlowBetween"

#|ru| После замен в теле метода параметр можно удалить из сигнатуры метода и из его вызовов.
#|en| After replacements in the method body, the parameter can be removed from the signature and calls of the method.
#|uk| Після замін в тілі методу параметр можна видалити з сигнатури методу та з його викликів.

Remove selected

Wait 500ms

Select "getFlowBetween(|||$startDate, |||"

Remove selected

Select "DateTime $end" in parameters of "getFlowBetween"

#|ru| Теперь займёмся оставшимся параметром.
#|en| Now for the remaining parameter.
#|uk| Тепер займемося залишившимся параметром.

Select "$end" in body of "getFlowBetween"

Replace "$range->getEnd()"

Wait 500ms

Select "DateTime $end, " in parameters of "getFlowBetween"

Remove selected

Select "getFlowBetween(|||$endDate, |||"

Remove selected

#C|ru| После всех переносов можно запустить авто-тесты.
#S Отлично, все работает, продолжаем!

#C|en| After performing all the moves, we can run autotests.
#S Everything is good! Let's continue.

#C|uk| Після всіх переносів можна запустити авто-тести.
#S Супер, все працює, продовжуємо.


Set step 4

#|ru| После того как все необходимые параметры были удалены, можно подумать о перенесении в объект-параметр каких-то поведений, которые ему подходят.
#|en| After all the necessary parameters were removed, we can start thinking about moving appropriate behaviors to the parameter object.
#|uk| Після того як всі необхідні параметри були видалені, можна подумати про перенесення в об'єкт-параметр якихось поведінок, які йому підходять.

Select:
```
$transaction->getDate() == $range->getStart() ||
          $transaction->getDate() == $range->getEnd() ||
          ($transaction->getDate() > $range->getStart() && $transaction->getDate() < $range->getEnd())
```

#|ru| В нашем случае можно перенести проверку вхождения дат в диапазон, избавившись от корявого кода внутри <code>getFlowBetween</code>.
#|en| In our case, we can move a check to see if a date is within a range. This gets rid of the unwieldy code inside <code>getFlowBetween</code>.
#|uk| У нашому випадку можна перенести перевірку входження дат в діапазон, позбувшись корявого коду всередині <code>getFlowBetween</code>.

Go to the end of "DateRange"

Print:
```

  public function includes(DateTime $arg) {
    return ($arg == $this->start || $arg == $this->end || ($arg > $this->start && $arg < $this->end));
  }
```

Wait 500ms

Select:
```
$transaction->getDate() == $range->getStart() ||
          $transaction->getDate() == $range->getEnd() ||
          ($transaction->getDate() > $range->getStart() && $transaction->getDate() < $range->getEnd())
```

Replace "$range->includes($transaction->getDate())"

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