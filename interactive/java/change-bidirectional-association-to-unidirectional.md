change-bidirectional-association-to-unidirectional:java

###

1. Убедитесь, что один из следующих пунктов имеет место для ваших классов:<ul><li>связь не используется вообще;</li><li>есть другой способ получить связанный объект (например, используя запрос в базу данных);</li><li>связанный объект может быть передан как аргумент в методы, которые используют его.</li></ul>

2. В зависимости от вашей ситуации, использование поля, содержащего связь с другим объектом, должно быть заменено параметром или вызовом метода, который бы достал связанный объект другим путём.

3. Удалите код присваивания связанного объекта полю.

4. Удалите неиспользуемое теперь поле.



###

```
class Order {
  // ...
  private Customer customer;

  public Customer getCustomer() {
    return customer;
  }
  public void setCustomer(Customer arg) {
    // Remove order from old customer.
    if (customer != null) {
      customer.friendOrders().remove(this);
    }
    customer = arg;
    // Add order to new customer.
    if (customer != null) {
      customer.friendOrders().add(this);
    }
  }

  double getDiscountedPrice() {
    return getGrossPrice() * (1 - getCustomer().getDiscount());
  }
}

class Customer {
  // ...
  private Set orders = new HashSet();

  // Should be used in Order class only.
  Set friendOrders() {
    return orders;
  }
  void addOrder(Order arg) {
    arg.setCustomer(this);
  }

  double getPriceFor(Order order) {
     Assert.isTrue(orders.contains(order));
     return order.getDiscountedPrice();
  }
}
```

###

```
class Order {
  // ...
  public Customer getCustomer() {
    Iterator iter = Customer.getInstances().iterator();
    while (iter.hasNext()) {
      Customer each = (Customer)iter.next();
      if (each.containsOrder(this)) {
        return each;
      }
    }
    return null;
  }

  double getDiscountedPrice() {
    return getGrossPrice() * (1 - getCustomer().getDiscount());
  }
}

class Customer {
  // ...
  private Set orders = new HashSet();

  void addOrder(Order arg) {
    orders.add(arg);
  }

  double getPriceFor(Order order) {
     Assert.isTrue(orders.contains(order));
     return order.getDiscountedPrice();
  }
}
```

###

Set step 1

# <i>Замену двунаправленной связи однонаправленной</i> мы продолжим с того места, на котором закончили в обратном рефакторинге.

Select name of "Order"
+ Select name of "Customer"

# У нас имеются класс <code>Покупатель</code> и <code>Заказ</code> с двусторонней связью.

# С момента окончания прошлого рефакторинга, в наш код успели внедриться два новых метода.

Select name of "getPriceFor"

#V Метод получения цены заказа внутри класса покупателя.

+ Select name of "getDiscountedPrice"

# А также метод получения цены со скидкой в классе заказа.

Select "private |||Customer||| customer;"

# Итак, недавно к нам поступили новые требования, указывающие, что заказы должны появляться, только если покупатель уже создан. Это позволяет нам откзаться от двусторонней связи между классами и избавиться от связи от заказа к покупателю.

Select "|||getCustomer()|||.getDiscount()"

#+ Самое трудное в данном рефакторинге – проверка возможности его осуществления. Необходимо убедиться в безопасности, а выполнить сам рефакторинг довольно легко. Проблема заключается в том, зависит ли код заказа от наличия поля покупателя. В этом случае, чтобы удалить поле, надо предоставить альтернативный способ получения объекта покупателя.

Set step 2

#^= Первым делом изучаются все операции чтения поля и все методы, использующие эти операции. Есть ли другой способ предоставить объект покупателя? Часто это означает, что надо передавать покупателя в качестве аргумента операции.

Go to parameters of "getDiscountedPrice"

Print "Customer customer"

Wait 500ms

Select "|||getCustomer()|||.getDiscount()"

Wait 500ms

Print "customer"


Select:
```
     return order.getDiscountedPrice();
```

# Особенно хорошо это действует, когда поведение вызывается клиентским кодом, в котором присутствует объект покупателя, который можно передать в качестве аргумента.

Go to:
```
     return order.getDiscountedPrice(|||);
```

Print "this"

Wait 1000ms

Select body of "getCustomer"

# Другая рассматриваемая альтернатива – такое изменение метода получения данных, которое позволяет ему получать покупателя, не используя поле. Тогда можно применить к телу <code>Order.getCustomer</code> <a href="/substitute-algorithm">Замещение алгоритма</a> и сделать что-то вроде следующего.

Print:
```
    Iterator iter = Customer.getInstances().iterator();
    while (iter.hasNext()) {
      Customer each = (Customer)iter.next();
      if (each.containsOrder(this)) {
        return each;
      }
    }
    return null;
```

Select parameters of "getDiscountedPrice"

# Предыдущее введение параметра в метод теперь можно убрать, т.к. геттер покупателя будет возвращать корректный объект.

Remove selected

Select "|||customer|||.getDiscount()"

Wait 500ms

Print "getCustomer()"


Select "getDiscountedPrice(|||this|||);"
Remove selected

Select name of "getCustomer"

# Медленно, но работает. В контексте базы данных выполнение может даже быть не таким медленным, если применить запрос базы данных.

Set step 3

Select:
```
  // Should be used in Order class only.

```
+ Select whole "friendOrders"
+ Select body of "addOrder"

# Теперь можно подготовиться к удалению метода <code>setCustomer</code>, заменив их вызовы в коде класса покупателя прямым добавлением объектов-заказов в коллекцию.

Select:
```
  // Should be used in Order class only.

```
+ Select whole "friendOrders"

Remove selected

Select body of "addOrder"

Type:
```
    orders.add(arg);
```

Select whole "setCustomer"

# А затем, удаляем метод присваивания нового значения покупателя в классе заказа.

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
