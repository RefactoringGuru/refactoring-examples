replace-method-with-method-object:php

###

1.ru. Создайте новый класс. Дайте ему название, основываясь на предназначении метода, который рефакторите.
1.uk. Створіть новий клас. Дайте йому назву, ґрунтуючись на призначенні методу, над яким проводиться рефакторінг.

2.ru. В новом классе создайте приватное поле для хранения ссылки на экземпляр класса, в котором раньше находился метод.
2.uk. У новому класі створіть приватне поле для зберігання посилання на екземпляр класу, в якому раніше знаходився метод.

3.ru. Кроме того, создайте по приватному полю для каждой локальной переменной и параметра метода.
3.uk. Крім того, створіть по приватному полю для кожної локальної змінної і параметра методу.

4.ru. Создайте конструктор, который принимает все параметры исходного метода и инициализирует соответствующие приватные поля.
4.uk. Створіть конструктор, який приймає всі параметри вихідного методу та ініціалізує відповідні приватні поля.

5.ru. Объявите основной метод и скопируйте в него код оригинального метода, заменив локальные переменные приватным полями.
5.uk. Оголосіть основний метод і скопіюйте в нього код оригінального методу, замінивши локальні змінні приватними полями.

6.ru. Замените тело оригинального метода в исходном классе созданием объекта-метода и вызовом его основного метода.
6.uk. Замініть тіло оригінального методу в початковому класі створенням об'єкту-методу і викликом його основного методу.



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

#|ru| Для хорошего примера потребовалась бы целая глава, поэтому продемонстрируем этот рефакторинг на методе, которому он не нужен (не задавайте вопросов о логике этого метода – она была придумана по ходу дела).
#|uk| Для доброго прикладу потрібна була б ціла глава, тому продемонструємо цей рефакторинг на методі, якому він не потрібен (не ставте запитань про логіку цього методу – вона була придумана з розвитку справи.).

Select name of "gamma"

#|ru| Мы видим, что в одном из методов класса есть множество запутанных вычислений и хитросплетение локальных переменных. Всё это затрудняет дальнейший рефакторинг класса.
#|uk| Ми бачимо, що в одному з методів класу є безліч заплутаних обчислень і хитросплетіння локальних змінних. Все це ускладнює подальший рефакторинг класу.

#|ru| Давайте преобразуем этот метод в отдельный класс так, чтобы локальные переменные стали полями этого класса. После чего можно будет переместить данный метод в новый класс.
#|uk| Давайте перетворимо цей метод в окремий клас так, щоб локальні змінні стали полями цього класу. Після чого можна буде перемістити даний метод в новий клас.

Go to the end of file

#|ru| Для начала создадим новый класс.
#|uk| Для початку створимо новий клас.

Print:
```


class Gamma {
}
```

Set step 2

#|ru| Первым делом создадим в классе <code>Gamma</code> неизменяемое поле для хранения исходного объекта.
#|uk| Першим ділом створимо в класі <code>Gamma</code> незмінне поле для зберігання вихідного об'єкта.

Go to the end of "Gamma"

Print:
```

  private $account; // Account
```

Set step 3


Select 1st "$importantValue1"
+Select 1st "$importantValue2"
+Select 1st "$importantValue3"

#|ru| Также перенесем все переменные из метода, который мы хотим отделить...
#|uk| Також перенесемо всі змінні з методу, який ми хочемо відокремити...

Go to the end of "Gamma"

Print:
```

  private $importantValue1;
  private $importantValue2;
  private $importantValue3;
```

Select "gamma(|||$inputVal, $quantity, $yearToDate|||)"

#|ru| ...а также создадим поля для каждого из параметров метода.
#|uk| ...та створимо поля для кожного з параметрів методу

Go to the end of "Gamma"

Print:
```

  private $inputVal;
  private $quantity;
  private $yearToDate;
```

Set step 4

Go to the end of "Gamma"

#|ru| Создадим конструктор, который будет принимать параметры метода и сохранять их в полях класса для дальнейшего использования.
#|uk| Створимо конструктор, який прийматиме параметри методу і зберігатиме їх в полях класу для подальшого використання.

Print:
```


  public Gamma(Account $source, $inputValArg, $quantityArg, $yearToDateArg) {
    $this->account = $source;
    $this->inputVal = $inputValArg;
    $this->quantity = $quantityArg;
    $this->yearToDate = $yearToDateArg;
  }
```

#C|ru| Запустим тесты, чтобы проверить код на наличие ошибок.
#S Все отлично, пока что ничего не успело сломаться.

#C|uk| Запустимо тести, щоб перевірити код на наявність помилок.
#S Все добре, поки що нічого не встигло зламатися.

Set step 5

Select whole "gamma" in "Account"

#|ru| Теперь можно переместить исходный метод, изменив его так, чтобы вместо переменных и параметров старого метода использовались поля.
#|uk| Тепер можна перемістити основний метод, змінивши його так, щоб замість змінних і параметрів старого методу використовувалися поля класу.

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

#|ru| При этом следует модифицировать любые вызовы методов <code>Account</code> так, чтобы они выполнялись через поле <code>account</code>.
#|uk| При цьому слід модифікувати будь-які виклики методів <code>Account</code> так, щоб вони виконувалися через поле <code>account</code>.

Print "account->delta()"

Set step 6

Select body of "gamma" in "Account"

#|ru| После этого осталось только заменить тело старого метода на вызов метода в новом классе.
#|uk| Після цього залишилося тільки замінити тіло старого методу на виклик методу в новому класі.

Print:
```
    return new Gamma($this, $inputVal, $quantity, $yearToDate)->compute();
```

#C|ru| Запустим тесты, чтобы проверить код на наличие ошибок.
#S Все работает отлично!

#C|uk| Запустимо тести, щоб перевірити код на наявність помилок.
#S Все працює добре.

Select:
```
    if (($this->yearToDate - $this->importantValue1) > 100) {
      $this->importantValue2 -= 20;
    }
```

#|ru| Преимущество это рефакторинга в том, что теперь можно легко применить <a href="/extract-method">извлечение метода</a> к методу <code>compute()</code>, не беспокоясь о передаче аргументов.
#|uk| Перевага цього рефакторінга в тому, що тепер можна легко застосувати <a href="/extract-method">відокремлення методу</a> до методу <code>compute()</code>, не турбуючись про передачу аргументів.

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

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.