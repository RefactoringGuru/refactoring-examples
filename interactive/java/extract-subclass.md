extract-subclass:java

###

1. Создайте новый подкласс из интересующего вас класса.

2. Если для создания объектов из подкласса будут нужны какие-то дополнительные данные, создайте конструктор и дополните его нужными параметрами. Не забудьте вызвать родительскую реализацию конструктора.

3. Найдите все вызовы конструктора родительского класса. В тех случаях, когда требуется функциональность подкласса, замените родительский конструктор конструктором подкласса.

4. Переместите нужные методы и поля из родительского класса в подкласс. Используйте для этого <a href="/push-down-method">спуск метода</a> и <a href="/push-down-field">спуск поля</a>. Проще всего начинать перенос с методов. Так  поля будут доступны для них все время:  из родительского класса до переноса, и из самого подкласса после окончания переноса.

5. После того как подкласс готов, найдите все старые поля, которые управляли тем, какой набор функций должен выполняться. Эти поля можно удалить, заменив полиморфизмом все условные операторы, в которых они использовались.



###

```
class JobItem {
  private int quantity;
  private int unitPrice;
  private Employee employee;
  private boolean isLabor;

  public JobItem(int quantity, int unitPrice, boolean isLabor, Employee employee) {
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.isLabor = isLabor;
    this.employee = employee;
  }
  public int getTotalPrice() {
    return quantity * getUnitPrice();
  }
  public int getQuantity() {
    return quantity;
  }
  public int getUnitPrice() {
    return (isLabor) ? employee.getRate() : unitPrice;
  }
  public Employee getEmployee() {
    return employee;
  }
}

class Employee {
  private int rate;
  public Employee(int rate) {
    this.rate = rate;
  }
  public int getRate() {
    return rate;
  }
}

// Somewhere in client code
Employee kent = new Employee(50);
JobItem j1 = new JobItem(5, 0, true, kent);
JobItem j2 = new JobItem(15, 10, false, null);
int total = j1.getTotalPrice() + j2.getTotalPrice();
```

###

```
abstract class JobItem {
  private int quantity;

  public JobItem(int quantity) {
    this.quantity = quantity;
  }
  public int getTotalPrice() {
    return quantity * getUnitPrice();
  }
  public int getQuantity() {
    return quantity;
  }
  public abstract int getUnitPrice();
}
class PartsItem extends JobItem {
  protected int unitPrice;
  
  public LaborItem(int quantity, int unitPrice) {
    super(quantity);
    this.unitPrice = unitPrice;
  }
  public int getUnitPrice() {
    return unitPrice;
  }
}
class LaborItem extends JobItem {
  protected Employee employee;

  public LaborItem(int quantity, Employee employee) {
    super(quantity);
    this.employee = employee;
  }
  public Employee getEmployee() {
    return employee;
  }
  public int getUnitPrice() {
    return employee.getRate();
  }
}

class Employee {
  private int rate;
  public Employee(int rate) {
    this.rate = rate;
  }
  public int getRate() {
    return rate;
  }
}

// Somewhere in client code
Employee kent = new Employee(50);
JobItem j1 = new LaborItem(5, kent);
JobItem j2 = new PartsItem(15, 10);
int total = j1.getTotalPrice() + j2.getTotalPrice();
```

###

Set step 1

# Начнём с класса выполняемых работ, который определяет цены операций, выполняемых в местном гараже.

Select name of "getUnitPrice"

# Цена работы может состоять как из фиксированной цены (например, платы за заказ каких-то деталей), так и оплаты времени механика, цена которой может браться напрямую из класса <code>Employee</code>.

# При этом цена вычисляется разными способами в пределах одного класса, что меня и смущает.

# Было бы здорово выделить из этого класса подкласс <code>LaborItem</code>, и переместить в него все поведения, связанные с ручной работой, а в оригинальном классе оставить только расчёты фиксированной работы.

# Итак, создаём новый класс.

Go to after "JobItem"

Print:
```

class LaborItem extends JobItem {
}
```


