extract-class:php

###

1.ru. Создайте новый класс, который будет содержать выделенную функциональность.
1.en. Create a new class to contain the relevant functionality.
1.uk. Створіть новий клас, який міститиме виділену функціональність.

2.ru. Создайте связь между старым и новым классом.
2.en. Create a relationship between the old class and the new one.
2.uk. Створіть зв'язок між старим і новим класом.

3.ru. Используйте <a href="/move-field">перемещение поля</a> и <a href="/move-method">перемещение метода</a> для каждого поля и метода, которое вы решили переместить в новый класс.
3.en. Use <a href="/move-field">move field</a> and <a href="/move-method">move method</a> for each field that method that you have decided to move to a new class.
3.uk. Використовуйте <a href="/move-field"> переміщення поля</a> і <a href="/move-method"> переміщення методу</a> для кожного поля та методу, яке ви вирішили перемістити в новий клас.

4.ru. Решите, делать ли новый класс доступным извне объекта старого класса.
4.en. Decide whether to make the new class accessible from outside the object of the old class.
4.uk. Вирішіть, чи робити новий клас доступним ззовні об'єкта старого класу.



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

#|ru| Давайте рассмотрим <i>Извлечение класса</i> на примере простого класса, описывающего личность.
#|en| Let's look at <i>Extract Class</i> using the example of a simple class that describes a person.
#|uk| Давайте розглянемо <i>Відокремлення  класу<i> на прикладі простого класу, що описує особистість.

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

#|ru| В данном примере можно выделить в отдельный класс методы, относящиеся к телефонным номерам.
#|en| In this example, you can isolate methods related to phone numbers to a separate class.
#|uk| В даному прикладі можна виділити в окремий клас методи, що відносяться до телефонних номерів.

Go to the end of file

#|ru| Начнём с определения класса телефонного номера.
#|en| Start with defining the phone number class.
#|uk| Почнемо з визначення класу телефонного номера.

Print:
```


class TelephoneNumber {
}
```

Set step 2

Select name of "Person"

#|ru| Это было просто! Теперь создадим ссылку из класса <code>Person</code> на класс телефонного номера.
#|en| Easy as pie! Now we create a reference from the <code>Person</code> class to the phone number class.
#|uk| Це було просто! Тепер створимо посилання з класу <code>Person</code> на клас телефонного номеру.

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

#|ru| Всё готово, чтобы начать перемещать поля и методы. Воспользуемся рефакторингом <a href="/move-field">перемещение поля</a>, чтобы передвинуть поле <code>officeAreaCode</code> в класс <code>TelephoneNumber</code>.
#|en| Everything is ready for you to start moving fields and methods. Use <a href="/move-field">Move Field</a> to move the <code>officeAreaCode</code> field to the <code>TelephoneNumber</code> class.
#|uk| Все готово, щоб почати переміщати поля і методи. Скористаємося рефакторингом <a href="/move-field">переміщення поля</a>, щоб пересунути поле <code>officeAreaCode</code> в клас <code>TelephoneNumber</code>.

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

#|ru| Заметили? Мы сразу переименовали это поле так, чтобы оно имело более нейтральное название. Это повысит наши шансы на повторное использование данного класса.
#|en| Did you notice? We immediately renamed the field to be more neutral. This improves our chances of reusing the class.
#|uk| Помітили? Ми відразу перейменували це поле так, щоб воно мало більш нейтральну назву. Це підвищить наші шанси на повторне використання даного класу.

Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"

#|ru| После того, как поле успешно переместилось в класс <code>TelephoneNumber</code>, методы, которые использовали перемещённое поле, необходимо изменить так, чтобы они обращались к экземпляру созданного класса.
#|en| Now that we have successfully moved the field to the <code>TelephoneNumber</code> class, the methods that were used by the moved field should be changed so that they reference an instance of the created class.
#|uk| Після того, як поле успішно перемістилося в клас <code>TelephoneNumber</code>, методи, які використовували переміщене поле, необхідно змінити так, щоб вони зверталися до примірника створеного класу.

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

#|ru| Методы, которые использовали прямой доступ к полю, необходимо изменить так, чтобы они обращались к геттеру поля.
#|en| As for the methods that used direct access to the field, change them so that they reference the field getter.
#|uk| Методи, які використовували прямий доступ до поля, необхідно змінити так, щоб вони зверталися до геттера поля.

Select "officeAreaCode" in "getTelephoneNumber"

Replace "getOfficeAreaCode()"

Select:
```
  private $officeAreaCode;

```

#|ru| После чего можно удалить поле из исходного класса.
#|en| Then you can remove the field from the original class.
#|uk| Після чого можна видалити поле з вихідного класу.

Remove selected

Select "private |||$officeNumber|||"

#|ru| Итак, с <code>areaCode</code> разобрались. Аналогичным образом переносим поле <code>officeNumber</code>...
#|en| <code>areaCode</code> is all done. Similarly, we move the <code>officeNumber</code> field...
#|uk| Отже, з <code>areaCode</code> розібралися. Аналогічним чином переносимо поле <code>officeNumber</code>...

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

#|ru| ...и метод получения отформатированного номера <code>getTelephoneNumber()</code>.
#|en| …and the method for getting the formatted phone number <code>getTelephoneNumber()</code>.
#|uk| ...і метод отримання відформатованого номеру <code>getTelephoneNumber()</code>.

Go to the end of "TelephoneNumber"

Print:
```

  public function getTelephoneNumber() {
    return ("(" + $this->areaCode + ") " + $this->number);
  }
```

