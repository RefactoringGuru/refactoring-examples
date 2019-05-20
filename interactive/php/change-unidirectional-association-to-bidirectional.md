change-unidirectional-association-to-bidirectional:php

###

1.ru. Добавьте поле, которое будет содержать обратную связь.
1.en. Add a field for holding the reverse association.
1.uk. Додайте поле, яке міститиме зворотний зв'язок.

2.ru. Решите, какой класс будет «управляющим классом».
2.en. Decide which class will be the "dominant" one.
2.uk. Вирішіть, який клас буде «управляючим класом».

3.ru. Создайте служебный метод установки связи в «не управляющем классе».
3.en. Create a utility property for establishing the association in the "non-controlling" class.
3.uk. Створіть службовий метод для установки зв'язку в «неуправляючому класі».

4.ru. Если старые методы управления однонаправленной связью находились в «управляющем классе», дополните их вызовами служебных методов из связываемого объекта.
4.en. If old methods for controlling the unidirectional association were in the "dominant" class, complement them with calls to utility methods from the associated object.
4.uk. Якщо старі методи управління одностороннім зв'язком знаходилися в «управляючому класі», доповните їх викликами службових методів із зв'язуваного об'єкту.

5.ru. Если старые методы управления связью находились в «не управляющем классе», то реализуйте алгоритм управления в «управляющем классе» и делегируйте им выполнение из «не управляющего класса».
5.en. If the old methods for controlling the association were in the non-dominant class, implement a control algorithm in the dominant class and delegate execution to them from the non-dominant class.
5.uk. Якщо старі методи управління зв'язком перебували в «неуправляючому класі», то реалізуйте алгоритм управління в «управляючому класі» і делегуйте їм виконання з «неуправляючого класу».


###

```
class Order {
  // ...
  private $customer; // Customer

  public function getCustomer() {
    return $this->customer;
  }
  public function setCustomer(Customer $arg) {
    $this->customer = $arg;
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
  private $customer; // Customer

  public function getCustomer() {
    return $this->customer;
  }
  public function setCustomer(Customer $arg) {
    // Remove order from old customer.
    if (isset($this->customer)) {
      $this->customer->friendOrders()->remove($this);
    }
    $this->customer = $arg;
    // Add order to new customer.
    if (isset($this->customer)) {
      $this->customer->friendOrders()->add($this);
    }
  }
}

class Customer {
  // ...
  private $orders = array();

  // Should be used in Order class only.
  public function friendOrders() {
    return $orders;
  }
  public function addOrder(Order $arg) {
    $arg->setCustomer($this);
  }
}
```

###

Set step 1

#|ru| Давайте рассмотрим <i>Замену однонаправленной связи двунаправленной</i> на примере двух классов — <code>Покупателя</code> и <code>Заказа</code>.
#|en| Let's consider <i>Replace Unidirectional Association with Bidirectional</i> using the example of two classes: <code>Customer</code> and <code>Order</code>.
#|uk| Давайте розглянемо <i>Заміну однонаправленого зв'язку двонаправленим</i> на прикладі двох класів – <code>Покупця</code> і <code>Замовлення</code>.

Select:
```
private $customer;
```

#|ru| Изначально, в классе заказов есть ссылка на объект покупателя.
#|en| The order class contains a reference to the customer object.
#|uk| Спочатку, в класі замовлень є посилання на об'єкт покупця.

Select name of "Customer"

#|ru| С другой стороны, класс покупателей не содержит ссылок на объекты заказов. Таким образом, если в методе объекта-покупателя потребуется получить объект заказа этого покупателя, это придётся делать окольными путями — медленно и неудобно.
#|en| However, the customer class does not have references to order objects. Thus, if you need to get an order object from the method of a customer object, you will have to usу some roundabout ways that are slow and inconvenient.
#|uk| З іншого боку, клас покупців не містить посилань на об'єкти замовлень. Таким чином, якщо в методі об'єкта-покупця буде потрібно отримати об'єкт замовлення цього покупця, це доведеться робити іншим способом (повільно та незручно).

#|ru| Итак, начнём рефакторинг с добавления поля заказов в класс <code>Покупатель</code>. Так как покупатель может иметь несколько заказов, сделаем поле коллекцией.
#|en| Let's start refactoring by adding order fields to the <code>Customer</code> class. Since a customer can have multiple orders, we should make the field a collection.
#|uk| Отже, почнемо рефакторинг з додавання поля замовлень в клас <code>Покупець</code>. Оскільки покупець може мати кілька замовлень, зробимо поле колекцією.

Go to the end of "Customer"

Print:
```

  private $orders = array();
```

Set step 2

Select name of "Order"
+ Select name of "Customer"

#|ru| Теперь надо решить, какой из классов будет отвечать за управление связью. Лучше всего возлагать ответственность на один класс, т. к. это позволяет хранить всю логику управления связью в одном месте.
#|en| Now we need to decide which class will be responsible for managing the association. It is better to place responsibility with a single class because this allows keeping the entire logic in a single place.
#|uk| Тепер треба вирішити, який з класів відповідатиме за управління зв'язком. Найкраще покладати відповідальність на один клас, тому що це дозволяє зберігати всю логіку управління зв'язком в одному місці.

