introduce-local-extension:php

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

```
class Account {
  // ...
  public function schedulePayment() {
    $paymentDate = (new MyNewDate($this->previousDate))->nextWeek();
    // Issue a payment using paymentDate.
    // ...
  }
}

// Local extension class.
class MyNewDate extends DateTime {
  public function __construct() {
  	$args = func_get_args();
  	if (isset($args[0]) && is_a($args[0], 'DateTime')) {
      call_user_func_array(array($this, 'parent::__construct'), $args[0]->format('Y-m-d H:i:s'));
    }
    else {
      call_user_func_array(array($this, 'parent::__construct'), $args);
    }
  }
  public function nextWeek() {
  	return $this->modify('+7 days');
  }
}
```

###

Set step 1

# <i>Введение локального расширения</i> можно осуществить двумя способами: через создание класса-наследника либо через создание класса-обёртки. В этом примере мы пойдём путём наследования.

# Для начала давайте создадим новый класс дат, как подкласс оригинального класса <code>Date</code>

Go to the end of file

Print:
```


// Local extension class.
class MyNewDate extends DateTime {
}
```

Set step 2

Go to the start of "MyNewDate"

# Затем надо повторить конструкторы оригинала путём простого делегирования.

Print:
```

  public function __construct() {
  	$args = func_get_args();
  	call_user_func_array(array($this, 'parent::__construct'), $args);
  }
```

Set step 3

Select name of "__construct"

# Теперь добавляется конвертирующий конструктор, принимающий оригинал в качестве аргумента. В PHP нельзя создавать несколько конструкторов, поэтому мы просто расширим существующий.

Select:
```
  	call_user_func_array(array($this, 'parent::__construct'), $args);
```

Replace:
```
  	if (isset($args[0]) && is_a($args[0], 'DateTime')) {
      call_user_func_array(array($this, 'parent::__construct'), $args[0]->format('Y-m-d H:i:s'));
    }
    else {
      call_user_func_array(array($this, 'parent::__construct'), $args);
    }
```

Set step 4

Select whole "nextWeek"

# Когда конструкторы класса готовы, можно добавить в него новые методы или перенести внешние методы из других классов. Давайте перенесём метод <code>nextWeek()</code>, воспользовавшись <a href="/move-method">перемещением метода</a>.

Go to the end of "MyNewDate"

Print:
```

  private static function nextWeek(DateTime $arg) {
  	$previousDate = clone $arg;
  	return $previousDate->modify('+7 days');
  }
```

Select parameters of "nextWeek" in "MyNewDate"

# Параметр метода нам теперь не нужен, т.к. метод находится внутри наследника <code>DateTime</code>. Соответственно, нужные данные можно взять из собственного объекта.

Remove selected

Select:
```
  	$previousDate = clone $arg;

```

Remove selected

Select "$previousDate" in "nextWeek" of "MyNewDate"

Replace "$this"

Select "|||private static||| function nextWeek" in "MyNewDate"

# Кроме того, метод перестаёт быть статическим и приватным, ведь нам надо иметь возможность вызывать его из других классов.

Replace "public"

Wait 500ms

Set step 5

Select "$this->nextWeek($this->previousDate)"

# Изменим код, использующий внешний метод, чтобы вместо метода он использовал новый класс-расширение.

Print "(new MyNewDate($this->previousDate))->nextWeek()"

Select whole "nextWeek" in "Account"
+ Select:
```

  /**
   * Foreign method. Should be on Date.
   */

```
# После всех замен внешний метод можно удалить.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.