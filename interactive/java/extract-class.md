extract-class:java

###

1. Создайте новый класс, который будет содержать выделенную функциональность.

2. Создайте связь между старым и новым классом.

3. Используйте <a href="/move-method">перемещение метода</a> и <a href="/move-field">перемещение поля</a> для каждого поля и метода, которое вы решили переместить в новый класс.

4. Решите, делать ли новый класс доступным извне объекта старого класса.



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

# Давайте рассмотрим <i>Извлечение класса</i> на примере простого класса, описывающего личность.

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

# В данном примере можно выделить в отдельный класс функции, относящиеся к телефонным номерам.

Go to the end of file

# Начнём с определения класса телефонного номера.

Print:
```


class TelephoneNumber {
}
```

Set step 2

Select name of "Person"

# Это было просто! Теперь создадим ссылку из класса <code>Person</code> на класс телефонного номера.

Go to "private String officeNumber;|||"

Print:
```

  private TelephoneNumber officeTelephone = new TelephoneNumber();
```

Set step 3

Select "private String |||officeAreaCode|||"
+ Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"

# Всё готово, чтобы начать перемещать поля и методы. Воспользуемся рефакторингом <a href="/move-field">перемещение поля</a>, чтобы передвинуть поле <code>officeAreaCode</code> в класс <code>TelephoneNumber</code>.

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

# Заметили? Я сразу переименовал это поле так, чтобы оно имело более нейтральное название. Это повысит наши шансы повторно использовать этот класс.

Select name of "getOfficeAreaCode"
+ Select name of "setOfficeAreaCode"

# После того, как поле успешно переместилось в класс <code>TelephoneNumber</code>, мы меняем геттеры и сеттеры телефонного поля в оригинальном классе так, чтобы они обращались к связанному объекту телефонного номера.

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

# Методы, которые использовали прямой доступ к полю, необходимо изменить так, чтобы они обращались к геттеру поля.

Select "officeAreaCode" in "getTelephoneNumber"

Replace "getOfficeAreaCode()"

Select:
```
  private String officeAreaCode;

```

# После чего можно удалить поле из исходного класса.

Remove selected

Select "private String |||officeNumber|||"

# Итак, с <code>areaCode</code> разобрались. Аналогичным образом переносим поле <code>officeNumber</code>...

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

# ...и метод получения отформатированного номера <code>getTelephoneNumber()</code>.

Go to the end of "TelephoneNumber"

Print:
```

  public String getTelephoneNumber() {
    return ("(" + areaCode + ") " + number);
  }
```

Select "private String |||officeNumber|||"

# Теперь мы можем делегировать всю телефонную функциональность классу <code>TelephoneNumber</code>.

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

#C Выполним компиляцию, чтобы удостоверится, что код остался рабочим.

#S Всё отлично, код работает корректно.

Set step 4

Select "private TelephoneNumber officeTelephone"

#+ На этом этапе осталось решить, в какой мере сделать новый класс доступным для клиентов. Можно полностью скрыть класс, создав для интерфейса делегирующие методы (как это сделано сейчас)...

Select whole "getOfficeAreaCode"
+ Select whole "setOfficeAreaCode"
+ Select whole "getOfficeNumber"
+ Select whole "setOfficeNumber"

#= ...а можно сделать класс открытым и удалить все делегирующие методы.

Remove selected


# При этом нужно будет создать публичный геттер для связанного объекта, дабы клиенты могли достучаться к нему.

Go to before "getTelephoneNumber"

Print:
```

  public TelephoneNumber getOfficeTelephone() {
    return officeTelephone;
  }
```

Select name of "getOfficeTelephone"

# Тем не менее, решив сделать класс общедоступным, следует учесть опасности, связанные со ссылками. Как отнестись к тому, что при открытии телефонного номера клиент может изменить код зоны? Такое изменение может произвести любой код, имеющий доступ к экземпляру класса через публичный геттер.

# Возможны следующие варианты: <ul><li>Допускается изменение любым объектом любой части телефонного номера. В таком случае телефонный номер становится объектом-ссылкой, и следует рассмотреть <a href="/change-value-to-reference">замену значения ссылкой</a>. Доступ к телефонному номеру осуществляется через экземпляр класса <code>Person</code>.</li><li>Вы не желаете, чтобы кто-либо мог изменить телефонный номер, кроме как посредством методов экземпляра класса <code>Person</code>. Можно сделать неизменяемым телефонный номер или обеспечить неизменяемый интерфейс к телефонному номеру.</li><li>Существует также возможность клонировать телефонный номер перед тем, как предоставить его, но это может привести к недоразумениям, потому что люди будут думать, что могут изменить его значение. При этом могут также возникнуть проблемы со ссылками у клиентов, если телефонный номер часто передаётся.</li></ul>

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.