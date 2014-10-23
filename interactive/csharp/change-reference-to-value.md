change-reference-to-value:csharp

###

1.ru. Обеспечьте неизменяемость объекта. Объект не должен иметь публичных сеттеров или других методов, меняющих его состояние и данные (в этом может помочь <a href="/remove-setting-method">удаление сеттера</a>). Единственное место, где полям объекта-значения присваиваются какие-то данные, должен быть конструктор.
1.uk. Забезпечте незмінність об'єкту. Об'єкт не повинен мати сетерів або інших методів, що міняють його стан і дані (у цьому може допомогти <a href="/remove-setting-method">видалення сетера</a>). Єдиним місцем, де полям об'єкту-значення привласнюються якісь дані, має бути конструктор.

2.ru. Создайте метод сравнения для сравнения двух объектов-значений.
2.uk. Створіть метод порівняння для порівняння двох об'єктів-значень.

3.ru. Проверьте, возможно ли удалить фабричный метод и сделать конструктор объекта публичным.
3.uk. Перевірте, чи можливо видалити фабричний метод і зробити конструктор об'єкту публічним.



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

#|ru| Давайте рассмотрим <i>Замену ссылки значением</i> на примере класса покупателя.
#|uk| Давайте розглянемо <i>Заміну посилання значенням<i> на прикладі класу покупця.

Select "public string |||Name|||"
+ Select "public DateTime |||BirthDate|||"

#|ru| Этот класс содержит имя и день рождения покупателя. Такой класс порождает объекты-ссылки, другими словами для одного реального покупателя создаётся только один экземпляр класса <code>Customer</code>.
#|uk| Цей клас містить ім'я і день народження покупця. Такий клас породжує об'єкти-посилання, іншими словами для одного реального покупця створюється тільки один екземпляр класу <code>Customer</code>.

Select "Customer.Get("John Smith")"

#|ru| Таким образом, для получения экземпляра может использоваться следующий код.
#|uk| Таким чином, для отримання примірника може використовуватися наступний код.

Select visibility of "private Customer"

#|ru| Класс <code>Customer</code> ведёт реестр своих экземпляров. Мы не можем просто обратиться к конструктору (потому он и является закрытым).
#|uk| Клас <code>Customer</code> веде реєстр своїх примірників. Ми не можемо просто звернутися до конструктора (тому він і є закритим).

Select name of "Get"

#|ru| Вместо этого мы вызываем статический фабричный метод, который ищет покупателя среди уже созданных объектов. И только в случае, если такой объект ещё не создан, фабричный метод запускает реальный конструктор, а затем добавляет созданный объект в реестр.
#|uk| Замість цього ми викликаємо статичний фабричний метод, який шукає покупця серед вже створених об'єктів. І тільки в разі, якщо такий об'єкт ще не створений, фабричний метод запускає реальний конструктор, а потім додає створений об'єкт в реєстр.

#|ru| Теперь, допустим, у вас есть несколько заказов, ссылающихся на одного и того же клиента. Внезапно код одного из заказов меняет значение даты рождения клиента. Так как оба заказа ссылаются на один тот же объект клиента, новая дата рождения будет доступна также из другого заказа.
#|uk| Тепер, припустимо, у вас є кілька замовлень, які посилаються на одного і того ж клієнта. Раптово код одного з замовлень міняє значення дати народження клієнта. Так як обидва замовлення посилаються на один той самий об'єкт клієнта, нова дата народження буде доступна також з іншого замовлення.

#|ru| Было бы такое возможно, если б каждый заказ имел разные экземпляры класса <code>Customer</code>? Пожалуй, нет. Вот почему главным требованием этого рефакторинга является приведение класса к неизменяемому виду. В некоторых случаях это вообще невозможно и от рефакторинга нужно отказаться.
#|uk| Було б таке можливим, якщо б кожне замовлення мало різні екземпляри класу <code>Customer</code>? Мабуть, ні. Ось чому головною вимогою цього рефакторінга є приведення класу до незмінного виляду. В деяких випадках це взагалі неможливо і від рефакторінга потрібно відмовитися.

Select:
```
get;
    |||set;|||
```

#|ru|^ Следуя этой логике, необходимо убрать сеттер поля даты рождения из публичного доступа и инициализировать значение этого поля в конструкторе. Применим рефакторинг <a href="/remove-setting-method">удаление сеттера</a>.
#|uk|^ Слідуючи цій логіці, необхідно прибрати сетер поля дати народження і ініціалізувати значення цього поля в конструкторі. Застосуємо рефакторинг <a href="/remove-setting-method">видалення сетера</a>.

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

#|ru| Так как публичного сеттера в классе теперь нет, нам нужно удалить его использование в клиентском коде. Действие этого сеттера пока нечем компенсировать, но не волнуйтесь, мы рассмотрим это чуточку позже.
#|uk| Так як сетера в класі тепер немає, нам потрібно видалити його використання в клієнтському коді. Дію цього сетера поки нічим компенсувати, але не хвилюйтеся, ми розглянемо це трішки пізніше.

Remove selected

Set step 2

Go to before "Customer Get"

#|ru| Есть ещё одна проблема. Объекты-значения с одинаковыми данными должны быть равны при сравнении. Чтобы сделать это на языке C#, нужно переопределить в сравниваемых классах методы <code>Equals</code> и <code>GetHashCode</code>.
#|uk| Є ще одна проблема. Об'єкти-значення з однаковими даними повинні бути рівні при порівнянні. Щоб зробити це в C#, потрібно визначити в класах, що порівнюються, спеціальні методи <code>Equals</code> и <code>GetHashCode</code>.

#|ru| Вот так это будет выглядеть в нашем случае.
#|uk| Ось так це буде виглядати в нашому випадку.

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

#|ru| Теперь сравнение вида <code>new Customer("John").Equals(new Customer("John"))</code> будет возвращать <code>TRUE</code>.
#|uk| Тепер порівняння виду <code>new Customer("John").Equals(new Customer("John"))</code> повертатиме <code>TRUE</code>.

Set step 3

Select:
```
  private static Hashtable instances = new Hashtable();


```
+ Select whole "Get"

#|ru| Остался последний штрих. Так как у нас теперь нет нужды хранить реестр созданных объектов, фабричный метод можно удалить, а конструктор сделать публичным.
#|uk| Залишився останній штрих. Так як у нас тепер немає потреби зберігати реєстр створених об'єктів, фабричний метод можна видалити, а конструктор зробити публічним.

Remove selected

Wait 500ms

Select "|||private||| Customer"

Replace "public"

Wait 500ms

Select "Customer.Get("John Smith")"

#|ru| После всех этих изменений, клиентский код тоже изменится.
#|uk| Після всіх цих змін, клієнтський код теж зміниться.

Print "new Customer("John Smith", new DateTime(1985, 1, 1))"

#C|ru| Запускаем финальную компиляцию.
#S Отлично, все работает!

#C|uk| Запускаємо фінальну компіляцію.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.