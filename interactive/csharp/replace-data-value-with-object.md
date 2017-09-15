replace-data-value-with-object:csharp

###

1.ru. Создайте новый класс и скопируйте в него ваше поле и свойство для доступа к нему.
1.en. Create a new class and copy your field and property for accessing it to the new class.
1.uk. Створіть новий клас і скопіюйте в нього ваше поле і властивість для доступу до нього.

2.ru. Создайте конструктор, принимающий начальное значение этого поля.
2.en. Create a constructor that accepts the original value of the field.
2.uk. Створіть конструктор, який приймає початкове значення цього поля.

3.ru. В исходном классе поменяйте тип поля на новый класс.
3.en. In the original class, change the field type to the new class.
3.uk. У початковому класі змініть тип поля на новий клас.

4.ru. Измените свойства и методы по работе с полем так, чтобы они делегировали исполнение новому классу.
4.en. Change the properties and methods for handling the field so that they delegate to the new class.
4.uk. Змініть властивості та методи по роботі з полем так, щоб вони делегували виконання нового класу.



###

```
public class Order
{
  // ...
  private string customer;

  public string Customer
  {
    get { return customer; }
    set { customer = value; }
  }

  public Order(string customer)
  {
    this.Customer = customer;
  }
}
//…
// Client code, which uses Order class.
private static int NumberOfOrdersFor(List<Order> orders, string customer)
{
  int result = 0;

  if (orders != null)
  {
    foreach (Order order in orders)
    {
      if (string.Equals(order.Customer, customer))
      {
        result++;
      }
    }
  }

  return result;
}
```

###

```
public class Order
{
  // ...
  private Customer customer;

  public string CustomerName
  {
    get { return customer.Name; }
    set { customer.Name = value; }
  }

  public Order(string customerName)
  {
    this.customer = new Customer(customerName);
  }
}

public class Customer
{
  public string Name
  {
    get;
    set;
  }

  public Customer(string name)
  {
    this.Name = name;
  }
}
//…
// Client code, which uses Order class.
private static int NumberOfOrdersFor(List<Order> orders, string customer)
{
  int result = 0;

  if (orders != null)
  {
    foreach (Order order in orders)
    {
      if (string.Equals(order.CustomerName, customer))
      {
        result++;
      }
    }
  }

  return result;
}
```

###

Set step 1

#|ru| Давайте рассмотрим рефакторинг <i>Замена простого поля объектом</i> на примере класса заказа.
#|en| Let's look at the <i>Replace Data Value with Object</i> refactoring, using an order class as an example.
#|uk| Давайте розглянемо рефакторинг <i>Заміна простого поля об'єктом<i> на прикладі класу замовлення.

Select "private string |||customer|||"

#|ru| В данном примере покупатель в классе заказа хранится в виде строки. Однако мы могли бы создать для покупателей свой класс и перенести в него все данные и операции, связанные с покупателями.
#|en| In this example, the customer in the order class is stored as a string. Alternatively, we could create a <code>Customer</code> class and move the other customer data and behaviors to this class.
#|uk| В даному прикладі покупець в класі замовлення зберігається у вигляді рядка. Однак ми могли б створити для покупців свій клас і перенести в нього всі дані та операції, пов'язані з покупцями.

Go to after "Order"

Print:
```


public class Customer
{
}
```

Select name of "Customer"

#|ru| Итак, класс готов. Давайте перенесём в него поле имени покупателя, т.к. оно используется в остальном коде заказа.
#|en| The class is now ready. Let's move the customer name field to it, since the field is used in the rest of the order code.
#|uk| Отже, клас готовий. Давайте перенесемо в нього поле імені покупця, т.к. воно використовується в іншому коді замовлення. И поскольку к данному полю потребуется доступ извне, то мы сразу преобразуем его в публичное свойство.

Go to end of "Customer"

Print:
```

  public string Name
  {
    get;
    set;
  }
```

Set step 2

Go to end of "Customer"

#|ru| Здесь же создадим конструктор, принимающий начальное значение имени.
#|en| We should also create a constructor that accepts the initial value of the name.
#|uk| Тут же створимо конструктор, який приймає початкове значення імені.

Print:
```


  public Customer(string name)
  {
    this.Name = name;
  }
```

Set step 3

Select "private string customer"

#|ru| После этого можно изменить тип поля <code>customer</code>. А также изменить связанные с ним методы и свойства, чтобы они теперь работали с экземпляром класса <code>Customer</code>.
#|en| Then we change the type of the <code>customer</code> field, as well as change the related methods and properties so that they now work with the instance of the <code>Customer</code> class.
#|uk| Після цього можна змінити тип поля <code>сustomer</code>, а також змінити пов'язані з ним методи таким чином, щоб вони тепер працювали з екземпляром класу <code>Customer</code>.

