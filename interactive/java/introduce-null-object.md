introduce-null-object:java

###

1. Из интересующего вас класса создайте подкласс, который будет выполнять роль Null-объекта.

2. В обоих классах создайте метод <code>isNull()</code>, который будет возвращать <code>true</code> для Null-объекта и <code>false</code> для реального класса.

3. Найдите все места, где код может вернуть <code>null</code> вместо реального объекта. Измените этот код так, чтобы он возвращал Null-объект.

4. Найдите все места, где переменные реального класса сравниваются с <code>null</code>. Замените такие проверки вызовом метода <code>isNull()</code>.

5. <ul><li>Если в этих условных операторах при значении не равном <code>null</code> выполняются методы исходного класса, переопределите эти методы в Null-классе и вставьте туда код из <code>else</code> части условия. После этого условный оператор можно будет вообще удалить, а разное поведение будет осуществляться за счёт полиморфизма.</li><li>Если не все так просто, и методы переопределить не получается, посмотрите, можно ли просто выделите операции, которые должны были выполняться при значении равном <code>null</code> в новые методы Null-объекта. Вызывайте эти методы вместо старого кода в <code>else</code> как операции по умолчанию.</li></ul>



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

# Рассмотрим рефакторинг на примере классов коммерческой компании.

Select name of "Customer"

# Каждая компания знает своих покупателей (<code>Customer</code>).

Select "getName" in "Customer"
+Select "getPlan" in "Customer"
+Select "getHistory" in "Customer"

# В свою очередь, у покупателей есть свои свойства и поведения.

Go to "// Somewhere in client code"

# Клиентский код оперирует этими методами доступа, чтобы делать какую-то работу. Например, так выглядит код получения имени текущего клиента компании.

Select "if (customer == null)"

# Обратите внимание на условный оператор, проверяющий, есть ли клиент в компании. Такая ситуация вполне может приключиться, если компания новая, либо старый покупатель решил сменить поставщика.

# В коде может быть много таких повторяющихся проверок на <code>null</code>, что сигнализирует о потребности введения Null-объекта.

# Первым делом создаём нулевой класс для <code>customer</code> и модифицируем класс <code>Сustomer</code>, чтобы он поддерживал запрос проверки на <code>null</code>.

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

# Для создания нулевых клиентов введём фабричный метод, благодаря чему клиентам не обязательно будет знать о существовании нулевого класса.

Go to after "isNull"

Print:
```

  public static Customer newNull() {
    return new NullCustomer();
  }
```

Set step 3

Select "return customer"

# Теперь мы должны модифицировать все участки кода, где запрашиваются объекты <code>Сustomer</code>, и модифицировать их так, чтобы возвращать нулевого пользователя вместо <code>null</code>.

Print "return (customer == null) ? Customer.newNull() : customer"


Set step 4

Select "if (|||customer == null|||)"

# После этого, в остальном коде можно заменить все проверки вида <code>customer == null</code> на вызовы <code>customer.isNull()</code>.

Print "customer.isNull()"

# Это самая сложная часть данного рефакторинга, т.к. для каждого заменяемого источника <code>null</code> необходимо найти все случаи проверки на <code>null</code> и отредактировать их. Если объект интенсивно передаётся, их может быть нелегко проследить.

#C После всех замен стоит провести компиляцию и тщательное тестирование.

#S Отлично, все работает, можем продолжать!

Set step 5

# В данный момент мы ничего не выигрываем от применения <code>isNull</code> вместо <code>== null</code>. Выгода появится тогда, когда код поведение в нулевой ситуации будет перемещён в нулевой класс, а условные операторы вообще убраны.

Select "customerName = "N/A""

# Итак, начнём перемещать поведения в нулевой класс. Начнём с переноса названия покупателя по-умолчанию в нулевой класс.

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

# После этого можно убрать проверку на <code>null</code> из части клиентского кода.

Print:
```
String customerName = customer.getName();
```

# То же можно проделать и с остальными методами, к которым можно придумать поведение по-умолчанию.

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

Print:
```
BillingPlan plan = customer.getPlan();
```

Select "customer.getHistory()"

# При внимательном рассмотрении последнего участка можно заметить, что там содержится потенциальная ошибка доступа уже к объекту оплат, если объект пользователя не имеет никакой истории оплат.

# Чтобы решить проблему, можно создать нулевой класс ещё и для класса истории оплат.

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

# После создания нулевого объекта, можно добавить в него поведения по-умолчанию.

Print:
```

  public int getWeeksDelinquentInLastYear() {
    return 0;
  }
```

Select "customer.getHistory()"

# Теперь о проблеме доступа к нулевому объекту истории оплат можно не волноваться. Но это ещё не всё.

# Мы можем возвращать нулевой объект истории оплат из нулевого объекта покупателей, полностью избавившись от проверок на <code>null</code> в клиентском коде.

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

Print:
```
int weeksDelinquent = customer.getHistory().getWeeksDelinquentInLastYear();
```

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.