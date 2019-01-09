extract-superclass:java

###

1.ru. Создайте абстрактный суперкласс.
1.en. Create an abstract superclass.
1.uk. Створіть абстрактний суперклас.

2.ru. Используйте <a href="/ru/pull-up-field">Подъём поля</a>, <a href="/ru/pull-up-method">Подъём метода</a> и <a href="/ru/pull-up-constructor-body">Подъём тела конструктора</a> для перемещения общей функциональности в суперкласс. Лучше начинать с полей, так как помимо общих полей, вам нужно будет перенести те из них, которые используются в общих методах.
2.en. Use <a href="/pull-up-field">Pull Up Field</a>, <a href="/pull-up-method">Pull Up Method</a>, and <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to move the common functionality to a superclass. Start with the fields, since in addition to the common fields you will need to move the fields that are used in the common methods.
2.uk. Використайте <a href="/uk/pull-up-field">Підйом поля</a>, <a href="/uk/pull-up-method">Підйом методу</a> і <a href="/uk/pull-up-constructor-body">Підйом тіла конструктора</a> для переміщення загальної функціональності в суперклас. Краще розпочинати з полів, оскільки окрім загальних полів, вам треба буде перенести ті з них, які використовуються в загальних методах.

3.ru. Стоит поискать места в клиентском коде, в которых можно заменить использование подклассов вашим общим классом (например, в объявлениях типов).
3.en. Look for places in the client code where use of subclasses can be replaced with your new class (such as in type declarations).
3.uk. Варто пошукати місця в клієнтському коді, в яких можна замінити використання підкласів вашим загальним класом (наприклад, в оголошеннях типів).



###

```
class Employee {
  private String name;
  private int annualCost;
  private String id;

  public Employee(String name, String id, int annualCost) {
    this.name = name;
    this.id = id;
    this.annualCost = annualCost;
  }
  public int getAnnualCost() {
    return annualCost;
  }
  public String getId() {
    return id;
  }
  public String getName() {
    return name;
  }
}

class Department {
  private String name;
  private Vector staff = new Vector();

  public Department(String name) {
    this.name = name;
  }
  public int getTotalAnnualCost() {
    int result = 0;
    Iterator i = staff.iterator();
    while (i.hasNext()) {
      Employee each = (Employee) i.next();
      result += each.getAnnualCost();
    }
    return result;
  }
  public int getHeadCount() {
    return staff.size();
  }
  public Enumeration getStaff() {
    return staff.elements();
  }
  public void addStaff(Employee arg) {
    staff.addElement(arg);
  }
  public String getName() {
    return name;
  }
}
```

###

```
abstract class Party {
  protected String name;

  protected Party(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
  public abstract int getAnnualCost();
  public abstract int getHeadCount();
}

class Employee extends Party {
  private int annualCost;
  private String id;

  public Employee(String name, String id, int annualCost) {
    super(name);
    this.id = id;
    this.annualCost = annualCost;
  }
  @Override public int getAnnualCost() {
    return annualCost;
  }
  public String getId() {
    return id;
  }
  @Override public int getHeadCount() {
    return 1;
  }
}

class Department extends Party {
  private Vector items = new Vector();

  public Department(String name) {
    super(name);
  }
  @Override public int getAnnualCost() {
    int result = 0;
    Iterator i = items.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      result += each.getAnnualCost();
    }
    return result;
  }
  @Override public int getHeadCount() {
    int headCount = 0;
    Iterator i = items.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      headCount += each.getHeadCount();
    }
    return headCount;
  }
  public Enumeration getItems() {
    return items.elements();
  }
  public void addItem(Party arg) {
    items.addElement(arg);
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

Select "private String name"

#|ru| У этих классов есть несколько общих черт. Во-первых, как у служащих, так и у отделов есть имена / названия.
#|en| These classes have several traits in common. First, as with employees, departments also have names.
#|uk| У цих класів є кілька спільних рис. По-перше, як у службовців, так і у відділів є імена / назви.

Select "private int annualCost"
+ Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"

#|ru| Во-вторых, у обоих классов есть годовой бюджет (правда подходы к его расчёту слегка различаются, но это не критично).
#|en| Second, for both classes there is an annual budget, although the calculation ways are slightly different.
#|uk| По-друге, у обох класів є річний бюджет (правда підходи до його розрахунку трошки розрізняються, але це не критично).

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

class Employee
```

Print:
```
abstract class Party {
}
```

Wait 500ms

Go to "class Employee|||"

Replace " extends Party"

Wait 500ms

Go to "class Department|||"

Replace " extends Party"

Wait 500ms

Set step 2

Select:
```
  private String name;
```

