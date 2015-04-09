extract-superclass:csharp

###

1.ru. Создайте абстрактный суперкласс.

1.en. Create an abstract superclass.

1.uk. Створіть абстрактний суперклас.

2.ru. Используйте <a href="/pull-up-field">подъём поля</a>, <a href="/pull-up-method">подъём метода</a> и <a href="/pull-up-constructor-body">подъём тела конструктора</a> для перемещения общей функциональности в суперкласс. Лучше начинать с полей, т.к. помимо общих полей, вам нужно будет перенести те из них, которые используются в общих методах.

2.en. Use <a href="/pull-up-field">Pull Up Field</a>, <a href="/pull-up-method">Pull Up Method</a>, and <a href="/pull-up-constructor-body">Pull Up Constructor Body</a> to move the common functionality to a superclass. Start with the fields, since in addition to the common fields you will need to move the fields that are used in the common methods.

2.uk. Використайте <a href="/pull-up-field">підйом поля</a>, <a href="/pull-up-method">підйом методу</a> і <a href="/pull-up-constructor-body">підйом тіла конструктора</a> для переміщення загальної функціональності в суперклас. Краще розпочинати з полів, оскільки окрім загальних полів, вам треба буде перенести ті з них, які використовуються в загальних методах.

3.ru. Стоит поискать места в клиентском коде, в которых можно заменить использование подклассов вашим общим классом (например, в объявлениях типов).

3.en. Look for places in the client code where use of subclasses can be replaced with your new class (such as in type declarations).

3.uk. Варто пошукати місця в клієнтському коді, в яких можна замінити використання підкласів вашим загальним класом (наприклад, в оголошеннях типів).



###

```
public class Employee
{
  private int annualCost;

  public string Name { get; private set; }
  public string Id { get; private set; }

  public Employee(string name, string id, int annualCost)
  {
    Name = name;
    Id = id;
    this.annualCost = annualCost;
  }

  public int GetAnnualCost()
  {
    return annualCost;
  }
}

public class Department
{
  private List<Employee> staff = new List<Employee>();

  public string Name { get; private set; }
  public int HeadCount
  {
    get{ return staff.Count; }
  }
  public IList<Employee> Staff
  {
    get{ return staff.AsReadOnly(); }
  }

  public Department(string name): base(i)
  {
    Name = name;
  }

  public void AddStaff(Employee item)
  {
    staff.Add(item);
  }
  public int GetTotalAnnualCost()
  {
    return staff.Sum(i => i.GetAnnualCost());
  }
}
```

###

```
public abstract class Party
{
  public string Name { get; protected set; }
  public abstract int HeadCount { get; }

  protected Party(string name)
  {
    Name = name;
  }

  public abstract int GetAnnualCost();
}

public class Employee: Party
{
  private int annualCost;

  public string Id { get; private set; }
  public override int HeadCount
  {
    get{ return 1; }
  }

  public Employee(string name, string id, int annualCost): base(name)
  {
    Id = id;
    this.annualCost = annualCost;
  }

  public override int GetAnnualCost()
  {
    return annualCost;
  }
}

public class Department: Party
{
  private List<Party> staff = new List<Party>();

  public override int HeadCount
  {
    get{ return staff.Sum(i => i.HeadCount); }
  }
  public IList<Party> Staff
  {
    get{ return staff.AsReadOnly(); }
  }

  public Department(string name): base(name)
  {
  }

  public void AddStaff(Party item)
  {
    staff.Add(item);
  }
  public override int GetAnnualCost()
  {
    return staff.Sum(i => i.GetAnnualCost());
  }
}
```

###

Select name of "public Department"