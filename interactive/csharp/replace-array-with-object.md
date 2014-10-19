replace-array-with-object:csharp

###

1. Создайте новый класс, который будет содержать данные из массива. Поместите в него сам массив как публичное поле.

2. Создайте поле для хранения объекта этого класса в исходном классе.

3. В новом классе, один за другим создайте публичные свойства для всех элементов массива.

4. Когда все элементы продублированы, удалите массив.



###

```
public class Tournament
{
  string[] row = new string[2];

  public Tournament()
  {
    row[0] = "Liverpool";
    row[1] = "15";
  }
  public void DisplayScore()
  {
    string name = row[0];
    int score = Convert.ToInt32(row[1]);
    // ...
  }
}
```

###

```
public class Tournament
{
  Performance row = new Performance();

  public Tournament()
  {
    row.Name = "Liverpool";
    row.Score = 15;
  }
  public void DisplayScore()
  {
    string name = row.Name;
    int score = row.Score;
    // ...
  }
}

public class Performance
{
  public string Name
  {
    get;
    set;
  }
  public int Score
  {
    get;
    set;
  }
}
```

###

Set step 1

# Давайте рассмотрим рефакторинг <i>Замена массива объектом</i> на примере класса, хранящего название спортивной команды и количество заработанных ею очков.

Go to the end of file

# Превращение массива в объект начинается с создания класса.

Print:
```


public class Performance
{
}
```

Go to the start of "Performance"

# На первом этапе мы добавляем в новый класс такое же поле массив, как и в оригинале. Не беспокойтесь, мы избавимся от него чуть позже.

Print:
```

  public string[] data = new string[2];
```

Set step 2

Select name of "Tournament"

# Теперь нужно найти места, в которых идут обращения к этому массиву, и заменить их обращениями к вашему новому классу.

Select "string[] row = new string[2]"

# В первую очередь создаём сам объект в том месте, где был инициирован массив данных.

Print:
```
Performance row = new Performance()
```

# Теперь нужно заменить код, использующий массив.

Select "row" in "public Tournament"
+Select "row" in "DisplayScore"

Replace "row.data"

Set step 3

Go to the end of "Performance"

# После этого в класс данных добавляем публичные свойства с более содержательными именами. Начнём с поля названия команды.

Print:
```

  public string Name
  {
    get;
    set;
  }
```

Select "row.data[0] = "Liverpool""
+ Select "string name = row.data[0]"

# Теперь нужно поочерёдно заменить код присваивания значениям элементов массива так, чтобы везде применялись методы класса <code>Perfomance</code>.

Select "row.data[0] = "Liverpool""

Replace "row.Name = "Liverpool""

Wait 500ms

Select "row.data[0]"

Replace "row.Name"

Go to the end of "Performance"

# То же самое можно проделать со вторым элементом (обратите внимание, что мы сразу создаем свойство с желаемым типом <code>int</code>).

Print:
```

  public int Score
  {
    get;
    set;
  }
```


Select "row.data[1] = "15""

Replace "row.Score = 15"

Wait 500ms

Select "Convert.ToInt32(row.data[1])"

Replace "row.Score"

Set step 4

Select:
```
  public string[] data = new string[2];

```

# Выполнив замену для всех элементов массива, можно удалить и само объявление массива из класса.

Remove selected

#C Запускаем финальную компиляцию.

#S Отлично, все работает!

Set final step

#Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.