#|ru| Теперь всё готово, чтобы начать поднимать код в родительский класс. Обычно проще сначала выполнить <a href="/ru/pull-up-field">Подъем полей</a>.
#|en| Now we can start pulling up code to the parent class. Usually it is simpler to employ <a href="/pull-up-field">Pull Up Field</a> first.
#|uk| Тепер все готово, щоб почати піднімати код в батьківський клас. Зазвичай простіше спочатку виконати <a href="/uk/pull-up-field">Підйом поля</a>.

Go to start of "Party"

Print:
```

  protected String name;
```

Select:
```
  private String name;

```

Remove selected

Select whole of "getName"

#|ru| После этого можно применить <a href="/ru/pull-up-method">Подъем метода</a> к методам доступа этого поля.
#|en| Then use <a href="/pull-up-method">Pull Up Method</a> on the methods for accessing the field.
#|uk| Після цього можна застосувати <a href="/uk/pull-up-method">Підйом методу</a> до методів доступу цього поля.

Go to end of "Party"

Print:
```


  public String getName() {
    return name;
  }
```

Wait 500ms

Select in "Employee":
```

  public String getName() {
    return name;
  }
```
+ Select in "Department":
```

  public String getName() {
    return name;
  }
```

Remove selected

Select:
```
this.name = name;
```

#|ru| Лучше, чтобы поля были защищёнными от публики, но для этого сперва нужно выполнить <a href="/ru/pull-up-constructor-body">Подъем тела конструктора</a>, чтобы инициализировать их.
#|en| The fields should be protected from the public, but for this we must first do <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to initialize them.
#|uk| Краще, щоб поля були захищеними від публіки, але для цього спершу потрібно виконати <a href="/uk/pull-up-constructor-body">Підйом тіла конструктора</a>, щоб ініціалізувати їх.

Go to before "getName" in "Party"

Print:
```

  protected Party(String name) {
    this.name = name;
  }
```

#|ru| В подклассах теперь можно убрать инициализацию поля, заменив её вызовами родительского конструктора.
#|en| In the subclasses, we can go ahead and remove code initialization, placing parent constructor calls there instead.
#|uk| В підкласах тепер можна прибрати ініціалізацію поля, замінивши її викликами батьківського конструктора.

Select "this.name = name" in "Employee"
+ Select "this.name = name" in "Department"

Replace "super(name)"

#|ru| На этом перенос имён окончен, и можно взяться за годовой бюджет.
#|en| The name has been moved, which leaves us only the annual budget.
#|uk| На цьому перенесення імен закінчено, і можна взятися за річний бюджет.

Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"

#|ru| Методы <code>getTotalAnnualCost</code> и <code>getAnnualCost</code> имеют одинаковое назначение, поэтому у них должно быть одинаковое название. Применим <a href="/rename-method">Переименование метода</a>, чтобы привести их к одному и тому же названию.
#|en| The <code>getTotalAnnualCost</code> and <code>getAnnualCost</code> methods have the same purpose, so they should have the same name. Use <a href="/rename-method">Rename Method</a> to give them the same name.
#|uk| Методи <code>getTotalAnnualCost</code> і <code>getAnnualCost</code> мають однакове призначення, тому у них має бути однакова назва. Застосуємо <a href="/uk/rename-method">Перейменування методу</a>, щоб привести їх до однієї і тієї ж назвою.

Select name of "getTotalAnnualCost"

Replace "getAnnualCost"

#|ru| Тела методов пока что различаются, поэтому мы не можем применить <a href="/ru/pull-up-method">Подъем метода</a>. Однако мы можем объявить в родительском классе абстрактный метод с таким же именем.
#|en| The bodies of the methods are currently different, so we cannot use <a href="/pull-up-method">Pull Up Method</a>. On the other hand, we can declare an abstract method with the same name in the parent class.
#|uk| Тіла методів поки що розрізняються, тому ми не можемо застосувати <a href="/uk/pull-up-method">Підйом методу</a>. Однак ми можемо оголосити в батьківському класі абстрактний метод з таким же ім'ям.

Go to the end of "Party"

Print:
```

  public abstract int getAnnualCost();
```

Wait 500ms

Select "||| ||| public int getAnnualCost" in "Employee"
+Select "||| ||| public int getAnnualCost" in "Department"

Replace "  @Override"

Wait 500ms

Set step 3

Select name of "Party"

#|ru| Осуществив все эти изменения, давайте рассмотрим клиентов обоих классов, чтобы выяснить, можно ли изменить их так, чтобы они использовали новый родительский класс.
#|en| Having made these changes, let's look at clients of both classes to determine whether we can make them use the new parent class.
#|uk| Здійснивши всі ці зміни, давайте розглянемо клієнтів обох класів, щоб з'ясувати, чи можна змінити їх так, щоб вони використовували новий батьківський клас.

