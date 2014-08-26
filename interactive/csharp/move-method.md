move-method:csharp

###

1. Проверьте все фичи, используемые старым методом в его же классе. Возможно, их тоже следует переместить.

2. Проверьте, не объявлен ли метод в суперклассах и подклассах. Если нет, объявите новый метод в классе-приёмнике и перенесите в него код старого метода.

3. Замените тело старого метода делгацией к новому методу.

4. Оцените, есть ли возможность полностью избавиться от старого метода.



###

```
public class Account
{
  // ...
  private AccountType type;
  private int daysOverdrawn;

  public double OverdraftCharge()
  {
    if (type.IsPremium())
    {
      double result = 10;
      if (daysOverdrawn > 7)
      {
        result += (daysOverdrawn - 7) * 0.85;
      }
      return result;
    }
    else
    {
      return daysOverdrawn * 1.75;
    }
  }
  public double BankCharge()
  {
    double result = 4.5;
    if (daysOverdrawn > 0)
    {
      result += OverdraftCharge();
    }
    return result;
  }
}

public class AccountType
{
  // ...
}
```

###

```
public class Account
{
  // ...
  private AccountType type;
  private int daysOverdrawn;

  public int DaysOverdrawn
  {
    get { return daysOverdrawn; }
  }

  public double BankCharge()
  {
    double result = 4.5;
    if (daysOverdrawn > 0)
    {
      result += type.OverdraftCharge(this);
    }
    return result;
  }
}

public class AccountType
{
  // ...
  public double OverdraftCharge(Account account)
  {
    if (IsPremium())
    {
      double result = 10;
      if (account.DaysOverdrawn > 7)
      {
        result += (account.DaysOverdrawn - 7) * 0.85;
      }
      return result;
    }
    else
    {
      return account.DaysOverdrawn * 1.75;
    }
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Перемещение метода</i> на примере класса банковского счета.

Select "private AccountType |||type|||"

# Представим себе, что будет введено несколько новых типов счетов со своими правилами начисления платы за овердрафт (превышение кредита).

# Я хочу переместить метод начисления этой оплаты внутрь класса, представляющего тип счета.

Select name of "OverdraftCharge"

# Прежде всего посмотрим, какие поля и методы использует <code>OverdraftCharge()</code>, и решим, следует ли переносить только его, или же надо будет перенести также и то, что с ним связано.

Select "private AccountType |||type|||"
+Select "type" in "OverdraftCharge"

# Поле <code>type</code> хранит тип счета, его нет смысла куда-то переносить.

Select "private int |||daysOverdrawn|||"
+Select "daysOverdrawn" in "OverdraftCharge"

# Поле <code>daysOverdrawn</code> тоже не стоит переносить, так как оно будет разным для отдельных счетов.

Select name of "OverdraftCharge"

# А значит будем переносить только метод <code>OverdraftCharge()</code>

Set step 2

Select whole "OverdraftCharge"

# Скопируем метод <code>OverdraftCharge()</code> в класс <code>AccountType</code>.

Go to the end of "AccountType"

Print:
```

  public double OverdraftCharge()
  {
    if (type.IsPremium())
    {
      double result = 10;
      if (daysOverdrawn > 7)
      {
        result += (daysOverdrawn - 7) * 0.85;
      }
      return result;
    }
    else
    {
      return daysOverdrawn * 1.75;
    }
  }
```

Select name of "OverdraftCharge" in "AccountType"

# Теперь метод необходимо отредактировать для правильной работы на новом месте.

Select "type." in "OverdraftCharge" of "AccountType"

#^= Первым делом удалим из метода поле <code>type</code>. Т.к. мы теперь находимся внутри класса, реализующего тип счета, и все методы можно вызывать из него напрямую.

Remove selected

Wait 500ms

Select "daysOverdrawn" in "OverdraftCharge" of "AccountType"

# Далее прорабатываем те поля и методы <code>Account</code>, которые остаются нужны. У нас таким полем является <code>daysOverdrawn</code>.

# В теории, если необходимо сохранить некоторый метод или поле исходного класса, то можно выбрать один из четырёх вариантов действия: <ol><li>Переместить это поле или метод в целевой класс.</li><li>Создать ссылку из целевого класса в исходный или воспользоваться уже имеющейся.</li><li>Передать экземпляр исходного класса в качестве параметра метода целевого класса.</li><li>Передать значение поля в виде параметра.</li></ol>

# В данном случае я передам значение поля как параметр...

Go to parameters of "OverdraftCharge" of "AccountType"

Print "int daysOverdrawn"

Select "daysOverdrawn" in "OverdraftCharge" of "AccountType"

# ...и буду использовать этот параметр в теле метода.

#C Запустим компиляцию, чтобы проверить код на наличие ошибок.

#S Всё отлично, можем продолжать!

Set step 3

Select body of "OverdraftCharge" in "Account"

# Теперь можно заменить тело исходного метода простым делегированием.

Print:
```
    return type.OverdraftCharge(daysOverdrawn);
```

#C Запустим компиляцию ещё раз на всякий случай.

#S Всё отлично, можем продолжать!

Set step 4

Select name of "OverdraftCharge" in "Account"

# Код в рабочем состоянии, поэтому мы можем пойти дальше и вообще удалить метод <code>OverdraftCharge()</code> из исходного класса.

Select "OverdraftCharge()" in "BankCharge"

# Но сперва нужно найти все места его вызова и выполнить в них переадресацию к методу в классе <code>AccountType</code>.

Print:
```
type.OverdraftCharge(daysOverdrawn)
```

Select whole "OverdraftCharge" in "Account"

# После перенаправления всех вызовов метода в новый класс, мы можем удалить объявление метода в классе <code>Account</code>.

# Обратите внимание, если перемещаемый метод не является приватным, необходимо посмотреть, не пользуются ли им другие классы. В строго типизированном языке, после удаления объявления метода в исходном классе, компиляция обнаружит всё, что могло быть пропущено. В остальных случаях помогут авто-тесты.

Remove selected

Select name in "Account"

# Можно ли на этом считать перемещение метода завершённым?<br/><br/>Не так быстро...

# Давайте рассмотрим ещё один нюанс. В данном случае метод обращался к единственному полю, поэтому я смог передать его значение в параметре. Если бы метод вызывал какой-то другой метод класса <code>Account</code>, то я не смог бы этого сделать. В таких ситуациях требуется передавать в параметрах экземпляр всего исходного класса. Давайте рассмотрим, как это могло быть реализовано.

Select parameters in "OverdraftCharge"

# Во-первых, в переносимый метод нужно передать экземпляр исходного класса.

Print:
```
Account account
```

Select "daysOverdrawn" in "OverdraftCharge"

# Во-вторых, все интересующие поля и методы теперь нужно брать напрямую из полученного экземпляра...

Print "account."

Wait 500ms

Select "|||private int daysOverdrawn|||"

# ...при этом необходимо учесть, что если поля приватные (а они всегда должны быть таковыми), то для доступа к ним надо создать соответствующие свойства.

Go to "int daysOverdrawn;|||"

Print:
```


  public int DaysOverdrawn
  {
    get { return daysOverdrawn; }
  }
```

Select "account." in "OverdraftCharge"

# После чего работаем с этими свойствами из экземпляра класса, переданного в параметрах метода.

Select "account.||||||" in "OverdraftCharge"

Replace "DaysOverdrawn"

Select "OverdraftCharge(|||daysOverdrawn|||)"

# И, наконец, в-третьих, во все вызовы метода необходимо добавить передачу текущего экземпляра класса <code>Account</code>.

Print "this"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.