#|ru| Процедура принятия решения выглядит так:<ul><li>Если оба объекта представляют собой объекты ссылок, и связь имеет тип «один-ко-многим», то управляющим будет объект, содержащий одну ссылку. (То есть если у одного клиента несколько заказов, связью управляет заказ.)</li><li>Если один объект является компонентом другого (т. е. связь имеет тип «целое-часть»), управлять связью должен составной объект.</li><li>Если оба объекта представляют собой объекты ссылок, и связь имеет тип «многие-ко-многим», то в качестве управляющего можно произвольно выбрать класс заказа или класс клиента.</li></ul>
#|en| By the way, here's how to decide this:<ul><li>If both objects are reference objects and the association is one-to-many, the “control” object will be the one that contains one reference. So if one client has many orders, the association is controlled by the order.</li><li>If one object is a component of the other (whole–part association), the “whole” object should control the association.</li><li>If both objects are reference objects, and the association is many-to-many: you can select either the order class or client class as the control class.</li></ul>
#|uk| Процедура прийняття рішення виглядає так: <ul><li>Якщо обидва об'єкти представляють собою об'єкти посилань, і зв'язок має тип «один-до-багатьох», то керуючим буде той об'єкт, що містить одне посилання. (Тобто якщо у одного клієнта кілька замовлень, зв'язком управляє замовлення.)</li><li>Якщо один об'єкт є компонентом іншого (тобто зв'язок має тип «ціле-частина»), управляти зв'язком повинен складений об'єкт.</li><li>Якщо обидва об'єкти представляють собою об'єкти посилань, і зв'язок має тип «багато-до-багатьох», то в якості керуючого можна довільно вибрати клас замовлення або клас клієнта.</li></ul>

Set step 3

Go to the end of "Customer"

#|ru| Поскольку отвечать за связь будет заказ, добавим к покупателю вспомогательный метод, предоставляющий доступ к коллекции заказов.
#|en| In our case, the order class will be responsible for the association. For this reason, we add a helper method to the customer class, which will provide access to the order collection.
#|uk| Оскільки відповідати за зв'язок буде замовлення, додамо до покупця допоміжний метод, який надає доступ до колекції замовлень.

Print:
```


  // Should be used in Order class only.
  public function friendOrders() {
    return $orders;
  }
```

Set step 4

Select name of "setCustomer"

#|ru| Теперь можно изменить сеттер свойства у класса <code>Заказ</code> так, чтобы он добавлял текущий объект заказа в список заказов пользователя.
#|en| Now we can change the field's setter in the <code>Order</code> class so that it would add the current order object to the list of customer orders.
#|uk| Тепер можна змінити сетер властивості у класу <code>Замовлення</code> так, щоб він додавав поточний об'єкт замовлення до списку замовлень користувача.

Go to the start of "setCustomer"

Print:
```

    // Remove order from old customer.
    if (isset($this->customer)) {
      $this->customer->friendOrders()->remove($this);
    }
```

Wait 1000ms

Go to the end of "setCustomer"

Print:
```

    // Add order to new customer.
    if (isset($this->customer)) {
      $this->customer->friendOrders()->add($this);
    }
```

Go to:
```
    // Remove order from old customer.|||
```

#|ru|< Точный код в управляющем модификаторе зависит от кратности связи. Если <code>Customer</code> не может быть <code>null</code>, можно обойтись без проверки его на <code>null</code>, но при этом аргумент проверять на <code>null</code> надо.
#|en|< The exact code in the controlling modifier varies with the multiplicity of the association. If <code>Customer</code> cannot be <code>null</code>, you can get by without checking it for <code>null</code> but in that case you should check the argument for <code>null</code>.
#|uk|< Точний код в керуючому модифікаторі залежить від кратності зв'язку. Якщо <code>Customer</code> не може бути <code>null</code>, можна обійтися без перевірки його на <code>null</code>, але треба перевіряти на <code>null</code> аргумент.

Go to:
```
    // Add order to new customer.|||
```
#|ru|< Тем не менее, базовая схема всегда одинакова: сначала нужно «отвязать» текущий связанный объект, чтобы он удалил ссылку на ваш объект; затем связь с ним заменяется новым объектом; затем в новом объекте добавляется обратная ссылка на ваш объект.
#|en|< The basic pattern is always the same, however: first tell the other object to remove its pointer to you, set your pointer to the new object, and then tell the new object to add a pointer to you.
#|uk|< Тим не менш, базова схема завжди однакова: спочатку потрібно «відв'язати» поточний зв'язаний об'єкт, щоб він видалив посилання на ваш об'єкт; потім зв'язок з ним замінюється новим об'єктом; потім в новому об'єкті додається зворотнє посилання на ваш об'єкт.

Set step 5

Go to the end of "Customer"

#|ru| Если вы хотите модифицировать ссылку через класс покупателя, он должен вызывать управляющий метод в связуемом объекте заказа:
#|en| If you want to modify a reference through the customer class, the class should call the control method in the associated order object:
#|uk| Якщо ви хочете модифікувати посилання через клас покупця, він повинен викликати керуючий метод в з'єднуваному об'єкті замовлення:

Print:
```

  public function addOrder(Order $arg) {
    $arg->setCustomer($this);
  }
```

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.