extract-subclass:csharp

###

1.ru. Создайте новый подкласс из интересующего вас класса.
1.en. Create a new subclass from the class of interest.
1.uk. Створіть новий підклас з класу, що вас цікавить.

2.ru. Если для создания объектов из подкласса будут нужны какие-то дополнительные данные, создайте конструктор и дополните его нужными параметрами. Не забудьте вызвать родительскую реализацию конструктора.
2.en. If you need additional data to create objects from a subclass, create a constructor and add the necessary parameters to it. Do not forget to call the constructor's parent implementation.
2.uk. Якщо для створення об'єктів підкласу будуть потрібні якісь додаткові дані, створіть конструктор і доповніть його потрібними параметрами. Не забудьте викликати батьківську реалізацію конструктора, використовуючи <code>parent</code>.

3.ru. Найдите все вызовы конструктора родительского класса. В тех случаях, когда требуется функциональность подкласса, замените родительский конструктор конструктором подкласса.
3.en. Find all calls to the constructor of the parent class. When the functionality of a subclass is necessary, replace the parent constructor with the subclass constructor.
3.uk. Знайдіть усі виклики конструктора батьківського класу. У тих випадках, коли потрібна також функціональність підкласу, замініть батьківський конструктор конструктором підкласу.

4.ru. Переместите нужные методы, поля и свойства из родительского класса в подкласс. Используйте для этого <a href="/ru/push-down-method">спуск метода</a> и <a href="/ru/push-down-field">спуск поля</a>. Проще всего начинать перенос с методов, т.к. тогда свойства и поля будут доступны для них все время: из родительского класса до переноса, и из самого подкласса после окончания переноса.
4.en. Move the necessary methods, fields and properties from the parent class to the subclass. Do this via <a href="/push-down-method">Push Down Method</a> and <a href="/push-down-field">Push Down Field</a>. It is simpler to start by moving the methods first. This way, the properties and fields remain accessible throughout the whole process: from the parent class prior to the move, and from the subclass itself after the move is complete.
4.uk. Перемістіть потрібні методи, поля і властивості з батьківського класу в підклас. Використайте для цього <a href="/uk/push-down-method">спуск методу</a> і <a href="/uk/push-down-field">спуск поля</a>. Найпростіше розпочинати перенесення з методів, тому тоді властивості і поля будуть доступні для них увесь час: з батьківського класу до перенесення, і з самого підкласу після закінчення перенесення.

5.ru. После того как подкласс будет полностью сформирован, посмотрите еще раз на родительский класс. Возможно вы заметите в нём код, под который стоит выделить еще один подкласс.
5.en. After the subclass is fully completed, look again at the parent class. Perhaps you will notice the code, under which it is necessary to create another subclass.
5.uk. Після того як підклас буде повністю сформований, подивіться ще раз на батьківський клас. Можливо ви помітите в ньому код, під який варто виділити ще один підклас.

###

```
public class JobItem
{
  private int unitPrice;

  public int Quantity
  { get; private set; }
  public bool IsLabor
  { get; private set; }
  public Employee Employee
  { get; private set; }

  public JobItem(int quantity, int unitPrice, bool isLabor, Employee employee)
  {
    this.Quantity = quantity;
    this.unitPrice = unitPrice;
    this.IsLabor = isLabor;
    this.Employee = employee;
  }

  public int GetTotalPrice()
  {
    return Quantity * GetUnitPrice();
  }
  public int GetUnitPrice()
  {
    return IsLabor ? Employee.Rate : unitPrice;
  }
}

public class Employee
{
  public int Rate
  { get; private set; }

  public Employee(int rate)
  {
    Rate = rate;
  }
}

// Somewhere in client code
Employee kent = new Employee(50);
JobItem j1 = new JobItem(5, 0, true, kent);
JobItem j2 = new JobItem(15, 10, false, null);
int total = j1.GetTotalPrice() + j2.GetTotalPrice();
```

###

