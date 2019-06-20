extract-class:csharp

###

1.ru. Создайте новый класс, который будет содержать выделенную функциональность.
1.en. Create a new class to contain the relevant functionality.
1.uk. Створіть новий клас, який міститиме виділену функціональність.

2.ru. Создайте связь между старым и новым классом.
2.en. Create a relationship between the old class and the new one.
2.uk. Створіть зв'язок між старим і новим класом.

3.ru. Используйте <a href="/move-field">перемещение поля</a> и <a href="/move-method">перемещение метода</a> для каждого свойства и метода, которое вы решили переместить в новый класс.
3.en. Use <a href="/move-field">move field</a> and <a href="/move-method">move field</a> for each property and method that you decide to remove to the new class.
3.uk. Використовуйте <a href="/move-field"> переміщення поля</a> і <a href="/move-method"> переміщення методу</a> для кожної властивості і методу, які ви вирішили перемістити в новий клас.

4.ru. Решите, делать ли новый класс доступным извне объекта старого класса.
4.en. Decide whether to make the new class accessible from outside the object of the old class.
4.uk. Вирішіть, чи робити новий клас доступним ззовні об'єкта старого класу.



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

#|ru| Давайте рассмотрим <i>Извлечение класса</i> на примере простого класса, описывающего личность.
#|en| Let's look at <i>Extract Class</i> using the example of a simple class that describes a person.
#|uk| Давайте розглянемо <i>Відокремлення  класу</i> на прикладі простого класу, що описує особистість.

Select "string |||OfficeAreaCode|||"
+ Select "string |||OfficeNumber|||"
+ Select name of "GetTelephoneNumber"

#|ru| В данном примере можно выделить в отдельный класс свойства и методы, относящиеся к телефонным номерам.
#|en| In this example, you can extract the properties and methods relating to phone numbers to a separate class.
#|uk| В даному прикладі можна виділити в окремий клас властивості і методи, що відносяться до телефонних номерів.

Go to the end of file

#|ru| Начнём с определения класса телефонного номера.
#|en| Let's start by defining the phone number class.
#|uk| Почнемо з визначення класу телефонного номера.

Print:
```


public class TelephoneNumber
{
}
```

Set step 2

Select name of "Person"

#|ru| Это было просто! Теперь создадим ссылку из класса <code>Person</code> на класс телефонного номера.
#|en| Easy as pie! Now we create a reference from the <code>Person</code> class to the phone number class.
#|uk| Це було просто! Тепер створимо посилання з класу <code>Person</code> на клас телефонного номеру.

Go to the start of "Person"

Print:
```

  private TelephoneNumber officeTelephone = new TelephoneNumber();

```

Set step 3

Select "string |||OfficeAreaCode|||"

#|ru| Всё готово, чтобы начать перемещать свойства и методы. Воспользуемся рефакторингом <a href="/ru/move-field">перемещение поля</a>, чтобы передвинуть свойство <code>OfficeAreaCode</code> в класс <code>TelephoneNumber</code>.
#|en| Everything is ready to start moving properties and methods. We use the <a href="/move-field">Move Field</a> technique to move the <code>OfficeAreaCode</code> property to the <code>TelephoneNumber</code> class.
#|uk| Все готово, щоб почати переміщати властивості та методи. Скористаємося рефакторингом <a href="/uk/move-field">переміщення поля</a>, щоб пересунути властивість <code>OfficeAreaCode</code> в клас <code>TelephoneNumber</code>.

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

#|ru| Заметили? Я сразу переименовал это свойство так, чтобы оно имело более нейтральное название. Это повысит наши шансы на повторное использование данного класса.
#|en| Did you notice? Right away I renamed the property to give it a neutral name. This improves our changes of reusing this class.
#|uk| Помітили? Я відразу перейменував цю властивість так, щоб вона мала більш нейтральну назву. Це підвищить наші шанси на повторне використання даного класу.

