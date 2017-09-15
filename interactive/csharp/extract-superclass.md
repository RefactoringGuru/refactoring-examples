extract-superclass:csharp

###

1.ru. Создайте абстрактный базовый класс.
1.en. Create an abstract base class.
1.uk. Створіть абстрактний базовий клас.

2.ru. Используйте <a href="/ru/pull-up-field">Подъём поля</a>, <a href="/ru/pull-up-method">Подъём метода</a> и <a href="/ru/pull-up-constructor-body">Подъём тела конструктора</a> для перемещения общей функциональности в базовый класс. Лучше начинать со свойств(полей), т.к. помимо самостоятельных свойств, вам нужно будет перенести те из них, которые используются в общих методах.
2.en. Use <a href="/pull-up-field">Pull Up Field</a>, <a href="/pull-up-method">Pull Up Method</a>, and <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to move the common functionality to the base class. Start with the properties(fields), since in addition to the common properties you will need to move the properties that are used in the common methods.
2.uk. Використайте <a href="/uk/pull-up-field">Підйом поля</a>, <a href="/uk/pull-up-method">Підйом методу</a> і <a href="/uk/pull-up-constructor-body">Підйом тіла конструктора</a> для переміщення загальної функціональності в базовий клас. Краще починати зі властивостей (полів), оскільки окрім самостійних властивостей, вам треба буде перенести ті з них, які використовуються в загальних методах.

3.ru. Стоит поискать места в клиентском коде, в которых можно заменить использование подклассов вашим общим классом (например, в объявлениях типов).
3.en. Look for places in the client code where use of subclasses can be replaced with your new class (such as in type declarations).
3.uk. Варто пошукати місця в клієнтському коді, в яких можна замінити використання підкласів вашим загальним класом (наприклад, в оголошеннях типів).



###

```
public class Employee
{
  private int annualCost;

  public string Name { get; private set; }
  public string Id { get; private set; }

  public Employee(string name, string id, int annualCost)
  {
    Name = name;
    Id = id;
    this.annualCost = annualCost;
  }

  public int GetAnnualCost()
  {
    return annualCost;
  }
}

public class Department
{
  private List<Employee> staff = new List<Employee>();

  public string Name { get; private set; }
  public int HeadCount
  {
    get{ return staff.Count; }
  }
  public IList<Employee> Staff
  {
    get{ return staff.AsReadOnly(); }
  }

  public Department(string name)
  {
    Name = name;
  }

  public void AddStaff(Employee item)
  {
    staff.Add(item);
  }
  public int GetTotalAnnualCost()
  {
    return staff.Sum(i => i.GetAnnualCost());
  }
}
```

###

```
public abstract class Party
{
  public string Name { get; protected set; }
  public abstract int HeadCount { get; }

  protected Party(string name)
  {
    Name = name;
  }

  public abstract int GetAnnualCost();
}

public class Employee: Party
{
  private int annualCost;

  public string Id { get; private set; }
  public override int HeadCount
  {
    get{ return 1; }
  }

  public Employee(string name, string id, int annualCost): base(name)
  {
    Id = id;
    this.annualCost = annualCost;
  }

  public override int GetAnnualCost()
  {
    return annualCost;
  }
}

public class Department: Party
{
  private List<Party> items = new List<Party>();

  public override int HeadCount
  {
    get{ return items.Sum(i => i.HeadCount); }
  }
  public IList<Party> Items
  {
    get{ return items.AsReadOnly(); }
  }

  public Department(string name): base(name)
  {
  }

  public void AddItem(Party item)
  {
    items.Add(item);
  }
  public override int GetAnnualCost()
  {
    return items.Sum(i => i.GetAnnualCost());
  }
}
```

###

Set step 1

Select name of "Employee"
+ Select name of "Department"

#|ru| Рассмотрим этот рефакторинг на примере классов служащего и отдела.
#|en| Let's look at <i>Extract Superclass</i> using the example of employees and their department.
#|uk| Розглянемо цей рефакторинг на прикладі класів службовця та відділу.

Select "|||Name||| { get"

#|ru| У этих классов есть несколько общих черт. Во-первых, как у служащих, так и у отделов есть имена / названия.
#|en| These classes have several traits in common. First, as with employees, departments also have names.
#|uk| У цих класів є кілька спільних рис. По-перше, як у службовців, так і у відділів є імена / назви.

Select name of "GetAnnualCost"
+ Select name of "GetTotalAnnualCost"

#|ru| Во-вторых, у обоих классов есть годовой бюджет (правда подходы к его расчёту слегка различаются, но это не критично).
#|en| Second, for both classes there is an annual budget, although the calculation ways are slightly different.
#|uk| По-друге, у обох класів є річний бюджет (правда підходи до його розрахунку трошки розрізняються, але це не критично).

+Select "|||Name||| { get"

#|ru| Так что у нас есть все основания, чтобы вынести описанные моменты в общий родительский класс.
#|en| For this reason, it would be good to extract these aspects to a shared parent class.
#|uk| Так що у нас є всі підстави, щоб винести описані моменти в загальний батьківський клас.

