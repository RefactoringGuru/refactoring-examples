change-bidirectional-association-to-unidirectional:csharp

###

1. Убедитесь, что один из следующих пунктов имеет место для ваших классов:<ul><li>связь не используется вообще;</li><li>есть другой способ получить связанный объект (например, используя запрос в базу данных);</li><li>связанный объект может быть передан как аргумент в методы, которые используют его.</li></ul>

2. В зависимости от вашей ситуации, использование поля, содержащего связь с другим объектом, должно быть заменено параметром, свойством или вызовом метода, который бы достал связанный объект другим путём.

3. Удалите код присваивания связанного объекта полю.

4. Удалите неиспользуемое теперь поле.



###

```
public class Order
{
  // ...
  private Customer customer;

  public Customer Customer
  {
    get {
      return customer;
    }
    set {
      // Remove order from old customer.
      if (customer != null)
      {
        customer.Orders.Remove(this);
      }
      customer = value;
      // Add order to new customer.
      if (customer != null)
      {
        customer.Orders.Add(this);
      }
    }
  }

  public double GetDiscountedPrice()
  {
    return GetGrossPrice() * (1 - this.Customer.GetDiscount());
  }
}

public class Customer
{
  // ...
  private HashSet<Order> orders = new HashSet<Order>();

  // Should be used in Order class only.
  public HashSet<Order> Orders
  {
    get {
      return orders;
    }
  }

  public void AddOrder(Order order)
  {
    order.Customer = this;
  }

  public double GetPriceFor(Order order)
  {
     Assert.IsTrue(orders.Contains(order));
     return order.GetDiscountedPrice();
  }
}
```

###

```
public class Order
{
  // ...
  public Customer Customer
  {
    get {
      foreach (Customer customer in Customer.GetInstances())
      {
        if (customer.ContainsOrder(this))
          return customer;
      }
      return null;
    }
  }

  public double GetDiscountedPrice()
  {
    return GetGrossPrice() * (1 - this.Customer.GetDiscount());
  }
}

public class Customer
{
  // ...
  private HashSet<Order> orders = new HashSet<Order>();

  public void AddOrder(Order order)
  {
    orders.Add(order);
  }

  public double GetPriceFor(Order order)
  {
     Assert.IsTrue(orders.Contains(order));
     return order.GetDiscountedPrice();
  }
}
```

###

Set step 1

# <i>Замену двунаправленной связи однонаправленной</i> мы продолжим с того места, на котором закончили в обратном рефакторинге.

Select name of "Order"
+ Select name of "Customer"

# У нас имеются классы <code>Покупатель</code> и <code>Заказ</code> с двусторонней связью.

# С момента окончания прошлого рефакторинга, в наш код успели внедриться два новых метода.

Select name of "GetPriceFor"

#V Метод получения цены заказа внутри класса покупателя.

+ Select name of "GetDiscountedPrice"

# А также метод получения цены со скидкой в классе заказа.

Select "private |||Customer||| customer;"

# Итак, недавно к нам поступили новые требования, в которых говорится, что заказы должны появляться, только в том случае, если покупатель уже создан. Это позволяет нам отказаться от двусторонней связи между классами и избавиться от связи заказа к покупателю.

Select "|||this.Customer|||.GetDiscount()"

# Самое трудное в данном рефакторинге – проверка возможности его осуществления. Необходимо убедиться в безопасности, а выполнить сам рефакторинг довольно просто. Проблема заключается в том, зависит ли код заказа от наличия поля покупателя. В этом случае, чтобы удалить поле, надо предоставить альтернативный способ получения объекта покупателя.

Set step 2

#^= Первым делом изучаются все операции чтения поля и все методы, использующие эти операции. Есть ли другой способ предоставить объект покупателя? Часто это означает, что надо передавать покупателя в качестве аргумента операции.

Go to parameters of "GetDiscountedPrice"

Print "Customer customer"

Wait 500ms

Select "|||this.Customer|||.GetDiscount()"

Replace "customer"


Select:
```
     return order.GetDiscountedPrice();
```

# Особенно хорошо это действует, когда поведение вызывается клиентским кодом, в котором присутствует объект покупателя, который можно передать в качестве аргумента.

Go to:
```
     return order.GetDiscountedPrice(|||);
```

Print "this"

Wait 1000ms

Select "    |||return customer;|||"

# Другая рассматриваемая альтернатива – такое изменение геттера свойства, которое позволяет ему получать покупателя, не используя поле. Тогда можно применить к телу <code>Order.Customer.get</code> <a href="/substitute-algorithm">Замещение алгоритма</a> и сделать нечто подобное следующим действиям.

Print:
```
foreach (Customer customer in Customer.GetInstances())
      {
        if (customer.ContainsOrder(this))
          return customer;
      }
      return null;
```

Select parameters of "GetDiscountedPrice"

# Предыдущее введение параметра в метод теперь можно убрать, т.к. геттер свойства <code>this.Customer</code> будет возвращать корректный объект.

Remove selected

Select "|||customer|||.GetDiscount()"

Replace "this.Customer"


Select "GetDiscountedPrice(|||this|||);"
Remove selected

Select:
```
    |||get||| {
      foreach (Customer customer in Customer.GetInstances())
```

# Медленно, но работает. В контексте базы данных выполнение может даже несколько ускорить, если применить запрос базы данных.

Set step 3

Select:
```
  // Should be used in Order class only.
  public HashSet<Order> Orders
  {
    get {
      return orders;
    }
  }

```

+ Select body of "AddOrder"

# Теперь можно подготовиться к удалению использования свойства <code>order.Customer</code>, заменив его присваивание в коде класса покупателя прямым добавлением объектов-заказов в коллекцию.

Select:
```

  // Should be used in Order class only.
  public HashSet<Order> Orders
  {
    get {
      return orders;
    }
  }

```

Remove selected

Select body of "AddOrder"

Type:
```
    orders.Add(order);
```

Select:
```
    set {
      // Remove order from old customer.
      if (customer != null)
      {
        customer.Orders.Remove(this);
      }
      customer = value;
      // Add order to new customer.
      if (customer != null)
      {
        customer.Orders.Add(this);
      }
    }

```

# Затем нужно удалить сеттер свойства в классе заказа, где происходило присваивание нового значения покупателя.

Remove selected

Set step 4

Select:
```
  private Customer customer;


```

# Осталось удалить само поле, полностью избавившись от двунаправленной связи между классами.

Remove selected

#C Запускаем финальную компиляцию и тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.