Select "private |||$officeNumber|||"

#|ru| После чего мы можем делегировать всю телефонную функциональность классу <code>TelephoneNumber</code>.
#|en| After that, we can delegate all phone functionality to the <code>TelephoneNumber</code> class.
#|uk| Після чого ми можемо делегувати всю телефонну функціональність класу <code>TelephoneNumber</code>.

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

#C|ru| Выполним тестирование, чтобы удостоверится, что код остался рабочим.
#S Всё отлично, код работает корректно.

#C|en| Let's launch autotests to check for errors in code.
#S Everything is OK! Code works correctly.

#C|uk| Виконаємо тестування, щоб упевниться, що код залишився робочим.
#S Все добре, код працює коректно.

Set step 4

Select "private $officeTelephone"

#|ru|+ На этом этапе осталось решить, в какой мере сделать наше поле доступным для клиентского кода. Можно полностью скрыть поле, создав для доступа к полям связанного объекта делегирующие методы (как это сделано сейчас)...
#|en|+ Here we should decide how available we want this new class to be for clients. We can hide it entirely, using delegate methods for access (as is currently done)…
#|uk|+ На цьому етапі залишилося вирішити, якою мірою зробити наше поле доступним для клієнтського коду. Можна повністю приховати поле, створивши для доступу до полів зв'язаного об'єкту делегуючі методи (як це зроблено зараз)...

Select whole "getOfficeAreaCode"
+ Select whole "setOfficeAreaCode"
+ Select whole "getOfficeNumber"
+ Select whole "setOfficeNumber"

#|ru|= ...а можно удалить все эти методы и сделать поле открытым.
#|en|= …or remove all these methods and make the class public.
#|uk|= ...а можна видалити всі ці методи і зробити поле відкритим.

Remove selected


#|ru| При этом нужно будет создать публичный геттер для связанного объекта, чтобы клиенты могли «достучаться» до него.
#|en| We will need to create a public getter for the associated object so that clients can access it.
#|uk| При цьому потрібно буде створити публічний геттер для пов'язаного об'єкта, щоб клієнти могли «достукатися» до нього.

Go to before "getTelephoneNumber"

Print:
```

  public function getOfficeTelephone() {
    return $this->officeTelephone;
  }
```

Select name of "getOfficeTelephone"

#|ru| Однако, решив сделать класс общедоступным, следует учесть опасности, связанные со ссылками. Как отнестись к тому, что при открытии телефонного номера клиент может изменить код зоны? Такое изменение может произвести любой код, имеющий доступ к экземпляру класса через публичный геттер.
#|en| But if we make the class public, take into account the dangers related to references. What about the fact that the client can change the area code when opening a phone number? This kind of change can be performed by any code that has access to a class instance via the public getter.
#|uk| Однак, вирішивши зробити клас загальнодоступним, слід врахувати небезпеки, пов'язані з посиланнями. Як поставитися до того, що при відкритті телефонного номера клієнт може змінити код зони? Така зміна може виконати будь-який код, який має доступ до примірника класу через публічний геттер.

#|ru| Возможны следующие варианты: <ul><li>Допускается изменение любым объектом любой части телефонного номера. В таком случае телефонный номер становится объектом-ссылкой, и следует рассмотреть <a href="/change-value-to-reference">замену значения ссылкой</a>. Доступ к телефонному номеру осуществляется через экземпляр класса <code>Person</code>.</li><li>Вы не желаете, чтобы кто-либо мог изменить телефонный номер, кроме как посредством методов экземпляра класса <code>Person</code>. Можно сделать телефонный номер только для чтения или обеспечить доступ к нему только через соответствующий метод.</li><li>Существует также возможность клонировать экземпляр класса <code>TelephoneNumber</code> перед тем, как предоставить его, но это может привести к недоразумениям, потому что люди будут думать, что могут изменить его значение. При этом могут также возникнуть проблемы со ссылками у клиентов, если телефонный номер часто передаётся.</li></ul>
#|en| The following options are possible: <ul><li>Any object can change any part of the phone number. In this case the phone number becomes a reference and you should look at <a href="/change-value-to-reference">Change Value to Reference</a>. Access to the phone number is implemented through an instance of <code>Person</code>.</li><li>You do not want anyone to be able to change a phone number except through the methods of an instance of the <code>Person</code> class. The phone number can be made read-only or access to it can be limited to an appropriate method.</li><li>You can also clone an instance of the <code>TelephoneNumber</code> class before providing access to it. But this can cause confusion because people will think that they can change this value. In addition, clients may have problems with references if the phone number is passed frequently.</li></ul>
#|uk| Можливі такі варіанти: <ul> <li> Допускається зміна будь-яким об'єктом будь-якої частини телефонного номера. У такому випадку номер стає об'єктом-посиланням, і слід розглянути <a href="/change-value-to-reference">заміну значення посиланням</a>. Доступ до телефонного номеру здійснюється через реалізацію класу <code>Person</code>. </ Li> <li> Ви не бажаєте, щоб хто-небудь міг змінити телефонний номер, окрім як за допомогою методів екземпляра класу <code>Person </ code >. Можна зробити телефонний номер тільки для читання або забезпечити доступ до нього тільки через відповідний метод. </ Li> <li> Існує також можливість клонувати екземпляр класу <code>TelephoneNumber</code> перед тим, як надати його, але це може привести до непорозумінь, тому що люди будуть думати, що можуть змінити його значення. При цьому можуть також виникнути проблеми з посиланнями у клієнтів, якщо телефонний номер часто передається. </ Li> </ ul>

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.