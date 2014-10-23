introduce-null-object:java

###

1.ru. Из интересующего вас класса создайте подкласс, который будет выполнять роль Null-объекта.
1.uk. З потрібного вам класу створіть підклас, який виконуватиме роль Null-об'єкту.

2.ru. В обоих классах создайте метод <code>isNull()</code>, который будет возвращать <code>true</code> для Null-объекта и <code>false</code> для реального класса.
2.uk. У обох класах створіть метод <code>isNull()</code>, який повертатиме <code>true</code> для Null-об'єкту і <code>false</code> для реального класу.

3.ru. Найдите все места, где код может вернуть <code>null</code> вместо реального объекта. Измените этот код так, чтобы он возвращал Null-объект.
3.uk. Знайдіть усі місця, де код може повернути <code>null</code> замість реального об'єкту. Змініть цей код так, щоб він повертав Null-об'єкт.

4.ru. Найдите все места, где переменные реального класса сравниваются с <code>null</code>. Замените такие проверки вызовом метода <code>isNull()</code>.
4.uk. Знайдіть усі місця, де змінні реального класу порівнюються з <code>null</code>. Замініть такі перевірки викликом методу <code>isNull()</code>.

5.ru. <ul><li>Если в этих условных операторах при значении не равном <code>null</code> выполняются методы исходного класса, переопределите эти методы в Null-классе и вставьте туда код из <code>else</code> части условия. После этого условный оператор можно будет вообще удалить, а разное поведение будет осуществляться за счёт полиморфизма.</li><li>Если не все так просто, и методы переопределить не получается, посмотрите, можно ли просто выделите операции, которые должны были выполняться при значении равном <code>null</code> в новые методы Null-объекта. Вызывайте эти методы вместо старого кода в <code>else</code> как операции по умолчанию.</li></ul>
5.uk. <ul><li>Якщо в цих умовних операторах при значенні, не рівному <code>null</code>, виконуються методи початкового класу, перевизначить ці методи в Null-класі і вставте туди код з  частини умови <code>else</code>. Після цього умовний оператор можна буде взагалі видалити, а різна поведінка здійснюватиметься за рахунок поліморфізму.</li><li>Якщо не все так просто, і методи перевизначити не виходить, подивіться, чи можна просто виділити операції, які повинні були виконуватися при значенні рівному <code>null</code> в нові методи Null-об'єкту. Викликайте ці методи замість старого коду в <code>else</code> як операції за умовчанням.</li></ul>



###

```
class Company {
  //...
  private Customer customer;
  public Customer getCustomer() {
    return customer;
  }
}

class Customer {
  //...
  public String getName() {
    //...
  }
  public BillingPlan getPlan() {
    //...
  }
  public PaymentHistory getHistory() {
    //...
  }
}

class PaymentHistory {
  public int getWeeksDelinquentInLastYear() {
    //...
  }
}

// Somewhere in client code
Customer customer = site.getCustomer();
String customerName;
if (customer == null) {
  customerName = "N/A";
}
else {
  customerName = customer.getName();
}

//...
BillingPlan plan;
if (customer == null) {
  plan = BillingPlan.basic();
}
else {
  plan = customer.getPlan();
}

//...
int weeksDelinquent;
if (customer == null) {
  weeksDelinquent = 0;
}
else {
  weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
}
```

###

```
class Company {
  //...
  private Customer customer;
  public Customer getCustomer() {
    return (customer == null) ? Customer.newNull() : customer;
  }
}

class Customer {
  //...
  public boolean isNull() {
    return false;
  }
  public static Customer newNull() {
    return new NullCustomer();
  }

  public String getName() {
    //...
  }
  public BillingPlan getPlan() {
    //...
  }
  public PaymentHistory getHistory() {
    //...
  }
}
class NullCustomer extends Customer {
  public boolean isNull() {
    return true;
  }
  public String getName() {
    return "N/A";
  }
  public String getPlan() {
    return BillingPlan.basic();
  }
  public PaymentHistory getHistory() {
    return PaymentHistory.newNull();
  }
}

class PaymentHistory {
  public boolean isNull() {
    return false;
  }
  public static PaymentHistory newNull() {
    return new NullPaymentHistory();
  }

  public int getWeeksDelinquentInLastYear() {
    //...
  }
}
class NullPaymentHistory extends PaymentHistory {
  public boolean isNull() {
    return true;
  }
  public int getWeeksDelinquentInLastYear() {
    return 0;
  }
}

// Somewhere in client code
Customer customer = site.getCustomer();
String customerName = customer.getName();

//...
BillingPlan plan = customer.getPlan();

//...
int weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
```

