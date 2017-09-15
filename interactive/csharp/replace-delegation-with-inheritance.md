replace-delegation-with-inheritance:csharp

###

1.ru. Сделайте класс подклассом класса-делегата.
1.en. Make the class a subclass of the delegate class.
1.uk. Зробіть клас підкласом класу-делегату.

2.ru. В поле, содержащее ссылку на объект-делегат, поставьте текущий объект.
2.en. Place the current object in a field containing a reference to the delegate object.
2.uk. У поле, що містить посилання на об'єкт-делегат, поставте поточний об'єкт.

3.ru. Один за другим удаляйте методы с простым делегированием. Если у них отличались названия, используйте <a href="/ru/rename-method">переименование метода</a> чтобы привести все методы к одному названию.
3.en. Delete the methods with simple delegation one by one. If their names were different, use <a href="/rename-method">Rename Method</a> to give all the methods a single name.
3.uk. Один за іншим видаляйте методи з простим делегуванням. Якщо у них відрізнялися назви, використайте <a href="/uk/rename-method">перейменування методу</a> щоб привести усі методи до однієї назви.

4.ru. Замените все обращения к полю-делегату обращениями к текущему объекту.
4.en. Replace all references to the delegate field with references to the current object.
4.uk. Замініть усі звернення до поля-делегата зверненнями до поточного об'єкту.

5.ru. Удалите поле-делегат.
5.en. Remove the delegate field.
5.uk. Видаліть поле-делегат.



###

```
public class Person
{
  public string Name
  { get; set; }
  public string LastName
  {
    get{ return Name.Substring(Name.LastIndexOf(' ') + 1); }
  }
}

public class Employee
{
  protected Person person;

  public string Name
  {
    get{ return person.Name; }
    set{ person.Name = value; }
  }

  public Employee()
  {
    this.person = new Person();
  }

  public override string ToString()
  {
    return "Emp: " + person.LastName;
  }
}
```

###

```
public class Person
{
  public string Name
  { get; set; }
  public string LastName
  {
    get{ return Name.Substring(Name.LastIndexOf(' ') + 1); }
  }
}

public class Employee: Person
{
  public override string ToString()
  {
    return "Emp: " + LastName;
  }
}
```

###

Set step 1

#|ru| У нас есть класс служащего <code>Employee</code>, который делегирует некоторою работу классу личности <code>Person</code>.
#|en| We have an <code>Employee</code> class that delegates certain work to the <code>Person</code> class.
#|uk| У нас є клас службовця <code>Employee</code>, який делегує деяку роботу класу особистості <code>Person</code>.

#|ru| Здесь наследование было бы уместнее, так как классу служащих необходимы практически все данные из <code>Person</code>.
#|en| Inheritance would be more appropriate here since the employee class needs practically all data from <code>Person</code>.
#|uk| Тут успадкування було б доречніше, так як класу службовців необхідні практично всі дані з <code>Person</code>.

#|ru| Начинаем рефакторинг с объявления наследования в классе <code>Employee</code>
#|en| Let's start refactoring by declaring the <code>Employee</code> as a subclass of <code>Person</code>.
#|uk| Починаємо рефакторинг з оголошення успадкування в класі <code>Employee</code>

Go to "class Employee|||"

Print ": Person"

#C|ru| Здесь стоит запустить компиляцию, чтобы убедиться в отсутствии конфликтующих методов. Они возникают, если методы с одинаковым именем возвращают значения различных типов или генерируют разные исключительные ситуации. Все проблемы такого рода исправляются с помощью <a href="/ru/rename-method">Переименования метода</a>.
#S В данном простом примере таких затруднений не возникает.

#C|en| Right after that we should compile and run autotests to make sure there are no conflicting methods. These issues can arise if identically named methods return values of different types or generate different exceptions. For all such issues, use  <a href="/rename-method">Rename Method</a>.
#S No such difficulties are present in our simple example.

#C|uk| Тут варто запустити компіляцію, щоб переконатися у відсутності конфліктуючих методів. Вони виникають, якщо методи з однаковим ім'ям повертають значення різних типів або генерують різні виняткові ситуації. Всі проблеми такого роду виправляються за допомогою <a href="/uk/rename-method">Перейменування методу</a>.
#S В даному простому прикладі таких ускладнень не виникає.

Set step 2

Select "new Person()"

#|ru| Следующим шагом заставляем поле делегирования ссылаться на сам объект.
#|en| Next, force the field, which contained reference to a <code>Person</code> object, to reference its own object. We will get rid of it later, but for now it will keep the code working.
#|uk| Наступним кроком змушуємо поле делегування посилатися на сам об'єкт.

Print "this"

Set step 3

Select:
```

  public string Name
  {
    get{ return person.Name; }
    set{ person.Name = value; }
  }

```

#|ru| Кроме того, мы должны удалить из <code>Employee</code> все делегирующие методы и свойства, такие, как <code>Name</code>. Если их оставить, возникнет ошибка переполнения стека, вызванная бесконечной рекурсией.
#|en| We also should remove all simple delegate methods from <code>Employee</code>, such as <code>getName</code> and <code>setName</code>. If we forget to remove them, a stack overflow will occur due to infinite recursion.
#|uk| Крім того, ми повинні видалити з <code>Employee</code> всі делегуючи методи і властивості, такі, як <code>Name</code>. Якщо їх залишити, виникне помилка переповнення стека, викликана нескінченної рекурсією.

Remove selected

Set step 4

Select "person."

#|ru| Далее следует избавиться от обращений к связанному полю, заменив их вызовами из собственного класса.
#|en| Then we get rid of delegation calls by calling our object's methods.
#|uk| Далі слід позбутися звернень до пов'язаного поля, замінивши їх викликами з власного класу.

Remove selected

Set step 5

Select:
```
  protected Person person;

  public Employee()
  {
    this.person = this;
  }


```

#|ru| После всех замен поле связанного объекта, а также код его инициализации становятся бесполезными и их можно убрать.
#|en| After all replacements, we can finally remove the field of the associated object and it's initialization code, which are no longer necessary.
#|uk| Після всіх замін поле пов'язаного об'єкта, а також код його ініціалізації стають даремними і їх можна прибрати.

Remove selected

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