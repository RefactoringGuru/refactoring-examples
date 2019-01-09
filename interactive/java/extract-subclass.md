extract-subclass:java

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

4.ru. Переместите нужные методы и поля из родительского класса в подкласс. Используйте для этого <a href="/ru/push-down-method">спуск метода</a> и <a href="/ru/push-down-field">спуск поля</a>. Проще всего начинать перенос с методов, так как тогда поля будут доступны для них все время: из родительского класса до переноса, и из самого подкласса после окончания переноса.
4.en. Move the necessary methods and fields from the parent class to the subclass. Do this via <a href="/push-down-method">Push Down Method</a> and <a href="/push-down-field">Push Down Field</a>. It is simpler to start by moving the methods first. This way, the fields remain accessible throughout the whole process: from the parent class prior to the move, and from the subclass itself after the move is complete.
4.uk. Перемістіть потрібні методи і поля з батьківського класу в підклас. Використайте для цього <a href="/uk/push-down-method">спуск методу</a> і <a href="/uk/push-down-field">спуск поля</a>. Найпростіше розпочинати перенесення з методів, тому тоді поля будуть доступні для них увесь час: з батьківського класу до перенесення, і з самого підкласу після закінчення перенесення.

5.ru. После того как подкласс готов, найдите все старые поля, которые управляли тем, какой набор функций должен выполняться. Эти поля можно удалить, заменив полиморфизмом все условные операторы, в которых они использовались.
5.en. After the subclass is ready, find all the old fields that controlled the choice of functionality. Delete these fields by using polymorphism to replace all the operators in which the fields had been used.
5.uk. Після того, як підклас готовий, знайдіть усі старі поля, які управляли тим, який набір функцій повинен виконуватися. Ці поля можна видалити, замінивши поліморфізмом усі умовні оператори, в яких вони використовувалися.



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

  protected JobItem(int quantity) {
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
  private int unitPrice;

  public PartsItem(int quantity, int unitPrice) {
    super(quantity);
    this.unitPrice = unitPrice;
  }
  @Override public int getUnitPrice() {
    return unitPrice;
  }
}

class LaborItem extends JobItem {
  private Employee employee;

