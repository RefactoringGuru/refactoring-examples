change-bidirectional-association-to-unidirectional:php

###

1.ru. Убедитесь, что один из следующих пунктов имеет место для ваших классов:<ul><li>связь не используется вообще;</li><li>есть другой способ получить связанный объект (например, используя запрос в базу данных);</li><li>связанный объект может быть передан как аргумент в методы, которые используют его.</li></ul>
1.en. Make sure that one of the following is true for your classes:<ul><li>Association is not used at all,</li><li>Another way of getting the associated object is available (such as by querying a database), or</li><li>The association object can be passed as an argument to the methods that use it.</li></ul>
1.uk. Переконайтеся, що один з наступних пунктів має місце для ваших класів: <ul><li>зв'язок не використовується взагалі;</li><li>є інший спосіб отримати зв'язаний об'єкт (наприклад, використовуючи запит до бази даних); </li ><li>зв'язаний об'єкт може бути переданий як аргумент в методи, які використовують його.</li></ul>

2.ru. В зависимости от вашей ситуации использование поля, содержащего связь с другим объектом, должно быть заменено параметром или вызовом метода, который бы достал связанный объект другим путём.
2.en. Depending on your situation, instead of using a field containing an association with the relevant object, you may want to use a parameter or method call for obtaining the associated object in a different way.
2.uk. Залежно від вашої ситуації використання поля, що містить зв'язок з іншим об'єктом, воно повинно бути замінено параметром або викликом методу, який би дістав пов'язаний об'єкт іншим шляхом.

3.ru. Удалите код присваивания связанного объекта полю.
3.en. Delete the code that assigns the associated object to the field.
3.uk. Видаліть код привласнення пов'язаного об'єкту полю.

4.ru. Удалите неиспользуемое теперь поле.
4.en. Delete the now-unused field.
4.uk. Видаліть невживане тепер поле.



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
    if ($this->customer != null) {
      $this->customer->friendOrders()->remove($this);
    }
    $this->customer = $arg;
    // Add order to new customer.
    if ($this->customer != null) {
      $this->customer->friendOrders()->add($this);
    }
  }

  public function getDiscountedPrice() {
    return $this->getGrossPrice() * (1 - $this->getCustomer()->getDiscount());
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

  public function getPriceFor(Order $order) {
     assert(array_search($order, $this->orders, TRUE), "Order can not be found");
     return $order->getDiscountedPrice();
  }
}
```

###

```
class Order {
  // ...
  public function getCustomer() {
    foreach (Customer::getInstances() as $customer) {
      if ($customer->containsOrder($this)) {
        return $customer;
      }
    }
    return null;
  }

  public function getDiscountedPrice() {
    return $this->getGrossPrice() * (1 - $this->getCustomer()->getDiscount());
  }
}

class Customer {
  // ...
  private $orders = array();

  public function addOrder(Order $arg) {
    $this->orders[] = $arg;
  }

  public function getPriceFor(Order $order) {
     assert(array_search($order, $this->orders, TRUE), "Order can not be found");
     return $order->getDiscountedPrice();
  }
}
```

###

Set step 1

#|ru| <i>Замену двунаправленной связи однонаправленной</i> мы продолжим с того места, на котором закончили в обратном рефакторинге.
#|en| We will start <i>Change Bidirectional Association to Unidirectional</i> from the place where we have stopped in the inverse refactoring.
#|uk| <i>Заміну двонаправленного зв'язку однонаправленим</i> ми продовжимо з того ж місця, на якому закінчили в зворотньому рефакторингу.

Select name of "Order"
+ Select name of "Customer"

#|ru| У нас имеются классы <code>Покупатель</code> и <code>Заказ</code> с двусторонней связью.
#|en| In other words, we have <code>Customer</code> and <code>Order</code> classes with a bidirectional association.
#|uk| Ми маємо класи <code>Покупець</code> та <code>Замовлення</code> з двонаправленим зв'язком.

#|ru| С момента окончания прошлого рефакторинга, в наш код успели внедриться два новых метода.
#|en| Two new methods have been added to the code since completion of the previous refactoring.
#|uk| З моменту закінчення минулого рефакторинга, в наш код встигли впровадитися два нових методи.

Select name of "getPriceFor"

#|ru|V Метод получения цены заказа внутри класса покупателя.
#|en|V First, the method for getting order price in the customer object.
#|uk|V Метод отримання ціни замовлення всередині класу покупця.

+ Select name of "getDiscountedPrice"

#|ru| А также метод получения цены со скидкой в классе заказа.
#|en| Then the method for getting price with discount in the order class.
#|uk| А також метод отримання ціни зі знижкою в класі замовлення.

Select "private |||$customer|||; // Customer"

#|ru| Итак, недавно к нам поступили новые требования, в которых говорится, что заказы должны появляться, только в том случае, если покупатель уже создан. Это позволяет нам отказаться от двусторонней связи между классами и избавиться от связи заказа к покупателю.
#|en| Few days ago a new requirement was received, which says that orders must only be created only for existing customers. This lets us eliminate the bidirectional association between orders and customer, and only keeping customers aware of their orders.
#|uk| Отже, нещодавно до нас надійшли нові вимоги, в яких йдеться про те, що замовлення повинні з'являтися, тільки в тому випадку, якщо покупець вже створений. Це дозволяє нам відмовитися від двонаправленого зв'язку між класами та позбутися від зв'язку замовлення до покупця.

Select "|||$this->getCustomer()|||->getDiscount()"

#|ru|+ Самое трудное в данном рефакторинге – проверка возможности его осуществления. Необходимо убедиться в безопасности, а выполнить сам рефакторинг довольно просто. Проблема заключается в том, зависит ли код заказа от наличия поля покупателя. В этом случае, чтобы удалить поле, надо предоставить альтернативный способ получения объекта покупателя.
#|en|+ The hardest part of this refactoring technique is making sure that it is possible. Refactoring itself is easy, but we must make sure that it is safe. The problem comes down to whether any of order class' code needs a customer field. If that is the case, removing the field requires you to provide an alternative method for getting the customer object.
#|uk|+ Найважче в даному рефакторингу – перевірка можливості його здійснення. Необхідно перш за все переконатися в безпеці, а виконати сам рефакторинг досить просто. Проблема полягає в тому, чи залежить код замовлення від наявності поля покупця. В цьому випадку, щоб видалити поле, треба надати альтернативний спосіб отримання об'єкта покупця.

Set step 2

#|ru|^= Первым делом изучаются все операции чтения поля и все методы, использующие эти операции. Есть ли другой способ предоставить объект покупателя? Часто это означает, что надо передавать покупателя в качестве аргумента операции.
#|en|^= First, we review all usages of the customer field and it's getter. Is there another way to provide the customer object or it's data? Often thу best solution means passing the customer as an argument to the methods, which use the field.
#|uk|^= В прешу чергу вивчаються всі операції читання поля і всі методи, які використовують ці операції. Чи є інший спосіб надати об'єкт покупця? Найчастіше це означає, що необхідно передавати покупця як аргумент операції.

Go to parameters of "getDiscountedPrice"

Print "Customer $customer"

Wait 500ms

Select "|||$this->getCustomer()|||->getDiscount()"

Replace "$customer"

Select:
```
     return $order->getDiscountedPrice();
