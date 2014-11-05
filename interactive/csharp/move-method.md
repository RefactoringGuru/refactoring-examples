move-method:csharp

###

1.ru. Проверьте все фичи, используемые старым методом в его же классе. Возможно, их тоже следует переместить.
1.en. Check all features used by the old method in its class. It may be a good idea to move them as well.
1.uk. Перевірте усі фічі, які були використані старим методом в його ж класі. Можливо, їх теж слід перемістити.

2.ru. Проверьте, не объявлен ли метод в суперклассах и подклассах. Если нет, объявите новый метод в классе-приёмнике и перенесите в него код старого метода.
2.en. Check whether the method has been declared in superclasses and subclasses. If not, declare a new method in the recipient class and move the code of the old method to it.
2.uk. Перевірте, чи не оголошений метод в суперкласах та підкласах. Якщо ні, оголосіть новий метод в класі-приймачі і перенесіть в нього код старого методу.

3.ru. Замените тело старого метода делгацией к новому методу.
3.en. Replace the body of the old method with delegation to the new method.
3.uk. Замініть тіло старого методу делгаціей до нового методу.

4.ru. Оцените, есть ли возможность полностью избавиться от старого метода.
4.en. Check whether you can get rid of the old method entirely.
4.uk. Оцініть, чи є можливість повністю позбутися від старого методу.



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

#|ru| Давайте рассмотрим <i>Перемещение метода</i> на примере класса банковского счета.
#|en| Let's look at <i>Move Method</i> using a bank account class as an example.
#|uk| Давайте розглянемо <i>Переміщення методу<i> на прикладі класу банківського рахунку.

Select "private AccountType |||type|||"

#|ru| Представим себе, что будет введено несколько новых типов счетов со своими правилами начисления платы за овердрафт (превышение кредита).
#|en| Say that there will be several new account types, and each account type will have different rules for how to calculate overdraft fees, when the bank's customer attempts to spend more money than is available.
#|uk| Уявімо собі, що буде введено кілька нових типів рахунків зі своїми правилами нарахування плати за овердрафт (перевищення кредиту).

#|ru| Мы хотим переместить метод начисления этой оплаты внутрь класса, представляющего тип счета.
#|en| We want to move the method for calculating this overdraft fee to inside the class that represents the account type.
#|uk| Ми хочемо перемістити метод нарахування цієї оплати всередину класу, що представляє тип рахунку.

Select name of "OverdraftCharge"

#|ru| Прежде всего, посмотрим, какие поля и методы использует <code>OverdraftCharge()</code>, и решим, следует ли переносить только его, или же надо будет перенести также и то, что с ним связано.
#|en| First see which fields and methods are used by <code>OverdraftCharge()</code> and decide whether you can move only it – or else move it and everything related to it as well.
#|uk| Перш за все, подивимося, які поля і методи використовує <code>OverdraftCharge()</code>, і вирішимо, чи слід переносити тільки його, або ж треба буде перенести також і те, що з ним пов'язано.

Select "private AccountType |||type|||"
+Select "type" in "OverdraftCharge"

#|ru| Поле <code>type</code> хранит тип счета, его нет смысла куда-то переносить.
#|en| The <code>type</code> field stores the account type. There is no reason to move it anywhere else.
#|uk| Поле <code>type</code> зберігає тип рахунку, його немає сенсу кудись переносити.

Select "private int |||daysOverdrawn|||"
+Select "daysOverdrawn" in "OverdraftCharge"

#|ru| Поле <code>daysOverdrawn</code> тоже не стоит переносить, так как оно будет разным для отдельных счетов.
#|en| Moving the <code>daysOverdrawn</code> field would not make sense either, since it is different for different accounts.
#|uk| Поле <code>daysOverdrawn</code> теж не варто переносити, так як воно буде відрізнятися для окремих рахунків.

Select name of "OverdraftCharge"

#|ru| А, значит, будем переносить только метод <code>OverdraftCharge()</code>
#|en| So we will move only the <code>OverdraftCharge()</code> method.
#|uk| А це означає, що будемо переносити тільки метод <code>OverdraftCharge()</code>

Set step 2

Select whole "OverdraftCharge"

#|ru| Скопируем метод <code>OverdraftCharge()</code> в класс <code>AccountType</code>.
#|en| Copy the <code>OverdraftCharge()</code> method to the <code>AccountType</code> class.
#|uk| Скопіюємо метод <code>OverdraftCharge()</code> в клас <code>AccountType</code>.

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

#|ru| Теперь метод необходимо отредактировать для правильной работы на новом месте.
#|en| Now edit the method so that it will function correctly in its new location.
#|uk| Тепер метод необхідно відредагувати для правильної роботи на новому місці.

Select "type." in "OverdraftCharge" of "AccountType"

