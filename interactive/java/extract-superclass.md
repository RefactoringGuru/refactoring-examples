extract-superclass:java

###

1. Создайте абстрактный суперкласс.

2. Используйте <a href="/pull-up-field">подъём поля</a>, <a href="/pull-up-method">подъём метода</a> и <a href="/pull-up-constructor-body">подъём тела конструктора</a> для перемещения общей функциональности в суперкласс. Лучше начинать с полей, т.к. помимо общих полей, вам нужно будет перенести те из них, которые используются в общих методах.

3. Стоит поискать места в клиентском коде, в которых можно заменить использование подклассов вашим общим классом (например, в объявлениях типов).



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

  public Department (String name) {
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
  public int getAnnualCost() {
    return annualCost;
  }
  public String getId() {
    return id;
  }
  public int getHeadCount() {
    return 1;
  }
}

class Department extends Party {
  private Party staff = new Vector();

  public Department (String name) {
    super(name);
  }
  public int getAnnualCost() {
    int result = 0;
    Iterator i = staff.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      result += each.getAnnualCost();
    }
    return result;
  }
  public int getHeadCount() {
    int headCount = 0;
    Iterator i = staff.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      headCount += each.getHeadCount();
    }
  }
  public Enumeration getStaff() {
    return staff.elements();
  }
  public void addStaff(Party arg) {
    staff.addElement(arg);
  }
}
```

###

Set step 1

Select name of "Employee"
+ Select name of "Department"

# Рассмотрим этот рефакторинг на примере классов служащих и отдела.

Select "private String name"

# У этих классов есть несколько общих черт. Во-первых, как у служащих, так и у отделов есть имена или названия.

Select "private int annualCost"
+ Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"

# Во-вторых, для обоих есть годовой бюджет (annualcost), хотя методы их расчёта слегка различаются.

# Посему, имеет смысл выделить эти моменты в общий родительский класс.

Go to before "Employee"

# Итак, на первом этапе создаём новый родительский класс, а имеющиеся классы определяем как его подклассы.

Print:
```
abstract class Party {
}


```

Go to "class Employee|||"

Print " extends Party"

Wait 500ms

Go to "class Department|||"

Print " extends Party"

Select:
```
  private String name;
```

# Теперь всё готово, чтобы начать поднимать функции в родительский класс. Обычно проще сначала выполнить <a href="/pull-up-field">Подъем полей</a>.

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

# После этого можно применить <a href="/pull-up-method">Подъем метода</a> к методам доступа этого поля.

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

# Лучше, чтобы поля были защищёнными от публики, но для этого сперва нужно выполнить <a href="/pull-up-constructor-body">Подъем тела конструктора</a>, чтобы инициализировать их.

Go to before "getName" in "Party"

Print:
```

  protected Party(String name) {
    this.name = name;
  }
```

# В подклассах теперь можно убрать инициализацию поля, заменив ее вызовами родительского конструктора.

Select "this.name = name" in "Employee"
+ Select "this.name = name" in "Department"

Print "super(name)"

# На этом перенос имени окончен и можно взяться за годовой бюджет.

Select name of "getAnnualCost"
+ Select name of "getTotalAnnualCost"

# Методы <code>getTotalAnnualCost</code> и <code>getAnnualCost</code> имеют одинаковое назначение, поэтому у них должно быть одинаковое название. Сначала применим <a href="/rename-method">Переименование метода</a>, чтобы привести их к одному и тому же названию.

Select name of "getTotalAnnualCost"

Replace "getAnnualCost"

# тела методов пока что различаются, поэтому мы не можем применить <a href="/pull-up-method">Подъем метода</a>. С другой стороны, мы можем объявить в родительском классе абстрактный метод с таким же именем.

Go to the end of "Party"

Print:
```

  public abstract int getAnnualCost();
```

Select name of "Party"

# Осуществив все эти изменения, я рассматриваю клиентов обоих классов, чтобы выяснить, можно ли изменить их так, чтобы они использовали новый родительский класс.

Select "Employee" in "getAnnualCost" of "Department"

# Одним из клиентов этих классов является сам класс <code>Department</code>, содержащий коллекцию классов служащих. Метод <code>getAnnualCost</code> использует только метод подсчёта годового бюджета, который теперь объявлен в <code>Party</code>.

Print "Party"

Select name of "Department"

# Такое поведение открывает новую возможность. Я могу рассматривать применение паттерна <a href="http://sourcemaking.com/design_patterns/composite">компоновщик</a> к <code>Department</code> и <code>Employee</code>.

# Это позволит мне включать один отдел в другой. В результате создаётся новая функциональность, так что нельзя, строго говоря, назвать это рефакторингом.

Select "|||Vector||| staff"

# Тем не менее, если бы требовалась компоновка, я получил бы её, изменив тип поля <code>staff</code>, чтобы картина была нагляднее.

Print "Party"

Select "Employee" in "Department"

# Такое изменение повлекло бы соответствующее изменение имени <code>addStaff</code> и замену параметра на <code>Party</code>.

Print "Party"

Select body of "getHeadCount"

# В окончательной редакции метод <code>headCount</code> должен быть сделан рекурсивным.

Print:
```
    int headCount = 0;
    Iterator i = staff.iterator();
    while (i.hasNext()) {
      Party each = (Party) i.next();
      headCount += each.getHeadCount();
    }
```

# Но для того, чтобы этот метод заработал, необходимо объявить аналогичный метод в <code>Employee</code>, который просто возвращает <code>1</code>.

Go to the end of "Employee"

Print:
```

  public int getHeadCount() {
    return 1;
  }
```

Go to the end of "Party"

# Этот метод теперь тоже следует объявить абстрактным в родительском классе.

Print:
```

  public abstract int getHeadCount();
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.