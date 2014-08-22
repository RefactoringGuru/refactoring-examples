self-encapsulate-field:csharp

###

1. Создайте свойство, инкапсулирующее поле.

2. Найдите все обращения к полю и замените их вызовами соответствующих свойств.

3. Если свойство лишь делегирует доступ к полю, не внося при этом дополнительной логики, то можете преобразовать его в автореализуемое свойство.



###

```
public class IntRange
{
  private int low, high;

  public IntRange(int low, int high)
  {
    this.low = low;
    this.high = high;
  }

  public bool Includes(int arg)
  {
    return arg >= low && arg <= high;
  }
  public void Grow(int factor)
  {
    high = high * factor;
  }
}
```

###

```
public class IntRange
{
  private int Low
  {
    get;
    set;
  }
  private int High
  {
    get;
    set;
  }

  public IntRange(int low, int high)
  {
    this.Low = low;
    this.High = high;
  }

  public bool Includes(int arg)
  {
    return arg >= Low && arg <= High;
  }
  public void Grow(int factor)
  {
    High = High * factor;
  }
}
```

###

Set step 1

# Давайте рассмотрим <i>Самоинкапсуляцию</i> на примере класса диапазонов.<br/><br/>Самоинкапсуляция заключается в реализации доступа к приватным полям через свойства даже в методах самого класса.

Select "private int |||low|||,"
+Select ", |||high|||;"

# Для начала создадим свойства, инкапсулирующие наши поля. При этом можно сделать их приватными, если к ним не требуется доступа извне.

Go to before "IntRange" in "IntRange"

Print:
```

  private int Low
  {
    get { return low; }
    set { low = value; }
  }
  private int High
  {
    get { return high; }
    set { high = value; }
  }

```

Set step 2

Select "low" in "Includes"
+ Select "high" in "Includes"
+ Select "high" in "Grow"

# В нашем примере есть несколько методов, которые используют прямой доступ к полям.

# Для завершения самоинкапсуляции давайте заменим все обращения к полям в этих методах на обращения к соответствующим свойствам.

Select "low" in "Includes"

Replace "Low"

Wait 500ms

Select "high" in "Includes"

Replace "High"

Wait 500ms

Select "high" in "Grow"

Replace "High"

Select "this.low"
+Select "this.high"

# Как вы могли заметить, мы еще не производили замену полей в конструкторе. Дело в том, что иногда в сеттерах может быть прописана логика, отличная от простого присваивания (которое требуется в конструкторе). 

Select "low = value;"
+Select "high = value;"

#^ Поэтому только убедившись, что наши сеттеры имеют стандартную логику присваивания, производим замену в конструкторе.

Select "this.|||low|||"

Replace "Low"

Select "this.|||high|||"

Replace "High"

Set step 3

# На этом рефакторинг можно считать законченным, но мы добавим коду еще немного лаконичности - преобразуем наши свойства в <i>автореализуемые свойства</i>.

Select " { return low; }"
+Select " { low = value; }"
+Select " { return high; }"
+Select " { high = value; }"

# Для этого удалим в свойствах тела геттеров и сеттеров...

Replace ";"

Select:
```
  private int low, high;


```

#^ ...после чего удалим ненужные больше поля <code>low</code> и <code>high</code>.

Remove selected

#C Все! Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.