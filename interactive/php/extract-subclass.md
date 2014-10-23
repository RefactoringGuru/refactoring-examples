extract-subclass:php

###

1.ru. Создайте новый подкласс из интересующего вас класса.
1.uk. Створіть новий підклас з класу, що вас цікавить.

2.ru. Если для создания объектов из подкласса будут нужны какие-то дополнительные данные, создайте конструктор и дополните его нужными параметрами. Не забудьте вызвать родительскую реализацию конструктора.
2.uk. Якщо для створення об'єктів підкласу будуть потрібні якісь додаткові дані, створіть конструктор і доповніть його потрібними параметрами. Не забудьте викликати батьківську реалізацію конструктора, використовуючи <code>parent</code>.

3.ru. Найдите все вызовы конструктора родительского класса. В тех случаях, когда требуется функциональность подкласса, замените родительский конструктор конструктором подкласса.
3.uk. Знайдіть усі виклики конструктора батьківського класу. У тих випадках, коли потрібна також функціональність підкласу, замініть батьківський конструктор конструктором підкласу.

4.ru. Переместите нужные методы и поля из родительского класса в подкласс. Используйте для этого <a href="/push-down-method">спуск метода</a> и <a href="/push-down-field">спуск поля</a>. Проще всего начинать перенос с методов. Так  поля будут доступны для них все время:  из родительского класса до переноса, и из самого подкласса после окончания переноса.
4.uk. Перемістіть потрібні методи і поля з батьківського класу в підклас. Використайте для цього <a href="/push-down-method">спуск методу</a> і <a href="/push-down-field">спуск поля</a>. Найпростіше розпочинати перенесення з методів. Тоді поля будуть доступні для них увесь час: з батьківського класу до перенесення, і з самого підкласу після закінчення перенесення.

5.ru. После того как подкласс готов, найдите все старые поля, которые управляли тем, какой набор функций должен выполняться. Эти поля можно удалить, заменив полиморфизмом все условные операторы, в которых они использовались.
5.uk. Після того, як підклас готовий, знайдіть усі старі поля, які управляли тим, який набір функцій повинен виконуватися. Ці поля можна видалити, замінивши поліморфізмом усі умовні оператори, в яких вони використовувалися.



###

```
class JobItem {
  private $quantity;
  private $unitPrice;
  private $employee; // Employee
  private $isLabor;

  public function __construct($quantity, $unitPrice, $isLabor, Employee $employee) {
    $this->quantity = $quantity;
    $this->unitPrice = $unitPrice;
    $this->isLabor = $isLabor;
    $this->employee = $employee;
  }
  public function getTotalPrice() {
    return $this->quantity * $this->getUnitPrice();
  }
  public function getQuantity() {
    return $this->quantity;
  }
  public function getUnitPrice() {
    return ($this->isLabor) ?
      $this->employee->getRate() : $this->unitPrice;
  }
  public function getEmployee() {
    return $this->employee;
  }
}

class Employee {
  private $rate;
  public function __construct($rate) {
    $this->rate = $rate;
  }
  public function getRate() {
    return $this->rate;
  }
}

// Somewhere in client code
$kent = new Employee(50);
$j1 = new JobItem(5, 0, true, kent);
$j2 = new JobItem(15, 10, false, null);
$total = $j1->getTotalPrice() + $j2->getTotalPrice();
```

###

```
abstract class JobItem {
  private $quantity;

  public function __construct($quantity) {
    $this->quantity = $quantity;
  }
  public function getTotalPrice() {
    return $this->quantity * $this->getUnitPrice();
  }
  public function getQuantity() {
    return $this->quantity;
  }
  public abstract function getUnitPrice();
}
class PartsItem extends JobItem {
  protected $unitPrice;

  public function __construct($quantity, $unitPrice) {
    parent::__construct($quantity);
    $this->unitPrice = $unitPrice;
  }
  public function getUnitPrice() {
    return $this->unitPrice;
  }
}
class LaborItem extends JobItem {
  protected $employee; // Employee

  public function __construct($quantity, Employee $employee) {
    parent::__construct($quantity);
    $this->employee = $employee;
  }
  public function getEmployee() {
    return $this->employee;
  }
  public function getUnitPrice() {
    return $employee->getRate();
  }
}

class Employee {
  private $rate;
  public function __construct($rate) {
    $this->rate = $rate;
  }
  public function getRate() {
    return $this->rate;
  }
}

// Somewhere in client code
$kent = new Employee(50);
$j1 = new LaborItem(5, kent);
$j2 = new PartsItem(15, 10);
$total = $j1->getTotalPrice() + $j2->getTotalPrice();
```

###

Set step 1

