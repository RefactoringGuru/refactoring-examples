change-value-to-reference:java

###

1.ru. Используйте <a href="/replace-constructor-with-factory-method">замену конструктора фабричным методом</a> над классом, который должен порождать объекты-ссылки.
1.uk. Використайте <a href="/replace-constructor-with-factory-method">заміну конструктора фабричним методом</a> над класом, який повинен породжувати об'єкти-посилання.

2.ru. Определите, какой объект будет ответственным за предоставление доступа к объектам-ссылкам. Вместо создания нового объекта, когда он нужен, вам теперь нужно получать его из какого-то объекта-хранилища или статического поля-словаря.
2.uk. Визначте, який об'єкт буде відповідальним за надання доступу до об'єктів-посилань. Замість створення нового об'єкту, коли він потрібний, вам тепер треба отримувати його з якогось об'єкту-сховища або статичного поля-словника.

3.ru. Определите, будут ли объекты-ссылки создаваться заранее или динамически по мере надобности. Если объекты создаются предварительно, необходимо обеспечить их загрузку перед использованием.
3.uk. Визначте, чи будуть об'єкти-посилання створюватись заздалегідь або динамічно у міру потреби. Якщо об'єкти створюються заздалегідь, необхідно забезпечити їх завантаження перед використанням.

4.ru. Измените фабричный метод так, чтобы он возвращал объект-ссылку. Если объекты создаются заранее, необходимо решить, как обрабатывать ошибки при запросе несуществующего объекта.
4.uk. Змініть фабричний метод так, щоб він повертав об'єкт-посилання. Якщо об'єкти створюються заздалегідь, необхідно вирішити, як обробляти помилки при запиті неіснуючого об'єкту.



###

```
class Customer {
  private final String name;
  public Customer(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}

class Order {
  //...
  private Customer customer;
  public String getCustomerName() {
      return customer.getName();
  }
  public void setCustomer(String customerName) {
    customer = new Customer(customerName);
  }
  public Order(String customerName) {
    customer = new Customer(customerName);
  }
}

// Some client code, which uses Order class.
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

```
class Customer {
  private static Dictionary instances = new Hashtable();

  // This code should be executed at the program launch.
  static void loadCustomers() {
    new Customer("Lemon Car Hire").store();
    new Customer("Associated Coffee Machines").store();
    new Customer("Bilston Gasworks").store();
  }
  private void store() {
    instances.put(this.getName(), this);
  }