```
public abstract class JobItem
{
  public int Quantity
  { get; private set; }

  protected JobItem(int quantity)
  {
    this.Quantity = quantity;
  }

  public int GetTotalPrice()
  {
    return Quantity * GetUnitPrice();
  }
  public abstract int GetUnitPrice();
}

public class PartsItem: JobItem
{
  private int unitPrice;

  public PartsItem(int quantity, int unitPrice): base(quantity)
  {
    this.unitPrice = unitPrice;
  }

  public override int GetUnitPrice()
  {
    return unitPrice;
  }
}

public class LaborItem: JobItem
{
  public Employee Employee
  { get; private set; }

  public LaborItem(int quantity, Employee employee): base(quantity)
  {
    Employee = employee;
  }
  public override int GetUnitPrice()
  {
    return Employee.Rate;
  }
}

public class Employee
{
  public int Rate
  { get; private set; }

  public Employee(int rate)
  {
    Rate = rate;
  }
}

// Somewhere in client code
Employee kent = new Employee(50);
JobItem j1 = new LaborItem(5, kent);
JobItem j2 = new PartsItem(15, 10);
int total = j1.GetTotalPrice() + j2.GetTotalPrice();
```

###

Set step 1

#|ru| Рассмотрим класс, который определяет цены работ, выполняемых в местном гараже.
#|en| We start with the <code>JobItem</code> class, which tracks the time and materials used to fix a client's car in a local garage. This class is also responsible for calculating the price client should pay.
#|uk| Розглянемо клас, який визначає ціни робіт, яківиконуються в місцевому гаражі.

Select name of "GetUnitPrice"

#|ru| Цена работы может состоять как из фиксированной суммы (например, платы за заказ каких-то деталей), так и из оплаты времени механика, стоимость которого может браться напрямую из класса <code>Employee</code>.
#|en| The price usually consists of several items. First, it's the fixed cost of certain parts. Second, it's the cost of a mechanic's time, multiplied by his rate (that can be taken directly from the <code>Employee</code> class).
#|uk| Ціна роботи може складатися як з фіксованої суми (наприклад, плати за замовлення якихось деталей), так і з оплати часу механіка, вартість якого може братися безпосередньо з класу <code>Employee</code>.

#|ru| Таким образом, цена вычисляется разными способами в пределах одного класса, что собственно нас и смущает.
#|en| So, the price is calculated in several ways, all of which sit in a single class. And that starts to smell as a <i>Large Class</i>.
#|uk| Таким чином, ціна обчислюється різними способами в межах одного класу, що власне нас і бентежить.

#|ru| Было бы здорово выделить из этого класса подкласс <code>LaborItem</code> и переместить в него весь код, связанный с ручной работой, а в оригинальном классе оставить только расчёты фиксированной платы.
#|en| As a solution, we could extract the <code>LaborItem</code> subclass and move all code, which are associated with manual work, to that subclass. Then we could leave only fixed amounts in the original class.
#|uk| Було б класно виділити з цього класу підклас <code>LaborItem</code> та перемістити в нього весь код, пов'язаний з ручною роботою, а в оригінальному класі залишити тільки розрахунки фіксованої плати.

Go to after "JobItem"

#|ru| Итак, создаём подкласс.
#|en| So, let's create a subclass.
#|uk| Отже, створюємо новий підклас.

Print:
```


public class LaborItem: JobItem
{
}
```


Set step 2

Select name of "LaborItem"

#|ru| Теперь начинаем спускать методы, свойственные только для подкласса.
#|en| Now we should start to push down the labor-related methods.
#|uk| Тепер починаємо спускати методи, які властиві тільки для підкласу.

#|ru| Прежде всего ему требуется собственный конструктор, потому что в <code>JobItem</code> нет конструктора, который принимал бы только объект работника и количество потраченных часов.
#|en| Above all, we need a constructor because <code>JobItem</code> does not have the constructor we need, one that would accept only the employee object and number of hours spent.
#|uk| Перш за все йому потрібен власний конструктор, тому що в <code>JobItem</code> немає конструктора, який брав би тільки об'єкт працівника і кількість витрачених годин.

