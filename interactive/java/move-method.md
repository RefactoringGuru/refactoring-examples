move-method:java

###

1. Проверьте все фичи, используемые старым методом в его же классе. Возможно, их тоже следует переместить.

2. Проверьте, не объявлен ли метод в суперклассах и подклассах. Если нет, объявите новый метод в классе-приёмнике и перенесите в него код старого метода.

3. Замените тело старого метода делгацией к новому методу.

4. Оцените, есть ли возможность полностью избавиться от старого метода.



###

```
class Account {
  // ...
  private AccountType type;
  private int daysOverdrawn;
  
  double overdraftCharge() {
    if (type.isPremium()) {
      double result = 10;
      if (daysOverdrawn > 7) {
        result += (daysOverdrawn - 7) * 0.85;
      }
      return result;
    }
    else {
      return daysOverdrawn * 1.75;
    }
  }
  double bankCharge() {
    double result = 4.5;
    if (daysOverdrawn > 0) {
      result += overdraftCharge();
    }
    return result;
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
  private AccountType type;
  private int daysOverdrawn;
  
  double bankCharge() {
    double result = 4.5;
    if (daysOverdrawn > 0) {
      result += type.overdraftCharge(this);
    }
    return result;
  }
}
  
class AccountType {
  // ...
  double overdraftCharge(Account account) {
    if (isPremium()) {
      double result = 10;
      if (account.getDaysOverdrawn() > 7) {
        result += (account.getDaysOverdrawn() - 7) * 0.85;
      }
      return result;
    }
    else {
      return account.getDaysOverdrawn() * 1.75;
    }
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Перемещение метода</i> на примере класса банковского счета.

Select "private AccountType |||type|||"

# Представим себе, что будет введено несколько новых типов счетов со своими правилами начисления платы за овердрафт (превышение кредита).

# Я хочу переместить метод начисления этой оплаты в соответствующий тип счета.

# Прежде всего, посмотрим, какие поля и методы использует <code>overdraftCharge()</code>, и решим, следует ли переносить только этот метод или сразу всё связанное с ним.

# Поле <code>type</code> хранит тип счета, его нет смысла куда-то переносить.

Select "private int |||daysOverdrawn|||"

# Поле <code>daysOverdrawn</code> тоже не стоит переносить, так как оно будет разным для отдельных счетов.

Set step 2

Select whole "overdraftCharge"

# Теперь я могу скопировать метод <code>overdraftCharge()</code> в класс типа счета <code>AccountType</code>.

Go to the end of "AccountType"

Print:
```

  double overdraftCharge() {
    if (type.isPremium()) {
      double result = 10;
      if (daysOverdrawn > 7) {
        result += (daysOverdrawn - 7) * 0.85;
      }
      return result;
    }
    else {
      return daysOverdrawn * 1.75;
    }
  }
```

Select name of "overdraftCharge" in "AccountType"

# Теперь, метод необходимо подогнать для правильной работы на новом месте.

Select "type." in "overdraftCharge" of "AccountType"

# В данном случае подгонка означает удаление <code>type</code> из вызовов методов класса <code>Account</code>...

Remove selected

Select "daysOverdrawn" in "overdraftCharge" of "AccountType"


# ...и некоторые действия с теми полями и методами <code>Account</code>, которые все же нужны, как например, поле <code>daysOverdrawn</code>.

# В теории, если мне требуется некоторый метод или поле исходного класса, можно выбрать один из четырёх вариантов действия: <ol><li>Переместить это поле или метод в целевой класс.</li><li>Создать ссылку из целевого класса в исходный или воспользоваться уже имеющейся.</li><li>Передать исходный объект в качестве параметра метода целевого класса.</li><li>Передать значение поля в виде параметра.</li></ol>

# В данном случае я передам значение поля как параметр и буду использовать этот параметр в теле метода.

Go to parameters of "overdraftCharge" of "AccountType"

Print "int daysOverdrawn"

Select "daysOverdrawn" in "overdraftCharge" of "AccountType"

# ...и буду использовать этот параметр в теле метода.

#C Запустим компиляцию, чтобы проверить код на наличие ошибок.

#S Всё отлично, можем продолжать!

Set step 3

Select body of "overdraftCharge" in "Account"

# Теперь можно заменить тело исходного метода простым делегированием.

Print:
```
    return type.overdraftCharge(daysOverdrawn);
```

#C Запустим компиляцию ещё раз на всякий случай.

#S Всё отлично, можем продолжать!

Set step 4

Select name of "overdraftCharge" in "Account"

# Код в рабочем состоянии, поэтому мы можем пойти дальше и вообще удалить метод <code>overdraftCharge()</code> из исходного класса.

Select "overdraftCharge()" in "bankCharge"

# Но сперва нужно найти все места его вызова и выполнить в них переадресацию к методу в классе AccountType.

Print:
```
type.overdraftCharge(daysOverdrawn)
```

# Обратите внимание, если метод не является закрытым, необходимо посмотреть, не пользуются ли ним другие классы. В строго типизированном языке, после удаления объявления метода в исходном классе, компиляция обнаружит всё, что могло быть пропущено. В остальных случаях помогут авто-тесты.

Select whole "overdraftCharge" in "Account"

# После замены во всех точках вызова можно удалить объявление метода в <code>Account</code>.

Remove selected

Select name in "Account"

# Можно выполнять компилирование и тестирование после каждого удаления либо сделать это за один приём.

# Можно ли на этом считать перемещение метода завершённым?<br/><br/>Не так быстро...

# Давайте рассмотрим ещё один нюанс. В данном случае метод обращался к единственному полю, поэтому я смог передать его значение в параметре. Если бы метод вызывал какой-то другой метод класса <code>Account</code>, я не смог бы этого сделать. В таких ситуациях требуется передавать в параметрах весь исходный объект. Давайте рассмотрим как это могло быть реализовано.

Select parameters in "overdraftCharge"

# Во-первых, в переносимый метод нужно передавать весь исходный объект.

Print:
```
Account account
```

Select "daysOverdrawn" in "overdraftCharge"

# Во-вторых, все интересующие поля и методы теперь можно брать прямо из этого объекта.

Print:
```
account.getDaysOverdrawn()
```

Select "overdraftCharge(|||daysOverdrawn|||)"

# И, наконец, в-третьих, во все вызовы метода необходимо передавать объекты в класс <code>Account</code>.

Print "this"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.