Select "|||Employee||| each = (|||Employee|||" in "Department"

#|ru| Одним из клиентов этих классов является сам класс <code>Department</code>, содержащий коллекцию классов служащих. Метод <code>getAnnualCost</code> использует только метод подсчёта годового бюджета, который теперь объявлен в <code>Party</code>.
#|en| One of the clients of the classes is the <code>Department</code> class itself, which contains a collection of employee classes. The <code>getAnnualCost</code> method uses only the annual budget calculation method, which is now declared in <code>Party</code>.
#|uk| Одним з клієнтів цих класів є сам клас <code>Department</code>, що містить колекцію класів службовців. Метод <code>getAnnualCost</code> використовує тільки метод підрахунку річного бюджету, який тепер оголошений в <code>Party</code>.

Print "Party"

Select name of "Department"

#|ru| Такое поведение открывает новую возможность. Мы можем рассматривать применение паттерна <a href="https://refactoring.guru/ru/design-patterns/composite">Компоновщик</a> к <code>Department</code> и <code>Employee</code>.
#|en| This behavior offers a new opportunity. We can consider using the <a href="https://refactoring.guru/design-patterns/composite">Composite</a> pattern on <code>Department</code> and <code>Employee</code>.
#|uk| Така поведінка відкриває нову можливість. Ми можемо розглядати застосування патерну <a href="https://refactoring.guru/design-patterns/composite">Компоновщик</a> до <code>Department</code> і <code>Employee</code>.

#|ru| Это позволит включать один отдел в другой. В результате создаётся новая функциональность, так что нельзя, строго говоря, называть эти действия рефакторингом.
#|en| That allows including one department in another. The result is new functionality, so strictly speaking this goes beyond refactoring.
#|uk| Це дозволить включати один відділ в інший. В результаті створюється нова функціональність, так що не можна, строго кажучи, називати ці дії рефакторингом.

Select "Vector" in "Department"

#|ru| Итак, если бы потребовалась компоновка, мы бы получили её, изменив тип списка <code>staff</code> на <code>Party</code>.
#|en| Be that as it may, if the Composite pattern were necessary, we would get it by changing the type of the <code>staff</code> field.
#|uk| Отже, якби знадобилася компоновка, ми б отримали її, змінивши тип списку <code>staff</code> на <code>Party</code>.

Select "|||staff||| =" in "Department"
+Select "|||staff|||." in "Department"

#|ru| После чего хорошо было бы дать списку более обобщённое название.
#|en| After that, it would be good to give the list a more generic name.
#|uk| Після чого добре було б дати списку більш узагальнена назва.

Replace "items"

Wait 500ms

Select name of "getStaff" in "Department"
+Select name of "addStaff" in "Department"

#|ru| И отредактировать соответствующим образом методы <code>getStaff</code> и <code>addStaff</code>.
#|en| And appropriately edit the <code>getStaff</code> and <code>addStaff</code> methods.
#|uk| І відредагувати відповідним чином методи <code>getStaff</code> і <code>addStaff</code>.

Select name of "getStaff" in "Department"

Replace "getItems"

Wait 500ms

Select name of "addStaff" in "Department"

Replace "addItem"

Wait 500ms

Select "Employee" in "Department"

Print "Party"

Wait 500ms

Select body of "getHeadCount"

#|ru| Для завершения компоновки осталось сделать рекурсивным метод <code>getHeadCount</code>.
#|en| To complete the Composite pattern, the <code>getHeadCount</code> method should be made recursive.
#|uk| Для завершення компонування залишилося зробити рекурсивним метод <code>getHeadCount</code>.

Print:
```
    int headCount = 0;
    Iterator i = items.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      headCount += each.getHeadCount();
    }
    return headCount;
```

#|ru| Но для того, чтобы этот подход заработал, необходимо создать аналогичный метод в <code>Employee</code>, который будет просто возвращать <code>1</code>.
#|en| But for this approach to work, we must create an equivalent method in <code>Employee</code> that simply returns <code>1</code>.
#|uk| Але для того, щоб цей підхід заробив, необхідно створити аналогічний метод в <code>Employee</code>, який буде просто повертати <code>1</code>.

Go to the end of "Employee"

Print:
```

  public int getHeadCount() {
    return 1;
  }
```

Go to the end of "Party"

#|ru| После чего объявить этот метод абстрактным в родительском классе.
#|en| After that this method should also be declared abstract in the parent class.
#|uk| Після чого оголосити цей метод абстрактним в батьківському класі.

Print:
```

  public abstract int getHeadCount();
```

Wait 500ms

Select "||| ||| public int getHeadCount" in "Employee"
+Select "||| ||| public int getHeadCount" in "Department"

Replace "  @Override"

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