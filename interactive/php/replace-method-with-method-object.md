replace-method-with-method-object:php

###

1. Создайте новый класс. Дайте ему название, основываясь на предназначении метода, который рефакторите.

2. В новом классе создайте приватное поле для хранения ссылки на экземпляр класса, в котором раньше находился метод.

3. Кроме того, создайте по приватному полю для каждой локальной переменной и параметра метода.

4. Создайте конструктор, который принимает все параметры исходного метода и инициализирует соответствующие приватные поля.

5. Объявите основной метод и скопируйте в него код оригинального метода, заменив локальные переменные приватным полями.

6. Замените тело оригинального метода в исходном классе созданием объекта-метода и вызовом его основного метода.



###

```
class Account {
  // ...
  function gamma($inputVal, $quantity, $yearToDate) {
    $importantValue1 = ($inputVal * $quantity) + $this->delta();
    $importantValue2 = ($inputVal * $yearToDate) + 100;
    if (($yearToDate - $importantValue1) > 100) {
      $importantValue2 -= 20;
    }
    $importantValue3 = $importantValue2 * 7;
    // and so on...
    return $importantValue3 - 2 * $importantValue1;
  }
  // ...
}
```

###

```
class Account {
  // ...
  function gamma($this->inputVal, $this->quantity, $this->yearToDate) {
    return new Gamma($this, $inputVal, $quantity, $yearToDate)->compute();
  }
  // ...
}

class Gamma {
  private $account; // Account
  private $this->importantValue1;
  private $this->importantValue2;
  private $this->importantValue3;
  private $this->inputVal;
  private $this->quantity;
  private $this->yearToDate;

  public Gamma(Account $source, $this->inputValArg, $this->quantityArg, $this->yearToDateArg) {
    $this->account = $source;
    $this->inputVal = $this->inputValArg;
    $this->quantity = $this->quantityArg;
    $this->yearToDate = $this->yearToDateArg;
  }
  public function compute() {
    $this->importantValue1 = ($this->inputVal * $this->quantity) + $this->account->delta();
    $this->importantValue2 = ($this->inputVal * $this->yearToDate) + 100;
    $this->importantThing();
    $this->importantValue3 = $this->importantValue2 * 7;
    // and so on...
    return $this->importantValue3 - 2 * $this->importantValue1;
  }
  private function importantThing() {
    if (($this->yearToDate - $this->importantValue1) > 100) {
      $this->importantValue2 -= 20;
    }
  }
}
```

###

Set step 1

# Для хорошего примера потребовалась бы целая глава, поэтому продемонстрируем этот рефакторинг на методе, которому он не нужен (не задавайте вопросов о логике этого метода – она была придумана по ходу дела).

Select name of "gamma"

# Мы видим, что в одном из методов класса есть множество запутанных вычислений и хитросплетение локальных переменных. Всё это затрудняет дальнейший рефакторинг класса.

# Давайте преобразуем этот метод в отдельный класс так, чтобы локальные переменные стали полями этого класса. После чего можно будет переместить данный метод в новый класс.

Go to the end of file

# Для начала создадим новый класс.

Print:
```


class Gamma {
}
```

Set step 2

# Первым делом создадим в классе <code>Gamma</code> неизменяемое поле для хранения исходного объекта.

Go to the end of "Gamma"

Print:
```

  private $account; // Account
```

Set step 3


Select 1st "$importantValue1"
+Select 1st "$importantValue2"
+Select 1st "$importantValue3"

# Также перенесем все переменные из метода, который мы хотим отделить...

Go to the end of "Gamma"

Print:
```

  private $importantValue1;
  private $importantValue2;
  private $importantValue3;
```

Select "gamma(|||$inputVal, $quantity, $yearToDate|||)"

# ...а также создадим поля для каждого из параметров метода.

Go to the end of "Gamma"

Print:
```

  private $inputVal;
  private $quantity;
  private $yearToDate;
```

Set step 4

Go to the end of "Gamma"

# Создадим конструктор, который будет принимать параметры метода и сохранять их в полях класса для дальнейшего использования.

Print:
```


  public Gamma(Account $source, $inputValArg, $quantityArg, $yearToDateArg) {
    $this->account = $source;
    $this->inputVal = $inputValArg;
    $this->quantity = $quantityArg;
    $this->yearToDate = $yearToDateArg;
  }
```

#C Запустим компиляцию, чтобы проверить код на наличие ошибок.

#S Все отлично, пока что ничего не успело сломаться.

Set step 5

Select whole "gamma" in "Account"

# Теперь можно переместить исходный метод, изменив его так, чтобы вместо переменных и параметров старого метода использовались поля.

Go to the end of "Gamma"

Print:
```

  public function compute() {
    $importantValue1 = ($inputVal * $quantity) + $this->delta();
    $importantValue2 = ($inputVal * $yearToDate) + 100;
    if (($yearToDate - $importantValue1) > 100) {
      $importantValue2 -= 20;
    }
    $importantValue3 = $importantValue2 * 7;
    // and so on...
    return $importantValue3 - 2 * $importantValue1;
  }
```

Select "$importantValue1"
Replace "$this->importantValue1"
Select "$importantValue2"
Replace "$this->importantValue2"
Select "$importantValue3"
Replace "$this->importantValue3"
Select "$inputVal"
Replace "$this->inputVal"
Select "$quantity"
Replace "$this->quantity"
Select "$yearToDate"
Replace "$this->yearToDate"


Select "delta()" in "compute"

# При этом следует модифицировать любые вызовы методов <code>Account</code> так, чтобы они выполнялись через поле <code>account</code>.

Print "account->delta()"

Set step 6

Select body of "gamma" in "Account"

# После этого осталось только заменить тело старого метода на вызов метода в новом классе.

Print:
```
    return new Gamma($this, $inputVal, $quantity, $yearToDate)->compute();
```

#C Запустим компиляцию и тесты, чтобы проверить код на наличие ошибок.

#S Все работает отлично!

Select:
```
    if (($this->yearToDate - $this->importantValue1) > 100) {
      $this->importantValue2 -= 20;
    }
```

# Преимущество это рефакторинга в том, что теперь можно легко применить <a href="/extract-method">извлечение метода</a> к методу <code>compute()</code>, не беспокоясь о передаче аргументов.

Go to the end of "Gamma"

Print:
```

  private function importantThing() {
    if (($this->yearToDate - $this->importantValue1) > 100) {
      $this->importantValue2 -= 20;
    }
  }
```

Wait 500ms

Select in "compute":
```
    if (($this->yearToDate - $this->importantValue1) > 100) {
      $this->importantValue2 -= 20;
    }
```

Replace "    $this->importantThing();"

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.