Go to before "Employee"

#|ru| Итак, на первом этапе создаём новый родительский класс, а имеющиеся классы определяем как его подклассы.
#|en| To start, we create a new parent class, and we define the existing classes as subclasses of it.
#|uk| Отже, на першому етапі створюємо новий батьківський клас, а наявні класи визначаємо як його підкласи.

Print:
```



```

Go to:
```
|||

public class Employee
```

Print:
```
public abstract class Party
{
}
```

Wait 500ms

Go to "class Employee|||"

Replace ": Party"

Wait 500ms

Go to "class Department|||"

Replace ": Party"

Wait 500ms

Set step 2

Select "public |||string Name|||"

#|ru| Теперь всё готово, чтобы начать поднимать код в родительский класс. Обычно проще сначала выполнить <a href="/ru/pull-up-field">Подъем полей</a> (в нашем случае - свойств).
#|en| Now we can start pulling up code to the parent class. Usually it is simpler to employ <a href="/pull-up-field">Pull Up Field</a> (in our case - property) first.
#|uk| Тепер все готово, щоб почати піднімати код в батьківський клас. Зазвичай простіше спочатку виконати <a href="/uk/pull-up-field">Підйом поля</a> (у нашому випадку - властивостей).

Go to start of "Party"

Print:
```

  public string Name { get; protected set; }
```

Select:
```
  public string Name { get; private set; }

```

Remove selected

Wait 500ms

Select "Name = name;"

#|ru| Дальше нужно выполнить <a href="/ru/pull-up-constructor-body">Подъем тела конструктора</a>, чтобы перенести код инициализации свойств в родительский класс.
#|en| Next we need do <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to move the initialization code for properties to the parent class.
#|uk| Далі потрібно виконати <a href="/uk/pull-up-constructor-body">Підйом тіла конструктора</a>, щоб перенести код ініціалізації властивостей в батьківський клас.

Go to end of "Party"

Print:
```


  protected Party(string name)
  {
    Name = name;
  }
```

Select in "Employee":
```
    Name = name;

```
+Select in "Department":
```
    Name = name;

```

#|ru| После чего можно заменить инициализацию свойств в подклассах на вызовы родительского конструктора.
#|en| Then we can replace the initialization of properties in subclasses with a call to the parent constructor.
#|uk| Після чого можна замінити ініціалізацію властивостей в підкласах на виклики батьківського конструктора.

Remove selected

Wait 500ms

Go to "int annualCost)|||"

Print ": base(name)"

Wait 500ms

Go to "Department(string name)|||"

Print ": base(name)"

#|ru| На этом перенос имён окончен, и можно взяться за годовой бюджет.
#|en| The name has been moved, which leaves us only the annual budget.
#|uk| На цьому перенесення імен закінчено, і можна взятися за річний бюджет.

Select name of "GetAnnualCost"
+ Select name of "GetTotalAnnualCost"

#|ru| Методы <code>GetAnnualCost()</code> и <code>GetTotalAnnualCost()</code> имеют одинаковое назначение, поэтому у них должно быть одинаковое название. Применим <a href="/rename-method">Переименование метода</a>, чтобы привести их к одному и тому же названию.
#|en| The <code>GetAnnualCost()</code> and <code>GetTotalAnnualCost()</code> methods have the same purpose, so they should have the same name. Use <a href="/rename-method">Rename Method</a> to give them the same name.
#|uk| Методи <code>GetAnnualCost()</code> і <code>GetTotalAnnualCost()</code> мають однакове призначення, тому у них має бути однакова назва. Застосуємо <a href="/uk/rename-method">Перейменування методу</a>, щоб привести їх до однієї і тієї ж назвою.

Select name of "GetTotalAnnualCost"

Replace "GetAnnualCost"

#|ru| Тела методов пока что различаются, поэтому мы не можем применить <a href="/ru/pull-up-method">Подъем метода</a>. Однако мы можем объявить в родительском классе абстрактный метод с таким же именем.
#|en| The bodies of the methods are currently different, so we cannot use <a href="/pull-up-method">Pull Up Method</a>. On the other hand, we can declare an abstract method with the same name in the parent class.
#|uk| Тіла методів поки що розрізняються, тому ми не можемо застосувати <a href="/uk/pull-up-method">Підйом методу</a>. Однак ми можемо оголосити в батьківському класі абстрактний метод з таким же ім'ям.

Go to the end of "Party"

Print:
```


  public abstract int GetAnnualCost();
```

Wait 500ms

Select "public||| |||int GetAnnualCost" in "Employee"
+Select "public||| |||int GetAnnualCost" in "Department"

Replace " override "

Wait 500ms

Set step 3

Select name of "Party"

#|ru| Осуществив все эти изменения, давайте рассмотрим клиентов обоих классов, чтобы выяснить, можно ли изменить их так, чтобы они использовали новый родительский класс.
#|en| Having made these changes, let's look at clients of both classes to determine whether we can make them use the new parent class.
#|uk| Здійснивши всі ці зміни, давайте розглянемо клієнтів обох класів, щоб з'ясувати, чи можна змінити їх так, щоб вони використовували новий батьківський клас.