#|ru|^= Первым делом удалим из метода поле <code>type</code>, т.к. мы теперь находимся внутри класса, реализующего тип счета, и все методы можно вызывать из него напрямую.
#|en|^= First remove the <code>type</code> field from the method, since we are now inside the class that implements the account type and all methods can be called from it directly.
#|uk|^= Першим ділом видалимо з методу поле <code>type</code>, так як ми тепер перебуваємо всередині класу, що реалізовує тип рахунку, і всі методи можна викликати з нього безпосередньо.

Remove selected

Wait 500ms

Select "daysOverdrawn" in "OverdraftCharge" of "AccountType"

#|ru| Далее прорабатываем те поля и методы <code>Account</code>, которые остаются нужны. У нас таким полем является <code>daysOverdrawn</code>.
#|en| Now go through the fields and methods of <code>Account</code> that you need. In our case, this would be the <code>daysOverdrawn</code> field.
#|uk| Далі опрацьовуємо ті поля та методи <code>Account</code>, які будуть потрібні і надалі. У нас таким полем є <code>daysOverdrawn</code>.

#|ru| В теории, если необходимо сохранить некоторый метод или поле исходного класса, то можно выбрать один из четырёх вариантов действия: <ol><li>Переместить это поле или метод в целевой класс.</li><li>Создать ссылку из целевого класса в исходный или воспользоваться уже имеющейся.</li><li>Передать экземпляр исходного класса в качестве параметра метода целевого класса.</li><li>Передать значение поля в виде параметра.</li></ol>
#|en| In theory, there are four options for saving a method or field of the original class: <ol><li>Move the field or method to the target class.</li><li>Create a reference from the target class to the original one or restore the previously existing one.</li><li>Pass an instance of the original class as a parameter of the target class method.</li><li>Pass the field value as a parameter.</li></ol>
#|uk| У теорії, якщо необхідно зберегти деякий метод або поле вихідного класу, то можна вибрати один з чотирьох варіантів дії: <ol> <li> Перемістити це поле або метод в цільовий клас. </ Li> <li> Створити посилання з цільового класу до вихідного або скористатися вже наявним осиланням. </ li> <li> Надіслати примірник вихідного класу як параметр методу цільового класу. </ li> <li> Надіслати значення поля у вигляді параметра. </ li> </ ol>

#|ru| В данном случае мы передаем значение поля как параметр...
#|en| In this case, we will pass the field value as a parameter…
#|uk| В даному випадку ми передаємо значення поля як параметр...

Go to parameters of "OverdraftCharge" of "AccountType"

Print "int daysOverdrawn"

Select "daysOverdrawn" in "OverdraftCharge" of "AccountType"

#|ru| ...и будем использовать этот параметр в теле метода.
#|en| …and use this parameter in the method body.
#|uk| ...і будемо використовувати цей параметр в тілі методу.

#C|ru| Запустим компиляцию, чтобы проверить код на наличие ошибок.
#S Всё отлично, можем продолжать!

#C|en| Let's compile and test to check for errors in your code.
#S Everything is OK! We can keep going.

#C|uk| Запустимо компіляцію, щоб перевірити код на наявність помилок.
#S Все добре, можемо продовжувати.

Set step 3

Select body of "OverdraftCharge" in "Account"

#|ru| Теперь можно заменить тело исходного метода простым делегированием.
#|en| Now we can replace the body of the original method with simple delegation.
#|uk| Тепер можна замінити тіло вихідного методу простим делегуванням.

Print:
```
    return type.OverdraftCharge(daysOverdrawn);
```

#C|ru| Запустим компиляцию ещё раз на всякий случай.
#S Всё отлично, можем продолжать!

#C|en| Let's compile again just to be safe.
#S Everything is OK! We can keep going.

#C|uk| Запустимо компіляцію ще раз на всякий випадок.
#S Все добре, можемо продовжувати.

Set step 4

Select name of "OverdraftCharge" in "Account"

#|ru| Код в рабочем состоянии, поэтому мы можем пойти дальше и вообще удалить метод <code>OverdraftCharge()</code> из исходного класса.
#|en| The code is functioning, so we can proceed and entirely remove the <code>OverdraftCharge()</code> method from the original class.
#|uk| Код в робочому стані, тому ми можемо піти далі і взагалі видалити метод <code>OverdraftCharge()</code> з вихідного класу.

Select "OverdraftCharge()" in "BankCharge"

#|ru| Но сначала нужно найти все места его вызова и выполнить в них переадресацию к методу в классе <code>AccountType</code>.
#|en| But first, find all places where it is called and readdress these to the method in the <code>AccountType</code> class.
#|uk| Але спочатку потрібно знайти всі місця його виклику і виконати в них переадресацію до методу в класі <code>AccountType</code>.

Print:
```
type.OverdraftCharge(daysOverdrawn)
```

