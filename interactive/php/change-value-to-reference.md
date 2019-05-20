change-value-to-reference:php

###

1.ru. Используйте <a href="/replace-constructor-with-factory-method">замену конструктора фабричным методом</a> над классом, который должен порождать объекты-ссылки.
1.en. Use <a href="/replace-constructor-with-factory-method">Replace Constructor with Factory Method</a> on the class from which the references are to be generated.
1.uk. Використайте <a href="/replace-constructor-with-factory-method">заміну конструктора фабричним методом</a> над класом, який повинен породжувати об'єкти-посилання.

2.ru. Определите, какой объект будет ответственным за предоставление доступа к объектам-ссылкам. Вместо создания нового объекта, когда он нужен, вам теперь нужно получать его из какого-то объекта-хранилища или статического поля-словаря.
2.en. Determine which object will be responsible for providing access to references. Instead of creating a new object, when you need one you now need to get it from a storage object or static dictionary field.
2.uk. Визначте, який об'єкт буде відповідальним за надання доступу до об'єктів-посилань. Замість створення нового об'єкту, коли він потрібний, вам тепер треба отримувати його з якогось об'єкту-сховища або статичного поля-словника.

3.ru. Определите, будут ли объекты-ссылки создаваться заранее или динамически по мере надобности. Если объекты создаются предварительно, необходимо обеспечить их загрузку перед использованием.
3.en. Determine whether references will be created in advance or dynamically as necessary. If objects are created in advance, make sure to load them before use.
3.uk. Визначте, чи будуть об'єкти-посилання створюватись заздалегідь або динамічно у міру потреби. Якщо об'єкти створюються заздалегідь, необхідно забезпечити їх завантаження перед використанням.

4.ru. Измените фабричный метод так, чтобы он возвращал объект-ссылку. Если объекты создаются заранее, необходимо решить, как обрабатывать ошибки при запросе несуществующего объекта.
4.en. Change the factory method so that it returns a reference. If objects are created in advance, decide how to handle errors when a non-existent object is requested.
4.uk. Змініть фабричний метод так, щоб він повертав об'єкт-посилання. Якщо об'єкти створюються заздалегідь, необхідно вирішити, як обробляти помилки при запиті неіснуючого об'єкту.



###

```
class Customer {
  private $name;
  public function __construct($name) {
    $this->name = $name;
  }
  public function getName() {
    return $this->name;
  }
}

class Order {
  //…
  private $customer; // Customer
  public function getCustomerName() {
      return $this->customer->getName();
  }
  public function setCustomer($customerName) {
    $this->customer = new Customer($customerName);
  }
  public function __construct($customerName) {
    $this->customer = new Customer($customerName);
  }
}

// Some client code, which uses Order class.
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

```
class Customer {
  private static $instances = array();

  // This code should be executed at the program launch.
  public static function loadCustomers() {
    Customer::instances["Lemon Car Hire"] = new Customer("Lemon Car Hire");
    Customer::instances["Associated Coffee Machines"] = new Customer("Associated Coffee Machines");
    Customer::instances["Bilston Gasworks"] = new Customer("Bilston Gasworks");
  }

  private $name;
  public static function getNamed($name) {
    return Customer::instances[$name];
  }
  private function __construct($name) {
    $this->name = $name;
  }
  public function getName() {
    return $this->name;
  }
}

class Order {
  //…
  private $customer; // Customer
  public function getCustomerName() {
      return $this->customer->getName();
  }
  public function setCustomer($customerName) {
    $this->customer = Customer::getNamed($customerName);
  }
  public function __construct($customerName) {
    $this->customer = Customer::getNamed($customerName);
  }
}

// Some client code, which uses Order class.
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

#|ru| Давайте рассмотрим <i>Замену значения ссылкой</i> на примере класса заказа и покупателя. Мы продолжим с того места, где закончили пример <a href="/replace-data-value-with-object">Замена простого поля объектом</a>.
#|en| Let's look at <i>Replace Data Value with Object</i> using the customer/order class example. We will pick up where we finished the <i>Replace Data Value with Object</i> refactoring.
#|uk| Давайте розглянемо <i>Заміну значення посиланням</i> на прикладі класу замовлення та покупця. Ми продовжимо з того місця, де закінчили приклад <a href="/uk/replace-data-value-with-object">Заміна простого поля об'єктом</a>.