  private final String name;
  public static Customer getNamed(String name) {
    return (Customer) instances.get(name);
  }
  private Customer(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}

class Order {
  //...
  private Customer customer;
  public String getCustomerName() {
      return customer.getName();
  }
  public void setCustomer(String customerName) {
    customer = Customer.getNamed(customerName);
  }
  public Order(String customerName) {
    customer = Customer.getNamed(customerName);
  }
}

// Some client code, which uses Order class.
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

#|ru| Давайте рассмотрим <i>Замену значения ссылкой</i> на примере класса заказа и покупателя. Мы продолжим с того места, где закончили пример <a href="/replace-data-value-with-object">Замена простого поля объектом</a>.
#|uk| Давайте розглянемо <i>Заміну значення посиланням<i> на прикладі класу замовлення та покупця. Ми продовжимо з того місця, де закінчили приклад <a href="/replace-data-value-with-object">Заміна простого поля об'єктом</a>.

Select name of "Customer"

#|ru|+ В данном случае имеется класс покупателя...
#|uk|+ В даному випадку мається клас покупця...

Select name of "Order"

#|ru|+ ...который используется в классе заказов...
#|uk|+ ...який використовується в класі замовлень...

Select name of "numberOfOrdersFor"

#|ru|= ...и некоторый клиентский код, который использует оба класса.
#|uk|= ...і деякий клієнтський код, який використовує обидва класи.

Select name of "Customer"

#|ru| В данный момент покупатель в классе заказа используется как объект-значение. Т.е. каждый заказ имеет собственный экземпляр <code>Сustomer</code>, даже если это один и тот же реальный покупатель. Надо изменить код так, чтобы при наличии нескольких заказов для одного покупателя в них использовался один и тот же экземпляр класса <code>Сustomer</code>.
#|uk| В даний момент покупець в класі замовлення використовується як об'єкт-значення. Тобто кожне замовлення має власний примірник <code>Сustomer</code>, навіть якщо це один і той же реальний покупець. Треба змінити код так, щоб при наявності декількох замовлень для одного покупця в них використовувався один і той же примірник класу <code>Сustomer</code>.

#|ru| В нашем случае это означает, что для каждого имени покупателя должен существовать только один экземпляр класса покупателя.
#|uk| У нашому випадку це означає, що для кожного імені покупця повинен існувати тільки один екземпляр класу покупця.

#|ru| Начнём с <a href="/replace-constructor-with-factory-method">замены конструктора фабричным методом</a>. Это позволит нам контролировать процесс создания объектов покупателей, что является крайне важным моментом. Итак, создадим фабричный метод в классе покупателя.
#|uk| Почнемо з <a href="/replace-constructor-with-factory-method">заміни конструктора фабричним методом</a>. Це дозволить нам контролювати процес створення об'єктів покупців, що є вкрай важливим моментом. Отже, створимо фабричний метод в класі покупця.

Go to before "public Customer"
Print:
```

  public static Customer create(String name) {
    return new Customer(name);
  }
```

Select "new Customer(customerName)"

#|ru| Затем заменим вызов конструктора класса <code>Customer</code> обращением к фабричному методу.
#|uk| Потім замінимо виклик конструктора класу <code>Customer</code> зверненням до фабричного методу.

Replace "Customer.create(customerName)"

Select visibility of "public Customer"

#|ru| После чего можно сделать конструктор покупателя закрытым.
#|uk| Після чого можна зробити конструктор покупця закритим.

Print "private"

Set step 2

Select name of "Customer"

#|ru| Теперь надо решить, какой объект будет ответственным за предоставление доступа к экземплярам класса покупателей. В общем случае для этого хорошо бы иметь какой-то объект-реестр, который будет содержать пул всех объектов-ссылок и получать нужные экземпляры из него. Например, если нужно сделать несколько товарных позиций в заказе, каждая позиция может храниться внутри объекта заказа.
#|uk| Тепер треба вирішити, який об'єкт буде відповідальним за надання доступу до екземплярів класу покупців. У загальному випадку для цього добре б мати якийсь об'єкт-реєстр, який буде містити пул всіх об'єктів-посилань, і отримувати потрібні екземпляри з нього. Наприклад, якщо потрібно зробити кілька товарних позицій у замовленні, кожна позиція може зберігатися всередині об'єкта замовлення.

#|ru| Однако в данной ситуации для покупателей такого объекта нет. Чтобы не создавать новый класс для хранения реестра покупателей, можно организовать хранение с помощью статического поля в классе <code>Сustomer</code>.
#|uk| Однак у даній ситуації для покупців такого об'єкта немає. Щоб не створювати новий клас для зберігання реєстру покупців, можна організувати зберігання за допомогою статичного поля в класі <code>Сustomer</code>.

Go to the start of "Customer"

Print:
```

  private static Dictionary instances = new Hashtable();

```

Set step 3

#|ru| Затем надо решить, как создавать покупателей – заранее или динамически (по мере надобности). Воспользуемся первым способом. При запуске приложения мы будем загружать тех клиентов, которые находятся в работе. Их можно взять, например, из базы данных или из файла.
#|uk| Потім треба вирішити, як створювати покупців – заздалегідь або динамічно (за потребою). Скористаємося першим способом. При запуску додатка ми будемо завантажувати тих клієнтів, які знаходяться в роботі. Їх можна взяти, наприклад, з бази даних або з файлу.

#|ru| В целях простоты используем для загрузки покупателей явный код. Впоследствии всегда можно будет изменить его с помощью <a href="/substitute-algorithm">замещения алгоритма</a>.
#|uk| З метою простоти використовуємо для завантаження покупців явний код. Згодом завжди можна буде змінити його за допомогою <a href="/substitute-algorithm">заміщення алгоритму</a>.

Print:
```

  // This code should be executed at the program launch.
  static void loadCustomers() {
    new Customer("Lemon Car Hire").store();
    new Customer("Associated Coffee Machines").store();
    new Customer("Bilston Gasworks").store();
  }
  private void store() {
    instances.put(this.getName(), this);
  }

```

Set step 4

Select name of "create"

#|ru| Теперь модифицируем фабричный метод класса <code>Customer</code> так, чтобы он возвращал заранее созданного покупателя.
#|uk| Тепер модифікуємо фабричний метод класу <code>Customer</code> так, щоб він повертав заздалегідь створеного покупця.

Select "new Customer(name)" in "create"

Replace "(Customer) instances.get(name)"

Select name of "create"

#|ru| И поскольку метод <code>Create()</code> теперь всегда возвращает уже существующего покупателя, надо это пояснить с помощью <a href="/rename-method">переименования метода</a>.
#|uk| І оскільки метод <code>Create()</code> тепер завжди повертає вже існуючого покупця, треба це пояснити за допомогою <a href="/rename-method">перейменування методу</a>.

Print "getNamed"

Wait 500ms

Select "Customer.|||create|||"

Replace "getNamed"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.