#|ru| Начнём с класса выполняемых работ, который определяет цены операций, выполняемых в местном гараже.
#|uk| Почнемо з класу виконуваних робіт, який визначає ціни операцій, яківиконуються в місцевому гаражі.

Select name of "getUnitPrice"

#|ru| Цена работы может состоять как из фиксированной суммы (например, платы за заказ каких-то деталей), так и оплаты времени механика, цена которого может браться напрямую из класса <code>Employee</code>.
#|uk| Ціна роботи може складатися як з фіксованої суми (наприклад, плати за замовлення якихось деталей), так і оплати часу механіка, ціна якого може братися безпосередньо з класу <code>Employee</code>.

#|ru| При этом цена вычисляется разными способами в пределах одного класса, именно это нас и смущает.
#|uk| При цьому ціна обчислюється різними способами в межах одного класу, саме це нас і бентежить.

#|ru| Было бы здорово выделить из этого класса подкласс <code>LaborItem</code> и переместить в него все поведения, связанные с ручной работой, а в оригинальном классе оставить только расчёты фиксированной работы.
#|uk| Було б класно виділити з цього класу підклас <code>LaborItem</code> та перемістити в нього всі поведінки, пов'язані з ручною роботою, а в оригінальному класі залишити тільки розрахунки фіксованої роботи.

#|ru| Итак, создаём новый класс.
#|uk| Отже, створюємо новий клас.

Go to after "JobItem"

Print:
```

class LaborItem extends JobItem {
}
```


Set step 2

#|ru| Теперь, начинаем спускать методы, свойственные только для подкласса.
#|uk| Тепер, починаємо спускати методи, які властиві тільки для підкласу.

Select name of "__construct" in "JobItem"

#|ru| Прежде всего, нам нужен конструктор для этого класса, потому что в <code>JobItem</code> нет нужного нам конструктора, который бы принимал только объект рабочего и количество потраченных часов.
#|uk| Перш за все, нам потрібен конструктор для цього класу, тому що в <code>JobItem</code> немає потрібного нам конструктора, який би брав тільки об'єкт працівника і кількість витрачених годин.

#|ru| Пока что для этого скопируем сигнатуру родительского конструктора.
#|uk| Поки що для цього скопіюємо сигнатуру батьківського конструктора.

Go to the start of "LaborItem"

Print:
```

  public function __construct($quantity, $unitPrice, $isLabor, Employee $employee) {
    parent::__construct($quantity, $unitPrice, $isLabor, $employee);
  }
```

#|ru| Этого достаточно, чтобы новый подкласс перестал выдавать ошибки. Однако этот конструктор путаный: одни аргументы нужны для <code>LaborItem</code>, а другие – нет. Мы это исправим, но позднее.
#|uk| Цього достатньо, щоб новий підклас перестав видавати помилки. Однак цей конструктор заплутаний: одні аргументи потрібні для <code>LaborItem</code>, а інші – ні. Ми це виправимо, але пізніше.

Set step 3

Select 1st "new JobItem"

#|ru| На следующем этапе осуществляется поиск обращений к конструктору <code>JobItem</code> и случаев, когда вместо него следует вызывать конструктор <code>LaborItem</code>.
#|uk| На наступному етапі здійснюється пошук звернень до конструктора <code>JobItem</code> і випадків, коли замість нього слід викликати конструктор <code>LaborItem</code>.

Print "new LaborItem"

#|ru| На данном этапе мы не трогаем тип переменной, а изменяем лишь тип конструктора.
#|uk| На даному етапі ми не чіпаємо тип змінної, а змінюємо лише тип конструктора.

#|ru| Это вызвано тем, что новый тип следует использовать только там, где это необходимо. В данный момент у нас нет специфического интерфейса для подкласса, поэтому лучше пока не объявлять какие-либо разновидности.
#|uk| Це викликано тим, що новий тип слід використовувати тільки там, де це необхідно. В даний момент у нас немає специфічного інтерфейсу для підкласу, тому краще поки не оголошувати будь-які різновиди.

Select parameters of "__construct" in "JobItem"
+ Select parameters of "__construct" in "LaborItem"

#|ru| Теперь самое время привести в порядок списки параметров конструктора. К каждому из них применим <a href="/remove-parameter">удаление параметров</a>.
#|uk| Тепер саме час привести в порядок списки параметрів конструктора. До кожного з них застосуємо <a href="/remove-parameter">видалення параметрів</a>.

Select visibility of "__construct" in "JobItem"

#|ru| Сначала обращаемся к родительскому классу. Создаём новый конструктор и объявляем прежний защищенным (подклассу он по-прежнему нужен).
#|uk| Спочатку звертаємося до батьківського класу. Створюємо новий конструктор і оголошуємо колишній захищеним (підкласу він як і раніше потрібний).

