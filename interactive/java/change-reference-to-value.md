change-reference-to-value:java

###

1. Обеспечьте неизменяемость объекта. Объект не должен иметь сеттеров или других методов, меняющих его состояние и данные (в этом может помочь <a href="/remove-setting-method">удаление сеттера</a>). Единственное место, где полям объекта-значения присваиваются какие-то данные, должен быть конструктор.

2. Создайте метод сравнения для сравнения двух объектов-значений.

3. Проверьте, возможно ли удалить фабричный метод и сделать конструктор объекта публичным.



###

```
class Customer {
  private final String name;
  private Date birthDate;

  public String getName() {
    return name;
  }
  public String getBirthDate() {
    return birthday;
  }
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }
  private Customer(String name) {
    this.name = name;
  }

  private static Dictionary instances = new Hashtable();

  public static Customer get(String name) {
    Customer value = instances.get(name);
    if (value == null) {
      value = new Currency(name);
      instances.put(name, value);
    }
    return value;
  }
}

// Somewhere in client code
Customer john = Customer.get("John Smith");
john.setBirthDate(new Date(1985, 1, 1));
```

###

```
class Customer {
  private final String name;
  private Date birthDate;

  public boolean equals(Object arg) {
    if (!(arg instanceof Customer)) {
      return false;
    }
    Customer other = (Customer) arg;
    return (name.equals(other.name));
  }
  public int hashCode() {
    return name.hashCode();
  }
  public String getName() {
    return name;
  }
  public String getBirthDate() {
    return birthday;
  }
  public Customer(String name, Date birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }
}

// Somewhere in client code
Customer john = new Customer("John Smith", new Date(1985, 1, 1));
```

###

Set step 1

# Давайте рассмотрим <i>Замену ссылки значением</i> на примере класса покупателя.

Select "private final String |||name|||"
+ Select "private Date |||birthDate|||"

# Этот класс содержит имя и день рождения покупателя. Такой класс порождает объекты-ссылки, другими словами для одного реального покупателя создаётся только один экземпляр класса <code>Customer</code>.

Select "Customer.get("John Smith")"

# Таким образом, для получения экземпляра может использоваться следующий код.

Select visibility of "private Customer"

# Класс <code>Customer</code> ведёт реестр своих экземпляров. Мы не можем просто обратиться к конструктору (потому он и является закрытым).

Select name of "get"

# Вместо этого мы вызываем статический фабричный метод, который ищет покупателя среди уже созданных объектов. И только в случае, если такой объект ещё не создан, фабричный метод запускает реальный конструктор, а затем добавляет созданный объект в реестр.

# Теперь, допустим, у вас есть несколько заказов, ссылающихся на одного и того же клиента. Внезапно код одного из заказов меняет значение даты рождения клиента. Так как оба заказа ссылаются на один тот же объект клиента, новая дата рождения будет доступна также из другого заказа.

# Было бы такое возможно, если б каждый заказ имел разные экземпляры класса <code>Customer</code>? Пожалуй, нет. Вот почему главным требованием этого рефакторинга является приведение класса к неизменяемому виду. В некоторых случаях это вообще невозможно и от рефакторинга нужно отказаться.

Select whole "setBirthDate"

# Следуя этой логике, необходимо убрать сеттер поля даты рождения и инициализировать значение этого поля в конструкторе. Применим рефакторинг <a href="/remove-setting-method">удаление сеттера</a>.

Remove selected

Go to the end of parameters of "private Customer"

Print ", Date birthDate"

Go to the end of "private Customer"

Print:
```

    this.birthDate = birthDate;
```

Select:
```

john.setBirthDate(new Date(1985, 1, 1));
```

# Так как сеттера в классе теперь нет, нам нужно удалить его использование в клиентском коде. Действие этого сеттера пока нечем компенсировать, но не волнуйтесь, мы рассмотрим это чуточку позже.

Remove selected


Set step 2

Go to before "getname"

# Есть ещё одна проблема. Объекты-значения с одинаковыми данными должны быть равны при сравнении. Чтобы сделать это на языке Java, нужно определить в сравниваемых классах специальные методы <code>equals</code> и <code>hash</code>.

# Вот так это будет выглядеть в нашем случае.

Print:
```

  public boolean equals(Object arg) {
    if (!(arg instanceof Customer)) {
      return false;
    }
    Customer other = (Customer) arg;
    return (name.equals(other.name));
  }
  public int hashCode() {
    return name.hashCode();
  }
```

# Теперь сравнение вида <code>new Customer("John").equals(new Customer("John"))</code> будет возвращать <code>TRUE</code>.

Set step 3

Select:
```

  private static Dictionary instances = new Hashtable();


```
+ Select whole "get"

# Остался последний штрих. Так как у нас теперь нет нужды хранить реестр созданных объектов, фабричный метод можно удалить, а конструктор сделать публичным.

Remove selected

Select "|||private||| Customer"

Replace "public"

Select "Customer.get("John Smith")"

# После всех этих изменений, клиентский код тоже изменится.

Print "new Customer("John Smith", new Date(1985, 1, 1))"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.