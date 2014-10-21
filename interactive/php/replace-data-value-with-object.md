replace-data-value-with-object:php

###

1. Создайте новый класс и скопируйте в него ваше поле и геттер для доступа к нему.

2. Создайте конструктор, принимающий начальное значение этого поля.

3. В исходном классе поменяйте тип поля на новый класс.

4. Измените методы доступа так, чтобы они делегировали исполнение новому классу.



###

```
class Order {
  // ...
  private $customer; // String

  public function __construct($customer) {
    $this->customer = $customer;
  }
  public function getCustomer() {
    return $this->customer;
  }
  public function setCustomer($customer) {
    $this->customer = $customer;
  }
}

// Client code, which uses Order class.
private static function numberOfOrdersFor($orders, $customer) {
  $result = 0;
  foreach ($orders as $order) {
    if ($order->getCustomer() === $customer) {
      $result++;
    }
  }
  return $result;
}
```

###

```
class Order {
  // ...
  private $customer; // Customer

  public function __construct($customerName) {
    $this->customer = new Customer($customerName);
  }
  public function getCustomerName() {
    return $this->customer->getName();
  }
  public function setCustomer($customerName) {
    $this->customer = new Customer($customerName);
  }
}

class Customer {
  private $name;

  public function __construct($name) {
    $this->name = $name;
  }
  public function getName() {
    return $this->name;
  }
}

// Client code, which uses Order class.
private static function numberOfOrdersFor($orders, $customer) {
  $result = 0;
  foreach ($orders as $order) {
    if ($order->getCustomerName() === $customer) {
      $result++;
    }
  }
  return $result;
}
```

###

Set step 1

# Давайте рассмотрим рефакторинг <i>Замена простого поля объектом</i> на примере класса заказа.

Select "private |||$customer|||"

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

  private $name;

  public function getName() {
    return $this->name;
  }
```

Set step 2

Go to before of "getName"

# Здесь же создадим конструктор, принимающий начальное значение имени.

Print:
```

  public function __construct($name) {
    $this->name = $name;
  }
```

Set step 3

Select "private $customer"

# После этого можно изменить тип поля <code>Customer</code>, а также изменить связанные с ним методы таким образом, чтобы они теперь работали с экземпляром класса <code>Customer</code>.

# Начнём с изменения типа поля покупателя.

Select "String" in "Order"

Replace "Customer"

Set step 4

# Затем сделаем так, чтобы геттер имени пользователя возвращал значение из связанного объекта.

Select "return |||$this->customer|||" in "getCustomer"

Replace "$this->customer->getName()"

Select name of "__construct"
+ Select name of "setCustomer"

# Теперь изменим конструктор и сеттер доступа так, чтобы они заполняли поле покупателя новым объектом <code>Customer</code>.

Select "= |||$customer|||" in "__construct"
+ Select "= |||$customer|||" in "setCustomer"

Replace "new Customer($customer)"

Select name of "setCustomer"

# Обратите внимание на то, что сеттер каждый раз создаёт новый экземпляр класса покупателя. Это значит, что покупатель является объектом-значением и в каждом заказе находится собственный экземпляр <code>Customer</code>. Другими словами, одному и тому же покупателю в разных заказах будут соответствовать разные экземпляры класса <code>Customer</code>.

# Как правило, объекты-значения должны быть неизменяемыми, благодаря чему удаётся избежать некоторых неприятных ошибок, связанных с тем, что объекты всегда передаются по ссылкам. Кстати, позднее нам потребуется, чтобы <code>customer</code> стал объектом-ссылкой, но для этого нужно будет применить ещё один рефакторинг.

#C В данный момент можно выполнить тестирование.

#S Всё отлично, код работает корректно.

Go to name of "Order"

# Осталось рассмотреть методы <code>Order</code>, работающие с <code>Customer</code>, и произвести некоторые изменения, призванные прояснить новое положение вещей.

Select name of "getCustomer"

# Применим к геттеру <a href="/rename-method">переименование метода</a>, чтобы стало ясно, что он возвращает имя, а не объект.

Replace "getCustomerName"

Wait 500ms

Select "$order->|||getCustomer|||"

Replace "getCustomerName"

Select "$customer" in "__construct"
+ Select "$customer" in "setCustomer"

# Кроме того, не помешает изменить названия параметров в конструкторе и сеттере.

Replace "$customerName"

#C Запускаем финальное тестирование.

#S Отлично, все работает!

# Прежде, чем закончить, хотелось бы обратить ваше внимание на то, что здесь, как и во многих других случаях, надо сделать ещё одну вещь. Вам может потребоваться добавить к клиенту оценку кредитоспособности, адрес и т.п. Пока что это сделать нельзя, так как <code>Customer</code> задействован как объект-значение. То есть в каждом заказе находится собственный экземпляр класса <code>Customer</code>.

# Чтобы создать в классе <code>Customer</code> требуемые атрибуты, необходимо применить к нему рефакторинг <a href="/change-value-to-reference">замена значения ссылкой</a>. После этого все заказы для одного и того же покупателя будут ссылаться на один и тот же экземпляр класса <code>Customer</code>.

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.