change-value-to-reference:csharp

###

1. Используйте <a href="/replace-constructor-with-factory-method" target="_blank">замену конструктора фабричным методом</a> над классом, который должен порождать объекты-ссылки.

2. Определите, какой объект будет ответственным за предоставление доступа к объектам-ссылкам. Вместо создания нового объекта, когда он нужен, вам теперь нужно получать его из какого-то объекта-хранилища или статического поля-словаря.

3. Определите, будут ли объекты-ссылки создаваться заранее или динамически по мере надобности. Если объекты создаются предварительно, необходимо обеспечить их загрузку перед использованием.

4. Измените фабричный метод так, чтобы он возвращал объект-ссылку. Если объекты создаются заранее, необходимо решить, как обрабатывать ошибки при запросе несуществующего объекта.

5. В финальной части рефакторинга произведите проверку тех участков кода, которые раньше позволяли работать с объектом-ссылкой как с объектом-значением, и при необходимости откорректируйте логику их работы под изменившиеся условия.



###

```
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

```
public class Customer
{
  private static Hashtable instances = new Hashtable();

  public string Name
  {
    get;
    private set;
  }

  private Customer(string name)
  {
    this.Name = name;
  }

  public static Customer GetByName(string name)
  {
    return (Customer) instances[name];
  }
  //TODO: This code should be executed at the program launch.
  public static void LoadCustomers()
  {
    new Customer("Lemon Car Hire").Store();
    new Customer("Associated Coffee Machines").Store();
    new Customer("Bilston Gasworks").Store();
  }
  private void Store()
  {
    instances.Add(this.Name, this);
  }
}

public class Order
{
  // ...
  private Customer customer;

  public string CustomerName
  {
    get { return customer.Name; }
  }

  public Order(string customerName)
  {
    SetCustomer(customerName);
  }
  public SetCustomer(string customerName)
  {
    customer = Customer.GetByName(customerName);
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

# Давайте рассмотрим <i>Замену значения ссылкой</i> на примере класса заказа и покупателя. Мы продолжим с того места, где закончили пример <a href="/replace-data-value-with-object">Замена простого поля объектом</a>.

Select name of "Customer"

#+ В данном случае имеется класс покупателя...

Select name of "Order"

#+ ...который используется в классе заказов...

Select name of "NumberOfOrdersFor"

#= ...и некоторый клиентский код, который использует оба класса.

Select "private Customer |||customer|||"
+Select "this.customer = new Customer(customerName);"

# В данный момент покупатель в классе заказа используется как объект-значение. Т.е. каждый заказ имеет собственный экземпляр <code>Сustomer</code>, даже если это один и тот же реальный покупатель. Надо изменить код так, чтобы при наличии нескольких заказов для одного покупателя в них использовался один и тот же экземпляр класса <code>Сustomer</code>.

# В нашем случае это означает, что для каждого имени покупателя должен существовать только один экземпляр класса покупателя.

Select name of "public Customer"

# Начнём с <a href="/replace-constructor-with-factory-method">замены конструктора фабричным методом</a>. Это позволит нам контролировать процесс создания объектов покупателей, что является крайне важным моментом. Итак, создадим фабричный метод в классе покупателя.

Go to after "public Customer"
Print:
```


  public static Customer Create(string name)
  {
    return new Customer(name);
  }
```

Select "new Customer(customerName)"

# Затем заменим вызов конструктора класса <code>Customer</code> обращением к фабричному методу.

Replace "Customer.Create(customerName)"

Select visibility of "public Customer"

# После чего можно сделать конструктор покупателя закрытым.

Print "private"

Set step 2

Select name of "Customer"

# Теперь надо решить, какой объект будет ответственным за предоставление доступа к экземплярам класса покупателей. В общем случае для этого хорошо бы иметь какой-то объект-реестр, который будет содержать пул всех объектов-ссылок, и получать нужные экземпляры из него. Например, если нужно сделать несколько товарных позиций в заказе, каждая позиция может храниться внутри объекта заказа.

# Однако в данной ситуации для покупателей такого объекта нет. Чтобы не создавать новый класс для хранения реестра покупателей, можно организовать хранение с помощью статического поля в классе <code>Сustomer</code>.

Go to the start of "Customer"

Print:
```

  private static Hashtable instances = new Hashtable();

```

Set step 3

# Затем надо решить, как создавать покупателей – заранее или динамически (по мере надобности). Воспользуемся первым способом. При запуске приложения мы будем загружать тех клиентов, которые находятся в работе. Их можно взять, например, из базы данных или из файла.

# В целях простоты используем для загрузки покупателей явный код. Впоследствии всегда можно будет изменить его с помощью <a href="/substitute-algorithm">замещения алгоритма</a>.

Go to after "Customer Create"

Print:
```

  //TODO: This code should be executed at the program launch.
  public static void LoadCustomers()
  {
    new Customer("Lemon Car Hire").Store();
    new Customer("Associated Coffee Machines").Store();
    new Customer("Bilston Gasworks").Store();
  }
  private void Store()
  {
    instances.Add(this.Name, this);
  }
```

Set step 4

Select name of "Create"

# Теперь модифицируем фабричный метод класса <code>Customer</code> так, чтобы он возвращал заранее созданного покупателя.

Select "new Customer(name)" in "Create"

Replace "(Customer) instances[name]"

Select name of "Create"

# И поскольку метод <code>Create()</code> теперь всегда возвращает уже существующего покупателя, надо это пояснить с помощью <a href="/rename-method">переименования метода</a>.

Print "GetByName"

Wait 500ms

Select "Customer.|||Create|||"

Replace "GetByName"

Set step 5

Select name of "Customer"

# После проведения основной части рефакторинга надо произвести аудит тех участков кода, которые раньше позволяли работать с покупателем как с объектом-значением, и проверить актуальность логики их работы в изменившихся условиях.

Select "public string |||Name|||" in "Customer"

# Покупатели теперь создаются с заранее предустановленными именами, поэтому нам следует ограничить доступ к изменению имени покупателя.

Go to "|||set;" in "Customer"

Replace "private "

Select "public string |||CustomerName|||"

Wait 500ms

# Сеттер имени покупателя стал приватным и извне доступен только для чтения, поэтому уберем возможность его установки и в классе заказа.

Select:
```
    set { customer.Name = value; }

```

Remove selected

Select name of "Order"

# В сложившейся ситуации нет возможности динамически устанавливать покупателя для заказа по его имени. Исправим это, добавив в класс <code>Order</code> новый метод.

Select "this.customer = Customer.GetByName(customerName)"

# Новый метод будет содержать код, который в данное время уже используется в конструкторе заказа. Поэтому для его создания воспользуемся <a href="/extract-method">выделением метода</a>.

Go to after "public Order"

Print:
```

  public SetCustomer(string customerName)
  {
    customer = Customer.GetByName(customerName);
  }
```

Select "this.customer = Customer.GetByName(customerName)"

Replace "SetCustomer(customerName)"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.