Select parameters of "public JobItem"

#|ru| Пока что воспользуемся сигнатурой родительского конструктора.
#|en| For now, we will copy the signature of the parent constructor.
#|uk| Поки що скористаємося сигнатурою батьківського конструктора.

Go to the start of "LaborItem"

Print:
```

  public LaborItem(int quantity, int unitPrice, bool isLabor, Employee employee)
    : base(quantity, unitPrice, isLabor, employee)
  {
  }
```

Select "int quantity, int unitPrice, bool isLabor, Employee employee" in "LaborItem"

#|ru| Этого достаточно, чтобы новый подкласс перестал выдавать ошибки. Однако данный набор параметров избыточен: одни из них нужны для <code>LaborItem</code>, а другие – нет. Чуть позже мы это поправим.
#|en| That is enough to make the new subclass stop displaying errors. However, this constructor is unwieldy: some arguments are necessary for <code>LaborItem</code> and others are not. We will fix this a little later.
#|uk| Цього достатньо, щоб новий підклас перестав видавати помилки. Однак даний набір параметрів надлишковий: одні з них потрібні для <code>LaborItem</code>, а інші – ні. Трохи пізніше ми це поправимо.

Set step 3

Select 1st "new JobItem"

#|ru| На следующем этапе осуществляем поиск обращений к конструктору <code>JobItem</code> и случаев, когда вместо него следует вызывать конструктор <code>LaborItem</code>.
#|en| During the next step, we need to search for references to the <code>JobItem</code> constructor and cases when the <code>LaborItem</code> constructor should be called instead.
#|uk| На наступному етапі здійснюємо пошук звернень до конструктора <code>JobItem</code> і випадків, коли замість нього слід викликати конструктор <code>LaborItem</code>.

Print "new LaborItem"

Select "|||JobItem||| j1"

#|ru| При этом мы не трогаем тип переменной, а изменяем лишь тип конструктора.
#|en| At this point we will not touch the variable type, changing only the type of the constructor.
#|uk| При цьому ми не чіпаємо тип змінної, а змінюємо лише тип конструктора.

#|ru| Это вызвано тем, что новый тип следует использовать только там, где это необходимо. В данный момент у нас нет специфического интерфейса для подкласса, поэтому лучше пока не объявлять какие-либо разновидности.
#|en| That is because the new type should be used only where necessary. We do not yet have a specific interface for the subclass, so it is better to not declare any varieties.
#|uk| Це викликано тим, що новий тип слід використовувати тільки там, де це необхідно. В даний момент у нас немає специфічного інтерфейсу для підкласу, тому краще поки не оголошувати будь-які різновиди.

Select parameters of "public JobItem"
+Select "int quantity, int unitPrice, bool isLabor, Employee employee" in "LaborItem"

#|ru| Теперь самое время привести в порядок списки параметров конструкторов. К каждому из них применим <a href="/ru/remove-parameter">удаление параметров</a>.
#|en| That is the perfect time to perform housekeeping on the lists of constructors parameters. Let's apply <a href="/remove-parameter">Remove Parameter</a> to each of them.
#|uk| Тепер саме час привести в порядок списки параметрів конструкторів. До кожного з них застосуємо <a href="/uk/remove-parameter">видалення параметрів</a>.

Select name of "public JobItem"

#|ru| Начнем с родительского класса. Создаём новый публичный конструктор, а старый объявляем защищенным (он пока ещё нужен подклассу).
#|en| First, we need to refer to the parent class. We create a new constructor and declare the previous one protected (the subclass still needs it).
#|uk| Почнемо з батьківського класу. Створюємо новий публічний конструктор, а старий оголошуємо захищеним (він поки ще потрібен підкласу).

Select visibility of "public JobItem"

Wait 500ms

Print "protected"

Go to after "protected JobItem"

Print:
```

  public JobItem(int quantity, int unitPrice): this(quantity, unitPrice, false, null)
  {
  }
```

Select "|||, false, null|||);"

