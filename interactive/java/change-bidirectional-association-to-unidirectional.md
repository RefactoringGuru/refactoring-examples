change-bidirectional-association-to-unidirectional:java

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

#|ru| <i>Замену двунаправленной связи однонаправленной</i> мы продолжим с того места, на котором закончили в обратном рефакторинге.
#|en| We will continue <i>Change Bidirectional Association to Unidirectional</i> from the place where we stopped in the inverse refactoring.
#|uk| <i>Заміну двонаправленного зв'язку однонаправленим<i> ми продовжимо з того ж місця, на якому закінчили в зворотньому рефакторингу.

Select name of "Order"
+ Select name of "Customer"

#|ru| У нас имеются классы <code>Покупатель</code> и <code>Заказ</code> с двусторонней связью.
#|en| We have <code>Customer</code> and <code>Order</code> classes with a bidirectional association.
#|uk| Ми маємо класи <code>Покупець</code> та <code>Замовлення</code> з двонаправленим зв'язком.

#|ru| С момента окончания прошлого рефакторинга, в наш код успели внедриться два новых метода.
#|en| Since completion of the previous refactoring technique, two new methods have appeared in the code:
#|uk| З моменту закінчення минулого рефакторинга, в наш код встигли впровадитися два нових методи.

Select name of "getPriceFor"

#|ru|V Метод получения цены заказа внутри класса покупателя.
#|en|V Method for getting order price inside the customer class, and
#|uk|V Метод отримання ціни замовлення всередині класу покупця.

+ Select name of "getDiscountedPrice"

#|ru| А также метод получения цены со скидкой в классе заказа.
#|en| Method for getting price with discount in the order class
#|uk| А також метод отримання ціни зі знижкою в класі замовлення.

Select "private |||Customer||| customer;"

#|ru| Итак, недавно к нам поступили новые требования, в которых говорится, что заказы должны создаваться, только в тогда, когда покупатель уже существует. Это позволяет нам отказаться от двусторонней связи между классами и избавиться от связи заказа к покупателю.
#|en| Recently we received new requirements: orders must appear only if the customer has already been created. This lets us forego bidirectional association between the classes and get rid of the association between the order and customer.
#|uk| Отже, нещодавно до нас надійшли нові вимоги, в яких йдеться про те, що замовлення повинні створюватись, тільки тоді, коли покупець вже існує. Це дозволяє нам відмовитися від двонаправленого зв'язку між класами та позбутися від зв'язку замовлення до покупця.

Select "|||getCustomer()|||.getDiscount()"

#|ru|+ Самое трудное в данном рефакторинге – проверка возможности его осуществления. Необходимо убедиться в безопасности, а выполнить сам рефакторинг довольно просто. Проблема заключается в том, зависит ли код заказа от наличия поля покупателя. В этом случае, чтобы удалить поле, надо предоставить альтернативный способ получения объекта покупателя.
#|en|+ The hardest part of this refactoring technique is making sure that it is possible. Refactoring is easy, but you must make sure that it is safe to do so. The problem comes down to whether the order code depends on the presence of the customer field. If that is the case, removing the field requires that you provide an alternative method for getting the customer object.
#|uk|+ Найважче в даному рефакторингу – перевірка можливості його здійснення. Необхідно перш за все переконатися в безпеці, а виконати сам рефакторинг досить просто. Проблема полягає в тому, чи залежить код замовлення від наявності поля покупця. В цьому випадку, щоб видалити поле, треба надати альтернативний спосіб отримання об'єкта покупця.

Set step 2

#|ru|^= Первым делом изучаются все операции чтения поля и все методы, использующие эти операции. Есть ли другой способ предоставить объект покупателя? Часто это означает, что надо передавать покупателя в качестве аргумента операции.
#|en|^= First review all read operations involving the field and all methods that use these operations. Is there another way to provide the customer object? Often this means passing the customer as an operation argument.
#|uk|^= В прешу чергу вивчаються всі операції читання поля і всі методи, які використовують ці операції. Чи є інший спосіб надати об'єкт покупця? Найчастіше це означає, що необхідно передавати покупця як аргумент операції.

