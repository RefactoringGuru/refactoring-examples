replace-method-with-method-object:php

###

1.ru. Создайте новый класс. Дайте ему название, основываясь на предназначении метода, который рефакторите.

1.en. Create a new class. Name it based on the purpose of the method that you are refactoring.

1.uk. Створіть новий клас. Дайте йому назву, ґрунтуючись на призначенні методу, над яким проводиться рефакторинг.

2.ru. В новом классе создайте приватное поле для хранения ссылки на экземпляр класса, в котором раньше находился метод.

2.en. In the new class, create a private field for storing a reference to an instance of the class in which the method was previously located.

2.uk. У новому класі створіть приватне поле для зберігання посилання на екземпляр класу, в якому раніше знаходився метод.

3.ru. Кроме того, создайте по приватному полю для каждой локальной переменной и параметра метода.

3.en. In addition, create a private field for each local variable and parameter of the method.

3.uk. Крім того, створіть по приватному полю для кожної локальної змінної і параметра методу.

4.ru. Создайте конструктор, который принимает все параметры исходного метода и инициализирует соответствующие приватные поля.

4.en. Create a constructor that accepts all parameters of the original method and initializes the relevant private fields.

4.uk. Створіть конструктор, який приймає всі параметри вихідного методу та ініціалізує відповідні приватні поля.

5.ru. Объявите основной метод и скопируйте в него код оригинального метода, заменив локальные переменные приватным полями.

5.en. Declare the main method and copy the code of the original method to it, replacing the local variables with private fields.

5.uk. Оголосіть основний метод і скопіюйте в нього код оригінального методу, замінивши локальні змінні приватними полями.

6.ru. Замените тело оригинального метода в исходном классе созданием объекта-метода и вызовом его основного метода.

6.en. Replace the body of the original method in the original class by creating a method object and calling its main method.

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
    // and so on…
    return $importantValue3 - 2 * $importantValue1;
  }
  // ...
}
```

###

```
class Account {
  // ...
  function gamma($inputVal, $quantity, $yearToDate) {
    return (new Gamma($this, $inputVal, $quantity, $yearToDate))->compute();
  }
  // ...
}

class Gamma {
  private $account; // Account
  private $importantValue1;
  private $importantValue2;
  private $importantValue3;
  private $inputVal;
  private $quantity;
  private $yearToDate;

