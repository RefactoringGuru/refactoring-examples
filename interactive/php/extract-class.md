extract-class:php

###

1. Создайте новый класс, который будет содержать выделенную функциональность.

2. Создайте связь между старым и новым классом.

3. Используйте <a href="/move-field">перемещение поля</a> и <a href="/move-method">перемещение метода</a> для каждого поля и метода, которое вы решили переместить в новый класс.

4. Решите, делать ли новый класс доступным извне объекта старого класса.



###

```
class Person {
  private $name;
  private $officeAreaCode;
  private $officeNumber;
  
  public function getName() {
    return $this->name;
  }
  public function getTelephoneNumber() {
    return ("(" . $this->officeAreaCode . ") " . $this->officeNumber);
  }
  public function getOfficeAreaCode() {
    return $this->officeAreaCode;
  }
  public function setOfficeAreaCode($arg) {
    $this->officeAreaCode = arg;
  }
  public function getOfficeNumber() {
    return $this->officeNumber;
  }
  public function setOfficeNumber($arg) {
    $this->officeNumber = $arg;
  }
}
```

###

```
class Person {
  private $name;
  private $officeTelephone; // TelephoneNumber
  
  public function __construct() {
    $this->officeTelephone = new TelephoneNumber();
  }
  
  public function getName() {
    return $this->name;
  }
  public function getOfficeTelephone() {
    return $this->officeTelephone;
  }
  public function getTelephoneNumber() {
    return $this->officeTelephone->getTelephoneNumber();
  }
}

class TelephoneNumber {
  private $areaCode;
  private $number;

  public function getAreaCode() {
    return $this->areaCode;
  }
  public function setAreaCode($arg) {
    $this->areaCode = $arg;
  }
  public function getNumber() {
    return $this->number;
  }
  public function setNumber($arg) {
    $this->number = $arg;
  }
  public function getTelephoneNumber() {
    return ("(" + $this->areaCode + ") " + $this->number);
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Извлечение класса</i> на примере простого класса, описывающего личность.

Select:
```
  private |||$officeAreaCode|||;
  private |||$officeNumber|||;
```
+ Select name of "getTelephoneNumber"
+ Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"
+ Select name of "getOfficeNumber"
+ Select name of "setOfficeNumber"

# В данном примере можно выделить в отдельный класс методы, относящиеся к телефонным номерам.

Go to the end of file

# Начнём с определения класса телефонного номера.

Print:
```


class TelephoneNumber {
}
```

Set step 2

Select name of "Person"

# Это было просто! Теперь создадим ссылку из класса <code>Person</code> на класс телефонного номера.

Go to "private $officeNumber;|||"

Print:
```

  private $officeTelephone; // TelephoneNumber
  
  public function __construct() {
    $this->officeTelephone = new TelephoneNumber();
  }
```

Set step 3

Select "private |||$officeAreaCode|||"
+ Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"

# Всё готово, чтобы начать перемещать поля и методы. Воспользуемся рефакторингом <a href="/move-field">перемещение поля</a>, чтобы передвинуть поле <code>officeAreaCode</code> в класс <code>TelephoneNumber</code>.

Go to the start of "TelephoneNumber"

Print:
```

  private $areaCode;

  public function getAreaCode() {
    return $this->areaCode;
  }
  public function setAreaCode($arg) {
    $this->areaCode = $arg;
  }
```

Select "areaCode" in "TelephoneNumber"

# Заметили? Мы сразу переименовали это поле так, чтобы оно имело более нейтральное название. Это повысит наши шансы на повторное использование данного класса.

Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"

# После того, как поле успешно переместилось в класс <code>TelephoneNumber</code>, методы, которые использовали перемещённое поле, необходимо изменить так, чтобы они обращались к экземпляру созданного класса.

Select body of "getOfficeAreaCode"

Replace:
```
    return $this->officeTelephone->getAreaCode();
```

Wait 500ms

Select body of "setOfficeAreaCode"

Replace:
```
    $this->officeTelephone->setAreaCode($arg);
```

# Методы, которые использовали прямой доступ к полю, необходимо изменить так, чтобы они обращались к геттеру поля.

Select "officeAreaCode" in "getTelephoneNumber"

Replace "getOfficeAreaCode()"

Select:
```
  private $officeAreaCode;

```

# После чего можно удалить поле из исходного класса.

Remove selected

Select "private |||$officeNumber|||"

# Итак, с <code>areaCode</code> разобрались. Аналогичным образом переносим поле <code>officeNumber</code>...

Go to "private $areaCode;|||"

Print:
```

  private $number;
```

Go to the end of "TelephoneNumber"

Print:
```

  public function getNumber() {
    return $this->number;
  }
  public function setNumber($arg) {
    $this->number = $arg;
  }
```

Select name of "getTelephoneNumber"

# ...и метод получения отформатированного номера <code>getTelephoneNumber()</code>.

Go to the end of "TelephoneNumber"

Print:
```

  public function getTelephoneNumber() {
    return ("(" + $this->areaCode + ") " + $this->number);
  }
```

Select "private |||$officeNumber|||"

# После чего мы можем делегировать всю телефонную функциональность классу <code>TelephoneNumber</code>.

Select body of "getTelephoneNumber"

Replace:
```
    return $this->officeTelephone->getTelephoneNumber();
```

Select body of "getOfficeNumber"

Replace:
```
    return $this->officeTelephone->getNumber();
```

Select body of "setOfficeNumber"

Replace:
```
    $this->officeTelephone->setNumber($arg);
```

Select:
```
  private $officeNumber;

```

Remove selected

#C Выполним тестирование, чтобы удостоверится, что код остался рабочим.

#S Всё отлично, код работает корректно.

Set step 4

Select "private $officeTelephone"

#+ На этом этапе осталось решить, в какой мере сделать новый класс доступным для клиентов. Можно полностью скрыть класс, создав для доступа делегирующие методы (как это сделано сейчас)...

Select whole "getOfficeAreaCode"
+ Select whole "setOfficeAreaCode"
+ Select whole "getOfficeNumber"
+ Select whole "setOfficeNumber"

#= ...а можно удалить все эти методы и сделать класс открытым.

Remove selected


# При этом нужно будет создать публичный геттер для связанного объекта, чтобы клиенты могли «достучаться» до него.

Go to before "getTelephoneNumber"

Print:
```

  public function getOfficeTelephone() {
    return $this->officeTelephone;
  }
```

Select name of "getOfficeTelephone"

# Однако, решив сделать класс общедоступным, следует учесть опасности, связанные со ссылками. Как отнестись к тому, что при открытии телефонного номера клиент может изменить код зоны? Такое изменение может произвести любой код, имеющий доступ к экземпляру класса через публичный геттер.

# Возможны следующие варианты: <ul><li>Допускается изменение любым объектом любой части телефонного номера. В таком случае телефонный номер становится объектом-ссылкой, и следует рассмотреть <a href="/change-value-to-reference">замену значения ссылкой</a>. Доступ к телефонному номеру осуществляется через экземпляр класса <code>Person</code>.</li><li>Вы не желаете, чтобы кто-либо мог изменить телефонный номер, кроме как посредством методов экземпляра класса <code>Person</code>. Можно сделать телефонный номер только для чтения или обеспечить доступ к нему только через соответствующий метод.</li><li>Существует также возможность клонировать экземпляр класса <code>TelephoneNumber</code> перед тем, как предоставить его, но это может привести к недоразумениям, потому что люди будут думать, что могут изменить его значение. При этом могут также возникнуть проблемы со ссылками у клиентов, если телефонный номер часто передаётся.</li></ul>

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.