introduce-parameter-object:php

###

1. Создайте новый класс, который будет представлять вашу группу параметров. Сделайте так, чтобы данные объектов этого класса нельзя было изменить после создания.

2. В методе, к которому применяем рефакторинг, <a href="/add-parameter">добавьте новый параметр</a>, в котором будет передаваться ваш объект-параметр. Во всех вызовах метода передавайте в этот параметр объект, создаваемый из старых параметров метода.

3. Теперь начинайте по одному удалять старые параметры из метода, заменяя их в коде полями объекта-параметра. Тестируйте программу после каждой замены параметра.

4. По окончанию оцените, есть ли возможность и смысл перенести какую-то часть метода (а иногда и весь метод) в класс объекта-параметра. Если так, используйте <a href="/move-method">перемещение метода</a> или <a href="/extract-method">извлечение метода</a>, чтобы осуществить перенос.



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
   
// Somewhere in client code...
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
   
// Somewhere in client code...
$flow = $account->getFlowBetween(new DateRange($startDate, $endDate));
```

###

Set step 1

# Расмотрим этот рефакторинг на примере класса банковского счета и его транзакций.

Select name of "getFlowBetween"

# Нас интересует метод получения суммы транзакиций за указанный период времени.

Select parameters in "getFlowBetween"

# Как видите, метод принимает в качестве параметров диапазон из двух даты. Это доволно частая картина и было бы неплохо вместо передачи двух дат передавать объект диапазона дат.

# В дальнейшем, в такой объект можно было бы перенести операции по проверке вхождения даты в диапазон и проче.

Go to after "Transaction"

# Итак, создадим простой класс диапазонов.

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

# Заметьте, что класс сделан неизменяемым — поменять даты диапазона после его создания невозможно, т.к. поля дат объявлены как приватные, а сеттеров для них мы не создали.

# Этот шаг позволит избежать многих ошибок связанных с передачей объектов в параметрах по ссылкам.

Set step 2

Go to the parameters end of "getFlowBetween"

# Теперь мы готовы добавить параметр дипазона в метод получения суммы транзакий.

Print ", DateRange $range"

# Теперь найдем все места, где вызывается этот метод и допишем в этих новых вызовах новый параметр, а именно объект, созданных из уже подаваемых в метод дат.

Go to ", $endDate|||"

Print ", new DateRange($startDate, $endDate)"

Set step 3

# Теперь, когда новый параметр уже на месте, вернемся в описание метода и попробуем избавиться в нем от старых параметров дат.

Select "DateTime $start" in parameters of "getFlowBetween"

# Сперва займемся параметром <code>start</code>

Select "$start" in body of "getFlowBetween"

Wait 500ms

Print "$range->getStart()"

Select "DateTime $start, " in parameters of "getFlowBetween"

# После замен в теле метода, параметр можно удалить из сигнатуры метода и из его вызовов.

Remove selected

Wait 500ms

Select "getFlowBetween(|||$startDate, |||"

Remove selected

Select "DateTime $end" in parameters of "getFlowBetween"

# Теперь займёмся оставшимся параметром.

Select "$end" in body of "getFlowBetween"

Wait 500ms

Print "$range->getEnd()"

Wait 500ms

Select "DateTime $end, " in parameters of "getFlowBetween"

Remove selected

Select "getFlowBetween(|||$endDate, |||"

Remove selected

#C После всех переносов можно запустить авто-тесты.

#S Отлично, все работает, продолжаем!


Set step 4

# После того, как все необходимые параметры были удалены, можно подумать о перенесении в объект-параметр каких-то поведений, которые ему подходят.

Select:
```
$transaction->getDate() == $range->getStart() ||
          $transaction->getDate() == $range->getEnd() ||
          ($transaction->getDate() > $range->getStart() && $transaction->getDate() < $range->getEnd())
```

# В нашем случае, можно перенести проверку вхождения дат в диапазон, избавившись от корявого кода внутри <code>getFlowBetween</code>.

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

Wait 500ms

Print "$range->includes($transaction->getDate())"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.