Select "private |||string||| customer"

#|ru| Начнём с изменения типа поля покупателя.
#|en| Let's start with changing the type of the customer field.
#|uk| Почнемо з зміни типу поля покупця.

Replace "Customer"

Set step 4

Select "public string Customer"

#|ru| Затем нам надо решить, что делать с публичным свойством. Тут есть 2 пути:<br>1. Поменять тип свойства на <code>Customer</code>, чтобы оно позволяло напрямую работать с экземпляром класса. Тем самым мы предоставим клиентскому коду полный доступ к экземпляру покупателя.<br>2. Оставить тип свойства неизменным. Тем самым мы ограничим смысловой контекст его использования (в данном случае ограничим только работой с именем покупателя).
#|en| Then we should decide what to do with the public property. There are two paths here:<br>1. Change the property type to <code>Customer</code> so that it allows working with the class instance directly. Thus we give the client code full access to the customer instance.<br>2. Leave the property type unchanged. This limits the context of its use (in this case, to working with the customer name).
#|uk| Потім нам треба вирішити, що робити з публічною властивістю. Тут є 2 шляхи: <br> 1. Поміняти тип властивості на <code>Customer</code>, щоб воно дозволяло безпосередньо працювати з екземпляром класу. Тим самим ми надамо клієнтському коду повний доступ до примірника покупця. <br> 2. Залишити тип властивості незмінним. Тим самим ми обмежимо смисловий контекст його використання (в даному випадку обмежимо тільки роботою з ім'ям покупця).

#|ru| В рассматриваемом примере мы пойдем по второму пути, оставив свойству только работу с именем покупателя. Для этого преобразуем его геттер и сеттер.
#|en| In the example considered here, we will take the second path. The property will work only with the customer name. So we will convert its getter and setter.
#|uk| У розглянутому прикладі ми підемо по другому шляху, залишивши властивості тільки роботу з ім'ям покупця. Для цього перетворимо його геттер і сетер.

Select "return |||customer|||;"
+Select "|||customer||| = value;"

Replace "customer.Name"

Wait 500ms

Select name of "public Order"

#|ru| Теперь изменим конструктор таким образом, чтобы он заполнял поле покупателя новым экземпляром класса <code>Customer</code>.
#|en| Now change the constructor so that it fills the customer field with a new instance of the <code>Customer</code> class.
#|uk| Тепер змінимо конструктор таким чином, щоб він заповнював поле покупця новим екземпляром класу <code>Customer</code>.

Select "this.|||Customer|||"

Replace "customer"

Select "= |||customer|||" in "public Order"

Replace "new Customer(customer)"

Select "new Customer(customer)"

#|ru| Обратите внимание на то, что конструктор каждый раз создаёт новый экземпляр класса покупателя, причем других установщиков значения поля у нас нет. Это значит, что покупатель является объектом-значением и в каждом заказе находится собственный экземпляр <code>Customer</code>. Другими словами, одному и тому же покупателю в разных заказах будут соответствовать разные экземпляры класса <code>Customer</code>.
#|en| Note that the constructor creates a new instance of the customer class each time; we do not have any other setters for the field. This means that the customer is a value and each order has its own instance of <code>Customer</code>. In other words, the same customer will correspond to different instances of the <code>Customer</code> class in different orders.
#|uk| Зверніть увагу на те, що конструктор щоразу створює новий екземпляр класу покупця, причому інших установників значення поля у нас немає. Це означає, що покупець є об'єктом-значенням і в кожному замовленні знаходиться власний примірник <code>Customer</code>. Іншими словами, одному і тому ж покупцеві в різних замовленнях будуть відповідати різні екземпляри класу <code>Customer</code>.

#|ru| Как правило, объекты-значения должны быть неизменяемыми, благодаря чему удаётся избежать некоторых неприятных ошибок, связанных с тем, что объекты всегда передаются по ссылкам. Кстати, позднее нам потребуется, чтобы <code>string Customer</code> стал объектом-ссылкой, но для этого нужно будет применить ещё один рефакторинг.
#|en| Generally, values should be immutable, which allows avoiding unpleasant errors related to the fact that objects are always passed via references. Incidentally, later we will need for the <code>Customer</code> string to become a reference object but for this we will need to use an additional refactoring technique.
#|uk| Як правило, об'єкти-значення повинні бути незмінними, завдяки чому вдається уникнути деяких неприємних помилок, пов'язаних з тим, що об'єкти завжди передаються за посиланнями. До речі, пізніше нам буде потрібно, щоб <code>string Customer</code> став об'єктом-посиланням, але для цього потрібно буде застосувати ще один рефакторинг.