Select whole "OverdraftCharge" in "Account"

#|ru| После перенаправления всех вызовов метода в новый класс, мы можем удалить объявление метода в классе <code>Account</code>.
#|en| After redirecting all method calls to the new class, we can entirely remove the method declaration in the <code>Account</code> class.
#|uk| Після перенаправлення всіх викликів методу в новий клас, ми можемо видалити оголошення методу в класі <code>Account</code>.

#|ru| Обратите внимание, если перемещаемый метод не является приватным, необходимо посмотреть, не пользуются ли им другие классы. В строго типизированном языке после удаления объявления метода в исходном классе компиляция обнаружит всё, что могло быть пропущено. В остальных случаях помогут автотесты.
#|en| If the moved method is not private, make sure that other classes are not using it. In a strongly typed language, after a method declaration in the original class is removed, compilation will uncover everything that may have been skipped. In other languages, autotests will assist.
#|uk| Зверніть увагу, якщо переміщуваний метод не є приватним, необхідно подивитися, чи не користуються їм інші класи. В строго типізованій мові після видалення оголошення методу у вихідному класі компіляція виявить все, що могло бути пропущено. В інших випадках допоможуть автотести.

Remove selected

Select name in "Account"

#|ru| Можно ли на этом считать перемещение метода завершённым?<br/><br/>Не так быстро...
#|en| So can we say that we are done moving the method?<br/><br/>Not quite…
#|uk| Чи можна на цьому вважати переміщення методу завершеним?<br/><br/>Не так швидко...

#|ru| Давайте рассмотрим ещё один нюанс. В данном случае метод обращался к единственному полю, поэтому мы смогли передать его значение в параметре. Если бы метод вызывал какой-то другой метод класса <code>Account</code>, то нам не удалось бы это сделать. В таких ситуациях требуется передавать в параметрах экземпляр всего исходного класса. Давайте рассмотрим, как это могло быть реализовано.
#|en| Let's look at one nuance. In this case, the method referred to a single field, because of which we could pass its value in a parameter. If the method had called any other method of the <code>Account</code> class, we would not be able to do this. In such situations, you must pass an instance of the entire original class in the parameters. Here are all the details of how to implement this.
#|uk| Давайте розглянемо ще один нюанс. В даному випадку метод звертався до єдиного поля, тому ми змогли передати його значення в параметрі. Якби метод викликав якийсь інший метод класу <code>Account</code>, то нам не вдалося б це зробити. В таких ситуаціях потрібно передавати в параметрах примірник всього вихідного класу. Давайте розглянемо, як це могло б бути реалізоване.

Select parameters in "OverdraftCharge"

#|ru| Во-первых, в переносимый метод нужно передать экземпляр исходного класса.
#|en| First, pass an instance of the original class to the method being moved.
#|uk| По-перше, у метод,який переноситься, потрібно передати примірник вихідного класу.

Print:
```
Account account
```

Select "daysOverdrawn" in "OverdraftCharge"

#|ru| Во-вторых, все интересующие поля и методы теперь нужно брать напрямую из полученного экземпляра.
#|en| In addition, all fields and methods of interest should now be taken directly from the instance received.
#|uk| По-друге, всі питання, що цікавлять поля і методи тепер потрібно брати безпосередньо з отриманого примірника...

Print "account."

Wait 500ms

Select "|||private int daysOverdrawn|||"

#|ru| ...при этом необходимо учесть, что если поля приватные (а они всегда должны быть таковыми), то для доступа к ним надо создать соответствующие свойства.
#|en| …and remember that if the fields are private (which they always should be), you need to create the relevant properties so that they can be accessed.
#|uk| ...при цьому необхідно врахувати, що якщо поля приватні (а вони завжди повинні бути такими), то для доступу до них треба створити відповідні властивості.

Go to "int daysOverdrawn;|||"

Print:
```


  public int DaysOverdrawn
  {
    get { return daysOverdrawn; }
  }
```

Select "account." in "OverdraftCharge"

#|ru| После чего работаем с этими свойствами из экземпляра класса, переданного в параметрах метода.
#|en| Then work with these properties from the instance of the class passed in the method parameters.
#|uk| Після чого працюємо з цими властивостями з екземпляра класу,який був переданий в параметрах методу.

Select "account.||||||" in "OverdraftCharge"

Replace "DaysOverdrawn"

Select "OverdraftCharge(|||daysOverdrawn|||)"

#|ru| И, наконец, в-третьих, во все вызовы метода необходимо добавить передачу текущего экземпляра класса <code>Account</code>.
#|en| And third, you must add passing of the current instance of the <code>Account</code> class to all method calls.
#|uk| І, нарешті, по-третє, в усі виклики методу необхідно додати передачу поточного екземпляра класу <code>Account</code>.

Print "this"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's run the final compile.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.