Select "OfficeAreaCode" in "GetTelephoneNumber"

#|ru| После того как свойство успешно переместилось в класс <code>TelephoneNumber</code>, методы, которые использовали перемещённое свойство, необходимо изменить так, чтобы они обращались к экземпляру созданного класса.
#|en| After the method has been successfully moved to the <code>TelephoneNumber</code> class, the methods that used the moved property must be changed so that they reference an instance of the created class.
#|uk| Після того як властивість успішно перемістилася в клас <code>TelephoneNumber</code>, методи, які використовували переміщену властивість, необхідно змінити так, щоб вони зверталися до примірника створеного класу.

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

#|ru| Кроме того, если требуется сохранить свойство в исходном классе, то надо переписать его сеттер и геттер таким образом, чтобы они делегировали свойство созданного класса.
#|en| In addition, if you need to store a property in the source class, you need to rewrite its setter and getter so that they delegate the property of the created class.
#|uk| Крім того, якщо потрібно зберегти властивість у вихідному класі, то треба переписати його сетер і геттер таким чином, щоб вони делегували властивість створеного класу.

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

#|ru|^= Итак, с <code>OfficeAreaCode</code> разобрались. Аналогичным образом переносим свойство <code>OfficeNumber</code>…
#|en|^= So now we have dealt with <code>OfficeAreaCode</code>. We can similarly move the <code>OfficeNumber</code> property…
#|uk|^= Отже, з <code>OfficeAreaCode</code> розібралися. Аналогічним чином переносимо властивість <code>OfficeNumber</code>…

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

#|ru| …и перенаправляем все обращения к нему в созданный нами класс.
#|en| …and redirect all references made to it in favor of the class that we have created.
#|uk| …та перенаправляємо всі звернення до нього в створений нами клас.

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

#|ru| Теперь осталось только перенести метод получения отформатированного номера <code>GetTelephoneNumber()</code>.
#|en| Now we need only to move the method for getting the formatted number <code>GetTelephoneNumber()</code>.
#|uk| Тепер залишилося тільки перенести метод отримання відформатованого номера <code>GetTelephoneNumber()</code>.

Go to the end of "TelephoneNumber"

Print:
```


  public string GetTelephoneNumber()
  {
    return "(" + AreaCode + ") " + Number;
  }
```

Select "TelephoneNumber |||officeTelephone|||"

#|ru| После чего мы можем делегировать всю телефонную функциональность классу <code>TelephoneNumber</code>.
#|en| After that, we can delegate all phone functionality to the <code>TelephoneNumber</code> class.
#|uk| Після чого ми можемо делегувати всю телефонну функціональність класу <code>TelephoneNumber</code>.

Select body of "GetTelephoneNumber"

Replace:
```
    return officeTelephone.GetTelephoneNumber();
```

#C|ru| Выполним компиляцию, чтобы удостоверится, что код остался рабочим.
#S Всё отлично, код работает корректно.

#C|en| Let's run the compiler to verify that the code is not broken anywhere.
#S Everything is OK! Code works correctly.

#C|uk| Виконаємо компіляцію, щоб упевниться, що код залишився робочим.
#S Все добре, код працює коректно.

Set step 4

Select "private TelephoneNumber officeTelephone"

#|ru|+ На этом этапе осталось решить, в какой степени новый класс будет доступным для клиентов. Можно полностью скрыть класс, создав для доступа делегирующие свойства (как это сделано сейчас)…
#|en|+ At this stage we need to decide how accessible the class should be for clients. We can hide the class entirely, creating delegating properties for access purposes (as is currently done)…
#|uk|+ На цьому етапі залишилося вирішити, якою мірою зробити новий клас доступним для клієнтів. Можна повністю приховати клас, створивши для доступу делегуючі властивості (як це зроблено зараз)…

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