Select name of "Customer"

#|ru|+ В данном случае имеется класс покупателя…
#|en|+ Here we have a customer class…
#|uk|+ В даному випадку мається клас покупця…

Select name of "Order"

#|ru|+ …который используется в классе заказов…
#|en|+ …that is used in the order class…
#|uk|+ …який використовується в класі замовлень…

Select name of "numberOfOrdersFor"

#|ru|= …и некоторый клиентский код, который использует оба класса.
#|en|= …and client code that is used by both classes.
#|uk|= …і деякий клієнтський код, який використовує обидва класи.

Select name of "Customer"

#|ru| В данный момент покупатель в классе заказа используется как объект-значение. Т.е. каждый заказ имеет собственный экземпляр <code>Сustomer</code>, даже если это один и тот же реальный покупатель. Надо изменить код так, чтобы при наличии нескольких заказов для одного покупателя в них использовался один и тот же экземпляр класса <code>Сustomer</code>.
#|en| Currently, the customer in the order class is used as a data value. In other words, each order has own instance of <code>Customer</code> even if the actual customer is the same. We want to change the code so that multiple orders for the same customer use the same instance of the <code>Customer</code> class.
#|uk| В даний момент покупець в класі замовлення використовується як об'єкт-значення. Тобто кожне замовлення має власний примірник <code>Сustomer</code>, навіть якщо це один і той же реальний покупець. Треба змінити код так, щоб при наявності декількох замовлень для одного покупця в них використовувався один і той же примірник класу <code>Сustomer</code>.

#|ru| В нашем случае это означает, что для каждого имени покупателя должен существовать только один экземпляр класса покупателя.
#|en| In our case, this means that for each customer name, there must exist one and only one instance of the customer class.
#|uk| У нашому випадку це означає, що для кожного імені покупця повинен існувати тільки один екземпляр класу покупця.

#|ru| Начнём с <a href="/replace-constructor-with-factory-method">замены конструктора фабричным методом</a>. Это позволит нам контролировать процесс создания объектов покупателей, что является крайне важным моментом. Итак, создадим фабричный метод в классе покупателя.
#|en| We start with <a href="/replace-constructor-with-factory-method">Replace Constructor with Factory Method</a>. This lets us keep an eye on the process of creation of customer objects, which is extremely important for what we are going to do. We create the factory method in the customer class.
#|uk| Почнемо з <a href="/uk/replace-constructor-with-factory-method">заміни конструктора фабричним методом</a>. Це дозволить нам контролювати процес створення об'єктів покупців, що є вкрай важливим моментом. Отже, створимо фабричний метод в класі покупця.

Go to before "__construct" in "Customer"
Print:
```

  public static function create($name) {
    return new Customer($name);
  }
```

Select "new Customer($customerName)"

#|ru| Затем заменим вызов конструктора класса <code>Customer</code> обращением к фабричному методу.
#|en| Then we replace the call to the <code>Customer</code> class constructor with a reference to the factory method.
#|uk| Потім замінимо виклик конструктора класу <code>Customer</code> зверненням до фабричного методу.

Replace "Customer::create($customerName)"

Select visibility of "__construct" in "Customer"

#|ru| После чего можно сделать конструктор покупателя закрытым.
#|en| We can now make the customer constructor private.
#|uk| Після чого можна зробити конструктор покупця закритим.

Print "private"

Set step 2

Select name of "Customer"