#|ru| Внешние вызовы конструктора теперь должны использовать новый конструктор.
#|en| External constructor calls should now use the new constructor.
#|uk| Зовнішні виклики конструктора тепер повинні використовувати новий конструктор.

Remove selected

Wait 500ms

#C|ru| Выполним компиляцию и тестирование, чтобы удостовериться в отсутствии ошибок.
#S Всё отлично, можем продолжать!

#C|en| Let's compile and test, just in case any errors appeared.
#S Everything is OK! We can keep going.

#C|uk| Виконаємо компіляцію і тестування, щоб упевнитися у відсутності помилок.
#S Все добре, можемо продовжувати.

Select name of "public LaborItem"

#|ru| Аналогичным образом применяем <a href="/ru/remove-parameter">удаление параметров</a> и для конструктора подкласса.
#|en| Now, we apply <a href="/remove-parameter">Remove Parameter</a> to the subclass constructor to get rid of unnecessary parameters.
#|uk| Аналогічним чином застосовуємо <a href="/uk/remove-parameter">видалення параметрів</a> і для конструктора підкласу.

Select "LaborItem(int quantity, |||int unitPrice, bool isLabor, |||Employee employee)"

Wait 500ms

Remove selected

Wait 500ms

Select "base(quantity, |||unitPrice|||, isLabor, employee)"

Replace "0"

Wait 500ms

Select "base(quantity, 0, |||isLabor|||, employee)"

Replace "true"

Wait 500ms

Select "new LaborItem(5, |||0, true, |||kent)"

Remove selected

Wait 500ms

Set step 4

Select "Employee |||Employee|||" in "JobItem"
+Select name of "GetUnitPrice" in "JobItem"

#|ru| Теперь начнём спускать в подкласс части класса <code>JobItem</code>. Нам надо спустить свойство работника и часть метода <code>GetUnitPrice()</code>, возвращающую стоимость его времени.
#|en| Now let's start to push down parts of <code>JobItem</code> to the subclass. We need to push down the employee's property and the method <code>GetUnitPrice()</code>, which returns the cost of his time.
#|uk| Тепер почнемо спускати в підклас частини класу <code>JobItem</code>. Нам треба спустити властивість працівника і частину методу <code>GetUnitPrice()</code>, що повертає вартість його часу.

Select name of "GetUnitPrice" in "JobItem"
+Select "|||return||| IsLabor ? |||Employee.Rate|||"

#|ru| Свойство <code>Employee</code> используется в методе <code>GetUnitPrice()</code>, поэтому вначале спустим в подкласс часть метода, использующую это свойство.
#|en| <code>Employee</code> property is used in the <code>GetUnitPrice()</code> method, so the first push down part of method, that uses this property.
#|uk| Властивість <code>Employee</code> використовується в методі <code>GetUnitPrice()</code>, тому спочатку спустимо в підклас частину методу, що використовує цю властивість.

Go to the end of "LaborItem"

Wait 500ms

Print:
```

  public int GetUnitPrice()
  {
    return Employee.Rate;
  }
```

Wait 500ms

Select "|||return||| IsLabor ? Employee.Rate : |||unitPrice;|||" in "JobItem"

#|ru| В родительском классе оставляем только работу с фиксированной ценой <code>unitPrice</code>.
#|en| In the parent class leave only work with <code>unitPrice</code> fixed price.
#|uk| У батьківському класі залишаємо тільки роботу з фіксованою ціною <code>unitPrice</code>.

Select "IsLabor ? Employee.Rate : " in "JobItem"

Remove selected

Wait 500ms

Select:
```
  public bool IsLabor
  { get; private set; }

```

#|ru| Свойство <code>IsLabor</code> больше не нужно, так что можем избавиться от него...
#|en| The <code>IsLabor</code> property is no longer needed, so we can get rid of it...
#|uk| Властивість <code>IsLabor</code> більше не потрібна, так що можемо позбутися від неї...

Remove selected

Wait 500ms

Select " bool isLabor,"
+Select " false,"
+Select " true,"
+Select:
```
    this.IsLabor = isLabor;

```

