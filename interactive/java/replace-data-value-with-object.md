replace-data-value-with-object:java

###

1.ru. Создайте новый класс и скопируйте в него ваше поле и геттер для доступа к нему.
1.en. Create a new class and copy your field and a getter for accessing the field to the class.
1.uk. Створіть новий клас і скопіюйте в нього ваше поле і геттер для доступу до нього.

2.ru. Создайте конструктор, принимающий начальное значение этого поля.
2.en. Create a constructor that accepts the original value of the field.
2.uk. Створіть конструктор, який приймає початкове значення цього поля.

3.ru. В исходном классе поменяйте тип поля на новый класс.
3.en. In the original class, change the field type to the new class.
3.uk. У початковому класі змініть тип поля на новий клас.

4.ru. Измените методы доступа так, чтобы они делегировали исполнение новому классу.
4.en. Change the access methods so that they delegate to the new class.
4.uk. Змініть методи доступу так, щоб вони делегували виконання нового класу.



###

```
class Order {
  // ...
  private String customer;

  public Order(String customer) {
    this.customer = customer;
  }
  public String getCustomer() {
    return customer;
  }
  public void setCustomer(String customer) {
    this.customer = customer;
  }
}

// Client code, which uses Order class.
private static int numberOfOrdersFor(Collection orders, String customer) {
  int result = 0;
  Iterator iter = orders.iterator();
  while (iter.hasNext()) {
    Order each = (Order) iter.next();
    if (each.getCustomer().equals(customer)) {
      result++;
    }
  }
  return result;
}
```

###

```
class Order {
  // ...
  private Customer customer;

  public Order(String customerName) {
    this.customer = new Customer(customerName);
  }
  public String getCustomerName() {
    return customer.getName();
  }
  public void setCustomer(String customerName) {
    this.customer = new Customer(customerName);
  }
}

class Customer {
  private final String name;

  public Customer(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}

// Client code, which uses Order class.
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

#|ru| Давайте рассмотрим рефакторинг <i>Замена простого поля объектом</i> на примере класса заказа.
#|en| Let's look at the <i>Replace Data Value with Object</i> refactoring technique, using an order class as an example.
#|uk| Давайте розглянемо рефакторинг <i>Заміна простого поля об'єктом<i> на прикладі класу замовлення.

Select "private String |||customer|||"

#|ru| В данном примере покупатель в классе заказа хранится в виде строки. Однако мы могли бы создать для покупателей свой класс и перенести в него все данные и операции, связанные с покупателями.
#|en| In this example, the customer in the order class is stored as a string. But we could have created a class just for customers and moved all data and operations related to customers to this class.
#|uk| В даному прикладі покупець в класі замовлення зберігається у вигляді рядка. Однак ми могли б створити для покупців свій клас і перенести в нього всі дані та операції, пов'язані з покупцями.

Go to after "Order"

Print:
```


class Customer {
}
```

#|ru| Итак, класс готов. Давайте перенесём в него поле имени покупателя, т.к. оно используется в остальном коде заказа.
#|en| The class is now ready. Let's move the customer name field to it, since the field is used in the rest of the order code.
#|uk| Отже, клас готовий. Давайте перенесемо в нього поле імені покупця, т.к. воно використовується в іншому коді замовлення.

Go to end of "Customer"

Print:
```

  private final String name;

  public String getName() {
    return name;
  }
```

Set step 2

Go to before of "getName"

#|ru| Здесь же создадим конструктор, принимающий начальное значение имени.
#|en| Here is where we will create a constructor that accepts the initial value of the name.
#|uk| Тут же створимо конструктор, який приймає початкове значення імені.

Print:
```

  public Customer(String name) {
    this.name = name;
  }
