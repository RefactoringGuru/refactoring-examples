replace-data-value-with-object:csharp

###

1. Создайте новый класс и скопируйте в него ваше поле и свойство для доступа к нему.

2. Создайте конструктор, принимающий начальное значение этого поля.

3. В исходном классе поменяйте тип поля на новый класс.

4. Измените свойства и методы по работе с полем так, чтобы они делегировали исполнение новому классу.



###

```
public class Order
{
  // ...
  private string customer;

  public string Customer
  {
    get { return customer; }
    set { customer = value; }
  }

  public Order(string customer)
  {
    this.Customer = customer;
  }
}
//...
// Client code, which uses Order class.
private static int NumberOfOrdersFor(List<Order> orders, string customer)
{
  int result = 0;

  if (orders != null)
  {
    foreach (Order order in orders)
    {
      if (string.Equals(order.Customer, customer))
      {
        result++;
      }
    }
  }

  return result;
}
```

###

```
public class Order
{
  // ...
  private Customer customer;

  public string CustomerName
  {
    get { return customer.Name; }
    set { customer.Name = value; }
  }

  public Order(string customerName)
  {
    this.customer = new Customer(customerName);
  }
}

public class Customer
{
  public string Name
  {
    get;
    set;
  }

  public Customer(string name)
  {
    this.Name = name;
  }
}
//...
// Client code, which uses Order class.
private static int NumberOfOrdersFor(List<Order> orders, string customer)
{
  int result = 0;

  if (orders != null)
  {
    foreach (Order order in orders)
    {
      if (string.Equals(order.CustomerName, customer))
      {
        result++;
      }
    }
  }

  return result;
}
```

###

Set step 1

# Давайте рассмотрим рефакторинг <i>Замена простого поля объектом</i> на примере класса заказа.

Select "private string |||customer|||"

# В данном примере покупатель в классе заказа хранится в виде строки. Однако мы могли бы создать для покупателей свой класс и перенести в него все данные и операции, связанные с покупателями.

Go to after "Order"

Print:
```


public class Customer
{
}
```

Select name of "Customer"

# Итак, класс готов. Давайте перенесём в него поле имени покупателя, т.к. оно используется в остальном коде заказа. И поскольку к данному полю потребуется доступ извне, то мы сразу преобразуем его в публичное свойство.

Go to end of "Customer"

Print:
```

  public string Name
  {
    get;
    set;
  }
```

Set step 2

Go to end of "Customer"

# Здесь же создадим конструктор, принимающий начальное значение имени.

Print:
```


  public Customer(string name)
  {
    this.Name = name;
  }
```

Set step 3

Select "private string customer"

# После этого можно изменить тип поля <code>customer</code>. А также изменить связанные с ним методы и свойства, чтобы они теперь работали с экземпляром класса <code>Customer</code>.

Select "private |||string||| customer"

# Начнём с изменения типа поля покупателя.

Replace "Customer"

Set step 4

Select "public string Customer"

# Затем нам надо решить, что делать с публичным свойством. Тут есть 2 пути:<br>1. Поменять тип свойства на <code>Customer</code>, чтобы оно позволяло напрямую работать с экземпляром класса. Тем самым мы предоставим клиентскому коду полный доступ к экземпляру покупателя.<br>2. Оставить тип свойства неизменным. Тем самым мы ограничим смысловой контекст его использования (в данном случае ограничим только работой с именем покупателя).

# В рассматриваемом примере мы пойдем по второму пути, оставив свойству только работу с именем покупателя. Для этого преобразуем его геттер и сеттер.

Select "return |||customer|||;"
+Select "|||customer||| = value;"

Replace "customer.Name"

Wait 500ms

Select name of "public Order"

# Теперь изменим конструктор так, чтобы он заполнял поле покупателя новым экземпляром класса <code>Customer</code>.

Select "this.|||Customer|||"

Replace "customer"

Select "= |||customer|||" in "public Order"

Replace "new Customer(customer)"

Select "new Customer(customer)"

# Обратите внимание на то, что конструктор каждый раз создаёт новый экземпляр класса покупателя, при том что других установщиков значения поля у нас нет. Это значит, что покупатель является объектом-значением и в каждом заказе находится собственный экземпляр <code>Customer</code>. Другими словами, одному и тому же покупателю в разных заказах будут соответствовать разные экземпляры класса <code>Customer</code>.

# Как правило, объекты-значения должны быть неизменяемыми, благодаря чему удаётся избежать некоторых неприятных ошибок, связанных с тем, что объекты всегда передаются по ссылкам. Кстати, позднее нам потребуется, чтобы <code>string Customer</code> стал объектом-ссылкой, но для этого нужно будет применить ещё один рефакторинг.

#C В данный момент можно выполнить компиляцию и тестирование.

#S Всё отлично, код работает корректно.

Select name of "Order"

# Осталось разобраться с именованием тех областей класса <code>Order</code>, в которых производилась правка кода для работы с новым типом данных.

Select "private Customer |||customer|||"

# Поле <code>customer</code> представляет собой экземпляр класса <code>Customer</code>, поэтому переименовывать его не нужно.

Select "public string |||Customer|||"

# А вот свойство <code>Customer</code> позволяет теперь работать только с именем покупателя, поэтому логичнее будет переименовать его в <code>CustomerName</code>.

Replace "CustomerName"

Wait 500ms

Select "(order.|||Customer|||"

# При этом надо также произвести замену названия свойства и в остальном коде (в основных средах разработки данный процесс автоматизирован).

Replace "CustomerName"

Wait 500ms

Select "string |||customer|||" in parameters of "public Order"
+ Select "(|||customer|||)" in "public Order"

# И в завершение, заменим название параметра в конструкторе, чтобы было понятно, что в него передается имя покупателя.

Replace "customerName"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Select "private Customer |||customer|||"

# Прежде чем закончить хотелось бы обратить ваше внимание на то, что здесь, как и во многих других случаях, надо сделать ещё одну вещь. Если требуется добавить к клиенту оценку кредитоспособности, адрес и т.п., то в настоящий момент сделать это нельзя, потому что <code>customer</code> задействован как объект-значение. То есть в каждом заказе находится собственный экземпляр  класса <code>Customer</code>.

# Чтобы создать в классе <code>Customer</code> требуемые атрибуты, необходимо применить к нему рефакторинг <a href="/change-value-to-reference">замена значения ссылкой</a>. После этого все заказы для одного и того же покупателя будут ссылаться на один и тот же экземпляр класса <code>Customer</code>.

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.