#|ru| ...не забыв почистить код от следов его использования.
#|en| ...not forgetting to clear the code from the traces of its use.
#|uk| ...не забувши почистити код від слідів її використання.

Remove selected

Wait 500ms

Select "public Employee |||Employee|||" in "JobItem"

#|ru| Свойство <code>Employee</code> больше не используется в методах родительского класса, поэтому теперь его можно спустить в наш подкласс.
#|en| The <code>Employee</code> property is no longer used in the methods of the parent class, so now it can push down in our subclass.
#|uk| Властивість <code>Employee</code> більше не використовується в методах батьківського класу, тому тепер її можна спустити в наш підклас.

Select ", Employee employee" in "JobItem"
+Select ", null"
+Select:
```
    this.Employee = employee;

```

#|ru| Сначала спускаем инициализацию этого свойства в конструктор подкласса.
#|en| First push down initialization of this property in the subclass constructor.
#|uk| Спочатку спускаємо ініціалізацію цієї властивості в конструктор підкласу.

Remove selected

Wait 500ms

Select "|||, employee|||)" in "LaborItem"

Remove selected

Wait 500ms

Go to start of "public LaborItem"

Print:
```

    Employee = employee;
```

Select:
```
  public Employee Employee
  { get; private set; }

```

#|ru| Затем спускаем и само свойство.
#|en| Then push down the property itself.
#|uk| Потім спускаємо і саму властивість.

Remove selected

Wait 500ms

Go to start of "LaborItem"

Print:
```

  public Employee Employee
  { get; private set; }

```

Select name of "protected JobItem"
+Select name of "public JobItem"

#|ru| После этих замен, конструкторы в <code>JobItem</code> стали идентичными, поэтому их можно склеить.
#|en| After the changes, the constructors in <code>JobItem</code> are identical and, for this reason, could be put together.
#|uk| Після цих замін, конструктори в <code>JobItem</code> стали ідентичними, тому їх можна склеїти.

Select whole "public JobItem"

Remove selected

Select visibility of "protected JobItem"

Replace "public"

#C|ru| Выполним компиляцию и тестирование, чтобы удостовериться в отсутствии ошибок.
#S Всё отлично, можем продолжать!

#C|en| Compile and test, just in case any errors appeared.
#S Everything is OK! We can keep going.

#C|uk| Виконаємо компіляцію і тестування, щоб упевнитися у відсутності помилок.
#S Все добре, можемо продовжувати.

Set step 5

Select "private int |||unitPrice|||"
+Select ", int |||unitPrice|||" in "JobItem"
+Select "this.unitPrice = unitPrice;" in "JobItem"
+Select name of "GetUnitPrice" in "JobItem"

#|ru| Итак, мы справились с извлечением <code>LaborItem</code>. Но есть ещё один момент. Поскольку цена запчастей <code>unitPrice</code> используется только в классе <code>JobItem</code> и не нужна в <code>LaborItem</code>, мы можем пойти дальше и снова применить к <code>JobItem</code> <a href="/ru/extract-subclass">выделение подкласса</a>, создав при этом класс, описывающий работу с запчастями.
#|en| So extraction of <code>LaborItem</code> is complete. But one more thing remains. Since the price of spare parts (<code>unitPrice</code>) is used only in the <code>JobItem</code> class and is not needed in <code>LaborItem</code>, we can go one step further and apply <a href="/extract-subclass">Extract Subclass</a> to <code>JobItem</code> again and create a class that represents spare parts.
#|uk| Отже, ми впоралися з витяганням <code>LaborItem</code>. Але є ще один момент. Оскільки ціна запчастин <code>unitPrice</code> використовується тільки в класі <code>JobItem</code> і не потрібна в <code>LaborItem</code>, ми можемо піти далі і знову застосувати до <code>JobItem</code> <a href="/uk/extract-subclass">витягання підкласу</a>, створивши при цьому клас, що описує роботу із запчастинами.

Go to after "JobItem"

