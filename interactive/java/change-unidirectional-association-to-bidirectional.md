java--java--java--java--change-unidirectional-association-to-bidirectional:java

###

1. Добавьте поле, которое будет содержать обратную связь.

2. Решите, какой класс будет «управляющим классом».

3. Создайте служебный метод установки связи в «неуправляющем классе».

4. Если старые методы управления однонаправленной связью находились в «управляющем классе», дополните их вызовами служебных методов из связываемого объекта.

5. Если старые методы управления связью находились в «неуправляющем классе», создайте управляющие методы в «управляющем классе» и вызывайте и делегируйте им выполнение.



###

```
class Order {
  // ...
  private Customer customer;

  public Customer getCustomer() {
    return customer;
  }
  public void setCustomer(Customer arg) {
    customer = arg;
  }
}

class Customer {
  // ...
}
```

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
}
```

###

Set step 1

# Давайте рассмотрим <i>Замену однонаправленной связи двунаправленной</i> на примере двух классов — <code>Покупателя</code> и <code>Заказа</code>.

Select:
```
private Customer customer;
```

# Изначально, в классе заказов есть ссылка на объект покупателя.

Select name of "Customer"

# С другой стороны, класс покупателей не содержит ссылок на объекты заказов. Таким образом, если в методе объекта-покупателя потребуется получить объект заказа этого покупателя, это придётся делать окольными путями — медленно и неудобно.

# Итак, начнём рефакторинг с добавления поля заказов в класс <code>Покупатель</code>. Так как покупатель может иметь несколько заказов, сделаем поле коллекцией.

Go to the end of "Customer"

Print:
```

  private Set orders = new HashSet();
```

Set step 2

Select name of "Order"
+ Select name of "Customer"

# Теперь надо решить, какой из классов будет отвечать за управление связью. Лучше всего возлагать ответственность на один класс, т. к. это позволяет хранить всю логику управления связью в одном месте.

# Процедура принятия решения выглядит так:<ul><li>Если оба объекта представляют собой объекты ссылок, и связь имеет тип «один-ко-многим», то управляющим будет объект, содержа-щий одну ссылку. (То есть если у одного клиента несколько заказов, связью управляет заказ.)</li><li>Если один объект является компонентом другого (т. е. связь имеет тип «целое-часть»), управлять связью должен составной объект.</li><li>Если оба объекта представляют собой объекты ссылок, и связь имеет тип «многие-ко-многим», то в качестве управляющего можно произвольно выбрать класс заказа или класс клиента.</li></ul>

Set step 3

Go to the end of "Customer"

# Поскольку отвечать за связь будет заказ, добавим к покупателю вспомогательный метод, предоставляющий доступ к коллекции заказов.

Print:
```


  // Should be used in Order class only.
  Set friendOrders() {
    return orders;
  }
```

Set step 4

Select name of "setCustomer"

# Теперь можно изменить сеттер класса Заказ так, чтобы он добавлял текущий объект заказа в список заказов пользователя.

Go to the start of "setCustomer"

Print:
```

    // Remove order from old customer.
    if (customer != null) {
      customer.friendOrders().remove(this);
    }
```

Wait 1000ms

Go to the end of "setCustomer"

Print:
```

    // Add order to new customer.
    if (customer != null) {
      customer.friendOrders().add(this);
    }
```

Go to:
```
    // Remove order from old customer.|||
```

#< Точный код в управляющем модификаторе зависит от кратности связи. Если <code>customer</code> не может быть <code>null</code>, можно обойтись без проверки его на <code>null</code>, но надо проверять на <code>null</code> аргумент.

Go to:
```
    // Add order to new customer.|||
```
#< Тем не менее, базовая схема всегда одинакова: сначала нужно «отвязать» текущий связанный объект, чтобы он удалил ссылку на ваш объект; затем связь с ним заменяется новым объектом; затем в новом объекте добавляется обратная ссылка на ваш объект.

Set step 5

Go to the end of "Customer"

# Если вы хотите модифицировать ссылку через класс покупателя, он должен вызывать управляющий метод в связуемом объекте заказа:

Print:
```

  void addOrder(Order arg) {
    arg.setCustomer(this);
  }
```

#C Запускаем финальную компиляцию и тестирование.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
