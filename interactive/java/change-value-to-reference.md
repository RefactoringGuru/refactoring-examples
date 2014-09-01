change-value-to-reference:java

###

1. Используйте <a href="/replace-constructor-with-factory-method" target="_blank">замену конструктора фабричным методом</a> над классом, который должен порождать объекты-ссылки.

2. Определите, какой объект будет ответственным за предоставление доступа к объектам-ссылкам. Вместо создания нового объекта, когда он нужен, вам теперь нужно получать его из какого-то объекта-хранилища или статического поля-словаря.

3. Определите, будут ли объекты-ссылки создаваться заранее или динамически по мере надобности. Если объекты создаются предварительно, необходимо обеспечить их загрузку перед использованием.

4. Измените фабричный метод так, чтобы он возвращал объект-ссылку. Если объекты создаются заранее, необходимо решить, как обрабатывать ошибки при запросе несуществующего объекта.



###

```
class Customer {
  private final String name;
  public Customer(String name) {
    name = name;
  }
  public String getName() {
    return name;
  }
}

class Order {
  //...
  private Customer customer;
  public String getCustomerName() {
      return customer.getName();
  }
  public void setCustomer(String customerName) {
    customer = new Customer(customerName);
  }
  public Order(String customerName) {
    customer = new Customer(customerName);
  }
}

// Some client code, which uses Order class.
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

```
class Customer {
  private static Dictionary instances = new Hashtable();

  // This code should be executed at the program launch.
  static void loadCustomers() {
    new Customer("Lemon Car Hire").store();
    new Customer("Associated Coffee Machines").store();
    new Customer("Bilston Gasworks").store();
  }
  private void store() {
    instances.put(this.getName(), this);
  }

  private final String name;
  public static Customer getNamed(String name) {
    return (Customer) instances.get(name);
  }
  private Customer(String name) {
    name = name;
  }
  public String getName() {
    return name;
  }
}

class Order {
  //...
  private Customer customer;
  public String getCustomerName() {
      return customer.getName();
  }
  public void setCustomer(String customerName) {
    customer = Customer.getNamed(customerName);
  }
  public Order(String customerName) {
    customer = Customer.getNamed(customerName);
  }
}

// Some client code, which uses Order class.
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

# Давайте рассмотрим <i>Замену значения ссылкой</i> на примере класса заказа и покупателя. Мы продолжим с того места, где закончили пример <a href="/replace-data-value-with-object">Замена простого поля объектом</a>.

Select name of "Customer"

#+ В данном случае имеется класс покупателя...

Select name of "Order"

#+ ...который используется в классе заказов...

Select name of "numberOfOrdersFor"

#= ...и некоторый клиентский код, который использует оба класса.

Select name of "Customer"

# В данный момент покупатель в классе заказа используется как объект-значение. Т.е. каждый заказ имеет собственный экземпляр <code>Сustomer</code>, даже если это один и тот же реальный покупатель. Надо изменить код так, чтобы при наличии нескольких заказов для одного покупателя в них использовался один и тот же экземпляр класса <code>Сustomer</code>.

# В нашем случае это означает, что для каждого имени покупателя должен существовать только один экземпляр класса покупателя.

# Начнём с <a href="/replace-constructor-with-factory-method">замены конструктора фабричным методом</a>. Это позволит нам контролировать процесс создания объектов покупателей, что является крайне важным моментом. Итак, создадим фабричный метод в классе покупателя.

Go to before "public Customer"
Print:
```

  public static Customer create(String name) {
    return new Customer(name);
  }
```

Select name of "public Order"

# Затем заменим вызов конструктора класса <code>Customer</code> обращением к фабричному методу.

Select "new Customer(customerName)"

Replace "Customer.create(customerName)"

Select visibility of "public Customer"

# После чего можно сделать конструктор покупателя закрытым.

Print "private"

Set step 2

Select name of "Customer"

# Теперь надо решить, какой объект будет ответственным за предоставление доступа к экземплярам класса покупателей. В общем случае для этого хорошо бы иметь какой-то объект-реестр, который будет содержать пул всех объектов-ссылок и получать нужные экземпляры из него. Например, если нужно сделать несколько товарных позиций в заказе, каждая позиция может храниться внутри объекта заказа.

# Однако в данной ситуации для покупателей такого объекта нет. Чтобы не создавать новый класс для хранения реестра покупателей, можно организовать хранение с помощью статического поля в классе <code>Сustomer</code>.

Go to the start of "Customer"

Print:
```

  private static Dictionary instances = new Hashtable();

```

Set step 3

# Затем надо решить, как создавать покупателей – заранее или динамически (по мере надобности). Воспользуемся первым способом. При запуске приложения мы будем загружать тех клиентов, которые находятся в работе. Их можно взять, например, из базы данных или из файла.

# В целях простоты используем для загрузки покупателей явный код. Впоследствии всегда можно будет изменить его с помощью <a href="/substitute-algorithm">замещения алгоритма</a>.

Print:
```

  // This code should be executed at the program launch.
  static void loadCustomers() {
    new Customer("Lemon Car Hire").store();
    new Customer("Associated Coffee Machines").store();
    new Customer("Bilston Gasworks").store();
  }
  private void store() {
    instances.put(this.getName(), this);
  }

```

Set step 4

Select name of "create"

# Теперь модифицируем фабричный метод класса <code>Customer</code> так, чтобы он возвращал заранее созданного покупателя.

Select "new Customer(name)" in "create"

Replace "(Customer) instances.get(name)"

Select name of "create"

# И поскольку метод <code>Create()</code> теперь всегда возвращает уже существующего покупателя, надо это пояснить с помощью <a href="/rename-method">переименования метода</a>.

Print "getNamed"

Wait 500ms

Select "Customer.|||create|||"

Replace "getNamed"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.