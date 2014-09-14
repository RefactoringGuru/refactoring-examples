change-reference-to-value:csharp

###

1. Обеспечьте неизменяемость объекта. Объект не должен иметь публичных сеттеров или других методов, меняющих его состояние и данные (в этом может помочь <a href="/remove-setting-method">удаление сеттера</a>). Единственное место, где полям объекта-значения присваиваются какие-то данные, должен быть конструктор.

2. Создайте метод сравнения для сравнения двух объектов-значений.

3. Проверьте, возможно ли удалить фабричный метод и сделать конструктор объекта публичным.



###

```
public class Customer
{
  private static Hashtable instances = new Hashtable();

  public string Name
  {
    get;
    private set;
  }
  public DateTime BirthDate
  {
    get;
    set;
  }

  private Customer(string Name)
  {
    this.Name = Name;
  }

  public static Customer Get(string name)
  {
    Customer result = (Customer)instances[name];

    if (result == null)
    {
      result = new Customer(name);
      instances.Add(name, result);
    }

    return result;
  }
}

// Somewhere in client code
Customer john = Customer.Get("John Smith");
john.BirthDate = new DateTime(1985, 1, 1);
```

###

```
public class Customer
{
  public string Name
  {
    get;
    private set;
  }
  public DateTime BirthDate
  {
    get;
    private set;
  }

  public Customer(string Name, DateTime BirthDate)
  {
    this.Name = Name;
    this.BirthDate = BirthDate;
  }

  public override bool Equals(Object obj)
  {
    Customer other = obj as Customer;

    if (other == null)
      return false;

    return this.BirthDate == other.BirthDate && string.Equals(this.Name, other.Name, StringComparison.Ordinal);
  }
  public override int GetHashCode()
  {
    int hashCode = 11;
    unchecked
    {
      if (Name != null)
        hashCode = hashCode * 22 + Name.GetHashCode();
      hashCode = hashCode * 22 + BirthDate.GetHashCode();
    }
    return hashCode;
  }
}

// Somewhere in client code
Customer john = new Customer("John Smith", new DateTime(1985, 1, 1));
```

###

Set step 1

# Давайте рассмотрим <i>Замену ссылки значением</i> на примере класса покупателя.

Select "public string |||Name|||"
+ Select "public DateTime |||BirthDate|||"

# Этот класс содержит имя и день рождения покупателя. Такой класс порождает объекты-ссылки, другими словами для одного реального покупателя создаётся только один экземпляр класса <code>Customer</code>.

Select "Customer.Get("John Smith")"

# Таким образом, для получения экземпляра может использоваться следующий код.

Select visibility of "private Customer"

# Класс <code>Customer</code> ведёт реестр своих экземпляров. Мы не можем просто обратиться к конструктору (потому он и является закрытым).

Select name of "Get"

# Вместо этого мы вызываем статический фабричный метод, который ищет покупателя среди уже созданных объектов. И только в случае, если такой объект ещё не создан, фабричный метод запускает реальный конструктор, а затем добавляет созданный объект в реестр.

# Теперь, допустим, у вас есть несколько заказов, ссылающихся на одного и того же клиента. Внезапно код одного из заказов меняет значение даты рождения клиента. Так как оба заказа ссылаются на один тот же объект клиента, новая дата рождения будет доступна также из другого заказа.

# Было бы такое возможно, если б каждый заказ имел разные экземпляры класса <code>Customer</code>? Пожалуй, нет. Вот почему главным требованием этого рефакторинга является приведение класса к неизменяемому виду. В некоторых случаях это вообще невозможно и от рефакторинга нужно отказаться.

Select:
```
get;
    |||set;|||
```

#^ Следуя этой логике, необходимо убрать сеттер поля даты рождения из публичного доступа и инициализировать значение этого поля в конструкторе. Применим рефакторинг <a href="/remove-setting-method">удаление сеттера</a>.

Go to "    |||set;"

Print "private "

Wait 500ms

Go to the end of parameters of "private Customer"

Print ", DateTime BirthDate"

Go to the end of "private Customer"

Print:
```

    this.BirthDate = BirthDate;
```

Select:
```

john.BirthDate = new DateTime(1985, 1, 1);
```

# Так как публичного сеттера в классе теперь нет, нам нужно удалить его использование в клиентском коде. Действие этого сеттера пока нечем компенсировать, но не волнуйтесь, мы рассмотрим это чуточку позже.

Remove selected

Set step 2

Go to before "Customer Get"

# Есть ещё одна проблема. Объекты-значения с одинаковыми данными должны быть равны при сравнении. Чтобы сделать это на языке C#, нужно переопределить в сравниваемых классах методы <code>Equals</code> и <code>GetHashCode</code>.

# Вот так это будет выглядеть в нашем случае.

Print:
```

  public override bool Equals(Object obj)
  {
    Customer other = obj as Customer;

    if (other == null)
      return false;

    return this.BirthDate == other.BirthDate && string.Equals(this.Name, other.Name, StringComparison.Ordinal);
  }
  public override int GetHashCode()
  {
    int hashCode = 11;
    unchecked
    {
      if (Name != null)
        hashCode = hashCode * 22 + Name.GetHashCode();
      hashCode = hashCode * 22 + BirthDate.GetHashCode();
    }
    return hashCode;
  }
```

Select "public override bool |||Equals|||"

# Теперь сравнение вида <code>new Customer("John").Equals(new Customer("John"))</code> будет возвращать <code>TRUE</code>.

Set step 3

Select:
```
  private static Hashtable instances = new Hashtable();


```
+ Select whole "Get"

# Остался последний штрих. Так как у нас теперь нет нужды хранить реестр созданных объектов, фабричный метод можно удалить, а конструктор сделать публичным.

Remove selected

Wait 500ms

Select "|||private||| Customer"

Replace "public"

Wait 500ms

Select "Customer.Get("John Smith")"

# После всех этих изменений, клиентский код тоже изменится.

Print "new Customer("John Smith", new DateTime(1985, 1, 1))"

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.