```

Set step 3

Select "private String customer"

#|ru| После этого можно изменить тип поля <code>Customer</code>, а также изменить связанные с ним методы таким образом, чтобы они теперь работали с экземпляром класса <code>Customer</code>.
#|en| Then you can change the type of the <code>Customer</code> field and also change the associated methods so that they now act on an instance of the <code>Customer</code> class.
#|uk| Після цього можна змінити тип поля <code>Customer</code>, а також змінити пов'язані з ним методи таким чином, щоб вони тепер працювали з екземпляром класу <code>Customer</code>.

#|ru| Начнём с изменения типа поля покупателя.
#|en| Let's start with changing the type of the customer field.
#|uk| Почнемо з зміни типу поля покупця.

Select "private |||String||| customer"

Replace "Customer"

Set step 4

#|ru| Затем сделаем так, чтобы геттер имени пользователя возвращал значение из связанного объекта.
#|en| Now we will make the getter for the user name return the value from the associated object.
#|uk| Потім зробимо так, щоб геттер імені користувача повертав значення з пов'язаного об'єкта.

Select "return |||customer|||" in "getCustomer"

Replace "customer.getName()"

Select name of "public Order"
+ Select name of "setCustomer"

#|ru| Теперь изменим конструктор и сеттер доступа так, чтобы они заполняли поле покупателя новым объектом <code>Customer</code>.
#|en| Then change the constructor and access setter so that they fill the customer field with a new <code>Customer</code> object.
#|uk| Тепер змінимо конструктор і сетер доступу так, щоб вони заповнювали поле покупця новим об'єктом <code>Customer</code>.

Select "= |||customer|||" in "public Order"
+ Select "= |||customer|||" in "setCustomer"

Replace "new Customer(customer)"

Select name of "setCustomer"

#|ru| Обратите внимание на то, что сеттер каждый раз создаёт новый экземпляр класса покупателя. Это значит, что покупатель является объектом-значением и в каждом заказе находится собственный экземпляр <code>Customer</code>. Другими словами, одному и тому же покупателю в разных заказах будут соответствовать разные экземпляры класса <code>Customer</code>.
#|en| Note that the setter creates a new instance of the customer class each time. This means that the customer is a value and each order contains its own instance of <code>Customer</code>. In other words, one and the same customer will correspond in different orders to different instances of the <code>Customer</code> class.
#|uk| Зверніть увагу на те, що сетер щоразу створює новий екземпляр класу покупця. Це означає, що покупець є об'єктом-значенням і в кожному замовленні знаходиться власний примірник <code>Customer</code>. Іншими словами, одному і тому ж покупцеві в різних замовленнях будуть відповідати різні екземпляри класу <code>Customer</code>.

#|ru| Как правило, объекты-значения должны быть неизменяемыми, благодаря чему удаётся избежать некоторых неприятных ошибок, связанных с тем, что объекты всегда передаются по ссылкам. Кстати, позднее нам потребуется, чтобы <code>customer</code> стал объектом-ссылкой, но для этого нужно будет применить ещё один рефакторинг.
#|en| Generally, value objects should be immutable, which allows avoiding certain unpleasant errors related to the fact that objects are always passed via references. Incidentally, later we will need for <code>customer</code> to become a reference object, but for that we will need to perform yet another refactoring.
#|uk| Як правило, об'єкти-значення повинні бути незмінними, завдяки чому вдається уникнути деяких неприємних помилок, пов'язаних з тим, що об'єкти завжди передаються за посиланнями. До речі, пізніше нам буде потрібно, щоб <code>сustomer</code> став об'єктом-посиланням, але для цього потрібно буде застосувати ще один рефакторинг.

#C|ru| В данный момент можно выполнить компиляцию и тестирование.
#S Всё отлично, код работает корректно.

#C|en| Let's compile and test.
#S Everything is OK! Code works correctly.

#C|uk| В даний момент можна виконати компіляцію і тестування.
#S Все добре, код працює коректно.

Go to name of "Order"

#|ru| Осталось рассмотреть методы <code>Order</code>, работающие с <code>Customer</code>, и произвести некоторые изменения, призванные прояснить новое положение вещей.
#|en| All we have left now is to look at <code>Order</code> methods working with <code>Customer</code> and implement a few changes in order to clarify the new way of things.
#|uk| Залишилося розглянути методи <code>Order</code>, що працюють з <code>Customer</code>, і зробити деякі зміни, які за покликанням повинні прояснити новий стан речей.

Select name of "getCustomer"

#|ru| Применим к геттеру <a href="/rename-method">переименование метода</a>, чтобы стало ясно, что он возвращает имя, а не объект.
#|en| Apply <a href="/rename-method">Rename Method</a> to the getter to make clear that it returns a name, not an object.
#|uk| Застосуємо до геттера <a href="/rename-method">перейменування методу</a>, щоб стало ясно, що він повертає ім'я, а не об'єкт.

Replace "getCustomerName"

Wait 500ms

Select "each.|||getCustomer|||"

Replace "getCustomerName"

Select "String |||customer|||" in parameters of "public Order"
+ Select "(|||customer|||)" in "public Order"
+ Select "String |||customer|||" in parameters of "setCustomer"
+ Select "(|||customer|||)" in "setCustomer"

#|ru| Кроме того, не помешает изменить названия параметров в конструкторе и сеттере.
#|en| It also is a good idea to change the names of the parameters in the constructor and setter.
#|uk| Крім того, не завадить змінити назви параметрів в конструкторі та сетері.

Replace "customerName"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's run the final compile.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

#|ru| Прежде, чем закончить, хотелось бы обратить ваше внимание на то, что здесь, как и во многих других случаях, надо сделать ещё одну вещь. Вам может потребоваться добавить к клиенту оценку кредитоспособности, адрес и т.п. Пока что это сделать нельзя, так как <code>Customer</code> задействован как объект-значение. То есть в каждом заказе находится собственный экземпляр класса <code>Customer</code>.
#|en| Before we finish, note that here and in many other cases, one more step is necessary. You may need to add a credit score, address, etc. to the client. You cannot do this yet, since <code>Customer</code> is used as a value. That is, each order has its own instance of the <code>Customer</code> class.
#|uk| Перш, ніж закінчити, хотілося б звернути вашу увагу на те, що тут, як і в багатьох інших випадках, треба зробити ще одну річ. Вам може знадобитися додати до клієнта оцінку кредитоспроможності, адресу і т.п. Поки що це зробити не можливо, так як <code>Customer</code> задіяний як об'єкт-значення. Тобто в кожному замовленні знаходиться власний примірник класу <code>Customer</code>.

#|ru| Чтобы создать в классе <code>Customer</code> требуемые атрибуты, необходимо применить к нему рефакторинг <a href="/change-value-to-reference">замена значения ссылкой</a>. После этого все заказы для одного и того же покупателя будут ссылаться на один и тот же экземпляр класса <code>Customer</code>.
#|en| To create the necessary attributes in the <code>Customer</code> class, use the <a href="/change-value-to-reference">Change Value to Reference</a> refactoring technique on it. Now all orders for the same customer will refer to the same instance of the <code>Customer</code> class.
#|uk| Щоб створити в класі <code>Customer</code> необхідні атрибути, треба застосувати до нього рефакторинг <a href="/change-value-to-reference">заміна значення посиланням</a>. Після цього всі замовлення для одного і того ж покупця будуть посилатися на той самий  примірник класу <code>Customer</code>.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q Now refactoring is complete. If you like, you can compare the old and new code.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.