Set step 2

# Теперь, начинаем спускать методы, свойственные только для подкласса.

Select name of "public JobItem"

# Прежде всего, нам нужен конструктор для этого класса, потому что в <code>JobItem</code> нет нужного нам конструктора, который бы принимал только объект рабочего и количество потраченных часов.

# Пока что для этого скопируем сигнатуру родительского конструктора.

Go to the start of "LaborItem"

Print:
```

  public LaborItem(int quantity, int unitPrice, boolean isLabor, Employee employee) {
    super(quantity, unitPrice, isLabor, employee);
  }
```

# Этого достаточно, чтобы новый подкласс перестал выдавать ошибки. Однако этот конструктор путаный: одни аргументы нужны для <code>LaborItem</code>, а другие – нет. Мы это исправим, но позднее.

Set step 3

Select 1st "new JobItem"

# На следующем этапе осуществляется поиск обращений к конструктору <code>JobItem</code> и случаи, когда вместо него следует вызывать конструктор <code>LaborItem</code>.

Print "new LaborItem"

Select "|||JobItem||| j1"

# На данном этапе мы не трогаем тип переменной, а изменил лишь тип конструктора.

# Это вызвано тем, что новый тип следует использовать только там, где это необходимо. В данный момент у нас нет специфического интерфейса для подкласса, поэтому лучше пока не объявлять какие-либо разновидности.

Select parameters of "public JobItem"
+ Select parameters of "public LaborItem"

# Теперь подходящее время, чтобы привести в порядок списки параметров конструктора. К каждому из них применим <a href="/remove-parameter">удаление параметров</a>.

Select visibility of "public JobItem"

# Сначала я обращаюсь к родительскому классу. Я создаю новый конструктор и объявляю прежний защищенным (подклассу он по-прежнему нужен):

Print "protected"

Go to after "protected JobItem"

Print:
```

  public JobItem(int quantity, int unitPrice) {
    this(quantity, unitPrice, false, null)
  }
```

Select ", false, null"

# Внешние вызовы конструктора теперь должны использовать новый конструктор.

Remove selected

#C Выполним компиляцию и тестирование, чтобы удостовериться в отсутствии ошибок.

#S Всё отлично, можем продолжать.

Select "LaborItem(int quantity, |||int unitPrice, boolean isLabor, |||Employee employee)"

# Теперь применим <a href="/remove-parameter">удаление параметров</a> к конструктору подкласса, чтобы избавиться от ненужных параметров.

Remove selected

Wait 500ms

Select "new LaborItem(5, |||0, true, |||kent)"

Remove selected

Wait 500ms

Select "super(quantity, |||unitPrice|||, isLabor, employee)"

Replace "0"

Wait 500ms

Select "super(quantity, 0, |||isLabor|||, employee)"

Replace "true"

Set step 4

Select name of "JobItem"

# Теперь мы можем начать спускать в подкласс части <code>JobItem</code>. Сперва займёмся методами.

Select whole of "getEmployee"

# Начнём с применения <a href="/push-down-method">Спуска метода</a> к <code>getEmployee</code>.

Remove selected

Go to the end of "LaborItem"

Print:
```

  public Employee getEmployee() {
    return employee;
  }
```

Select "|||private||| Employee employee;"

# Поскольку поле <code>employee</code> позднее будет спущено в подкласс, пока объявим его защищенным.

Print "protected"

Select ", Employee employee" in "JobItem"
+ Select "|||, employee|||)"


# После того как поле <code>employee</code> защищено, мы можем привести в порядок конструкторы, чтобы <code>employee</code> инициализировалось только в подклассе, куда оно спускается.

Remove selected

Select:
```
    this.employee = employee;

```

Remove selected

Go to the end of "public LaborItem"

Print:
```

    this.employee = employee;
```

Set step 5

Select "private boolean isLabor;"