###

Set step 1

Select name of "Company"

#|ru| Рассмотрим рефакторинг на примере классов коммерческой компании.
#|uk| Розглянемо рефакторинг на прикладі класів комерційної компанії.

Select name of "Customer"

#|ru| Каждая компания знает своих покупателей (<code>Customer</code>).
#|uk| Кожна компанія знає своїх покупців (<code>Customer</code>).

Select "getName" in "Customer"
+Select "getPlan" in "Customer"
+Select "getHistory" in "Customer"

#|ru| В свою очередь у покупателей есть свои свойства и поведения.
#|uk| В свою чергу у покупців є свої властивості та поведінки.

Go to "// Somewhere in client code"

#|ru| Клиентский код оперирует этими методами доступа, чтобы делать какую-то работу. Например, так выглядит код получения имени текущего клиента компании.
#|uk| Клієнтський код оперує цими методами доступу, щоб робити якусь роботу. Наприклад, так виглядає код отримання імені поточного клієнта компанії.

Select "if (customer == null)"

#|ru| Обратите внимание на условный оператор, проверяющий, есть ли клиент в компании. Такая ситуация вполне может приключиться, если компания новая, либо старый покупатель решил сменить поставщика.
#|uk| Зверніть увагу на умовний оператор, перевіряючий, чи є клієнт в компанії. Така ситуація цілком може статись, якщо компанія нова, або старий покупець вирішив змінити постачальника.

#|ru| В коде может быть много таких повторяющихся проверок на <code>null</code>, что сигнализирует о потребности введения Null-объекта.
#|uk| У коді може бути багато таких повторюваних перевірок на <code>null</code>, що сигналізує про потребу введення Null-об'єкта.

#|ru| Первым делом создаём нулевой класс для <code>customer</code> и модифицируем класс <code>Сustomer</code>, чтобы он поддерживал запрос проверки на <code>null</code>.
#|uk| Спершу створюємо нульовий клас для <code>Customer</code> і модифікуємо клас <code>Сustomer</code> так, щоб він підтримував запит перевірки на <code>null</code>.

Set step 2

Go to before "getName"

Print:
```

  public boolean isNull() {
    return false;
  }

```
Go to after "Customer"

Print:
```

class NullCustomer extends Customer {
  public boolean isNull() {
    return true;
  }
}
```

#|ru| Для создания нулевых клиентов введём фабричный метод, благодаря чему клиентам не обязательно будет знать о существовании нулевого класса.
#|uk| Для створення нульових клієнтів введемо фабричний метод, завдяки чому клієнтам не обов'язково буде знати про існування нульового класу.

Go to after "isNull"

Print:
```

  public static Customer newNull() {
    return new NullCustomer();
  }
```

Set step 3

Select "return customer"

#|ru| Теперь мы должны модифицировать все участки кода, где запрашиваются объекты <code>Сustomer</code>, и модифицировать их так, чтобы возвращать нулевого пользователя вместо <code>null</code>.
#|uk| Тепер ми повинні модифікувати всі ділянки коду, де э запит щодо об'єктів <code>Сustomer</code>, і модифікувати їх так, щоб повертати нульового користувача замість <code>null</code>.

Print "return (customer == null) ? Customer.newNull() : customer"


Set step 4

Select "if (|||customer == null|||)"

#|ru| После этого в остальном коде можно заменить все проверки вида <code>Customer == null</code> на вызовы <code>Customer.isNull()</code>.
#|uk| Після цього в іншому коді можна замінити всі перевірки виду <code>Customer == null</code> на виклики <code>Customer.isNull()</code>.

Print "customer.isNull()"

#|ru| Это самая сложная часть данного рефакторинга, т.к. для каждого заменяемого источника <code>null</code> необходимо найти все случаи проверки на <code>null</code> и отредактировать их. Если объект интенсивно передаётся, их может быть нелегко проследить.
#|uk| Це найскладніша частина даного рефакторінга, т.к. для кожного джерела<code>null</code>, що замінюється,  необхідно знайти всі випадки перевірки на <code>null</code> і відредагувати їх. Якщо об'єкт інтенсивно передається, їх може бути нелегко простежити.

#C|ru| После всех замен стоит провести компиляцию и тщательное тестирование.
#S Отлично, все работает, можем продолжать!