Go to "$isLabor|||, Employee $employee" in "JobItem"

Print " = false"

Go to "Employee $employee|||" in "JobItem"

Print " = null"

Select ", false, null"

#|ru| Внешние вызовы конструктора теперь должны использовать новый конструктор.
#|uk| Зовнішні виклики конструктора тепер повинні використовувати новий конструктор.

Remove selected

#C|ru| Выполним тестирование, чтобы удостовериться в отсутствии ошибок.
#S Всё отлично, можем продолжать.

#C|uk| Виконаємо тестування, щоб упевнитися у відсутності помилок.
#S Все добре, можемо продовжувати.

Select "($quantity, |||$unitPrice, $isLabor, |||Employee $employee)"

#|ru| Теперь применим <a href="/remove-parameter">удаление параметров</a> к конструктору подкласса, чтобы избавиться от ненужных параметров.
#|uk| Тепер застосуємо <a href="/remove-parameter">видалення параметрів</a> до конструктора підкласу, щоб позбутися від непотрібних параметрів.

Remove selected

Wait 500ms

Select "new LaborItem(5, |||0, true, |||kent)"

Remove selected

Wait 500ms

Select "parent::__construct($quantity, |||$unitPrice|||, $isLabor, $employee)"

Replace "0"

Wait 500ms

Select "parent::__construct($quantity, 0, |||$isLabor|||, $employee)"

Replace "true"

Set step 4

Select name of "JobItem"

#|ru| Далее мы можем начать спускать в подкласс части <code>JobItem</code>. Сперва займёмся методами.
#|uk| Далі ми можемо почати спускати в підклас частини <code>JobItem</code>. Почнемо з методів.

Select whole of "getEmployee"

#|ru| Начнём с применения <a href="/push-down-method">Спуска метода</a> к <code>getEmployee</code>.
#|uk| Почнемо з застосування <a href="/push-down-method">Спуску методу</a> до <code>getEmployee</code>.

Remove selected

Go to the end of "LaborItem"

Print:
```

  public function getEmployee() {
    return $this->employee;
  }
```

Select "|||private||| $employee;"

#|ru| Поскольку поле <code>employee</code> позднее будет спущено в подкласс, пока объявим его защищённым.
#|uk| Оскільки поле <code>employee</code> пізніше буде спущено в підклас, поки оголосимо його захищеним.

Print "protected"

Select ", Employee $employee = null" in "JobItem"
+ Select "|||, $employee|||)"


#|ru| После того как поле <code>employee</code> защищено, мы можем привести в порядок конструкторы, чтобы <code>employee</code> инициализировалось только в подклассе, в который оно спускается.
#|uk| Після того як поле <code>employee</code> стало захищеним, ми можемо привести до ладу конструктори, щоб <code>employee</code> ініціювалося тільки в підкласі, в який воно спускається.

Remove selected

Select:
```
    $this->employee = $employee;

```

Remove selected

Go to the end of "__construct" in "LaborItem"

Print:
```

    $this->employee = $employee;
```

Set step 5

Select "private $isLabor;"

#|ru| Поле <code>isLabor</code> применяется для указания информации, которая теперь присуща иерархии, поэтому можно удалить это поле.<br/><br/>Лучше всего сделать это, сначала применив <a href="/self-encapsulate-field">Самоинкапсуляцию поля</a>, после чего изменив метод доступа, чтобы применить полиморфный константный метод (это такой метод, посредством которого каждая реализация возвращает (своё) фиксированное значение).
#|uk| Поле <code>isLabor</code> застосовується для вказівки інформації, яка тепер властива ієрархії, тому можна видалити це поле.<br/><br/>Найкраще зробити це, спочатку застосувавши <a href = "/ self-encapsulate- field ">Самоінкапсуляцію поля</a>, після чого змінивши метод доступу, щоб застосувати поліморфний константний метод (це такий метод, за допомогою якого кожна реалізація повертає (своє) фіксоване значення).

#|ru| Итак, объявим геттеры <code>isLabor</code> в обоих классах.
#|uk| Отже, оголосимо геттери <code>isLabor</code> в обох класах.

Go to the end of "JobItem"

Print:
```

  protected function isLabor() {
    return false;
  }
```

Go to the end of "LaborItem"

Print:
```

  protected function isLabor() {
    return true;
  }
```

#|ru| Теперь заменим использование поля вызовами этих геттеров.
#|uk| Тепер замінимо використання поля викликами цих геттеров.

Select "isLabor" in "getUnitPrice"

Replace "isLabor()"

Select:
```
  private $isLabor;

```
+ Select ", $isLabor = false"
+ Select "|||, true|||)"
+ Select:
```
    $this->isLabor = $isLabor;

```

