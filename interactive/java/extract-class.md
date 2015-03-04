extract-class:java

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
  private String name;
  private String officeAreaCode;
  private String officeNumber;

  public String getName() {
    return name;
  }
  public String getTelephoneNumber() {
    return ("(" + officeAreaCode + ") " + officeNumber);
  }
  public String getOfficeAreaCode() {
    return officeAreaCode;
  }
  public void setOfficeAreaCode(String arg) {
    officeAreaCode = arg;
  }
  public String getOfficeNumber() {
    return officeNumber;
  }
  public void setOfficeNumber(String arg) {
    officeNumber = arg;
  }
}
```

###

```
class Person {
  private String name;
  private TelephoneNumber officeTelephone = new TelephoneNumber();

  public String getName() {
    return name;
  }
  public TelephoneNumber getOfficeTelephone() {
    return officeTelephone;
  }
  public String getTelephoneNumber() {
    return officeTelephone.getTelephoneNumber();
  }
}

class TelephoneNumber {
  private String areaCode;
  private String number;

  public String getAreaCode() {
    return areaCode;
  }
  public void setAreaCode(String arg) {
    areaCode = arg;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String arg) {
    number = arg;
  }
  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
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
  private String |||officeAreaCode|||;
  private String |||officeNumber|||;
```
+ Select name of "getTelephoneNumber"
+ Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"
+ Select name of "getOfficeNumber"
+ Select name of "setOfficeNumber"

#|ru| В данном примере можно выделить в отдельный класс методы, относящиеся к телефонным номерам.
#|en| In this example, we can isolate methods related to phone numbers to a separate class.
#|uk| В даному прикладі можна виділити в окремий клас методи, що відносяться до телефонних номерів.

Go to the end of file

#|ru| Начнём с определения класса телефонного номера.
#|en| Let's start by defining the phone number class.
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

Go to "private String officeNumber;|||"

Print:
```

  private TelephoneNumber officeTelephone = new TelephoneNumber();
```

Set step 3

Select "private String |||officeAreaCode|||"
+ Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"

#|ru| Всё готово, чтобы начать перемещать поля и методы. Воспользуемся рефакторингом <a href="/move-field">перемещение поля</a>, чтобы передвинуть поле <code>officeAreaCode</code> в класс <code>TelephoneNumber</code>.
#|en| Everything is ready to start moving fields and methods. We use <a href="/move-field">Move Field</a> to move the <code>officeAreaCode</code> field to the <code>TelephoneNumber</code> class.
#|uk| Все готово, щоб почати переміщати поля і методи. Скористаємося рефакторингом <a href="/move-field">переміщення поля</a>, щоб пересунути поле <code>officeAreaCode</code> в клас <code>TelephoneNumber</code>.

Go to the start of "TelephoneNumber"

Print:
```

  private String areaCode;