#|ru|= …а можно удалить все эти свойства и сделать класс открытым.
#|en|= …or remove all these properties and make the class public.
#|uk|= …а можна видалити всі ці властивості і зробити клас відкритим.

Remove selected

#|ru| При этом нужно будет создать публичное свойство для связанного объекта, чтобы клиенты могли «достучаться» до него.
#|en| If so, we must create a public property for the associated object so that the clients can access it.
#|uk| При цьому потрібно буде створити публічну властивість для пов'язаного об'єкта, щоб клієнти могли достукатися до нього.

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

#|ru| Однако, решив сделать класс общедоступным, следует учесть опасности, связанные со ссылками. Как относиться к тому, что при открытии телефонного номера клиент сможет изменить код зоны? Такое изменение может произвести любой код, имеющий доступ к экземпляру класса через публичное свойство.
#|en| However, we should also consider the reference-related dangers inherent with making a class public. When opening a phone number, the client can change the area code. What do about this, a change that could be made by any code that has access to a class instance via the public property?
#|uk| Однак вирішивши зробити клас загальнодоступним, слід врахувати небезпеки, пов'язані з посиланнями. Як поставитися до того, що при відкритті телефонного номера клієнт може змінити код зони? Таку зміну може призвести будь-який код, який має доступ до примірника класу через публічну властивість.

#|ru| Возможны следующие варианты: <ul><li>Допускается изменение любым объектом любой части телефонного номера. В таком случае телефонный номер становится объектом-ссылкой, и следует рассмотреть <a href="/ru/change-value-to-reference">замену значения ссылкой</a>. Доступ к телефонному номеру осуществляется через экземпляр класса <code>Person</code>.</li><li>Вы не желаете, чтобы кто-либо мог изменить телефонный номер, кроме как посредством методов экземпляра класса <code>Person</code>. Можно сделать строковые свойства телефонного номера доступными только для чтения или обеспечить доступ к нему только через соответствующий метод.</li><li>Существует также возможность клонировать экземпляр класса <code>TelephoneNumber</code> перед тем, как предоставить к нему доступ. Но это может привести к недоразумениям, потому что люди будут думать, что могут изменить его значение. Кроме того, у клиентов могут возникнуть проблемы со ссылками, если телефонный номер часто передаётся.</li></ul>
#|en| The following options are possible: <ul><li>Any object can change any part of the phone number. In this case the phone number becomes a reference and you should look at <a href="/change-value-to-reference">Change Value to Reference</a>. Access to the phone number is implemented through an instance of <code>Person</code>.</li><li>You do not want anyone to be able to change a phone number except through the methods of an instance of the <code>Person</code> class. String properties of the phone number can be made read-only or access to it can be limited to an appropriate method.</li><li>You can also clone an instance of the <code>TelephoneNumber</code> class before providing access to it. But this can cause confusion because people will think that they can change this value. In addition, clients may have problems with references if the phone number is passed frequently.</li></ul>
#|uk| Можливі наступні варіанти: <ul><li>Допускається зміна будь-яким об'єктом будь-якою частині телефонного номеру. У такому випадку номер стає об'єктом-посиланням, і слід розглянути <a href="/uk/change-value-to-reference">заміну значення посиланням</a>. Доступ до телефонного номеру здійснюється через реалізацію класу <code>Person</code>.</li><li>Ви не бажаєте, щоб хто-небудь міг змінити телефонний номер, окрім як за допомогою методів екземпляра класу <code>Person</code>. Можна зробити строкові властивості телефонного номера тільки для читання. Або забезпечити доступ до нього тільки через відповідний метод.</li><li>Існує також можливість клонувати екземпляр класу <code>TelephoneNumber</code> перед тим, як надати його, але це може призвести до непорозумінь, тому що люди будуть думати , що можуть змінити його значення. При цьому можуть також виникнути проблеми з посиланнями у клієнтів, якщо телефонний номер часто передається.</li></ul>

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