  public LaborItem(int quantity, Employee employee) {
    super(quantity);
    this.employee = employee;
  }
  public Employee getEmployee() {
    return employee;
  }
  @Override public int getUnitPrice() {
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

#|ru| Рассмотрим класс, который определяет цены работ, выполняемых в местном гараже.
#|en| We start with the <code>JobItem</code> class, which tracks the time and materials used to fix a client's car in a local garage. This class is also responsible for calculating the price client should pay.
#|uk| Розглянемо клас, який визначає ціни робіт, яківиконуються в місцевому гаражі.

Select name of "getUnitPrice"

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


class LaborItem extends JobItem {
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

  public LaborItem(int quantity, int unitPrice, boolean isLabor, Employee employee) {
    super(quantity, unitPrice, isLabor, employee);
  }
```

Select parameters of "public LaborItem"

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
+ Select parameters of "public LaborItem"

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

  public JobItem(int quantity, int unitPrice) {
    this(quantity, unitPrice, false, null);
  }
```

Select "15, 10|||, false, null|||"

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

Select "LaborItem(int quantity, |||int unitPrice, boolean isLabor, |||Employee employee)"

#|ru| Аналогичным образом применяем <a href="/ru/remove-parameter">удаление параметров</a> и для конструктора подкласса.
#|en| Now, we apply <a href="/remove-parameter">Remove Parameter</a> to the subclass constructor to get rid of unnecessary parameters.
#|uk| Аналогічним чином застосовуємо <a href="/uk/remove-parameter">видалення параметрів</a> і для конструктора підкласу.

Remove selected

Wait 500ms

Select "super(quantity, |||unitPrice|||, isLabor, employee)"

Replace "0"

Wait 500ms

Select "super(quantity, 0, |||isLabor|||, employee)"

Replace "true"

Wait 500ms

Select "new LaborItem(5, |||0, true, |||kent)"

Remove selected

Wait 500ms

Set step 4

Select name of "JobItem"

#|ru| Далее мы можем начать спускать в подкласс части <code>JobItem</code>. Сперва займёмся методами.
#|en| Subsequently we can push down parts of <code>JobItem</code> to the subclass. First look at the methods.
#|uk| Далі ми можемо почати спускати в підклас частини <code>JobItem</code>. Почнемо з методів.

Select whole of "getEmployee"

#|ru| Начнём с применения <a href="/ru/push-down-method">Спуска метода</a> к <code>getEmployee</code>.
#|en| Start with applying <a href="/push-down-method">Push Down Method</a> to <code>getEmployee</code>. 
#|uk| Почнемо з застосування <a href="/uk/push-down-method">Спуску методу</a> до <code>getEmployee</code>.

Remove selected

Go to the end of "LaborItem"

Print:
```

  public Employee getEmployee() {
    return employee;
  }
```

Select "|||private||| Employee employee;"

#|ru| Поскольку поле <code>employee</code> позднее будет спущено в подкласс, пока что объявим его защищенным.
#|en| Since the <code>employee</code> field will be pushed down to the subclass later, for now we will declare it protected.
#|uk| Оскільки поле <code>employee</code> пізніше буде спущено в підклас, поки що оголосимо його захищеним.

Print "protected"

Select ", Employee employee" in "JobItem"
+ Select "|||, employee|||)"


#|ru| После того как поле <code>employee</code> защищено, мы можем привести в порядок конструкторы, чтобы <code>employee</code> инициализировалось только в подклассе, в который оно спускается.
#|en| Once the <code>employee</code> field is protected, we can clean up the constructors so that <code>employee</code> is initialized only in the subclass.
#|uk| Після того як поле <code>employee</code> стало захищеним, ми можемо привести до ладу конструктори, щоб <code>employee</code> ініціювалося тільки в підкласі, в який воно спускається.

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

#|ru| Поле <code>isLabor</code> применяется для указания информации, которая теперь присуща иерархии, поэтому можно удалить это поле.<br/><br/>Лучше всего сделать это, сначала применив <a href="/ru/self-encapsulate-field">Самоинкапсуляцию поля</a>, после чего изменив метод доступа, чтобы применить полиморфный константный метод (это такой метод, посредством которого каждая реализация возвращает (своё) фиксированное значение).
#|en| The <code>isLabor</code> field is used to indicate information now implied by the hierarchy, so the field can be removed<br/><br/>The best way to do so is to first use <a href="/self-encapsulate-field">Self-Encapsulate Field</a> and then override the getter in subclasses so that it return own fixed value (such methods usually called "polymorphic constant method").
#|uk| Поле <code>isLabor</code> застосовується для вказівки інформації, яка тепер властива ієрархії, тому можна видалити це поле.<br/><br/>Найкраще зробити це, спочатку застосувавши <a href = "/uk/self-encapsulate-field">Самоінкапсуляцію поля</a>, після чого змінивши метод доступу, щоб застосувати поліморфний константний метод (це такий метод, за допомогою якого кожна реалізація повертає (своє) фіксоване значення).

#|ru| Итак, объявим геттеры <code>isLabor</code> в обоих классах.
#|en| So we declare <code>isLabor</code> getters in both classes.
#|uk| Отже, оголосимо геттери <code>isLabor</code> в обох класах.

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

#|ru| Теперь заменим использование поля вызовами этих геттеров.
#|en| Now replace use of the field with calls to the getters.
#|uk| Тепер замінимо використання поля викликами цих геттеров.

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

#|ru| После этого можно избавиться от поля <code>isLabor</code>.
#|en| Remove the <code>isLabor</code> field.
#|uk| Після цього можна позбутися від поля <code>isLabor</code>.

Remove selected

#|ru| После этих замен, конструкторы в <code>JobItem</code> стали идентичными, поэтому их можно склеить.
#|en| After the changes, the constructors in <code>JobItem</code> are identical and, for this reason, could be put together.
#|uk| Після цих замін, конструктори в <code>JobItem</code> стали ідентичними, тому їх можна склеїти.

Select whole "public JobItem"

Remove selected

Select visibility of "protected JobItem"

Replace "public"

Select "isLabor" in "getUnitPrice"

#|ru| Теперь можно посмотреть на пользователей методов <code>isLabor</code>. Они должны быть подвергнуты рефакторингу <a href="/ru/replace-conditional-with-polymorphism">Замена условного оператора полиморфизмом</a>.
#|en| Now look at the uses of the <code>isLabor</code> methods. They should be refactored using <a href="/replace-conditional-with-polymorphism">Replace Conditional With Polymorphism</a>.
#|uk| Тепер можна подивитися на користувачів методів <code>isLabor</code>. Для них необхідно застосувати рефакторинг <a href="/uk/replace-conditional-with-polymorphism">Заміна умовного оператора полиморфизмом</a>.

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

#|ru| После этого становится видно, что методы <code>isLabor</code> теперь нигде не используются и их можно удалить из всех классов.
#|en| Then it becomes clear that <code>isLabor</code> methods are not used anywhere and can be safely removed from all classes.
#|uk| Після цього стає видно, що методи <code>isLabor</code> тепер ніде не використовуються і їх можна видалити з усіх класів.

Remove selected

Select name of "JobItem"

#|ru| После того как группа методов, использующих некоторые данные, перемещена в подкласс, к этим данным можно применить <a href="/ru/push-down-field">Спуск поля</a>. В некоторых случаях это невозможно, так как данные используются некоторым методом.
#|en| After pushing methods down to a subclass, you can consider moving some of the fields as well. We can apply <a href="/push-down-field">Push Down Field</a> to these fields. In some cases, this is impossible because the fields are still used in the context of superclass.
#|uk| Після того як група методів, що використовують деякі дані, переміщена в підклас, до цих даних можна застосувати <a href="/uk/push-down-field">Спуск поля</a>. В деяких випадках це неможливо, оскільки дані використовуються деяким методом.

#|ru| В нашем случае все готово, чтобы переместить поле <code>employee</code> в <code>LaborItem</code>.
#|en| In our case, everything is ready for us to move the <code>employee</code> field to <code>LaborItem</code>.
#|uk| У нашому випадку все готово, щоб перемістити поле <code>employee</code> в <code>LaborItem</code>.

Select:
```
  protected Employee employee;

```

Remove selected

Go to start of "LaborItem"

Print:
```

  private Employee employee;

```

Select:
```
  private int unitPrice;
```

#C|ru| Выполним компиляцию и тестирование, чтобы удостовериться в отсутствии ошибок.
#S Всё отлично, можем продолжать!

#C|en| Compile and test, just in case any errors appeared.
#S Everything is OK! We can keep going.

#C|uk| Виконаємо компіляцію і тестування, щоб упевнитися у відсутності помилок.
#S Все добре, можемо продовжувати.

#|ru| Итак, мы справились с извлечением <code>LaborItem</code>. Но есть ещё один момент. Поскольку цена запчастей <code>unitPrice</code> используется только в классе <code>JobItem</code> и не нужна в <code>LaborItem</code>, мы можем пойти дальше и снова применить к <code>JobItem</code> <a href="/ru/extract-subclass">выделение подкласса</a>, создав при этом класс, описывающий работу с запчастями.
#|en| So extraction of <code>LaborItem</code> is complete. But one more thing remains. Since the price of spare parts (<code>unitPrice</code>) is used only in the <code>JobItem</code> class and is not needed in <code>LaborItem</code>, we can go one step further and apply <a href="/extract-subclass">Extract Subclass</a> to <code>JobItem</code> again and create a class that represents spare parts.
#|uk| Отже, ми впоралися з витяганням <code>LaborItem</code>. Але є ще один момент. Оскільки ціна запчастин <code>unitPrice</code> використовується тільки в класі <code>JobItem</code> і не потрібна в <code>LaborItem</code>, ми можемо піти далі і знову застосувати до <code>JobItem</code> <a href="/uk/extract-subclass">витягання підкласу</a>, створивши при цьому клас, що описує роботу із запчастинами.

Go to after "JobItem"

#|ru| Создаём подкласс <code>PartsItem</code> и изменяем клиентский код так, чтобы он использовал конструктор созданного подкласса.
#|en| Create a <code>PartsItem</code> subclass and change the client code to use the constructor of the created subclass.
#|uk| Створюємо підклас <code>PartsItem</code> і змінюємо клієнтський код так, щоб він використовував конструктор створеного підкласу.

Print:
```


class PartsItem extends JobItem {
  public PartsItem(int quantity, int unitPrice) {
    super(quantity, unitPrice);
  }
}
```

Wait 500ms

Select "new |||JobItem|||"

Replace "PartsItem"

Wait 500ms

Select ", int |||unitPrice|||" in "JobItem"
+Select "this.unitPrice = unitPrice;" in "JobItem"
+Select name of "getUnitPrice" in "JobItem"

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

Go to end of "public PartsItem"

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

Wait 500ms

Select name of "getUnitPrice" in "JobItem"

#|ru| Затем спускаем метод <code>getUnitPrice</code>.
#|en| Then push down the <code>getUnitPrice</code> method.
#|uk| Потім спускаємо метод <code>getUnitPrice</code>.

Select body of "getUnitPrice" in "JobItem"

Remove selected

Wait 500ms

Go to end of "PartsItem"

Print:
```

  public int getUnitPrice() {
    return unitPrice;
  }
```

Wait 500ms

Select whole "getUnitPrice" in "JobItem"

#|ru| После чего объявляем родительский метод абстрактным.
#|en| After that, declare the parent method as abstract.
#|uk| Після чого оголошуємо батьківський метод абстрактним.

Print:
```

```
Go to after "getQuantity" in "JobItem"
Print:
```

  public abstract int getUnitPrice();
```

Wait 500ms

Select "|||||| public int getUnitPrice"

Replace " @Override"

Wait 500ms

Select name of "JobItem"

#|ru| А вместе с ним и сам класс <code>JobItem</code>.
#|en| And with it, a <code>JobItem</code> class.
#|uk| А разом з ним і сам клас <code>JobItem</code>.

Go to "|||class JobItem"
Print " "
Go to "||| class JobItem"
Print "abstract"

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