Go to parameters of "getDiscountedPrice"

Print "Customer customer"

Wait 500ms

Select "|||getCustomer()|||.getDiscount()"

Replace "customer"


Select:
```
     return order.getDiscountedPrice();
```

#|ru| Особенно хорошо это действует, когда поведение вызывается клиентским кодом, в котором присутствует объект покупателя, который можно передать в качестве аргумента.
#|en| This works particularly well when behavior is called by client code containing a customer object that can be passed as an argument.
#|uk| Особливо добре це діє, коли поведінка викликається клієнтським кодом, в якому присутній об'єкт покупця, який можна передати в якості аргументу.

Go to:
```
     return order.getDiscountedPrice(|||);
```

Print "this"

Wait 1000ms

Select body of "getCustomer"

#|ru| Другая рассматриваемая альтернатива – такое изменение метода получения данных, которое позволяет ему получать покупателя, не используя поле. Тогда можно применить к телу <code>Order.getCustomer</code> <a href="/substitute-algorithm">Замещение алгоритма</a> и сделать что-то вроде следующего.
#|en| Another alternative is to change the method for getting data so as to get the customer while bypassing use of any field. Then you can use <a href="/substitute-algorithm">Substitute Algorithm</a> on the body of <code>Order.getCustomer</code> and do something similar to the following.
#|uk| Інша розглянута альтернатива – така зміна методу отримання даних, яка дозволяє йому отримувати покупця, не використовуючи поле. Тоді можна застосувати до тіла <code>Order.getCustomer</code> <a href="/substitute-algorithm">Заміщення алгоритму</a> і зробити щось на кшталт такого.

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

#|ru| Предыдущее введение параметра в метод теперь можно убрать, т.к. геттер покупателя будет возвращать корректный объект.
#|en| The previous introduction of a parameter into the method can now be removed, since the customer getter will return the correct object.
#|uk| Попереднє введення параметру в метод тепер можна прибрати, так як геттер покупця повертатиме коректний об'єкт.

Remove selected

Select "|||customer|||.getDiscount()"

Replace "getCustomer()"


Select "getDiscountedPrice(|||this|||);"
Remove selected

Select name of "getCustomer"

#|ru| Медленно, но работает. В контексте базы данных выполнение может даже несколько ускорить, если применить запрос базы данных.
#|en| Slow… but it works. In the context of a database, things may even become a little faster if a database query is used.
#|uk| Повільно, але працює. В контексті бази даних виконання може навіть дещо прискорити, якщо застосувати запит бази даних.

Set step 3

Select:
```
  // Should be used in Order class only.

```
+ Select whole "friendOrders"
+ Select body of "addOrder"

#|ru| Теперь можно подготовиться к удалению метода <code>setCustomer</code>, заменив его вызовы в коде класса покупателя прямым добавлением объектов-заказов в коллекцию.
#|en| Prepare to get rid of the <code>setCustomer</code> method: replace calls to it in the code of customer class, instead directly adding order objects to the collection.
#|uk| Тепер можна підготуватися до видалення методу <code>setCustomer</code>, замінивши його виклики в коді класу покупця прямим додаванням об'єктів-замовлень в колекцію.

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

#|ru| А затем удаляем метод присваивания нового значения покупателя в классе заказа.
#|en| Then remove the method that assigns a new customer value in the order class.
#|uk| А потім видаляємо метод присвоювання нового значення покупця в класі замовлення.

Remove selected

Set step 4

Select:
```
  private Customer customer;


```

#|ru| Осталось удалить само поле, полностью избавившись от двунаправленной связи между классами.
#|en| Now just remove the field itself, fully eliminating the bidirectional association between the classes.
#|uk| Залишилося видалити саме поле, повністю позбавившись від двонаправленого зв'язку між класами.

Remove selected

#C|ru| Запускаем финальную компиляцию и тестирование.
#S Отлично, все работает!

#C|en| Let's run the final compile and test.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію і тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.
