change-bidirectional-association-to-unidirectional:csharp

###

1.ru. Убедитесь, что один из следующих пунктов имеет место для ваших классов:<ul><li>связь не используется вообще;</li><li>есть другой способ получить связанный объект (например, используя запрос в базу данных);</li><li>связанный объект может быть передан как аргумент в методы, которые используют его.</li></ul>
1.en. Make sure that one of the following is true for your classes:<ul><li>Association is not used at all,</li><li>Another way of getting the associated object is available (such as by querying a database), or</li><li>The association object can be passed as an argument to the methods that use it.</li></ul>
1.uk. Переконайтеся, що один з наступних пунктів має місце для ваших класів: <ul><li>зв'язок не використовується взагалі;</li><li>є інший спосіб отримати зв'язаний об'єкт (наприклад, використовуючи запит до бази даних); </li ><li>зв'язаний об'єкт може бути переданий як аргумент в методи, які використовують його.</li></ul>

2.ru. В зависимости от вашей ситуации использование поля, содержащего связь с другим объектом, должно быть заменено параметром, свойством или вызовом метода, который бы достал связанный объект другим путём.
2.en. Depending on your situation, instead of using a field containing an association with the relevant object, you may want to use a parameter, property, or method call for obtaining the associated object in a different way.
2.uk. Залежно від вашої ситуації використання поля, яке містить зв'язок з іншим об'єктом, має бути замінене параметром, властивістю або викликом методу, який би дістав зв'язаний об'єкт іншим шляхом.

3.ru. Удалите код присваивания связанного объекта полю.
3.en. Delete the code that assigns the associated object to the field.
3.uk. Видаліть код привласнення пов'язаного об'єкту полю.

4.ru. Удалите неиспользуемое теперь поле.
4.en. Delete the now-unused field.
4.uk. Видаліть невживане тепер поле.



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

Select name of "GetPriceFor"

#|ru|V Метод получения цены заказа внутри класса покупателя.
#|en|V Method for getting order price inside the customer class, and
#|uk|V Метод отримання ціни замовлення всередині класу покупця.

+ Select name of "GetDiscountedPrice"

#|ru| А также метод получения цены со скидкой в классе заказа.
#|en| Method for getting price with discount in the order class
#|uk| А також метод отримання ціни зі знижкою в класі замовлення.

Select "private |||Customer||| customer;"

#|ru| Итак, недавно к нам поступили новые требования, в которых говорится, что заказы должны появляться, только в том случае, если покупатель уже создан. Это позволяет нам отказаться от двусторонней связи между классами и избавиться от связи заказа к покупателю.
#|en| Recently we received new requirements: orders must appear only if the customer has already been created. This lets us forego bidirectional association between the classes and get rid of the association between the order and customer.
#|uk| Отже, нещодавно до нас надійшли нові вимоги, в яких йдеться про те, що замовлення повинні з'являтися, тільки в тому випадку, якщо покупець вже створений. Це дозволяє нам відмовитися від двонаправленого зв'язку між класами та позбутися від зв'язку замовлення до покупця.

Select "|||this.Customer|||.GetDiscount()"

#|ru| Самое трудное в данном рефакторинге – проверка возможности его осуществления. Необходимо убедиться в безопасности, а выполнить сам рефакторинг довольно просто. Проблема заключается в том, зависит ли код заказа от наличия поля покупателя. В этом случае, чтобы удалить поле, надо предоставить альтернативный способ получения объекта покупателя.
#|en| The hardest part of this refactoring technique is making sure that it is possible. Refactoring is easy, but you must make sure that it is safe to do so. The problem comes down to whether the order code depends on the presence of the customer field. If that is the case, removing the field requires that you provide an alternative method for getting the customer object.
#|uk| Найважче в даному рефакторингу – перевірка можливості його здійснення. Необхідно перш за все переконатися в безпеці, а виконати сам рефакторинг досить просто. Проблема полягає в тому, чи залежить код замовлення від наявності поля покупця. В цьому випадку, щоб видалити поле, треба надати альтернативний спосіб отримання об'єкта покупця.

