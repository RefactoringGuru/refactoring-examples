extract-class:csharp

###

1. Создайте новый класс, который будет содержать выделенную функциональность.

2. Создайте связь между старым и новым классом.

3. Используйте <a href="/move-field">перемещение поля</a> и <a href="/move-method">перемещение метода</a> для каждого свойства и метода, которое вы решили переместить в новый класс.

4. Решите, делать ли новый класс доступным извне объекта старого класса.



###

```
public class Person
{
  public string Name
  {
    get;
  }
  public string OfficeAreaCode
  {
    get;
    set;
  }
  public string OfficeNumber
  {
    get;
    set;
  }

  public string GetTelephoneNumber()
  {
    return "(" + OfficeAreaCode + ") " + OfficeNumber;
  }
}
```

###

```
public class Person
{
  private TelephoneNumber officeTelephone = new TelephoneNumber();

  public string Name
  {
    get;
  }
  public TelephoneNumber OfficeTelephone
  {
    get {
      return officeTelephone;
    }
  }

  public string GetTelephoneNumber()
  {
    return officeTelephone.GetTelephoneNumber();
  }
}

public class TelephoneNumber
{
  public string AreaCode
  {
    get;
    set;
  }
  public string Number
  {
    get;
    set;
  }

  public string GetTelephoneNumber()
  {
    return "(" + AreaCode + ") " + Number;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Извлечение класса</i> на примере простого класса, описывающего личность.

Select "string |||OfficeAreaCode|||"
+ Select "string |||OfficeNumber|||"
+ Select name of "GetTelephoneNumber"

# В данном примере можно выделить в отдельный класс свойства и методы, относящиеся к телефонным номерам.

Go to the end of file

# Начнём с определения класса телефонного номера.

Print:
```


public class TelephoneNumber
{
}
```

Set step 2

Select name of "Person"

# Это было просто! Теперь создадим ссылку из класса <code>Person</code> на класс телефонного номера.

Go to the start of "Person"

Print:
```

  private TelephoneNumber officeTelephone = new TelephoneNumber();

```

Set step 3

Select "string |||OfficeAreaCode|||"

# Всё готово, чтобы начать перемещать свойства и методы. Воспользуемся рефакторингом <a href="/move-field">перемещение поля</a>, чтобы передвинуть свойство <code>OfficeAreaCode</code> в класс <code>TelephoneNumber</code>.

Go to the start of "TelephoneNumber"

Print:
```

  public string AreaCode
  {
    get;
    set;
  }
```

Select "AreaCode" in "TelephoneNumber"

# Заметили? Я сразу переименовал это свойство так, чтобы оно имело более нейтральное название. Это повысит наши шансы на повторное использование данного класса.

Select "OfficeAreaCode" in "GetTelephoneNumber"

# После того, как свойство успешно переместилось в класс <code>TelephoneNumber</code>, методы, которые использовали перемещённое свойство, необходимо изменить так, чтобы они обращались к экземпляру созданного класса.

Print:
```
officeTelephone.AreaCode
```

Select:
```
  public string OfficeAreaCode
  {
    |||get|||;
    |||set|||
```

# Кроме того, если требуется сохранить свойство в исходном классе, то надо переписать его сеттер и геттер таким образом, чтобы они делегировали свойство созданного класса.

Select:
```
  public string OfficeAreaCode
  {
    get|||;|||
```

Replace:
```
 {
      return officeTelephone.AreaCode;
    }
```

Select:
```
AreaCode;
    }
    set|||;|||
```

Replace:
```
 {
      officeTelephone.AreaCode = value;
    }
```

Select "string |||OfficeNumber|||"

#^= Итак, с <code>OfficeAreaCode</code> разобрались. Аналогичным образом переносим свойство <code>OfficeNumber</code>...

Go to the end of "TelephoneNumber"

Print:
```

  public string Number
  {
    get;
    set;
  }
```

Select "OfficeNumber" in "GetTelephoneNumber"

# ...и перенаправляем все обращения к нему в созданный нами класс.

Print:
```
officeTelephone.Number
```

Select:
```
  public string OfficeNumber
  {
    get;
    set;
  }

```

Select "string |||OfficeNumber|||"

Wait 500ms

Select:
```
  public string OfficeNumber
  {
    get|||;|||
```

Replace:
```
 {
      return officeTelephone.Number;
    }
```

Select:
```
Number;
    }
    set|||;|||
```

Replace:
```
 {
      officeTelephone.Number = value;
    }
```

Select name of "GetTelephoneNumber"

# Теперь осталось только перенести метод получения отформатированного номера <code>GetTelephoneNumber()</code>.

Go to the end of "TelephoneNumber"

Print:
```


  public string GetTelephoneNumber()
  {
    return "(" + AreaCode + ") " + Number;
  }
```

Select "TelephoneNumber |||officeTelephone|||"

# После чего мы можем делегировать всю телефонную функциональность классу <code>TelephoneNumber</code>.

Select body of "GetTelephoneNumber"

Replace:
```
    return officeTelephone.GetTelephoneNumber();
```

#C Выполним компиляцию, чтобы удостоверится, что код остался рабочим.

#S Всё отлично, код работает корректно.

Set step 4

Select "private TelephoneNumber officeTelephone"

#+ На этом этапе осталось решить, в какой мере сделать новый класс доступным для клиентов. Можно полностью скрыть класс, создав для доступа делегирующие свойства (как это сделано сейчас)...

Select:
```
  public string OfficeAreaCode
  {
    get {
      return officeTelephone.AreaCode;
    }
    set {
      officeTelephone.AreaCode = value;
    }
  }
  public string OfficeNumber
  {
    get {
      return officeTelephone.Number;
    }
    set {
      officeTelephone.Number = value;
    }
  }
```

#= ...а можно удалить все эти свойства и сделать класс открытым.

Remove selected

# При этом нужно будет создать публичное свойство для связанного объекта, дабы клиенты могли достучаться к нему.

Print:
```
  public TelephoneNumber OfficeTelephone
  {
    get {
      return officeTelephone;
    }
  }
```

Select "public TelephoneNumber |||OfficeTelephone|||"

# Однако решив сделать класс общедоступным, следует учесть опасности, связанные со ссылками. Как отнестись к тому, что при открытии телефонного номера клиент может изменить код зоны? Такое изменение может произвести любой код, имеющий доступ к экземпляру класса через публичное свойство.

# Возможны следующие варианты: <ul><li>Допускается изменение любым объектом любой части телефонного номера. В таком случае телефонный номер становится объектом-ссылкой, и следует рассмотреть <a href="/change-value-to-reference">замену значения ссылкой</a>. Доступ к телефонному номеру осуществляется через экземпляр класса <code>Person</code>.</li><li>Вы не желаете, чтобы кто-либо мог изменить телефонный номер, кроме как посредством методов экземпляра класса <code>Person</code>. Можно сделать строковые свойства телефонного номера только для чтения. Или обеспечить доступ к нему только через соответствующий метод.</li><li>Существует также возможность клонировать экземпляр класса <code>TelephoneNumber</code> перед тем, как предоставить его, но это может привести к недоразумениям, потому что люди будут думать, что могут изменить его значение. При этом могут также возникнуть проблемы со ссылками у клиентов, если телефонный номер часто передаётся.</li></ul>

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.