#C|uk| Після всіх замін варто провести компіляцію і ретельне тестування.
#S Супер, все працює, можемо продовжувати.

Set step 5

#|ru| В данный момент мы ничего не выигрываем от применения <code>isNull</code> вместо <code>== null</code>. Выгода появится тогда, когда код поведения в нулевой ситуации будет перемещён в нулевой класс, а условные операторы вообще убраны.
#|uk| В даний момент ми нічого не виграємо від застосування <code>isNull</code> замість <code>== null</code>. Вигода з'явиться тоді, коли код поведінки в нульовій ситуації буде переміщений в нульовий клас, а умовні оператори взагалі видалені.

Select "customerName = "N/A""

#|ru| Итак, начнём перемещать поведения в нулевой класс. И первое, что мы сделаем, это перенесём название покупателя по умолчанию в нулевой класс.
#|uk| Отже, почнемо переміщати поведінки в нульовий клас. І перше, що ми зробимо, це перенесемо початкову назву покупця в нульовий клас.

Go to the end of "NullCustomer"

Print:
```

  public String getName() {
    return "N/A";
  }
```

Wait 500ms

Select:
```
String customerName;
if (customer.isNull()) {
  customerName = "N/A";
}
else {
  customerName = customer.getName();
}
```

#|ru| После этого можно убрать проверку на <code>null</code> из части клиентского кода.
#|uk| Після цього можна прибрати перевірку на <code>null</code> з частини клієнтського коду.

Print:
```
String customerName = customer.getName();
```

#|ru| То же самое можно проделать и с остальными методами, к которым возможно придумать поведение по умолчанию.
#|uk| Те ж саме можна зробити і з рештою методів, до яких можливо придумати типову поведінку.

Go to the end of "NullCustomer"

Print:
```

  public String getPlan() {
    return BillingPlan.basic();
  }
```

Wait 500ms

Select:
```
BillingPlan plan;
if (customer.isNull()) {
  plan = BillingPlan.basic();
}
else {
  plan = customer.getPlan();
}
```

Replace:
```
BillingPlan plan = customer.getPlan();
```

Select "customer.getHistory()"

#|ru| При внимательном рассмотрении последнего участка можно заметить, что там содержится потенциальная ошибка доступа уже к объекту оплат, в случае, когда объект пользователя не имеет никакой истории оплат.
#|uk| При уважному розгляді останньої ділянки можна помітити, що там міститься потенційна помилка доступу вже до об'єкта оплат, у разі, коли об'єкт користувача не має ніякої історії оплат.

#|ru| Чтобы решить проблему, можно создать нулевой класс ещё и для класса истории оплат.
#|uk| Щоб вирішити проблему, можна створити нульовий клас ще й для класу історії оплат.

Go to the start of "PaymentHistory"

Print:
```

  public boolean isNull() {
    return false;
  }
```
Go to after "PaymentHistory"

Print:
```

class NullPaymentHistory extends PaymentHistory {
  public boolean isNull() {
    return true;
  }
}
```

Wait 500ms

Go to before "getWeeksDelinquentInLastYear"

Print:
```

  public static PaymentHistory newNull() {
    return new NullPaymentHistory();
  }

```

Go to the end of "NullPaymentHistory"

#|ru| После создания нулевого объекта можно добавить в него поведение по умолчанию.
#|uk| Після створення нульового об'єкта можна додати в нього типову поведінка.

Print:
```

  public int getWeeksDelinquentInLastYear() {
    return 0;
  }
```

Select "customer.getHistory()"

#|ru| Теперь о проблеме доступа к нулевому объекту истории оплат можно не волноваться. Но это ещё не всё.
#|uk| Тепер про проблему доступу до нульового об'єкту історії оплат можна не хвилюватися. Але це ще не все.

#|ru| Мы можем возвращать нулевой объект истории оплат из нулевого объекта покупателей, полностью избавившись от проверок на <code>null</code> в клиентском коде.
#|uk| Ми можемо повертати нульовий об'єкт історії оплат з нульового об'єкта покупців, повністю позбавившись від перевірок на <code>null</code> в клієнтському коді.

Go to the end of "NullCustomer"

Print:
```

  public PaymentHistory getHistory() {
    return PaymentHistory.newNull();
  }
```

Select:
```
int weeksDelinquent;
if (customer.isNull()) {
  weeksDelinquent = 0;
}
else {
  weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
}
```

Replace:
```
int weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
```

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.