  public function __construct(Account $source, $inputValArg, $quantityArg, $yearToDateArg) {
    $this->account = $source;
    $this->inputVal = $inputValArg;
    $this->quantity = $quantityArg;
    $this->yearToDate = $yearToDateArg;
  }
  public function compute() {
    $this->importantValue1 = ($this->inputVal * $this->quantity) + $this->account->delta();
    $this->importantValue2 = ($this->inputVal * $this->yearToDate) + 100;
    $this->importantThing();
    $this->importantValue3 = $this->importantValue2 * 7;
    // and so on…
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
#|en| A thorough example would require an entire chapter, so we will demonstrate this refactoring on a method that does not require it (for this reason, it's better not to question the logic of the method – it was devised without any grand plan in mind).
#|uk| Для доброго прикладу потрібна була б ціла глава, тому продемонструємо цей рефакторинг на методі, якому він не потрібен (не ставте запитань про логіку цього методу – вона була придумана з розвитку справи.).

Select name of "gamma"

#|ru| Мы видим, что в одном из методов класса есть множество запутанных вычислений и хитросплетение локальных переменных. Всё это затрудняет дальнейший рефакторинг класса.
#|en| We see that one of the class methods has many sophisticated calculations and entanglement of local variables. All this makes it hard to refactor the class.
#|uk| Ми бачимо, що в одному з методів класу є безліч заплутаних обчислень і хитросплетіння локальних змінних. Все це ускладнює подальший рефакторинг класу.

#|ru| Давайте преобразуем этот метод в отдельный класс так, чтобы локальные переменные стали полями этого класса.
#|en| Let's convert this method to a separate class so that the local variables become fields of the class. That will isolate it and ease the further refactoring.
#|uk| Давайте перетворимо цей метод в окремий клас так, щоб локальні змінні стали полями цього класу.

Go to the end of file

#|ru| Для начала создадим новый класс.
#|en| So, let's create a new class.
#|uk| Для початку створимо новий клас.

Print:
```


class Gamma {
}
```

Set step 2

#|ru| Первым делом создадим в классе <code>Gamma</code> неизменяемое поле для хранения исходного объекта.
#|en| First, create an immutable field for storing the source object, in the <code>Gamma</code> class.
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

#|ru| Также перенесем все переменные из метода, который мы хотим отделить…
#|en| Move all variables from the method that we want to separate…
#|uk| Також перенесемо всі змінні з методу, який ми хочемо відокремити…

Go to the end of "Gamma"

Print:
```

  private $importantValue1;
  private $importantValue2;
  private $importantValue3;
```

Select "gamma(|||$inputVal, $quantity, $yearToDate|||)"

#|ru| …а также создадим поля для каждого из параметров метода.
#|en| …and create fields for each of the method's parameters.
#|uk| …та створимо поля для кожного з параметрів методу

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
#|en| Create a constructor that will accept the method's parameters and store them in class fields for further use.
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

#C|en| Let's launch autotests to check for errors in code.
#S All is well, nothing has managed to break yet.

#C|uk| Запустимо тести, щоб перевірити код на наявність помилок.
#S Все добре, поки що нічого не встигло зламатися.

Set step 5

Select whole "gamma" in "Account"

#|ru| Теперь можно переместить исходный метод, изменив его так, чтобы вместо переменных и параметров старого метода использовались поля.
#|en| Move the original method, changing it so that fields are used instead of the variables and parameters of the old method.
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
    // and so on…
    return $importantValue3 - 2 * $importantValue1;
  }
```

Select 1st "$importantValue1" in "compute"

#|ru| Избавимся от локальных переменных, заменив их на поля.
#|en| It's time to replace local variables with fields.
#|uk| Позбавимось від локальних змінних, замінивши їх на поля.

Select "$importantValue1" in "compute"
Replace "$this->importantValue1"
Select "$importantValue2" in "compute"
Replace "$this->importantValue2"
Select "$importantValue3" in "compute"
Replace "$this->importantValue3"
Select "$inputVal" in "compute"
Replace "$this->inputVal"
Select "$quantity" in "compute"
Replace "$this->quantity"
Select "$yearToDate" in "compute"
Replace "$this->yearToDate"

Select "delta()" in "compute"

#|ru| При этом следует модифицировать любые вызовы методов <code>Account</code> так, чтобы они выполнялись через поле <code>account</code>.
#|en| Modify any calls to the <code>Account</code> methods so that they are run via the <code>account</code> field.
#|uk| При цьому слід модифікувати будь-які виклики методів <code>Account</code> так, щоб вони виконувалися через поле <code>account</code>.

Print "account->delta()"

Set step 6

Select body of "gamma" in "Account"

#|ru| После этого осталось только заменить тело старого метода на вызов метода в новом классе.
#|en| Then simply replace the body of the old method with a call to the method in the new class.
#|uk| Після цього залишилося тільки замінити тіло старого методу на виклик методу в новому класі.

Print:
```
    return (new Gamma($this, $inputVal, $quantity, $yearToDate))->compute();
```

#C|ru| Запустим тесты, чтобы проверить код на наличие ошибок.
#S Все работает отлично!

#C|en| Let's launch autotests to check for errors in code.
#S Everything is working great!

#C|uk| Запустимо тести, щоб перевірити код на наявність помилок.
#S Все працює добре.

Select:
```
    if (($this->yearToDate - $this->importantValue1) > 100) {
      $this->importantValue2 -= 20;
    }
```

#|ru| Преимущество это рефакторинга в том, что теперь можно легко применить <a href="/ru/extract-method">извлечение метода</a> к методу <code>compute()</code>, не беспокоясь о передаче аргументов в подметоды.
#|en| The benefit of this refactoring is that you can now easily apply <a href="/extract-method">Extract Method</a> to the <code>compute()</code> method without worrying about passing correct arguments between sub-methods.
#|uk| Перевага цього рефакторинга в тому, що тепер можна легко застосувати <a href="/uk/extract-method">відокремлення методу</a> до методу <code>compute()</code>, не турбуючись про передачу аргументів в субметоди.

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
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.