#|ru| Создаём подкласс <code>PartsItem</code> и изменяем клиентский код так, чтобы он использовал конструктор созданного подкласса.
#|en| Create a <code>PartsItem</code> subclass and change the client code to use the constructor of the created subclass.
#|uk| Створюємо підклас <code>PartsItem</code> і змінюємо клієнтський код так, щоб він використовував конструктор створеного підкласу.

Print:
```


public class PartsItem: JobItem
{
  public PartsItem(int quantity, int unitPrice): base(quantity, unitPrice)
  {
  }
}
```

Wait 500ms

Select "new |||JobItem|||"

Replace "PartsItem"

Wait 500ms

Select ", int |||unitPrice|||" in "JobItem"
+Select "this.unitPrice = unitPrice;" in "JobItem"
+Select name of "GetUnitPrice" in "JobItem"

#|ru| Перед спуском поля <code>unitPrice</code> надо сначала спустить код его инициализации, а также метод, в котором оно используется.
#|en| Before pushing down the <code>unitPrice</code> field we must first push down its initialization code, as well as the method in which it is used.
#|uk| Перед спуском поля <code>unitPrice</code> треба спочатку спустити код його ініціалізації, а також метод, в якому воно використовується.

Select "|||private||| int unitPrice"

#|ru| Чтобы при этом не возникло ошибок компиляции, изменим видимость поля, сделав его доступным для дочернего класса <code>PartsItem</code>.
#|en| To avoid compilation errors, change the visibility of the field, making it accessible to the <code>PartsItem</code> class.
#|uk| Щоб при цьому не виникло помилок компіляції, змінимо видимість поля, зробивши його доступним для дочірнього класу <code>PartsItem</code>.

Replace "protected"

Wait 500ms

Select:
```
    this.unitPrice = unitPrice;

```

#|ru| Итак, спускаем код инициализации поля.
#|en| So, push down initialization code of the field.
#|uk| Отже, спускаємо код ініціалізації поля.

Remove selected

Go to start of "public PartsItem"

Print:
```

    this.unitPrice = unitPrice;
```

Wait 500ms

Select ", int unitPrice" in "JobItem"

Remove selected

Wait 500ms

Select ", unitPrice" in "PartsItem"
+Select ", 0" in "LaborItem"

Wait 500ms

Remove selected

Select:
```
|||
    |||: base(quantity
```

Remove selected

Wait 500ms

Select name of "GetUnitPrice" in "JobItem"

#|ru| Затем спускаем метод <code>GetUnitPrice()</code>.
#|en| Then push down the <code>GetUnitPrice()</code> method.
#|uk| Потім спускаємо метод <code>GetUnitPrice()</code>.

Select body of "GetUnitPrice" in "JobItem"

Remove selected

Wait 500ms

Go to end of "PartsItem"

Print:
```


  public int GetUnitPrice()
  {
    return unitPrice;
  }
```

Wait 500ms

Select whole "GetUnitPrice" in "JobItem"

#|ru| После чего объявляем родительский метод абстрактным.
#|en| After that, declare the parent method as abstract.
#|uk| Після чого оголошуємо батьківський метод абстрактним.

Print:
```

```
Go to after "GetTotalPrice" in "JobItem"
Print:
```

  public abstract int GetUnitPrice();
```

Wait 500ms

Select "public|||||| int GetUnitPrice"

Replace " override"

Wait 500ms

Select name of "JobItem"

#|ru| А вместе с ним и сам класс <code>JobItem</code>.
#|en| And with it, a <code>JobItem</code> class.
#|uk| А разом з ним і сам клас <code>JobItem</code>.

Go to "public||| class JobItem"

Print " abstract"

Wait 500ms

Select visibility of "public JobItem"

Replace "protected"

Wait 500ms

Select:
```
  protected int unitPrice;


```

#|ru| Наконец, спускаем само поле.
#|en| Finally, we push down the field itself.
#|uk| Нарешті, спускаємо саме поле.

Remove selected

Go to start of "PartsItem"

Wait 500ms

Print:
```

  private int unitPrice;

```

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