#|ru| После этого можно избавиться от поля <code>isLabor</code>.
#|uk| Після цього можна позбутися від поля <code>isLabor</code>.

Remove selected

Select "isLabor" in "getUnitPrice"

#|ru| Теперь можно посмотреть на пользователей методов <code>isLabor</code>. Они должны быть подвергнуты рефакторингу <a href="/replace-conditional-with-polymorphism">Замена условного оператора полиморфизмом</a>.
#|uk| Тепер можна подивитися на користувачів методів <code>isLabor</code>. Для них необхідно застосувати рефакторинг <a href="/replace-conditional-with-polymorphism">Заміна умовного оператора полиморфизмом</a>.

Select body of "getUnitPrice"

Replace "    return $this->unitPrice;"

Wait 500ms

Go to the end of "LaborItem"

Print:
```

  public function getUnitPrice() {
    return $employee->getRate();
  }
```

Select whole "isLabor" in "JobItem"
+Select whole "isLabor" in "LaborItem"

#|ru| После этого становится видно, что методы <code>isLabor</code> теперь нигде не используются и их можно удалить из всех классов.
#|uk| Після цього стає видно, що методи <code>isLabor</code> тепер ніде не використовуються і їх можна видалити з усіх класів.

Remove selected

Select name of "JobItem"

#|ru| После того, как группа методов, использующих некоторые данные, перемещена в подкласс, к этим данным можно применить <a href="/push-down-field">Спуск поля</a>. В некоторых случаях это невозможно, так как данные используются некоторым методом. Такое положение вещей означает, что необходимо продолжить работу с методами, применяя <a href="/push-down-method">Спуск метода</a> или <a href="/replace-conditional-with-polymorphism">Замену условного оператора полиморфизмом</a>.
#|uk| Після того, як група методів, що використовують деякі дані, переміщена в підклас, до цих даних можна застосувати <a href="/push-down-field">Спуск поля</a>. В деяких випадках це неможливо, так як дані використовуються деяким методом. Такий стан речей означає, що необхідно продовжити роботу з методами, застосовуючи <a href="/push-down-method">Спуск методу</a> або <a href="/replace-conditional-with-polymorphism">Заміну умовного оператора поліморфізмом</a>.

#|ru| В нашем случае все готово, чтобы переместить поле <code>employee</code> в <code>LaborItem</code>.
#|uk| У нашому випадку все готово, щоб перемістити поле <code>employee</code> в <code>LaborItem</code>.

Select:
```
  protected $employee; // Employee

```

Remove selected

Go to start of "LaborItem"

Print:
```

  protected $employee; // Employee

```

Select:
```
  private $unitPrice;
```

#C|ru| Выполним тестирование, чтобы удостовериться в отсутствии ошибок.
#S Всё отлично, можем продолжать.

#C|uk| Виконаємо тестування, щоб упевнитися у відсутності помилок.
#S Все добре, можемо продовжувати.

#|ru| Итак, мы справились с извлечением <code>LaborItem</code>. Но есть ещё один момент. Поскольку цена запчастей <code>unitPrice</code> используется только в классе <code>JobItem</code> и не нужна в <code>LaborItem</code>, мы можем пойти дальше и снова применить к <code>JobItem</code> <a href="/extract-subclass">Выделение подкласса</a> и создать класс, представляющий запчасти.
#|uk| Отже, ми впоралися з витяганням <code>LaborItem</code>. Але є ще один момент. Оскільки ціна запчастин <code>unitPrice</code> використовується тільки в класі <code>JobItem</code> і не потрібна в <code>LaborItem</code>, ми можемо піти далі і знову застосувати до <code>JobItem</code> <a href="/extract-subclass">Витягання підкласу</a> і створити клас, що представляє запчастини.

Go to after "JobItem"

Print:
```

class PartsItem extends JobItem {
  protected $unitPrice;

  public function __construct($quantity, $unitPrice) {
    parent::__construct($quantity);
    $this->unitPrice = $unitPrice;
  }
  public function getUnitPrice() {
    return $this->unitPrice;
  }
}
```

Wait 500ms

Select "new JobItem"

Replace "new PartsItem"

Wait 500ms

Select in "JobItem":
```
  private $unitPrice;

```
+Select ", 0"
+Select ", $unitPrice" in "JobItem"
+Select in "JobItem":
```
    $this->unitPrice = $unitPrice;

```

Remove selected

Wait 500ms

Select whole "getUnitPrice"

Replace:
```
  public abstract function getUnitPrice();

```


Select type of "JobItem"

#|ru| В итоге класс <code>JobItem</code> станет абстрактным.
#|uk| У підсумку клас <code>JobItem</code> стане абстрактним.

Replace "abstract class"

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.