Select "private |||List<Employee> staff|||" in "Department"
+Select "i.|||GetAnnualCost()|||" in "GetAnnualCost" of "Department"

#|ru| Одним из клиентов этих классов является сам класс <code>Department</code>, содержащий коллекцию классов служащих. Метод <code>GetAnnualCost()</code> использует метод подсчёта годового бюджета, который теперь объявлен в <code>Party</code>.
#|en| One of the clients of the classes is the <code>Department</code> class itself, which contains a collection of employee classes. The <code>GetAnnualCost()</code> method uses the annual budget calculation method, which is now declared in <code>Party</code>.
#|uk| Одним з клієнтів цих класів є сам клас <code>Department</code>, що містить колекцію класів службовців. Метод <code>GetAnnualCost()</code> використовує метод підрахунку річного бюджету, який тепер оголошений в <code>Party</code>.

#|ru| Такое поведение открывает новую возможность. Мы можем рассматривать применение паттерна <a href="https://refactoring.guru/ru/design-patterns/composite">Компоновщик</a> к <code>Department</code> и <code>Employee</code>.
#|en| This behavior offers a new opportunity. We can consider using the <a href="https://refactoring.guru/design-patterns/composite">Composite</a> pattern on <code>Department</code> and <code>Employee</code>.
#|uk| Така поведінка відкриває нову можливість. Ми можемо розглядати застосування патерну <a href="https://refactoring.guru/design-patterns/composite">Компоновщик</a> до <code>Department</code> і <code>Employee</code>.

#|ru| Это позволит включать один отдел в другой. В результате создаётся новая функциональность, так что нельзя, строго говоря, называть эти действия рефакторингом.
#|en| That allows including one department in another. The result is new functionality, so strictly speaking this goes beyond refactoring.
#|uk| Це дозволить включати один відділ в інший. В результаті створюється нова функціональність, так що не можна, строго кажучи, називати ці дії рефакторингом.

Select "private List<|||Employee|||>" in "Department"
+Select "List<|||Employee|||>()" in "Department"

#|ru| Итак, если бы потребовалась компоновка, мы бы получили её, изменив тип списка <code>staff</code> на <code>Party</code>.
#|en| Be that as it may, if the Composite pattern were necessary, we would get it by changing the type of the <code>staff</code> field.
#|uk| Отже, якби знадобилася компоновка, ми б отримали її, змінивши тип списку <code>staff</code> на <code>Party</code>.

Print "Party"

Wait 500ms

Select "IList<|||Employee|||>"

Replace "Party"

Wait 500ms

Select "|||staff||| =" in "Department"
+Select "|||staff|||." in "Department"

#|ru| После чего хорошо было бы дать списку более обобщённое название.
#|en| After that, it would be good to give the list a more generic name.
#|uk| Після чого добре було б дати списку більш узагальнена назва.

Replace "items"

Wait 500ms

Select "> |||Staff|||"

Replace "Items"

Wait 500ms

Select name of "AddStaff" in "Department"

#|ru| И отредактировать соответствующим образом метод <code>AddStaff()</code>.
#|en| And appropriately edit the <code>AddStaff()</code> method.
#|uk| І відредагувати відповідним чином метод <code>AddStaff()</code>.

Replace "AddItem"

Wait 500ms

Select "Employee" in "Department"

Print "Party"

Wait 500ms

Select "return items.Count;" in "Department"

#|ru| Для завершения компоновки осталось сделать рекурсивным геттер свойства <code>HeadCount</code>.
#|en| To complete the Composite pattern, the <code>HeadCount</code> getter should be made recursive.
#|uk| Для завершення компонування залишилося зробити рекурсивним геттер властивості <code>HeadCount</code>.

Print "return items.Sum(i => i.HeadCount);"

#|ru| Но для того, чтобы этот подход заработал, необходимо создать аналогичное свойство в <code>Employee</code>, геттер которого будет просто возвращать <code>1</code>.
#|en| But for this approach to work, we must create an equivalent property in <code>Employee</code> that simply returns <code>1</code>.
#|uk| Але для того, щоб цей підхід заробив, необхідно створити аналогічну властивість в <code>Employee</code>, геттер якої буде просто повертати <code>1</code>.

Go to "private set; }|||" in "Employee"

Print:
```

  public int HeadCount
  {
    get{ return 1; }
  }
```

Go to "protected set; }|||" in "Party"

#|ru| После чего объявить это свойство абстрактным в родительском классе.
#|en| After that this property should also be declared abstract in the parent class.
#|uk| Після чого оголосити цю властивість абстрактної в батьківському класі.

Print:
```

  public abstract int HeadCount { get; }
```

Wait 500ms

Select "public||| |||int HeadCount" in "Employee"
+Select "public||| |||int HeadCount" in "Department"

Replace " override "

Wait 500ms

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