#|ru| Теперь надо решить, какой объект будет ответственным за предоставление доступа к экземплярам класса покупателей. В общем случае для этого хорошо бы иметь какой-то объект-реестр, который будет содержать пул всех объектов-ссылок и получать нужные экземпляры из него. Например, если нужно сделать несколько товарных позиций в заказе, каждая позиция может храниться внутри объекта заказа.
#|en| A decision must be made: Which object will be responsible for providing access to instances of the customer class? It would be good to have a registry object for this purpose, containing a pool of all reference objects and retrieving the necessary instances from it. For example, if you need to put several products in an order, each product can be stored inside the order object.
#|uk| Тепер треба вирішити, який об'єкт буде відповідальним за надання доступу до екземплярів класу покупців. У загальному випадку для цього добре б мати якийсь об'єкт-реєстр, який буде містити пул всіх об'єктів-посилань, і отримувати потрібні екземпляри з нього. Наприклад, якщо потрібно зробити кілька товарних позицій у замовленні, кожна позиція може зберігатися всередині об'єкта замовлення.

#|ru| Однако в данной ситуации для покупателей такого объекта нет. Чтобы не создавать новый класс для хранения реестра покупателей, можно организовать хранение с помощью статического поля в классе <code>Сustomer</code>.
#|en| But, in this case, no such object exists for customers. To not create a new class for storing a customer registry, you can set up storage by using a static field in the <code>Customer</code> class.
#|uk| Однак у даній ситуації для покупців такого об'єкта немає. Щоб не створювати новий клас для зберігання реєстру покупців, можна організувати зберігання за допомогою статичного поля в класі <code>Сustomer</code>.

Go to the start of "Customer"

Print:
```

  private static $instances = array();

```

Set step 3

#|ru| Затем надо решить, как создавать покупателей – заранее или динамически (по мере надобности). Воспользуемся первым способом. При запуске приложения мы будем загружать тех клиентов, которые находятся в работе. Их можно взять, например, из базы данных или из файла.
#|en| Then decide how to create customers: in advance or dynamically (as needed). We will use the first way. When launching the application, we will load the clients that are currently "in use". We can take this information from a database or file, for example.
#|uk| Потім треба вирішити, як створювати покупців – заздалегідь або динамічно (за потребою). Скористаємося першим способом. При запуску додатка ми будемо завантажувати тих клієнтів, які знаходяться в роботі. Їх можна взяти, наприклад, з бази даних або з файлу.

#|ru| В целях простоты используем для загрузки покупателей явный код. Впоследствии всегда можно будет изменить его с помощью <a href="/ru/substitute-algorithm">замещения алгоритма</a>.
#|en| For simplicity, we will use explicit code for loading customers. This makes it possible to change it by using <a href="/substitute-algorithm">Substitute Algorithm</a>.
#|uk| З метою простоти використовуємо для завантаження покупців явний код. Згодом завжди можна буде змінити його за допомогою <a href="/uk/substitute-algorithm">заміщення алгоритму</a>.

Print:
```

  // This code should be executed at the program launch.
  public static function loadCustomers() {
    Customer::instances["Lemon Car Hire"] = new Customer("Lemon Car Hire");
    Customer::instances["Associated Coffee Machines"] = new Customer("Associated Coffee Machines");
    Customer::instances["Bilston Gasworks"] = new Customer("Bilston Gasworks");
  }

```

Set step 4

Select name of "create"

#|ru| Теперь модифицируем фабричный метод класса <code>Customer</code> так, чтобы он возвращал заранее созданного покупателя.
#|en| Now we modify the factory method of the <code>Customer</code> class so that it returns the previously created customer.
#|uk| Тепер модифікуємо фабричний метод класу <code>Customer</code> так, щоб він повертав заздалегідь створеного покупця.

Select "new Customer($name)" in "create"

Replace "Customer::instances[$name]"

Select name of "create"

#|ru| И поскольку метод <code>create()</code> теперь всегда возвращает уже существующего покупателя, надо это пояснить с помощью <a href="/rename-method">переименования метода</a>.
#|en| And since the <code>create()</code> method now always returns an existing customer, this should be clarified with the help of <a href="/rename-method">Rename Method</a>.
#|uk| І оскільки метод <code>create()</code> тепер завжди повертає вже існуючого покупця, треба це пояснити за допомогою <a href="/uk/rename-method">перейменування методу</a>.

Print "getNamed"

Wait 500ms

Select "Customer::|||create|||"

Replace "getNamed"

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