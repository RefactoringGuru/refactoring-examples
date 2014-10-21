move-method:php

###

1. Проверьте все фичи, используемые старым методом в его же классе. Возможно, их тоже следует переместить.

2. Проверьте, не объявлен ли метод в суперклассах и подклассах. Если нет, объявите новый метод в классе-приёмнике и перенесите в него код старого метода.

3. Замените тело старого метода делгацией к новому методу.

4. Оцените, есть ли возможность полностью избавиться от старого метода.



###

```
class Account {
  // ...
  private $type; // AccountType
  private $daysOverdrawn;

  public function overdraftCharge() {
    if ($this->type->isPremium()) {
      $result = 10;
      if ($this->daysOverdrawn > 7) {
        $result += ($this->daysOverdrawn - 7) * 0.85;
      }
      return $result;
    }
    else {
      return $this->daysOverdrawn * 1.75;
    }
  }
  public function bankCharge() {
    $result = 4.5;
    if ($this->daysOverdrawn > 0) {
      $result += $this->overdraftCharge();
    }
    return $result;
  }
}

class AccountType {
  // ...
}
```

###

```
class Account {
  // ...
  private $type; // AccountType
  private $daysOverdrawn;

  public function bankCharge() {
    $result = 4.5;
    if ($this->daysOverdrawn > 0) {
      $result += $this->type->overdraftCharge($this);
    }
    return $result;
  }
}

class AccountType {
  // ...
  public function overdraftCharge(Account $account) {
    if ($this->isPremium()) {
      $result = 10;
      if ($account->getDaysOverdrawn() > 7) {
        $result += ($account->getDaysOverdrawn() - 7) * 0.85;
      }
      return $result;
    }
    else {
      return $account->getDaysOverdrawn() * 1.75;
    }
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Перемещение метода</i> на примере класса банковского счета.

Select "private |||$type|||"

# Представим себе, что будет введено несколько новых типов счетов со своими правилами начисления платы за овердрафт (превышение кредита).

# Мы хотим переместить метод начисления этой оплаты внутрь класса, представляющего тип счета.

Select name of "OverdraftCharge"

# Прежде всего, посмотрим, какие поля и методы использует <code>OverdraftCharge()</code>, и решим, следует ли переносить только его, или же надо будет перенести также и то, что с ним связано.

Select "private |||$type|||"

# Поле <code>type</code> хранит тип счета, его нет смысла куда-то переносить.

Select "private |||$daysOverdrawn|||"

# Поле <code>daysOverdrawn</code> тоже не стоит переносить, так как оно будет разным для отдельных счетов.

Select name of "OverdraftCharge"

# А, значит, будем переносить только метод <code>overdraftCharge()</code>

Set step 2

Select whole "overdraftCharge"

# Скопируем метод <code>overdraftCharge()</code> в класс <code>AccountType</code>.

Go to the end of "AccountType"

Print:
```

  public function overdraftCharge() {
    if ($this->type->isPremium()) {
      $result = 10;
      if ($this->daysOverdrawn > 7) {
        $result += ($this->daysOverdrawn - 7) * 0.85;
      }
      return $result;
    }
    else {
      return $this->daysOverdrawn * 1.75;
    }
  }
```

Select name of "overdraftCharge" in "AccountType"

# Теперь метод необходимо отредактировать для правильной работы на новом месте.

Select "type->" in "overdraftCharge" of "AccountType"

# Первым делом удалим из метода поле <code>type</code>, т.к. мы теперь находимся внутри класса, реализующего тип счета, и все методы можно вызывать из него напрямую.

Remove selected

Select "daysOverdrawn" in "overdraftCharge" of "AccountType"


# Далее прорабатываем те поля и методы <code>Account</code>, которые остаются нужны. У нас таким полем является <code>daysOverdrawn</code>.

# В теории, если необходимо сохранить некоторый метод или поле исходного класса, то можно выбрать один из четырёх вариантов действия: <ol><li>Переместить это поле или метод в целевой класс.</li><li>Создать ссылку из целевого класса в исходный или воспользоваться уже имеющейся.</li><li>Передать экземпляр исходного класса в качестве параметра метода целевого класса.</li><li>Передать значение поля в виде параметра.</li></ol>

# В данном случае мы передаем значение поля как параметр...

Go to parameters of "overdraftCharge" of "AccountType"

Print "$daysOverdrawn"

Select "$this->daysOverdrawn" in "overdraftCharge" of "AccountType"

# ...и будем использовать этот параметр в теле метода.

Replace "$daysOverdrawn"

#C Запустим тесты, чтобы проверить код на наличие ошибок.

#S Всё отлично, можем продолжать!

Set step 3

Select body of "overdraftCharge" in "Account"

# Теперь можно заменить тело исходного метода простым делегированием.

Print:
```
    return $this->type->overdraftCharge($this->daysOverdrawn);
```

#C Запустим тесты ещё раз на всякий случай.

#S Всё отлично, можем продолжать!

Set step 4

Select name of "overdraftCharge" in "Account"

# Код в рабочем состоянии, поэтому мы можем пойти дальше и вообще удалить метод <code>overdraftCharge()</code> из исходного класса.

Select "overdraftCharge()" in "bankCharge"

# Но сначала нужно найти все места его вызова и выполнить в них переадресацию к методу в классе <code>AccountType</code>.

Print:
```
type->overdraftCharge($this->daysOverdrawn)
```

# Обратите внимание, если перемещаемый метод не является приватным, необходимо посмотреть, не пользуются ли им другие классы. В строго типизированном языке после удаления объявления метода в исходном классе авто-тесты обнаружат всё, что могло быть пропущено. В остальных случаях помогут автотесты.

Select whole "overdraftCharge" in "Account"

# После перенаправления всех вызовов метода в новый класс, мы можем удалить объявление метода в классе <code>Account</code>.

Remove selected

Select name in "Account"

# Можно выполнять тестирование после каждого удаления либо сделать это за один приём.

# Можно ли на этом считать перемещение метода завершённым?<br/><br/>Не так быстро...

# Давайте рассмотрим ещё один нюанс. В данном случае метод обращался к единственному полю, поэтому мы смогли передать его значение в параметре. Если бы метод вызывал какой-то другой метод класса <code>Account</code>, то нам не удалось бы это сделать. В таких ситуациях требуется передавать в параметрах экземпляр всего исходного класса. Давайте рассмотрим, как это могло быть реализовано.

Select parameters in "overdraftCharge"

# Во-первых, в переносимый метод нужно передать экземпляр исходного класса.

Print:
```
Account $account
```

Select "$daysOverdrawn" in "overdraftCharge"

# Во-вторых, все интересующие поля и методы теперь нужно брать напрямую из полученного экземпляра.

Print:
```
$account->getDaysOverdrawn()
```

Select "overdraftCharge(|||$this->daysOverdrawn|||)"

# И, наконец, в-третьих, во все вызовы метода необходимо добавить передачу текущего экземпляра класса <code>Account</code>.

Print "$this"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.