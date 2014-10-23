extract-class:java

###

1.ru. Создайте новый класс, который будет содержать выделенную функциональность.
1.uk. Створіть новий клас, який міститиме виділену функціональність.

2.ru. Создайте связь между старым и новым классом.
2.uk. Створіть зв'язок між старим і новим класом.

3.ru. Используйте <a href="/move-field">перемещение поля</a> и <a href="/move-method">перемещение метода</a> для каждого поля и метода, которое вы решили переместить в новый класс.
3.uk. Використовуйте <a href="/move-field"> переміщення поля</a> і <a href="/move-method"> переміщення методу</a> для кожного поля та методу, яке ви вирішили перемістити в новий клас.

4.ru. Решите, делать ли новый класс доступным извне объекта старого класса.
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
#|uk| В даному прикладі можна виділити в окремий клас методи, що відносяться до телефонних номерів.

Go to the end of file

#|ru| Начнём с определения класса телефонного номера.
#|uk| Почнемо з визначення класу телефонного номера.

Print:
```


class TelephoneNumber {
}
```

Set step 2

Select name of "Person"

#|ru| Это было просто! Теперь создадим ссылку из класса <code>Person</code> на класс телефонного номера.
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
#|uk| Помітили? Ми відразу перейменували це поле так, щоб воно мало більш нейтральну назву. Це підвищить наші шанси на повторне використання даного класу.

Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"

#|ru| После того, как поле успешно переместилось в класс <code>TelephoneNumber</code>, методы, которые использовали перемещённое поле, необходимо изменить так, чтобы они обращались к экземпляру созданного класса.
#|uk| Після того, як поле успішно перемістилося в клас <code>TelephoneNumber</code>, методи, які використовували переміщене поле, необхідно змінити так, щоб вони зверталися до примірника створеного класу.

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
#|uk| Методи, які використовували прямий доступ до поля, необхідно змінити так, щоб вони зверталися до геттера поля.

Select "officeAreaCode" in "getTelephoneNumber"

Replace "getOfficeAreaCode()"

Select:
```
  private String officeAreaCode;

```

#|ru| После чего можно удалить поле из исходного класса.
#|uk| Після чого можна видалити поле з вихідного класу.

Remove selected

Select "private String |||officeNumber|||"

#|ru| Итак, с <code>areaCode</code> разобрались. Аналогичным образом переносим поле <code>officeNumber</code>...
#|uk| Отже, з <code>areaCode</code> розібралися. Аналогічним чином переносимо поле <code>officeNumber</code>...

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

#|ru| ...и метод получения отформатированного номера <code>getTelephoneNumber()</code>.
#|uk| ...і метод отримання відформатованого номеру <code>getTelephoneNumber()</code>.

Go to the end of "TelephoneNumber"

Print:
```

  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
  }
```

Select "private String |||officeNumber|||"

#|ru| После чего мы можем делегировать всю телефонную функциональность классу <code>TelephoneNumber</code>.
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

#C|uk| Виконаємо компіляцію, щоб упевниться, що код залишився робочим.
#S Все добре, код працює коректно.

Set step 4

Select "private TelephoneNumber officeTelephone"

#|ru|+ На этом этапе осталось решить, в какой мере сделать новый класс доступным для клиентов. Можно полностью скрыть класс, создав для доступа делегирующие методы (как это сделано сейчас)...
#|uk|+ На цьому етапі залишилося вирішити, якою мірою зробити новий клас доступним для клієнтів. Можна повністю приховати клас, створивши для доступу делегуючі методи (як це зроблено зараз)...

Select whole "getOfficeAreaCode"
+ Select whole "setOfficeAreaCode"
+ Select whole "getOfficeNumber"
+ Select whole "setOfficeNumber"

#|ru|= ...а можно удалить все эти методы и сделать класс открытым.
#|uk|= ...а можна видалити всі ці методи і зробити клас відкритим.

Remove selected


#|ru| При этом нужно будет создать публичный геттер для связанного объекта, чтобы клиенты могли «достучаться» до него.
#|uk| При цьому потрібно буде створити публічний геттер для пов'язаного об'єкта, щоб клієнти могли «достукатися» до нього.

Go to before "getTelephoneNumber"

Print:
```

  public TelephoneNumber getOfficeTelephone() {
    return officeTelephone;
  }
```

Select name of "getOfficeTelephone"

#|ru| Однако, решив сделать класс общедоступным, следует учесть опасности, связанные со ссылками. Как отнестись к тому, что при открытии телефонного номера клиент может изменить код зоны? Такое изменение может произвести любой код, имеющий доступ к экземпляру класса через публичный геттер.
#|uk| Однак, вирішивши зробити клас загальнодоступним, слід врахувати небезпеки, пов'язані з посиланнями. Як поставитися до того, що при відкритті телефонного номера клієнт може змінити код зони? Така зміна може виконати будь-який код, який має доступ до примірника класу через публічний геттер.

#|ru| Возможны следующие варианты: <ul><li>Допускается изменение любым объектом любой части телефонного номера. В таком случае телефонный номер становится объектом-ссылкой, и следует рассмотреть <a href="/change-value-to-reference">замену значения ссылкой</a>. Доступ к телефонному номеру осуществляется через экземпляр класса <code>Person</code>.</li><li>Вы не желаете, чтобы кто-либо мог изменить телефонный номер, кроме как посредством методов экземпляра класса <code>Person</code>. Можно сделать телефонный номер только для чтения или обеспечить доступ к нему только через соответствующий метод.</li><li>Существует также возможность клонировать экземпляр класса <code>TelephoneNumber</code> перед тем, как предоставить его, но это может привести к недоразумениям, потому что люди будут думать, что могут изменить его значение. При этом могут также возникнуть проблемы со ссылками у клиентов, если телефонный номер часто передаётся.</li></ul>
#|uk| Можливі такі варіанти: <ul> <li> Допускається зміна будь-яким об'єктом будь-якої частини телефонного номера. У такому випадку номер стає об'єктом-посиланням, і слід розглянути <a href="/change-value-to-reference">заміну значення посиланням</a>. Доступ до телефонного номеру здійснюється через реалізацію класу <code>Person</code>. </ Li> <li> Ви не бажаєте, щоб хто-небудь міг змінити телефонний номер, окрім як за допомогою методів екземпляра класу <code>Person </ code >. Можна зробити телефонний номер тільки для читання або забезпечити доступ до нього тільки через відповідний метод. </ Li> <li> Існує також можливість клонувати екземпляр класу <code>TelephoneNumber</code> перед тим, як надати його, але це може привести до непорозумінь, тому що люди будуть думати, що можуть змінити його значення. При цьому можуть також виникнути проблеми з посиланнями у клієнтів, якщо телефонний номер часто передається. </ Li> </ ul>

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.