  public String getAreaCode() {
    return areaCode;
  }
  public void setAreaCode(String arg) {
    areaCode = arg;
  }
```

Select "areaCode" in "TelephoneNumber"

#|ru| Заметили? Мы сразу переименовали это поле так, чтобы оно имело более нейтральное название. Это повысит наши шансы на повторное использование данного класса.
#|en| Did you notice? We immediately renamed the field to be more neutral. That improves our chances of reusing the class.
#|uk| Помітили? Ми відразу перейменували це поле так, щоб воно мало більш нейтральну назву. Це підвищить наші шанси на повторне використання даного класу.

Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"

#|ru| После того как поле успешно переместилось в класс <code>TelephoneNumber</code>, методы, которые использовали перемещённое поле, необходимо изменить так, чтобы они обращались к экземпляру созданного класса.
#|en| Now we should change the methods, which used the moved field so that they access it through a <code>TelephoneNumber</code> object.
#|uk| Після того як поле успішно перемістилося в клас <code>TelephoneNumber</code>, методи, які використовували переміщене поле, необхідно змінити так, щоб вони зверталися до примірника створеного класу.

Select body of "getOfficeAreaCode"

Replace:
```
    return officeTelephone.getAreaCode();
```

Wait 500ms

Select body of "setOfficeAreaCode"

Replace:
```
    officeTelephone.setAreaCode(arg);
```

#|ru| Методы, которые использовали прямой доступ к полю, необходимо изменить так, чтобы они обращались к геттеру поля.
#|en| We can also turn all cases of direct field access to the proper getter/setter calls.
#|uk| Методи, які використовували прямий доступ до поля, необхідно змінити так, щоб вони зверталися до геттера поля.

Select "officeAreaCode" in "getTelephoneNumber"

Replace "getOfficeAreaCode()"

Select:
```
  private String officeAreaCode;

```

#|ru| После чего можно удалить поле из исходного класса.
#|en| At last, we can remove the field from the original class.
#|uk| Після чого можна видалити поле з вихідного класу.

Remove selected

Select "private String |||officeNumber|||"

#|ru| Итак, с <code>areaCode</code> разобрались. Аналогичным образом переносим поле <code>officeNumber</code>…
#|en| The <code>areaCode</code> is all done. Similarly, we move the <code>officeNumber</code> field…
#|uk| Отже, з <code>areaCode</code> розібралися. Аналогічним чином переносимо поле <code>officeNumber</code>…

Go to "private String areaCode;|||"

Print:
```

  private String number;
```

Go to the end of "TelephoneNumber"

Print:
```

  public String getNumber() {
    return number;
  }
  public void setNumber(String arg) {
    number = arg;
  }
```

Select name of "getTelephoneNumber"

#|ru| …и метод получения отформатированного номера <code>getTelephoneNumber()</code>.
#|en| …and the method for getting the formatted phone number <code>getTelephoneNumber()</code>.
#|uk| …і метод отримання відформатованого номеру <code>getTelephoneNumber()</code>.

Go to the end of "TelephoneNumber"

Print:
```

  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
  }
```

Select "private String |||officeNumber|||"

#|ru| После чего мы можем делегировать всю телефонную функциональность классу <code>TelephoneNumber</code>.
#|en| After that, we can delegate all phone functionality to the <code>TelephoneNumber</code> class.
#|uk| Після чого ми можемо делегувати всю телефонну функціональність класу <code>TelephoneNumber</code>.

Select body of "getTelephoneNumber"

Replace:
```
    return officeTelephone.getTelephoneNumber();
```

Select body of "getOfficeNumber"

Replace:
```
    return officeTelephone.getNumber();
```

Select body of "setOfficeNumber"

Replace:
```
    officeTelephone.setNumber(arg);
```

Select:
```
  private String officeNumber;

```

Remove selected

#C|ru| Выполним компиляцию, чтобы удостоверится, что код остался рабочим.
#S Всё отлично, код работает корректно.

#C|en| Let's run the compiler to verify that the code is not broken anywhere.
#S Everything is OK! Code works correctly.

#C|uk| Виконаємо компіляцію, щоб упевниться, що код залишився робочим.
#S Все добре, код працює коректно.

Set step 4

Select "private TelephoneNumber officeTelephone"

#|ru|+ На этом этапе осталось решить, в какой мере сделать наше поле доступным для клиентского кода. Можно полностью скрыть поле, создав для доступа к полям связанного объекта делегирующие методы (как это сделано сейчас)…
#|en|+ Here we should decide how available we want this new field to be for a client code. We can hide it entirely using delegation methods for accessing all the fields (as is currently done)…
#|uk|+ На цьому етапі залишилося вирішити, якою мірою зробити наше поле доступним для клієнтського коду. Можна повністю приховати поле, створивши для доступу до полів зв'язаного об'єкту делегуючі методи (як це зроблено зараз)…

Select whole "getOfficeAreaCode"
+ Select whole "setOfficeAreaCode"
+ Select whole "getOfficeNumber"
+ Select whole "setOfficeNumber"

#|ru|= …а можно удалить все эти методы и сделать поле открытым.
#|en|= …or remove all these methods and make the field public.
#|uk|= …а можна видалити всі ці методи і зробити поле відкритим.

Remove selected


#|ru| При этом нужно будет создать публичный геттер для связанного объекта, чтобы клиенты могли «достучаться» до него.
#|en| To do this, we will need to create a public getter for the associated object so that clients can access it.
#|uk| При цьому потрібно буде створити публічний геттер для пов'язаного об'єкта, щоб клієнти могли «достукатися» до нього.

Go to before "getTelephoneNumber"

Print:
```

  public TelephoneNumber getOfficeTelephone() {
    return officeTelephone;
  }