#C|ru| В данный момент можно выполнить компиляцию и тестирование.
#S Всё отлично, код работает корректно.

#C|en| Anyway, let's compile and test to make sure there are no errors.
#S Everything is OK! Code works correctly.

#C|uk| В даний момент можна виконати компіляцію і тестування.
#S Все добре, код працює коректно.

Select name of "Order"

#|ru| Осталось разобраться с именованием тех областей класса <code>Order</code>, в которых производилась правка кода для работы с новым типом данных.
#|en| At this point we work out how to name the areas of the <code>Order</code> class in which the code was adjusted to work with the new data type.
#|uk| Залишилося розібратися з ім'ям тих областей класу <code>Order</code>, в яких проводилася правка коду для роботи з новим типом даних.

Select "private Customer |||customer|||"

#|ru| Поле <code>customer</code> представляет собой экземпляр класса <code>Customer</code>, поэтому переименовывать его не нужно.
#|en| The <code>customer</code> field is an instance of the <code>Customer</code> class, therefore renaming it is not necessary.
#|uk| Поле <code>сustomer</code> представляє собою екземпляр класу <code>Customer</code>, тому перейменовувати його не потрібно.

Select "public string |||Customer|||"

#|ru| А вот свойство <code>Customer</code> позволяет теперь работать только с именем покупателя, поэтому логичнее будет переименовать его в <code>CustomerName</code>.
#|en| Meanwhile the <code>Customer</code> property now allows working only with the customer name, so it would be logical to rename it to <code>CustomerName</code>.
#|uk| А ось властивість <code>Customer</code> дозволяє тепер працювати тільки з ім'ям покупця, тому логічніше буде перейменувати його в <code>CustomerName</code>.

Replace "CustomerName"

Wait 500ms

Select "(order.|||Customer|||"

#|ru| При этом надо также произвести замену названия свойства и в остальном коде (в основных средах разработки данный процесс автоматизирован).
#|en| You should also replace the name of the property in the remaining code (in most development environments, this is automatic).
#|uk| При цьому треба також провести заміну назви властивості і в решті коді (в основних середовищах розробки даний процес автоматизований).

Replace "CustomerName"

Wait 500ms

Select "string |||customer|||" in parameters of "public Order"
+ Select "(|||customer|||)" in "public Order"

#|ru| И в завершение, заменим название параметра в конструкторе, чтобы было понятно, что в него передается имя покупателя.
#|en| To finish off the process, replace the parameter name in the constructor to make clear that the customer name is passed to it.
#|uk| І на завершення, замінимо назву параметру в конструкторі, щоб було зрозуміло, що в нього передається ім'я покупця.

Replace "customerName"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|en| Let's perform the final compilation and testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Select "private Customer |||customer|||"

#|ru| Прежде, чем закончить, хотелось бы обратить ваше внимание на то, что здесь, как и во многих других случаях, надо сделать ещё одну вещь. Вам может потребоваться добавить к клиенту оценку кредитоспособности, адрес и т.п. Пока что это сделать нельзя, так как <code>Customer</code> задействован как объект-значение. То есть в каждом заказе находится собственный экземпляр класса <code>Customer</code>.
#|en| Before we finish, note that here and in many other cases, one more step is necessary. You may need to add a credit score, address, etc. to the <code>Customer</code>. You cannot do this yet, since <code>Customer</code> is used as a value object. That is, each order has its own instance of the <code>Customer</code> class.
#|uk| Перш, ніж закінчити, хотілося б звернути вашу увагу на те, що тут, як і в багатьох інших випадках, треба зробити ще одну річ. Вам може знадобитися додати до клієнта оцінку кредитоспроможності, адресу і т.п. Поки що це зробити не можливо, так як <code>Customer</code> задіяний як об'єкт-значення. Тобто в кожному замовленні знаходиться власний примірник класу <code>Customer</code>.

#|ru| Чтобы создать в классе <code>Customer</code> требуемые атрибуты, необходимо применить к нему рефакторинг <a href="/ru/change-value-to-reference">замена значения ссылкой</a>. После этого все заказы для одного и того же покупателя будут ссылаться на один и тот же экземпляр класса <code>Customer</code>.
#|en| To create the necessary attributes in the <code>Customer</code> class, use the <a href="/change-value-to-reference">Change Value to Reference</a> refactoring technique on it. After that refactoring, all orders for the same customer will refer to the same instance of the <code>Customer</code> class.
#|uk| Щоб створити в класі <code>Customer</code> необхідні атрибути, треба застосувати до нього рефакторинг <a href="/uk/change-value-to-reference">заміна значення посиланням</a>. Після цього всі замовлення для одного і того ж покупця будуть посилатися на той самий  примірник класу <code>Customer</code>.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.