Set step 2

#|ru|^= Первым делом изучаются все операции чтения поля и все методы, использующие эти операции. Есть ли другой способ предоставить объект покупателя? Часто это означает, что надо передавать покупателя в качестве аргумента операции.
#|en|^= First review all read operations involving the field and all methods that use these operations. Is there another way to provide the customer object? Often this means passing the customer as an operation argument.
#|uk|^= В прешу чергу вивчаються всі операції читання поля і всі методи, які використовують ці операції. Чи є інший спосіб надати об'єкт покупця? Найчастіше це означає, що необхідно передавати покупця як аргумент операції.

Go to parameters of "GetDiscountedPrice"

Print "Customer customer"

Wait 500ms

Select "|||this.Customer|||.GetDiscount()"

Replace "customer"


Select:
```
     return order.GetDiscountedPrice();
```

#|ru| Особенно хорошо это действует, когда поведение вызывается клиентским кодом, в котором присутствует объект покупателя, который можно передать в качестве аргумента.
#|en| This works particularly well when behavior is called by client code containing a customer object that can be passed as an argument.
#|uk| Особливо добре це діє, коли поведінка викликається клієнтським кодом, в якому присутній об'єкт покупця, який можна передати в якості аргументу.

Go to:
```
     return order.GetDiscountedPrice(|||);
```

Print "this"

Wait 1000ms

Select "    |||return customer;|||"

#|ru| Другая рассматриваемая альтернатива – такое изменение геттера свойства, которое позволяет ему получать покупателя, не используя поле. Тогда можно применить к телу <code>Order.Customer.get</code> <a href="/substitute-algorithm">Замещение алгоритма</a> и сделать нечто подобное следующим действиям.
#|en| Another alternative to consider is changing the getter of the property that allows it to get the customer without using the field. Then you can apply <a href="/substitute-algorithm">Replace Algorithm</a> to the body of <code>Order.Customer.get</code> and do something similar to the actions outlined below.
#|uk| Інша альтернатива, яку можна розглянути – така зміна геттера властивості, яка дозволяє йому отримувати покупця, не використовуючи поле. Тоді можна застосувати до тіла <code>Order.Customer.get</code> <a href="/substitute-algorithm">Заміщення алгоритму</a> і зробити щось подібне наступним діям.

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

#|ru| Предыдущее введение параметра в метод теперь можно убрать, т.к. геттер свойства <code>this.Customer</code> будет возвращать корректный объект.
#|en| The previous insertion of the parameter in the method can now be removed, since the getter of the <code>this.Customer</code> property will return the correct object.
#|uk| Попереднє введення параметра в метод тепер можна прибрати, тому що геттер властивості <code>this.Customer</code> повертатиме коректний об'єкт.

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

#|ru| Медленно, но работает. В контексте базы данных выполнение может даже несколько ускорить, если применить запрос базы данных.
#|en| Slow… but it works. In the context of a database, things may even become a little faster if a database query is used.
#|uk| Повільно, але працює. В контексті бази даних виконання може навіть дещо прискорити, якщо застосувати запит бази даних.

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

#|ru| Теперь можно подготовиться к удалению использования свойства <code>order.Customer</code>, заменив его присваивание в коде класса покупателя прямым добавлением объектов-заказов в коллекцию.
#|en| Now you can prepare to remove use of the <code>order.Customer</code> property, replacing its assignment in the code of the customer class with direct addition of order objects to the collection.
#|uk| Тепер можна підготуватися до видалення використання властивості <code>order.Customer</code>, замінивши її присвоювання в коді класу покупця прямим додаванням об'єктів-замовлень в колекцію.

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

#|ru| Затем нужно удалить сеттер свойства в классе заказа, где происходило присваивание нового значения покупателя.
#|en| Then remove the property setter in the order class where assignment of the new customer value had taken place.
#|uk| Потім потрібно видалити сетер властивості в класі замовлення, де відбувалося присвоювання нового значення покупця.

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