```

Select name of "getOfficeTelephone"

#|ru| Однако, решив сделать поле общедоступным, следует учесть опасности, связанные со ссылками. Как отнестись к тому, что при открытии телефонного номера клиент может изменить код зоны? Такое изменение может произвести любой код, имеющий доступ к экземпляру класса через публичный геттер.
#|en| But if we want to make the field public, let's consider some of the dangers related to object references. What about the fact that the client can change the area code when opening a phone number? Any code that has access to a class instance via the public getter could perform such change.
#|uk| Однак, вирішивши зробити поле загальнодоступним, слід врахувати небезпеки, пов'язані з посиланнями. Як поставитися до того, що при відкритті телефонного номера клієнт може змінити код зони? Така зміна може виконати будь-який код, який має доступ до примірника класу через публічний геттер.

#|ru| Возможны следующие варианты: <ul><li>Допускается изменение любым объектом любой части телефонного номера. В таком случае телефонный номер становится объектом-ссылкой, и следует рассмотреть <a href="/change-value-to-reference">замену значения ссылкой</a>. Доступ к телефонному номеру осуществляется через экземпляр класса <code>Person</code>.</li><li>Вы не желаете, чтобы кто-либо мог изменить телефонный номер, кроме как посредством методов экземпляра класса <code>Person</code>. Можно сделать телефонный номер только для чтения или обеспечить доступ к нему только через соответствующий метод.</li><li>Существует также возможность клонировать экземпляр класса <code>TelephoneNumber</code> перед тем, как предоставить его, но это может привести к недоразумениям, потому что люди будут думать, что могут изменить его значение. При этом могут также возникнуть проблемы со ссылками у клиентов, если телефонный номер часто передаётся.</li></ul>
#|en| The following options are possible: <ul><li>Any object can change any part of the phone number. In this case the phone number becomes a reference and you should look at <a href="/change-value-to-reference">Change Value to Reference</a>. Access to the phone number is implemented through an instance of <code>Person</code>.</li><li>We do not want anyone to be able to change a phone number except through the methods of an instance of the <code>Person</code> class. The phone number can be made read-only or access to it can be limited to an appropriate method.</li><li>We can also clone an instance of the <code>TelephoneNumber</code> class before providing access to it. But this can cause confusion because people will think that they can change this value. In addition, clients may have problems with references if the phone number is frequently passed .</li></ul>
#|uk| Можливі такі варіанти: <ul> <li> Допускається зміна будь-яким об'єктом будь-якої частини телефонного номера. У такому випадку номер стає об'єктом-посиланням, і слід розглянути <a href="/change-value-to-reference">заміну значення посиланням</a>. Доступ до телефонного номеру здійснюється через реалізацію класу <code>Person</code>. </ Li> <li> Ви не бажаєте, щоб хто-небудь міг змінити телефонний номер, окрім як за допомогою методів екземпляра класу <code>Person </ code >. Можна зробити телефонний номер тільки для читання або забезпечити доступ до нього тільки через відповідний метод. </ Li> <li> Існує також можливість клонувати екземпляр класу <code>TelephoneNumber</code> перед тим, як надати його, але це може привести до непорозумінь, тому що люди будуть думати, що можуть змінити його значення. При цьому можуть також виникнути проблеми з посиланнями у клієнтів, якщо телефонний номер часто передається. </ Li> </ ul>

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.