```

#|ru| Особенно хорошо это действует, когда поведение вызывается клиентским кодом, в котором присутствует объект покупателя, который можно передать в качестве аргумента.
#|en| This works particularly well for methods that called by client code already containing a customer object. In this case, you just pass it as a method's argument.
#|uk| Особливо добре це діє, коли поведінка викликається клієнтським кодом, в якому присутній об'єкт покупця, який можна передати в якості аргументу.

Go to:
```
     return $order->getDiscountedPrice(|||);
```

Print "$this"

Wait 1000ms

Select body of "getCustomer"

#|ru| Другая рассматриваемая альтернатива – такое изменение метода получения данных, которое позволяет ему получать покупателя, не используя поле. Тогда можно применить к телу <code>Order.getCustomer</code> <a href="/ru/substitute-algorithm">Замещение алгоритма</a> и сделать что-то вроде следующего.
#|en| Another alternative is to change the customer field getter so that it would get the right object by looking it in some object repository like this:
#|uk| Інша розглянута альтернатива – така зміна методу отримання даних, яка дозволяє йому отримувати покупця, не використовуючи поле. Тоді можна застосувати до тіла <code>Order.getCustomer</code> <a href="/uk/substitute-algorithm">Заміщення алгоритму</a> і зробити щось на кшталт такого.

Print:
```
    foreach (Customer::getInstances() as $customer) {
      if ($customer->containsOrder($this)) {
        return $customer;
      }
    }
    return null;
```

Select parameters of "getDiscountedPrice"

#|ru| Предыдущее введение параметра в метод теперь можно убрать, так как геттер покупателя будет возвращать корректный объект.
#|en| The previous introduction of a parameter into the method can now be removed since the customer getter will return the correct object.
#|uk| Попереднє введення параметру в метод тепер можна прибрати, оскільки геттер покупця повертатиме коректний об'єкт.

Remove selected

Select "|||$customer|||->getDiscount()"

Replace "$this->getCustomer()"

Wait 500ms

Select "getDiscountedPrice(|||$this|||);"

Remove selected

Select name of "getCustomer"

#|ru| Медленно, но работает. В контексте базы данных выполнение может даже несколько ускорить, если применить запрос базы данных.
#|en| Slow… But it works. In the context of a database, things may even become a little faster if a database query is used.
#|uk| Повільно, але працює. В контексті бази даних виконання може навіть дещо прискорити, якщо застосувати запит бази даних.

Set step 3

Select:
```
  // Should be used in Order class only.

```
+ Select whole "friendOrders"
+ Select body of "addOrder"

#|ru| Теперь можно подготовиться к удалению метода <code>setCustomer</code>, заменив их вызовы в коде класса покупателя прямым добавлением объектов-заказов в коллекцию.
#|en| Now prepare to remove the <code>setCustomer</code> method by replacing their calls in the code of the customer class with direct addition of order objects to the collection.
#|uk| Тепер можна підготуватися до видалення методу <code>setCustomer</code>, замінивши їх виклики в коді класу покупця прямим додаванням об'єктів-замовлень в колекцію.

Select:
```
  // Should be used in Order class only.

```
+ Select whole "friendOrders"

Remove selected

Select body of "addOrder"

Type:
```
    $this->orders[] = $arg;
```

Select whole "setCustomer"

#|ru| А затем удаляем метод присваивания нового значения покупателя в классе заказа.
#|en| Then we can freely remove the method in the order class.
#|uk| А потім видаляємо метод присвоювання нового значення покупця в класі замовлення.

Remove selected

Set step 4

Select:
```
  private $customer; // Customer


```

#|ru| Осталось удалить само поле, полностью избавившись от двунаправленной связи между классами.
#|en| At last, we can remove the field itself, fully eliminating the bidirectional association between the classes.
#|uk| Залишилося видалити саме поле, повністю позбавившись від двонаправленого зв'язку між класами.

Remove selected

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