# Поле <code>isLabor</code> применяется для указания информации, которая теперь присуща иерархии, поэтому можно удалить это поле.<br/><br/>Лучше всего сделать это, сначала применив <a href="/self-encapsulate-field">Самоинкапсуляцию поля</a>, а затем изменив метод доступа, чтобы применить полиморфный константный метод (это такой метод, посредством которого каждая реализация возвращает (своё) фиксированное значение)

# Итак, объявим геттеры <code>isLabor</code> в обоих классах.

Go to the end of "JobItem"

Print:
```

  protected boolean isLabor() {
    return false;
  }
```

Go to the end of "LaborItem"

Print:
```

  protected boolean isLabor() {
    return true;
  }
```

# Теперь заменим использование поля вызовами этих геттеров.

Select "isLabor" in "getUnitPrice"

Replace "isLabor()"

Select:
```
  private boolean isLabor;

```
+ Select ", boolean isLabor"
+ Select "|||, true|||)"
+ Select:
```
    this.isLabor = isLabor;

```

# После этого можно избавиться от поля <code>isLabor</code>.

Remove selected

# После этих замен, конструкторы в <code>JobItem</code> стали идентичными, поэтому их можно склеить.

Select whole "public JobItem"

Remove selected

Select visibility of "protected JobItem"

Replace "public"

Select "isLabor" in "getUnitPrice"

# Теперь можно посмотреть на пользователей методов <code>isLabor</code>. Они должны быть подвергнуты рефакторингу <a href="/replace-conditional-with-polymorphism">Замена условного оператора полиморфизмом</a>.

Select body of "getUnitPrice"

Replace "    return unitPrice;"

Wait 500ms

Go to the end of "LaborItem"

Print:
```

  public int getUnitPrice() {
    return employee.getRate();
  }
```

Select whole "isLabor" in "JobItem"
+Select whole "isLabor" in "LaborItem"

# После этого становится видно, что методы <code>isLabor</code> теперь нигде не используются и их можно удалить из всех классов.

Remove selected

Select name of "JobItem"

# После того как группа методов, использующих некоторые данные, перемещена в подкласс, к этим данным можно применить <a href="/push-down-field">Спуск поля</a>. Если я не могу применить его из-за того, что эти данные используются некоторым методом, это свидетельствует о необходимости продолжить работу с методами, применяя <a href="/push-down-method">Спуск метода</a> или <a href="/replace-conditional-with-polymorphism">Замену условного оператора полиморфизмом</a>.

# В нашем случае, все готово, чтобы переместить поле <code>employee</code> в <code>LaborItem</code>.

Select:
```
  protected Employee employee;

```

Remove selected

Go to start of "LaborItem"

Print:
```

  protected Employee employee;

```

Select:
```
  private int unitPrice;
```

#C Выполним компиляцию и тестирование, чтобы удостовериться в отсутствии ошибок.

#S Всё отлично, можем продолжать.

# Итак, мы справились с извлечением <code>LaborItem</code>. Но есть ещё один момент. Поскольку цена запчастей <code>unitPrice</code> используется только в классе <code>JobItem</code>, и не нужна в <code>LaborItem</code>, мы можем пойти дальше и снова применить к <code>JobItem</code> <a href="/extract-subclass">Выделение подкласса</a> и создать класс, представляющий запчасти.

Go to after "JobItem"

Print:
```

class PartsItem extends JobItem {
  protected int unitPrice;
  
  public LaborItem(int quantity, int unitPrice) {
    super(quantity);
    this.unitPrice = unitPrice;
  }
  public int getUnitPrice() {
    return unitPrice;
  }
}
```

Wait 500ms

Select "new JobItem"

Replace "new PartsItem"

Wait 500ms

Select in "JobItem":
```
  private int unitPrice;

```
+Select ", 0"
+Select ", int unitPrice" in "JobItem"
+Select in "JobItem":
```
    this.unitPrice = unitPrice;

```

Remove selected

Wait 500ms

Select whole "getUnitPrice"

Replace:
```
  public abstract int getUnitPrice();

```


Select type of "JobItem"

# В итоге класс <code>JobItem</code> станет абстрактным.

Replace "abstract class"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.