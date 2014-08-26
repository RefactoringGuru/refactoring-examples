replace-data-value-with-object:java

###

1. Создайте новый класс и скопируйте в него ваше поле и геттер для доступа к нему.

2. Создайте конструктор, принимающий начальное значение этого поля.

3. В исходном классе поменяйте тип поля на новый класс.

4. Измените методы доступа так, чтобы они делегировали исполнение новому классу.



###

```
class Order {
  // ...
  private String customer;

  public Order(String customer) {
    this.customer = customer;
  }
  public String getCustomer() {
    return customer;
  }
  public void setCustomer(String customer) {
    this.customer = customer;
  }
}

// Client code, which uses Order class.
private static int numberOfOrdersFor(Collection orders, String customer) {
  int result = 0;
  Iterator iter = orders.iterator();
  while (iter.hasNext()) {
    Order each = (Order) iter.next();
    if (each.getCustomer().equals(customer)) {
      result++;
    }
  }
  return result;
}
```

###

```
class Order {
  // ...
  private Customer customer;

  public Order(String customerName) {
    this.customer = new Customer(customerName);
  }
  public String getCustomerName() {
    return customer.getName();
  }
  public void setCustomer(String customerName) {
    this.customer = new Customer(customerName);
  }
}

class Customer {
  private final String name;

  public Customer(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}

// Client code, which uses Order class.
private static int numberOfOrdersFor(Collection orders, String customer) {
  int result = 0;
  Iterator iter = orders.iterator();
  while (iter.hasNext()) {
    Order each = (Order) iter.next();
    if (each.getCustomerName().equals(customer)) {
      result++;
    }
  }
  return result;
}
```

###

Set step 1

# Давайте рассмотрим рефакторинг <i>Замена простого поля объектом</i> на примере класса заказа.

Select "private String |||customer|||"

# В данном примере покупатель в классе заказа хранится в виде строки. Однако мы могли бы создать для покупателей свой класс и перенести в него все данные и операции, связанные с покупателями.

Go to after "Order"

Print:
```


class Customer {
}
```

# Итак, класс готов. Давайте перенесём в него поле имени покупателя, т.к. оно используется в остальном коде заказа.

Go to end of "Customer"

Print:
```

  private final String name;

  public String getName() {
    return name;
  }
```

Set step 2

Go to before of "getName"

# Здесь же создадим конструктор, принимающий начальное значение имени.

Print:
```

  public Customer(String name) {
    this.name = name;
  }
```

Set step 3

Select "private String customer"

# После этого можно изменить тип поля <code>customer</code>. А также изменить связанные с ним методы, чтобы они теперь работали с экземпляром класса <code>Customer</code>.

# Начнём с изменения типа поля покупателя.

Select "private |||String||| customer"

Replace "Customer"

Set step 4

# Затем сделаем так, чтобы геттер имени пользователя возвращал значение из связанного объекта.

Select "return |||customer|||" in "getCustomer"

Replace "customer.getName()"

Select name of "public Order"
+ Select name of "setCustomer"

# Теперь изменим конструктор и сеттер доступа так, чтобы они заполняли поле покупателя новым объектом <code>Customer</code>.

Select "= |||customer|||" in "public Order"
+ Select "= |||customer|||" in "setCustomer"

Replace "new Customer(customer)"

Select name of "setCustomer"

# Обратите внимание на то, что сеттер каждый раз создаёт новый экземпляр класса покупателя. Это значит, что покупатель является объектом-значением и в каждом заказе находится собственный экземпляр <code>Customer</code>. Другими словами, одному и тому же покупателю в разных заказах будут соответствовать разные экземпляры класса <code>Customer</code>.

# Как правило, объекты-значения должны быть неизменяемыми, благодаря чему удаётся избежать некоторых неприятных ошибок, связанных с тем, что объекты всегда передаются по ссылкам. Кстати, позднее нам потребуется, чтобы <code>customer</code> стал объектом-ссылкой, но для этого нужно будет применить ещё один рефакторинг.

#C В данный момент можно выполнить компиляцию и тестирование.

#S Всё отлично, код работает корректно.

Go to name of "Order"

# Осталось рассмотреть методы <code>Order</code>, работающие с <code>Customer</code>, и произвести некоторые изменения, призванные прояснить новое положение вещей.

Select name of "getCustomer"

# Применим к геттеру <a href="/rename-method">переименование метода</a>, чтобы стало ясно, что он возвращает имя, а не объект.

Print "getCustomerName"

Wait 500ms

Select "each.|||getCustomer|||"

Replace "getCustomerName"

Select "String |||customer|||" in parameters of "public Order"
+ Select "(|||customer|||)" in "public Order"
+ Select "String |||customer|||" in parameters of "setCustomer"
+ Select "(|||customer|||)" in "setCustomer"

# Кроме того, не помешает изменить названия параметров в конструкторе и сеттера.

Print "customerName"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

# Прежде чем закончить хотелось бы обратить ваше внимание на то, что здесь, как и во многих других случаях, надо сделать ещё одну вещь. Если требуется добавить к клиенту оценку кредитоспособности, адрес и т.п., то в настоящий момент сделать это нельзя, потому что <code>Customer</code> задействован как объект-значение. То есть в каждом заказе находится собственный экземпляр  класса <code>Customer</code>.

# Чтобы создать в классе <code>Customer</code> требуемые атрибуты, необходимо применить к нему рефакторинг <a href="/change-value-to-reference">замена значения ссылкой</a>. После этого все заказы для одного и того же покупателя будут ссылаться на один и тот же экземпляр класса <code>Customer</code>.

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.